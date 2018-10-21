package manage;

// 자바 클래스
import java.util.*;

/*
 * 학생의 학번을 받어 정보를 찾아주는 클래스
 */

/*
 * 학생의 학번을 받아서 학생 List의 인덱스를 반환함
 * List의 인덱스 반환
 */
class StudentSearch {
	int input_SNo, index;
	Scanner scanner = new Scanner(System.in);

	public int openSearch(ArrayList <Student> studentarr){
		int input_SNo,index;
		System.out.println("학사 관리 로그인 : (hint : 학번) ");
		input_SNo = scanner.nextInt();
		for(Student temp : studentarr){
			if(input_SNo == temp.SNo){
				System.out.println("접속에 성공하였습니다.");
				index = studentarr.indexOf(temp);
				//여기다가 이제 상위로 올라갈 메소드
		
				/*배열번호 = 과목번호 return*/
				return index;
			}
		}
		System.out.println("로그인에 실패하였습니다.");
		return -1;
	}
	
	public int managerSearch(ArrayList <Student> studentarr){
		int input_SNo,index;
		System.out.println("학사 관리 로그인 : (hint : 학번) ");
		input_SNo = scanner.nextInt();
		for(Student temp : studentarr){
			if(input_SNo == temp.SNo){
				System.out.println("접속에 성공하였습니다.");
				index = studentarr.indexOf(temp);
				//여기다가 이제 상위로 올라갈 메소드
				return index;
				/*배열번호 = 과목번호 return*/
			}
			else
				System.out.println("로그인에 실패하였습니다.");
				/*과목번호찾기*/ 
				
		}
		return -1;
	}
	
	/*
	 * 학생의 학번을 받아서 학생 List의 인덱스를 반환함
	 * List의 인덱스 반환
	 */
	int search(ArrayList<Student> studentarr) {
		System.out.print("검색할 학생의 학번을 입력하시오 (로그인)> ");
		input_SNo = scanner.nextInt();
		for(Student temp : studentarr ) {
			if(input_SNo == temp.SNo){
				index = studentarr.indexOf(temp);
				// System.out.println(index);
				return index;
			}
		}
		return -1;
		
	}
	
	/*
	 * 학생의 학번을 받아서 중복되는 것이 있는지 확인
	 * 중복되는 것이 있을 경우 1을 반환
	 * 중복되지 않을 경우 -1을 반환
	 */
	int search(ArrayList<Student> studentarr, int input_SNo) {
		// System.out.println(input_SNo);
		for(Student temp : studentarr ) {
			if(input_SNo == temp.SNo){
				// System.out.println(index);
				return 1;
			}
		}
		return -1;
		
	}
}
