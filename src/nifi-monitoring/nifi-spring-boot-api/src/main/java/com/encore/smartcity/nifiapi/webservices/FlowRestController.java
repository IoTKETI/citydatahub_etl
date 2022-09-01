package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.externalconfig.JsonConfigProperties;
import com.encore.smartcity.nifiapi.entities.flows.ProcessGroupFlow;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import com.encore.smartcity.nifiapi.responses.flow.FlowStatus;
import com.encore.smartcity.nifiapi.services.FlowService;
import com.encore.smartcity.nifiapi.services.ProcessGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/flow")
public class FlowRestController {

    private FlowService flowService;
    private JsonConfigProperties jsonConfigProperties;
    private ProcessGroupService processGroupService;

    public FlowRestController(FlowService flowService, JsonConfigProperties jsonConfigProperties, ProcessGroupService processGroupService) {
        this.flowService = flowService;
        this.jsonConfigProperties = jsonConfigProperties;
        this.processGroupService = processGroupService;
    }

    @GetMapping(value = "/status")
    public Map<String, Object> getAllComponents() throws IOException {
        Map<String, Object> response = new HashMap<>();

        FlowStatus flowStatus = this.flowService.getFlowStatus().body().getT();

        response.put("data", flowStatus);
        response.put("status", true);
        response.put("message", "Flow status successfully!");

        return response;
    }

    @GetMapping(value = "/root-flow-id")
    public Map<String, Object> getNifiRootFlowId() {
        Map<String, Object> response = new HashMap<>();

        response.put("data", jsonConfigProperties.getNifiRootFlowId());
        response.put("status", true);
        response.put("message", "Get Root Process Group Flow ID successfully!");

        return response;
    }

    @GetMapping(value = "/root")
    public Map<String, Object> getRootProcessGroupFlow() throws IOException {
        Map<String, Object> response = new HashMap<>();

        ProcessGroup flowInfo = this.processGroupService.getProcessGroupById(jsonConfigProperties.getNifiRootFlowId()).body();

        if (flowInfo == null)
            return null;

        System.out.println("=====================");
        System.out.println("flowInfo");
        System.out.println(flowInfo);
        System.out.println("=====================");

        ProcessGroupFlow processGroupFlow = this.flowService.getRootProcessGroupFlow().body().getProcessGroupFlow();

        processGroupFlow.getFlow().setFlowInfo(flowInfo);

        response.put("data", processGroupFlow);
        response.put("status", true);
        response.put("message", "Get Root Process Group Flow successfully!");

        return response;
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getProcessGroupFlowById(@PathVariable("id") String id) throws IOException {
        Map<String, Object> response = new HashMap<>();

        ProcessGroup flowInfo = this.processGroupService.getProcessGroupById(id).body();

        if (flowInfo == null)
            return null;

        ProcessGroupFlow processGroupFlow = this.flowService.getProcessGroupFlowById(id).body().getProcessGroupFlow();

        processGroupFlow.getFlow().setFlowInfo(flowInfo);

        response.put("data", processGroupFlow);
        response.put("status", true);
        response.put("message", "Get Process Group Flow successfully!");

        return response;
    }

}
