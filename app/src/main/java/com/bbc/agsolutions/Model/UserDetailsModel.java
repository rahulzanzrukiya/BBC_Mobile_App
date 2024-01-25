package com.bbc.agsolutions.Model;

import com.google.gson.annotations.SerializedName;

public class UserDetailsModel {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

//    public UserDetailsModel.Profile getData() {
//        return data;
//    }
//
//    public void setData(UserDetailsModel.Profile data) {
//        this.data = data;
//    }

    @SerializedName("code")
    private int code;
    @SerializedName("productimages")
    private UserDetailsModel.Profile productimages;

    public Profile getProductimages() {
        return productimages;
    }

    public void setProductimages(Profile productimages) {
        this.productimages = productimages;
    }

    public static class Profile{
        String id;
        String bbc_profile_id;
        String product_image1;
        String product_image2;
        String product_image3;
        String product_image4;
        String product_image5;
        String product_about_us;
        String created_at;
        String updated_at;
        String name;
        String occupation;
        String category;
        String company;
        String company_short;
        String experience;
        String address;
        String area;
        String address_proof;
        String landline;
        String working_hours;
        String mobile;
        String whatsapp_number;
        String website;
        String dob;
        String age;
        String gender;
        String spouse_name;
        String spouse_dob;
        String product;
        String doa;
        String expertise;
        String image;
        String user_status;
        String details_view;
        String page_link;
        String profile_user_type;
        String community;
        String community_type;
        String community_order;
        String email;
        String email_verified_at;
        String password;
        String cpassword;
        String remember_token;
        String token;
        String last_login;
        String profile_mix;
        String b_paid;
        String p_type;
        String user_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBbc_profile_id() {
            return bbc_profile_id;
        }

        public void setBbc_profile_id(String bbc_profile_id) {
            this.bbc_profile_id = bbc_profile_id;
        }

        public String getProduct_image1() {
            return product_image1;
        }

        public void setProduct_image1(String product_image1) {
            this.product_image1 = product_image1;
        }

        public String getProduct_image2() {
            return product_image2;
        }

        public void setProduct_image2(String product_image2) {
            this.product_image2 = product_image2;
        }

        public String getProduct_image3() {
            return product_image3;
        }

        public void setProduct_image3(String product_image3) {
            this.product_image3 = product_image3;
        }

        public String getProduct_image4() {
            return product_image4;
        }

        public void setProduct_image4(String product_image4) {
            this.product_image4 = product_image4;
        }

        public String getProduct_image5() {
            return product_image5;
        }

        public void setProduct_image5(String product_image5) {
            this.product_image5 = product_image5;
        }

        public String getProduct_about_us() {
            return product_about_us;
        }

        public void setProduct_about_us(String product_about_us) {
            this.product_about_us = product_about_us;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompany_short() {
            return company_short;
        }

        public void setCompany_short(String company_short) {
            this.company_short = company_short;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress_proof() {
            return address_proof;
        }

        public void setAddress_proof(String address_proof) {
            this.address_proof = address_proof;
        }

        public String getLandline() {
            return landline;
        }

        public void setLandline(String landline) {
            this.landline = landline;
        }

        public String getWorking_hours() {
            return working_hours;
        }

        public void setWorking_hours(String working_hours) {
            this.working_hours = working_hours;
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

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getSpouse_name() {
            return spouse_name;
        }

        public void setSpouse_name(String spouse_name) {
            this.spouse_name = spouse_name;
        }

        public String getSpouse_dob() {
            return spouse_dob;
        }

        public void setSpouse_dob(String spouse_dob) {
            this.spouse_dob = spouse_dob;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getDoa() {
            return doa;
        }

        public void setDoa(String doa) {
            this.doa = doa;
        }

        public String getExpertise() {
            return expertise;
        }

        public void setExpertise(String expertise) {
            this.expertise = expertise;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getDetails_view() {
            return details_view;
        }

        public void setDetails_view(String details_view) {
            this.details_view = details_view;
        }

        public String getPage_link() {
            return page_link;
        }

        public void setPage_link(String page_link) {
            this.page_link = page_link;
        }

        public String getProfile_user_type() {
            return profile_user_type;
        }

        public void setProfile_user_type(String profile_user_type) {
            this.profile_user_type = profile_user_type;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getCommunity_type() {
            return community_type;
        }

        public void setCommunity_type(String community_type) {
            this.community_type = community_type;
        }

        public String getCommunity_order() {
            return community_order;
        }

        public void setCommunity_order(String community_order) {
            this.community_order = community_order;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public void setEmail_verified_at(String email_verified_at) {
            this.email_verified_at = email_verified_at;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCpassword() {
            return cpassword;
        }

        public void setCpassword(String cpassword) {
            this.cpassword = cpassword;
        }

        public String getRemember_token() {
            return remember_token;
        }

        public void setRemember_token(String remember_token) {
            this.remember_token = remember_token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLast_login() {
            return last_login;
        }

        public void setLast_login(String last_login) {
            this.last_login = last_login;
        }

        public String getProfile_mix() {
            return profile_mix;
        }

        public void setProfile_mix(String profile_mix) {
            this.profile_mix = profile_mix;
        }

        public String getB_paid() {
            return b_paid;
        }

        public void setB_paid(String b_paid) {
            this.b_paid = b_paid;
        }

        public String getP_type() {
            return p_type;
        }

        public void setP_type(String p_type) {
            this.p_type = p_type;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }
    }

}
