package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentServiceDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("request_type")
    @Expose
    private String requestType;
    @SerializedName("farmer_id")
    @Expose
    private Integer farmerId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_name")
    @Expose
    private String imageName;
    @SerializedName("service_request")
    @Expose
    private String serviceRequest;
    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("farmer_address")
    @Expose
    private String farmer_address;

    public String getFarmer_address() {
        return farmer_address;
    }

    public void setFarmer_address(String farmer_address) {
        this.farmer_address = farmer_address;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    @SerializedName("farmer_name")
    @Expose
    private String farmer_name;

    @SerializedName("incident_date")
    @Expose
    private String incidentDate;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
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

    public CurrentServiceDatum withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public CurrentServiceDatum withRequestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public CurrentServiceDatum withFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CurrentServiceDatum withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public CurrentServiceDatum withImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public String getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(String serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public CurrentServiceDatum withServiceRequest(String serviceRequest) {
        this.serviceRequest = serviceRequest;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public CurrentServiceDatum withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public CurrentServiceDatum withIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
        return this;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public CurrentServiceDatum withAgentId(Integer agentId) {
        this.agentId = agentId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CurrentServiceDatum withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public CurrentServiceDatum withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CurrentServiceDatum withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
