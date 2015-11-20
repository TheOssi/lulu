package com.betit.entities;

import java.util.Date;

public class Group {

	private final Long groupID;
	private final Date createDate;
	private final Long adminID;
	private final String groupname;
	private final String groupPictureURI;

	public Group(final Date createDate, final Long adminID, final String groupname, final String groupPictureURI) {
		this(null, createDate, adminID, groupname, groupPictureURI);
	}

	public Group(final Long groupID, final Date createDate, final Long adminID, final String groupname, final String groupPictureURI) {
		this.groupID = groupID;
		this.createDate = createDate;
		this.adminID = adminID;
		this.groupname = groupname;
		this.groupPictureURI = groupPictureURI;
	}

	public Long getGroupID() {
		return groupID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Long getAdminID() {
		return adminID;
	}

	public String getGroupname() {
		return groupname;
	}

	public String getGroupPictureURI() {
		return groupPictureURI;
	}
}