package com.encore.smartcity.nifiapi.entities.flows.processgroup;

import lombok.Data;

@Data
public class Component {
    private String id;
    private String parentGroupId;
    private String name;
    private String comments;

//    private Object variables;

    private Integer runningCount;
    private Integer stoppedCount;
    private Integer invalidCount;
    private Integer disabledCount;
    private Integer activeRemotePortCount;
    private Integer inactiveRemotePortCount;
    private Integer upToDateCount;
    private Integer locallyModifiedCount;
    private Integer staleCount;
    private Integer locallyModifiedAndStaleCount;
    private Integer syncFailureCount;
    private Integer inputPortCount;
    private Integer outputPortCount;

}
