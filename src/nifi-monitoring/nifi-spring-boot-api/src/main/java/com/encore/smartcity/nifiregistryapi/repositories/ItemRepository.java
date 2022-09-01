package com.encore.smartcity.nifiregistryapi.repositories;

import com.encore.smartcity.nifiregistryapi.entities.Item;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ItemRepository {

    @GET(value = "/nifi-registry-api/items")
    Call<List<Item>> getAllItems();

}
