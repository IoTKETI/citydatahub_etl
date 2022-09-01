package com.encore.smartcity.nifiapi.entities.flows.processor;

import com.encore.smartcity.nifiapi.entities.flows.BulletinWrapper;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import lombok.Data;

import java.util.List;

@Data
public class Processor {

    private String id;
    private String uri;

    private List<BulletinWrapper> bulletins;

    private Component component;

    private String inputRequirement;

    private Status status;

    private ProcessGroup parentProcessGroup;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<BulletinWrapper> getBulletins() {
        return bulletins;
    }

    public void setBulletins(List<BulletinWrapper> bulletins) {
        this.bulletins = bulletins;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getInputRequirement() {
        return inputRequirement;
    }

    public void setInputRequirement(String inputRequirement) {
        this.inputRequirement = inputRequirement;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ProcessGroup getParentProcessGroup() {
        return parentProcessGroup;
    }

    public void setParentProcessGroup(ProcessGroup parentProcessGroup) {
        this.parentProcessGroup = parentProcessGroup;
    }
}
