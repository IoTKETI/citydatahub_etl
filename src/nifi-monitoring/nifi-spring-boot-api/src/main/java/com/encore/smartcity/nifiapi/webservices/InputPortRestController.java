package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.nifiapi.entities.flows.inoutport.InOutPort;
import com.encore.smartcity.nifiapi.services.InputPortService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/input-ports")
public class InputPortRestController {

    private InputPortService inputPortService;

    public InputPortRestController(InputPortService inputPortService) {
        this.inputPortService = inputPortService;
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getInputPortById(@PathVariable("id") String id) throws IOException {
        Map<String, Object> response = new HashMap<>();

        InOutPort inOutPort = this.inputPortService.getInputPortById(id).body();

        response.put("data", inOutPort);
        response.put("status", true);
        response.put("message", "Get Input Port by Id successfully!");

        return response;
    }
}
