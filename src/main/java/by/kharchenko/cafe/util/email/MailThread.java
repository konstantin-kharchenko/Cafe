package by.kharchenko.cafe.util.email;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailThread extends Thread {

    private static final Logger logger = LogManager.getLogger(MailThread.class);
    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    public MailThread(String sendToEmail,
                      String mailSubject, String mailText, Properties properties) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.properties = properties;
    }

    private void init() {
        Session mailSession = (new SessionCreator(properties)).createSession();
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        try {
            message.setSubject(mailSubject);
            message.setContent(mailText, "text/html");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        } catch (AddressException e) {
            logger.log(Level.ERROR, "Некорректный адрес:" + sendToEmail + " " + e);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Ошибка формирования сообщения" + e);
        }
    }

    public void run() {
        init();
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Ошибка при отправлении сообщения" + e);
        }
    }
}
