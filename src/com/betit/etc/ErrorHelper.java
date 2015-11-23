package com.betit.etc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class ErrorHelper {

	private static final String ERROR_LOG_FILE_NAME = "java_error_log";

	/**
	 * 
	 * @param exception
	 */
	public static void writeToErrorFile(final Exception exception) {
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
