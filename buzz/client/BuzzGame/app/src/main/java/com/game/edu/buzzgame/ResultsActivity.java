package com.game.edu.buzzgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultsActivity extends ActionBarActivity {
    TextView results;
    TextView score;
    Button back;
    String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        back = (Button)findViewById(R.id.back3);
        results = (TextView)findViewById(R.id.results);
        score = (TextView)findViewById(R.id.score);

        String allScores= getIntent().getStringExtra("scores");
        String nickName = getIntent().getStringExtra("nickName");

        String[] scoreTable = allScores.split("<>");
        String MyScore = scoreTable[0];
        String scoreString = "";
        for (int i = 0;i<scoreTable.length;i++){
            System.out.println(scoreTable[i]);
            if (scoreTable[i].startsWith(nickName)){
                String[] n = scoreTable[i].split(":");
                MyScore = n[1];
            }
            scoreString+=scoreTable[i].replace(":","  :  ")+"\n";

        }
        results.setText(scoreString);
        score.setText(MyScore);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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

    public void goBack3(View V){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
