package com.ananta.fieldAgent.Models;

public class PumpInstallModel {

    public boolean success;
    public String message;
    public Pumpinstall pumpinstall;


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

    public Pumpinstall getPumpinstall() {
        return pumpinstall;
    }

    public void setPumpinstall(Pumpinstall pumpinstall) {
        this.pumpinstall = pumpinstall;
    }
}
