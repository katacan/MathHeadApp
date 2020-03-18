package com.example.matematikkafas;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Substraction extends OperationFactory {

    private int level;
    private int trueAnswer;
    private int numOfAnswers = 4;
    private Random random = new Random();
    private ArrayList<Integer> answers = new ArrayList<>();
    private int firstSumNum;
    private int secondSumNum;
    private int indexOfTrueAnswer;

    public Substraction(int level) {
        super(level);
    }

    @Override
    public void setAnswers(int level) {
        indexOfTrueAnswer = setRandomIndexOfTrueAnswer();
        switch (level) {
            case 1:
                setPositiveResultOperationNums(10, 0);
                break;
            case 2:
                setPositiveResultOperationNums(20, 5);

                break;
            case 3:
                setPositiveResultOperationNums(35, 5);
                break;
            case 4:
                setPositiveResultOperationNums(50, 15);
                break;
            case 5:
                setPositiveResultOperationNums(150, 15);
                break;
            case 6:
                firstSumNum = randomInt(200, 30);
                secondSumNum = randomInt(200, 30);
                trueAnswer = getTrueAnswer();
                break;
            case 7:
                firstSumNum = randomInt(499, 100);
                secondSumNum = randomInt(499, 100);
                trueAnswer = getTrueAnswer();
                break;
            default:
                throw new IllegalArgumentException("This level does not exist");

        }
        for(int i = 0; i <numOfAnswers; i++) {
            if (i == indexOfTrueAnswer) {
                answers.add(trueAnswer);
            } else {
                getWrongAnswer(level, trueAnswer);
            }

        }

        // The last digits of two answers are same
        if(level > 3) {
            while(!containAnswersLastDigitTwoTimes(answers, trueAnswer)) {
                int index = random.nextInt(numOfAnswers);
                answers.remove(index);
                getWrongAnswer(level, trueAnswer);
            }
        }
    }

    public void setPositiveResultOperationNums(int upperBound, int lowerBound) {
        firstSumNum = randomInt(upperBound, lowerBound);
        secondSumNum = randomInt(upperBound, lowerBound);
        while(firstSumNum < secondSumNum) {
            firstSumNum = randomInt(upperBound, lowerBound);
            secondSumNum = randomInt(upperBound, lowerBound);
        }
        trueAnswer = getTrueAnswer();
    }


    public boolean containAnswersLastDigitTwoTimes (ArrayList<Integer> answers, int trueAnswer) {
        if(answers == null) {
            throw new NullPointerException("Answers is empty!");
        }

        if(!answers.contains(trueAnswer)) {
            answers.remove(answers.size() - 1);
            answers.add(trueAnswer);
            indexOfTrueAnswer = answers.size() - 1;
            return containAnswersLastDigitTwoTimes(answers, trueAnswer);
        }

        int counter = 0;
        int lastDigitOfTrueAnswer = trueAnswer % 10;

        for(int i = 0; i < answers.size(); i++) {
            int lastDigit = answers.get(i) % 10;
            if(lastDigitOfTrueAnswer == lastDigit) {
                counter++;
            }
            if(counter == 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    @Override
    public int randomInt(int upperBound, int lowerBound) {
        int randomNum = random.nextInt((upperBound - lowerBound) + 1) + lowerBound;
        return randomNum;
    }

    @Override
    public int getTrueAnswer() {
        return firstSumNum - secondSumNum;

    }

    @Override
    public int setRandomIndexOfTrueAnswer() {
        return random.nextInt(numOfAnswers);
    }

    @Override
    public int getWrongAnswer(int level, int trueAnswer) {
        int wrongAnswer;

        if(level == 1 || level == 2 || level == 3) {
            wrongAnswer = randomInt(trueAnswer + 4, trueAnswer - 4);
            while (wrongAnswer == trueAnswer || wrongAnswer < 0 || answers.contains(wrongAnswer)) {
                wrongAnswer  = randomInt(trueAnswer + 4, trueAnswer - 4);
            }
            answers.add(wrongAnswer);
        } else if (level == 4 || level == 5){
            wrongAnswer = randomInt(trueAnswer + 13, trueAnswer - 13);
            while (wrongAnswer == trueAnswer || wrongAnswer < 0 || answers.contains(wrongAnswer)) {
                wrongAnswer  = randomInt(trueAnswer + 10, trueAnswer - 10);
            }
            answers.add(wrongAnswer);
        } else {
            wrongAnswer = randomInt(trueAnswer + 30, trueAnswer - 30);
            // The result could be negative
            while (wrongAnswer == trueAnswer || answers.contains(wrongAnswer)) {
                wrongAnswer  = randomInt(trueAnswer  + 30, trueAnswer - 30);
            }
            answers.add(wrongAnswer);
        }
        return wrongAnswer;
    }

    @Override
    public int getNumOfAnswers() {
        return numOfAnswers;
    }

    @Override
    public int[] getQuestion() {
        int[] numbers = new int[2];
        numbers[0] = firstSumNum;
        numbers[1] = secondSumNum;
        return numbers;
    }

    @Override
    public int getIndexOfTrueAnswer() {
        return indexOfTrueAnswer;
    }

    @Override
    public void setOperationText(TextView textView) {
        String firstOperationNum = Integer.toString(firstSumNum);
        String secondOperationNum = Integer.toString(secondSumNum);
        textView.setText(firstOperationNum + " - " + secondOperationNum);
    }
}
