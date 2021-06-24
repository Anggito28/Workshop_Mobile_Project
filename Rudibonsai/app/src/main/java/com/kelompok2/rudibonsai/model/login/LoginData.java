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

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setEmailVerifiedAt(String emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public String getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public void setProfilePicture(String profilePicture){
		this.profilePicture = profilePicture;
	}

	public String getProfilePicture(){
		return profilePicture;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setAddressDetail(String addressDetail){
		this.addressDetail = addressDetail;
	}

	public String getAddressDetail(){
		return addressDetail;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setRoleId(int roleId){
		this.roleId = roleId;
	}

	public int getRoleId(){
		return roleId;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setSubdistrict(String subdistrict){
		this.subdistrict = subdistrict;
	}

	public String getSubdistrict(){
		return subdistrict;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}