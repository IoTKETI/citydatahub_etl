package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup.RemoteProcessGroup;
import com.encore.smartcity.nifiapi.services.RemoteProcessGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/api/v1/remote-process-groups")
@RestController
public class RemoteProcessGroupRestController {

    private RemoteProcessGroupService remoteprocessGroupService;

    public RemoteProcessGroupRestController(RemoteProcessGroupService remoteProcessGroupService) {
        this.remoteprocessGroupService = remoteProcessGroupService;
    }

    @GetMapping(value = "/{id}")
        public Map<String, Object> getAllComponents(@PathVariable("id") String id) throws IOException {
            Map<String, Object> response = new HashMap<>();

            RemoteProcessGroup remoteProcessGroup = this.remoteprocessGroupService.getProcessGroupById(id).body();

            response.put("data", remoteProcessGroup);
            response.put("status", true);
            response.put("message", "Get Remote Process Group successfully!");

            return response;
        }
}
