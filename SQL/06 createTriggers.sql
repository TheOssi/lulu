USE APP;

-- Trigger AFTER UPDATE ON PrivateQuestionsToUser
-- If the choosenAnswer is set, the scores will updated
-- Also if the confition for the end is reached, the question will marked as finished
DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER trigger_aUOnPrivateQuestionsToUsers_updateScores_checkFinished
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
			IF NEW.groupID IS NOT NULL THEN
				UPDATE APP.GroupsToUsers
					SET score = (score + l_points)
				WHERE groupID = ( SELECT groupID FROM PrivateQuestions WHERE questionID = NEW.questionID) AND
						userID = NEW.userID;
			END IF;
			
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

-- Trigger AFTER UPDATE ON PublicQuestionsToUsers
-- If a user participate a public question the scores will be updateded
DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER trigger_aUPublicQuestionsToUser_updateScores
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

-- Trigger AFTER INSERT ON PublicQuestions
-- If somebody creates a new public question, the score will be increased
DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER trigger_afterInsertOnPublicQuestions_updateScores
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

DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost'
	TRIGGER tg_aInsert_PrivateQuestions_updateScoresForRightAnswer_not
	AFTER UPDATE ON PrivateQuestions
    FOR EACH ROW BEGIN
	DECLARE l_points INT UNSIGNED;
    DECLARE l_code_lost VARCHAR(4);
	DECLARE l_code_win VARCHAR(4);
    DECLARE l_code VARCHAR(4);
    DECLARE l_notificationID INT UNSIGNED;
    
	
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
			
            -- SELECT code for notification
			SELECT value FROM APP.AppConstants 
				WHERE name = "NOT_BET_WIN"
			INTO l_code_win;
            SELECT value FROM APP.AppConstants 
				WHERE name = "NOT_BET_LOST"
			INTO l_code_lost;
    
			-- INSERT notification for win
			INSERT INTO APP.notifications VALUES (default, l_code_win);
    
			-- Get notification ID for win
			SELECT MAX(notificationID) FROM APP.notifications INTO l_notificationID;
    
			-- Connect notification and user
			INSERT INTO APP.notificationstousers 
				SELECT P.userID,l_notificationID FROM APP.PrivateQuestionsToUsers P
					WHERE choosedAnswerID = NEW.selectedAnswerID
                    AND questionID = NEW.questionID;
            
			-- Add parameter for win
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'questionID',NEW.questionID);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'question',NEW.question);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupID',NEW.groupID);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupname',( SELECT groupname FROM groups WHERE groupID = NEW.groupID ));
            
            -- INSERT notification for lost
			INSERT INTO APP.notifications VALUES (default, l_code_lost);
    
			-- Get notification ID for lost
			SELECT MAX(notificationID) FROM APP.notifications INTO l_notificationID;
    
			-- Connect notification and user for lost
			INSERT INTO APP.notificationstousers 
				SELECT P.userID,l_notificationID FROM APP.PrivateQuestionsToUsers P
					WHERE choosedAnswerID != NEW.selectedAnswerID
                    AND questionID = NEW.questionID;
            
			-- Add parameter for lost
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'questionID',NEW.questionID);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'question',NEW.question);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupID',NEW.groupID);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupname',( SELECT groupname FROM groups WHERE groupID = NEW.groupID ));
            
            -- richtige Antort setzten
		END IF;
        
        IF NEW.finished IS TRUE AND
        OLD.finished IS AND FALSE 
        NEW.isBet IS FALSE THEN
        
			-- SELECT code for notification that question is finished
			SELECT value FROM APP.AppConstants 
				WHERE name = "NOT_QUESTION_END"
			INTO l_code;
            
			-- INSERT notification
			INSERT INTO APP.notifications VALUES (default, l_code_lost);
    
			-- Get notification ID
			SELECT MAX(notificationID) FROM APP.notifications INTO l_notificationID;
    
			-- Connect notification and user
			INSERT INTO APP.notificationstousers 
				SELECT P.userID,l_notificationID FROM APP.PrivateQuestionsToUsers P
					WHERE choosedAnswerID != NEW.selectedAnswerID
                    AND questionID = NEW.questionID;
            
			-- Add parameter
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'questionID',NEW.questionID);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'question',NEW.question);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupID',NEW.groupID);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupname',( SELECT groupname FROM groups WHERE groupID = NEW.groupID ));
        
        END IF;
        
        IF NEW.finished IS TRUE AND
        OLD.finished IS AND FALSE 
        NEW.isBet IS TRUE THEN
        
        -- SELECT code for notification that question is finished
			SELECT value FROM APP.AppConstants 
				WHERE name = "NOT_SET_ANSWER"
			INTO l_code;
            
			-- INSERT notification
			INSERT INTO APP.notifications VALUES (default, l_code_lost);
    
			-- Get notification ID
			SELECT MAX(notificationID) FROM APP.notifications INTO l_notificationID;
    
			-- Connect notification and user
			INSERT INTO APP.notificationstousers VALUES ( NEW.hostID, l_notificationID);
            
			-- Add parameter
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'questionID',NEW.questionID);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'question',NEW.question);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupID',NEW.groupID);
			INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupname',( SELECT groupname FROM groups WHERE groupID = NEW.groupID ));
        END IF;
END \\
DELIMITER ;

DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost' 
	TRIGGER tg_aInsertGroupsToUsers_not
    AFTER INSERT ON GroupsToUsers
    FOR EACH ROW BEGIN
    DECLARE l_code VARCHAR(4);
    DECLARE l_notificationID INT UNSIGNED;
    
	SELECT value FROM AppConstants 
		WHERE name = "NOT_ADD_TO_GROUP"
	INTO l_code;
    
    -- INSERT notification
    INSERT INTO APP.notifications VALUES (default, l_code);
    
    -- Get notification ID
    SELECT MAX(questionID) FROM APP.privatequestions INTO l_notificationID;
    
    -- Connect notification and user
    INSERT INTO APP.notificationstousers VALUES (NEW.userID,l_notificationID);
    
    -- Add parametrs
    INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupID',NEW.groupID);
	INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupname',( SELECT groupname FROM groups WHERE groupID = NEW.groupID ));
END \\
DELIMITER ;


DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost' 
	TRIGGER tg_aInsertMessages_not
    AFTER INSERT ON Messages
    FOR EACH ROW BEGIN
	DECLARE l_code VARCHAR(4);
    DECLARE l_notificationID INT UNSIGNED;
    
    SELECT value FROM APP.AppConstants 
		WHERE name = "NOT_NEW_MESS"
	INTO l_code;
    
    -- INSERT notification
    INSERT INTO APP.notifications VALUES (default, l_code);
    
    -- Get notification ID
    SELECT MAX(notificationID) FROM APP.notifications INTO l_notificationID;
    
	-- Connect notification and user
    INSERT INTO APP.notificationstousers SELECT G.userID,l_notificationID FROM APP.groupstousers G WHERE groupID = NEW.groupID;
    
    -- Add parameter
	INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupID',NEW.groupID);
    INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupname',( SELECT groupname FROM groups WHERE groupID = NEW.groupID ));

END \\
DELIMITER ;

DELIMITER \\
CREATE DEFINER = 'appAdmin'@'localhost' 
	TRIGGER tg_aInsertPrivateQuestions_not
    AFTER INSERT ON PrivateQuestions
    FOR EACH ROW BEGIN
	DECLARE l_code VARCHAR(4);
    DECLARE l_notificationID INT UNSIGNED;
	    
	 -- INSERT notification
    INSERT INTO APP.notifications VALUES (default, l_code);
    
    -- Get notification ID
    SELECT MAX(notificationID) FROM APP.notifications INTO l_notificationID;
    
	-- Connect notification and user
    INSERT INTO APP.notificationstousers SELECT G.userID,l_notificationID FROM APP.groupstousers G WHERE groupID = NEW.groupID;
    
    -- Add parameter
	INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupID',NEW.groupID);
    INSERT INTO APP.notificationsparameters VALUES (default,l_notificationID,'groupname',( SELECT groupname FROM groups WHERE groupID = NEW.groupID ));    
	    
	    
    END \\
DELIMITER ;