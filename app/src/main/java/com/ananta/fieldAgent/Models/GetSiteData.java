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
    @SerializedName("siteInpections")
    @Expose
    private ArrayList<SiteInpection> siteInpections;

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

    public ArrayList<SiteInpection> getSiteInpections() {
        return siteInpections;
    }

    public void setSiteInpections(ArrayList<SiteInpection> siteInpections) {
        this.siteInpections = siteInpections;
    }

    public GetSiteData withSiteInpections(ArrayList<SiteInpection> siteInpections) {
        this.siteInpections = siteInpections;
        return this;
    }

}
