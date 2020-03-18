package com.example.matematikkafas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class operationActivity extends AppCompatActivity {

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button playAgainButton;

    private GridLayout buttonGridLayout;
    private ConstraintLayout gameLayout;

    private TextView operationTextView;
    private TextView scoreTextView;
    private TextView timerTextView;
    private TextView pauseTextView;
    private TextView resultTextView;

    private int numOfQuestions;
    private int numOfTrueAnsweredQuestions;
    private int numOfAnswerButtons;
    private int indexOfTrueAnswer;

    private long timeLimit;
    private long remainingCountDownTime;

    private float smallNumTextSize;
    private float normalNumTextSize;

    private boolean gamePaused = false;
    private boolean gameFinish;

    private CountDownTimer countDownTimer;

    private int level;
    private String chosenOperation;
    private String trueAnswerButtonTag;



    private OperationFactory operationFactory;

    private boolean isClickedAnswerTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerTextView = findViewById(R.id.timerTextView);
        operationTextView = findViewById(R.id.operationTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        pauseTextView = findViewById(R.id.pauseTextView);
        resultTextView = findViewById(R.id.resultTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);
        buttonGridLayout = findViewById(R.id.buttonGridLayout);
        gameLayout = findViewById(R.id.gameLayout);

        timeLimit = 45000;
        numOfTrueAnsweredQuestions = 0;
        numOfQuestions = 0;

        normalNumTextSize = (float) 55;
        smallNumTextSize = (float) 50;

        isClickedAnswerTrue = false;

        // See levelSelect.java for extras's names
        level = getIntent().getIntExtra("Level", 1);
        chosenOperation = getIntent().getStringExtra("Chosen Operation");

        createOperation(chosenOperation);
        numOfAnswerButtons = operationFactory.getNumOfAnswers();


        Log.i("Level", String.valueOf(this.level));
        Log.i("Chosen Operation", chosenOperation);

        startGame();
    }


    public void createOperation(String chosenOperation) {
        // It has to be completed
        if(chosenOperation.equals(Operations.SUMMATION.toString())) {
            operationFactory = new Summation(level);
        } else if(chosenOperation.equals(Operations.SUBSTRACTION.toString())) {
            operationFactory = new Substraction(level);
        } else if(chosenOperation.equals(Operations.MULTIPLICATION.toString())) {
            operationFactory = new Multiplication(level);
        }




    }

    public void setAnswers() {
        operationFactory.setAnswers(level);
        ArrayList<Integer> answers = operationFactory.getAnswers();
        int trueAnswerIndex = operationFactory.getIndexOfTrueAnswer();

        Log.i("ANSWERS ", answers.toString());

        // Set answers in buttons texts
        for(int i = 0; i < numOfAnswerButtons; i++) {
            if(buttonGridLayout.getChildAt(i) instanceof Button) {
                Button button = (Button) buttonGridLayout.getChildAt(i);
                if(i == trueAnswerIndex) {
                    trueAnswerButtonTag = button.getTag().toString();
                }
                String answer = Integer.toString(answers.get(i));
                button.setText(answer);
            }
        }
        answers.clear();
    }

    public void newRound() {
        setAnswers();
        operationFactory.setOperationText(operationTextView);
    }


    public void timer(long millisInfuture, long countDownInterval) {

        countDownTimer = new CountDownTimer(millisInfuture, countDownInterval){

            @Override
            public void onTick(long l) {
                int remainingTimeInSec = (int) (l / 1000);
                timerTextView.setText(Integer.toString(remainingTimeInSec));
                if(gamePaused) {
                    remainingCountDownTime = l;
                    cancel();
                }
                if(remainingTimeInSec == 0) {
                    resultTextView.setVisibility(View.INVISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);
                    gameFinish = true;
                    setAnswerButtonsClickable(gameFinish);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    public void setAnswerButtonsClickable(boolean gameFinish) {
        for(int i = 0; i < buttonGridLayout.getChildCount(); i++) {
            if(buttonGridLayout.getChildAt(i) instanceof Button) {
                Button button = (Button) buttonGridLayout.getChildAt(i);
                if (gameFinish) {
                    button.setClickable(false);
                } else {
                    button.setClickable(true);
                }
            }
        }
    }

    public void startGame() {
        gameFinish = false;
        newRound();
        timer(timeLimit, 1000);
    }

    public void playAgain(View view) {
        gameFinish = false;
        playAgainButton.setVisibility(View.INVISIBLE);
        setAnswerButtonsClickable(gameFinish);
        startGame();
    }


    public void playGame(View view) {
        Button button = (Button) view;
        int trueAnswer = operationFactory.getTrueAnswer();
        String chosenAnswer = button.getText().toString();

        if(trueAnswer > 999) {
            setAnswerButtonsTextsSize(smallNumTextSize);
        } else {
            setAnswerButtonsTextsSize(normalNumTextSize);
        }

        if(chosenAnswer.equals(Integer.toString(trueAnswer))) {
            resultTextView.setText("DOÐRU");
            numOfTrueAnsweredQuestions++;
        } else {
            resultTextView.setText("YANLIÞ");
        }
        resultTextView.setVisibility(View.VISIBLE);

        newRound();
    }

    public void setAnswerButtonsTextsSize(float size) {
        for(int i = 0; i < buttonGridLayout.getChildCount(); i++) {
            if(buttonGridLayout.getChildAt(i) instanceof Button) {
                Button button = (Button) buttonGridLayout.getChildAt(i);
                button.setTextSize(size);
            }
        }
    }


}
