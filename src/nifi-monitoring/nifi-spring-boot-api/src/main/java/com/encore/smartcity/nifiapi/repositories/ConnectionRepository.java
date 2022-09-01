package com.encore.smartcity.nifiapi.repositories;

import com.encore.smartcity.nifiapi.entities.flows.connection.Connection;
import com.encore.smartcity.nifiapi.responses.ConnectionResponse;
import com.encore.smartcity.nifiapi.responses.ResponseCustom;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConnectionRepository {

    @GET(value = "/nifi-api/connections/{id}")
    Call<ResponseCustom<ConnectionResponse>> getConnectionByID(@Path("id") String id);

    @GET(value = "/nifi-api/connections/{id}")
    Call<Connection> getConnectionById(@Path("id") String id);
}
