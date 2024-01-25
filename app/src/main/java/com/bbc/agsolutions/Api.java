package com.bbc.agsolutions;


//import com.dfc.agsolutions.Model.CheckNomberModel;
//import com.dfc.agsolutions.Model.CreatTripModel;
//import com.dfc.agsolutions.Model.CreatePaymentDataModel;
//import com.dfc.agsolutions.Model.CurrantHistoryModel;
//import com.dfc.agsolutions.Model.CurrantTripDataModel;
//import com.dfc.agsolutions.Model.DebitTypeDataModel;
//import com.dfc.agsolutions.Model.DeletModel;
//import com.dfc.agsolutions.Model.DriverListDataModel;
//import com.dfc.agsolutions.Model.ExpensesListDataModel;
//import com.dfc.agsolutions.Model.FatchAggencyDataModel;
//import com.dfc.agsolutions.Model.FatchBHSDDataModel;
//import com.dfc.agsolutions.Model.FatchDriverDataModel;
//import com.dfc.agsolutions.Model.FatchVendorDataModel;
//import com.dfc.agsolutions.Model.FatchVhicalDataModel;
//import com.dfc.agsolutions.Model.GarageDataModel;
//import com.dfc.agsolutions.Model.MyResponseData;
//import com.dfc.agsolutions.Model.OngoingTruckTypeModel;
//import com.dfc.agsolutions.Model.PreviousHistoryDataModel;
//import com.dfc.agsolutions.Model.ProfileModel;
//import com.dfc.agsolutions.Model.ResponseArrayModel;
//import com.dfc.agsolutions.Model.ServiceFatchVhicalDataModel;
//import com.dfc.agsolutions.Model.ServiceStatusDataModel;
//import com.dfc.agsolutions.Model.ServiceTypeDataModel;
//import com.dfc.agsolutions.Model.TodoListDataModel;
//import com.dfc.agsolutions.Model.TripCurrantDataModel;
//import com.dfc.agsolutions.Model.TruckTypeModel;
//import com.dfc.agsolutions.Model.VoucherTypeDataModel;

import com.bbc.agsolutions.Model.CheckNomberModel;
import com.bbc.agsolutions.Model.JoinModel;
import com.bbc.agsolutions.Model.MyResponseData;
import com.bbc.agsolutions.Model.ProfileModel;
import com.bbc.agsolutions.Model.UserDetailsModel;
import com.bbc.agsolutions.Model.UserProfileListModel;
import com.bbc.agsolutions.Model.UserType;
import com.bbc.agsolutions.Model.UserprofileModel;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("check-mobile")
    Call<CheckNomberModel> get_check_mobail(@Query("mobile") String mobail);
//
    @POST("login")
    Call<MyResponseData> get_login(@Query("mobile") String mobail, @Query("password") String password, @Query("astrologer_id") String astrologer_id);
//
//
//    @POST("fetch-branch")
//    Call<ResponseArrayModel> get_branch();
//
//
//    @POST("fetch-driver-current-trip")
//    Call<CurrantHistoryModel> get_currantTrip();
//
//
    @POST("fetch-profile")
    Call<ProfileModel> get_profile();
    @POST("fetch-user")
    Call<UserprofileModel> get_User_profile();
//
//
//    @POST("fetch-vehicle-list")
//    Call<TruckTypeModel> get_vhiclelist(@Query("branch_name") String branch_name, @Query("truck_type") String truck_type);
//
//
//    @POST("update-profile-status")
//    Call<DeletModel> get_deleteaccount();
//
//
//    @POST("fetch-remaing-trip-vehicle")
//    Call<FatchVhicalDataModel> get_fatchvhiclelist(@Query("branch_name") String branch_name);
//
//
//    @POST("fetch-driver-trip-history")
//    Call<PreviousHistoryDataModel> get_previousHistory(@Query("trip_type") String branch_name);
//
//    @POST("fetch-todo")
//    Call<TodoListDataModel> get_TodoList(@Query("branch_name") String branch_name, @Query("todo_type") String todo_type);
//
//
//    @POST("update-todo")
//    Call<TodoListDataModel> get_UpdateList(@Query("todo_id") String todo_id, @Query("branch_name") String branch_name);
//
//    @POST("create-todo")
//    Call<TodoListDataModel> get_CompleteUpdateList(@Query("todo_id") String todo_id, @Query("branch_name") String branch_name);
//
//
//    @POST("fetch-driver-current-trip")
//    Call<CurrantTripDataModel> get_DriverCurrantTrip();
//
//
//    @POST("fetch-voucher-type")
//    Call<VoucherTypeDataModel> get_VoucherType();
//
//
//    @POST("fetch-service-type")
//    Call<ServiceTypeDataModel> get_getServiceType();
//
//
//    @POST("create-service")
//    Call<ServiceStatusDataModel> get_getServiceStatus(@Query("service_date") String service_date, @Query("service_year") String service_year, @Query("service_truck_no") String service_truck_no, @Query("service_garage") String service_garage, @Query("service_km") String service_km, @Query("service_amount") String service_amount, @Query("service_count") String service_count, @Query("service_remarks") String service_remarks, @Query("service_sub_data[]") Map<String, String> service_sub_type[]);
//
//
//    @POST("fetch-payment-debit")
//    Call<DebitTypeDataModel> get_DebitType(@Query("payment_details_voucher_type") String payment_details_voucher_type, @Query("branch_name") String branch_name);
//
//    @POST("create-payment-details")
//    Call<CreatePaymentDataModel> get_CreatePayment(@Query("payment_details_date") String payment_details_date, @Query("payment_details_mode_type") String payment_details_mode_type, @Query("payment_details_voucher_type") String payment_details_voucher_type, @Query("payment_details_debit") String payment_details_debit, @Query("payment_details_amount") String payment_details_amount, @Query("branch_name") String branch_name, @Query("payment_details_transaction") String payment_details_transaction, @Query("payment_details_narration") String payment_details_narration);
//
//
//    @POST("fetch-payment-details")
//    Call<ExpensesListDataModel> get_ExpensesList(@Query("branch_name") String branch_name);
//
//
//    @POST("update-driver-current-trip")
//    Call<TripCurrantDataModel> get_TripCurrant(@Query("trip_id") String trip_id, @Query("trip_status") String trip_status);
//
//
//    @POST("fetch-vendors")
//    Call<GarageDataModel> get_Garage(@Query("branch_name") String trip_id, @Query("vendor_type") String trip_status);
//
//
//    @POST("fetch-driver")
//    Call<DriverListDataModel> get_driverlist(@Query("branch_name") String branch_name);
//
//    @POST("fetch-service-vehicle")
//    Call<ServiceFatchVhicalDataModel> get_servicefatchvhiclelist(@Query("branch_name") String branch_name);
//
//    @POST("fetch-drivers")
//    Call<FatchDriverDataModel> get_fatchdriver(@Query("branch_name") String branch_name);
//
//    @POST("fetch-vendors")
//    Call<FatchVendorDataModel> get_fatchaggent(@Query("branch_name") String branch_name, @Query("vendor_type") String vendor_type);
//
//    @POST("fetch-agency")
//    Call<FatchAggencyDataModel> get_fetch_agency(@Query("branch_name") String branch_name);
//
//    @POST("fetch-vehicle-bhsd")
//    Call<FatchBHSDDataModel> get_fetch_bhsd(@Query("trip_vehicle") String trip_vehicle);
//
//    @POST("fetch-vehicle-list")
//    Call<OngoingTruckTypeModel> get_vhiclelistongoing(@Query("branch_name") String branch_name, @Query("truck_type") String truck_type);
//
//
    @POST("create-join")
    Call<JoinModel> join_BBC(@Query("person_company") String person_company
            , @Query("person_occupation") String person_occupation
            , @Query("person_service") String person_service
            , @Query("person_message") String person_message);

    @POST("sign-up")
    Call<JoinModel> Sign_Up(@Query("person_name") String person_name
            , @Query("person_email") String person_email
            , @Query("person_mobile") String person_mobile
            , @Query("person_user_type") String person_user_type
            , @Query("person_area") String person_area);

    @POST("fetch-user-type")
    Call<UserType> get_User_Type();

    @POST("fetch-user-by-id")
    Call<ProfileModel> user_profile(@Query("user_id") String user_id);

    @POST("fetch-user-details")
    Call<UserDetailsModel> User_Details(@Query("companyshort") String companyshort);

    @POST("fetch-slider")
    Call<SliderModel> Slider_list();

}
