package com.encore.smartcity.nifiapi.responses;

public class ConnectionResponse {
    private String groupId;
    private String name;
    private String statsLastRefreshed;
    private String sourceId;
    private String sourceName;
    private String destinationId;
    private String destinationName;

    private AggregateSnapshot aggregateSnapshot;

    public ConnectionResponse() {
    }

    public ConnectionResponse(String groupId, String name, String statsLastRefreshed, String sourceId, String sourceName, String destinationId, String destinationName, AggregateSnapshot aggregateSnapshot) {
        this.groupId = groupId;
        this.name = name;
        this.statsLastRefreshed = statsLastRefreshed;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.aggregateSnapshot = aggregateSnapshot;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatsLastRefreshed() {
        return statsLastRefreshed;
    }

    public void setStatsLastRefreshed(String statsLastRefreshed) {
        this.statsLastRefreshed = statsLastRefreshed;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public AggregateSnapshot getAggregateSnapshot() {
        return aggregateSnapshot;
    }

    public void setAggregateSnapshot(AggregateSnapshot aggregateSnapshot) {
        this.aggregateSnapshot = aggregateSnapshot;
    }

    @Override
    public String toString() {
        return "ConnectionResponse{" +
                "groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", statsLastRefreshed='" + statsLastRefreshed + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", destinationId='" + destinationId + '\'' +
                ", destinationName='" + destinationName + '\'' +
                ", aggregateSnapshot=" + aggregateSnapshot +
                '}';
    }
}
