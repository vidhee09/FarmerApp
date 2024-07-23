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
import com.ananta.fieldAgent.Models.PumpInstallModel;
import com.ananta.fieldAgent.Models.SiteReportModel;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    Call<LoginModel> getOtpVerify(@Body HashMap<String, String> otp);

    /*--- Get Dashboard Data ---*/
    @POST("agent/details")
    Call<FarmerModel> getDashboardData(@Body HashMap<String, String> dashboard);

    /*--- Get service request Data  ---*/
    @POST("service-requests")
    Call<AddServiceModel> getAddServiceRequest(@Body HashMap<String, String> serviceRequest);

    /*--- Get delivery request Data  ---*/
    @POST("delivery-requests")
    Call<DeliveryDataModel> addDeliveryReport(@Body HashMap<String, String> Delivery);

    /*--- Get site inspection request Data  ---*/
    @POST("site-requests")
    Call<SiteReportModel> addSiteInspection(@Body HashMap<String, String> site);

    /*--- Get Pump installation Data  ---*/
    @POST("pump-install")
    Call<PumpInstallModel> getPumpInstallData(@Body HashMap<String, String> site);


    /*--- Get joint surveyor report Data  ---*/
    @POST("jointsurvey-request")
    Call<JointSurveyorModel> addJointSurveyorData(@Body HashMap<String, String> joint);

    /*---  upload Image  ---*/
    @Multipart
    @POST("upload-image")
    Call<ImageModel> uploadImage(@Part MultipartBody.Part image, @Part("type") String type);

    /*--- Get all farmer Data  ---*/
    @POST("farmer/details")
    Call<AllFarmerModel> getAllFarmerData(@Body HashMap<String, String> allFarmer);

    /*--- Get current request Data  ---*/
    @POST("agent/details")
    Call<CurrentReqModel> getCurrentRequest(@Body HashMap<String, String> currentReq);

    /*--- Get past request Data  ---*/
    @POST("agent/details")
    Call<CurrentReqModel> getPastRequest(@Body HashMap<String, String> pastReq);

    /*--- site report update  ---*/
    @POST("site-requests/update")
    Call<SiteReportModel> updateSiteReport(@Body HashMap<String, String> updateSite);

    /*--- delivery update  ---*/
    @POST("delivery-requests/update")
    Call<DeliveryDataModel> updateDeliveryReport(@Body HashMap<String, String> updateDelivery);

    /*---  update Pump installation report  ---*/
    @POST("pump-install/update")
    Call<PumpInstallModel> updatePumpInstallReport(@Body HashMap<String, String> pumpUpdate);

    /*---  update Pump installation report  ---*/
    @POST("jointsurvey-request/update")
    Call<JointSurveyorModel> updateJointReport(@Body HashMap<String, String> pumpUpdate);

    /*---  check report status  ---*/
    @POST("report-status")
    Call<CheckStatusModel> checkReportStatus(@Body HashMap<String, String> pumpUpdate);

    /*---  check report status  ---*/
    @GET("site-reports")
    Call<GetSiteData> getSiteReport(@QueryMap Map<String, String> stringMap);

    /*---  check report status  ---*/
    @GET("delivery-reports")
    Call<GetDeliveryData> getDeliveryReport(@QueryMap Map<String, String> stringMap);

    /*---  check report status  ---*/
    @GET("pump-reports")
    Call<GetPumpData> getPumpReport(@QueryMap Map<String, String> stringMap);

    /*---  check report status  ---*/
    @GET("joint-reports")
    Call<GetJointData> getJointReport(@QueryMap Map<String, String> stringMap);

    @POST("farmer-service")
    Call<FarmerServiceResponseModel> getCurrentAndPastData(@Body HashMap<String, String> currentPastData);
}
