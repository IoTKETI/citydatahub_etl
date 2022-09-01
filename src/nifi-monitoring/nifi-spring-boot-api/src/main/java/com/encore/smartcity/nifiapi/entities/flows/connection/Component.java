package com.encore.smartcity.nifiapi.entities.flows.connection;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Component {

    private String id;
    private String parentGroupId;

    private SourceDestination source;
    private SourceDestination destination;

    private String name;
    private Integer labelIndex;
    private Integer zIndex;

    private List<String> selectedRelationships = new ArrayList<>();
    private List<String> availableRelationships = new ArrayList<>();
    private Integer backPressureObjectThreshold;
    private String backPressureDataSizeThreshold;
    private String flowFileExpiration;

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    //    "prioritizers": [],
//    "bends": [],
//    "loadBalanceStrategy": "DO_NOT_LOAD_BALANCE",
//    "loadBalancePartitionAttribute": "",
//    "loadBalanceCompression": "DO_NOT_COMPRESS",
//    "loadBalanceStatus": "LOAD_BALANCE_NOT_CONFIGURED"

}
