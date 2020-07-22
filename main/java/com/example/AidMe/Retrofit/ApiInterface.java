package com.example.AidMe.Retrofit;

import com.example.AidMe.Models.TestModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("getid.php")
    Call<TestModel> getid(@Field("id")String id );
    @FormUrlEncoded
    @POST("getidn.php")
    Call<TestModel> getidn(@Field("id")String id );
    @FormUrlEncoded
    @POST("testfile.php")
    Call<TestModel> info(
                         @Field("id") String id);


    @FormUrlEncoded
    @POST("insert.php")
    Call<TestModel> insert(@Field("name") String name,
                           @Field("age") String age,
                           @Field("number")String number);

    @FormUrlEncoded
    @POST("getdoners.php")
    Call<TestModel> getdoners (@Field("id") String id);
    @FormUrlEncoded
    @POST("getneedes.php")
    Call<TestModel> getneedes (@Field("id") String id);

    @FormUrlEncoded
    @POST("deletingdoner.php")
    Call<TestModel> deletingdoner (@Field("pass") String pass);

    @FormUrlEncoded
    @POST("deletingneeders.php")
    Call<TestModel> deletingneeders (@Field("pass") String pass);

    @FormUrlEncoded
    @POST("insertdoner.php")
    Call<TestModel> insertdoner(@Field("name") String name,
                                @Field("phone") String phone,
                                @Field("donating") String donating,
                                @Field("pass") String pass,

                                @Field("lon") String lon,
                           @Field("lat")String lat);
    @FormUrlEncoded
    @POST("insertneed.php")
    Call<TestModel> insertneed(@Field("name") String name,
                                @Field("phone") String phone,
                               @Field("age") String age,
                               @Field("needes") String needes,
                                @Field("pass") String pass,

                                @Field("lon") String lon,
                                @Field("lat")String lat);



}
