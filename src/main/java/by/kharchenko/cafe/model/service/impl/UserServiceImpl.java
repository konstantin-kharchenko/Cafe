package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.UserDao;
import by.kharchenko.cafe.model.dao.impl.AdministratorDaoImpl;
import by.kharchenko.cafe.model.dao.impl.ClientDaoImpl;
import by.kharchenko.cafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.UserService;
import by.kharchenko.cafe.util.encryption.EncryptionPassword;
import by.kharchenko.cafe.util.filereadwrite.FileReaderWriter;
import by.kharchenko.cafe.validator.DataValidator;
import by.kharchenko.cafe.validator.impl.UserValidatorImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static by.kharchenko.cafe.controller.RequestAttribute.EMPTY;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class UserServiceImpl implements BaseService<User>, UserService {
    private static final String FILE_EXTENSION = ".txt";
    private static final String PHOTO_PATH_ON_HDD = "D:\\FINAL_PROJECT\\PHOTO\\";
    private static final String MAIL_SUBJECT = "Registration message";
    private static final String MAIL_TEXT = "you have successfully registration in the 'Cafe' application ";
    private static final UserServiceImpl instance = new UserServiceImpl();
    private final DataValidator validator = UserValidatorImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(User user) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Map<String, String> userData) throws ServiceException {
        boolean isCorrectData = validator.isCorrectRegisterData(userData);
        boolean isLoginExists;
        try {
            if (isCorrectData) {
                isLoginExists = UserDaoImpl.getInstance().findIdUserByLogin(userData.get(LOGIN)).isPresent();
                if (!isLoginExists) {
                    String encryptionPassword = EncryptionPassword.encryption(userData.get(PASSWORD));
                    userData.put(PASSWORD, encryptionPassword);
                    UserDaoImpl userDao = UserDaoImpl.getInstance();
                    DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    User user = new User();
                    user.setName(userData.get(NAME));
                    user.setSurname(userData.get(SURNAME));
                    user.setLogin(userData.get(LOGIN));
                    user.setPassword(encryptionPassword);
                    user.setEmail(userData.get(EMAIL));
                    user.setBirthday(LocalDate.parse(userData.get(BIRTHDAY), parser));
                    user.setRegistrationTime(new Date());
                    user.setPhoneNumber(userData.get(PHONE_NUMBER));
                    user.setRole(User.Role.valueOf(userData.get(ROLE).toUpperCase()));

                    boolean match = userDao.add(user);
                    if (match) {
                        EmailServiceImpl.getInstance().sendMail(userData.get(EMAIL), MAIL_SUBJECT, MAIL_TEXT);
                    }
                    return match;
                } else {
                    userData.put(LOGIN, LOGIN_EXISTS);
                    return false;
                }
            } else {
                if (!Objects.equals(userData.get(LOGIN), "")) {
                    isLoginExists = UserDaoImpl.getInstance().findIdUserByLogin(userData.get(LOGIN)).isPresent();
                    if (isLoginExists) {
                        userData.put(LOGIN, LOGIN_EXISTS);
                    }
                    return false;
                }
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> userData) throws ServiceException {
        try {
            boolean isCorrectData;
            if (userData.get(ROLE).equals(User.Role.ADMINISTRATOR.toString())) {
                isCorrectData = validator.isCorrectUpdateAdministratorData(userData);
            } else {
                isCorrectData = validator.isCorrectUpdateClientData(userData);
            }
            Integer idUser = Integer.parseInt(userData.get(ID_USER));
            String login = userData.get(LOGIN);
            Optional<Integer> idAnotherUser;
            String photoName = userData.get(PHOTO_NAME);
            boolean isCorrectPhoto = true;
            if (!photoName.equals("")) {
                isCorrectPhoto = validator.isCorrectPhoto(userData.get(PHOTO_NAME));
            }
            if (!userData.get(LOGIN).equals("")) {
                idAnotherUser = UserDaoImpl.getInstance().findIdUserWhereLoginAndNotIdUser(login, idUser);
                if (idAnotherUser.isPresent()) {
                    userData.put(LOGIN, LOGIN_EXISTS);
                    if (!photoName.equals("") && !isCorrectPhoto) {
                        userData.put(PHOTO, "");
                    } else if (photoName.equals("")) {
                        userData.put(PHOTO, EMPTY);
                    }
                    return false;
                }
            }
            if (isCorrectData && isCorrectPhoto) {
                User user = null;
                StringBuilder stringBuilder = new StringBuilder(PHOTO_PATH_ON_HDD);
                if (userData.get(ROLE).equals(User.Role.CLIENT.toString())) {
                    stringBuilder.append(User.Role.CLIENT);
                    user = new Client();
                } else if (userData.get(ROLE).equals(User.Role.ADMINISTRATOR.toString())) {
                    stringBuilder.append(User.Role.ADMINISTRATOR);
                    user = new Administrator();
                    ((Administrator) user).setExperience(Double.parseDouble(userData.get(EXPERIENCE)));
                }
                stringBuilder.append("\\").append(userData.get(ID_USER)).append(FILE_EXTENSION);
                String path = stringBuilder.toString();
                if (!photoName.equals("")) {
                    FileReaderWriter.getInstance().writePhoto(userData.get(PHOTO), path);
                }
                userData.put(PHOTO, path);
                if (user != null) {
                    user.setName(userData.get(NAME));
                    user.setSurname(userData.get(SURNAME));
                    user.setLogin(userData.get(LOGIN));
                    user.setPhoneNumber(userData.get(PHONE_NUMBER));
                    user.setPhotoPath(path);
                    user.setIdUser(Integer.parseInt(userData.get(ID_USER)));
                    return UserDaoImpl.getInstance().update(user);
                } else {
                    return false;
                }

            } else {
                if (photoName.equals("")) {
                    userData.put(PHOTO, EMPTY);
                }
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User.Role> authenticate(String login, String password) throws ServiceException {
        Optional<User.Role> role = Optional.empty();
        boolean isCorrectLogin = validator.isCorrectLogin(login);
        boolean isCorrectPassword = validator.isCorrectPassword(password);
        if (isCorrectLogin && isCorrectPassword) {
            String encryptionPassword = EncryptionPassword.encryption(password);
            UserDao userDao = UserDaoImpl.getInstance();
            boolean match;
            try {
                match = userDao.authenticate(login, encryptionPassword);
                if (match) {
                    role = userDao.findUserRoleByLogin(login);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return role;
    }

    @Override
    public User.Role findUserRoleByLogin(String login) throws ServiceException {
        return null;
    }

    @Override
    public Optional<? extends User> findUserByLoginAndRole(String login, User.Role role) throws ServiceException {
        try {
            Optional<Integer> userId = UserDaoImpl.getInstance().findIdUserByLogin(login);
            if (userId.isPresent()) {
                if (role == User.Role.CLIENT) {
                    Client client = ClientDaoImpl.getInstance().findClientByUserId(userId.get());
                    if (client == null) {
                        return Optional.empty();
                    }
                    String photoPath = client.getPhotoPath();
                    String stringPhoto = FileReaderWriter.getInstance().readPhoto(photoPath);
                    client.setStringPhoto(stringPhoto);
                    return Optional.of(client);
                } else if (role == User.Role.ADMINISTRATOR) {
                    Administrator administrator = AdministratorDaoImpl.getInstance().findAdministratorByUserId(userId.get());
                    if (administrator == null) {
                        return Optional.empty();
                    }
                    String photoPath = administrator.getPhotoPath();
                    String stringPhoto = FileReaderWriter.getInstance().readPhoto(photoPath);
                    administrator.setStringPhoto(stringPhoto);
                    return Optional.of(administrator);
                } else {
                    throw new ServiceException("no user");
                }
            } else {
                throw new ServiceException("no user id");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> findLogins() throws ServiceException {
        return null;
    }

    @Override
    public String findStringPhotoByStringPath(String stringPath) throws ServiceException {
        return FileReaderWriter.getInstance().readPhoto(stringPath);
    }

    @Override
    public void refill(BigDecimal refill, Integer idClient) throws ServiceException {
        try {
            UserDaoImpl.getInstance().refill(refill, idClient);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
