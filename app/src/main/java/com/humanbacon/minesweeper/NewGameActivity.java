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
        public void onClick(View cell) {
            ((Button)cell).setText("" + gameBoard.getCell(x, y).getContent());
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
                row.addView(tableCells[i][j]);
            }
            row.setLayoutParams(tableParams);
            tableLayout.addView(row);
        }
        contentView.addView(tableLayout);
    }
}
