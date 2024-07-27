package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Siteinspectionn {

    public String farmer_id;
    public String agent_id;
    public String inspection_officer_name;
    public String present_person_name;
    public String pump_image;
    public String pump_benificiaryimage;
    public String inspection_sign;
    public String latitude;
    public String longitude;
    public int status;
    public Date created_at;
    public Date updated_at;
    public int id;


    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getInspection_officer_name() {
        return inspection_officer_name;
    }

    public void setInspection_officer_name(String inspection_officer_name) {
        this.inspection_officer_name = inspection_officer_name;
    }

    public String getPresent_person_name() {
        return present_person_name;
    }

    public void setPresent_person_name(String present_person_name) {
        this.present_person_name = present_person_name;
    }

    public String getPump_image() {
        return pump_image;
    }

    public void setPump_image(String pump_image) {
        this.pump_image = pump_image;
    }

    public String getPump_benificiaryimage() {
        return pump_benificiaryimage;
    }

    public void setPump_benificiaryimage(String pump_benificiaryimage) {
        this.pump_benificiaryimage = pump_benificiaryimage;
    }

    public String getInspection_sign() {
        return inspection_sign;
    }

    public void setInspection_sign(String inspection_sign) {
        this.inspection_sign = inspection_sign;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
