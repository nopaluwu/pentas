/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.widget.Toast
 *  com.google.gson.JsonObject
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  retrofit2.Call
 *  retrofit2.Callback
 *  retrofit2.Response
 */
package id.hudaazzuhri.pentas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;
import com.google.gson.JsonObject;
import id.hudaazzuhri.pentas.DBSoal;
import id.hudaazzuhri.pentas.DBUjian;
import id.hudaazzuhri.pentas.DBUser;
import id.hudaazzuhri.pentas.MainActivity;
import id.hudaazzuhri.pentas.api.BaseApiService;
import id.hudaazzuhri.pentas.api.UtilsApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelesaiUjian {
    BaseApiService baseApiService;
    DBSoal dbSoal;
    DBUjian dbUjian;
    DBUser dbUser;
    Context mContext;
    ProgressDialog progressDialog;

    public SelesaiUjian(Context context) {
        this.mContext = context;
        this.dbSoal = new DBSoal(this.mContext);
        this.dbUjian = new DBUjian(this.mContext);
        this.dbUser = new DBUser(this.mContext);
    }

    public void onUpdate() {
        this.baseApiService = UtilsApi.getApiService();
        this.progressDialog = ProgressDialog.show((Context)this.mContext, null, (CharSequence)"Sedang memproses nilai ujian. Harap tunggu.");
        this.dbUjian.open();
        this.dbUser.open();
        this.dbSoal.open();
        Cursor cursor = this.dbUjian.getUjianAktif();
        Cursor cursor2 = this.dbUser.fetch();
        String string2 = cursor.getString(cursor.getColumnIndex("tipe"));
        Integer.valueOf((int)cursor2.getInt(cursor2.getColumnIndex("siswa_id")));
        Integer n = cursor.getInt(cursor.getColumnIndex("ujian_id"));
        final Integer n2 = cursor.getInt(cursor.getColumnIndex("urutan"));
        String string3 = this.dbSoal.getJawaban().toString();
        this.dbUjian.close();
        this.dbUser.close();
        this.dbSoal.close();
        this.baseApiService.selesaiUjianRequest(string2, Integer.toString((int)n), string3).enqueue((Callback)new Callback<JsonObject>(){

            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Toast.makeText((Context)SelesaiUjian.this.mContext, (CharSequence)"Koneksi bermasalah!", (int)0).show();
                SelesaiUjian.this.progressDialog.dismiss();
            }

            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        if (new JSONObject(((JsonObject)response.body()).toString()).getString("success").equals((Object)"true")) {
                            SelesaiUjian.this.dbUjian.open();
                            SelesaiUjian.this.dbUjian.updateStatusUjian(n2, 2);
                            SelesaiUjian.this.dbUjian.close();
                            SelesaiUjian.this.dbUjian.open();
                            SelesaiUjian.this.dbUjian.updateSisaWaktu(n2, 0);
                            SelesaiUjian.this.dbUjian.close();
                            ((Activity)SelesaiUjian.this.mContext).finish();
                            Intent intent = new Intent(SelesaiUjian.this.mContext, MainActivity.class);
                            intent.addCategory("android.intent.category.HOME");
                            SelesaiUjian.this.mContext.startActivity(intent);
                            SelesaiUjian.this.progressDialog.dismiss();
                            return;
                        }
                        SelesaiUjian.this.progressDialog.dismiss();
                        Toast.makeText((Context)SelesaiUjian.this.mContext, (CharSequence)"Gagal memproses nilai!", (int)0).show();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }
                Toast.makeText((Context)SelesaiUjian.this.mContext, (CharSequence)"Gagal memproses nilai!", (int)0).show();
                SelesaiUjian.this.progressDialog.dismiss();
            }
        });
    }

}

