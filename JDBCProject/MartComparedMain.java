package JDBCProject;
import java.util.*;
public class MartComparedMain {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		MainMenu mainMenu = new MainMenu();
		MartJoinSVC martJoinSVC = new MartJoinSVC();
		while(true){
			mainMenu.managerMemberJoinMenu();
			//1. 관리자 모드 2. 회원 모드 3.회원 가입 4.회원 탈퇴
			int mainMenuSelected = sc.nextInt();
			if(mainMenuSelected==ManagerMemberJoin.manager){
				//1. 관리자접속
				//-> 여기서 대분류코드추가/ 소분류코드추가
				//키워드코드 추가
				//물품정보 수정이력,제재이력, 할인이력 확인
				// 판매자 인증 허용, 판매자 인증이력 확인
				// 쪽지함(회원모드도 허용)

				// 1. 분류코드
				// 2. 키워드 코드
				// 3. 물품정보 이력
				// 4. 판매자 인증
				// 5. 쪽지함 관리
			}
			else if(mainMenuSelected==ManagerMemberJoin.member){
				//2. 회원모드
				//일반회원 및 마트대표 관리자 선택
				// if 일반회원
				// 물품구매 및 주소변경 및 취소
				// 판매자 인증 요청!
				// else 마트대표자 모드
				// 물품추가 수정 삭제 
				mainMenu.generalMartRepresentSelected();
				int memberSelected = sc.nextInt();
				if(memberSelected==GeneralMartRepresentSelect.generalMember){
					//일반회원 선택시
					// 1. 물품 고르기 -> 
					// 2. 장바구니
					// 3. 판매자 인증 신청
					// 4. 쪽지함
				}else if(memberSelected==GeneralMartRepresentSelect.martRepresentMember){
					//마트 대표 선택시
					//판매자 인증이 되었는지 확인
					//1. 물품추가
					//2. 물품수정
					//3. 물품삭제
					//4. 쪽지함
					mainMenu.goodsAddUpdateDeleteMenu();
				}
			}//end of 2.회원모드
			else if(mainMenuSelected==ManagerMemberJoin.join){
				//3. 회원가입
				// 회원가입 - 관리자 / 일반회원 구분
				mainMenu.managerGeneralMemberSelected();
				int managerMemberSelected = sc.nextInt();
				
				if(managerMemberSelected==ManagerMemberSelect.managerMember){
					//관리자 회원가입을 누를경우
					martJoinSVC.joinMember(Integer.toString(managerMemberSelected));
				}//end of 관리자 회원가입
				else if(managerMemberSelected==ManagerMemberSelect.generalMember){
					//일반멤버 회원 가입을 누를 경우
					//managerMemberSelected가 int형이므로 martJoinSVC.joinMember(String)형이여서 형변환
					martJoinSVC.joinMember(Integer.toString(managerMemberSelected));
				}//end of 일반멤버 회원가입
			}//end of 3.회원가입 
			else if(mainMenuSelected==ManagerMemberJoin.secession){
				//4. 회원탈퇴
				//관리자탈퇴는... 일단보류
				martJoinSVC.secessionMember();
			}//end of 4.회원 탈퇴
			else{
				System.out.println("1~4번중에서 택하세요");
			}//end of 1~4번중에 택하세요
		}//end of while
	}//end of main

}//end of MartComparedMain

class MainMenu{
	public void managerMemberJoinMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 관리자 모드                   |");
		System.out.println("| 2. 회원    모드                   |");
		System.out.println("| 3. 회원    가입                   |");
		System.out.println("| 4. 회원    탈퇴                   |");
		System.out.println("└====================┘");
	}
	public void martGoodsDiscountMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 마트    관리                   |");
		System.out.println("| 2. 물품    관리                   |");
		System.out.println("| 3. 할인    관리                   |");
		System.out.println("| 4. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}//end of managerMemberJoin()
	
	public void martAddUpdateDeleteMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 마트    추가                   |");
		System.out.println("| 2. 마트    수정                   |");
		System.out.println("| 3. 마트    삭제                   |");
		System.out.println("| 4. 마트    목록                   |");
		System.out.println("| 5. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}//end of martAddUpdateDelete()
	public void goodsAddUpdateDeleteMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 물품    추가                   |");
		System.out.println("| 2. 물품    수정                   |");
		System.out.println("| 3. 물품    삭제                   |");
		System.out.println("| 4. 물품    검색                   |");
		System.out.println("| 5. 쪽    지   함                   |");
		System.out.println("| 6. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}//end of goodsAddUpdateDeleteMenu
	
	public void managerGeneralMemberSelected(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 관리자 가입                   |");
		System.out.println("| 2. 일반회원 가입                |");
		System.out.println("└====================┘");
	}//end of managerGeneralMemberSelected
	
	public void generalMartRepresentSelected(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 일반화원 모드                |");
		System.out.println("| 2. 마트대표자 모드             |");
		System.out.println("└====================┘");
	}//end of generalMartRepresentSelected
	
	
}//end of MainMenu

class MartAddUpdateDelete{
	static int martAdd = 1;
	static int martUpdate = 2;
	static int martDelete = 3;
	static int martList = 4;
	static int previous = 5;
}

class ManagerAdmin{
	static int mart = 1;
	static int goods = 2;
	static int discount = 3;
	static int previous = 4;
}

class ManagerMemberJoin{
	static int manager = 1;
	static int member = 2;
	static int join =3;
	static int secession = 4;
}
class GoodsAddUpdateDelete{
	static int goodsAdd = 1;
	static int goodsUpdate = 2;
	static int goodsDelete = 3;
	static int goodsSearch = 4;
	static int previous = 5;
}
class ManagerMemberSelect{
	static int managerMember = 1;
	static int generalMember = 2;
}

class GeneralMartRepresentSelect{
	static int generalMember = 1;
	static int martRepresentMember = 2;
}