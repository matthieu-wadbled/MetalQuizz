package com.wadbled.matthieu.metalquizz.model;

import java.util.List;

public class Question {
    private String mQuestion;
    private List<String> mChoiceList;
    private int mIndexReponse;

    public Question(String question, List<String> choiceList, int indexReponse) {
        mQuestion = question;
        mChoiceList = choiceList;
        mIndexReponse = indexReponse;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public int getIndexReponse() {
        return mIndexReponse;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public void setChoiceList(List<String> choiceList) {
        mChoiceList = choiceList;
    }

    public void setIndexReponse(int indexReponse) {
        mIndexReponse = indexReponse;
    }
}
