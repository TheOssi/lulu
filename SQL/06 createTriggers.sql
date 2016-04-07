USE APP;

-- ================================================================================
-- AFTER UPDATE ON PrivateQuestionsToUser
-- 		If the choosenAnswer is set, the scores will updated
-- 		Also if the condition for the end is reached, the question will marked as finished
-- ================================================================================
DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER tg_afterUpdate_PrivateQuestionsToUsers
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
		
		IF NEW.choosedAnswerID IS NOT NULL
		AND OLD.choosedAnswerID IS NULL THEN
		
			SELECT CAST( value AS UNSIGNED INTEGER ) FROM AppConstants 
				WHERE name = "POINTS_QUESTION_ANS_PRIVATE"
			INTO l_points;
			
            -- Update group scores, for OTQ this will not happen
			UPDATE APP.GroupsToUsers
				SET score = (score + l_points)
			WHERE groupID = ( SELECT groupID FROM PrivateQuestions WHERE questionID = NEW.questionID) AND
				userID = NEW.userID;
			
			-- Count all Users whitch have allready answerd the questionID
			SELECT Count(*) FROM APP.PrivateQuestionsToUsers 
				WHERE questionID = NEW.questionID AND 
					  choosedAnswerID <> 0
				INTO l_countAllAnsweredUsers;
			
			-- Count all Users of this question	
			SELECT Count(*) FROM APP.PrivateQuestionsToUsers 
				WHERE questionID = NEW.questionID INTO l_countAllUser;
                
			IF l_countAllUser = l_countAllAnsweredUsers THEN
				SET l_allUsersHaveAnswered = true;
			END IF;
				
			-- Select the definition of End
			SELECT definitionOfEnd FROM APP.PrivateQuestions WHERE questionID = NEW.questionID INTO l_definitionOfEnd;
				
			-- Select the sum of answered users if defOfEnd is 3 and set a Bool flag
			IF l_definitionOfEnd = 3 THEN
				SELECT sumOfUsersToAnswer FROM APP.PrivateQuestions WHERE questionID = NEW.questionID INTO l_sumOfToAnsweredUsers;
				IF l_sumOfToAnsweredUsers = l_countAllAnsweredUsers THEN
					SET l_sumOfAnsweredUsersReached = true;
				END IF;
			END IF;
				
			-- If this question finished after all users have answered (2) or all aswered(3), finish this question
			IF ( l_sumOfAnsweredUsersReached = true AND l_definitionOfEnd = 3 ) OR ( l_allUsersHaveAnswered = true AND l_definitionOfEnd = 2 ) THEN
				UPDATE APP.PrivateQuestions SET finished = 1 WHERE questionID = NEW.questionID;
			END IF;
			
		END IF;	
END \\
DELIMITER ;


-- ================================================================================
-- AFTER UPDATE ON PublicQuestionsToUsers
-- 		If a user participate a public question the scores will be updateded
-- ================================================================================
DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER tg_afterUpdate_PublicQuestionsToUser
	AFTER INSERT ON PublicQuestionsToUsers
    FOR EACH ROW BEGIN
		DECLARE l_hostID INT UNSIGNED;
		DECLARE l_pointsUser INT UNSIGNED;
		DECLARE l_pointsHost INT UNSIGNED;
		
			SELECT CAST( value AS UNSIGNED INTEGER ) FROM AppConstants 
				WHERE name = "POINTS_QUESTION_ANS_PUBLIC"
			INTO l_pointsUser;
			
			SELECT CAST( value AS UNSIGNED INTEGER ) FROM AppConstants 
				WHERE name = "POINTS_QUESTION_ANS_HOST_PUBLIC"
			INTO l_pointsHost;
            
			-- Update GlobalScore of user
			UPDATE APP.Users
				SET scoreOfGlobal = (scoreOfGlobal + l_pointsUser )
			WHERE userID = NEW.userID;
			
			-- SELECT hostID
			SELECT hostID FROM PublicQuestions
				WHERE questionID = NEW.questionID
			INTO l_hostID;
           
			-- Update global score of host
			UPDATE APP.Users
				SET scoreOfGlobal = (scoreOfGlobal + l_pointsHost ) 
			WHERE userID = l_hostID;		
END \\
DELIMITER ;


-- ================================================================================
-- AFTER INSERT ON PublicQuestions
-- If somebody creates a new public question, the score will be increased
-- ================================================================================
DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER tg_afterInsert_PublicQuestions
	AFTER INSERT ON PublicQuestions
    FOR EACH ROW BEGIN
	DECLARE l_points INT UNSIGNED;
	
		SELECT CAST( value AS UNSIGNED INTEGER ) FROM AppConstants 
			WHERE name = "POINTS_QUESTION_PUPLIC"
		INTO l_points;
           
		-- Update group scores
		UPDATE APP.Users
			SET scoreOfGlobal = (scoreOfGlobal + l_points )
		WHERE  userID = NEW.hostID;

END \\
DELIMITER ;


-- ================================================================================
-- AFTER UPDATE ON PrivateQuestions
--		If the selectedAnswer is set and this is a bet, this trigger update the 
--			score of all users, which selected this answer
-- ================================================================================
DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER tg_afterInsert_PrivateQuestions
	AFTER UPDATE ON PrivateQuestions
    FOR EACH ROW BEGIN
	DECLARE l_points INT UNSIGNED;
	
        IF NEW.selectedAnswerID IS NOT NULL 
        AND NEW.isBet IS TRUE THEN
		
			SELECT CAST( value AS UNSIGNED INTEGER ) FROM AppConstants 
				WHERE name = "POINTS_BET_RIGHT_ANSWER_PRIVATE"
			INTO l_points;
			
			-- Update group scores
			IF NEW.groupID IS NOT NULL THEN
			
				UPDATE APP.GroupsToUsers G
					INNER JOIN APP.PrivateQuestionsToUsers P ON
						choosedAnswerID = NEW.selectedAnswerID
	                    AND questionID = NEW.questionID
					SET score = (score + l_points)
				WHERE G.groupID = NEW.groupID AND
					  G.userID = P.userID;
					  
			END IF;           
		END IF;
END \\
DELIMITER ;