package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SiteReportModel {

    public boolean success;
    public String message;
    public Siteinspectionn siteinspection;
    public boolean store_already_existe;


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

    public Siteinspectionn getSiteinspection() {
        return siteinspection;
    }

    public void setSiteinspection(Siteinspectionn siteinspection) {
        this.siteinspection = siteinspection;
    }

    public boolean isStore_already_existe() {
        return store_already_existe;
    }

    public void setStore_already_existe(boolean store_already_existe) {
        this.store_already_existe = store_already_existe;
    }
}
