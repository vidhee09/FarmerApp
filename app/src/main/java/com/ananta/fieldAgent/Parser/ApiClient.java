package com.ananta.fieldAgent.Parser;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

 /*   private static Retrofit retrofit = null;
    private static final int CONNECTION_TIMEOUT = 30; //seconds
    private static final int READ_TIMEOUT = 20; //seconds
    private static final int WRITE_TIMEOUT = 20; //seconds
//    private static String BASE_URL = "https://farmer.idglock.com/api/";
    private static String BASE_URL = "http://192.168.31.145:8000/api/";
    private static String token = "" ;

    public static Retrofit getClient(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor(){
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
//                        .addHeader("Authorization", "Bearer" + " " + token)
                        .build();
                Log.d("token=====","="+"Authorization"+ ":"+ "Bearer" + " " + token);
                httpLoggingInterceptor.intercept(chain);
                return chain.proceed(newRequest);
            }
        }).connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;

    }

    public static void setLoginDetail(@NonNull String token) {
        ApiClient.token = token;
        retrofit = null;
    }*/

    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://farmer.idglock.com/api/";
//  private static String BASE_URL = "http://192.168.1.9:8000/api/";
    private static String token = "";

    public static Retrofit getClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder().build();
//            httpLoggingInterceptor.intercept(chain);
            return chain.proceed(newRequest);
        }).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

//    public static void setLoginDetail(@NonNull String token) {
//        ApiClient.token = token;
//        retrofit = null;
//    }
}
