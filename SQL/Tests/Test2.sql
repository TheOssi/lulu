-- Test for Trigger 'trigger_afterUpdateOnPrivateQuestionsToUsers_updateScores_checkFinished'

USE APP;

-- Create Users
INSERT INTO `app`.`users`
VALUES (1,'THISISAHASH1','THISISANOTHERHASH1','UNAME1',NOW(),0,'','DE');

INSERT INTO `app`.`users`
VALUES (2,'THISISAHASH2','THISISANOTHERHASH2','UNAME2',NOW(),0,'','DE');

INSERT INTO `app`.`users`
VALUES (3,'THISISAHASH3','THISISANOTHERHASH3','UNAME3',NOW(),0,'','DE');

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
VALUES (1,2,3);

INSERT INTO `app`.`groupstousers`
VALUES (2,2,default);

INSERT INTO `app`.`groupstousers`
VALUES (1,3,default);

INSERT INTO `app`.`groupstousers`
VALUES (2,3,7);


-- Create Private Questions
INSERT INTO `app`.`privatequestions`
VALUES (1,'THISISAQUESTION','THISISAADDINFO',1,1,'',NOW(),NOW(),0,1,null,'DE',true,null,false);

INSERT INTO `app`.`privatequestions`
VALUES (2,'THISISAQUESTION','THISISAADDINFO',2,1,'',NOW(),NOW(),0,2,null,'DE',true,null,false);

INSERT INTO `app`.`privatequestions`
VALUES (3,'THISISAQUESTION','THISISAADDINFO',3,1,'',NOW(),NOW(),0,2,null,'DE',false,null,false);

INSERT INTO `app`.`privatequestions`
VALUES (4,'THISISAQUESTION','THISISAADDINFO',1,2,'',NOW(),NOW(),0,3,2,'DE',true,null,false);

INSERT INTO `app`.`privatequestions`
VALUES (5,'THISISAQUESTION','THISISAADDINFO',1,2,'',NOW(),NOW(),0,3,2,'DE',true,null,false);

-- Create Answers Private Questions
INSERT INTO `app`.`answersprivatequestions`
VALUES(1,1,'THISISAANSWER');
INSERT INTO `app`.`answersprivatequestions`
VALUES(6,2,'THISISAANSWER');

INSERT INTO `app`.`answersprivatequestions`
VALUES(2,2,'THISISAANSWER');

INSERT INTO `app`.`answersprivatequestions`
VALUES(3,3,'THISISAANSWER');

INSERT INTO `app`.`answersprivatequestions`
VALUES(4,4,'THISISAANSWER');

INSERT INTO `app`.`answersprivatequestions`
VALUES(5,5,'THISISAANSWER');

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
-- q1 
UPDATE app.privatequestionstousers SET choosedAnswerID = 1 WHERE questionID = 1 AND userID = 1;
UPDATE app.privatequestionstousers SET choosedAnswerID = 1 WHERE questionID = 1 AND userID = 2;
UPDATE app.privatequestionstousers SET choosedAnswerID = 1 WHERE questionID = 1 AND userID = 3;
-- q2 
UPDATE app.privatequestionstousers SET choosedAnswerID = 2 WHERE questionID = 2 AND userID = 1;
UPDATE app.privatequestionstousers SET choosedAnswerID = 2 WHERE questionID = 2 AND userID = 2;
-- q3
UPDATE app.privatequestionstousers SET choosedAnswerID = 3 WHERE questionID = 3 AND userID = 1;
UPDATE app.privatequestionstousers SET choosedAnswerID = 3 WHERE questionID = 3 AND userID = 2;
UPDATE app.privatequestionstousers SET choosedAnswerID = 3 WHERE questionID = 3 AND userID = 3;
-- q4
UPDATE app.privatequestionstousers SET choosedAnswerID = 4 WHERE questionID = 4 AND userID = 1;
-- q5
UPDATE app.privatequestionstousers SET choosedAnswerID = 5 WHERE questionID = 5 AND userID = 1;
UPDATE app.privatequestionstousers SET choosedAnswerID = 5 WHERE questionID = 5 AND userID = 2;


-- Question 1,2,4 : not finished
-- Question 3,5 : finished

-- Scores
	-- User 1: GS-0 	G1-3	G2-2
    -- User 2: GS-0 	G1-6	G2-1
    -- User 3: GS-0 	G1-2  	G2-7
    
UPDATE privatequestions SET selectedAnswerID = 1 WHERE questionID = 1;
UPDATE privatequestions SET selectedAnswerID = 6 WHERE questionID = 2;
UPDATE privatequestions SET selectedAnswerID = 5 WHERE questionID = 5;

-- Scores
	-- User 1: 		GS-0 	G1-8	G2-7
    -- User 2: 		GS-0 	G1-11	G2-6
    -- User 3: 		GS-0 	G1-7  	G2-7

    
