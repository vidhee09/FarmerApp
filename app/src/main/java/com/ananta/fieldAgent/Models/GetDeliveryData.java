package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetDeliveryData {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("delivery")
    @Expose
    private ArrayList<DeliveryReport> delivery;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public GetDeliveryData withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetDeliveryData withMessage(String message) {
        this.message = message;
        return this;
    }

    public ArrayList<DeliveryReport> getDelivery() {
        return delivery;
    }

    public void setDelivery(ArrayList<DeliveryReport> delivery) {
        this.delivery = delivery;
    }

    public GetDeliveryData withDelivery(ArrayList<DeliveryReport> delivery) {
        this.delivery = delivery;
        return this;
    }
}
