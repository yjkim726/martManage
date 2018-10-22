package JDBCProject;
import java.util.*;
public class MartComparedMain {
	//**추가해야할부분
	// 1수.키워드 코드 추가/수정/삭제
	// 2수.마트 대표사용자가 물품 추가 할때   키워드 코드 선택->입점완료
	// 3수.관리자가  물품 관리할때 키워드별_물품 테이블로 입점한 날짜 확인 가능
	// 0화.장바구니 기능 넣기 (GoodsPurchase에서 select)
	// 4목&일.~이력기능, 할인/수정/제재하시겠습니까[y/n]?->물품선택 -> 할인여부 제재여부 삭제여부가 'y'가 될경우 해당하는 테이블에 컬럼생성
	// n를 누를경우 다시 n 상태로 변경
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		MainMenu mainMenu = new MainMenu();
		GoodsSVC goodsSVC = new GoodsSVC();
		MartJoinSVC martJoinSVC = new MartJoinSVC();
		MartClassCodeSVC martClassCodeSVC = new MartClassCodeSVC();
		SellerConfirmationSVC sellerConfirmationSVC = new SellerConfirmationSVC();
		MessageSVC messageSVC = new MessageSVC();
		MartMemberVO LoginMember = null;
		GoodsPurchaseVO goodsPurchaseVO = null;
		KeywordSVC keywordSVC = new KeywordSVC();
		
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
				while(true){
					//로그인
					LoginMember = martJoinSVC.memberLogin();
					if(LoginMember.getMemberseparation().equals("managerMember")){
						// 1. 분류코드      2. 키워드 코드
						// 3. 물품정보 이력      4. 판매자 인증     5. 쪽지함 관리
						while(true){
							mainMenu.managerMemberMenu();
							int managerSelected = sc.nextInt();
							if(managerSelected==GeneralMemberSelected.classCode){
								// 1.분류코드
								while(true){
									mainMenu.codeSelected();  //1.대분류  2.소분류 3.뒤로 가기
									int codeSelected = sc.nextInt();
									if(codeSelected==CodeDetail.classcodeDetail){
										// 1.대분류
										mainMenu.classCodeMenu();
										int classCodeDetail = sc.nextInt();
										if(classCodeDetail==ClasscodeDetail.classcodeAdd){
											// 1.대분류 추가
											martClassCodeSVC.addClassCode();	}
										else if(classCodeDetail==ClasscodeDetail.classcodeModify){
											// 2. 대분류 수정
											martClassCodeSVC.updateClassCode();	}
										else if(classCodeDetail==ClasscodeDetail.classcodeDelete){
											// 3. 대분류 삭제
											martClassCodeSVC.deleteClassCode();	}
										else if(classCodeDetail==ClasscodeDetail.classcodeList){
											// 4. 대분류 목록
											martClassCodeSVC.dbListClassCode();		}
										else if(classCodeDetail==ClasscodeDetail.previous){
											// 5. 뒤로가기
											break;		}
										
									}else if(codeSelected==CodeDetail.subclasscodeDetail){
										// 2.소분류
										mainMenu.subClassCodeMenu();
										int subClassCodeDetail = sc.nextInt();
										if(subClassCodeDetail==SubclassCodeDetail.subClasscodeAdd){
											// 1.소분류 추가
											martClassCodeSVC.addSubclassCode();	}
										else if(subClassCodeDetail==SubclassCodeDetail.subClasscodeModify){
											// 2. 소분류 수정
											martClassCodeSVC.updateSubclassCodeCode();			}
										else if(subClassCodeDetail==SubclassCodeDetail.subClasscodeDelete){
											// 3. 소분류 삭제
											martClassCodeSVC.deleteSubclassCodeCode();		}
										else if(subClassCodeDetail==SubclassCodeDetail.subClasscodeList){
											// 4. 소분류 목록
											martClassCodeSVC.dbListSubclassCode();			}
										else if(subClassCodeDetail==SubclassCodeDetail.previous){
											// 5. 뒤로가기
											break;		}
									}else if(codeSelected==CodeDetail.previous){
										 //3. 뒤로가기
										break; 	}
									else{   System.out.println("1~3번중에서 택해주세요.");			}//end of else
								}//end of while
							}//end of 1. 분류코드
							else if(managerSelected==GeneralMemberSelected.keywordCode){
								// 2.키워드코드
								while(true){
									mainMenu.keywordMenu();   //메뉴창
									int keywordSeleted = sc.nextInt();
									if(keywordSeleted==keyword.keywordAdd){
										keywordSVC.addkeyword(martClassCodeSVC);
									}else if(keywordSeleted==keyword.keywordUpdate){
										keywordSVC.updateKeyword(martClassCodeSVC);
									}else if(keywordSeleted==keyword.keywordDelete){
										keywordSVC.deleteKeyword(martClassCodeSVC);
									}else if(keywordSeleted==keyword.previous){
										break;	
									}else{
										System.out.println("1~4번중에서 선택해주세요.");  }
								}//end of while
							}//end of 2.키워드 코드
							else if(managerSelected==GeneralMemberSelected.goodsInfoRecord){
								// 3.물품정보 이력
								mainMenu.goodsRecordMenu();
								int goodsRecordSelected = sc.nextInt();
								while(true){
									if(goodsRecordSelected==goodRecord.goodsModificationHistory){
										//물품 정보수정이력
										goodsSVC.listGoodsModificationHistory(martClassCodeSVC);
										break;
									}else if(goodsRecordSelected==goodRecord.goodsRestrictRecord){
										//물품제재관리
										
										goodsSVC.goodsRestrictRecord(martClassCodeSVC);
											
									}else if(goodsRecordSelected==goodRecord.goodsDiscountRecord){
										//물품할인이력
										goodsSVC.listGoodsDiscountRecord(martClassCodeSVC);
										break;
									}else if(goodsRecordSelected==goodRecord.previous){
										break;
									}else{
										System.out.println("1~4번중에서 선택해주세요.");
									}
								}//end of while
							}//end of 3.물품정보 이력
							else if(managerSelected==GeneralMemberSelected.sellerConfirmation){
								// 4.판매자 인증
								sellerConfirmationSVC.sellersConfimationRequest();
								///////////////////인증 이후에 인증이력에 들어가는 메소드
							}
							else if(managerSelected==GeneralMemberSelected.messageBoxManagement){
								// 5.쪽지함 관리
							}
							else if(managerSelected==GeneralMemberSelected.previous){
								break;
							}
						}//end of while(true)
					}//end of equals("manager")
					else{
						System.out.println("Manager가 아니므로 접근이 불가능합니다.");
						break;
					}
				}//end of while(true)
			}//end of 관리자를 선택한 경우
			else if(mainMenuSelected==ManagerMemberJoin.member){
				//2. 회원모드
				//일반회원 및 마트대표 관리자 선택
				// if 일반회원    물품구매 및 주소변경 및 취소    판매자 인증 요청!
				// else 마트대표자 모드     물품추가 수정 삭제 
				while(true){
					mainMenu.generalMartRepresentSelected();
					int memberSelected = sc.nextInt();
					if(memberSelected==GeneralMartRepresentSelect.generalMember){
						//일반회원 선택시
						while(true){
							//로그인
							LoginMember = martJoinSVC.memberLogin();
							// 1. 물품 고르기 -> ?  2. 장바구니
							// 3. 판매자 인증 신청       4. 쪽지함   5. 뒤로가기
							if(LoginMember!=null){
								mainMenu.generalMemberMenu();
								int generalMembersSelected = sc.nextInt();
								if(generalMembersSelected==generalMembersSelect.goodsSelected){
									//1. 물품 고르기
									goodsPurchaseVO = goodsSVC.shoppingBasket(martClassCodeSVC,LoginMember);
								}//end of if 1. 물품 고르기-> 판매자에게 쪽지보내기 기능도 있음
								else if(generalMembersSelected==generalMembersSelect.shoppingBasket){
									//2. 장바구니
									goodsSVC.dbListShoppingBasket(goodsPurchaseVO);
								}//end of 2. 장바구니 
								else if(generalMembersSelected==generalMembersSelect.sellerConfimationRequest){
									//3. 판매자 인증 신청
									sellerConfirmationSVC.sellerConfimationRequest(LoginMember);
								}//end of 3. 판매자 인증 신청
								
								else if(generalMembersSelected==generalMembersSelect.messageBox){
									while(true){
										mainMenu.messageBoxMenu();
										int messageSelected = sc.nextInt();
										if(messageSelected==messageBoxSelected.sendMessage){
											//1.쪽지보내기
											messageSVC.addMessage(LoginMember.getMemberId(), martJoinSVC);
										}else if(messageSelected==messageBoxSelected.receiveMessageBox){
											//2.수신 쪽지함 
											messageSVC.receiveMessage(LoginMember.getMemberId());
										}else if(messageSelected==messageBoxSelected.sendMessageBox){
											//3.발신 쪽지함
											messageSVC.senderMessage(LoginMember.getMemberId());
										}else if(messageSelected==messageBoxSelected.MessageBox){
											//4. 쪽지 보관함 최대 50개
											System.out.println("서비스 진행중");
										}else if(messageSelected==messageBoxSelected.previous){
											//5. 이전으로
											break;
										}
										else{
											System.out.println("보기 중에서 선택해 주세요.");	}
									}//end of while
								}//end of else if 쪽지함
								
								else if(generalMembersSelected==generalMembersSelect.previous){
									break;
								}//end of 5. 뒤로가기
								break;
							}//end of if 로그인 멤버가 !=null
							else if(LoginMember==null){
								break;
							}
						}//end of while
					}else if(memberSelected==GeneralMartRepresentSelect.martRepresentMember){
						//마트 대표 선택시
						MartMemberVO sellerConfirmationMembers = null;
						//판매자 인증이 되었는지 확인
						while(true){
							sellerConfirmationMembers = martJoinSVC.sellerConfirmation();
							if(sellerConfirmationMembers!=null){
								//returnCount==1 인경우는 인증을 받은 경우
								while(true){
									//1. 물품추가  2. 물품수정
									//3. 물품삭제  4. 쪽지함
									mainMenu.goodsAddUpdateDeleteMenu();
									int goodsAddUpdateDeleteSelected = sc.nextInt();
									if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.goodAdd){
										///물품추가만 소분류 코드를 연동해야하므로 martClassCodeSVC를 집어넣음
										goodsSVC.addGoods(sellerConfirmationMembers,martClassCodeSVC);
									}//end of if 물품추가
									else if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.goodUpdate){
										goodsSVC.updateGoods(martClassCodeSVC);
									}//end of else if 물품 수정
									else if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.goodDelete){
										goodsSVC.deleteGoods(martClassCodeSVC);
									}//end of else if 물품 삭제
									else if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.messageBox){
										while(true){
											mainMenu.messageBoxMenu();
											int messageSelected = sc.nextInt();
											if(messageSelected==messageBoxSelected.sendMessage){
												//1.쪽지보내기
												messageSVC.addMessage(sellerConfirmationMembers.getMemberId(), martJoinSVC);
											}
											else if(messageSelected==messageBoxSelected.receiveMessageBox){
												//2.수신 쪽지함 
												messageSVC.receiveMessage(sellerConfirmationMembers.getMemberId());
											}
											else if(messageSelected==messageBoxSelected.sendMessageBox){
												//3.발신 쪽지함
												messageSVC.senderMessage(sellerConfirmationMembers.getMemberId());
											}
											else if(messageSelected==messageBoxSelected.MessageBox){
												//4. 쪽지 보관함 최대 50개
												System.out.println("서비스 진행중");
											}
											else if(messageSelected==messageBoxSelected.previous){
												//5. 이전으로
												break;
											}
											else{
												System.out.println("보기 중에서 선택해 주세요.");
											}
											
										}//end of while
									}//end of else if 쪽지함
									else if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.previous){
										//이전
										break;
									}//end of else if 이전
									else{
										System.out.println("--1~6번 중에서 선택해주세요");
									}//end of else
								}//end of 1~4 물품추가/수정/삭제/쪽지함
							}//end of if 판매자 인증받은 경우
							else{
								//인증받지 못한경우
								break;
							}//end of else 인증 못받은 경우
						}//end of Inner while
					}//end of 마트대표선택시
					else if(memberSelected==GeneralMartRepresentSelect.previous){
						break;
					}
				}//end of while
			}//end of 2.회원모드
			else if(mainMenuSelected==ManagerMemberJoin.join){
				//3. 회원가입
				// 회원가입 - 관리자 / 일반회원 구분
				mainMenu.managerGeneralMemberSelected();
				int managerMemberSelected = sc.nextInt();		
				if(managerMemberSelected==ManagerMemberSelect.managerMember){
					//관리자 회원가입을 누를경우
					martJoinSVC.joinMember("managerMember");
				}//end of 관리자 회원가입
				else if(managerMemberSelected==ManagerMemberSelect.generalMember){
					//일반멤버 회원 가입을 누를 경우
					//managerMemberSelected가 int형이므로 martJoinSVC.joinMember(String)형이여서 형변환
					martJoinSVC.joinMember("generalMember");
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

	public void managerMemberMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 분류    코드                   |");
		System.out.println("| 2. 키워드 코드                   |");
		System.out.println("| 3. 물품정보 이력                |");
		System.out.println("| 4. 판매자 인증                   |");
		System.out.println("| 5. 쪽지함 관리                   |");
		System.out.println("| 6. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}
	
	public void codeSelected(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 대   분   류                    |");
		System.out.println("| 2. 소   분   류                    |");
		System.out.println("| 3. 뒤로   가기                    |");
		System.out.println("└====================┘");
	}
	
	public void classCodeMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 대분류 추가                   |");
		System.out.println("| 2. 대분류 수정                   |");
		System.out.println("| 3. 대분류 삭제                   |");
		System.out.println("| 4. 대분류 목록                   |");
		System.out.println("| 5. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}
	
	public void subClassCodeMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 소분류 추가                   |");
		System.out.println("| 2. 소분류 수정                   |");
		System.out.println("| 3. 소분류 삭제                   |");
		System.out.println("| 4. 소분류 목록                   |");
		System.out.println("| 5. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}
	
	
	public void generalMemberMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 물품    고르기                |");
		System.out.println("| 2. 장  바  구 니                  |");
		System.out.println("| 3. 판매자 인증 신청            |");
		System.out.println("| 4. 쪽    지   함                   |");
		System.out.println("| 5. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}
	
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
		System.out.println("| 1. 일반회원 모드                |");
		System.out.println("| 2. 마트대표자 모드             |");
		System.out.println("| 3. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}//end of generalMartRepresentSelected
	
	public void messageBoxMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 쪽지 보내기                   |");
		System.out.println("| 2. 수신 쪽지함                   |");
		System.out.println("| 3. 발신 쪽지함                   |");
		System.out.println("| 4. 쪽지 보관함                   |");
		System.out.println("| 5. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}
	
	public void keywordMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 키워드 추가                   |");
		System.out.println("| 2. 키워드 수정                   |");
		System.out.println("| 3. 키워드 삭제                   |");
		System.out.println("| 4. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}
	
	public void goodsRecordMenu(){
		System.out.println("┌====================┐");
		System.out.println("| 1. 물품정보수정이력           |");
		System.out.println("| 2. 물품제재이력                 |");
		System.out.println("| 3. 물품할인이력                 |");
		System.out.println("| 4. 뒤로    가기                   |");
		System.out.println("└====================┘");
	}
}

class goodRecord{
	static int goodsModificationHistory = 1;
	static int goodsRestrictRecord =2;
	static int goodsDiscountRecord =3;
	static int previous = 4;
}

class keyword{
	static int keywordAdd = 1;
	static int keywordUpdate = 2;
	static int keywordDelete = 3;
	static int previous = 4;
}

class CodeDetail{
	static int classcodeDetail = 1;
	static int subclasscodeDetail = 2;
	static int previous = 3;
}

class ClasscodeDetail{
	static int classcodeAdd = 1;
	static int classcodeModify = 2;
	static int classcodeDelete = 3;
	static int classcodeList = 4;
	static int previous = 5;
} 

class SubclassCodeDetail{
	static int subClasscodeAdd = 1;
	static int subClasscodeModify = 2;
	static int subClasscodeDelete = 3;
	static int subClasscodeList = 4;
	static int previous = 5;
}

class GeneralMemberSelected{
	
	static int classCode =1;
	static int keywordCode = 2;
	static int goodsInfoRecord =3;
	static int sellerConfirmation = 4;
	static int messageBoxManagement =5;
	static int previous = 6;
}

class messageBoxSelected{
	static int sendMessage = 1;
	static int receiveMessageBox = 2;
	static int sendMessageBox = 3;
	static int MessageBox = 4;
	static int previous = 5;
}

class GoodAddUpdateDelete{
	static int goodAdd = 1;
	static int goodUpdate = 2;
	static int goodDelete = 3;
	static int goodList = 4;
	static int messageBox = 5;
	static int previous = 6;
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
	static int previous = 3;
}

class generalMembersSelect{
	static int goodsSelected = 1;
	static int shoppingBasket = 2;
	static int sellerConfimationRequest = 3;
	static int messageBox = 4;
	static int previous = 4;
}