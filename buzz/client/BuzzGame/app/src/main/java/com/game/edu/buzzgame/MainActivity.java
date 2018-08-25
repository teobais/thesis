package com.game.edu.buzzgame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    String ipAddress;
    String portNumber;
    String nickName;
    TextView questionText;
    TextView correctAnswersText;
    TextView wrongAnswersText;
    TextView notAnsweredText;
    TextView correctAnswersTextLabel;
    TextView wrongAnswersTextLabel;
    TextView notAnsweredTextLabel;
    Button answerA;
    Button answerB;
    Button answerC;
    Button answerD;
    Button answerE;
    Button buzzer;
    Button helpAudience;
    Button help50_50;
    Button helpPhone;
    ImageView poster;
    TextView timeLeftChronometer;
    MyCountDownTimer chronometer;
    Thread thread;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    boolean helpPhoneMode;
    boolean helpAudienceMode;
    boolean helpHalfMode;
    int answerTime;
    int gameType;
    boolean informOtherPlayers;
    //true if all players are informed about answers
    boolean scoreVisible;
    //true if the score is visible during the game
    int correctScore;
    //score for correct answers
    int correctAnswers;
    int wrongAnswers;
    int correctAnswer;
    int notAnswered;
    ProgressDialog pDialog;
    Bitmap bitmap;
    String scores;
    boolean questionNotAnswered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionText = (TextView)findViewById(R.id.questionText);
        correctAnswersText = (TextView)findViewById(R.id.correctAnswers);
        wrongAnswersText = (TextView)findViewById(R.id.wrongAnswers);
        notAnsweredText = (TextView)findViewById(R.id.notAnswered);
        correctAnswersTextLabel = (TextView)findViewById(R.id.correctAnswersLabel);
        wrongAnswersTextLabel = (TextView)findViewById(R.id.wrongAnswersLabel);
        notAnsweredTextLabel = (TextView)findViewById(R.id.notAnsweredLabel);
        answerA = (Button)findViewById(R.id.answerA);
        answerB = (Button)findViewById(R.id.answerB);
        answerC = (Button)findViewById(R.id.answerC);
        answerD = (Button)findViewById(R.id.answerD);
        answerE = (Button)findViewById(R.id.answerE);
        poster = (ImageView)findViewById(R.id.poster);

        buzzer = (Button)findViewById(R.id.buzzer);
        timeLeftChronometer = (TextView)findViewById(R.id.timeLeft);
        helpAudience = (Button)findViewById(R.id.helpAudienceBtn);
        help50_50 = (Button)findViewById(R.id.help50_50Btn);
        helpPhone = (Button)findViewById(R.id.helpPhoneBtn);
        gameType = 1;
        informOtherPlayers = false;
        scoreVisible = false;
        correctScore = 1;

        helpPhoneMode = true;
        helpAudienceMode = true;
        helpHalfMode = true;
        answerTime = 10;

        answerA.setTag(new Integer(1));
        answerB.setTag(new Integer(2));
        answerC.setTag(new Integer(3));
        answerD.setTag(new Integer(4));
        answerE.setTag(new Integer(5));
        buzzer.setTag(new Integer(0));

        correctAnswers = 0;
        wrongAnswers = 0;
        notAnswered = 0;

        ipAddress= getIntent().getStringExtra("ipAddress");
        portNumber= getIntent().getStringExtra("portNumber");
        nickName= getIntent().getStringExtra("nickName");

        hideUnhide(true);
        goGame();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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




    public void goGame() {
        //connection to server

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    //a new socket is created
                    socket = new Socket(ipAddress, Integer.parseInt(portNumber));
                    //communicating channels between server and client
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    //message variable
                    String line;
                    // connection initialisation
                    boolean nicknameOk = false;
                    //if nickname has not been accepted

                    questionNotAnswered = true;
                    while (!nicknameOk) {
                        out.println(nickName);
                        line = in.readLine();
                        if (line == null) {

                        }
                        else {
                            if (line.startsWith("NICKNAME::")) {
                                System.out.println(line);
                                //if nickname has been accepted
                                if (line.substring(10).equals("OK")) {
                                    nicknameOk = true;
                                    //configuration options message
                                    line = in.readLine();
                                    System.out.println(line);
                                    String[] configOptions = line.substring(8).split("<>");
                                    //help options
                                    if ("false".equals(configOptions[1])){
                                        helpPhoneMode = false;
                                    }
                                    else {
                                        helpPhoneMode = true;
                                    }

                                    if ("false".equals(configOptions[2])){
                                        helpAudienceMode = false;
                                    }
                                    else {
                                        helpAudienceMode = true;
                                    }

                                    if ("false".equals(configOptions[3])){
                                        helpHalfMode = false;
                                    }
                                    else {
                                        helpHalfMode = true;
                                    }
                                    //answer time
                                    answerTime = Integer.parseInt(configOptions[4]);
                                    if ("false".equals(configOptions[8])){
                                        informOtherPlayers = false;
                                    }
                                    else {
                                        informOtherPlayers = true;
                                    }
                                    if ("false".equals(configOptions[9])){
                                        scoreVisible = false;
                                    }
                                    else {
                                        scoreVisible = true;
                                    }
                                    correctScore = Integer.parseInt(configOptions[7]);
                                    gameType = Integer.parseInt(configOptions[0]);
                                    nickName = configOptions[6];
                                    final int timeForStarting = Integer.parseInt(configOptions[5]);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            help50_50.setEnabled(helpHalfMode);
                                            helpPhone.setEnabled(helpPhoneMode);
                                            helpAudience.setEnabled(helpAudienceMode);
                                            if (!scoreVisible){
                                                correctAnswersText.setVisibility(View.INVISIBLE);
                                                wrongAnswersText.setVisibility(View.INVISIBLE);
                                                notAnsweredText.setVisibility(View.INVISIBLE);
                                                correctAnswersTextLabel.setVisibility(View.INVISIBLE);
                                                wrongAnswersTextLabel.setVisibility(View.INVISIBLE);
                                                notAnsweredTextLabel.setVisibility(View.INVISIBLE);
                                            }
                                            else {
                                                correctAnswersText.setVisibility(View.VISIBLE);
                                                wrongAnswersText.setVisibility(View.VISIBLE);
                                                notAnsweredText.setVisibility(View.VISIBLE);
                                                correctAnswersTextLabel.setVisibility(View.VISIBLE);
                                                wrongAnswersTextLabel.setVisibility(View.VISIBLE);
                                                notAnsweredTextLabel.setVisibility(View.VISIBLE);
                                            }

                                            chronometer = new MyCountDownTimer(timeForStarting*1000,1000);
                                            chronometer.countingType=1;
                                            chronometer.preString = "";
                                            chronometer.start();
                                        }
                                    });

                                    break;
                                } else {


                                    line = in.readLine();
                                    final String finalLine1 = line.substring(10);
                                    System.out.println(line);
                                    System.out.println(line.substring(8));
                                    String[] configOptions = line.substring(8).split("<>");
                                    nickName = configOptions[3];
                                    if ("false".equals(configOptions[1])){
                                        helpPhoneMode = false;
                                    }
                                    else {
                                        helpPhoneMode = true;
                                    }

                                    if ("false".equals(configOptions[2])){
                                        helpAudienceMode = false;
                                    }
                                    else {
                                        helpAudienceMode = true;
                                    }

                                    if ("false".equals(configOptions[3])){
                                        helpHalfMode = false;
                                    }
                                    else {
                                        helpHalfMode = true;
                                    }

                                    answerTime = Integer.parseInt(configOptions[4]);
                                    if ("false".equals(configOptions[8])){
                                        informOtherPlayers = false;
                                    }
                                    else {
                                        informOtherPlayers = true;
                                    }
                                    if ("false".equals(configOptions[9])){
                                        scoreVisible = false;
                                    }
                                    else {
                                        scoreVisible = true;
                                    }
                                    correctScore = Integer.parseInt(configOptions[7]);
                                    gameType = Integer.parseInt(configOptions[0]);
                                    final int timeForStarting = Integer.parseInt(configOptions[5]);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getBaseContext(), getString(R.string._m_Yournicknamehasbeenmodifiedto)+finalLine1,Toast.LENGTH_SHORT).show();
                                            chronometer = new MyCountDownTimer(timeForStarting*1000,1000);
                                            chronometer.countingType=1;
                                            chronometer.preString = "";
                                            chronometer.start();
                                        }
                                    });
                                }
                            }
                        }
                    }
                    //
                    line = in.readLine();

                    if ("GAME_STARTING".equals(line)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideUnhide(false);
                                help50_50.setEnabled(helpHalfMode);
                                helpPhone.setEnabled(helpPhoneMode);
                                helpAudience.setEnabled(helpAudienceMode);
                                if (scoreVisible==false){
                                    correctAnswersText.setVisibility(View.INVISIBLE);
                                    wrongAnswersText.setVisibility(View.INVISIBLE);
                                    notAnsweredText.setVisibility(View.INVISIBLE);
                                }
                                else {
                                    correctAnswersText.setVisibility(View.VISIBLE);
                                    wrongAnswersText.setVisibility(View.VISIBLE);
                                    notAnsweredText.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        );

                            correctAnswers=0;
                            wrongAnswers=0;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run () {
                                    Toast.makeText(getBaseContext(), getString(R.string._m_GameStarted), Toast.LENGTH_SHORT).show();
                                }
                            }

                            );

                            while(true)

                            {
                                line = in.readLine();
                                final String finalLine = line;
                                //new question arrives
                                if (line.startsWith("NEW_QUESTION")) {//new question arrived
                                    questionNotAnswered = true;
                                    //UI update for the new question
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            makeAllAnswersEnabled();
                                            String[] questionElements = finalLine.substring(14).split("<>");
                                            answerA.setText(questionElements[1]);
                                            answerB.setText(questionElements[2]);
                                            answerC.setText(questionElements[3]);
                                            answerD.setText(questionElements[4]);
                                            answerE.setText(questionElements[5]);
                                            new LoadImage().execute(questionElements[7]);
                                            questionText.setText(questionElements[0]);
                                            correctAnswer = Integer.parseInt(questionElements[6]);
                                            //buttons are being enabled
                                            answerA.setEnabled(true);
                                            answerB.setEnabled(true);
                                            answerC.setEnabled(true);
                                            answerD.setEnabled(true);
                                            answerE.setEnabled(true);
                                            buzzer.setEnabled(true);
                                            if ("...".equals(questionElements[1])) {//buzz question
                                                answerA.setEnabled(false);
                                                answerB.setEnabled(false);
                                                answerC.setEnabled(false);
                                                answerD.setEnabled(false);
                                                answerE.setEnabled(false);
                                            } else {//normal question -->buzz button is unavailable
                                                buzzer.setEnabled(false);
                                            }
                                            //chronometer setting
                                            chronometer.cancel();
                                            chronometer = new MyCountDownTimer(answerTime * 1000, 1000);
                                            chronometer.countingType = 2;
                                            chronometer.preString = "";
                                            chronometer.start();
                                        }
                                    });

                                } else {
                                    //a message has been arrived
                                    if (line.startsWith("MESSAGE")) {//message arrived
                                        //last answer was a correct one
                                        if (line.substring(9).startsWith("CORRECT")) {


                                            if (finalLine.substring(17).contains(nickName)){
                                                questionNotAnswered = false;
                                                correctAnswers++;

                                            }
                                            final int cA = correctScore * correctAnswers;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getBaseContext(), getString(R.string._m_CorrectAnswerfrom) + finalLine.substring(17), Toast.LENGTH_SHORT).show();
                                                    if (finalLine.substring(17).equals(nickName)) {

                                                        correctAnswersText.setText("" + cA);
                                                    }
                                                    if (gameType == 2) {//if only one player answers each question
                                                        //buttons are being disabled
                                                        answerA.setEnabled(false);
                                                        answerB.setEnabled(false);
                                                        answerC.setEnabled(false);
                                                        answerD.setEnabled(false);
                                                        answerE.setEnabled(false);
                                                        buzzer.setEnabled(false);
                                                    }
                                                }
                                            });
                                        }
                                        //last answer where a wrong one
                                        else if (line.substring(9).startsWith("ERROR")) {
                                            if (finalLine.substring(15).contains(nickName)){
                                                questionNotAnswered = false;
                                                wrongAnswers++;
                                            }
                                            final int wA = wrongAnswers;

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getBaseContext(), getString(R.string._m_WrongAnswerfrom) + finalLine.substring(15), Toast.LENGTH_SHORT).show();
                                                    if (finalLine.substring(15).equals(nickName)) {

                                                        wrongAnswersText.setText("" + wA);
                                                    }

                                                }
                                            });
                                        }
                                        //this is the end of the game
                                        else if (line.substring(9).startsWith("END_OF_GAME")) {
                                            System.out.println("scores = " + line);
                                            String[] parts= line.split("==");
                                            scores = parts[1];
                                            System.out.println("scores = " + scores);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getBaseContext(), getString(R.string._m_endofgame), Toast.LENGTH_SHORT).show();
                                                    correctAnswersText.setVisibility(View.VISIBLE);
                                                    wrongAnswersText.setVisibility(View.VISIBLE);
                                                    notAnsweredText.setVisibility(View.VISIBLE);


                                                }
                                            });
                                            break;
                                        } else if (line.substring(9).startsWith("NOANSWER")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getBaseContext(), getString(R.string._m_timeout), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                        //a player has pressed the buzz button
                                        else if (line.substring(9).startsWith("BUZZ")) {
                                            //waiting to receive message DONE_BUZZ

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    chronometer.cancel();
                                                }
                                            });
                                            String line2 = in.readLine();
                                            while (line2 == null) {

                                                line2 = in.readLine();
                                            }
                                            final String finaline = line2;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getBaseContext(), finaline, Toast.LENGTH_SHORT).show();
                                                    chronometer = new MyCountDownTimer(1000, 1000);
                                                    chronometer.countingType = 2;
                                                    chronometer.preString = "";
                                                    chronometer.start();
                                                }
                                            });

                                        }
                                        //any other message
                                        else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //Toast.makeText(getBaseContext(), finalLine.substring(9), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }
                            }

                            socket.close();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run () {
                                    Toast.makeText(getBaseContext(), getString(R.string._m_endofgame), Toast.LENGTH_SHORT).show();
                                    try {
                                        sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("scores2 = " + scores);
                                    Intent i = new Intent(getBaseContext(), ResultsActivity.class);
                                    i.putExtra("scores",scores);
                                    i.putExtra("nickName",nickName);
                                    startActivity(i);

                                }
                            }

                            );
                            return;
                        }
                        else {

                        socket.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), getString(R.string._m_gamefailure), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(),HomeActivity.class);
                                startActivity(i);


                            }
                        });
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(),getString(R.string._m_connectionerror),Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(i);

                            }
                        });

                }
            }
        };
        thread.start();
    }

    public void setAnswer(View v){
        Integer x = new Integer(correctAnswer);
        if (v.getTag().toString().equals(x.toString()))   {
            //Toast.makeText(getBaseContext(),"CorrectAnswer",Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(getBaseContext(),"WrongAnswer",Toast.LENGTH_SHORT).show();
        }
        out.println(nickName + "::" + v.getTag().toString());
        //buttons are being enabled
        answerA.setEnabled(false);
        answerB.setEnabled(false);
        answerC.setEnabled(false);
        answerD.setEnabled(false);
        answerE.setEnabled(false);
        buzzer.setEnabled(false);
    }

    public void makeAudienceHelp(View V){
        int[] levels;
        char[] LETTERS = {'A','B','C','D','E'};
        int k,l;
        levels = new int[5];
        Random rand = new Random();
        int betterAnswer = rand.nextInt((95 - 70) + 1) + 70;
        k = 100-betterAnswer;
        String str = "Audience Help: ";
        for (int i=0;i<5;i++){
            if ((i+1)==correctAnswer){
                levels[i] = betterAnswer;
                str += LETTERS[i]+":"+levels[i]+" , ";
            }
            else {
                if (i==4){
                    levels[i] = k;
                    str += LETTERS[i] + ":" + levels[i] + " , ";
                }
                else {
                    l = rand.nextInt((k - 0) + 1) + 0;
                    levels[i] = l;
                    str += LETTERS[i] + ":" + levels[i] + " , ";
                    k -= l;
                }
            }
        }
        int sum = 0;
        for (int i=0;i<5;i++){
            sum += levels[i];
        }
        levels[correctAnswer-1]+=(100-sum);
        Toast.makeText(getBaseContext(),str,Toast.LENGTH_SHORT).show();
        helpAudience.setEnabled(false);
    }

    public void makePhoneHelp(View v){
        Random rand = new Random();
        char[] LETTERS = {'A','B','C','D','E'};
        int betterAnswer = rand.nextInt((100 - 0) + 1) + 0;
        if (betterAnswer > 10){
            Toast.makeText(getBaseContext(),"The correct Answer is "+LETTERS[correctAnswer-1],Toast.LENGTH_SHORT).show();
        }
        else {
            int pointer = rand.nextInt((4 - 0) + 0) + 0;
            Toast.makeText(getBaseContext(),"The correct Answer is "+LETTERS[pointer],Toast.LENGTH_SHORT).show();
        }
        helpPhone.setEnabled(false);
    }

    public void make50_50Help(View v){
        Random rand = new Random();
        int hidden1 = rand.nextInt((5 - 1) + 1);
        while (hidden1 == (correctAnswer)){
            hidden1 = rand.nextInt((5 - 1) + 1);
        }
        int hidden2 = rand.nextInt((5 - 1) + 1);
        while ((hidden2==hidden1) || (hidden2==(correctAnswer))){
            hidden2 = rand.nextInt((5 - 1) + 1);
        }
        int hidden3 = rand.nextInt((5 - 1) + 1);
        while ((hidden3==hidden1)||(hidden3==hidden2) || (hidden2==(correctAnswer))){
            hidden3 = rand.nextInt((5 - 1) + 1);
        }
        if ((hidden1==1)|| (hidden2==1) || (hidden3==1)){
            answerA.setEnabled(false);
        }
        if ((hidden1==2)|| (hidden2==2) || (hidden3==2)){
            answerB.setEnabled(false);
        }
        if ((hidden1==3)|| (hidden2==3) || (hidden3==3)){
            answerC.setEnabled(false);
        }
        if ((hidden1==4)|| (hidden2==4) || (hidden3==4)){
            answerD.setEnabled(false);
        }
        if ((hidden1==5)|| (hidden2==5) || (hidden3==5)){
            answerE.setEnabled(false);
        }
        help50_50.setEnabled(false);
    }

    public void makeAllAnswersEnabled(){
        answerA.setEnabled(true);
        answerB.setEnabled(true);
        answerC.setEnabled(true);
        answerD.setEnabled(true);
        answerE.setEnabled(true);
    }

    // CountDownTimer class
    public class MyCountDownTimer extends CountDownTimer{
        public String preString = "";
        public int countingType = 0;
        public MyCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            timeLeftChronometer.setText("");
            if (countingType==1){

            }
            else if (countingType==2){
                if (questionNotAnswered) {
                    notAnswered++;
                }
                notAnsweredText.setText(""+notAnswered);
                out.println(nickName + "::9");
            }
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            timeLeftChronometer.setText(preString +":" + (millisUntilFinished/1000));
        }
    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                poster.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this,getString(R.string._m_imagdoesnotexist), Toast.LENGTH_SHORT).show();

            }
        }
    }

    void hideUnhide(boolean b){
        if (b){
            questionText.setVisibility(View.INVISIBLE);
            correctAnswersText.setVisibility(View.INVISIBLE);
            wrongAnswersText.setVisibility(View.INVISIBLE);
            notAnsweredText.setVisibility(View.INVISIBLE);
            correctAnswersTextLabel.setVisibility(View.INVISIBLE);
            wrongAnswersTextLabel.setVisibility(View.INVISIBLE);
            notAnsweredTextLabel.setVisibility(View.INVISIBLE);
            answerA.setVisibility(View.INVISIBLE);
            answerB.setVisibility(View.INVISIBLE);
            answerC.setVisibility(View.INVISIBLE);
            answerD.setVisibility(View.INVISIBLE);
            answerE.setVisibility(View.INVISIBLE);
            poster.setVisibility(View.INVISIBLE);

            buzzer.setVisibility(View.INVISIBLE);

            helpAudience.setVisibility(View.INVISIBLE);
            help50_50.setVisibility(View.INVISIBLE);
            helpPhone.setVisibility(View.INVISIBLE);
        }
        else {
            questionText.setVisibility(View.VISIBLE);
            correctAnswersText.setVisibility(View.VISIBLE);
            wrongAnswersText.setVisibility(View.VISIBLE);
            notAnsweredText.setVisibility(View.VISIBLE);
            correctAnswersTextLabel.setVisibility(View.VISIBLE);
            wrongAnswersTextLabel.setVisibility(View.VISIBLE);
            notAnsweredTextLabel.setVisibility(View.VISIBLE);
            answerA.setVisibility(View.VISIBLE);
            answerB.setVisibility(View.VISIBLE);
            answerC.setVisibility(View.VISIBLE);
            answerD.setVisibility(View.VISIBLE);
            answerE.setVisibility(View.VISIBLE);
            poster.setVisibility(View.VISIBLE);

            buzzer.setVisibility(View.VISIBLE);

            helpAudience.setVisibility(View.VISIBLE);
            help50_50.setVisibility(View.VISIBLE);
            helpPhone.setVisibility(View.VISIBLE);
        }
    }

}
