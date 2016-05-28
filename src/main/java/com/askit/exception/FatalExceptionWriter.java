package com.askit.exception;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class FatalExceptionWriter {

	// TODO is it thread safe

	private static final int SLEEP_TIME = 1000;
	private static final FatalExceptionWriter INSTANCE = new FatalExceptionWriter();
	private static final String ERROR_LOG_FILE_NAME = "java_error_log";
	private static final long TIMEOUT = 5 * 60 * 1000; // 5min
	private static final File ERROR_FILE = new File("", ERROR_LOG_FILE_NAME); // TODO

	private final Thread checkThread;
	private final Map<Exception, Long> exceptionMap = new ConcurrentHashMap<Exception, Long>();

	private FatalExceptionWriter() {
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
					} else {
						try {
							Thread.sleep(SLEEP_TIME);
						} catch (final InterruptedException e) {
							// TODO was hier
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
	public static synchronized FatalExceptionWriter getInstance() {
		return INSTANCE;
	}

	/**
	 *
	 * @param exception
	 *            the Excpetion to handle
	 */
	public void handleError(final Exception exception) {
		if (insertException(exception)) {
			exception.printStackTrace();
			writeFullErrorToFile(exception);
		} else {
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
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (final IOException e) {
				// TODO was muss man hier noch machen
			}
		}
	}

	private void writeFullErrorToFile(final Exception exception) {
		String errorText = "";
		errorText = errorText + new Date(System.currentTimeMillis()).toString();
		errorText = errorText + " : (" + exception.hashCode() + ") " + getExceptionText(exception);
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

	/**
	 * This method extracts the text of a exception
	 *
	 * @param exception
	 *            the exception
	 * @return the exception text
	 */
	private static String getExceptionText(final Exception exception) {
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter, true);
		printWriter.flush();
		exception.printStackTrace(printWriter);
		return stringWriter.getBuffer().toString();
	}
}