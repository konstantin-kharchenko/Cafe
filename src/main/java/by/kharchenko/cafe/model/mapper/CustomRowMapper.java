package by.kharchenko.cafe.model.mapper;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

public interface CustomRowMapper<T extends AbstractEntity> {
    Logger logger = LogManager.getLogger();

    void rowMap(T t, ResultSet resultSet) throws DaoException;
}
