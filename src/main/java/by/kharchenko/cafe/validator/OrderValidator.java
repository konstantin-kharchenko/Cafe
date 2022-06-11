package by.kharchenko.cafe.validator;

import java.util.Map;

public interface OrderValidator {

    boolean isCorrectPaymentType(String paymentType);

    boolean isCorrectOrderName(String orderName);

    boolean isCorrectDate(String date);

    boolean isCorrectCreateData(Map<String, String> orderData);
}
