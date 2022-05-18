package by.kharchenko.cafe.validator;

import java.util.Map;

public interface DataValidator {
    boolean isCorrectLogin(String login);

    boolean isCorrectPassword(String password);

    boolean isCorrectName(String name);

    boolean isCorrectAge(String age);

    boolean isCorrectSurname(String surname);

    boolean isCorrectDate(String birthdayDate);

    boolean isCorrectEmail(String email);

    boolean isCorrectPaymentType(String paymentType);

    boolean isCorrectIsBlock(String isBlock);

    boolean isCorrectLoyaltyPoints(String loyaltyPoints);

    boolean isCorrectExperience(String experience);

    boolean isCorrectStatus(String status);

    boolean isCorrectPhone(String phone);

    boolean isCorrectUserRole(String user_role);

    boolean isCorrectRegisterData(Map<String, String> userData);
}
