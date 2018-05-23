package com.moonstterinc.epidemicgames.epidemicgames;

public class News {

    private int id;
    private String title;
    private String Text;
    private String Date;
    private String Label;
    //private URL Url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String texto) {
        Text = texto;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    /*public URL getUrl() {
        return Url;
    }

    public void setUrl(URL url) {
        Url = url;
    }*/
}