package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.ClientDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.mapper.impl.ClientMapper;
import by.kharchenko.cafe.model.mapper.impl.OrderMapper;
import by.kharchenko.cafe.model.mapper.impl.UserMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.*;
import java.util.*;

import static by.kharchenko.cafe.model.dao.SqlQuery.*;

public class ClientDaoImpl implements BaseDao<Client>, ClientDao {
    private static final Logger logger = LogManager.getLogger(ClientDaoImpl.class);
    private static final ClientDaoImpl instance = new ClientDaoImpl();

    private ClientDaoImpl() {
    }

    public static ClientDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Client client) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean add(Client userData) throws DaoException {
        return false;
    }

    @Override
    public List<Client> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Client t) throws DaoException {
        return false;
    }

    @Override
    public Client findClientByUserId(int idUser) throws DaoException {
        Client client = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement userStatement = connection.prepareStatement(SqlQuery.SELECT_USER_BY_USER_ID);
             PreparedStatement clientStatement = connection.prepareStatement(SqlQuery.SELECT_CLIENT_BY_USER_ID)) {
            userStatement.setInt(1, idUser);
            clientStatement.setInt(1, idUser);
            try (ResultSet userResultSet = userStatement.executeQuery();
                 ResultSet clientResultSet = clientStatement.executeQuery()) {
                if (userResultSet.next() && clientResultSet.next()) {
                    client = new Client();
                    UserMapper.getInstance().rowMap(client, userResultSet);
                    ClientMapper.getInstance().rowMap(client, clientResultSet);
                }
                return client;
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Client> findClientsWithOverdueOrders() throws DaoException {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_OVERDUE_ORDERS)) {
            statement.setDate(1, date);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    OrderMapper.getInstance().rowMap(order, resultSet);
                    orders.add(order);
                }
                if (orders.size() > 0) {
                    Set<Integer> clientsId = new HashSet<>();
                    orders.forEach(a -> clientsId.add(a.getIdClient()));
                    StringBuilder query = new StringBuilder(SELECT_CLIENTS_BY_CLIENTS_ID);
                    int size = clientsId.size();
                    query.append("?, ".repeat(Math.max(0, size - 1)));
                    query.append("?)");
                    List<Integer> integerList = clientsId.stream().toList();
                    try (PreparedStatement clientsStatement = connection.prepareStatement(query.toString())) {
                        for (int i = 0; i < size; i++) {
                            clientsStatement.setInt(i + 1, integerList.get(i));
                        }
                        List<Client> clients = new ArrayList<>();
                        try (ResultSet resultSet1 = clientsStatement.executeQuery()) {
                            while (resultSet1.next()) {
                                Client client = new Client();
                                ClientMapper.getInstance().rowMap(client, resultSet1);
                                clients.add(client);
                            }
                        }
                        StringBuilder query2 = new StringBuilder(SELECT_USERS_BY_USERS_ID);
                        int size2 = clients.size();
                        query2.append("?, ".repeat(Math.max(0, size2 - 1)));
                        query2.append("?)");
                        try (PreparedStatement usersStatement = connection.prepareStatement(query2.toString())) {
                            for (int i = 0; i < size2; i++) {
                                usersStatement.setInt(i + 1, clients.get(i).getIdUser());
                            }
                            try (ResultSet resultSet2 = usersStatement.executeQuery()) {
                                int i = 0;
                                while (resultSet2.next()) {
                                    UserMapper.getInstance().rowMap(clients.get(i), resultSet2);
                                    i++;
                                }
                            }
                        }
                        for (Client client : clients) {
                            List<Order> ordersForClients = new ArrayList<>();
                            for (Order order : orders) {
                                if (client.getIdClient() == order.getIdClient()) {
                                    ordersForClients.add(order);
                                }
                            }
                            client.setOrders(ordersForClients);
                        }
                        return clients;
                    }
                } else {
                    return new ArrayList<>();
                }
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Client> findClientsByPageNumber(int currentPage) throws DaoException {
        int offSet = (currentPage - 1) * 10;
        List<Client> clients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement usersStatement = connection.prepareStatement(SqlQuery.SELECT_USERS_BY_PAGE);
             PreparedStatement clientsStatement = connection.prepareStatement(SqlQuery.SELECT_CLIENTS_BY_PAGE)) {
            usersStatement.setInt(1, offSet);
            clientsStatement.setInt(1, offSet);
            try (ResultSet resultSet = usersStatement.executeQuery();
                 ResultSet resultSet1 = clientsStatement.executeQuery();) {
                while (resultSet.next() && resultSet1.next()) {
                    Client client = new Client();
                    UserMapper.getInstance().rowMap(client, resultSet);
                    ClientMapper.getInstance().rowMap(client, resultSet1);
                    clients.add(client);
                }
                return clients;
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public int countClients() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_CLIENTS);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return 0;
    }

    @Override
    public List<Client> findClientsByIdClients(List<Integer> idClients) throws DaoException {
        if (idClients.size() > 0) {
            StringBuilder query = new StringBuilder(SELECT_CLIENTS_BY_CLIENTS_ID);
            int size = idClients.size();
            query.append("?, ".repeat(Math.max(0, size - 1)));
            query.append("?)");
            List<Client> clients = new ArrayList<>();
            try (Connection connection = ConnectionPool.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query.toString())) {
                for (int i = 0; i < size; i++) {
                    statement.setInt(i + 1, idClients.get(i));
                }
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        Client client = new Client();
                        ClientMapper.getInstance().rowMap(client, resultSet);
                        clients.add(client);
                    }
                    StringBuilder query1 = new StringBuilder(SELECT_USERS_BY_USERS_ID);
                    int size1 = clients.size();
                    query1.append("?, ".repeat(Math.max(0, size1 - 1)));
                    query1.append("?)");
                    try (PreparedStatement statement1 = connection.prepareStatement(query1.toString())) {
                        for (int i = 0; i < size1; i++) {
                            statement1.setInt(i + 1, clients.get(i).getIdUser());
                        }
                        int i = 0;
                        try (ResultSet resultSet1 = statement1.executeQuery()) {
                            while (resultSet1.next()) {
                                UserMapper.getInstance().rowMap(clients.get(i), resultSet1);
                                i++;
                            }
                        }
                    }
                }
                return clients;
            } catch (SQLException | DaoException e) {
                logger.log(Level.ERROR, e);
                throw new DaoException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean unlock(int parseInt) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UNLOCK_CLIENT_BY_CLIENT_ID);
        ) {
            statement.setInt(1, parseInt);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean addLoyaltyPoints(int idClient) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_LOYALTY_POINTS_BI_CLIENT_ID)) {
            statement.setInt(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int loyaltyPoints = resultSet.getInt(1);
                    loyaltyPoints++;
                    try (PreparedStatement statement1 = connection.prepareStatement(SqlQuery.UPDATE_LOYALTY_POINTS_BI_CLIENT_ID)) {
                        statement1.setInt(1, loyaltyPoints);
                        statement1.setInt(2, idClient);
                        return statement1.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public void subLoyaltyPoints(int idClient) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_LOYALTY_POINTS_BI_CLIENT_ID)) {
            statement.setInt(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int loyaltyPoints = resultSet.getInt(1);
                    if (loyaltyPoints != 0) {
                        loyaltyPoints--;
                        try (PreparedStatement statement1 = connection.prepareStatement(SqlQuery.UPDATE_LOYALTY_POINTS_BI_CLIENT_ID)) {
                            statement1.setInt(1, loyaltyPoints);
                            statement1.setInt(2, idClient);
                            statement1.executeUpdate();
                        }
                    }
                    if (loyaltyPoints == 0) {
                        try (PreparedStatement statement1 = connection.prepareStatement(SqlQuery.LOCK_CLIENT_BY_CLIENT_ID)) {
                            statement1.setInt(1, idClient);
                            statement1.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public Client findClientWithOverdueOrders(int idClient) throws DaoException {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_OVERDUE_ORDERS_BY_CLIENT_ID)) {
            statement.setDate(1, date);
            statement.setInt(2, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    OrderMapper.getInstance().rowMap(order, resultSet);
                    orders.add(order);
                }
                Set<Integer> clientsId = new HashSet<>();
                orders.forEach(a -> clientsId.add(a.getIdClient()));
                Client client = new Client();
                try (PreparedStatement clientsStatement = connection.prepareStatement(SELECT_CLIENT_BY_CLIENTS_ID)) {
                    clientsStatement.setInt(1, idClient);
                    try (ResultSet resultSet1 = clientsStatement.executeQuery()) {
                        if (resultSet1.next()) {
                            ClientMapper.getInstance().rowMap(client, resultSet1);
                        }
                    }
                    try (PreparedStatement usersStatement = connection.prepareStatement(SELECT_USER_BY_USERS_ID)) {
                        usersStatement.setInt(1, client.getIdUser());
                        try (ResultSet resultSet2 = usersStatement.executeQuery()) {
                            if (resultSet2.next()) {
                                UserMapper.getInstance().rowMap(client, resultSet2);
                            }
                        }
                    }
                    List<Order> ordersForClients = new ArrayList<>();
                    for (Order order : orders) {
                        if (client.getIdClient() == order.getIdClient()) {
                            ordersForClients.add(order);
                        }
                    }
                    client.setOrders(ordersForClients);
                    return client;
                }
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public BigDecimal clientAccountByIdClient(int idClient) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CLIENT_ACCOUNT_BY_CLIENT_ID)) {
            statement.setInt(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                } else {
                    throw new DaoException("no client account");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void subPriceFromClientAccount(BigDecimal orderPrice, int idClient) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CLIENT_ACCOUNT_BY_CLIENT_ID)) {
            statement.setInt(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BigDecimal clientAccount = resultSet.getBigDecimal(1);
                    BigDecimal result = clientAccount.subtract(orderPrice);
                    try (PreparedStatement statement1 = connection.prepareStatement(UPDATE_CLIENT_ACCOUNT_BY_CLIENT_ID)) {
                        statement1.setBigDecimal(1, result);
                        statement1.setInt(2, idClient);
                        statement1.executeUpdate();
                    }
                } else {
                    throw new DaoException("no client account");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }
}
