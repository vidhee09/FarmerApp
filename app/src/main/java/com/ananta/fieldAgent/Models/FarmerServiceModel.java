package com.ananta.fieldAgent.Models;

public class FarmerServiceModel {

    public boolean success;
    public String message;
    public Farmerservice farmerservice;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Farmerservice getFarmerservice() {
        return farmerservice;
    }

    public void setFarmerservice(Farmerservice farmerservice) {
        this.farmerservice = farmerservice;
    }
}
