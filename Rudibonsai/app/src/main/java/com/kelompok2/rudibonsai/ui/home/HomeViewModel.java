package com.kelompok2.rudibonsai.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("ini fragment home");
    }

    public LiveData<String> getText() {
        return mText;
    }

//    public LiveData<String> getFullname(Context mContext){
//        SessionManager sessionManager = new SessionManager(mContext);
//        String fullname = sessionManager.getFULLNAME();
//
//        MutableLiveData<String> data = new MutableLiveData<>();
//        data.setValue(fullname);
//
//        return data;
//    }
}