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
Button go;
/*

 */
    String[] countries = { "выберите эвристику","Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myTextView = new TextView(this);
        myTextView.setText("asd");
        myTextView.layout(50,50,150,150);
        LinearLayout linearLayout =  findViewById(R.id.linearLayout_main);
        linearLayout.addView(myTextView);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                if(item=="выберите эвристику") myTextView.setText("*******");
                else myTextView.setText(item);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                myTextView.setText("*************");
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);


        go=(Button)findViewById(R.id.button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                String eText = "information to send";
                intent.putExtra("et", eText);
                startActivity(intent);
            }
        });
    }
}