package com.encore.smartcity.nifiapi.services;

import com.encore.smartcity.nifiapi.entities.flows.inoutport.InOutPort;
import com.encore.smartcity.nifiapi.repositories.OutputPortRepository;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Service
public class OutputPortService {

   private OutputPortRepository outputPortRepository;

    public OutputPortService(Retrofit retrofit) {
        this.outputPortRepository = retrofit.create(OutputPortRepository.class);
    }

    public Response<InOutPort> getOutputPortById(String id) throws IOException {
        return this.outputPortRepository.getOutputPortById(id).execute();
    }

}
