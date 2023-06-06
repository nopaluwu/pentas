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
 */
package id.hudaazzuhri.pentas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import id.hudaazzuhri.pentas.DatabaseHelper;

public class DBUser {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    public DBUser(Context context) {
        this.mContext = context;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void deleteAll() {
        this.database.execSQL("delete from user");
    }

    public Cursor fetch() {
        String[] arrstring = new String[]{"_id", "siswa_id", "nisn", "nama", "kelas", "agama", "token"};
        Cursor cursor = this.database.query("user", arrstring, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insert(Integer n, String string2, String string3, String string4, String string5, String string6) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("siswa_id", n);
        contentValues.put("nisn", string2);
        contentValues.put("nama", string3);
        contentValues.put("kelas", string4);
        contentValues.put("agama", string5);
        contentValues.put("token", string6);
        this.database.insert("user", null, contentValues);
    }

    public DBUser open() throws SQLException {
        this.dbHelper = new DatabaseHelper(this.mContext);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }
}

