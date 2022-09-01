package com.encore.smartcity.nifiapi.entities.flows.processor;

import lombok.Data;

@Data
public class Status {

    private String groupId;
    private String id;
    private String name;
    private String runStatus;
    private String statsLastRefreshed;

    private AggregateSnapshot aggregateSnapshot;

}
