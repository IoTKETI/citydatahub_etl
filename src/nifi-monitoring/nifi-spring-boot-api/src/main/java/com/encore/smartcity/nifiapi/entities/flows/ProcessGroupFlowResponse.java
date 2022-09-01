package com.encore.smartcity.nifiapi.entities.flows;

import lombok.Data;

@Data
public class ProcessGroupFlowResponse {
    private ProcessGroupFlow processGroupFlow;

    public ProcessGroupFlow getProcessGroupFlow() {
        return processGroupFlow;
    }

    public void setProcessGroupFlow(ProcessGroupFlow processGroupFlow) {
        this.processGroupFlow = processGroupFlow;
    }
}
