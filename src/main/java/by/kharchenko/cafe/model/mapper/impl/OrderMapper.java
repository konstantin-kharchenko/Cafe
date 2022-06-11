package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.controller.RequestParameter;
import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderMapper implements CustomRowMapper<Order> {
    private static final OrderMapper instance = new OrderMapper();

    public static OrderMapper getInstance() {
        return instance;
    }

    private OrderMapper() {

    }

    @Override
    public void rowMap(Order order, ResultSet resultSet) throws DaoException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            order.setIdOrder(resultSet.getInt(RequestParameter.ID_ORDER));
            order.setName(resultSet.getString(RequestParameter.NAME));
            order.setDate(resultSet.getDate(RequestParameter.DATE).toLocalDate());
            order.setPrice(resultSet.getBigDecimal(RequestParameter.PRICE));
            order.setPaymentType(Order.PaymentType.valueOf(resultSet.getString(RequestParameter.PAYMENT_TYPE).toUpperCase()));
            logger.log(Level.INFO, "order " + order.getName() + " added");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
