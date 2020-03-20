package com.example.matematikkafas;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Division extends OperationFactory {

    private int trueAnswer;
    private int numOfAnswers = 4;
    private Random random = new Random();
    private int level;
    private ArrayList<Integer> answers = new ArrayList<>();
    private int firstSumNum;
    private int secondSumNum;
    private int indexOfTrueAnswer;

    public Division(int level) {
        super(level);
    }

    @Override
    public void setAnswers(int level) {
        indexOfTrueAnswer = setRandomIndexOfTrueAnswer();
        switch (level) {
            case 1:
                setOperationNums(10, 1, 1);
                break;
            case 2:
                setOperationNums(50, 2, 2);
                break;
            case 3:
                setOperationNums(100, 2, 3);
                break;
            case 4:
                setOperationNums(100, 10, 4);
                break;
            case 5:
                setOperationNums(300, 10, 5);
                break;
            case 6:
                setOperationNums(600,10, 6);
                break;
            case 7:
                setOperationNums(1500, 50, 7);
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
     /*   if(level > 4) {
            while(!containAnswersLastDigitTwoTimes(answers, trueAnswer)) {
                int index = random.nextInt(numOfAnswers);
                answers.remove(index);
                getWrongAnswer(level, trueAnswer);
            }
        }  */

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

    public void setOperationNums(int upperLevel, int lowerLevel, int level) {
        firstSumNum = randomInt(upperLevel, lowerLevel);
        secondSumNum = randomInt(upperLevel, lowerLevel);

        while (firstSumNum < secondSumNum ) {
            firstSumNum = randomInt(upperLevel, lowerLevel);
            secondSumNum = randomInt(upperLevel, lowerLevel);
        }
        if (level > 2) {
            while (firstSumNum - secondSumNum < 10 ||firstSumNum % secondSumNum != 0) {
                firstSumNum = randomInt(upperLevel, lowerLevel);
                secondSumNum = randomInt(upperLevel, lowerLevel);
            }
        }


        // Divisior is a digit
        if(level == 2 || level == 3) {
            while(secondSumNum / 10 != 0 || firstSumNum % secondSumNum != 0) {
                secondSumNum = randomInt(upperLevel, lowerLevel);
            }
        }
        trueAnswer = getTrueAnswer();
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
        return firstSumNum / secondSumNum;
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
            wrongAnswer = randomInt(trueAnswer + 5, trueAnswer - 5);
            while (wrongAnswer == trueAnswer || wrongAnswer < 0 || answers.contains(wrongAnswer)) {
                wrongAnswer  = randomInt(trueAnswer + 5, trueAnswer - 5);
            }
            answers.add(wrongAnswer);
        } else {
            wrongAnswer = randomInt(trueAnswer + 8, trueAnswer - 8);
            while (wrongAnswer == trueAnswer || wrongAnswer < 0 || answers.contains(wrongAnswer)) {
                wrongAnswer  = randomInt(trueAnswer  + 8, trueAnswer - 8);
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
        textView.setText(firstOperationNum + " / " + secondOperationNum);
    }
}
