package com.ananta.fieldAgent.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRequestDataModel {
    @SerializedName("pump_id")
    @Expose
    private String pumpId;
    @SerializedName("imei_no")
    @Expose
    private String imeiNo;
    @SerializedName("panel_id")
    @Expose
    private String panelId;
    @SerializedName("pump_recom_survey")
    @Expose
    private String pumpRecomSurvey;

    public String getPumpId() {
        return pumpId;
    }

    public void setPumpId(String pumpId) {
        this.pumpId = pumpId;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getPumpRecomSurvey() {
        return pumpRecomSurvey;
    }

    public void setPumpRecomSurvey(String pumpRecomSurvey) {
        this.pumpRecomSurvey = pumpRecomSurvey;
    }
}
