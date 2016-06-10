USE APP;

-- ================================================================================
-- AFTER UPDATE ON PrivateQuestions
-- 		This creates the connection between the users of a group and the private question
-- ================================================================================

DELIMITER &
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER tg_afterUpdate_PrivateQuestions
	AFTER INSERT ON PrivateQuestions
    FOR EACH ROW BEGIN
		
			INSERT INTO PrivateQuestionsToUsers (questionID, userID, choosedAnswerID)
				SELECT NEW.questionID, G.userID, null FROM GroupsToUsers G
					WHERE G.groupID = NEW.groupID;
		
END &
DELIMITER ;