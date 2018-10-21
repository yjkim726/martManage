package manage;

import java.util.ArrayList;
import java.util.Scanner;

public class GradeManage {
	Scanner scanner = new Scanner(System.in);
	StudentSearch search = new StudentSearch();
	Student temp = new Student();
	
	void register(){
		System.out.println("*****수강과목*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.print("과목 번호 : "+ subtemp.subNo + " 과목 이름 : " + subtemp.subName + " 학점 : " + subtemp.credit);
			System.out.println(" 교수 : " + subtemp.professor);
		}//end of for
		System.out.println("*******************");
		
	}
	public void inputGrade(ArrayList<Student> studentarr ) {
		
		int index;
		String quit;
		index = search.managerSearch(studentarr);
		temp = studentarr.get(index);
		
		/*System.out.println("*****수강과목*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.print("과목 번호 : "+ subtemp.subNo + " 과목 이름 : " + subtemp.subName + " 학점 : " + subtemp.credit);
			System.out.println(" 교수 : " + subtemp.professor);
		}//end of for
		System.out.println("*******************");*/
		register();
		do {
			System.out.print("성적을 입력할 과목 번호> ");
			index = scanner.nextInt();
			//System.out.println(index);
			for(Subject subtemp : temp.subjectlist){
				if(index==subtemp.subNo){
					int grade;
					System.out.println("선택한 과목 : "+subtemp.subName );
					System.out.print("성적을 입력하세요> ");
					grade = scanner.nextInt();
					while(true) {
						if(grade<0 || grade>100) {
							System.out.print("0~100까지의 성적을 입력해주세요>");
							grade = scanner.nextInt();
						}
						else
							break;
					}
					subtemp.grade = grade;
				}
				//System.out.println("-1");
			}
			System.out.print("다른 과목을 입력하시겠습니까? (y/n)> ");
			quit = scanner.next();
		}while(quit.equals("y"));
	}
		
	void printGrade(ArrayList<Student> studentarr) {
		Student temp = new Student();
		int index;
		index = search.managerSearch(studentarr);
		temp = studentarr.get(index);
		
		System.out.println("*****수강과목*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.println(" 과목 이름 : " + subtemp.subName + " 점수 : " + subtemp.grade);
		}
		System.out.println("*******************");
	}//end of printGrade
//		System.out.println(temp.SNo);
	
	
	public void delGrade(ArrayList<Student>studentarr){
		
		int subNo;
		GradeSearch search = new GradeSearch();
		
		int index = search.gradesearch(studentarr);
		temp = studentarr.get(index);
		
		register();		//메뉴출력
		System.out.println("삭제할 과목을 선택하시오 : ");
		subNo = scanner.nextInt();
		for(Subject subtemp : temp.subjectlist){
			if(subtemp.subNo == subNo){
												/*System.out.println(subNo);
													System.out.println(subtemp.subNo);*/
				index= temp.subjectlist.indexOf(subtemp);
				subtemp = temp.subjectlist.get(index);
				System.out.println("삭제할 과목 : "+ subtemp.subName);
				subtemp.grade = 0;
				break;
			}
			else{
				System.out.println("해당하는 과목번호가 없습니다.");
				break;
				
			}
		}//end of for
		
	}//end of delGrade
	
	public void modgrade(ArrayList<Student>studentarr){
		int index,inputSNo;
		int inputgrade;
		GradeSearch search = new GradeSearch();
		Subject sn = new Subject();
		
		index=search.gradesearch(studentarr);					 //검색
		register(); 										//보기
		System.out.println("점수를 수정할 과목 번호를 입력하시오 : ");
		inputSNo = scanner.nextInt();
		for(Subject subtemp : temp.subjectlist){
			if(subtemp.subNo == inputSNo){
				index = temp.subjectlist.indexOf(subtemp);
				sn=temp.subjectlist.get(index);
				System.out.println("수정된 점수를 입력하세요 : ");
				inputgrade = scanner.nextInt();
				sn.grade = inputgrade;
				break;
			}//end of if
			else{
				System.out.println("잘못된 과목 번호입니다. 다시 입력해주세요");
				break;
			}
		}//end of for
	}//end of modgrade
	
	/*void OpenGrade(ArrayList<Student> studentarr) {
		Student temp = new Student();
		int index;
		index = search.openSearch(studentarr);
		temp = studentarr.get(index);
		
		System.out.println("*****수강과목*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.println(" 과목 이름 : " + subtemp.subName + " 점수 : " + subtemp.grade);
		}
		System.out.println("*******************");
	}end of OpenGrade*/
	
	void openGrade(ArrayList<Student> studentarr) {
		int index;
		index = StudentProfessor.id;
		temp = studentarr.get(index);
		
		System.out.println("*****수강과목*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.print("과목 번호 : "+ subtemp.subNo + " 과목 이름 : " + subtemp.subName + " 학점 : " + subtemp.credit);
			System.out.println(" 교수 : " + subtemp.professor +" 점수 : " + subtemp.grade);
		}
		System.out.println("*******************");
	}

}
