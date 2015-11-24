-- Trigger On Update on the answer table
	-- this Trigger performs a test if all users have answerd a question; if this is true, the allAnswered falg will be set.
	-- Also if the definitionOfEnd is 2 or 3, the bet will be flaged as finished
DELIMITER \\
CREATE DEFINER = 'betAppAdmin'@'localhost'
	TRIGGER tg_aUpdate_QuestionsToUsers_updateScores_checkAllAnswered_checkFinished
	AFTER UPDATE ON questionsToUsers
    FOR EACH ROW BEGIN
        DECLARE l_definitionOfEnd TINYINT unsigned;
        DECLARE l_countAllUser INT unsigned;
        DECLARE l_countAllAnsweredUsers INT unsigned;
		DECLARE l_sumOfToAnsweredUsers INT unsigned;
		DECLARE l_sumOfAnsweredUsersReached BOOL DEFAULT false;
		DECLARE l_allUsersHaveAnswered BOOL DEFAULT false;
		DECLARE l_threshold INT UNSIGNED;
		DECLARE	l_countOfUser INT UNSIGNED;
		DECLARE	l_thresholdCalculated INT UNSIGNED;
		DECLARE l_quantityOfDubed INT UNSIGNED;
		
		IF NEW.choosedAnswerID <> NULL 
		AND OLD.choosedAnswerID = NULL THEN
			
			-- Update global Score
			UPDATE APP.Users 
				SET globaleScore = ( globaleScore + 1 )
			WHERE userID = NEW.userID;
            
			-- Update Betgroup scores
			UPDATE APP.GroupsToUsers
				SET score = (score + 1)
			WHERE groupID = (SELECT groupID FROM APP.Questions WHERE questionID = NEW.questionID) AND
					userID = NEW.userID;
			
			-- Count all Users whitch have allready answerd the questionID
			SELECT Count(*) FROM APP.QuestionsToUsers 
				WHERE questionID = NEW.questionID AND 
					  choosedAnswerID <> 0
				INTO l_countAllAnsweredUsers;
			
			-- Count all Users of this bet	
			SELECT Count(*) FROM APP.QuestionsToUsers 
				WHERE questionID = NEW.questionID INTO l_countAllUser;
				
			-- Select the definition of End
			SELECT definitionOfEnd FROM APP.Questions WHERE questionID = NEW.questionID INTO l_definitionOfEnd;
				
			-- Select the sum of answered users if defOfEnd is 3 and set a Bool flag
			IF l_definitionOfEnd = 3 THEN
				SELECT sumOfUsersToAnswer FROM APP.Questions WHERE questionID = NEW.questionID INTO l_sumOfToAnsweredUsers;
				IF l_sumOfToAnsweredUsers = l_countAllAnsweredUsers THEN
					SET l_sumOfAnsweredUsersReached = true;
				END IF;
			END IF;
			
			-- If All Users have answerd, set this bet to all answered
			IF l_countAllUser = l_countAllAnsweredUsers THEN
				UPDATE APP.Questions SET allAnswered = 1 WHERE questionID = NEW.questionID;
				SET l_allUsersHaveAnswered = true;
			END IF;
				
			-- If this bet finished after all users have answered (2) or after a special time OR all aswered(3), finish this bet
			IF ( l_sumOfAnsweredUsersReached = true AND l_definitionOfEnd = 3 ) OR ( l_allUsersHaveAnswered = true AND l_definitionOfEnd = 2 ) THEN
				UPDATE APP.Questions SET finished = 1 WHERE questionID = NEW.questionID;
			END IF;
			
		END IF;	
END \\
DELIMITER ;

DELIMITER \\
CREATE DEFINER = 'betAppAdmin'@'localhost'
	TRIGGER tg_aInsert_Questions_updateScores
	AFTER INSERT ON Questions
    FOR EACH ROW BEGIN
		
		-- Update global Score
		UPDATE APP.Users 
			SET globaleScore = ( globaleScore + 1 )
		WHERE userID = NEW.hostID;
           
		-- Update Betgroup scores
		UPDATE APP.GroupsToUsers
			SET score = (score + 1)
		WHERE groupID = NEW.groupID AND
			  userID = NEW.userID;

END \\




