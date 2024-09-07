package services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ServiceMailing {

    private static final String FROM_EMAIL = "ichrak@gmail.com";

    public static Properties getMailtrapProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true); // Use TLS for secure communication
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Replace with your actual hostname if it's different
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.ssl.enable", "false"); // No need for SSL with TLS enabled
        props.put("mail.user", ""); // Replace with your Mailtrap username
        props.put("mail.password", ""); // Replace with your Mailtrap password (avoid storing directly in code)
        return props;


    }
    public static void sendHtmlNotification(String toEmail, String subject, String code) {
        String htmlContent = "templateMail.html";
        Properties props = getMailtrapProperties();


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(FROM_EMAIL));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            mimeMessage.setSubject(subject);


            String html = loadHtmlFromResources(htmlContent);

            html = html.replace("$code", code);
            mimeMessage.setContent(html, "text/html");


            Transport.send(mimeMessage);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String loadHtmlFromResources(String resourceName) {
        try (InputStream is = ServiceMailing.class.getResourceAsStream("/Mailing/" + resourceName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
