package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.IngredientDao;
import by.kharchenko.cafe.model.dao.SqlQuery;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.mapper.impl.IngredientMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static by.kharchenko.cafe.model.dao.SqlQuery.*;

public class IngredientDaoImpl implements BaseDao<Ingredient>, IngredientDao {
    private static final Logger logger = LogManager.getLogger(IngredientDaoImpl.class);
    private static final IngredientDaoImpl instance = new IngredientDaoImpl();

    private IngredientDaoImpl() {
    }

    public static IngredientDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Ingredient ingredient) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.BLOCK_INGREDIENT_BY_ID)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean add(Ingredient ingredientData) throws DaoException {
        int result;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_INGREDIENT)) {
            statement.setString(1, ingredientData.getName());
            statement.setDate(2, Date.valueOf(ingredientData.getShelfLife()));
            result = statement.executeUpdate();
            if (result > 0) {
                return true;
            } else {
                throw new DaoException("failed to add in ingredients table");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Ingredient> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Ingredient ingredientData) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_INGREDIENT_BY_ID)) {
            statement.setString(1, ingredientData.getName());
            statement.setDate(2, Date.valueOf(ingredientData.getShelfLife()));
            statement.setInt(3, ingredientData.getIdIngredient());
            if (statement.executeUpdate() != 0) {
                return true;
            } else {
                throw new DaoException("Failed to update ingredient");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Ingredient> findIngredientsByIdIngredients(List<Pair<Integer, Double>> idList) throws DaoException {
        if (idList.size() > 0) {
            StringBuilder query = new StringBuilder(SELECT_INGREDIENTS_BY_ID_LIST);
            int size = idList.size();
            query.append("?, ".repeat(Math.max(0, size - 1)));
            query.append("?)");
            List<Ingredient> ingredients = new ArrayList<>();
            try (Connection connection = ConnectionPool.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query.toString())) {
                for (int i = 0; i < size; i++) {
                    statement.setInt(i + 1, idList.get(i).getValue0());
                }
                try (ResultSet resultSet = statement.executeQuery();) {
                    int i = 0;
                    while (resultSet.next()) {
                        Ingredient ingredient = new Ingredient();
                        ingredient.setGrams(idList.get(i).getValue1());
                        IngredientMapper.getInstance().rowMap(ingredient, resultSet);
                        ingredients.add(ingredient);
                        i++;
                    }
                    for (; i < size; i++) {
                        int id = idList.get(i).getValue0();
                        Optional<Ingredient> optionalIngredient = ingredients.stream().filter(a -> a.getIdIngredient() == id).findFirst();
                        Ingredient ingredient = (Ingredient) optionalIngredient.get().clone();
                        ingredient.setGrams(idList.get(i).getValue1());
                        ingredients.add(ingredient);
                    }
                }
                return ingredients;
            } catch (SQLException | DaoException | CloneNotSupportedException e) {
                logger.log(Level.ERROR, e);
                throw new DaoException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Pair<Integer, Double>> findIngredientsIdByProductId(int idProduct) throws DaoException {
        return null;
    }

    @Override
    public boolean addIngredientsInProducts(List<Product> products) throws DaoException {
        if (products != null && products.size() > 0) {
            StringBuilder query = new StringBuilder(SELECT_INGREDIENTS_BY_PRODUCTS_ID);
            int size = products.size();
            query.append("?, ".repeat(Math.max(0, size - 1)));
            query.append("?)");
            List<Triplet<Integer, Double, Integer>> productsIngredients = new ArrayList<>();
            try (Connection connection = ConnectionPool.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query.toString())) {
                for (int i = 0; i < size; i++) {
                    statement.setInt(i + 1, products.get(i).getIdProduct());
                }
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        int idIngredient = resultSet.getInt(ID_INGREDIENT);
                        double grams = resultSet.getDouble(GRAMS);
                        int idProduct = resultSet.getInt(ID_PRODUCT);
                        productsIngredients.add(new Triplet<>(idIngredient, grams, idProduct));
                    }
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
                throw new DaoException(e);
            }
            List<Pair<Integer, Double>> pairList = new ArrayList<>();
            for (Triplet<Integer, Double, Integer> triplet : productsIngredients) {
                Pair<Integer, Double> pair = new Pair<>(triplet.getValue0(), triplet.getValue1());
                pairList.add(pair);
            }
            List<Ingredient> ingredients = findIngredientsByIdIngredients(pairList);
            for (Product product : products) {
                List<Pair<Integer, Double>> idIngredients = new ArrayList<>();
                for (Triplet<Integer, Double, Integer> triplet : productsIngredients) {
                    if (product.getIdProduct() == triplet.getValue2()) {
                        idIngredients.add(new Pair<>(triplet.getValue0(), triplet.getValue1()));
                    }
                }
                boolean isHave;
                List<Ingredient> productIngredients = new ArrayList<>();
                for (Ingredient ingredient : ingredients) {
                    isHave = false;
                    int id = ingredient.getIdIngredient();
                    double grams = ingredient.getGrams();
                    for (Pair<Integer, Double> idIngredient : idIngredients) {
                        if (id == idIngredient.getValue0() && grams == idIngredient.getValue1()) {
                            isHave = true;
                            break;
                        }
                    }
                    if (isHave) {
                        productIngredients.add(ingredient);
                    }
                }
                product.setIngredients(productIngredients);
            }
        }
        return true;
    }

    @Override
    public boolean addIngredientsInProduct(Product product) throws DaoException {
        List<Triplet<Integer, Double, Integer>> productsIngredients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_INGREDIENTS_BY_PRODUCT_ID)) {
            statement.setInt(1, product.getIdProduct());
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    int idIngredient = resultSet.getInt(ID_INGREDIENT);
                    double grams = resultSet.getDouble(GRAMS);
                    int idProduct = resultSet.getInt(ID_PRODUCT);
                    productsIngredients.add(new Triplet<>(idIngredient, grams, idProduct));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        List<Pair<Integer, Double>> pairList = new ArrayList<>();
        for (Triplet<Integer, Double, Integer> triplet : productsIngredients) {
            Pair<Integer, Double> pair = new Pair<>(triplet.getValue0(), triplet.getValue1());
            pairList.add(pair);
        }
        List<Ingredient> ingredients = findIngredientsByIdIngredients(pairList);
        List<Pair<Integer, Double>> idIngredients = new ArrayList<>();
        for (Triplet<Integer, Double, Integer> triplet : productsIngredients) {
            if (product.getIdProduct() == triplet.getValue2()) {
                idIngredients.add(new Pair<>(triplet.getValue0(), triplet.getValue1()));
            }
        }
        boolean isHave;
        List<Ingredient> productIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            isHave = false;
            int id = ingredient.getIdIngredient();
            double grams = ingredient.getGrams();
            for (Pair<Integer, Double> idIngredient : idIngredients) {
                if (id == idIngredient.getValue0() && grams == idIngredient.getValue1()) {
                    isHave = true;
                    break;
                }
            }
            if (isHave) {
                productIngredients.add(ingredient);
            }
        }
        product.setIngredients(productIngredients);
        return true;
    }

    @Override
    public List<Ingredient> findIngredientsByPageNumber(int currentPage) throws DaoException {
        int offSet = (currentPage - 1) * 10;
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_INGREDIENTS_BY_PAGE)) {
            statement.setInt(1, offSet);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Ingredient ingredient = new Ingredient();
                    IngredientMapper.getInstance().rowMap(ingredient, resultSet);
                    if (!ingredient.isBlock()) {
                        ingredients.add(ingredient);
                    }
                }
                return ingredients;
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }

    @Override
    public int countProducts() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_COUNT_INGREDIENTS);
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
    public Optional<Ingredient> findIngredientByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_INGREDIENT_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Ingredient ingredient = new Ingredient();
                    IngredientMapper.getInstance().rowMap(ingredient, resultSet);
                    return Optional.of(ingredient);
                }
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> findIdIngredientByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_INGREDIENT_BY_NAME)) {
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
    public Optional<Ingredient> findIngredientById(Integer idIngredient) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_INGREDIENT_BY_ID)) {
            statement.setInt(1, idIngredient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Ingredient ingredient = new Ingredient();
                    IngredientMapper.getInstance().rowMap(ingredient, resultSet);
                    return Optional.of(ingredient);
                }
            }
        } catch (SQLException | DaoException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> findIdIngredientByNameAndNotId(String name, Integer idIngredient) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_ID_INGREDIENT_WHERE_NAME_AND_NOT_ID_PRODUCT)) {
            statement.setString(1, name);
            statement.setInt(2, idIngredient);
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
    public double findGrams(int idIngredient, int idProduct) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.SELECT_GRAMS_FROM_PRODUCTS_INGREDIENTS)) {
            statement.setInt(1, idProduct);
            statement.setInt(2, idIngredient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }
}
