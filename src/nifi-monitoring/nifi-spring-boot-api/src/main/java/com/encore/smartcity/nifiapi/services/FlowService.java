package com.encore.smartcity.nifiapi.services;

import com.encore.smartcity.externalconfig.JsonConfigProperties;
import com.encore.smartcity.nifiapi.entities.flows.ProcessGroupFlowResponse;
import com.encore.smartcity.nifiapi.repositories.FlowRepository;
import com.encore.smartcity.nifiapi.responses.flow.FlowStatus;
import com.encore.smartcity.nifiapi.responses.flow.FlowStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Service
public class FlowService {

    @Autowired
    private JsonConfigProperties jsonConfigProperties;

    private FlowRepository flowRepository;

    public FlowService(Retrofit retrofit) {
        this.flowRepository = retrofit.create(FlowRepository.class);
    }

    public Response<ProcessGroupFlowResponse> getRootProcessGroupFlow() throws IOException {

        System.out.println(jsonConfigProperties.getNifiRootFlowId());

        return this.flowRepository.getRootProcessGroupFlow(jsonConfigProperties.getNifiRootFlowId()).execute();
    }

    public Response<FlowStatusResponse<FlowStatus>> getFlowStatus() throws IOException {
        return this.flowRepository.getFlowStatus().execute();
    }


    public Response<ProcessGroupFlowResponse> getProcessGroupFlowById(String id) throws IOException {
        return this.flowRepository.getProcessGroupFlowById(id).execute();
    }
}
