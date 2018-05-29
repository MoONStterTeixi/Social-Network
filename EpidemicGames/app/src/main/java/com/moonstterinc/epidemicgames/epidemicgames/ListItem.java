package com.moonstterinc.epidemicgames.epidemicgames;

import android.nfc.Tag;

public class ListItem {
    private String head;
    private String about;
    private String desc;
    private String tag;
    private String date;
    private String text;
    private String imageUrl;

    public ListItem(String head, String about, String desc, String tag, String date, String text, String imageUrl) {
        this.head = head;
        this.about = about;
        this.desc = desc;
        this.tag = tag;
        this.date = date;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getHead() {
        return head;
    }

    public String getAbout() {
        return about;
    }

    public String getDesc() {
        return desc;
    }

    public String getTag() {
        return tag;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
