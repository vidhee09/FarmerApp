package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reports {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSiteReport() {
        return siteReport;
    }

    public void setSiteReport(Integer siteReport) {
        this.siteReport = siteReport;
    }

    public Integer getDeliveryReport() {
        return deliveryReport;
    }

    public void setDeliveryReport(Integer deliveryReport) {
        this.deliveryReport = deliveryReport;
    }

    public Integer getJointReport() {
        return jointReport;
    }

    public void setJointReport(Integer jointReport) {
        this.jointReport = jointReport;
    }

    public Integer getPumpReport() {
        return pumpReport;
    }

    public void setPumpReport(Integer pumpReport) {
        this.pumpReport = pumpReport;
    }
}
