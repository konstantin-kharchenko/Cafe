package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.ClientDaoImpl;
import by.kharchenko.cafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.ClientService;
import by.kharchenko.cafe.util.encryption.EncryptionPassword;
import by.kharchenko.cafe.validator.DataValidator;
import by.kharchenko.cafe.validator.impl.DataValidatorImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestParameter.*;

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
    public boolean add(Map<String, String> userData) throws ServiceException {
        boolean isCorrectData = validator.isCorrectRegisterData(userData);
        if (isCorrectData) {
            boolean isUserAdd = false;
            try {
                String encryptionPassword = EncryptionPassword.encryption(userData.get(PASSWORD));
                userData.put(PASSWORD, encryptionPassword);
                UserDaoImpl userDao = UserDaoImpl.getInstance();
                isUserAdd = userDao.add(userData);
                if (isUserAdd) {
                    ClientDaoImpl administratorDao = ClientDaoImpl.getInstance();
                    Optional<Integer> userId = userDao.findIdUserByLogin(userData.get(LOGIN));
                    userId.ifPresent(integer -> userData.put(ID_USER, Integer.toString(integer)));
                    return  administratorDao.add(userData);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return false;
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
