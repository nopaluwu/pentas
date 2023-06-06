/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.database.Cursor
 *  android.util.Log
 *  android.widget.Toast
 *  com.google.gson.JsonObject
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
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

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.JsonObject;
import id.hudaazzuhri.pentas.DBSoal;
import id.hudaazzuhri.pentas.DBUjian;
import id.hudaazzuhri.pentas.DBUser;
import id.hudaazzuhri.pentas.api.BaseApiService;
import id.hudaazzuhri.pentas.api.UtilsApi;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MulaiUjian {
    BaseApiService baseApiService;
    DBSoal dbSoal;
    DBUjian dbUjian;
    DBUser dbUser;
    Context mContext;
    Integer no_soal;
    ProgressDialog progressDialog;
    Integer soal_id;
    private Integer urutan;

    public MulaiUjian(Context context, Integer n) {
        this.mContext = context;
        this.dbSoal = new DBSoal(this.mContext);
        this.dbUjian = new DBUjian(this.mContext);
        this.dbUser = new DBUser(this.mContext);
        this.baseApiService = UtilsApi.getApiService();
        this.setDataUjian(n);
        this.updateStatusUjian(n);
    }

    public void setDataUjian(final Integer n) {
        this.progressDialog = ProgressDialog.show((Context)this.mContext, null, (CharSequence)"Harap tunggu...");
        this.dbUjian.open();
        this.dbUser.open();
        Cursor cursor = this.dbUjian.fetchByUrutan(n);
        Cursor cursor2 = this.dbUser.fetch();
        String string2 = cursor.getString(cursor.getColumnIndex("tipe"));
        Integer n2 = cursor2.getInt(cursor2.getColumnIndex("siswa_id"));
        Integer n3 = cursor.getInt(cursor.getColumnIndex("jadwal_id"));
        Integer n4 = cursor.getInt(cursor.getColumnIndex("pelajaran_id"));
        this.dbUjian.close();
        this.dbUser.close();
        this.baseApiService.mulaiUjianRequest(string2, Integer.toString((int)n2), Integer.toString((int)n3), Integer.toString((int)n4), Integer.toString((int)n)).enqueue((Callback)new Callback<JsonObject>(){

            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                MulaiUjian.this.progressDialog.dismiss();
                Toast.makeText((Context)MulaiUjian.this.mContext, (CharSequence)"Koneksi bermasalah!", (int)0).show();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onFailure: ERROR > ");
                stringBuilder.append(throwable.toString());
                Log.e((String)"debug", (String)stringBuilder.toString());
            }

            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    MulaiUjian.this.progressDialog.dismiss();
                    try {
                        JSONObject jSONObject = new JSONObject(((JsonObject)response.body()).toString());
                        if (jSONObject.getString("success").equals((Object)"true")) {
                            Integer n2 = Integer.parseInt((String)jSONObject.getString("ujian_id").toString());
                            MulaiUjian.this.dbUjian.open();
                            MulaiUjian.this.dbUjian.updateUjianId(n, n2);
                            MulaiUjian.this.dbUjian.close();
                            MulaiUjian.this.setSoalToLocal(n);
                            return;
                        }
                        Toast.makeText((Context)MulaiUjian.this.mContext, (CharSequence)"Gagal mengambil & menampilkan jadwal!", (int)0).show();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }
                MulaiUjian.this.progressDialog.dismiss();
                Toast.makeText((Context)MulaiUjian.this.mContext, (CharSequence)"Gagal mengambil & menampilkan jadwal!", (int)0).show();
            }
        });
    }

    public void setSoalToLocal(Integer n) {
        this.progressDialog = ProgressDialog.show((Context)this.mContext, null, (CharSequence)"Sedang menyiapkan soal. Harap tunggu.");
        this.dbUjian.open();
        Cursor cursor = this.dbUjian.getJadwalByUrutan(n);
        String string2 = cursor.getString(cursor.getColumnIndex("tipe"));
        Integer.valueOf((int)cursor.getInt(cursor.getColumnIndex("jadwal_id")));
        Integer n2 = cursor.getInt(cursor.getColumnIndex("jadwal_detail_id"));
        this.dbUjian.close();
        this.baseApiService.getSoalRequest(string2, Integer.toString((int)n2)).enqueue((Callback)new Callback<JsonObject>(){

            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Toast.makeText((Context)MulaiUjian.this.mContext, (CharSequence)"Koneksi bermasalah!", (int)0).show();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onFailure: ERROR > ");
                stringBuilder.append(throwable.toString());
                Log.e((String)"debug", (String)stringBuilder.toString());
                MulaiUjian.this.progressDialog.dismiss();
            }

            /*
             * Exception decompiling
             */
            public void onResponse(Call<JsonObject> var1_1, Response<JsonObject> var2_2) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 3[CATCHBLOCK]
                // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
                // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
                // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
                // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
                // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
                // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
                // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.g.p(Method.java:396)
                // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
                // org.benf.cfr.reader.entities.d.c(ClassFile.java:773)
                // org.benf.cfr.reader.entities.d.e(ClassFile.java:870)
                // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
                // org.benf.cfr.reader.b.a(Driver.java:128)
                // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
                // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
                // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
                // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
                // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
                // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1137)
                // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:637)
                // java.lang.Thread.run(Thread.java:1012)
                throw new IllegalStateException("Decompilation failed");
            }
        });
    }

    public void updateStatusUjian(Integer n) {
        this.dbUjian.open();
        this.dbUjian.updateStatusUjian(n, 1);
        this.dbUjian.close();
    }

}

