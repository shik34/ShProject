package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayActivity extends AppCompatActivity {

    SQLiteDatabase db;
    TextView button_for_text;
    Button button_for_save, button_for_continue, button_for_exit;

    ImageView [][] imageView;
    int depth=0,i,j,ROWS,COLS;
    boolean turnIsAI;
//    boolean only_for_first_step_AI;
    String heuristic, infoBtn [] = {"Ваш ход","Машина думает! ЖДИТЕ !"};
@Override//*****************************************************************************************
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        button_for_text     = (TextView) findViewById(R.id.button_for_text);
        button_for_save     = (Button) findViewById(R.id.button_for_save);
        button_for_continue = (Button) findViewById(R.id.button_for_continue);
        button_for_exit     = (Button) findViewById(R.id.button_for_exit);

    SharedPreferences sharedPreferences = this.getSharedPreferences("visible",MODE_PRIVATE);
    Integer visibility = sharedPreferences.getInt("VISIBILITY", 0);
    if(visibility==1){
        button_for_save.setVisibility(View.VISIBLE);
        button_for_continue.setVisibility(View.VISIBLE);
        button_for_exit.setVisibility(View.VISIBLE);
    }
//get Data from MainActivity
        Bundle arguments = getIntent().getExtras();

        ROWS = arguments.getInt("size");
        COLS = ROWS;
        Desk desk = new Desk(ROWS,COLS);

        heuristic = arguments.getString("heuristic");
        depth     = arguments.getInt("depth");

        String firstPlayer  = arguments.getString("firstPlayer");
        String title=new String();
        switch (firstPlayer) {
            case "Я":
                turnIsAI=false;
//                only_for_first_step_AI=false;
                title=infoBtn[0];
                break;
            case "Искусственный интеллект":
                turnIsAI=true;
//                only_for_first_step_AI=true;
                title=infoBtn[1];
                break;
        }
        if(sharedPreferences.getString("title","")!="") title=sharedPreferences.getString("title","");
        button_for_text.setText(title);
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

        /*ImageView [][] */imageView  =new ImageView[ROWS][COLS];
        int [][] imageViewID          =new int[ROWS][COLS];
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
                imageView[i][j].setId(i*10+j);
                tableRow.addView(imageView[i][j], j);
                imageViewID[i][j]=imageView[i][j].getId();
            }
            tblLayout.addView(tableRow, i);
        }
//onClick*******************************************************************************************
        for(i=0;i<ROWS;i++)
            for(j=0;j<COLS;j++) {
                ImageView iv=imageView[i][j];
                iv.setOnClickListener(new View.OnClickListener() {
@Override//*****************************************************************************************
                    public void onClick(View v) {
//                      imageView[i][j].setImageResource(R.drawable.icon_cross);
                        int id  = v.getId();
                        for (int ii = 0; ii < ROWS; ii++)
                            for (int jj = 0; jj < COLS; jj++)
                                if (turnIsAI == false & imageViewID[ii][jj] == id & desk.cells[ii][jj] == -1) {
                                    iv.setImageResource(R.drawable.icon_cross);
                                    desk.cells[ii][jj] = 1;
                                    db.execSQL("UPDATE game SET column_"+jj+"=1 WHERE row_id="+ii+";");
                                    turnIsAI = true;
                                    button_for_text.setText(infoBtn[1]);
                                }
                        if (desk.checkWin(1)) {
                            onClick_cancel(1);
                        }
                        if (desk.checkDeskFull()) {
                            onClick_cancel(2);
                        }

    if(turnIsAI) {

        Thread tr=new Thread(new Runnable() {
            public void run() {
                button_for_text.setText(infoBtn[1]);

                try {TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e){}
                desk.turnAI(heuristic,depth, imageView,db);

                {//onPostExecute
                    turnIsAI = false;
                    button_for_text.setText(infoBtn[0]);
                    if (desk.checkWin(0)) {
                        onClick_cancel(0);
                    }
                    if (desk.checkDeskFull()) {
                        onClick_cancel(2);
                    }
                }

            }
        });
        tr.start();
                        }
                    }

                });
            }//onClick------------------------------------------------------------------------------
    //если первый ход машины ***************************************************************************
    SharedPreferences sharedPreferences2 = this.getSharedPreferences("visible",MODE_PRIVATE);
    Boolean first_step_AI_done = sharedPreferences2.getBoolean("first_step_AI_NOT_done", true);
        if(first_step_AI_done){
            turnIsAI=false;

            button_for_text.setText(infoBtn[1]);
            desk.turnAI(heuristic,depth,imageView,db);
            button_for_text.setText(infoBtn[0]);

            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
            editor2.putBoolean("first_step_AI_NOT_done", false);
            editor2.commit();
        }
    //если первый ход машины ---------------------------------------------------------------------------
    }
//onCreate------------------------------------------------------------------------------------------
    void onClick_cancel(int zero_or_cross){
    String title2=new String();
    switch (zero_or_cross) {
        case 0:
            title2="Искусственный интеллект победил вас!";
            break;
        case 1:
            title2="Вы победили искусственный интеллект!";
            break;
        case 2:
            title2="НИЧЬЯ !";
            break;
    }
        button_for_text.setText(title2);
        turnIsAI=false;
        for(i=0;i<ROWS;i++)
            for(j=0;j<COLS;j++) {
                ImageView iv=imageView[i][j];
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        button_for_save.setVisibility(View.VISIBLE);
        button_for_continue.setVisibility(View.VISIBLE);
        button_for_exit.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = this.getSharedPreferences("visible",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("VISIBILITY", 1);
        editor.putString("title", title2);
        editor.commit();

        db.close();
    }
    public void onClick_save(View v){
        Intent intent = new Intent(PlayActivity.this, WinActivity.class);
        intent.putExtra("winner", 0);
        startActivity(intent);
    }
    public void onClick_continue(View v){
        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
//        intent.putExtra("winner", 0);
        startActivity(intent);
    }
    public void onClick_exit(View v){
        finishAffinity();
        System.exit(0);
    }
}