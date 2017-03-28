package com.example.android.quizapp;

/**
 * Class - Quiz
 * Function - This class implements getter and setter methods of the Quiz object attributes
 */

public class Quiz {

    int quiz_id;
    String quiz_level;
    String quiz_question;
    int quiz_answer_num;
    String quiz_answer;
    String quiz_answer_options;

    /**
     * Empty Constructor
     */
    public Quiz() {

    }

    /**
     * Non-empty Constructors
     */
    public Quiz(int qid, String level, String question, int num, String answer, String options) {
        this.quiz_id = qid;
        this.quiz_level = level;
        this.quiz_question = question;
        this.quiz_answer_num = num;
        this.quiz_answer = answer;
        this.quiz_answer_options = options;
    }

    public Quiz(String level, String question, int num, String answer, String options) {
        this.quiz_level = level;
        this.quiz_question = question;
        this.quiz_answer_num = num;
        this.quiz_answer = answer;
        this.quiz_answer_options = options;
    }

    /**
     * Setter and Getter Methods
     */
    public void setID(int qid)              { this.quiz_id = qid; }
    public int getID()                      { return this.quiz_id; }

    public void setLevel(String level)      { this.quiz_level = level; }
    public String getLevel()                { return this.quiz_level; }

    public void setQuestion(String question){ this.quiz_question = question; }
    public String getQuestion()             { return this.quiz_question; }

    public void setNum(int num)             { this.quiz_answer_num = num; }
    public int getNum()                     { return this.quiz_answer_num; }

    public void setAnswer(String answer)    { this.quiz_answer = answer; }
    public String getAnswer()               { return this.quiz_answer; }

    public void setOptions(String options)  { this.quiz_answer_options = options; }
    public String getOptions()              { return this.quiz_answer_options; }

}
