package com.film.rba.model;

import java.io.Serializable;

public class Video implements Serializable {

    private String id;
    private String title;
    private String description;
    private String third_image;
    private String duration;
    private String ratings;
    private String default_image;
    private String category_id;
    private String category_name;
    private String watch_count;
    private String publish_time;


    public String getId() {
        return id;
    }

    public Video setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Video setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Video setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getThird_image() {
        return third_image;
    }

    public Video setThird_image(String third_image) {
        this.third_image = third_image;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public Video setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getRatings() {
        return ratings;
    }

    public Video setRatings(String ratings) {
        this.ratings = ratings;
        return this;
    }

    public String getDefault_image() {
        return default_image;
    }

    public Video setDefault_image(String default_image) {
        this.default_image = default_image;
        return this;
    }

    public String getCategory_id() {
        return category_id;
    }

    public Video setCategory_id(String category_id) {
        this.category_id = category_id;
        return this;
    }

    public String getCategory_name() {
        return category_name;
    }

    public Video setCategory_name(String category_name) {
        this.category_name = category_name;
        return this;
    }

    public String getWatch_count() {
        return watch_count;
    }

    public Video setWatch_count(String watch_count) {
        this.watch_count = watch_count;
        return this;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public Video setPublish_time(String publish_time) {
        this.publish_time = publish_time;
        return this;
    }
}
