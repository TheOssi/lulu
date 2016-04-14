package com.askit.entities;

import java.util.Date;

public class Group {

	public final static String GROUP_ID = "groupID";
	public final static String CREATE_DATE = "createDate";
	public final static String ADMIN_ID = "adminID";
	public final static String GROUP_NAME = "groupName";
	public final static String GROUP_PICTURE_URI = "groupPictureURI";

	private Long groupID;
	private Date createDate;
	private Long adminID;
	private String groupname;
	private String groupPictureURI;

	public Group() {
		this(null, null, null, null, null);
	}

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

	public void setGroupID(final Long groupID) {
		this.groupID = groupID;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public void setAdminID(final Long adminID) {
		this.adminID = adminID;
	}

	public void setGroupname(final String groupname) {
		this.groupname = groupname;
	}

	public void setGroupPictureURI(final String groupPictureURI) {
		this.groupPictureURI = groupPictureURI;
	}

}