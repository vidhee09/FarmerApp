package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FarmerServiceResponseModel {

    public boolean success;
    public ArrayList<CurrentServiceDatum> current_service_data;
    public ArrayList<PastServiceDatum> past_service_data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<CurrentServiceDatum> getCurrent_service_data() {
        return current_service_data;
    }

    public void setCurrent_service_data(ArrayList<CurrentServiceDatum> current_service_data) {
        this.current_service_data = current_service_data;
    }

    public ArrayList<PastServiceDatum> getPast_service_data() {
        return past_service_data;
    }

    public void setPast_service_data(ArrayList<PastServiceDatum> past_service_data) {
        this.past_service_data = past_service_data;
    }
}
