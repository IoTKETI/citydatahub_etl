package com.encore.smartcity.nifiapi.entities.flows;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTemplateRequestEntity {

    private String name;
    private String description;
    private String snippetId;
    private Boolean disconnectedNodeAcknowledged;
}
