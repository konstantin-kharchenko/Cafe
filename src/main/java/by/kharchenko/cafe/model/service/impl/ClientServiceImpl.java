package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.ClientDaoImpl;
import by.kharchenko.cafe.model.dao.impl.OrderDaoImpl;
import by.kharchenko.cafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.ClientService;
import by.kharchenko.cafe.util.filereadwrite.FileReaderWriter;

import java.util.*;

public class ClientServiceImpl implements BaseService<Client>, ClientService {
    private static final ClientServiceImpl instance = new ClientServiceImpl();

    private ClientServiceImpl() {
    }

    public static ClientServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(Client client) throws ServiceException {
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
    public List<Client> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> t) throws ServiceException {
        return false;
    }

    @Override
    public List<Client> findClientsWithOverdueOrders() throws ServiceException {
        try {
            return ClientDaoImpl.getInstance().findClientsWithOverdueOrders();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Client> findClientsByPageNumber(int currentPage) throws ServiceException {
        try {
            List<Client> clients = ClientDaoImpl.getInstance().findClientsByPageNumber(currentPage);
            for (Client client : clients) {
                String stringPhoto = FileReaderWriter.getInstance().readPhoto(client.getPhotoPath());
                client.setStringPhoto(stringPhoto);
            }
            return clients;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countClients() throws ServiceException {
        try {
            return ClientDaoImpl.getInstance().countClients();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Client> findClientByLogin(String login) throws ServiceException {
        try {
            Optional<Integer> userId = UserDaoImpl.getInstance().findIdUserByLogin(login);
            if (userId.isPresent()) {
                Client client = ClientDaoImpl.getInstance().findClientByUserId(userId.get());
                if (client == null) {
                    return Optional.empty();
                }
                String photoPath = client.getPhotoPath();
                String stringPhoto = FileReaderWriter.getInstance().readPhoto(photoPath);
                client.setStringPhoto(stringPhoto);
                return Optional.of(client);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Client> findTodayClients() throws ServiceException {
        try {
            List<Order> todayOrders = OrderDaoImpl.getInstance().findTodayOrders();
            Set<Integer> idClients = new HashSet<>();
            todayOrders.forEach(a -> idClients.add(a.getIdClient()));
            List<Client> clients = ClientDaoImpl.getInstance().findClientsByIdClients(idClients.stream().toList());

            for (Client client : clients) {
                List<Order> clientTodayOrders = new ArrayList<>();
                for (Order order : todayOrders) {
                    if (client.getIdClient() == order.getIdClient()) {
                        clientTodayOrders.add(order);
                    }
                }
                client.setOrders(clientTodayOrders);
            }
            return clients;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Client> findClientByUserId(Integer idUser) throws ServiceException {
        try {
            Client client = ClientDaoImpl.getInstance().findClientByUserId(idUser);
            if (client == null) {
                return Optional.empty();
            }
            String photoPath = client.getPhotoPath();
            String stringPhoto = FileReaderWriter.getInstance().readPhoto(photoPath);
            client.setStringPhoto(stringPhoto);
            List<Order> orders = OrderDaoImpl.getInstance().findOrdersByIdClient(client.getIdClient());
            client.setOrders(orders);
            return Optional.of(client);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean unlockClient(int parseInt) throws ServiceException {
        try {
            return ClientDaoImpl.getInstance().unlock(parseInt);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Client findClientWithOverdueOrders(int idClient) throws ServiceException {
        try {
            return ClientDaoImpl.getInstance().findClientWithOverdueOrders(idClient);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
