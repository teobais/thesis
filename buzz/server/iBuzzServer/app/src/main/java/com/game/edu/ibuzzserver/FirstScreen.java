package com.game.edu.ibuzzserver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;


public class FirstScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_screen, menu);
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

    public void goConfig(View v){
        Intent i = new Intent(getApplicationContext(), ConfigurationActivity.class);
        startActivity(i);
    }
    public void goRanking(View v){
        Intent i = new Intent(getApplicationContext(), RankingActivity.class);
        startActivity(i);
    }
    public void goAbout(View v){
        Intent i = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(i);
    }
    public void update(View v) throws IOException {
        Intent i = new Intent(getApplicationContext(), UpdateDBActivity.class);
        startActivity(i);

    }

    public void exit(View v){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
