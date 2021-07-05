package com.kelompok2.rudibonsai.model.cart.get;

import com.google.gson.annotations.SerializedName;

public class ProductImagesItem{

	@SerializedName("laravel_through_key")
	private int laravelThroughKey;

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

	public int getLaravelThroughKey(){
		return laravelThroughKey;
	}

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