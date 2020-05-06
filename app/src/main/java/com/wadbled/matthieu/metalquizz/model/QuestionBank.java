package com.wadbled.matthieu.metalquizz.model;

import java.util.ArrayList;
import java.util.Random;

public class QuestionBank {
    private ArrayList<Question> mQuestionList;
    private ArrayList<Question> mQuestionAsked;
    private Random mRandomGenerator;

    public QuestionBank(ArrayList<Question> questionList) {
        mQuestionList = questionList;
        mQuestionAsked = new ArrayList<>();
        mRandomGenerator = new Random();

    }

    public int getIndex() {
        if (mRandomGenerator == null) {
            mRandomGenerator = new Random();
        }
        return mRandomGenerator.nextInt(mQuestionList.size());
    }

    public Question getQuestion() {
        Question question = mQuestionList.get(getIndex());
        mQuestionAsked.add(question);
        mQuestionList.remove(question);

        return question;
    }

    public void resetList() {
        for (int i = 0; i < mQuestionAsked.size(); i++) {
            mQuestionList.add(mQuestionAsked.get(i));
            mQuestionAsked.remove(i);
        }
    }

}
