package com.encore.smartcity.nifiapi.services;

import com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup.RemoteProcessGroup;
import com.encore.smartcity.nifiapi.repositories.RemoteProcessGroupRepository;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Service
public class RemoteProcessGroupService {

    private RemoteProcessGroupRepository remoteProcessGroupRepository;

    public RemoteProcessGroupService(Retrofit retrofit) {
        this.remoteProcessGroupRepository = retrofit.create(RemoteProcessGroupRepository.class);
    }

    public Response<RemoteProcessGroup> getProcessGroupById(String id) throws IOException {
        return this.remoteProcessGroupRepository.getRemoteProcessGroupById(id).execute();
    }
}
