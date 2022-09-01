package com.encore.smartcity.nifiregistryapi.services;

import com.encore.smartcity.nifiregistryapi.entities.Bucket;
import com.encore.smartcity.nifiregistryapi.repositories.BucketRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Service
public class BucketService {

    private BucketRepository bucketRepository;

    public BucketService(@Qualifier(value = "retrofitRegistry") Retrofit retrofit) {
        this.bucketRepository = retrofit.create(BucketRepository.class);
    }

    public Response<List<Bucket>> getAllBuckets() throws IOException {
        return this.bucketRepository.getAllBuckets().execute();
    }
}
