package dynamic_beat_1;

public class Main {
	
	public static final int SCREEN_WIDTH =1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int NOTE_SPEED =3;	//노트가 떨어진 속도
	public static final int SLEEP_TIME = 10;
	public static final int REACH_TIME = 2; //노트가 생성되고 나서 판정 라인에 도착하는데 걸리는 시간
	
	public static void main(String[]args) {
		new DynamicBeat();
	}
}
