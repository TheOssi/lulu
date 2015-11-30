package com.askit;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.askit.entities.Answer;
import com.askit.entities.Message;
import com.askit.face.JSONBuilder;

public class JSONBuilderTest {
	@Test
	public void testBuildOneEnitity() {
		final Answer answer = new Answer(new Long(2325), "Hello");
		final JSONBuilder jsonBuilder = new JSONBuilder();
		final String jsonString = normalizeJSONString(jsonBuilder.createJSON(answer));
		final String jsonExpected = "{\"Answer\":{\"questionID\":" + answer.getQuestionID().intValue()
				+ ",\"answer\":\"" + answer.getAnswer() + "\"}}";
		//assertThat("The json doens't match the expected JSON", jsonExpected.equals(jsonString));
		System.out.println(jsonString);
	}

	@Test
	public void testBuildEntitiySet() {
		final String[] answerStrings = new String[] { "Hello", "uhuhu", "Hi", "Answer" };
		final Long[] ids = new Long[] { 2325L, 232422L, 232423L, 232424L };
		final Answer[] answers = new Answer[4];
		for (int i = 0; i < answers.length; i++) {
			answers[i] = new Answer(ids[i], new Long(1234), answerStrings[i]);

			System.out.println(answers[i].getQuestionID());
		}
		final String jsonString = normalizeJSONString(new JSONBuilder().createJSON(answers));
		System.out.println(jsonString);
		// String jsonExpected = "{\"AnswerSet\":{";
		// for (final Answer answer : answers) {
		// jsonExpected = jsonExpected + "\"" + Answer.class.getSimpleName() +
		// answer.hashCode()
		// }

	}

	@Test
	public void testRemoveNull() {
		final Message m = new Message(0L, new Long(2324), null, null);
		final JSONBuilder jb = new JSONBuilder();
		System.out.println(jb.createJSON(m));
	}

	private String normalizeJSONString(final String json) {
		return json.trim().replace("\t", "").replace(" ", "").replace(System.lineSeparator(), "").replace("\n", "");
	}
}
