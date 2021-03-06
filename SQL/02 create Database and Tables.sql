CREATE DATABASE APP;
USE APP;

CREATE TABLE PrivateQuestions (
	questionID INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT,
    question VARCHAR(150) NOT NULL,
    additionalInformation VARCHAR(250),
    hostID MEDIUMINT UNSIGNED,
    groupID MEDIUMINT UNSIGNED,
    pictureURI VARCHAR(100),
	createDate DATETIME NOT NULL,
    endDate DATETIME,
    optionExtension BOOL NOT NULL,
    definitionOfEnd TINYINT UNSIGNED NOT NULL,
	sumOfUsersToAnswer SMALLINT UNSIGNED,
	language VARCHAR(2) NOT NULL,
	isBet BOOL NOT NULL,
	selectedAnswerID INT UNSIGNED,
    finished BOOL NOT NULL DEFAULT 0,
    PRIMARY KEY ( questionID )
);                 

CREATE TABLE AnswersPrivateQuestions (
	answerID INT UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT,
	questionID INT UNSIGNED,
	answer VARCHAR(100) NOT NULL,
	PRIMARY KEY ( answerID )
);

CREATE TABLE PrivateQuestionsToUsers ( 
	questionID INT UNSIGNED NOT NULL,
	userID MEDIUMINT UNSIGNED NOT NULL,
	choosedAnswerID INT UNSIGNED,
	PRIMARY KEY ( questionID, userID )
);

CREATE TABLE Contacts (
	userID MEDIUMINT UNSIGNED NOT NULL,
	contactID MEDIUMINT UNSIGNED NOT NULL,
	PRIMARY KEY ( userID, contactID )
);

CREATE TABLE Messages ( 
	messageID MEDIUMINT	UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT,
	groupID MEDIUMINT UNSIGNED NOT NULL,
	userID MEDIUMINT UNSIGNED,
	message VARCHAR(100) NOT NULL,
	date DATETIME NOT NULL,
	PRIMARY KEY ( messageID )
);

CREATE TABLE Groups ( 
	groupID MEDIUMINT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT,
	createDate DATETIME NOT NULL,
	adminID MEDIUMINT UNSIGNED NOT NULL,
	groupname VARCHAR(30)  NOT NULL,
	groupPictureURI VARCHAR(100),
	PRIMARY KEY ( groupID )
);

CREATE TABLE GroupsToUsers ( 
	groupID MEDIUMINT UNSIGNED NOT NULL,
	userID MEDIUMINT UNSIGNED NOT NULL,
	score SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY ( groupID, userID )
);
                        
CREATE TABLE Users ( 
	userID 	MEDIUMINT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT,
	passwordHash VARCHAR(100),
	email VARCHAR(100),
	phoneNumberHash VARCHAR(100) UNIQUE,
	username VARCHAR(25) NOT NULL UNIQUE,
	accessionDate DATETIME NOT NULL,
	scoreOfGlobal SMALLINT UNSIGNED NOT NULL DEFAULT 0,
	profilePictureURI VARCHAR(100),
	language VARCHAR(2) NOT NULL,
	PRIMARY KEY ( userID )
);				

CREATE TABLE PublicQuestions (
	questionID INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT,
    question VARCHAR(150) NOT NULL,
    additionalInformation VARCHAR(250),
    hostID MEDIUMINT UNSIGNED,
    pictureURI VARCHAR(100),
	createDate DATETIME NOT NULL,
    endDate DATETIME,
    language VARCHAR(2) NOT NULL,
    optionExtension BOOL NOT NULL,
    finished BOOL NOT NULL DEFAULT 0,
    PRIMARY KEY ( questionID )
);                 

CREATE TABLE AnswersPublicQuestions (
	answerID INT UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT,
	questionID INT UNSIGNED NOT NULL,
	answer VARCHAR(100) NOT NULL,
	PRIMARY KEY ( answerID )
);

CREATE TABLE PublicQuestionsToUsers ( 
	questionID INT UNSIGNED NOT NULL,
	userID MEDIUMINT UNSIGNED NOT NULL,
	choosedAnswerID INT UNSIGNED NOT NULL,
	PRIMARY KEY ( questionID, userID )
);

CREATE TABLE AppConstants (
	name VARCHAR(50) NOT NULL UNIQUE,
	value VARCHAR(100) NOT NULL,
	datatype VARCHAR(20) NOT NULL,
	PRIMARY KEY ( name )
);									  