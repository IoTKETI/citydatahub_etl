package com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup;

import lombok.Data;

@Data
public class Status {

    private String  groupId;
    private String  id;
    private String  name;
    private String  targetUri;
    private String  transmissionStatus;
    private String  statsLastRefreshed;
    private String  validationStatus;

    private AggregateSnapshot aggregateSnapshot;

}
