package com.encore.smartcity.nifiapi.repositories;

import com.encore.smartcity.nifiapi.entities.flows.resources.ResourcesResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ResourceRepository {

    @GET(value = "/nifi-api/resources")
    Call<ResourcesResponse> getNifiResources();

}
