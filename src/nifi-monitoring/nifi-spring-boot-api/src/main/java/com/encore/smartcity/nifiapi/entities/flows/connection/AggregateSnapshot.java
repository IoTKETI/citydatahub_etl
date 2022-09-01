package com.encore.smartcity.nifiapi.entities.flows.connection;

import lombok.Data;

@Data
public class AggregateSnapshot {

    private String id;
    private String groupId;
    private String name;
    private String sourceName;
    private String destinationName;
    private Long flowFilesIn;
    private Long bytesIn;
    private String input;
    private Long flowFilesOut;
    private Long bytesOut;
    private String output;
    private Long flowFilesQueued;
    private Long bytesQueued;
    private String queued;
    private String queuedSize;
    private String queuedCount;
    private Integer percentUseCount;
    private Long percentUseBytes;

}
