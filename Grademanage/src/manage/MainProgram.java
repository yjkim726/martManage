package manage;

public class MainProgram {
	public static void main(String[] args) {
		/*MenuSelect menuselect = new AllMenuSelect();
		menuselect.select();*/
		MenuSelect menuselect = new StudentProfessor();
		menuselect.select();
	}
}