package com.example.gorevyoneticisi.entity;

import android.widget.ImageView;

public class tasks {
    private int task_id;
    private String task_header;
    private String task_content;
    private String task_date;
    private String task_type;
    private ImageView task_image;
    private int task_status;

    public tasks() {
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_header() {
        return task_header;
    }

    public void setTask_header(String task_header) {
        this.task_header = task_header;
    }

    public String getTask_content() {
        return task_content;
    }

    public void setTask_content(String task_content) {
        this.task_content = task_content;
    }

    public String getTask_date() {
        return task_date;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public ImageView getTask_image() {
        return task_image;
    }

    public void setTask_image(ImageView task_image) {
        this.task_image = task_image;
    }

    public int getTask_status() {
        return task_status;
    }

    public void setTask_status(int task_status) {
        this.task_status = task_status;
    }
}
