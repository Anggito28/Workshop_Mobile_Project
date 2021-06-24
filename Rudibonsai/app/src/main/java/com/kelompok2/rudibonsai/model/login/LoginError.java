package com.kelompok2.rudibonsai.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginError{

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private Errors errors;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setErrors(Errors errors){
		this.errors = errors;
	}

	public Errors getErrors(){
		return errors;
	}
}