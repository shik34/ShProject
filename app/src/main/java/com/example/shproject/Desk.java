package com.example.shproject;

class Cell{
    public int eox=-1;
}
public class Desk{
    public Cell [][] cells;
    Desk(int ROWS,int COLS){
        cells=new Cell[ROWS][COLS];
        for(int i=0;i<ROWS;i++)
            for(int j=0;j<COLS;j++){
                cells[i][j]=new Cell();
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
/*
    public int checkWin(int x){// x==0 check victory for ZERO, x==1 check victory for CROSS
        int sum;
        int[][] temp_state_cells =new int [3][3];
        int i;
        int j;
        for(i=0;i<3;i++)
            for(j=0;j<3;j++){
                if(x==0) temp_state_cells[i][j]=(cells[i][j].eox==0?1:0);
                if(x==1) temp_state_cells[i][j]=(cells[i][j].eox==1?1:0);
            }
        for(i=0;i<3;i++){
            sum=0;
            for(j=0;j<3;j++) sum+=temp_state_cells[i][j];
            if (sum==3) return x;
        }
        for(i=0;i<3;i++){
            sum=0;
            for(j=0;j<3;j++) sum+=temp_state_cells[j][i];
            if (sum==3) return x;
        }
        sum=0;
        for(i=0;i<3;i++){
            sum+=temp_state_cells[i][i];
            if (sum==3) return x;
        }
        sum=0;
        for(i=0;i<3;i++){
            sum+=temp_state_cells[i][2-i];
            if (sum==3) return x;
        }
        return -1;
    }
*/
}//end class Desk
