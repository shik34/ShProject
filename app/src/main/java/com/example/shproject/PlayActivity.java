package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    Button btn;
    int i,j;
    Desk desk;
    boolean turnIsAI;

    String [] infoBtn = {"Ваш ход","Ходит искусственный интеллект"};
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        btn     = (Button) findViewById(R.id.btn);

//get Data from MainActivity
        Bundle arguments = getIntent().getExtras();

        int ROWS = arguments.getInt("size");
        int COLS = ROWS;
        Desk desk = new Desk(ROWS,COLS);
        int [][] cells=new int[ROWS][COLS];//??????????????????????????????????
        for(int i=0;i<ROWS;i++)
            for(int j=0;j<COLS;j++) cells[i][j]=-1;

        String heuristic    = arguments.getString("heuristic");

        String firstPlayer  = arguments.getString("firstPlayer");
        switch (firstPlayer) {
            case "Я":
                turnIsAI=false;
                btn.setText(infoBtn[0]);
                break;
            case "Искусственный интеллект":
                turnIsAI=true;
                btn.setText(infoBtn[1]);
                break;
        }
//build grid
        ImageView [][] imageView  =new ImageView[ROWS][COLS];
        int       [][] imageViewID=new int[ROWS][COLS];
        TableLayout tblLayout = null;
        tblLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < ROWS; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(500,500));
            for (int j = 0; j < COLS; j++) {
                // получение изображения полки
                imageView[i][j] = new ImageView(this);
                imageView[i][j].setImageResource(R.drawable.icon_empty);
                imageView[i][j].setId(i*10+j);
                tableRow.addView(imageView[i][j], j);
                imageViewID[i][j]=imageView[i][j].getId();
            }
            tblLayout.addView(tableRow, i);
        }

        for(i=0;i<ROWS;i++)
            for(j=0;j<COLS;j++) {
                ImageView iv=imageView[i][j];
//              imageView[i][j].setOnClickListener(new View.OnClickListener() {
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
    //                  imageView[i][j].setImageResource(R.drawable.icon_cross);
                        int id  = v.getId();
                        for(int ii=0;ii<ROWS;ii++) for(int jj=0;jj<COLS;jj++)
                            if(turnIsAI==false & imageViewID[ii][jj]==id & cells[ii][jj]==-1) {
                                iv.setImageResource(R.drawable.icon_cross);
                                cells[ii][jj]=1;
                                turnIsAI=true;
                                btn.setText(infoBtn[1]);
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {Thread.sleep(3000);} catch (Exception e) {}

                                        turnIsAI=false;
                                        btn.setText(infoBtn[0]);
                                    }
                                }).start();
                                return;
                            }
/*                        for(int ii=0;ii<ROWS;ii++)
                           for(int jj=0;jj<COLS;jj++)
                               if(imageViewID[ii][jj]==id & cells[ii][jj]==1) {
                                      iv.setImageResource(R.drawable.icon_empty);
                                      cells[ii][jj]=-1;
                                      return;
                               }*/
                    }
                }
                );
            }
    }
}