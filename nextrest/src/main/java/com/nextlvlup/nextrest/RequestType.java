package com.nextlvlup.nextrest;

public enum RequestType {
	
	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	PATCH("PATCH"),
	DELETE("DELETE"),
	OPTIONS("OPTIONS"),
	HEAD("HEAD");
	
	private String name;
	
	RequestType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
