-- Please replace all 'PASS' by the corresponding fields in the config file

CREATE USER 'appAdmin'@'localhost' IDENTIFIED by 'PASS';
GRANT ALL PRIVILEGES ON APP.* TO 'appAdmin'@'localhost';

CREATE USER 'appReader'@'localhost' IDENTIFIED BY 'PASS';
GRANT SELECT ON APP.* TO 'appReader'@'localhost';

CREATE USER 'appWriter'@'localhost' IDENTIFIED BY 'PASS';
GRANT SELECT,INSERT,UPDATE ON APP.* TO 'appWriter'@'localhost';

CREATE USER 'appDeleter'@'localhost' IDENTIFIED BY 'PASS';
GRANT DELETE ON APP.Users TO 'appDeleter'@'localhost';
GRANT DELETE ON APP.PrivateQuestions TO 'appDeleter'@'localhost';
GRANT DELETE ON APP.Groups TO 'appDeleter'@'localhost';
GRANT DELETE ON APP.PublicQuestions TO 'appDeleter'@'localhost';

DELETE FROM mysql.user WHERE User = '';

FLUSH PRIVILEGES;