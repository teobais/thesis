package com.game.edu.ibuzzserver;


public class Player {
    String nickName;
    int score;

    public Player(){
        this.nickName = "";
        this.score = 0;
    }

    public Player(String x){
        this.nickName = x;
        this.score = 0;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getNickName(){
        return this.nickName;
    }

    public int getScore(){
        return this.score;
    }

    public void addScore(int score){
        this.score += score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
