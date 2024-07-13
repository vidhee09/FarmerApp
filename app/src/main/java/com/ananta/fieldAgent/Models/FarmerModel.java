package com.ananta.fieldAgent.Models;

import java.util.ArrayList;

public class FarmerModel {

    String success,message;

    ArrayList<FarmerModel> farmer_data;

    String id,application_no,name,cast_id,image,country_id,state_id,district_id,city_id,village_id,address,mobile_number,
            otp_generated_at,agent_id,order_status,status,created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplication_no() {
        return application_no;
    }

    public void setApplication_no(String application_no) {
        this.application_no = application_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCast_id() {
        return cast_id;
    }

    public void setCast_id(String cast_id) {
        this.cast_id = cast_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(String village_id) {
        this.village_id = village_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getOtp_generated_at() {
        return otp_generated_at;
    }

    public void setOtp_generated_at(String otp_generated_at) {
        this.otp_generated_at = otp_generated_at;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public FarmerModel(ArrayList<FarmerModel> farmer_data) {
        this.farmer_data = farmer_data;
    }

    public ArrayList<FarmerModel> getFarmer_data() {
        return farmer_data;
    }

    public void setFarmer_data(ArrayList<FarmerModel> farmer_data) {
        this.farmer_data = farmer_data;
    }

}
