package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentReqModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("current_service_data")
    @Expose
    private ArrayList<CurrentServiceDatum> current_service_data;

    @SerializedName("past_service_data")
    @Expose
    private ArrayList<PastServiceDatum> pastServiceData;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CurrentReqModel withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public ArrayList<CurrentServiceDatum> getCurrent_service_data() {
        return current_service_data;
    }

    public void setCurrent_service_data(ArrayList<CurrentServiceDatum> current_service_data) {
        this.current_service_data = current_service_data;
    }

    public ArrayList<PastServiceDatum> getPastServiceData() {
        return pastServiceData;
    }

    public void setPastServiceData(ArrayList<PastServiceDatum> pastServiceData) {
        this.pastServiceData = pastServiceData;
    }

    public CurrentReqModel withPastServiceData(ArrayList<PastServiceDatum> pastServiceData) {
        this.pastServiceData = pastServiceData;
        return this;
    }


}
