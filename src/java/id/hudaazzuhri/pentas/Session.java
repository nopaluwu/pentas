/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.preference.PreferenceManager
 *  java.lang.Object
 *  java.lang.String
 */
package id.hudaazzuhri.pentas;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences((Context)context);
    }

    public void clearSession() {
        this.prefs.edit().clear().commit();
    }

    public String getpassword() {
        return this.prefs.getString("password", "");
    }

    public String gettoken() {
        return this.prefs.getString("token", "");
    }

    public String getusename() {
        return this.prefs.getString("usename", "");
    }

    public void setpassword(String string2) {
        this.prefs.edit().putString("password", string2).commit();
    }

    public void settoken(String string2) {
        this.prefs.edit().putString("token", string2).commit();
    }

    public void setusename(String string2) {
        this.prefs.edit().putString("usename", string2).commit();
    }
}

