package manage;

// 자바 클래스
import java.util.*;

/*
 * 학생 정보를 입력하는 클래스
 */
class Student {
	Scanner scanner = new Scanner(System.in);
	StudentSearch search = new StudentSearch();
	ArrayList<Subject> subjectlist = new ArrayList<Subject>();
	
	String name , department;
	int SNo,year;
	// int subject
	
	
	/*
	 * 학번을 입력하는 메소드
	 */
	public void inputSNo(ArrayList<Student> studentarr) {
		int inputSNo;
		System.out.println("학번을 입력하세요 : ");
		inputSNo = scanner.nextInt();
		// System.out.println(SNo);
		while(true) {
			if(search.search(studentarr, inputSNo)==-1) {
				this.SNo = inputSNo;
				return;
			}
			else {
				// System.out.println(search.Search(studentarr, SNo));
				System.out.print("이미 있는 학번입니다. 다시 입력해주세요 : ");
				inputSNo = scanner.nextInt();
			}
		}
	}
	
	/*
	 * 이름을 입력하는 메소드
	 */
	public void inputName() {
		System.out.println("이름을 입력하세요 : ");
		this.name = scanner.next();
	}
	
	public void inputdepart(){
		
		int menu;
		System.out.println("학과 번호를 선택해주세요.");
		System.out.println("1. 정보통신과");
		System.out.println("2. 소프트웨어정보과");
		menu = scanner.nextInt();
		switch(menu){
		case 1 :
			this.department = "정보통신과";
			break;
		case 2 :
			this.department = "소프트웨어정보과";
			break;
		}
	
	}
}

/*
 * 정보통신과를 입력하는 클래스
 */
class InforTechStudent extends Student {
			/*배열로 설정해서 해당 index에 나오는 값을 번호로 설정*/
		
	int index,all;
		// TODO Auto-generated method stub
}


/*
 * 소프트웨어정보과를 입력하는 클래스
 */
class SoftwareInforStudent extends Student {
	int number[];
	SoftwareInforStudent() {
		this.department = "소프트웨어정보과";
	}
}