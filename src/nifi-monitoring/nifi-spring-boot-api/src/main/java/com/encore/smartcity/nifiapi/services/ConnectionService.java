package com.encore.smartcity.nifiapi.services;

import com.encore.smartcity.nifiapi.entities.flows.connection.Connection;
import com.encore.smartcity.nifiapi.entities.flows.processgroup.ProcessGroup;
import com.encore.smartcity.nifiapi.repositories.ConnectionRepository;
import com.encore.smartcity.nifiapi.repositories.ProcessGroupRepository;
import com.encore.smartcity.nifiapi.responses.ConnectionResponse;
import com.encore.smartcity.nifiapi.responses.ResponseCustom;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

@Service
public class ConnectionService {

    private ConnectionRepository connectionRepository;
    private ProcessGroupRepository processGroupRepository;

    public ConnectionService(Retrofit retrofit) {
        this.connectionRepository = retrofit.create(ConnectionRepository.class);
        this.processGroupRepository = retrofit.create(ProcessGroupRepository.class);
    }

    public Response<ResponseCustom<ConnectionResponse>> getConnectionByID(String id) throws IOException {
        return this.connectionRepository.getConnectionByID(id).execute();
    }

    public Connection getConnectionById(String id) throws IOException {

        // get parent process group
        Connection connection = this.connectionRepository.getConnectionById(id).execute().body();
        if (connection == null)
            return null;

        ProcessGroup processGroup = this.processGroupRepository.getProcessGroupById(connection.getComponent().getParentGroupId()).execute().body();

        if (processGroup !=  null)
            connection.setParentProcessGroup(processGroup);

        return connection;
    }

}
