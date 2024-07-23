package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PastServiceDatum {

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

    @SerializedName("farmer_address")
    @Expose
    private String farmer_address;

    @SerializedName("farmer_name")
    @Expose
    private String farmer_name;

    @SerializedName("complaint_id")
    @Expose
    private String complaint_id;

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PastServiceDatum withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public PastServiceDatum withRequestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public PastServiceDatum withFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PastServiceDatum withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public PastServiceDatum withImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public String getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(String serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public PastServiceDatum withServiceRequest(String serviceRequest) {
        this.serviceRequest = serviceRequest;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PastServiceDatum withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public PastServiceDatum withIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
        return this;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public PastServiceDatum withAgentId(Integer agentId) {
        this.agentId = agentId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PastServiceDatum withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public PastServiceDatum withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PastServiceDatum withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
