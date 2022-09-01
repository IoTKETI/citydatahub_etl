package com.encore.smartcity.nifiapi.services;

import com.encore.smartcity.nifiapi.entities.flows.resources.Resources;
import com.encore.smartcity.nifiapi.entities.flows.resources.ResourcesResponse;
import com.encore.smartcity.nifiapi.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceService {

    private ResourceRepository resourceRepository;

    public ResourceService(Retrofit retrofit) {
        this.resourceRepository = retrofit.create(ResourceRepository.class);
    }

    /*
     * TODO: this method not get all resource, get only processors groups resources
     *  */
    public Map<String, String> getAllNifiProcessGroups() {
        ResourcesResponse resourcesResponse = new ResourcesResponse();
        List<String> processGroupIdList =  new ArrayList<>();
        Map<String, String> resourcesList = new HashMap<>();

        try {
            resourcesResponse = this.resourceRepository.getNifiResources().execute().body();
        } catch (IOException e) {
            return null;
        }

        if (resourcesResponse != null) {
            List<Resources> filterResourcesList = new ArrayList<>();
            for (Resources resources :
                    resourcesResponse.getResources()) {
//                System.out.println(resources);
                if (resources.getIdentifier().length() > 14) {
                    String identifier = resources.getIdentifier().substring(1, 15);
                    if (identifier.equals("process-groups")){
                        Resources pgResource = new Resources();
                        pgResource.setIdentifier(resources.getIdentifier().substring(16));
                        System.out.println("processGroupId : " + resources.getIdentifier().substring(16));
                        System.out.println("name : " + resources.getName());
                        pgResource.setName(resources.getName());
                        resourcesList.put(resources.getIdentifier().substring(16), resources.getName());
//                        System.out.println(identifier);
//                        String processGroupId = resources.getIdentifier().substring(16);
//                        processGroupIdList.add(processGroupId);
                    }
                }
            }
        }
        return resourcesList;
    }
}
