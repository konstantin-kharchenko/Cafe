package by.kharchenko.cafe.validator.impl;

import by.kharchenko.cafe.validator.ProductValidator;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static by.kharchenko.cafe.controller.RequestParameter.DATE;

public class ProductValidatorImpl implements ProductValidator {
    private static final ProductValidatorImpl instance = new ProductValidatorImpl();
    private static final String NAME_REGEX = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$";
    private static final String PHOTO_REGEX = "^.+\\.(jpg|jpeg|raw|png)$";

    private ProductValidatorImpl() {
    }

    public static ProductValidatorImpl getInstance() {
        return instance;
    }


    @Override
    public boolean isCorrectPrice(String price) {
        if (price == null) {
            return false;
        }
        try {
            BigDecimal bigDecimalPrice = new BigDecimal(price);
            if (bigDecimalPrice.compareTo(BigDecimal.ONE) < 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isCorrectProductName(String productName) {
        if (productName == null) {
            return false;
        }
        return productName.matches(NAME_REGEX);
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
    public boolean isCorrectPhoto(String photo) {
        if (photo == null) {
            return false;
        }
        return photo.matches(PHOTO_REGEX);
    }

    @Override
    public boolean isCorrectGrams(String grams) {
        if (grams == null) {
            return false;
        }
        try {
            double doubleGrams = Double.parseDouble(grams);
            if (doubleGrams <= 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isCorrectCreateData(Map<String, String> productData) {
        boolean isCorrectData = true;
        if (!isCorrectProductName(productData.get(NAME))) {
            isCorrectData = false;
            productData.put(NAME, "");
        }
        if (!isCorrectPrice(productData.get(PRICE))) {
            isCorrectData = false;
            productData.put(PRICE, "");
        }
        if (!isCorrectDate(productData.get(DATE))) {
            isCorrectData = false;
            productData.put(DATE, "");
        }
        return isCorrectData;
    }
}
