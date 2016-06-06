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

    
UPDATE privatequestions SET selectedAnswerID = 1 WHERE questionID = 1;
UPDATE privatequestions SET selectedAnswerID = 6 WHERE questionID = 2;
UPDATE privatequestions SET selectedAnswerID = 5 WHERE questionID = 5;  