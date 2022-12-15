package dynamic_beat_1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame {
	// 더블 버퍼링 기술 사용하기 위함
	private Image screenImage;
	private Graphics screenGraphic;

	private ImageIcon exitButtonPressed = new ImageIcon(Main.class.getResource("../images/exitButtonPressed.png"));
	private ImageIcon exitButtonBasic = new ImageIcon(Main.class.getResource("../images/exitButton.png"));
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/gameStartButton.png"));
	private ImageIcon startButtonEnteredImage = new ImageIcon(
			Main.class.getResource("../images/gameStartButtonPressed.png"));
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/gameExitButton.png"));
	private ImageIcon quitButtonEnteredImage = new ImageIcon(
			Main.class.getResource("../images/gameExitButtonPressed.png"));

	private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftImage.png"));
	private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightImage.png"));
	private ImageIcon leftButtonPressImage = new ImageIcon(Main.class.getResource("../images/leftImagePressed.png"));
	private ImageIcon rightButtonPressImage = new ImageIcon(Main.class.getResource("../images/rightImagePressed.png"));

	// easy mode 와 hard mode 버튼
	private ImageIcon easyButtonBasicImage = new ImageIcon(Main.class.getResource("../images/easyButtonBasic.png"));
	private ImageIcon hardButtonBasicImage = new ImageIcon(Main.class.getResource("../images/hardButtonBasic.png"));
	private ImageIcon easyButtonPressImage = new ImageIcon(Main.class.getResource("../images/easyButtonEntered.png"));
	private ImageIcon hardButtonPressImage = new ImageIcon(Main.class.getResource("../images/hardButtonEntered.png"));

	// 게임 실행시 뒤로가기 버튼
	private ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftImage.png"));
	private ImageIcon backButtonPressImage = new ImageIcon(Main.class.getResource("../images/leftImagePressed.png"));

	
	private Image background = new ImageIcon(Main.class.getResource("../images/mainImage.jpg")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	private JButton exitButton = new JButton(exitButtonBasic);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton easyButton = new JButton(easyButtonBasicImage);
	private JButton hardButton = new JButton(hardButtonBasicImage);
	private JButton backButton = new JButton(backButtonBasicImage);
	private int mouseX, mouseY;
	
	private boolean isMainScreen = false;
	private boolean isGameScreen = false;		//게임 화면으로 넘어왔는지 확인하는 변수

	ArrayList<Track> trackList = new ArrayList<Track>();

	// 곡이 선택되는 부분
	private Image titleImage;
	private Image selectedImage;
	private Music selectedMusic;
	private Music introMusic = new Music("intromusic.mp3", true);
	private int nowSelected = 0;// 첫번 째 곡을 선택하도록 0으로 설정

	public static Game game;
	
	// 생성자 만들기
	public DynamicBeat() {
		//프로그램의 로딩이 오래 걸릴 수도 있기 때문에 미리 trackList를 add.
		//add 하지 않은 상태에서 이벤트 처리를 하면 오류 발생할 수도 있기 때문
		// 곡에 대한 정보들 (곡이름, 앨범 사진, 곡 정보 등)
		trackList.add(new Track("newTitle.png", "newIntro.jpg", "newGame.png", "newMusic.mp3", "newMusic.mp3", "New Jeans-Attantion"));
		trackList.add(new Track("redTitle.png", "redIntro.jpg", "redGame.png", "redMusic.mp3", "redMusic.mp3", "Red Velvet-7월 7일"));
		trackList.add(new Track("exoTitle.png", "exoIntro.jpg", "exoGame.png", "exoMusic.mp3", "exoMusic.mp3", "EXO-12월의 기적"));
		
		
		setUndecorated(true);
		setTitle("리듬이 사라졌으면 좋겠어!!");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);

		addKeyListener(new KeyListener());
		
		introMusic.start();


		// 버튼 이미지 조작
		exitButton.setBounds(1200, 10, 60, 60);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Music buttonEnteredMusic = new Music("click.mp3", false);
				buttonEnteredMusic.start();
				exitButton.setIcon(exitButtonPressed);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasic);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0); // Button 이미지 눌렀을 때 프로그램 종료
			}
		});
		add(exitButton);

		// 버튼 이미지 조작 - 게임 시작 버튼 조작
		startButton.setBounds(800, 450, 420, 120);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Music buttonEnteredMusic = new Music("click.mp3", false);
				buttonEnteredMusic.start();
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				enterMain();
			}
		});
		add(startButton);

		// 버튼 이미지 조작 - 게임 종료 버튼 조작
		quitButton.setBounds(40, 450, 420, 120);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Music buttonEnteredMusic = new Music("click.mp3", false);
				buttonEnteredMusic.start();
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0); // Button 이미지 눌렀을 때 프로그램 종료
			}
		});
		add(quitButton);

		// 버튼 이미지 조작 - 왼쪽 버튼 조작
		leftButton.setVisible(false);
		leftButton.setBounds(120, 310, 200, 200);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Music buttonEnteredMusic = new Music("click.mp3", false);
				buttonEnteredMusic.start();
				leftButton.setIcon(leftButtonPressImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 왼쪽 버튼 이벤트 발생
				selectLeft();
			}
		});
		add(leftButton);

		// 버튼 이미지 조작 - 오른쪽 버튼 조작
		rightButton.setVisible(false);
		rightButton.setBounds(1000, 310, 200, 200);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Music buttonEnteredMusic = new Music("click.mp3", false);
				buttonEnteredMusic.start();
				rightButton.setIcon(rightButtonPressImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 오른쪽 버튼 이벤트 발생
				selectRight();
			}
		});
		add(rightButton);

		// 버튼 이미지 조작 - easymode 버튼 조작
		easyButton.setVisible(false);
		easyButton.setBounds(40, 580, 330, 126);
		easyButton.setBorderPainted(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setFocusPainted(false);
		easyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Music buttonEnteredMusic = new Music("click.mp3", false);
				buttonEnteredMusic.start();
				easyButton.setIcon(easyButtonPressImage);
				easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				easyButton.setIcon(easyButtonBasicImage);
				easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// easymode 버튼 이벤트 발생
				gameStart(nowSelected, "Easy");

			}
		});
		add(easyButton);

		// 버튼 이미지 조작 - hardmode 버튼 조작
		hardButton.setVisible(false);
		hardButton.setBounds(930, 580, 330, 126);
		hardButton.setBorderPainted(false);
		hardButton.setContentAreaFilled(false);
		hardButton.setFocusPainted(false);
		hardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Music buttonEnteredMusic = new Music("click.mp3", false);
				buttonEnteredMusic.start();
				hardButton.setIcon(hardButtonPressImage);
				hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hardButton.setIcon(hardButtonBasicImage);
				hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// hardmode 버튼 이벤트 발생
				gameStart(nowSelected, "Hard");

			}
		});
		add(hardButton);

		// 버튼 이미지 조작 - 돌아가기 버튼 조작
		backButton.setVisible(false);
		backButton.setBounds(20, 30, 200, 200);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Music buttonEnteredMusic = new Music("click.mp3", false);
				buttonEnteredMusic.start();
				backButton.setIcon(backButtonPressImage);
				backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(backButtonBasicImage);
				backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				// 돌아가기 버튼 이벤트 발생 (게임 진행 중에 메인으로 다시 돌아가는 이벤트 발생)
				backMain();

			}
		});
		add(backButton);

		// 메뉴바 삽입
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();

			}

		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);

			}
		});
		add(menuBar);

	}

	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D)screenGraphic);
		g.drawImage(screenImage, 0, 0, null);

	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		if (isMainScreen) {
			g.drawImage(selectedImage, 400, 100, null);
			g.drawImage(titleImage, 420, 600, null);
		}
		if(isGameScreen) {
			
			game.screenDraw(g);
			
		}

		paintComponents(g);
		try {
			Thread.sleep(5);
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.repaint();
	}

	public void selectedTrack(int nowSelected) {
		if (selectedMusic != null)
			selectedMusic.close();
		titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage()))
				.getImage();
		selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage()))
				.getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
		selectedMusic.start();

	}

	// 왼쪽 버튼 눌렀을 때 이벤트 처리 함수
	public void selectLeft() {
		if (nowSelected == 0)
			nowSelected = trackList.size() - 1;
		else
			nowSelected--;
		selectedTrack(nowSelected);

	}

	// 오른쪽 버튼 눌렀을 때 이벤트 처리 함수
	public void selectRight() {
		if (nowSelected == trackList.size() - 1)
			nowSelected = 0;
		else
			nowSelected++;
		selectedTrack(nowSelected);
	}

	public void gameStart(int nowSelected, String difficulty) {
		if (selectedMusic != null)
			selectedMusic.close();
		// 화면과 버튼들이 보이지 않게 됨 (게임 시작화면 구성해야 하기 때문)
		isMainScreen = false;
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);

		background = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage()))
				.getImage();
		backButton.setVisible(true);
		isGameScreen = true;
		game = new Game(trackList.get(nowSelected).getTitleName(),difficulty,trackList.get(nowSelected).getGameMusic());
		game.start();
		//키보드 이벤트가 오류 없이 실행 되도록
		setFocusable(true);
		
	}

	public void backMain() {
		isMainScreen = true;
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		background = new ImageIcon(Main.class.getResource("../images/introBackground.png")).getImage();
		backButton.setVisible(false);
		selectedTrack(nowSelected);
		isGameScreen = false;
		//현재 게임중에 실행되는 음악을 종료
		game.close();
		

	}

	public void enterMain() {

		// 게임 시작 이벤트
		startButton.setVisible(false);
		quitButton.setVisible(false);

		background = new ImageIcon(Main.class.getResource("../images/introBackground.png")).getImage();
		isMainScreen = true;
		// 왼쪽 버튼과 오른쪽 버튼 표시하기
		leftButton.setVisible(true);
		rightButton.setVisible(true);

		// easymode, hardmode 버튼 표시하기
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		introMusic.close();
		selectedTrack(0);
	}
}
