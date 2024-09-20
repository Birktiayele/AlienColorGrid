package com.example.proj3_alientitles;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;


public class alienTitles_Controller {
    public final int ROWS =7, COLUMNS = 7;
    Board board = new Board(ROWS, COLUMNS, 0);
    ArrayList <Pair> clickHistory = new ArrayList<>();
    ArrayList <Pair> undoHistory = new ArrayList<>();

    Color[] colors ={ Color.RED, Color.BLUE, Color.GREEN, Color.PURPLE};
    private String[] styleColors = {
            "-fx-background-color: Red",
            "-fx-background-color: Green",
            "-fx-background-color: Blue",
            "-fx-background-color: Purple",
    };
    @FXML
    private MenuItem menuItemRed,menuItemGreen, menuItemBlue;
    @FXML
    private MenuItem  menuItemRandom, menuItemPurple;

    private MenuItem[] items;

    private Button[][] btns;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button b00,b01,b02,b03,b04,b05,b06,
            b10,b11,b12,b13,b14,b15,b16,
            b20,b21,b22,b23,b24,b25,b26,
            b30,b31,b32,b33,b34,b35,b36,
            b40,b41,b42,b43,b44,b45,b46,
            b50,b51,b52,b53,b54,b55,b56,
            b60,b61,b62,b63,b64,b65,b66;

    @FXML
    public void initialize(){
            btns = new Button[][]{
                    { b00,b01,b02,b03,b04,b05,b06},
            {b10,b11,b12,b13,b14,b15,b16},
            {b20,b21,b22,b23,b24,b25,b26},
            {b30,b31,b32,b33,b34,b35,b36},
            {b40,b41,b42,b43,b44,b45,b46},
            {b50,b51,b52,b53,b54,b55,b56},
            {b60,b61,b62,b63,b64,b65,b66}};

            items = new MenuItem[]{
                    menuItemRed,menuItemGreen, menuItemBlue, menuItemPurple,  menuItemRandom
            };
        repaintAllButtons();

    }

    private void repaintAllButtons(){
        for(int i=0; i<ROWS;i++){
            for (int j=0; j<COLUMNS;j++){
                //b.setBackground(colors[0]);
                final int COLOR_INDEX = board.cells[i][j];
                btns[i][j].setStyle(styleColors[COLOR_INDEX]);
            }
        }
    }

    private void repaint1Row1ColumnButtons(int rowIndex, int columnIndex){
        for(int i=0; i<ROWS;i++) {
            final int COLOR_INDEX = board.cells[i][columnIndex];
            btns[i][columnIndex].setStyle(styleColors[COLOR_INDEX]);
        }
        for (int j=0; j<COLUMNS;j++){
                if(j != columnIndex) {
                    final int COLOR_INDX = board.cells[rowIndex][j];
                    btns[rowIndex][j].setStyle(styleColors[COLOR_INDX]);
                }
        }
    }
    public void exit() {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onBtnClick(ActionEvent e){
      //  //b.setStyle("-fx-background-color: Red");
        Button clickedButton = (Button) e.getSource();
        Pair pair = getRowColumnIndex(clickedButton);
        if(pair != null){
            clickHistory.add(pair);
            //set colors for row: pair.x
            //set colors for column pair.y
            board.clickedAt(pair.x, pair.y);
            //repaintAllButtons();
            repaint1Row1ColumnButtons(pair.x, pair.y);
        }
    }

    @FXML
    protected void onSavePattern(ActionEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage)gridPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if(file!=null){
            board.save2File(file);
        }

    }

    @FXML
    protected void onLoadPattern(ActionEvent e){
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage)gridPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null){
            board.readFromFile(file);
            repaintAllButtons();
            clickHistory.clear();
        }
    }

    @FXML
    protected void onNewGame(ActionEvent e){
        MenuItem srcItem = (MenuItem) e.getSource();
        int i = -1;
        for(i=0; i<items.length; i++){
            if(srcItem == items[i]){
                break;
            }
        }
        if (0<=i && i<4){// new single color  game
            board.resetSingleColor(i);
        }else if (i==4){ //random game
            board.resetRandomColor();
        }
        clickHistory.clear();
        repaintAllButtons();
    }

    @FXML
    protected void onUndo(ActionEvent e){
               final int N = clickHistory.size();
               if(N >0){
                   Pair p = clickHistory.remove(N-1);
                   board.undoClickedAt(p.x, p.y);
                   undoHistory.add(p);
                   repaint1Row1ColumnButtons(p.x, p.y);
               }
    }

    @FXML
    protected void onRedo(ActionEvent e){
        final int N = undoHistory.size();
        if(N >0){
            Pair p = undoHistory.remove(N-1);
            board.clickedAt(p.x, p.y);
            clickHistory.add(p);
            repaint1Row1ColumnButtons(p.x, p.y);
        }
    }


    @FXML
    protected void onExit(ActionEvent e){
         exit();
    }


    /**
     * @param button: the button a user clicked
     * @return a pair for the row and column indices of the button
     */
    private Pair getRowColumnIndex(Button button){
        for(int i=0; i < btns.length; i++){
            for(int j=0; j< btns[i].length; j++){
            if(button == btns[i][j]){
                return new Pair(i,j);
            }
        }
    }
        return null;
     }


}