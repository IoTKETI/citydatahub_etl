package com.encore.smartcity.nifiapi.repositories;

import com.encore.smartcity.nifiapi.entities.flows.ProcessGroupFlowResponse;
import com.encore.smartcity.nifiapi.responses.flow.FlowStatus;
import com.encore.smartcity.nifiapi.responses.flow.FlowStatusResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlowRepository {

    // Special One
    @GET(value = "/nifi-api/flow/process-groups/{id}")
    Call<ProcessGroupFlowResponse> getRootProcessGroupFlow(@Path("id") String id);

    @GET(value = "/nifi-api/flow/process-groups/{id}")
    Call<ProcessGroupFlowResponse> getProcessGroupFlowById(@Path("id") String id);

    @GET(value = "/nifi-api/flow/status")
    Call<FlowStatusResponse<FlowStatus>> getFlowStatus();


}
