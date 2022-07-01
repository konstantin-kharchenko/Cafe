package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findNewOrdersByIdClient(int idClient) throws DaoException;

    List<Order> findOrdersByIdClient(int idClient) throws DaoException;

    Optional<Integer> findIdOrderByIdAndName(String name, int id) throws DaoException;

    Optional<Order> findOrderByOrderId(int parseInt) throws DaoException;

    boolean deleteProductFromOrder(int idProduct, int idOrder) throws DaoException;

    boolean addProductsIdInOrdersProductsTableByIdOrder(Integer idOrder, List<Integer> idList) throws DaoException;

    Optional<Integer> findIdOrderByIdAndNameAndNoIdOrder(String s, Integer idClient, Integer idOrder) throws DaoException;

    List<Order> findTodayOrders() throws DaoException;

    BigDecimal priceByOrderId(int idOrder) throws DaoException;

    Order.PaymentType paymentTypeByOrderId(int idOrder) throws DaoException;
}
