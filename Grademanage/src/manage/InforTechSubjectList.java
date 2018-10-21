package manage;

import java.util.*;

class InforTechSubjectList {
	ArrayList<Subject> inforSubjectList = new ArrayList<Subject>();
	
	public void addSubject() {
		Subject temp = new Subject(2002118, 3, 2, "서버구축", "박중오");
		inforSubjectList.add(temp);
		temp = new Subject(1981046, 3, 2,"마이크로프로세서( I )", "이창주");
		inforSubjectList.add(temp);
		temp = new Subject(1982016, 3, 2,"통신이론", "조채연");
		inforSubjectList.add(temp);
		temp = new Subject(2003016, 4, 1,"CCNA1", "이재설");
		inforSubjectList.add(temp);
		temp = new Subject(1979156, 3, 2,"전자회로( I )", "강신구");
		inforSubjectList.add(temp);
		temp = new Subject(1998117, 3, 2,"TCP/IP", "김교일");
		inforSubjectList.add(temp);
		temp = new Subject(1981038, 3, 1,"전자회로실험", "강신구");
		inforSubjectList.add(temp);
	}
	
	void printList() {
		System.out.println("*****수강 신청 가능 과목*****");
		for(Subject temp : inforSubjectList) {
			System.out.print("과목 번호 : "+ temp.subNo + " 과목 이름 : " + temp.subName + " 학점 : " + temp.credit);
			System.out.println("교수 : " + temp.professor);
		}
		System.out.println("******************************");
	}
	
	void CourseRegister(ArrayList<Student> studentarr){
		GradeSearch gradesearch = new GradeSearch();
		Student stuTemp = new Student();
		Subject subTemp = new Subject();
	    InforTechSubjectList inforTemp = new  InforTechSubjectList();
	    inforTemp.addSubject();
	    
	    int index;
	    
	    do {
		index = gradesearch.CourseRegisterSearch(inforTemp);	 //검색
	    }while(index == -1);
		subTemp = inforTemp.inforSubjectList.get(index);
		stuTemp = studentarr.get(StudentProfessor.id);

		System.out.print("선택한 과목 번호 : "+ subTemp.subNo + " 과목 이름 : " + subTemp.subName + " 학점 : " + subTemp.credit);
		System.out.println(" 교수 : " + subTemp.professor);
		stuTemp.subjectlist.add(subTemp);
	}
}