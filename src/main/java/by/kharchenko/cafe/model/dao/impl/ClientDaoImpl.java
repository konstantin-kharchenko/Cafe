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

public class ClientDaoImpl implements BaseDao<Client>, ClientDao {
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
    public boolean add(Map<String, String> userData) throws DaoException {
        return false;
    }

    @Override
    public List<Client> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> t) throws DaoException {
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
            throw new DaoException(e);
        }
    }
}
