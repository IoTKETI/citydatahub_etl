package com.encore.smartcity.nifiapi.entities.flows.processgroup;

import com.encore.smartcity.nifiapi.entities.flows.BulletinWrapper;
import com.encore.smartcity.nifiapi.entities.flows.ProcessGroupFlow;
import lombok.Data;

import java.util.List;

@Data
public class ProcessGroup {
    private String id;
    private String uri;

    private List<BulletinWrapper> bulletins;
    private Component component;
    private Status status;

    private Integer runningCount;
    private Integer stoppedCount;
    private Integer invalidCount;
    private Integer disabledCount;
    private Integer activeRemotePortCount;
    private Integer inactiveRemotePortCount;
    private Integer upToDateCount;
    private Integer locallyModifiedCount;
    private Integer staleCount;
    private Integer locallyModifiedAndStaleCount;
    private Integer syncFailureCount;
    private Integer inputPortCount;
    private Integer outputPortCount;

    private ProcessGroupFlow processGroupFlow;

    private Boolean clicked = false;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getRunningCount() {
        return runningCount;
    }

    public void setRunningCount(Integer runningCount) {
        this.runningCount = runningCount;
    }

    public Integer getStoppedCount() {
        return stoppedCount;
    }

    public void setStoppedCount(Integer stoppedCount) {
        this.stoppedCount = stoppedCount;
    }

    public Integer getInvalidCount() {
        return invalidCount;
    }

    public void setInvalidCount(Integer invalidCount) {
        this.invalidCount = invalidCount;
    }

    public Integer getDisabledCount() {
        return disabledCount;
    }

    public void setDisabledCount(Integer disabledCount) {
        this.disabledCount = disabledCount;
    }

    public Integer getActiveRemotePortCount() {
        return activeRemotePortCount;
    }

    public void setActiveRemotePortCount(Integer activeRemotePortCount) {
        this.activeRemotePortCount = activeRemotePortCount;
    }

    public Integer getInactiveRemotePortCount() {
        return inactiveRemotePortCount;
    }

    public void setInactiveRemotePortCount(Integer inactiveRemotePortCount) {
        this.inactiveRemotePortCount = inactiveRemotePortCount;
    }

    public Integer getUpToDateCount() {
        return upToDateCount;
    }

    public void setUpToDateCount(Integer upToDateCount) {
        this.upToDateCount = upToDateCount;
    }

    public Integer getLocallyModifiedCount() {
        return locallyModifiedCount;
    }

    public void setLocallyModifiedCount(Integer locallyModifiedCount) {
        this.locallyModifiedCount = locallyModifiedCount;
    }

    public Integer getStaleCount() {
        return staleCount;
    }

    public void setStaleCount(Integer staleCount) {
        this.staleCount = staleCount;
    }

    public Integer getLocallyModifiedAndStaleCount() {
        return locallyModifiedAndStaleCount;
    }

    public void setLocallyModifiedAndStaleCount(Integer locallyModifiedAndStaleCount) {
        this.locallyModifiedAndStaleCount = locallyModifiedAndStaleCount;
    }

    public Integer getSyncFailureCount() {
        return syncFailureCount;
    }

    public void setSyncFailureCount(Integer syncFailureCount) {
        this.syncFailureCount = syncFailureCount;
    }

    public Integer getInputPortCount() {
        return inputPortCount;
    }

    public void setInputPortCount(Integer inputPortCount) {
        this.inputPortCount = inputPortCount;
    }

    public Integer getOutputPortCount() {
        return outputPortCount;
    }

    public void setOutputPortCount(Integer outputPortCount) {
        this.outputPortCount = outputPortCount;
    }

    public ProcessGroupFlow getProcessGroupFlow() {
        return processGroupFlow;
    }

    public void setProcessGroupFlow(ProcessGroupFlow processGroupFlow) {
        this.processGroupFlow = processGroupFlow;
    }

    public Boolean getClicked() {
        return clicked;
    }

    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }
}
