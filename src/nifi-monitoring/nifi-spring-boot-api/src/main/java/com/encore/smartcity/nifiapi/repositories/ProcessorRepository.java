package com.encore.smartcity.nifiapi.repositories;

import com.encore.smartcity.nifiapi.entities.flows.processor.Processor;
import com.encore.smartcity.nifiapi.responses.ProcessorResponse;
import com.encore.smartcity.nifiapi.responses.ResponseCustom;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProcessorRepository {

    @GET(value = "/nifi-api/processors/{id}")
    Call<ResponseCustom<ProcessorResponse>> getProcessorByID(@Path("id") String id);

    @GET(value = "/nifi-api/processors/{id}")
    Call<Processor> getProcessorById(@Path("id") String id);
}
