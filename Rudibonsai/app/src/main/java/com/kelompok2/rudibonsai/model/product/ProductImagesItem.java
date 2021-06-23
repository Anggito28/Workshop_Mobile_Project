package com.kelompok2.rudibonsai.model.product;

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

	public void setIsPrimary(Object isPrimary){
		this.isPrimary = isPrimary;
	}

	public Object getIsPrimary(){
		return isPrimary;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}