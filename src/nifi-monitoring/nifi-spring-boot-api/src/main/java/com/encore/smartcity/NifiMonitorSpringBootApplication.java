package com.encore.smartcity;

import com.encore.smartcity.utils.Const;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;

@SpringBootApplication
@EnableSwagger2
public class NifiMonitorSpringBootApplication  {

    public static void main(String[] args) {

//        System.setProperty("javax.net.ssl.trustStore", "file:/Applications/nifi-registry/conftruststore.jks");
//        System.setProperty("javax.net.ssl.trustStorePassword", "F0NU1YwDlS8Zk3FL9B+30NaRSueE7z9HbuJ4tQo0KMk");




        String fullPath = System.getProperty("user.dir") + "/" + Const.nifiProcessId;
        File file = new File(fullPath);

        if (file.delete()) {
            System.out.println("Delete file successfully!");
        }

        SpringApplication.run(NifiMonitorSpringBootApplication.class, args);
    }
}
