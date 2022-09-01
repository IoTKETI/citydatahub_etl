package com.encore.smartcity.nifiapi.entities.flows.connection;

import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import lombok.Data;

@Data
public class Connection {

    private String id;
    private String uri;

    private Component component;

    private Status status;

    private Integer labelIndex;
    private Integer zIndex;
    private String sourceId;
    private String sourceGroupId;
    private String sourceType;
    private String destinationId;
    private String destinationGroupId;
    private String destinationType;

    // parent
    private ProcessGroup parentProcessGroup;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public ProcessGroup getParentProcessGroup() {
        return parentProcessGroup;
    }

    public void setParentProcessGroup(ProcessGroup parentProcessGroup) {
        this.parentProcessGroup = parentProcessGroup;
    }
}