package com.askit.queries;

public interface SecurityManager {

	public boolean hasUserAccessToGroup(long userID, long groupID);

	public boolean hasUserAccessToPrivateQuestion(long userID, long questionID);

	public boolean isUserAdmin(long userID, long groupID);

	public boolean isUserHost(long userID, long questionID);

}
