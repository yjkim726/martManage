package JDBCProject;
import java.util.*;
public class MartComparedMain {
	//**�߰��ؾ��Һκ�
	// 1��.Ű���� �ڵ� �߰�/����/����
	// 2��.��Ʈ ��ǥ����ڰ� ��ǰ �߰� �Ҷ�   Ű���� �ڵ� ����->�����Ϸ�
	// 3��.�����ڰ�  ��ǰ �����Ҷ� Ű���庰_��ǰ ���̺�� ������ ��¥ Ȯ�� ����
	// 0ȭ.��ٱ��� ��� �ֱ� (GoodsPurchase���� select)
	// 4��&��.~�̷±��, ����/����/�����Ͻðڽ��ϱ�[y/n]?->��ǰ���� -> ���ο��� ���翩�� �������ΰ� 'y'�� �ɰ�� �ش��ϴ� ���̺� �÷�����
	// n�� ������� �ٽ� n ���·� ����
	
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
			//1. ������ ��� 2. ȸ�� ��� 3.ȸ�� ���� 4.ȸ�� Ż��
			int mainMenuSelected = sc.nextInt();
			if(mainMenuSelected==ManagerMemberJoin.manager){
				//1. ����������
				//-> ���⼭ ��з��ڵ��߰�/ �Һз��ڵ��߰�
				//Ű�����ڵ� �߰�
				//��ǰ���� �����̷�,�����̷�, �����̷� Ȯ��
				// �Ǹ��� ���� ���, �Ǹ��� �����̷� Ȯ��
				// ������(ȸ����嵵 ���)
				while(true){
					//�α���
					LoginMember = martJoinSVC.memberLogin();
					if(LoginMember.getMemberseparation().equals("managerMember")){
						// 1. �з��ڵ�      2. Ű���� �ڵ�
						// 3. ��ǰ���� �̷�      4. �Ǹ��� ����     5. ������ ����
						while(true){
							mainMenu.managerMemberMenu();
							int managerSelected = sc.nextInt();
							if(managerSelected==GeneralMemberSelected.classCode){
								// 1.�з��ڵ�
								while(true){
									mainMenu.codeSelected();  //1.��з�  2.�Һз� 3.�ڷ� ����
									int codeSelected = sc.nextInt();
									if(codeSelected==CodeDetail.classcodeDetail){
										// 1.��з�
										mainMenu.classCodeMenu();
										int classCodeDetail = sc.nextInt();
										if(classCodeDetail==ClasscodeDetail.classcodeAdd){
											// 1.��з� �߰�
											martClassCodeSVC.addClassCode();	}
										else if(classCodeDetail==ClasscodeDetail.classcodeModify){
											// 2. ��з� ����
											martClassCodeSVC.updateClassCode();	}
										else if(classCodeDetail==ClasscodeDetail.classcodeDelete){
											// 3. ��з� ����
											martClassCodeSVC.deleteClassCode();	}
										else if(classCodeDetail==ClasscodeDetail.classcodeList){
											// 4. ��з� ���
											martClassCodeSVC.dbListClassCode();		}
										else if(classCodeDetail==ClasscodeDetail.previous){
											// 5. �ڷΰ���
											break;		}
										
									}else if(codeSelected==CodeDetail.subclasscodeDetail){
										// 2.�Һз�
										mainMenu.subClassCodeMenu();
										int subClassCodeDetail = sc.nextInt();
										if(subClassCodeDetail==SubclassCodeDetail.subClasscodeAdd){
											// 1.�Һз� �߰�
											martClassCodeSVC.addSubclassCode();	}
										else if(subClassCodeDetail==SubclassCodeDetail.subClasscodeModify){
											// 2. �Һз� ����
											martClassCodeSVC.updateSubclassCodeCode();			}
										else if(subClassCodeDetail==SubclassCodeDetail.subClasscodeDelete){
											// 3. �Һз� ����
											martClassCodeSVC.deleteSubclassCodeCode();		}
										else if(subClassCodeDetail==SubclassCodeDetail.subClasscodeList){
											// 4. �Һз� ���
											martClassCodeSVC.dbListSubclassCode();			}
										else if(subClassCodeDetail==SubclassCodeDetail.previous){
											// 5. �ڷΰ���
											break;		}
									}else if(codeSelected==CodeDetail.previous){
										 //3. �ڷΰ���
										break; 	}
									else{   System.out.println("1~3���߿��� �����ּ���.");			}//end of else
								}//end of while
							}//end of 1. �з��ڵ�
							else if(managerSelected==GeneralMemberSelected.keywordCode){
								// 2.Ű�����ڵ�
								while(true){
									mainMenu.keywordMenu();   //�޴�â
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
										System.out.println("1~4���߿��� �������ּ���.");  }
								}//end of while
							}//end of 2.Ű���� �ڵ�
							else if(managerSelected==GeneralMemberSelected.goodsInfoRecord){
								// 3.��ǰ���� �̷�
								mainMenu.goodsRecordMenu();
								int goodsRecordSelected = sc.nextInt();
								while(true){
									if(goodsRecordSelected==goodRecord.goodsModificationHistory){
										//��ǰ ���������̷�
										goodsSVC.listGoodsModificationHistory(martClassCodeSVC);
										break;
									}else if(goodsRecordSelected==goodRecord.goodsRestrictRecord){
										//��ǰ�������
										
										goodsSVC.goodsRestrictRecord(martClassCodeSVC);
											
									}else if(goodsRecordSelected==goodRecord.goodsDiscountRecord){
										//��ǰ�����̷�
										goodsSVC.listGoodsDiscountRecord(martClassCodeSVC);
										break;
									}else if(goodsRecordSelected==goodRecord.previous){
										break;
									}else{
										System.out.println("1~4���߿��� �������ּ���.");
									}
								}//end of while
							}//end of 3.��ǰ���� �̷�
							else if(managerSelected==GeneralMemberSelected.sellerConfirmation){
								// 4.�Ǹ��� ����
								sellerConfirmationSVC.sellersConfimationRequest();
								///////////////////���� ���Ŀ� �����̷¿� ���� �޼ҵ�
							}
							else if(managerSelected==GeneralMemberSelected.messageBoxManagement){
								// 5.������ ����
							}
							else if(managerSelected==GeneralMemberSelected.previous){
								break;
							}
						}//end of while(true)
					}//end of equals("manager")
					else{
						System.out.println("Manager�� �ƴϹǷ� ������ �Ұ����մϴ�.");
						break;
					}
				}//end of while(true)
			}//end of �����ڸ� ������ ���
			else if(mainMenuSelected==ManagerMemberJoin.member){
				//2. ȸ�����
				//�Ϲ�ȸ�� �� ��Ʈ��ǥ ������ ����
				// if �Ϲ�ȸ��    ��ǰ���� �� �ּҺ��� �� ���    �Ǹ��� ���� ��û!
				// else ��Ʈ��ǥ�� ���     ��ǰ�߰� ���� ���� 
				while(true){
					mainMenu.generalMartRepresentSelected();
					int memberSelected = sc.nextInt();
					if(memberSelected==GeneralMartRepresentSelect.generalMember){
						//�Ϲ�ȸ�� ���ý�
						while(true){
							//�α���
							LoginMember = martJoinSVC.memberLogin();
							// 1. ��ǰ ���� -> ?  2. ��ٱ���
							// 3. �Ǹ��� ���� ��û       4. ������   5. �ڷΰ���
							if(LoginMember!=null){
								mainMenu.generalMemberMenu();
								int generalMembersSelected = sc.nextInt();
								if(generalMembersSelected==generalMembersSelect.goodsSelected){
									//1. ��ǰ ����
									goodsPurchaseVO = goodsSVC.shoppingBasket(martClassCodeSVC,LoginMember);
								}//end of if 1. ��ǰ ����-> �Ǹ��ڿ��� ���������� ��ɵ� ����
								else if(generalMembersSelected==generalMembersSelect.shoppingBasket){
									//2. ��ٱ���
									goodsSVC.dbListShoppingBasket(goodsPurchaseVO);
								}//end of 2. ��ٱ��� 
								else if(generalMembersSelected==generalMembersSelect.sellerConfimationRequest){
									//3. �Ǹ��� ���� ��û
									sellerConfirmationSVC.sellerConfimationRequest(LoginMember);
								}//end of 3. �Ǹ��� ���� ��û
								
								else if(generalMembersSelected==generalMembersSelect.messageBox){
									while(true){
										mainMenu.messageBoxMenu();
										int messageSelected = sc.nextInt();
										if(messageSelected==messageBoxSelected.sendMessage){
											//1.����������
											messageSVC.addMessage(LoginMember.getMemberId(), martJoinSVC);
										}else if(messageSelected==messageBoxSelected.receiveMessageBox){
											//2.���� ������ 
											messageSVC.receiveMessage(LoginMember.getMemberId());
										}else if(messageSelected==messageBoxSelected.sendMessageBox){
											//3.�߽� ������
											messageSVC.senderMessage(LoginMember.getMemberId());
										}else if(messageSelected==messageBoxSelected.MessageBox){
											//4. ���� ������ �ִ� 50��
											System.out.println("���� ������");
										}else if(messageSelected==messageBoxSelected.previous){
											//5. ��������
											break;
										}
										else{
											System.out.println("���� �߿��� ������ �ּ���.");	}
									}//end of while
								}//end of else if ������
								
								else if(generalMembersSelected==generalMembersSelect.previous){
									break;
								}//end of 5. �ڷΰ���
								break;
							}//end of if �α��� ����� !=null
							else if(LoginMember==null){
								break;
							}
						}//end of while
					}else if(memberSelected==GeneralMartRepresentSelect.martRepresentMember){
						//��Ʈ ��ǥ ���ý�
						MartMemberVO sellerConfirmationMembers = null;
						//�Ǹ��� ������ �Ǿ����� Ȯ��
						while(true){
							sellerConfirmationMembers = martJoinSVC.sellerConfirmation();
							if(sellerConfirmationMembers!=null){
								//returnCount==1 �ΰ��� ������ ���� ���
								while(true){
									//1. ��ǰ�߰�  2. ��ǰ����
									//3. ��ǰ����  4. ������
									mainMenu.goodsAddUpdateDeleteMenu();
									int goodsAddUpdateDeleteSelected = sc.nextInt();
									if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.goodAdd){
										///��ǰ�߰��� �Һз� �ڵ带 �����ؾ��ϹǷ� martClassCodeSVC�� �������
										goodsSVC.addGoods(sellerConfirmationMembers,martClassCodeSVC);
									}//end of if ��ǰ�߰�
									else if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.goodUpdate){
										goodsSVC.updateGoods(martClassCodeSVC);
									}//end of else if ��ǰ ����
									else if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.goodDelete){
										goodsSVC.deleteGoods(martClassCodeSVC);
									}//end of else if ��ǰ ����
									else if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.messageBox){
										while(true){
											mainMenu.messageBoxMenu();
											int messageSelected = sc.nextInt();
											if(messageSelected==messageBoxSelected.sendMessage){
												//1.����������
												messageSVC.addMessage(sellerConfirmationMembers.getMemberId(), martJoinSVC);
											}
											else if(messageSelected==messageBoxSelected.receiveMessageBox){
												//2.���� ������ 
												messageSVC.receiveMessage(sellerConfirmationMembers.getMemberId());
											}
											else if(messageSelected==messageBoxSelected.sendMessageBox){
												//3.�߽� ������
												messageSVC.senderMessage(sellerConfirmationMembers.getMemberId());
											}
											else if(messageSelected==messageBoxSelected.MessageBox){
												//4. ���� ������ �ִ� 50��
												System.out.println("���� ������");
											}
											else if(messageSelected==messageBoxSelected.previous){
												//5. ��������
												break;
											}
											else{
												System.out.println("���� �߿��� ������ �ּ���.");
											}
											
										}//end of while
									}//end of else if ������
									else if(goodsAddUpdateDeleteSelected==GoodAddUpdateDelete.previous){
										//����
										break;
									}//end of else if ����
									else{
										System.out.println("--1~6�� �߿��� �������ּ���");
									}//end of else
								}//end of 1~4 ��ǰ�߰�/����/����/������
							}//end of if �Ǹ��� �������� ���
							else{
								//�������� ���Ѱ��
								break;
							}//end of else ���� ������ ���
						}//end of Inner while
					}//end of ��Ʈ��ǥ���ý�
					else if(memberSelected==GeneralMartRepresentSelect.previous){
						break;
					}
				}//end of while
			}//end of 2.ȸ�����
			else if(mainMenuSelected==ManagerMemberJoin.join){
				//3. ȸ������
				// ȸ������ - ������ / �Ϲ�ȸ�� ����
				mainMenu.managerGeneralMemberSelected();
				int managerMemberSelected = sc.nextInt();		
				if(managerMemberSelected==ManagerMemberSelect.managerMember){
					//������ ȸ�������� �������
					martJoinSVC.joinMember("managerMember");
				}//end of ������ ȸ������
				else if(managerMemberSelected==ManagerMemberSelect.generalMember){
					//�Ϲݸ�� ȸ�� ������ ���� ���
					//managerMemberSelected�� int���̹Ƿ� martJoinSVC.joinMember(String)���̿��� ����ȯ
					martJoinSVC.joinMember("generalMember");
				}//end of �Ϲݸ�� ȸ������
			}//end of 3.ȸ������ 
			else if(mainMenuSelected==ManagerMemberJoin.secession){
				//4. ȸ��Ż��
				//������Ż���... �ϴܺ���
				martJoinSVC.secessionMember();
			}//end of 4.ȸ�� Ż��
			else{
				System.out.println("1~4���߿��� ���ϼ���");
			}//end of 1~4���߿� ���ϼ���
		}//end of while
	}//end of main
}//end of MartComparedMain

class MainMenu{
	public void managerMemberJoinMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ������ ���                   |");
		System.out.println("| 2. ȸ��    ���                   |");
		System.out.println("| 3. ȸ��    ����                   |");
		System.out.println("| 4. ȸ��    Ż��                   |");
		System.out.println("��====================��");
	}

	public void managerMemberMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. �з�    �ڵ�                   |");
		System.out.println("| 2. Ű���� �ڵ�                   |");
		System.out.println("| 3. ��ǰ���� �̷�                |");
		System.out.println("| 4. �Ǹ��� ����                   |");
		System.out.println("| 5. ������ ����                   |");
		System.out.println("| 6. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}
	
	public void codeSelected(){
		System.out.println("��====================��");
		System.out.println("| 1. ��   ��   ��                    |");
		System.out.println("| 2. ��   ��   ��                    |");
		System.out.println("| 3. �ڷ�   ����                    |");
		System.out.println("��====================��");
	}
	
	public void classCodeMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ��з� �߰�                   |");
		System.out.println("| 2. ��з� ����                   |");
		System.out.println("| 3. ��з� ����                   |");
		System.out.println("| 4. ��з� ���                   |");
		System.out.println("| 5. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}
	
	public void subClassCodeMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. �Һз� �߰�                   |");
		System.out.println("| 2. �Һз� ����                   |");
		System.out.println("| 3. �Һз� ����                   |");
		System.out.println("| 4. �Һз� ���                   |");
		System.out.println("| 5. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}
	
	
	public void generalMemberMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ��ǰ    ����                |");
		System.out.println("| 2. ��  ��  �� ��                  |");
		System.out.println("| 3. �Ǹ��� ���� ��û            |");
		System.out.println("| 4. ��    ��   ��                   |");
		System.out.println("| 5. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}
	
	public void goodsAddUpdateDeleteMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ��ǰ    �߰�                   |");
		System.out.println("| 2. ��ǰ    ����                   |");
		System.out.println("| 3. ��ǰ    ����                   |");
		System.out.println("| 4. ��ǰ    �˻�                   |");
		System.out.println("| 5. ��    ��   ��                   |");
		System.out.println("| 6. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}//end of goodsAddUpdateDeleteMenu
	
	public void managerGeneralMemberSelected(){
		System.out.println("��====================��");
		System.out.println("| 1. ������ ����                   |");
		System.out.println("| 2. �Ϲ�ȸ�� ����                |");
		System.out.println("��====================��");
	}//end of managerGeneralMemberSelected
	
	public void generalMartRepresentSelected(){
		System.out.println("��====================��");
		System.out.println("| 1. �Ϲ�ȸ�� ���                |");
		System.out.println("| 2. ��Ʈ��ǥ�� ���             |");
		System.out.println("| 3. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}//end of generalMartRepresentSelected
	
	public void messageBoxMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ���� ������                   |");
		System.out.println("| 2. ���� ������                   |");
		System.out.println("| 3. �߽� ������                   |");
		System.out.println("| 4. ���� ������                   |");
		System.out.println("| 5. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}
	
	public void keywordMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. Ű���� �߰�                   |");
		System.out.println("| 2. Ű���� ����                   |");
		System.out.println("| 3. Ű���� ����                   |");
		System.out.println("| 4. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}
	
	public void goodsRecordMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ��ǰ���������̷�           |");
		System.out.println("| 2. ��ǰ�����̷�                 |");
		System.out.println("| 3. ��ǰ�����̷�                 |");
		System.out.println("| 4. �ڷ�    ����                   |");
		System.out.println("��====================��");
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