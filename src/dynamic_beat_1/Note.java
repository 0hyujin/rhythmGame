package dynamic_beat_1;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

//스레드 상송 - 각각의 노트 또한 하나의 부분적인 기능, 떨어지는 역활을 수행해야 하기 때문에 스레드 상속
public class Note extends Thread{
	private Image noteBasicImage = new ImageIcon (Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x,y=580-(1000/Main.SLEEP_TIME*Main.NOTE_SPEED)*Main.REACH_TIME;	//현재 노트의 위치 (y좌표는 생성될때 고정이기 때문에 값을 주었습니다.)
	private String noteType;
	private boolean proceeded = true;
	
	public String getNoteType() {
		return noteType;
	}
	public boolean isProceeded() {
		return proceeded;
	}
	public void close() {
		proceeded=false;
	}
	//Note 클래스 생성자
	public Note(String noteType) {
		if(noteType.equals("S")){
			x =228;
		}
		else if(noteType.equals("D")) {
			x=332;
		}
		else if(noteType.equals("F")) {
			x=436;
		}
		else if(noteType.equals("Space")) {
			x=540;
		}
		else if(noteType.equals("J")) {
			x=744;
		}
		else if(noteType.equals("K")) {
			x=848;
		}
		else if(noteType.equals("L")) {
			x=952;
		}
		this.noteType = noteType;
	}
	public void screenDraw(Graphics2D g) {
		if(!noteType.equals("Space")) {
			g.drawImage(noteBasicImage, x, y,null); //x,y 위치에 노트 찍기
		}
		else {
			g.drawImage(noteBasicImage, x, y,null); //x,y 위치에 노트 찍기
			g.drawImage(noteBasicImage, x+100, y,null); //x,y 위치에 노트 찍기
		}
	}
	
	
	public void drop() { 
		y+=Main.NOTE_SPEED;	//노트가 떨어짐
		if(y>620) {
			//노트가 판정바를 벗어나는 경우 Miss 출력
			System.out.println("Miss");
			close();
		}
	}
	
	@Override
	public void run() {
		try {
			//노트를 계속해서 떨어뜨림
			while(true) {
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				}
				else {
					//해당 노트가 판정바에 도달하면 멈춤
					interrupt();
					break;
				}
				//너무 빠르지 않도록 잠깐 쉬기 (현재 1초에 700픽셀만큼 y좌표가 떨어진다.)
				//Thread.sleep(Main.SLEEP_TIME);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public String judge() {
		if(y>=613) {
			System.out.println("Late");
			close();
			return "Late";
		}
		else if (y>=600) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y>=587) {
			System.out.println("Perfect");
			close();
			return "Perfect";
		}
		else if (y>=573) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if (y>=550) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y>=535) {
			System.out.println("Early");
			close();
			return "Eearly";
		}
		return "None";
	}
	public int getY() {
		return y;
	}

}
