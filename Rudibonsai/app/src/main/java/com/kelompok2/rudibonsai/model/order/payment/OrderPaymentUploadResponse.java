package com.kelompok2.rudibonsai.model.order.payment;

import com.google.gson.annotations.SerializedName;

public class OrderPaymentUploadResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
