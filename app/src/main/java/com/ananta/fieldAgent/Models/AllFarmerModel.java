package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllFarmerModel {

    /*public boolean success;
    public ArrayList<Farmer> farmer;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Farmer> getFarmer() {
        return farmer;
    }

    public void setFarmer(ArrayList<Farmer> farmer) {
        this.farmer = farmer;
    }*/


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("farmers")
    @Expose
    private ArrayList<Farmer> farmers;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public AllFarmerModel withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public ArrayList<Farmer> getFarmers() {
        return farmers;
    }

    public void setFarmers(ArrayList<Farmer> farmers) {
        this.farmers = farmers;
    }

    public AllFarmerModel withFarmers(ArrayList<Farmer> farmers) {
        this.farmers = farmers;
        return this;
    }


}
