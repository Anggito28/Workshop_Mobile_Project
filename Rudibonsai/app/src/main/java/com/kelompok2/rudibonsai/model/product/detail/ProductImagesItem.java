package com.kelompok2.rudibonsai.model.product.detail;

import com.google.gson.annotations.SerializedName;

public class ProductImagesItem{

	@SerializedName("is_primary")
	private Object isPrimary;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("id")
	private int id;

	public Object getIsPrimary(){
		return isPrimary;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public int getProductId(){
		return productId;
	}

	public String getName(){
		return name;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}
}