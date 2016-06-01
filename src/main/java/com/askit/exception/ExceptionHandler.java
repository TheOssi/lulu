package com.askit.exception;

import java.io.BufferedWriter;
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

/**
 * The ExceptionHandler is for the handling of Exceptions. The handler stores
 * the exceptions inside a map. If a new exception is inserted, the exception
 * will be written to a log file. After a certain time the exception is deleted
 * from the map. So if a error occurs and a exception is very of thrown, the
 * handler is the instance that helps for not spamming the log file.
 *
 * @author Kai Müller
 * @since 1.0.0.0
 * @version 1.0.0.0
 *
 */
public class ExceptionHandler {
	private static final ExceptionHandler INSTANCE = new ExceptionHandler();
	private static final String ERROR_LOG_FILE_NAME = "java_error_log.log";
	private static final long TIMEOUT = 5 * 60 * 1000; // 5min
	private static final String SHA1_HASH = "SHA1";
	private static final int SLEEP_TIME = 1000;
	private static final String UTF_8 = "UTF-8";

	private final Map<String, Long> exceptionMap = new ConcurrentHashMap<String, Long>();

	private final Thread checkThread;
	private final File errorFile;

	private ExceptionHandler() {
		errorFile = createErrorFile();
		checkThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (!exceptionMap.isEmpty()) {
						for (final Entry<String, Long> entry : exceptionMap.entrySet()) {
							final String key = entry.getKey();
							final Long time = entry.getValue();
							if (time <= System.currentTimeMillis()) {
								exceptionMap.remove(key);
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

	private File createErrorFile() {
		final File file = new File("./", ERROR_LOG_FILE_NAME);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (final IOException e) {
				handleErrorInternal(e, false);
			}
		}
		return file;
	}

	private void handleErrorInternal(final Exception exception, final boolean writeToFile) {
		if (insertException(exception)) {
			// TODO delete this after test phase
			doDebugOutput(exception);
			if (writeToFile) {
				writeFullErrorToFile(exception);
			} else {
				System.out.println("== BEGIN ExceptionHandler ==");
				exception.printStackTrace();
				System.out.println("== END DEBUG ExceptionHandler ==");
			}
		} else {
			if (writeToFile) {
				writeHashExceptionToFile(exception);
			} else {
				exception.printStackTrace();
			}
		}
	}

	private void doDebugOutput(final Exception exception) {
		System.out.println("== BEGIN DEBUG ExceptionHandler ==");
		System.out.println(getExceptionText(exception));
		System.out.println("== END DEBUG ExceptionHandler ==");
	}

	private void writeToErrorFile(final String errorText) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(errorFile, true));
			writer.write(errorText);
			writer.flush();
		} catch (final IOException e) {
			handleErrorInternal(e, false);
		} finally {
			try {
				writer.close();
			} catch (final IOException e) {
				handleErrorInternal(e, false);
			}
		}
	}

	private void writeFullErrorToFile(final Exception exception) {
		String errorText = "";
		errorText = errorText + new Date(System.currentTimeMillis()).toString();
		errorText = errorText + " : (" + getSHA1HashFromString(getExceptionText(exception)) + ") " + getExceptionText(exception);
		writeToErrorFile(errorText);
	}

	private void writeHashExceptionToFile(final Exception exception) {
		String errorText = "";
		errorText = errorText + new Date(System.currentTimeMillis()).toString();
		errorText = errorText + " : " + getSHA1HashFromString(getExceptionText(exception));
		writeToErrorFile(errorText);
	}

	private boolean insertException(final Exception exception) {
		final String hashException = getSHA1HashFromString(getExceptionText(exception));
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

	private String getSHA1HashFromString(final String input) {
		String hexStr = "";
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance(SHA1_HASH);
			messageDigest.reset();
			final byte[] buffer = input.getBytes(UTF_8);
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