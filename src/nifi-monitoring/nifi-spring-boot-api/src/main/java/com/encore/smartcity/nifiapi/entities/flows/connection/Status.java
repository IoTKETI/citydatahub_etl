package com.encore.smartcity.nifiapi.entities.flows.connection;

import lombok.Data;

@Data
public class Status {

    private String id;
    private String groupId;
    private String name;
    private String statsLastRefreshed;
    private String sourceId;
    private String sourceName;
    private String destinationId;
    private String destinationName;

    private AggregateSnapshot aggregateSnapshot;
}
