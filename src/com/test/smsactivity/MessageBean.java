package com.test.smsactivity;

public class MessageBean {
	
	private String from,message;
	
	
	public MessageBean(String from, String message) {
		super();
		this.from = from;
		this.message = message;
	}
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public String getFrom() {
		return from;
	}



	public void setFrom(String from) {
		this.from = from;
	}
	
	

}
