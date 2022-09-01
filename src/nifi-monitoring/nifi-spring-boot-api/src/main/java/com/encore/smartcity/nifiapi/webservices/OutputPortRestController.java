package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.nifiapi.entities.flows.inoutport.InOutPort;
import com.encore.smartcity.nifiapi.services.OutputPortService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/output-ports")
public class OutputPortRestController {

    private OutputPortService outputPortService;

    public OutputPortRestController(OutputPortService outputPortService) {
        this.outputPortService = outputPortService;
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getOutputPortById(@PathVariable("id") String id) throws IOException {
        Map<String, Object> response = new HashMap<>();

        InOutPort inOutPort = this.outputPortService.getOutputPortById(id).body();

        response.put("data", inOutPort);
        response.put("status", true);
        response.put("message", "Get Output Port by Id successfully!");

        return response;
    }
}
