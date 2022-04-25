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

        String heuristic    = arguments.getString("heuristic");

        String firstPlayer  = arguments.getString("firstPlayer");
        switch (firstPlayer) {
            case "Я":
                btn.setText(infoBtn[0]);
                break;
            case "Искусственный интеллект":
                btn.setText(infoBtn[1]);
                break;
        }
//build grid
        ImageView [][] imageView =new ImageView[ROWS][COLS];
        TableLayout tblLayout = null;
        tblLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < ROWS; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(500,500));
            for (int j = 0; j < COLS; j++) {
                // получение изображения полки
                imageView[i][j] = new ImageView(this);
                imageView[i][j].setImageResource(R.drawable.icon_empty);
                tableRow.addView(imageView[i][j], j);
            }
            tblLayout.addView(tableRow, i);
        }



/*

        tv=findViewById(R.id.textView);
        String str = getIntent().getStringExtra("et");
        tv.setText(str);
        */
/*imageView[1-1]=findViewById(R.id.imageView1);

        imageView[4-1]=findViewById(R.id.imageView4);
        imageView[5-1]=findViewById(R.id.imageView5);
        imageView[6-1]=findViewById(R.id.imageView6);*//*

        imageViews = new ArrayList<ImageView>();
        int i=0;
        for(int id : IMAGE_VIEW_NUMBER) {
            ImageView imageView = (ImageView)findViewById(id);
//try{
            imageView_ID[i]=imageView.getId();
            i++;
//}catch (Exception e){Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_LONG).show();}
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (cellNumber = 0; cellNumber < 9; cellNumber++)//получение номера cellNumber клетки, на которую кликнули
                        if (v.getId() == imageView_ID[cellNumber]) break;
                    if (keyFinish[cellNumber] == false) {//если клетка пустая
//                        if (keyTemp[cellNumber] == false) {//если клетка пустая
                            if(playerNumber==0) imageView.setImageResource(R.drawable.o);
                            else imageView.setImageResource(R.drawable.x);
//                          keyTemp[cellNumber] = true;  //теперь клетка занята
                            keyFinish[cellNumber]=true;//теперь клетка занята
//                    }
*/
/*                        else {             //если клетка занята
                            imageView.setImageDrawable(null);
                            keyTemp[cellNumber] = false; //теперь клетка пустая
                        }*//*

                        //for(int j=0;j<9;j++){tv.setText(tv.getText().toString()+imageView_ID[j]%10);}
                        desk.setCellStateByIMAGE_VIEW_NUMBER(v.getId(), playerNumber);
                        checkVictory(playerNumber);
*/
/*                        turn_1();
                        switch (desk.checkWin(1)) {
                            case 0:
                                tv.setText("OOOOOOOOOOOOOOOOOOOO");
                                break;
                            case 1:
                                tv.setText("XXXXXXXXXXXXXXXXXXXX");
                                break;
                        }*//*

                    }
                }
            });
            imageViews.add(imageView);
        }
*/    }














}