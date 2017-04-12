package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.android.quizapp.R.id.button_email_score;

/**
 * Class - ResultActivity
 * Functions of this class are as follows:
 *     (1) Displays score of the quiz
 *     (2) Displays the quiz questions, right answers and user input answers
 *     (3) Provides option to EMAIL SCORE
 *     (4) Provides option to start a new quiz
 */
public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    final Context context = this;

    private static final int MAX_QNUM = 7;
    private static final int PASS_SCORE = 5;

    private String [] mArrayQuestion = new String[7];
    private String [] mArrayAnswer = new String[7];
    private String [] mArrayAnswerGiven = new String[7];
    private String [] mIntentMessageArray = new String[4];
    private int mFinalScore = 0;
    private Typeface mCustomFont;
    private Typeface mCustomFontBold;

    // All UI components
    private TextView mTextViewResultMsg;
    private TextView mTextViewScore;
    private TextView mTextViewQs1;
    private TextView mTextViewQs2;
    private TextView mTextViewQs3;
    private TextView mTextViewQs4;
    private TextView mTextViewQs5;
    private TextView mTextViewQs6;
    private TextView mTextViewQs7;
    private TextView mTextViewAns1;
    private TextView mTextViewAns2;
    private TextView mTextViewAns3;
    private TextView mTextViewAns4;
    private TextView mTextViewAns5;
    private TextView mTextViewAns6;
    private TextView mTextViewAns7;
    private TextView mTextViewAnsGiven1;
    private TextView mTextViewAnsGiven2;
    private TextView mTextViewAnsGiven3;
    private TextView mTextViewAnsGiven4;
    private TextView mTextViewAnsGiven5;
    private TextView mTextViewAnsGiven6;
    private TextView mTextViewAnsGiven7;
    private Button mButtonRestart;
    private Button mButtonEmailScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String intentMessage = "";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        mCustomFontBold = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");

        Bundle bundle = getIntent().getExtras();
        intentMessage = bundle.getString("message");
        mIntentMessageArray = intentMessage.split("\\$"); //Log.v("RA", intentMessage); Log.v("RA", ""+mIntentMessageArray.length);
        getResults();

        // Initialize View Elements
        mTextViewResultMsg = (TextView) findViewById(R.id.textView_msg_result);
        mTextViewScore = (TextView) findViewById(R.id.textView_score);
        mTextViewQs1 = (TextView) findViewById(R.id.textView_qs1);
        mTextViewQs2 = (TextView) findViewById(R.id.textView_qs2);
        mTextViewQs3 = (TextView) findViewById(R.id.textView_qs3);
        mTextViewQs4 = (TextView) findViewById(R.id.textView_qs4);
        mTextViewQs5 = (TextView) findViewById(R.id.textView_qs5);
        mTextViewQs6 = (TextView) findViewById(R.id.textView_qs6);
        mTextViewQs7 = (TextView) findViewById(R.id.textView_qs7);
        mTextViewAns1 = (TextView) findViewById(R.id.textView_ans1);
        mTextViewAns2 = (TextView) findViewById(R.id.textView_ans2);
        mTextViewAns3 = (TextView) findViewById(R.id.textView_ans3);
        mTextViewAns4 = (TextView) findViewById(R.id.textView_ans4);
        mTextViewAns5 = (TextView) findViewById(R.id.textView_ans5);
        mTextViewAns6 = (TextView) findViewById(R.id.textView_ans6);
        mTextViewAns7 = (TextView) findViewById(R.id.textView_ans7);
        mTextViewAnsGiven1 = (TextView) findViewById(R.id.textView_ans_given1);
        mTextViewAnsGiven2 = (TextView) findViewById(R.id.textView_ans_given2);
        mTextViewAnsGiven3 = (TextView) findViewById(R.id.textView_ans_given3);
        mTextViewAnsGiven4 = (TextView) findViewById(R.id.textView_ans_given4);
        mTextViewAnsGiven5 = (TextView) findViewById(R.id.textView_ans_given5);
        mTextViewAnsGiven6 = (TextView) findViewById(R.id.textView_ans_given6);
        mTextViewAnsGiven7 = (TextView) findViewById(R.id.textView_ans_given7);
        mButtonRestart = (Button) findViewById(R.id.button_restart);
        mButtonEmailScore = (Button) findViewById(button_email_score);

        mButtonRestart.setOnClickListener(this);
        mButtonEmailScore.setOnClickListener(this);

        setCustomTypeface();
        displayScore();
        displayQuestionAnswer();
    }

    /**
     * Invokes methods for individual call to action buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case button_email_score:
                sendEmail();
                break;

            case R.id.button_restart:
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * This method sets custom font for all views
     */
    public void setCustomTypeface() {
        mTextViewScore.setTypeface(mCustomFont);
        mTextViewResultMsg.setTypeface(mCustomFont);
        mTextViewQs1.setTypeface(mCustomFontBold);
        mTextViewQs2.setTypeface(mCustomFontBold);
        mTextViewQs3.setTypeface(mCustomFontBold);
        mTextViewQs4.setTypeface(mCustomFontBold);
        mTextViewQs5.setTypeface(mCustomFontBold);
        mTextViewQs6.setTypeface(mCustomFontBold);
        mTextViewQs7.setTypeface(mCustomFontBold);
        mTextViewAns1.setTypeface(mCustomFont);
        mTextViewAns2.setTypeface(mCustomFont);
        mTextViewAns3.setTypeface(mCustomFont);
        mTextViewAns4.setTypeface(mCustomFont);
        mTextViewAns5.setTypeface(mCustomFont);
        mTextViewAns6.setTypeface(mCustomFont);
        mTextViewAns7.setTypeface(mCustomFont);
        mTextViewAnsGiven1.setTypeface(mCustomFont);
        mTextViewAnsGiven2.setTypeface(mCustomFont);
        mTextViewAnsGiven3.setTypeface(mCustomFont);
        mTextViewAnsGiven4.setTypeface(mCustomFont);
        mTextViewAnsGiven5.setTypeface(mCustomFont);
        mTextViewAnsGiven6.setTypeface(mCustomFont);
        mTextViewAnsGiven7.setTypeface(mCustomFont);
    }

    /**
     * This method gets the questions, answers and score passed through Intent
     */
    public void getResults() {

        // Populate Question Array
        mArrayQuestion = mIntentMessageArray[0].split("~");

        // Populate Answers Array
        mArrayAnswer = mIntentMessageArray[1].split("~");

        // Populate Answers Given Array
        mArrayAnswerGiven = mIntentMessageArray[2].split("~");

        // Populate Score
        mFinalScore = Integer.parseInt(mIntentMessageArray[3]);
    }

    /**
     * This method displays quiz score
     */
    public void displayScore() {
        String msgResult = "";

        mTextViewScore.setText(getString(R.string.info_scores, mFinalScore));
        mTextViewScore.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        if (mFinalScore >= PASS_SCORE) {
            mTextViewResultMsg.setText(getString(R.string.info_pass));
            mTextViewResultMsg.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            mTextViewResultMsg.setText(getString(R.string.info_fail));
            mTextViewResultMsg.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }
    }

    /**
     * This method displays all questions along with right answers and answers given
     */
    public void displayQuestionAnswer() {

        // Display the questions
        mTextViewQs1.setText(mArrayQuestion[0]);
        mTextViewQs2.setText(mArrayQuestion[1]);
        mTextViewQs3.setText(mArrayQuestion[2]);
        mTextViewQs4.setText(mArrayQuestion[3]);
        mTextViewQs5.setText(mArrayQuestion[4]);
        mTextViewQs6.setText(mArrayQuestion[5]);
        mTextViewQs7.setText(mArrayQuestion[6]);

        // Display the right answers
        mTextViewAns1.setText(getString(R.string.info_ans, mArrayAnswer[0].replace("|", ", ")));
        mTextViewAns2.setText(getString(R.string.info_ans, mArrayAnswer[1].replace("|", ", ")));
        mTextViewAns3.setText(getString(R.string.info_ans, mArrayAnswer[2].replace("|", ", ")));
        mTextViewAns4.setText(getString(R.string.info_ans, mArrayAnswer[3].replace("|", ", ")));
        mTextViewAns5.setText(getString(R.string.info_ans, mArrayAnswer[4].replace("|", ", ")));
        mTextViewAns6.setText(getString(R.string.info_ans, mArrayAnswer[5].replace("|", ", ")));
        mTextViewAns7.setText(getString(R.string.info_ans, mArrayAnswer[6].replace("|", ", ")));

        // Display the given answers and set color depending on right or wrong answer
        mTextViewAnsGiven1.setText(getString(R.string.info_given_ans, mArrayAnswerGiven[0].replace("|", ", ")));
        if (mArrayAnswerGiven[0].equals(mArrayAnswer[0])) {
            mTextViewAnsGiven1.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            mTextViewAnsGiven1.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        mTextViewAnsGiven2.setText(getString(R.string.info_given_ans, mArrayAnswerGiven[1].replace("|", ", ")));
        if (mArrayAnswerGiven[1].equals(mArrayAnswer[1])) {
            mTextViewAnsGiven2.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            mTextViewAnsGiven2.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        mTextViewAnsGiven3.setText(getString(R.string.info_given_ans, mArrayAnswerGiven[2].replace("|", ", ")));
        if (mArrayAnswerGiven[2].equals(mArrayAnswer[2])) {
            mTextViewAnsGiven3.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            mTextViewAnsGiven3.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        mTextViewAnsGiven4.setText(getString(R.string.info_given_ans, mArrayAnswerGiven[3].replace("|", ", ")));
        if (mArrayAnswerGiven[3].equals(mArrayAnswer[3])) {
            mTextViewAnsGiven4.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            mTextViewAnsGiven4.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        mTextViewAnsGiven5.setText(getString(R.string.info_given_ans, mArrayAnswerGiven[4].replace("|", ", ")));
        if (mArrayAnswerGiven[4].equals(mArrayAnswer[4])) {
            mTextViewAnsGiven5.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            mTextViewAnsGiven5.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        mTextViewAnsGiven6.setText(getString(R.string.info_given_ans, mArrayAnswerGiven[5].replace("|", ", ")));
        if (mArrayAnswerGiven[5].equals(mArrayAnswer[5])) {
            mTextViewAnsGiven6.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            mTextViewAnsGiven6.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        mTextViewAnsGiven7.setText(getString(R.string.info_given_ans, mArrayAnswerGiven[6].replace("|", ", ")));
        if (mArrayAnswerGiven[6].equals(mArrayAnswer[6])) {
            mTextViewAnsGiven7.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            mTextViewAnsGiven7.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }
    }

    /**
     * This method emails score to the specified email id
     */
    public void sendEmail() {
        String msg = "";

        msg = getString(R.string.label_email, mIntentMessageArray[4]);
        msg += getString(R.string.info_scores, mFinalScore) + "\n\n";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{mIntentMessageArray[5]});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
