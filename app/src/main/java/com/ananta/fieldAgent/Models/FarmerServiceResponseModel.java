package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FarmerServiceResponseModel {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("current_service_data")
    @Expose
    private List<CurrentRequestFarmerModel> currentServiceData;
    @SerializedName("past_service_data")
    @Expose
    private List<PastFarmerRequestModel> pastServiceData;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<CurrentRequestFarmerModel> getCurrentServiceData() {
        return currentServiceData;
    }

    public void setCurrentServiceData(List<CurrentRequestFarmerModel> currentServiceData) {
        this.currentServiceData = currentServiceData;
    }

    public List<PastFarmerRequestModel> getPastServiceData() {
        return pastServiceData;
    }

    public void setPastServiceData(List<PastFarmerRequestModel> pastServiceData) {
        this.pastServiceData = pastServiceData;
    }
}
