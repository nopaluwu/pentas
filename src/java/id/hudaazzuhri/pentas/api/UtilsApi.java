/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package id.hudaazzuhri.pentas.api;

import id.hudaazzuhri.pentas.api.BaseApiService;
import id.hudaazzuhri.pentas.api.RetrofitClient;

public class UtilsApi {
    public static final String BASE_URL_API = "http://smkn5tangerangkota.sch.id:5680/api/";

    public static BaseApiService getApiService() {
        return (BaseApiService)RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}

