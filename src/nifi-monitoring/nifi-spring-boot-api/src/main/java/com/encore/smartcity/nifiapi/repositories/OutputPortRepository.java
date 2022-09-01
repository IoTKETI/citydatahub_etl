package com.encore.smartcity.nifiapi.repositories;

import com.encore.smartcity.nifiapi.entities.flows.inoutport.InOutPort;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OutputPortRepository {

    @GET(value = "/nifi-api/output-ports/{id}")
    Call<InOutPort> getOutputPortById(@Path("id") String id);
}
