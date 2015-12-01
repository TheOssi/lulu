-- Test for Trigger 'trigger_afterUpdateOnPrivateQuestionsToUsers_updateScores_checkFinished'

USE APP;

-- Create Users
INSERT INTO `app`.`users`
VALUES (1,'THISISAHASH1','THISISANOTHERHASH1','UNAME1',NOW(),0,'','DE');

INSERT INTO `app`.`users`
VALUES (2,'THISISAHASH2','THISISANOTHERHASH2','UNAME2',NOW(),0,'','DE');

INSERT INTO `app`.`users`
VALUES (3,'THISISAHASH3','THISISANOTHERHASH3','UNAME3',NOW(),4,'','DE');

-- Create Groups


-- Create Private Questions
INSERT INTO `app`.`privatequestions`
VALUES
(1,
'THISISAQUESTION',
'THISISAADDINFO',
1,
2,
'',
NOW(),
NO,
<{optionExtension: }>,
<{definitionOfEnd: }>,
<{sumOfUsersToAnswer: }>,
<{language: }>,
<{isBet: }>,
<{selectedAnswerID: }>,
<{finished: 0}>);


-- Create Answers Private Questions

-- Create Private Questions To Users

-- Update Private Questions To Users ( Answer to Bets )

-- 