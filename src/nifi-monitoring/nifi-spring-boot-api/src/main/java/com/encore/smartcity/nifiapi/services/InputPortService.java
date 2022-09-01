package com.encore.smartcity.nifiapi.services;

import com.encore.smartcity.nifiapi.entities.flows.inoutport.InOutPort;
import com.encore.smartcity.nifiapi.repositories.InputPortRepository;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Service
public class InputPortService {

   private InputPortRepository inputPortRepository;

    public InputPortService(Retrofit retrofit) {
        this.inputPortRepository = retrofit.create(InputPortRepository.class);
    }

    public Response<InOutPort> getInputPortById(String id) throws IOException {
        return this.inputPortRepository.getInputPortById(id).execute();
    }

}
