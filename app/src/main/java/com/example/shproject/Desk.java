package com.example.shproject;

import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class Desk{// игровое поле
    public int [][] cells;// -1 клетка пустая, 0 нолик, 1 крестик
    int ROWS,COLS;
    Desk(int ROWS,int COLS){
        this.ROWS=ROWS;
        this.COLS=COLS;
        cells=new int[ROWS][COLS];
        for(int i=0;i<ROWS;i++) for(int j=0;j<COLS;j++) cells[i][j]=-1;
    }

    public void turnAI(@NonNull String heuristic,int depth ,ImageView[][] imageView, SQLiteDatabase db){
        switch (heuristic){
            case "Горизонально последовательно":
                turnAI_horizontal(imageView,db);
                break;
            case "Случайно":
                turnAI_random(imageView,db);
                break;
            case "Следующий лучший":
                turnAI_nextBest(/*cells,*/imageView,db);
                break;
            case "Настоящая эвристика":
                turnAI_heuristic(imageView,depth,db);
                break;
        }
    }

    private void turnAI_horizontal(ImageView[][] imageView, SQLiteDatabase db){
        for(int i=0;i<ROWS;i++) for(int j=0;j<COLS;j++)
            if(cells[i][j]==-1){
                cells[i][j]=0;
                db.execSQL("UPDATE game SET column_"+j+"=0 WHERE row_id="+i+";");
                imageView[i][j].setImageResource(R.drawable.icon_zero);
                return;
            }
    }
    private void turnAI_random(ImageView[][] imageView, SQLiteDatabase db){
        int i,j;
        boolean todo=true;
        while (todo) {
            i=(int)(Math.random()*ROWS);
            j=(int)(Math.random()*ROWS);
            if (cells[i][j] == -1) {
                cells[i][j] = 0;
                db.execSQL("UPDATE game SET column_"+j+"=0 WHERE row_id="+i+";");
                imageView[i][j].setImageResource(R.drawable.icon_zero);
                todo=false;
            }
        }
    }
    private void turnAI_nextBest(/*int[][]cells,*/ImageView[][] imageView, SQLiteDatabase db){
        int i,j;
        int [][]temp_value=new int[ROWS][COLS];
        int max_value=-10000000,max_i=0,max_j=0;
        for(i=0;i<ROWS;i++) for(j=0;j<COLS;j++)
            if(cells[i][j]==-1){
                temp_value[i][j]= heuristicValue_for_ZeroOrCrossTurn(cells,i,j,0);
                if (max_value < temp_value[i][j]){
                    max_value=temp_value[i][j];
                    max_i=i;
                    max_j=j;
                }
            }
        cells[max_i][max_j] = 0;
        db.execSQL("UPDATE game SET column_"+max_j+"=0 WHERE row_id="+max_i+";");
        imageView[max_i][max_j].setImageResource(R.drawable.icon_zero);
    }

    void turnAI_heuristic(ImageView[][] imageView,int depth, SQLiteDatabase db){
        int temp_value,max_value=-1000000000,max_i=0,max_j=0;
        for(int i=0;i<ROWS;i++) for(int j=0;j<COLS;j++)
            if(cells[i][j]==-1) {
                temp_value=mini_max_heuristicValue(cells,i,j,depth,0);//получение значения в случае хода в i,j
                if (max_value < temp_value) {
                    max_value = temp_value;
                    max_i = i;
                    max_j = j;
                }
            }
        cells[max_i][max_j] = 0;
        db.execSQL("UPDATE game SET column_"+max_j+"=0 WHERE row_id="+max_i+";");
        imageView[max_i][max_j].setImageResource(R.drawable.icon_zero);
    }
    int mini_max_heuristicValue(int[][] cells, int i, int j, int depth,int zero_or_cross_Turn) {
        int heuristicValue=0;
        if(depth==1){
            heuristicValue = heuristicValue_for_ZeroOrCrossTurn(cells,i,j,zero_or_cross_Turn);
        }
        else{
            depth--;
            cells[i][j]=zero_or_cross_Turn;

            zero_or_cross_Turn=(zero_or_cross_Turn==0?1:0);
            int temp_value=0,max_value=-100000000,min_value=1000000000;
            for(int ii=0;ii<ROWS;ii++) for(int jj=0;jj<COLS;jj++){
                if(cells[ii][jj]==-1) temp_value=mini_max_heuristicValue(cells,ii,jj,depth,zero_or_cross_Turn);
                if (zero_or_cross_Turn==0 & max_value < temp_value) max_value=temp_value;
                if (zero_or_cross_Turn==1 & min_value > temp_value) min_value=temp_value;
            }
            if (zero_or_cross_Turn==0) heuristicValue = max_value;
            if (zero_or_cross_Turn==1) heuristicValue = min_value;
            cells[i][j]=-1;
        }
        return heuristicValue;
    }

    private int heuristicValue_for_ZeroOrCrossTurn(int[][]cells, int i, int j, int zero_or_cross_Turn){// - if will be step to i,j by zero_or_cross
        if(zero_or_cross_Turn==0){
            cells[i][j]=0;
            int v= sum_of_winLines_for_ZeroOrCross(cells,0)- sum_of_winLines_for_ZeroOrCross(cells,1);
            cells[i][j]=-1;
            return v;
        }
        else{
            cells[i][j]=1;
            int v= sum_of_winLines_for_ZeroOrCross(cells,0)- sum_of_winLines_for_ZeroOrCross(cells,1);
            cells[i][j]=-1;
            return v;
        }
    }

    private int sum_of_winLines_for_ZeroOrCross(int[][]cells, int zero_or_cross){//zero_or_cross==0 - for zeros, zero_or_cross==1 - for cross
        int i,j;
        int [][] temp_cells=new int[ROWS][COLS];
        if(zero_or_cross==1){
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
        return sum_of_winLines_for_1(temp_cells);
    }

    private int sum_of_winLines_for_1(int[][]cells){
        int sum=0,temp_sum;
        for (int i = 0; i <ROWS ; i++) {//horizontal
            temp_sum=0;
            for (int j = 0; j < COLS; j++) temp_sum+=cells[i][j];
            if(temp_sum==ROWS) sum++;
        }
        for (int i = 0; i <ROWS ; i++) {//vertical
            temp_sum=0;
            for (int j = 0; j < COLS; j++) temp_sum+=cells[j][i];
            if(temp_sum==ROWS) sum++;
        }
        temp_sum=0;
        for (int i = 0; i <ROWS ; i++) {//diagonal
            temp_sum+=cells[i][i];
        }
        if(temp_sum==ROWS) sum++;
        temp_sum=0;
        for (int i = 0; i <ROWS ; i++) {//diagonal
            temp_sum+=cells[i][ROWS-1-i];
        }
        if(temp_sum==ROWS) sum++;

        return sum;
    }

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
    public boolean checkDeskFull(){
        for(int i=0;i<ROWS;i++)
            for(int j=0;j<COLS;j++)
                if(cells[i][j]==-1) return false;
        return true;
    }
}//end class Desk
