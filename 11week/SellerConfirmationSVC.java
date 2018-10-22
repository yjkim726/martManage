package JDBCProject;

import static JDBCProject.JdbcUtil.getConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SellerConfirmationSVC {
Scanner sc = new Scanner(System.in);
	
	Connection con = null;
	PreparedStatement pstmt= null;
	ResultSet rs = null;
	
	ArrayList<SellerConfirmationVO> arraySellerConfirmation =new ArrayList<SellerConfirmationVO>();
	
	//////////////3. 판매자 인증 신청 메소드
	public void sellerConfimationRequest(MartMemberVO LoginMember){
		SellerConfirmationVO sellerConfirmationVO = null;

		System.out.println("*****판매자 인증 절차*****");
		System.out.println("판매자 인증 하시는 ID : "+ LoginMember.getMemberId());
		System.out.println("관리자에게 판매자를 요청하시겠습니까? [Y/N]" );
		String sellerSelected = sc.next();
		if(sellerSelected.equals("Y")||sellerSelected.equals("y")){
			/////////////판매자 검색부터
			System.out.println("판매자 상호명 : ");
			String sellerCompanyName = sc.next();
			System.out.println("판매자 사업자 번호 : ");
			String sellerOperatorNumber = sc.next();
			System.out.println("판매자 수익율 : ");
			String sellerEarningsRate = sc.next();
			System.out.println("판매자 연락처 : ");
			String sellerPhone = sc.next();
			
			sellerConfirmationVO = new SellerConfirmationVO(LoginMember.getMemberId(),"nonPermission",
					sellerCompanyName,sellerOperatorNumber,sellerEarningsRate,sellerPhone);
			
			//DB로 전달
			insertSellerConfimationRequest(sellerConfirmationVO);
			//ArrayList에 추가
			sellerConfirmationVO=sellerConfimationRequest(sellerConfirmationVO);
			requestMember(sellerConfirmationVO);
			
		}else if(sellerSelected.equals("N")||sellerSelected.equals("n")){
				}
		else{
			System.out.println("제대로 입력해 주세요.");	}
	
	}//end of sellerConfimationRequest
	
	//ArrayList에 추가하는 메소드
	public void requestMember(SellerConfirmationVO sellerConfirmationVO){
		arraySellerConfirmation.add(sellerConfirmationVO);
	}
	
	//DB에 데이터 입력
	public void insertSellerConfimationRequest(SellerConfirmationVO sellerConfirmationVO){
		con = getConnect();
		String sql = "insert into SellerConfirmation values(?,?,?,?,?,?,default)";
		try{
			/*
			 * memberId varchar2(20),
				 sellerState varchar2(9),  --허가될 경우 permission 이라고 바뀜  허가전은 nonPermission
				 sellerCompanyName varchar2(20),
				 sellerOperatorNumber number(20),
				 sellerEarningsRate varchar2(10),
				 sellerPhone varchar2(15),
				 sellerCertificationDate varchar2(15) default sysdate,
			 * */
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sellerConfirmationVO.getMemberId());
			pstmt.setString(2, sellerConfirmationVO.getSellerState());
			pstmt.setString(3, sellerConfirmationVO.getSellerCompanyName());
			pstmt.setString(4, sellerConfirmationVO.getSellerOperatorNumber());
			pstmt.setString(5, sellerConfirmationVO.getSellerEarningsRate());
			pstmt.setString(6, sellerConfirmationVO.getSellerPhone());
			
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("테이블입력성공");
			}else{
				System.out.println("테이블입력실패");
			}
		}catch(SQLException e){
			e.printStackTrace();
			}
		finally{
			try{
				pstmt.close();
				con.close();
			}catch(Exception e){		}
		}//end of finally
	}//end of sellerConfimationRequest
	
	
	//DB에 데이터 검색
		public SellerConfirmationVO sellerConfimationRequest(SellerConfirmationVO sellerConfirmationVO){
			con = getConnect();
			SellerConfirmationVO selectedsellerConfirmationVO = null;
			String sql = "select * from SellerConfirmation where memberId=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, sellerConfirmationVO.getMemberId());
				
				rs = pstmt.executeQuery();
				while(rs.next()){
					String memberId = rs.getString("memberId");
					String sellerState = rs.getString("sellerState");
					String sellerCompanyName =rs.getString("sellerCompanyName");
					String sellerOperatorNumber =rs.getString("sellerOperatorNumber");
					String sellerEarningsRate =rs.getString("sellerEarningsRate");
					String sellerPhone =rs.getString("sellerPhone");
					String sellerCertificationDate =rs.getString("sellerCertificationDate");
					
					selectedsellerConfirmationVO = new SellerConfirmationVO(memberId,sellerState,
							sellerCompanyName,sellerOperatorNumber,sellerEarningsRate,sellerPhone,sellerCertificationDate);
				}//end of while
				
			}catch(SQLException e){
				System.out.println("SellerConfirmation table 입력 실패");}
			finally{
				try{
					pstmt.close();
					con.close();
				}catch(Exception e){		}
			}//end of finally
			return selectedsellerConfirmationVO;
		}//end of sellerConfimationRequest

	/////////////////판매자 인증허가
		
		//1.판매자 인증요청인들 검색
	public void sellersConfimationRequest(){
		SellerConfirmationVO selectedSellerConfirmationVO = null;
		System.out.println("*******************");
		int count = dbSelectedSellersConfimationRequest();
		
		if(count!=0){
			System.out.println("허가할 memberId를 입력해주세요 : ");
			String permissionMemberId = sc.next();
			System.out.println("--판매자 승인을 허가 하시겠습니까? [y/n]");
			
			String sellerPermission = sc.next();
			if(sellerPermission.equals("y")||sellerPermission.equals("Y")){
				dbUpdateSellersConfimationRequest(permissionMemberId);
				/////////////////////승인 인증과 동시에 판매자 인증이력테이블에 기록이 남는다
			}
			else{
				System.out.println("승인 허가가 취소되었습니다.");
			}
		}else{
			System.out.println("판매자 인증 요청이 들어오지 않았습니다.");
		}
	}//end of sellersConfimationRequest
	
	public void dbUpdateSellersConfimationRequest(String permissionMemberId){
		con = getConnect();
		String sql = "update SellerConfirmation set sellerState=? where memberId=?";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "permission");
			pstmt.setString(2, permissionMemberId);
			int count = pstmt.executeUpdate();
			
			if(count>0){
				System.out.println("판매가 허가 요청 완료");
			}else {
				System.out.println("판매가 허가 요청 실패");
			}
			
	}catch(SQLException e){
		System.out.println("SellerConfirmation table 불러오기");}
	finally{
		try{
			pstmt.close();
			con.close();
			}catch(Exception e){		}
		}//end of finally
	}//end of dbUpdateSellersConfimationRequest(SellerConfirmationVO selectedsellerConfirmationVO)
		
	
	//판매자 이력들 검색
	public int dbSelectedSellersConfimationRequest(){
		con = getConnect();
		String sql = "select * from SellerConfirmation where sellerState=?";
		int i=0;
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "nonPermission");
			System.out.println("*******************");
			int count = pstmt.executeUpdate();
			if(count>0){
				rs = pstmt.executeQuery();
				while(rs.next()){
					String memberId = rs.getString("memberId");
					String sellerCompanyName = rs.getString("sellerCompanyName");
					String sellerOperatorNumber = rs.getString("sellerOperatorNumber");
					String sellerEarningsRate = rs.getString("sellerEarningsRate");
					String sellerPhone = rs.getString("sellerPhone");
					String sellerCertificationDate = rs.getString("sellerCertificationDate");
					i = 1;
					System.out.println("["+i+"] 회원명 :"+memberId);
					System.out.println("상업명 : "+sellerCompanyName);
					System.out.println("상업번호 : "+sellerOperatorNumber);
					System.out.println("수익률 : "+sellerEarningsRate);
					System.out.println("상업번호 : "+sellerPhone);
					System.out.println("상업등록일 : "+sellerCertificationDate);
					i++;
					System.out.println("*******************");
				}//end of while
			}//end of if
			else {
				
			}//end of else
	}catch(SQLException e){
		e.printStackTrace();}
	finally{
		try{
			pstmt.close();
			con.close();
			rs.close();
			}catch(Exception e){		}
		}//end of finally
		return i;
	}//end of dbUpdateSellersConfimationRequest(SellerConfirmationVO selectedsellerConfirmationVO)
		
		
		
}
