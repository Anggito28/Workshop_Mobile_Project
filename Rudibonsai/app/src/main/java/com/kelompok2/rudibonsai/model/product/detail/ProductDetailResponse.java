package com.kelompok2.rudibonsai.model.product.detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductDetailResponse{

	@SerializedName("length")
	private String length;

	@SerializedName("weight")
	private int weight;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("product_category_id")
	private int productCategoryId;

	@SerializedName("product_images")
	private List<ProductImagesItem> productImages;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("price")
	private int price;

	@SerializedName("name")
	private String name;

	@SerializedName("width")
	private String width;

	@SerializedName("id")
	private int id;

	@SerializedName("stock")
	private int stock;

	@SerializedName("product_category")
	private ProductCategory productCategory;

	@SerializedName("height")
	private String height;

	public String getLength(){
		return length;
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

	public int getProductCategoryId(){
		return productCategoryId;
	}

	public List<ProductImagesItem> getProductImages(){
		return productImages;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getWidth(){
		return width;
	}

	public int getId(){
		return id;
	}

	public int getStock(){
		return stock;
	}

	public ProductCategory getProductCategory(){
		return productCategory;
	}

	public String getHeight(){
		return height;
	}
}