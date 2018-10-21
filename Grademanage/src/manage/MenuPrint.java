package manage;

/*
 * 메뉴 출력을 위한 클래스
 */
public class MenuPrint {
	/*
	 * 최상위 메뉴 출력 메소드
	 */

	void studentprofessor(){
		System.out.println("다음 학생용/교사용 선택해주시오.");
		System.out.println("학생용 (1)");
		System.out.println("교수용 (2)");	
	}
	 void departmentPrint(){
			System.out.println("학과를 선택하시오.");
			System.out.println("1. 정보통신과");
			System.out.println("2. 소프트웨어 정보과");
			return;
		}
	
	void allMenuPrint() {
		System.out.println("----------MENU----------");
		System.out.println("1. 학생 관리");
		System.out.println("2. 성적 관리");
		System.out.println("3. 상위 메뉴");
		System.out.println("4. 종료");
		System.out.println("------------------------");
		return;
	}
	
	/*
	 * 학생 메뉴 출력 메소드
	 */
	void studentMenuPrint() {
		System.out.println("=========STUDENT=========");
		System.out.println("1. 학생 추가");
		System.out.println("2. 학생 수정");
		System.out.println("3. 학생 삭제");
		System.out.println("4. 학생 목록");
		System.out.println("5. 상위 메뉴");
		System.out.println("=========================");
		return;
	}
	
	/*
	 * 성적 메뉴 출력 메소드
	 */
	void gradeMenuPrint() {
		System.out.println("==========GRADE==========");
		System.out.println("1. 성적 추가");
		System.out.println("2. 성적 수정");
		System.out.println("3. 성적 삭제");
		System.out.println("4. 성적 보기");
		System.out.println("5. 상위 메뉴");
		System.out.println("=========================");
		return;
	}
	
	void yearMenuPrint(){
		System.out.println("==========학년선택==========");
		System.out.println("1학년일경우  : (1)");
		System.out.println("2학년일경우  : (2)");
		System.out.println("3학년일경우  : (3)");
		System.out.println("=========================");
	}
	
	void gradeopen(){
		System.out.println("==========OPEN==========");
		System.out.println("1. 성적 열람");
		System.out.println("2. 수강 신청");
		System.out.println("3. 종합 정보");
		System.out.println("4. 상위 메뉴");
		System.out.println("=========================");
	}
	
}