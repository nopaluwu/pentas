/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.Calendar
 *  java.util.Date
 */
package id.hudaazzuhri.pentas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import id.hudaazzuhri.pentas.DBUser;
import id.hudaazzuhri.pentas.MulaiUjian;
import id.hudaazzuhri.pentas.api.BaseApiService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ListUjianAdapter
extends ArrayAdapter<String> {
    private BaseApiService baseApiService;
    Integer button1;
    Integer button2;
    Integer button3;
    private DBUser dbUser;
    private final String[] jam_mulai;
    private final String[] jam_selesai;
    private final Activity mContext;
    private final String[] mapel;
    private final String[] nilai;
    private final String[] status;
    private final String[] tanggal;
    private String tipe;
    private final String[] urutan;

    public ListUjianAdapter(Activity activity, String[] arrstring, String[] arrstring2, String[] arrstring3, String[] arrstring4, String[] arrstring5, String[] arrstring6, String[] arrstring7, String string2) {
        Integer n;
        super((Context)activity, 2131296289, (Object[])arrstring);
        this.button1 = n = Integer.valueOf((int)0);
        this.button2 = n;
        this.button3 = n;
        this.mContext = activity;
        this.mapel = arrstring;
        this.tanggal = arrstring2;
        this.jam_mulai = arrstring3;
        this.jam_selesai = arrstring4;
        this.urutan = arrstring5;
        this.nilai = arrstring6;
        this.status = arrstring7;
        this.tipe = string2;
    }

    public View getView(final int n, View view, ViewGroup viewGroup) {
        View view2 = this.mContext.getLayoutInflater().inflate(2131296289, null, true);
        TextView textView = (TextView)view2.findViewById(2131165372);
        TextView textView2 = (TextView)view2.findViewById(2131165366);
        TextView textView3 = (TextView)view2.findViewById(2131165371);
        TextView textView4 = (TextView)view2.findViewById(2131165375);
        TextView textView5 = (TextView)view2.findViewById(2131165373);
        TextView textView6 = (TextView)view2.findViewById(2131165368);
        Button button = (Button)view2.findViewById(2131165224);
        textView.setText((CharSequence)this.tipe);
        textView2.setText((CharSequence)this.mapel[n]);
        textView3.setText((CharSequence)this.tanggal[n]);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.jam_mulai[n]);
        stringBuilder.append(" - ");
        stringBuilder.append(this.jam_selesai[n]);
        textView4.setText((CharSequence)stringBuilder.toString());
        textView5.setText((CharSequence)this.urutan[n]);
        String[] arrstring = this.nilai;
        if (arrstring[n] != null && !arrstring[n].isEmpty() && !this.nilai[n].equalsIgnoreCase("null")) {
            textView6.setText((CharSequence)this.nilai[n]);
        } else {
            textView6.setText((CharSequence)"-");
        }
        try {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.jam_mulai[n].toString());
            stringBuilder2.append(":00");
            String string2 = stringBuilder2.toString();
            Date date = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(this.jam_selesai[n].toString());
            stringBuilder3.append(":00");
            String string3 = stringBuilder3.toString();
            Date date2 = new SimpleDateFormat("HH:mm:ss").parse(string3);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);
            String string4 = new SimpleDateFormat("HH:mm:ss").format(new Date());
            Date date3 = new SimpleDateFormat("HH:mm:ss").parse(string4);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(date3);
            Date date4 = calendar3.getTime();
            if (date4.after(calendar.getTime()) && date4.before(calendar2.getTime())) {
                if (this.nilai[n] != null && !this.nilai[n].isEmpty() && !this.nilai[n].equalsIgnoreCase("null")) {
                    button.setVisibility(4);
                } else {
                    button.setVisibility(0);
                }
            } else {
                button.setVisibility(4);
            }
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle((CharSequence)"Kerjakan Ujian");
                builder.setMessage((CharSequence)"Apakah Anda yakin ingin mulai mengerjakan ujian ini?");
                builder.setPositiveButton((CharSequence)"Ya", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        new MulaiUjian(view.getContext(), Integer.parseInt((String)ListUjianAdapter.this.urutan[n]));
                    }
                });
                builder.setNegativeButton((CharSequence)"Tidak", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                    }
                });
                builder.create().show();
            }

        });
        return view2;
    }

}

