package com.encore.smartcity.nifiapi.entities.flows;

import lombok.Data;

@Data
public class BulletinWrapper {

    private Integer id;
    private String groupId;
    private String sourceId;
    private String timestamp;
    private boolean canRead;

    private Bulletin bulletin;

}
