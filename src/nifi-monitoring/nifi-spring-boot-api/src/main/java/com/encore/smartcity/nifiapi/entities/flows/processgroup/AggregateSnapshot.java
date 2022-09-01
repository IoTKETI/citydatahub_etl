package com.encore.smartcity.nifiapi.entities.flows.processgroup;

import lombok.Data;

@Data
public class AggregateSnapshot {

    private String id;
    private String name;
    private Long flowFilesIn;
    private Long bytesIn;
    private String input;
    private Long flowFilesQueued;
    private Long bytesQueued;
    private String queued;
    private String queuedCount;
    private String queuedSize;
    private Long bytesRead;
    private String read;
    private Long bytesWritten;
    private String written;
    private Long flowFilesOut;
    private Long bytesOut;
    private String output;
    private Long flowFilesTransferred;
    private Long bytesTransferred;
    private String transferred;
    private Long bytesReceived;
    private Long flowFilesReceived;
    private String received;
    private Long bytesSent;
    private Long flowFilesSent;
    private String sent;
    private Integer activeThreadCount;
    private Integer terminatedThreadCount;


}
