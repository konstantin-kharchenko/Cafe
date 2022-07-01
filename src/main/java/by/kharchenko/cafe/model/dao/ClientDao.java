package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Client;

import java.math.BigDecimal;
import java.util.List;

public interface ClientDao {
    Client findClientByUserId(int idUser) throws DaoException;

    List<Client> findClientsWithOverdueOrders() throws DaoException;

    List<Client> findClientsByPageNumber(int currentPage) throws DaoException;

    int countClients() throws DaoException;

    List<Client> findClientsByIdClients(List<Integer> idClients) throws DaoException;

    boolean unlock(int parseInt) throws DaoException;

    void addLoyaltyPoints(int idClient) throws DaoException;

    void subLoyaltyPoints(int idClient) throws DaoException;

    Client findClientWithOverdueOrders(int idClient) throws DaoException;

    BigDecimal clientAccountByIdClient(int idClient) throws DaoException;

    void subPriceFromClientAccount(BigDecimal orderPrice, int idClient) throws DaoException;
}
