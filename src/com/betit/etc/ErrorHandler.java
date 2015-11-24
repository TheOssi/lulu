package com.betit.etc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.MessagingException;

public class ErrorHandler {

	// TODO make exceptionMap thread safe
	// TODO wegen fix, ausgabe bei Fehler immer ob Fehler noch da ist

	private static final ErrorHandler INSTANCE = new ErrorHandler();
	private static final String ERROR_LOG_FILE_NAME = "java_error_log";
	private static final long TIMEOUT = 5 * 60 * 1000; // 5min

	private final Thread checkThread;
	private final Map<Exception, Long> exceptionMap = new ConcurrentHashMap<Exception, Long>();

	private ErrorHandler() {
		checkThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (!exceptionMap.isEmpty()) {
						for (final Entry<Exception, Long> entry : exceptionMap.entrySet()) {
							final Exception key = entry.getKey();
							final Long value = entry.getValue();
							if (value > System.currentTimeMillis()) {
								exceptionMap.remove(key);
							}
						}
					}
				}
			}
		});
		checkThread.start();
	}

	public static synchronized ErrorHandler getInstance() {
		return INSTANCE;
	}

	public void handleError(final Exception exception) {
		if (insertException(exception)) {
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

	private boolean insertException(final Exception exception) {

		if (exceptionMap.containsKey(exception)) {
			for (final Entry<Exception, Long> entry : exceptionMap.entrySet()) {
				final Exception key = entry.getKey();
				final Long value = entry.getValue();
				if (key.equals(exception)) {
					entry.setValue(System.currentTimeMillis() + TIMEOUT);
				}
			}
			return false;
		} else {
			exceptionMap.put(exception, System.currentTimeMillis() + TIMEOUT);
			return true;
		}
	}

	private synchronized Map<Exception, Long> getExceptionMap() {
		return exceptionMap;
	}
}
