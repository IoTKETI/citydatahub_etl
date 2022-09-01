package com.encore.smartcity.nifiapi.entities.flows.inoutport;

import lombok.Data;

@Data
public class Status {

    private String id;
    private String groupId;
    private String name;
    private String runStatus;
    private String statsLastRefreshed;

    private AggregateSnapshot aggregateSnapshot;

}
