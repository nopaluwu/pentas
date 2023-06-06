/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.util.Log
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.AnimationUtils
 *  android.widget.ImageView
 *  android.widget.Toast
 *  com.google.gson.JsonObject
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  org.json.JSONException
 *  org.json.JSONObject
 *  retrofit2.Call
 *  retrofit2.Callback
 *  retrofit2.Response
 */
package id.hudaazzuhri.pentas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import id.hudaazzuhri.pentas.DBUser;
import id.hudaazzuhri.pentas.LoginActivity;
import id.hudaazzuhri.pentas.MainActivity;
import id.hudaazzuhri.pentas.Session;
import id.hudaazzuhri.pentas.api.BaseApiService;
import id.hudaazzuhri.pentas.api.UtilsApi;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity
extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE;
    private BaseApiService baseApiService;
    private DBUser dbUser;
    String device_unique_id;
    String imei;
    private ImageView ivLogoSekolah;
    private ImageView ivLogoSplash;
    private Context mContext;
    private ProgressDialog progressDialog;
    private Session session;

    private void requestCekLoginSession() {
        this.session = new Session(this.mContext);
        String string2 = this.session.getusename();
        String string3 = this.session.getpassword();
        final String string4 = this.session.gettoken();
        this.progressDialog = ProgressDialog.show((Context)this.mContext, null, (CharSequence)"Harap tunggu..");
        this.baseApiService.cekLoginSessionRequest(string2, string3, string4).enqueue((Callback)new Callback<JsonObject>(){

            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Toast.makeText((Context)SplashActivity.this.mContext, (CharSequence)"Periksa kembali koneksi internet Anda!", (int)0).show();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onFailure: ERROR > ");
                stringBuilder.append(throwable.toString());
                Log.e((String)"debug", (String)stringBuilder.toString());
                SplashActivity.this.progressDialog.dismiss();
                SplashActivity.this.requestCekLoginSession();
            }

            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                SplashActivity.this.progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        JSONObject jSONObject = new JSONObject(((JsonObject)response.body()).toString());
                        if (jSONObject.getString("login").equals((Object)"true")) {
                            String string2 = jSONObject.getString("username");
                            String string3 = jSONObject.getString("password");
                            SplashActivity.this.requestLogin(string2, string3, string4);
                            return;
                        }
                        Intent intent = new Intent(SplashActivity.this.mContext, LoginActivity.class);
                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.finish();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }
                Intent intent = new Intent(SplashActivity.this.mContext, LoginActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        });
    }

    private void requestLogin(String string2, String string3, final String string4) {
        this.baseApiService.loginRequest(string2, string3, string4).enqueue((Callback)new Callback<JsonObject>(){

            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Toast.makeText((Context)SplashActivity.this.mContext, (CharSequence)"Periksa kembali koneksi internet Anda!", (int)0).show();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onFailure: ERROR > ");
                stringBuilder.append(throwable.toString());
                Log.e((String)"debug", (String)stringBuilder.toString());
            }

            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jSONObject = new JSONObject(((JsonObject)response.body()).toString());
                        if (jSONObject.getString("success").equals((Object)"true")) {
                            JSONObject jSONObject2 = new JSONObject(jSONObject.getString("siswa"));
                            jSONObject2.getString("id");
                            SplashActivity.this.dbUser = new DBUser(SplashActivity.this.mContext);
                            SplashActivity.this.dbUser.open();
                            SplashActivity.this.dbUser.deleteAll();
                            SplashActivity.this.dbUser.insert(Integer.parseInt((String)jSONObject2.getString("id")), jSONObject2.getString("nisn"), jSONObject2.getString("nama"), jSONObject2.getString("nama_kelas"), jSONObject2.getString("agama"), string4);
                            SplashActivity.this.dbUser.close();
                            SplashActivity.this.session = new Session(SplashActivity.this.mContext);
                            SplashActivity.this.session.setusename(jSONObject2.getString("nisn"));
                            SplashActivity.this.session.setpassword(jSONObject2.getString("nama"));
                            SplashActivity.this.session.settoken(string4);
                            Intent intent = new Intent(SplashActivity.this.mContext, MainActivity.class);
                            SplashActivity.this.startActivity(intent);
                            SplashActivity.this.finish();
                            return;
                        }
                        Toast.makeText((Context)SplashActivity.this.mContext, (CharSequence)jSONObject.getString("msg"), (int)0).show();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }
                Toast.makeText((Context)SplashActivity.this.mContext, (CharSequence)"Login Gagal", (int)0).show();
            }
        });
    }

    public void cekNewVersion() {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            String string2 = Integer.toString((int)packageManager.getPackageInfo((String)this.mContext.getPackageName(), (int)0).versionCode);
            this.baseApiService.cekVersion(string2).enqueue((Callback)new Callback<JsonObject>(){

                public void onFailure(Call<JsonObject> call, Throwable throwable) {
                    Toast.makeText((Context)SplashActivity.this.mContext, (CharSequence)"Periksa kembali koneksi internet Anda!", (int)0).show();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onFailure: ERROR > ");
                    stringBuilder.append(throwable.toString());
                    Log.e((String)"debug", (String)stringBuilder.toString());
                    SplashActivity.this.requestCekLoginSession();
                }

                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        try {
                            if (new JSONObject(((JsonObject)response.body()).toString()).getString("update").equals((Object)"true")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this.mContext);
                                builder.setCancelable(false);
                                builder.setTitle((CharSequence)"Update Aplikasi");
                                builder.setMessage((CharSequence)"Silahkan update aplikasi ini terlebih dahulu!");
                                builder.setPositiveButton((CharSequence)"UPDATE", new DialogInterface.OnClickListener(){

                                    public void onClick(DialogInterface dialogInterface, int n) {
                                        Intent intent = new Intent("android.intent.action.VIEW");
                                        intent.setData(Uri.parse((String)"https://smkn5kotatangerang.sch.id/download/PenTAS.apk"));
                                        SplashActivity.this.startActivity(intent);
                                    }
                                });
                                builder.create().show();
                                return;
                            }
                            SplashActivity.this.requestCekLoginSession();
                            return;
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            return;
                        }
                    }
                    Toast.makeText((Context)SplashActivity.this.mContext, (CharSequence)"Gagal!", (int)0).show();
                }

            });
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131296288);
        this.mContext = this;
        this.baseApiService = UtilsApi.getApiService();
        this.ivLogoSekolah = (ImageView)this.findViewById(2131165269);
        this.ivLogoSplash = (ImageView)this.findViewById(2131165270);
        Animation animation = AnimationUtils.loadAnimation((Context)this, (int)2130771980);
        this.ivLogoSekolah.startAnimation(animation);
        this.ivLogoSplash.startAnimation(animation);
        new Handler().postDelayed(new Runnable(){

            public void run() {
                SplashActivity.this.requestCekLoginSession();
            }
        }, 3000L);
    }

}

