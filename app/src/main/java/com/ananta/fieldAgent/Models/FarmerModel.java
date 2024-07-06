package com.ananta.fieldAgent.Models;

import java.util.ArrayList;

public class FarmerModel {

    String name,success,message;

    public FarmerModel(String name) {
        this.name = name;
    }

//    ArrayList<FarmerModel> farmer_data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
