package com.askit;

import org.junit.Test;

import com.askit.entities.Answer;
import com.askit.entities.Message;
import com.askit.face.JSONBuilder;

public class JSONBuilderTest {
	@Test
	public void testBuildOneEnitity(){
		Answer aw = new Answer(new Long(2325), "Hello");
		JSONBuilder  jb = new JSONBuilder();
		System.out.println(jb.createJSON(aw));
	}
	@Test
	public void testBuildEntitiySet(){
		Answer aw = new Answer(new Long(2325), "Hello");
		Answer aw2 = new Answer(new Long(232422), "uhuhu");
		Answer aw3 = new Answer(new Long(232423), "Hi");
		Answer aw4 = new Answer(new Long(232424), "Answer");
		JSONBuilder  jb = new JSONBuilder();
		Answer[] aws = new Answer[5];
		aws[0] = aw;
		aws[1] = aw;
		aws[2] = aw3;
		aws[3] = aw4;
		aws[4] = aw2;
		System.out.println(jb.createJSON(aws));
	}
	@Test
	public void testRemoveNull(){
	Message m = new Message(new Long(0), new Long(2324), null, null);
	JSONBuilder  jb = new JSONBuilder();
	System.out.println(jb.createJSON(m));
	}
}
