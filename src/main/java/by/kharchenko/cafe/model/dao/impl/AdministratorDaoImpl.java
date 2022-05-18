package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.AdministratorDao;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.mapper.impl.AdministratorMapper;
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

public class AdministratorDaoImpl implements AdministratorDao, BaseDao<Administrator> {
    private static final AdministratorDaoImpl instance = new AdministratorDaoImpl();

    private AdministratorDaoImpl() {
    }

    public static AdministratorDaoImpl getInstance() {
        return instance;
    }


    @Override
    public Optional<Administrator> findAdministratorByUserId(int id) throws DaoException {
        Administrator administrator = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement userStatement = connection.prepareStatement(SqlQuery.SELECT_USER_BY_USER_ID); ) {
            userStatement.setInt(1, id);
            ResultSet resultSet = userStatement.executeQuery();
            if (resultSet.next()) {
                administrator = new Administrator();
                UserMapper.getInstance().rowMap(administrator, resultSet);
                try (PreparedStatement AdministratorStatement = connection.prepareStatement(SqlQuery.SELECT_ADMINISTRATOR_BY_USER_ID)) {
                    AdministratorStatement.setInt(1, id);
                    try (ResultSet resultSet1 = AdministratorStatement.executeQuery()){
                        if (resultSet1.next()) {
                            AdministratorMapper.getInstance().rowMap(administrator, resultSet1);
                        }
                    }
                }
                return Optional.of(administrator);
            }
            return Optional.of(administrator);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void addInHistory(Map<String, String> userData) {

    }

    @Override
    public boolean insert(Administrator administrator) throws DaoException {
        return false;
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
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_ADMINISTRATOR)) {
            statement.setDouble(1, Double.parseDouble(userData.get(EXPERIENCE)));
            statement.setString(2, userData.get(STATUS));
            statement.setInt(3, Integer.parseInt(userData.get(ID_USER)));
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
    public List<Administrator> findAll() throws DaoException {
        return null;
    }

    @Override
    public Administrator update(Administrator administrator) throws DaoException {
        return null;
    }
}
