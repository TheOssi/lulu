package com.kj.test;

public class MethodsToCall {

	@Query(request = "get", entity = "Question")
	public String getQuestion() {
		return "GET QUESTION";
	}

	@Query(request = "get", entity = "Answer")
	public String getAnswer() {
		return "GET ANSWER";
	}

	@Query(request = "get", entity = "User")
	public String getUser() {
		return "GET USER";
	}

}
