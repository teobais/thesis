package com.game.edu.ibuzzserver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;


public class UpdateDBActivity extends ActionBarActivity {
    private TextView updateServerUrl;
    private ToggleButton updateButton;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_db);

        updateButton = (ToggleButton)findViewById(R.id.updateButton);
        updateServerUrl = (TextView)findViewById(R.id.updateServerUrl);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_db, menu);
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

    public void updateDB(View v) {
        thread = new Thread() {
            @Override
            public void run() {
                String url = updateServerUrl.getText().toString();
                boolean keep = updateButton.isChecked();

                try {
                    MyDBHandler MDBH = new MyDBHandler(getBaseContext(), null, null, 1);
                    MDBH.updateFromWeb(url, keep);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), getString(R.string._m_databaseupdated), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), FirstScreen.class);
                            startActivity(i);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
}
