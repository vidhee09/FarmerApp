package com.ananta.fieldAgent.Models;

public class ImageModel {

    public boolean success;
    public String message;
    public Uploadimage uploadimage;

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

    public Uploadimage getUploadimage() {
        return uploadimage;
    }

    public void setUploadimage(Uploadimage uploadimage) {
        this.uploadimage = uploadimage;
    }
}
