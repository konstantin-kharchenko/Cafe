package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.email.MailThread;
import by.kharchenko.cafe.model.service.EmailService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {
    private static final String MAIL_PROPERTIES_PATH = "email.configuration/mail.properties";
    private static final EmailServiceImpl instance = new EmailServiceImpl();

    private EmailServiceImpl() {
    }

    public static EmailServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean sendMail(String mail, String mailSubject, String mailText) throws ServiceException {
        Properties properties = null;
        ClassLoader classLoader = UserServiceImpl.class.getClassLoader();
        InputStream fileInputStream;
        properties = new Properties();
        try {
            fileInputStream = classLoader.getResourceAsStream(MAIL_PROPERTIES_PATH);
            properties.load(fileInputStream);
            MailThread mailOperator =
                    new MailThread(mail, mailSubject, mailText, properties);
            mailOperator.start();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return false;
    }
}
