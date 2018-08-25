package com.game.edu.ibuzzserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Download {

    public ArrayList<String> page;

    public Download(String s){
        String str1 = "";

        BufferedReader i;
        page = new ArrayList<>();
        try {
            URL url = new URL(s);
            i = new BufferedReader(new InputStreamReader(url.openStream()));
            int count = 0;
            str1 = i.readLine();
            while(str1!=null) {
                page.add(str1);
                str1 = i.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
