package com.moonstterinc.epidemicgames.epidemicgames;

import android.nfc.Tag;

public class ListItem {

    //Noticias Y Games
    private String head;
    private String about;
    private String desc;
    private String tag;
    private String date;
    private String text;
    private String imageUrl;

    //Global
    private int position;
    private String nick;
    private String lvl;
    private String round;

    //Player
    private String experience;
    private String life;
    private String DmgRange;
    private String money;
    private String act_round;



    //Noticias Y Games
    public ListItem(String head, String about, String desc, String tag, String date, String text, String imageUrl) {
        this.head = head;
        this.about = about;
        this.desc = desc;
        this.tag = tag;
        this.date = date;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    //Global
    public ListItem(int position, String nick, String lvl, String round) {
        this.position = position;
        this.nick = nick;
        this.lvl = lvl;
        this.round = round;
    }



    //Noticias Y Games
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

    //Global
    public int getPosition() {
        return position;
    }

    public String getNick() {
        return nick;
    }

    public String getLvl() {
        return lvl;
    }

    public String getRound() {
        return round;
    }


}
