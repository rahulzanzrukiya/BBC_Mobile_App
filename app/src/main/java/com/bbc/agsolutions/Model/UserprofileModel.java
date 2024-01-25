package com.bbc.agsolutions.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserprofileModel {

    private ArrayList<UserprofileModel> data;

    public ArrayList<UserprofileModel> getData() {
        return data;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public void setData(ArrayList<UserprofileModel> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }
    String code;

    String id;
    String name;
    String occupation;
    String company;
    String image;
    String details_view;
    String mobile;
    String whatsapp_number;
    String email;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWhatsapp_number() {
        return whatsapp_number;
    }

    public void setWhatsapp_number(String whatsapp_number) {
        this.whatsapp_number = whatsapp_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails_view() {
        return details_view;
    }

    public void setDetails_view(String details_view) {
        this.details_view = details_view;
    }
}
