package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.ClientDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.mapper.impl.ClientMapper;
import by.kharchenko.cafe.model.mapper.impl.UserMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestParameter.*;

public class ClientDaoImpl implements ClientDao, BaseDao<Client> {
    private static final ClientDaoImpl instance = new ClientDaoImpl();

    private ClientDaoImpl() {
    }

    public static ClientDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Client> findClientByUserId(int id) throws DaoException {
        Client client = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement userStatement = connection.prepareStatement(SqlQuery.SELECT_USER_BY_USER_ID)) {
            userStatement.setInt(1, id);
            try (ResultSet userResultSet = userStatement.executeQuery()) {
                if (userResultSet.next()) {
                    client = new Client();
                    UserMapper.getInstance().rowMap(client, userResultSet);
                    try (PreparedStatement clientStatement = connection.prepareStatement(SqlQuery.SELECT_CLIENT_BY_USER_ID)) {
                        clientStatement.setInt(1, id);
                        try (ResultSet clientResultSet = clientStatement.executeQuery()) {
                            if (clientResultSet.next()) {
                                ClientMapper.getInstance().rowMap(client, clientResultSet);
                            }
                        }
                    }
                    return Optional.of(client);
                }
                return Optional.of(client);
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean insert(Client client) throws DaoException {
        return false;
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
    public boolean add(Map<String, String> userData) throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_CLIENT)) {
            statement.setBoolean(1, Boolean.parseBoolean(userData.get(IS_BLOCK)));
            statement.setInt(2, Integer.parseInt(userData.get(LOYALTY_POINTS)));
            statement.setString(3, userData.get(PAYMENT_TYPE));
            statement.setInt(4, Integer.parseInt(userData.get(ID_USER)));
            result = statement.executeUpdate();
            if (result >= 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public List<Client> findAll() throws DaoException {
        return null;
    }

    @Override
    public Client update(Client client) throws DaoException {
        return null;
    }
}
