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

public class SMPTEmailSender {

	// TODO account + in config file

	private static final String SMTP_HOST_NAME = "";
	private static final String SMTP_AUTH_USER = "";
	private static final String SMTP_AUTH_PWD = "";

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
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			final Authenticator auth = new SMTPAuthenticator();
			final Session session = Session.getDefaultInstance(props, auth);
			session.setDebug(debug);

			final Message msg = new MimeMessage(session);

			// simple way, because the user of the acooount send the email
			final String emailFrom = SMTP_AUTH_USER;

			final InternetAddress addressFrom = new InternetAddress(emailFrom);
			msg.setFrom(addressFrom);
			final InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			msg.setSubject(subject);

			msg.setContent(message, "text/plain");

			Transport.send(msg);

		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private static class SMTPAuthenticator extends javax.mail.Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			final String username = SMTP_AUTH_USER;
			final String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}
