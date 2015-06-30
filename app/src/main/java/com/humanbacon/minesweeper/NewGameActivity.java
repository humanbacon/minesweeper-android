package com.humanbacon.minesweeper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class NewGameActivity extends Activity {

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

        Button[][] tableCells = new Button[8][8];

        ClassicGame classicGame = new ClassicGame();
        ClassicGame.GameBoard gameBoard = classicGame.getGameBoard();

        TableRow[] tableRows = new TableRow[8];
        for (int j = 0; j < tableCells.length; j++) {
            TableRow row = new TableRow(this);
            for (int i = 0; i < tableCells[j].length; i++) {
                tableCells[i][j] = new Button(this);
                tableCells[i][j].setLayoutParams(new TableRow.LayoutParams(120, 120));
                tableCells[i][j].setText("" + gameBoard.getCell(i, j).getContent());
                row.addView(tableCells[i][j]);
            }
            row.setLayoutParams(tableParams);
            tableLayout.addView(row);
        }
        contentView.addView(tableLayout);
    }
}
