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
	
	//////////////3. �Ǹ��� ���� ��û �޼ҵ�
	public void sellerConfimationRequest(MartMemberVO LoginMember){
		SellerConfirmationVO sellerConfirmationVO = null;

		System.out.println("*****�Ǹ��� ���� ����*****");
		System.out.println("�Ǹ��� ���� �Ͻô� ID : "+ LoginMember.getMemberId());
		System.out.println("�����ڿ��� �Ǹ��ڸ� ��û�Ͻðڽ��ϱ�? [Y/N]" );
		String sellerSelected = sc.next();
		if(sellerSelected.equals("Y")||sellerSelected.equals("y")){
			/////////////�Ǹ��� �˻�����
			System.out.println("�Ǹ��� ��ȣ�� : ");
			String sellerCompanyName = sc.next();
			System.out.println("�Ǹ��� ����� ��ȣ : ");
			String sellerOperatorNumber = sc.next();
			System.out.println("�Ǹ��� ������ : ");
			String sellerEarningsRate = sc.next();
			System.out.println("�Ǹ��� ����ó : ");
			String sellerPhone = sc.next();
			
			sellerConfirmationVO = new SellerConfirmationVO(LoginMember.getMemberId(),"nonPermission",
					sellerCompanyName,sellerOperatorNumber,sellerEarningsRate,sellerPhone);
			
			//DB�� ����
			insertSellerConfimationRequest(sellerConfirmationVO);
			//ArrayList�� �߰�
			sellerConfirmationVO=sellerConfimationRequest(sellerConfirmationVO);
			requestMember(sellerConfirmationVO);
			
		}else if(sellerSelected.equals("N")||sellerSelected.equals("n")){
				}
		else{
			System.out.println("����� �Է��� �ּ���.");	}
	
	}//end of sellerConfimationRequest
	
	//ArrayList�� �߰��ϴ� �޼ҵ�
	public void requestMember(SellerConfirmationVO sellerConfirmationVO){
		arraySellerConfirmation.add(sellerConfirmationVO);
	}
	
	//DB�� ������ �Է�
	public void insertSellerConfimationRequest(SellerConfirmationVO sellerConfirmationVO){
		con = getConnect();
		String sql = "insert into SellerConfirmation values(?,?,?,?,?,?,default)";
		try{
			/*
			 * memberId varchar2(20),
				 sellerState varchar2(9),  --�㰡�� ��� permission �̶�� �ٲ�  �㰡���� nonPermission
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
				System.out.println("���̺��Է¼���");
			}else{
				System.out.println("���̺��Է½���");
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
	
	
	//DB�� ������ �˻�
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
				System.out.println("SellerConfirmation table �Է� ����");}
			finally{
				try{
					pstmt.close();
					con.close();
				}catch(Exception e){		}
			}//end of finally
			return selectedsellerConfirmationVO;
		}//end of sellerConfimationRequest

	/////////////////�Ǹ��� �����㰡
		
		//1.�Ǹ��� ������û�ε� �˻�
	public void sellersConfimationRequest(){
		SellerConfirmationVO selectedSellerConfirmationVO = null;
		System.out.println("*******************");
		int count = dbSelectedSellersConfimationRequest();
		
		if(count!=0){
			System.out.println("�㰡�� memberId�� �Է����ּ��� : ");
			String permissionMemberId = sc.next();
			System.out.println("--�Ǹ��� ������ �㰡 �Ͻðڽ��ϱ�? [y/n]");
			
			String sellerPermission = sc.next();
			if(sellerPermission.equals("y")||sellerPermission.equals("Y")){
				dbUpdateSellersConfimationRequest(permissionMemberId);
				/////////////////////���� ������ ���ÿ� �Ǹ��� �����̷����̺� ����� ���´�
			}
			else{
				System.out.println("���� �㰡�� ��ҵǾ����ϴ�.");
			}
		}else{
			System.out.println("�Ǹ��� ���� ��û�� ������ �ʾҽ��ϴ�.");
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
				System.out.println("�ǸŰ� �㰡 ��û �Ϸ�");
			}else {
				System.out.println("�ǸŰ� �㰡 ��û ����");
			}
			
	}catch(SQLException e){
		System.out.println("SellerConfirmation table �ҷ�����");}
	finally{
		try{
			pstmt.close();
			con.close();
			}catch(Exception e){		}
		}//end of finally
	}//end of dbUpdateSellersConfimationRequest(SellerConfirmationVO selectedsellerConfirmationVO)
		
	
	//�Ǹ��� �̷µ� �˻�
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
					System.out.println("["+i+"] ȸ���� :"+memberId);
					System.out.println("����� : "+sellerCompanyName);
					System.out.println("�����ȣ : "+sellerOperatorNumber);
					System.out.println("���ͷ� : "+sellerEarningsRate);
					System.out.println("�����ȣ : "+sellerPhone);
					System.out.println("�������� : "+sellerCertificationDate);
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
