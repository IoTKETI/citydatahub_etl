package com.encore.smartcity.nifiregistryapi.webservices;

import com.encore.smartcity.nifiregistryapi.entities.Item;
import com.encore.smartcity.nifiregistryapi.services.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/items")
public class ItemRestController {

    private ItemService itemService;

    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "")
    public Map<String, Object> getAllBuckets() throws IOException {
        Map<String, Object> response = new HashMap<>();

        List<Item> items = this.itemService.getAllBuckets().body();

        System.out.println(items);

        response.put("data", items);
        response.put("status", true);
        response.put("message", "Get all items successfully!");

        return response;
    }
}
