package com.example.pagedemo.utils;

import android.app.Activity;
import android.widget.Toast;

import com.example.pagedemo.models.AjaxResponseOfTResult;
import com.example.pagedemo.models.ErrorInfo;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ServiceHttpClient {
    private final MediaType JSON;

    private final OkHttpClient client;
    private final Gson gson;
    private String bootUrl = "http://10.1.10.29:21021/api";


    private String accessToken = "";
    private String encryptedAccessToken = "";

    private static class RequestInstance {
        private static final ServiceHttpClient instance = new ServiceHttpClient();
    }

    private ServiceHttpClient() {
        JSON = MediaType.parse("application/json");

        client = new OkHttpClient();
        gson = new Gson();
    }
    public static ServiceHttpClient getInstance() {
        return RequestInstance.instance;
    }

    public void Post(String url, Object data, final Type resultType, final Activity activity, final ServiceCallback callback) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(data));

        Request.Builder requestBuilder = new Request.Builder()
                .url( bootUrl + url)
                .post(body);

        if (accessToken != null && !accessToken.equals("")) {
            requestBuilder.header("Authorization", "Bearer " + accessToken);
        }
        Request request = requestBuilder.build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (activity != null) {
                    EasyToast.showText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG);
                }

                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AjaxResponseOfTResult resp = gson.fromJson(response.body().string(), AjaxResponseOfTResult.class);
                if (resp.getSuccess()) {
                    callback.onSuccess(gson.fromJson(gson.toJson(resp.getResult()), resultType));
                } else {
                    final ErrorInfo errorInfo = gson.fromJson(gson.toJson(resp.getError()), ErrorInfo.class);
                    if (activity != null) {
                        EasyToast.showText(activity, errorInfo.getMessage() + "\n" + errorInfo.getDetails(), Toast.LENGTH_LONG);
                    }

                    callback.onError(errorInfo);
                }
            }
        });
    }

    public boolean hasToken() {
        return null != accessToken && !"".equals(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEncryptedAccessToken() {
        return encryptedAccessToken;
    }

    public void setEncryptedAccessToken(String encryptedAccessToken) {
        this.encryptedAccessToken = encryptedAccessToken;
    }

    public void Get(String url, Object param, final Type resultType, final Activity activity, final ServiceCallback callback) {
        if (null != param) {
            try {
                url = url + "?" + getQueryString(param);
            } catch (IllegalAccessException e) {
                EasyToast.showText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(bootUrl + url)
                .get();
        if (accessToken != null && !accessToken.equals("")) {
            requestBuilder.header("Authorization", "Bearer " + accessToken);
        }
        Request request = requestBuilder.build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                if (activity != null) {
                    EasyToast.showText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG);
                }

                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AjaxResponseOfTResult resp = gson.fromJson(response.body().string(), AjaxResponseOfTResult.class);
                if (resp.getSuccess()) {
                    callback.onSuccess(gson.fromJson(gson.toJson(resp.getResult()), resultType));
                } else {
                    final ErrorInfo errorInfo = gson.fromJson(gson.toJson(resp.getError()), ErrorInfo.class);
                    if (activity != null) {
                        EasyToast.showText(activity, errorInfo.getMessage() + "\n" + errorInfo.getDetails(), Toast.LENGTH_LONG);
                    }

                    callback.onError(errorInfo);
                }
            }
        });
    }

    public static String getQueryString(Object param) throws IllegalAccessException {
        Class<? extends Object> c = param.getClass();
        Field[] fields = c.getDeclaredFields();
        Map<String, Object> map = new TreeMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(param);
            if (value != null) {
                map.put(name, value);
            }
        }
        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
