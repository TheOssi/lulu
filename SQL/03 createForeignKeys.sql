USE APP;

ALTER TABLE Questions
	ADD CONSTRAINT fk_questions_groupID
		FOREIGN KEY ( groupID ) REFERENCES Groups( groupID )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_questions_hostID
		FOREIGN KEY ( hostID ) REFERENCES Users( userID )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_questions_rightAnswerID
		FOREIGN KEY ( rightAnswerID ) REFERENCES Answers ( answerID )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION;


ALTER TABLE Groups
	ADD CONSTRAINT fk_Groups_adminID
		FOREIGN KEY ( adminID ) REFERENCES Users ( userID )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION;
	

ALTER TABLE Answers
	ADD CONSTRAINT fk_Answers_questionID
		FOREIGN KEY ( questionID ) REFERENCES Questions ( questionID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;
			
			
ALTER TABLE Messages
	ADD CONSTRAINT fk_Messages_groupID
		FOREIGN KEY ( groupID ) REFERENCES Groups( groupID )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_Messages_userID
		FOREIGN KEY ( userID ) REFERENCES Users( userID )
			ON DELETE SET NULL
			ON UPDATE NO ACTION;
	


ALTER TABLE questionsToUsers
	ADD CONSTRAINT fk_questionsToUsers_questionID
		FOREIGN KEY ( questionID ) REFERENCES Questions( questionID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_questionsToUsers_userID
		FOREIGN KEY ( userID ) REFERENCES Users( userID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_questionsToUsers_choosedAnswerID
		FOREIGN KEY ( choosedAnswerID ) REFERENCES Answers( answerID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;



ALTER TABLE GroupsToUsers
	ADD CONSTRAINT fk_GroupsToUsers_groupID
		FOREIGN KEY ( groupID ) REFERENCES Groups ( groupID )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_GroupsToUsers_userID
		FOREIGN KEY ( userID ) REFERENCES Users ( userID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;
								  
			
ALTER TABLE NotificationsToUsers
	ADD CONSTRAINT fk_NotificationsToUsers_userID
		FOREIGN KEY ( userID ) REFERENCES Users ( userID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_NotificationsToUsers_notificationID
		FOREIGN KEY ( notificationID ) REFERENCES Notifications ( notificationID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;
									  
									  
ALTER TABLE NotificationsParameters
	ADD CONSTRAINT fk_NotificatiosnParameters_notificationID
		FOREIGN KEY ( notificationID ) REFERENCES Notifications ( notificationID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;