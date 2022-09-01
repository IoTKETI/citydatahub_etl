package com.encore.smartcity.nifiapi.repositories;

import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroupList;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProcessGroupRepository {

    @GET(value = "/nifi-api/process-groups/{id}")
    Call<ProcessGroup> getProcessGroupById(@Path("id") String id);

    @GET(value = "/nifi-api/process-groups/{id}/process-groups")
    Call<ProcessGroupList> getSubProcessGroupById(@Path("id") String id);

    @Multipart
    @POST(value = "/nifi-api/process-groups/{id}/templates/upload")
    Call<Void> uploadTemplate(@Path("id") String id, @Part MultipartBody.Part template);

}