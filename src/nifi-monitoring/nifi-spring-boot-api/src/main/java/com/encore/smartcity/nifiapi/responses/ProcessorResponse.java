package com.encore.smartcity.nifiapi.responses;

public class ProcessorResponse {

    private String groupId;
    private String id;
    private String name;
    private String runStatus;
    private String statsLastRefreshed;

    public ProcessorResponse() {
    }

    public ProcessorResponse(String groupId, String id, String name, String runStatus, String statsLastRefreshed) {
        this.groupId = groupId;
        this.id = id;
        this.name = name;
        this.runStatus = runStatus;
        this.statsLastRefreshed = statsLastRefreshed;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getStatsLastRefreshed() {
        return statsLastRefreshed;
    }

    public void setStatsLastRefreshed(String statsLastRefreshed) {
        this.statsLastRefreshed = statsLastRefreshed;
    }

    @Override
    public String toString() {
        return "ProcessorResponse{" +
                "groupId='" + groupId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", runStatus='" + runStatus + '\'' +
                ", statsLastRefreshed='" + statsLastRefreshed + '\'' +
                '}';
    }
}
