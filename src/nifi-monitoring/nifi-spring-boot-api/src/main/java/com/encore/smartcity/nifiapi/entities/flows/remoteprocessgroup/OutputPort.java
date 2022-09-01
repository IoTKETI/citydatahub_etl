package com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup;

import lombok.Data;

@Data
public class OutputPort {

    private String id;
    private String targetId;
    private String groupId;
    private String name;
    private String comments;
    private Integer concurrentlySchedulableTaskCount;
    private boolean transmitting;
    private boolean useCompression;
    private boolean exists;
    private boolean targetRunning;
    private boolean connected;

}
