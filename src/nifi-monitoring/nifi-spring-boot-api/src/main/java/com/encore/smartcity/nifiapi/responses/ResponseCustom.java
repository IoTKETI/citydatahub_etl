package com.encore.smartcity.nifiapi.responses;

import com.google.gson.annotations.SerializedName;

public class ResponseCustom<T> {

    @SerializedName(value = "status")
    private T t;

    public ResponseCustom() {
    }

    public ResponseCustom(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "ResponseCustom{" +
                "t=" + t +
                '}';
    }
}
