package com.example.shproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowActivity extends AppCompatActivity {
    SQLiteDatabase db;
    TextView button_for_text, tv_show;
    Button btn_show;
    EditText et_input_show;
    Desk desk;
    ImageView [][] imageView;
    int i,j,ROWS=0,COLS;
@Override//onCreate ********************************************************************************
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

    tv_show        =  findViewById(R.id.tv_SHOW);
    et_input_show  =  findViewById(R.id.et_input_SHOW);
    btn_show         =  findViewById(R.id.btn_show);
    et_input_show=findViewById(R.id.et_input_SHOW);

    SharedPreferences sharedPreferences = this.getSharedPreferences("visible_show",MODE_PRIVATE);

    Integer visibility = sharedPreferences.getInt("VISIBILITY_SHOW", 1);
    String teble_name = sharedPreferences.getString("TABLE_NAME_SHOW", "");
    if(visibility==0){
        tv_show.setVisibility(View.INVISIBLE);
        et_input_show.setVisibility(View.INVISIBLE);
        btn_show.setVisibility(View.INVISIBLE);
        show(teble_name);
    }
    else{
        tv_show.setVisibility(View.VISIBLE);
        et_input_show.setVisibility(View.VISIBLE);
        btn_show.setVisibility(View.VISIBLE);
    }
    et_input_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input_show.setText("");
            }
        });
    }
//onCreate-------------------------------------------------------------------------------------
//onClick ******************************************************************************************
public void onClick_show(View v) {

        String name=et_input_show.getText().toString();
        boolean it_is_good=show(name);
if(it_is_good){
        tv_show.setVisibility(View.INVISIBLE);
        et_input_show.setVisibility(View.INVISIBLE);
        btn_show.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPreferences = this.getSharedPreferences("visible_show",MODE_PRIVATE);
//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("VISIBILITY_SHOW", 0);
        editor.putString("TABLE_NAME_SHOW", name);
        editor.commit();
}
    }
//onClick--------------------------------------------------------------------------------------
//show *********************************************************************************************
    private boolean show(String name){
    db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
    Cursor query = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+name+"'", null);
    int ii=query.getCount();
    if(ii==0){
        et_input_show.setText("такого имени нет - введите другое");
        query.close();
        db.close();
        return false;
    }
    query = db.rawQuery("SELECT * FROM "+name, null);
    ROWS=5;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    COLS = ROWS;
    int [][] full_cells=new int [ROWS][COLS];
    for(int i=0;i<ROWS;i++) {
        query.moveToNext();
        for (int j = 0; j < COLS; j++) {
            full_cells[i][j] = query.getInt(j+1);
        }
    }
//find number of ROWS ******************************************************************************
    for(int i=4; i>=0; i--) {
        int sum=0;
        for (int j = 0; j <= 4; j++) sum+=full_cells[i][j];
        if(sum==-5) ROWS--;
        else break;
    }
    for(int i=4; i>=0; i--) {
        int sum=0;
        for (int j = 0; j <= 4; j++) sum+=full_cells[j][i];
        if(sum==-5) COLS--;
        else break;
    }
    ROWS=Math.max(ROWS,COLS);
    COLS = ROWS;
//--------------------------------------------------------------------------------------------------
    query.close();
    db.close();
    desk=new Desk(ROWS,COLS);
    for(int i=0;i<ROWS;i++) {
        for (int j = 0; j < COLS; j++) {
            desk.cells[i][j] = full_cells[i][j];
        }
    }
    imageView  =new ImageView[ROWS][COLS];
    TableLayout tblLayout         =null;
    tblLayout                     =(TableLayout) findViewById(R.id.tableLayout);
    for (int i = 0; i < ROWS; i++) {
        TableRow tableRow = new TableRow(ShowActivity.this);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(500,500));
        for (int j = 0; j < COLS; j++) {
            imageView[i][j] = new ImageView(ShowActivity.this);
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
    return true;
}
//show --------------------------------------------------------------------------------------------
    public void onClick_continue_SHOW(View v){
        Intent intent = new Intent(ShowActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void onClick_exit_SHOW(View v){
        finishAffinity();
        System.exit(0);
    }
}