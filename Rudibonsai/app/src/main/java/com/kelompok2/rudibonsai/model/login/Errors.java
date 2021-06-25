package com.kelompok2.rudibonsai.model.login;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Errors{

	@SerializedName("email")
	private List<String> email;

	public void setEmail(List<String> email){
		this.email = email;
	}

	public List<String> getEmail(){
		return email;
	}
}