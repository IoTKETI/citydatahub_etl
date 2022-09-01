package com.encore.smartcity.externalconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Component
@PropertySource(value = "file:${user.dir}/configprops.json"
        , factory = JsonPropertySourceFactory.class)
@ConfigurationProperties(prefix = "config")
public class JsonConfigProperties {

    /*
    * TODO: This class is used to read the value of configuration file.
    *   JSON properties and values, and location of this file is in the root folder of the project and filename configprops.json
    * */
    /*{
        "config.nifiRootFlowId": "d175e4b0-016c-1000-1d83-ba9b3dd91ce4",
        "config.nifiPort": 8080,
        "config.runningOS": "MacOS",
        "config.nifiURL": "http://localhost:8080",
        "config.nifiRegistryURL": "http://210.115.182.181:18080"
    }*/
    private String nifiRootFlowId;
    private Integer nifiPort;
    private String runningOS;
    private String nifiURL;
    private String nifiRegistryURL;
}
