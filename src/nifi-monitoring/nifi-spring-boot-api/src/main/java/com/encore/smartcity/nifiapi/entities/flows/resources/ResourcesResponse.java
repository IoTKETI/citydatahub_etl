package com.encore.smartcity.nifiapi.entities.flows.resources;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourcesResponse {

    @SerializedName(value = "resources")
    private List<Resources> resources;

    public ResourcesResponse() {
    }

    public ResourcesResponse(List<Resources> resources) {
        this.resources = resources;
    }

    public List<Resources> getResources() {
        return resources;
    }

    public void setResources(List<Resources> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "ResourcesResponse{" +
                "resources=" + resources +
                '}';
    }
}
