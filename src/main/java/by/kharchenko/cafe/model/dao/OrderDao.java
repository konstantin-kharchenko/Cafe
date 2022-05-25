package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findOrdersByIdClient(int idClient) throws DaoException;
    List<Integer> findMenuIdByOrderId(int idOrder) throws DaoException;
}
