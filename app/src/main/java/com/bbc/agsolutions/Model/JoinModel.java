package com.bbc.agsolutions.Model;

import com.google.gson.annotations.SerializedName;

public class JoinModel {


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @SerializedName("code")
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("data")
    private Profile data;


    @SerializedName("msg")
    private String msg;

    public Profile getData() {
        return data;
    }

    public void setData(Profile data) {
        this.data = data;
    }

    public static class Profile{

        String cpassword;

        public String getCpassword() {
            return cpassword;
        }

        public void setCpassword(String cpassword) {
            this.cpassword = cpassword;
        }
    }

}
