/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.GridView
 *  android.widget.ListAdapter
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package id.hudaazzuhri.pentas;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import id.hudaazzuhri.pentas.DBSoal;
import id.hudaazzuhri.pentas.GridNomorAdapter;
import java.util.ArrayList;
import java.util.List;

public class NomorActivity
extends Activity {
    private DBSoal dbSoal;
    List<String> lstSource = new ArrayList();

    private void setupList() {
        this.dbSoal = new DBSoal((Context)this);
        this.dbSoal.open();
        int n = 1;
        while ((long)n <= this.dbSoal.getRowCount()) {
            this.lstSource.add((Object)Integer.toString((int)n));
            ++n;
        }
        this.dbSoal.close();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131296286);
        this.setupList();
        ((GridView)this.findViewById(2131165258)).setAdapter((ListAdapter)new GridNomorAdapter(this.lstSource, (Context)this));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        this.getWindow().setLayout(-1, -2);
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = 17;
        layoutParams.x = 0;
        layoutParams.y = 0;
        this.getWindow().setAttributes(layoutParams);
    }
}

