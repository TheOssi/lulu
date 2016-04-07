USE APP;

ALTER TABLE PrivateQuestions
	ADD CONSTRAINT fk_PrivateQuestions_groupID
		FOREIGN KEY ( groupID ) REFERENCES Groups( groupID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_PrivateQuestions_hostID
		FOREIGN KEY ( hostID ) REFERENCES Users( userID )
			ON DELETE SET NULL
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_PrivateQuestions_selectedAnswerID
		FOREIGN KEY ( selectedAnswerID ) REFERENCES AnswersPrivateQuestions ( answerID )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION;
			
ALTER TABLE AnswersPrivateQuestions
	ADD CONSTRAINT fk_AnswersPrivateQuestions_questionID
		FOREIGN KEY ( questionID ) REFERENCES PrivateQuestions ( questionID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;
			
ALTER TABLE PrivateQuestionsToUsers
	ADD CONSTRAINT fk_PrivateQuestionsToUsers_questionID
		FOREIGN KEY ( questionID ) REFERENCES PrivateQuestions( questionID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_PrivateQuestionsToUsers_userID
		FOREIGN KEY ( userID ) REFERENCES Users( userID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_PrivateQuestionsToUsers_choosedAnswerID
		FOREIGN KEY ( choosedAnswerID ) REFERENCES AnswersPrivateQuestions( answerID )
			ON DELETE SET NULL
			ON UPDATE NO ACTION;
			
ALTER TABLE Contacts
	ADD CONSTRAINT fk_Contacs_userID
		FOREIGN KEY ( userID ) REFERENCES Users ( userID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_Contacs_contactID
		FOREIGN KEY ( userID ) REFERENCES Users ( userID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;
			
ALTER TABLE Messages
	ADD CONSTRAINT fk_Messages_groupID
		FOREIGN KEY ( groupID ) REFERENCES Groups( groupID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_Messages_userID
		FOREIGN KEY ( userID ) REFERENCES Users( userID )
			ON DELETE SET NULL
			ON UPDATE NO ACTION;

ALTER TABLE Groups
	ADD CONSTRAINT fk_Groups_adminID
		FOREIGN KEY ( adminID ) REFERENCES Users ( userID )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION;
	
ALTER TABLE GroupsToUsers
	ADD CONSTRAINT fk_GroupsToUsers_groupID
		FOREIGN KEY ( groupID ) REFERENCES Groups ( groupID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_GroupsToUsers_userID
		FOREIGN KEY ( userID ) REFERENCES Users ( userID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;

-- Users no foreign Key						  		
			
ALTER TABLE PublicQuestions
	ADD CONSTRAINT fk_PublicQuestions_hostID
		FOREIGN KEY ( hostID ) REFERENCES Users( userID )
			ON DELETE SET NULL
			ON UPDATE NO ACTION;
			
ALTER TABLE AnswersPublicQuestions
	ADD CONSTRAINT fk_AnswersPublicQuestions_questionID
		FOREIGN KEY ( questionID ) REFERENCES PublicQuestions ( questionID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;
			
ALTER TABLE PublicQuestionsToUsers
	ADD CONSTRAINT fk_PublicQuestionsToUsers_questionID
		FOREIGN KEY ( questionID ) REFERENCES PublicQuestions( questionID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	ADD CONSTRAINT fk_PublicQuestionsToUsers_userID
		FOREIGN KEY ( userID ) REFERENCES Users( userID )
			ON DELETE CASCADE
			ON UPDATE NO ACTION;