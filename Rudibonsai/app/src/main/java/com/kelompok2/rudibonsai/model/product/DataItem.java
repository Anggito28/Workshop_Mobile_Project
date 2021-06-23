package com.kelompok2.rudibonsai.model.product;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

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

	public void setLength(String length){
		this.length = length;
	}

	public String getLength(){
		return length;
	}

	public void setWeight(int weight){
		this.weight = weight;
	}

	public int getWeight(){
		return weight;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setProductCategoryId(int productCategoryId){
		this.productCategoryId = productCategoryId;
	}

	public int getProductCategoryId(){
		return productCategoryId;
	}

	public void setProductImages(List<ProductImagesItem> productImages){
		this.productImages = productImages;
	}

	public List<ProductImagesItem> getProductImages(){
		return productImages;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setWidth(String width){
		this.width = width;
	}

	public String getWidth(){
		return width;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStock(int stock){
		this.stock = stock;
	}

	public int getStock(){
		return stock;
	}

	public void setProductCategory(ProductCategory productCategory){
		this.productCategory = productCategory;
	}

	public ProductCategory getProductCategory(){
		return productCategory;
	}

	public void setHeight(String height){
		this.height = height;
	}

	public String getHeight(){
		return height;
	}
}