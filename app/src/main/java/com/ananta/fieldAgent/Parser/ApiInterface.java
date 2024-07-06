package com.ananta.fieldAgent.Parser;

import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Models.LoginModel;
import com.ananta.fieldAgent.Models.ServiceModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    /*---Login---*/
    @POST("login")
    Call<LoginModel> getLoginData(@Body HashMap<String,String> login);

    /*---Otp verify ---*/
    @POST("verify-otp")
    Call<LoginModel> getOtpVerify(@Body HashMap<String,String> otp);

    /*---Get Service Data---*/
    @POST("services")
    Call<ServiceModel> getServiceData(@Body HashMap<String,String> service);

    /*---Get Dashboard Data---*/
    @POST("agent/details")
    Call<FarmerModel> getDashboardData(@Body HashMap<String,String> dashboard);



}
