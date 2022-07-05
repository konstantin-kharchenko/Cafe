package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.ClientDaoImpl;
import by.kharchenko.cafe.model.dao.impl.OrderDaoImpl;
import by.kharchenko.cafe.model.dao.impl.ProductDaoImpl;
import by.kharchenko.cafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.OrderService;
import by.kharchenko.cafe.validator.OrderValidator;
import by.kharchenko.cafe.validator.impl.OrderValidatorImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static by.kharchenko.cafe.controller.RequestParameter.*;

public class OrderServiceImpl implements BaseService<Order>, OrderService {
    private static final OrderServiceImpl instance = new OrderServiceImpl();
    private static final String MAIL_SUBJECT = "Create order";
    private static final String MAIL_TEXT = "You have successfully created an order: ";
    private final OrderValidator validator = OrderValidatorImpl.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Order order) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return OrderDaoImpl.getInstance().delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> orderData) throws ServiceException {
        boolean isCorrectData = validator.isCorrectCreateData(orderData);
        boolean isNameExists;
        Integer id = Integer.parseInt(orderData.get(ID_CLIENT));
        OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
        try {
            if (isCorrectData) {
                isNameExists = orderDao.findIdOrderByIdAndName(orderData.get(NAME), id).isPresent();
                if (!isNameExists) {
                    DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    Order.PaymentType paymentType = Order.PaymentType.valueOf(orderData.get(PAYMENT_TYPE).toUpperCase());
                    Order order = new Order();
                    order.setName(orderData.get(NAME));
                    order.setDate(LocalDate.parse(orderData.get(DATE), parser));
                    order.setIdClient(Integer.parseInt(orderData.get(ID_CLIENT)));
                    order.setPaymentType(paymentType);
                    boolean match = orderDao.add(order);
                    if (match) {
                        EmailServiceImpl.getInstance().sendMail(orderData.get(EMAIL), MAIL_SUBJECT, MAIL_TEXT + orderData.get(NAME));
                    }
                    return match;
                } else {
                    orderData.put(NAME, NAME_EXISTS);
                    return false;
                }
            } else {
                if (!Objects.equals(orderData.get(NAME), "")) {
                    isNameExists = UserDaoImpl.getInstance().findIdUserByLogin(orderData.get(NAME)).isPresent();
                    if (isNameExists) {
                        orderData.put(NAME, NAME_EXISTS);
                    }
                    return false;
                }
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> orderData) throws ServiceException {
        boolean isCorrectData = validator.isCorrectCreateData(orderData);
        boolean isNameExists;
        Integer idClient = Integer.parseInt(orderData.get(ID_CLIENT));
        Integer idOrder = Integer.parseInt(orderData.get(ID_ORDER));
        OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
        try {
            if (isCorrectData) {
                //todo name
                isNameExists = orderDao.findIdOrderByIdAndNameAndNoIdOrder(orderData.get(NAME), idClient, idOrder).isPresent();
                if (!isNameExists) {
                    DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    Order.PaymentType paymentType = Order.PaymentType.valueOf(orderData.get(PAYMENT_TYPE).toLowerCase());
                    Order order = new Order();
                    order.setName(orderData.get(NAME));
                    order.setDate(LocalDate.parse(orderData.get(DATE), parser));
                    order.setIdOrder(Integer.parseInt(orderData.get(ID_ORDER)));
                    order.setPaymentType(paymentType);
                    return orderDao.update(order);
                } else {
                    orderData.put(NAME, NAME_EXISTS);
                    return false;
                }
            } else {
                if (!Objects.equals(orderData.get(NAME), "")) {
                    isNameExists = UserDaoImpl.getInstance().findIdUserByLogin(orderData.get(NAME)).isPresent();
                    if (isNameExists) {
                        orderData.put(NAME, NAME_EXISTS);
                    }
                    return false;
                }
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findNewOrdersByIdClient(int idClient) throws ServiceException {
        try {
            List<Order> orders = OrderDaoImpl.getInstance().findNewOrdersByIdClient(idClient);
            ProductDaoImpl.getInstance().addProductsInOrders(orders);
            return orders;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findOrdersByIdClient(int idClient) throws ServiceException {
        try {
            List<Order> orders = OrderDaoImpl.getInstance().findOrdersByIdClient(idClient);
            ProductDaoImpl.getInstance().addProductsInOrders(orders);
            return orders;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Order> findOrderByOrderId(int parseInt) throws ServiceException {
        try {
            return OrderDaoImpl.getInstance().findOrderByOrderId(parseInt);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteProductFromOrder(int idProduct, int idOrder) throws ServiceException {
        try {
            return OrderDaoImpl.getInstance().deleteProductFromOrder(idProduct, idOrder);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addProductsIdInOrdersProductsTableByIdOrder(Integer idOrder, List<Product> products) throws ServiceException {
        List<Integer> idList = new ArrayList<>();
        for (Product product : products) {
            idList.add(product.getIdProduct());
        }
        try {
            return OrderDaoImpl.getInstance().addProductsIdInOrdersProductsTableByIdOrder(idOrder, idList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean confirmReceipt(int idOrder, int idClient) throws ServiceException {
        try {
            Order.PaymentType paymentType = OrderDaoImpl.getInstance().paymentTypeByOrderId(idOrder);
            if (paymentType == Order.PaymentType.CLIENT_ACCOUNT) {
                BigDecimal orderPrice = OrderDaoImpl.getInstance().priceByOrderId(idOrder);
                BigDecimal clientAccount = ClientDaoImpl.getInstance().clientAccountByIdClient(idClient);
                if (clientAccount.compareTo(orderPrice) < 0) {
                    return false;
                }
                ClientDaoImpl.getInstance().subPriceFromClientAccount(orderPrice, idClient);
            }
            OrderDaoImpl.getInstance().delete(idOrder);
            ClientDaoImpl.getInstance().addLoyaltyPoints(idClient);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean reject(int idOrder, int idClient) throws ServiceException {
        try {
            OrderDaoImpl.getInstance().delete(idOrder);
            ClientDaoImpl.getInstance().subLoyaltyPoints(idClient);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
