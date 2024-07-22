package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PumpInstallation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("farmer_id")
    @Expose
    private Integer farmerId;
    @SerializedName("pump_id")
    @Expose
    private String pumpId;
    @SerializedName("panel_id")
    @Expose
    private String panelId;
    @SerializedName("controller_id")
    @Expose
    private String controllerId;
    @SerializedName("imei_no")
    @Expose
    private String imeiNo;
    @SerializedName("structure_id")
    @Expose
    private String structureId;
    @SerializedName("policy_no")
    @Expose
    private String policyNo;
    @SerializedName("install_image")
    @Expose
    private String installImage;
    @SerializedName("pump_benifi_image")
    @Expose
    private String pumpBenifiImage;
    @SerializedName("pump_work_image")
    @Expose
    private String pumpWorkImage;
    @SerializedName("sign")
    @Expose
    private String sign;
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

    public PumpInstallation withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public PumpInstallation withAgentId(Integer agentId) {
        this.agentId = agentId;
        return this;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public PumpInstallation withFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
        return this;
    }

    public String getPumpId() {
        return pumpId;
    }

    public void setPumpId(String pumpId) {
        this.pumpId = pumpId;
    }

    public PumpInstallation withPumpId(String pumpId) {
        this.pumpId = pumpId;
        return this;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public PumpInstallation withPanelId(String panelId) {
        this.panelId = panelId;
        return this;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public PumpInstallation withControllerId(String controllerId) {
        this.controllerId = controllerId;
        return this;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public PumpInstallation withImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
        return this;
    }

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public PumpInstallation withStructureId(String structureId) {
        this.structureId = structureId;
        return this;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public PumpInstallation withPolicyNo(String policyNo) {
        this.policyNo = policyNo;
        return this;
    }

    public String getInstallImage() {
        return installImage;
    }

    public void setInstallImage(String installImage) {
        this.installImage = installImage;
    }

    public PumpInstallation withInstallImage(String installImage) {
        this.installImage = installImage;
        return this;
    }

    public String getPumpBenifiImage() {
        return pumpBenifiImage;
    }

    public void setPumpBenifiImage(String pumpBenifiImage) {
        this.pumpBenifiImage = pumpBenifiImage;
    }

    public PumpInstallation withPumpBenifiImage(String pumpBenifiImage) {
        this.pumpBenifiImage = pumpBenifiImage;
        return this;
    }

    public String getPumpWorkImage() {
        return pumpWorkImage;
    }

    public void setPumpWorkImage(String pumpWorkImage) {
        this.pumpWorkImage = pumpWorkImage;
    }

    public PumpInstallation withPumpWorkImage(String pumpWorkImage) {
        this.pumpWorkImage = pumpWorkImage;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public PumpInstallation withSign(String sign) {
        this.sign = sign;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PumpInstallation withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public PumpInstallation withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PumpInstallation withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

}
