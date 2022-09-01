package com.encore.smartcity.nifiapi.responses;

public class AggregateSnapshot {
    private String id;
    private String groupId;
    private String name;
    private String sourceName;
    private String destinationName;
    private String flowFilesIn;
    private String bytesIn;
    private String input;
    private String flowFilesOut;
    private String bytesOut;
    private String output;
    private String flowFilesQueued;
    private String bytesQueued;
    private String queued;
    private String queuedSize;
    private String queuedCount;
    private String percentUseCount;
    private String percentUseBytes;

    public AggregateSnapshot() {
    }

    public AggregateSnapshot(String id, String groupId, String name, String sourceName, String destinationName, String flowFilesIn, String bytesIn, String input, String flowFilesOut, String bytesOut, String output, String flowFilesQueued, String bytesQueued, String queued, String queuedSize, String queuedCount, String percentUseCount, String percentUseBytes) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.flowFilesIn = flowFilesIn;
        this.bytesIn = bytesIn;
        this.input = input;
        this.flowFilesOut = flowFilesOut;
        this.bytesOut = bytesOut;
        this.output = output;
        this.flowFilesQueued = flowFilesQueued;
        this.bytesQueued = bytesQueued;
        this.queued = queued;
        this.queuedSize = queuedSize;
        this.queuedCount = queuedCount;
        this.percentUseCount = percentUseCount;
        this.percentUseBytes = percentUseBytes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getFlowFilesIn() {
        return flowFilesIn;
    }

    public void setFlowFilesIn(String flowFilesIn) {
        this.flowFilesIn = flowFilesIn;
    }

    public String getBytesIn() {
        return bytesIn;
    }

    public void setBytesIn(String bytesIn) {
        this.bytesIn = bytesIn;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getFlowFilesOut() {
        return flowFilesOut;
    }

    public void setFlowFilesOut(String flowFilesOut) {
        this.flowFilesOut = flowFilesOut;
    }

    public String getBytesOut() {
        return bytesOut;
    }

    public void setBytesOut(String bytesOut) {
        this.bytesOut = bytesOut;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getFlowFilesQueued() {
        return flowFilesQueued;
    }

    public void setFlowFilesQueued(String flowFilesQueued) {
        this.flowFilesQueued = flowFilesQueued;
    }

    public String getBytesQueued() {
        return bytesQueued;
    }

    public void setBytesQueued(String bytesQueued) {
        this.bytesQueued = bytesQueued;
    }

    public String getQueued() {
        return queued;
    }

    public void setQueued(String queued) {
        this.queued = queued;
    }

    public String getQueuedSize() {
        return queuedSize;
    }

    public void setQueuedSize(String queuedSize) {
        this.queuedSize = queuedSize;
    }

    public String getQueuedCount() {
        return queuedCount;
    }

    public void setQueuedCount(String queuedCount) {
        this.queuedCount = queuedCount;
    }

    public String getPercentUseCount() {
        return percentUseCount;
    }

    public void setPercentUseCount(String percentUseCount) {
        this.percentUseCount = percentUseCount;
    }

    public String getPercentUseBytes() {
        return percentUseBytes;
    }

    public void setPercentUseBytes(String percentUseBytes) {
        this.percentUseBytes = percentUseBytes;
    }

    @Override
    public String toString() {
        return "AggregateSnapshot{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", destinationName='" + destinationName + '\'' +
                ", flowFilesIn='" + flowFilesIn + '\'' +
                ", bytesIn='" + bytesIn + '\'' +
                ", input='" + input + '\'' +
                ", flowFilesOut='" + flowFilesOut + '\'' +
                ", bytesOut='" + bytesOut + '\'' +
                ", output='" + output + '\'' +
                ", flowFilesQueued='" + flowFilesQueued + '\'' +
                ", bytesQueued='" + bytesQueued + '\'' +
                ", queued='" + queued + '\'' +
                ", queuedSize='" + queuedSize + '\'' +
                ", queuedCount='" + queuedCount + '\'' +
                ", percentUseCount='" + percentUseCount + '\'' +
                ", percentUseBytes='" + percentUseBytes + '\'' +
                '}';
    }
}
