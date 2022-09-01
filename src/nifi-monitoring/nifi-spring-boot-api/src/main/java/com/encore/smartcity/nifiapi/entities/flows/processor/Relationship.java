package com.encore.smartcity.nifiapi.entities.flows.processor;

import lombok.Data;

@Data
public class Relationship {

    private String  name;
    private String description;
    private boolean autoTerminate;

}
