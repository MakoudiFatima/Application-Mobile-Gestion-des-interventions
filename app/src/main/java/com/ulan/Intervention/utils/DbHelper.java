package com.ulan.Intervention.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ulan.Intervention.model.Intervention;
import com.ulan.Intervention.model.Note;
import com.ulan.Intervention.model.Job;
import com.ulan.Intervention.model.Week;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "intervention_db";
    private static final String TIMETABLE = "timetable";
    private static final String WEEK_ID = "id";
    private static final String WEEK_SUBJECT = "subject";
    private static final String WEEK_FRAGMENT = "fragment";
    private static final String WEEK_JOB = "job";
    private static final String WEEK_ROOM = "room";
    private static final String WEEK_FROM_TIME = "fromtime";
    private static final String WEEK_TO_TIME = "totime";
    private static final String WEEK_COLOR = "color";



    private static final String NOTES = "notes";
    private static final String NOTES_ID = "id";
    private static final String NOTES_TITLE = "title";
    private static final String NOTES_TEXT = "text";
    private static final String NOTES_COLOR = "color";

    private static final String JOBS = "jobs";
    private static final String JOBS_ID = "id";
    private static final String JOBS_NAME = "name";
    private static final String JOBS_POST = "post";
    private static final String JOBS_PHONE_NUMBER = "phonenumber";
    private static final String JOBS_EMAIL = "email";
    private static final String JOBS_COLOR = "color";

    private static final String INTERVENTIONS = "interventions";
    private static final String INTERVENTIONS_ID = "id";
    private static final String INTERVENTIONS_TYPE = "type";
    private static final String INTERVENTIONS_CLIENT = "client";
    private static final String INTERVENTIONS_NUMBER = "number";
    private static final String INTERVENTIONS_ADRESSE = "adresse";
    private static final String INTERVENTIONS_RECETTE = "recette";
    private static final String INTERVENTIONS_STATUS = "status";
    private static final String INTERVENTIONS_PRIX = "prix";
    private static final String INTERVENTIONS_DATE = "date";
    private static final String INTERVENTIONS_TIME = "time";

    private static final String INTERVENTIONS_COLOR = "color";


    public static final String USERS="users";
    public static final String USERS_ID="id";
    public static final String USERS_NAME="name";
    public static final String USERS_PHONE="phone";
    public static final String USERS_GMAIL="gmail";
    public static final String USERS_PASSWORD="password";



    public DbHelper(Context context){
        super(context , DB_NAME, null, DB_VERSION);
    }

     public void onCreate(SQLiteDatabase db) {
        String CREATE_TIMETABLE = "CREATE TABLE " + TIMETABLE + "("
                + WEEK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WEEK_SUBJECT + " TEXT,"
                + WEEK_FRAGMENT + " TEXT,"
                + WEEK_JOB + " TEXT,"
                + WEEK_ROOM + " TEXT,"
                + WEEK_FROM_TIME + " TEXT,"
                + WEEK_TO_TIME + " TEXT,"
                + WEEK_COLOR + " INTEGER" +  ")";



        String CREATE_NOTES = "CREATE TABLE " + NOTES + "("
                + NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTES_TITLE + " TEXT,"
                + NOTES_TEXT + " TEXT,"
                + NOTES_COLOR + " INTEGER" + ")";

        String CREATE_JOBS = "CREATE TABLE " + JOBS + "("
                + JOBS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + JOBS_NAME + " TEXT,"
                + JOBS_POST + " TEXT,"
                + JOBS_PHONE_NUMBER + " TEXT,"
                + JOBS_EMAIL + " TEXT,"
                + JOBS_COLOR + " INTEGER" + ")";

        String CREATE_INTERVENTIONS = "CREATE TABLE " + INTERVENTIONS + "("
                + INTERVENTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + INTERVENTIONS_TYPE + " TEXT,"
                + INTERVENTIONS_CLIENT + " TEXT,"
                + INTERVENTIONS_NUMBER + " TEXT,"
                + INTERVENTIONS_ADRESSE + " TEXT,"
                + INTERVENTIONS_PRIX + " TEXT,"
                + INTERVENTIONS_RECETTE + " TEXT,"
                + INTERVENTIONS_STATUS + " TEXT,"
                + INTERVENTIONS_DATE + " TEXT,"
                + INTERVENTIONS_TIME+ " TEXT,"
                + INTERVENTIONS_COLOR + " INTEGER" + ")";

         String CREATE_USERS = "CREATE TABLE " + USERS + "("
                 + USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + USERS_NAME + " TEXT,"
                 + USERS_PHONE + " TEXT,"
                 + USERS_GMAIL+ " TEXT,"
                 + USERS_PASSWORD + " TEXT" + ")";

        db.execSQL(CREATE_TIMETABLE);
        db.execSQL(CREATE_NOTES);
        db.execSQL(CREATE_JOBS);
        db.execSQL(CREATE_INTERVENTIONS);
         db.execSQL(CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("DROP TABLE IF EXISTS " + TIMETABLE);

            case 2:
                db.execSQL("DROP TABLE IF EXISTS " + NOTES);

            case 3:
                db.execSQL("DROP TABLE IF EXISTS " + JOBS);

            case 4:
                db.execSQL("DROP TABLE IF EXISTS " + INTERVENTIONS);
                break;

            case 5 :
                db.execSQL("DROP TABLE IF EXISTS " + USERS);
        }
        onCreate(db);
    }

    /**
     * Methods for Week fragments
     **/
    public void insertWeek(Week week){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEEK_SUBJECT, week.getSubject());
        contentValues.put(WEEK_FRAGMENT, week.getFragment());
        contentValues.put(WEEK_JOB, week.getJob());
        contentValues.put(WEEK_ROOM, week.getRoom());
        contentValues.put(WEEK_FROM_TIME, week.getFromTime());
        contentValues.put(WEEK_TO_TIME, week.getToTime());
        contentValues.put(WEEK_COLOR, week.getColor());
        db.insert(TIMETABLE,null, contentValues);
        db.update(TIMETABLE, contentValues, WEEK_FRAGMENT, null);
        db.close();
    }

    public void deleteWeekById(Week week) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TIMETABLE, WEEK_ID + " = ? ", new String[]{String.valueOf(week.getId())});
        db.close();
    }

    public void updateWeek(Week week) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEEK_SUBJECT, week.getSubject());
        contentValues.put(WEEK_JOB, week.getJob());
        contentValues.put(WEEK_ROOM, week.getRoom());
        contentValues.put(WEEK_FROM_TIME,week.getFromTime());
        contentValues.put(WEEK_TO_TIME, week.getToTime());
        contentValues.put(WEEK_COLOR, week.getColor());
        db.update(TIMETABLE, contentValues, WEEK_ID + " = " + week.getId(), null);
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Week> getWeek(String fragment){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Week> weeklist = new ArrayList<>();
        Week week;
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+TIMETABLE+" ORDER BY " + WEEK_FROM_TIME + " ) WHERE "+ WEEK_FRAGMENT +" LIKE '"+fragment+"%'",null);
        while (cursor.moveToNext()){
            week = new Week();
            week.setId(cursor.getInt(cursor.getColumnIndex(WEEK_ID)));
            week.setSubject(cursor.getString(cursor.getColumnIndex(WEEK_SUBJECT)));
            week.setJob(cursor.getString(cursor.getColumnIndex(WEEK_JOB)));
            week.setRoom(cursor.getString(cursor.getColumnIndex(WEEK_ROOM)));
            week.setFromTime(cursor.getString(cursor.getColumnIndex(WEEK_FROM_TIME)));
            week.setToTime(cursor.getString(cursor.getColumnIndex(WEEK_TO_TIME)));
            week.setColor(cursor.getInt(cursor.getColumnIndex(WEEK_COLOR)));
            weeklist.add(week);
        }
        return  weeklist;
    }


    /**
     * Methods for Notes activity
     **/
    public void insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, note.getTitle());
        contentValues.put(NOTES_TEXT, note.getText());
        contentValues.put(NOTES_COLOR, note.getColor());
        db.insert(NOTES, null, contentValues);
        db.close();
    }

    public void updateNote(Note note)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, note.getTitle());
        contentValues.put(NOTES_TEXT, note.getText());
        contentValues.put(NOTES_COLOR, note.getColor());
        db.update(NOTES, contentValues, NOTES_ID + " = " + note.getId(), null);
        db.close();
    }

    public void deleteNoteById(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTES, NOTES_ID + " =? ", new String[] {String.valueOf(note.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Note> getNote() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Note> notelist = new ArrayList<>();
        Note note;
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTES, null);
        while (cursor.moveToNext()) {
            note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex(NOTES_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(NOTES_TITLE)));
            note.setText(cursor.getString(cursor.getColumnIndex(NOTES_TEXT)));
            note.setColor(cursor.getInt(cursor.getColumnIndex(NOTES_COLOR)));
            notelist.add(note);
        }
        cursor.close();
        db.close();
        return notelist;
    }

    /**
     * Methods for jobs activity
     **/
    public void insertJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JOBS_NAME, job.getName());
        contentValues.put(JOBS_POST, job.getPost());
        contentValues.put(JOBS_PHONE_NUMBER, job.getPhonenumber());
        contentValues.put(JOBS_EMAIL, job.getEmail());
        contentValues.put(JOBS_COLOR, job.getColor());
        db.insert(JOBS, null, contentValues);
        db.close();
    }

    public void updateJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JOBS_NAME, job.getName());
        contentValues.put(JOBS_POST, job.getPost());
        contentValues.put(JOBS_PHONE_NUMBER, job.getPhonenumber());
        contentValues.put(JOBS_EMAIL, job.getEmail());
        contentValues.put(JOBS_COLOR, job.getColor());
        db.update(JOBS, contentValues, JOBS_ID + " = " + job.getId(), null);
        db.close();
    }

    public void deleteJobById(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(JOBS, JOBS_ID + " =? ", new String[] {String.valueOf(job.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Job> getJob() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Job> joblist = new ArrayList<>();
        Job job;
        Cursor cursor = db.rawQuery("SELECT * FROM " + JOBS, null);
        while (cursor.moveToNext()) {
            job = new Job();
            job.setId(cursor.getInt(cursor.getColumnIndex(JOBS_ID)));
            job.setName(cursor.getString(cursor.getColumnIndex(JOBS_NAME)));
            job.setPost(cursor.getString(cursor.getColumnIndex(JOBS_POST)));
            job.setPhonenumber(cursor.getString(cursor.getColumnIndex(JOBS_PHONE_NUMBER)));
            job.setEmail(cursor.getString(cursor.getColumnIndex(JOBS_EMAIL)));
            job.setColor(cursor.getInt(cursor.getColumnIndex(JOBS_COLOR)));
            joblist.add(job);
        }
        cursor.close();
        db.close();
        return joblist;
    }

    /**
     * Methods for Intervention activity
     **/
    public void insertIntervention(Intervention intervention) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INTERVENTIONS_TYPE, intervention.getType());
        contentValues.put(INTERVENTIONS_CLIENT, intervention.getClient());
        contentValues.put(INTERVENTIONS_NUMBER, intervention.getNumber());
        contentValues.put(INTERVENTIONS_ADRESSE, intervention.getAddress());
        contentValues.put(INTERVENTIONS_PRIX, intervention.getPrix());
        contentValues.put(INTERVENTIONS_RECETTE, intervention.getRecette());
        contentValues.put(INTERVENTIONS_STATUS, intervention.getStatus());
        contentValues.put(INTERVENTIONS_DATE, intervention.getDate());
        contentValues.put(INTERVENTIONS_TIME, intervention.getTime());
        contentValues.put(INTERVENTIONS_COLOR, intervention.getColor());
        db.insert(INTERVENTIONS, null, contentValues);
        db.close();
    }

    public void updateIntervention(Intervention intervention) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INTERVENTIONS_TYPE, intervention.getType());
        contentValues.put(INTERVENTIONS_CLIENT, intervention.getClient());
        contentValues.put(INTERVENTIONS_NUMBER, intervention.getNumber());
        contentValues.put(INTERVENTIONS_ADRESSE, intervention.getAddress());
        contentValues.put(INTERVENTIONS_PRIX, intervention.getPrix());
        contentValues.put(INTERVENTIONS_RECETTE, intervention.getRecette());
        contentValues.put(INTERVENTIONS_STATUS, intervention.getStatus());
        contentValues.put(INTERVENTIONS_DATE, intervention.getDate());
        contentValues.put(INTERVENTIONS_TIME, intervention.getTime());
        contentValues.put(INTERVENTIONS_COLOR, intervention.getColor());
        db.update(INTERVENTIONS, contentValues, INTERVENTIONS_ID + " = " + intervention.getId(), null);
        db.close();
    }

    public void deleteInerventionById(Intervention intervention) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INTERVENTIONS, INTERVENTIONS_ID + " =? ", new String[] {String.valueOf(intervention.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Intervention> getIntervention() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Intervention> Interentionslist = new ArrayList<>();
        Intervention intervention;
        Cursor cursor = db.rawQuery("SELECT * FROM " + INTERVENTIONS, null);
        while (cursor.moveToNext()) {
            intervention = new Intervention();
            intervention.setId(cursor.getInt(cursor.getColumnIndex(INTERVENTIONS_ID)));
            intervention.setType(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_TYPE)));
            intervention.setClient(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_CLIENT)));
            intervention.setNumber(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_NUMBER)));
            intervention.setAddress(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_ADRESSE)));
            intervention.setRecette(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_RECETTE)));
            intervention.setStatus(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_STATUS)));
            intervention.setDate(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_DATE)));
            intervention.setTime(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_TIME)));
            intervention.setColor(cursor.getInt(cursor.getColumnIndex(INTERVENTIONS_COLOR)));
            Interentionslist.add(intervention);
        }
        cursor.close();
        db.close();
        return Interentionslist;
    }
    String Fait = "Fait";
    String Annuler = "Annuler";
    String Reporter = "Reporter";
    @SuppressLint("Range")
    public ArrayList<Intervention> getInterventionFait() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Intervention> Interentionslist = new ArrayList<>();
        Intervention intervention;
        Cursor cursor = db.rawQuery("SELECT * FROM " + INTERVENTIONS + "  WHERE " + INTERVENTIONS_STATUS +" LIKE '"+Fait+"%'", null);
        while (cursor.moveToNext()) {
            intervention = new Intervention();
            intervention.setId(cursor.getInt(cursor.getColumnIndex(INTERVENTIONS_ID)));
            intervention.setType(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_TYPE)));
            intervention.setClient(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_CLIENT)));
            intervention.setNumber(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_NUMBER)));
            intervention.setAddress(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_ADRESSE)));
            intervention.setRecette(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_RECETTE)));
            intervention.setStatus(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_STATUS)));
            intervention.setDate(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_DATE)));
            intervention.setTime(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_TIME)));
            intervention.setColor(cursor.getInt(cursor.getColumnIndex(INTERVENTIONS_COLOR)));
            Interentionslist.add(intervention);
        }
        cursor.close();
        db.close();
        return Interentionslist;
    }
    @SuppressLint("Range")
    public ArrayList<Intervention> getInterventionAnnuler() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Intervention> Interentionslist = new ArrayList<>();
        Intervention intervention;
        Cursor cursor = db.rawQuery("SELECT * FROM " + INTERVENTIONS + "  WHERE " + INTERVENTIONS_STATUS +" LIKE '"+Annuler+"%'", null);
        while (cursor.moveToNext()) {
            intervention = new Intervention();
            intervention.setId(cursor.getInt(cursor.getColumnIndex(INTERVENTIONS_ID)));
            intervention.setType(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_TYPE)));
            intervention.setClient(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_CLIENT)));
            intervention.setNumber(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_NUMBER)));
            intervention.setAddress(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_ADRESSE)));
            intervention.setRecette(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_RECETTE)));
            intervention.setStatus(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_STATUS)));
            intervention.setDate(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_DATE)));
            intervention.setTime(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_TIME)));
            intervention.setColor(cursor.getInt(cursor.getColumnIndex(INTERVENTIONS_COLOR)));
            Interentionslist.add(intervention);
        }
        cursor.close();
        db.close();
        return Interentionslist;
    }
    @SuppressLint("Range")
    public ArrayList<Intervention> getInterventionReporter() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Intervention> Interentionslist = new ArrayList<>();
        Intervention intervention;
        Cursor cursor = db.rawQuery("SELECT * FROM " + INTERVENTIONS + "  WHERE " + INTERVENTIONS_STATUS +" LIKE '"+Reporter+"%'", null);
        while (cursor.moveToNext()) {
            intervention = new Intervention();
            intervention.setId(cursor.getInt(cursor.getColumnIndex(INTERVENTIONS_ID)));
            intervention.setType(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_TYPE)));
            intervention.setClient(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_CLIENT)));
            intervention.setNumber(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_NUMBER)));
            intervention.setAddress(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_ADRESSE)));
            intervention.setRecette(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_RECETTE)));
            intervention.setStatus(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_STATUS)));
            intervention.setDate(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_DATE)));
            intervention.setTime(cursor.getString(cursor.getColumnIndex(INTERVENTIONS_TIME)));
            intervention.setColor(cursor.getInt(cursor.getColumnIndex(INTERVENTIONS_COLOR)));
            Interentionslist.add(intervention);
        }
        cursor.close();
        db.close();
        return Interentionslist;
    }
}
