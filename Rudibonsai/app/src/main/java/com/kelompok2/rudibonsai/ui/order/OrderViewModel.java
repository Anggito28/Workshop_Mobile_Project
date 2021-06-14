package com.kelompok2.rudibonsai.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrderViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("ini fragment pesanan");
    }

    public LiveData<String> getText(){return mText;}
}
