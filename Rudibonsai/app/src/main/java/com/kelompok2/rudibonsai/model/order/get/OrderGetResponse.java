package com.kelompok2.rudibonsai.model.order.get;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderGetResponse{

	@SerializedName("OrderGetResponse")
	private List<OrderGetResponseItem> orderGetResponse;

	public List<OrderGetResponseItem> getOrderGetResponse(){
		return orderGetResponse;
	}
}