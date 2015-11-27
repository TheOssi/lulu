USE APP;

DELIMITER \\
CREATE DEFINER = 'betAppAdmin'@'localhost'
	TRIGGER tg_aUpdate_PrivateQuestionsToUsers_updateScores_checkFinished
	AFTER UPDATE ON PrivateQuestionsToUsers
    FOR EACH ROW BEGIN
        DECLARE l_definitionOfEnd TINYINT unsigned;
        DECLARE l_countAllUser INT unsigned;
        DECLARE l_countAllAnsweredUsers INT unsigned;
		DECLARE l_sumOfToAnsweredUsers INT unsigned;
		DECLARE l_sumOfAnsweredUsersReached BOOL DEFAULT false;
		DECLARE l_allUsersHaveAnswered BOOL DEFAULT false;
		DECLARE l_threshold INT UNSIGNED;
		DECLARE	l_countOfUser INT UNSIGNED;
		DECLARE l_hostID INT UNSIGNED;
		DECLARE l_points INT UNSIGNED;
		
		IF NEW.choosedAnswerID <> NULL 
		AND OLD.choosedAnswerID = NULL THEN
		
			SELECT CAST( name AS UNSIGNED INTEGER ) FROM BetAppConstants 
				WHERE name = "POINTS_QUESTION_ANS_PRIVATE"
			INTO l_points;
            
			-- Update Betgroup scores
			UPDATE APP.GroupsToUsers
				SET score = (score + l_points1)
			WHERE groupID = ( SELECT groupID FROM PrivateQuestions WHERE questionID = NEW.questionID) AND
					userID = NEW.userID;
			
			-- Count all Users whitch have allready answerd the questionID
			SELECT Count(*) FROM APP.PrivateQuestionsToUsers 
				WHERE questionID = NEW.questionID AND 
					  choosedAnswerID <> 0
				INTO l_countAllAnsweredUsers;
			
			-- Count all Users of this bet	
			SELECT Count(*) FROM APP.PrivateQuestionsToUsers 
				WHERE questionID = NEW.questionID INTO l_countAllUser;
				
			-- Select the definition of End
			SELECT definitionOfEnd FROM APP.PrivateQuestions WHERE questionID = NEW.questionID INTO l_definitionOfEnd;
				
			-- Select the sum of answered users if defOfEnd is 3 and set a Bool flag
			IF l_definitionOfEnd = 3 THEN
				SELECT sumOfUsersToAnswer FROM APP.Questions WHERE questionID = NEW.questionID INTO l_sumOfToAnsweredUsers;
				IF l_sumOfToAnsweredUsers = l_countAllAnsweredUsers THEN
					SET l_sumOfAnsweredUsersReached = true;
				END IF;
			END IF;
				
			-- If this bet finished after all users have answered (2) or all aswered(3), finish this bet
			IF ( l_sumOfAnsweredUsersReached = true AND l_definitionOfEnd = 3 ) OR ( l_allUsersHaveAnswered = true AND l_definitionOfEnd = 2 ) THEN
				UPDATE APP.PrivateQuestions SET finished = 1 WHERE questionID = NEW.questionID;
			END IF;
			
		END IF;	
END \\
DELIMITER ;

DELIMITER \\
CREATE DEFINER = 'betAppAdmin'@'localhost'
	TRIGGER tg_aUpdate_PublicQuestionsToUsers_updateScores
	AFTER UPDATE ON PublicQuestionsToUsers
    FOR EACH ROW BEGIN
		DECLARE l_hostID INT UNSIGNED;
		DECLARE l_pointsUser INT UNSIGNED;
		DECLARE l_pointsHost INT UNSIGNED;

		
		IF NEW.choosedAnswerID <> NULL 
		AND OLD.choosedAnswerID = NULL THEN
		
			SELECT CAST( name AS UNSIGNED INTEGER ) FROM BetAppConstants 
				WHERE name = "POINTS_QUESTION_ANS_PUBLIC"
			INTO l_pointsUser;
			
			SELECT CAST( name AS UNSIGNED INTEGER ) FROM BetAppConstants 
				WHERE name = "POINTS_QUESTION_ANS_HOST_PUBLIC"
			INTO l_pointsHost;
            
			-- Update GlobalScore of user
			UPDATE APP.Users
				SET scoreOfGlobal = (scoreOfGlobal + l_pointsUser )
			WHERE userID = NEW.userID;
			
			-- SELECT hostID
			SELECT hostID FROM PublicQuestions
				WHERE betID = NEW.questionID
			INTO l_hostID;
           
			-- Update global score of host
			UPDATE APP.User
				SET score = (score + l_pointsHost ) 
			WHERE userID = l_hostID;
			
		END IF;	
END \\
DELIMITER ;

DELIMITER \\
CREATE DEFINER = 'betAppAdmin'@'localhost'
	TRIGGER tg_aInsert_PublicQuestions_updateScores
	AFTER INSERT ON PublicQuestions
    FOR EACH ROW BEGIN
	DECLARE l_points INT UNSIGNED;
	
		SELECT CAST( name AS UNSIGNED INTEGER ) FROM BetAppConstants 
			WHERE name = "POINTS_QUESTION_PUPLIC"
		INTO l_points;
           
		-- Update Betgroup scores
		UPDATE APP.Users
			SET scoreOfGlobal = (scoreOfGlobal + l_points )
		WHERE  userID = NEW.hostID;

END \\
DELIMITER ;

DELIMITER \\
CREATE DEFINER = 'betAppAdmin'@'localhost'
	TRIGGER tg_aInsert_PrivateQuestions_updateScoresForRightAnswer
	AFTER UPDATE ON PrivateQuestions
    FOR EACH ROW BEGIN
	DECLARE l_points INT UNSIGNED;
	
        IF NEW.selectedAnswerID <> NULL THEN
		
			SELECT CAST( name AS UNSIGNED INTEGER ) FROM BetAppConstants 
				WHERE name = "POINTS_QUESTION_RIGHT_ANSWER_PRIVATE"
			INTO l_points;
			
			-- Update Betgroup scores
			UPDATE APP.GroupsToUsers
				SET score = (score + l_points)
			WHERE groupID = NEW.groupID AND
				  userID = 
					( SELECT userID FROM AnswersPrivateQuestions WHERE answerID = NEW.selectedAnswerID);
			  
		END IF;

END \\
DELIMITER ;




