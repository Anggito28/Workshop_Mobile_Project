package com.kelompok2.rudibonsai.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginSuccess{

	@SerializedName("data")
	private LoginData loginData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public LoginData getData(){
		return loginData;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}