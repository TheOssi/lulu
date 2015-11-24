CREATE USER 'betAppAdmin'@'localhost' IDENTIFIED by 'muellimuellmuell';
GRANT ALL PRIVILEGES ON APP.* TO 'betAppAdmin'@'localhost';

-- CREATE USER 'betAppUser'@'localhost' IDENTIFIED BY 'fellifellfell';
-- GRANT INSERT,SELECT,UPDATE ON BET_APP.* TO 'betAppUser'@'localhost';
-- REVOKE INSERT,SELECT,UPDATE ON BET_APP.Bets FROM 'betAppUser'@'localhost';
-- GRANT INSERT, SELECT, UPDATE, DELETE ON BET_APP.Bets TO 'betAppUser'@'localhost';

CREATE USER 'betAppReader'@'localhost' IDENTIFIED BY 'marciMarcMarc';
GRANT SELECT ON APP.* TO 'betAppReader'@'localhost';

CREATE USER 'betAppWriter'@'localhost' IDENTIFIED BY 'felliFellFell';
GRANT INSERT,UPDATE ON APP.* TO 'betAppWriter'@'localhost';

CREATE USER 'betAppDeleter'@'localhost' IDENTIFIED BY 'lenkiLenkLenk';
GRANT DELETE ON APP.Questions TO 'betAppDeleter'@'localhost';
GRANT DELETE ON APP.Users TO 'betAppDeleter'@'localhost';


DELETE FROM mysql.user WHERE User = '';

FLUSH PRIVILEGES;
