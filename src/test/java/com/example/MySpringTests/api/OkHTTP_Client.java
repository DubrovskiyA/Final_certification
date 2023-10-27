package com.example.MySpringTests.api;

import api.LogInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

@Component
public class OkHTTP_Client {
    private OkHttpClient client;
    public OkHTTP_Client(){
        client=new OkHttpClient().newBuilder().addNetworkInterceptor(new LogInterceptor()).build();
    }

    public OkHttpClient getClient() {
        return client;
    }
}
