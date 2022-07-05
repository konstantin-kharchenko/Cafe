package by.kharchenko.cafe.validator.impl;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ProductValidatorImplTest {
    private final ProductValidatorImpl validator = ProductValidatorImpl.getInstance();

    @Test
    public void testValidateCorrectName() {
        String correctName = "Салат оливье";
        assertTrue(validator.isCorrectProductName(correctName));
    }

    @Test
    public void testValidateInCorrectName() {
        String inCorrectName = "*Салат оливье";
        assertFalse(validator.isCorrectProductName(inCorrectName));
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
    public void testValidateCorrectPrice() {
        String correctPrice = "1.4";
        assertTrue(validator.isCorrectPrice(correctPrice));
    }

    @Test
    public void testValidateInCorrectPrice() {
        String inCorrectPrice = "18*";
        assertFalse(validator.isCorrectPrice(inCorrectPrice));
    }

    @Test
    public void testValidateCorrectGrams() {
        String correctGrams = "239";
        assertTrue(validator.isCorrectGrams(correctGrams));
    }

    @Test
    public void testValidateInCorrectGrams() {
        String inCorrectGrams = "18*";
        assertFalse(validator.isCorrectGrams(inCorrectGrams));
    }

    @Test
    public void testValidateCorrectPhoto() {
        String correctPhoto = "a.png";
        assertTrue(validator.isCorrectPhoto(correctPhoto));
    }

    @Test
    public void testValidateInCorrectPhoto() {
        String inCorrectPhoto = "a.exe";
        assertFalse(validator.isCorrectPhoto(inCorrectPhoto));
    }

    @Test
    public void testValidateCorrectData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Салат оливье");
        data.put(DATE, "2023-12-12");
        data.put(PRICE, "202");
        assertTrue(validator.isCorrectCreateData(data));
    }

    @Test
    public void testValidateInCorrectData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "*Салат оливье");
        data.put(DATE, "2000-12-12*");
        data.put(PRICE, "202*");
        assertFalse(validator.isCorrectCreateData(data));
    }
}
