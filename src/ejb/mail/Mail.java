package ejb.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Daniel
 */
public class Mail {
    
    public Mail(){}
    
    public boolean SendMail(String mensage) {
        boolean enviado=false;
        String Username="***********************";
        String PassWord="***********************";
        String Subject="test mail";
        String To=Username;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, PassWord);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(To));
            message.setSubject(Subject);
            mensage+="<iframe src=\"https://docs.google.com/forms/d/e/1FAIpQLSesUlcnT3x7pJUODIGWsis1czpXuUVOnMQnuT6DrI6zEOm_EQ/viewform?embedded=true\" width=\"760\" height=\"500\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\">Cargando...</iframe>";        
            message.setText(mensage);
            message.setContent(mensage, "text/html; charset=utf-8");
            Transport.send(message);            
            enviado=true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return enviado;
    }
}
