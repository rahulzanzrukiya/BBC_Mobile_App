package com.bbc.agsolutions.Model;

import java.util.ArrayList;

public class UserType {
    public ArrayList<UserType> getData() {
        return data;
    }

    public void setData(ArrayList<UserType> data) {
        this.data = data;
    }

    private ArrayList<UserType> data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String code;

String user_type_name;

    public String getUser_type_name() {
        return user_type_name;
    }

    public void setUser_type_name(String user_type_name) {
        this.user_type_name = user_type_name;
    }
}
