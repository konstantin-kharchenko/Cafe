package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> findIngredientsByIdProduct(int idProduct) throws ServiceException;

    List<Ingredient> findIngredientsByPageNumber(int currentPage) throws ServiceException;

    int countProducts() throws ServiceException;

    Optional<Ingredient> findIngredientByName(String name) throws ServiceException;

    Optional<Ingredient> findIngredientById(Integer idIngredient) throws ServiceException;

    Optional<Ingredient> findIngredientWithGrams(int idIngredient, int idProduct) throws ServiceException;

}
