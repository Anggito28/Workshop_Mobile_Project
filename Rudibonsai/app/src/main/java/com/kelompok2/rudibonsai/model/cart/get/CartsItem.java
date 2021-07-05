package com.kelompok2.rudibonsai.model.cart.get;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartsItem{

	@SerializedName("product_images")
	private List<ProductImagesItem> productImages;

	@SerializedName("product")
	private Product product;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public List<ProductImagesItem> getProductImages(){
		return productImages;
	}

	public Product getProduct(){
		return product;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getUserId(){
		return userId;
	}

	public int getProductId(){
		return productId;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}
}