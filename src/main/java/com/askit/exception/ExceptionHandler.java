package com.askit.exception;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ExceptionHandler {

	// TODO is it thread safe

	private static final int SLEEP_TIME = 1000;
	private static final ExceptionHandler INSTANCE = new ExceptionHandler();
	private static final String ERROR_LOG_FILE_NAME = "java_error_log";
	private static final long TIMEOUT = 5 * 60 * 1000; // 5min
	// TODO @M File creation and check
	private static final File ERROR_FILE = new File("", ERROR_LOG_FILE_NAME);

	private final Thread checkThread;
	private final Map<String, Long> exceptionMap = new ConcurrentHashMap<String, Long>();

	private ExceptionHandler() {
		checkThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (!exceptionMap.isEmpty()) {
						for (final Entry<String, Long> entry : exceptionMap.entrySet()) {
							final Long time = entry.getValue();
							if (time > System.currentTimeMillis()) {
								exceptionMap.remove(entry);
							}
						}
					} else {
						try {
							Thread.sleep(SLEEP_TIME);
						} catch (final InterruptedException e) {
							handleErrorInternal(e, false);
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
	public static synchronized ExceptionHandler getInstance() {
		return INSTANCE;
	}

	/**
	 *
	 * @param exception
	 *            the Excpetion to handle
	 */
	public void handleError(final Exception exception) {
		handleErrorInternal(exception, true);
	}

	private void handleErrorInternal(final Exception exception, final boolean writeToFile) {
		if (insertException(exception)) {
			// TODO delete this after test phase
			System.out.println("== BEGIN DEBUG ExceptionHandler ==");
			System.out.println(getExceptionText(exception));
			System.out.println("== END DEBUG ExceptionHandler ==");
			if (writeToFile) {
				writeFullErrorToFile(exception);
			} else {
				exception.printStackTrace();
			}
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
			writer.flush();
		} catch (final IOException e) {
			handleErrorInternal(e, false);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (final IOException e) {
				handleErrorInternal(e, false);
			}
		}
	}

	private void writeFullErrorToFile(final Exception exception) {
		String errorText = "";
		errorText = errorText + new Date(System.currentTimeMillis()).toString();
		errorText = errorText + " : (" + makeSHA1Hash(getExceptionText(exception)) + ") " + getExceptionText(exception);
		writeToErrorFile(errorText);
	}

	private void writeHashExceptionToFile(final Exception exception) {
		String errorText = "";
		errorText = errorText + new Date(System.currentTimeMillis()).toString();
		errorText = errorText + " : " + makeSHA1Hash(getExceptionText(exception));
		writeToErrorFile(errorText);
	}

	private boolean insertException(final Exception exception) {
		final String hashException = makeSHA1Hash(getExceptionText(exception));
		if (exceptionMap.containsKey(hashException)) {
			for (final Entry<String, Long> entry : exceptionMap.entrySet()) {
				final String key = entry.getKey();
				if (key.equals(hashException)) {
					entry.setValue(System.currentTimeMillis() + TIMEOUT);
				}
			}
			return false;
		} else {
			exceptionMap.put(hashException, System.currentTimeMillis() + TIMEOUT);
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

	private String makeSHA1Hash(final String input) {
		String hexStr = "";
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.reset();
			final byte[] buffer = input.getBytes("UTF-8");
			messageDigest.update(buffer);
			final byte[] digest = messageDigest.digest();
			for (final byte element : digest) {
				hexStr += Integer.toString((element & 0xff) + 0x100, 16).substring(1);
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			handleErrorInternal(e, false);
			hexStr = String.valueOf(System.currentTimeMillis());
		}
		return hexStr;
	}
}