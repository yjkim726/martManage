package calculator.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
/**
 * DBControl
 * DB를 컨트롤 하기위한 클래스
 */
public class DBControl {
	Connection conn = null;												// DB Connection을 위한 변수
	Statement stmt = null;												// Statement를 사용하기 위한 변수
	
	DBControl() {
	}
	
	void curriculumToDB() {
		// Curriculum 의 curriculumArr에 있는 정보를 DB로 저장시켜주는 Method
		try {
			Subject tempSubject;
			try {
				Class.forName("com.mysql.jdbc.Driver");					// mysql driver를 연결
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acquiredCreditDB","root","");
				// connection에 MySQL의 acquiredCreditDB를 찾아연결
				// "root"는 ID, "" 는 PASSWORD이다.
				System.out.println("데이터베이스 접속 완료");
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("해당 클래스를 찾을 수 없습니다."+ cnfe.getMessage());
			}
			catch (SQLException se) {
				System.out.println(se.getMessage());
			}// end of try
			
			stmt = conn.createStatement();								// stmt에 연결된 DB의 Statement를 생성한다.
			
			Iterator<Subject> curriculumItr = Curriculum.getInstance().curriculumArr.iterator();
			// curriculumArr의 Iterator
			stmt.executeUpdate("delete from curriculum;");				// curriculum이라는 table의 내용을 삭제한다.
			
			while(curriculumItr.hasNext()) {
				// curriculumArr의 마지막 element까지 오면 반복을 그만한다.
				tempSubject = curriculumItr.next();						// tempSubject에 curriculumArr의 element를 넣는다.
				String[] subject = { String.valueOf(tempSubject.getNumber()),
						tempSubject.getName(),
						String.valueOf(tempSubject.getCredit()),
						String.valueOf(tempSubject.getDivision()),
						String.valueOf(tempSubject.getYear())
				};
				// db에 추가할 String형 배열을 생성한다.
				stmt.executeUpdate("insert curriculum (subjectNumber, subjectName, credit, division, year) values('"+
						subject[0] + "','" +
						subject[1] + "','" +
						subject[2] + "','" +
						subject[3] + "','" +
						subject[4] + "');");
				// curriculum 테이블에 추가한다.
				System.out.println(tempSubject.getNumber() +  " " + tempSubject.getName()  + "이(가) 추가되었습니다.");
			}
		}
		
		catch(SQLException se) {
			System.out.println(se.getMessage());
			// 실패시
		}
		finally {							// 예외처리를 하든 안하든 무조건 처리함
			try{
				stmt.close();				// stmt를 닫는다.
			}
			catch(Exception ignored) {
				
			}// end of try
			try{
				conn.close();				// conn을 닫는다.
			}
			catch(Exception ignored) {
				
			}// end of try
		}// end of finally
	}// end of try
	
	void dbToFile(){
		// DB에 있는 내용을 File로 옮겨서 저장하는 Method
		ArrayList<Subject> dbList = new ArrayList<Subject>();		// DB의 테이블에 있는 내용을 받아오는 ArrayList. 파일에 저장된다.
		FileOutputStream fos = null;								// File에 저장하기 위한 Stream
		ObjectOutputStream oos = null;								// 객체 단위로 저장하기 위한 Stream
		try {
			Subject tempSubject;
			try {
				Class.forName("com.mysql.jdbc.Driver");				// MySQL DB Driver에 연결한다.
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acquiredCreditDB","root","");
				// localhost(자기 자신)의 포트 3306번으로 들어가 acquiredCreditDB와 연결한다.
				// "root"는 ID, ""는 PASSWORD이다.
				System.out.println("데이터베이스 접속 완료");
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("해당 클래스를 찾을 수 없습니다."+ cnfe.getMessage());
			}
			catch (SQLException se) {
				System.out.println(se.getMessage());
			}// end of try
			
			stmt = conn.createStatement();							// stmt에 연결된 DB의 Statement를 생성한다.
			
			ResultSet rs = stmt.executeQuery("select subjectNumber, subjectName, credit, division, year from curriculum;");
			// curriculum 테이블에 있는 내용 중 subjectNumber, subjectName, credit, division, year를 읽어온다.
			fos = new FileOutputStream("curriculum.dat");			// ArrayList를 저장할 파일을 지정한다. 파일은 curriculum.dat이다.
			oos = new ObjectOutputStream(fos);						// 객체 단위로 저장할 수 있게끔 지정한다.
			
			while(rs.next()) {
				// rs가 끝날 때 까지 반복한다.
				tempSubject = new Subject(					// Subject를 새로 생성
						rs.getInt("subjectNumber"),			// int형으로 subjectNumber의 내용을 받아온다.
						rs.getString("subjectName"),		// Stirng형으로 subjectName의 내용을 받아온다.
						rs.getInt("credit"),				// int형으로 credit의 내용을 받아온다.
						rs.getInt("division"),				// int형으로 division의 내용을 받아온다.
						rs.getInt("year")					// int형으로 year의 내용을 받아온다.
						);
				
				dbList.add(tempSubject);					// 새로 생성된 tempSubject를 저장할 ArrayList에 추가.
			}// end of while
			
			try {
				oos.writeObject(dbList);					// dbList를 curriculum.dat에 저장한다.
			}
			catch(Exception e){
				e.printStackTrace();
			}// end of try
		}
		catch(SQLException | IOException se) {
			System.out.println(se.getMessage());
		}
		finally {											// 예외처리를 하든 안하든 무조건 실행함
			try{
				fos.close();								// fos 를 닫는다.
				oos.close();								// oos 를 닫는다.
			}
			catch(Exception ignored) {
				
			}// end of try
			try{
				stmt.close();								// stmt 를 닫는다.
			}
			catch(Exception ignored) {
				
			}// end of try
			try{
				conn.close();								// conn 를 닫는다.
			}
			catch(Exception ignored) {
				
			}// end of try
		}// end of finally
	}// end of Method(dbToFile)
}//end of class(DBControl)

class DBControlWindow extends JFrame implements ActionListener {
	// DB Connection이 성공했을 시 생성되는 윈도우
	private JPanel newPanel;
	private JLabel newLabel;
	private JButton newButton;
	private Font newFont;
	DBControl db = new DBControl();
	
	DBControlWindow(String title) {
		super(title);
		JPanel tempPanel;
		setLayout(new GridLayout(3,1));

		
		newFont = new Font("gulim", Font.PLAIN, 20);
		newPanel = new JPanel(new GridLayout(2,1));
		tempPanel = new JPanel(new FlowLayout(1,10,10));
		newLabel = new JLabel("DB Control Menu");
		newLabel.setFont(newFont);
		tempPanel.add(newLabel);
		newPanel.add(tempPanel);
		newFont = new Font("gulim", Font.PLAIN, 11);
		tempPanel = new JPanel(new FlowLayout(1,10,10));
		newLabel = new JLabel("DB 와 Connection이 성공했을 시 생성되는 윈도우");
		newLabel.setFont(newFont);
		tempPanel.add(newLabel);
		newPanel.add(tempPanel);
		add(newPanel);
		
		newPanel = new JPanel(new FlowLayout(1,10,10));
		newButton = new JButton("Curriculum To DB");
		newButton.setPreferredSize(new Dimension(150,30));
		newButton.addActionListener(this);
		newPanel.add(newButton);
		add(newPanel);
		
		newPanel = new JPanel(new FlowLayout(1,10,10));
		newButton = new JButton("DB To File");
		newButton.setPreferredSize(new Dimension(150,30));
		newButton.addActionListener(this);
		newPanel.add(newButton);
		add(newPanel);
		
		this.setSize(300,350);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "Curriculum To DB" :						// 버튼의 내용이 Curriculum To DB 일 경우
			db.curriculumToDB();
			// db 클래스(DBControl)의 curriculumToDB Method를 호출한다.
			break;
		case "DB To File" :								// 버튼의 내용이 DB To File 일 경우
			db.dbToFile();
			// db 클래스(DBControl)의 dbToFile Method를 호출한다.
			break;
		}
		
	}
}