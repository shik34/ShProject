package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle arguments = getIntent().getExtras();
        int number_of_winner    = arguments.getInt("winner");
        Toast toast = Toast.makeText(this,"Победил "+number_of_winner, Toast.LENGTH_SHORT);
        toast.show();

    }
    public void onClick(View view) {


        Toast toast = Toast.makeText(this,"Пора покормить кота!", Toast.LENGTH_SHORT);
        toast.show();
    }
}