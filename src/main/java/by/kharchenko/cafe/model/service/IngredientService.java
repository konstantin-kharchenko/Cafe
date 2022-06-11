package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findIngredientsByIdProduct(int idProduct) throws ServiceException;
}
