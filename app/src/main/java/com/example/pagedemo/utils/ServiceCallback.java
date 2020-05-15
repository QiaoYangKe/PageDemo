package com.example.pagedemo.utils;

import android.view.View;

import com.example.pagedemo.models.ErrorInfo;
import com.squareup.okhttp.Request;

import java.io.IOException;

public abstract class ServiceCallback {
    public void onSuccess(Object resultObj) {
    }

    public void onError(ErrorInfo errorInfo) {
    }

    public void onFailure(Request request, IOException e) {
    }
}
