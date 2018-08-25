package com.game.edu.ibuzzserver;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;


public class HomeActivity extends ActionBarActivity {

    //server port
    private int PortNumber;
    //max number of players per game
    private int maxNumberOfPlayers;
    //max waiting time for new connections
    private int maxTimeWaitingForPlayers = 10;
    //questions per game
    private int maxNumberOfQuestionsPerGame = 4;
    //question list
    private ArrayList<Question> questionsSet = new ArrayList<>();
    //player list
    private ArrayList<Player> names = new ArrayList<>();
    //list of output streams
    private ArrayList<PrintWriter> writers = new ArrayList<>();
    //first connection time
    private long startTime;
    //game type
    private int gameType;
    //1: All players can answer continuously until one of them give the correct answer
    //2: Only one answer received per question
    private boolean helpHalfMode = false;
    private boolean helpPhoneMode = false;
    private boolean helpAudienceMode = false;
    //true if help is enabled
    private boolean informOtherPlayers = false;
    //true if all players are informed about answers
    private boolean scoreVisible = false;
    //true if the score is visible during the game
    private int correctScore = 1;
    //score for correct answers
    private int maxAnswerTime = 10;
    private MyCountDownTimer chronometer;
    private TextView questionPanel;
    private TextView serverText;
    private TextView timeOutLabel;
    private TextView roundLabel;
    private TextView answeredLabel;
    private TextView timeOutText;
    private TextView roundText;
    private TextView answeredText;
    private TextView playersText;
    private TextView playersLabel;
    private TextView numberOfConnectedPlayers;
    private TextView connectedPlayersList;
    //current player name
    private String name;
    //socket
    private Socket socket;
    private ServerSocket serverSocket;
    //input and output streams
    private BufferedReader in;
    private PrintWriter out;
    private Button button;
    private String[] filters;

    private int buzzNumber;

    private ListView playersList;

    SocketServerThread socketServerThread;
    MediaPlayer success;
    MediaPlayer error;
    MediaPlayer buzzer;
    int playersConnected = 0;
    boolean serverSounds;
    String scoreMessage;
    boolean madeScoreMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //initialise game characteristics
        PortNumber = getIntent().getIntExtra("port",8888);
        maxNumberOfPlayers = getIntent().getIntExtra("maxNumberOfPlayers", 32);
        maxTimeWaitingForPlayers = getIntent().getIntExtra("maxTimeWaitingForPlayers", 60);
        maxNumberOfQuestionsPerGame = getIntent().getIntExtra("maxNumberOfQuestionsPerGame", 10);
        gameType = getIntent().getIntExtra("gameType", 1);
        helpHalfMode = getIntent().getBooleanExtra("helpHalfMode", true);
        helpPhoneMode = getIntent().getBooleanExtra("helpPhoneMode", true);
        helpAudienceMode = getIntent().getBooleanExtra("helpAudienceMode", true);
        informOtherPlayers = getIntent().getBooleanExtra("informOtherPlayers", true);
        correctScore = getIntent().getIntExtra("correctScore", 1);
        maxAnswerTime =getIntent().getIntExtra("maxAnswerTime",10);
        serverSounds = getIntent().getBooleanExtra("serverSounds", true);
        scoreVisible = getIntent().getBooleanExtra("scoreVisible", true);
        if (serverSounds){
            buzzer = MediaPlayer.create(getBaseContext(),R.raw.buzzer);
            success = MediaPlayer.create(getBaseContext(),R.raw.success);
            error = MediaPlayer.create(getBaseContext(),R.raw.error);
        }
        button = (Button)findViewById(R.id.button);
        questionPanel = (TextView)findViewById(R.id.questionPanel);
        serverText = (TextView)findViewById(R.id.textView);
        timeOutText = (TextView)findViewById(R.id.timeouttext);
        roundText = (TextView)findViewById(R.id.roundtext);
        answeredText = (TextView)findViewById(R.id.answeredtext);
        playersText = (TextView)findViewById(R.id.playerstext);
        playersLabel = (TextView)findViewById(R.id.playerslabel);
        timeOutLabel = (TextView)findViewById(R.id.timeoutlabel);
        roundLabel = (TextView)findViewById(R.id.roundlabel);
        answeredLabel = (TextView)findViewById(R.id.answeredlabel);
        timeOutText.setVisibility(View.INVISIBLE);
        roundText.setVisibility(View.INVISIBLE);
        answeredLabel.setVisibility(View.INVISIBLE);
        timeOutLabel.setVisibility(View.INVISIBLE);
        roundLabel.setVisibility(View.INVISIBLE);
        answeredText.setVisibility(View.INVISIBLE);
        playersText.setVisibility(View.INVISIBLE);
        playersLabel.setVisibility(View.INVISIBLE);
        numberOfConnectedPlayers = (TextView)findViewById(R.id.numberOfConnectedPlayers);
        connectedPlayersList = (TextView)findViewById(R.id.connectedPlayersList);
        playersList = (ListView) findViewById(R.id.playerslist);

        filters = new String[4];
        filters[0] = getIntent().getStringExtra("actorFilter");
        filters[1] = getIntent().getStringExtra("genderFilter");
        filters[2] = getIntent().getStringExtra("yearFilter");
        filters[3] = getIntent().getStringExtra("ratingFilter");
        madeScoreMessage = false;

        double bn = getIntent().getDoubleExtra("buzzPercent", 0);
        buzzNumber =(int)((double)maxNumberOfQuestionsPerGame * bn/100);
        if ((buzzNumber == 0) && (bn>0)){
            buzzNumber = 1;
        }
        serverText.setText(getLocalIpAddress()+":"+PortNumber);
        try {
            initGame();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

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

    public void makePlayersList(){
        Player[] namesArray = new Player[names.size()];
        for (int i = 0;i<names.size();i++){
            namesArray[i] = names.get(i);
        }
        PlayerAdapter adapter = new PlayerAdapter(this,R.layout.list_item, namesArray);
        playersList.setAdapter(adapter);
        playersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player P = names.get(position);
                P.addScore(correctScore);
                socketServerThread.buzzDone();
            }
        });
    }

    public boolean isInBuzzSet(int x, int[] b){
        for (int i=0;i<b.length;i++){
            if (b[i]==x){
                return true;
            }
        }
        return false;
    }

    public void initGame() throws SQLException, IOException {
        /*
        to be done
        */
        Random R = new Random();
        int[] buzzIds = new int[buzzNumber];
        //selecting buzz question randomly
        for (int k = 0;k<buzzNumber;k++){
            buzzIds[k]=R.nextInt(maxNumberOfQuestionsPerGame);
        }
        //question subjects
        String[] questionContent = {"director","actor","released","writer","year"};
        //making questions table
        for (int i = 0;i<maxNumberOfQuestionsPerGame;i++){
            Question q ;//= new Question(i+". Question  "+i,"A","B","C","D","E",(i%5)+1);
            MyDBHandler MDBH = new MyDBHandler(this, null, null, 1);
            q = MDBH.makeQuestion(i+1,questionContent[R.nextInt(4)],filters);
            if (q==null){
                System.out.println("i="+i);
                break;
            }
            //buzz questions have not possible answers
            if (isInBuzzSet(i, buzzIds)){
                q.setCorrect(0);
                q.setAnswer(0,"...");
                q.setAnswer(1,"...");
                q.setAnswer(2,"...");
                q.setAnswer(3,"...");
                q.setAnswer(4,"...");

            }

            questionsSet.add(q);
        }
        //preparing UI
        CharSequence cs = "0";
        answeredText.setText(cs);
        roundText.setText(cs);
        timeOutText.setVisibility(View.INVISIBLE);
        roundText.setVisibility(View.INVISIBLE);
        answeredLabel.setVisibility(View.INVISIBLE);
        timeOutLabel.setVisibility(View.INVISIBLE);
        roundLabel.setVisibility(View.INVISIBLE);
        answeredText.setVisibility(View.INVISIBLE);
        playersText.setVisibility(View.INVISIBLE);
        playersLabel.setVisibility(View.INVISIBLE);
        numberOfConnectedPlayers.setText("# of connected players:0");

        cs = "";
        connectedPlayersList.setText(cs);
        numberOfConnectedPlayers.setVisibility(View.VISIBLE);
        connectedPlayersList.setVisibility(View.VISIBLE);
    }

    //makes a string containing all players scores
    public void makePlayersScoresMessage(){
        if (!madeScoreMessage) {

            sortPlayersByScore();
            String str = "";
            for (int i = 0; i < names.size(); i++) {
                str += names.get(i).getNickName() + ":" + names.get(i).getScore();
                if (i != (names.size() - 1)) {
                    str += "<>";
                }
            }
            madeScoreMessage = true;

            scoreMessage = str;

            System.out.println(scoreMessage);
        }

    }

    //starting server
    public void startServer(View v) throws IOException {

        socketServerThread = new SocketServerThread();
        socketServerThread.start();
        button.setBackgroundColor(Color.rgb(220,50,50));
    }

    private class SocketServerThread extends Thread implements Runnable {
        boolean suspended = false;
        //current question number

        public void buzzWait(){
            synchronized(this){
                suspended = true;
                while(suspended){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void buzzDone(){
            synchronized(this){
                this.suspended = false;
                notifyAll();
            }
        }

        @Override
        public void run() {


            try {
                // create ServerSocket using specified port
                serverSocket = new ServerSocket(PortNumber);

                while (true) {
                    // block the call until connection is created and return
                    // Socket object
                    //Socket socket = serverSocket.accept();

                    new ThreadHandler(serverSocket.accept()).start();
                    //threadHandler.run();

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    //connection thread
    public class ThreadHandler extends Thread {


        //player's name
        private String name;
        private int playerId;
        private Socket socket;
        //input and output streams
        private BufferedReader in;
        private PrintWriter out;


        //CONSTRUCTOR
        public ThreadHandler(Socket socket) {
            this.socket = socket;
        }


        public void run() {
            //current question
            int questionNumber = 0;
            long tl=0;
            try {
                //communication channels
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                //1: player connection
                while (true) {
                    final Player P;

                    name = in.readLine();
                    out.println("MESSAGE::CONNECTION ACCEPTED");

                    name = in.readLine();

                    if (name == null) {
                        return;
                    }
                    P = new Player(name);
                    //only unique nicknames
                    int ii;
                    for (ii = 0; ii < names.size(); ii++) {
                        if (P.getNickName().equals(names.get(ii).getNickName())) {
                            Random rand = new Random();
                            int kA = rand.nextInt(33) + 1;
                            name = name + "_" + kA;
                            System.out.println("New name = " + name);
                            P.setNickName(name);
                            ii = 0;
                        }
                    }
                    //player added to players list
                    playerId = names.size();
                    names.add(P);
                    //adding an output stream
                    writers.add(out);
                    //new player is added to player list on UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectedPlayersList.setText(connectedPlayersList.getText() + "\n" + P.getNickName());
                            String[] x = numberOfConnectedPlayers.getText().toString().split(":");
                            int cplrs = Integer.parseInt(x[1].trim()) + 1;
                            numberOfConnectedPlayers.setText("# of players connected:" + cplrs);
                            if (serverSounds) {
                                success.start();
                            }
                        }
                    });
                    //this is the first player
                    if (names.size() == 1) {
                        startTime = System.currentTimeMillis();
                        System.out.println("MESSAGE::WAITING<>60");
                        //configuration message to client
                        writers.get(writers.size() - 1).println("NICKNAME::OK\nCONFIG::" + gameType + "<>" + helpPhoneMode + "<>" + helpAudienceMode + "<>" + helpHalfMode + "<>" + maxAnswerTime + "<>60<>" + names.get(writers.size() - 1).getNickName() + "<>" + correctScore + "<>" + informOtherPlayers + "<>" + scoreVisible);
                    } else if (names.size() > 1) {
                        tl = 60 - ((System.currentTimeMillis() - startTime) / 1000);
                        System.out.println("MESSAGE::WAITING<>" + tl);
                        writers.get(writers.size() - 1).println("NICKNAME::OK\nCONFIG::" + gameType + "<>" + helpPhoneMode + "<>" + helpAudienceMode + "<>" + helpHalfMode + "<>" + maxAnswerTime + "<>"+tl+"<>" + names.get(writers.size() - 1).getNickName() + "<>" + correctScore + "<>" + informOtherPlayers + "<>" + scoreVisible);
                    }
                    while (System.currentTimeMillis() - startTime < maxTimeWaitingForPlayers * 1000) {
                        //if the max number of players reached
                        if (names.size() == maxNumberOfPlayers) {
                            break;
                        }
                    }
                    //if only one player connected end_of_game 
                    if (names.size() == 1) {

                        writers.get(0).println("MESSAGE::END_OF_GAME");
                        if (serverSounds) {
                            error.start();
                        }
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent;
                                intent = new Intent(getBaseContext(), ConfigurationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    else {//else go on

                        //UI preparation for starting game
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                numberOfConnectedPlayers.setVisibility(View.INVISIBLE);
                                connectedPlayersList.setVisibility(View.INVISIBLE);
                                timeOutText.setVisibility(View.VISIBLE);
                                roundText.setVisibility(View.VISIBLE);
                                answeredLabel.setVisibility(View.VISIBLE);
                                timeOutLabel.setVisibility(View.VISIBLE);
                                roundLabel.setVisibility(View.VISIBLE);
                                answeredText.setVisibility(View.VISIBLE);
                                playersText.setVisibility(View.VISIBLE);
                                playersLabel.setVisibility(View.VISIBLE);
                                makePlayersList();
                                if (serverSounds) {
                                    success.start();
                                }
                            }
                        });
                        out.println("GAME_STARTING");
                        playersConnected = names.size();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                playersText.setText(""+playersConnected);
                            }
                        });
                        questionNumber = 0;
                        break;
                    }
                }
                //game
                while (true) {
                    if (out.checkError()){
                        //there is not any connection with client
                        final String disPlayer =  names.get(playerId).getNickName();
                        names.get(playerId).setNickName("--Disconnected--");
                        playersConnected--;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                playersText.setText(""+playersConnected);
                                makePlayersList();
                                Toast.makeText(getBaseContext(),getText(R.string._m_player)+" "+disPlayer+" " +getText(R.string._m_disconnected), Toast.LENGTH_SHORT).show();
                            }
                        });
                        try {
                            socket.close();
                            return;
                        }
                        catch (IOException e) {
                        }
                    }
                    //last question -end of game
                    if (questionNumber == maxNumberOfQuestionsPerGame) {

                        //insert score into score db
                        MyDBHandler MDBH = new MyDBHandler(getBaseContext(), null, null, 1);
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        MDBH.addScore(dateFormat.format(date),names.get(playerId).getNickName(), names.get(playerId).getScore());
                        //make all players score message
                        makePlayersScoresMessage();
                        //send message to client about the end of the game
                        out.println("MESSAGE::END_OF_GAME=="+scoreMessage);
                        //out.print("MESSAGE::" + tempStr);
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (name != null) {
                            names.remove(name);
                        }

                        if (out != null) {
                            writers.remove(out);
                        }
                        try {
                            socket.close();
                            if (names.size()==0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent;
                                        intent = new Intent(getBaseContext(), ConfigurationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        }
                        catch (IOException e) {
                        }
                    }//last question

                    //question is given to client
                    out.println("NEW_QUESTION::" + questionsSet.get(questionNumber).toString(true));
                    if (serverSounds) {
                        success.start();
                    }
                    //keeping timeout
                    long timePoint = System.currentTimeMillis();
                    final int qn = questionNumber;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final String k = ""+qn;

                            roundText.setText(k);
                            chronometer = new MyCountDownTimer(maxAnswerTime*1000, 1000);
                            chronometer.countingType = 2;
                            chronometer.preString = ":";
                            chronometer.start();
                            questionPanel.setText(questionsSet.get(qn).toString());
                        }
                    });
                    boolean answered = false;
                    //getting answer (only one try per player
                    //answer type is  PLAYERNAME::ANSWER
                    int currentAnswer = -1;
                    String currentPlayer = "";
                    while (true){
                        //time out reached

                        if ((System.currentTimeMillis() - timePoint) >= (maxAnswerTime * 1000)) {
                            System.out.println("END OF TIME!");
                            questionNumber++;

                            break;
                        }

                        if (!answered) {
                            String input = in.readLine();
                            if (input != null ) {
                                //getting player and answer from message
                                String[] stringArray = input.split("::");
                                currentPlayer = stringArray[0];
                                currentAnswer = Integer.parseInt(stringArray[1]);
                                System.out.println(currentAnswer);
                                answered = true;


                                //if is a buzzer answer
                                if (currentAnswer == 0) {
                                    if (serverSounds) {
                                        buzzer.start();
                                    }
                                    //informing other players about buzzing
                                    for (int i = 0; i < writers.size(); i++) {
                                        System.out.println("MESSAGE::BUZZ");
                                        writers.get(i).println("MESSAGE::BUZZ");
                                    }
                                    //server is waiting for user decision
                                    socketServerThread.buzzWait();
                                    //after users's decision informing all users and going to the next question
                                    for (int i = 0; i < writers.size(); i++) {
                                        System.out.println("MESSAGE::DONE_BUZZ");
                                        writers.get(i).println("MESSAGE::DONE_BUZZ");
                                    }

                                } else if (currentAnswer == 9) {//buzzer


                                } else {
                                    //correct answer
                                    if (questionsSet.get(questionNumber).getCorrect() == currentAnswer) {
                                        if (serverSounds) {
                                            success.start();
                                        }
                                        for (int i = 0; i < writers.size(); i++) {
                                            //informing other players without giving player's name
                                            if ((informOtherPlayers) && (!(names.get(i).getNickName().equals(currentPlayer)))) {
                                                writers.get(i).println("MESSAGE::CORRECT:" + currentPlayer);
                                            }
                                            //informing other players giving player's name
                                            if ((!informOtherPlayers) && (!(names.get(i).getNickName().equals(currentPlayer)))) {
                                                writers.get(i).println("MESSAGE::CORRECT:N/A");
                                            }
                                            //informing current player
                                            if (names.get(i).getNickName().equals(currentPlayer)) {
                                                writers.get(i).println("MESSAGE::CORRECT:" + currentPlayer);
                                                names.get(i).addScore(correctScore);
                                            }

                                        }
                                        //updating the UI
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                String k = answeredText.getText().toString();
                                                int k1 = Integer.parseInt(k) + 1;
                                                answeredText.setText("" + k1);
                                            }
                                        });
                                        //if all players answer
                                        if (gameType == 1) {
                                            //questionNumber++;
                                        } else if (gameType == 2) {//if one player answers
                                            //questionNumber++;
                                        }
                                    } else {//wrong answer
                                        if (serverSounds) {
                                            error.start();
                                        }
                                        for (int i = 0; i < writers.size(); i++) {
                                            //informing other players without giving player's name
                                            if ((informOtherPlayers) && (!(names.get(i).getNickName().equals(currentPlayer)))) {
                                                writers.get(i).println("MESSAGE::ERROR:" + currentPlayer);
                                            }
                                            //informing other players giving player's name
                                            if ((!informOtherPlayers) && (!(names.get(i).getNickName().equals(currentPlayer)))) {
                                                writers.get(i).println("MESSAGE::ERROR:N/A");
                                            }
                                            //informing current player
                                            if (names.get(i).getNickName().equals(currentPlayer)) {
                                                writers.get(i).println("MESSAGE::ERROR:" + currentPlayer);
                                            }
                                        }
                                        if (gameType == 1) {
                                            //questionNumber++;
                                        } else if (gameType == 2) {
                                            //questionNumber++;
                                        }
                                    }
                                }
                            }
                            else {//client disconnected
                                //there is not any connection with client
                                final String disPlayer =  names.get(playerId).getNickName();
                                names.get(playerId).setNickName("--Disconnected--");
                                playersConnected--;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        playersText.setText(""+playersConnected);
                                        makePlayersList();
                                        Toast.makeText(getBaseContext(),getText(R.string._m_player)+" "+disPlayer+" " +getText(R.string._m_disconnected), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                try {
                                    socket.close();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent;
                                            intent = new Intent(getBaseContext(), ConfigurationActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    return;
                                }
                                catch (IOException e) {
                                }
                            }
                        }
                    }
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                }
                catch (IOException e) {
                }
            }
        }


    }


    // gets the ip address of your phone's network
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if ((!inetAddress.isLoopbackAddress()) && (inetAddress instanceof Inet4Address))
                    { return inetAddress.getHostAddress().toString(); }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }
    private void sortPlayersByScore() {
        int n = names.size();
        for (int c = 0; c < (n - 1); c++) {
            for (int d = 0; d < n - c - 1; d++) {
                if (names.get(d).getScore() < names.get(d + 1).getScore()) {
                    Player swap = new Player();
                    swap.setNickName(names.get(d).getNickName());
                    swap.setScore(names.get(d).getScore());

                    names.get(d).setNickName(names.get(d + 1).getNickName());
                    names.get(d).setScore(names.get(d + 1).getScore());

                    names.get(d + 1).setNickName(swap.getNickName());
                    names.get(d + 1).setScore(swap.getScore());

                }
            }
        }
    }




    // CountDownTimer class
    public class MyCountDownTimer extends CountDownTimer {
        public String preString = "";
        public int countingType = 0;
        public MyCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            timeOutText.setText("0");

        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            timeOutText.setText("" + (millisUntilFinished/1000));

        }
    }

}
