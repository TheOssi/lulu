package com.askit;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.askit.face.FileSupporter;

public class PictureSupporterTest {
	@Test
	public void testWriteRead() {
		final String data = "TestString";
		final String path = "/group";
		final Long id = 1234L;
		FileSupporter.createFileWithContent(data, path, id.toString());
		final String readString = FileSupporter.getFileContent(path, id.toString());
		assertThat("Write=Read", data.equals(readString));
	}

	@Test
	public void writeMultipleFiles() {
		final String[] paths = { FileSupporter.PICTURE_ROOT + "/group", FileSupporter.PICTURE_ROOT + "/privateQuestion",
				FileSupporter.PICTURE_ROOT + "/pubQuestion", FileSupporter.PICTURE_ROOT + "/user" };
		for (int i = 0; i < 100; i++) {
			final Long id = Double.doubleToLongBits(Math.random() * 1000000);
			final int rnd = (int) (Math.random() * 4);
			FileSupporter.createFileWithContent(id.toString(), paths[rnd], id.toString());
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		FileUtils.deleteDirectory(new File("./pictures"));
	}

	@BeforeClass
	public static void setUp() throws Exception {
		Files.createDirectory(Paths.get("./pictures"));
	}
}
