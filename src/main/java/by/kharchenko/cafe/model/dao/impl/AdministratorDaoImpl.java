package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.AdministratorDao;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.mapper.impl.AdministratorMapper;
import by.kharchenko.cafe.model.mapper.impl.UserMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.kharchenko.cafe.model.dao.SqlQuery.SELECT_USERS_BY_USERS_ID;

public class AdministratorDaoImpl implements BaseDao<Administrator>, AdministratorDao {
    private static final Logger logger = LogManager.getLogger(AdministratorDaoImpl.class);
    private static final AdministratorDaoImpl instance = new AdministratorDaoImpl();

    private AdministratorDaoImpl() {
    }

    public static AdministratorDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Administrator administrator) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean add(Administrator userData) throws DaoException {
        return false;
    }

    @Override
    public List<Administrator> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Administrator t) throws DaoException {
        return false;
    }

    @Override
    public Administrator findAdministratorByUserId(int idUser) throws DaoException {
        Administrator administrator = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement userStatement = connection.prepareStatement(SqlQuery.SELECT_USER_BY_USER_ID);
             PreparedStatement administratorStatement = connection.prepareStatement(SqlQuery.SELECT_ADMINISTRATOR_BY_USER_ID)) {
            userStatement.setInt(1, idUser);
            administratorStatement.setInt(1, idUser);
            try (ResultSet userResultSet = userStatement.executeQuery();
                 ResultSet clientResultSet = administratorStatement.executeQuery()) {
                if (userResultSet.next() && clientResultSet.next()) {
                    administrator = new Administrator();
                    UserMapper.getInstance().rowMap(administrator, userResultSet);
                    AdministratorMapper.getInstance().rowMap(administrator, clientResultSet);
                }
                return administrator;
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Administrator> findApplications() throws DaoException {
        List<Administrator> administrators = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement userStatement = connection.prepareStatement(SqlQuery.SELECT_ADMINISTRATOR_APPLICATIONS);
             ResultSet resultSet = userStatement.executeQuery()) {
            connection.setAutoCommit(false);
            while (resultSet.next()) {
                Administrator administrator = new Administrator();
                AdministratorMapper.getInstance().rowMap(administrator, resultSet);
                administrators.add(administrator);
            }
            if (administrators.size() > 0) {
                StringBuilder query2 = new StringBuilder(SELECT_USERS_BY_USERS_ID);
                int size2 = administrators.size();
                query2.append("?, ".repeat(Math.max(0, size2 - 1)));
                query2.append("?)");
                try (PreparedStatement usersStatement = connection.prepareStatement(query2.toString())) {
                    for (int i = 0; i < size2; i++) {
                        usersStatement.setInt(i + 1, administrators.get(i).getIdUser());
                    }
                    try (ResultSet resultSet2 = usersStatement.executeQuery()) {
                        int i = 0;
                        while (resultSet2.next()) {
                            UserMapper.getInstance().rowMap(administrators.get(i), resultSet2);
                            i++;
                        }
                    }
                }
            }
            connection.commit();
            return administrators;
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
    }

    @Override
    public void approval(int parseInt) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.APPROVAL_ADMINISTRATOR_BY_ID)) {
            statement.setInt(1, parseInt);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

}
