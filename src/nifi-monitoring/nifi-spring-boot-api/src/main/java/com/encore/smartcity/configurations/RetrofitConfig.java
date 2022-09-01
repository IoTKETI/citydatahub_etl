package com.encore.smartcity.configurations;

import com.encore.smartcity.externalconfig.JsonConfigProperties;
import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.util.Date;

@Configuration
public class RetrofitConfig {

    private JsonConfigProperties jsonConfigProperties;

    public RetrofitConfig(JsonConfigProperties jsonConfigProperties) {
        this.jsonConfigProperties = jsonConfigProperties;
    }

    @Bean
    public OkHttpClient client() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        return okHttpClientBuilder.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
//                        .header("Content-Type", "application/json")
//                        .header("Authorization", "Basic c2lkYToxMjMxMjM=")
                    .method(original.method(), original.body()).build();
            return chain.proceed(request);
        }).build();
    }

    @Bean
    @Autowired
    public Retrofit retrofit(OkHttpClient client) {

        Gson gson = new GsonBuilder().setLenient().registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        }).create();
        return new Retrofit.Builder()
                .baseUrl(jsonConfigProperties.getNifiURL())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
