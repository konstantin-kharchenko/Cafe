package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.ProductDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.mapper.impl.ProductMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;
import org.javatuples.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.kharchenko.cafe.controller.RequestParameter.ID_ORDER;
import static by.kharchenko.cafe.controller.RequestParameter.ID_PRODUCT;
import static by.kharchenko.cafe.model.dao.SqlQuery.SELECT_PRODUCTS_BY_ID_PRODUCTS;

public class ProductDaoImpl implements BaseDao<Ingredient>, ProductDao {
    private static final ProductDaoImpl instance = new ProductDaoImpl();

    private ProductDaoImpl() {
    }

    public static ProductDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Ingredient ingredient) throws DaoException {
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
    public List<Ingredient> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> t) throws DaoException {
        return false;
    }

    @Override
    public List<Product> findProductsByIdProducts(List<Integer> idList) throws DaoException {
        if (idList.size() > 0) {
            StringBuilder query = new StringBuilder(SELECT_PRODUCTS_BY_ID_PRODUCTS);
            int size = idList.size();
            query.append("?, ".repeat(Math.max(0, size - 1)));
            query.append("?)");
            List<Product> products = new ArrayList<>();

            try (Connection connection = ConnectionPool.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query.toString())) {
                for (int i = 0; i < size; i++) {
                    statement.setInt(i + 1, idList.get(i));
                }
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        Product product = new Product();
                        ProductMapper.getInstance().rowMap(product, resultSet);
                        products.add(product);
                    }
                }
                return products;

            } catch (SQLException | DaoException e) {
                throw new DaoException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addProductsInOrders(List<Order> orders) throws DaoException {
        if (orders != null && orders.size() > 0) {
            StringBuilder query = new StringBuilder(SqlQuery.SELECT_PRODUCTS_ID_BY_ORDERS_ID);
            int size = orders.size();
            query.append("?, ".repeat(Math.max(0, size - 1)));
            query.append("?)");
            List<Pair<Integer, Integer>> ordersProducts = new ArrayList<>();
            try (Connection connection = ConnectionPool.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query.toString())) {
                for (int i = 0; i < size; i++) {
                    statement.setInt(i + 1, orders.get(i).getIdOrder());
                }
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        int orderId = resultSet.getInt(ID_ORDER);
                        int idProduct = resultSet.getInt(ID_PRODUCT);
                        ordersProducts.add(new Pair<>(orderId, idProduct));
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
            Set<Integer> uniqueIdProducts = new HashSet<>();
            ordersProducts.forEach(a -> uniqueIdProducts.add(a.getValue1()));
            List<Product> products = findProductsByIdProducts(uniqueIdProducts.stream().toList());
            for (Order order : orders) {
                List<Integer> idProducts = new ArrayList<>();
                for (Pair<Integer, Integer> pair : ordersProducts) {
                    if (order.getIdOrder() == pair.getValue0()) {
                        idProducts.add(pair.getValue1());
                    }
                }
                boolean isHave;
                List<Product> orderProducts = new ArrayList<>();
                for (Product product : products) {
                    isHave = false;
                    int id = product.getIdProduct();
                    for (Integer idProduct : idProducts) {
                        if (id == idProduct) {
                            isHave = true;
                            break;
                        }
                    }
                    if (isHave) {
                        orderProducts.add(product);
                    }
                }
                order.setProducts(orderProducts);
            }
        }
        return false;
    }

    @Override
    public List<Product> findNewProducts() throws DaoException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_NEW_PRODUCTS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Product product = new Product();
                ProductMapper.getInstance().rowMap(product, resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int countProducts() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_PRODUCTS);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return 0;
    }

    @Override
    public List<Product> findProductsByPageNumber(int currentPage) throws DaoException {
        int offSet = (currentPage - 1) * 10;
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_PRODUCTS_BY_PAGE)) {
            statement.setInt(1, offSet);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    ProductMapper.getInstance().rowMap(product, resultSet);
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Integer> findProductsIdByOrderId(int idOrder) throws DaoException {
        List<Integer> menuListId = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_PRODUCTS_ID_BY_ORDER_ID)) {
            statement.setInt(1, idOrder);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    menuListId.add(resultSet.getInt(1));
                }
                return menuListId;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Product> findProductByProductId(int parseInt) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_PRODUCT_BY_ID_PRODUCT)) {
            statement.setInt(1, parseInt);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    ProductMapper.getInstance().rowMap(product, resultSet);
                    return Optional.of(product);
                }
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }
}
