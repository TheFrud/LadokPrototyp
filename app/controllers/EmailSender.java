package controllers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    //String mail = dit mejlet ska skickas, dvs användarens mejl
    //String sendControl = controlmejl som skickas till oss, jag har redan satt denna men du kan ändra den till din egen
    //String subject = ämnesraden
    //String content = innehåll i mejlet till användaren.
    //String controlSubject = ämnesraden i controlmejlet
    //String controlContent = innehåll i kontrollmejlet
    public static void start(String mail, int sendControl, String subject, String controlSubject, String content, String controlContent) {

        String to = mail;
        String toControl = "magnus.inghardt@gmail.com";

        String from = "ladokprototyp@inghardt.se";
        final String username = "bbe2855f-0a6d-40d0-b2d3-d49ed0acbd293";
        final String password = "bbe2855f-0a6d-40d0-b2d3-d49ed0acbd293";

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