/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  java.lang.Object
 *  java.lang.String
 *  retrofit2.Call
 *  retrofit2.http.Field
 *  retrofit2.http.FormUrlEncoded
 *  retrofit2.http.POST
 */
package id.hudaazzuhri.pentas.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {
    @FormUrlEncoded
    @POST(value="cekloginsession")
    public Call<JsonObject> cekLoginSessionRequest(@Field(value="username") String var1, @Field(value="password") String var2, @Field(value="imei") String var3);

    @FormUrlEncoded
    @POST(value="cekversion")
    public Call<JsonObject> cekVersion(@Field(value="version") String var1);

    @FormUrlEncoded
    @POST(value="getsoal")
    public Call<JsonObject> getSoalRequest(@Field(value="tipe") String var1, @Field(value="jadwal_detail_id") String var2);

    @FormUrlEncoded
    @POST(value="main")
    public Call<JsonObject> listUjianRequest(@Field(value="siswa_id") String var1);

    @FormUrlEncoded
    @POST(value="login")
    public Call<JsonObject> loginRequest(@Field(value="username") String var1, @Field(value="password") String var2, @Field(value="imei") String var3);

    @FormUrlEncoded
    @POST(value="logout")
    public Call<JsonObject> logoutRequest(@Field(value="username") String var1);

    @FormUrlEncoded
    @POST(value="mulaiujian")
    public Call<JsonObject> mulaiUjianRequest(@Field(value="tipe") String var1, @Field(value="siswa_id") String var2, @Field(value="jadwal_id") String var3, @Field(value="pelajaran_id") String var4, @Field(value="urutan") String var5);

    @FormUrlEncoded
    @POST(value="selesaiujian")
    public Call<JsonObject> selesaiUjianRequest(@Field(value="tipe") String var1, @Field(value="ujian_id") String var2, @Field(value="jawaban_pg") String var3);
}

