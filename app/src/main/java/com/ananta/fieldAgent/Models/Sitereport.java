package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sitereport {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("farmer_id")
    @Expose
    private Integer farmerId;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("inspection_officer_name")
    @Expose
    private String inspectionOfficerName;
    @SerializedName("present_person_name")
    @Expose
    private String presentPersonName;
    @SerializedName("pump_image")
    @Expose
    private String pumpImage;
    @SerializedName("pump_benificiaryimage")
    @Expose
    private String pumpBenificiaryimage;
    @SerializedName("inspection_sign")
    @Expose
    private String inspectionSign;
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

    public Sitereport withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public Sitereport withFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
        return this;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Sitereport withAgentId(Integer agentId) {
        this.agentId = agentId;
        return this;
    }

    public String getInspectionOfficerName() {
        return inspectionOfficerName;
    }

    public void setInspectionOfficerName(String inspectionOfficerName) {
        this.inspectionOfficerName = inspectionOfficerName;
    }

    public Sitereport withInspectionOfficerName(String inspectionOfficerName) {
        this.inspectionOfficerName = inspectionOfficerName;
        return this;
    }

    public String getPresentPersonName() {
        return presentPersonName;
    }

    public void setPresentPersonName(String presentPersonName) {
        this.presentPersonName = presentPersonName;
    }

    public Sitereport withPresentPersonName(String presentPersonName) {
        this.presentPersonName = presentPersonName;
        return this;
    }

    public String getPumpImage() {
        return pumpImage;
    }

    public void setPumpImage(String pumpImage) {
        this.pumpImage = pumpImage;
    }

    public Sitereport withPumpImage(String pumpImage) {
        this.pumpImage = pumpImage;
        return this;
    }

    public String getPumpBenificiaryimage() {
        return pumpBenificiaryimage;
    }

    public void setPumpBenificiaryimage(String pumpBenificiaryimage) {
        this.pumpBenificiaryimage = pumpBenificiaryimage;
    }

    public Sitereport withPumpBenificiaryimage(String pumpBenificiaryimage) {
        this.pumpBenificiaryimage = pumpBenificiaryimage;
        return this;
    }

    public String getInspectionSign() {
        return inspectionSign;
    }

    public void setInspectionSign(String inspectionSign) {
        this.inspectionSign = inspectionSign;
    }

    public Sitereport withInspectionSign(String inspectionSign) {
        this.inspectionSign = inspectionSign;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Sitereport withLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Sitereport withLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Sitereport withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Sitereport withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Sitereport withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
