package com.askit.face;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lelmac
 * Supports creating and reading pictures from the the filesystem
 */
public class PictureSupporter {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	final static String rootPath = "./pictures";

	/**
	 * creates Picturefile at specified path
	 * @param data : Binary data
	 * @param path : filepath
	 * @param id : id of File
	 */
	public static void createPictureFile(String data, String path, Long id) {
		path = rootPath + path;
		try {
			writeFile(path, data, id.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns picture data
	 * @param path: path of picture(more like category)
	 * @param id : id of requested picture
	 * @return binary picture data
	 */
	public static String getPicture(String path, Long id) {
		String data = null;
		path = rootPath + path + "/" + id;
		try {
			data = readFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**Private Method, writes File to filesystem
	 * @param path
	 * @param data
	 * @param fileName
	 * @throws IOException
	 */
	private static void writeFile(String path, String data, String fileName) throws IOException {
		Path fullPath = Paths.get(path+"/" + fileName);
		Path dirPath = Paths.get(path);
		if(!Files.exists(dirPath)){
			Files.createDirectory(dirPath);
		}
		try (BufferedWriter writer = Files.newBufferedWriter(fullPath, ENCODING)) {
			writer.write(data);
		}

	}

	/**Private Method reads File from file system
	 * @param fileUri
	 * @return
	 * @throws IOException
	 */
	private static String readFile(String fileUri) throws IOException {
		StringBuilder sBuilder = new StringBuilder();
		Path path = Paths.get(fileUri);
		try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}
		}
		return sBuilder.toString();
	}

}
