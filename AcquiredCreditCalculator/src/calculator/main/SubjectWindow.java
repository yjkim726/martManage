package calculator.main;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * SubjectWindow
 * 수강 과목 추가를 해주는 윈도우를 생성하는 클래스
 */
public class SubjectWindow extends JFrame implements ActionListener {
	private JPanel newPanel;
	private JLabel newLabel;
	private JComboBox newComboBox;
	private JButton newButton;
	private JTable table;
	private DefaultTableModel model;
	private int division;
	// 윈도우를 구성하는 것. 클래스 변수로 생성
	
	SubjectWindow(String title, int division) {
		//division에 따라서 추가되는 과목들의 종류가 달라짐
		super(title);
		this.division = division;
		
		getContentPane().setLayout(new BorderLayout());								// 전체를 BorderLayout으로 설정
		
		newPanel = new JPanel(new FlowLayout(0,10,10));								// 작은 패널을 FlowLayout으로 생성
		newLabel = new JLabel("학기 선택 : ");										// Label 생성
		newPanel.add(newLabel);														// 작은 패널에 Label 추가
		String[] comboBoxItem = { "1학년 1학기", "1학년 2학기", "2학년 1학기", "2학년 2학기", "3학년 1학기","3학년 2학기" };
		// comboBox의 Item
		newComboBox = new JComboBox(comboBoxItem);									// ComboBox를 생성
		newPanel.add(newComboBox);													// 작은 패널에 ComboBox를 추가
		newButton = new JButton("확인");											// Button 생성. 내용은 '확인'
		newButton.addActionListener(this);											// Button에 ActionListener 추가. 자세한 내용은 actionPerformed 확인
		newPanel.add(newButton);													// 작은 패널에 Button 추가
		add(newPanel,"North");														// 작은 패널을 North(북)에다가 추가
		
		newPanel = new JPanel(new FlowLayout(1,10,10));								// 작은 패널을 FlowLayout 으로 생성
		newLabel = new JLabel("수강 가능 과목");									// Label 생성
		newPanel.add(newLabel);														// 작은 패널에 Label 추가
		String[] subjectColName = { "과목 번호", "과목 이름", "구분" , "학점" };	// Table의 열(Column) 생성
		
		model = new DefaultTableModel(subjectColName, 0){							// Table Model 생성. 열은 subjectColName이고 행은 없음.
			public boolean isCellEditable(int rowIndex,int mColIndex) {
				return false;
			}
		};// Table 변경 불가
		table = new JTable(model);													// Model을 설정하고 Table 추가
		
		
		JScrollPane tableScroll = new JScrollPane(table);							// Table에 ScrollPane 추가
		table.setPreferredScrollableViewportSize(new Dimension(300,180));			// Table의 크기를 300, 180으로 지정
		table.getColumnModel().getColumn(0).setPreferredWidth(80);					// 1열(과목 번호)의 폭을 80으로 지정
		table.getColumnModel().getColumn(1).setPreferredWidth(120);					// 2열(과목 이름)의 폭을 120으로 지정
		table.getColumnModel().getColumn(2).setPreferredWidth(50);					// 3열(구분)의 폭을 50으로 지정
		table.getColumnModel().getColumn(3).setPreferredWidth(50);					// 4열(학점)의 폭을 50으로 지정
		table.getTableHeader().setReorderingAllowed(false);							// 열 순서 변경 불가
		table.getTableHeader().setResizingAllowed(false);							// 열 크기 변경 불가
		newPanel.add(tableScroll);													// 작은 패널에 Table 추가
		add(newPanel,"Center");														// 작은 패널을 Center(중앙)에 추가
		
		newPanel = new JPanel(new FlowLayout(1,10,10));								// 작은 패널을 FlowLayout 으로 생성
		newButton = new JButton("수강");											// Button 생성. 내용은 '수강'
		newButton.addActionListener(this);											// Button에 ActionListener 추가
		newPanel.add(newButton);													// 작은 패널에 Button 추가
		add(newPanel,"South");														// 작은 패널을 South(남)에 추가
		
		this.setSize(350,400);														// 윈도우 크기를 350, 400으로 지정
		this.setVisible(true);														// 윈도우 가시 상태
		this.setResizable(false);													// 윈도우 크기 조절 불가
		
	}// end of Constructor
	
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		// arg0.getActionCommand()는 버튼을 누를 시 버튼의 내용을 가져온다.
			case "확인" :															// 버튼의 내용이 '확인'일 경우
				tableChange(newComboBox.getSelectedIndex());
				// tableChange 메소드를 호출한다. 
				// 그리고 ComoboBox의 Item의 index를 받아와서 파라미터로 넘겨준다.
				// index는 0(1학년 1학기), 1(1학년 2학기), 2(2학년 1학기), 3(2학년 2학기), 4(3학년 1학기), 5(3학년 2학기)이다.
				break;
			case "수강" :															// 버튼의 내용이 '수강'일 경우
				courseRegister();													// coursRegister 메소드를 호출한다.
				break;
		}
	}
	
	void tableChange(int index) {
		// table을 바꿔주는 Method
		Subject tempSubject, tempCurriculum;										
		// tempSubject는 SubjectList에 추가할 변수이며 tempCurriculum은 Curriculum의 과목을 받아오는 변수이다.
		boolean addCheck = true;													
		// SubjectList에 있으면 addCheck가 false가 되어서 테이블에 추가하지 않는다.
		
		model = (DefaultTableModel) table.getModel();						// table의 Model을 받아옴
		model.setNumRows(0);												// 모델에 있는 행(row)를 0개로 만들어버린다. -> 모든 행을 지움
			
		Iterator<Subject> curriculumItr = Curriculum.getInstance().curriculumArr.iterator();
		// Curriculum의 ArrayList인 curriculumArr의 iterator로 지정함 
		
		if(division == 3) {
			// division 이 3일 경우(타전공일 경우)
			while(curriculumItr.hasNext()) {								
				// curriculum의 다음 element가 있으면 true여서 반복하고 다음 element가 없으면 false여서 반복이 끝난다.
				tempCurriculum = curriculumItr.next();			// tempCurriculum에 curriculum의 다음 element를 받아옴.
				if(tempCurriculum.getDivision() == 3) {			// tempCurriculum의 division이 3일 경우
				String[] addTable = { 							// Table에 추가할 배열
						String.valueOf(tempCurriculum.getNumber()), // tempCurriculum의 subjectNumber을 받아옴 int형이므로 Stirng형으로 전환
						tempCurriculum.getName(),					// tempCurriculum의 subjectName을 받아옴
						tempCurriculum.getDivisionName(),			// tempCurriculum의 divisionName을 받아옴
						String.valueOf(tempCurriculum.getCredit())	// tempCurriculum의 credit을 받아옴 int형이므로 String형으로 전환
						};
				tempCurriculum.setYear(index);					// -1인 타전공의 year를 받아온 index로 바꿔줌
				model.addRow(addTable);							// table에 추가함.
				}
			}// end of while(curriculumItr)
		}
		else {
			// division 이 타전공 외에 다른것일경우
			while(curriculumItr.hasNext()) {
				// curriculum의 다음 element가 있으면 true로 반복하고 다음 element가 없으면 false로 반복이 끝난다.
				tempCurriculum = curriculumItr.next();			// tempCurriculum에 curriculum의 다음 element를 받아옴.
				Iterator<Subject> subjectItr = SubjectList.getInstance().subjectArr.iterator();
				// SubjectList의 ArrayList인 subjectArr의 iterator로 지정함 
				
				while(subjectItr.hasNext()) {
					//subjectArr의 다음 element가 있으면 true여서 반복하고 다음 element가 없으면 false여서 반복이 끝난다.
					// 이 반복은 이미 신청되어 있는 subject는 더이상 추가가 안되게 하기 위해서이다.
					tempSubject = subjectItr.next();		// tempSubject에 subjectArr의 다음 element를 받아옴
					if(tempCurriculum.getNumber() == tempSubject.getNumber()) {
						// tempCurriculum의 subjectNumber와 tempSubject의 subjectNumber가 같을 경우
						addCheck = false;
						// 이미 subjectArr에 있는 subject이므로 테이블에 추가하지 않는다.
						break;
					}
					else {
						addCheck = true;
						// 같지 않을경우 true로 한다.
					}
				}// end of while(subjectItr)
				
				if(addCheck == true) {
					// 위에서 addCheck가 true일 경우 
					if(division == 4) {
						// division이 4일 경우(교양일 경우)
						if(tempCurriculum.getDivision() == 4) {		// tempCurriculum의 division이 4(교양)인 경우
							String[] addTable = { String.valueOf(tempCurriculum.getNumber()),		//테이블에 넣을 행(row) 데이터
									// tempCurriculum의 subjectNumber를 받아옴 int형이므로 String형으로 형변환
									tempCurriculum.getName(),				// tempCurriculum의 subjectName을 받아옴
									tempCurriculum.getDivisionName(),		// tempCurriculum의 divisionName을 받아옴
									String.valueOf(tempCurriculum.getCredit())	// tempCurriculum의 credit을 받아옴 int형이므로 String형으로 형변환
									};
							tempCurriculum.setYear(index); 					// 타전공의 year는 -1이므로 year를 index로 바꾸어준다.
							model.addRow(addTable);							// 테이블에 추가한다.
						}
					}
					else if(division == tempCurriculum.getDivision() || tempCurriculum.getDivision() == 0) {
						// division이 교양이 아니고 tempCurriclum의 division과 같을 경우나, tempCurriculum의 division이 0(공통 전공)일 경우 
						if(index == tempCurriculum.getYear()){				// index(위에서 받아온 
							String[] addTable = { String.valueOf(tempCurriculum.getNumber()),
									tempCurriculum.getName(),
									tempCurriculum.getDivisionName(),
									String.valueOf(tempCurriculum.getCredit())
									};
							// addTable은 위와 똑같다.
							model.addRow(addTable);							// 테이블에 추가한다.
						}
					}
				}// end of if(addcheck)
			} // end of while(curriculum)	
		}// end of while(curriculum)
	}// end of Method(tableChange)	
	
	void courseRegister() {
		// 수강을 누르면 SubjectList에 subject를 추가해주는 Method 
		Subject tempSubject;
		int row = table.getSelectedRow();		// 클릭한 곳의 행(row)의 번호를 받아온다.
		int tempSubjectNumber;
		
		if(row == -1) {							// getSelectedRow가 -1을 return하는 경우는 선택하지 않았을 경우이다.
			return;
			// 선택하지 않았을 경우 Method를 끝낸다.
		}
		
		model = (DefaultTableModel) table.getModel();	// table의 model를 얻어온다.
		
		tempSubjectNumber = Integer.parseInt(String.valueOf(model.getValueAt(row,0)));
		// getValueAt을 통해서 선택한 row의 0번째 column 즉, subjectNumber를 받아온다.
		// 하지만 getValueAt은 Object형으로 반환되므로 String형으로 바꾸었다가 Integer.parseInt로 다시 int형으로 바꾸었다.
		
		Iterator<Subject> curriculumItr = Curriculum.getInstance().curriculumArr.iterator();
		// Curriculum의 curriculumArr 의 iterator
		
		while(curriculumItr.hasNext()) {
			// curriculumItr이 마지막 element까지 가서 false가 나올때 까지 반복
			tempSubject = curriculumItr.next();				// tempSubject에 다음 curriculum의 element를 받아옴
			if(tempSubjectNumber == tempSubject.getNumber()) {
				// geteValueAt으로 받아온 subjectNumber가 tempSubject와 같을 경우
				SubjectList.getInstance().addSubject(tempSubject);
				// SubjectList의 subjectArr에 tempSubject를 추가한다.
				model.removeRow(row);
				// 테이블에서 row행을 삭제한다.
			}
		}
	}
}

/**
 * PrintSubjectListWindow
 * 수강 과목 추가한 내역을 보여주는 윈도우로 보여주는 클래스
 */
class PrintSubjectListWindow extends JFrame implements ActionListener {
	private JPanel newPanel;
	private JLabel newLabel;
	private JComboBox newComboBox;
	private JButton newButton;
	private JTable table;
	private DefaultTableModel model;
	// 윈도우 구성용 클래스 변수
	
	PrintSubjectListWindow(String title) {
		super(title);
		getContentPane().setLayout(new BorderLayout());				// 이 윈도우 전체를 BorderLayout으로 설정
		
		newPanel = new JPanel(new FlowLayout(0,10,10));				// 작은 패널 생성 FlowLayout
		newLabel = new JLabel("학기 선택 : ");						// Label 생성	
		newPanel.add(newLabel);										// 작은 패널에 Label 추가
		String[] comboBoxItem = { "1학년 1학기", "1학년 2학기", "2학년 1학기", "2학년 2학기", "3학년 1학기","3학년 2학기" };
		// ComboBox용 item 배열
		newComboBox = new JComboBox(comboBoxItem);					// ComboBox Item을 이용하여 comboBox 생성
		newPanel.add(newComboBox);									// 작은 패널에 ComboBox 추가
		newButton = new JButton("확인");							// Button 생성. 내용은 '확인'
		newButton.addActionListener(this);							// Button에 ActionListener 추가
		newPanel.add(newButton);									// 작은 패널에 Button 추가
		add(newPanel,"North");										// 전체 패널에 작은 패널을 North에 추가
		
		newPanel = new JPanel(new FlowLayout(1,10,10));				// 작은 패널 생성 FlowLayout
		newLabel = new JLabel("수강 과목");							// Label 생성
		newPanel.add(newLabel);										// 작은 패널에 Label 추가
		String[] subjectColName = { "과목 번호", "과목 이름", "구분" , "학점" };
		// table의 column의 내용이다.
		
		model = new DefaultTableModel(subjectColName, 0){			// subjectColName을 열(Column)으로 행은 없이 Table model 생성
			public boolean isCellEditable(int rowIndex,int mColIndex) {
				return false;
			}
		};// Table 수정 불가능
		
		table = new JTable(model);											// model을 토대로 table생성
		
		
		JScrollPane tableScroll = new JScrollPane(table);					// table에 ScrollPane 추가
		table.setPreferredScrollableViewportSize(new Dimension(300,180));	// table의 크기를 300, 180으로 지정
		table.getColumnModel().getColumn(0).setPreferredWidth(80);			// 1열(과목 번호)의 폭을 80으로 지정
		table.getColumnModel().getColumn(1).setPreferredWidth(120);			// 2열(과목 이름)의 폭을 120으로 지정
		table.getColumnModel().getColumn(2).setPreferredWidth(50);			// 3열(구분)의 폭을 50으로 지정
		table.getColumnModel().getColumn(3).setPreferredWidth(50);			// 4열(학점)의 폭을 50으로 지정
		table.getTableHeader().setReorderingAllowed(false);					// 열(Column)의 순서 이동 불가
		table.getTableHeader().setResizingAllowed(false);					// 열의 크기 조절 불가
		newPanel.add(tableScroll);											// 작은 패널에 table 추가
		add(newPanel,"Center");												// 전체 패널 Center에 작은 패널을 추가
		
		newPanel = new JPanel(new FlowLayout(1,10,10));						// 작은 패널 FlowLayout으로 생성
		newButton = new JButton("수강 취소");								// Button 생성. 내용은 '수강 취소'
		newButton.addActionListener(this);									// ActionListener 추가
		newPanel.add(newButton);											// Button 추가
		add(newPanel,"South");												// 전체 패널 South에 작은 패널을 추가
		
		this.setSize(350,400);												// 윈도우 크기를 350, 400으로 함
		this.setVisible(true);												// 윈도우 가시 상태
		this.setResizable(false);											// 윈도우 크기 조절 불가
	}
	
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		// arg0.getActionCommand()는 버튼을 누를 시 버튼의 내용을 가져온다.
		case "확인" :														// 버튼의 내용이 '확인'일 경우
			tableChange(newComboBox.getSelectedIndex());
			// tableChange 메소드를 호출한다. 
			// 그리고 ComoboBox의 Item의 index를 받아와서 파라미터로 넘겨준다.
			// index는 0(1학년 1학기), 1(1학년 2학기), 2(2학년 1학기), 3(2학년 2학기), 4(3학년 1학기), 5(3학년 2학기)이다.
			break;
		case "수강 취소" :													// 버튼의 내용이 '수강 취소'일 경우
			cancelCourse();
			// cancelCourse 메소드를 호출한다.
			break;
		}
	}
	
	void tableChange(int index) {
		// 학기별 index를 받아 수강한 과목 목록을 추가해주는 메소드
		Subject tempSubject;									// subjectArr의 subject를 받는 임시 변수
		Iterator<Subject> subjectItr = SubjectList.getInstance().subjectArr.iterator();
		// SubjectList의 subejctArr Iterator
		model = (DefaultTableModel) table.getModel();			// table model을 얻어온다.
		model.setNumRows(0);									// table의 row의 갯수를 0으로 만든다 -> table을 비운다.
		
		while(subjectItr.hasNext()) {
			// subjectItr의 마지막까지 확인할 경우 hasNext()가 false가 되어 반복이 끝난다.
			tempSubject = subjectItr.next();					// tempSubject에 subjectArr의 내용을 넣는다.
			
			if(index == tempSubject.getYear() || index == -1) {
				// index로 받은 학기와 tempSubject의 year가 같을경우 , 혹은 index가 -1인경우(교양 및 타전공)
				String[] addTable = {
						String.valueOf(tempSubject.getNumber()),
						tempSubject.getName(),
						tempSubject.getDivisionName(),
						String.valueOf(tempSubject.getCredit())
				};
				model.addRow(addTable);
				// 얻은 tempSubject의 데이터를 통해 table을 추가한다.
			}
		}
	}
	
	void cancelCourse() {
		// 수강 취소를 누르면 지워주는 Method
		Subject tempSubject;
		int row = table.getSelectedRow();				// 클릭한 곳의 row를 받아온다.
		int tempSubjectNumber;
		
		if(row == -1) {
			// row가 -1일 경우는 클릭한 곳이 없는것이다. 클릭한 곳이 없으면 Method를 종료한다.
			return;
		}
		
		model = (DefaultTableModel) table.getModel();	// table의 모델을 받아온다.
		
		tempSubjectNumber = Integer.parseInt(String.valueOf(model.getValueAt(row,0)));
		// 수강
		
		Iterator<Subject> subjectItr = SubjectList.getInstance().subjectArr.iterator();
		// getValueAt을 통해서 선택한 row의 0번째 column 즉, subjectNumber를 받아온다.
		// 하지만 getValueAt은 Object형으로 반환되므로 String형으로 바꾸었다가 Integer.parseInt로 다시 int형으로 바꾸었다.
		
		while(subjectItr.hasNext()) {
			// subjectItr이 끝날때 까지 반복
			tempSubject = subjectItr.next();			// tempSubject에 subjectArr의 element를 넣는다.
			
			if(tempSubjectNumber == tempSubject.getNumber()) {
				// getValueAt 한 subjectNumber가 tempSubject의 subjectNumber와 같을경우
				SubjectList.getInstance().delSubject(tempSubject);
				// subjectArr에서 tempSubject를 삭제한다.
				model.removeRow(row);
				// table에서 row를 삭제한다.
				break;
			}
		}
	}
}
