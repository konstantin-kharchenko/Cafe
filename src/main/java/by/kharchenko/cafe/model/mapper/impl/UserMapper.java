package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.controller.RequestParameter;
import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class UserMapper implements CustomRowMapper<User> {

    private static final UserMapper instance = new UserMapper();

    public static UserMapper getInstance() {
        return instance;
    }

    private UserMapper() {

    }

    @Override
    public void rowMap(User user, ResultSet resultSet) throws DaoException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try {
            user.setIdUser(resultSet.getInt(RequestParameter.ID_USER));
            user.setName(resultSet.getString(RequestParameter.NAME));
            user.setSurname(resultSet.getString(RequestParameter.SURNAME));
            user.setLogin(resultSet.getString(RequestParameter.LOGIN));
            user.setPassword(resultSet.getString(RequestParameter.PASSWORD));
            user.setEmail(resultSet.getString(RequestParameter.EMAIL));
            user.setAge(resultSet.getInt(RequestParameter.AGE));
            user.setRegistrationTime(format.parse(resultSet.getString(RequestParameter.REGISTRATION_TIME)));
            user.setPhoneNumber(resultSet.getString(RequestParameter.PHONE_NUMBER));
            user.setRole(User.Role.valueOf(resultSet.getString(RequestParameter.ROLE).toUpperCase()));
            logger.log(Level.INFO,"user added");
        } catch (SQLException | ParseException e) {
            throw new DaoException(e);
        }
    }
}
