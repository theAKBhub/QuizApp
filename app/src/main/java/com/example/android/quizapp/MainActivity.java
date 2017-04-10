package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class - MainActivity
 * This class takes input from first Activity (activity_main.xml) and validates
 * user name and user email for empty field and valid format. If both are
 * found valid then invokes second Activity (activity_intro.xml) with an Intent
 * message formed by concatenating user name, user email and quiz level.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // All UI components
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private Button mButtonContinue;
    private Spinner mSpinnerLevel;

    // Various identifiers
    private Typeface mCustomFont;
    private String mUserName;
    private String mUserEmail;

    /**
     * onCreate method of MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        mEditTextName = (EditText) findViewById(R.id.editText_name);
        mEditTextEmail = (EditText) findViewById(R.id.editText_email);
        mButtonContinue = (Button) findViewById(R.id.button_continue);
        mSpinnerLevel = (Spinner) findViewById(R.id.spinner_level);

        // Set custom typeface
        mCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        setCustomTypeface();

        mButtonContinue.setOnClickListener(this);

        mEditTextName.addTextChangedListener(new QuizTextWatcher(mEditTextName));
        mEditTextEmail.addTextChangedListener(new QuizTextWatcher(mEditTextEmail));

    }

    /**
     * Invokes methods for individual call to action buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_continue:
                continueQuiz();
                break;
        }
    }

    /**
     * Extends TextWatcher class for user name and user email views
     */
    private class QuizTextWatcher implements TextWatcher {
        private View view;

        private QuizTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.editText_name:
                    validateName();
                    break;
            }
        }
    }

    /**
     * This method sets custom font for all views
     */
    public void setCustomTypeface() {
        mEditTextName.setTypeface(mCustomFont);
        mEditTextEmail.setTypeface(mCustomFont);
        mButtonContinue.setTypeface(mCustomFont);
    }

    /**
     * This method invokes processes to continue quiz
     */
    public void continueQuiz() {
        final Context context = this;
        String intentMessage = "";
        boolean isValidName = false;
        boolean isValidEmail = false;

        isValidName = validateName();
        // Validate email only if name is found valid
        if (isValidName) {
            isValidEmail = validateEmail();

            // Form intent message if both name and email found valid
            if (isValidEmail) {
                intentMessage = mUserName + "|";
                intentMessage += mUserEmail + "|";
                intentMessage += mSpinnerLevel.getSelectedItem();
                Intent intent = new Intent(context, IntroActivity.class);
                intent.putExtra("message", intentMessage);
                startActivity(intent);
            }
        }
    }

    /**
     * This method Validates user input Name
     * @return boolean variable
     */
    public boolean validateName() {
        String patternName = "[a-zA-z]+([ '-][a-zA-Z]+)*";
        mUserName = mEditTextName.getText().toString().trim();

        if (mUserName.length() == 0) {
            requestFocus(mEditTextName);
            mEditTextName.setError(getString(R.string.error_name_empty));
            return false;
        }
        else  {
            Pattern pattern = Pattern.compile(patternName);
            Matcher matcher = pattern.matcher(mUserName);
            if (!matcher.matches()) {
                requestFocus(mEditTextName);
                mEditTextName.setError(getString(R.string.error_name_invalid));
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * This method Validates user input Email
     * @return boolean variable
     */
    public boolean validateEmail() {
        boolean isEmailValid = false;

        mUserEmail = mEditTextEmail.getText().toString().trim();
        if (mUserEmail.length() == 0) {
            requestFocus(mEditTextEmail);
            mEditTextEmail.setError(getString(R.string.error_email_empty));
            return false;
        } else  {
            isEmailValid = Patterns.EMAIL_ADDRESS.matcher(mUserEmail).matches();

            if (!isEmailValid) {
                requestFocus(mEditTextEmail);
                mEditTextEmail.setError(getString(R.string.error_email_invalid));
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * This method brings the View with error in focus
     * @param view
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
