package com.ananta.fieldAgent.Parser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    String token = "";

    @Override
    public Response intercept(Chain chain) throws IOException {
   
       //rewrite the request to add bearer token
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer "+ token)
                .build();

        return chain.proceed(newRequest);
    }
}