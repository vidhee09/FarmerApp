package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetJointData {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("jointServey")
    @Expose
    private ArrayList<Jointservey> jointServey;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public GetJointData withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetJointData withMessage(String message) {
        this.message = message;
        return this;
    }

    public ArrayList<Jointservey> getJointServey() {
        return jointServey;
    }

    public void setJointServey(ArrayList<Jointservey> jointServey) {
        this.jointServey = jointServey;
    }

    public GetJointData withJointServey(ArrayList<Jointservey> jointServey) {
        this.jointServey = jointServey;
        return this;
    }

}
