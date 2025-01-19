package com.bill.tech.util;
//

//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.extern.slf4j.Slf4j;
//
//
//@Slf4j
//@Component
//public class EmailUtil {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public String getMailMessageBodyForOtp(Integer userID, long otp) {
//        String messageForOtp = "Your OTP has been generated successfully.\n\n" +
//            " UserID : " + userID + "\n" +
//            " OTP : " + otp + "\n\n" +
//            "Please refer and change your password ";
//        return messageForOtp;
//    }
//
//    public String sendMailForOtp(Integer userID, String email, long otp) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//            messageHelper.setFrom("hru111297@gmail.com"); // Replace with your Gmail
//            messageHelper.setTo(email);
//            messageHelper.setSubject("One Time Password");
//            messageHelper.setText(getMailMessageBodyForOtp(userID, otp));
//
//            javaMailSender.send(mimeMessage);
//            log.info("Mail sent successfully...");
//            return "Mail sent successfully...";
//        } catch (MessagingException e) {
//            log.error("Error while sending mail: " + e.getMessage());
//            return "Error while sending mail: " + e.getMessage();
//        }
//    }
//    
//    public static final long genOTP() throws NoSuchAlgorithmException {
//		long otp = 0;
//		Random random = SecureRandom.getInstanceStrong();
//
//		otp = random.nextInt(900000) + 100000L;
//
//		return otp;
//	}
//}

import java.beans.PropertyVetoException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import org.springframework.stereotype.Component;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.SendFailedException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class EmailUtil {

	public static final String userName = "uatapps@rabbitandtortoise.com";
	public static final String password = "2tXsl@nq";
	Session session;

	public String getMailMessageBodyForOtp(String userID, long otp) {
		String messageForOtp = "Your OTP has been generated successfully.\n\n" + " UserID : " + userID + "\n"
				+ " OTP : " + otp + "\n\n" + "Please refer and change your password ";
		return messageForOtp;
	}

	public String sendMailForOtp(String userID, String email, long otp)
			throws SQLException, PropertyVetoException, MessagingException {

		try {
			Properties properties = new Properties();

			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.zoho.com");
			properties.put("mail.smtp.port", "587");

			session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});

			MimeMessage mimeMessage = new MimeMessage(session);

			mimeMessage.setFrom(userName);
			mimeMessage.addRecipients(Message.RecipientType.TO, email);
			mimeMessage.setSubject("One Time Password");
			mimeMessage.setContent(getMailMessageBodyForOtp(userID, otp), "text/plain");

			Transport.send(mimeMessage);
			log.info("Mail Send successfully....");
			return "Mail Send successfully....";

		} catch (SendFailedException sfe) {
			log.error("Send failed: " + sfe.getMessage());
			log.error("uatapps@rabbitandtortoise.com mailId may be blocked.. Please connect to IT team"
					+ sfe.getMessage());
			return "Send failed: " + sfe.getMessage();
		} catch (MessagingException mex) {
			log.warn("Messaging exception: " + mex.getMessage());
			return "Messaging exception: " + mex.getMessage();

		}

	}

	public static final long genOTP() throws NoSuchAlgorithmException {
		long otp = 0;
		Random random = SecureRandom.getInstanceStrong();

		otp = random.nextInt(900000) + 100000L;

		return otp;
	}
}
