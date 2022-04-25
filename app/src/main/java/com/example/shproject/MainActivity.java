package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] gridSize = {"3 x 3", "4 x 4", "5 x 5"};
    int size=3;

    String[] heuristicType = {"Горизонально последовательно", "Случайно", "Следующий лучший", "Настоящая эвристика"};
    String heuristic;

    String[] whoAreFirst = {"Я", "Искусственный интеллект"};
    String firstPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        TextView myTextView = new TextView(this);
        myTextView.setText("asd");
        myTextView.layout(50,50,150,150);
        LinearLayout linearLayout =  findViewById(R.id.linearLayout_main);
        linearLayout.addView(myTextView);*/


        Spinner spinner11 = findViewById(R.id.spinner11);
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gridSize);
        // Определяем разметку для использования при выборе элемента
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner11.setAdapter(adapter1);
        AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                switch (item){
                    case "3 x 3":size=3;break;
                    case "4 x 4":size=4;break;
                    case "5 x 5":size=5;break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                size=3;
            }
        };
        spinner11.setOnItemSelectedListener(itemSelectedListener1);

        Spinner spinner21 = findViewById(R.id.spinner21);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, heuristicType);
        // Определяем разметку для использования при выборе элемента
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner21.setAdapter(adapter2);
        AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                switch (item){
                    case "Горизонально последовательно":heuristic="Горизонально последовательно";break;
                    case "Случайно":                    heuristic="Случайно";                    break;
                    case "Следующий лучший":            heuristic="Следующий лучший";            break;
                    case "Настоящая эвристика":         heuristic="Настоящая эвристика";         break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                heuristic="Горизонально последовательно";
            }
        };
        spinner21.setOnItemSelectedListener(itemSelectedListener2);

        Spinner spinner31 = findViewById(R.id.spinner31);
        ArrayAdapter<String> adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, whoAreFirst);
        // Определяем разметку для использования при выборе элемента
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner31.setAdapter(adapter3);
        AdapterView.OnItemSelectedListener itemSelectedListener3 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                switch (item){
                    case "Я":                       firstPlayer="Я";                      break;
                    case "Искусственный интеллект": firstPlayer="Искусственный интеллект";break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                firstPlayer="Я";
            }
        };
        spinner31.setOnItemSelectedListener(itemSelectedListener3);


        Button go=(Button)findViewById(R.id.button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                String eText = "information to send";
                intent.putExtra("size", size);
                intent.putExtra("heuristic", heuristic);
                intent.putExtra("firstPlayer", firstPlayer);
                startActivity(intent);
            }
        });
    }
}