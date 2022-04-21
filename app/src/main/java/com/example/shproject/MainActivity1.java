package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity {
//    ImageView imageView[]=new ImageView[9];
    private final int[] IMAGE_VIEW_NUMBER = {
            R.id.imageView1,
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView5,
            R.id.imageView6,
            R.id.imageView7,
            R.id.imageView8,
            R.id.imageView9
    };
    private int[] imageView_ID=new int[9];
    int cellNumber;
    private boolean[] keyFinish=new boolean[9];

    TextView tv;
    private List<ImageView> imageViews;
//    private boolean[] keyTemp =new boolean[9];

    int playerNumber=0;

    Desk desk = new Desk(IMAGE_VIEW_NUMBER);

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
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        tv=findViewById(R.id.textView);

        String str = getIntent().getStringExtra("et");
        tv.setText(str);
        /*imageView[1-1]=findViewById(R.id.imageView1);

        imageView[4-1]=findViewById(R.id.imageView4);
        imageView[5-1]=findViewById(R.id.imageView5);
        imageView[6-1]=findViewById(R.id.imageView6);*/
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
/*                        else {             //если клетка занята
                            imageView.setImageDrawable(null);
                            keyTemp[cellNumber] = false; //теперь клетка пустая
                        }*/
                        //for(int j=0;j<9;j++){tv.setText(tv.getText().toString()+imageView_ID[j]%10);}
                        desk.setCellStateByIMAGE_VIEW_NUMBER(v.getId(), playerNumber);
                        checkVictory(playerNumber);
/*                        turn_1();
                        switch (desk.checkWin(1)) {
                            case 0:
                                tv.setText("OOOOOOOOOOOOOOOOOOOO");
                                break;
                            case 1:
                                tv.setText("XXXXXXXXXXXXXXXXXXXX");
                                break;
                        }*/
                    }
                }
            });
            imageViews.add(imageView);
        }
    }

    public void cl(View v){
        Toast.makeText(this,Integer.toString(v.getId()),Toast.LENGTH_LONG).show();
//        Integer.toString(v.getId())
 //       v.getId
//        ImageView imageView = findViewById(R.id.imageView5);
//        imageView.setImageResource(R.drawable.o);
    }
    public void turn(View v){
//        keyFinish[cellNumber]=keyTemp[cellNumber];
        if(playerNumber==0) {
            playerNumber=1;
            tv.setText("ход Х");
        }
        else {
            playerNumber=0;
            tv.setText("ход O");
        }
    }
    public void turn_AI(View v){
        int i,l;
        for(i=0;i<9;i++) {
            if(keyFinish[i]==false) break;
        }
        keyFinish[i]=true;
        for (l=0;l<9;l++) {
            if (imageViews.get(l).getId()==imageView_ID[i]) break;
        }
        imageViews.get(l).setImageResource(R.drawable.x);
        desk.setCellStateByIMAGE_VIEW_NUMBER(imageViews.get(l).getId(), 1);
        checkVictory(1);
    }
}