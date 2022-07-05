package by.kharchenko.cafe.util.email.impl;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import by.kharchenko.cafe.util.email.MailThread;
import by.kharchenko.cafe.util.email.EmailSender;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailSenderImpl implements EmailSender {
    private static final String MAIL_PROPERTIES_PATH = "email.configuration/mail.properties";
    private static final EmailSenderImpl instance = new EmailSenderImpl();

    private EmailSenderImpl() {
    }

    public static EmailSenderImpl getInstance() {
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
