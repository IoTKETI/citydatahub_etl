package com.encore.smartcity.nifiapi.entities.flows.processgroup;

import lombok.Data;

@Data
public class Status {

    private String id;
    private String name;
    private String statsLastRefreshed;

    private AggregateSnapshot aggregateSnapshot;

}
