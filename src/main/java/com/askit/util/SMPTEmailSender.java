package com.askit.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.askit.exception.ExceptionHandler;

public class SMPTEmailSender {

	private static final String TEXT_PLAIN = "text/plain";
	private static final String SMTP_HOST_NAME = "";
	private static String smptpUser = "";
	private static String smptPassword = "";

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

	static {
		setAuth();
	}

	public static void sendMail(final String recipients[], final String subject, final String message) throws MessagingException {
		try {
			final boolean debug = false;
			final Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			final Authenticator auth = new SMTPAuthenticator();
			final Session session = Session.getDefaultInstance(props, auth);
			session.setDebug(debug);

			final Message msg = new MimeMessage(session);

			// simple way, because the user of the acooount send the email
			final String emailFrom = smptpUser;

			final InternetAddress addressFrom = new InternetAddress(emailFrom);
			msg.setFrom(addressFrom);
			final InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			msg.setSubject(subject);

			msg.setContent(message, TEXT_PLAIN);

			Transport.send(msg);

		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private static void setAuth() {
		Properties properties = null;
		try {
			properties = PropertiesFileHelper.loadPropertiesFile(new File(PropertiesFileHelper.CONFIG_RROT_DIR, "email.properties"));
		} catch (final IOException e) {
			ExceptionHandler.getInstance().handleError(e);
		}
		smptpUser = properties.getProperty("email");
		smptPassword = properties.getProperty("password");
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
