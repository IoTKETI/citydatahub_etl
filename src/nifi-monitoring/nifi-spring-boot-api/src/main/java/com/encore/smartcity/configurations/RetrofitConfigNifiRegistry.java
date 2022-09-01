package com.encore.smartcity.configurations;

import com.encore.smartcity.externalconfig.JsonConfigProperties;
import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.util.Date;

@Configuration
public class RetrofitConfigNifiRegistry {

    private JsonConfigProperties jsonConfigProperties;


    public RetrofitConfigNifiRegistry(JsonConfigProperties jsonConfigProperties) {
        this.jsonConfigProperties = jsonConfigProperties;
    }


    @Bean(name = "clientRegistry")
    public OkHttpClient client() {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        return okHttpClientBuilder.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body()).build();
            return chain.proceed(request);
        }).build();
    }


    @Bean(name = "retrofitRegistry")
    @Autowired
    @Qualifier(value = "clientRegistry")
    public Retrofit retrofit(OkHttpClient client) {

        Gson gson = new GsonBuilder().setLenient().registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        })
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(jsonConfigProperties.getNifiRegistryURL())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }


}
