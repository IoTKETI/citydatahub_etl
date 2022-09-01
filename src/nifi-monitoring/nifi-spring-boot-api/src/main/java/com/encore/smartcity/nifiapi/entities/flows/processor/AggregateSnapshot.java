package com.encore.smartcity.nifiapi.entities.flows.processor;

import lombok.Data;

@Data
public class AggregateSnapshot {

    private String id;
    private String groupId;
    private String name;
    private String type;
    private String runStatus;
    private String executionNode;
    private Long bytesRead;
    private Long bytesWritten;
    private String read;
    private String written;
    private Long flowFilesIn;
    private Long bytesIn;
    private String input;
    private Long flowFilesOut;
    private Long bytesOut;
    private String output;
    private Integer taskCount;
    private String tasksDurationNanos;
    private String tasks;
    private String tasksDuration;
    private Integer activeThreadCount;
    private Integer terminatedThreadCount;

}
