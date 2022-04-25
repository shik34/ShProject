package com.example.shproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity000 extends AppCompatActivity {
Button go;

    String[] gridSize = {"3 х 3", "4 х 4", "5 х 5"};
    String[] heuristicType = {"Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        TextView myTextView = new TextView(this);
        myTextView.setText("asd");
        myTextView.layout(50,50,150,150);
        LinearLayout linearLayout =  findViewById(R.id.linearLayout_main);
        linearLayout.addView(myTextView);*/


        Spinner spinner1 = findViewById(R.id.spinner11);
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gridSize);
        // Определяем разметку для использования при выборе элемента
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner1.setAdapter(adapter1);
        AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
/*                if(item=="выберите эвристику") myTextView.setText("*******");
                else myTextView.setText(item);*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                myTextView.setText("*************");
            }
        };
        spinner1.setOnItemSelectedListener(itemSelectedListener1);

        Spinner spinner = findViewById(R.id.spinner21);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, heuristicType);
        // Определяем разметку для использования при выборе элемента
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter2);
        AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
/*                if(item=="выберите эвристику") myTextView.setText("*******");
                else myTextView.setText(item);*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                myTextView.setText("*************");
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener2);


        go=(Button)findViewById(R.id.button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity000.this, PlayActivity.class);
                String eText = "information to send";
                intent.putExtra("et", eText);
                startActivity(intent);
            }
        });
    }
}