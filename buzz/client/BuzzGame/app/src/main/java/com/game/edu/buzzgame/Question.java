package com.game.edu.buzzgame;


import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Question {
    private String question;
    private String[] answer;
    private int correct;

    Question(){
        this.question = "";
        this.answer = new String[5];
        this.answer[0] = "";
        this.answer[1] = "";
        this.answer[2] = "";
        this.answer[3] = "";
        this.answer[4] = "";
        this.correct = -1;
    }

    Question(String q,String a1,String a2,String a3, String a4,String a5,int c){
        this.question = q;
        this.answer = new String[5];
        this.answer[0] = a1;
        this.answer[1] = a2;
        this.answer[2] = a3;
        this.answer[3] = a4;
        this.answer[4] = a5;
        this.correct = c;
    }

    int getCorrect(){
        return this.correct;
    }

    public String toString(){
        String result = this.question + "\n";
        for (int i=0;i<5;i++){
            result += this.answer[i]+"\n";
        }
        return result;
    }

    public String toString(boolean x){
        String str;
        if (x==true){
            str = this.question+"<>";
            for (int i=0;i<5;i++){
                str+=this.answer[i]+"<>";
            }
            str+=this.correct;
        }
        else {
            str = this.question;
        }
        return str;
    }

    public String getQuestion(){
        return this.question;
    }

    public String getAnswer(int x){
        return this.answer[x];
    }

    public void setQuestion(String x){
        this.question = x;
    }

    public void setAnswer(int x, String answer){
        this.answer[x] = answer;
    }

    public void setCorrect(int correct){ this.correct = correct; }

    public boolean answerExists(String s){
        for (int i=0;i<5;i++){
            if (this.answer[i].equals(s)){
                return true;
            }
        }
        return false;
    }

}
