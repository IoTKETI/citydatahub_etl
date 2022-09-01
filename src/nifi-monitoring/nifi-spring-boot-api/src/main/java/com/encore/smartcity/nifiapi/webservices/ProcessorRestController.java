package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.nifiapi.entities.flows.processor.Processor;
import com.encore.smartcity.nifiapi.services.ProcessorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/api/v1/processors")
@RestController
public class ProcessorRestController {

    private ProcessorService processorService;

    public ProcessorRestController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @GetMapping(value = "/{id}")
        public Map<String, Object> getProcessorById(@PathVariable("id") String id) throws IOException {
            Map<String, Object> response = new HashMap<>();

            Processor processor = this.processorService.getProcessorById(id);

            response.put("data", processor);
            response.put("status", true);
            response.put("message", "Get Processor successfully!");

            return response;
        }
}
