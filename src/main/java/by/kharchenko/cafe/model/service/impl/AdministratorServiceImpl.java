package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.AdministratorDaoImpl;
import by.kharchenko.cafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.service.AdministratorService;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.validator.DataValidator;
import by.kharchenko.cafe.validator.impl.DataValidatorImpl;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdministratorServiceImpl implements AdministratorService, BaseService<Administrator> {

    private static final AdministratorServiceImpl instance = new AdministratorServiceImpl();
    private final DataValidator validator = DataValidatorImpl.getInstance();

    private AdministratorServiceImpl() {
    }

    public static AdministratorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Administrator> findAdministratorByLogin(String login) throws ServiceException {
        try {
            Optional<Integer> id = UserDaoImpl.getInstance().findIdUserByLogin(login);
            if (id.isPresent()) {
                return AdministratorDaoImpl.getInstance().findAdministratorByUserId(id.get());
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean insert(Administrator administrator) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(Administrator administrator) throws ServiceException {
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
    public List<Administrator> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Administrator update(Administrator administrator) throws ServiceException {
        return null;
    }
}
