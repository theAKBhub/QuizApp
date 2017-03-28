package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    Typeface custom_font;
    TextView textView_hello;
    TextView textView_level_chosen;
    TextView textView_rules;
    Button button_start_quiz;

    String intent_msg;
    String [] intent_msg_array = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Bundle bundle = getIntent().getExtras();
        intent_msg = bundle.getString("message");
        intent_msg_array = intent_msg.split("\\|");

        // Set custom typeface
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");

        textView_hello = (TextView) findViewById(R.id.textView_hello);
        textView_level_chosen = (TextView) findViewById(R.id.textView_level_chosen);
        textView_rules = (TextView) findViewById(R.id.textView_rules);
        button_start_quiz = (Button) findViewById(R.id.button_start_quiz);

        button_start_quiz.setOnClickListener(this);

        setCustomTypeface();
        displayIntro();
    }

    @Override
    public void onClick(View view) {
        final Context context = this;

        switch (view.getId()) {
            case R.id.button_start_quiz:
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("message", intent_msg);
                startActivity(intent);
                break;
        }
    }

    /**
     * This method sets custom font for all views
     */
    public void setCustomTypeface() {
        textView_hello.setTypeface(custom_font);
        textView_level_chosen.setTypeface(custom_font);
        textView_rules.setTypeface(custom_font);
    }

    /**
     * This method displays the intro messages
     */
    public void displayIntro() {
        textView_hello.setText(getString(R.string.info_hello, intent_msg_array[0]));
        textView_level_chosen.setText(getString(R.string.info_level_chosen, intent_msg_array[2]));
    }
}
