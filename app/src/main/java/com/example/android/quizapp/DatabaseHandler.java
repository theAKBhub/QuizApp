package com.example.android.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class - DatabaseHandler
 * Function - handles all database operations
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "quizManager";
    private static final String TABLE_QUIZ = "quiz";

    //Column Names
    private static final String KEY_ID = "quiz_id";
    private static final String KEY_LEVEL = "quiz_level";
    private static final String KEY_QUESTION = "quiz_question";
    private static final String KEY_NUM = "quiz_answer_num";
    private static final String KEY_ANSWER = "quiz_answer";
    private static final String KEY_OPTIONS = "quiz_answer_options";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create Table Quiz
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_QUIZ + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_LEVEL + " TEXT,"
                + KEY_QUESTION + " TEXT,"
                + KEY_NUM + " INTEGER,"
                + KEY_ANSWER + " TEXT,"
                + KEY_OPTIONS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    /**
     * Upgrade Table Quiz
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);

        // Create tables again
        onCreate(db);
    }

    /**
     * This methods adds record to the Table Quiz
     */
    void addRecord(Quiz quiz) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL, quiz.getLevel()); // Quiz Level
        values.put(KEY_QUESTION, quiz.getQuestion()); // Quiz Question
        values.put(KEY_NUM, quiz.getNum()); // Quiz Num answers
        values.put(KEY_ANSWER, quiz.getAnswer()); // Quiz Answer
        values.put(KEY_OPTIONS, quiz.getOptions()); // Quiz Answer Options

        // Inserting Row
        db.insert(TABLE_QUIZ, null, values);
        db.close(); // Closing database connection
    }

    /**
     * This method retrieves a subset of records
     */
    public List<Quiz> getRecords(String level) {
        List<Quiz> quizList = new ArrayList<Quiz>();
        String selectQuery = "SELECT * FROM " + TABLE_QUIZ + " WHERE " + KEY_LEVEL + " = '" + level + "' ORDER BY RANDOM() LIMIT 7";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Quiz quiz = new Quiz();
                quiz.setID(Integer.parseInt(cursor.getString(0)));
                quiz.setLevel(cursor.getString(1));
                quiz.setQuestion(cursor.getString(2));
                quiz.setNum(Integer.parseInt(cursor.getString(3)));
                quiz.setAnswer(cursor.getString(4));
                quiz.setOptions(cursor.getString(5));
                // Adding contact to list
                quizList.add(quiz);
            } while (cursor.moveToNext());
        }

        return quizList;
    }

    /**
     * This method deletes all rows in the table
     */
    public void deleteRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUIZ, null, null);
        db.close();
    }


}
