package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.dao.UserDao;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.pool.ConnectionPool;

import java.sql.*;
import java.util.*;

import static by.kharchenko.cafe.controller.RequestParameter.*;

public class UserDaoImpl implements UserDao, BaseDao<User> {

    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_LOGIN_PASS)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                String passFromDb;
                if (resultSet.next()) {
                    passFromDb = resultSet.getString(1);
                    return password.equals(passFromDb);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public Optional<Integer> findIdUserByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ID_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                Optional<Integer> id = Optional.empty();
                if (resultSet.next()) {
                    id = Optional.of(resultSet.getInt(1));
                    return id;
                }
                return id;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User.Role findUserRoleByLogin(String login) throws DaoException {
        User.Role role = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ROLE_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    role = User.Role.valueOf(resultSet.getString(1).toUpperCase());
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return role;
    }

    @Override
    public List<String> findLogins() throws DaoException {
        List<String> logins = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_LOGINS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                logins.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return logins;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean add(Map<String, String> userData) throws DaoException {
        int result;
        long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, userData.get(NAME));
            statement.setString(2, userData.get(SURNAME));
            statement.setString(3, userData.get(LOGIN));
            statement.setString(4, userData.get(PASSWORD));
            statement.setString(5, userData.get(EMAIL));
            statement.setInt(6, Integer.parseInt(userData.get(AGE)));
            statement.setTimestamp(7, timestamp);
            statement.setString(8, userData.get(PHONE_NUMBER));
            statement.setString(9, userData.get(ROLE));
            result = statement.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idUser = generatedKeys.getInt(1);
                        if (Objects.equals(userData.get(ROLE), User.Role.CLIENT.toString().toLowerCase())) {
                            try (PreparedStatement clientStatement = connection.prepareStatement(SqlQuery.ADD_CLIENT)) {
                                clientStatement.setBoolean(1, Boolean.parseBoolean(userData.get(IS_BLOCK)));
                                clientStatement.setInt(2, Integer.parseInt(userData.get(LOYALTY_POINTS)));
                                clientStatement.setString(3, userData.get(PAYMENT_TYPE));
                                clientStatement.setInt(4, idUser);
                                int clientResult = clientStatement.executeUpdate();
                                if (clientResult >= 1) {
                                    connection.commit();
                                    return true;
                                } else {
                                    throw new SQLException("failed to add in clients table");
                                }
                            }
                        }
                        if (Objects.equals(userData.get(ROLE), User.Role.ADMINISTRATOR.toString().toLowerCase())) {
                            try (PreparedStatement administratorStatement = connection.prepareStatement(SqlQuery.ADD_ADMINISTRATOR)) {
                                administratorStatement.setDouble(1, Double.parseDouble(userData.get(EXPERIENCE)));
                                administratorStatement.setString(2, userData.get(STATUS));
                                administratorStatement.setInt(3, idUser);
                                int clientResult = administratorStatement.executeUpdate();
                                if (clientResult >= 1) {
                                    connection.commit();
                                    return true;
                                } else {
                                    throw new SQLException("failed to add in administrators table");
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    throw new DaoException(e);
                } finally {
                    connection.setAutoCommit(true);
                }
            } else {
                throw new SQLException("failed to add in users table");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }
}
