package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.controller.RequestParameter;
import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class IngredientMapper implements CustomRowMapper<Ingredient> {
    private static final IngredientMapper instance = new IngredientMapper();

    public static IngredientMapper getInstance() {
        return instance;
    }

    private IngredientMapper() {

    }

    @Override
    public void rowMap(Ingredient ingredient, ResultSet resultSet) throws DaoException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try {
            ingredient.setIdIngredient(resultSet.getInt(RequestParameter.ID_INGREDIENT));
            ingredient.setName(resultSet.getString(RequestParameter.NAME));
            ingredient.setShelfLife(resultSet.getDate(RequestParameter.SHELF_LIFE).toLocalDate());
            ingredient.setBlock(resultSet.getBoolean(RequestParameter.BLOCK));
            logger.log(Level.INFO, ingredient.getName());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
