package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.model.pool.ConnectionPool;
import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AbstractDaoTest {
    @BeforeTest
    public void dataBaseCreation() throws ManagedProcessException, FileNotFoundException {
        DB database = DB.newEmbeddedDB(9000);
        var is = new FileInputStream("src/test/resources/script.sql");

        database.start();
        database.source(is);
        ConnectionPool.getInstance();
    }
}
