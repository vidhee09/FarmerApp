package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryReport {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("surveyor_name")
    @Expose
    private String surveyorName;
    @SerializedName("present_person_name")
    @Expose
    private String presentPersonName;
    @SerializedName("farmer_id")
    @Expose
    private Integer farmerId;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("sign")
    @Expose
    private String sign;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DeliveryReport withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getSurveyorName() {
        return surveyorName;
    }

    public void setSurveyorName(String surveyorName) {
        this.surveyorName = surveyorName;
    }

    public DeliveryReport withSurveyorName(String surveyorName) {
        this.surveyorName = surveyorName;
        return this;
    }

    public String getPresentPersonName() {
        return presentPersonName;
    }

    public void setPresentPersonName(String presentPersonName) {
        this.presentPersonName = presentPersonName;
    }

    public DeliveryReport withPresentPersonName(String presentPersonName) {
        this.presentPersonName = presentPersonName;
        return this;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public DeliveryReport withFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
        return this;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public DeliveryReport withAgentId(Integer agentId) {
        this.agentId = agentId;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DeliveryReport withImage(String image) {
        this.image = image;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public DeliveryReport withSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public DeliveryReport withLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public DeliveryReport withLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DeliveryReport withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public DeliveryReport withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DeliveryReport withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

}
