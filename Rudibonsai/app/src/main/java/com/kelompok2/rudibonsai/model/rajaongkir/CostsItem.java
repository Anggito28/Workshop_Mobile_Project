package com.kelompok2.rudibonsai.model.rajaongkir;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CostsItem{

	@SerializedName("cost")
	private List<CostItem> cost;

	@SerializedName("service")
	private String service;

	@SerializedName("description")
	private String description;

	public List<CostItem> getCost(){
		return cost;
	}

	public String getService(){
		return service;
	}

	public String getDescription(){
		return description;
	}
}