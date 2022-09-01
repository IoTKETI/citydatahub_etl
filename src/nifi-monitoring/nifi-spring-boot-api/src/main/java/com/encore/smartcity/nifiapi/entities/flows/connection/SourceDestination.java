package com.encore.smartcity.nifiapi.entities.flows.connection;

import lombok.Data;

@Data
public class SourceDestination {

    private String id;
    private String type;
    private String groupId;
    private String name;
    private boolean running;
    private String comments;

}
