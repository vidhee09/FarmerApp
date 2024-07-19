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
    private List<CurrentFarmerRequestModel> currentServiceData;
    @SerializedName("past_service_data")
    @Expose
    private List<PastFarmerRequestModel> pastServiceData;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<CurrentFarmerRequestModel> getCurrentServiceData() {
        return currentServiceData;
    }

    public void setCurrentServiceData(List<CurrentFarmerRequestModel> currentServiceData) {
        this.currentServiceData = currentServiceData;
    }

    public List<PastFarmerRequestModel> getPastServiceData() {
        return pastServiceData;
    }

    public void setPastServiceData(List<PastFarmerRequestModel> pastServiceData) {
        this.pastServiceData = pastServiceData;
    }
}
