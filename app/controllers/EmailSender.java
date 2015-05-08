package controllers;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public static void start(String mail, int sendControl, String subject, String controlSubject, String content, String controlContent) {

        String to = mail;
        String toControl = "anmalan@ssvendels.se";

        String from = "anmalan@ssvendels.se";
        final String username = "078a8966-c7ea-4deb-aa4d-d4f2d147d063";//change accordingly
        final String password = "078a8966-c7ea-4deb-aa4d-d4f2d147d063";//change accordingly

        String host = "smtp.postmarkapp.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message1 = new MimeMessage(session);
            Message message2 = new MimeMessage(session);

            // Set From: header field of the header.
            message1.setFrom(new InternetAddress(from));
            message2.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message1.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message2.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toControl));

            // Set Subject: header field
            message1.setSubject(subject);
            message2.setSubject(controlSubject);

            // Now set the actual message
            message1.setContent(content, "text/HTML; charset=UTF-8");
            message2.setContent(controlContent, "text/HTML; charset=UTF-8");

            // Send message
            Transport.send(message1);

            if(sendControl == 1){
                Transport.send(message2);
            }


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}