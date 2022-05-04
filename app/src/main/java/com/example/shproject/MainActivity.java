package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int depth = 3;// >=1
    String[] gridSize = {"3 x 3", "4 x 4", "5 x 5"};
    int size = 3;

    String[] heuristicType = {"По горизональным рядам", "Случайный ход", "Лучший ход", "Настоящая эвристика"};
    String heuristic;

    String[] whoAreFirst = {"Я", /*"увы пока нет выбора"};//"*/"Искусственный интеллект"};
    String firstPlayer="Я";
    Button btn_load;
    @Override
//*****************************************************************************************
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//instance=this;
        SharedPreferences sharedPreferences = this.getSharedPreferences("visible", MODE_PRIVATE);

        TextView tv_dbOut = findViewById(R.id.tv_dbOut);
        TextView tv_info2 = findViewById(R.id.tv_info2);
        btn_load = (Button) findViewById(R.id.btn_load);
/*        Cursor query = db.rawQuery("SELECT * FROM game;", null);
        TextView tv_dbOut = findViewById(R.id.tv_dbOut);
        tv_dbOut.setText("");
        String s;
        while(query.moveToNext()){
            s="";
            for (int i = 0; i <6 ; i++) {
                s+=" "+query.getInt(i);
            }
            tv_dbOut.append(s+"\n");
        }
        query.close();*/
        String[] hardLevel = {"Первый", "Второй", "Третий"/*, "Четвёртый", "Пятый", "Шестой", "Седьмой", "Восьмой", "Девятый"*/};
        Spinner spinner_hardLevel = findViewById(R.id.spinner_hard_level);
        ArrayAdapter<String> adapter_hardLevel = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hardLevel);
        // Определяем разметку для использования при выборе элемента
        adapter_hardLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner_hardLevel.setAdapter(adapter_hardLevel);
        AdapterView.OnItemSelectedListener itemSelectedListener_hardLevel = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition(position);
                switch (item) {
                    case "Первый":
                        depth = 1;
                        break;
                    case "Второй":
                        depth = 2;
                        break;
                    case "Третий":
                        depth = 3;
                        break;
/*                    case "Четвёртый":
                        depth = 4;
                        break;
                    case "Пятый":
                        depth = 5;
                        break;
                    case "Шестой":
                        depth = 6;
                        break;
                    case "Седьмой":
                        depth = 7;
                        break;
                    case "Восьмой":
                        depth = 8;
                        break;
                    case "Девятый":
                        depth = 9;
                        break;*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                depth = 1;
            }
        };
        spinner_hardLevel.setOnItemSelectedListener(itemSelectedListener_hardLevel);

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
                String item = (String) parent.getItemAtPosition(position);
                switch (item) {
                    case "3 x 3":
                        size = 3;
                        break;
                    case "4 x 4":
                        size = 4;
                        break;
                    case "5 x 5":
                        size = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                size = 3;
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
                String item = (String) parent.getItemAtPosition(position);
                switch (item) {
                    case "По горизональным рядам":
                        heuristic = "Горизонально последовательно";
                        tv_dbOut.setVisibility(View.INVISIBLE);
                        tv_info2.setVisibility(View.INVISIBLE);
                        spinner_hardLevel.setVisibility(View.INVISIBLE);
                        break;
                    case "Случайный ход":
                        heuristic = "Случайно";
                        tv_dbOut.setVisibility(View.INVISIBLE);
                        tv_info2.setVisibility(View.INVISIBLE);
                        spinner_hardLevel.setVisibility(View.INVISIBLE);
                        break;
                    case "Лучший ход":
                        heuristic = "Следующий лучший";
                        tv_dbOut.setVisibility(View.INVISIBLE);
                        tv_info2.setVisibility(View.INVISIBLE);
                        spinner_hardLevel.setVisibility(View.INVISIBLE);
                        break;
                    case "Настоящая эвристика":
                        heuristic = "Настоящая эвристика";
                        tv_dbOut.setVisibility(View.VISIBLE);
                        tv_info2.setVisibility(View.VISIBLE);
                        spinner_hardLevel.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                heuristic = "Горизонально последовательно";
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
                String item = (String) parent.getItemAtPosition(position);
                switch (item) {
                    case "Я":
                        firstPlayer = "Я";
                        break;
                    case "Искусственный интеллект":
                        firstPlayer = "Искусственный интеллект";
                        break;
                    default :
                        firstPlayer = "Я";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                firstPlayer = "Я";
            }
        };
        spinner31.setOnItemSelectedListener(itemSelectedListener3);

        Button go = (Button) findViewById(R.id.button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                db.execSQL("DROP TABLE IF EXISTS game");
                db.execSQL("CREATE TABLE game (row_id INTEGER,column_0 INTEGER,column_1 INTEGER,column_2 INTEGER,column_3 INTEGER,column_4 INTEGER)");
                db.execSQL("INSERT INTO game VALUES (0,-1,-1,-1,-1,-1),(1,-1,-1,-1,-1,-1),(2,-1,-1,-1,-1,-1),(3,-1,-1,-1,-1,-1),(4,-1,-1,-1,-1,-1);");
                db.close();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("VISIBILITY", 0);
                if(firstPlayer=="Я") editor.putBoolean("first_step_AI_NOT_done", false);
                else                 editor.putBoolean("first_step_AI_NOT_done", true);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("size", size);
                intent.putExtra("heuristic", heuristic);
                intent.putExtra("depth", depth);
                intent.putExtra("firstPlayer", firstPlayer);
                startActivity(intent);
            }
        });
    }
    /*    private static MainActivity instance;
        public static MainActivity getInstance() {
            return instance;
        }
        public void exit() {
            //        this.finish();
            finishAffinity();
    //        finishAndRemoveTask();
    //        android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);

        }*/
    public void onClick_load(View view){
        SharedPreferences sharedPreferences = this.getSharedPreferences("visible_show",MODE_PRIVATE);
//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("VISIBILITY_SHOW", 1);
        editor.commit();
        Intent intent = new Intent(MainActivity.this, ShowActivity.class);
        startActivity(intent);
    }
}