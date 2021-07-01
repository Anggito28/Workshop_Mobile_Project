package com.kelompok2.rudibonsai.model.cart;

import com.google.gson.annotations.SerializedName;

public class CartDeleteResponse{

	@SerializedName("message")
	private String message;

	public String getMessage(){
		return message;
	}
}