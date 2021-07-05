package com.kelompok2.rudibonsai.model.order.get.detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderDetail{

	@SerializedName("order_detail")
	private List<OrderDetailItem> orderDetail;

	@SerializedName("order")
	private Order order;

	public List<OrderDetailItem> getOrderDetail(){
		return orderDetail;
	}

	public Order getOrder(){
		return order;
	}
}