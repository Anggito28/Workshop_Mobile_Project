package com.kelompok2.rudibonsai.model.order.post;

import com.google.gson.annotations.SerializedName;

public class OrderPostDetailItem {

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("product_id")
	private int productId;

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}
}