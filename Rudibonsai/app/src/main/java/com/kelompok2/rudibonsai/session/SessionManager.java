package com.kelompok2.rudibonsai.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kelompok2.rudibonsai.model.login.LoginData;

public class SessionManager {
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final String FULLNAME = "fullname";
    private static final String EMAIL_VERIFIED_AT = "email_verified_at";
    private static final String ROLE_ID = "role_id";
    private static final String PHONE = "phone";
    private static final String GENDER = "gender";
    private static final String PROFILE_PICTURE = "profile_picture";
    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final String CITY_ID = "city_id";
    private static final String SUBDISTRICT = "subdistrict";
    private static final String ADDRESS_DETAIL = "address_detail";
    private static final String TOKEN = "token";

    public SessionManager(Context context){
        this.mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public int getCityId(){return sharedPreferences.getInt(CITY_ID, 0);}

    public int getUserId() {
        return sharedPreferences.getInt(USER_ID, 0);
    }

    public String getEMAIL() {
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getFULLNAME() {
        return sharedPreferences.getString(FULLNAME, null) ;
    }

    public String getEmailVerifiedAt() {
        return sharedPreferences.getString(EMAIL_VERIFIED_AT, null);
    }

    public int getRoleId() {
        return sharedPreferences.getInt(ROLE_ID, 0);
    }

    public String getPHONE() {
        return sharedPreferences.getString(PHONE, null) ;
    }

    public String getGENDER() {
        return sharedPreferences.getString(GENDER, null);
    }

    public String getProfilePicture() {
        return sharedPreferences.getString(PROFILE_PICTURE, null);
    }

    public String getPROVINCE() {
        return sharedPreferences.getString(PROVINCE, null);
    }

    public String getCITY() {
        return sharedPreferences.getString(CITY, null);
    }

    public String getSUBDISTRICT() {
        return sharedPreferences.getString(SUBDISTRICT, null);
    }

    public String getAddressDetail() {
        return sharedPreferences.getString(ADDRESS_DETAIL, null);
    }

    public String getTOKEN() {
        return sharedPreferences.getString(TOKEN, null);
    }

    public void createLoginSession(LoginData user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(USER_ID, user.getId());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(FULLNAME, user.getFullname());
        editor.putString(EMAIL_VERIFIED_AT, user.getEmailVerifiedAt());
        editor.putInt(ROLE_ID, user.getRoleId());
        editor.putString(PHONE, user.getPhone());
        editor.putString(GENDER, user.getGender());
        editor.putString(PROFILE_PICTURE, user.getProfilePicture());
        editor.putString(PROVINCE, user.getProvince());
        editor.putString(CITY, user.getCity());
        editor.putInt(CITY_ID, user.getCityId());
        editor.putString(SUBDISTRICT, user.getSubdistrict());
        editor.putString(ADDRESS_DETAIL, user.getAddressDetail());
        editor.putString(TOKEN, user.getToken());
        editor.commit();
    }

    public void logoutSession(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
