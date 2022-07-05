package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.dao.UserDao;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDaoImpl implements UserDao, BaseDao<User> {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
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
    public boolean add(User user) throws DaoException {
        int result;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            statement.setDate(6, Date.valueOf(user.getBirthday()));
            statement.setDate(7, new java.sql.Date(user.getRegistrationTime().getTime()) );
            statement.setString(8, user.getPhoneNumber());
            statement.setString(9, user.getRole().toString().toLowerCase());
            result = statement.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idUser = generatedKeys.getInt(1);
                        if (Objects.equals(user.getRole(), User.Role.CLIENT)) {
                            try (PreparedStatement clientStatement = connection.prepareStatement(SqlQuery.ADD_CLIENT)) {
                                clientStatement.setInt(1, idUser);
                                int clientResult = clientStatement.executeUpdate();
                                if (clientResult >= 1) {
                                    connection.commit();
                                    return true;
                                } else {
                                    throw new DaoException("failed to add in clients table");
                                }
                            }
                        } else if (Objects.equals(user.getRole(), User.Role.ADMINISTRATOR)) {
                            try (PreparedStatement administratorStatement = connection.prepareStatement(SqlQuery.ADD_ADMINISTRATOR)) {
                                administratorStatement.setInt(1, idUser);
                                int clientResult = administratorStatement.executeUpdate();
                                if (clientResult >= 1) {
                                    connection.commit();
                                    return true;
                                } else {
                                    throw new DaoException("failed to add in administrators table");
                                }
                            }
                        }
                    }
                }
            } else {
                throw new DaoException("failed to add in users table");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, e);
            }
            throw new DaoException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(User user) throws DaoException {
        String photoPath = user.getPhotoPath();
        String query;
        boolean isNewPhoto;
        if (photoPath.equals("")) {
            query = SqlQuery.UPDATE_USER_WHERE_NOT_NEW_PHOTO_BY_USER_ID;
            isNewPhoto = false;
        } else {
            query = SqlQuery.UPDATE_USER_WHERE_NEW_PHOTO_BY_USER_ID;
            isNewPhoto = true;
        }
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPhoneNumber());
            if (isNewPhoto) {
                statement.setString(5, user.getPhotoPath());
                statement.setInt(6, user.getIdUser());
            } else {
                statement.setInt(5, user.getIdUser());
            }
            if (statement.executeUpdate() != 0) {
                if (user.getRole().equals(User.Role.ADMINISTRATOR)) {
                    try (PreparedStatement updateAdmin = connection.prepareStatement(SqlQuery.UPDATE_ADMINISTRATOR_EXPERIENCE_BY_USER_ID)) {
                        updateAdmin.setDouble(1, ((Administrator)user).getExperience());
                        updateAdmin.setInt(2, user.getIdUser());
                        if (updateAdmin.executeUpdate() != 0) {
                            connection.commit();
                            return true;
                        } else {
                            throw new DaoException("Failed to update user");
                        }
                    }
                } else {
                    connection.commit();
                    return true;
                }
            } else {
                throw new DaoException("Failed to update user");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, e);
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
    public boolean authenticate(String login, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_PASSWORD_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                String passFromDb;
                if (resultSet.next()) {
                    passFromDb = resultSet.getString(1);
                    return password.equals(passFromDb);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public Optional<User.Role> findUserRoleByLogin(String login) throws DaoException {
        Optional<User.Role> role = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ROLE_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    role = Optional.of(User.Role.valueOf(resultSet.getString(1).toUpperCase()));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return role;
    }

    @Override
    public Optional<Integer> findIdUserByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ID_USER_BY_LOGIN)) {
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
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Integer> findIdUserWhereLoginAndNotIdUser(String login, Integer idUser) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ID_USER_WHERE_LOGIN_AND_NOT_ID_USER)) {
            statement.setString(1, login);
            statement.setInt(2, idUser);
            try (ResultSet resultSet = statement.executeQuery()) {
                Optional<Integer> id = Optional.empty();
                if (resultSet.next()) {
                    id = Optional.of(resultSet.getInt(1));
                    return id;
                }
                return id;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void refill(BigDecimal refill, Integer idClient) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_CLIENT_ACCOUNT_BI_CLIENT_ID);
             PreparedStatement insert = connection.prepareStatement(SqlQuery.INSERT_CLIENT_ACCOUNT_BI_CLIENT_ID)) {
            statement.setInt(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                BigDecimal clientAccount = null;
                if (resultSet.next()) {
                    clientAccount = resultSet.getBigDecimal(1);
                    BigDecimal sum = clientAccount.add(refill);
                    insert.setBigDecimal(1, sum);
                    insert.setInt(2, idClient);
                    insert.executeUpdate();
                } else {
                    throw new DaoException("failed to find client_account");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }
}
