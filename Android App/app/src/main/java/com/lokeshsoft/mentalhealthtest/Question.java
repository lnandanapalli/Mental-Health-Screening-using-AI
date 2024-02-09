package com.lokeshsoft.mentalhealthtest;

public class Question
{
    // answerResId will store question
    private final int answerResId;

    // answerTrue will store correct answer
    // of the question provided
    private final boolean answerTrue;

    public Question(int answerResId, boolean answerTrue)
    {
        // setting the values through
        // arguments passed in constructor
        this.answerResId = answerResId;
        this.answerTrue = answerTrue;
    }

    // returning the question passed
    public int getAnswerResId()
    {
        return answerResId;
    }

    // setting the question passed

    // returning the correct answer
    // of question
    public boolean isAnswerTrue()
    {
        return answerTrue;
    }

    // setting the correct
    // ans of question
}
