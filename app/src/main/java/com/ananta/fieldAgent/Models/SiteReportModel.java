package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SiteReportModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("siteinspectionn")
    @Expose
    private Siteinspectionn siteinspectionn;

    @SerializedName("store_already_existe")
    @Expose
    private boolean store_already_existe;


    public boolean isStore_already_existe() {
        return store_already_existe;
    }

    public void setStore_already_existe(boolean store_already_existe) {
        this.store_already_existe = store_already_existe;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public SiteReportModel withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SiteReportModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public Siteinspectionn getSiteinspectionn() {
        return siteinspectionn;
    }

    public void setSiteinspectionn(Siteinspectionn siteinspectionn) {
        this.siteinspectionn = siteinspectionn;
    }

    public SiteReportModel withSiteinspectionn(Siteinspectionn siteinspectionn) {
        this.siteinspectionn = siteinspectionn;
        return this;
    }

}
