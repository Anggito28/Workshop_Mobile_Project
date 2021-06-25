package com.kelompok2.rudibonsai.model.cart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse{

	@SerializedName("carts")
	private List<CartsItem> carts;

	public List<CartsItem> getCarts(){
		return carts;
	}
}