package com.encore.smartcity.nifiregistryapi.entities;

import java.sql.Timestamp;

public class Bucket {

    private Boolean allowBundleRedeploy;
    private Boolean allowPublicRead;
    private Timestamp createdTimestamp;
    private String identifier;
    private String name;

    public Bucket() {
    }

    public Bucket(Boolean allowBundleRedeploy, Boolean allowPublicRead, Timestamp createdTimestamp, String identifier, String name) {
        this.allowBundleRedeploy = allowBundleRedeploy;
        this.allowPublicRead = allowPublicRead;
        this.createdTimestamp = createdTimestamp;
        this.identifier = identifier;
        this.name = name;
    }

    public Boolean getAllowBundleRedeploy() {
        return allowBundleRedeploy;
    }

    public void setAllowBundleRedeploy(Boolean allowBundleRedeploy) {
        this.allowBundleRedeploy = allowBundleRedeploy;
    }

    public Boolean getAllowPublicRead() {
        return allowPublicRead;
    }

    public void setAllowPublicRead(Boolean allowPublicRead) {
        this.allowPublicRead = allowPublicRead;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
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

    @Override
    public String toString() {
        return "Bucket{" +
                "allowBundleRedeploy=" + allowBundleRedeploy +
                ", allowPublicRead=" + allowPublicRead +
                ", createdTimestamp=" + createdTimestamp +
                ", identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
