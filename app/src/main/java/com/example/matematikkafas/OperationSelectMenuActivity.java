package com.example.matematikkafas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OperationSelectMenuActivity extends AppCompatActivity {

    Button sum;
    Button sub;
    Button mul;
    Button div;
    ConstraintLayout operationSelectLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_select_menu);

        sum = findViewById(R.id.sumButton);
        sub = findViewById(R.id.subButton);
        mul = findViewById(R.id.mulButton);
        div = findViewById(R.id.divButton);

        ConstraintLayout operationSelectLayout = findViewById(R.id.operationSelectLayout);

    }

    public void goLevelSelect(View view) {
        String chosenOperation = view.getTag().toString();
        Intent levelSelectIntent = new Intent(this, levelSelect.class);
        levelSelectIntent.putExtra("Chosen Operation", chosenOperation);
        startActivity(levelSelectIntent);
    }

}
