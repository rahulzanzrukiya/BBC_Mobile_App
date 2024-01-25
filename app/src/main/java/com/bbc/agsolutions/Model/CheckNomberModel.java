package com.bbc.agsolutions.Model;

import com.google.gson.annotations.SerializedName;

public class CheckNomberModel {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("code")
    private int code;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SerializedName("data")
    private String data;

    @SerializedName("msg")
    private String msg;
}
