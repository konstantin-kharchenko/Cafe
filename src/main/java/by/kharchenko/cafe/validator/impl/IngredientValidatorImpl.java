package by.kharchenko.cafe.validator.impl;

import by.kharchenko.cafe.validator.IngredientValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestParameter.*;

public class IngredientValidatorImpl implements IngredientValidator {
    private static final IngredientValidatorImpl instance = new IngredientValidatorImpl();
    private static final String NAME_REGEX = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$";

    private IngredientValidatorImpl() {
    }

    public static IngredientValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isCorrectIngredientName(String ingredientName) {
        return ingredientName.matches(NAME_REGEX);
    }

    @Override
    public boolean isCorrectDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(date);
            Date date2 = new Date();
            String stringDate = format.format(date2);
            if (stringDate.equals(date)){
                return true;
            }
            int compare = date2.compareTo(date1);
            return compare <= 0;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean isCorrectCreateData(Map<String, String> ingredientData) {
        boolean isCorrectData = true;
        if (!isCorrectIngredientName(ingredientData.get(NAME))) {
            isCorrectData = false;
            ingredientData.put(NAME, "");
        }
        if (!isCorrectDate(ingredientData.get(SHELF_LIFE))) {
            isCorrectData = false;
            ingredientData.put(SHELF_LIFE, "");
        }
        return isCorrectData;
    }
}
