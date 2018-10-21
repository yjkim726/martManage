package calculator.main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * MainProgrm
 * main 메소드를 포함한 클래스
 */
public class MainProgram {
	static Dimension dim =  Toolkit.getDefaultToolkit().getScreenSize();									// 스크린의 크기를 받아오기 위한 Dimension
	static final int SCREEN_WIDTH =  (dim.width);														// 스크린의 횡
	static final int SCREEN_HEIGHT =  (dim.height);														// 스크린의 높이
	public static void main(String args[]) {
		Curriculum.getInstance().loadFile();																
		// curriculum.dat 파일을 불러온다. 자세한 것은 Curriculum -> loadFile() 로
		Connection conn = null;																				// DB와의 연결을 하는 Connection 타입의 변수
		try {
			Class.forName("com.mysql.jdbc.Driver");															// mysql driver와 연결한다.
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acquiredCreditDB","root","");	
			// localhost(자기 자신 127.0.0.1)의 3306포트(mysql 설치시 지정됨)를 사용하는 mysql에 있는 acquiredCreditDB와의 연결을 시도.
			// "root", "" 는 ID와 PASSWORD. 지정된 PASSWORD가 없어서 ""이다.
			// DB사용을 위해서는 MySQL에 acquiredCreditDB가 존재해야 된다.
			System.out.println("데이터베이스 접속 완료");
			new DBControlWindow("DBControl");
			// 데이터 베이스 접속이 완료되면 DB를 쓰고 파일로 옮길 수 있는 창이 나타난다.
			conn.close();																					// DB와의 접속을 끊는다.
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("해당 클래스를 찾을 수 없습니다."+ cnfe.getMessage());
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
		}// end of try
		new MainWindow("취득 학점 계산기");																	// MainWindow를 실행한다.
	}// end of Method(main)
}// end of class(MainProgram)

/**
 * MainWindow
 * 첫 화면
 */
class MainWindow extends JFrame {
	private JPanel newPanel;
	private JLabel newLabel;
	private JButton newButton;
	private Font newFont;
	// JComponent를 사용하기 위한 클래스 변수들
	private CardLayout cardLayout = new CardLayout();												// CardLayout을 사용하기 위한 클래스 변수
	
	MainWindow(String title) {
		super(title);
		getContentPane().setLayout(cardLayout);														// ContentPane 의 Layout을 CardLayout으로 지정한다.

		getContentPane().add("취득 학점 계산기" , new MainWindow_MainPanel(this));
		// '취득 학점 계산기'라는 이름을 가진 1번째 패널을 ContentPane에 추가한다. 
		getContentPane().add("수강 과목 추가하기" , new MainWindow_SubjectAddMenu(this));
		// '수강 과목 추가하기'라는 이름을 가진 2번째 패널을 ContentPane에 추가한다.
		
	}// end of Constructor
	
	public CardLayout getCardLayout() {																// CardLayout을 얻기위한 get
		return cardLayout;
	}// end of Method(getCardLayout)
	
	/*
	 * MainWindow_MainPanel
	 * MainWindow의 1번째 패널
	 */
	class MainWindow_MainPanel extends JPanel implements ActionListener  {
		private JFrame frame;																		
		// MainWindow의 frame을 받아오기 위한 JFrame
		
		MainWindow_MainPanel(JFrame f) {
			frame = f;
			setLayout(new GridLayout(3,1));											// 이 패널은 GridLayout(3행 1열)으로 지정
			
			// 여기서 사용되는 newFont, newPanel 등등..은 MainWindow의 클래스 변수이다.
			
			newFont = new Font("gulim", Font.PLAIN, 20);							// 굴림체의 크기 20으로 폰트를 생성한다.
			newPanel = new JPanel(new FlowLayout(1,30,30));							
			// 작은 패널(GridLayout의 1행 1열)은 FlowLayout(가운데정렬,여백 30,30)으로 지정
			newLabel = new JLabel("정보통신과 취득 학점 계산기");					// Label 생성
			newLabel.setFont(newFont);												// Label 에 생성한 폰트를 지정한다.
			newPanel.add(newLabel);													// 작은 패널에 Label을 추가한다.
			add(newPanel);															// 전체 패널에 작은 패널을 추가한다.
			
			newPanel = new JPanel(new FlowLayout(1,10,10));
			// 작은 패널(GridLayout의 2행 1열)은 FlowLayout(가운데정렬,여백 10,10)으로 지정
			newButton = new JButton("수강 과목 추가하기");							// Button 생성. 내용은 '수강 과목 추가하기'
			newButton.setPreferredSize(new Dimension(150,50));						// Button 의 크기를 150,50으로 조절한다.
			newButton.addActionListener(this);										
			// Button에 ActionListener를 추가한다. ActionListener는 MainWindow_MainPanel 자체가 ActionListener 이므로 this이다.
			// ActionListener의 내용은 actionPerformed 를 참고
			newPanel.add(newButton);												// 작은 패널에 Button을 추가한다.
			add(newPanel);															// 전체 패널에 작은 패널을 추가한다.
			
			newPanel = new JPanel(new FlowLayout(1,10,10));	
			// 작은 패널(GridLayout의 3행 1열)은 FlowLayout(가운데정렬,여백 10,10)으로 지정
			newButton = new JButton("취득 학점 확인하기");							// Button 생성. 내용은 '취득 학점 확인하기'
			newButton.setPreferredSize(new Dimension(150,50));						// Button 의 크기를 150,50으로 조절한다.
			newButton.addActionListener(this);
			// Button에 ActionListener를 추가한다. ActionListener는 MainWindow_MainPanel 자체가 ActionListener 이므로 this이다.
			// ActionListener의 내용은 actionPerformed 를 참고
			newPanel.add(newButton);												// 작은 패널에 Button을 추가한다.
			add(newPanel);															// 전체 패널에 작은 패널을 추가한다.
			
			frame.setSize(300,300);													// 윈도우 크기를 300,300으로 지정한다.
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					// 윈도우를 끌 시 java도 종료된다.
			frame.setLocation(((MainProgram.SCREEN_WIDTH/2)-frame.getWidth()/2),((MainProgram.SCREEN_HEIGHT/2)-frame.getHeight()/2));
			// 윈도우가 정중앙에 위치하도록 이동한다.
			frame.setVisible(true);													// 가시상태로 한다.
			frame.setResizable(false);												// 윈도우 크기 변경 불가 상태
		}//end of Constructor
		
		public void actionPerformed(ActionEvent arg0) {
			// MainWindow_MainPanel의 ActionListener
			switch(arg0.getActionCommand()) {
			// arg0.getActionCommand()는 누른 버튼의 내용이 무엇인지 얻어오는 것이다.
				case "수강 과목 추가하기" :													
					// 누른 버튼의 내용이 '수강 과목 추가하기' 일 경우
					frame.setSize(300,350);													// 윈도우 크기를 300,350으로 지정
					getCardLayout().show(frame.getContentPane(), "수강 과목 추가하기");		// CardLayout을 받아와 2번째 패널로 바꾼다.
					break;
				case "취득 학점 확인하기" :
					// 누른 버튼의 내용이 '취득 학점 확인하기' 일 경우
					new MainWindow_AcquiredCredit("취득 학점 확인하기");					// MainWindow_AcquiredCredit을 생성한다.
					dispose();																// 이 윈도우는 종료
					break;
			}// end of switch
		}
	}
	
	/*
	 * MainWindow_SubjectAddMenu
	 * MainWindow의 2번째 패널
	 */
	class MainWindow_SubjectAddMenu extends JPanel implements ActionListener {
		private JFrame frame;
		// MainWindow의 frame을 받아오기 위한 JFrame
		
		MainWindow_SubjectAddMenu(JFrame f) {
			frame = f;
			setLayout(new GridLayout(7,1));								// 이 패널은 GridLayout(7행 1열)으로 지정
			
			// 여기서 사용되는 newFont, newPanel 등등..은 MainWindow의 클래스 변수이다.
			
			newFont = new Font("gulim", Font.PLAIN, 15);				// 폰트를 굴림체, 크기 15로 생성 PLAIN은 아무상태아님(굵음X,기울임X)
			newPanel = new JPanel(new FlowLayout(1,10,10));				// 작은 패널(1행 1열) FlowLayout으로 생성
			newLabel = new JLabel("메뉴를 선택해주세요");				// Label 생성
			newLabel.setFont(newFont);									// Label의 폰트를 생성한 폰트로 지정
			newPanel.add(newLabel);										// 작은 패널에 Label 추가
			add(newPanel);												// 전체 패널에 작은 패널 추가
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// 작은 패널(2행 1열) FlowLayout으로 생성
			newButton = new JButton("네트워크 정보통신과");				// Button 생성. 내용은 '네트워크 정보통신과'
			newButton.addActionListener(this);							// ActionListener 추가. 자세한 내용은 actionPerformed 참고
			newButton.setPreferredSize(new Dimension(200,30));			// Button 의 크기를 200, 30으로 지정
			newPanel.add(newButton);									// 작은 패널에 Button 추가
			add(newPanel);												// 전체 패널에 작은 패널 추가
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// 작은 패널(3행 1열) FlowLayout으로 생성
			newButton = new JButton("무선 정보통신과");					// Button 생성. 내용은 '무선 정보통신과'
			newButton.addActionListener(this);							// ActionListener 추가. 자세한 내용은 actionPerformed 참고
			newButton.setPreferredSize(new Dimension(200,30));			// Button 의 크기를 200, 30으로 지정
			newPanel.add(newButton);									// 작은 패널에 Button 추가
			add(newPanel);												// 전체 패널에 작은 패널 추가
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// 작은 패널(4행 1열) FlowLayout으로 생성
			newButton = new JButton("타전공");							// Button 생성. 내용은 '타전공'
			newButton.addActionListener(this);							// ActionListener 추가. 자세한 내용은 actionPerformed 참고
			newButton.setPreferredSize(new Dimension(200,30));			// Button 의 크기를 200, 30으로 지정
			newPanel.add(newButton);									// 작은 패널에 Button 추가
			add(newPanel);												// 전체 패널에 작은 패널 추가
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// 작은 패널(5행 1열) FlowLayout으로 생성
			newButton = new JButton("교양");							// Button 생성. 내용은 '교양'
			newButton.addActionListener(this);							// ActionListener 추가. 자세한 내용은 actionPerformed 참고
			newButton.setPreferredSize(new Dimension(200,30));			// Button 의 크기를 200, 30으로 지정
			newPanel.add(newButton);									// 작은 패널에 Button 추가
			add(newPanel);												// 전체 패널에 작은 패널 추가
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// 작은 패널(6행 1열) FlowLayout으로 생성
			newButton = new JButton("수강 내역");						// Button 생성. 내용은 '수강 내역'
			newButton.addActionListener(this);							// ActionListener 추가. 자세한 내용은 actionPerformed 참고
			newButton.setPreferredSize(new Dimension(200,30));			// Button 의 크기를 200, 30으로 지정
			newPanel.add(newButton);									// 작은 패널에 Button 추가
			add(newPanel);												// 전체 패널에 작은 패널 추가
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// 작은 패널(7행 1열) FlowLayout으로 생성
			newButton = new JButton("상위 메뉴");						// Button 생성. 내용은 '상위 메뉴'
			newButton.addActionListener(this);							// ActionListener 추가. 자세한 내용은 actionPerformed 참고
			newButton.setPreferredSize(new Dimension(200,30));			// Button의 크기를 200,30으로 지정
			newPanel.add(newButton);									// 작은 패널에 Button 추가
			add(newPanel);												// 전체 패널에 작은 패널 추가
		}// end of constructor
		
		public void actionPerformed(ActionEvent arg0) {
			// MainWindow_SubjectAddMenu 의 ActionListener
			switch(arg0.getActionCommand()) {
			// arg0.getActionCommand()는 버튼을 눌렀을 시 버튼의 내용을 가져온다.
				case "네트워크 정보통신과" :								// 버튼의 내용이 '네트워크 정보통신과'인 경우
					new SubjectWindow("네트워크 정보통신과 과목 수강",1);	
					// SubjectWindow를 생성하고 Division을 1(네트워크 전공)로 한다.
					break;
				case "무선 정보통신과" :									// 버튼의 내용이 '무선 정보통신과'인 경우
					new SubjectWindow("무선 정보통신과 과목 수강",2);
					// SubjectWindow를 생성하고 Division을 2(무선 전공)로 한다.
					break;
				case "타전공" :												// 버튼의 내용이 '타전공' 인 경우
					new SubjectWindow("타전공 과목 수강",3);
					// SubjectWindow를 생성하고 Division을 3(타전공)으로 한다.
					break;
				case "교양" :												// 버튼의 내용이 '교양' 인 경우
					new SubjectWindow("교양 과목 수강",4);
					// SubjectWindow를 생성하고 Division을 4(교양)으로 한다.
					break;
				case "수강 내역" :											// 버튼의 내용이 '수강 내역'인 경우
					new PrintSubjectListWindow("수강 내역");
					// PringSubjectListWindow를 생성한다.
					break;
				case "상위 메뉴" :											// 버튼의 내용이 '상위 메뉴'인 경우
					frame.setSize(300,300);									// MainWindow의 frame size를 300, 300으로 한다.
					getCardLayout().show(frame.getContentPane(), "취득 학점 계산기");
					// CardLayout을 불러와 MainWindow의 1번째 패널로 바꾼다.
					break;
			}// end of switch
		}// end of method(actioinPerformed)
	}
	
	/*
	 * MainWindow_AcquiredCredit
	 * MainWindow -> 취득 학점 확인하기를 누를 시 생성되는 윈도우
	 */
	class MainWindow_AcquiredCredit extends JFrame implements ActionListener {
		private JTable table;
		private DefaultTableModel model;
		// Table 및 모델 사용을 위한 클래스 변수
		
		MainWindow_AcquiredCredit(String title) {
			super(title);
			setLayout(new GridLayout(2,1));													// 전체 패널을 GridLayout(2행 1열)로 나눈다.
			
			// 여기서 사용되는 newFont, newPanel 등등..은 MainWindow의 클래스 변수이다.
			
			newFont = new Font("gulim", Font.PLAIN, 15);									// 폰트를 굴림, 크기 15, 아무상태없음으로 한다.
			newPanel = new JPanel(new FlowLayout(1,10,10));									// FlowLayout으로 작은 패널 생성
			newLabel = new JLabel("학기 별 취득 학점");										// Label을 생성한다.
			newLabel.setFont(newFont);														// Label을 위 폰트(newFont)로 지정한다.
			newPanel.add(newLabel);															// 작은 패널에 Label을 추가한다.
			add(newPanel);																	// 전체 패널에 작은 패널 추가
			
			String colName[] = { "학기", "전공" , "교양" , "총 취득 학점" };				// 테이블의 열(Column)이 뜻하는 바를 지정해준다.
			
			model = new DefaultTableModel(colName, 0){										// Table 모델을 생성한다. 열은 colName이고 행은 없음.
				public boolean isCellEditable(int rowIndex,int mColIndex) {				
					return false;
					// Table의 변경을 막음.
				}
			};
			
			table = new JTable(model);														// Table을 Table모델을 적용해 생성한다.
			
			for(int i=0 ; i<6 ; i++) {
				// i는 year를 뜻한다. 0(1학년 1학기), 1(1학년 2학기), 2(2학년 1학기), 3(2학년 2학기), 4(3학년 1학기), 5(3학년 2학기)
				addTableComponent(i);
				//	학기 마다 테이블에 추가한다
			}
			
			JScrollPane tableScroll = new JScrollPane(table);								// ScrollPane을 테이블에 추가한다.
			table.setPreferredScrollableViewportSize(new Dimension(320,95));				// 테이블의 크기를 320, 95로 지정한다.
			table.getColumnModel().getColumn(0).setPreferredWidth(130);						// 테이블 1열(학기)의 폭을 130으로 지정한다.
			table.getColumnModel().getColumn(1).setPreferredWidth(50);						// 테이블 2열(전공)의 폭을 50으로 지정한다.
			table.getColumnModel().getColumn(2).setPreferredWidth(50);						// 테이블 3열(교양)의 폭을 50으로 지정한다.
			table.getColumnModel().getColumn(3).setPreferredWidth(70);						// 테이블 4열(총 취득 학점)의 폭을 70으로 지정한다.
			table.getTableHeader().setReorderingAllowed(false);								// 열(Column)의 순서 변경을 금한다.
			table.getTableHeader().setResizingAllowed(false);								// 열(Column)의 폭 변경을 금한다.
			newPanel.add(tableScroll);														// 작은 패널에 ScrollPane을 추가한다.
			add(newPanel);																	// 전체 패널에 작은 패널을 추가한다.
			
			newFont = new Font("gulim", Font.PLAIN, 15);									// 폰트를 굴림, 크기 15, 아무상태없음으로 한다.
			newPanel = new JPanel(new FlowLayout(1,10,10));									// FlowLayout으로 작은 패널 생성
			newLabel = new JLabel("총 취득 학점");											// Label을 생성한다.
			newLabel.setFont(newFont);														// Label의 폰트를 지정한다.
			newPanel.add(newLabel);															// 작은 패널에 Label을 추가한다.
			add(newPanel);																	// 전체 패널에 작은 패널을 추가한다.
			
			model = new DefaultTableModel(colName, 0){										// Table 모델을 생성한다. 행과 열은 위와 마찬가지
				public boolean isCellEditable(int rowIndex,int mColIndex) {
					return false;
					// Table의 변경을 막음.
				}
			};
			
			table = new JTable(model);														// Table을 Table모델을 적용해 생성한다.
			
			addTableComponent(-1);
			// -1은 전체 학기를 의미한다. 전체 학기의 학점을 계산하여 테이블에 추가한다.
			// 자세한 내용은 addTableComponent를 참고
			
			tableScroll = new JScrollPane(table);											// ScrollPane을 테이블에 추가한다.
			table.setPreferredScrollableViewportSize(new Dimension(320,15));				// 테이블의 크기를 320, 15로 지정한다.
			table.getColumnModel().getColumn(0).setPreferredWidth(130);						// 테이블 1열(학기)의 폭을 130으로 지정한다.
			table.getColumnModel().getColumn(1).setPreferredWidth(50);						// 테이블 2열(전공)의 폭을 50으로 지정한다.
			table.getColumnModel().getColumn(2).setPreferredWidth(50);						// 테이블 3열(교양)의 폭을 50으로 지정한다.
			table.getColumnModel().getColumn(3).setPreferredWidth(70);						// 테이블 4열(총 취득 학점)의 폭을 70으로 지정한다.
			table.getTableHeader().setReorderingAllowed(false);								// 열의 순서 변경을 금한다.
			table.getTableHeader().setResizingAllowed(false);								// 열의 폭 변경을 금한다.
			newPanel.add(tableScroll);														// 작은 패널에 ScrollPane을 추가.
			
			newFont = new Font("gulim", Font.PLAIN, 15);									// 폰트를 굴림, 크기 15, 아무상태 없음
			newLabel = new JLabel("전공 최소 학점 : 90점      교양 최소 학점 : 12점");		// Label 생성
			newLabel.setFont(newFont);														// Label의 폰트를 지정
			newPanel.add(newLabel);															// 작은 패널에 Label 추가
			newLabel = new JLabel("졸업 최소 학점 : 120학점");								// Label 생성
			newLabel.setFont(newFont);														// Label의 폰트를 지정
			newPanel.add(newLabel);															// 작은 패널에 Label 추가
			newButton = new JButton("상위 메뉴");											// Button 생성. 내용은 '상위 메뉴'
			newButton.setPreferredSize(new Dimension(180,30));								// Button 의 크기를 180, 30으로 지정
			newButton.addActionListener(this);												// Button 의 ActionListener 지정. 자세한 내용은 actionPerformed 참고
			newPanel.add(newButton);														// 작은 패널에 Button 추가
			add(newPanel);																	// 전체 패널에 작은 패널 추가
			
			this.setSize(350,400);															// 윈도우 크기 350, 400으로 지정
			this.setResizable(false);														// 윈도우 크기 조절 불가
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);							// 윈도우 끌 시 java도 꺼짐
			this.setLocation(((MainProgram.SCREEN_WIDTH/2)-this.getWidth()/2),((MainProgram.SCREEN_HEIGHT/2)-this.getHeight()/2));
			// 윈도우를 중앙에 위치하게 함
			this.setVisible(true);															// 윈도우 가시 상태
		}// end of Constructor
		
		public void actionPerformed(ActionEvent arg0) {
			// '상위 메뉴' 버튼을 누를시 MainWindow를 생성한다.
			new MainWindow("취득 학점 계산기");												// MainWindow 생성
			dispose();																		// 이 윈도우는 제거
		}// end of Method(actionPerformed)
		
		void addTableComponent(int year) {
			// '취득 학점 확인하기'의 테이블을 추가해주는 메소드
			Subject tempSubject;															// Subject 정보를 받아내는 임시 변수
			int majorCredit = 0;															// 전공 학점
			int culturalCredit = 0;															// 교양 학점
			int totalCredit = 0;															// 총 취득 학점
			String yearName = null;															// 학기의 이름을 저장하는 String 변수
			
			if(year == 0) yearName = "1학년 1학기";											// year가 0일 시 yearName이 1학년 1학기 
			else if(year == 1) yearName = "1학년 2학기";									// year가 1일 시 yearName이 1학년 2학기
			else if(year == 2) yearName = "2학년 1학기";									// year가 2일 시 yearName이 2학년 1학기
			else if(year == 3) yearName = "2학년 2학기";									// year가 3일 시 yearName이 2학년 2학기
			else if(year == 4) yearName = "3학년 1학기";									// year가 4일 시 yearName이 3학년 1학기
			else if(year == 5) yearName = "3학년 2학기";									// year가 5일 시 yearName이 3학년 2학기
			else if(year == -1) yearName = "전체 학기";										// year가 -1일 시 yearName이 전체 학기
			
			Iterator<Subject> Itr = SubjectList.getInstance().subjectArr.iterator();		// SubjectList의 subjectArr의 Iterator
			
			while(Itr.hasNext()) {															
				// subjectArr의 다음 element가 있을 시 true로 반복한다,하지만 다음 element가 없을 시 false로 반복을 마친다.
				tempSubject = Itr.next();													// tempSubject에 subjectArr의 다음 element를 가져온다.
				if(year == -1) {
					// year가 만약 -1(전체 학기 일 시)
					if(tempSubject.getDivision() == 0 || tempSubject.getDivision() == 1 || tempSubject.getDivision() == 2 || tempSubject.getDivision() == 3) {
						// tempSubject의 division이 0(공통 전공), 1(네트워크 전공), 2(무선 전공), 3(타전공) 일 시 
						majorCredit += tempSubject.getCredit();								// 전공 학점에 tempSubject의 학점을 더한다.
					}
					else if(tempSubject.getDivision() == 4) {
						// tempSubject의 division이 4(교양) 일 시
						culturalCredit += tempSubject.getCredit();							// 교양 학점에 tempSubject의 학점을 더한다.
					}
					totalCredit += tempSubject.getCredit();									
					// 전공/교양에 상관없이 총 학점 계산을 위해 tempSubject의 학점을 더한다.
				}
				else if(year == tempSubject.getYear()) {
					// year가 전체 학기가 아닐 시 학기마다 계산을 위해 계산을 원하는 학기와 tempSubject의 학기가 같은지 구분한다.
					// 위 year가 만약 2일 경우 2학년 1학기의 과목만을 계산한다.
					if(tempSubject.getDivision() == 0 || tempSubject.getDivision() == 1 || tempSubject.getDivision() == 2 || tempSubject.getDivision() == 3) {
						// tempSubject의 division이 0(공통 전공), 1(네트워크 전공), 2(무선 전공), 3(타전공) 일 시 
						majorCredit += tempSubject.getCredit();								// 전공 학점에 tempSubject의 학점을 더한다.
					}
					else if(tempSubject.getDivision() == 4) {
						// tempSubject의 division이 4(교양) 일 시
						culturalCredit += tempSubject.getCredit();							// 교양 학점에 tempSubject의 학점을 더한다.
					}
					totalCredit += tempSubject.getCredit();
					// 전공/교양에 상관없이 총 학점 계산을 위해 tempSubject의 학점을 더한다.
				}
			}// end of while
			
			String[] component = { yearName, String.valueOf(majorCredit), String.valueOf(culturalCredit), String.valueOf(totalCredit) };
			// 계산한 학점들을 통해서 테이블에 추가할 component를 생성한다. 1열(학기), 2열(전공), 3열(교양), 4열(총 취득 학점)이다.
			// String 배열이므로 모두 String으로 바꿔준다.
			model = (DefaultTableModel) table.getModel();			// model을 사용하기 위해 table의 모델을 받아온다.
			
			model.addRow(component);								// model(table)에 component를 추가한다.
		}// end of Method(addTableComponent)
	}// end of class(MainWindow_AcquiredCredit)
}// end of class(MainWindow)