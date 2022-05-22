package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.ClientDaoImpl;
import by.kharchenko.cafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.ClientService;
import by.kharchenko.cafe.validator.DataValidator;
import by.kharchenko.cafe.validator.impl.DataValidatorImpl;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClientServiceImpl implements ClientService, BaseService<Client> {

    private static final ClientServiceImpl instance = new ClientServiceImpl();
    private final DataValidator validator = DataValidatorImpl.getInstance();

    private ClientServiceImpl() {
    }

    public static ClientServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Client> findClientByLogin(String login) throws ServiceException {
        try {
            Optional<Integer> id = UserDaoImpl.getInstance().findIdUserByLogin(login);
            if (id.isPresent()) {
                return ClientDaoImpl.getInstance().findClientByUserId(id.get());
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean insert(Client client) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(Client client) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    @Override
    public Triplet<Boolean, Boolean, Boolean> add(Map<String, String> userData) throws ServiceException {
        return new Triplet<>(false, false, false);
    }

    @Override
    public List<Client> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Client update(Client client) throws ServiceException {
        return null;
    }
}
