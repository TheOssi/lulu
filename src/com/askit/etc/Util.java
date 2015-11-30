package com.askit.etc;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
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

	public static <T> T[] concatenateTwoArrays(final T[] arrayOne, final T[] arrayTwo) {
		final int arrayOneLenght = arrayOne.length;
		final int arrayTwoLenght = arrayTwo.length;
		@SuppressWarnings("unchecked")
		final T[] finalArray = (T[]) Array.newInstance(arrayOne.getClass().getComponentType(), arrayOneLenght + arrayTwoLenght);
		System.arraycopy(arrayOne, 0, finalArray, 0, arrayOneLenght);
		System.arraycopy(arrayTwo, 0, finalArray, arrayOneLenght, arrayTwoLenght);
		return finalArray;
	}

	public static String[] getFromToFromArray(final String[] array, final int from, final int to) {
		final int size = to - from + 1;
		final String[] selectedValues = new String[size];
		for (int i = 0; i < size; i++) {
			selectedValues[i] = array[from + i];
		}
		return selectedValues;
	}

}
