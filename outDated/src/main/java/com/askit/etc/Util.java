package com.askit.etc;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;

import com.askit.database.ConnectionManager;

/**
 * @author Kai Müller, Max Lenk
 * 
 *         This util class provides different helper methods
 */
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
			jarFile = new File(ConnectionManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString());
			parentPathOfJar = jarFile.getParentFile();
		} catch (final URISyntaxException e) {
			parentPathOfJar = new File(File.separator + ".");
		}
		return parentPathOfJar;
	}


}