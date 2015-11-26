package com.askit.etc;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;

import com.askit.database.ConnectionFactory;

public class Util {

	/**
	 * This method will help to select the current execution path
	 * 
	 * @return the current runntime directory
	 */
	public static File getRunntimeDirectory() {
		File jarFile = null;
		File parentPathOfJar = null;
		try {
			jarFile = new File(ConnectionFactory.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString());
			parentPathOfJar = jarFile.getParentFile();
		} catch (final URISyntaxException e) {
			parentPathOfJar = new File(File.separator + ".");
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
