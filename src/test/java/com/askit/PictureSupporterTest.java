package com.askit;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.askit.face.PictureSupporter;

public class PictureSupporterTest {
	@Test
	public void testWriteRead() {
		String data = "TestString";
		String path = "/group";
		Long id = 1234L;
		PictureSupporter.createPictureFile(data, path, id);
		String readString = PictureSupporter.getPicture(path, id);
		assertThat("Write=Read", data.equals(readString));
	}
	
	@Test
	public void writeMultipleFiles(){
		String[] paths = {"/group","/privateQuestion","/pubQuestion","/user"};
		for(int i = 0;i<100;i++){
			Long id = Double.doubleToLongBits(Math.random()*1000000);
			int rnd = (int) ((Math.random()*4));
			PictureSupporter.createPictureFile(id.toString(), paths[rnd], id);
		}
	}
	@AfterClass
	public static void tearDown() throws Exception{
		FileUtils.deleteDirectory(new File("./pictures"));
	}
	@BeforeClass
	public static void setUp() throws Exception{
		Files.createDirectory(Paths.get("./pictures"));
	}
}
