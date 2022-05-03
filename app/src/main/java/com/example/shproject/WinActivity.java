package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
/*        Bundle arguments = getIntent().getExtras();
        int number_of_winner    = arguments.getInt("winner");
        Toast toast = Toast.makeText(this,"Победил "+number_of_winner, Toast.LENGTH_SHORT);
        toast.show();*/
        EditText et=findViewById(R.id.et_input);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                }
        });
        Button  btn=findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                String name=et.getText().toString();
                Cursor query = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+name+"'", null);
                int i=query.getCount();
                if(i==1){
                    et.setText("такое имя уже есть - введите другое");
//                    db.close();
//                    return;
                }
                else {
                    String s="CREATE TABLE " + name + " AS SELECT * FROM game";
                    db.execSQL(s);
//                    db.execSQL("CREATE TABLE " + name + " SELECT * FROM game;");
//                    et.setText("у Вас всё получилось!");
                    TextView tv=findViewById(R.id.tv_win);
                    tv.setVisibility(View.VISIBLE);
                    tv.setText("у Вас всё получилось!");
                    btn.setVisibility(View.INVISIBLE);

                    Button btn_continue=findViewById(R.id.button_for_continue2);
                    btn_continue.setVisibility(View.VISIBLE);
                    btn_continue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(WinActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    Button btn_exit=findViewById(R.id.button_for_exit2);
                    btn_exit.setVisibility(View.VISIBLE);
                    btn_exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finishAffinity();
                            System.exit(0);
                        }
                    });
                }
                query.close();
                db.close();
            }
        });
    }
/*
    public void onClick(View view) {
        Toast toast = Toast.makeText(this,"Пора покормить кота!", Toast.LENGTH_SHORT);
        toast.show();
    }
*/
}