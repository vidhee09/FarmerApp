package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSiteData {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sitereport")
    @Expose
    private ArrayList<Sitereport> sitereport;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public GetSiteData withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetSiteData withMessage(String message) {
        this.message = message;
        return this;
    }

    public ArrayList<Sitereport> getSitereport() {
        return sitereport;
    }

    public void setSitereport(ArrayList<Sitereport> sitereport) {
        this.sitereport = sitereport;
    }

    public GetSiteData withSitereport(ArrayList<Sitereport> sitereport) {
        this.sitereport = sitereport;
        return this;
    }

}
