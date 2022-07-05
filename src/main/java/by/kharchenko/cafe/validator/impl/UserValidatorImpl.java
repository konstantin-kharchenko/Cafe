package by.kharchenko.cafe.validator.impl;

import by.kharchenko.cafe.validator.DataValidator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestParameter.*;

public class UserValidatorImpl implements DataValidator {

    private static final UserValidatorImpl instance = new UserValidatorImpl();

    private UserValidatorImpl() {
    }

    public static UserValidatorImpl getInstance() {
        return instance;
    }

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";
    private static final String LOGIN_REGEX = "^[A-Za-z0-9-_]{3,}$";
    private static final String UPPER_PASSWORD_REGEX = ".*[A-Z].*";
    private static final String AGE_REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
    private static final String LOWER_PASSWORD_REGEX = ".*[a-z].*";
    private static final String NAME_REGEX = "^[A-Z][a-z]+";
    private static final String SURNAME_REGEX = "^[A-Z](([a-z]{1,}(['-]*)[a-z]*)|(['-][a-z]+))";
    private static final String NUMBER_PASSWORD_REGEX = ".*[0-9].*";
    private static final String PHONE_REGEX = "\\+375(33|29|25|44)\\d{7}";
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String ROLE_REGEX = "(client)|(administrator)";
    private static final String STATUS_REGEX = "(accepted)|(waiting)|(declined)";
    private static final String LOYALTY_POINTS_REGEX = "[0-9]{1,2}";
    private static final String EXPERIENCE_REGEX = "[0-9]{1,2}.[0-9]{1,2}";
    private static final String IS_BLOCK_REGEX = "(true)|(false)";
    private static final String PHOTO_REGEX = "^.+\\.(jpg|jpeg|raw|png)$";


    @Override
    public boolean isCorrectLogin(String login) {
        if (login == null) {
            return false;
        }
        return login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        if (password == null) {
            return false;
        }
        boolean a = password.matches(UPPER_PASSWORD_REGEX);
        boolean b = password.matches(LOWER_PASSWORD_REGEX);
        return a && b && password.matches(NUMBER_PASSWORD_REGEX);
    }

    @Override
    public boolean isCorrectName(String name) {
        if (name == null) {
            return false;
        }
        return name.matches(NAME_REGEX);
    }

    @Override
    public boolean isCorrectAge(String age) {
        if (age == null) {
            return false;
        }
        if (age.matches(AGE_REGEX)) {
            LocalDate localDate = LocalDate.parse(age);
            LocalDate end = LocalDate.now();
            long years = ChronoUnit.YEARS.between(localDate, end);
            return years > 11;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isCorrectSurname(String surname) {
        if (surname == null) {
            return false;
        }
        return surname.matches(SURNAME_REGEX);
    }

    @Override
    public boolean isCorrectDate(String date) {
        if (date == null) {
            return false;
        }
        return date.matches(DATE_REGEX);
    }

    @Override
    public boolean isCorrectEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean isCorrectIsBlock(String isBlock) {
        if (isBlock == null) {
            return false;
        }
        return isBlock.matches(IS_BLOCK_REGEX);
    }

    @Override
    public boolean isCorrectLoyaltyPoints(String loyaltyPoints) {
        if (loyaltyPoints == null) {
            return false;
        }
        return loyaltyPoints.matches(LOYALTY_POINTS_REGEX);
    }

    @Override
    public boolean isCorrectExperience(String experience) {
        if (experience == null) {
            return false;
        }
        return experience.matches(EXPERIENCE_REGEX);
    }

    @Override
    public boolean isCorrectStatus(String status) {
        if (status == null) {
            return false;
        }
        return status.matches(STATUS_REGEX);
    }

    @Override
    public boolean isCorrectPhone(String phone) {
        if (phone == null) {
            return false;
        }
        return phone.matches(PHONE_REGEX);
    }

    @Override
    public boolean isCorrectUserRole(String userRole) {
        if (userRole == null) {
            return false;
        }
        return userRole.toLowerCase().matches(ROLE_REGEX);
    }

    @Override
    public boolean isCorrectPhoto(String photo) {
        if (photo == null) {
            return false;
        }
        return photo.matches(PHOTO_REGEX);
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
        if (!isCorrectAge(userData.get(BIRTHDAY))) {
            isCorrectData = false;
            userData.put(BIRTHDAY, "");
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
        return isCorrectData;
    }

    @Override
    public boolean isCorrectUpdateClientData(Map<String, String> userData) {
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
        if (!isCorrectPhone(userData.get(PHONE_NUMBER))) {
            isCorrectData = false;
            userData.put(PHONE_NUMBER, "");
        }
        return isCorrectData;
    }

    @Override
    public boolean isCorrectUpdateAdministratorData(Map<String, String> userData) {
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
        if (!isCorrectPhone(userData.get(PHONE_NUMBER))) {
            isCorrectData = false;
            userData.put(PHONE_NUMBER, "");
        }
        if (!isCorrectExperience(userData.get(EXPERIENCE))) {
            isCorrectData = false;
            userData.put(EXPERIENCE, "");
        }
        return isCorrectData;
    }


}
