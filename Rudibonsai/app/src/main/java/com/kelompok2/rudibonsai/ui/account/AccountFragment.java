package com.kelompok2.rudibonsai.ui.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.constant.ConstantValue;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.ui.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

public class AccountFragment extends Fragment {
    ImageView ivUserPicture;
    TextView tvFullname, tvEmail, tvPhone, tvGender, tvProvince, tvCity, tvSubdistrict, tvDetailAddress;
    SessionManager sessionManager;
    LinearLayout menuLogout;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);

        ivUserPicture = root.findViewById(R.id.iv_account_picture);
        tvFullname = root.findViewById(R.id.tv_account_fullname);
        tvEmail = root.findViewById(R.id.tv_account_email);
        tvPhone = root.findViewById(R.id.tv_account_phone);
        tvGender = root.findViewById(R.id.tv_account_gender_user);
        tvProvince = root.findViewById(R.id.tv_account_province_user);
        tvCity = root.findViewById(R.id.tv_account_city_user);
        tvSubdistrict = root.findViewById(R.id.tv_account_subdistrict_user);
        tvDetailAddress = root.findViewById(R.id.tv_account_detail_address_user);
        menuLogout = root.findViewById(R.id.menu_account_logout);
        sessionManager = new SessionManager(getActivity());
        builder = new AlertDialog.Builder(getActivity());

        tvFullname.setText(sessionManager.getFULLNAME());
        tvEmail.setText(sessionManager.getEMAIL());
        tvPhone.setText(sessionManager.getPHONE());
        tvGender.setText(sessionManager.getGENDER());
        tvProvince.setText(sessionManager.getPROVINCE());
        tvCity.setText(sessionManager.getCITY());
        tvSubdistrict.setText(sessionManager.getSUBDISTRICT());
        tvDetailAddress.setText(sessionManager.getAddressDetail());
        setUserPicture();
        setLogoutDialog();

        menuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        return root;
    }

    private void setLogoutDialog() {
        builder.setTitle(R.string.logout);
        builder.setMessage(R.string.logout_message);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.logoutSession();
                moveToLogin();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog = builder.create();
    }

    private void moveToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        getActivity().finish();
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

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.account_action_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}