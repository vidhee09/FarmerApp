package com.ananta.fieldAgent.Parser;

import com.ananta.fieldAgent.Models.AddServiceModel;
import com.ananta.fieldAgent.Models.AllFarmerModel;
import com.ananta.fieldAgent.Models.DeliveryDataModel;
import com.ananta.fieldAgent.Models.DetailModel;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Models.ImageModel;
import com.ananta.fieldAgent.Models.JointSurveyorModel;
import com.ananta.fieldAgent.Models.LoginModel;
import com.ananta.fieldAgent.Models.PumpInstallModel;
import com.ananta.fieldAgent.Models.ServiceModel;
import com.ananta.fieldAgent.Models.SiteReportModel;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    /*--- Login ---*/

    @POST("login")
    Call<LoginModel> getLoginData(@Body HashMap<String, String> login);

    /*--- Otp verify ---*/
    @POST("verify-otp")
    Call<LoginModel> getOtpVerify(@Body HashMap<String, String> otp);

    /*--- Get Dashboard Data ---*/
    @POST("agent/details")
    Call<FarmerModel> getDashboardData(@Body HashMap<String, String> dashboard);

    /*---  Get service details Data ---*/
    @POST("agent/details")
    Call<ServiceModel> getDashboardService(@Body HashMap<String, String> service);

    /*--- Get service request Data  ---*/
    @POST("service-requests")
    Call<AddServiceModel> getAddServiceRequest(@Body HashMap<String, String> serviceRequest);

    /*--- Get delivery request Data  ---*/
    @POST("delivery-requests")
    Call<DeliveryDataModel> getDeliveryData(@Body HashMap<String, String> Delivery);

    /*--- Get site inspection request Data  ---*/
    @POST("site-requests")
    Call<SiteReportModel> getSiteReport(@Body HashMap<String, String> site);

    /*--- Get Pump installation Data  ---*/
    @POST("pump-install")
    Call<PumpInstallModel> getPumpInstallData(@Body HashMap<String, String> site);


    /*--- Get joint surveyor report Data  ---*/
    @POST("jointsurvey-request")
    Call<JointSurveyorModel> getJointSurveyorData(@Body HashMap<String, String> joint);

    /*---  upload Image  ---*/
    @Multipart
    @POST("upload-image")
    Call<ImageModel> uploadImage(@Part MultipartBody.Part image,
                                 @Part("type") String type);


    /*--- Get all farmer Data  ---*/
    @POST("farmer/details")
    Call<AllFarmerModel> getAllFarmerData(@Body HashMap<String, String> allFarmer);

}
