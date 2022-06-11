package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.IngredientDao;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.mapper.impl.IngredientMapper;
import by.kharchenko.cafe.model.pool.ConnectionPool;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static by.kharchenko.cafe.model.dao.SqlQuery.*;

public class IngredientDaoImpl implements BaseDao<Ingredient>, IngredientDao {
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
            StringBuilder query = new StringBuilder(SELECT_INGREDIENTS_ID_BY_PRODUCTS_ID);
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
             PreparedStatement statement = connection.prepareStatement(SELECT_INGREDIENTS_ID_BY_PRODUCT_ID)) {
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
}
