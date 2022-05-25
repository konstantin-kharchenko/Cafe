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

import static by.kharchenko.cafe.model.dao.SqlQuery.SELECT_MENU_LIST_BY_ID_LIST;

public class MenuDaoImpl implements MenuDao, BaseDao<Menu> {
    private static final MenuDaoImpl instance = new MenuDaoImpl();

    private MenuDaoImpl() {
    }

    public static MenuDaoImpl getInstance() {
        return instance;
    }

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
             PreparedStatement userStatement = connection.prepareStatement(SqlQuery.SELECT_ALL_MENU);
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

    @Override
    public List<Menu> findMenuListByIdList(List<Integer> idList) throws DaoException {
        StringBuilder query = new StringBuilder(SELECT_MENU_LIST_BY_ID_LIST);
        int size = idList.size();
        query.append("?, ".repeat(Math.max(0, size - 1)));
        query.append("?)");
        List<Menu> menuList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < size; i++) {
                statement.setInt(i + 1, idList.get(i));
            }
            try (ResultSet resultSet = statement.executeQuery();){
                while (resultSet.next()) {
                    Menu menu = new Menu();
                    MenuMapper.getInstance().rowMap(menu, resultSet);
                    menuList.add(menu);
                }
            }
            return menuList;

        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
    }
}
