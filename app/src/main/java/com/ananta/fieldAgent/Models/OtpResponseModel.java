package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponseModel {
    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("user_id")
    @Expose
    public int user_id;
    @SerializedName("user_name")
    @Expose
    public String user_name;
    @SerializedName("user_companyname")
    @Expose
    public String user_companyname;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("mobile_number")
    @Expose
    public String mobile_number;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_companyname() {
        return user_companyname;
    }

    public void setUser_companyname(String user_companyname) {
        this.user_companyname = user_companyname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
}
