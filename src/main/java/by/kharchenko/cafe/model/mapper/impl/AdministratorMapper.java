package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.controller.RequestParameter;
import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorMapper implements CustomRowMapper<Administrator> {

    private static final AdministratorMapper instance = new AdministratorMapper();

    public static AdministratorMapper getInstance() {
        return instance;
    }

    private AdministratorMapper() {
    }

    @Override
    public void rowMap(Administrator administrator, ResultSet resultSet) throws DaoException {
        try {
            administrator.setIdAdministrator(resultSet.getInt(RequestParameter.ID_ADMINISTRATOR));
            administrator.setStatus(Administrator.Status.valueOf(resultSet.getString(RequestParameter.STATUS).toUpperCase()));
            administrator.setExperience(Double.parseDouble(resultSet.getString(RequestParameter.EXPERIENCE)));
            //information todo
            logger.log(Level.INFO,"administrator added");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
