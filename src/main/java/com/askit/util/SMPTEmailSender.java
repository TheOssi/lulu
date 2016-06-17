package com.askit.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.PropertyException;

import com.askit.exception.ExceptionHandler;

public class SMPTEmailSender {
	private static final String TEXT_HML = "text/html";
	private static String smtpHostName = "";
	private static String smptpUser = "";
	private static String smptPassword = "";
	private static String smtpPort = "";
	private static String smptpMail = "";

	static {
		setAuth();
	}

	/**
	 * This Method sends a simple email over SMTP
	 * 
	 * @param recipients
	 *            the recipients of this email
	 * @param subject
	 *            the subject of the email
	 * @param message
	 *            the message of the email
	 * @throws MessagingException
	 */
	public static void sendMail(final String recipients[], final String subject, final String message) throws MessagingException {
		try {
			final boolean debug = false;
			final Properties props = new Properties();
			props.put("mail.smtp.host", smtpHostName);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", smtpPort);

			final Authenticator auth = new SMTPAuthenticator();
			final Session session = Session.getDefaultInstance(props, auth);
			session.setDebug(debug);

			final Message msg = new MimeMessage(session);

			final String emailFrom = smptpMail;

			final InternetAddress addressFrom = new InternetAddress(emailFrom);
			msg.setFrom(addressFrom);
			final InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			msg.setSubject(subject);

			msg.setContent(message, TEXT_HML);

			Transport.send(msg);

		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private static void setAuth() {
		try {
			smptpMail = PropertiesFileHelper.getProperty("email");
			smptpUser = PropertiesFileHelper.getProperty("user");
			smptPassword = PropertiesFileHelper.getProperty("password");
			smtpHostName = PropertiesFileHelper.getProperty("host");
			smtpPort = PropertiesFileHelper.getProperty("port");
		} catch (final PropertyException e) {
			ExceptionHandler.getInstance().handleError(e);
		}
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private static class SMTPAuthenticator extends javax.mail.Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			final String username = smptpUser;
			final String password = smptPassword;
			return new PasswordAuthentication(username, password);
		}
	}
}
