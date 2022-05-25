package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.MenuDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Menu;
import by.kharchenko.cafe.model.mapper.impl.MenuMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuDaoImpl implements MenuDao, BaseDao<Menu> {
    @Override
    public boolean insert(Menu menu) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Menu menu) throws DaoException {
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
    public List<Menu> findAll() throws DaoException {
        List<Menu> menuList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement userStatement = connection.prepareStatement(SqlQuery.SELECT_USER_BY_USER_ID);
             ResultSet resultSet = userStatement.executeQuery()) {
            while (resultSet.next()) {
                Menu menu = new Menu();
                MenuMapper.getInstance().rowMap(menu, resultSet);
                menuList.add(menu);
            }
            return menuList;

        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Menu update(Menu menu) throws DaoException {
        return null;
    }
}
