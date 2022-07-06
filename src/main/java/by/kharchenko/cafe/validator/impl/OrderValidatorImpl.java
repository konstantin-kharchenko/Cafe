package by.kharchenko.cafe.validator.impl;

import by.kharchenko.cafe.validator.OrderValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static by.kharchenko.cafe.controller.RequestParameter.DATE;

public class OrderValidatorImpl implements OrderValidator {
    private static final OrderValidatorImpl instance = new OrderValidatorImpl();
    private static final String PAYMENT_TYPE_REGEX = "(CASH)|(CLIENT_ACCOUNT)";
    private static final String NAME_REGEX = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$";

    private OrderValidatorImpl() {
    }

    public static OrderValidatorImpl getInstance() {
        return instance;
    }


    @Override
    public boolean isCorrectPaymentType(String paymentType) {
        if (paymentType == null) {
            return false;
        }
        return paymentType.matches(PAYMENT_TYPE_REGEX);
    }

    @Override
    public boolean isCorrectOrderName(String orderName) {
        if (orderName == null) {
            return false;
        }
        return orderName.matches(NAME_REGEX);
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
    public boolean isCorrectCreateData(Map<String, String> orderData) {
        boolean isCorrectData = true;
        if (!isCorrectOrderName(orderData.get(NAME))) {
            isCorrectData = false;
            orderData.put(NAME, "");
        }
        if (!isCorrectPaymentType(orderData.get(PAYMENT_TYPE))) {
            isCorrectData = false;
            orderData.put(PAYMENT_TYPE, "");
        }
        if (!isCorrectDate(orderData.get(DATE))) {
            isCorrectData = false;
            orderData.put(DATE, "");
        }
        return isCorrectData;
    }
}
