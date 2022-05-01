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
                turnAI_horizontal(imageView);
                break;
            case "Случайно":
                turnAI_random(imageView);
                break;
            case "Следующий лучший":
                turnAI_nextBest(imageView);
                break;
            case "Настоящая эвристика":
                turnAI_heuristic(imageView,3);
                break;
        }
    }
    void turnAI_horizontal(ImageView[][] imageView){
        for(int i=0;i<ROWS;i++) for(int j=0;j<COLS;j++)
            if(cells[i][j]==-1){
                cells[i][j]=0;
                imageView[i][j].setImageResource(R.drawable.icon_zero);
                return;
            }
    }
    void turnAI_random(ImageView[][] imageView){
        int i,j;
        while (true) {
            i=(int)(Math.random()*ROWS);
            j=(int)(Math.random()*ROWS);
            if (cells[i][j] == -1) {
                cells[i][j] = 0;
                imageView[i][j].setImageResource(R.drawable.icon_zero);
                return;
            }
        }
    }
    void turnAI_nextBest(ImageView[][] imageView){
        int i,j;
        int temp_value,max_value=0,max_i=0,max_j=0;
        for(i=0;i<ROWS;i++) for(j=0;j<COLS;j++){
            temp_value=heuristicValue();
            if (max_value < temp_value){
                max_value=temp_value;
                max_i=i;
                max_j=j;
            }
        }
        imageView[max_i][max_j].setImageResource(R.drawable.icon_zero);
    }
    void turnAI_heuristic(ImageView[][] imageView,int depth){
        int i,j;
        int temp_value=0,max_value=0,max_i=0,max_j=0;
        for(i=0;i<ROWS;i++) for(j=0;j<COLS;j++){
            if(cells[i][j]==-1) temp_value=mini_max_heuristicValue(cells,i,j,depth);
            if (max_value < temp_value){
                max_value=temp_value;
                max_i=i;
                max_j=j;
            }
        }
        imageView[max_i][max_j].setImageResource(R.drawable.icon_zero);
    }

    private int mini_max_heuristicValue(int[][] cells, int i, int j, int n) {
        int h=0;

        int new_cells[][]=new int[ROWS][COLS];
        return h;
    }


    int heuristicValue(){
        return value_of_winLine(0)-value_of_winLine(1);
    }
    int value_of_winLine(int x){//x==0 - for zeros, x==1 - for cross
        int i,j;
        int [][] temp_cells=new int[ROWS][COLS];
        if(x==1){
        for(i=0;i<ROWS;i++) for(j=0;j<COLS;j++)
                if (cells[i][j]==-1) temp_cells[i][j]=1;
                else                 temp_cells[i][j]=cells[i][j];
        }
        else
            for(i=0;i<ROWS;i++) for(j=0;j<COLS;j++)
                switch (cells[i][j]) {
                    case -1:
                        temp_cells[i][j] = 1;
                    case 0:
                        temp_cells[i][j] = 1;
                    case 1:
                        temp_cells[i][j] = 0;
                }
        return checkSums(temp_cells);
    }
    int checkSums(int[][]cells){
        int sum=0,temp_sum;
        for (int i = 0; i <ROWS ; i++) {//horizontal
            temp_sum=0;
            for (int j = 0; j < COLS; j++) temp_sum=cells[i][j];
            if(temp_sum==ROWS) sum++;
        }
        for (int i = 0; i <ROWS ; i++) {//vertical
            temp_sum=0;
            for (int j = 0; j < COLS; j++) temp_sum=cells[j][i];
            if(temp_sum==ROWS) sum++;
        }
        temp_sum=0;
        for (int i = 0; i <ROWS ; i++) {//diagonal
            temp_sum=cells[i][i];
        }
        if(temp_sum==ROWS) sum++;
        temp_sum=0;
        for (int i = 0; i <ROWS ; i++) {//diagonal
            temp_sum=cells[i][ROWS-1-i];
        }
        if(temp_sum==ROWS) sum++;

        return sum;
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
            sum+=temp_state_cells[i][ROWS-1-i];
            if (sum==sum_for_win) return true;
        }
        return false;
    }
}//end class Desk
