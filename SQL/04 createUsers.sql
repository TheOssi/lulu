CREATE USER 'betAppAdmin'@'localhost' IDENTIFIED by 'muellimuellmuell';
GRANT ALL PRIVILEGES ON APP.* TO 'betAppAdmin'@'localhost';

CREATE USER 'betAppReader'@'localhost' IDENTIFIED BY 'marciMarcMarc';
GRANT SELECT ON APP.* TO 'betAppReader'@'localhost';

CREATE USER 'betAppWriter'@'localhost' IDENTIFIED BY 'felliFellFell';
GRANT INSERT,UPDATE ON APP.* TO 'betAppWriter'@'localhost';

CREATE USER 'betAppDeleter'@'localhost' IDENTIFIED BY 'lenkiLenkLenk';
GRANT DELETE ON APP.Questions TO 'betAppDeleter'@'localhost';
GRANT DELETE ON APP.PrivateQuestions TO 'betAppDeleter'@'localhost';
GRANT DELETE ON APP.Groups TO 'betAppDeleter'@'localhost';
GRANT DELETE ON APP.PublicQuestions TO 'betAppDeleter'@'localhost';

DELETE FROM mysql.user WHERE User = '';

FLUSH PRIVILEGES;
