package com.encore.smartcity.nifiapi.entities.flows;

import lombok.Data;

@Data
public class ProcessGroupFlow {

    private String id;
    private String uri;

    private Flow flow;

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }
}
