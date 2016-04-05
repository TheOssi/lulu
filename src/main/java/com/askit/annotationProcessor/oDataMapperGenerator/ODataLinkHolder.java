package com.askit.annotationProcessor.oDataMapperGenerator;

public class ODataLinkHolder {

	private final String oData, methodName;
	private final boolean expectResult;

	public ODataLinkHolder(final String oData, final String methodName, final boolean expectResult) {
		this.oData = oData;
		this.methodName = methodName;
		this.expectResult = expectResult;
	}

	public String getOData() {
		return oData;
	}

	public String getMethodName() {
		return methodName;
	}

	public boolean exceptResult() {
		return expectResult;
	}

}