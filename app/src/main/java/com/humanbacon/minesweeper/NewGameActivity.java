package com.humanbacon.minesweeper;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class NewGameActivity extends Activity {

    final Button[][] tableCells = new Button[9][9];

    ClassicGame classicGame = new ClassicGame();
    ClassicGame.GameBoard gameBoard = classicGame.getGameBoard();

    private class CellOnClickListener implements Button.OnClickListener {
        int x;
        int y;
        public CellOnClickListener (int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public void onClick(View v) {

            if (gameBoard.getCell(x, y).select()) {
                for (int j = 0; j < 9; j++) {
                    for (int i = 0; i < 9; i++) {
                        if (gameBoard.getCell(i, j).isKnown()) {
                            int content = gameBoard.getCell(i, j).getContent();
                            if (content == 9) {
                                tableCells[i][j].setText("x");
                            } else {
                                tableCells[i][j].setText("" + content);
                            }
                        }
                    }
                }
            }else {
                if(classicGame.checkWin()){
                    ((TextView)findViewById(R.id.winView)).setText("WIN");
                }else{
                    ((TextView)findViewById(R.id.winView)).setText("LOSE");
                }
                endGame();
            }
        }
    }

    private class CellOnLongClickListener implements Button.OnLongClickListener{
        int x;
        int y;

        public CellOnLongClickListener(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean onLongClick(View v) {
            if(gameBoard.getCell(x, y).toggleFlag()){
                if(gameBoard.getCell(x, y).getMark() == ClassicGame.FLAG){
                    tableCells[x][y].setText("_");
                }else{
                    tableCells[x][y].setText("");
                }
            }else{
                ((TextView)findViewById(R.id.winView)).setText("WIN");
                endGame();
            }
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        RelativeLayout contentView = (RelativeLayout) findViewById(R.id.layout_new_game);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableLayout tableLayout = new TableLayout(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        TableRow[] tableRows = new TableRow[9];
        for (int j = 0; j < tableCells.length; j++) {
            TableRow row = new TableRow(this);
            for (int i = 0; i < tableCells[j].length; i++) {
                tableCells[i][j] = new Button(this);
                tableCells[i][j].setLayoutParams(new TableRow.LayoutParams(110, 110));
                tableCells[i][j].setText("");
                tableCells[i][j].setOnClickListener(new CellOnClickListener(i, j));
                tableCells[i][j].setOnLongClickListener(new CellOnLongClickListener(i, j));
                row.addView(tableCells[i][j]);
            }
            row.setLayoutParams(tableParams);
            tableLayout.addView(row);
        }
        ((RelativeLayout)findViewById(R.id.gameView)).addView(tableLayout);
    }

    public void endGame(){
        for(int j = 0; j < 9; j++){
            for(int i = 0; i < 9; i++){
                gameBoard.getCell(i, j).setKnown();
                int content = gameBoard.getCell(i, j).getContent();
                if (content == 9) {
                    tableCells[i][j].setText("x");
                } else {
                    tableCells[i][j].setText("" + content);
                }
            }
        }
    }
}
