package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.controller.RequestParameter;
import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements CustomRowMapper<Client> {
    private static final ClientMapper instance = new ClientMapper();

    public static ClientMapper getInstance() {
        return instance;
    }

    private ClientMapper() {

    }

    @Override
    public void rowMap(Client client, ResultSet resultSet) throws DaoException {
        try {
            client.setIdClient(resultSet.getInt(RequestParameter.ID_CLIENT));
            client.setLoyaltyPoints(resultSet.getInt(RequestParameter.LOYALTY_POINTS));
            client.setBlock(resultSet.getBoolean(RequestParameter.IS_BLOCK));
            client.setClientAccount(resultSet.getBigDecimal(RequestParameter.CLIENT_ACCOUNT));
            logger.log(Level.INFO, "client " + client.getName() + " added");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
