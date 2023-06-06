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
 *  android.os.CountDownTimer
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.Window
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.Button
 *  android.widget.ImageButton
 *  android.widget.RadioButton
 *  android.widget.RadioGroup
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.Locale
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
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import id.hudaazzuhri.pentas.DBSoal;
import id.hudaazzuhri.pentas.DBUjian;
import id.hudaazzuhri.pentas.DBUser;
import id.hudaazzuhri.pentas.NomorActivity;
import id.hudaazzuhri.pentas.SelesaiUjian;
import id.hudaazzuhri.pentas.api.BaseApiService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SoalActivity
extends Activity {
    BaseApiService baseApiService;
    private Button btnLanjut;
    private ImageButton btnPilihSoal;
    private ImageButton btnSelesai;
    private CountDownTimer countDownTimer;
    private DBSoal dbSoal;
    private DBUjian dbUjian;
    private DBUser dbUser;
    private Context mContext;
    private int no_soal;
    ProgressDialog progressDialog;
    private RadioButton radioChoiceA;
    private RadioButton radioChoiceB;
    private RadioButton radioChoiceC;
    private RadioButton radioChoiceD;
    private RadioButton radioChoiceE;
    private RadioGroup radioGroup;
    private long timeLeftInMillis;
    private TextView tvDurasi;
    private TextView tvMapel;
    private TextView tvNoSoal;
    private WebView wvChoiceA;
    private WebView wvChoiceB;
    private WebView wvChoiceC;
    private WebView wvChoiceD;
    private WebView wvChoiceE;
    private WebView wvSoal;

    private void displaySoal(int n) {
        this.clearAllButtonChoice();
        this.dbSoal = new DBSoal((Context)this);
        this.dbSoal.open();
        this.dbUjian = new DBUjian((Context)this);
        this.dbUjian.open();
        if ((long)n > this.dbSoal.getRowCount()) {
            n = 1;
        }
        Cursor cursor = this.dbSoal.fetch(n);
        Cursor cursor2 = this.dbUjian.fetch();
        this.tvMapel.setText((CharSequence)cursor2.getString(cursor2.getColumnIndex("nama_pelajaran")));
        this.tvNoSoal.setText((CharSequence)Integer.toString((int)n));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html><html lang=\"en\" xmlns:m=\"http://www.w3.org/1998/Math/MathML\"><head><meta charset=\"utf-8\"><style>    * {        margin: 0;        padding: 0;    }    html, body {        margin: 0;        padding: 0;        font-size: 18px;    }</style><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({extensions: [\"tex2jax.js\"],jax: [\"input/TeX\", \"output/HTML-CSS\"],tex2jax: {inlineMath: [ ['$','$'], [\"\\\\(\",\"\\\\)\"] ],displayMath: [ ['$$','$$'], [\"\\\\[\",\"\\\\]\"] ],processEscapes: true},\"HTML-CSS\": { fonts: [\"TeX\"] }});</script><script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script></head><body>");
        stringBuilder.append("");
        stringBuilder.append(cursor.getString(cursor.getColumnIndex("soal")));
        stringBuilder.append("</body></html>");
        String string2 = stringBuilder.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("<!DOCTYPE html><html lang=\"en\" xmlns:m=\"http://www.w3.org/1998/Math/MathML\"><head><meta charset=\"utf-8\"><style>    * {        margin: 0;        padding: 0;    }    html, body {        margin: 0;        padding: 0;        font-size: 18px;    }</style><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({extensions: [\"tex2jax.js\"],jax: [\"input/TeX\", \"output/HTML-CSS\"],tex2jax: {inlineMath: [ ['$','$'], [\"\\\\(\",\"\\\\)\"] ],displayMath: [ ['$$','$$'], [\"\\\\[\",\"\\\\]\"] ],processEscapes: true},\"HTML-CSS\": { fonts: [\"TeX\"] }});</script><script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script></head><body>");
        stringBuilder2.append("");
        stringBuilder2.append(cursor.getString(cursor.getColumnIndex("pil_a")));
        stringBuilder2.append("</body></html>");
        String string3 = stringBuilder2.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("<!DOCTYPE html><html lang=\"en\" xmlns:m=\"http://www.w3.org/1998/Math/MathML\"><head><meta charset=\"utf-8\"><style>    * {        margin: 0;        padding: 0;    }    html, body {        margin: 0;        padding: 0;        font-size: 18px;    }</style><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({extensions: [\"tex2jax.js\"],jax: [\"input/TeX\", \"output/HTML-CSS\"],tex2jax: {inlineMath: [ ['$','$'], [\"\\\\(\",\"\\\\)\"] ],displayMath: [ ['$$','$$'], [\"\\\\[\",\"\\\\]\"] ],processEscapes: true},\"HTML-CSS\": { fonts: [\"TeX\"] }});</script><script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script></head><body>");
        stringBuilder3.append("");
        stringBuilder3.append(cursor.getString(cursor.getColumnIndex("pil_b")));
        stringBuilder3.append("</body></html>");
        String string4 = stringBuilder3.toString();
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("<!DOCTYPE html><html lang=\"en\" xmlns:m=\"http://www.w3.org/1998/Math/MathML\"><head><meta charset=\"utf-8\"><style>    * {        margin: 0;        padding: 0;    }    html, body {        margin: 0;        padding: 0;        font-size: 18px;    }</style><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({extensions: [\"tex2jax.js\"],jax: [\"input/TeX\", \"output/HTML-CSS\"],tex2jax: {inlineMath: [ ['$','$'], [\"\\\\(\",\"\\\\)\"] ],displayMath: [ ['$$','$$'], [\"\\\\[\",\"\\\\]\"] ],processEscapes: true},\"HTML-CSS\": { fonts: [\"TeX\"] }});</script><script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script></head><body>");
        stringBuilder4.append("");
        stringBuilder4.append(cursor.getString(cursor.getColumnIndex("pil_c")));
        stringBuilder4.append("</body></html>");
        String string5 = stringBuilder4.toString();
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append("<!DOCTYPE html><html lang=\"en\" xmlns:m=\"http://www.w3.org/1998/Math/MathML\"><head><meta charset=\"utf-8\"><style>    * {        margin: 0;        padding: 0;    }    html, body {        margin: 0;        padding: 0;        font-size: 18px;    }</style><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({extensions: [\"tex2jax.js\"],jax: [\"input/TeX\", \"output/HTML-CSS\"],tex2jax: {inlineMath: [ ['$','$'], [\"\\\\(\",\"\\\\)\"] ],displayMath: [ ['$$','$$'], [\"\\\\[\",\"\\\\]\"] ],processEscapes: true},\"HTML-CSS\": { fonts: [\"TeX\"] }});</script><script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script></head><body>");
        stringBuilder5.append("");
        stringBuilder5.append(cursor.getString(cursor.getColumnIndex("pil_d")));
        stringBuilder5.append("</body></html>");
        String string6 = stringBuilder5.toString();
        StringBuilder stringBuilder6 = new StringBuilder();
        stringBuilder6.append("<!DOCTYPE html><html lang=\"en\" xmlns:m=\"http://www.w3.org/1998/Math/MathML\"><head><meta charset=\"utf-8\"><style>    * {        margin: 0;        padding: 0;    }    html, body {        margin: 0;        padding: 0;        font-size: 18px;    }</style><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({extensions: [\"tex2jax.js\"],jax: [\"input/TeX\", \"output/HTML-CSS\"],tex2jax: {inlineMath: [ ['$','$'], [\"\\\\(\",\"\\\\)\"] ],displayMath: [ ['$$','$$'], [\"\\\\[\",\"\\\\]\"] ],processEscapes: true},\"HTML-CSS\": { fonts: [\"TeX\"] }});</script><script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script></head><body>");
        stringBuilder6.append("");
        stringBuilder6.append(cursor.getString(cursor.getColumnIndex("pil_e")));
        stringBuilder6.append("</body></html>");
        String string7 = stringBuilder6.toString();
        this.wvSoal.loadData(string2, "text/html; charset=utf-8", null);
        this.wvChoiceA.loadData(string3, "text/html; charset=utf-8", null);
        this.wvChoiceB.loadData(string4, "text/html; charset=utf-8", null);
        this.wvChoiceC.loadData(string5, "text/html; charset=utf-8", null);
        this.wvChoiceD.loadData(string6, "text/html; charset=utf-8", null);
        this.wvChoiceE.loadData(string7, "text/html; charset=utf-8", null);
        String string8 = cursor.getString(cursor.getColumnIndex("jawaban"));
        if (string8 != null && !string8.isEmpty()) {
            if (string8.equalsIgnoreCase("null")) {
                return;
            }
            String string9 = string8.toString();
            if (string9.contentEquals((CharSequence)"A")) {
                this.radioChoiceA.setChecked(true);
            }
            if (string9.contentEquals((CharSequence)"B")) {
                this.radioChoiceB.setChecked(true);
            }
            if (string9.contentEquals((CharSequence)"C")) {
                this.radioChoiceC.setChecked(true);
            }
            if (string9.contentEquals((CharSequence)"D")) {
                this.radioChoiceD.setChecked(true);
            }
            if (string9.contentEquals((CharSequence)"E")) {
                this.radioChoiceE.setChecked(true);
            }
            this.dbSoal.close();
        }
    }

    private String doubleEscapeTeX(String string2) {
        String string3 = "";
        for (int i = 0; i < string2.length(); ++i) {
            if (string2.charAt(i) == '\'') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string3);
                stringBuilder.append('\\');
                string3 = stringBuilder.toString();
            }
            if (string2.charAt(i) != '\n') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string3);
                stringBuilder.append(string2.charAt(i));
                string3 = stringBuilder.toString();
            }
            if (string2.charAt(i) != '\\') continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append("\\");
            string3 = stringBuilder.toString();
        }
        return string3;
    }

    private void initComponent() {
        this.tvMapel = (TextView)this.findViewById(2131165366);
        this.tvNoSoal = (TextView)this.findViewById(2131165370);
        this.radioChoiceA = (RadioButton)this.findViewById(2131165309);
        this.radioChoiceB = (RadioButton)this.findViewById(2131165310);
        this.radioChoiceC = (RadioButton)this.findViewById(2131165311);
        this.radioChoiceD = (RadioButton)this.findViewById(2131165312);
        this.radioChoiceE = (RadioButton)this.findViewById(2131165313);
        this.tvDurasi = (TextView)this.findViewById(2131165364);
        this.btnSelesai = (ImageButton)this.findViewById(2131165223);
        this.btnPilihSoal = (ImageButton)this.findViewById(2131165221);
        this.btnLanjut = (Button)this.findViewById(2131165218);
        this.wvSoal = (WebView)this.findViewById(2131165387);
        this.wvSoal.getSettings().setJavaScriptEnabled(true);
        this.wvSoal.setWebViewClient(new WebViewClient());
        this.wvChoiceA = (WebView)this.findViewById(2131165382);
        this.wvChoiceA.getSettings().setJavaScriptEnabled(true);
        this.wvChoiceA.setWebViewClient(new WebViewClient());
        this.wvChoiceB = (WebView)this.findViewById(2131165383);
        this.wvChoiceB.getSettings().setJavaScriptEnabled(true);
        this.wvChoiceB.setWebViewClient(new WebViewClient());
        this.wvChoiceC = (WebView)this.findViewById(2131165384);
        this.wvChoiceC.getSettings().setJavaScriptEnabled(true);
        this.wvChoiceC.setWebViewClient(new WebViewClient());
        this.wvChoiceD = (WebView)this.findViewById(2131165385);
        this.wvChoiceD.getSettings().setJavaScriptEnabled(true);
        this.wvChoiceD.setWebViewClient(new WebViewClient());
        this.wvChoiceE = (WebView)this.findViewById(2131165386);
        this.wvChoiceE.getSettings().setJavaScriptEnabled(true);
        this.wvChoiceE.setWebViewClient(new WebViewClient());
        this.dbUjian = new DBUjian(this.mContext);
        this.dbUser = new DBUser(this.mContext);
        this.btnSelesai.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SoalActivity.this.selesai();
            }
        });
        this.btnPilihSoal.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SoalActivity.this.pilihSoal();
            }
        });
        this.btnLanjut.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SoalActivity.this.soalBerikutnya();
            }
        });
        this.radioChoiceA.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SoalActivity.this.clearAllButtonChoice();
                SoalActivity.this.radioChoiceA.setChecked(true);
            }
        });
        this.radioChoiceB.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SoalActivity.this.clearAllButtonChoice();
                SoalActivity.this.radioChoiceB.setChecked(true);
            }
        });
        this.radioChoiceC.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SoalActivity.this.clearAllButtonChoice();
                SoalActivity.this.radioChoiceC.setChecked(true);
            }
        });
        this.radioChoiceD.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SoalActivity.this.clearAllButtonChoice();
                SoalActivity.this.radioChoiceD.setChecked(true);
            }
        });
        this.radioChoiceE.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SoalActivity.this.clearAllButtonChoice();
                SoalActivity.this.radioChoiceE.setChecked(true);
            }
        });
        this.startCountDown();
        this.displaySoal(1);
    }

    private void selesai() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setCancelable(true);
        builder.setTitle((CharSequence)"Selesai");
        builder.setMessage((CharSequence)"Apakah Anda yakin telah selesai dan ingin melanjutkan ujian berikutnya?");
        builder.setPositiveButton((CharSequence)"Ya", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                SoalActivity.this.stopCountDown();
                new SelesaiUjian((Context)SoalActivity.this).onUpdate();
            }
        });
        builder.setNegativeButton((CharSequence)"Tidak", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
            }
        });
        builder.create().show();
    }

    private void startCountDown() {
        try {
            this.dbUjian.open();
            Cursor cursor = this.dbUjian.fetchJamUjian();
            String string2 = cursor.getString(cursor.getColumnIndex("jam_selesai"));
            this.dbUjian.close();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(":00");
            String string3 = stringBuilder.toString();
            Date date = new SimpleDateFormat("HH:mm:ss").parse(string3);
            String string4 = new SimpleDateFormat("HH:mm:ss").format(new Date());
            Date date2 = new SimpleDateFormat("HH:mm:ss").parse(string4);
            this.timeLeftInMillis = (int)(date.getTime() - date2.getTime());
            CountDownTimer countDownTimer = new CountDownTimer(this.timeLeftInMillis, 1000L){

                public void onFinish() {
                    SoalActivity.this.timeLeftInMillis = 0L;
                    SoalActivity.this.updateCountDownText();
                }

                public void onTick(long l) {
                    SoalActivity.this.timeLeftInMillis = l;
                    SoalActivity.this.updateCountDownText();
                }
            };
            this.countDownTimer = countDownTimer.start();
            return;
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
            Toast.makeText((Context)this.mContext, (CharSequence)"Time gagal", (int)0).show();
            return;
        }
    }

    private void updateCountDownText() {
        this.dbUjian.open();
        Cursor cursor = this.dbUjian.fetchUrutan();
        Integer n = cursor.getInt(cursor.getColumnIndex("urutan"));
        this.dbUjian.updateSisaWaktu(n, (int)this.timeLeftInMillis);
        this.dbUjian.close();
        long l = this.timeLeftInMillis;
        int n2 = (int)(l / 1000L) / 60;
        int n3 = (int)(l / 1000L) % 60;
        Locale locale = Locale.getDefault();
        Object[] arrobject = new Object[]{n2, n3};
        String string2 = String.format((Locale)locale, (String)"%02d:%02d", (Object[])arrobject);
        this.tvDurasi.setText((CharSequence)string2);
        if (this.timeLeftInMillis < 10000L) {
            this.tvDurasi.setTextColor(-65536);
        } else {
            this.tvDurasi.setTextColor(-1);
        }
        if (this.timeLeftInMillis == 0L) {
            new SelesaiUjian((Context)this).onUpdate();
        }
    }

    public void clearAllButtonChoice() {
        this.radioChoiceA.setChecked(false);
        this.radioChoiceB.setChecked(false);
        this.radioChoiceC.setChecked(false);
        this.radioChoiceD.setChecked(false);
        this.radioChoiceE.setChecked(false);
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 1 && n2 == -1) {
            this.no_soal = Integer.parseInt((String)intent.getStringExtra("no_soal"));
            this.displaySoal(this.no_soal);
        }
    }

    public void onBackPressed() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getWindow().setFlags(8192, 8192);
        this.setContentView(2131296287);
        this.mContext = this;
        this.initComponent();
    }

    public void pilihSoal() {
        this.startActivityForResult(new Intent((Context)this, NomorActivity.class), 1);
    }

    public void soalBerikutnya() {
        this.no_soal = Integer.parseInt((String)this.tvNoSoal.getText().toString());
        String string2 = this.radioChoiceA.isChecked() ? "A" : (this.radioChoiceB.isChecked() ? "B" : (this.radioChoiceC.isChecked() ? "C" : (this.radioChoiceD.isChecked() ? "D" : (this.radioChoiceE.isChecked() ? "E" : null))));
        this.dbSoal = new DBSoal((Context)this);
        this.dbSoal.open();
        this.dbSoal.update(this.no_soal, string2);
        this.dbSoal.close();
        this.displaySoal(1 + this.no_soal);
    }

    public void stopCountDown() {
        this.countDownTimer.cancel();
    }

}

