package com.game.edu.ibuzzserver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.io.IOException;


public class RankingActivity extends ActionBarActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        gridView = (GridView) findViewById(R.id.gridView);
        MyDBHandler MDBH = null;
        try {
            MDBH = new MyDBHandler(this, null, null, 1);
            String res = MDBH.getBetterScores();
            System.out.println(res);
            String[] tempRes = new String[10];
            String[] tempRes1 = res.split("#");
            for (int i = 0; i<tempRes1.length; i++){
                tempRes[i] = tempRes1[i];

            }
            for (int i = tempRes1.length; i< 10; i++){
                tempRes[i]= "-, , ";

            }
            String[] results = new String[]{ tempRes[0], tempRes[1], tempRes[2],tempRes[3],tempRes[4],tempRes[5],tempRes[6],tempRes[7],tempRes[8],tempRes[9]};
            gridView.setAdapter(new LineAdapter(this, results));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
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

    public void rankingGoBack(View v){
        Intent i = new Intent(getApplicationContext(), FirstScreen.class);
        startActivity(i);
    }
}
