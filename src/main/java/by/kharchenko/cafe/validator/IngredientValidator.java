package by.kharchenko.cafe.validator;

import java.util.Map;

public interface IngredientValidator {

    boolean isCorrectIngredientName(String orderName);

    boolean isCorrectDate(String date);

    boolean isCorrectCreateData(Map<String, String> orderData);
}
