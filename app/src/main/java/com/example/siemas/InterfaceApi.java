package com.example.siemas;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InterfaceApi {

    @FormUrlEncoded
    @POST("api/login_android")
    Call<ResponseBody> login_android(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/logout_android")
    Call<ResponseBody> logout_android(@Field("email") String email, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/get_alokasi_dsbs_pcl")
    Call<ResponseBody> getDsbsPcl(@Field("pencacah") String pencacah, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/get_alokasi_dsrt_pcl")
    Call<ResponseBody> getDsrtPcl(@Field("pencacah") String pencacah, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/get_alokasi_dsbs_pml")
    Call<ResponseBody> getDsbsPml(@Field("pengawas") String pengawas, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/get_alokasi_dsrt_pml")
    Call<ResponseBody> getDsrtPml(@Field("pengawas") String pengawas, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/update_dsrt")
    Call<ResponseBody> updateDsrt(@Field("dsrt") String dsrt, @Header("Authorization") String token);

    @Multipart()
    @POST("api/upload_foto")
    Call<ResponseBody> upload_foto(
            @Header("Authorization") String token,
            @Part MultipartBody.Part file_foto,
            @Part("id_dsrt") RequestBody id_dsrt
    );

    @Multipart()
    @POST("api/upload_data")
    Call<ResponseBody> upload_data(
            @Header("Authorization") String token,
            @Part MultipartBody.Part file_foto,
            @Part("dsrt") RequestBody dsrt
    );

    @FormUrlEncoded
    @POST("api/get_jadwal")
    Call<ResponseBody> getJadwal(@Field("pengawas") String pengawas, @Header("Authorization") String token);


    @FormUrlEncoded
    @POST("api/insert_laporan")
    Call<ResponseBody> insertLaporan(@Field("laporan") String laporan, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/get_laporan")
    Call<ResponseBody> getLaporan(@Field("pengawas") String pengawas, @Header("Authorization") String token);


    @GET("api/get_periode")
    Call<ResponseBody> getPeriodeFromApi();




}
