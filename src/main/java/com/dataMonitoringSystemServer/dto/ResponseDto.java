package com.dataMonitoringSystemServer.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseDto<T> implements Serializable {

	private int resultCode=ResultCodes.UNEXPECTED_ERROR;
	private String errorMessage;
	private long responseId;
    private List<T> responseBody;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public long getResponseId() {
		return responseId;
	}

	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}

	public List<T> getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(List<T> responseBody) {
		this.responseBody = responseBody;
	}


	

}
