package com.encore.smartcity.nifiapi.entities.flows.processgroup;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ProcessGroups {

    private String id;
    private String uri;
    private PGComponent component;

}
