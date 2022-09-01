package com.encore.smartcity.nifiregistryapi.services;

import com.encore.smartcity.nifiregistryapi.entities.Item;
import com.encore.smartcity.nifiregistryapi.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(@Qualifier(value = "retrofitRegistry") Retrofit retrofit) {
        this.itemRepository = retrofit.create(ItemRepository.class);
    }

    public Response<List<Item>> getAllBuckets() throws IOException {
        return this.itemRepository.getAllItems().execute();
    }
}
