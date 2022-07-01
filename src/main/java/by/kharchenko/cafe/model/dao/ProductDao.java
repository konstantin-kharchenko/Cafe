package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductDao {
    List<Product> findProductsByIdProducts(List<Integer> idList) throws DaoException;

    boolean addProductsInOrders(List<Order> orders) throws DaoException;

    List<Product> findNewProducts() throws DaoException;

    int countProducts() throws DaoException;

    List<Product> findProductsByPageNumber(int currentPage) throws DaoException;

    List<Integer> findProductsIdByOrderId(int idOrder) throws DaoException;

    Optional<Product> findProductByProductId(int parseInt) throws DaoException;

    Optional<Product> findProductByName(String name) throws DaoException;

    Optional<Integer> findIdProductByName(String name) throws DaoException;

    Optional<Integer> findIdProductByNameAndNotId(String name, Integer idProduct) throws DaoException;

    void deleteIngredientFromProduct(int idIngredient, int idProduct) throws DaoException;

    void changeGrams(Map<String, String> data) throws DaoException;

    boolean addIngredientsIdInProductsIngredientsTableByIdProduct(int idProduct, List<Integer> idList) throws DaoException;
}
