package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.nifiapi.services.ResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/resources")
public class ResourcesRestController {

    private ResourceService resourceService;

    public ResourcesRestController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping(value = "")
    public List<String> getNifiResources() {

//        return this.resourceService.getAllNifiProcessGroups();
        return null;

    }
}
