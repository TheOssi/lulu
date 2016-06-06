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
public class PictureSupporter {
	private static final Charset ENCODING = StandardCharsets.UTF_8;
	private static final String ROOT_PATH = "./pictures";
	public static final String PUBLIC_QUESTION_PATH = "/publicQuestion";
	public static final String PRIVATE_QUESTION_PATH = "/privateQuestion";
	public static final String GROUP_QUESTION_PATH = "/group";
	public static final String USER_QUESTION_PATH = "/user";
	public static final ExceptionHandler EXCEPTION_HANDLER = ExceptionHandler.getInstance();

	/**
	 * creates Picturefile at specified path
	 *
	 * @param data
	 *            : Binary data
	 * @param path
	 *            : filepath
	 * @param id
	 *            : id of File
	 */
	public static void createPictureFile(final String data, String path, final Long id) {
		path = ROOT_PATH + path;
		try {
			writeFile(path, data, id.toString());
		} catch (final IOException e) {
			EXCEPTION_HANDLER.handleError(e);
		}
		System.out.println("Created File");
	}

	/**
	 * returns picture data
	 *
	 * @param path
	 *            : path of picture(more like category)
	 * @param id
	 *            : id of requested picture
	 * @return binary picture data
	 */
	public static String getPicture(String path, final Long id) {
		String data = null;
		path = ROOT_PATH + path + "/" + id;
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

	/**
	 * @param path
	 * @param fileName
	 */
	public static void createFile(final String path, final String fileName) {
		final Path fullPath = Paths.get(path + "/" + fileName);
		final Path dirPath = Paths.get(path);
		if (!Files.exists(dirPath)) {
			try {
				Files.createDirectory(dirPath);
			} catch (final IOException e) {
				EXCEPTION_HANDLER.handleError(e);
			}
		}
		if (!Files.exists(fullPath)) {
			try {
				Files.createFile(fullPath);
			} catch (final IOException e) {
				EXCEPTION_HANDLER.handleError(e);
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