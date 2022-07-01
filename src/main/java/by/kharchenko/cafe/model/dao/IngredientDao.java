package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Product;
import org.javatuples.Pair;

import java.util.List;
import java.util.Optional;

public interface IngredientDao {
    List<Ingredient> findIngredientsByIdIngredients(List<Pair<Integer, Double>> idList) throws DaoException;

    List<Pair<Integer, Double>> findIngredientsIdByProductId(int idMenu) throws DaoException;

    boolean addIngredientsInProducts(List<Product> products) throws DaoException;

    boolean addIngredientsInProduct(Product product) throws DaoException;

    List<Ingredient> findIngredientsByPageNumber(int currentPage) throws DaoException;

    int countProducts() throws DaoException;

    Optional<Ingredient> findIngredientByName(String name) throws DaoException;

    Optional<Integer> findIdIngredientByName(String name) throws DaoException;

    Optional<Ingredient> findIngredientById(Integer idIngredient) throws DaoException;

    Optional<Integer> findIdIngredientByNameAndNotId(String name, Integer idIngredient) throws DaoException;

    double findGrams(int idIngredient, int idProduct) throws DaoException;
}
