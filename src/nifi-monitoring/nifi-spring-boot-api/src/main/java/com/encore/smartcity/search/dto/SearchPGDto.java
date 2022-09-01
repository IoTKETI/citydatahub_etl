package com.encore.smartcity.search.dto;

import com.encore.smartcity.nifiapi.entities.flows.ProcessGroupFlow;
import lombok.Data;

@Data
public class SearchPGDto {

    private Long id;

    private Long level;

    private String name;

    private String flowId;

    private String uri;

//    private String parentName;

    private String parentId;

    ProcessGroupFlow processGroupFlow;

}
