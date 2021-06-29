package com.kelompok2.rudibonsai.model.rajaongkir;

import com.google.gson.annotations.SerializedName;

public class RajaongkirResponse{

	@SerializedName("rajaongkir")
	private Rajaongkir rajaongkir;

	public Rajaongkir getRajaongkir(){
		return rajaongkir;
	}
}