package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.OrderDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.mapper.impl.OrderMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.kharchenko.cafe.model.dao.SqlQuery.INSERT_PRODUCTS_ID_BY_ORDER_ID;

public class OrderDaoImpl implements BaseDao<Order>, OrderDao {
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);
    private static final OrderDaoImpl instance = new OrderDaoImpl();

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Order ingredient) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        int result;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement deleteFromOrders = connection.prepareStatement(SqlQuery.DELETE_ORDER_BY_ID_FROM_ORDERS_TABLE);
             PreparedStatement deleteFromOrdersProducts = connection.prepareStatement(SqlQuery.DELETE_ORDER_BY_ID_FROM_ORDERS_PRODUCTS_TABLE);) {
            connection.setAutoCommit(false);
            deleteFromOrdersProducts.setInt(1, id);
            deleteFromOrdersProducts.executeUpdate();
            deleteFromOrders.setInt(1, id);
            result = deleteFromOrders.executeUpdate();
            if (result > 0) {
                connection.commit();
                return true;
            } else {
                throw new DaoException("failed to delete in orders table");
            }
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
    public boolean add(Order orderData) throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_ORDER)) {
            statement.setString(1, orderData.getName());
            statement.setDate(2, Date.valueOf(orderData.getDate()));
            statement.setInt(3, orderData.getIdClient());
            statement.setString(4, orderData.getPaymentType().toString().toLowerCase());
            result = statement.executeUpdate();
            if (result > 0) {
                return true;
            } else {
                throw new DaoException("failed to add in users table");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Order orderData) throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_ORDER)) {
            statement.setString(1, orderData.getName());
            statement.setString(2, orderData.getPaymentType().toString().toLowerCase());
            statement.setDate(3, Date.valueOf(orderData.getDate()));
            statement.setInt(4, orderData.getIdOrder());
            result = statement.executeUpdate();
            if (result > 0) {
                return true;
            } else {
                throw new DaoException("failed to add in users table");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findNewOrdersByIdClient(int idClient) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_NEW_ORDERS_BY_CLIENT_ID)) {
            statement.setInt(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order(idClient);
                    OrderMapper.getInstance().rowMap(order, resultSet);
                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findOrdersByIdClient(int idClient) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ORDERS_BY_CLIENT_ID)) {
            statement.setInt(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order(idClient);
                    OrderMapper.getInstance().rowMap(order, resultSet);
                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Integer> findIdOrderByIdAndName(String name, int idClient) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ORDER_ID_BY_ID_CLIENT_AND_ORDER_NAME)) {
            statement.setInt(1, idClient);
            statement.setString(2, name);
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
    public Optional<Order> findOrderByOrderId(int parseInt) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ORDER_BY_ORDER_ID)) {
            statement.setInt(1, parseInt);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = new Order();
                    OrderMapper.getInstance().rowMap(order, resultSet);
                    return Optional.of(order);
                }
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteProductFromOrder(int idProduct, int idOrder) throws DaoException {
        int result;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_PRODUCT_FROM_ORDER)) {
            connection.setAutoCommit(false);
            statement.setInt(1, idProduct);
            statement.setInt(2, idOrder);
            result = statement.executeUpdate();
            if (result > 0) {
                boolean match = calculateOrderPrice(connection, idOrder);
                if (match) {
                    connection.commit();
                } else {
                    throw new DaoException("failed to delete in order_products table");
                }
            } else {
                throw new DaoException("failed to delete in order_products table");
            }
        } catch (SQLException | DaoException e) {
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
        return true;
    }

    private boolean calculateOrderPrice(Connection connection, int idOrder) throws DaoException {
        int result2;
        try (PreparedStatement findIdProducts = connection.prepareStatement(SqlQuery.SELECT_PRODUCTS_ID_BY_ORDER_ID);
             PreparedStatement updateOrderPrice = connection.prepareStatement(SqlQuery.UPDATE_ORDER_PRICE_BY_ORDER_ID)) {
            findIdProducts.setInt(1, idOrder);
            try (ResultSet resultSet = findIdProducts.executeQuery()) {
                List<Integer> productsId = new ArrayList<>();
                while (resultSet.next()) {
                    productsId.add(resultSet.getInt(1));
                }
                BigDecimal orderPrice;
                if (productsId.size() > 0) {
                    List<BigDecimal> allPrice = new ArrayList<>();
                    StringBuilder query = new StringBuilder(SqlQuery.SELECT_PRODUCTS_PRICE_BY_ID_PRODUCTS);
                    int size = productsId.size();
                    query.append("?, ".repeat(Math.max(0, size - 1)));
                    query.append("?)");
                    try (PreparedStatement findAllPrice = connection.prepareStatement(query.toString())) {
                        for (int i = 0; i < size; i++) {
                            findAllPrice.setInt(i + 1, productsId.get(i));
                        }
                        try (ResultSet resultSet2 = findAllPrice.executeQuery();) {
                            while (resultSet2.next()) {
                                allPrice.add(resultSet2.getBigDecimal(1));
                            }
                            orderPrice = allPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                        }
                    }
                } else {
                    orderPrice = new BigDecimal(0);
                }
                updateOrderPrice.setBigDecimal(1, orderPrice);
                updateOrderPrice.setInt(2, idOrder);
                result2 = updateOrderPrice.executeUpdate();
                if (result2 > 0) {
                    return true;
                } else {
                    throw new DaoException("failed to update in orders table");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean addProductsIdInOrdersProductsTableByIdOrder(Integer idOrder, List<Integer> idList) throws DaoException {
        int result;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement findIdProducts = connection.prepareStatement(SqlQuery.SELECT_PRODUCTS_ID_BY_ORDER_ID)) {
            findIdProducts.setInt(1, idOrder);
            try (ResultSet resultSet = findIdProducts.executeQuery()) {
                List<Integer> productsId = new ArrayList<>();
                while (resultSet.next()) {
                    productsId.add(resultSet.getInt(1));
                }
                for (int i = 0; i < idList.size(); i++) {
                    for (int j = 0; j < productsId.size(); j++) {
                        if (idList.size() == 0) {
                            break;
                        }
                        if (idList.get(i).equals(productsId.get(j))) {
                            idList.remove(i);
                            i = 0;
                            j = 0;
                        }
                    }
                }
            }
            StringBuilder query = new StringBuilder(INSERT_PRODUCTS_ID_BY_ORDER_ID);
            int size = idList.size();
            if (size > 0) {
                connection.setAutoCommit(false);
                query.append(" ( ?, ? ),".repeat(Math.max(0, size - 1)));
                query.append(" ( ?, ? )");
                try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
                    int i = 0;
                    for (Integer id : idList) {
                        statement.setInt(++i, idOrder);
                        statement.setInt(++i, id);
                    }
                    result = statement.executeUpdate();
                    if (result > 0) {
                        boolean match = calculateOrderPrice(connection, idOrder);
                        if (match) {
                            connection.commit();
                        } else {
                            throw new DaoException("failed to update in orders table");
                        }
                    } else {
                        throw new DaoException("failed to update in orders table");
                    }
                }
            }
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
        return true;
    }

    @Override
    public Optional<Integer> findIdOrderByIdAndNameAndNoIdOrder(String name, Integer idClient, Integer idOrder) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ORDER_ID_BY_ID_CLIENT_AND_ORDER_NAME_AND_NOT_ID_ORDER)) {
            statement.setInt(1, idClient);
            statement.setString(2, name);
            statement.setInt(3, idOrder);
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
    public List<Order> findTodayOrders() throws DaoException {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_TODAY_ORDERS)) {
            statement.setDate(1, date);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    OrderMapper.getInstance().rowMap(order, resultSet);
                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public BigDecimal priceByOrderId(int idOrder) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_PRICE_BY_ORDER_ID)) {
            statement.setInt(1, idOrder);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                } else {
                    throw new DaoException("no client account");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public Order.PaymentType paymentTypeByOrderId(int idOrder) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_PAYMENT_TYPE_BY_ORDER_ID)) {
            statement.setInt(1, idOrder);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Order.PaymentType.valueOf(resultSet.getString(1).toUpperCase());
                } else {
                    throw new DaoException("no client account");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }
}
