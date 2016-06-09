package com.askit.database;

import javax.mail.MessagingException;

import com.askit.exception.DatabaseLayerException;
import com.askit.util.SMPTEmailSender;

public class PasswordResetter {

	public static void resetPassword(final Long userID) throws DatabaseLayerException {
		final String newPassword = "";
		final QueryManager databaseQueryManager = new DatabaseQueryManager();
		final String emailAdress = databaseQueryManager.getEmail(userID);
		final String message = "Your new password is " + newPassword;
		// TODO hash the new password
		final String newPasswordHash = "";
		databaseQueryManager.setPasswordHash(userID, newPasswordHash);
		try {
			SMPTEmailSender.sendMail(new String[] { emailAdress }, "Password reset", message);
		} catch (final MessagingException e) {
			throw new DatabaseLayerException(e);
		}
	}
}