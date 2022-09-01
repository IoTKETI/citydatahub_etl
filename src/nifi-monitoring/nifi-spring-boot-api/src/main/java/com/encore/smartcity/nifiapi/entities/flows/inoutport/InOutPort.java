package com.encore.smartcity.nifiapi.entities.flows.inoutport;

import com.encore.smartcity.nifiapi.entities.flows.BulletinWrapper;
import lombok.Data;

import java.util.List;

@Data
public class InOutPort {

    private String id;
    private String uri;

    private List<BulletinWrapper> bulletins;

    private Component component;
    private Status status;

    private String portType;
}
