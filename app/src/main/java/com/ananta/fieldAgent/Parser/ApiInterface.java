package com.ananta.fieldAgent.Parser;

import com.ananta.fieldAgent.Models.AddServiceModel;
import com.ananta.fieldAgent.Models.AllFarmerModel;
import com.ananta.fieldAgent.Models.CheckStatusModel;
import com.ananta.fieldAgent.Models.CurrentReqModel;
import com.ananta.fieldAgent.Models.DeliveryDataModel;
import com.ananta.fieldAgent.Models.FarmerModel;
import com.ananta.fieldAgent.Models.GetDeliveryData;
import com.ananta.fieldAgent.Models.GetJointData;
import com.ananta.fieldAgent.Models.GetPumpData;
import com.ananta.fieldAgent.Models.GetSiteData;
import com.ananta.fieldAgent.Models.FarmerServiceResponseModel;
import com.ananta.fieldAgent.Models.ImageModel;
import com.ananta.fieldAgent.Models.JointSurveyorModel;
import com.ananta.fieldAgent.Models.LoginModel;
import com.ananta.fieldAgent.Models.OtpResponseModel;
import com.ananta.fieldAgent.Models.PumpInstallModel;
import com.ananta.fieldAgent.Models.SiteReportModel;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    /*--- Login ---*/
    @POST("login")
    Call<LoginModel> getLoginData(@Body HashMap<String, String> login);

    /*--- Otp verify ---*/
    @POST("verify-otp")
    Call<OtpResponseModel> getOtpVerify(@Body HashMap<String, String> otp);

    /*--- Get Dashboard Data ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("agent/details")
    Call<FarmerModel> getDashboardData(@Body HashMap<String, String> dashboard, @Header("Authorization") String auth);

    /*--- Get service request Data  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("service-requests")
    Call<AddServiceModel> getAddServiceRequest(@Body HashMap<String, String> serviceRequest, @Header("Authorization") String auth);

    /*--- Get delivery request Data  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("delivery-requests")
    Call<DeliveryDataModel> addDeliveryReport(@Body HashMap<String, String> Delivery, @Header("Authorization") String auth);

    /*--- Get site inspection request Data  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("site-requests")
    Call<SiteReportModel> addSiteInspection(@Body HashMap<String, String> site,  @Header("Authorization") String auth);

    /*--- Get Pump installation Data  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("pump-install")
    Call<PumpInstallModel> getPumpInstallData(@Body HashMap<String, String> site,  @Header("Authorization") String auth);


    /*--- Get joint surveyor report Data  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("jointsurvey-request")
    Call<JointSurveyorModel> addJointSurveyorData(@Body HashMap<String, String> joint, @Header("Authorization") String auth);

    /*---  upload Image  ---*/

    @Multipart
    @POST("upload-image")
    Call<ImageModel> uploadImage(@Part MultipartBody.Part image, @Part("type") String type, @Header("Authorization") String auth );

    /*--- Get all farmer Data  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("farmer/details")
    Call<AllFarmerModel> getAllFarmerData(@Body HashMap<String, String> allFarmer,  @Header("Authorization") String auth);

    /*--- Get current request Data  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("agent/details")
    Call<CurrentReqModel> getCurrentRequest(@Body HashMap<String, String> currentReq,  @Header("Authorization") String auth);

    /*--- Get past request Data  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("agent/details")
    Call<CurrentReqModel> getPastRequest(@Body HashMap<String, String> pastReq,  @Header("Authorization") String auth);

    /*--- site report update  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("site-requests/update")
    Call<SiteReportModel> updateSiteReport(@Body HashMap<String, String> updateSite, @Header("Authorization") String auth);

    /*--- delivery update  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("delivery-requests/update")
    Call<DeliveryDataModel> updateDeliveryReport(@Body HashMap<String, String> updateDelivery, @Header("Authorization") String auth);

    /*---  update Pump installation report  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("pump-install/update")
    Call<PumpInstallModel> updatePumpInstallReport(@Body HashMap<String, String> pumpUpdate,  @Header("Authorization") String auth);

    /*---  update Pump installation report  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("jointsurvey-request/update")
    Call<JointSurveyorModel> updateJointReport(@Body HashMap<String, String> pumpUpdate,  @Header("Authorization") String auth);

    /*---  check report status  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("report-status")
    Call<CheckStatusModel> checkReportStatus(@Body HashMap<String, String> pumpUpdate,  @Header("Authorization") String auth);

    /*---  check report status  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @GET("site-reports")
    Call<GetSiteData> getSiteReport(@QueryMap Map<String, String> stringMap,  @Header("Authorization") String auth);

    /*---  check report status  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @GET("delivery-reports")
    Call<GetDeliveryData> getDeliveryReport(@QueryMap Map<String, String> stringMap,  @Header("Authorization") String auth);

    /*---  check report status  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @GET("pump-reports")
    Call<GetPumpData> getPumpReport(@QueryMap Map<String, String> stringMap,  @Header("Authorization") String auth);

    /*---  check report status  ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @GET("joint-reports")
    Call<GetJointData> getJointReport(@QueryMap Map<String, String> stringMap,  @Header("Authorization") String auth);

    /*--- current past data ---*/
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("farmer-service")
    Call<FarmerServiceResponseModel> getCurrentAndPastData(@Body HashMap<String, String> currentPastData,  @Header("Authorization") String auth);
}
