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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowActivity extends AppCompatActivity {
    SQLiteDatabase db;
    TextView button_for_text;
    Button button_for_continue, button_for_exit;

    ImageView [][] imageView;
    int i,j,ROWS,COLS;
@Override//*****************************************************************************************
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
/*
        button_for_continue = (Button) findViewById(R.id.button_for_continue_SHOW);
        button_for_exit     = (Button) findViewById(R.id.button_for_exit_SHOW);*/

        ROWS =3;//??????????????????????????????????????????????????????????????????????????????????
        COLS = ROWS;
        Desk desk = new Desk(ROWS,COLS);

//build grid
        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM game;", null);
        for(int i=0;i<ROWS;i++) {
            query.moveToNext();
            for (int j = 0; j < COLS; j++) {
                desk.cells[i][j] = query.getInt(j+1);
            }
        }
        query.close();

        imageView  =new ImageView[ROWS][COLS];
        TableLayout tblLayout         =null;
        tblLayout                     =(TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < ROWS; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(500,500));
            for (int j = 0; j < COLS; j++) {
                imageView[i][j] = new ImageView(this);
                switch (desk.cells[i][j]){
                    case -1:
                        imageView[i][j].setImageResource(R.drawable.icon_empty);
                        break;
                    case  0:
                        imageView[i][j].setImageResource(R.drawable.icon_zero);
                        break;
                    case  1:
                        imageView[i][j].setImageResource(R.drawable.icon_cross);
                        break;
                }
                tableRow.addView(imageView[i][j], j);
            }
            tblLayout.addView(tableRow, i);
        }
        EditText et=findViewById(R.id.et_input_SHOW);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
    }//onCreate-------------------------------------------------------------------------------------

    public void onClick_continue_SHOW(View v){
        Intent intent = new Intent(ShowActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void onClick_exit_SHOW(View v){
        finishAffinity();
        System.exit(0);
    }
}