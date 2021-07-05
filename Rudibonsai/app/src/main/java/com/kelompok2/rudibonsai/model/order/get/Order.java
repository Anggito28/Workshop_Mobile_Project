package com.kelompok2.rudibonsai.model.order.get;

import com.google.gson.annotations.SerializedName;

public class Order{

	@SerializedName("quantity_total")
	private int quantityTotal;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("grand_total_amount")
	private int grandTotalAmount;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public int getQuantityTotal(){
		return quantityTotal;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getGrandTotalAmount(){
		return grandTotalAmount;
	}

	public int getId(){
		return id;
	}

	public String getStatus(){
		return status;
	}
}