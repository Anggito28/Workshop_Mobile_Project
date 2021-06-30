package com.kelompok2.rudibonsai.model.login;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Errors{

	@SerializedName("password")
	private List<String> password;

	@SerializedName("email")
	private List<String> email;

	public List<String> getPassword(){
		return password;
	}

	public List<String> getEmail(){
		return email;
	}
}