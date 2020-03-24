package com.example.matematikkafas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class levelSelect extends AppCompatActivity {

    NumberPicker levelPicker;
    String chosenOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        // Intent comes from OperationSelectMenuActivity.java
        chosenOperation = getIntent().getStringExtra("Chosen Operation");
        Log.i("CHOSEN OPERATION", chosenOperation);

        levelPicker = findViewById(R.id.levelPicker);
        levelPicker.setMinValue(1);
        levelPicker.setMaxValue(7);
        levelPicker.setValue(4);
    }

    public void startGame(View view) {
        int level = levelPicker.getValue();
        Intent operationIntent = new Intent(this, operationActivity.class);
        operationIntent.putExtra("Level", level);
        operationIntent.putExtra("Chosen Operation" , chosenOperation);
        startActivity(operationIntent);
    }




}
