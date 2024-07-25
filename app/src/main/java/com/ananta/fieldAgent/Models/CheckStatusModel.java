package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckStatusModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("site_report")
    @Expose
    private String siteReport;
    @SerializedName("delivery_report")
    @Expose
    private String deliveryReport;
    @SerializedName("joint_report")
    @Expose
    private String jointReport;
    @SerializedName("pump_report")
    @Expose
    private String pumpReport;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CheckStatusModel withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckStatusModel withName(String name) {
        this.name = name;
        return this;
    }

    public String getSiteReport() {
        return siteReport;
    }

    public void setSiteReport(String siteReport) {
        this.siteReport = siteReport;
    }

    public CheckStatusModel withSiteReport(String siteReport) {
        this.siteReport = siteReport;
        return this;
    }

    public String getDeliveryReport() {
        return deliveryReport;
    }

    public void setDeliveryReport(String deliveryReport) {
        this.deliveryReport = deliveryReport;
    }

    public CheckStatusModel withDeliveryReport(String deliveryReport) {
        this.deliveryReport = deliveryReport;
        return this;
    }

    public String getJointReport() {
        return jointReport;
    }

    public void setJointReport(String jointReport) {
        this.jointReport = jointReport;
    }

    public CheckStatusModel withJointReport(String jointReport) {
        this.jointReport = jointReport;
        return this;
    }

    public String getPumpReport() {
        return pumpReport;
    }

    public void setPumpReport(String pumpReport) {
        this.pumpReport = pumpReport;
    }

    public CheckStatusModel withPumpReport(String pumpReport) {
        this.pumpReport = pumpReport;
        return this;
    }
}
