package com.example.shproject;

import android.widget.ImageView;

public class Desk{// игровое поле
    public int [][] cells;// -1 клетка пустая, 0 нолик, 1 крестик
    int ROWS,COLS;
    Desk(int ROWS,int COLS){
        this.ROWS=ROWS;
        this.COLS=COLS;
        cells=new int[ROWS][COLS];
        for(int i=0;i<ROWS;i++) for(int j=0;j<COLS;j++) cells[i][j]=-1;
    }
    public void turnAI(String heuristic, ImageView[][] imageView){
        switch (heuristic){
            case "Горизонально последовательно":
                for(int i=0;i<ROWS;i++) for(int j=0;j<COLS;j++)
                    if(cells[i][j]==-1){
                        cells[i][j]=0;
                        imageView[i][j].setImageResource(R.drawable.icon_zero);
                        return;
                    }
                break;
        }
    }
/*
    public void setCellStateByIMAGE_VIEW_NUMBER(int image_view_number, int x){//x==0 - нолик, х==1 - крестик
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(cells[i][j].image_view_number == image_view_number) {
                    cells[i][j].eox=(x==0?0:1);
                }
    }
*/
    public boolean checkWin(int x){// x==0 check victory for ZERO, x==1 check victory for CROSS
        int sum=0,sum_for_win=ROWS;
        int[][] temp_state_cells =new int [ROWS][COLS];
        int i,j;
        for(i=0;i<ROWS;i++)
            for(j=0;j<COLS;j++){
                if(x==0) temp_state_cells[i][j]=(cells[i][j]==0?1:0);
                if(x==1) temp_state_cells[i][j]=(cells[i][j]==1?1:0);
            }
        for(i=0;i<ROWS;i++){//победа по горизонтали
            sum=0;
            for(j=0;j<COLS;j++) sum+=temp_state_cells[i][j];
            if (sum==sum_for_win) return true;
        }
        for(i=0;i<COLS;i++){//победа по вертикали
            sum=0;
            for(j=0;j<ROWS;j++) sum+=temp_state_cells[j][i];
            if (sum==sum_for_win) return true;
        }
        sum=0;
        for(i=0;i<ROWS;i++){//победа по диагонали
            sum+=temp_state_cells[i][i];
            if (sum==sum_for_win) return true;
        }
        sum=0;
        for(i=0;i<ROWS;i++){//победа по диагонали
            sum+=temp_state_cells[i][2-i];
            if (sum==sum_for_win) return true;
        }
        return false;
    }
}//end class Desk
