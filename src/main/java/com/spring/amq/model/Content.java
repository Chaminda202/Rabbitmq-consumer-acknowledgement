package com.spring.amq.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
	@JsonProperty("mobile_number")
	private String mobileNumber;
	@JsonProperty("name")
	private String name;
	@JsonProperty("message")
	private String message;
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
