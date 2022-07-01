package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findNewOrdersByIdClient(int idClient) throws ServiceException;

    List<Order> findOrdersByIdClient(int idClient) throws ServiceException;

    Optional<Order> findOrderByOrderId(int parseInt) throws ServiceException;

    boolean deleteProductFromOrder(int idProduct, int idOrder) throws ServiceException;

    boolean addProductsIdInOrdersProductsTableByIdOrder(Integer idOrder, List<Product> toList) throws ServiceException;

    boolean confirmReceipt(int idOrder, int idClient) throws ServiceException;

    boolean reject(int idOrder, int idClient) throws ServiceException;
}
