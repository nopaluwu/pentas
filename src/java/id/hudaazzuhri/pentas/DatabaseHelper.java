/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  java.lang.String
 */
package id.hudaazzuhri.pentas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper
extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_SOAL = "create table soal(_id INTEGER PRIMARY KEY AUTOINCREMENT, soal_id INTEGER NOT NULL, no_soal TEXT NOT NULL, soal TEXT NOT NULL, pil_a TEXT NOT NULL, pil_b TEXT NOT NULL, pil_c TEXT NOT NULL, pil_d TEXT NOT NULL, pil_e TEXT NOT NULL, kunci TEXT NOT NULL, jawaban TEXT);";
    private static final String CREATE_TABLE_UJIAN = "create table ujian(_id INTEGER PRIMARY KEY AUTOINCREMENT, tipe TEXT NOT NULL, jadwal_id INTEGER NOT NULL, jadwal_detail_id INTEGER NOT NULL, pelajaran_id INTEGER NOT NULL, nama_pelajaran INTEGER NOT NULL, jam_mulai TEXT NOT NULL, jam_selesai TEXT NOT NULL, durasi INTEGER, urutan INTEGER NOT NULL, jawaban TEXT, nilai TEXT, sisa_waktu INTEGER, status INTEGER, ujian_id INTEGER);";
    private static final String CREATE_TABLE_USER = "create table user(_id INTEGER PRIMARY KEY AUTOINCREMENT, siswa_id INTEGER NOT NULL, nisn TEXT NOT NULL, nama TEXT NOT NULL, kelas TEXT NOT NULL, agama TEXT NOT NULL, token TEXT NOT NULL);";
    static final String DB_NAME = "pentas_db";
    static final int DB_VERSION = 11;
    public static final String SOAL_JAWABAN = "jawaban";
    public static final String SOAL_KUNCI = "kunci";
    public static final String SOAL_NO_SOAL = "no_soal";
    public static final String SOAL_PIL_A = "pil_a";
    public static final String SOAL_PIL_B = "pil_b";
    public static final String SOAL_PIL_C = "pil_c";
    public static final String SOAL_PIL_D = "pil_d";
    public static final String SOAL_PIL_E = "pil_e";
    public static final String SOAL_SOAL = "soal";
    public static final String SOAL_SOAL_ID = "soal_id";
    public static final String SOAL_TIPE = "tipe";
    public static final String TABLE_SOAL = "soal";
    public static final String TABLE_UJIAN = "ujian";
    public static final String TABLE_USER = "user";
    public static final String UJIAN_DURASI = "durasi";
    public static final String UJIAN_JADWAL_DETAIL_ID = "jadwal_detail_id";
    public static final String UJIAN_JADWAL_ID = "jadwal_id";
    public static final String UJIAN_JAM_MULAI = "jam_mulai";
    public static final String UJIAN_JAM_SELESAI = "jam_selesai";
    public static final String UJIAN_JAWABAN = "jawaban";
    public static final String UJIAN_NAMA_PELAJARAN = "nama_pelajaran";
    public static final String UJIAN_NILAI = "nilai";
    public static final String UJIAN_PELAJARAN_ID = "pelajaran_id";
    public static final String UJIAN_SISA_WAKTU = "sisa_waktu";
    public static final String UJIAN_STATUS = "status";
    public static final String UJIAN_TIPE = "tipe";
    public static final String UJIAN_UJIAN_ID = "ujian_id";
    public static final String UJIAN_URUTAN = "urutan";
    public static final String USER_AGAMA = "agama";
    public static final String USER_KELAS = "kelas";
    public static final String USER_NAMA = "nama";
    public static final String USER_NISN = "nisn";
    public static final String USER_SISWA_ID = "siswa_id";
    public static final String USER_TOKEN = "token";
    public static final String _ID = "_id";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 11);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_USER);
        sQLiteDatabase.execSQL(CREATE_TABLE_SOAL);
        sQLiteDatabase.execSQL(CREATE_TABLE_UJIAN);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS soal");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ujian");
        this.onCreate(sQLiteDatabase);
    }
}

