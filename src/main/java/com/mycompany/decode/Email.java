package com.mycompany.decode;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author VanAnh
 */
public class Email {
        static final String from = "tuananh13022020@gmail.com";
	static final String password = "rrcc ihxb ulai nzdm";
        static Properties properties = new Properties();
	public static boolean sendEmail(String to, String tieuDe, String noiDung) {
		// Properties : khai báo các thuộc tính
                System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
                Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
		props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		// create Authenticator
                Authenticator auth = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, password);
                    }
		};
                properties.put("mail.smtp.ssl.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

                Session session = Session.getInstance(props, auth);
                MimeMessage msg = new MimeMessage(session);
		try {
                    msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                    msg.setFrom(new InternetAddress(from));
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
                    msg.setSubject(tieuDe);
                    msg.setSentDate(new Date());
                    msg.setContent(noiDung, "text/HTML; charset=UTF-8");
                    Transport.send(msg);
                    System.out.println("Send mail succcesfully!!!!");
                    return true;
		} catch (Exception e) {
			System.out.println("An error occurred while sending email =((((");
			e.printStackTrace();
			return false;
		}
	}
}
