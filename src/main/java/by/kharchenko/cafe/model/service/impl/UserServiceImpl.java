package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.UserDao;
import by.kharchenko.cafe.model.dao.impl.AdministratorDaoImpl;
import by.kharchenko.cafe.model.dao.impl.ClientDaoImpl;
import by.kharchenko.cafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.UserService;
import by.kharchenko.cafe.util.encryption.EncryptionPassword;
import by.kharchenko.cafe.validator.impl.DataValidatorImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService, BaseService<User> {

    private static final UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(User user) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Map<String, String> userData) throws ServiceException {
        return false;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        return null;
    }

    @Override
    public User update(User user) throws ServiceException {
        return null;
    }

    @Override
    public Optional<? extends User> authenticate(String login, String password) throws ServiceException {
        DataValidatorImpl validator = DataValidatorImpl.getInstance();
        boolean isPassword = validator.isCorrectPassword(password);
        boolean isLogin = validator.isCorrectLogin(login);
        Optional<? extends User> userOptional = Optional.empty();
        if (isLogin && isPassword) {
            String encryptionPassword = EncryptionPassword.encryption(password);
            UserDao userDao = UserDaoImpl.getInstance();
            boolean match;
            try {
                match = userDao.authenticate(login, encryptionPassword);
                if (match) {
                    userOptional = findUserByLogin(login);
                    return userOptional;
                } else {
                    return userOptional;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            if (!isLogin){
                login = "";
            }
            if (!isPassword){
                password = "";
            }
            return userOptional;
        }
    }

    @Override
    public Optional<? extends User> findUserByLogin(String login) throws ServiceException {
        try {
            User.Role role = UserDaoImpl.getInstance().findUserRoleByLogin(login);
            Optional<Integer> userId = UserDaoImpl.getInstance().findIdUserByLogin(login);
            if (userId.isPresent()) {
                if (role == User.Role.CLIENT) {
                    return ClientDaoImpl.getInstance().findClientByUserId(userId.get());
                } else if (role == User.Role.ADMINISTRATOR) {
                    return AdministratorDaoImpl.getInstance().findAdministratorByUserId(userId.get());
                } else {
                    throw new ServiceException("no user");
                }
            } else {
                throw new ServiceException("no user id");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Integer> findIdUserByLogin(String login) throws ServiceException {
        try {
            return UserDaoImpl.getInstance().findIdUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> findLogins() throws ServiceException {
        try {
            return UserDaoImpl.getInstance().findLogins();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
