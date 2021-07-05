package com.kelompok2.rudibonsai.model.order.get.detail;

import com.google.gson.annotations.SerializedName;

public class Order{

	@SerializedName("shipping_service")
	private String shippingService;

	@SerializedName("shipping_cost")
	private int shippingCost;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("grand_total_amount")
	private int grandTotalAmount;

	@SerializedName("expired_at")
	private String expiredAt;

	@SerializedName("product_total_amount")
	private int productTotalAmount;

	@SerializedName("quantity_total")
	private int quantityTotal;

	@SerializedName("payment_type")
	private String paymentType;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("shipping_agent")
	private String shippingAgent;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("id")
	private int id;

	@SerializedName("payment_agent")
	private String paymentAgent;

	@SerializedName("payment_proof")
	private String paymentProof;

	@SerializedName("status")
	private String status;

	public String getShippingService(){
		return shippingService;
	}

	public int getShippingCost(){
		return shippingCost;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getGrandTotalAmount(){
		return grandTotalAmount;
	}

	public String getExpiredAt(){
		return expiredAt;
	}

	public int getProductTotalAmount(){
		return productTotalAmount;
	}

	public int getQuantityTotal(){
		return quantityTotal;
	}

	public String getPaymentType(){
		return paymentType;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getShippingAgent(){
		return shippingAgent;
	}

	public int getUserId(){
		return userId;
	}

	public int getId(){
		return id;
	}

	public String getPaymentAgent(){
		return paymentAgent;
	}

	public String getPaymentProof(){
		return paymentProof;
	}

	public String getStatus(){
		return status;
	}
}