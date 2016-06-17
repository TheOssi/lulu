package com.askit.database;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.mail.MessagingException;

import com.askit.exception.DatabaseLayerException;
import com.askit.util.SMPTEmailSender;

public class PasswordResetter {

	// TODO may don't send the plain password

	public static void resetPassword(final Long userID) throws DatabaseLayerException {
		try {
			final QueryManager databaseQueryManager = new DatabaseQueryManager();
			final String emailAdress = databaseQueryManager.getEmail(userID);
			final String language = databaseQueryManager.getLanguage(userID);
			final String username = databaseQueryManager.getUsername(userID);
			final String newPassword = radomPassword(username + String.valueOf(System.currentTimeMillis()));

			String message = "";
			if (language.equals("DE")) {
				message = getGermanText(username, newPassword);
			} else if (language.equals("EN")) {
				message = getEnglishText(username, newPassword);
			}

			// TODO hash the new password
			final String newPasswordHash = newPassword;
			databaseQueryManager.setPasswordHash(userID, newPasswordHash);
			SMPTEmailSender.sendMail(new String[] { emailAdress }, "Password reset", message);
		} catch (DatabaseLayerException | MessagingException e) {
			throw new DatabaseLayerException("Error resetting password", e);
		}
	}

	private static String getEnglishText(final String username, final String newPassword) {
		return "Dear " + username + "<br/>your new password is: <b>" + newPassword + "</b><br/><br/>Regards,<br/>your AskIt-Team"
				+ "<br/><br/><br/><i>Please don't answer to this Mail</i>";
	}

	private static String getGermanText(final String username, final String newPassword) {
		return "Hallo " + username + "<br/>dein neues Passwort ist: <b>" + newPassword + "</b><br/><br/>Mit freundlichen Grüßen,<br/>dein AskIt-Team"
				+ "<br/><br/><br/><i>Bitte antworte nicht auf die diese Mail</i>";
	}

	private static String radomPassword(final String seed) {
		return new BigInteger(60, new SecureRandom(seed.getBytes())).toString(32);
	}
}