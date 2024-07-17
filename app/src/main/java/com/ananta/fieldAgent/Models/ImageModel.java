package com.ananta.fieldAgent.Models;

public class ImageModel {

    String success,message;


    FileUploadData data;

    public FileUploadData getFileUploadData() {
        return data;
    }

    public void setFileUploadData(FileUploadData fileUploadData) {
        this.data = fileUploadData;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
