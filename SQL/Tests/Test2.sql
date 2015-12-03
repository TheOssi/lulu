-- Test for Trigger 'trigger_afterUpdateOnPrivateQuestionsToUsers_updateScores_checkFinished'

-- Fälle
	-- DOE = 1 -> nichts passiert
    -- DOE = 2 & allA -> finsih
    -- DOE = 2 !& allA -> nichts passiert
    -- DOE = 3 & SOUTA -> finish
    -- DOE = 3 !& SOUTA -> nichts passiert

USE APP;

-- Create Users
INSERT INTO `app`.`users`
VALUES (1,'THISISAHASH1','THISISANOTHERHASH1','UNAME1',NOW(),0,'','DE');

INSERT INTO `app`.`users`
VALUES (2,'THISISAHASH2','THISISANOTHERHASH2','UNAME2',NOW(),0,'','DE');

INSERT INTO `app`.`users`
VALUES (3,'THISISAHASH3','THISISANOTHERHASH3','UNAME3',NOW(),4,'','DE');

-- Create Groups
INSERT INTO `app`.`groups`
VALUES (1,NOW(),1,'THISISAGROUP','');

INSERT INTO `app`.`groups`
VALUES (2,NOW(),2,'THISISAGROUP','');

-- Create Groups To Users
INSERT INTO `app`.`groupstousers`
VALUES (1,1,default);

INSERT INTO `app`.`groupstousers`
VALUES (2,1,default);

INSERT INTO `app`.`groupstousers`
VALUES (1,2,default);

INSERT INTO `app`.`groupstousers`
VALUES (2,2,default);

INSERT INTO `app`.`groupstousers`
VALUES (1,3,default);

INSERT INTO `app`.`groupstousers`
VALUES (2,3,default);


-- Create Private Questions
INSERT INTO `app`.`privatequestions`
VALUES (1,'THISISAQUESTIOpublicquestionsprivatequestionsN','THISISAADDINFO',1,1,'',NOW(),NOW(),0,1,null,'DE',false,null,defprivatequestionsault);

INSERT INTO `app`.`privatequestions`
VALUES (2,'THISISAQUESTION','THISISAADDINFO',2,1,'',NOW(),NOW(),0,2,null,'DE',false,null,defprivatequestionsault);

INSERT INTO `app`.`privatequestions`
VALUES (3,'THISISAQUESTION','THISISAADDINFO',3,1,'',NOW(),NOW(),0,2,null,'DE',false,null,defprivatequestionsault);

INSERT INTO `app`.`privatequestions`
VALUES (4,'THISISAQUESTION','THISISAADDINFO',1,2,'',NOW(),NOW(),0,3privatequestions,3,'DE',false,null,defprivatequestionsault);

INSERT INTO `app`.`privatequestions`
VALUES (5,'THISISAQUESTION','THISISAADDINFO',1,2,'',NOW(),NOW(),0,3,null,'DE',false,2,defprivatequestionsault);

-- Create Answers Private Questions
INSERT INTO `app`.`answersprivatequestions`
VALUES(1,1,'THISISAANSWER');

INSERT INTO `app`.`answersprivatequestions`
VALUES(2,1,'THISISAANSWER');

INSERT INTO `app`.`answersprivatequestions`
VALUES(3,2,'THISISAANSWER');

INSERT INTO `app`.`answersprivatequestions`
VALUES(4,2,'THISISAANSWER');

-- Create Private Questions To Users
INSERT INTO `app`.`privatequestionstousers`
VALUES(1,1,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(2,1,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(3,1,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(4,1,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(5,1,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(1,2,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(2,2,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(3,2,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(4,2,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(5,2,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(1,3,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(2,3,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(3,3,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(4,3,null);

INSERT INTO `app`.`privatequestionstousers`
VALUES(5,3,null);



-- Update Private Questions To Users ( Answer to Bets )


-- Question 1,2,4 : not finished
-- Question 3,5 : finished

-- Scores
	-- User 1: GS-0 	G1-3x1-3 (Q1 & Q2 Q3)		G2-?x1-
    -- User 2: GS-0 	G1-3x1-3  (Q1 & Q2 & Q3)	G2-?x1-
    -- User 3: GS-0 	G1-2x1-2  (Q1 & Q3)			G2-?x1-

-- 