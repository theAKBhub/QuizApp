package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_name;
    EditText editText_email;
    Button button_continue;
    Typeface custom_font;
    Spinner spinner_level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_email = (EditText) findViewById(R.id.editText_email);
        spinner_level = (Spinner) findViewById(R.id.spinner_level);
        button_continue = (Button) findViewById(R.id.button_continue);

        // Set custom typeface
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        setCustomTypeface();

        button_continue.setOnClickListener(this);
    }

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
        editText_name.setTypeface(custom_font);
        editText_email.setTypeface(custom_font);
        button_continue.setTypeface(custom_font);
    }

    /**
     * This method invokes processes to continue quiz
     */
    public void continueQuiz() {
        final Context context = this;
        String user_name = "";
        String user_email = "";
        String intent_msg = "";
        boolean isValidName = false;
        boolean isValidEmail = false;

        user_name = editText_name.getText().toString();
        user_email = editText_email.getText().toString();

        isValidName = validateName(user_name);
        if (isValidName) {
            isValidEmail = validateEmail(user_email);
        }
        if (isValidName && isValidEmail) {
            intent_msg = user_name + "|";
            intent_msg += user_email + "|";
            intent_msg += spinner_level.getSelectedItem();
            Intent intent = new Intent(context, IntroActivity.class);
            intent.putExtra("message", intent_msg);
            startActivity(intent);
        }
    }

    /**
     * This method Validates Name
     */
    public boolean validateName(String name) {
        String pattern_name = "[a-zA-z]+([ '-][a-zA-Z]+)*";

        if (name.length() == 0) {
            editText_name.setError(getString(R.string.error_name_empty));
            return false;
        }
        else  {
            Pattern pattern = Pattern.compile(pattern_name);
            Matcher matcher = pattern.matcher(name);
            if (!matcher.matches()) {
                editText_name.setError(getString(R.string.error_name_invalid));
                return false;
            }
            else {
                return true;
            }
        }
    }

    /**
     * This method Validates Email
     */
    public boolean validateEmail(String email) {
        String pattern_email = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (email.length() == 0) {
            editText_email.setError(getString(R.string.error_email_empty));
            return false;
        }
        else  {
            Pattern pattern = Pattern.compile(pattern_email);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                editText_email.setError(getString(R.string.error_email_invalid));
                return false;
            }
            else {
                return true;
            }
        }
    }
}
