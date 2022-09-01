package com.encore.smartcity.nifiapi.repositories;

import com.encore.smartcity.nifiapi.entities.flows.remoteprocessgroup.RemoteProcessGroup;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RemoteProcessGroupRepository {

    @GET(value = "/nifi-api/remote-process-groups/{id}")
    Call<RemoteProcessGroup> getRemoteProcessGroupById(@Path("id") String id);

}
