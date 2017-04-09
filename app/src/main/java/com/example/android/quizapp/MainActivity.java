package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
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
        String userName = "";
        String userEmail = "";
        String intentMessage = "";
        boolean isValidName = false;
        boolean isValidEmail = false;

        userName = mEditTextName.getText().toString();
        userEmail = mEditTextEmail.getText().toString();

        isValidName = validateName(userName);
        // Validate email only if name is found valid
        if (isValidName) {
            isValidEmail = validateEmail(userEmail);

            // Form intent message if both name and email found valid
            if (isValidEmail) {
                intentMessage = userName + "|";
                intentMessage += userEmail + "|";
                intentMessage += mSpinnerLevel.getSelectedItem();
                Intent intent = new Intent(context, IntroActivity.class);
                intent.putExtra("message", intentMessage);
                startActivity(intent);
            }
        }
    }

    /**
     * This method Validates user input Name
     * @param name
     * @return boolean variable
     */
    public boolean validateName(String name) {
        String patternName = "[a-zA-z]+([ '-][a-zA-Z]+)*";

        if (name.length() == 0) {
            mEditTextName.setError(getString(R.string.error_name_empty));
            return false;
        }
        else  {
            Pattern pattern = Pattern.compile(patternName);
            Matcher matcher = pattern.matcher(name);
            if (!matcher.matches()) {
                mEditTextName.setError(getString(R.string.error_name_invalid));
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * This method Validates user input Email
     * @param email
     * @return boolean variable
     */
    public boolean validateEmail(String email) {
        boolean isEmailValid = false;

        if (email.length() == 0) {
            mEditTextEmail.setError(getString(R.string.error_email_empty));
            return false;
        } else  {
            isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();

            if (!isEmailValid) {
                mEditTextEmail.setError(getString(R.string.error_email_invalid));
                return false;
            } else {
                return true;
            }
        }
    }
}
