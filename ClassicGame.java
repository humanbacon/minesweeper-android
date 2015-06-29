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

	private GameTimer timer = new GameTimer();

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

		class GameBoard{
			private int width;
			private int height;
			private int mineNo;
			private Cell[][] board;			

			public GameBoard(int w, int h, int m){
				width = w;
				height = h;
				mineNo = m;
				for(int j = 0; j < height; j++){
					for(int i = 0; i < width; i++){
						board[i][j] = new Cell();
					}
				}
			}

			public GameBoard(){
				this(9, 9, 10);
			}

		}

		class Cell{

			private int state;
			private int content;
			public Cell(){
				state = UNKNOWN;
				content = -1;
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
				if(c <= 0 && c >= 9){
					content = c;
				}else{
					System.err.println("Ivalid content");
				}				
			}

		}

	}	

	public static void main(String[] args){
		ClassicGame hihi = new ClassicGame();
		Scanner s = new Scanner(System.in);
		String input;
		do{
			input = s.next();
			if(input.equals("show")){
				System.out.println(hihi.timer.getTime());
			}else if(input.equals("start")){
				hihi.timer.start();
			}else if(input.equals("resume")){
				hihi.timer.resume();
			}else if(input.equals("pause")){
				hihi.timer.pause();
			}else if(input.equals("stop")){
				hihi.timer.stop();
			}
		}while(!input.equals("quit"));
		System.exit(0);
	}

}
