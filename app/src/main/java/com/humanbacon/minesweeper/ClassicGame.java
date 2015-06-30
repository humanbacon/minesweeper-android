package com.humanbacon.minesweeper;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
import java.io.InputStream;

public class ClassicGame {
	//state
	public static final int UNKNOWN = 0;
	public static final int KNOWN = 1;
	public static final int FLAG = 2;
	public static final int QUESTION = 3;
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
		private int mineNo;
		private Cell[][] board;

        public GameBoard(int w, int h, int m){
            width = w;
            height = h;
            mineNo = m;
            board = new Cell[width][height];
            for(int j = 0; j < height; j++){
                for(int i = 0; i < width; i++){
                    board[i][j] = new Cell(i, j);
                }
            }
            Random rand = new Random();
            for(int assignedMineNum = 0; assignedMineNum < m; ) {
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
                        Cell[] neighbors = getNeighbors(board[i][j]);
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

		public Cell getN(Cell cell){
            int x = cell.getX();
            int y = cell.getY();
			if(y != 0){
                return board[x][y - 1];
            }else{
                return null;
            }
		}

        public Cell getE(Cell cell){
            int x = cell.getX();
            int y = cell.getY();
            if(x != width - 1){
                return board[x + 1][y];
            }else{
                return null;
            }
        }

        public Cell getW(Cell cell){
            int x = cell.getX();
            int y = cell.getY();
            if(x != 0){
                return board[x - 1][y];
            }else{
                return null;
            }
        }

        public Cell getS(Cell cell){
            int x = cell.getX();
            int y = cell.getY();
            if(y != height - 1){
                return board[x][y + 1];
            }else{
                return null;
            }
        }

        public Cell getNW(Cell cell){
            int x = cell.getX();
            int y = cell.getY();
            if(x != 0 && y != 0){
                return board[x - 1][y - 1];
            }else {
                return null;
            }
        }

        public Cell getNE(Cell cell){
            int x = cell.getX();
            int y = cell.getY();
            if(x != width - 1 && y != 0){
                return board[x + 1][y - 1];
            }else {
                return null;
            }
        }

        public Cell getSE(Cell cell){
            int x = cell.getX();
            int y = cell.getY();
            if(x != width - 1 && y != height - 1){
                return board[x + 1][y + 1];
            }else {
                return null;
            }
        }

        public Cell getSW(Cell cell){
            int x = cell.getX();
            int y = cell.getY();
            if(x != 0 && y != height - 1){
                return board[x - 1][y + 1];
            }else {
                return null;
            }
        }

        public Cell[] getNeighbors(Cell cell){
            Cell[] cells;
            int x = cell.getX();
            int y = cell.getY();
            if(x == 0 && y == 0){
                return new Cell[] {getE(cell), getSE(cell), getS(cell)};
            }else if(x == width - 1 && y == 0){
                return new Cell[] {getS(cell), getSW(cell), getW(cell)};
            }else if(x == width - 1 && y == height - 1){
                return new Cell[] {getW(cell), getNW(cell), getN(cell)};
            }else if(x == 0 && y == height - 1){
                return new Cell[] {getN(cell), getNE(cell), getE(cell)};
            }else if(y == 0){
                return new Cell[] {getE(cell), getSE(cell), getS(cell), getSW(cell), getW(cell)};
            }else if(x == width - 1){
                return new Cell[] {getS(cell), getSW(cell), getW(cell), getNW(cell), getN(cell)};
            }else if(y == height - 1){
                return new Cell[] {getW(cell), getNW(cell), getN(cell), getNW(cell), getE(cell)};
            }else if (x == 0) {
                return new Cell[] {getN(cell), getNW(cell), getE(cell), getSE(cell), getS(cell)};
            }else{
                return new Cell[] {getN(cell), getNE(cell), getE(cell), getSE(cell), getS(cell), getSW(cell), getW(cell), getNW(cell)};
            }
        }

        public Cell getCell(int x, int y){
            return board[x][y];
        }
	}

	class Cell{
		private int state;
		private int content;
		private int x;
		private int y;
		public Cell(int x, int y){
			this.x = x;
			this.y = y;
			state = UNKNOWN;
			content = 0;
		}

		public void setKnown(){
			state = KNOWN;
		}

		public void setFlag(){
			state = FLAG;
		}

		public void setQuestion(){
			state = QUESTION;
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

		public int getState(){
			return state;
		}

		public int getX(){
			return x;
		}

		public int getY(){
			return y;
		}

	}

    public GameTimer getGameTimer(){
        return gameTimer;
    }

    public GameBoard getGameBoard(){
        return gameBoard;
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
