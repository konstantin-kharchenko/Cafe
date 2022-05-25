package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.controller.RequestParameter;
import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Menu;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MenuMapper implements CustomRowMapper<Menu> {
    private static final MenuMapper instance = new MenuMapper();

    public static MenuMapper getInstance() {
        return instance;
    }

    private MenuMapper() {

    }

    @Override
    public void rowMap(Menu menu, ResultSet resultSet) throws DaoException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try {
            menu.setIdMenu(resultSet.getInt(RequestParameter.ID_MENU));
            menu.setName(resultSet.getString(RequestParameter.NAME));
            menu.setDate(format.parse(resultSet.getString(RequestParameter.DATE)));
            //photo todo
            logger.log(Level.INFO, "menu" + menu.getName() + " added");
        } catch (SQLException | ParseException e) {
            throw new DaoException(e);
        }
    }
}
