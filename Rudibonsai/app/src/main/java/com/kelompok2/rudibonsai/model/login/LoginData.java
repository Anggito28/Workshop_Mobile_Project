package com.kelompok2.rudibonsai.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("gender")
	private String gender;

	@SerializedName("city")
	private String city;

	@SerializedName("email_verified_at")
	private String emailVerifiedAt;

	@SerializedName("profile_picture")
	private String profilePicture;

	@SerializedName("token")
	private String token;

	@SerializedName("address_detail")
	private String addressDetail;

	@SerializedName("province")
	private String province;

	@SerializedName("role_id")
	private int roleId;

	@SerializedName("phone")
	private String phone;

	@SerializedName("subdistrict")
	private String subdistrict;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("email")
	private String email;

	@SerializedName("city_id")
	private int cityId;

	public String getGender(){
		return gender;
	}

	public String getCity(){
		return city;
	}

	public String getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public String getProfilePicture(){
		return profilePicture;
	}

	public String getToken(){
		return token;
	}

	public String getAddressDetail(){
		return addressDetail;
	}

	public String getProvince(){
		return province;
	}

	public int getRoleId(){
		return roleId;
	}

	public String getPhone(){
		return phone;
	}

	public String getSubdistrict(){
		return subdistrict;
	}

	public int getId(){
		return id;
	}

	public String getFullname(){
		return fullname;
	}

	public String getEmail(){
		return email;
	}

	public int getCityId(){
		return cityId;
	}
}