package com.askit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.askit.exception.ExceptionHandler;

public class PropertiesFileHelper {

	public static final String CONFIG_RROT_DIR = "./config";

	public static Properties loadPropertiesFile(final File propertiesFile) throws IOException {
		InputStream inputStream = null;
		Properties properties = null;
		try {
			if (propertiesFile.exists() == false) {
				throw new FileNotFoundException("Propertiesfile not found; " + propertiesFile.getAbsolutePath());
			}
			properties = new Properties();
			inputStream = new FileInputStream(propertiesFile);
			properties.load(inputStream);

		} catch (final IOException e) {
			throw e;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (final IOException e) {
				ExceptionHandler.getInstance().handleError(e);
			}
		}
		return properties;
	}
}