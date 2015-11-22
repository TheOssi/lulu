package com.betit.etc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Date;

import com.betit.database.ConnectionFactory;

public class ErrorHelper {

	private static final String ERROR_LOG_FILE_NAME = "java_error_log";

	/**
	 * 
	 * @param exception
	 */
	public static void writeToErrorFile(final Exception exception) {
		final File errorFile = new File(getRunntimeDirectory(), ERROR_LOG_FILE_NAME);
		FileWriter writer = null;
		try {
			writer = new FileWriter(errorFile, true);
			writer.write(new Date(System.currentTimeMillis()).toString());
			writer.write(" : " + getExceptionText(exception));
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

	/**
	 * This method will help to select the current execution path
	 * 
	 * @return the current runntime directory
	 */
	private static File getRunntimeDirectory() {
		File jarFile = null;
		File parentPathOfJar = null;
		try {
			jarFile = new File(ConnectionFactory.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString());
			parentPathOfJar = jarFile.getParentFile();
		} catch (final URISyntaxException e) {
			parentPathOfJar = new File(File.separator + "." + File.separator);
		}
		return parentPathOfJar;
	}

	/**
	 * 
	 * @param exception
	 * @return the exception text
	 */
	public static String getExceptionText(final Exception exception) {
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter, true);
		printWriter.flush();
		exception.printStackTrace(printWriter);
		return stringWriter.getBuffer().toString();
	}

}
