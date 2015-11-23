package com.betit.etc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

public class ErrorHandler {

	private static final ErrorHandler INSTANCE = new ErrorHandler();
	private static final String ERROR_LOG_FILE_NAME = "java_error_log";

	private final List<Exception> exceptions = new ArrayList<Exception>();

	private ErrorHandler() {
	}

	public static ErrorHandler getInstance() {
		return INSTANCE;
	}

	public void handleError(final Exception exception) {
		// TODO send Error to Frontend AXB
		exception.printStackTrace();
		writeToErrorFile(exception);
		final String exceptionText = Util.getExceptionText(exception);
		final String message = "" + new Date(System.currentTimeMillis()).toString() + System.lineSeparator() + exceptionText;
		try {
			SMPTEmailSender.sendMail(new String[] { "kai.jmueller@gmail.com" }, exception.getMessage(), message);
		} catch (final MessagingException e1) {
			writeToErrorFile(e1);
		}
	}

	private void writeToErrorFile(final Exception exception) {
		final File errorFile = new File(Util.getRunntimeDirectory(), ERROR_LOG_FILE_NAME);
		FileWriter writer = null;
		try {
			writer = new FileWriter(errorFile, true);
			writer.write(new Date(System.currentTimeMillis()).toString());
			writer.write(" : " + Util.getExceptionText(exception));
			writer.write(System.lineSeparator());
		} catch (final IOException e) {
			// TODO was da los
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (final IOException e) {
				// TODO was da los
			}
		}
	}
}
