package com.encore.smartcity.nifiapi.entities.flows;

import com.encore.smartcity.nifiapi.entities.flows.connection.Connection;
import com.encore.smartcity.nifiapi.entities.flows.inoutport.InOutPort;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import com.encore.smartcity.nifiapi.entities.flows.processor.Processor;
import com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup.RemoteProcessGroup;
import lombok.Data;

import java.util.List;

@Data
public class Flow {

    // this is the parent to all below components.
    private ProcessGroup flowInfo;

    private List<ProcessGroup> processGroups;
    private List<RemoteProcessGroup> remoteProcessGroups;
    private List<Processor> processors;
    private List<InOutPort> inputPorts;
    private List<InOutPort> outputPorts;
    private List<Connection> connections;
//    private List<Funnel> funnels;


    public ProcessGroup getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(ProcessGroup flowInfo) {
        this.flowInfo = flowInfo;
    }

    public List<ProcessGroup> getProcessGroups() {
        return processGroups;
    }

    public void setProcessGroups(List<ProcessGroup> processGroups) {
        this.processGroups = processGroups;
    }

    public List<RemoteProcessGroup> getRemoteProcessGroups() {
        return remoteProcessGroups;
    }

    public void setRemoteProcessGroups(List<RemoteProcessGroup> remoteProcessGroups) {
        this.remoteProcessGroups = remoteProcessGroups;
    }

    public List<Processor> getProcessors() {
        return processors;
    }

    public void setProcessors(List<Processor> processors) {
        this.processors = processors;
    }

    public List<InOutPort> getInputPorts() {
        return inputPorts;
    }

    public void setInputPorts(List<InOutPort> inputPorts) {
        this.inputPorts = inputPorts;
    }

    public List<InOutPort> getOutputPorts() {
        return outputPorts;
    }

    public void setOutputPorts(List<InOutPort> outputPorts) {
        this.outputPorts = outputPorts;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
