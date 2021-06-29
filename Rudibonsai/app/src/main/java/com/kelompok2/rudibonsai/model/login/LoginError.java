package com.kelompok2.rudibonsai.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginError{

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private Errors errors;

	public String getMessage(){
		return message;
	}

	public Errors getErrors(){
		return errors;
	}
}