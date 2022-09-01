package com.encore.smartcity.nifiapi.services;

import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import com.encore.smartcity.nifiapi.entities.flows.processor.Processor;
import com.encore.smartcity.nifiapi.repositories.ProcessGroupRepository;
import com.encore.smartcity.nifiapi.repositories.ProcessorRepository;
import com.encore.smartcity.nifiapi.responses.ProcessorResponse;
import com.encore.smartcity.nifiapi.responses.ResponseCustom;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Service
public class ProcessorService {

    private ProcessorRepository processorRepository;
    private ProcessGroupRepository processGroupRepository;

    public ProcessorService(Retrofit retrofit) {
        this.processorRepository = retrofit.create(ProcessorRepository.class);
        this.processGroupRepository = retrofit.create(ProcessGroupRepository.class);
    }

    public Response<ResponseCustom<ProcessorResponse>> getProcessorByID(String id) throws IOException {
        return this.processorRepository.getProcessorByID(id).execute();
    }

    public Processor getProcessorById(String id) throws IOException {
        Processor processor = this.processorRepository.getProcessorById(id).execute().body();
        if (processor == null)
            return null;
        // Process Group of processor
        String parentId = processor.getComponent().getParentGroupId();
        ProcessGroup processGroup = this.processGroupRepository.getProcessGroupById(parentId).execute().body();

        if (processGroup != null)
            processor.setParentProcessGroup(processGroup);
        return processor;
    }
}
