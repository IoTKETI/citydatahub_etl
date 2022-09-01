package com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup;

import lombok.Data;

@Data
public class AggregateSnapshot {

    private String id;
    private String groupId;
    private String name;
    private String targetUri;
    private String transmissionStatus;
    private Integer activeThreadCount;
    private Long flowFilesSent;
    private Long bytesSent;
    private String sent;
    private Long flowFilesReceived;
    private Long bytesReceived;
    private String received;

}
