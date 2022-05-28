package com.example.demo.user;
import org.springframework.stereotype.Component;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


@Component
public class EmailService {

    private final String email = "p70987742@gmail.com";
    private final String password = "contTest";
    private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    public void sendEmail(String toEmail) {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email, password);
                        }});

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(email));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail,false));
            msg.setSubject("Bookstore order");
            msg.setText("Thank you for placing an order at our bookstore. We already started preparing your books. We'll contact you via email for shipping details soon.");
            msg.setSentDate(new Date());
            Transport.send(msg);
        }catch (MessagingException e){
            throw new RuntimeException("Error sending email", e);
        }
    }
}

