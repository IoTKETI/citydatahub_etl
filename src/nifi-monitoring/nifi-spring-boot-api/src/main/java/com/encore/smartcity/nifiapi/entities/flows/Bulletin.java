package com.encore.smartcity.nifiapi.entities.flows;


import lombok.Data;

@Data
public class Bulletin {

    private Integer id;
    private String category;
    private String groupId;
    private String sourceId;
    private String sourceName;
    private String level;
    private String message;
    private String timestamp;

}
