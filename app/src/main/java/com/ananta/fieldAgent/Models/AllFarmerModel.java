package com.ananta.fieldAgent.Models;

import java.util.ArrayList;

public class AllFarmerModel {

    public boolean success;
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
    }

}
