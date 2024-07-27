package com.ananta.fieldAgent.Models;

public class JointSurveyorModel {

    public boolean success;
    public String message;
    public Jointservey jointservey;

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

    public Jointservey getJointservey() {
        return jointservey;
    }

    public void setJointservey(Jointservey jointservey) {
        this.jointservey = jointservey;
    }
}
