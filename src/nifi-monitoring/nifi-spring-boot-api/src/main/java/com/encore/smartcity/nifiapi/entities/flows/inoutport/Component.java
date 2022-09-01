package com.encore.smartcity.nifiapi.entities.flows.inoutport;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Component {

    private String id;
    private String parentGroupId;
    private String name;
    private String state;
    private String type;
    private Integer concurrentlySchedulableTaskCount;

    List<String> validationErrors = new ArrayList<>();

}
