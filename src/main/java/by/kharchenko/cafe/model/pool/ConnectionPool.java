package by.kharchenko.cafe.model.pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final ReentrantLock lock = new ReentrantLock(true);
    private static final int CAPACITY = 10;
    private static final AtomicBoolean isCreate = new AtomicBoolean(false);
    private static final String URL = "url";
    private static final Properties properties;
    private static final String PROPERTIES_PATH = "connection.configuration/connection.properties";
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> free = new LinkedBlockingDeque<>(CAPACITY);
    private final BlockingQueue<ProxyConnection> used = new LinkedBlockingDeque<>(CAPACITY);

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            //log
            throw new ExceptionInInitializerError(e);
        }
        ClassLoader classLoader = ConnectionPool.class.getClassLoader();
        InputStream fileInputStream;
        properties = new Properties();
        try {
            fileInputStream = classLoader.getResourceAsStream(PROPERTIES_PATH);
            properties.load(fileInputStream);
        } catch (IOException e) {
            //log
            throw new ExceptionInInitializerError(e);
        }
    }

    public static ConnectionPool getInstance() {
        if (!isCreate.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreate.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
        try {
            for (int i = 0; i < CAPACITY; i++) {
                Connection connection = DriverManager.getConnection(properties.getProperty(URL), properties);
                free.add(new ProxyConnection(connection));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = free.take();
            used.put(connection);
        } catch (InterruptedException e) {
            //log
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean isRemoved = false;
        if (connection instanceof ProxyConnection) {
            isRemoved = used.remove(connection);
            if (isRemoved) {
                try {
                    free.put((ProxyConnection) connection);
                } catch (InterruptedException e) {
                    //log
                    Thread.currentThread().interrupt();
                }
            }
        }
        return isRemoved;
    }

    public void deregisterDriver() {
        try {
            DriverManager.deregisterDriver(DriverManager.getDriver(properties.getProperty(URL)));
        } catch (SQLException e) {
            //log
        }
    }

    public void destroyPool() {
        for (int i = 0; i < CAPACITY; i++) {
            try {
                free.take().close();
            } catch (SQLException | InterruptedException e) {
                //log //todo
            }
        }
    }
}
