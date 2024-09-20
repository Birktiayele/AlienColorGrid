package com.example.proj3_alientitles;
import java.io.*;

public class Board implements Serializable{
    int [][] cells;
    int rows;
    int columns;
    ;
    public Board(int rows, int columns, int defaultValue){
        this.rows=rows;
        this.columns=columns;
        cells=new int[rows][columns];
        if(0<= defaultValue && defaultValue <4){
            resetSingleColor(defaultValue);
        }else{
            resetSingleColor(0);
        }
    }

    public void resetSingleColor(int colorIndex){
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                cells[i][j]=colorIndex;
            }
        }
    }

    public void resetRandomColor(){
        // pick random (i,j) in range (rows,columns
        //call
    }
    /**
     *
     * @param rowIndex
     * @param columnIndex
     */
    public void clickedAt(int rowIndex, int columnIndex) {
        for (int i = 0; i < rows; i++) {
            cells[i][columnIndex] = (cells[i][columnIndex] +1) % 4;
        }
        for (int j = 0; j < columns; j++) {
            if (j != columnIndex) {
                cells[rowIndex][j] = (cells[rowIndex][j] + 1) % 4;
            }
        }
    }

    public void undoClickedAt(int rowIndex, int columnIndex) {
        for (int i = 0; i < rows; i++) {
            cells[i][columnIndex] = (cells[i][columnIndex] +3) % 4;
        }
        for (int j = 0; j < columns; j++) {
            if (j != columnIndex) {
                cells[rowIndex][j] = (cells[rowIndex][j] + 3) % 4;
            }
        }
    }

    public void save2File(File file) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new java.io.FileOutputStream(file));
            out.writeObject(cells);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
        public void readFromFile(File file){
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new java.io.FileInputStream(file));
                int[][] aa = (int[][]) (in.readObject());
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        cells[i][j] = aa[i][j];
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

}
