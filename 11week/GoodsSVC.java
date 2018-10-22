package JDBCProject;

import static JDBCProject.JdbcUtil.getConnect;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GoodsSVC {
	Scanner sc = new Scanner(System.in);
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	/////////////////////////////////////////////////////////////////////////////////////////
	//��ǰ�߰�
	public void addGoods(MartMemberVO memberVO, MartClassCodeSVC martClassCodeSVC){
	
		System.out.println("*****��ǰ�߰�â*****");
		//��з� �ڵ� ����
		martClassCodeSVC.dbListClassCode();
		String selectedClassCode = sc.next();
		//�Һз� �ڵ� ���� - ��з� ���ý� �� ���� ���̺� ��ϵ�
		martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
		String selectedSubclassCode = sc.next();
		
		//��ǰ Code ����
		System.out.print("GoodsCode : ");
		String goodsCode = sc.next();
		
		//�ش� Code�� �ֳ� �ߺ��˻�
		GoodsVO matchgoodsVO = matchGoodsVOCode(goodsCode,selectedSubclassCode);
		if(matchgoodsVO!=null){
			//�ش� Code�� �ִ�!
			System.out.println("--�ش��ϴ� ��ǰCode�� �̹������մϴ�.");
		}
		else{
			System.out.println("--�ߺ��˻� �Ϸ�");
			System.out.println("--��ǰ���� ����");
			System.out.print("��ǰ �̸� : ");
			String goodsName = sc.next();
			System.out.print("��ǰ ���� : ");
			int goodsPrice = sc.nextInt();
			
			String goodsDiscountWhether = "N"; //��ǰ���ο���
			String goodsDeleteWhether = "Y"; //��ǰ��������
			String goodsRestrictWhether = "N";  //��ǰ���翩��
			
			
			//GoodsVO(String goodsCode, String goodsName, int goodsPrice, 
			//String goodsDiscountWhether (��ǰ���ο���), String goodsDeleteWhether(��ǰ��������), 
			//String goodsRestrictWhether(��ǰ���翩��),String goodSubclassCode(��ǰ�Һз��ڵ�), String memberId )
			GoodsVO goodsVO = new GoodsVO(goodsCode,goodsName,goodsPrice,goodsDiscountWhether,goodsDeleteWhether
					,goodsRestrictWhether,selectedSubclassCode,memberVO.getMemberId());
			//Goods DB�� �ִ� �޼ҵ�
			dbAddGoods(goodsVO);
			///////////////////////////////////////////Goods ArrayList �� ������
		}//end of else
	
	}//end of addGoods()
		
	//��ǰ Code�� �մ��� �ߺ��˻�
	public GoodsVO matchGoodsVOCode(String matchGoodsCode,String selectedSubclassCode){
		GoodsVO goodsVO = null;
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Goods where goodsCode = ? and goodsSubclassCode=? "; 
		try{
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, matchGoodsCode);
		pstmt.setString(2, selectedSubclassCode);
		
		int count = pstmt.executeUpdate();
		if(count>0){
			rs = pstmt.executeQuery();
			while(rs.next()){
				//goodsCode varchar2(20),
				//goodsName varchar2(15),
				//goodsPrice int,
				//goodsDiscountWhether varchar2(15), --��ǰ ���� ����  Goods.java���� boolean
				//goodsDeleteWhether varchar2(15),
				//goodsRestrictWhether varchar2(15),
				//goodsRegistrationDate date,
				//goodsSubclassCode varchar2(20),
				//memberId varchar2(20),
				  String goodsCode = rs.getString("goodsCode");
				  String goodsName = rs.getString("goodsName");
				  int goodsPrice = rs.getInt("goodsPrice");
				  String goodsDiscountWhether = rs.getString("goodsDiscountWhether");
				  String goodsDeleteWhether = rs.getString("goodsDeleteWhether");
				  String goodsRestrictWhether = rs.getString("goodsRestrictWhether");
				  String goodsRegistrationDate = rs.getString("goodsRegistrationDate");
				  String goodsSubclassCode = rs.getString("goodsSubclassCode");
				  String memberId = rs.getString("memberId");
				 // GoodsVO(String goodsCode, String goodsName, int goodsPrice, String goodsDiscountWhether, String goodsDeleteWhether,
							//String goodsRestrictWhether, String goodSubclassCode, String memberId )
				  goodsVO = new GoodsVO(goodsCode,goodsName,goodsPrice,goodsDiscountWhether,goodsDeleteWhether
						  ,goodsRestrictWhether,goodsRegistrationDate,goodsSubclassCode,memberId);
				if(matchGoodsCode.equals(goodsCode)){
					return goodsVO;
					}
				else{	return null;	}//end of else
				}//end for while
			}//end of if(count>0)
		else{		}//end of else - if(count>0)
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
		return null;
	}//end of matchGoodsVOCode
		
	//Goods ���̺� �߰�
	public void dbAddGoods(GoodsVO goodsVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		
		String sql = "insert into Goods values(?,?,?,?,?,?,default,?,?)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, goodsVO.getGoodsCode());
			pstmt.setString(2, goodsVO.getGoodsName());
			pstmt.setInt(3, goodsVO.getGoodsPrice());
			pstmt.setString(4, goodsVO.getGoodsDiscountWhether()); //��ǰ���ο���
			pstmt.setString(5, goodsVO.getGoodsDeleteWhether()); //��ǰ��������
			pstmt.setString(6, goodsVO.getGoodsRestrictWhether()); //��ǰ���翩��
			//////////////��¥�ֱ�
			pstmt.setString(7, goodsVO.getGoodSubclassCode()); //��ǰ�Һз��ڵ�
			pstmt.setString(8, goodsVO.getMemberId());
			
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--��ǰ �Է� �Ϸ�");
			}else{
				System.out.println("--��ǰ �Է� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dbAddGoods(GoodsVO goodsVO)
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	//��ǰ����
	public void updateGoods(MartClassCodeSVC martClassCodeSVC){

		System.out.println("*****��ǰ����â*****");
		GoodsVO matchgoodsVO = selectedCodeSearchGoods(martClassCodeSVC);
		//�ش� Code�� �ֳ� �ߺ��˻�
		String reGoodsName = matchgoodsVO.getGoodsName();
		
		if(matchgoodsVO!=null){
			//�ش� Code�� �ִ�!
			if(matchgoodsVO.getGoodsRestrictWhether().equals("Y")||matchgoodsVO.getGoodsRestrictWhether().equals("y")){
				System.out.println("��ǰ�Ǹ� �������� ��ǰ�Դϴ�.");
			}else{
				System.out.println("--��ǰ���� ����");			
				System.out.print("��ǰ �̸� : ");
				String goodsName = sc.next();
				System.out.print("��ǰ ���� : ");
				int goodsPrice = sc.nextInt();
				System.out.print("��ǰ���ο��� [y/n]: ");
				String goodsDiscountWhether = sc.next();
	
				//GoodsVO(String goodsCode, String goodsName, int goodsPrice, 
				//String goodsDiscountWhether (��ǰ���ο���), String goodsDeleteWhether(��ǰ��������), 
				//String goodsRestrictWhether(��ǰ���翩��),String goodSubclassCode(��ǰ�Һз��ڵ�), String memberId )
				matchgoodsVO.setGoodsName(goodsName);
				matchgoodsVO.setGoodsPrice(goodsPrice);
				matchgoodsVO.setGoodsDiscountWhether(goodsDiscountWhether);
				//Goods DB�� �ִ� �޼ҵ�
				dbUpdateGoods(matchgoodsVO);
				
				if((goodsDiscountWhether.equals("y")||goodsDiscountWhether.equals("Y"))){
					//��ǰ���α�����̺� ���
					goodsDiscountRecord(matchgoodsVO);
				}
				
				//��ǰ���������̷� ���̺� ����
				dbGoodsModificationHistory(matchgoodsVO,reGoodsName);
				
				///////////////////////////////////////////Goods ArrayList �� ������
			}
		}
		else{
			System.out.println("--�ش��ϴ� ��ǰCode�� �����ϴ�.");	}//end of else
	
	}//end of updateGoods()
	
	//Goods ���̺� �߰�
		public void dbUpdateGoods(GoodsVO goodsVO){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "update Goods set goodsName=? , goodsPrice=?, goodsDiscountWhether=?  where goodsCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, goodsVO.getGoodsName());
				pstmt.setInt(2, goodsVO.getGoodsPrice());
				pstmt.setString(3, goodsVO.getGoodsDiscountWhether()); //��ǰ���ο���
				pstmt.setString(4, goodsVO.getGoodsCode());
			
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--��ǰ ���� �Ϸ�");
				}else{
					System.out.println("--��ǰ ���� ����");
				}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally
		}//end of dbUpdateGoods(GoodsVO goodsVO)
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	//��ǰ����
	public void deleteGoods(MartClassCodeSVC martClassCodeSVC){
			System.out.println("*****��ǰ����â*****");
			//��з� �ڵ� ����
			martClassCodeSVC.dbListClassCode();
			String selectedClassCode = sc.next();
			//�Һз� �ڵ� ���� - ��з� ���ý� �� ���� ���̺� ��ϵ�
			martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
			String selectedSubclassCode = sc.next();
			
			//��ǰ Code ����
			System.out.print("GoodsCode : ");
			String goodsCode = sc.next();
			
			//�ش� ��ǰCode ã��
			GoodsVO matchgoodsVO = matchGoodsVOCode(goodsCode,selectedSubclassCode);
			if(matchgoodsVO!=null){
				System.out.println("--���� �������Դϴ�.");
				dbUpdateGoods(matchgoodsVO);
				///////////////////////////////////////////Goods ArrayList �� ������
			}
			else{
				System.out.println("--�ش��ϴ� ��ǰCode�� �����ϴ�.");
			}//end of else
	
	}//end of deleteGoods()
		
	//Goods ���̺� �߰�
		public void dbDeleteGoods(GoodsVO goodsVO){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "delete from Goods where goodsCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, goodsVO.getGoodsCode());
			
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--��ǰ ���� �Ϸ�");
				}else{
					System.out.println("--��ǰ ���� ����");
				}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally
		}//end of dbDeleteGoods(GoodsVO goodsVO)
	
	//��ǰ ���
	public int listGoodsVOCode(String matchSubclassCode){
		int i=0;
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Goods where goodsSubclassCode=? and goodsRestrictWhether=?";
		try{
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, matchSubclassCode);
		pstmt.setString(2, "N");
		rs = pstmt.executeQuery();
		System.out.println("===========================");
		while(rs.next()){
			  String goodsCode = rs.getString("goodsCode");
			  String goodsName = rs.getString("goodsName");
			  int goodsPrice = rs.getInt("goodsPrice");
			  i=1;
			  System.out.println("goodsCode : "+goodsCode+" goodsName : "+goodsName + "goodsPrice : "+goodsPrice);
			 
			}//end for while
		System.out.println("===========================");
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
		return i;
	}//end of listGoodsVOCode
	
			
	//��ٱ��� 
	public GoodsPurchaseVO shoppingBasket(MartClassCodeSVC martClassCodeSVC,MartMemberVO LoginMember){
		String goodsPurchaseWay = null;
		GoodsPurchaseVO goodsPurchaseVO = null;
		System.out.println("*****��ٱ���*****");
		GoodsVO matchgoodsVO =selectedCodeSearchGoods(martClassCodeSVC);
		 // []goodsPurchaseNumber varchar2(20), --��ǰ���Ź�ȣ
		 // goodsCode varchar2(20),  --��ǰ��ȣ
		 // memberId varchar2(20),  --ȸ�� ID
		 // goodsPurchasePrice int, --��ǰ���Ű���
		 // goodsPurchaseWay varchar2(15), --��ǰ���Ź��
		 // goodsReceiver varchar2(20), --��ǰ������
		 // goodsAddress varchar2(30), --��ǰ�����
		 // goodsDeliveryFinishWhether varchar2(15),  --��ǰ��ۿϷῩ��
		 // goodsRefundWhether varchar2(15),  --��ǰȯ�ҿ���
		//  []goodsPurchaseDate date, --��ǰ��������
		System.out.println("��ٱ��Ͽ� �����ðڽ��ϱ�? [y/n]");
		String goodsPurchase = sc.next();
		
		if(goodsPurchase.equals("Y")||goodsPurchase.equals("y")){
			System.out.println("���Ź�� [1.�������Ա�/2.�ſ�ī��/3.�ڵ���]");
			int selectedPurchaseWay = sc.nextInt();
			if(selectedPurchaseWay==1){
				goodsPurchaseWay = "noBankbookDeposit";
			}
			else if(selectedPurchaseWay==2){
				goodsPurchaseWay = "creditCard";
			}
			else if(selectedPurchaseWay==3){
				goodsPurchaseWay = "phone";
			}
			else{
				System.out.println("�ٽ� �������ּ���.");
			}
			System.out.println("��ǰ ������� �Է����ּ��� :");
			String goodsAddress = sc.next();
			String goodsDeliveryFinishWhether = "N";
			String goodsRefundWhether = "N";
			goodsPurchaseVO = new GoodsPurchaseVO(
					matchgoodsVO.getGoodsCode(), LoginMember.getMemberId(), matchgoodsVO.getGoodsPrice(),
					goodsPurchaseWay, LoginMember.getMemberName(),goodsAddress,
					goodsDeliveryFinishWhether,goodsRefundWhether);
			dbShoppingBasket(goodsPurchaseVO);
			System.out.println("��ǰ ������ �Ϸ�Ǿ����ϴ�.");
		}else{
			System.out.println("��ǰ������ ��ҵǾ����ϴ�.");   }
	
		return goodsPurchaseVO;
	}//end of goodsSelected(MartClassCodeSVC martClassCodeSVC)
			
	
	public void dbShoppingBasket(GoodsPurchaseVO goodsPurchaseVO){
		con = getConnect();
		PreparedStatement pstmt = null;
	
		String sql = "insert into GoodsPurchase "
				+ "values(goodsPurchaseN.nextval,?,?,?,?,?,?,?,?,default)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, goodsPurchaseVO.getGoodsCode());
			pstmt.setString(2, goodsPurchaseVO.getMemberId());
			pstmt.setInt(3, goodsPurchaseVO.getGoodsPurchasePrice());
			pstmt.setString(4, goodsPurchaseVO.getGoodsPurchaseWay());
			pstmt.setString(5, goodsPurchaseVO.getGoodsReceiver());
			pstmt.setString(6, goodsPurchaseVO.getGoodsAddress());
			pstmt.setString(7, goodsPurchaseVO.getGoodsDeliveryFinishWhether());
			pstmt.setString(8, goodsPurchaseVO.getGoodsRefundWhether());
		
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--��ٱ��� �Է� �Ϸ�");
			}else{
				System.out.println("--��ٱ��� �Է� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of  dbshoppingBasket(GoodsPurchaseVO goodsPurchaseVO)
	
	
	//��ٱ��� 
	public void dbListShoppingBasket(GoodsPurchaseVO goodsPurchaseVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		String sql = "select * from GoodsPurchase where memberId =?";
		try{
			 // []goodsPurchaseNumber varchar2(20), --��ǰ���Ź�ȣ
			 // goodsCode varchar2(20),  --��ǰ��ȣ
			 // memberId varchar2(20),  --ȸ�� ID
			 // goodsPurchasePrice int, --��ǰ���Ű���
			 // goodsPurchaseWay varchar2(15), --��ǰ���Ź��
			 // goodsReceiver varchar2(20), --��ǰ������
			 // goodsAddress varchar2(30), --��ǰ�����
			 // goodsDeliveryFinishWhether varchar2(15),  --��ǰ��ۿϷῩ��
			 // goodsRefundWhether varchar2(15),  --��ǰȯ�ҿ���
			//  []goodsPurchaseDate date, --��ǰ��������
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, goodsPurchaseVO.getMemberId());
			
			rs = pstmt.executeQuery();
			System.out.println("===========================");
			while(rs.next()){
				  String goodsDiscountRecordNumber = rs.getString("goodsPurchaseNumber");
				  String goodsCode = rs.getString("goodsCode");
				  String memberId = rs.getString("memberId");
				  int goodsPurchasePrice = rs.getInt("goodsPurchasePrice");
				  String goodsPurchaseWay = rs.getString("goodsPurchaseWay");
				  String goodsReceiver = rs.getString("goodsReceiver");
				  String goodsAddress = rs.getString("goodsAddress");
				  String goodsDeliveryFinishWhether = rs.getString("goodsDeliveryFinishWhether");
				  String goodsRefundWhether = rs.getString("goodsRefundWhether");
				  String goodsPurchaseDate = rs.getString("goodsPurchaseDate");
				  
				  if((goodsDeliveryFinishWhether.equals("y"))||(goodsDeliveryFinishWhether.equals("Y")||
						  goodsRefundWhether.equals("y"))||goodsRefundWhether.equals("Y")){
					  goodsDeliveryFinishWhether = "��ۿϷ�";
					  goodsRefundWhether = "ȯ�ҿϷ�";
				  }
				  
				  if((goodsDeliveryFinishWhether.equals("n"))||(goodsDeliveryFinishWhether.equals("N")||
						  goodsRefundWhether.equals("n"))||goodsRefundWhether.equals("N")){
					  goodsDeliveryFinishWhether = "����غ���";
					  goodsRefundWhether = null;
				  }
				  
				  System.out.println("[ "+goodsDiscountRecordNumber+"]"+ "��ǰ�ڵ� : "+goodsCode
						  + "ȸ�� ID : "+memberId); 
				  System.out.println("��ǰ���Ű��� : "+goodsPurchasePrice);
				  System.out.println("��ǰ���Ź�� : "+goodsPurchaseWay);
				  System.out.println("��ǰ������ : "+goodsReceiver);
				  System.out.println("��ǰ�ּ� : "+goodsAddress);
				  System.out.println("��ǰ ��� : "+goodsDeliveryFinishWhether);
				  if(goodsRefundWhether!=null){
				  System.out.println("��ǰ ȯ�� : "+goodsRefundWhether);}
				  System.out.println("��ǰ�������� : "+goodsPurchaseDate);
				}//end for while
			System.out.println("===========================");
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}
	
	
	//��ǰ���������̷�
	//��ǰ���������̷� �߰�
	public void dbGoodsModificationHistory(GoodsVO matchgoodsVO,String reGoodsName){
		con = getConnect();
		PreparedStatement pstmt = null;
		
		//create table GoodsModificationHistory(
				//goodsModificationHistoryNumber varchar2(18),
				//goodsCode varchar2(20),
				//goodsModificationCode varchar2(18),
				//goodsModificationContent varchar2(50),
				//goodsModificationDate date ,
		String sql = "insert into GoodsModificationHistory "
				+ "values(goodsModification.nextval,?,?,?,default)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, matchgoodsVO.getGoodsCode());
			pstmt.setString(2, reGoodsName);
			pstmt.setString(3, "���� : "+matchgoodsVO.getGoodsPrice()+"���ο��� :"+matchgoodsVO.getGoodsDiscountWhether());
			
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--��ǰ���������̷� �Է� �Ϸ�");
			}else{
				System.out.println("--��ǰ���������̷� �Է� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of goodsModificationHistory
	
	//��ǰ���������̷� ������
	public void listGoodsModificationHistory(MartClassCodeSVC martClassCodeSVC){
		System.out.println("*****��ǰ���������̷� ���â*****");
		GoodsVO matchgoodsVO = selectedCodeSearchGoods(martClassCodeSVC);
		if(matchgoodsVO!=null){
			dbListGoodsModificationHistory(matchgoodsVO);
		}//end of if
		else{
			System.out.println("����� �� ��ǰ�ڵ带 �Է����ּ���.");
		}
	}//end of listGoodsModificationHistory(MartClassCodeSVC martClassCodeSVC)
	
	public void dbListGoodsModificationHistory(GoodsVO matchgoodsVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from GoodsModificationHistory where goodsCode=?";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, matchgoodsVO.getGoodsCode());
			
			rs = pstmt.executeQuery();
			System.out.println("===========================");
			while(rs.next()){
				  String goodsModificationHistoryNumber = rs.getString("goodsModificationHistoryNumber");
				  String goodsCode = rs.getString("goodsCode");
				  String goodsModificationCode = rs.getString("goodsModificationCode");
				  String goodsModificationContent = rs.getString("goodsModificationContent");
				  Date goodsModificationDate = rs.getDate("goodsModificationDate");
				  
				  System.out.println("[ "+goodsModificationHistoryNumber+"]"+ "��ǰ�ڵ� : "+goodsCode
						  + "�����̸� : "+goodsModificationCode); 
				  System.out.println("�������� : "+goodsModificationContent);
				  System.out.println("������¥ : "+goodsModificationDate);
				}//end for while
			System.out.println("===========================");
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
				rs.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dbListGoodsModificationHistory
	
		///////////////////////////////////////////����
	//��ǰ�����̷�
	
	public void goodsRestrictRecord(MartClassCodeSVC martClassCodeSVC){
		System.out.println("*****��ǰ�����̷� ���â*****");
		GoodsVO matchgoodsVO = selectedCodeSearchGoods(martClassCodeSVC);
		if(matchgoodsVO!=null){
			System.out.println("���縦 �ϰڽ��ϱ�?[Y/N] : ");
			String goodsRestrictWhether = sc.next();
			if(goodsRestrictWhether.equals("Y")){
				matchgoodsVO.setGoodsRestrictWhether("Y");
				System.out.println("�����ڵ��̸� : ");
				String goodsRestrictCode = sc.next();
				System.out.println("������� : ");
				String goodsRestrictContent = sc.next();
				dbGoodsRestrictRecord(matchgoodsVO,goodsRestrictCode,goodsRestrictContent);}
			else if(goodsRestrictWhether.equals("N")){
				matchgoodsVO.setGoodsRestrictWhether("N");
			}
			dbGoodsRestrictWhether(matchgoodsVO);
		}//end of if
		else{
			System.out.println("����� �� ��ǰ�ڵ带 �Է����ּ���.");
		}
	}//end of goodsRestrictRecord(MartClassCodeSVC martClassCodeSVC)
	
	////////////goodsRestrictWhether 
	public void dbGoodsRestrictWhether(GoodsVO goodsVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		String sql = "update Goods set goodsRestrictWhether=? where goodsCode=?";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, goodsVO.getGoodsRestrictWhether());
			pstmt.setString(2, goodsVO.getGoodsCode());
		
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--��ǰ ���� ���� �Ϸ�");
			}else{
				System.out.println("--��ǰ ���� ���� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dbUpdateGoods(GoodsVO goodsVO)
	
	
	//��ǰ�����̷��߰�
	public void dbGoodsRestrictRecord(GoodsVO matchgoodsVO,String goodsRestrictCode, String goodsRestrictContent){
		con = getConnect();
		PreparedStatement pstmt = null;
		
		// goodsRestrictRecordNumber varchar2(15),
		// goodsCode varchar2(20),
		// goodsRestrictCode varchar2(20),
		// goodsRestrictContent varchar2(50),
		// goodsRestrictDate date default sysdate,
		String sql = "insert into GoodsRestrictRecord "
				+ "values(goodsRestrictRecordN.nextval,?,?,?,?)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, matchgoodsVO.getGoodsCode());
			pstmt.setString(2, goodsRestrictCode);
			pstmt.setString(3, goodsRestrictContent);
			pstmt.setString(4, matchgoodsVO.getGoodsRegistrationDate()); 
			
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--��ǰ�����̷� �Է� �Ϸ�");
			}else{
				System.out.println("--��ǰ�����̷��Է� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dbGoodsRestrictRecord
	

	
	
///////////////////////////////////////////����
	//��ǰ�����̷�
	//��ǰ�����̷��߰�
	public void goodsDiscountRecord(GoodsVO matchgoodsVO){
		System.out.println("*****��ǰ����â*****");
		System.out.println("��ǰ�����̸� : ");
		String goodsDiscountName = sc.next();	
		System.out.println("��ǰ���γ��� : ");
		String goodsDiscountContent = sc.next();
		System.out.println("��ǰ���ΰ��� : ");
		int goodsDiscountPrice = sc.nextInt();
		System.out.println("��ǰ���ν������� : ");
		String goodsDiscountStartDate = sc.next();
		System.out.println("��ǰ������������ : ");
		String goodsDiscountEndDate = sc.next();
		
		
		GoodsDiscountVO goodsDiscountVO = new GoodsDiscountVO(
				goodsDiscountName,	goodsDiscountContent,goodsDiscountPrice,
				 goodsDiscountStartDate,goodsDiscountEndDate);
		
		//db�� �Է�
		dbGoodsDiscountRecord(matchgoodsVO, goodsDiscountVO);
	}//end of goodsDiscountRecord
	
	public void dbGoodsDiscountRecord(GoodsVO matchgoodsVO,GoodsDiscountVO goodsDiscountVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		
		// goodsDiscountRecordNumber varchar2(15),  ��ǰ�����̷¹�ȣ
		// goodsCode varchar2(20),    ��ǰ��ȣ
		// goodsDiscountCode varchar2(20),   ��ǰ�����ڵ�(�̸�)
		// goodsDiscountContent varchar2(50),   ��ǰ���γ���
		// goodsDiscountPrice int,    ��ǰ���ΰ���
		// goodsDiscountStartDate date,  ��ǰ���ν�������
		// goodsDiscountEndDate date,   ��ǰ������������ 
		// goodsDiscountRegisterDate date default sysdate,  ��ǰ���ε������
		String sql = "insert into GoodsDiscountRecord "
				+ "values(goodsDiscountN.nextval,?,?,?,?,?,?,default)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, matchgoodsVO.getGoodsCode());
			pstmt.setString(2, goodsDiscountVO.getGoodsDiscountCode());
			pstmt.setString(3, goodsDiscountVO.getGoodsDiscountContent());
			pstmt.setInt(4, goodsDiscountVO.getGoodsDiscountPrice()); 
			pstmt.setString(5, goodsDiscountVO.getGoodsDiscountStartDate());
			pstmt.setString(6, goodsDiscountVO.getGoodsDiscountEndDate());
			
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--��ǰ�����̷� �Է� �Ϸ�");
			}else{
				System.out.println("--��ǰ�����̷� �Է� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dbGoodsDiscountRecord
		
		//��ǰ�����̷¸�����
	public void listGoodsDiscountRecord(MartClassCodeSVC martClassCodeSVC){
	
			System.out.println("*****��ǰ����â*****");
			GoodsVO matchgoodsVO = selectedCodeSearchGoods(martClassCodeSVC);
			if(matchgoodsVO!=null){
				dbListGoodsDiscountRecord(matchgoodsVO);
			}//end of if
			else{
				System.out.println("����� �� ��ǰ�ڵ带 �Է����ּ���.");
			}	

	}//end of listGoodsDiscountRecord(MartClassCodeSVC martClassCodeSVC)
		
	public void dbListGoodsDiscountRecord(GoodsVO matchgoodsVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from GoodsDiscountRecord where goodsCode=?";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, matchgoodsVO.getGoodsCode());
			rs = pstmt.executeQuery();
			System.out.println("===========================");
			while(rs.next()){
				  String goodsDiscountRecordNumber = rs.getString("goodsDiscountRecordNumber");
				  String goodsCode = rs.getString("goodsCode");
				  String goodsDiscountCode = rs.getString("goodsDiscountCode");
				  
				  String goodsDiscountContent = rs.getString("goodsDiscountContent");
				  
				  int goodsDiscountPrice = rs.getInt("goodsDiscountPrice");
				  String goodsDiscountStartDate = rs.getString("goodsDiscountStartDate");
				  String goodsDiscountEndDate = rs.getString("goodsDiscountEndDate");
				  
				  System.out.println("[ "+goodsDiscountRecordNumber+"]"+ "��ǰ�ڵ� : "+goodsCode
						  + "�����̸� : "+goodsDiscountCode); 
				  System.out.println("���ΰ��� : "+goodsDiscountPrice);
				  System.out.println("���ν��۳�¥ : "+goodsDiscountStartDate);
				  System.out.println("�������ᳯ¥ : "+goodsDiscountEndDate);
				  System.out.println("��� : "+goodsDiscountContent);
				}//end for while
			System.out.println("===========================");
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dblistGoodsModificationHistory
		
		
		
	/////////////////��з��ڵ� -> �Һз��ڵ� -> ��ǰã��
	public GoodsVO selectedCodeSearchGoods(MartClassCodeSVC martClassCodeSVC){
		GoodsVO matchgoodsVO = null;
		//��з� �ڵ� ����
		martClassCodeSVC.dbListClassCode();
		String selectedClassCode = sc.next();
		//�Һз� �ڵ� ���� - ��з� ���ý� �� ���� ���̺� ��ϵ�
		martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
		String selectedSubclassCode = sc.next();
		
		//��ǰ Code ����
		int i =listGoodsVOCode(selectedSubclassCode);
		if(i==1){
			System.out.print("GoodsCode : ");
			String goodsCode = sc.next();
			
			//�ش� Code�� �ֳ� �ߺ��˻�
			matchgoodsVO = matchGoodsVOCode(goodsCode,selectedSubclassCode);
		}
		else {
			System.out.println("��ǰ�� �����ϴ�.");
		}
		return matchgoodsVO;
	}//end of selectedCodeSearchGoods(MartClassCodeSVC martClassCodeSVC)
}
