package com.ananta.fieldAgent.Models;

import java.util.Date;

public class Pumpinstall {

    public int agent_id;
    public int farmer_id;
    public String pump_id;
    public String panel_id;
    public String controller_id;
    public String imei_no;
    public String structure_id;
    public String policy_no;
    public String install_image;
    public String pump_benifi_image;
    public String pump_work_image;
    public String sign;
    public int status;
    public Date created_at;
    public Date updated_at;
    public int id;


    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public int getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(int farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getPump_id() {
        return pump_id;
    }

    public void setPump_id(String pump_id) {
        this.pump_id = pump_id;
    }

    public String getPanel_id() {
        return panel_id;
    }

    public void setPanel_id(String panel_id) {
        this.panel_id = panel_id;
    }

    public String getController_id() {
        return controller_id;
    }

    public void setController_id(String controller_id) {
        this.controller_id = controller_id;
    }

    public String getImei_no() {
        return imei_no;
    }

    public void setImei_no(String imei_no) {
        this.imei_no = imei_no;
    }

    public String getStructure_id() {
        return structure_id;
    }

    public void setStructure_id(String structure_id) {
        this.structure_id = structure_id;
    }

    public String getPolicy_no() {
        return policy_no;
    }

    public void setPolicy_no(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getInstall_image() {
        return install_image;
    }

    public void setInstall_image(String install_image) {
        this.install_image = install_image;
    }

    public String getPump_benifi_image() {
        return pump_benifi_image;
    }

    public void setPump_benifi_image(String pump_benifi_image) {
        this.pump_benifi_image = pump_benifi_image;
    }

    public String getPump_work_image() {
        return pump_work_image;
    }

    public void setPump_work_image(String pump_work_image) {
        this.pump_work_image = pump_work_image;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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
