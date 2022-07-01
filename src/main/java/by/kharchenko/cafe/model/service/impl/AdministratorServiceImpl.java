package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.AdministratorDaoImpl;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.service.AdministratorService;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.util.filereadwrite.FileReaderWriter;

import java.util.List;
import java.util.Map;

public class AdministratorServiceImpl implements BaseService<Administrator>, AdministratorService {
    private static final AdministratorServiceImpl instance = new AdministratorServiceImpl();

    private AdministratorServiceImpl() {
    }

    public static AdministratorServiceImpl getInstance() {
        return instance;
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
    public boolean add(Map<String, String> userData) throws ServiceException {
        return false;
    }

    @Override
    public List<Administrator> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> t) throws ServiceException {
        return false;
    }

    @Override
    public List<Administrator> findApplications() throws ServiceException {
        try {
            List<Administrator> administrators = AdministratorDaoImpl.getInstance().findApplications();
            for (Administrator administrator : administrators) {
                String stringPhoto = FileReaderWriter.getInstance().readPhoto(administrator.getPhotoPath());
                administrator.setStringPhoto(stringPhoto);
            }
            return administrators;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void approval(int parseInt) throws ServiceException {
        try {
            AdministratorDaoImpl.getInstance().approval(parseInt);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
