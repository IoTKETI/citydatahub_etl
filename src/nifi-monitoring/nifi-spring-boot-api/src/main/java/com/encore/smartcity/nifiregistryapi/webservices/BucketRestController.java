package com.encore.smartcity.nifiregistryapi.webservices;

import com.encore.smartcity.nifiregistryapi.entities.Bucket;
import com.encore.smartcity.nifiregistryapi.services.BucketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/buckets")
public class BucketRestController {

    private BucketService flowService;

    public BucketRestController(BucketService flowService) {
        this.flowService = flowService;
    }

    @GetMapping(value = "")
    public Map<String, Object> getAllBuckets() throws IOException {
        Map<String, Object> response = new HashMap<>();

        List<Bucket> bucket = this.flowService.getAllBuckets().body();

        System.out.println(bucket);

        response.put("data", bucket);
        response.put("status", true);
        response.put("message", "Get all buckets successfully!");

        return response;
    }
}
