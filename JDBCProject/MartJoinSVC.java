package JDBCProject;
import static JDBCProject.JdbcUtil.getConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MartJoinSVC {

	Connection con ;
	Scanner sc = new Scanner(System.in);
	ArrayList<MartMemberVO> arrayMarketMember =new ArrayList<MartMemberVO>();
	
	//private String memberId;
	//private String memberseparation;
	//private String memberPassword;
	//private String memberName;
	//private String memberPhone;
	//private String memberEmail;
	//private String termsAgreement; //약관동의
	//private String memberAddress;
	//private String memberJoinDate;
	
	//회원가입
	//if memberseparation이는 일반회원으로
	public void joinMember(String generalMember){
		System.out.println("*****회원가입창*****");
		System.out.print("아이디 : ");
		String memberId = sc.next();
		System.out.print("비밀번호 : ");
		String memberPassword = sc.next();
		System.out.print("이름 : ");
		String memberName = sc.next();
		System.out.print("전화번호 : ");
		String memberPhone = sc.next();
		System.out.print("이메일 : ");
		String memberEmail = sc.next();
		System.out.print("주소 : ");
		String memberAddress = sc.next();
		System.out.println("--약관 동의를 허가하지 않으시다면 가입이 취소될수 있습니다. ");
		System.out.println("--동의하시겠습니까? [Y/N]");
		String termsAgreement = sc.next();
		if(termsAgreement.equals("Y")||termsAgreement.equals("y")){
			//가입진행
			//id 중복확인
			System.out.println("--아이디 중복확인 중입니다.");
			MartMemberVO overLapmartMember = overLapJoinIdCheck(memberId);
			if(overLapmartMember==null){
				//중복된 id 가 없는 경우 회원가입 진행
				MartMemberVO martMember = new MartMemberVO(memberId,generalMember,memberPassword,memberName,memberPhone,
						memberEmail,memberAddress); //새로운 MartMemberVO 객체 생성
				//MartMemberVO 생성자
				//MartMemberVO(String memberId,  String memberseparation,String memberPassword, String memberName, 
				//String memberPhone, String memberEmail,  String memberAddress)
				joinMember(martMember); //ArrayList에 추가
				dbInsertMember(martMember); //DB에 데이터 입력
				
			}
		}//end of if
		else{
				//가입취소
		}//end of else	
	}//end of joinMember;
	
	//ArrayList에 Member을 추가
		public void joinMember(MartMemberVO martMember){
			arrayMarketMember.add(martMember);
		}
	
	//회원가입시 id 중복확인 하는 메소드
		//나중에 필요하면 String에서 멤버변수로 변환
		public MartMemberVO overLapJoinIdCheck(String id){
			con = getConnect();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			MartMemberVO martMember = null;
			String sql = "select * from Marketmember where id=? ";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
					while(rs.next()){//아이디가 중복될 경우
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
		
		
		//회원가입시 DB에 입력되는 메소드 
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
			String sql = "insert into Marketmember values(?,?,?,?,?,?,?,?,sysdate)";
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
					System.out.println("DB입력실패");
				}//end of count의 else
			}catch(SQLException e){
				System.out.println("실패");
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
		
		//4. 회원탈퇴
		//4.회원탈퇴을 누를시 데이터를 입력하고, 해당 데이터가 MemberVO변수에 삭제되게 하는 객체
		public void secessionMember(){
			MartMemberVO overMV =null;
			System.out.println("*****회원탈퇴창*****");
			System.out.print("회원 탈퇴할 ID를 입력하세요 : ");
			String id = sc.next();
			MartMemberVO overLapMV = overLapJoinIdCheck(id);
			if(overLapMV.getMemberId()==null){
				//DB에서 검색했을때 해당하는 아이디가 없을 경우
				System.out.println("해당 하는 아이디가 없습니다.");
			}//end of  if(overLapId==null)
			if(overLapMV.getMemberId().equals("SYS")){
				//관리자 권한인 아이디를 삭제할라고 할경우
				System.out.println("관리자 삭제는 불가능 합니다.");
			}
			else if(overLapMV.getMemberId().equals(id)){
				//패스워드 비교
				System.out.println("회원 탈퇴할 passwd를 입력하세요 : ");
				String passwd = sc.next();
				overMV = overLapJoinPasswdCheck(overLapMV.getMemberId(),passwd);
				//패스워드까지 일치한 객체를 불러옵니다.
				//해당 객체를 삭제하는 메소드
				secessionMember(overMV);  //ArrayList에서 제거
				dbdeleteMember(overMV);   //DB에서 제거
			}//end of else if
			else{
				//DB에서 검색했을때 해당 아이디가 일치하는것도 아니고 없는것도 아닌, 다른 아이디 일 경우
				System.out.println("제대로된 아이디를 입력하세요.");
			}//end of else
		}//end of secessionMember
		
		//ArrayList에 Member을 추가
		public void secessionMember(MartMemberVO martMember){
			arrayMarketMember.remove(martMember);
		}
		
		public void dbdeleteMember(MartMemberVO deleteMV){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "delete from Marketmember where id = ? and passwd= ?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, deleteMV.getMemberId());
				pstmt.setString(2, deleteMV.getMemberPassword());
				int count = pstmt.executeUpdate();
				if(count>0){		}
				else{
					System.out.println("db입력실패");
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
		
		//회원가입시 Passwd 중복확인 하는 메소드
				public MartMemberVO overLapJoinPasswdCheck(String id, String passwd){
					con = getConnect();
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					//String overLapId,overLapPasswd, overEmail,overDeptId = null;
					MartMemberVO overMV =null;
					String sql = "select * from Marketmember where id = ? and passwd=? ";
					try{
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, id);
						pstmt.setString(2, passwd);
						rs = pstmt.executeQuery();
						while(rs.next()){
							String overLapId = rs.getString("memberId");
							String overLapPasswd = rs.getString("memberPassword");
							String memberName = rs.getString("memberName");
							String memberPhone = rs.getString("memberPhone");
							String overLapEmail = rs.getString("memberEmail");
							String overLapAddress = rs.getString("memberAddress");
							String overLapJoinDate = rs.getString("memberJoinDate");

							overMV = new MartMemberVO( overLapId,  overLapPasswd,  memberName,  memberPhone, 
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
		
}//end of MartJointSVC
