package dynamic_beat_1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Game extends Thread {
	private Image judegementLineImage = new ImageIcon(Main.class.getResource("../images/judegementLine.png"))
			.getImage();
	private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("../images/noteRouteLine.png")).getImage();
	private Image gameInfoImage = new ImageIcon(Main.class.getResource("../images/gameinfo.png")).getImage();

	private Image noteRoutSImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRoutDImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRoutFImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	// space 경로의 크기가 더 크기 때문에 2개 붙여서 사용
	private Image noteRoutSPACE1Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRoutSPACE2Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRoutJImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRoutKImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRoutLImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();

	private Image flareImage;

	// 판정 관련 이미지
	private Image judgeImage;
	// 키패드 관련 이미지
	private Image keyPadSImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadDImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadFImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadSpace1Image = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadSpace2Image = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadJImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadKImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image keyPadLImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	private Image scoreBoard = new ImageIcon(Main.class.getResource("../images/scoreBoard.png")).getImage();

	private String titleName;
	private String difficulty;
	private String musicTitle;
	private Music gameMusic;

	public static int score = 0; // 점수
	public static boolean gameFinal = false;
	public static int count = 3000; // 시간제한 (90초가 지나면 종료)

	ArrayList<Note> noteList = new ArrayList<Note>();

	public Game(String titleName, String difficulty, String musicTitle) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		gameMusic = new Music(this.musicTitle, false);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(noteRoutSImage, 228, 30, null);
		g.drawImage(noteRoutDImage, 332, 30, null);
		g.drawImage(noteRoutFImage, 436, 30, null);
		g.drawImage(noteRoutSPACE1Image, 540, 30, null);
		g.drawImage(noteRoutSPACE2Image, 640, 30, null);
		g.drawImage(noteRoutJImage, 744, 30, null);
		g.drawImage(noteRoutKImage, 848, 30, null);
		g.drawImage(noteRoutLImage, 952, 30, null);

		g.drawImage(noteRouteLineImage, 224, 30, null);
		g.drawImage(noteRouteLineImage, 328, 30, null);
		g.drawImage(noteRouteLineImage, 432, 30, null);
		g.drawImage(noteRouteLineImage, 536, 30, null);
		g.drawImage(noteRouteLineImage, 740, 30, null);
		g.drawImage(noteRouteLineImage, 844, 30, null);
		g.drawImage(noteRouteLineImage, 948, 30, null);
		g.drawImage(noteRouteLineImage, 1052, 30, null);
		g.drawImage(gameInfoImage, 0, 660, null);

		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (note.getY() > 620) {
				judgeImage = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
			}
			if (!note.isProceeded()) {
				noteList.remove(i);
				i--;
			} else {
				note.screenDraw(g);
			}
		}
		g.drawImage(judegementLineImage, 0, 580, null);// 판정 라인
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		// 현재 실행 중인 곡 정보
		g.drawString(titleName, 20, 702);
		g.drawString(difficulty, 1190, 702);
		g.setFont(new Font("Arial", Font.BOLD, 26));
		g.setColor(Color.DARK_GRAY);
		g.drawString("S", 270, 609);
		g.drawString("D", 374, 609);
		g.drawString("F", 478, 609);
		g.drawString("SPACE", 580, 609);
		g.drawString("J", 784, 609);
		g.drawString("K", 889, 609);
		g.drawString("L", 993, 609);
		// Timer 생성
		g.setColor(Color.black);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		g.drawString(String.valueOf(score), 650, 700);

		g.setColor(Color.black);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		g.drawString("Count", 70, 280);
		g.drawString(String.valueOf(count), 80, 340);

		g.drawImage(flareImage, 270, 250, null);
		g.drawImage(judgeImage, 460, 420, null);

		g.drawImage(keyPadSImage, 228, 580, null);
		g.drawImage(keyPadDImage, 332, 580, null);
		g.drawImage(keyPadFImage, 436, 580, null);
		g.drawImage(keyPadSpace1Image, 540, 580, null);
		g.drawImage(keyPadSpace2Image, 640, 580, null);
		g.drawImage(keyPadJImage, 744, 580, null);
		g.drawImage(keyPadKImage, 848, 580, null);
		g.drawImage(keyPadLImage, 952, 580, null);

		// 추가 게임의 끝을 판정
		if (gameFinal) {
			gameMusic.close();
			this.interrupt();
			
			g.drawImage(scoreBoard, 200, 80, null);
			judgeImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
			flareImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
			g.setFont(new Font("Elephant", Font.BOLD, 100));
			g.drawString("Score", 480, 300);
			g.setFont(new Font("Elephant", Font.BOLD, 70));
			g.drawString(String.valueOf(score), 570, 400);
		}
	}

	// 드럼 효과음 추가해야 됨 **
	public void pressS() {
		judge("S");
		noteRoutSImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		// new Music(".mp3",false).start();
	}

	public void releaseS() {
		noteRoutSImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}

	public void pressD() {
		judge("D");
		noteRoutDImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		// new Music(".mp3",false).start();
	}

	public void releaseD() {
		noteRoutDImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}

	public void pressF() {
		judge("F");
		noteRoutFImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		// new Music(".mp3",false).start();
	}

	public void releaseF() {
		noteRoutFImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}

	public void pressJ() {
		judge("J");
		noteRoutJImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		// new Music(".mp3",false).start();
	}

	public void releaseJ() {
		noteRoutJImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}

	public void pressK() {
		judge("K");
		noteRoutKImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		// new Music(".mp3",false).start();
	}

	public void releaseK() {
		noteRoutKImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}

	public void pressL() {
		judge("L");
		noteRoutLImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		// new Music(".mp3",false).start();
	}

	public void releaseL() {
		noteRoutLImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}

	public void pressSpace() {
		judge("Space");
		noteRoutSPACE1Image = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		noteRoutSPACE2Image = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("../images/keyPadPressed.png")).getImage();
		// new Music(".mp3",false).start();
	}

	public void releaseSpace() {
		noteRoutSPACE1Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		noteRoutSPACE2Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("../images/keyPadBasic.png")).getImage();
	}

	@Override
	public void run() {
		dropNotes(this.titleName);

	}

	// 메인 화면으로 다시 돌아갔을때 게임 중에 실행되는 음악을 멈추는 메소드 & 다시 메인으로 돌아가기 때문에 점수도 초기화 해준다.
	public void close() {
		gameMusic.close();
		this.interrupt();
		score = 0;
		gameFinal =false;
		count=3000;
	}

	// 노트들이 떨어지도록 하는 메소드
	public void dropNotes(String titleName) {
		Beat[] beats = null;
		if (titleName.equals("New Jeans-Attantion") && difficulty.equals("Easy")) {
			int startTime = 4460 - Main.REACH_TIME * 1000;
			int gap = 125;
			beats = new Beat[] { new Beat(startTime, "Space"), new Beat(startTime + gap * 2, "D"),
					new Beat(startTime + gap * 4, "S"), new Beat(startTime + gap * 6, "D"),
					new Beat(startTime + gap * 8, "S"), new Beat(startTime + gap * 10, "D"),
					new Beat(startTime + gap * 12, "S"), new Beat(startTime + gap * 14, "D"),
					new Beat(startTime + gap * 18, "J"), new Beat(startTime + gap * 20, "K"),
					new Beat(startTime + gap * 24, "K"), new Beat(startTime + gap * 26, "J"),
					new Beat(startTime + gap * 28, "K"), new Beat(startTime + gap * 30, "J"),
					new Beat(startTime + gap * 32, "K"), new Beat(startTime + gap * 36, "S"),
					new Beat(startTime + gap * 38, "D"), new Beat(startTime + gap * 40, "S"),
					new Beat(startTime + gap * 42, "D"), new Beat(startTime + gap * 46, "D"),
					new Beat(startTime + gap * 48, "J"), new Beat(startTime + gap * 49, "K"),
					new Beat(startTime + gap * 50, "L"), new Beat(startTime + gap * 52, "F"),
					new Beat(startTime + gap * 52, "Space"), new Beat(startTime + gap * 52, "J"),
					new Beat(startTime + gap * 56, "S"), new Beat(startTime + gap * 56, "D"),
					new Beat(startTime + gap * 58, "S"), new Beat(startTime + gap * 58, "D"),
					new Beat(startTime + gap * 58, "S"), new Beat(startTime + gap * 66, "D"),
					new Beat(startTime + gap * 69, "K"), new Beat(startTime + gap * 80, "L"),
					new Beat(startTime + gap * 82, "D"), new Beat(startTime + gap * 83, "J"),
					new Beat(startTime + gap * 87, "K"), new Beat(startTime + gap * 89, "Space"),
					new Beat(startTime + gap * 91, "K"), new Beat(startTime + gap * 92, "S"),
					new Beat(startTime + gap * 92, "D"), new Beat(startTime + gap * 95, "S"),
					new Beat(startTime + gap * 100, "S"), new Beat(startTime + gap * 103, "D"),
					new Beat(startTime + gap * 103, "J"), new Beat(startTime + gap * 105, "K"),
					new Beat(startTime + gap * 106, "L"), new Beat(startTime + gap * 108, "F"),
					new Beat(startTime + gap * 108, "Space"), new Beat(startTime + gap * 110, "J"),
					new Beat(startTime + gap * 113, "D"), new Beat(startTime + gap * 115, "S"),
					new Beat(startTime + gap * 117, "D"), new Beat(startTime + gap * 119, "S"),
					new Beat(startTime + gap * 113, "D"), new Beat(startTime + gap * 115, "D"),
					new Beat(startTime + gap * 117, "J"), new Beat(startTime + gap * 121, "D"),
					new Beat(startTime + gap * 123, "L"), new Beat(startTime + gap * 124, "J"),
					new Beat(startTime + gap * 125, "K"), new Beat(startTime + gap * 127, "J"),
					new Beat(startTime + gap * 127, "S"), new Beat(startTime + gap * 128, "D"),
					new Beat(startTime + gap * 129, "S"), new Beat(startTime + gap * 130, "D"),
					new Beat(startTime + gap * 134, "S"), new Beat(startTime + gap * 135, "D"),
					new Beat(startTime + gap * 136, "K"), new Beat(startTime + gap * 137, "L"),
					new Beat(startTime + gap * 137, "F"), new Beat(startTime + gap * 139, "Space"),
					new Beat(startTime + gap * 141, "Space"), new Beat(startTime + gap * 142, "D"),
					new Beat(startTime + gap * 143, "S"), new Beat(startTime + gap * 144, "D"),
					new Beat(startTime + gap * 148, "D"), new Beat(startTime + gap * 149, "S"),
					new Beat(startTime + gap * 150, "D"), new Beat(startTime + gap * 151, "J"),
					new Beat(startTime + gap * 152, "K"), new Beat(startTime + gap * 154, "J"),
					new Beat(startTime + gap * 154, "F"), new Beat(startTime + gap * 155, "K"),
					new Beat(startTime + gap * 156, "J"), new Beat(startTime + gap * 157, "K"),
					new Beat(startTime + gap * 158, "S"), new Beat(startTime + gap * 159, "D"),
					new Beat(startTime + gap * 162, "K"), new Beat(startTime + gap * 163, "S"),
					new Beat(startTime + gap * 165, "J"), new Beat(startTime + gap * 165, "K"),
					new Beat(startTime + gap * 166, "L"), new Beat(startTime + gap * 169, "Space"),
					new Beat(startTime + gap * 171, "J"), new Beat(startTime + gap * 173, "K"),
					new Beat(startTime + gap * 175, "S"), new Beat(startTime + gap * 177, "D"),
					new Beat(startTime + gap * 178, "K"), new Beat(startTime + gap * 181, "Space"),
					new Beat(startTime + gap * 183, "D"), new Beat(startTime + gap * 185, "L"),
					new Beat(startTime + gap * 187, "D"), new Beat(startTime + gap * 191, "F"),
					new Beat(startTime + gap * 193, "Space"), new Beat(startTime + gap * 195, "F"),
					new Beat(startTime + gap * 197, "K"), new Beat(startTime + gap * 199, "J"),
					new Beat(startTime + gap * 200, "D"), new Beat(startTime + gap * 202, "Space"),
					new Beat(startTime + gap * 204, "S"), new Beat(startTime + gap * 206, "J"),
					new Beat(startTime + gap * 208, "D"), new Beat(startTime + gap * 212, "F"),
					new Beat(startTime + gap * 214, "J"), new Beat(startTime + gap * 216, "S"),
					new Beat(startTime + gap * 218, "L"), new Beat(startTime + gap * 220, "D"),
					new Beat(startTime + gap * 222, "D"), new Beat(startTime + gap * 226, "K"),
					new Beat(startTime + gap * 228, "Space"), new Beat(startTime + gap * 230, "J"),
					new Beat(startTime + gap * 232, "F"), new Beat(startTime + gap * 236, "D"),
					new Beat(startTime + gap * 238, "J"), new Beat(startTime + gap * 238, "F"),
					new Beat(startTime + gap * 240, "K"), new Beat(startTime + gap * 244, "J"),
					new Beat(startTime + gap * 246, "F"), new Beat(startTime + gap * 248, "Space"),
					new Beat(startTime + gap * 250, "K"), new Beat(startTime + gap * 252, "D"),
					new Beat(startTime + gap * 254, "J"), new Beat(startTime + gap * 258, "F"),
					new Beat(startTime + gap * 260, "Space"), new Beat(startTime + gap * 262, "F"),
					new Beat(startTime + gap * 272, "J"), new Beat(startTime + gap * 274, "D"),
					new Beat(startTime + gap * 275, "K"), new Beat(startTime + gap * 278, "K"),
					new Beat(startTime + gap * 280, "S"), new Beat(startTime + gap * 282, "Space"),
					new Beat(startTime + gap * 284, "D"), new Beat(startTime + gap * 288, "F"),
					new Beat(startTime + gap * 290, "J"), new Beat(startTime + gap * 292, "L"),
					new Beat(startTime + gap * 294, "D"), new Beat(startTime + gap * 296, "F"),
					new Beat(startTime + gap * 298, "J"), new Beat(startTime + gap * 302, "D"),
					new Beat(startTime + gap * 304, "Space"), new Beat(startTime + gap * 306, "J"),
					new Beat(startTime + gap * 308, "F"), new Beat(startTime + gap * 312, "K"),
					new Beat(startTime + gap * 314, "J"), new Beat(startTime + gap * 316, "F"),
					new Beat(startTime + gap * 318, "L"), new Beat(startTime + gap * 320, "D"),
					new Beat(startTime + gap * 322, "Space"), new Beat(startTime + gap * 324, "K"),
					new Beat(startTime + gap * 326, "J"), new Beat(startTime + gap * 330, "D"),
					new Beat(startTime + gap * 332, "Space"), new Beat(startTime + gap * 334, "D"),
					new Beat(startTime + gap * 336, "F"), new Beat(startTime + gap * 338, "K"),
					new Beat(startTime + gap * 340, "K"), new Beat(startTime + gap * 342, "L"),
					new Beat(startTime + gap * 344, "F"), new Beat(startTime + gap * 346, "F"),
					new Beat(startTime + gap * 348, "D"), new Beat(startTime + gap * 352, "Space"),
					new Beat(startTime + gap * 354, "F"), new Beat(startTime + gap * 356, "K"),
					new Beat(startTime + gap * 358, "L"), new Beat(startTime + gap * 360, "S"),
					new Beat(startTime + gap * 362, "L"), new Beat(startTime + gap * 366, "J"),
					new Beat(startTime + gap * 368, "Space"), new Beat(startTime + gap * 370, "F"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "K"),
					new Beat(startTime + gap * 372, "K"), new Beat(startTime + gap * 374, "L"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 372, "Space"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 372, "F"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 374, "K"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "D"),
					new Beat(startTime + gap * 372, "F"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "S"),
					new Beat(startTime + gap * 374, "Space"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 372, "K"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 374, "Space"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 372, "K"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "Space"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 374, "S"), new Beat(startTime + gap * 372, "F"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 372, "S"), new Beat(startTime + gap * 374, "Space"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "S"),

			};
		} else if (titleName.equals("New Jeans-Attantion") && difficulty.equals("Hard")) {
			int startTime = 4460 - Main.REACH_TIME * 1000;
			int gap = 125;
			beats = new Beat[] { new Beat(startTime, "S"), new Beat(startTime + gap * 4, "S"),
					new Beat(startTime + gap * 6, "D"), new Beat(startTime + gap * 8, "S"),
					new Beat(startTime + gap * 10, "D"), new Beat(startTime + gap * 12, "S"),
					new Beat(startTime + gap * 14, "D"), new Beat(startTime + gap * 18, "J"),
					new Beat(startTime + gap * 20, "K"), new Beat(startTime + gap * 22, "J"),
					new Beat(startTime + gap * 24, "K"), new Beat(startTime + gap * 26, "J"),
					new Beat(startTime + gap * 28, "K"), new Beat(startTime + gap * 30, "J"),
					new Beat(startTime + gap * 32, "K"), new Beat(startTime + gap * 36, "S"),
					new Beat(startTime + gap * 38, "D"), new Beat(startTime + gap * 40, "S"),
					new Beat(startTime + gap * 42, "D"), new Beat(startTime + gap * 44, "S"),
					new Beat(startTime + gap * 46, "D"), new Beat(startTime + gap * 48, "J"),
					new Beat(startTime + gap * 49, "K"), new Beat(startTime + gap * 50, "L"),
					new Beat(startTime + gap * 52, "F"), new Beat(startTime + gap * 52, "Space"),
					new Beat(startTime + gap * 52, "J"), new Beat(startTime + gap * 56, "D"),
					new Beat(startTime + gap * 56, "S"), new Beat(startTime + gap * 56, "D"),
					new Beat(startTime + gap * 58, "S"), new Beat(startTime + gap * 58, "D"),
					new Beat(startTime + gap * 58, "S"), new Beat(startTime + gap * 66, "D"),
					new Beat(startTime + gap * 67, "J"), new Beat(startTime + gap * 69, "K"),
					new Beat(startTime + gap * 80, "L"), new Beat(startTime + gap * 82, "D"),
					new Beat(startTime + gap * 83, "J"), new Beat(startTime + gap * 87, "K"),
					new Beat(startTime + gap * 89, "Space"), new Beat(startTime + gap * 91, "K"),
					new Beat(startTime + gap * 92, "S"), new Beat(startTime + gap * 92, "D"),
					new Beat(startTime + gap * 95, "S"), new Beat(startTime + gap * 97, "K"),
					new Beat(startTime + gap * 100, "S"), new Beat(startTime + gap * 103, "D"),
					new Beat(startTime + gap * 103, "J"), new Beat(startTime + gap * 105, "K"),
					new Beat(startTime + gap * 106, "L"), new Beat(startTime + gap * 108, "F"),
					new Beat(startTime + gap * 108, "Space"), new Beat(startTime + gap * 110, "J"),
					new Beat(startTime + gap * 111, "Space"), new Beat(startTime + gap * 113, "D"),
					new Beat(startTime + gap * 115, "S"), new Beat(startTime + gap * 117, "D"),
					new Beat(startTime + gap * 119, "S"), new Beat(startTime + gap * 113, "D"),
					new Beat(startTime + gap * 114, "S"), new Beat(startTime + gap * 115, "D"),
					new Beat(startTime + gap * 117, "J"), new Beat(startTime + gap * 119, "K"),
					new Beat(startTime + gap * 121, "D"), new Beat(startTime + gap * 123, "L"),
					new Beat(startTime + gap * 124, "J"), new Beat(startTime + gap * 125, "K"),
					new Beat(startTime + gap * 127, "J"), new Beat(startTime + gap * 127, "K"),
					new Beat(startTime + gap * 127, "S"), new Beat(startTime + gap * 128, "D"),
					new Beat(startTime + gap * 129, "S"), new Beat(startTime + gap * 130, "D"),
					new Beat(startTime + gap * 134, "S"), new Beat(startTime + gap * 135, "D"),
					new Beat(startTime + gap * 136, "J"), new Beat(startTime + gap * 136, "K"),
					new Beat(startTime + gap * 137, "L"), new Beat(startTime + gap * 137, "F"),
					new Beat(startTime + gap * 139, "Space"), new Beat(startTime + gap * 139, "J"),
					new Beat(startTime + gap * 141, "Space"), new Beat(startTime + gap * 142, "D"),
					new Beat(startTime + gap * 143, "S"), new Beat(startTime + gap * 144, "D"),
					new Beat(startTime + gap * 146, "S"), new Beat(startTime + gap * 148, "D"),
					new Beat(startTime + gap * 149, "S"), new Beat(startTime + gap * 150, "D"),
					new Beat(startTime + gap * 151, "J"), new Beat(startTime + gap * 152, "K"),
					new Beat(startTime + gap * 154, "J"), new Beat(startTime + gap * 154, "F"),
					new Beat(startTime + gap * 155, "J"), new Beat(startTime + gap * 155, "K"),
					new Beat(startTime + gap * 156, "J"), new Beat(startTime + gap * 157, "K"),
					new Beat(startTime + gap * 158, "S"), new Beat(startTime + gap * 159, "D"),
					new Beat(startTime + gap * 160, "S"), new Beat(startTime + gap * 162, "K"),
					new Beat(startTime + gap * 163, "S"), new Beat(startTime + gap * 164, "D"),
					new Beat(startTime + gap * 165, "J"), new Beat(startTime + gap * 165, "K"),
					new Beat(startTime + gap * 166, "L"), new Beat(startTime + gap * 168, "F"),
					new Beat(startTime + gap * 169, "Space"), new Beat(startTime + gap * 171, "J"),
					new Beat(startTime + gap * 173, "K"), new Beat(startTime + gap * 175, "S"),
					new Beat(startTime + gap * 177, "D"), new Beat(startTime + gap * 178, "K"),
					new Beat(startTime + gap * 181, "Space"), new Beat(startTime + gap * 183, "D"),
					new Beat(startTime + gap * 185, "L"), new Beat(startTime + gap * 187, "D"),
					new Beat(startTime + gap * 189, "S"), new Beat(startTime + gap * 191, "F"),
					new Beat(startTime + gap * 193, "Space"), new Beat(startTime + gap * 195, "F"),
					new Beat(startTime + gap * 197, "K"), new Beat(startTime + gap * 199, "J"),
					new Beat(startTime + gap * 200, "D"), new Beat(startTime + gap * 202, "Space"),
					new Beat(startTime + gap * 204, "S"), new Beat(startTime + gap * 206, "J"),
					new Beat(startTime + gap * 208, "D"), new Beat(startTime + gap * 210, "K"),
					new Beat(startTime + gap * 212, "F"), new Beat(startTime + gap * 214, "J"),
					new Beat(startTime + gap * 216, "S"), new Beat(startTime + gap * 218, "L"),
					new Beat(startTime + gap * 220, "D"), new Beat(startTime + gap * 222, "D"),
					new Beat(startTime + gap * 224, "F"), new Beat(startTime + gap * 226, "K"),
					new Beat(startTime + gap * 228, "Space"), new Beat(startTime + gap * 230, "J"),
					new Beat(startTime + gap * 232, "F"), new Beat(startTime + gap * 234, "D"),
					new Beat(startTime + gap * 236, "D"), new Beat(startTime + gap * 238, "J"),
					new Beat(startTime + gap * 238, "F"), new Beat(startTime + gap * 240, "K"),
					new Beat(startTime + gap * 242, "F"), new Beat(startTime + gap * 244, "J"),
					new Beat(startTime + gap * 246, "F"), new Beat(startTime + gap * 248, "Space"),
					new Beat(startTime + gap * 250, "K"), new Beat(startTime + gap * 252, "D"),
					new Beat(startTime + gap * 254, "J"), new Beat(startTime + gap * 256, "J"),
					new Beat(startTime + gap * 258, "F"), new Beat(startTime + gap * 260, "Space"),
					new Beat(startTime + gap * 262, "F"), new Beat(startTime + gap * 272, "J"),
					new Beat(startTime + gap * 274, "D"), new Beat(startTime + gap * 275, "K"),
					new Beat(startTime + gap * 276, "S"), new Beat(startTime + gap * 278, "K"),
					new Beat(startTime + gap * 280, "S"), new Beat(startTime + gap * 282, "Space"),
					new Beat(startTime + gap * 284, "D"), new Beat(startTime + gap * 286, "J"),
					new Beat(startTime + gap * 288, "F"), new Beat(startTime + gap * 290, "J"),
					new Beat(startTime + gap * 292, "L"), new Beat(startTime + gap * 294, "D"),
					new Beat(startTime + gap * 296, "F"), new Beat(startTime + gap * 298, "J"),
					new Beat(startTime + gap * 300, "L"), new Beat(startTime + gap * 302, "D"),
					new Beat(startTime + gap * 304, "Space"), new Beat(startTime + gap * 306, "J"),
					new Beat(startTime + gap * 308, "F"), new Beat(startTime + gap * 310, "D"),
					new Beat(startTime + gap * 312, "K"), new Beat(startTime + gap * 314, "J"),
					new Beat(startTime + gap * 316, "F"), new Beat(startTime + gap * 318, "L"),
					new Beat(startTime + gap * 320, "D"), new Beat(startTime + gap * 322, "Space"),
					new Beat(startTime + gap * 324, "K"), new Beat(startTime + gap * 326, "J"),
					new Beat(startTime + gap * 328, "F"), new Beat(startTime + gap * 330, "D"),
					new Beat(startTime + gap * 332, "Space"), new Beat(startTime + gap * 334, "D"),
					new Beat(startTime + gap * 336, "F"), new Beat(startTime + gap * 338, "K"),
					new Beat(startTime + gap * 340, "K"), new Beat(startTime + gap * 340, "D"),
					new Beat(startTime + gap * 342, "L"), new Beat(startTime + gap * 344, "F"),
					new Beat(startTime + gap * 346, "F"), new Beat(startTime + gap * 348, "D"),
					new Beat(startTime + gap * 350, "S"), new Beat(startTime + gap * 352, "Space"),
					new Beat(startTime + gap * 354, "F"), new Beat(startTime + gap * 356, "K"),
					new Beat(startTime + gap * 358, "L"), new Beat(startTime + gap * 360, "S"),
					new Beat(startTime + gap * 362, "L"), new Beat(startTime + gap * 364, "F"),
					new Beat(startTime + gap * 366, "J"), new Beat(startTime + gap * 368, "Space"),
					new Beat(startTime + gap * 370, "F"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 372, "K"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 372, "Space"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 372, "F"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "S"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "D"), new Beat(startTime + gap * 372, "F"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "S"),
					new Beat(startTime + gap * 374, "Space"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 374, "S"), new Beat(startTime + gap * 372, "K"),
					new Beat(startTime + gap * 374, "S"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 374, "Space"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 374, "S"), new Beat(startTime + gap * 372, "K"),
					new Beat(startTime + gap * 374, "S"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 374, "Space"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 374, "S"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 374, "S"), new Beat(startTime + gap * 372, "F"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 374, "S"), new Beat(startTime + gap * 372, "S"),
					new Beat(startTime + gap * 374, "Space"), new Beat(startTime + gap * 372, "L"),
					new Beat(startTime + gap * 374, "S"),

			};
		} else if (titleName.equals("Red Velvet-7월 7일") && difficulty.equals("Easy")) {
			int startTime = 1000 - Main.REACH_TIME * 1000;
			int gap = 125;
			beats = new Beat[] { new Beat(startTime, "S") };

		} else if (titleName.equals("Red Velvet-7월 7일") && difficulty.equals("Hard")) {
			int startTime = 1000 - Main.REACH_TIME * 1000;
			int gap = 125;
			beats = new Beat[] { new Beat(startTime, "S"), new Beat(startTime + gap * 6, "D"),
					new Beat(startTime + gap * 8, "S"), new Beat(startTime + gap * 10, "D"),
					new Beat(startTime + gap * 12, "S"), new Beat(startTime + gap * 14, "D"),
					new Beat(startTime + gap * 18, "J"), new Beat(startTime + gap * 20, "K"),
					new Beat(startTime + gap * 22, "J"), new Beat(startTime + gap * 24, "K"),
					new Beat(startTime + gap * 26, "J"), new Beat(startTime + gap * 28, "K"),
					new Beat(startTime + gap * 30, "J"), new Beat(startTime + gap * 32, "K"),
					new Beat(startTime + gap * 36, "S"), new Beat(startTime + gap * 38, "D"),
					new Beat(startTime + gap * 40, "S"), new Beat(startTime + gap * 42, "D"),
					new Beat(startTime + gap * 44, "S"), new Beat(startTime + gap * 46, "D"),
					new Beat(startTime + gap * 48, "J"), new Beat(startTime + gap * 49, "K"),
					new Beat(startTime + gap * 50, "L"), new Beat(startTime + gap * 52, "F"),
					new Beat(startTime + gap * 52, "Space"), new Beat(startTime + gap * 52, "J"),
					new Beat(startTime + gap * 56, "D"), new Beat(startTime + gap * 56, "S"),
					new Beat(startTime + gap * 56, "D"), new Beat(startTime + gap * 58, "S"),
					new Beat(startTime + gap * 58, "D"), new Beat(startTime + gap * 58, "S"),
					new Beat(startTime + gap * 66, "D"), new Beat(startTime + gap * 67, "J"),
					new Beat(startTime + gap * 69, "K"), new Beat(startTime + gap * 80, "L"),
					new Beat(startTime + gap * 82, "D"), new Beat(startTime + gap * 83, "J"),
					new Beat(startTime + gap * 87, "K"), new Beat(startTime + gap * 89, "Space"),
					new Beat(startTime + gap * 91, "K"), new Beat(startTime + gap * 92, "S"),
					new Beat(startTime + gap * 92, "D"), new Beat(startTime + gap * 95, "S"),
					new Beat(startTime + gap * 97, "K"), new Beat(startTime + gap * 100, "S"),
					new Beat(startTime + gap * 103, "D"), new Beat(startTime + gap * 103, "J"),
					new Beat(startTime + gap * 105, "K"), new Beat(startTime + gap * 106, "L"),
					new Beat(startTime + gap * 108, "F"), new Beat(startTime + gap * 108, "Space"),
					new Beat(startTime + gap * 110, "J"), new Beat(startTime + gap * 111, "Space"),
					new Beat(startTime + gap * 113, "D"), new Beat(startTime + gap * 115, "S"),
					new Beat(startTime + gap * 117, "D"), new Beat(startTime + gap * 119, "S"),
					new Beat(startTime + gap * 113, "D"), new Beat(startTime + gap * 114, "S"),
					new Beat(startTime + gap * 115, "D"), new Beat(startTime + gap * 117, "J"),
					new Beat(startTime + gap * 119, "K"), new Beat(startTime + gap * 121, "D"),
					new Beat(startTime + gap * 123, "L"), new Beat(startTime + gap * 124, "J"),
					new Beat(startTime + gap * 125, "K"), new Beat(startTime + gap * 127, "J"),
					new Beat(startTime + gap * 127, "K"), new Beat(startTime + gap * 127, "S"),
					new Beat(startTime + gap * 128, "D"), new Beat(startTime + gap * 129, "S"),
					new Beat(startTime + gap * 130, "D"), new Beat(startTime + gap * 134, "S"),
					new Beat(startTime + gap * 135, "D"), new Beat(startTime + gap * 136, "J"),
					new Beat(startTime + gap * 136, "K"), new Beat(startTime + gap * 137, "L"),
					new Beat(startTime + gap * 137, "F"), new Beat(startTime + gap * 139, "Space"),
					new Beat(startTime + gap * 139, "J"), new Beat(startTime + gap * 141, "Space"),
					new Beat(startTime + gap * 142, "D"), new Beat(startTime + gap * 143, "S"),
					new Beat(startTime + gap * 144, "D"), new Beat(startTime + gap * 146, "S"),
					new Beat(startTime + gap * 148, "D"), new Beat(startTime + gap * 149, "S"),
					new Beat(startTime + gap * 150, "D"), new Beat(startTime + gap * 151, "J"),
					new Beat(startTime + gap * 152, "K"), new Beat(startTime + gap * 154, "J"),
					new Beat(startTime + gap * 154, "F"), new Beat(startTime + gap * 155, "J"),
					new Beat(startTime + gap * 155, "K"), new Beat(startTime + gap * 156, "J"),
					new Beat(startTime + gap * 157, "K"), new Beat(startTime + gap * 158, "S"),
					new Beat(startTime + gap * 159, "D"), new Beat(startTime + gap * 160, "S"),
					new Beat(startTime + gap * 162, "K"), new Beat(startTime + gap * 163, "S"),
					new Beat(startTime + gap * 164, "D"), new Beat(startTime + gap * 165, "J"),
					new Beat(startTime + gap * 165, "K"), new Beat(startTime + gap * 166, "L"),
					new Beat(startTime + gap * 168, "F"), new Beat(startTime + gap * 169, "Space"),
					new Beat(startTime + gap * 171, "J"), new Beat(startTime + gap * 173, "K"),
					new Beat(startTime + gap * 175, "S"), new Beat(startTime + gap * 177, "D"),
					new Beat(startTime + gap * 178, "K"), new Beat(startTime + gap * 181, "Space"),
					new Beat(startTime + gap * 183, "D"), new Beat(startTime + gap * 185, "L"),
					new Beat(startTime + gap * 187, "D"), new Beat(startTime + gap * 189, "S"),
					new Beat(startTime + gap * 191, "F"), new Beat(startTime + gap * 193, "Space"),
					new Beat(startTime + gap * 195, "F"), new Beat(startTime + gap * 197, "K"),
					new Beat(startTime + gap * 199, "J"), new Beat(startTime + gap * 200, "D"),
					new Beat(startTime + gap * 202, "Space"), new Beat(startTime + gap * 204, "S"),
					new Beat(startTime + gap * 206, "J"), new Beat(startTime + gap * 208, "D"),
					new Beat(startTime + gap * 210, "K"), new Beat(startTime + gap * 212, "F"),
					new Beat(startTime + gap * 214, "J"), new Beat(startTime + gap * 216, "S"),
					new Beat(startTime + gap * 218, "L"), new Beat(startTime + gap * 220, "D"),
					new Beat(startTime + gap * 222, "D"), new Beat(startTime + gap * 224, "F"),
					new Beat(startTime + gap * 226, "K"), new Beat(startTime + gap * 228, "Space"),
					new Beat(startTime + gap * 230, "J"), new Beat(startTime + gap * 232, "F"),
					new Beat(startTime + gap * 234, "D"), new Beat(startTime + gap * 236, "D"),
					new Beat(startTime + gap * 238, "J"), new Beat(startTime + gap * 238, "F"),
					new Beat(startTime + gap * 240, "K"), new Beat(startTime + gap * 242, "F"),
					new Beat(startTime + gap * 244, "J"), new Beat(startTime + gap * 246, "F"),
					new Beat(startTime + gap * 248, "Space"), new Beat(startTime + gap * 250, "K"),
					new Beat(startTime + gap * 252, "D"), new Beat(startTime + gap * 254, "J"),
					new Beat(startTime + gap * 256, "J"), new Beat(startTime + gap * 258, "F"),
					new Beat(startTime + gap * 260, "Space"), new Beat(startTime + gap * 262, "F"),
					new Beat(startTime + gap * 272, "J"), new Beat(startTime + gap * 274, "D"),
					new Beat(startTime + gap * 275, "K"), new Beat(startTime + gap * 276, "S"),
					new Beat(startTime + gap * 278, "K"), new Beat(startTime + gap * 280, "S"),
					new Beat(startTime + gap * 282, "Space"), new Beat(startTime + gap * 284, "D"),
					new Beat(startTime + gap * 286, "J"), new Beat(startTime + gap * 288, "F"),
					new Beat(startTime + gap * 290, "J"), new Beat(startTime + gap * 292, "L"),
					new Beat(startTime + gap * 294, "D"), new Beat(startTime + gap * 296, "F"),
					new Beat(startTime + gap * 298, "J"), new Beat(startTime + gap * 300, "L"),
					new Beat(startTime + gap * 302, "D"), new Beat(startTime + gap * 304, "Space"),
					new Beat(startTime + gap * 306, "J"), new Beat(startTime + gap * 308, "F"),
					new Beat(startTime + gap * 310, "D"), new Beat(startTime + gap * 312, "K"),
					new Beat(startTime + gap * 314, "J"), new Beat(startTime + gap * 316, "F"),
					new Beat(startTime + gap * 318, "L"), new Beat(startTime + gap * 320, "D"),
					new Beat(startTime + gap * 322, "Space"), new Beat(startTime + gap * 324, "K"),
					new Beat(startTime + gap * 326, "J"), new Beat(startTime + gap * 328, "F"),
					new Beat(startTime + gap * 330, "D"), new Beat(startTime + gap * 332, "Space"),
					new Beat(startTime + gap * 334, "D"), new Beat(startTime + gap * 336, "F"),
					new Beat(startTime + gap * 338, "K"), new Beat(startTime + gap * 340, "K"),
					new Beat(startTime + gap * 340, "D"), new Beat(startTime + gap * 342, "L"),
					new Beat(startTime + gap * 344, "F"), new Beat(startTime + gap * 346, "F"),
					new Beat(startTime + gap * 348, "D"), new Beat(startTime + gap * 350, "S"),
					new Beat(startTime + gap * 352, "Space"), new Beat(startTime + gap * 354, "F"),
					new Beat(startTime + gap * 356, "K"), new Beat(startTime + gap * 358, "L"),
					new Beat(startTime + gap * 360, "S"), new Beat(startTime + gap * 362, "L"),
					new Beat(startTime + gap * 364, "F"), new Beat(startTime + gap * 366, "J"),
					new Beat(startTime + gap * 368, "Space"), new Beat(startTime + gap * 370, "F"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "K"),
					new Beat(startTime + gap * 372, "K"), new Beat(startTime + gap * 374, "L"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "K"),
					new Beat(startTime + gap * 372, "Space"), new Beat(startTime + gap * 374, "L"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "K"),
					new Beat(startTime + gap * 372, "F"), new Beat(startTime + gap * 374, "L"),
					new Beat(startTime + gap * 372, "S"), new Beat(startTime + gap * 374, "K"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "D"),
					new Beat(startTime + gap * 372, "F"), new Beat(startTime + gap * 374, "K"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "L"),
					new Beat(startTime + gap * 372, "S"), new Beat(startTime + gap * 374, "Space"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "K"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 372, "K"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "Space"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "L"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 372, "K"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "Space"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 372, "D"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 372, "F"), new Beat(startTime + gap * 374, "L"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 372, "S"), new Beat(startTime + gap * 374, "Space"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 374, "S"), };

		} else if (titleName.equals("EXO-12월의 기적") && difficulty.equals("Easy")) {
			int startTime = 1000 - Main.REACH_TIME * 1000;
			beats = new Beat[] { new Beat(startTime, "J") };

		} else if (titleName.equals("EXO-12월의 기적") && difficulty.equals("Hard")) {
			int startTime = 1000 - Main.REACH_TIME * 1000;
			beats = new Beat[] { new Beat(startTime, "S") };

		}
		int i = 0;
		// 배열 초기화 후 바로 시작
		gameMusic.start();
		while (i < beats.length && !isInterrupted()) {
			boolean dropped = false;
			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName());
				note.start();
				noteList.add(note);
				i++;
				dropped = true;
			}
			if (!dropped) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
				}
			}

		}

	}

	public void judge(String input) {
		// 먼저 입력된 노트부터 판정 시작 ArrayList를 큐처럼 사용
		// 만약 입력된 노트가 없더라면 수행하지 않게 됨
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			while (true) {
				judgeEvent(note.judge());
				count--;
				// 만약 count 횟수가 0이 되면 게임 종료
				if (count == 0)
					gameFinal = true;
				break; // 먼저 입력된 노트를 찾자마자 반복문 종료
			}
		}
	}

	public void judgeEvent(String judge) {
		if (!judge.equals("None")) {
			flareImage = new ImageIcon(Main.class.getResource("../images/FlareImage.png")).getImage();
		}
		if (judge.equals("Miss")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
			score += 0;
			System.out.println(score);
		} else if (judge.equals("Late")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/late.png")).getImage();
			score += 10;
			System.out.println(score);
		} else if (judge.equals("Good")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/good.png")).getImage();
			score += 20;
			System.out.println(score);
		} else if (judge.equals("Great")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/great.png")).getImage();
			score += 30;
			System.out.println(score);
		} else if (judge.equals("Perfect")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/perfect.png")).getImage();
			score += 50;
			System.out.println(score);
		} else if (judge.equals("Early")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/early.png")).getImage();
			score += 10;
			System.out.println(score);
		}
	}

}
