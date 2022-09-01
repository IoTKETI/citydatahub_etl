package com.encore.smartcity.nifiregistryapi.entities;

import java.sql.Timestamp;

public class Item {

    private String bucketIdentifier;
    private String bucketName;
    private Timestamp createdTimestamp;
    private Timestamp modifiedTimestamp;
    private String description;
    private String identifier;
    private String name;
    private String type;
    private Integer versionCount;

    public Item() {
    }

    public Item(String bucketIdentifier, String bucketName, Timestamp createdTimestamp, Timestamp modifiedTimestamp, String description, String identifier, String name, String type, Integer versionCount) {
        this.bucketIdentifier = bucketIdentifier;
        this.bucketName = bucketName;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
        this.description = description;
        this.identifier = identifier;
        this.name = name;
        this.type = type;
        this.versionCount = versionCount;
    }

    public String getBucketIdentifier() {
        return bucketIdentifier;
    }

    public void setBucketIdentifier(String bucketIdentifier) {
        this.bucketIdentifier = bucketIdentifier;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Timestamp getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersionCount() {
        return versionCount;
    }

    public void setVersionCount(Integer versionCount) {
        this.versionCount = versionCount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "bucketIdentifier='" + bucketIdentifier + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", modifiedTimestamp=" + modifiedTimestamp +
                ", description='" + description + '\'' +
                ", identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", versionCount=" + versionCount +
                '}';
    }
}
