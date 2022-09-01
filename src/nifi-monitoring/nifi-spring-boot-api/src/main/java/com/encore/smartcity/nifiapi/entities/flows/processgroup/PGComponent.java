package com.encore.smartcity.nifiapi.entities.flows.processgroup;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PGComponent {

    private String id;
    private String parentGroupId;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PGComponent{" +
                "id='" + id + '\'' +
                ", parentGroupId='" + parentGroupId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
