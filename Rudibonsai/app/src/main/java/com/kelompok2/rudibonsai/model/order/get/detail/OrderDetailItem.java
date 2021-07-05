package com.kelompok2.rudibonsai.model.order.get.detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderDetailItem{

	@SerializedName("product_images")
	private List<ProductImagesItem> productImages;

	@SerializedName("product")
	private Product product;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("order_id")
	private int orderId;

	public List<ProductImagesItem> getProductImages(){
		return productImages;
	}

	public Product getProduct(){
		return product;
	}

	public int getQuantity(){
		return quantity;
	}

	public String getUpdatedAt(){
		return updatedAt;
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

	public int getOrderId(){
		return orderId;
	}
}