USE APP;

-- CREATE USERS

INSERT INTO `app`.`users`
VALUES (1,'THISISAHASH1','THISISANOTHERHASH1','UNAME1',NOW(),0,'','DE');

INSERT INTO `app`.`users`
VALUES (2,'THISISAHASH2','THISISANOTHERHASH2','UNAME2',NOW(),0,'','DE');

INSERT INTO `app`.`users`
VALUES (3,'THISISAHASH3','THISISANOTHERHASH3','UNAME3',NOW(),4,'','DE');

INSERT INTO `app`.`users`
VALUES (4,'THISISAHASH4','THISISANOTHERHASH4','UNAME4',NOW(),5,'','DE');

INSERT INTO `app`.`users`
VALUES (5,'THISISAHASH5','THISISANOTHERHASH5','UNAME5',NOW(),7,'','DE');

INSERT INTO `app`.`users`
VALUES (6,'THISISAHASH6','THISISANOTHERHASH6','UNAME6',NOW(),100,'','DE');

-- CREATE PUBLIC QUESTIONS

INSERT INTO `app`.`publicquestions`
VALUES (1,'THISISAQUESTION','THIS IS ADD INFO',1,'',NOW(),NOW(),'DE',0,default);

INSERT INTO `app`.`publicquestions`
VALUES (2,'THISISAQUESTION','THIS IS ADD INFO',3,'',NOW(),NOW(),'DE',0,default);

INSERT INTO `app`.`publicquestions`
VALUES (3,'THISISAQUESTION','THIS IS ADD INFO',3,'',NOW(),NOW(),'DE',0,default);

-- CREATE ANSWERS PUBLIC QUESTION

INSERT INTO `app`.`answerspublicquestions`
VALUES (1,1,'THISISAANSWER');

INSERT INTO `app`.`answerspublicquestions`
VALUES (2,1,'THISISAANSWER');

INSERT INTO `app`.`answerspublicquestions`
VALUES (3,1,'THISISAANSWER');

INSERT INTO `app`.`answerspublicquestions`
VALUES (4,2,'THISISAANSWER');

INSERT INTO `app`.`answerspublicquestions`
VALUES (5,2,'THISISAANSWER');


-- CREATE PUBLIC QUESTIONS TO USERS

INSERT INTO `app`.`publicquestionstousers`
VALUES(1,1,2);

INSERT INTO `app`.`publicquestionstousers`
VALUES(1,2,3);

INSERT INTO `app`.`publicquestionstousers`
VALUES(2,3,1);

INSERT INTO `app`.`publicquestionstousers`
VALUES(2,4,1);

-- CREATE GROUPS
INSERT INTO `app`.`groups`
VALUES (1,NOW(),1,'THISISAGROUP','');

INSERT INTO `app`.`groups`
VALUES (2,NOW(),2,'THISISAGROUP','');

-- CREATE GROUPS TO USERS
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

-- CREATE PRIVATE QUESTIONS
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

-- CREATE ANSWER PRIVATE QUESTION
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