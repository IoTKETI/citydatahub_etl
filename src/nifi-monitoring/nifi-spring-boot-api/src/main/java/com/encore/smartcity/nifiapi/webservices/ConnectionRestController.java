package com.encore.smartcity.nifiapi.webservices;

import com.encore.smartcity.nifiapi.entities.flows.connection.Connection;
import com.encore.smartcity.nifiapi.services.ConnectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/connections")
public class ConnectionRestController {

    private ConnectionService connectionService;

    public ConnectionRestController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getConnectionById(@PathVariable("id") String id) throws IOException {
        Map<String, Object> response = new HashMap<>();

        Connection connection = this.connectionService.getConnectionById(id);

        response.put("data", connection);
        response.put("status", true);
        response.put("message", "Get Connection successfully!");

        return response;
    }

}
