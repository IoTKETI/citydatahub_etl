package com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup;

import lombok.Data;

@Data
public class Component {

    private String id;
    private String parentGroupId;
    private String targetUri;
    private String targetUris;
    private boolean targetSecure;
    private String name;
    private String comments;
    private String communicationsTimeout;
    private String yieldDuration;
    private String transportProtocol;
    private String proxyHost;
    private String proxyUser;
    private boolean transmitting;
    private Integer inputPortCount;
    private Integer outputPortCount;
    private Integer activeRemoteInputPortCount;
    private Integer inactiveRemoteInputPortCount;
    private Integer activeRemoteOutputPortCount;
    private Integer inactiveRemoteOutputPortCount;
    private String flowRefreshed;

    private Content contents;


}
