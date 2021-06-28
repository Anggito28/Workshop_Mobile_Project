package com.kelompok2.rudibonsai.model.order;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderPost{

	@SerializedName("product_total_amount")
	private int productTotalAmount;

	@SerializedName("shipping_service")
	private String shippingService;

	@SerializedName("quantity_total")
	private int quantityTotal;

	@SerializedName("shipping_cost")
	private int shippingCost;

	@SerializedName("shipping_agent")
	private String shippingAgent;

	@SerializedName("grand_total_amount")
	private int grandTotalAmount;

	@SerializedName("order_detail")
	private List<OrderDetailItem> orderDetail;

	public void setProductTotalAmount(int productTotalAmount){
		this.productTotalAmount = productTotalAmount;
	}

	public int getProductTotalAmount(){
		return productTotalAmount;
	}

	public void setShippingService(String shippingService){
		this.shippingService = shippingService;
	}

	public String getShippingService(){
		return shippingService;
	}

	public void setQuantityTotal(int quantityTotal){
		this.quantityTotal = quantityTotal;
	}

	public int getQuantityTotal(){
		return quantityTotal;
	}

	public void setShippingCost(int shippingCost){
		this.shippingCost = shippingCost;
	}

	public int getShippingCost(){
		return shippingCost;
	}

	public void setShippingAgent(String shippingAgent){
		this.shippingAgent = shippingAgent;
	}

	public String getShippingAgent(){
		return shippingAgent;
	}

	public void setGrandTotalAmount(int grandTotalAmount){
		this.grandTotalAmount = grandTotalAmount;
	}

	public int getGrandTotalAmount(){
		return grandTotalAmount;
	}

	public void setOrderDetail(List<OrderDetailItem> orderDetail){
		this.orderDetail = orderDetail;
	}

	public List<OrderDetailItem> getOrderDetail(){
		return orderDetail;
	}
}