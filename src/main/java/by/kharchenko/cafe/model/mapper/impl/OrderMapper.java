package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.controller.RequestParameter;
import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
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
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try {
            order.setIdOrder(resultSet.getInt(RequestParameter.ID_ORDER));
            order.setName(resultSet.getString(RequestParameter.NAME));
            order.setDate(format.parse(resultSet.getString(RequestParameter.DATE)));
            order.setSum(resultSet.getDouble(RequestParameter.SUM));
            //photo todo
            logger.log(Level.INFO, "menu" + order.getName() + " added");
        } catch (SQLException | ParseException e) {
            throw new DaoException(e);
        }
    }
}
