package com.askit.etc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.MessagingException;

public class FantalExceptionWriter {

	// TODO is it thread safe

	private static final FantalExceptionWriter INSTANCE = new FantalExceptionWriter();
	private static final String ERROR_LOG_FILE_NAME = "java_error_log";
	private static final long TIMEOUT = 5 * 60 * 1000; // 5min
	private static final File ERROR_FILE = new File(Util.getRunntimeDirectory(), ERROR_LOG_FILE_NAME);

	private final Thread checkThread;
	private final Map<Exception, Long> exceptionMap = new ConcurrentHashMap<Exception, Long>();

	private FantalExceptionWriter() {
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

	/**
	 * 
	 * @return the instance of the errorHandler
	 */
	public static synchronized FantalExceptionWriter getInstance() {
		return INSTANCE;
	}

	/**
	 * This method handle a exception. If this exception doesn't appears in last
	 * 5 minutes, the stackTrance will be printed, the error will e written to a
	 * file and a emal will be send Else the Timeout of this exception will be
	 * increased of 5 minutes
	 * 
	 * @param exception
	 *            the Excpetion to handle
	 */
	public void handleError(final Exception exception) {
		if (insertException(exception)) {
			exception.printStackTrace();
			writeFullErrorToFile(exception);
			final String exceptionText = Util.getExceptionText(exception);
			final String message = "" + new Date(System.currentTimeMillis()).toString() + System.lineSeparator() + exceptionText;
			try {
				SMPTEmailSender.sendMail(new String[] { "kai.jmueller@gmail.com" }, exception.getMessage(), message);
			} catch (final MessagingException e1) {
				writeFullErrorToFile(e1);
			}
		} else {
			exception.printStackTrace();
			writeHashExceptionToFile(exception);
		}
	}

	private void writeToErrorFile(final String errorText) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(ERROR_FILE, true);
			writer.write(errorText);
			writer.write(System.lineSeparator());
		} catch (final IOException e) {
			// TODO was muss man hier noch machen
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (final IOException e) {
				// TODO was muss man hier noch machen
				e.printStackTrace();
			}
		}
	}

	private void writeFullErrorToFile(final Exception exception) {
		String errorText = "";
		errorText = errorText + new Date(System.currentTimeMillis()).toString();
		errorText = errorText + " : (" + exception.hashCode() + ") " + Util.getExceptionText(exception);
		writeToErrorFile(errorText);
	}

	private void writeHashExceptionToFile(final Exception exception) {
		String errorText = "";
		errorText = errorText + new Date(System.currentTimeMillis()).toString();
		errorText = errorText + " : " + exception.hashCode();
		writeToErrorFile(errorText);
	}

	private boolean insertException(final Exception exception) {

		if (exceptionMap.containsKey(exception)) {
			for (final Entry<Exception, Long> entry : exceptionMap.entrySet()) {
				final Exception key = entry.getKey();
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
}
