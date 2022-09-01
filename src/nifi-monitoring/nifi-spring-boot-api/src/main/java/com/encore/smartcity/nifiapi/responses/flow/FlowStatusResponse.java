package com.encore.smartcity.nifiapi.responses.flow;

import com.google.gson.annotations.SerializedName;

public class FlowStatusResponse<T> {
    @SerializedName(value = "controllerStatus")
    private T t;

    public FlowStatusResponse() {
    }

    public FlowStatusResponse(T t) {
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
        return "FlowStatusResponse{" +
                "t=" + t +
                '}';
    }
}
