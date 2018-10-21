package manage;

import java.util.*;

/*
 * 학생을 관리하기 위한 클래스
 */
class StudentManage {
	StudentSearch search = new StudentSearch();
	Scanner scanner = new Scanner(System.in);
	int index;
	
	/*
	 * 학생 삭제 메소드 
	 */
	void delStudent(ArrayList<Student> studentarr) {
		Student temp = new Student();
		
		index = search.search(studentarr);
		// System.out.println(index);
		if(index==-1){
			System.out.println("검색된 학생이 없습니다");
			return;
		}
		temp = studentarr.get(index);
		System.out.println("삭제된 학생 : " + temp.name);
		studentarr.remove(temp);
		return;
	}
	
	/*
	 * 학생 수정 메소드
	 */
	void modStudent(ArrayList<Student> studentarr) {
		Student temp = new Student();
		
		index = search.search(studentarr);
		// System.out.println(index);
		if(index==-1){
			System.out.println("검색된 학생이 없습니다");
			return;
		}
		temp = studentarr.get(index);
		System.out.println("검색된 학생 : " + temp.name);
		temp.inputSNo(studentarr);	
		temp.inputName();
		temp.inputdepart();
		return;
	}
	
	/*
	 * 학생 추가 메소드
	 */
	void addStudent(ArrayList<Student> studentarr) {
			Student temp = new InforTechStudent();
			
			temp.inputSNo(studentarr);
			temp.inputName();
			temp.inputdepart();

			
			//System.out.println(stutmp.SNo + stutmp.name);
			studentarr.add(temp);
			temp = null;
	}
	
	/*
	 * 학생 목록 메소드
	 */
	void printStudent(ArrayList<Student> studentarr) {	
		for(Student temp : studentarr ) {
			System.out.print("학번 : " + temp.SNo);			
			System.out.print(" | 이름 : " + temp.name);
			System.out.println(" | 학과 : " + temp.department);
		}
	}
}
