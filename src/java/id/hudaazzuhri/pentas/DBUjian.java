/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package id.hudaazzuhri.pentas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import id.hudaazzuhri.pentas.DatabaseHelper;

public class DBUjian {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    public DBUjian(Context context) {
        this.mContext = context;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void deleteAll() {
        this.database.execSQL("delete from ujian");
    }

    public Cursor fetch() {
        String[] arrstring = new String[]{"tipe", "jadwal_id", "jadwal_detail_id", "pelajaran_id", "nama_pelajaran", "jam_mulai", "jam_selesai", "durasi", "urutan", "jawaban", "nilai", "sisa_waktu", "status"};
        Cursor cursor = this.database.query("ujian", arrstring, "status = 1", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchByUrutan(Integer n) {
        String[] arrstring = new String[]{"tipe", "jadwal_id", "jadwal_detail_id", "pelajaran_id", "nama_pelajaran", "jam_mulai", "jam_selesai", "durasi", "urutan", "jawaban", "nilai", "sisa_waktu", "status"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("urutan = ");
        stringBuilder.append((Object)n);
        String string2 = stringBuilder.toString();
        Cursor cursor = this.database.query("ujian", arrstring, string2, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchJamUjian() {
        String[] arrstring = new String[]{"jam_mulai", "jam_selesai"};
        Cursor cursor = this.database.query("ujian", arrstring, "status = 1", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchSisaWaktu() {
        String[] arrstring = new String[]{"sisa_waktu", "jam_mulai", "jam_selesai"};
        Cursor cursor = this.database.query("ujian", arrstring, "status = 1", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchUrutan() {
        String[] arrstring = new String[]{"urutan"};
        Cursor cursor = this.database.query("ujian", arrstring, "status = 1", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getJadwalByUrutan(Integer n) {
        String[] arrstring = new String[]{"tipe", "jadwal_id", "jadwal_detail_id"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("urutan = ");
        stringBuilder.append((Object)n);
        String string2 = stringBuilder.toString();
        Cursor cursor = this.database.query("ujian", arrstring, string2, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getUjianAktif() {
        String[] arrstring = new String[]{"tipe", "jadwal_id", "jadwal_detail_id", "pelajaran_id", "nama_pelajaran", "jam_mulai", "jam_selesai", "durasi", "urutan", "jawaban", "nilai", "sisa_waktu", "status", "ujian_id"};
        Cursor cursor = this.database.query("ujian", arrstring, "status = 1", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insert(String string2, Integer n, Integer n2, Integer n3, String string3, String string4, String string5, Integer n4, Integer n5, String string6, Integer n6) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tipe", string2);
        contentValues.put("jadwal_id", n);
        contentValues.put("jadwal_detail_id", n2);
        contentValues.put("pelajaran_id", n3);
        contentValues.put("nama_pelajaran", string3);
        contentValues.put("jam_mulai", string4);
        contentValues.put("jam_selesai", string5);
        contentValues.put("durasi", n4);
        contentValues.put("urutan", n5);
        contentValues.put("nilai", string6);
        contentValues.put("sisa_waktu", n6);
        this.database.insert("ujian", null, contentValues);
    }

    public DBUjian open() throws SQLException {
        this.dbHelper = new DatabaseHelper(this.mContext);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public int updateSisaWaktu(Integer n, Integer n2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sisa_waktu", n2);
        SQLiteDatabase sQLiteDatabase = this.database;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("urutan = ");
        stringBuilder.append((Object)n);
        return sQLiteDatabase.update("ujian", contentValues, stringBuilder.toString(), null);
    }

    public int updateStatusUjian(Integer n, Integer n2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", n2);
        SQLiteDatabase sQLiteDatabase = this.database;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("urutan = ");
        stringBuilder.append((Object)n);
        return sQLiteDatabase.update("ujian", contentValues, stringBuilder.toString(), null);
    }

    public int updateUjianId(Integer n, Integer n2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ujian_id", n2);
        SQLiteDatabase sQLiteDatabase = this.database;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("urutan = ");
        stringBuilder.append((Object)n);
        return sQLiteDatabase.update("ujian", contentValues, stringBuilder.toString(), null);
    }
}

