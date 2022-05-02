package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
    int depth=0;
    SQLiteDatabase db;
    String heuristic;



//    Button button_for_text;
    TextView button_for_text;
    int i,j,ROWS,COLS;
    Desk desk;
    boolean turnIsAI;

    String [] infoBtn = {"Ваш ход","Машина думает! ЖДИТЕ !"};
//    private int[] imageView_ID=new int[9];
    int cellNumber;
    private boolean[] keyFinish=new boolean[9];

    TextView tv;
    private List<ImageView> imageViews;
//    private boolean[] keyTemp =new boolean[9];
    int playerNumber=0;
/*    Desk desk = new Desk(IMAGE_VIEW_NUMBER);
    private boolean checkVictory(int playerNumber){
        switch (desk.checkWin(playerNumber)) {
            case 0:
                tv.setText("OOOOOOOOOOOOOOOOOOOO");
                return true;
            case 1:
                tv.setText("XXXXXXXXXXXXXXXXXXXX");
                return true;
        }
        return false;
    }*/
ImageView [][] imageView;

@Override//*****************************************************************************************
    protected void onCreate(Bundle savedInstanceState) {



    class DoAI extends AsyncTask<Void, Void, Desk> {
        @Override
        protected void onPreExecute() {
//            button_for_text.setText(infoBtn[1]);
//            mInfoTextView.setText("Кот полез на крышу");
        }
        @Override
        protected Desk doInBackground(Void... voids) {
//            desk.turnAI(heuristic,depth, imageView,db);
/*            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }*/
            return null;
        }
        @Override
        protected void onPostExecute(Desk aVoid) {
            turnIsAI = false;
//            button_for_text.setText(infoBtn[0]);
            if (aVoid.checkWin(0)) {
                onClick_cancel(0);
            }
//            mInfoTextView.setText("Кот залез на крышу");
        }
    }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);



        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        button_for_text = (TextView) findViewById(R.id.button_for_text);

//get Data from MainActivity
        Bundle arguments = getIntent().getExtras();

        ROWS = arguments.getInt("size");
        COLS = ROWS;
        Desk desk = new Desk(ROWS,COLS);
        //int [][] cells=new int[ROWS][COLS];//??????????????????????????????????
        Cursor query = db.rawQuery("SELECT * FROM game;", null);
        for(int i=0;i<ROWS;i++) {
            query.moveToNext();
            for (int j = 0; j < COLS; j++) {
                desk.cells[i][j] = query.getInt(j+1);
            }
        }
        query.close();

        heuristic = arguments.getString("heuristic");
        depth     = arguments.getInt("depth");

        String firstPlayer  = arguments.getString("firstPlayer");
        switch (firstPlayer) {
            case "Я":
                turnIsAI=false;
                button_for_text.setText(infoBtn[0]);
                break;
            case "Искусственный интеллект":
                turnIsAI=true;
                button_for_text.setText(infoBtn[1]);
                break;
        }
//build grid
        /*ImageView [][] */imageView  =new ImageView[ROWS][COLS];
        int       [][] imageViewID=new int[ROWS][COLS];
        TableLayout tblLayout = null;
        tblLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < ROWS; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(500,500));
            for (int j = 0; j < COLS; j++) {
                // получение изображения полки
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
//              imageView[i][j].setOnClickListener(new View.OnClickListener() {
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
                            /*                                            Intent intent = new Intent(PlayActivity.this, WinActivity.class);
                                        intent.putExtra("winner", 1);
                                        startActivity(intent);*/
                        }
                        if(turnIsAI) {
//                          desk.turnAI(heuristic, depth, imageView, db);
                            Thread tr=new Thread(new Runnable() {
                                public void run() {
                                    //           try {Thread.sleep(3000);} catch (Exception e) {}
                                    //        button_for_text.setText(infoBtn[1]);
                                    desk.turnAI(heuristic,depth, imageView,db);
                                    //      button_for_text.setText(infoBtn[0]);
//                turnIsAI = false;
                                }
                            });
//                            tr.start();

//                            while(tr.isAlive()){}
//                          (new DoAI()).execute();
                            new AsyncTask<Void,Void,Void>(){
                                @Override
                                protected void onPreExecute() {
//            button_for_text.setText(infoBtn[1]);
//            mInfoTextView.setText("Кот полез на крышу");
                                }
                                @Override
                                protected Void doInBackground(Void... voids) {
            desk.turnAI(heuristic,depth, imageView,db);
/*            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }*/
                                    return null;
                                }
                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    //turnIsAI = false;
                                    button_for_text.setText(infoBtn[0]);
                                    if (desk.checkWin(0)) {
                                        onClick_cancel(0);
                                    }
//            mInfoTextView.setText("Кот залез на крышу");
                                }

                            }.execute();
                            button_for_text.setText(infoBtn[0]);
                            if (desk.checkWin(0)) {
                                onClick_cancel(0);
                                /*                                Intent intent = new Intent(PlayActivity.this, WinActivity.class);
                            intent.putExtra("winner", 0);
                            startActivity(intent);*/
                            }
                            turnIsAI = false;
                        }
                    }
/*                        for(int ii=0;ii<ROWS;ii++)
                           for(int jj=0;jj<COLS;jj++)
                               if(imageViewID[ii][jj]==id & cells[ii][jj]==1) {
                                      iv.setImageResource(R.drawable.icon_empty);
                                      cells[ii][jj]=-1;
                                      return;
                               }*/
                });
            }//onClick------------------------------------------------------------------------------
    }//onCreate-------------------------------------------------------------------------------------
    void onClick_cancel(int zero_or_cross){
        if(zero_or_cross==0) button_for_text.setText("Искусственный интеллект победил вас!");
        else                 button_for_text.setText("Вы победили искусственный интеллект!");
        turnIsAI=false;
        for(i=0;i<ROWS;i++)
            for(j=0;j<COLS;j++) {
                ImageView iv=imageView[i][j];
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }  );
            }
    }
}