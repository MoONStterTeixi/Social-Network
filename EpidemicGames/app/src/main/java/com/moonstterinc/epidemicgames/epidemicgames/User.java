package com.moonstterinc.epidemicgames.epidemicgames;

import org.json.JSONObject;

public class User {
    private String username;
    private String email;
    private int genre;
    private String password;
    private boolean sub;

    private String action;
    //private Bitmap img;

    public User() {

    }

    public User(String username, String email, String password, int genre, boolean sub) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.genre = genre;
        this.sub = sub;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSub() {
        return sub;
    }

    public void setSub(boolean sub) {
        this.sub = sub;
    }

    public String toJson(){
        JSONObject jsonobj = new JSONObject();
        try{
            jsonobj.put("username",this.username);
            jsonobj.put("email",this.email);
            jsonobj.put("password",this.password);
            jsonobj.put("genre",this.genre);
            jsonobj.put("sub",this.sub);
            //jsonobj.put("img",this.img);

        }catch (Exception e){}
        return jsonobj.toString();
    }

    public String toJsonL(){

        JSONObject jsonobj = new JSONObject();
        try{
            jsonobj.put("email",this.email);
            jsonobj.put("password",this.password);
            //jsonobj.put("img",this.img);

        }catch (Exception e){}
        return jsonobj.toString();
    }

    public static User GetObj(String v){
        User usr = new User();
        try {
            JSONObject json = new JSONObject(v);
            usr.setEmail(json.getString("email"));
            usr.setUsername(json.getString("username"));
        }catch (Exception e){}
        return usr;
    }
}
