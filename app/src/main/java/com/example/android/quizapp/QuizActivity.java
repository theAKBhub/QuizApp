package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import static com.example.android.quizapp.R.id.button_prev;

/**
 * Class - QuizActivity
 * Functions of this class are as follows:
 *     (1) Displays 7 questions, one by one, based on the level chosen by user
 *     (2) User can navigate from one question to another using PREV and NEXT buttons
 *     (3) PREV button is disabled on 1st question, and NEXT button is disabled on 7th question.
 *     (4) SUBMIT button appears on the last question, which when clicked, score is calculated
 *              and ResultActivity is invoked.
 */
public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    final Context context = this;

    // Constant variables
    private static final int MIN_QNUM = 1;
    private static final int MAX_QNUM = 7;
    private static final int MAX_PROGRESS = 70;
    private static final int PROGRESS_INCREMENT = 10;

    // SavedInstance variables
    private static final String STATE_CURRID = "currId_svd";
    private static final String STATE_QID = "arrayId_svd";
    private static final String STATE_QUESTIONS = "arrayQuestion_svd";
    private static final String STATE_LEVEL = "arrayLevel_svd";
    private static final String STATE_NUM = "arrayNum_svd";
    private static final String STATE_ANSWER = "arrayAnswer_svd";
    private static final String STATE_OPTIONS = "arrayOptions_svd";
    private static final String STATE_ANSWER_GIVEN = "arrayAnswerGiven_svd";
    private static final String STATE_SCORES = "scores_svd";
    private static final String STATE_ENDOFQUIZ = "isEndOfQuiz_svd";
    private static final String STATE_WRONG_ANSWER = "wrongAnswers_svd";
    private static final String STATE_UICHECKER = "uiChecker_svd";

    // Various identifiers
    private Typeface mCustomFont;
    private String [] mIntentMessage_array = new String[3];
    private int mCurrentQID = 1;
    private int mScores = 0;
    private boolean isEndOfQuiz = false;
    private int mUIChecker = 0; // 0 for EditText, 1 for CheckBox, 2 for 2 radios, 3 for 4 radios
    private int [] mArrayId = new int[7];
    private int [] mArrayNum = new int[7];
    private String [] mArrayQuestion = new String[7];
    private String [] mArrayLevel = new String[7];
    private String [] mArrayAnswer = new String[7];
    private String [] mArrayOptions = new String[7];
    private String [] mArrayAnswerGiven = new String[7];
    private String mWrongAnswers;
    private int mProgressBarStatus = 0;

    // All UI components
    private TextView mTextViewQnum;
    private TextView mTextViewQuestion;
    private Button mButtonPrev;
    private Button mButtonNext;
    private Button mButtonSubmit;
    private RadioGroup mRadioGroupA;
    private RadioButton mRadioButton1A;
    private RadioButton mRadioButton2A;
    private RadioButton mRadioButton3A;
    private RadioButton mRadioButton4A;
    private RadioGroup mRadioGroupB;
    private RadioButton mRadioButton1B;
    private RadioButton mRadioButton2B;
    private LinearLayout mCheckBoxHolder;
    private CheckBox mCheckBoxOption1;
    private CheckBox mCheckBoxOption2;
    private CheckBox mCheckBoxOption3;
    private CheckBox mCheckBoxOption4;
    private EditText mEditTextAnswer;
    private ProgressBar mProgressQuiz;

    /**
     * onCreate method of MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String intentMessage = "";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");

        Bundle bundle = getIntent().getExtras();
        intentMessage = bundle.getString("message");
        mIntentMessage_array = intentMessage.split("\\|");

        // Initialize UI components
        mTextViewQnum = (TextView) findViewById(R.id.textView_qnum);
        mTextViewQuestion = (TextView) findViewById(R.id.textView_question);
        mButtonPrev = (Button) findViewById(button_prev);
        mButtonNext = (Button) findViewById(R.id.button_next);
        mButtonSubmit = (Button) findViewById(R.id.button_submit);
        mRadioGroupA = (RadioGroup) findViewById(R.id.radio_group_A);
        mRadioButton1A = (RadioButton) findViewById(R.id.radioButton_option_1A);
        mRadioButton2A = (RadioButton) findViewById(R.id.radioButton_option_2A);
        mRadioButton3A = (RadioButton) findViewById(R.id.radioButton_option_3A);
        mRadioButton4A = (RadioButton) findViewById(R.id.radioButton_option_4A);
        mRadioGroupB = (RadioGroup) findViewById(R.id.radio_group_B);
        mRadioButton1B = (RadioButton) findViewById(R.id.radioButton_option_1B);
        mRadioButton2B = (RadioButton) findViewById(R.id.radioButton_option_2B);
        mCheckBoxHolder = (LinearLayout) findViewById(R.id.checkbox_holder);
        mCheckBoxOption1 = (CheckBox) findViewById(R.id.checkBox_option_1);
        mCheckBoxOption2 = (CheckBox) findViewById(R.id.checkBox_option_2);
        mCheckBoxOption3 = (CheckBox) findViewById(R.id.checkBox_option_3);
        mCheckBoxOption4 = (CheckBox) findViewById(R.id.checkBox_option_4);
        mEditTextAnswer = (EditText) findViewById(R.id.editText_answer);
        mProgressQuiz = (ProgressBar) findViewById(R.id.progress_bar);

        mProgressQuiz.setProgress(PROGRESS_INCREMENT);
        mProgressQuiz.setMax(MAX_PROGRESS);

        mButtonPrev.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);

        if (savedInstanceState == null) {
            getQuizRecords();
            showQuizDetails();
        }
        setCustomTypeface();
    }

    /**
     * This method saves variable instances
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRID, mCurrentQID);
        outState.putIntArray(STATE_QID, mArrayId);
        outState.putStringArray(STATE_QUESTIONS, mArrayQuestion);
        outState.putStringArray(STATE_LEVEL, mArrayLevel);
        outState.putIntArray(STATE_NUM, mArrayNum);
        outState.putStringArray(STATE_ANSWER, mArrayAnswer);
        outState.putStringArray(STATE_OPTIONS, mArrayOptions);
        outState.putStringArray(STATE_ANSWER_GIVEN, mArrayAnswerGiven);
        outState.putInt(STATE_SCORES, mScores);
        outState.putBoolean(STATE_ENDOFQUIZ, isEndOfQuiz);
        outState.putString(STATE_WRONG_ANSWER, mWrongAnswers);
        outState.putInt(STATE_UICHECKER, mUIChecker);
        super.onSaveInstanceState(outState);
    }

    /**
     * This method restores variable instances
     * @param savedInstanceState
     */

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentQID = savedInstanceState.getInt(STATE_CURRID);
            mArrayId = savedInstanceState.getIntArray(STATE_QID);
            mArrayQuestion = savedInstanceState.getStringArray(STATE_QUESTIONS);
            mArrayLevel = savedInstanceState.getStringArray(STATE_LEVEL);
            mArrayNum = savedInstanceState.getIntArray(STATE_NUM);
            mArrayAnswer = savedInstanceState.getStringArray(STATE_ANSWER);
            mArrayOptions = savedInstanceState.getStringArray(STATE_OPTIONS);
            mArrayAnswerGiven = savedInstanceState.getStringArray(STATE_ANSWER_GIVEN);
            mScores = savedInstanceState.getInt(STATE_SCORES);
            isEndOfQuiz = savedInstanceState.getBoolean(STATE_ENDOFQUIZ);
            mWrongAnswers = savedInstanceState.getString(STATE_WRONG_ANSWER);
            mUIChecker = savedInstanceState.getInt(STATE_UICHECKER);

            showQuizDetails();
            if (isEndOfQuiz) {
                hideQuestionUI();
            }
        }
    }

    /**
     * Invokes methods for individual call to action buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case button_prev:
                if (mCurrentQID > MIN_QNUM) {
                    saveAnswers();
                    resetViews();
                    mCurrentQID--;
                    displayProgress();
                    showQuizDetails();
                }
                break;

            case R.id.button_next:
                if (mCurrentQID < MAX_QNUM) {
                    saveAnswers();
                    resetViews();
                    mCurrentQID++;
                    displayProgress();
                    showQuizDetails();
                }
                break;

            case R.id.button_submit:
                saveAnswers();
                isEndOfQuiz = true;
                processScores();
                hideQuestionUI();
                displayToast();
                showResult();
                break;
        }
    }

    /**
     * This method sets custom font for all views
     */
    public void setCustomTypeface() {
        mTextViewQnum.setTypeface(mCustomFont);
        mTextViewQuestion.setTypeface(mCustomFont);
    }

    /**
     * This method displays quiz details
     */
    public void showQuizDetails() {
        int index = 0;
        int len = 0;
        String [] options;

        index = mCurrentQID - 1;
        displayQuizQs(index);

        if (mArrayOptions[index].contains("|")) { // Show RadioGroup or Checkboxes
            options = mArrayOptions[index].split("\\|");
            len = options.length;

            if (mArrayNum[index] == 1) { // Number of answers - 1
                showRadioGroup(len, index, options);
            } else if (mArrayNum[index] == 2) { // Number of answers - 2
                showCheckBoxes(index, options);
            }
        } else { //Show EditText
            showEditText(index);
        }
        showButtons();
    }

    /**
     * This method shows radio buttons for quiz answer options if number of answers = 1
     * @param rbNum, qsId, ansOptions
     */
    public void showRadioGroup(int rbNum, int qsId, String [] ansOptions) {

        if (rbNum == 2) {
            mUIChecker = 2; // radio group with 2 buttons
            mRadioGroupB.setVisibility(View.VISIBLE);
            mRadioGroupA.setVisibility(View.GONE);
            mRadioButton1B.setText(ansOptions[0]);
            mRadioButton2B.setText(ansOptions[1]);

            if (ansOptions[0].equals(mArrayAnswerGiven[qsId])) {
                mRadioButton1B.setChecked(true);
            } else if (ansOptions[1].equals(mArrayAnswerGiven[qsId])) {
                mRadioButton2B.setChecked(true);
            }

        } else if (rbNum == 4) {
            mUIChecker = 3; // radio group with 4 buttons
            mRadioGroupA.setVisibility(View.VISIBLE);
            mRadioGroupB.setVisibility(View.GONE);
            mRadioButton1A.setText(ansOptions[0]);
            mRadioButton2A.setText(ansOptions[1]);
            mRadioButton3A.setText(ansOptions[2]);
            mRadioButton4A.setText(ansOptions[3]);

            if (ansOptions[0].equals(mArrayAnswerGiven[qsId])) {
                mRadioButton1A.setChecked(true);
            } else if (ansOptions[1].equals(mArrayAnswerGiven[qsId])) {
                mRadioButton2A.setChecked(true);
            } else if (ansOptions[2].equals(mArrayAnswerGiven[qsId])) {
                mRadioButton3A.setChecked(true);
            } else if (ansOptions[3].equals(mArrayAnswerGiven[qsId])) {
                mRadioButton4A.setChecked(true);
            }
        }
    }

    /**
     * This method shows checkboxes for quiz answer options if number of answers > 1
     * @param qsId, ansOptions
     */
    public void showCheckBoxes(int qsId, String [] ansOptions) {
        String [] answers = new String[4];

        mUIChecker = 1; // checkbox
        mCheckBoxHolder.setVisibility(View.VISIBLE);
        mCheckBoxOption1.setText(ansOptions[0]);
        mCheckBoxOption2.setText(ansOptions[1]);
        mCheckBoxOption3.setText(ansOptions[2]);
        mCheckBoxOption4.setText(ansOptions[3]);

        if(mArrayAnswerGiven.length > qsId && mArrayAnswerGiven[qsId] != null) {
            answers = mArrayAnswerGiven[qsId].split("\\|");
            List<String> list = Arrays.asList(answers);
            if (list.contains(ansOptions[0])) {
                mCheckBoxOption1.setChecked(true);
            }
            if (list.contains(ansOptions[1])) {
                mCheckBoxOption2.setChecked(true);
            }
            if (list.contains(ansOptions[2])) {
                mCheckBoxOption3.setChecked(true);
            }
            if (list.contains(ansOptions[3])) {
                mCheckBoxOption4.setChecked(true);
            }
        }
    }

    /**
     * This method shows EditText for quiz answer if number of answers = 1, and options = 1
     * @param qsId
     */
    public void showEditText(int qsId) {
        mUIChecker = 0; //this is for EditText
        mEditTextAnswer.setVisibility(View.VISIBLE);
        if(mArrayAnswerGiven.length > qsId && mArrayAnswerGiven[qsId] != null) {
            if (!mArrayAnswerGiven[qsId].equals("")) {
                mEditTextAnswer.setText(mArrayAnswerGiven[qsId]);
            }
        }
    }

    /**
     * This method displays quiz question and number
     * @param qsId
     * */
    public void displayQuizQs(int qsId) {
        mTextViewQnum.setText(getString(R.string.info_qnum, mCurrentQID));
        mTextViewQuestion.setText(mArrayQuestion[qsId]);
    }

    /**
     * This method get quiz questions from table
     */
    public void getQuizRecords() {
        DatabaseHandler db = new DatabaseHandler(this);

        // Retrieving records
        List<Quiz> quiz = db.getRecords(mIntentMessage_array[2]);

        int i = 0;
        for (Quiz qz : quiz) {
            mArrayId[i] = qz.getID();
            mArrayLevel[i] = qz.getLevel();
            mArrayQuestion[i] = qz.getQuestion();
            mArrayNum[i] = qz.getNum();
            mArrayAnswer[i] = qz.getAnswer();
            mArrayOptions[i] = qz.getOptions();
            i++;
        }
    }

    /**
     * This method handles display of buttons
     */
    public void showButtons() {
        if (mCurrentQID == MIN_QNUM) {
            mButtonPrev.setBackgroundResource(R.color.colorGreyBtnDisabled);
            mButtonPrev.setEnabled(false);
            mButtonNext.setBackgroundResource(R.color.colorAccent);
            mButtonNext.setEnabled(true);
            mButtonSubmit.setVisibility(View.GONE);
        }
        else if (mCurrentQID == MAX_QNUM) {
            mButtonNext.setBackgroundResource(R.color.colorGreyBtnDisabled);
            mButtonNext.setEnabled(false);
            mButtonPrev.setBackgroundResource(R.color.colorGreyDark);
            mButtonPrev.setEnabled(true);
            mButtonSubmit.setVisibility(View.VISIBLE);
        }
        else if (mCurrentQID > MIN_QNUM && mCurrentQID < MAX_QNUM) {
            mButtonPrev.setBackgroundResource(R.color.colorGreyDark);
            mButtonPrev.setEnabled(true);
            mButtonNext.setBackgroundResource(R.color.colorAccent);
            mButtonNext.setEnabled(true);
            mButtonSubmit.setVisibility(View.GONE);
        }
    }

    /**
     * This method saves answers given
     */
    public void saveAnswers() {
        if (mUIChecker > 1) { // radio buttons used for answer
            saveAnswersRadioOptions();
        } else if (mUIChecker == 1) { // check boxes used for answer
            saveAnswersCheckOptions();
        } else if (mUIChecker == 0) { // EditText used for answer
            saveAnswersEditText();
        }
    }

    /**
     * This method saves answers provides via radio button options
     */
    public void saveAnswersRadioOptions() {
        int selectedId = 0;
        if (mUIChecker == 2) {
            selectedId = mRadioGroupB.getCheckedRadioButtonId();
        } else if (mUIChecker == 3) {
            selectedId = mRadioGroupA.getCheckedRadioButtonId();
        }

        RadioButton radioSelected = (RadioButton) findViewById(selectedId);
        if (radioSelected != null) {
            mArrayAnswerGiven[mCurrentQID - 1] = radioSelected.getText().toString();
        }
    }

    /**
     * This method saves answers provides via check box options
     */
    public void saveAnswersCheckOptions() {
        String checkBoxAnswer = "";
        boolean isFirstCheck = false;

        if (mCheckBoxOption1.isChecked()) {
            checkBoxAnswer = mCheckBoxOption1.getText().toString();
        }
        if (mCheckBoxOption2.isChecked()) {
            checkBoxAnswer += "|" + mCheckBoxOption2.getText().toString();
        }
        if (mCheckBoxOption3.isChecked()) {
            checkBoxAnswer += "|" + mCheckBoxOption3.getText().toString();
        }
        if (mCheckBoxOption4.isChecked()) {
            checkBoxAnswer += "|" + mCheckBoxOption4.getText().toString();
        }
        if (checkBoxAnswer.indexOf("|") == 0) {
            checkBoxAnswer = checkBoxAnswer.substring(1);
        }
        mArrayAnswerGiven[mCurrentQID - 1] = checkBoxAnswer;
    }

    /**
     * This method saves answers provided via EditText
     */
    public void saveAnswersEditText() {
        mArrayAnswerGiven[mCurrentQID - 1] =  mEditTextAnswer.getText().toString();
    }

    /**
     * This methods resets quiz answers
     */
    public void resetViews() {
        mRadioGroupA.clearCheck();
        mRadioGroupB.clearCheck();
        mCheckBoxOption1.setChecked(false);
        mCheckBoxOption2.setChecked(false);
        mCheckBoxOption3.setChecked(false);
        mCheckBoxOption4.setChecked(false);
        mEditTextAnswer.setText("");
        mRadioGroupA.setVisibility(View.GONE);
        mRadioGroupB.setVisibility(View.GONE);
        mCheckBoxHolder.setVisibility(View.GONE);
        mEditTextAnswer.setVisibility(View.GONE);
    }

    /**
     * This method processes scores
     */
    public void processScores() {
        mWrongAnswers = "";
        for (int i = 0; i < MAX_QNUM; i++) {
            if (mArrayAnswerGiven[i] != null) {
                if (mArrayAnswerGiven[i].equalsIgnoreCase(mArrayAnswer[i])) {
                    mScores++;
                }
            }
        }
    }

    /**
     * This method hides all question related UI components
     */
    public void hideQuestionUI() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rlayout_buttons);
        relativeLayout.setVisibility(View.GONE);

        mTextViewQnum.setVisibility(View.GONE);
        mTextViewQuestion.setVisibility(View.GONE);
        mRadioGroupA.setVisibility(View.GONE);
        mRadioGroupB.setVisibility(View.GONE);
        mCheckBoxHolder.setVisibility(View.GONE);
        mEditTextAnswer.setVisibility(View.GONE);
        mButtonPrev.setVisibility(View.GONE);
        mButtonNext.setVisibility(View.GONE);
        mButtonSubmit.setVisibility(View.GONE);
    }

    /**
     * This method displays a toast with the score at the end of the quiz
     */
    public void displayToast() {
        String toastMessage = "";

        toastMessage = getString(R.string.info_scores, mScores);
        if (mScores < MAX_QNUM) {
            toastMessage += "\n" + getString(R.string.info_toast);
        }
        Toast toast = Toast.makeText(QuizActivity.this, toastMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    /**
     * This method invokes ResultActivity to display the quiz results
     */
    public void showResult() {
        String questions = "";
        String answers = "";
        String answersGiven = "";
        String score = "0";
        String intentMessage = "";

        //Prepare string for Intent message
        questions = TextUtils.join("~", mArrayQuestion);
        answers = TextUtils.join("~", mArrayAnswer);

        for (int i = 0; i < MAX_QNUM; i++) {
            if (i > 0) {
                answersGiven += "~";
            }
            if (mArrayAnswerGiven[i] != null) {
                if (mArrayAnswerGiven[i].equals("")) {
                    answersGiven += getString(R.string.info_not_answered);
                } else {
                    answersGiven += mArrayAnswerGiven[i];
                }
            } else {
                answersGiven += getString(R.string.info_not_answered);
            }

        }

        score = Integer.toString(mScores);
        intentMessage = questions + "$" + answers + "$" + answersGiven + "$" + score;
        intentMessage += "$" + mIntentMessage_array[0];
        intentMessage += "$" + mIntentMessage_array[1];

        // Invoke Activity
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("message", intentMessage);
        startActivity(intent);
    }

    /**
     * This method shows progress of progress bar depending on Quiz Id
     */
    public void displayProgress() {
        mProgressBarStatus = mCurrentQID * PROGRESS_INCREMENT;
        mProgressQuiz.setProgress(mProgressBarStatus);
    }
}
