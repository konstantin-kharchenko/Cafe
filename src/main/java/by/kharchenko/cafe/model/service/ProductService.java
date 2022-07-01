package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    List<Product> findNewProducts() throws ServiceException;

    List<Product> findProductsByIdOrder(int idOrder) throws ServiceException;

    int countProducts() throws ServiceException;

    List<Product> findProductsByPageNumber(int currentPage) throws ServiceException;

    Optional<Product> findProductByProductId(int parseInt) throws ServiceException;

    Optional<Product> findProductByName(String name) throws ServiceException;

    void deleteIngredientFromProduct(int idIngredient, int idProduct) throws ServiceException;

    boolean changeGrams(Map<String, String> data) throws ServiceException;

    boolean addIngredientsIdInProductsIngredientsTableByIdProduct(int idProduct, List<Ingredient> toList) throws ServiceException;
}
