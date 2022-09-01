package com.encore.smartcity.nifiapi.services;

import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroupList;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroups;
import com.encore.smartcity.nifiapi.repositories.ProcessGroupRepository;
import okhttp3.MultipartBody;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Service
public class ProcessGroupService {

    private ProcessGroupRepository processGroupRepository;


    public ProcessGroupService(Retrofit retrofit) {
        this.processGroupRepository = retrofit.create(ProcessGroupRepository.class);
    }

    public Response<ProcessGroup> getProcessGroupById(String id) throws IOException {
        return this.processGroupRepository.getProcessGroupById(id).execute();
    }

    public Void uploadTemplate(String id, MultipartBody.Part template) throws IOException {
        return this.processGroupRepository.uploadTemplate(id, template).execute().body();
    }

    public ProcessGroupList getSubPG(String id) throws IOException {
        return this.processGroupRepository.getSubProcessGroupById(id).execute().body();
    }

}
