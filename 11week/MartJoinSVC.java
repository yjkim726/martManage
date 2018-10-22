package JDBCProject;
import static JDBCProject.JdbcUtil.getConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MartJoinSVC {

	Connection con =null;
	PreparedStatement pstmt = null;
	Scanner sc = new Scanner(System.in);
	ArrayList<MartMemberVO> arrayMarketMember =new ArrayList<MartMemberVO>();
	
	//private String memberId;
	//private String memberseparation;
	//private String memberPassword;
	//private String memberName;
	//private String memberPhone;
	//private String memberEmail;
	//private String termsAgreement; //�������
	//private String memberAddress;
	//private String memberJoinDate;
	
	//ȸ������
	//if memberseparation�̴� �Ϲ�ȸ������
	public void joinMember(String generalMember){
		System.out.println("*****ȸ������â*****");
		System.out.print("���̵� : ");
		String memberId = sc.next();
		System.out.print("��й�ȣ : ");
		String memberPassword = sc.next();
		System.out.print("�̸� : ");
		String memberName = sc.next();
		System.out.print("��ȭ��ȣ : ");
		String memberPhone = sc.next();
		System.out.print("�̸��� : ");
		String memberEmail = sc.next();
		System.out.print("�ּ� : ");
		String memberAddress = sc.next();
		System.out.println("--��� ���Ǹ� �㰡���� �����ôٸ� ������ ��ҵɼ� �ֽ��ϴ�. ");
		System.out.println("--�����Ͻðڽ��ϱ�? [Y/N]");
		String termsAgreement = sc.next();
		if(termsAgreement.equals("Y")||termsAgreement.equals("y")){
			//��������
			//id �ߺ�Ȯ��
			System.out.println("--���̵� �ߺ�Ȯ�� ���Դϴ�.");
			MartMemberVO overLapmartMember = overLapJoinIdCheck(memberId);
			if(overLapmartMember==null){
				//�ߺ��� id �� ���� ��� ȸ������ ����
				MartMemberVO martMember = new MartMemberVO(memberId,generalMember,memberPassword,memberName,memberPhone,
						memberEmail,memberAddress ); //���ο� MartMemberVO ��ü ����
				//MartMemberVO ������
				//MartMemberVO(String memberId,  String memberseparation,String memberPassword, String memberName, 
				//String memberPhone, String memberEmail,  String memberAddress)
				dbInsertMember(martMember); //DB�� ������ �Է�
				
				//������ ��¥ �Է��� �ȵǾ� �����Ƿ� ��¥�Է±��� �Ȱɷ� ArrayList�� �߰�
				MartMemberVO arrayListMember =overLapJoinIdCheck(martMember.getMemberId());
				joinMember(arrayListMember); //ArrayList�� �߰�
				
			}
		}//end of if
		else{
				//�������
		}//end of else	
	}//end of joinMember;
	
	//ArrayList�� Member�� �߰�
		public void joinMember(MartMemberVO martMember){
			arrayMarketMember.add(martMember);
		}
	
	//ȸ�����Խ� id �ߺ�Ȯ�� �ϴ� �޼ҵ�
		//���߿� �ʿ��ϸ� String���� ��������� ��ȯ
		public MartMemberVO overLapJoinIdCheck(String id){
			con = getConnect();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			MartMemberVO martMember = null;
			String sql = "select * from Member where memberId=? ";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				int count = pstmt.executeUpdate();
				if(count>0){
					
				}else{
					
				}
				rs = pstmt.executeQuery();
					while(rs.next()){//���̵� �ߺ��� ���
						String overLapId = rs.getString("memberId");
						String overLapMemberseparation = rs.getString("memberseparation");
						String overLapPasswd = rs.getString("memberPassword");
						String memberName = rs.getString("memberName");
						String memberPhone = rs.getString("memberPhone");
						String overLapEmail = rs.getString("memberEmail");
						String overLapAddress = rs.getString("memberAddress");
						String overLapMemberJoinDate = rs.getString("memberJoinDate");
						

						martMember = new MartMemberVO( overLapId, overLapMemberseparation, overLapPasswd,  memberName,  memberPhone, 
								overLapEmail,  overLapAddress,overLapMemberJoinDate);
					}//end of while
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
					rs.close();
				}catch(Exception e){
				}//end of Inner Catch
			}//end of finally
			return martMember;
		}//end of overLapJoinIdCheck
		
		
		//ȸ�����Խ� DB�� �ԷµǴ� �޼ҵ� 
		public void dbInsertMember(MartMemberVO martMember){
			con = getConnect();
			PreparedStatement pstmt=null;
		//	memberId varchar2(20),
		//	memberseparation varchar2(15),
		//	memberPassword varchar2(20),                                                               
		//	memberName varchar2(15),
		//	memberPhone varchar2(15),
		//	memberEmail varchar2(15),
		//	termsAgreement varchar2(10),
		//	memberAddress varchar2(30),
		//	memberJoinDate date,
			String sql = "insert into Member values(?,?,?,?,?,?,?,?,default)";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, martMember.getMemberId());
				pstmt.setString(2, martMember.getMemberseparation());
				pstmt.setString(3, martMember.getMemberPassword());
				pstmt.setString(4, martMember.getMemberName());
				pstmt.setString(5, martMember.getMemberPhone());
				pstmt.setString(6, martMember.getMemberEmail());
				pstmt.setString(7, "y");
				pstmt.setString(8, martMember.getMemberAddress());
				int count = pstmt.executeUpdate();
				if(count>0){
				}else{	
					System.out.println("DB�Է½���");
				}//end of count�� else
			}catch(SQLException e){
				System.out.println("����");
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception cne){
					cne.printStackTrace();
				}//end of InnerCatch
			}//end of Catch
		}//end of dbInsertMember
		
		/////////////////////////////////////////////////////////////////////////////
		
		//4. ȸ��Ż��
		//4.ȸ��Ż���� ������ �����͸� �Է��ϰ�, �ش� �����Ͱ� MemberVO������ �����ǰ� �ϴ� ��ü
		public void secessionMember(){
			MartMemberVO overMV =null;
			System.out.println("*****ȸ��Ż��â*****");
			System.out.print("ȸ�� Ż���� ID�� �Է��ϼ��� : ");
			String id = sc.next();
			MartMemberVO overLapMV = overLapJoinIdCheck(id);
			if(overLapMV.getMemberId()==null){
				//DB���� �˻������� �ش��ϴ� ���̵� ���� ���
				System.out.println("�ش� �ϴ� ���̵� �����ϴ�.");
			}//end of  if(overLapId==null)
			if(overLapMV.getMemberId().equals("SYS")){
				//������ ������ ���̵� �����Ҷ�� �Ұ��
				System.out.println("������ ������ �Ұ��� �մϴ�.");
			}
			else if(overLapMV.getMemberId().equals(id)){
				//�н����� ��
				System.out.println("ȸ�� Ż���� passwd�� �Է��ϼ��� : ");
				String passwd = sc.next();
				overMV = overLapJoinPasswdCheck(overLapMV.getMemberId(),passwd);
				//�н�������� ��ġ�� ��ü�� �ҷ��ɴϴ�.
				//�ش� ��ü�� �����ϴ� �޼ҵ�
				secessionMember(overMV);  //ArrayList���� ����
				dbdeleteMember(overMV);   //DB���� ����
			}//end of else if
			else{
				//DB���� �˻������� �ش� ���̵� ��ġ�ϴ°͵� �ƴϰ� ���°͵� �ƴ�, �ٸ� ���̵� �� ���
				System.out.println("����ε� ���̵� �Է��ϼ���.");
			}//end of else
		}//end of secessionMember
		
		//ArrayList�� Member�� �߰�
		public void secessionMember(MartMemberVO martMember){
			arrayMarketMember.remove(martMember);
		}
		
		public void dbdeleteMember(MartMemberVO deleteMV){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "delete from Member where id = ? and passwd= ?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, deleteMV.getMemberId());
				pstmt.setString(2, deleteMV.getMemberPassword());
				int count = pstmt.executeUpdate();
				if(count>0){		}
				else{
					System.out.println("db�Է½���");
				}//end of else
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){ e.printStackTrace();	}
			}//end of finally
		}//end of dbdeleteMember()
		
		//ȸ�����Խ� Passwd �ߺ�Ȯ�� �ϴ� �޼ҵ�
				public MartMemberVO overLapJoinPasswdCheck(String id, String passwd){
					con = getConnect();
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					//String overLapId,overLapPasswd, overEmail,overDeptId = null;
					MartMemberVO overMV =null;
					String sql = "select * from Member where memberId = ? and memberPassword=? ";
					try{
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, id);
						pstmt.setString(2, passwd);
						rs = pstmt.executeQuery();
						while(rs.next()){
							String overLapId = rs.getString("memberId");
							String overLapMemberseparation = rs.getString("memberseparation");
							String overLapPasswd = rs.getString("memberPassword");
							String memberName = rs.getString("memberName");
							String memberPhone = rs.getString("memberPhone");
							String overLapEmail = rs.getString("memberEmail");
							String overLapAddress = rs.getString("memberAddress");
							String overLapJoinDate = rs.getString("memberJoinDate");

							overMV = new MartMemberVO( overLapId, overLapMemberseparation,  overLapPasswd,  memberName,  memberPhone, 
									overLapEmail,  overLapAddress,  overLapJoinDate);
							}//end of while
						//end of if(count>0)
					}catch(SQLException cne){
						cne.printStackTrace();
					}finally{
						try{
							con.close();
							pstmt.close();
							rs.close();
							}catch(Exception e){
						}//end of Inner Catch
					}//end of finally
					return overMV;
				}//end of overLapJoinIdCheck
				
				
	///////////////////////////////////////////////////
				
	//�α���â
	public MartMemberVO memberLogin(){
		System.out.println("*****�α���â*****");
		System.out.println("id �Է� : ");
		String loingId = sc.next();
		System.out.println("password �Է� : ");
		String loginPasswd = sc.next();
		
		//�ش� id, password�� �ֳ� �˻�
		MartMemberVO memberVO =overLapJoinPasswdCheck(loingId,loginPasswd); 
		
		if(memberVO!=null){
		System.out.println("*****�α��� �Ϸ�*****");
		System.out.println("id : "+memberVO.getMemberId());
		System.out.println("Name : "+memberVO.getMemberName());
			}
		else{
			System.out.println("���̵�/��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		}
		return memberVO;
	}//end of memberLogin
	
	
	public MartMemberVO sellerConfirmation(){
		MartMemberVO sellerConfirmationMember = null;
		System.out.println("*****�α���â*****");
		System.out.println("id �Է� : ");
		String loingId = sc.next();
		System.out.println("password �Է� : ");
		String loginPasswd = sc.next();
		sellerConfirmationMember = dbSellerConfirmation(loingId); //db�� �ش� id�� �Ǹ��� ������ �޾Ҵ��� Ȯ��
		if(sellerConfirmationMember==null){
			//�Ǹ��� �����ȵ�
			System.out.println("---------------------------------");
			System.out.println("--�Ǹ��� ������ �Ǿ����� �ʽ��ϴ�.");
			System.out.println("--�Ϲ�ȸ������ ���� �Ǹ��� ��û�� ���ּ���");
			System.out.println("---------------------------------");
			
			return null;
		}
		else{
			System.out.println("---------------------------------");
			System.out.println("--�Ǹ��� ������ �Ǿ����ϴ�.");
			System.out.println("--�Ǹ��� ���� ����˴ϴ�.");
			MartMemberVO memberVO =overLapJoinPasswdCheck(loingId,loginPasswd); 
			System.out.println("*****�α��� �Ϸ�*****");
			System.out.println("id : "+memberVO.getMemberId());
			System.out.println("Name : "+memberVO.getMemberName());
			System.out.println("---------------------------------");
			//�Ǹ��� ������
			return sellerConfirmationMember;
		}
	
}//end of sellerConfirmation

public MartMemberVO dbSellerConfirmation(String memberId){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	con = getConnect();
	MartMemberVO martMember = null;
	String sql = "select * from Member m, SellerConfirmation s where m.memberId=s.memberId "
			+ "and s.memberId =? and s.sellerState=?";
	try{
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, memberId);
		pstmt.setString(2, "permission");
		
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			String overLapId = rs.getString("memberId");
			String overLapPasswd = rs.getString("memberPassword");
			String memberName = rs.getString("memberName");
			String memberPhone = rs.getString("memberPhone");
			String overLapEmail = rs.getString("memberEmail");
			String overLapAddress = rs.getString("memberAddress");
			String overLapJoinDate = rs.getString("memberJoinDate");

			martMember = new MartMemberVO( overLapId,  overLapPasswd,  memberName,  memberPhone, 
					overLapEmail,  overLapAddress,  overLapJoinDate); 
		}//end of while
	}catch(SQLException e){
		
	}finally{
		try{
			con.close();
			pstmt.close();
			rs.close();
		}catch(Exception e){
		}//end of Inner Catch
	}//end of finally
	return martMember;
}//end of dbSellerConfirmation
	
	
	
		
}//end of MartJointSVC
