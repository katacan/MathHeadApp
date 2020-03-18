package com.example.matematikkafas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button goOperationChooseMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goOperationChooseMenuButton = findViewById(R.id.goOperationChooseMenuButton);

    }

    public void goOperationChooseMenu(View view) {
        Intent operationChooseMenu = new Intent(this, OperationSelectMenuActivity.class);
        startActivity(operationChooseMenu);
    }

}
