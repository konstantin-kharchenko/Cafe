package by.kharchenko.cafe.validator.impl;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class IngredientValidatorImplTest {
    private final IngredientValidatorImpl validator = IngredientValidatorImpl.getInstance();

    @Test
    public void testValidateCorrectName() {
        String correctName = "Хлеб";
        assertTrue(validator.isCorrectIngredientName(correctName));
    }

    @Test
    public void testValidateInCorrectName() {
        String inCorrectName = "*Хлеб";
        assertFalse(validator.isCorrectIngredientName(inCorrectName));
    }

    @Test
    public void testValidateCorrectDate() {
        String correctDate = "2023-12-12";
        assertTrue(validator.isCorrectDate(correctDate));
    }

    @Test
    public void testValidateInCorrectDate() {
        String inCorrectDate = "2000-12-12*";
        assertFalse(validator.isCorrectDate(inCorrectDate));
    }

    @Test
    public void testValidateCorrectData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Хлеб");
        data.put(SHELF_LIFE, "2023-12-12");
        assertTrue(validator.isCorrectCreateData(data));
    }

    @Test
    public void testValidateInCorrectData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "*Хлеб");
        data.put(SHELF_LIFE, "2000-12-12*");
        assertFalse(validator.isCorrectCreateData(data));
    }

}
