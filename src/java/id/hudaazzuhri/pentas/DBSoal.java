/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.DatabaseUtils
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.util.Log
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package id.hudaazzuhri.pentas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import id.hudaazzuhri.pentas.DatabaseHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBSoal {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    public DBSoal(Context context) {
        this.mContext = context;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void delete(long l) {
        SQLiteDatabase sQLiteDatabase = this.database;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id=");
        stringBuilder.append(l);
        sQLiteDatabase.delete("soal", stringBuilder.toString(), null);
    }

    public void deleteAll() {
        this.database.execSQL("delete from soal");
    }

    public Cursor fetch(int n) {
        String[] arrstring = new String[]{"_id", "soal_id", "no_soal", "soal", "pil_a", "pil_b", "pil_c", "pil_d", "pil_e", "kunci", "jawaban"};
        String[] arrstring2 = new String[]{String.valueOf((int)n)};
        Cursor cursor = this.database.query("soal", arrstring, "no_soal = ?", arrstring2, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JSONArray getJawaban() {
        String[] arrstring = new String[]{"_id", "soal_id", "kunci", "jawaban"};
        Cursor cursor = this.database.query("soal", arrstring, null, null, null, null, null);
        JSONArray jSONArray = new JSONArray();
        cursor.moveToFirst();
        do {
            if (cursor.isAfterLast()) {
                cursor.close();
                Log.d((String)"TAG_NAME", (String)jSONArray.toString());
                return jSONArray;
            }
            int n = cursor.getColumnCount();
            JSONObject jSONObject = new JSONObject();
            for (int i = 0; i < n; ++i) {
                if (cursor.getColumnName(i) == null || cursor.getColumnName(i).equalsIgnoreCase("_id")) continue;
                try {
                    String string2 = cursor.getString(i);
                    if (string2 != null) {
                        Log.d((String)"TAG_NAME", (String)cursor.getString(i));
                        if (cursor.getColumnName(i).equalsIgnoreCase("soal_id")) {
                            jSONObject.put("id", (Object)cursor.getString(i));
                            continue;
                        }
                        jSONObject.put(cursor.getColumnName(i), (Object)cursor.getString(i));
                        continue;
                    }
                    boolean bl = cursor.getColumnName(i).equalsIgnoreCase("soal_id");
                    if (bl) {
                        jSONObject.put("id", (Object)"");
                        continue;
                    }
                    jSONObject.put(cursor.getColumnName(i), (Object)"");
                    continue;
                }
                catch (JSONException jSONException) {
                    Log.d((String)"TAG_NAME", (String)jSONException.getMessage());
                }
            }
            jSONArray.put((Object)jSONObject);
            cursor.moveToNext();
        } while (true);
    }

    public long getRowCount() {
        return DatabaseUtils.queryNumEntries((SQLiteDatabase)this.database, (String)"soal");
    }

    public void insert(Integer n, Integer n2, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("soal_id", n);
        contentValues.put("no_soal", n2);
        contentValues.put("soal", string2);
        contentValues.put("pil_a", string3);
        contentValues.put("pil_b", string4);
        contentValues.put("pil_c", string5);
        contentValues.put("pil_d", string6);
        contentValues.put("pil_e", string7);
        contentValues.put("kunci", string8);
        this.database.insert("soal", null, contentValues);
    }

    public DBSoal open() throws SQLException {
        this.dbHelper = new DatabaseHelper(this.mContext);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public int update(Integer n, String string2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("jawaban", string2);
        SQLiteDatabase sQLiteDatabase = this.database;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("no_soal = ");
        stringBuilder.append((Object)n);
        return sQLiteDatabase.update("soal", contentValues, stringBuilder.toString(), null);
    }
}

