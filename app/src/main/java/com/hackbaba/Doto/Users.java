package com.hackbaba.Doto;

public class Users {

    String titledoes;
    String datedoes;
    String descdoes;
    String key;
    String keydoes;
    String usernamestory = "usernamestory";


    public Users() {
    }

    public Users(String titledoes, String datedoes, String descdoes, String keydoes,String usernamestory,String key) {
        this.titledoes = titledoes;
        this.datedoes = datedoes;
        this.descdoes = descdoes;
        this.key = key;
        this.keydoes = keydoes;
        this.usernamestory = usernamestory;
    }

    public String getKeydoes() {
        return keydoes;
    }

    public void setKeydoes(String key) {
        this.keydoes = key;
    }

    public String getKey() {
        return keydoes;
    }

    public void setKey(String keydoes) {
        this.keydoes = keydoes;
    }


    public String getTitledoes() {
        return titledoes;
    }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }

    public String getDatedoes() {
        return datedoes;
    }

    public void setDatedoes(String datedoes) {
        this.datedoes = datedoes;
    }

    public String getDescdoes() {
        return descdoes;
    }

    public void setDescdoes(String descdoes) {
        this.descdoes = descdoes;
    }

    public String getUsernamestory(String usernamestory){
        return usernamestory;
    }

    public  void  setUsernamestory(String usernamestory){
        this.usernamestory = usernamestory;
    }

}
