/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView
 *  android.widget.AbsListView$LayoutParams
 *  android.widget.BaseAdapter
 *  android.widget.Button
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package id.hudaazzuhri.pentas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import id.hudaazzuhri.pentas.DBSoal;
import java.util.List;

public class GridNomorAdapter
extends BaseAdapter {
    List<String> lstSource;
    Context mContext;

    public GridNomorAdapter(List<String> list, Context context) {
        this.lstSource = list;
        this.mContext = context;
    }

    public int getCount() {
        return this.lstSource.size();
    }

    public Object getItem(int n) {
        return this.lstSource.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        DBSoal dBSoal = new DBSoal(this.mContext);
        dBSoal.open();
        Cursor cursor = dBSoal.fetch(n + 1);
        String string2 = cursor.getString(cursor.getColumnIndex("jawaban"));
        dBSoal.close();
        if (view == null) {
            final Button button = new Button(this.mContext);
            button.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(85, 85));
            button.setPadding(10, 10, 10, 10);
            button.setTextSize(16.0f);
            button.setText((CharSequence)this.lstSource.get(n));
            if (string2 != null) {
                button.setBackgroundColor(-12303292);
                button.setTextColor(-1);
            } else {
                button.setBackgroundColor(-1);
                button.setTextColor(-12303292);
            }
            button.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("no_soal", button.getText().toString());
                    ((Activity)GridNomorAdapter.this.mContext).setResult(-1, intent);
                    ((Activity)GridNomorAdapter.this.mContext).finish();
                }
            });
            return button;
        }
        return (Button)view;
    }

}

