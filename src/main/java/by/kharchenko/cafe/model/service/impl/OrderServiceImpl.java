package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.MenuDaoImpl;
import by.kharchenko.cafe.model.dao.impl.OrderDaoImpl;
import by.kharchenko.cafe.model.entity.Menu;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.OrderService;
import by.kharchenko.cafe.validator.DataValidator;
import by.kharchenko.cafe.validator.impl.DataValidatorImpl;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService, BaseService<Order> {
    private static final OrderServiceImpl instance = new OrderServiceImpl();
    private final DataValidator validator = DataValidatorImpl.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Order order) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(Order order) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    @Override
    public Triplet<Boolean, Boolean, Boolean> add(Map<String, String> userData) throws ServiceException {
        return null;
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Order update(Order order) throws ServiceException {
        return null;
    }

    @Override
    public List<Order> findOrdersByIdClient(int idClient) throws ServiceException {
        try {
            return OrderDaoImpl.getInstance().findOrdersByIdClient(idClient);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Menu> findMenuByIdOrder(int idOrder) throws ServiceException {
        try {
            List<Integer> menuListId = OrderDaoImpl.getInstance().findMenuIdByOrderId(idOrder);
            return MenuDaoImpl.getInstance().findMenuListByIdList(menuListId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
