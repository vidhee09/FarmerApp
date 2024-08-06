package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FarmerDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("application_no")
    @Expose
    private String applicationNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cast_id")
    @Expose
    private Integer castId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("district_id")
    @Expose
    private Integer districtId;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("village_id")
    @Expose
    private Integer villageId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("otp_generated_at")
    @Expose
    private String otpGeneratedAt;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("service_request_id")
    @Expose
    private Integer serviceRequestId;
    @SerializedName("site_report")
    @Expose
    private Integer siteReport;
    @SerializedName("delivery_report")
    @Expose
    private Integer deliveryReport;
    @SerializedName("joint_report")
    @Expose
    private Integer jointReport;
    @SerializedName("pump_report")
    @Expose
    private Integer pumpReport;
    @SerializedName("order_status")
    @Expose
    private Integer orderStatus;
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

    public FarmerDatum withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public FarmerDatum withApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FarmerDatum withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCastId() {
        return castId;
    }

    public void setCastId(Integer castId) {
        this.castId = castId;
    }

    public FarmerDatum withCastId(Integer castId) {
        this.castId = castId;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public FarmerDatum withImage(String image) {
        this.image = image;
        return this;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public FarmerDatum withCountryId(Integer countryId) {
        this.countryId = countryId;
        return this;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public FarmerDatum withStateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public FarmerDatum withDistrictId(Integer districtId) {
        this.districtId = districtId;
        return this;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public FarmerDatum withCityId(Integer cityId) {
        this.cityId = cityId;
        return this;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public FarmerDatum withVillageId(Integer villageId) {
        this.villageId = villageId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FarmerDatum withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public FarmerDatum withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public FarmerDatum withOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public String getOtpGeneratedAt() {
        return otpGeneratedAt;
    }

    public void setOtpGeneratedAt(String otpGeneratedAt) {
        this.otpGeneratedAt = otpGeneratedAt;
    }

    public FarmerDatum withOtpGeneratedAt(String otpGeneratedAt) {
        this.otpGeneratedAt = otpGeneratedAt;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public FarmerDatum withCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public FarmerDatum withAgentId(Integer agentId) {
        this.agentId = agentId;
        return this;
    }

    public Integer getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Integer serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public FarmerDatum withServiceRequestId(Integer serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
        return this;
    }

    public Integer getSiteReport() {
        return siteReport;
    }

    public void setSiteReport(Integer siteReport) {
        this.siteReport = siteReport;
    }

    public FarmerDatum withSiteReport(Integer siteReport) {
        this.siteReport = siteReport;
        return this;
    }

    public Integer getDeliveryReport() {
        return deliveryReport;
    }

    public void setDeliveryReport(Integer deliveryReport) {
        this.deliveryReport = deliveryReport;
    }

    public FarmerDatum withDeliveryReport(Integer deliveryReport) {
        this.deliveryReport = deliveryReport;
        return this;
    }

    public Integer getJointReport() {
        return jointReport;
    }

    public void setJointReport(Integer jointReport) {
        this.jointReport = jointReport;
    }

    public FarmerDatum withJointReport(Integer jointReport) {
        this.jointReport = jointReport;
        return this;
    }

    public Integer getPumpReport() {
        return pumpReport;
    }

    public void setPumpReport(Integer pumpReport) {
        this.pumpReport = pumpReport;
    }

    public FarmerDatum withPumpReport(Integer pumpReport) {
        this.pumpReport = pumpReport;
        return this;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public FarmerDatum withOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FarmerDatum withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public FarmerDatum withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FarmerDatum withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
