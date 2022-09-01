package com.encore.smartcity.nifiapi.entities.flows.processor;

import lombok.Data;

import java.util.List;

@Data
public class Component {

    private String id;
    private String parentGroupId;
    private String name;
    private String state;

    private List<Relationship> relationships;

    private boolean supportsParallelProcessing;
    private boolean supportsEventDriven;
    private boolean supportsBatching;
    private boolean persistsState;
    private boolean restricted;
    private boolean deprecated;
    private boolean executionNodeRestricted;
    private boolean multipleVersionsAvailable;
    private String inputRequirement;

//    removed
//    private Config config;

    private String validationStatus;
    private boolean extensionMissing;

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }
}
