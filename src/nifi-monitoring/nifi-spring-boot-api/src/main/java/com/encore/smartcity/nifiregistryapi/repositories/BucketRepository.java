package com.encore.smartcity.nifiregistryapi.repositories;

import com.encore.smartcity.nifiregistryapi.entities.Bucket;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface BucketRepository {

    @GET(value = "/nifi-registry-api/buckets")
    Call<List<Bucket>> getAllBuckets();

}
