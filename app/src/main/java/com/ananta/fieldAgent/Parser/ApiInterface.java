package com.ananta.fieldAgent.Parser;

import com.ananta.fieldAgent.Models.LoginModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    /*   Farmer Login   */

    @POST("login")
    Call<LoginModel> getLoginData(@Body HashMap<String,String> login);


}
