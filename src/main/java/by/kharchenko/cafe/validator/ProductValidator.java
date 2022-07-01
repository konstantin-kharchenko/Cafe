package by.kharchenko.cafe.validator;

import java.util.Map;

public interface ProductValidator {
    boolean isCorrectPrice(String price);

    boolean isCorrectProductName(String orderName);

    boolean isCorrectDate(String date);

    boolean isCorrectPhoto(String photo);

    boolean isCorrectGrams(String grams);

    boolean isCorrectCreateData(Map<String, String> orderData);
}
