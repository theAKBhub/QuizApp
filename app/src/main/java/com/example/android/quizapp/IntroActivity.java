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
        DatabaseHandler db = new DatabaseHandler(this);

        // Clear table
        db.deleteRows();

        // Add records
        db.addRecord(new Quiz(
                "Easy",
                "Which view will you use to enable scrolling on screen?",
                1, "ScrollView",
                "RelativeLayout|ScrollView|LinearLayout|GridView"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Which view will you use to display an Image?",
                1, "ImageView",
                "ImageView|EditText|TextView|Button"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Which attribute is used to set font size in TextView?",
                1, "android:textSize",
                "android:textStyle|android:textSize|android:textColor|android:textAllCaps"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Which one of the following is a valid Java identifier?",
                1, "BIGclass",
                "#number|BIGclass|4corners|!first_name"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Which one of the following is a valid Java data type?",
                1, "boolean",
                "boolean|static|null|Integer"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "XML Layout files are stored in directory",
                1, "/res/layout",
                "/assets|/src|/res/values|/res/layout"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "All color values in an android app are stored in colors.xml",
                1, "True",
                "True|False"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Which of the following attributes is used to identify the Activity elements",
                1, "android:id",
                "android:id|android:src|android:text|android:gravity"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "In XML layouts, this attribute is used to prefix and define a namespace",
                1, "xmlns",
                "xmlns"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "The following component is used to display information for a short period of time",
                1, "Toast",
                "Toast"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "LinearLayout can have the following orientations",
                2, "Horizontal|Vertical",
                "Horizontal|Diagonal|Vertical|Radial"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Which of the languages below are used in building an Android app?",
                2, "XML|Java",
                "Objective-C|XML|Python|Java"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "What value should be assigned to \"android:layout_width\" if you want a TextView " +
                        "to take up width only as much needed for its content?",
                1, "wrap_content",
                "wrap_content"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "What is the preferred unit for dimensions in Android?",
                1, "dp",
                "dp"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Which symbol is used to reference a resource in the Android App?",
                1, "@",
                "@"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Pick the values that can be assigned to \"android:scaleType\" attribute of an ImageView?",
                2, "center|centerCrop",
                "left|inside|center|centerCrop"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Pick the attributes that can be used to control the position of an element on the UI",
                2, "android:layout_margin|android:padding",
                "android:margin|android:layout_margin|android:layout_padding|android:padding"
        ));
        db.addRecord(new Quiz(
                "Easy",
                "Pick the logical operators used in Java",
                2, "&|^",
                "*|&|^|%"
        ));

        db.addRecord(new Quiz(
                "Medium",
                "All activities in an app must be declared in AndroidManifest.xml",
                1, "True",
                "True|False"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which are the valid layouts available in Android",
                1, "All of the above",
                "LinearLayout|RelativeLayout|ConstraintLayout|All of the above"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Attribute android:layout_weight is associated with this layout",
                1, "LinearLayout",
                "LinearLayout|RelativeLayout|ConstraintLayout|All of the above"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which layout arranges its children in a single column or a single row",
                1, "LinearLayout",
                "LinearLayout|RelativeLayout|ConstraintLayout|GridLayout"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which relational operator will you use to check if 2 variables have same values?",
                1, "==",
                ">=|<=|=|=="
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which of the following is not a Java keyword",
                1, "Integer",
                "static|try|Integer|new"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "A method declared as void must have a return statement",
                1, "False",
                "True|False"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "The first method of any Activity in an Android app is",
                1, "onCreate()",
                "onCreate()|onStart()|onResume()|onRestart()"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "This component is used to launch an activity from another activity",
                1, "Intent",
                "Intent"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "A method cannot be overridden when declared",
                1, "final",
                "final"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which of the following attributes are used in defining style of a TextView element?",
                2, "android:textSize|android:textColor",
                "android:textSize|android:textColor|android:orientation|android:buttonTint"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Pick the Intent types available in Android",
                2, "Implicit Intent|Explicit Intent",
                "Implicit Intent|Explicit Intent|Inbound Intent|Outbound Intent"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which string function can be used to split a string?",
                1, "split",
                "split"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which keyword in Java is used to pass a value from one method to another?",
                1, "return",
                "return"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Name the block used in Java where all exceptions are caught",
                1, "catch",
                "catch"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which of the following features are applicable to Java?",
                2, "Object Oriented|Multithreaded",
                "Object Oriented|Procedural|Multithreaded|Non-portable"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which of the following principles are applicable to Java?",
                2, "Inheritance|Polymorphism",
                "Inheritance|Decapsulation|Polymorphism|Conceptual"
        ));
        db.addRecord(new Quiz(
                "Medium",
                "Which of these methods are called when the screen changes orientation?",
                2, "onCreate()|onRestoreInstanceState()",
                "onCreate()|onRestart()|onActivityResult()|onRestoreInstanceState()"
        ));

        db.addRecord(new Quiz(
                "Difficult",
                "Which of the following methods is associated with Intent?",
                1, "startActivity",
                "onCreate|startActivity|onStartCommand|onReceive"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "What is correct syntax for main method of a java class?",
                1, "public static void main(String[] args)",
                "public static int main(String[] args)|public int main(String[] args)|" +
                        "public static void main(String[] args)|None of the above"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Java keywords can be both in uppercase and lowercase",
                1, "False",
                "True|False"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "These variables can only be accessed from inside the method " +
                        "they are declared and are destroyed when the method stops executing",
                1, "Local",
                "Global|Private|Static|Local"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Choose the right data type for this value - 5.5",
                1, "double",
                "double|boolean|int|String"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Choose the right data type for this variable - isEndofQuiz",
                1, "boolean",
                "float|boolean|int|String"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Which of the following is a loop control in Java",
                1, "while",
                "if..else|while|foreach|switch"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "This type of Intent exactly specifies which component to call to perform an action",
                1, "Explicit Intent",
                "Explicit Intent|Implicit Intent"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "A class cannot be extended when declared",
                1, "final",
                "final"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "What will be the value of \"y\" after the following statements execute?" +
                        "\nint x = 7;\nint y = x++;",
                1, "7",
                "7"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Which of the following are arithmetic operators?",
                2, "++|--",
                "AND|++|--|OR"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Two elements are involved in creating an object in Java. They are:",
                2, "new operator|constructor method",
                "new operator|subclass|interface|constructor method"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "What will be the output of the following piece of code?\n" +
                        "String s1 = new String(\"thomas\");\n" +
                        "String s2 = new String(\"thomson\");\n?" +
                        "System.out.println(s1 = s2);",
                1, "thomson",
                "thomson"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Which keyword in Java refers to the current object?",
                1, "this",
                "this"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Which keyword refers to the immediate parent class object?",
                1, "super",
                "super"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Pick the keywords that are used in Java exception handling",
                2, "try|catch",
                "error|try|exception|catch"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "Pick the functions that can be associated with a Java method",
                2, "Overriding|Overloading",
                "Overwriting|Overthrowing|Overriding|Overloading"
        ));
        db.addRecord(new Quiz(
                "Difficult",
                "A class in Java can be",
                2, "final|abstract",
                "default|final|native|abstract"
        ));
    }
}
