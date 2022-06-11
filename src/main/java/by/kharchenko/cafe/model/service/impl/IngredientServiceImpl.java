package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.IngredientService;

import java.util.List;
import java.util.Map;

public class IngredientServiceImpl implements BaseService<Ingredient>, IngredientService {
    private static final IngredientServiceImpl instance = new IngredientServiceImpl();

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
        return false;
    }

    @Override
    public boolean add(Map<String, String> userData) throws ServiceException {
        return false;
    }

    @Override
    public List<Ingredient> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> t) throws ServiceException {
        return false;
    }

    @Override
    public List<Ingredient> findIngredientsByIdProduct(int idProduct) throws ServiceException {
        return null;
    }
}
