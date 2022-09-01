package com.encore.smartcity.nifiapi.repositories;

import com.encore.smartcity.nifiapi.entities.flows.inoutport.InOutPort;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InputPortRepository {

    @GET(value = "/nifi-api/input-ports/{id}")
    Call<InOutPort> getInputPortById(@Path("id") String id);
}
