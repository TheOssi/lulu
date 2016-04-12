package com.askit.queries;

import static org.junit.Assert.fail;

import java.sql.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.askit.entities.Group;
import com.askit.exception.DatabaseLayerException;

public class DatabaseQueryManagerTest {

	private final QueryManager queryManager = new DatabaseQueryManager();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testCreateNewGroup() throws DatabaseLayerException {
		final Group group = new Group(new Date(System.currentTimeMillis()), new Long(2), "", "");
		queryManager.createNewGroup(group);
	}

	@Test
	public void testCreatePublicQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateNewPrivateQuestionInGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateOneTimeQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUserToGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddContact() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUserToOneTimeQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAnswerToPublicQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAnswerToPrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUserToPublicQuestion() {
		fail("Not yet implemented");
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
	public void testGetUserScoreOfGlobal() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserScoreInGroup() {
		fail("Not yet implemented");
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
	public void testGetGroupPictureURI() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGroupName() {
		fail("Not yet implemented");
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

	@Test
	public void testSetLanguage() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetProfilPictureOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGroupPicture() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPasswordHash() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetChoosedAnswerOfPublicQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetChoosedAnswerOfPrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSelectedAnswerOfPrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGroupAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPhoneNumberHash() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGroupName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetUsername() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPrivateQuestionToFinish() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletePrivateQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUserFromGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteContact() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchForGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchForPrivateQuestionInGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchForPublicQuestion() {
		fail("Not yet implemented");
	}

}
