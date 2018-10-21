package manage;

import java.util.*;

public class GradeSearch {
	Scanner scanner = new Scanner(System.in);
	int index,subNo,inputNo;
	Subject sn;

	
	public int gradesearch(ArrayList<Student>studentarr){

			System.out.println("학사 관리 로그인 : (hint : 학번) ");
			inputNo = scanner.nextInt();
			for(Student temp : studentarr){
				if(temp.SNo == inputNo){
					index=studentarr.indexOf(temp);
					return index;
			}//end of if
			else{
				System.out.println("로그인에 실패하였습니다.");
			}//end of else
		}//end of for
			return -1;
	}//end of gradesearch
	
	public int CourseRegisterSearch(InforTechSubjectList listTemp){
		int courseNo;
		//Student temp = new Student();
		
			System.out.println("수강신청할 과목번호를 입력하세요.");
			courseNo = scanner.nextInt();
			for(Subject subtemp : listTemp.inforSubjectList){
				// System.out.println("for문안에 index : "+index);
				if(courseNo == subtemp.subNo){
				// System.out.println("if문안에 subtemp : "+subtemp.subNo);
				int index = listTemp.inforSubjectList.indexOf(subtemp);
				// System.out.println("if문안에 subNo : "+subtemp.subNo);
				return index;
				}
			}
			System.out.println("과목 번호가 잘못입력 되었습니다. 다시 입력해주세요.");
			return -1;
	}// end of CourseRegisterSearch
}
