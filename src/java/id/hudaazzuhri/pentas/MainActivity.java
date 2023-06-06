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
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.ImageButton
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  android.widget.Toast
 *  com.google.gson.JsonObject
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import id.hudaazzuhri.pentas.DBUjian;
import id.hudaazzuhri.pentas.DBUser;
import id.hudaazzuhri.pentas.ListUjianAdapter;
import id.hudaazzuhri.pentas.LoginActivity;
import id.hudaazzuhri.pentas.Session;
import id.hudaazzuhri.pentas.api.BaseApiService;
import id.hudaazzuhri.pentas.api.UtilsApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity
extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE;
    private BaseApiService baseApiService;
    private ImageButton btnLogout;
    private ImageButton btnRefresh;
    private Button btnStartUjian;
    private ImageButton btnTutorial;
    private DBUjian dbUjian;
    private DBUser dbUser;
    String device_unique_id;
    String imei;
    String[] jam_mulai;
    String[] jam_selesai;
    private ListView lvUjian;
    private Context mContext;
    String[] mapel;
    String[] nilai;
    private ProgressDialog progressDialog;
    private Session session;
    String[] status;
    String[] tanggal;
    private TextView tvAgama;
    private TextView tvKelas;
    private TextView tvNamaSiswa;
    private TextView tvNisn;
    String[] urutan;

    static /* synthetic */ DBUjian access$300(MainActivity mainActivity) {
        return mainActivity.dbUjian;
    }

    static /* synthetic */ ListView access$500(MainActivity mainActivity) {
        return mainActivity.lvUjian;
    }

    private void initComponent() {
        this.dbUser.open();
        Cursor cursor = this.dbUser.fetch();
        this.dbUser.close();
        this.tvNamaSiswa.setText((CharSequence)cursor.getString(cursor.getColumnIndex("nama")));
        this.tvNisn.setText((CharSequence)cursor.getString(cursor.getColumnIndex("nisn")));
        this.tvKelas.setText((CharSequence)cursor.getString(cursor.getColumnIndex("kelas")));
        this.tvAgama.setText((CharSequence)cursor.getString(cursor.getColumnIndex("agama")));
        this.btnRefresh.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity.this.requestMain();
            }
        });
        this.btnLogout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity.this.logout();
            }
        });
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setCancelable(true);
        builder.setTitle((CharSequence)"Logout");
        builder.setMessage((CharSequence)"Apakah Anda yakin ingin keluar dari aplikasi ini?");
        builder.setPositiveButton((CharSequence)"Ya", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                MainActivity mainActivity = MainActivity.this;
                mainActivity.progressDialog = ProgressDialog.show((Context)mainActivity.mContext, null, (CharSequence)"Logout...");
                MainActivity.this.dbUser.open();
                Cursor cursor = MainActivity.this.dbUser.fetch();
                String string2 = cursor.getString(cursor.getColumnIndex("nisn"));
                MainActivity.this.dbUser.close();
                MainActivity.this.baseApiService.logoutRequest(string2).enqueue((Callback)new Callback<JsonObject>(){

                    public void onFailure(Call<JsonObject> call, Throwable throwable) {
                        Toast.makeText((Context)MainActivity.this.mContext, (CharSequence)"Periksa kembali koneksi internet Anda!", (int)0).show();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onFailure: ERROR > ");
                        stringBuilder.append(throwable.toString());
                        Log.e((String)"debug", (String)stringBuilder.toString());
                        MainActivity.this.progressDialog.dismiss();
                    }

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            MainActivity.this.progressDialog.dismiss();
                            try {
                                if (new JSONObject(((JsonObject)response.body()).toString()).getString("success").equals((Object)"true")) {
                                    MainActivity.this.session = new Session(MainActivity.this.mContext);
                                    MainActivity.this.session.clearSession();
                                    Intent intent = new Intent((Context)MainActivity.this, LoginActivity.class);
                                    MainActivity.this.startActivity(intent);
                                    return;
                                }
                                Toast.makeText((Context)MainActivity.this.mContext, (CharSequence)"Logout Gagal", (int)0).show();
                                return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }
                        Toast.makeText((Context)MainActivity.this.mContext, (CharSequence)"Logout Gagal", (int)0).show();
                        MainActivity.this.progressDialog.dismiss();
                    }
                });
            }

        });
        builder.setNegativeButton((CharSequence)"Tidak", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
            }
        });
        builder.create().show();
    }

    private void requestMain() {
        this.progressDialog = ProgressDialog.show((Context)this.mContext, null, (CharSequence)"Sedang mengecek jadwal ujian. Harap tunggu.");
        this.dbUser.open();
        Cursor cursor = this.dbUser.fetch();
        Integer n = cursor.getInt(cursor.getColumnIndex("siswa_id"));
        this.dbUser.close();
        this.baseApiService.listUjianRequest(Integer.toString((int)n)).enqueue((Callback)new Callback<JsonObject>(){

            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                MainActivity.this.progressDialog.dismiss();
                Toast.makeText((Context)MainActivity.this.mContext, (CharSequence)"Koneksi bermasalah!", (int)0).show();
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public void onResponse(Call<JsonObject> var1_1, Response<JsonObject> var2_2) {
                if (!var2_2.isSuccessful()) {
                    MainActivity.access$200(MainActivity.this).dismiss();
                    Toast.makeText((Context)MainActivity.access$400(MainActivity.this), (CharSequence)"Gagal mengecek jadwal!", (int)0).show();
                    return;
                }
                MainActivity.access$200(MainActivity.this).dismiss();
                try {
                    var3_3 = new JSONObject(((JsonObject)var2_2.body()).toString());
                    var5_4 = var3_3.getString("tipe").toString();
                    var6_5 = new JSONArray(var3_3.getString("jadwal").toString());
                    if (var3_3.getString("success").equals((Object)"true")) {
                        MainActivity.this.mapel = new String[var6_5.length()];
                        MainActivity.this.tanggal = new String[var6_5.length()];
                        MainActivity.this.jam_mulai = new String[var6_5.length()];
                        MainActivity.this.jam_selesai = new String[var6_5.length()];
                        MainActivity.this.urutan = new String[var6_5.length()];
                        MainActivity.this.nilai = new String[var6_5.length()];
                        MainActivity.this.status = new String[var6_5.length()];
                        MainActivity.access$300(MainActivity.this).open();
                        MainActivity.access$300(MainActivity.this).deleteAll();
                        var8_6 = var6_5.length();
                        var9_7 = 0;
                    } else {
                        Toast.makeText((Context)MainActivity.access$400(MainActivity.this), (CharSequence)"Tidak ada jadwal ujian hari ini.", (int)1).show();
                        return;
                    }
lbl25: // 2 sources:
                    do {
                        if (var9_7 < var8_6) {
                            block13 : {
                                var15_14 = var6_5.getJSONObject(var9_7);
                                MainActivity.this.mapel[var9_7] = var15_14.getString("n_pelajaran").toString();
                                MainActivity.this.tanggal[var9_7] = var15_14.getString("tanggal_ujian").toString();
                                MainActivity.this.jam_mulai[var9_7] = var15_14.getString("jam_mulai").toString();
                                MainActivity.this.jam_selesai[var9_7] = var15_14.getString("jam_selesai").toString();
                                MainActivity.this.urutan[var9_7] = var15_14.getString("urutan").toString();
                                MainActivity.this.nilai[var9_7] = var15_14.getString("nilai").toString();
                                MainActivity.this.status[var9_7] = var15_14.getString("status").toString();
                                var16_15 = MainActivity.access$300(MainActivity.this);
                                var17_16 = Integer.parseInt((String)var15_14.getString("id"));
                                var18_17 = Integer.parseInt((String)var15_14.getString("jadwal_detail_id"));
                                var19_18 = Integer.parseInt((String)var15_14.getString("pelajaran_id"));
                                var20_19 = var15_14.getString("n_pelajaran");
                                var21_20 = var15_14.getString("jam_mulai");
                                var22_21 = var15_14.getString("jam_selesai");
                                var23_22 = Integer.parseInt((String)var15_14.getString("urutan"));
                                var12_11 = var9_7;
                                var13_12 = var8_6;
                                var14_13 = var6_5;
                                try {
                                    var16_15.insert(var5_4, var17_16, var18_17, var19_18, var20_19, var21_20, var22_21, null, var23_22, null, null);
                                    break;
                                }
                                catch (JSONException var11_9) {
                                    break block13;
                                }
                                catch (JSONException var11_10) {
                                    var12_11 = var9_7;
                                    var13_12 = var8_6;
                                    var14_13 = var6_5;
                                }
                            }
                            Toast.makeText((Context)MainActivity.access$400(MainActivity.this), (CharSequence)"Gagal menyiapkan soal.", (int)0).show();
                            var11_8.printStackTrace();
                            break;
                        }
                        MainActivity.access$300(MainActivity.this).close();
                        var10_23 = new ListUjianAdapter(MainActivity.this, MainActivity.this.mapel, MainActivity.this.tanggal, MainActivity.this.jam_mulai, MainActivity.this.jam_selesai, MainActivity.this.urutan, MainActivity.this.nilai, MainActivity.this.status, var5_4);
                        MainActivity.access$500(MainActivity.this).setAdapter((ListAdapter)var10_23);
                        return;
                        break;
                    } while (true);
                }
                catch (JSONException var4_24) {
                    var4_24.printStackTrace();
                    Toast.makeText((Context)MainActivity.access$400(MainActivity.this), (CharSequence)"Tidak ada jadwal ujian hari ini.", (int)1).show();
                    return;
                }
                var9_7 = var12_11 + 1;
                var8_6 = var13_12;
                var6_5 = var14_13;
                ** while (true)
            }
        });
    }

    public void onBackPressed() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131296285);
        this.mContext = this;
        this.baseApiService = UtilsApi.getApiService();
        this.tvNamaSiswa = (TextView)this.findViewById(2131165367);
        this.tvNisn = (TextView)this.findViewById(2131165369);
        this.tvKelas = (TextView)this.findViewById(2131165365);
        this.tvAgama = (TextView)this.findViewById(2131165363);
        this.btnTutorial = (ImageButton)this.findViewById(2131165225);
        this.btnRefresh = (ImageButton)this.findViewById(2131165222);
        this.btnLogout = (ImageButton)this.findViewById(2131165220);
        this.btnStartUjian = (Button)this.findViewById(2131165224);
        this.lvUjian = (ListView)this.findViewById(2131165291);
        this.dbUser = new DBUser(this.mContext);
        this.dbUjian = new DBUjian(this.mContext);
        this.initComponent();
        this.requestMain();
    }

    public void openTutorial(View view) {
    }

}

