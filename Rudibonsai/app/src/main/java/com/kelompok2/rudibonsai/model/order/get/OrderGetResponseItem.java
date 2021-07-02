package com.kelompok2.rudibonsai.model.order.get;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderGetResponseItem{

	@SerializedName("products_name")
	private List<String> productsName;

	@SerializedName("order")
	private Order order;

	public List<String> getProductsName(){
		return productsName;
	}

	public Order getOrder(){
		return order;
	}
}