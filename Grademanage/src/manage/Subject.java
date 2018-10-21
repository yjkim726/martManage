package manage;


public class Subject {
	int subNo , grade, credit, complete;
	String subName, professor;
	
	Subject(int subNo, int credit, int complete, String subName, String professor) {
		this.subNo = subNo;
		this.subName = subName;
		this.credit = credit;
		this.professor = professor;
		this.complete = complete;
		this.grade = 0;
	}
	//ºó»ý¼ºÀÚ
	Subject() {}
}