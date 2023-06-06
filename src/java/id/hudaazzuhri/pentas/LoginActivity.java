/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.text.Editable
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.Toast
 *  androidx.appcompat.app.AppCompatActivity
 *  com.google.gson.JsonObject
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.util.Random
 *  org.json.JSONException
 *  org.json.JSONObject
 *  retrofit2.Call
 *  retrofit2.Callback
 *  retrofit2.Response
 */
package id.hudaazzuhri.pentas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import id.hudaazzuhri.pentas.DBUser;
import id.hudaazzuhri.pentas.MainActivity;
import id.hudaazzuhri.pentas.Session;
import id.hudaazzuhri.pentas.api.BaseApiService;
import id.hudaazzuhri.pentas.api.UtilsApi;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity
extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE;
    private BaseApiService baseApiService;
    private Button btnLogin;
    private DBUser dbUser;
    String device_unique_id;
    private EditText etPassword;
    private EditText etUsername;
    String imei;
    private Context mContext;
    private ProgressDialog progressDialog;
    private Session session;
    private TextView tvVersion;

    private void initComponents() {
        this.tvVersion = (TextView)this.findViewById(2131165374);
        this.etUsername = (EditText)this.findViewById(2131165248);
        this.etPassword = (EditText)this.findViewById(2131165247);
        this.btnLogin = (Button)this.findViewById(2131165219);
        this.btnLogin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                int n = new Random().nextInt(8999);
                LoginActivity loginActivity = LoginActivity.this;
                loginActivity.requestLogin(loginActivity.etUsername.getText().toString(), LoginActivity.this.etPassword.getText().toString(), Integer.toString((int)n));
            }
        });
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.mContext.getPackageName(), 0);
            TextView textView = this.tvVersion;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("v.");
            stringBuilder.append(packageInfo.versionName);
            textView.setText((CharSequence)stringBuilder.toString());
            return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            nameNotFoundException.printStackTrace();
            return;
        }
    }

    private void requestLogin(String string2, String string3, final String string4) {
        this.progressDialog = ProgressDialog.show((Context)this.mContext, null, (CharSequence)"Login proses...");
        this.baseApiService.loginRequest(string2, string3, string4).enqueue((Callback)new Callback<JsonObject>(){

            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Toast.makeText((Context)LoginActivity.this.mContext, (CharSequence)"Koneksi bermasalah!", (int)0).show();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onFailure: ERROR > ");
                stringBuilder.append(throwable.toString());
                Log.e((String)"debug", (String)stringBuilder.toString());
                LoginActivity.this.progressDialog.dismiss();
            }

            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    LoginActivity.this.progressDialog.dismiss();
                    try {
                        JSONObject jSONObject = new JSONObject(((JsonObject)response.body()).toString());
                        if (jSONObject.getString("success").equals((Object)"true")) {
                            JSONObject jSONObject2 = new JSONObject(jSONObject.getString("siswa"));
                            jSONObject2.getString("id");
                            LoginActivity.this.dbUser = new DBUser(LoginActivity.this.mContext);
                            LoginActivity.this.dbUser.open();
                            LoginActivity.this.dbUser.deleteAll();
                            LoginActivity.this.dbUser.insert(Integer.parseInt((String)jSONObject2.getString("id")), jSONObject2.getString("nisn"), jSONObject2.getString("nama"), jSONObject2.getString("nama_kelas"), jSONObject2.getString("agama"), string4);
                            LoginActivity.this.session = new Session(LoginActivity.this.mContext);
                            LoginActivity.this.session.setusename(jSONObject2.getString("nisn"));
                            LoginActivity.this.session.setpassword(jSONObject2.getString("nama"));
                            LoginActivity.this.session.settoken(string4);
                            Intent intent = new Intent(LoginActivity.this.mContext, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                            return;
                        }
                        Toast.makeText((Context)LoginActivity.this.mContext, (CharSequence)jSONObject.getString("msg"), (int)0).show();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }
                Toast.makeText((Context)LoginActivity.this.mContext, (CharSequence)"Login Gagal", (int)0).show();
                LoginActivity.this.progressDialog.dismiss();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        this.startActivity(intent);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131296284);
        this.mContext = this;
        this.baseApiService = UtilsApi.getApiService();
        this.initComponents();
    }

}

