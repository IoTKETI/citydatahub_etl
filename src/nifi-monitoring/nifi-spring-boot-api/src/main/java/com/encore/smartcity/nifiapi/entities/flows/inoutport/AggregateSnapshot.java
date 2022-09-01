package com.encore.smartcity.nifiapi.entities.flows.inoutport;

import lombok.Data;

@Data
public class AggregateSnapshot {

    private String id;
    private String groupId;
    private String name;
    private Integer activeThreadCount;
    private Long flowFilesIn;
    private Long bytesIn;
    private String input;
    private Long flowFilesOut;
    private Long bytesOut;
    private String output;
    private String runStatus;

}
