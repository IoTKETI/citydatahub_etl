package com.encore.smartcity.nifiapi.entities.flows.processgroup;

import lombok.Data;

import java.util.List;

@Data
public class ProcessGroupList {
    private List<ProcessGroups> processGroups;
}
