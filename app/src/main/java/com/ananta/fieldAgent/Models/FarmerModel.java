package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FarmerModel {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("farmer_data")
    @Expose
    private List<FarmerDatum> farmerData;
    @SerializedName("current_service_data")
    @Expose
    private List<CurrentServiceDatum> currentServiceData;
    @SerializedName("past_service_data")
    @Expose
    private List<PastServiceDatum> pastServiceData;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public FarmerModel withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public List<FarmerDatum> getFarmerData() {
        return farmerData;
    }

    public void setFarmerData(List<FarmerDatum> farmerData) {
        this.farmerData = farmerData;
    }

    public FarmerModel withFarmerData(List<FarmerDatum> farmerData) {
        this.farmerData = farmerData;
        return this;
    }

    public List<CurrentServiceDatum> getCurrentServiceData() {
        return currentServiceData;
    }

    public void setCurrentServiceData(List<CurrentServiceDatum> currentServiceData) {
        this.currentServiceData = currentServiceData;
    }

    public FarmerModel withCurrentServiceData(List<CurrentServiceDatum> currentServiceData) {
        this.currentServiceData = currentServiceData;
        return this;
    }

    public List<PastServiceDatum> getPastServiceData() {
        return pastServiceData;
    }

    public void setPastServiceData(List<PastServiceDatum> pastServiceData) {
        this.pastServiceData = pastServiceData;
    }

    public FarmerModel withPastServiceData(List<PastServiceDatum> pastServiceData) {
        this.pastServiceData = pastServiceData;
        return this;
    }
}
