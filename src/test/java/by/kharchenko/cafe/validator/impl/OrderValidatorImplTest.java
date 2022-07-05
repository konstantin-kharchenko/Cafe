package by.kharchenko.cafe.validator.impl;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class OrderValidatorImplTest {
    private final OrderValidatorImpl validator = OrderValidatorImpl.getInstance();

    @Test
    public void testValidateCorrectName() {
        String correctName = "завтрак";
        assertTrue(validator.isCorrectOrderName(correctName));
    }

    @Test
    public void testValidateInCorrectName() {
        String inCorrectName = "*завтрак";
        assertFalse(validator.isCorrectOrderName(inCorrectName));
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
    public void testValidateCorrectPaymentType() {
        String correctDate = "CASH";
        assertTrue(validator.isCorrectPaymentType(correctDate));
    }

    @Test
    public void testValidateInCorrectPaymentType() {
        String inCorrectDate = "CASHJ";
        assertFalse(validator.isCorrectPaymentType(inCorrectDate));
    }

    @Test
    public void testValidateCorrectData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Хлеб");
        data.put(DATE, "2023-12-12");
        data.put(PAYMENT_TYPE, "CASH");
        assertTrue(validator.isCorrectCreateData(data));
    }

    @Test
    public void testValidateInCorrectData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "*Хлеб");
        data.put(DATE, "2000-12-12*");
        data.put(PAYMENT_TYPE, "CASH7");
        assertFalse(validator.isCorrectCreateData(data));
    }
}
