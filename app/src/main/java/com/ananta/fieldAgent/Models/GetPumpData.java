package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetPumpData {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pumpInstallation")
    @Expose
    private ArrayList<PumpInstallation> pumpInstallation;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public GetPumpData withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetPumpData withMessage(String message) {
        this.message = message;
        return this;
    }

    public ArrayList<PumpInstallation> getPumpInstallation() {
        return pumpInstallation;
    }

    public void setPumpInstallation(ArrayList<PumpInstallation> pumpInstallation) {
        this.pumpInstallation = pumpInstallation;
    }

    public GetPumpData withPumpInstallation(ArrayList<PumpInstallation> pumpInstallation) {
        this.pumpInstallation = pumpInstallation;
        return this;
    }

}
