package com.kelompok2.rudibonsai.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.constant.ConstantValue;
import com.kelompok2.rudibonsai.session.SessionManager;

public class AccountFragment extends Fragment {
    ImageView ivUserPicture;
    TextView tvFullname, tvEmail, tvPhone, tvGender, tvProvince, tvCity, tvSubdistrict, tvDetailAddress;
    SessionManager sessionManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        ivUserPicture = root.findViewById(R.id.iv_account_picture);
        tvFullname = root.findViewById(R.id.tv_account_fullname);
        tvEmail = root.findViewById(R.id.tv_account_email);
        tvPhone = root.findViewById(R.id.tv_account_phone);
        tvGender = root.findViewById(R.id.tv_account_gender_user);
        tvProvince = root.findViewById(R.id.tv_account_province_user);
        tvCity = root.findViewById(R.id.tv_account_city_user);
        tvSubdistrict = root.findViewById(R.id.tv_account_subdistrict_user);
        tvDetailAddress = root.findViewById(R.id.tv_account_detail_address_user);
        sessionManager = new SessionManager(getActivity());

        tvFullname.setText(sessionManager.getFULLNAME());
        tvEmail.setText(sessionManager.getEMAIL());
        tvPhone.setText(sessionManager.getPHONE());
        tvGender.setText(sessionManager.getGENDER());
        tvProvince.setText(sessionManager.getPROVINCE());
        tvCity.setText(sessionManager.getCITY());
        tvSubdistrict.setText(sessionManager.getSUBDISTRICT());
        tvDetailAddress.setText(sessionManager.getAddressDetail());
        setUserPicture();

        return root;
    }

    private void setUserPicture() {
        String userPicture = sessionManager.getProfilePicture();

        Log.i("tes", userPicture);

        if (!userPicture.equals("default")){
            String picturePath = ConstantValue.BASE_URL + "user/picture/" + userPicture;

            Log.i("tes", "masuk if");

            Glide.with(getActivity())
                    .load(picturePath)
                    .into(ivUserPicture);
        }
    }
}