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


public class ConfigurationActivity extends ActionBarActivity {
    int gameType = 1;

    boolean helpPhoneMode = false;
    boolean helpAudienceMode = false;
    boolean helpHalfMode = false;

    boolean informOtherPlayers = false;

    boolean scoreVisible = false;
    boolean serverSoundsEnabled = false;
    int correctScore = 1;

    int port;
    int maxNumberOfPlayers = 32;

    int maxTimeWaitingForPlayers = 10;

    int maxNumberOfQuestionsPerGame = 4;
    ToggleButton gameTypeBtn;
    ToggleButton scoreVisibilityBtn;

    ToggleButton helpPhoneBtn;
    ToggleButton helpAudienceBtn;
    ToggleButton helpHalfBtn;
    ToggleButton serverSounds;
    ToggleButton playerInformationBtn;
    TextView correctScoreText;
    TextView waitingTimeForAnswersText;

    TextView actorFilter;
    TextView yearFilter;
    TextView ratingFilter;
    TextView genderFilter;


    TextView questionsPerGame;
    TextView portText;
    TextView buzzPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        gameTypeBtn = (ToggleButton)findViewById(R.id.gameTypeChooser);
        scoreVisibilityBtn = (ToggleButton)findViewById(R.id.scoreVisibilityChooser);
        playerInformationBtn = (ToggleButton)findViewById(R.id.playerInforamtionChooser);
        helpPhoneBtn = (ToggleButton)findViewById(R.id.helpPhone);
        helpAudienceBtn = (ToggleButton)findViewById(R.id.helpAudience);
        helpHalfBtn = (ToggleButton)findViewById(R.id.helpHalf);
        correctScoreText = (TextView)findViewById(R.id.correctScore);
        portText = (TextView)findViewById(R.id.portText);
        waitingTimeForAnswersText = (TextView)findViewById(R.id.waitingTimeText);
        serverSounds = (ToggleButton)findViewById(R.id.serverSounds);
        questionsPerGame = (TextView)findViewById(R.id.questionsPerGameText);

        actorFilter = (TextView)findViewById(R.id.actorsfilter);
        yearFilter = (TextView)findViewById(R.id.yearfilter);
        ratingFilter = (TextView)findViewById(R.id.ratingfilter);
        genderFilter = (TextView)findViewById(R.id.genderfilter);
        buzzPercent = (TextView)findViewById(R.id.buzzpercent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
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

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public void go(View v) {
        String error_message = getString(R.string._m_checkErros)+"\n";
        boolean isOk = true;
        if ((!correctScoreText.getText().toString().isEmpty())&&(isNumeric(correctScoreText.getText().toString()))) {
            correctScore = Integer.parseInt(correctScoreText.getText().toString());

        }
        else {
            error_message +=getString(R.string._m_correctanswerscore)+"\n";// "correct answer score\n";
            isOk = false;
        }
        if ((!portText.getText().toString().isEmpty()) &&(isNumeric(portText.getText().toString()))) {
            port = Integer.parseInt(portText.getText().toString());
        }
        else {
            error_message += getString(R.string._m_connectionport)+"\n";//"connection port\n";
            isOk = false;
        }
        if ((!waitingTimeForAnswersText.getText().toString().isEmpty())&&(isNumeric(waitingTimeForAnswersText.getText().toString()))) {
            maxTimeWaitingForPlayers = Integer.parseInt(waitingTimeForAnswersText.getText().toString());
        }
        else {
            error_message +=  getString(R.string._m_waitingtimeforanswer)+"\n";//"waiting time for answer\n";
            isOk = false;
        }
        if ((!questionsPerGame.getText().toString().isEmpty())&&(isNumeric(questionsPerGame.getText().toString()))) {
            maxNumberOfQuestionsPerGame = Integer.parseInt(questionsPerGame.getText().toString());
        }
        else {
            error_message += getString(R.string._m_questionspergame)+"\n";//"questions per game\n";
            isOk = false;
        }
        helpHalfMode = helpHalfBtn.isChecked();
        helpPhoneMode = helpPhoneBtn.isChecked();
        helpAudienceMode = helpAudienceBtn.isChecked();
        scoreVisible = scoreVisibilityBtn.isChecked();
        if (gameTypeBtn.isChecked()) {
            gameType = 2;
        }
        else {
            gameType = 1;
        }
        serverSoundsEnabled = serverSounds.isChecked();
        informOtherPlayers = playerInformationBtn.isChecked();
        if (isOk) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            i.putExtra("gameType", gameType);
            i.putExtra("helpHalfMode", helpHalfMode);
            i.putExtra("helpPhoneMode", helpPhoneMode);
            i.putExtra("helpAudienceMode", helpAudienceMode);
            i.putExtra("informOtherPlayers", informOtherPlayers);
            i.putExtra("correctScore", correctScore);
            i.putExtra("port", port);
            i.putExtra("maxTimeWaitingForPlayers", 60);
            i.putExtra("maxAnswerTime", maxTimeWaitingForPlayers);
            i.putExtra("maxNumberOfQuestionsPerGame", maxNumberOfQuestionsPerGame);
            i.putExtra("maxNumberOfPlayers", 32);

            i.putExtra("actorFilter", actorFilter.getText().toString());
            i.putExtra("yearFilter", yearFilter.getText().toString());
            i.putExtra("genderFilter", genderFilter.getText().toString());
            i.putExtra("ratingFilter", ratingFilter.getText().toString());
            i.putExtra("serverSounds", serverSoundsEnabled);
            i.putExtra("buzzPercent", Double.parseDouble(buzzPercent.getText().toString()));
            i.putExtra("scoreVisible", scoreVisible);
            startActivity(i);
        }
        else {
            Toast.makeText(getBaseContext(),error_message,Toast.LENGTH_LONG).show();
        }
    }

    public void choosePlayerInformation(View v){
        informOtherPlayers = playerInformationBtn.isChecked();
    }

    public void chooseServerSounds(View v){
        serverSoundsEnabled = serverSounds.isChecked();
    }

    public void chooseScoreVisibility(View v){
        scoreVisible = scoreVisibilityBtn.isChecked();
    }

    public void gameTypeChoose(View v){
        if (gameTypeBtn.isChecked()) {
            gameType = 2;
        }
        else {
            gameType = 1;
        }
    }

    public void helpPhone(View v){
        helpPhoneMode = helpPhoneBtn.isChecked();
    }

    public void helpAudience(View v){
        helpAudienceMode = helpAudienceBtn.isChecked();
    }

    public void helpHalf(View v){
        helpHalfMode = helpHalfBtn.isChecked();
    }

    public void goBack1(View v){
        Intent i = new Intent(getApplicationContext(), FirstScreen.class);

        startActivity(i);

    }
}
