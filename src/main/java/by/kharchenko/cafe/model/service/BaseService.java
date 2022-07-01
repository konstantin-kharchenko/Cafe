package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.AbstractEntity;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Map;

public interface BaseService<T extends AbstractEntity> {

    boolean delete(T t) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    boolean add(Map<String, String> userData) throws ServiceException;

    List<T> findAll() throws ServiceException;

    boolean update(Map<String, String> t) throws ServiceException;


}
