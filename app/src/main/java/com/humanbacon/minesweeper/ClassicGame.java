package com.humanbacon.minesweeper;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class ClassicGame {
	//mark
    public static final int UNMARKED = 0;
    public static final int FLAG = 1;
	public static final int QUESTION = 2;
	//content
	public static final int MINE = 9;

	protected int width;
	protected int height;
	protected int mineNo;	

	protected GameTimer gameTimer;
    protected GameBoard gameBoard;

	public ClassicGame(){
		gameTimer = new GameTimer();
        gameBoard = new GameBoard();
	}

	class GameTimer{

		private int time;
		private boolean running;
		private Timer timer;
		private TimerTask timerTask;

		public GameTimer(){
			time = 0;
			running = false;
			timer = new Timer();
		}

		public void start(){
			if(!running){
				timer = new Timer();
				time = 0;
				timerTask = new TimerTask(){
					public void run(){
						time += 10;
					}
				};
				timer.scheduleAtFixedRate(timerTask, 0, 10);
				running = true;
			}else{
				System.err.println("Alerady running");
			}
		}

		public void pause(){
			if(running){
				timer.cancel();
				timer.purge();
				timerTask.cancel();
				running = false;
			}else{
				System.err.println("Not running");
			}
		}

		public void resume(){
			if(!running){
				timer = new Timer();
				timerTask = new TimerTask(){
					public void run(){
						time += 10;
					}
				};
				timer.scheduleAtFixedRate(timerTask, 0, 10);
				running = true;
			}else{
				System.err.println("Alerady running");
			}
		}

		public void stop(){
			if(running){
				timer.cancel();
				timer.purge();
				timerTask.cancel();
				time = 0;				
				running = false;
			}else{
				System.err.println("Not running");
			}
		}

		public int getTime(){
			return time;
		}
	}

	class GameBoard{
		private int width;
		private int height;
		private int mineCount;
        private int flagCount;
        private int correctFlagCount;
        private int questionCount;
        private int knowonCount;
		private Cell[][] board;

        public GameBoard(int w, int h, int m){
            width = w;
            height = h;
            mineCount = m;
            flagCount = 0;
            correctFlagCount = 0;
            questionCount = 0;
            board = new Cell[width][height];
            for(int j = 0; j < height; j++){
                for(int i = 0; i < width; i++){
                    board[i][j] = new Cell(i, j);
                }
            }
            Random rand = new Random();
            for(int assignedMineNum = 0; assignedMineNum < m;) {
                int x = rand.nextInt(width);
                int y = rand.nextInt(height);
                if(board[x][y].getContent() == 0){
                    board[x][y].setContent(MINE);
                    assignedMineNum++;
                }
            }
            for(int j = 0; j < height; j++){
                for(int i = 0; i < width; i++){
                    if(board[i][j].getContent() == MINE){
                        Cell[] neighbors = board[i][j].getNeighbors();
                        for(Cell cell : neighbors){
                            if(cell.getContent() != MINE){
                                cell.setContent(cell.getContent() + 1);
                            }
                        }
                    }
                }
            }
        }

        public GameBoard(){
            this(9, 9, 10);
        }

        class Cell{
            private boolean isKnown;
            private int mark;
            private int content;
            private int x;
            private int y;
            public Cell(int x, int y){
                this.x = x;
                this.y = y;
                this.isKnown = false;
                this.mark = UNMARKED;
                content = 0;
            }

            public void setKnown(){
                isKnown = true;
            }

            public void setContent(int c){
                if(c >= 0 && c <= 9){
                    content = c;
                }else{
                    System.err.println("Ivalid content");
                }
            }

            public int getContent(){
                return content;
            }

            public boolean isKnown(){
                return isKnown;
            }

            public int getX(){
                return x;
            }

            public int getY(){
                return y;
            }

            public Cell getN(){
                if(y != 0){
                    return board[x][y - 1];
                }else{
                    return null;
                }
            }

            public Cell getE(){
                if(x != width - 1){
                    return board[x + 1][y];
                }else{
                    return null;
                }
            }

            public Cell getW(){
                if(x != 0){
                    return board[x - 1][y];
                }else{
                    return null;
                }
            }

            public Cell getS(){
                if(y != height - 1){
                    return board[x][y + 1];
                }else{
                    return null;
                }
            }

            public Cell getNW(){
                if(x != 0 && y != 0){
                    return board[x - 1][y - 1];
                }else {
                    return null;
                }
            }

            public Cell getNE(){
                if(x != width - 1 && y != 0){
                    return board[x + 1][y - 1];
                }else {
                    return null;
                }
            }

            public Cell getSE(){
                if(x != width - 1 && y != height - 1){
                    return board[x + 1][y + 1];
                }else {
                    return null;
                }
            }

            public Cell getSW(){
                if(x != 0 && y != height - 1){
                    return board[x - 1][y + 1];
                }else {
                    return null;
                }
            }

            public Cell[] getNeighbors(){
                Cell[] cells;
                if(x == 0 && y == 0){
                    return new Cell[] {getE(), getSE(), getS()};
                }else if(x == width - 1 && y == 0){
                    return new Cell[] {getS(), getSW(), getW()};
                }else if(x == width - 1 && y == height - 1){
                    return new Cell[] {getW(), getNW(), getN()};
                }else if(x == 0 && y == height - 1){
                    return new Cell[] {getN(), getNE(), getE()};
                }else if(y == 0){
                    return new Cell[] {getE(), getSE(), getS(), getSW(), getW()};
                }else if(x == width - 1){
                    return new Cell[] {getS(), getSW(), getW(), getNW(), getN()};
                }else if(y == height - 1){
                    return new Cell[] {getW(), getNW(), getN(), getNE(), getE()};
                }else if (x == 0) {
                    return new Cell[] {getN(), getNE(), getE(), getSE(), getS()};
                }else{
                    return new Cell[] {getN(), getNE(), getE(), getSE(), getS(), getSW(), getW(), getNW()};
                }
            }

            public int getMark(){
                return mark;
            }

            //tfalse == end game : true == continue
            public boolean select(){
                if(this.getContent() == MINE) {
                    return false;
                }else{
                    LinkedList<Cell> Q = new LinkedList<Cell>();
                    Q.add(this);
                    this.setKnown();
                    while (Q.size() != 0) {
                        Cell v = Q.remove();
                        if (this.getContent() == 0) {
                            for (Cell w : v.getNeighbors()) {
                                if (w.getMark() != FLAG && w.getContent() != 9) {
                                    if (!w.isKnown() && w.getContent() == 0) {
                                        Q.add(w);
                                        w.setKnown();
                                    } else if (w.getContent() != 0) {
                                        w.setKnown();
                                    }
                                    knowonCount++;
                                }
                            }
                        }
                    }
                    return true;
                }
            }

            public boolean toggleFlag(){
                if(!this.isKnown()){
                    if(this.mark != FLAG){
                        this.mark = FLAG;
                        flagCount++;
                        if(this.getContent() == MINE){
                            correctFlagCount++;
                            if(correctFlagCount == mineCount){
                                return false;
                            }
                        }
                    }else{
                        this.mark = UNMARKED;
                        flagCount--;
                        if(this.getContent() == MINE){
                            correctFlagCount--;
                        }
                    }
                }
                return true;
            }
        }

        public Cell getCell(int x, int y){
            return board[x][y];
        }
	}



    public GameTimer getGameTimer(){
        return gameTimer;
    }

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public boolean checkWin(){
        if(gameBoard.correctFlagCount == gameBoard.mineCount || gameBoard.knowonCount == gameBoard.width * gameBoard.height - gameBoard.mineCount){
            return true;
        }else{
            return false;
        }
    }

	public static void main(String[] args){
		ClassicGame hihi = new ClassicGame();
		Scanner s = new Scanner(System.in);
		String input;
		do{
			input = s.next();
			if(input.equals("show")){
				System.out.println(hihi.gameTimer.getTime());
			}else if(input.equals("start")){
				hihi.gameTimer.start();
			}else if(input.equals("resume")){
				hihi.gameTimer.resume();
			}else if(input.equals("pause")){
				hihi.gameTimer.pause();
			}else if(input.equals("stop")){
				hihi.gameTimer.stop();
			}
		}while(!input.equals("quit"));
		System.exit(0);
	}

}
