package com.askit.notification;

import com.askit.database.DatabaseQueryManager;
import com.askit.database.QueryManager;
import com.askit.entities.User;
import com.askit.exception.DatabaseLayerException;
import com.askit.exception.NotificationException;

public class NotificationCreator {

	public NotificationCreator() {

	}

	public void sendNotificationToAllMembersOfAGroup(Notification not, Long groupID) throws DatabaseLayerException, NotificationException {
		final QueryManager queryManager = new DatabaseQueryManager();
		User[] users = queryManager.getUsersOfGroup(groupID);
		RegIDHandler regIDHandler = RegIDHandler.getInstance();
		NotificationHandler notificationHandler = NotificationHandler.getInstace();
		for (User user : users) {
			if (not.getTo() == null) {
				Long userID = user.getUserID();
				String regID = regIDHandler.getRegIDFromUser(userID);
				if(regID!=null){
					not.setTo(regID);
				}else{
					throw new NotificationException("No regID for User: " + userID);
				}
				
			}
			notificationHandler.addNotification(not);
		}
	}
}
