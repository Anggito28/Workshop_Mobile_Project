package com.kelompok2.rudibonsai.model.rajaongkir;

import com.google.gson.annotations.SerializedName;

public class CostItem{

	@SerializedName("note")
	private String note;

	@SerializedName("etd")
	private String etd;

	@SerializedName("value")
	private int value;

	public String getNote(){
		return note;
	}

	public String getEtd(){
		return etd;
	}

	public int getValue(){
		return value;
	}
}