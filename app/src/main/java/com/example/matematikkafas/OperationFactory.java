package com.example.matematikkafas;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public abstract class OperationFactory {

    private int level;

    public OperationFactory(int level) {
        this.level = level;
    }

    public abstract void setAnswers(int level);
    public abstract ArrayList<Integer> getAnswers();

    public abstract int randomInt(int upperBound, int lowerBound);
    public abstract int getTrueAnswer();
    public abstract int setRandomIndexOfTrueAnswer();
    public abstract int getWrongAnswer(int level, int trueAnswer);
    public abstract int getNumOfAnswers();
    public abstract int[] getQuestion();
    public abstract int getIndexOfTrueAnswer();
    public abstract void setOperationText(TextView textView);
}
