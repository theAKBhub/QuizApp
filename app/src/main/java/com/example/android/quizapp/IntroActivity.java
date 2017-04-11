package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class - IntroActivity
 * Functions of this class are as follows:
 *     (1) Processes Intent message and displays relevant messages.
 *     (2) Creates and populates Quiz Table (SQLliteDatabase) with quiz questions
 *     (3) Upon clicking START QUIZ button, invokes third Activity (activity_quiz.xml).
 */

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    // All UI components
    private TextView mTextViewHello;
    private TextView mTextViewLevelChosen;
    private TextView mTextViewRules;
    private Button mButtonStartQuiz;

    // Various identifiers
    private Typeface mCustomFont;
    private String mIntentMessage;
    private String [] mIntentMsgArray = new String[3];

    /**
     * onCreate method of MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Retrieve Intent message
        Bundle bundle = getIntent().getExtras();
        mIntentMessage = bundle.getString("message");
        mIntentMsgArray = mIntentMessage.split("\\|");

        // Initialize UI components
        mTextViewHello = (TextView) findViewById(R.id.textView_hello);
        mTextViewLevelChosen = (TextView) findViewById(R.id.textView_level_chosen);
        mTextViewRules = (TextView) findViewById(R.id.textView_rules);
        mButtonStartQuiz = (Button) findViewById(R.id.button_start_quiz);

        // Set custom typeface
        mCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        setCustomTypeface();

        mButtonStartQuiz.setOnClickListener(this);

        setQuizRecords();
        displayIntro();
    }

    /**
     * Invokes methods for individual call to action buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        final Context context = this;

        switch (view.getId()) {
            case R.id.button_start_quiz:
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("message", mIntentMessage);
                startActivity(intent);
                break;
        }
    }

    /**
     * This method sets custom font for all views
     */
    public void setCustomTypeface() {
        mTextViewHello.setTypeface(mCustomFont);
        mTextViewLevelChosen.setTypeface(mCustomFont);
        mTextViewRules.setTypeface(mCustomFont);
    }

    /**
     * This method displays the introduction messages
     */
    public void displayIntro() {
        mTextViewHello.setText(getString(R.string.info_hello, mIntentMsgArray[0]));
        mTextViewLevelChosen.setText(getString(R.string.info_level_chosen, mIntentMsgArray[2]));
    }

    /**
     * This method creates and populates table
     */
    public void setQuizRecords() {
        String [] record = new String[5];
        String [] records = getResources().getStringArray(R.array.records_array);
        int rowCount = 0;

        DatabaseHandler db = new DatabaseHandler(this);
        rowCount = db.getRowsCount();

        if (rowCount == 0) {
            for (int i = 0; i < records.length; i++) {
                record = records[i].split(", ");

                db.addRecord(new Quiz(record[0],
                        record[1],
                        Integer.parseInt(record[2]),
                        record[3],
                        record[4]
                ));
            }
        }

    }
}
