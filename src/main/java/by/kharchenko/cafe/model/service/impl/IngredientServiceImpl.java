package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.IngredientDaoImpl;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.IngredientService;
import by.kharchenko.cafe.validator.IngredientValidator;
import by.kharchenko.cafe.validator.impl.IngredientValidatorImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestParameter.*;

public class IngredientServiceImpl implements BaseService<Ingredient>, IngredientService {
    private static final IngredientServiceImpl instance = new IngredientServiceImpl();
    private final IngredientValidator validator = IngredientValidatorImpl.getInstance();

    private IngredientServiceImpl() {
    }

    public static IngredientServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Ingredient ingredient) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return IngredientDaoImpl.getInstance().delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> ingredientData) throws ServiceException {
        boolean isCorrectData = validator.isCorrectCreateData(ingredientData);
        boolean isNameExists;
        IngredientDaoImpl ingredientDao = IngredientDaoImpl.getInstance();
        try {
            if (isCorrectData) {
                isNameExists = ingredientDao.findIdIngredientByName(ingredientData.get(NAME)).isPresent();
                if (!isNameExists) {
                    DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientData.get(NAME));
                    ingredient.setShelfLife(LocalDate.parse(ingredientData.get(SHELF_LIFE), parser));
                    return ingredientDao.add(ingredient);
                } else {
                    ingredientData.put(NAME, NAME_EXISTS);
                    return false;
                }
            } else {
                if (!Objects.equals(ingredientData.get(NAME), "")) {
                    isNameExists = ingredientDao.findIdIngredientByName(ingredientData.get(NAME)).isPresent();
                    if (isNameExists) {
                        ingredientData.put(NAME, NAME_EXISTS);
                    }
                    return false;
                }
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Ingredient> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> data) throws ServiceException {
        try {
            boolean isCorrectData;
            isCorrectData = validator.isCorrectCreateData(data);
            Integer idIngredient = Integer.parseInt(data.get(ID_INGREDIENT));
            String name = data.get(NAME);
            Optional<Integer> idAnotherProduct;
            if (!data.get(NAME).equals("")) {
                idAnotherProduct = IngredientDaoImpl.getInstance().findIdIngredientByNameAndNotId(name, idIngredient);
                if (idAnotherProduct.isPresent()) {
                    data.put(NAME, NAME_EXISTS);
                    return false;
                }
            }
            if (isCorrectData) {
                DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Ingredient ingredient = new Ingredient();
                ingredient.setName(data.get(NAME));
                ingredient.setShelfLife(LocalDate.parse(data.get(SHELF_LIFE), parser));
                ingredient.setIdIngredient(Integer.parseInt(data.get(ID_INGREDIENT)));
                return IngredientDaoImpl.getInstance().update(ingredient);
            } else {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Ingredient> findIngredientsByIdProduct(int idProduct) throws ServiceException {
        return null;
    }

    @Override
    public List<Ingredient> findIngredientsByPageNumber(int currentPage) throws ServiceException {
        try {
            return IngredientDaoImpl.getInstance().findIngredientsByPageNumber(currentPage);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countProducts() throws ServiceException {
        try {
            return IngredientDaoImpl.getInstance().countProducts();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Ingredient> findIngredientByName(String name) throws ServiceException {
        try {
            return IngredientDaoImpl.getInstance().findIngredientByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Ingredient> findIngredientById(Integer idIngredient) throws ServiceException {
        try {
            return IngredientDaoImpl.getInstance().findIngredientById(idIngredient);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Ingredient> findIngredientWithGrams(int idIngredient, int idProduct) throws ServiceException {
        try {
            Optional<Ingredient> ingredient = IngredientDaoImpl.getInstance().findIngredientById(idIngredient);
            if (ingredient.isPresent()) {
                double grams = IngredientDaoImpl.getInstance().findGrams(idIngredient, idProduct);
                ingredient.get().setGrams(grams);
            }
            return ingredient;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
