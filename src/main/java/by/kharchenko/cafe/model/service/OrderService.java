package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Menu;
import by.kharchenko.cafe.model.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findOrdersByIdClient(int idClient) throws ServiceException;
    List<Menu> findMenuByIdOrder(int idOrder) throws ServiceException;
}
