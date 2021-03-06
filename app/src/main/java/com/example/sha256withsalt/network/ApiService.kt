package com.hairulharun.intensivecourse.network

import com.example.sha256withsalt.network.ModelRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("register.php")
    fun register(
            @Field("nama") nama: String,
            @Field("email") email: String,
            @Field("alamat") alamat: String,
            @Field("ktp") ktp: String,//change username
            @Field("no_hp") hp: String,
            @Field("tgl_lahir") tglLahir: String,
            @Field("pass") pass: String
    ): Call<ModelRegister>

    @FormUrlEncoded
    @POST("register256.php")
    fun register256(
            @Field("nama") nama: String,
            @Field("email") email: String,
            @Field("alamat") alamat: String,
            @Field("ktp") ktp: String,//change username
            @Field("no_hp") hp: String,
            @Field("tgl_lahir") tglLahir: String,
            @Field("pass") pass: String
    ): Call<ModelRegister>

    @FormUrlEncoded
    @POST("register_normal.php")
    fun registernormal(
            @Field("nama") nama: String,
            @Field("email") email: String,
            @Field("alamat") alamat: String,
            @Field("ktp") ktp: String,//change username
            @Field("no_hp") hp: String,
            @Field("tgl_lahir") tglLahir: String,
            @Field("pass") pass: String
    ): Call<ModelRegister>


}