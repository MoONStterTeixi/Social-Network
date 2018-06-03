package com.moonstterinc.epidemicgames.epidemicgames;

import org.json.JSONObject;

public class User {
    private String username;
    private String email;
    private int genre;
    private String password;
    private boolean sub;

    public User() {

    }

    //Registro
    public User(String username, String email, String password, int genre, boolean sub) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.genre = genre;
        this.sub = sub;
    }

    //Login
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

    public boolean getSub() {
        return sub;
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

    public void setSub(boolean sub) {
        this.sub = sub;
    }

    //Registro
    public String toJson(){
        JSONObject jsonobj = new JSONObject();
        try{
            jsonobj.put("username",this.username);
            jsonobj.put("email",this.email);
            jsonobj.put("password",this.password);
            jsonobj.put("genre",this.genre);
            jsonobj.put("sub",this.sub);

        }catch (Exception e){}
        return jsonobj.toString();
    }

    //Login
    /*public String toJsonL(){

        JSONObject jsonobj = new JSONObject();
        try{
            jsonobj.put("email",this.email);
            jsonobj.put("password",this.password);

        }catch (Exception e){}
        return jsonobj.toString();
    }*/

    public static User GetObj(String v){
        User usr = new User();
        try {
            JSONObject json = new JSONObject(v);
            usr.setEmail(json.getString("email"));
            usr.setUsername(json.getString("username"));
            usr.setPassword(json.getString("password"));
            usr.setGenre(json.getInt("genre"));
            usr.setSub(json.getBoolean("sub"));
        }catch (Exception e){}
        return usr;
    }
}
