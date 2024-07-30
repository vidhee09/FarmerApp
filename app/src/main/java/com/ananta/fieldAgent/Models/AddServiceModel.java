package com.ananta.fieldAgent.Models;


public class AddServiceModel {

    public boolean success;
    public String message;
    public Agentservice agentservice;

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

    public Agentservice getAgentservice() {
        return agentservice;
    }

    public void setAgentservice(Agentservice agentservice) {
        this.agentservice = agentservice;
    }
}
