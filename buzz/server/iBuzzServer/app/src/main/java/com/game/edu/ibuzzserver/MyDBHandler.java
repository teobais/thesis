package com.game.edu.ibuzzserver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =2;
    private static final String DATABASE_PATH = "/data/data/com.game.edu.ibuzzserver/databases/";
    private static final String DATABASE_NAME = "movies.db";
    private static final String TABLE_MOVIES = "movie";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_RATED = "rated";
    public static final String COLUMN_RELEASED = "released";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_WRITER = "writer";
    public static final String COLUMN_ACTORS = "actors";
    public static final String COLUMN_PLOT = "plot";
    public static final String COLUMN_POSTER = "poster";
    public static final String COLUMN_RUNTIME = "runtime";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_VOTES = "votes";
    public static final String COLUMN_IMDB = "imdb";
    public static final String COLUMN_TSTAMP = "tstamp";


    private static final String TABLE_SCORES = "scores";

    public static final String COLUMN_PLAYERNAME = "player";
    public static final String COLUMN_DATETIME = "whengained";
    public static final String COLUMN_SCORE = "score";

    public long Rows;

    private final Context context;
    private SQLiteDatabase dataBase;

    public MyDBHandler(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) throws IOException {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
        create();
    }

    // Creates a empty database on the system and rewrites it with your own database.
    public void create() throws IOException{
        boolean dbExist = checkDataBase();
        System.out.println("DBEXIST = " + dbExist);
        if(dbExist){
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            System.out.println("Going to create DB 1!");
            this.getReadableDatabase();
            System.out.println("Going to create DB 2!");

            open();
            System.out.println("Going to create DB 3!");
            onCreate(dataBase);
            System.out.println("Going to create DB 4!");
            close();
            System.out.println("Going to create DB 5!");
        }

    }

    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String path = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            // database don't exist yet.
            e.printStackTrace();
        }
        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    // copy your assets db to the new system DB
    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //Open the database
    public boolean open() {
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            dataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            SQLiteStatement s = dataBase.compileStatement("select count(*) from movie;");
            Rows = s.simpleQueryForLong();

            return true;
        } catch(android.database.SQLException sqle) {
            dataBase = null;
            return false;
        }
    }

    @Override
    public synchronized void close() {
        if(dataBase != null)
            dataBase.close();
        super.close();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS movie ";
        CREATE_TABLE += "("+COLUMN_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT,  ";
        CREATE_TABLE += COLUMN_TITLE+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_YEAR+" VARCHAR DEFAULT NULL,  ";//
        CREATE_TABLE += COLUMN_RATED+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_RELEASED+" VARCHAR DEFAULT NULL,  ";//
        CREATE_TABLE += COLUMN_GENRE+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_DIRECTOR+" VARCHAR DEFAULT NULL,  ";//
        CREATE_TABLE += COLUMN_WRITER+" VARCHAR DEFAULT NULL,  ";//
        CREATE_TABLE += COLUMN_ACTORS+" VARCHAR DEFAULT NULL,  ";//
        CREATE_TABLE += COLUMN_PLOT+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_POSTER+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_RUNTIME+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_RATING+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_VOTES+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_IMDB+" VARCHAR DEFAULT NULL,  ";
        CREATE_TABLE += COLUMN_TSTAMP+" VARCHAR DEFAULT NULL); ";

        db.execSQL(CREATE_TABLE);

        String INSERT_VALUES;
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'The Inglorious Bastards', '1978', 'R', '01 Dec 1981', 'Action, Adventure, Comedy', 'Enzo G. Castellari', 'Sandro Continenza, Sergio Grieco, Romano Migliorini, Laura Toscano, Franco Marotta, Alberto Piferi (dialogue collaboration)', 'Bo Svenson, Peter Hooten, Fred Williamson, Michael Pergolani', 'In 1944, in France, the rogue American soldiers Lieutenant Robert Yeager, Private Fred Canfield, the murderer Tony, the thief Nick and the coward Berle are transported to a military prison....', 'http://ia.media-imdb.com/images/M/MV5BMTU1NjM5NTYwM15BMl5BanBnXkFtZTcwODYxMjQ3Mg@@._V1_SX300.jpg', '99 min', '6.6', '6,781', 'tt0076584', '1434315842');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'The Godfather II', '2009', 'N/A', '07 Apr 2009', 'Animation, Action, Crime', 'Si Duy Tran', 'N/A', 'Robert Duvall, Carlos Ferro, John Mariano, Danny Jacobs', 'A video game in which players control a new member of the Corleone crime family who is rising through the ranks.', 'http://ia.media-imdb.com/images/M/MV5BODY0OTIxNTI1Ml5BMl5BanBnXkFtZTcwNjc0NjkzMg@@._V1_SX300.jpg', 'N/A', '7.5', '656', 'tt1198207', '1434315844');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'Goodfellas', '1990', 'R', '21 Sep 1990', 'Biography, Crime, Drama', 'Martin Scorsese', 'Nicholas Pileggi (book), Nicholas Pileggi (screenplay), Martin Scorsese (screenplay)', 'Robert De Niro, Ray Liotta, Joe Pesci, Lorraine Bracco', 'Henry Hill and his friends work their way up through the mob hierarchy.', 'http://ia.media-imdb.com/images/M/MV5BMTY2OTE5MzQ3MV5BMl5BanBnXkFtZTgwMTY2NTYxMTE@._V1_SX300.jpg', '146 min', '8.7', '627,857', 'tt0099685', '1434315845');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'The Godfather', '1972', 'R', '24 Mar 1972', 'Crime, Drama', 'Francis Ford Coppola', 'Mario Puzo (screenplay), Francis Ford Coppola (screenplay), Mario Puzo (novel)', 'Marlon Brando, Al Pacino, James Caan, Richard S. Castellano', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'http://ia.media-imdb.com/images/M/MV5BMjEyMjcyNDI4MF5BMl5BanBnXkFtZTcwMDA5Mzg3OA@@._V1_SX300.jpg', '175 min', '9.2', '1,001,492', 'tt0068646', '1434315846');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'Pulp Fiction', '1994', 'R', '14 Oct 1994', 'Crime, Drama', 'Quentin Tarantino', 'Quentin Tarantino (story), Roger Avary (story), Quentin Tarantino', 'Tim Roth, Amanda Plummer, Laura Lovelace, John Travolta', 'The lives of two mob hit men, a boxer, a gangster''s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 'http://ia.media-imdb.com/images/M/MV5BMjE0ODk2NjczOV5BMl5BanBnXkFtZTYwNDQ0NDg4._V1_SX300.jpg', '154 min', '8.9', '1,132,720', 'tt0110912', '1434315847');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'Fight Club', '1999', 'R', '15 Oct 1999', 'Drama', 'David Fincher', 'Chuck Palahniuk (novel), Jim Uhls (screenplay)', 'Edward Norton, Brad Pitt, Helena Bonham Carter, Meat Loaf', 'An insomniac office worker looking for a way to change his life crosses paths with a devil-may-care soap maker and they form an underground fight club that evolves into something much, much more...', 'http://ia.media-imdb.com/images/M/MV5BMjIwNTYzMzE1M15BMl5BanBnXkFtZTcwOTE5Mzg3OA@@._V1_SX300.jpg', '139 min', '8.9', '1,141,595', 'tt0137523', '1434315848');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'Forrest Gump', '1994', 'PG-13', '06 Jul 1994', 'Drama, Romance', 'Robert Zemeckis', 'Winston Groom (novel), Eric Roth (screenplay)', 'Tom Hanks, Rebecca Williams, Sally Field, Michael Conner Humphreys', 'Forrest Gump, while not intelligent, has accidentally been present at many historic moments, but his true love, Jenny Curran, eludes him.', 'http://ia.media-imdb.com/images/M/MV5BMTQwMTA5MzI1MF5BMl5BanBnXkFtZTcwMzY5Mzg3OA@@._V1_SX300.jpg', '142 min', '8.8', '1,035,173', 'tt0109830', '1434315849');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'The Matrix', '1999', 'R', '31 Mar 1999', 'Action, Sci-Fi', 'Andy Wachowski, Lana Wachowski', 'Andy Wachowski, Lana Wachowski', 'Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'http://ia.media-imdb.com/images/M/MV5BMTkxNDYxOTA4M15BMl5BanBnXkFtZTgwNTk0NzQxMTE@._V1_SX300.jpg', '136 min', '8.7', '1,037,112', 'tt0133093', '1434315851');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'Troy', '2004', 'R', '14 May 2004', 'Adventure', 'Wolfgang Petersen', 'Homer (poem), David Benioff (screenplay)', 'Julian Glover, Brian Cox, Nathan Jones, Adoni Maropis', 'An adaptation of Homer''s great epic, the film follows the assault on Troy by the united Greek forces and chronicles the fates of the men involved.', 'http://ia.media-imdb.com/images/M/MV5BMTk5MzU1MDMwMF5BMl5BanBnXkFtZTcwNjczODMzMw@@._V1_SX300.jpg', '163 min', '7.2', '334,976', 'tt0332452', '1434315852');";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO movie VALUES (NULL , 'Gladiator', '2000', 'R', '05 May 2000', 'Action, Drama', 'Ridley Scott', 'David Franzoni (story), David Franzoni (screenplay), John Logan (screenplay), William Nicholson (screenplay)', 'Russell Crowe, Joaquin Phoenix, Connie Nielsen, Oliver Reed', 'When a Roman general is betrayed and his family murdered by an emperor''s corrupt son, he comes to Rome as a gladiator to seek revenge.', 'http://ia.media-imdb.com/images/M/MV5BMTgwMzQzNTQ1Ml5BMl5BanBnXkFtZTgwMDY2NTYxMTE@._V1_SX300.jpg', '155 min', '8.5', '837,051', 'tt0172495', '1434315853');";
        db.execSQL(INSERT_VALUES);


        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORES
                + "("
                + COLUMN_DATETIME + " TEXT,"
                + COLUMN_PLAYERNAME + " TEXT,"
                + COLUMN_SCORE + " INTEGER "
                + ")";
        db.execSQL(CREATE_TABLE);

        INSERT_VALUES = "INSERT INTO scores VALUES ('01/01/2015 00:00:00' , 'user8', 109)";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO scores VALUES ('01/02/2015 00:00:00' , 'user8', 119)";
        db.execSQL(INSERT_VALUES);
        INSERT_VALUES = "INSERT INTO scores VALUES ('01/03/2015 00:00:00' , 'user8', 209)";
        db.execSQL(INSERT_VALUES);


    }

    public void updateFromWeb(String s, boolean keepingOldEntries){
        Download D = new Download(s);
        open();
        if (!keepingOldEntries){
            dataBase.execSQL("delete from movie");
        }
        for (int i = 0; i < D.page.size(); i++){
            if ((D.page.get(i)!=null) && (D.page.get(i).length()>0)){
                dataBase.execSQL(D.page.get(i));
            }
        }
        close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void update(){
        open();
        onUpgrade(dataBase,1,1);
        close();
    }



    public void addScore(String D,String P,int S) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATETIME, D);
        values.put(COLUMN_PLAYERNAME, P);
        values.put(COLUMN_SCORE, S);
        SQLiteDatabase db = this.getWritableDatabase();

        dataBase.insert(TABLE_SCORES, null, values);
        dataBase.close();
    }

    public void addEntry(Movie M,String s) {
        if (s!=null){
            dataBase.execSQL(s);
        }
        else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, M.id);
            values.put(COLUMN_TITLE, M.title);
            values.put(COLUMN_ACTORS, M.actors);
            values.put(COLUMN_DIRECTOR, M.director);
            values.put(COLUMN_GENRE, M.genre);
            values.put(COLUMN_IMDB, M.imdb);
            values.put(COLUMN_PLOT, M.plot);
            values.put(COLUMN_POSTER, M.poster);
            values.put(COLUMN_RATED, M.rated);
            values.put(COLUMN_RATING, M.rating);
            values.put(COLUMN_RELEASED, M.released);
            values.put(COLUMN_YEAR, M.year);
            values.put(COLUMN_WRITER, M.writer);
            values.put(COLUMN_VOTES, M.votes);
            values.put(COLUMN_TSTAMP, M.tstamp);
            values.put(COLUMN_RUNTIME, M.runtime);

            open();

            dataBase.insert(TABLE_MOVIES, null, values);
            close();

        }

    }

    public String getBetterScores(){
        String[] queryTable = new String[3];

        queryTable[0] = COLUMN_PLAYERNAME;
        queryTable[1] = COLUMN_DATETIME;
        queryTable[2] = COLUMN_SCORE;

        open();

        int i = 0;
        Cursor cursor = dataBase.query(TABLE_SCORES,queryTable, null, null, null, null, COLUMN_SCORE+" DESC",null);
        String res = "";
        if (cursor.moveToFirst()) {
            do {
                res = res + cursor.getString(0) + " ," + cursor.getString(1) + " ," + Integer.parseInt(cursor.getString(2)) + "#";
                i++;
                if (i==10) { break; }
            } while (cursor.moveToNext());
            cursor.close();
        }

        close();
        return res;

    }

    public Movie getMovie(long x, String[] filters){
        String[] queryTable = new String[16];
        queryTable[0] = COLUMN_TITLE;
        queryTable[1] = COLUMN_TITLE;
        queryTable[2] = COLUMN_YEAR;
        queryTable[3] = COLUMN_RATED;
        queryTable[4] = COLUMN_RELEASED;
        queryTable[5] = COLUMN_GENRE;
        queryTable[6] = COLUMN_DIRECTOR;
        queryTable[7] = COLUMN_WRITER;
        queryTable[8] = COLUMN_ACTORS;
        queryTable[9] = COLUMN_PLOT;
        queryTable[10] = COLUMN_POSTER;
        queryTable[11] = COLUMN_RUNTIME;
        queryTable[12] = COLUMN_RATING;
        queryTable[13] = COLUMN_VOTES;
        queryTable[14] = COLUMN_IMDB;
        queryTable[15] = COLUMN_TSTAMP;
        //SQLiteDatabase db = this.getWritableDatabase();
        open();

        String where_clause = " (1 = 1) ";
        String actorF = "";
        String genderF = "";
        String yearF = "";
        String ratingF = "";
        if (filters[0].equals("")==false){
            String tempFilter[] = filters[0].split(",");
            for (int i=0;i<tempFilter.length;i++){
                if (i!=0) {
                    actorF = actorF + " or " + COLUMN_ACTORS + " like '" + tempFilter[i].trim() + "' ";
                }
                else if (i==0){
                    actorF = " ( " + actorF + COLUMN_ACTORS + " like '" + tempFilter[i].trim() + "' ";
                }
                if (i==(tempFilter.length-1)){
                    actorF += " )";
                }
            }
            where_clause += " AND "  + actorF;
        }
        if (filters[1].equals("")==false){
            String tempFilter[] = filters[1].split(",");
            for (int i=0;i<tempFilter.length;i++){
                if (i!=0) {
                    genderF = genderF + " or " + COLUMN_GENRE + " like '" + tempFilter[i].trim() + "' ";
                }
                else if (i==0){
                    genderF = " ( " + genderF  + COLUMN_GENRE + " like '" + tempFilter[i].trim() + "' ";
                }
                if (i==(tempFilter.length-1)){
                    genderF += " )";
                }
            }
            where_clause += " AND "  + genderF;
        }
        if (filters[2].equals("")==false) {
            String tempFilter[] = filters[2].split("-");
            int up, down;
            up = Integer.parseInt(tempFilter[1].trim());
            down = Integer.parseInt(tempFilter[0].trim());
            for (int i = down; i <= up; i++) {
                yearF += "'" + i + "'";
                if (i != up) {
                    yearF += ",";
                }
            }
            yearF = COLUMN_YEAR + " in ( " + yearF + " ) ";
            where_clause += " AND "  + yearF;
        }
        if (filters[3].equals("")==false) {
            String tempFilter[] = filters[3].split("-");
            double up, down, k;
            up = Double.parseDouble(tempFilter[1].trim().substring(0,3));
            down = Double.parseDouble(tempFilter[0].substring(0,3));
            System.out.println("UP = "+ up + " DOWN " + down);
            k=down;
            while (k < (up+0.01)) {
                ratingF += "'" + String.format("%.01f", k) + "'";
                if (k != up) {
                    ratingF += ",";
                }
                k=k+0.1;
                System.out.println("K = "+ k);
            }
            ratingF = COLUMN_RATING + " in ( " + ratingF.substring(0,ratingF.length()-1) + " ) ";
            where_clause += " AND "  + ratingF;
        }


        Cursor cursor = dataBase.query(TABLE_MOVIES,queryTable, where_clause, null, null, null, null,String.valueOf(x)+",1");
        System.out.println("where_clause = " + where_clause);
        if (cursor.moveToFirst()) {
            Movie M = new Movie();
            M.id = ""+x;
            M.title = cursor.getString(1);
            M.year = cursor.getString(2);
            M.rated = cursor.getString(3);
            M.released = cursor.getString(4);
            M.genre = cursor.getString(5);
            M.director = cursor.getString(6);
            M.writer = cursor.getString(7);
            M.actors = cursor.getString(8);
            M.plot = cursor.getString(9);
            M.poster = cursor.getString(10);
            M.runtime = cursor.getString(11);
            M.rating = cursor.getString(12);
            M.votes = cursor.getString(13);
            M.imdb = cursor.getString(14);
            M.tstamp = cursor.getString(15);
            close();

            return M;
        }
        else {
            return null;
        }

    }

    public boolean isInArray(long[] a, long y){
        for (int i = 0;i<5;i++){
            if (y==a[i]){
                return true;
            }
        }
        return false;
    }

    public Question makeQuestion(int id,String ty, String[] filters) {
        int recordPointer = 0;
        String[] filtersNone = {"","","",""};
        long[] records = new long[5];
        Movie M = getMovie(id,filters);
        if (M == null){

            System.out.println("Error id="+id+" ty="+ty);
            return null;
        }
        records[0] = id;
        recordPointer++;


        Random r = new Random();
        Question Q = new Question();
        Q.setPoster(M.poster);
        if ("actor".equals(ty)) {//QUESTION FOR STAR
            int c = r.nextInt(4) + 1;//number in [1..5]
            Q.setQuestion(M.id + ". Who is the star of \"" + M.title + "?\"");
            Q.setCorrect(c);
            Q.setAnswer(c-1,M.actors.split(",")[0].trim());
            for (int i=1;i<5;i++){
                int k=c-1+i;
                if (k>=5){
                    k=k-5;
                }
                long rec;
                rec = (Math.abs(r.nextLong())%Rows-1) + 1;
                while (isInArray(records,rec)){
                    rec = (Math.abs(r.nextLong())%Rows-1) + 1;
                }

                records[recordPointer]=rec;
                Movie M1 = getMovie(rec,filtersNone);
                if (M1 == null){
                    System.out.println("Error rec="+rec+" ty="+ty);
                    return null;
                }


                int nextAns = 0;
                while (Q.answerExists(M1.actors.split(",")[nextAns].trim())){
                    nextAns++;
                }
                Q.setAnswer(k, M1.actors.split(",")[nextAns].trim());
            }

        }
        else if ("director".equals(ty)) {//QUESTION FOR WRITER
            int c = r.nextInt(4) + 1;
            Q.setQuestion(M.id + ". Who is the director of \"" + M.title + "?\"");
            Q.setCorrect(c);
            Q.setAnswer(c-1,M.writer.split(",")[0].trim());
            for (int i=1;i<5;i++){
                int k=c-1+i;
                if (k>=5){
                    k=k-5;
                }
                int rec = r.nextInt((int)Rows-1)+1;
                while (isInArray(records,rec)){
                    rec = r.nextInt((int)Rows-1)+1;

                }
                Movie M1 = getMovie(rec,filtersNone);
                while ((Q.answerExists(M1.director.trim()))||("N/A".equals(M1.writer.trim()))){
                    rec = ((++rec)%((int)Rows-1))+1;
                    M1 = getMovie(rec,filtersNone);
                }
                Q.setAnswer(k, M1.director.trim());
                records[recordPointer]=rec;
            }
        }
        else if ("writer".equals(ty)) {//QUESTION FOR WRITER
            int c = r.nextInt(4) + 1;
            Q.setQuestion(M.id + ". Who is the writer of \"" + M.title + "?\"");
            Q.setCorrect(c);
            Q.setAnswer(c-1,M.writer.split(",")[0].trim());
            for (int i=1;i<5;i++){
                int k=c-1+i;
                if (k>=5){
                    k=k-5;
                }
                int rec = r.nextInt((int)Rows-1)+1;
                while (isInArray(records,rec)){
                    rec = r.nextInt((int)Rows-1)+1;

                }
                Movie M1 = getMovie(rec,filtersNone);
                long changeCounter = 0;
                while ((Q.answerExists(M1.writer.split(",")[0].trim()))||("N/A".equals(M1.writer.trim()))){
                    rec = ((++rec)%((int)Rows-1))+1;
                    M1 = getMovie(rec,filtersNone);
                    if (++changeCounter == Rows){
                        break;
                    }
                }
                Q.setAnswer(k, M1.writer.trim());
                records[recordPointer]=rec;
            }
        }
        else if ("released".equals(ty)) {//QUESTION FOR WRITER
            int c = r.nextInt(4) + 1;
            Q.setQuestion(M.id + ". When had movie \"" + M.title + "\" released?");
            Q.setCorrect(c);
            Q.setAnswer(c-1,M.released.split(",")[0].trim());
            for (int i=1;i<5;i++){
                int k=c-1+i;
                if (k>=5){
                    k=k-5;
                }
                int rec = r.nextInt((int)Rows-1)+1;
                while (isInArray(records,rec)){
                    rec = r.nextInt((int)Rows-1)+1;

                }
                Movie M1 = getMovie(rec,filtersNone);
                while ((Q.answerExists(M1.released.trim()))||("N/A".equals(M1.writer.trim()))){
                    rec = ((++rec)%((int)Rows-1))+1;
                    M1 = getMovie(rec,filtersNone);
                }
                Q.setAnswer(k, M1.released.trim());
                records[recordPointer]=rec;
            }
        }
        else if ("year".equals(ty)) {//QUESTION FOR YEAR
            int c = r.nextInt(4) + 1;
            Q.setQuestion(M.id + ". When had movie \"" + M.title + "\" produced?");
            Q.setCorrect(c);
            Q.setAnswer(c-1,M.year.split(",")[0].trim());
            for (int i=1;i<5;i++){
                int k=c-1+i;
                if (k>=5){
                    k=k-5;
                }
                int rec = r.nextInt((int)Rows-1)+1;
                while (isInArray(records,rec)){
                    rec = r.nextInt((int)Rows-1)+1;

                }
                Movie M1 = getMovie(rec,filtersNone);
                while ((Q.answerExists(M1.year.trim()))||("N/A".equals(M1.writer.trim()))){
                    rec = ((++rec)%((int)Rows-1))+1;
                    M1 = getMovie(rec,filtersNone);
                }
                Q.setAnswer(k, M1.year.trim());
                records[recordPointer]=rec;
            }
        }
        return Q;
    }
}


