package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ClientDaoImplTest extends AbstractDaoTest {
    private static final DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final int idUser = 1;
    private static final String name = "Kostya";
    private static final String surname = "May";
    private static final String login = "cat";
    private static final String password = "BPpb23-=";
    private static final String encryptionPassword = "-2712-119102118199710449-11510540-7882-121-99";
    private static final String email = "cattom@gmail.com";
    private static final String phoneNumber = "+375294781234";
    private static final User.Role role = User.Role.CLIENT;
    private static final LocalDate birthday = LocalDate.parse("1996-12-12", parser);
    private static final String photo = "D:\\FINAL_PROJECT\\PHOTO\\CLIENT\\1.txt";
    private static final int idClient = 1;
    private static final boolean isBlock = false;
    private static final int loyaltyPoints = 2;
    private static final BigDecimal clientAccount = new BigDecimal("0");
    private static final Date registrationTime = new Date();
    private static final ClientDaoImpl clientDao = ClientDaoImpl.getInstance();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static Client client;


    @BeforeMethod
    public void setUp() {
        client = new Client();
        client.setIdUser(idUser);
        client.setName(name);
        client.setSurname(surname);
        client.setLogin(login);
        client.setPassword(password);
        client.setEmail(email);
        client.setRegistrationTime(registrationTime);
        client.setPhoneNumber(phoneNumber);
        client.setRole(role);
        client.setBirthday(birthday);
        client.setPhotoPath(photo);
        client.setIdClient(idClient);
        client.setBlock(isBlock);
        client.setLoyaltyPoints(loyaltyPoints);
        client.setClientAccount(clientAccount);
    }

    @Test(priority = 0)
    public void testInsert() throws DaoException {
        boolean match = userDao.add(client);
        assertTrue(match);
        client.setPassword(encryptionPassword);
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testAuthenticate() throws DaoException {
        boolean match = userDao.authenticate(client.getLogin(), client.getPassword());
        assertTrue(match);
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testFindByUserId() throws DaoException {
        Client client1 = clientDao.findClientByUserId(idUser);
        assertEquals(client1, client1);
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testAddLoyaltyPoints() throws DaoException {
        boolean match = clientDao.addLoyaltyPoints(idClient);
        assertTrue(match);
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testClientAccount() throws DaoException {
        BigDecimal clientAccount2 = clientDao.clientAccountByIdClient(idClient);
        assertEquals(clientAccount2, client.getClientAccount());
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testUpdateClient() throws DaoException {
        assertTrue(userDao.update(client));
    }
}
