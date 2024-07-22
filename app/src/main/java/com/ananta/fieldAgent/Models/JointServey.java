package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JointServey {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("farmer_id")
    @Expose
    private Integer farmerId;
    @SerializedName("alternet_mo")
    @Expose
    private String alternetMo;
    @SerializedName("imei_no")
    @Expose
    private String imeiNo;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("is_water_source_available")
    @Expose
    private String isWaterSourceAvailable;
    @SerializedName("type_of_water_source")
    @Expose
    private String typeOfWaterSource;
    @SerializedName("water_depth")
    @Expose
    private String waterDepth;
    @SerializedName("constant_water")
    @Expose
    private String constantWater;
    @SerializedName("water_delivery_point")
    @Expose
    private String waterDeliveryPoint;
    @SerializedName("pump_type")
    @Expose
    private String pumpType;
    @SerializedName("pump_recom_survey")
    @Expose
    private String pumpRecomSurvey;
    @SerializedName("pump_recom_benefits")
    @Expose
    private String pumpRecomBenefits;
    @SerializedName("is_pump_electricity")
    @Expose
    private String isPumpElectricity;
    @SerializedName("is_solar_pump")
    @Expose
    private String isSolarPump;
    @SerializedName("is_shadow_area")
    @Expose
    private String isShadowArea;
    @SerializedName("is_mobile_network")
    @Expose
    private String isMobileNetwork;
    @SerializedName("survey_person")
    @Expose
    private String surveyPerson;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("water_res_image")
    @Expose
    private String waterResImage;
    @SerializedName("landmark_image")
    @Expose
    private String landmarkImage;
    @SerializedName("beneficiary_image")
    @Expose
    private String beneficiaryImage;
    @SerializedName("beneficiary_sign")
    @Expose
    private String beneficiarySign;
    @SerializedName("survey_sign")
    @Expose
    private String surveySign;
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

    public JointServey withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public JointServey withAgentId(Integer agentId) {
        this.agentId = agentId;
        return this;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public JointServey withFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
        return this;
    }

    public String getAlternetMo() {
        return alternetMo;
    }

    public void setAlternetMo(String alternetMo) {
        this.alternetMo = alternetMo;
    }

    public JointServey withAlternetMo(String alternetMo) {
        this.alternetMo = alternetMo;
        return this;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public JointServey withImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public JointServey withLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public JointServey withLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getIsWaterSourceAvailable() {
        return isWaterSourceAvailable;
    }

    public void setIsWaterSourceAvailable(String isWaterSourceAvailable) {
        this.isWaterSourceAvailable = isWaterSourceAvailable;
    }

    public JointServey withIsWaterSourceAvailable(String isWaterSourceAvailable) {
        this.isWaterSourceAvailable = isWaterSourceAvailable;
        return this;
    }

    public String getTypeOfWaterSource() {
        return typeOfWaterSource;
    }

    public void setTypeOfWaterSource(String typeOfWaterSource) {
        this.typeOfWaterSource = typeOfWaterSource;
    }

    public JointServey withTypeOfWaterSource(String typeOfWaterSource) {
        this.typeOfWaterSource = typeOfWaterSource;
        return this;
    }

    public String getWaterDepth() {
        return waterDepth;
    }

    public void setWaterDepth(String waterDepth) {
        this.waterDepth = waterDepth;
    }

    public JointServey withWaterDepth(String waterDepth) {
        this.waterDepth = waterDepth;
        return this;
    }

    public String getConstantWater() {
        return constantWater;
    }

    public void setConstantWater(String constantWater) {
        this.constantWater = constantWater;
    }

    public JointServey withConstantWater(String constantWater) {
        this.constantWater = constantWater;
        return this;
    }

    public String getWaterDeliveryPoint() {
        return waterDeliveryPoint;
    }

    public void setWaterDeliveryPoint(String waterDeliveryPoint) {
        this.waterDeliveryPoint = waterDeliveryPoint;
    }

    public JointServey withWaterDeliveryPoint(String waterDeliveryPoint) {
        this.waterDeliveryPoint = waterDeliveryPoint;
        return this;
    }

    public String getPumpType() {
        return pumpType;
    }

    public void setPumpType(String pumpType) {
        this.pumpType = pumpType;
    }

    public JointServey withPumpType(String pumpType) {
        this.pumpType = pumpType;
        return this;
    }

    public String getPumpRecomSurvey() {
        return pumpRecomSurvey;
    }

    public void setPumpRecomSurvey(String pumpRecomSurvey) {
        this.pumpRecomSurvey = pumpRecomSurvey;
    }

    public JointServey withPumpRecomSurvey(String pumpRecomSurvey) {
        this.pumpRecomSurvey = pumpRecomSurvey;
        return this;
    }

    public String getPumpRecomBenefits() {
        return pumpRecomBenefits;
    }

    public void setPumpRecomBenefits(String pumpRecomBenefits) {
        this.pumpRecomBenefits = pumpRecomBenefits;
    }

    public JointServey withPumpRecomBenefits(String pumpRecomBenefits) {
        this.pumpRecomBenefits = pumpRecomBenefits;
        return this;
    }

    public String getIsPumpElectricity() {
        return isPumpElectricity;
    }

    public void setIsPumpElectricity(String isPumpElectricity) {
        this.isPumpElectricity = isPumpElectricity;
    }

    public JointServey withIsPumpElectricity(String isPumpElectricity) {
        this.isPumpElectricity = isPumpElectricity;
        return this;
    }

    public String getIsSolarPump() {
        return isSolarPump;
    }

    public void setIsSolarPump(String isSolarPump) {
        this.isSolarPump = isSolarPump;
    }

    public JointServey withIsSolarPump(String isSolarPump) {
        this.isSolarPump = isSolarPump;
        return this;
    }

    public String getIsShadowArea() {
        return isShadowArea;
    }

    public void setIsShadowArea(String isShadowArea) {
        this.isShadowArea = isShadowArea;
    }

    public JointServey withIsShadowArea(String isShadowArea) {
        this.isShadowArea = isShadowArea;
        return this;
    }

    public String getIsMobileNetwork() {
        return isMobileNetwork;
    }

    public void setIsMobileNetwork(String isMobileNetwork) {
        this.isMobileNetwork = isMobileNetwork;
    }

    public JointServey withIsMobileNetwork(String isMobileNetwork) {
        this.isMobileNetwork = isMobileNetwork;
        return this;
    }

    public String getSurveyPerson() {
        return surveyPerson;
    }

    public void setSurveyPerson(String surveyPerson) {
        this.surveyPerson = surveyPerson;
    }

    public JointServey withSurveyPerson(String surveyPerson) {
        this.surveyPerson = surveyPerson;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JointServey withRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getWaterResImage() {
        return waterResImage;
    }

    public void setWaterResImage(String waterResImage) {
        this.waterResImage = waterResImage;
    }

    public JointServey withWaterResImage(String waterResImage) {
        this.waterResImage = waterResImage;
        return this;
    }

    public String getLandmarkImage() {
        return landmarkImage;
    }

    public void setLandmarkImage(String landmarkImage) {
        this.landmarkImage = landmarkImage;
    }

    public JointServey withLandmarkImage(String landmarkImage) {
        this.landmarkImage = landmarkImage;
        return this;
    }

    public String getBeneficiaryImage() {
        return beneficiaryImage;
    }

    public void setBeneficiaryImage(String beneficiaryImage) {
        this.beneficiaryImage = beneficiaryImage;
    }

    public JointServey withBeneficiaryImage(String beneficiaryImage) {
        this.beneficiaryImage = beneficiaryImage;
        return this;
    }

    public String getBeneficiarySign() {
        return beneficiarySign;
    }

    public void setBeneficiarySign(String beneficiarySign) {
        this.beneficiarySign = beneficiarySign;
    }

    public JointServey withBeneficiarySign(String beneficiarySign) {
        this.beneficiarySign = beneficiarySign;
        return this;
    }

    public String getSurveySign() {
        return surveySign;
    }

    public void setSurveySign(String surveySign) {
        this.surveySign = surveySign;
    }

    public JointServey withSurveySign(String surveySign) {
        this.surveySign = surveySign;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public JointServey withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public JointServey withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public JointServey withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

}
