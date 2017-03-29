package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MIN_QNUM = 1;
    public static final int MAX_QNUM = 5;

    static final String STATE_CURRID = "currId_svd";
    static final String STATE_QID = "arrayId_svd";
    static final String STATE_QUESTIONS = "arrayQuestion_svd";
    static final String STATE_LEVEL = "arrayLevel_svd";
    static final String STATE_NUM = "arrayNum_svd";
    static final String STATE_ANSWER = "arrayAnswer_svd";
    static final String STATE_OPTIONS = "arrayOptions_svd";
    static final String STATE_ANSWER_GIVEN = "arrayAnswerGiven_svd";
    static final String STATE_SCORES = "scores_svd";
    static final String STATE_ENDOFQUIZ = "isEndOfQuiz_svd";
    static final String STATE_WRONG_ANSWER = "wrongAnswers_svd";

    Typeface custom_font;
    String intent_msg;
    String [] intent_msg_array = new String[3];

    int currId = 1;
    int scores = 0;
    boolean isEndOfQuiz = false;

    int [] arrayId = new int[5];
    int [] arrayNum = new int[5];
    String [] arrayQuestion = new String[5];
    String [] arrayLevel = new String[5];
    String [] arrayAnswer = new String[5];
    String [] arrayOptions = new String[5];
    String [] arrayAnswerGiven = new String[5];
    String wrongAnswers;

    TextView textView_qnum;
    TextView textView_question;
    TextView textView_result;

    Button button_prev;
    Button button_next;
    Button button_submit;
    Button button_restart;
    Button button_email_score;

    RadioGroup radioGroup;
    RadioButton radioButton_option_1;
    RadioButton radioButton_option_2;
    RadioButton radioButton_option_3;
    RadioButton radioButton_option_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");

        Bundle bundle = getIntent().getExtras();
        intent_msg = bundle.getString("message");
        intent_msg_array = intent_msg.split("\\|");

        textView_qnum = (TextView) findViewById(R.id.textView_qnum);
        textView_question = (TextView) findViewById(R.id.textView_question);
        textView_result = (TextView) findViewById(R.id.textView_result);

        button_prev = (Button) findViewById(R.id.button_prev);
        button_next = (Button) findViewById(R.id.button_next);
        button_submit = (Button) findViewById(R.id.button_submit);
        button_restart = (Button) findViewById(R.id.button_restart);
        button_email_score = (Button) findViewById(R.id.button_email_score);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioButton_option_1 = (RadioButton) findViewById(R.id.radioButton_option_1);
        radioButton_option_2 = (RadioButton) findViewById(R.id.radioButton_option_2);
        radioButton_option_3 = (RadioButton) findViewById(R.id.radioButton_option_3);
        radioButton_option_4 = (RadioButton) findViewById(R.id.radioButton_option_4);

        button_prev.setOnClickListener(this);
        button_next.setOnClickListener(this);
        button_submit.setOnClickListener(this);
        button_restart.setOnClickListener(this);
        button_email_score.setOnClickListener(this);

        if (savedInstanceState == null) {
            getQuizRecords();
            showQuizDetails();
        }
        setCustomTypeface();
    }

    /**
     * Save Instance
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRID, currId);
        outState.putIntArray(STATE_QID, arrayId);
        outState.putStringArray(STATE_QUESTIONS, arrayQuestion);
        outState.putStringArray(STATE_LEVEL, arrayLevel);
        outState.putIntArray(STATE_NUM, arrayNum);
        outState.putStringArray(STATE_ANSWER, arrayAnswer);
        outState.putStringArray(STATE_OPTIONS, arrayOptions);
        outState.putStringArray(STATE_ANSWER_GIVEN, arrayAnswerGiven);
        outState.putInt(STATE_SCORES, scores);
        outState.putBoolean(STATE_ENDOFQUIZ, isEndOfQuiz);
        outState.putString(STATE_WRONG_ANSWER, wrongAnswers);
        super.onSaveInstanceState(outState);
    }


    /**
     * Restore Saved Instance
     * @param savedInstanceState
     */

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            currId = savedInstanceState.getInt(STATE_CURRID);
            arrayId = savedInstanceState.getIntArray(STATE_QID);
            arrayQuestion = savedInstanceState.getStringArray(STATE_QUESTIONS);
            arrayLevel = savedInstanceState.getStringArray(STATE_LEVEL);
            arrayNum = savedInstanceState.getIntArray(STATE_NUM);
            arrayOptions = savedInstanceState.getStringArray(STATE_OPTIONS);
            arrayAnswerGiven = savedInstanceState.getStringArray(STATE_ANSWER_GIVEN);
            scores = savedInstanceState.getInt(STATE_SCORES);
            isEndOfQuiz = savedInstanceState.getBoolean(STATE_ENDOFQUIZ);
            wrongAnswers = savedInstanceState.getString(STATE_WRONG_ANSWER);

            showQuizDetails();
            if (isEndOfQuiz) {
                processScores();
            }
        }

    }

    @Override
    public void onClick(View view) {
        final Context context = this;

        switch (view.getId()) {
            case R.id.button_prev:
                if (currId > MIN_QNUM) {
                    saveAnswers();
                    resetViews();
                    currId--;
                    showQuizDetails();
                }
                break;

            case R.id.button_next:
                if (currId < MAX_QNUM) {
                    saveAnswers();
                    resetViews();
                    currId++;
                    showQuizDetails();
                }
                break;

            case R.id.button_email_score:
                sendEmail();

            case R.id.button_submit:
                saveAnswers();
                isEndOfQuiz = true;
                processScores();
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
        textView_qnum.setTypeface(custom_font);
        textView_question.setTypeface(custom_font);
    }


    /**
     * This method displays quiz details
     */
    public void showQuizDetails() {

        int index = currId - 1;
        textView_qnum.setText(getString(R.string.info_qnum, currId));
        textView_question.setText(arrayQuestion[index]);

        String [] options = arrayOptions[index].split("\\|");
        int len = options.length;

        radioGroup.setVisibility(View.VISIBLE);
        radioButton_option_1.setVisibility(View.VISIBLE);
        radioButton_option_1.setText(options[0]);

        radioButton_option_2.setVisibility(View.VISIBLE);
        radioButton_option_2.setText(options[1]);

        if (len == 3) {
            radioButton_option_3.setVisibility(View.VISIBLE);
            radioButton_option_3.setText(options[2]);
        }
        else if (len == 4) {
            radioButton_option_3.setVisibility(View.VISIBLE);
            radioButton_option_3.setText(options[2]);
            radioButton_option_4.setVisibility(View.VISIBLE);
            radioButton_option_4.setText(options[3]);
        }

        if (options[0].equals(arrayAnswerGiven[index])) {
            radioButton_option_1.setChecked(true);
        }
        if (options[1].equals(arrayAnswerGiven[index])) {
            radioButton_option_2.setChecked(true);
        }
        if (len == 3) {
            if (options[2].equals(arrayAnswerGiven[index])) {
                radioButton_option_3.setChecked(true);
            }
        } else if (len == 4) {
            if (options[3].equals(arrayAnswerGiven[index])) {
                radioButton_option_4.setChecked(true);
            }
        }

        showButtons();
    }


    /**
     * This method get quiz questions from table
     */
    public void getQuizRecords() {
        DatabaseHandler db = new DatabaseHandler(this);

        // Retrieving records
        List<Quiz> quiz = db.getRecords(intent_msg_array[2]);

        int i = 0;
        for (Quiz qz : quiz) {
            arrayId[i] = qz.getID();
            arrayLevel[i] = qz.getLevel();
            arrayQuestion[i] = qz.getQuestion();
            arrayNum[i] = qz.getNum();
            arrayAnswer[i] = qz.getAnswer();
            arrayOptions[i] = qz.getOptions();
            i++;
        }
    }

    /**
     * This method handles display of buttons
     */
    public void showButtons() {
        if (currId == MIN_QNUM) {
            button_prev.setBackgroundColor(getColor(R.color.colorGreyBtnDisabled));
            button_prev.setEnabled(false);
            button_next.setBackgroundColor(getColor(R.color.colorAccent));
            button_next.setEnabled(true);
            button_submit.setVisibility(View.GONE);
        }
        else if (currId == MAX_QNUM) {
            button_next.setBackgroundColor(getColor(R.color.colorGreyBtnDisabled));
            button_next.setEnabled(false);
            button_prev.setBackgroundColor(getColor(R.color.colorGreyDark));
            button_prev.setEnabled(true);
            button_submit.setVisibility(View.VISIBLE);
        }
        else if (currId > MIN_QNUM && currId < MAX_QNUM) {
            button_prev.setBackgroundColor(getColor(R.color.colorGreyDark));
            button_prev.setEnabled(true);
            button_next.setBackgroundColor(getColor(R.color.colorAccent));
            button_next.setEnabled(true);
            button_submit.setVisibility(View.GONE);
        }
    }

    /**
     * This method saves answers given
     */
    public void saveAnswers() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioSelected = (RadioButton) findViewById(selectedId);
        if(radioSelected != null) {
            arrayAnswerGiven[currId - 1] = radioSelected.getText().toString();
        }
    }

    /**
     * This methods resets quiz answers
     */
    public void resetViews() {
        radioGroup.setVisibility(View.GONE);
        radioGroup.clearCheck();
        radioButton_option_1.setVisibility(View.GONE);
        radioButton_option_2.setVisibility(View.GONE);
        radioButton_option_3.setVisibility(View.GONE);
        radioButton_option_4.setVisibility(View.GONE);
    }

    /**
     * This method processes scores
     */
    public void processScores() {

        String final_result = "";
        wrongAnswers = "";

        for (int i = 0; i < MAX_QNUM; i++) {
            if (arrayAnswerGiven[i] != null) {
                if (arrayAnswerGiven[i].equals(arrayAnswer[i])) {
                    scores++;
                }
                else {
                    wrongAnswers += getString(R.string.info_qs, arrayQuestion[i]) + "\n";
                    wrongAnswers += getString(R.string.info_your_ans, arrayAnswerGiven[i]) + "\n";
                    wrongAnswers += getString(R.string.info_ans, arrayAnswer[i]) + "\n\n";
                }
            }
        }
        textView_qnum.setText(getString(R.string.info_scores, scores));
        if (scores >= 3) {
            textView_question.setText(getString(R.string.info_pass));
            textView_question.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        else {
            textView_question.setText(getString(R.string.info_fail));
            textView_question.setTextColor(getColor(R.color.colorRed));
        }

        radioGroup.setVisibility(View.GONE);
        radioButton_option_1.setVisibility(View.GONE);
        radioButton_option_2.setVisibility(View.GONE);
        radioButton_option_3.setVisibility(View.GONE);
        radioButton_option_4.setVisibility(View.GONE);

        button_prev.setVisibility(View.GONE);
        button_next.setVisibility(View.GONE);
        button_submit.setVisibility(View.GONE);

        button_restart.setVisibility(View.VISIBLE);
        button_email_score.setVisibility(View.VISIBLE);

        textView_result.setVisibility(View.VISIBLE);
        final_result = getString(R.string.info_wrong_ans);
        final_result += wrongAnswers;
        textView_result.setText(final_result);
    }

    /**
     * This method emails score to the specified email id
     */
    public void sendEmail() {
        String msg = "";

        msg = getString(R.string.info_scores, scores) + "\n\n";
        msg += getString(R.string.info_wrong_ans) + "\n\n";
        msg += wrongAnswers;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{intent_msg_array[1]});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
