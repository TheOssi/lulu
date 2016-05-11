package com.askit.database;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.askit.entities.User;
import com.askit.exception.DatabaseLayerException;

@FixMethodOrder(MethodSorters.JVM)
public class DatabaseQueryManagerTest {

	private static final String DEFAULT_PICTURE_URI = "/PATH/TO/PICTURE";
	private static final long DEFAULT_SCORE_OF_GLOBAL = 1;
	private static final long DEFAULT_USER_ID = 1;
	private static final long DEFAULT_GROUP_ID = 1;

	private final QueryManager queryManager = new DatabaseQueryManager();

	private static User defaultUser;
	private static User defaultChangedUser;

	@BeforeClass()
	public static void setUpBeforeClass() throws Exception {
		defaultUser = new User(1L, "PASSWORDHASH", "PHONENUMBERHASH", "USERNAME", new Date(146295926700L), "URI/TO/PICTURE", "DE", 0);
		defaultChangedUser = new User(1L, "NEWPASSWORDHASH", "NEWPHONENUMBERHASH", "NEWUSERNAME", new Date(146295926700L), "NEW/URI/TO/PICTURE",
				"EN", 0);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheckUser() throws DatabaseLayerException {
		queryManager.checkUser("", "");
	}

	@Test
	public void testGetPublicQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPublicQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuestionsOfGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersByUsername() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsername() throws DatabaseLayerException {
		queryManager.getUsername(1);
	}

	@Test
	public void testGetUsersOfPublicQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersOfPrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersOfAnswerPrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersOfAnswerPublicQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersOfGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserScoreOfGlobal() throws DatabaseLayerException {
		assertThat("", queryManager.getUserScoreOfGlobal(1).equals(DEFAULT_PICTURE_URI));
	}

	@Test
	public void testGetUserScoreInGroup() throws DatabaseLayerException {
		fail("not");
	}

	@Test
	public void testGetPhoneNumberHash() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChoseAnswerInPublicQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChoseAnswerInPrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSelectedAnswerInPrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRankingInGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPasswordHash() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLanguage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProfilePictureURI() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGroupPictureURI() throws DatabaseLayerException {
		assertThat("Path is false", queryManager.getGroupPictureURI(1).equals(DEFAULT_PICTURE_URI));
	}

	@Test
	public void testGetGroupName() throws DatabaseLayerException {
		queryManager.getGroupName(1);
	}

	@Test
	public void testGetOldPrivateQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnswersOfPublicQuestionAndCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnswersOfPrivateQuestionAndCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActivePublicQuestionsOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActivePrivateQuestionsOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOldPublicQuestionsOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOldPrivateQuestionsOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllGroupScoresAndGlobalScoreOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersOfGroupsWithScore() {
		fail("Not yet implemented");
	}
	//
	// @Test
	// public void testSetLanguage() throws DatabaseLayerException {
	// queryManager.setLanguage(DEFAULT_USER_ID, "EN");
	// }
	//
	// @Test
	// public void testSetProfilPictureOfUser() throws DatabaseLayerException {
	// queryManager.setProfilPictureOfUser(DEFAULT_USER_ID, "URI/TO/PIC");
	// }
	//
	// @Test
	// public void testSetGroupPicture() throws DatabaseLayerException {
	// queryManager.setGroupPicture(DEFAULT_GROUP_ID, DEFAULT_PICTURE_URI);
	// }
	//
	// @Test
	// public void testSetPasswordHash() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSetChoosedAnswerOfPublicQuestion() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSetChoosedAnswerOfPrivateQuestion() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSetSelectedAnswerOfPrivateQuestion() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSetGroupAdmin() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSetPhoneNumberHash() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSetGroupName() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSetUsername() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSetPrivateQuestionToFinish() throws
	// DatabaseLayerException {
	// queryManager.setPrivateQuestionToFinish(1);
	// }
	//
	// @Test
	// public void testDeletePrivateQuestion() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testDeleteUserFromGroup() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testDeleteGroup() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testDeleteContact() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSearchForGroup() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSearchForPrivateQuestionInGroup() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSearchForPublicQuestion() {
	// fail("Not yet implemented");
	// }

}
