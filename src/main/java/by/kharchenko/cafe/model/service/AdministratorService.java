package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Administrator;

import java.util.List;

public interface AdministratorService {
    List<Administrator> findApplications() throws ServiceException;

    void approval(int parseInt) throws ServiceException;
}
