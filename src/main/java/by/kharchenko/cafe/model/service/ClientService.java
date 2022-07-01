package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findClientsWithOverdueOrders() throws ServiceException;

    List<Client> findClientsByPageNumber(int currentPage) throws ServiceException;

    int countClients() throws ServiceException;

    Optional<Client> findClientByLogin(String login) throws ServiceException;

    List<Client> findTodayClients() throws ServiceException;

    Optional<Client> findClientByUserId(Integer idUser) throws ServiceException;

    boolean unlockClient(int parseInt) throws ServiceException;

    Client findClientWithOverdueOrders(int idClient) throws ServiceException;
}
