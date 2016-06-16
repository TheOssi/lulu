package com.askit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import com.askit.exception.ExceptionHandler;

public class PropertiesFileHelper {

	public static final String CONFIG_RROT_DIR = "./config";
	public static final String DEFAULT_CONFIG_FILE_NAME = "config.properties";
	private static Properties propteries = null;

	public static String getProperty(final String key) throws PropertyException {
		if (propteries == null) {
			try {
				propteries = loadPropertiesFileFromDefault();
			} catch (final IOException e) {
				throw new PropertyException(e);
			}
		}
		final String value = propteries.getProperty(key);
		if (value == null) {
			throw new PropertyException("Property " + key + " not found");
		}
		return value;
	}

	public static Properties loadPropertiesFileFromDefault() throws IOException {
		final File propertiesFile = new File(CONFIG_RROT_DIR, DEFAULT_CONFIG_FILE_NAME);
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