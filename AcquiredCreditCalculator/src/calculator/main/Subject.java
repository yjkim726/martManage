package calculator.main;

import java.io.*;
import java.util.*;

/**
 * Subject
 * 과목 정보를 저장하는 클래스
 */
public class Subject implements java.io.Serializable {							// Serializable은 직렬화로 File 입출력을 위해 사용
	private int subjectNumber;																// 식별 용 고유 과목 번호
																					// 전공은 학교 정보를 참조하였고, 나머지는 임시로 설정해 두었습니다.
	private int credit;																		// 이수 학점
	private int division;																	// 공통 전공(0)/네트워크 전공(1)/무선 전공(2)/타전공(3)/교양(4) 구분
	private int year;																		
	// 학년 학기 구분 1-1(0) 1-2(1) 2-1(2) 2-2(3) 3-1(1) 3-2(2) , 교양 및 타전공은 처음은 -1, 나중에 추가해주면 학년 학기가 적용된다.
	private String subjectName;																// 과목 이름
	
	Subject(int subjectNumber, String subjectName, int credit, int division, int year) {
		this.subjectNumber = subjectNumber;
		this.subjectName = subjectName;
		this.credit = credit;
		this.division = division;
		this.year = year;
	}// end of Constructor
	
	Subject() {
		// Empty Constructor
	}

	/*
	 * Getter
	 */
	int getNumber() {								
		return subjectNumber;
	}// subjectNumber를 return하는 getMethod
	
	int getCredit() {
		return credit;
	}// credit을 return하는 getMethod
	
	String getName() {
		return subjectName;
	}// name을 return하는 getMethod
	
	int getDivision() {
		return division;
	}// division을 return하는 getMethod
	
	int getYear() {
		return year;
	}// year를 return하는 getMethod
	
	String getDivisionName() {
		if(division == 0 || division == 1 || division == 2  || division == 3) return "전공"; 
		else return "교양";
	}// division에 따라서 이름을 반환해주는 getMethod
	
	/*
	 * Setter
	 */
	void setNumber(int subjectNumber) {
		this.subjectNumber = subjectNumber;
	}// number를 설정해주는 setMethod 
	
	void setName(String subjectName) {
		this.subjectName = subjectName;
	}// name을 설정해주는 setMethod
	
	void setCredit(int credit) {
		this.credit = credit;
	}// credit을 설정해주는 setMethod
	
	void setDivision(int division) {
		this.division = division;
	}// division을 설정해주는 setMethod
	
	void setYear(int year) {
		this.year = year;
	}// year를 설정해주는 setMethod
}// end of class(Subject)

/**
 * SubjectList
 * 수강 추가한 과목의 정보를 담아놓는 클래스
 * Singleton 패턴
 */
class SubjectList {
	private static SubjectList subjectList = new SubjectList(); 			// Singleton 패턴. SubjectList는 단 하나만 사용 가능
	ArrayList<Subject> subjectArr = new ArrayList<Subject>();				// 수강 과목 정보를 저장하는 ArrayList
	
	SubjectList() {
		// Empty Constructor
	}
	
	public static SubjectList getInstance() {
		return subjectList;
	}// Singleton 패턴에서 subjectList를 return해준다.
	// 사용은 SubjectList.getInstance() 로 사용한다.
	
	void addSubject(Subject tempSubject) {
		subjectArr.add(tempSubject);
	}// 받아온 tempSubject를 subjectArr에 추가하는 Method
	
	void delSubject(Subject tempSubject) {
		subjectArr.remove(tempSubject);
	}// 받아온 tempSubject를 subjectArr에서 제거하는 Method
	
	Subject getSubject(int subjectNumber){
		// Subject를 찾아서 return해주는 getter 
		Subject tempSubject;
		
		Iterator<Subject> subjectListItr = subjectArr.iterator();
		
		while(subjectListItr.hasNext()) {
			tempSubject = subjectListItr.next();
			if(tempSubject.getNumber() == subjectNumber) {
				// 입력된 subjectNumber와 tempSubject의 subjectNumber가 같을 경우
				return tempSubject;
				// tempSubject를 return 해준다.
			}
		}
		return null;
	}// end of Method(getSubject)
}// end of class(SubjectList)

/**
 * Curriculum
 * 수강 추가가 가능한 과목 목록을 저장하는 클래스
 * Singleton 패턴
 * file(curriculum.dat)에서 정보를 얻어 온다.
 */
class Curriculum {
	private static Curriculum curriculum = new Curriculum(); 					// Singleton 패턴, Curriculum은 단 하나만 사용 가능
	ArrayList<Subject> curriculumArr = new ArrayList<Subject>();				// 수강 가능한 과목이 저장되고, 파일 입출력에 사용되는 ArrayList
	
	Curriculum() {
		// Empty Constructor
	}
	
	public static Curriculum getInstance() {
		return curriculum;
	}// Singleton 패턴에서 접근하기 위해 사용하는 Method
	
	void addSubject(int subjectNumber, String subjectName, int credit, int division, int year) {
		Subject tempSubject =  new Subject(subjectNumber, subjectName, credit, division, year);
		curriculumArr.add(tempSubject);
	}// tempSubject를 받은 파라미터를 토대로 curriculumArr에 새로 생성해주는 Method  
	
	void modSubject(Subject subject,int subjectNumber, String subjectName, int credit, int division,int year) {
		subject.setNumber(subjectNumber);
		subject.setName(subjectName);
		subject.setCredit(credit);
		subject.setDivision(division);
		subject.setYear(year);
	}// setter를 사용해 subject의 내용을 바꿔주는 Method
	
	void delSubject(Subject subject) {
		curriculumArr.remove(subject);
	}// curriculumArr 안에 있는 subject를 삭제하는 Method
	
	Subject getSubject(int subjectNumber){
		// 받은 subjectNumber로 curriculum에 있는 subject를 찾아내는 Method
		Subject tempSubject;
		Iterator<Subject> subjectListItr = curriculumArr.iterator();
		
		while(subjectListItr.hasNext()) {
			tempSubject = subjectListItr.next();
			if(tempSubject.getNumber() == subjectNumber) {
				// 입력한 subejctNumber와 tempSubject의 subjectNumber가 같을경우
				return tempSubject;
				// tempSubject를 return 해준다.
			}
		}
		return null;
	}// end of Method(getSubject)
	
	void loadFile() {
		// 파일(curriculum.dat)을 읽어와주는 Method
		FileInputStream fis = null;												// 파일을 읽어오기 위한 Stream
		ObjectInputStream ois = null;											// 파일에서 객체(Object)를 읽어오기 위한 Stream
		try{
			fis = new FileInputStream("curriculum.dat");						// File을 'curriculum.dat'로 지정한다.
			ois = new ObjectInputStream(fis);									// ois는 File의 object를 읽어온다.
			
			curriculumArr =(ArrayList<Subject>) ois.readObject();				// curriculumArr는 ois에서 읽어온 object이다.
			// curriculum.dat에 저장되는 것은 ArrayList이므로 읽어온 객체(Object형이다)는 ArrayList로 형변환한다.
			// Generics을 사용하였는데 ois.readObject()가 Object형이기에 warning이 뜬 상태 
		}
		catch(Exception e){
			System.out.println("File Load Failure");
			// 파일 load 실패시 기존 커리큘럼으로 대체
			// addSubject( subjectNumber , subjectName, credit, division, year)
			
			addSubject(1979133,"디지탈공학", 3 ,0, 0);
			addSubject(1981026,"공업수학", 3, 0, 0);
			addSubject(1985052,"기초실험", 2, 0, 0);
			addSubject(1992020,"회로시뮬레이션", 2, 0, 0);
			addSubject(1993019,"회로이론", 3, 0, 0);
			addSubject(1997058,"정보통신개론", 2, 0, 0);
			addSubject(2007005,"프로그래밍입문", 3, 0, 0);
			// 1학년 1학기
			addSubject(1981035,"DATA통신", 3, 0, 1);
			addSubject(1989038,"디지탈실험", 2, 0, 1);
			addSubject(1990002,"CAD", 2, 0, 1);
			addSubject(1997072,"C-프로그래밍", 3, 0, 1);
			addSubject(1998017,"디지탈공학응용", 2, 0, 1);
			addSubject(2002104,"회로실험", 2, 0, 1);
			addSubject(2002105,"회로해석", 3, 0, 1);
			addSubject(2006028,"시스템운영", 2, 1, 1);
			addSubject(1983011,"전자자기학", 3, 2, 1);
			// 1학년 2학기
			addSubject(1979156,"전자회로(Ⅰ)", 3, 0, 2);
			addSubject(1981038,"전자회로실험", 2, 0, 2);
			addSubject(1981046,"마이크로프로세서(Ⅰ)", 3, 0, 2);
			addSubject(1982016,"통신이론", 3, 0, 2);
			addSubject(1998117,"TCP/IP", 3, 0, 2);
			addSubject(2002118,"서버구축", 3, 1, 2);
			addSubject(2003016,"CCNA1", 4, 1, 2);
			addSubject(2002107,"전자장론", 3, 2, 2);
			addSubject(1993017,"통신기기실험", 2, 2, 2);
			// 2학년 1학기
			addSubject(1981047,"마이크로프로세서(Ⅱ)", 3, 0, 3);
			addSubject(2002109,"디지털통신", 3, 0, 3);
			addSubject(2012002,"디지털회로설계", 3, 0, 3);
			addSubject(2003017,"CCNA2", 4, 1, 3);
			addSubject(2006027,"JAVA 프로그래밍", 3, 1, 3);
			addSubject(2011051,"서버보안", 3, 1, 3);
			addSubject(1979157,"전자회로(Ⅱ)", 3, 2, 3);
			addSubject(1996017,"디지탈통신실험", 2, 2, 3);
			addSubject(2006023,"디지털선로", 3, 2, 3);
			addSubject(2009018,"초고주파 공학 및 실습", 3, 2, 3);
			// 2학년 2학기
			addSubject(1998076,"실험 프로젝트", 3, 0, 4);
			addSubject(1998023,"멀티미디어통신", 3, 1, 4);
			addSubject(2003018,"CCNA3", 4, 1, 4);
			addSubject(2000036,"웹프로그래밍", 3, 1, 4);
			addSubject(2006029,"정보보안", 3, 1, 4);
			addSubject(2006031,"무선 LAN", 3, 1, 4);
			addSubject(1999059,"무선통신시스템", 3, 2, 4);
			addSubject(2006024,"RF 회로", 3, 2, 4);
			addSubject(2006025,"안테나공학 및 실습", 3, 2, 4);
			addSubject(2006026,"통신시뮬레이션", 3, 2, 4);
			// 3학년 1학기
			addSubject(1984041,"현장 실습", 3, 0, 5);
			addSubject(1994030,"시스템프로젝트", 3, 0, 5);
			addSubject(1989064,"전공 영어", 2, 0, 5);
			addSubject(2006032,"차세대 통신망", 3, 0, 5);
			addSubject(2012003,"실용글쓰기", 3, 0, 5);
			addSubject(2003019,"CCNA4", 4, 1, 5);
			addSubject(2002111,"이동통신시스템", 3, 2, 5);
			addSubject(2002112,"위성통신시스템", 3, 2, 5);
			// 3학년 2학기
			addSubject(9000001,"타전공 2학점", 2, 3, -1);
			addSubject(9000002,"타전공 3학점", 3, 3, -1);
			addSubject(9000003,"타전공 4학점", 4, 3, -1);
			// 타전공
			addSubject(8000001,"언어생활과문장", 2, 4, -1);
			addSubject(8000002,"대화의기법", 2, 4, -1);
			addSubject(8000003,"논리와비판적사고", 2, 4, -1);
			addSubject(8000004,"인성과리더십", 2, 4, -1);
			addSubject(8000005,"인감심리의이해", 2, 4, -1);
			addSubject(8000006,"영어회화", 2, 4, -1);
			addSubject(8000007,"초급영작문", 2, 4, -1);
			addSubject(8000008,"영어듣기", 2, 4, -1);
			addSubject(8000009,"영어발음클리닉", 2, 4, -1);
			addSubject(8000010,"취업영어", 2, 4, -1);
			addSubject(8000011,"중급영어회화", 2, 4, -1);
			addSubject(8000012,"기초일본어회화", 2, 4, -1);
			addSubject(8000013,"기초중국어회화", 2, 4, -1);
			addSubject(8000014,"한자의이해", 2, 4, -1);
			addSubject(8000015,"21세기트랜드와경영", 2, 4, -1);
			addSubject(8000016,"비전설계와자기경영", 2, 4, -1);
			addSubject(8000017,"창의와창조기법", 2, 4, -1);
			addSubject(8000018,"문학의이해", 2, 4, -1);
			addSubject(8000019,"생활속의수학", 2, 4, -1);
			addSubject(8000020,"영화와음악", 2, 4, -1);
			addSubject(8000021,"여성과문화", 2, 4, -1);
			addSubject(8000022,"문화이야기와역사읽기", 2, 4, -1);
			addSubject(8000023,"일상생활사", 2, 4, -1);
			addSubject(8000024,"현대사회와인간", 2, 4, -1);
			addSubject(8000025,"생활스포츠", 2, 4, -1);
			addSubject(8000026,"취업과진로", 2, 4, -1);
			addSubject(8000027,"기업가정신과창업", 2, 4, -1);
			addSubject(8000028,"키워드로 본 IT의 현재와미래", 2, 4, -1);
			// 밑으로는 학점 교류 과목
			addSubject(7000001,"기업경영과 비즈니스 전략", 2, 4, -1);
			addSubject(7000002,"생태친화적 기업경영", 2, 4, -1);
			addSubject(7000003,"상상과 창조", 2, 4, -1);
			addSubject(7000004,"문화콘텐츠 스토리텔링 전략", 2, 4, -1);
			addSubject(7000005,"문화기술과 사회변동", 2, 4, -1);
			addSubject(7000006,"문화콘텐츠 마케팅 전략의 수립과 집행", 2, 4, -1);
			addSubject(7000007,"21세기 기업의 인재상", 2, 4, -1);
			addSubject(7000008,"색채심리와 현대생활", 3, 4, -1);
			addSubject(7000009,"글로벌 시대의 예술과 가치", 3, 4, -1);
			addSubject(7000010,"음식과 세계문화", 3, 4, -1);
			addSubject(7000011,"웰니스와 삶의 질", 2, 4, -1);
			addSubject(7000012,"UCC 기획, 제작, 편집 및 활용", 3, 4, -1);
			addSubject(7000013,"그린 IT의 이해", 3, 4, -1);
			addSubject(7000014,"고객관계관리 전략", 3, 4, -1);
			addSubject(7000015,"성공하는 문화콘텐츠 소재 개발 전략", 2, 4, -1);
			addSubject(7000016,"수학과 문화", 2, 4, -1);
			addSubject(7000017,"생활 속의 계약과 협상", 3, 4, -1);
			addSubject(7000018,"전략적 의사결정과 문제해결", 2, 4, -1);
			// 교양
		}finally{						// Exception이 발생하건 안하건 처리함.
			if(fis != null) {
				try{fis.close();}		// fis 종료
				catch(IOException e){} // end of try
			}
			if(ois != null) {
				try{ois.close();} 		// ois 종료
				catch(IOException e){} // end of try
			}
		}// end of try
	}// end of Method(loadFile)
}// end of class(Currciulum)