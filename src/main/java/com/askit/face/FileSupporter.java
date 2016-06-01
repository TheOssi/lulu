package com.askit.face;

//TODO delete System.put.println's

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.askit.exception.ExceptionHandler;

/**
 * @author lelmac Supports creating and reading pictures from the the filesystem
 */
public class FileSupporter {
	public final static String ROOT = "./";
	public final static String PICTURE_ROOT = "./pictures";
	public final static String CONFIG_ROOT = "./config";

	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private final static ExceptionHandler EXCEPTION_HANDLER = ExceptionHandler.getInstance();

	/**
	 * creates file at specified path
	 * 
	 * @param data
	 *            : Binary data
	 * @param path
	 *            : filepath
	 * @param fileName
	 *            : Name of File
	 */
	public static void createFileWithContent(final String data, final String path, final String fileName) {
		try {
			writeFile(path, data, fileName);
		} catch (final IOException e) {
			EXCEPTION_HANDLER.handleError(e);
		}
		System.out.println("Created File");
	}

	/**
	 * returns data of file
	 * 
	 * @param path
	 *            : path of picture(more like category)
	 * @param fileName
	 *            : name of teh requested file
	 * @return binary picture data
	 */
	public static String getFileContent(String path, final String fileName) {
		String data = null;
		path = path + "/" + fileName;
		try {
			data = readFile(path);
		} catch (final IOException e) {
			EXCEPTION_HANDLER.handleError(e);
		}
		return data;
	}

	/**
	 * Private Method, writes File to filesystem
	 * 
	 * @param path
	 * @param data
	 * @param fileName
	 * @throws IOException
	 */
	private static void writeFile(final String path, final String data, final String fileName) throws IOException {
		final Path fullPath = Paths.get(path + "/" + fileName);
		final Path dirPath = Paths.get(path);
		if (!Files.exists(dirPath)) {
			Files.createDirectory(dirPath);
		}
		try (BufferedWriter writer = Files.newBufferedWriter(fullPath, ENCODING)) {
			writer.write(data);
			System.out.println("wrote data");
		}

	}

	public static void createFile(final String path, final String fileName) {
		final Path fullPath = Paths.get(path + "/" + fileName);
		final Path dirPath = Paths.get(path);
		final ExceptionHandler eHandler = ExceptionHandler.getInstance();
		if (!Files.exists(dirPath)) {
			try {
				Files.createDirectory(dirPath);
			} catch (final IOException e) {
				eHandler.handleError(e);
			}
		}
		if (!Files.exists(fullPath)) {
			try {
				Files.createFile(fullPath);
			} catch (final IOException e) {
				eHandler.handleError(e);
			}
		}
	}

	/**
	 * Private Method reads File from file system
	 * 
	 * @param fileUri
	 * @return
	 * @throws IOException
	 */
	private static String readFile(final String fileUri) throws IOException {
		final StringBuilder sBuilder = new StringBuilder();
		final Path path = Paths.get(fileUri);
		try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}
		}
		return sBuilder.toString();
	}
}