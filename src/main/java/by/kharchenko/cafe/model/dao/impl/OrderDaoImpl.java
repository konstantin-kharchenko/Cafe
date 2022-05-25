package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.OrderDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.mapper.impl.OrderMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao, BaseDao<Order> {
    private static final OrderDaoImpl instance = new OrderDaoImpl();

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        return instance;
    }
    @Override
    public boolean insert(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Order order) throws DaoException {
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
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Order update(Order order) throws DaoException {
        return null;
    }

    @Override
    public List<Order> findOrdersByIdClient(int idClient) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ORDERS_BY_CLIENT_ID)){
             statement.setInt(1, idClient);
             try(ResultSet resultSet = statement.executeQuery()) {
                 while (resultSet.next()) {
                     Order order = new Order(idClient);
                     OrderMapper.getInstance().rowMap(order, resultSet);
                     orders.add(order);
                 }
                 return orders;
             }
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Integer> findMenuIdByOrderId(int idOrder) throws DaoException {
        List<Integer> menuListId = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_MENU_ID_BY_ORDER_ID)){
            statement.setInt(1, idOrder);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    menuListId.add(resultSet.getInt(1));
                }
                return menuListId;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
