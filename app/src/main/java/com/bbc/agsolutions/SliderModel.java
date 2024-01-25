package com.bbc.agsolutions;

import java.util.ArrayList;

public class SliderModel {

    private ArrayList<SliderModel> data;

    public ArrayList<SliderModel> getData() {
        return data;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public void setData(ArrayList<SliderModel> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    String code;
    String id;
    String slider_heading;
    String slider_button_text;
    String slider_image;
    String slider_link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlider_heading() {
        return slider_heading;
    }

    public void setSlider_heading(String slider_heading) {
        this.slider_heading = slider_heading;
    }

    public String getSlider_button_text() {
        return slider_button_text;
    }

    public void setSlider_button_text(String slider_button_text) {
        this.slider_button_text = slider_button_text;
    }

    public String getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(String slider_image) {
        this.slider_image = slider_image;
    }

    public String getSlider_link() {
        return slider_link;
    }

    public void setSlider_link(String slider_link) {
        this.slider_link = slider_link;
    }
}
