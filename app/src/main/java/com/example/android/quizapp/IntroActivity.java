package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    Typeface custom_font;
    TextView textView_hello;
    TextView textView_level_chosen;
    TextView textView_rules;
    Button button_start_quiz;

    List<Quiz> quiz;

    String intent_msg;
    String [] intent_msg_array = new String[3];

    int [] arrayId = new int[3];
    String [] arrayQuestion = new String[3];

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

        setQuizRecords();
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

    /**
     * This method populates table
     */
    public void setQuizRecords() {
        DatabaseHandler db = new DatabaseHandler(this);

        // Clear table
        db.deleteRows();

        // Add records
        db.addRecord(new Quiz("Easy", "Which view will you use to enable scrolling on screen?", 1, "ScrollView", "RelativeLayout|ScrollView|LinearLayout|GridView"));
        db.addRecord(new Quiz("Easy", "Which view will you use to display an Image?", 1, "ImageView", "ImageView|EditText|TextView|Button"));
        db.addRecord(new Quiz("Easy", "Which attribute is used to set font size in TextView?", 1, "android:textSize", "android:textStyle|android:textSize|android:textColor|android:textAllCaps"));
        db.addRecord(new Quiz("Easy", "Which one of the following is a valid Java identifier?", 1, "BIGclass", "#number|BIGclass|4corners|!first_name"));
        db.addRecord(new Quiz("Easy", "Which one of the following is a valid Java data type?", 1, "boolean", "boolean|static|null|Integer"));
        db.addRecord(new Quiz("Easy", "XML Layout files are stored in directory", 1, "/res/layout", "/assets|/src|/res/values|/res/layout"));
        db.addRecord(new Quiz("Easy", "All color values in an android app are stored in colors.xml", 1, "True", "True|False"));

        db.addRecord(new Quiz("Medium", "All activities in an app must be declared in AndroidManifest.xml", 1, "True", "True|False"));
        db.addRecord(new Quiz("Medium", "Which are the valid layouts available in Android", 1, "All of the above", "LinearLayout|RelativeLayout|ConstraintLayout|All of the above"));
        db.addRecord(new Quiz("Medium", "Attribute android:layout_weight is associated with this layout", 1, "LinearLayout", "LinearLayout|RelativeLayout|ConstraintLayout|All of the above"));
        db.addRecord(new Quiz("Medium", "Which layout arranges its children in a single column or a single row", 1, "LinearLayout", "LinearLayout|RelativeLayout|ConstraintLayout|GridLayout"));
        db.addRecord(new Quiz("Medium", "Which relational operator will you use to check if 2 variables have same values?", 1, "==", ">=|<=|=|=="));
        db.addRecord(new Quiz("Medium", "Which of the following is not a Java keyword", 1, "Integer", "static|try|Integer|new"));
        db.addRecord(new Quiz("Medium", "A method declared as void must have a return statement", 1, "False", "True|False"));

        db.addRecord(new Quiz("Difficult", "Which of the following methods is associated with Intent?", 1, "startActivity", "onCreate|startActivity|onStartCommand|onReceive"));
        db.addRecord(new Quiz("Difficult", "What is correct syntax for main method of a java class?", 1, "public static void main(String[] args)", "public static int main(String[] args)|public int main(String[] args)|public static void main(String[] args)|None of the above"));
        db.addRecord(new Quiz("Difficult", "Java keywords can be both in uppercase and lowercase", 1, "False", "True|False"));
        db.addRecord(new Quiz("Difficult", "These variables can only be accessed from inside the method they are declared and are destroyed when the method stops executing", 1, "Local", "Global|Private|Static|Local"));
        db.addRecord(new Quiz("Difficult", "Choose the right data type for this value - 5.5", 1, "double", "double|boolean|int|String"));
        db.addRecord(new Quiz("Difficult", "Choose the right data type for this value - 5.5", 1, "double", "double|boolean|int|String"));
        db.addRecord(new Quiz("Difficult", "Which of the following is a loop control in Java", 1, "while", "if..else|while|foreach"));

    }
}
