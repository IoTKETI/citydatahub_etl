package com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup;

import com.encore.smartcity.nifiapi.entities.flows.BulletinWrapper;
import lombok.Data;

import java.util.List;

@Data
public class RemoteProcessGroup {
    private String id;
    private String uri;

    private List<BulletinWrapper> bulletins;
    private Component component;
    private Status status;

    private Integer inputPortCount;
    private Integer outputPortCount;
}
