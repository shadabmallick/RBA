package com.film.rba.model;

public class Event {


    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int productImage;
    public String title;
    public String subtitle;
    public String time;

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String title1;
    public String title2;



    public Event(String title, String  subtitle, String time, int productImage, String title1, String
                 title2) {
        this.subtitle = subtitle;
        this.title = title;
        this.time = time;
        this.productImage = productImage;
        this.title1 = title1;
        this.title2 = title2;
    }


}