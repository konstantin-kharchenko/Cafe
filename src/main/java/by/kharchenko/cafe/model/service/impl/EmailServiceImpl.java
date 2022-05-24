package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.email.MailThread;
import by.kharchenko.cafe.model.service.EmailService;
import by.kharchenko.cafe.validator.DataValidator;
import by.kharchenko.cafe.validator.impl.DataValidatorImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {
    private static final EmailServiceImpl instance = new EmailServiceImpl();
    private final DataValidator validator = DataValidatorImpl.getInstance();

    private EmailServiceImpl() {
    }

    public static EmailServiceImpl getInstance() {
        return instance;
    }
    @Override
    public boolean sendMail(String mail) {
        Properties properties = null;
        ClassLoader classLoader = EmailServiceImpl.class.getClassLoader();
        InputStream fileInputStream;
        properties = new Properties();
        try {
            fileInputStream = classLoader.getResourceAsStream("config/mail.properties");
            properties.load(fileInputStream);
            MailThread mailOperator =
                    new MailThread(mail,"registration message", "you successful registration in site Cafe", properties);
            mailOperator.start();
        } catch (IOException e) {
            //log
            throw new ExceptionInInitializerError(e);
        }
        return false;
    }
}
