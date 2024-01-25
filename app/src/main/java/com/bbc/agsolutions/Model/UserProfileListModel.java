package com.bbc.agsolutions.Model;

import com.google.gson.annotations.SerializedName;

public class UserProfileListModel {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ProfileModel.Profile getData() {
        return data;
    }

    public void setData(ProfileModel.Profile data) {
        this.data = data;
    }

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private ProfileModel.Profile data;

    public static class Profile {

        int id;
        String name;
        String occupation;
        String company;
        String image;
        String product;
        String mobile;
        String whatsapp_number;
        String email;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

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
    }
}
