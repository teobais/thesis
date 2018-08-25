package com.game.edu.ibuzzserver;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class AboutActivity extends ActionBarActivity {
    Button thodorisSocialAuthor;
    Button thodorisSocialBlog;
    Button thodorisSocialLinkedDn;
    Button thodorisSocialSite;
    Button thodorisSocialTwitter;
    Button thodorisSocialInstagram;
    Button thodorisSocialGitHub;

    Button minasSocialAuthor;
    Button minasSocialBlog;
    Button minasSocialLinkedDn;
    Button minasSocialSite;
    Button minasSocialTwitter;
    Button minasSocialInstagram;
    Button minasSocialGitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        thodorisSocialAuthor = (Button)findViewById(R.id.thodorisSocialAuthor);
        thodorisSocialBlog = (Button)findViewById(R.id.thodorisSocialBlog);
        thodorisSocialLinkedDn = (Button)findViewById(R.id.thodorisSocialLinkedIn);
        thodorisSocialSite = (Button)findViewById(R.id.thodorisSocialSite);
        thodorisSocialTwitter = (Button)findViewById(R.id.thodorisSocialTwitter);
        thodorisSocialInstagram = (Button)findViewById(R.id.thodorisSocialInstagramt);
        thodorisSocialGitHub = (Button)findViewById(R.id.thodorisSocialGitHub);

        minasSocialAuthor = (Button)findViewById(R.id.minasSocialAuthor);
        minasSocialBlog = (Button)findViewById(R.id.minasSocialBlog);
        minasSocialLinkedDn = (Button)findViewById(R.id.minasSocialLinkedIn);
        minasSocialSite = (Button)findViewById(R.id.minasSocialSite);
        minasSocialTwitter = (Button)findViewById(R.id.minasSocialTwitter);
        minasSocialInstagram = (Button)findViewById(R.id.minasSocialInstagram);
        minasSocialGitHub = (Button)findViewById(R.id.minasSocialGitHub);
        //assign tags to buttons
        thodorisSocialAuthor.setTag("thodorisSocialAuthor");
        thodorisSocialBlog.setTag("thodorisSocialBlog");
        thodorisSocialLinkedDn.setTag("thodorisSocialLinkedIn");
        thodorisSocialSite.setTag("thodorisSocialSite");
        thodorisSocialTwitter.setTag("thodorisSocialTwitter");
        thodorisSocialInstagram.setTag("thodorisSocialInstagram");
        thodorisSocialGitHub.setTag("thodorisSocialGitHub");

        minasSocialAuthor.setTag("minasSocialAuthor");
        minasSocialBlog.setTag("minasSocialBlog");
        minasSocialLinkedDn.setTag("minasSocialLinkedIn");
        minasSocialSite.setTag("minasSocialSite");
        minasSocialTwitter.setTag("minasSocialTwitter");
        minasSocialInstagram.setTag("minasSocialInstagram");
        minasSocialGitHub.setTag("minasSocialGitHub");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goBack(View v){
        Intent i = new Intent(getApplicationContext(), FirstScreen.class);
        startActivity(i);
    }

    public void openUrl(View V){
        String url = "";
        //keeping in url variable the corresponding url
        switch  (V.getTag().toString()){
            case "thodorisSocialAuthor":{
                url = "http://www.google.com";
                break;
            }
            case "thodorisSocialBlog":{
                url = "http://www.google.com";
                break;
            }
            case "thodorisSocialLinkedDn":{
                url = "http://www.google.com";
                break;
            }
            case "thodorisSocialSite":{
                url = "http://www.google.com";
                break;
            }
            case "thodorisSocialTwitter":{
                url = "http://www.google.com";
                break;
            }
            case "thodorisSocialInstagram":{
                url = "http://www.google.com";
                break;
            }
            case "thodorisSocialGitHub":{
                url = "http://www.google.com";
                break;
            }

            case "minasSocialAuthor":{
                url = "http://www.google.com";
                break;
            }
            case "minasSocialBlog":{
                url = "http://www.google.com";
                break;
            }
            case "minasSocialLinkedDn":{
                url = "http://www.google.com";
                break;
            }
            case "minasSocialSite":{
                url = "http://www.google.com";
                break;
            }
            case "minasSocialTwitter":{
                url = "http://www.google.com";
                break;
            }
            case "minasSocialInstagram":{
                url = "http://www.google.com";
                break;
            }
            case "minasSocialGitHub":{
                url = "http://www.google.com";
                break;
            }
            default :{
                url = "http://www.google.com";
                break;
            }
        }
        //open web page on browser
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
