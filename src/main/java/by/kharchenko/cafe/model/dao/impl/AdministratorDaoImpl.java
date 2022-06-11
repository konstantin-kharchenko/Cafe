package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.AdministratorDao;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.mapper.impl.AdministratorMapper;
import by.kharchenko.cafe.model.mapper.impl.ClientMapper;
import by.kharchenko.cafe.model.mapper.impl.UserMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdministratorDaoImpl implements BaseDao<Administrator>, AdministratorDao {
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
    public boolean add(Map<String, String> userData) throws DaoException {
        return false;
    }

    @Override
    public List<Administrator> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> t) throws DaoException {
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
            throw new DaoException(e);
        }
    }

}
