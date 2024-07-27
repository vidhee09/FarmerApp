package com.ananta.fieldAgent.Models;

import java.util.ArrayList;

public class AllFarmerModel {

    String success, message;
    String id,name,image;

    public AllFarmerModel(String name) {
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

    ArrayList<AllFarmerModel> farmer = new ArrayList<>();

    Farmer farmers;

    public Farmer getFarmers() {
        return farmers;
    }

    public void setFarmers(Farmer farmers) {
        this.farmers = farmers;
    }

    public ArrayList<AllFarmerModel> getFarmer() {
        return farmer;
    }

    public void setFarmer(ArrayList<AllFarmerModel> farmer) {
        this.farmer = farmer;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
