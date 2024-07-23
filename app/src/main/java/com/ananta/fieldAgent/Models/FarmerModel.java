package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FarmerModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("farmer_data")
    @Expose
    private ArrayList<FarmerDatum> farmerData;
    @SerializedName("current_service_data")
    @Expose
    private ArrayList<CurrentServiceDatum> currentServiceData;
    @SerializedName("past_service_data")
    @Expose
    private ArrayList<PastServiceDatum> pastServiceData;

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

    public ArrayList<FarmerDatum> getFarmerData() {
        return farmerData;
    }

    public void setFarmerData(ArrayList<FarmerDatum> farmerData) {
        this.farmerData = farmerData;
    }

    public FarmerModel withFarmerData(ArrayList<FarmerDatum> farmerData) {
        this.farmerData = farmerData;
        return this;
    }

    public ArrayList<CurrentServiceDatum> getCurrentServiceData() {
        return currentServiceData;
    }

    public void setCurrentServiceData(ArrayList<CurrentServiceDatum> currentServiceData) {
        this.currentServiceData = currentServiceData;
    }

    public FarmerModel withCurrentServiceData(ArrayList<CurrentServiceDatum> currentServiceData) {
        this.currentServiceData = currentServiceData;
        return this;
    }

    public ArrayList<PastServiceDatum> getPastServiceData() {
        return pastServiceData;
    }

    public void setPastServiceData(ArrayList<PastServiceDatum> pastServiceData) {
        this.pastServiceData = pastServiceData;
    }

    public FarmerModel withPastServiceData(ArrayList<PastServiceDatum> pastServiceData) {
        this.pastServiceData = pastServiceData;
        return this;
    }

}
