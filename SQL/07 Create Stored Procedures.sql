DELIMITER //

CREATE PROCEDURE randomHostAndAdmin
(IN userID MEDIUMINT)
BEGIN
	-- Set a new admin in every group in witch this user is admin
	-- The new admin is the first entry in the relations table between
	-- groups and users with the restriction on the groupID
	UPDATE APP.Groups 
		SET adminID = 
			( SELECT TOP 1 FROM App.GroupsToUsers 
				WHERE groupID = Groups.groupID ) 
		WHERE adminID = userID;
		
	--Delete all running Questions with this user as host
	DELETE FROM APP.QUESTIONS
	WHERE finished = 0 AND
		  hostID = userID;	
		
END //
DELIMITER ;
