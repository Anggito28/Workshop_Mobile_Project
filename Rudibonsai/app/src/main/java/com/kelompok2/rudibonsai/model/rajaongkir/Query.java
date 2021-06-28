package com.kelompok2.rudibonsai.model.rajaongkir;

import com.google.gson.annotations.SerializedName;

public class Query{

	@SerializedName("courier")
	private String courier;

	@SerializedName("origin")
	private String origin;

	@SerializedName("destination")
	private String destination;

	@SerializedName("weight")
	private int weight;

	public String getCourier(){
		return courier;
	}

	public String getOrigin(){
		return origin;
	}

	public String getDestination(){
		return destination;
	}

	public int getWeight(){
		return weight;
	}
}