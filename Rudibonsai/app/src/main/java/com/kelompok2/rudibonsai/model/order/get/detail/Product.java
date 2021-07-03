package com.kelompok2.rudibonsai.model.order.get.detail;

import com.google.gson.annotations.SerializedName;

public class Product{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("price")
	private int price;

	@SerializedName("name")
	private String name;

	@SerializedName("length")
	private String length;

	@SerializedName("width")
	private String width;

	@SerializedName("weight")
	private int weight;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("stock")
	private int stock;

	@SerializedName("height")
	private String height;

	@SerializedName("product_category_id")
	private int productCategoryId;

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getLength(){
		return length;
	}

	public String getWidth(){
		return width;
	}

	public int getWeight(){
		return weight;
	}

	public String getDescription(){
		return description;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public int getStock(){
		return stock;
	}

	public String getHeight(){
		return height;
	}

	public int getProductCategoryId(){
		return productCategoryId;
	}
}