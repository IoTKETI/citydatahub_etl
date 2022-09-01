package com.encore.smartcity.nifiapi.entities.flows.resources;

public class Resources {

    private String name;
    private String identifier;

    public Resources() {
    }

    public Resources(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
