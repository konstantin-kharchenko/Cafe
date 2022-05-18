package by.kharchenko.cafe.validator.impl;

import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.validator.DataValidator;

import java.util.Map;
import java.util.Objects;

import static by.kharchenko.cafe.controller.RequestParameter.*;

public class DataValidatorImpl implements DataValidator {

    private static final DataValidatorImpl instance = new DataValidatorImpl();

    private DataValidatorImpl() {
    }

    public static DataValidatorImpl getInstance() {
        return instance;
    }

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";
    private static final String LOGIN_REGEX = "^[A-Za-z0-9-_]{3,}$";
    private static final String UPPER_PASSWORD_REGEX = ".*[A-Z].*";
    private static final String LOWER_PASSWORD_REGEX = ".*[a-z].*";
    private static final String NAME_REGEX = "^[A-Z][a-z]+";
    private static final String SURNAME_REGEX = "^[A-Z](([a-z]{1,}(['-]*)[a-z]*)|(['-][a-z]+))";
    private static final String NUMBER_PASSWORD_REGEX = ".*[0-9].*";
    private static final String AGE_REGEX = "[1-9]([0-9]{1,2})";
    private static final String PHONE_REGEX = "\\+375(33|29|25|44)\\d{7}";
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String ROLE_REGEX = "(client)|(administrator)";
    private static final String PAYMENT_TYPE_REGEX = "(cash)|(client account)";
    private static final String STATUS_REGEX = "(accepted)|(waiting)|(declined)";
    private static final String LOYALTY_POINTS_REGEX = "[0-9]{1,2}";
    private static final String EXPERIENCE_REGEX = "[0-9]{1,2}";
    private static final String IS_BLOCK_REGEX = "(true)|(false)";


    @Override
    public boolean isCorrectLogin(String login) {
        return login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        boolean a = password.matches(UPPER_PASSWORD_REGEX);
        boolean b = password.matches(LOWER_PASSWORD_REGEX);
        return a && b && password.matches(NUMBER_PASSWORD_REGEX);
    }

    @Override
    public boolean isCorrectName(String name) {
        return name.matches(NAME_REGEX);
    }

    @Override
    public boolean isCorrectAge(String age) {
        return age.matches(AGE_REGEX);
    }

    @Override
    public boolean isCorrectSurname(String surname) {
        return surname.matches(SURNAME_REGEX);
    }

    @Override
    public boolean isCorrectDate(String Date) {
        return Date.matches(DATE_REGEX);
    }

    @Override
    public boolean isCorrectEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean isCorrectPaymentType(String paymentType) {
        return paymentType.matches(PAYMENT_TYPE_REGEX);
    }

    @Override
    public boolean isCorrectIsBlock(String isBlock) {
        return isBlock.matches(IS_BLOCK_REGEX);
    }

    @Override
    public boolean isCorrectLoyaltyPoints(String loyaltyPoints) {
        return loyaltyPoints.matches(LOYALTY_POINTS_REGEX);
    }

    @Override
    public boolean isCorrectExperience(String experience) {
        return experience.matches(EXPERIENCE_REGEX);
    }

    @Override
    public boolean isCorrectStatus(String status) {
        return status.matches(STATUS_REGEX);
    }

    @Override
    public boolean isCorrectPhone(String phone) {
        return phone.matches(PHONE_REGEX);
    }

    @Override
    public boolean isCorrectUserRole(String user_role) {
        return user_role.toLowerCase().matches(ROLE_REGEX);
    }

    @Override
    public boolean isCorrectRegisterData(Map<String, String> userData) {
        boolean isCorrectData = true;
        if (!isCorrectName(userData.get(NAME))) {
            isCorrectData = false;
            userData.put(NAME, "");
        }
        if (!isCorrectSurname(userData.get(SURNAME))) {
            isCorrectData = false;
            userData.put(SURNAME, "");
        }
        if (!isCorrectLogin(userData.get(LOGIN))) {
            isCorrectData = false;
            userData.put(LOGIN, "");
        }
        if (!isCorrectPassword(userData.get(PASSWORD))) {
            isCorrectData = false;
            userData.put(PASSWORD, "");
        }
        if (!isCorrectAge(userData.get(AGE))) {
            isCorrectData = false;
            userData.put(AGE, "");
        }
        if (!isCorrectUserRole(userData.get(ROLE))) {
            isCorrectData = false;
            userData.put(ROLE, "");
        }
        if (!isCorrectEmail(userData.get(EMAIL))) {
            isCorrectData = false;
            userData.put(EMAIL, "");
        }
        if (!isCorrectPhone(userData.get(PHONE_NUMBER))) {
            isCorrectData = false;
            userData.put(PHONE_NUMBER, "");
        }
        if (Objects.equals(userData.get(ROLE), User.Role.ADMINISTRATOR.toString().toLowerCase())) {
            if (!isCorrectExperience(userData.get(EXPERIENCE))) {
                isCorrectData = false;
                userData.put(EXPERIENCE, "");
            }
            if (!isCorrectStatus(userData.get(STATUS))) {
                isCorrectData = false;
                userData.put(EXPERIENCE, "");
            }
        } else if (Objects.equals(userData.get(ROLE), User.Role.CLIENT.toString().toLowerCase())) {
            if (!isCorrectLoyaltyPoints(userData.get(LOYALTY_POINTS))) {
                isCorrectData = false;
                userData.put(EXPERIENCE, "");
            }
            if (!isCorrectPaymentType(userData.get(PAYMENT_TYPE))) {
                isCorrectData = false;
                userData.put(EXPERIENCE, "");
            }
            if (!isCorrectIsBlock(userData.get(IS_BLOCK))) {
                isCorrectData = false;
                userData.put(IS_BLOCK, "");
            }
        }
        return isCorrectData;
    }
}
