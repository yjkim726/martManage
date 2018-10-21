package manage;

// 자바 클래스
import java.util.*;

/*
 * 이 인터페이스는 Menu 를 출력하는데 필요한 객체와 학생 ArrayList 선언
 * 메소드 Select() 는 메뉴를 선택하는 메소드
 */
interface MenuSelect {
	ArrayList<Student> studentarr = new ArrayList<Student>();
	ArrayList<Subject> subject = new ArrayList<Subject>();	
	MenuPrint menuprint = new MenuPrint();
	AllMenuSelect allselect = new AllMenuSelect();
	StudentMenuSelect stuselect = new StudentMenuSelect();
	GradeMenuSelect gradeselect = new GradeMenuSelect();
	Scanner scanner = new Scanner(System.in);
	GradeManage grademanage = new GradeManage();
	StudentProfessor studentprofessor = new StudentProfessor();
	OpenGrade openselect = new OpenGrade();
	
	// SubjectList subjectlist = new SubjectList();
	
	void select();
}



/*
 * 최상위 메뉴를 선택 및 출력해주는 클래스
 */
class StudentProfessor implements MenuSelect{
	static int id = 0;
	@Override
	public void select() {
		StudentSearch OpenSearch = new StudentSearch();
		// TODO Auto-generated method stub
		int menu;
		int login;
		
		menuprint.studentprofessor();
		while(true) {
			try {
				menu = scanner.nextInt();
				
				if(menu<1||menu>2) {			
					System.out.print("학생용/교수용 중에서 택해주세요.> ");
				}	
				else {								
					switch(menu) {
					case 1 :
						login = OpenSearch.openSearch(studentarr);
						if(login == -1) {
							studentprofessor.select();
							
						}
						else {
							openselect.select();
							id = login;
						}
						break;
						
					case 2 :						
						allselect.select();				
						break;
					}
				}
			}
			catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				return;
			}
		}
			
		
	}
	
}

/*교수용*/
class AllMenuSelect implements MenuSelect {
	int menu;
	
	/*
	 * 최상위 메뉴를 선택
	 * 1. 학생 관리  2. 성적 관리  3. 종료
	 * 1~3이 아닐 시 반복
	 */
	@Override
	public void select() {
		menuprint.allMenuPrint();		
		System.out.print("메뉴 선택> ");
		while(true) {
			try {
				menu = scanner.nextInt();
				if(menu<1||menu>4) {			
					System.out.print("제대로된 메뉴를 선택해주세요> ");
				}	
				else {								
					switch(menu) {
					case 1 :
						stuselect.select();		
						break;
						
					case 2 :						
						menuprint.gradeMenuPrint();									
						gradeselect.select();	
						break;
						
					case 3 :						
						studentprofessor.select();
						break;
					case 4:
						System.out.print("종료합니다.");
						return;	
					}
				}
			}
			catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				return;
			}
		}
	}	
}


/*
 * 학생 메뉴를 선택 및 출력 해주는 클래스
 * 
 */

class GradeMenuSelect implements MenuSelect{
	int menu;
	@Override
	public void select() {
		System.out.print("메뉴 선택> ");			
	while(true) {
		try {
			menu = scanner.nextInt();
			// System.out.println(menu);
			
			if(menu<1||menu>6) {					
														
				System.out.print("제대로된 메뉴를 선택해주세요> ");
			}	
		else {									
			switch(menu) {
			case 1 :							
								//성적추가
				// subjectlist.addList(studentarr);
				grademanage.inputGrade(studentarr);
				gradeselect.select();
				break;
			
			case 2 :
				grademanage.modgrade(studentarr);
				gradeselect.select();
				break;
									//성적수정
				
			case 3 :							
									//성적삭제
				grademanage.delGrade(studentarr);
				gradeselect.select();
				break;
				
			case 4 :
				grademanage.printGrade(studentarr);
				gradeselect.select();
				break;
				
			case 5 :							
				allselect.select();
				break;				//상위메뉴
				
			}
		}
	}
	catch (Exception e) {
		System.out.println("잘못된 입력입니다.");
		return;
	}
	
}

}
	
}
class StudentMenuSelect implements MenuSelect {			
	int menu;
	
	/*
	 * 학생 메뉴를 선택
	 * 1. 학생 추가  2. 학생 수정  3. 학생 삭제  4. 학생 목록  5. 상위 메뉴
	 * 1~5가 아닐시 반복
	 * 
 	 */
	@Override
	public void select() {
		menuprint.studentMenuPrint();
		System.out.print("메뉴 선택> ");	
		while(true) {
			try {
				StudentManage manage = new StudentManage();
				menu = scanner.nextInt();
				// System.out.println(menu);
				
				if(menu<1||menu>5) {					
															
					System.out.print("제대로된 메뉴를 선택해주세요> ");
				}
				else {									
					switch(menu) {
					case 1 :							
						manage.addStudent(studentarr);
						stuselect.select();
						
						break;
					case 2 :							
						manage.modStudent(studentarr);
						stuselect.select();
						
						break;
					case 3 :							
						manage.delStudent(studentarr);
						stuselect.select();
						
						break;
					case 4 :							
						manage.printStudent(studentarr);
						stuselect.select();
						
						break;
					case 5 :						
						allselect.select();
						break;
					}
				}
			}
			catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				return;
			}
			
		}
		
	}
	
}

/*로그인 성공할경우 성적 열람,수강신청 등 */
class OpenGrade implements MenuSelect{

	

	/*int store; public int stors(){
		this.store = store;
		return store;
	}*/
	@Override
	public void select() {
		int menu;
		// TODO Auto-generated method stub
		menuprint.gradeopen();
		System.out.println("메뉴 선택> ");		
		while(true) {
			try {
				InforTechSubjectList InforTemp = new InforTechSubjectList();
				
				menu = scanner.nextInt();

				// System.out.println(menu);
				
				
				if(menu<1||menu>4) {					
															
					System.out.print("제대로된 메뉴를 선택해주세요> ");
				}
				else {									
					switch(menu) {
					case 1 :							
						grademanage.openGrade(studentarr);
						openselect.select();
						break;
					case 2 :			
						InforTemp.addSubject();
						InforTemp.printList();
						InforTemp.CourseRegister(studentarr);
						openselect.select();
						break;
					case 3 :							
							// 종합검색
						
						break;
					case 4 :							
						studentprofessor.select();
						break;
					}
				}
			}
			catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				return;
			}
			
		}
	}
	
}//end of OpenGrade



