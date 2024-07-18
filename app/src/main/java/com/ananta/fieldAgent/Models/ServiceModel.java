package com.ananta.fieldAgent.Models;

import java.util.ArrayList;

public class ServiceModel {

    String message,description,service_request,incident_date,agent_id,created_at,updated_at;
    boolean success;
    String image;
    ArrayList<ServiceModel> service_data;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getService_request() {
        return service_request;
    }

    public void setService_request(String service_request) {
        this.service_request = service_request;
    }

    public String getIncident_date() {
        return incident_date;
    }

    public void setIncident_date(String incident_date) {
        this.incident_date = incident_date;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<ServiceModel> getService_data() {
        return service_data;
    }

    public void setService_data(ArrayList<ServiceModel> service_data) {
        this.service_data = service_data;
    }
}
