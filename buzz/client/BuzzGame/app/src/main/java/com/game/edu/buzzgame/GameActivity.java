package com.game.edu.buzzgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends ActionBarActivity {


    private TextView ipAddressArea;
    private TextView portNumberArea;
    private TextView nickNameArea;
    private Button nextBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        nextBtn = (Button)findViewById(R.id.connectionBtn);
        backBtn = (Button)findViewById(R.id.back);
        ipAddressArea = (TextView)findViewById(R.id.ipAddressArea);
        portNumberArea = (TextView)findViewById(R.id.portNumberArea);
        nickNameArea = (TextView)findViewById(R.id.nickNameArea);

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

    public void goToGamePanel(View v){
        String message = getString(R.string._m_checkinput)+"\n";
        boolean isOk=true;
        if (ipAddressArea.getText().toString().equals("")){
            message += getString(R.string.ipaddress)+"\n";
            isOk=false;
        }
        if (ipAddressArea.getText().toString().equals("")){
            message += getString(R.string.portnumber)+"\n";
            isOk=false;
        }
        if (ipAddressArea.getText().toString().equals("")){
            message += getString(R.string.nickname)+"\n";
            isOk=false;
        }
        if (isOk) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("ipAddress", ipAddressArea.getText().toString());
            i.putExtra("portNumber", portNumberArea.getText().toString());
            i.putExtra("nickName", nickNameArea.getText().toString());
            startActivity(i);
        }
        else {
            Toast.makeText(getBaseContext(),message, Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View v){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
