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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static by.kharchenko.cafe.model.dao.SqlQuery.*;

public class ProductDaoImpl implements BaseDao<Ingredient>, ProductDao {
    private static final Logger logger = LogManager.getLogger(ProductDaoImpl.class);
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_PRODUCT_BY_ID)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> productData) throws DaoException {
        int result;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_PRODUCT)) {
            java.util.Date utilDate = format.parse(productData.get(DATE));
            Timestamp timeStamp = new Timestamp(utilDate.getTime());
            statement.setString(1, productData.get(NAME));
            statement.setTimestamp(2, timeStamp);
            statement.setBigDecimal(3, new BigDecimal(productData.get(PRICE)));
            result = statement.executeUpdate();
            if (result > 0) {
                return true;
            } else {
                throw new DaoException("failed to add in products table");
            }
        } catch (SQLException | ParseException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Ingredient> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> data) throws DaoException {
        String photoName = data.get(PHOTO_NAME);
        String query;
        boolean isNewPhoto;
        if (photoName.equals("")) {
            query = SqlQuery.UPDATE_PRODUCT_WHERE_NOT_NEW_PHOTO_BY_PRODUCT_ID;
            isNewPhoto = false;
        } else {
            query = SqlQuery.UPDATE_PRODUCT_WHERE_NEW_PHOTO_BY_PRODUCT_ID;
            isNewPhoto = true;
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, data.get(NAME));
            statement.setString(2, data.get(DATE));
            statement.setString(3, data.get(PRICE));
            if (isNewPhoto) {
                statement.setString(4, data.get(PHOTO));
                statement.setInt(5, Integer.parseInt(data.get(ID_PRODUCT)));
            } else {
                statement.setInt(4, Integer.parseInt(data.get(ID_PRODUCT)));
            }
            if (statement.executeUpdate() != 0) {
                return true;
            } else {
                throw new DaoException("Failed to update user");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
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
                logger.log(Level.ERROR, e);
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
                logger.log(Level.ERROR, e);
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
            logger.log(Level.ERROR, e);
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
            logger.log(Level.ERROR, e);
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
            logger.log(Level.ERROR, e);
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
            logger.log(Level.ERROR, e);
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
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> findProductByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_PRODUCT_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    ProductMapper.getInstance().rowMap(product, resultSet);
                    return Optional.of(product);
                }
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> findIdProductByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_PRODUCT_ID_BY_PRODUCT_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> findIdProductByNameAndNotId(String name, Integer idProduct) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ID_PRODUCT_WHERE_NAME_AND_NOT_ID_PRODUCT)) {
            statement.setString(1, name);
            statement.setInt(2, idProduct);
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
    public void deleteIngredientFromProduct(int idIngredient, int idProduct) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_INGREDIENT_FROM_PRODUCT)) {
            statement.setInt(1, idProduct);
            statement.setInt(2, idIngredient);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void changeGrams(Map<String, String> data) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_GRAMS_FROM_PRODUCTS_INGREDIENTS)) {
            statement.setDouble(1, Double.parseDouble(data.get(GRAMS)));
            statement.setInt(2, Integer.parseInt(data.get(ID_PRODUCT)));
            statement.setInt(3, Integer.parseInt(data.get(ID_INGREDIENT)));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean addIngredientsIdInProductsIngredientsTableByIdProduct(int idProduct, List<Integer> idList) throws DaoException {
        int result;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement findIdProducts = connection.prepareStatement(SqlQuery.SELECT_INGREDIENTS_ID_BY_PRODUCT_ID)) {
            connection.setAutoCommit(false);
            findIdProducts.setInt(1, idProduct);
            try (ResultSet resultSet = findIdProducts.executeQuery()) {
                List<Integer> ingredientsId = new ArrayList<>();
                while (resultSet.next()) {
                    ingredientsId.add(resultSet.getInt(1));
                }
                for (int i = 0; i < idList.size(); i++) {
                    for (int j = 0; j < ingredientsId.size(); j++) {
                        if (idList.size() == 0) {
                            break;
                        }
                        if (idList.get(i).equals(ingredientsId.get(j))) {
                            idList.remove(i);
                            i = 0;
                            j = 0;
                        }
                    }
                }
            }
            StringBuilder query = new StringBuilder(INSERT_INGREDIENTS_ID_BY_PRODUCT_ID);
            int size = idList.size();
            if (size > 0) {
                connection.setAutoCommit(false);
                query.append(" ( ?, ? ),".repeat(Math.max(0, size - 1)));
                query.append(" ( ?, ? )");
                try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
                    int i = 0;
                    for (Integer id : idList) {
                        statement.setInt(++i, idProduct);
                        statement.setInt(++i, id);
                    }
                    result = statement.executeUpdate();
                    if (result > 0) {
                        connection.commit();
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
}
