package com.ananta.fieldAgent.Models;

public class JointSurveyorModel {
    public boolean success;
    public String message;
    public JointServey jointservey;

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

    public JointServey getJointservey() {
        return jointservey;
    }

    public void setJointservey(JointServey jointservey) {
        this.jointservey = jointservey;
    }
}
