package com.bbc.agsolutions.Model;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Profile getData() {
        return data;
    }

    public void setData(Profile data) {
        this.data = data;
    }

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private Profile data;


    public static class Profile{

        String image;
        String name;
        String company;
        String mobile;
        String email;
        String dob;
        String doa;
        String occupation;
        String product;
        String address;

        int id;
        String whatsapp_number;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWhatsapp_number() {
            return whatsapp_number;
        }

        public void setWhatsapp_number(String whatsapp_number) {
            this.whatsapp_number = whatsapp_number;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getDoa() {
            return doa;
        }

        public void setDoa(String doa) {
            this.doa = doa;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}
