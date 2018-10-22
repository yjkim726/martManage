package JDBCProject;
import static JDBCProject.JdbcUtil.getConnect;

import java.util.*;
import java.sql.*;

import static JDBCProject.JdbcUtil.getConnect;
public class MartClassCodeSVC {
	Scanner sc = new Scanner(System.in);
	ArrayList<SubclassCodeVO> arrayListSubclassCode = new ArrayList<SubclassCodeVO>();
	ArrayList<ClassCodeVO> arrayListClassCode = new ArrayList<ClassCodeVO>();
	Connection con = null;
	//1. �з��ڵ�
	
		/////////////////////////////////////////////////////////////////////
		//�Һз�
	//��з� Ŭ�������� ��������
	public void dbMatchSubclassCode(String matchClassCode){
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from SubclassCode where goodsClassCode= ?";
		try{
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, matchClassCode);
		int count = pstmt.executeUpdate();
		if(count>0){
			rs = pstmt.executeQuery();
			System.out.println("==============================");
			while(rs.next()){
				String goodsSubclassCode = rs.getString("goodsSubclassCode");
				String goodsSubclassName = rs.getString("goodsSubclassName");
				
				System.out.println("�Һз��ڵ� : "+goodsSubclassCode);
				System.out.println("�Һз��̸� : "+goodsSubclassName);
				System.out.println("==============================");
				}//end for while
			}//end of if
		else{
			System.out.println("�Һз� �ڵ� ã�� ����");
		}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dbMatchSubclassCode
	
	
	//�ش� �ڵ� Ŭ���ؼ� ���� ������ �޼ҵ� db	
	public SubclassCodeVO dbSelectSubclassCode(String matchSubclassCode){
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SubclassCodeVO subclassCodeVO = null;
		String sql = "select * from SubclassCode where goodsSubclassCode= ?";
		try{
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, matchSubclassCode);
		int count = pstmt.executeUpdate();
		if(count>0){
			rs = pstmt.executeQuery();
			System.out.println("==============================");
			while(rs.next()){
				String goodsSubclassCode = rs.getString("goodsSubclassCode");
				String goodsSubclassName = rs.getString("goodsSubclassName");
				String goodsClassCode = rs.getString("goodsClassCode");
				
				subclassCodeVO = new SubclassCodeVO(goodsSubclassCode,goodsSubclassName,goodsClassCode);
				
				System.out.println("���� ��з� : "+goodsClassCode);
				System.out.println("�Һз��ڵ� : "+goodsSubclassCode);
				System.out.println("�� �Һз� ī�װ� �̸� : "+goodsSubclassName);
				System.out.println("==============================");
				}//end for while
		}//end of if
		else{
			System.out.println("�Һз� �ڵ� ã�� ����");
		}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
		return subclassCodeVO;
	}//end of dbSelectSubclassCode
	
		// 1.�Һз��߰�
	public void addSubclassCode(){
		//goodsSubclassCode varchar2(20),
		//goodsSubclassName varchar2(20),
		//goodsClassCode varchar2(20),
		
		System.out.println("*****�Һз��߰�â*****");
		System.out.println("--����� ��з� �ڵ带 �������ּ���");
		//��з� ��� ǥ��
		dbListClassCode();
		String goodsClassCode = sc.next();
		System.out.println("�Һз��ڵ� : ");
		String goodsSubclassCode = sc.next();
		System.out.println("�Һз��̸� : ");
		String goodsSubclassName = sc.next();
		SubclassCodeVO subclassCodeVO = new SubclassCodeVO(goodsSubclassCode,goodsSubclassName,goodsClassCode);
		
		//db�� �Է�
		dbAddSubclassCode(subclassCodeVO);
		//ArrayList�� �Է�
		arrayListSubclassCode.add(subclassCodeVO);
	
	}//end of addSubclassCode
	
	public void dbAddSubclassCode(SubclassCodeVO subclassCodeVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		String sql = "insert into SubclassCode values(?,?,?)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, subclassCodeVO.getGoodsSubclassCode());
			pstmt.setString(2, subclassCodeVO.getGoodsSubclassName());
			pstmt.setString(3, subclassCodeVO.getGoodsClassCode());

			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--�Һз� �ڵ� �߰� �Ϸ�");
			}else{
				System.out.println("--�Һз� �ڵ� �߰� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally	
	}//end of dbAddSubclassCode
	
		// 2.�Һз�����
	public void updateSubclassCodeCode( ){
		SubclassCodeVO subclassCode = null;

		System.out.println("*****�Һз� �ڵ� ����â*****");
		System.out.println(" ****�Һз� ���****");
		dbListSubclassCode();
		System.out.println("�Һз��ڵ� : ");
		String goodsSubclassCode = sc.next(); 
		subclassCode= dbSelectSubclassCode(goodsSubclassCode); //SubclassCodeVO �Һз� ���� ����
		

		if(subclassCode!=null){
			//goodsSubclassCode varchar2(20),
			//goodsSubclassName varchar2(20),
			//goodsClassCode varchar2(20),
			System.out.println("���� �� �Һз��̸� : ");
			String goodsSubclassName = sc.next();
			subclassCode.setGoodsSubclassName(goodsSubclassName);
			
			System.out.println(" ****��з� ���****");
			dbListClassCode();
			System.out.println("������ ��з��ڵ� : ");
			String goodsClassCode = sc.next();
			subclassCode.setGoodsClassCode(goodsClassCode);
			
			//subclassCode DB�� �ִ� �޼ҵ�
			dbupdateSubclassCode(subclassCode);
			///////////////////////////////////////////subclassCode ArrayList �� ������
		}
		else{
			System.out.println("--�ش��ϴ� �Һз� �ڵ�Code�� �����ϴ�.");
		}//end of else

	}//end of updateSubclassCodeCode()
	
	//ClassCode ���̺� ����
		public void dbupdateSubclassCode(SubclassCodeVO subclassCode){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "update SubclassCode set goodsSubclassName=?, goodsClassCode=? where goodsSubclassCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, subclassCode.getGoodsSubclassName());
				pstmt.setString(2, subclassCode.getGoodsClassCode());
				pstmt.setString(3, subclassCode.getGoodsSubclassCode());
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--�Һз�code ���� �Ϸ�");
				}else{
					System.out.println("--��з� �ڵ带 �߸� �Է��ϼ̽��ϴ�.");
				}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally
		}//end of dbupdateSubclassCode(SubclassCodeVO subclassCode)
	
		// 3.�Һз�����
		public void deleteSubclassCodeCode( ){
			SubclassCodeVO subclassCode = null;
			while(true){
				System.out.println("*****�Һз� �ڵ� ����â*****");
				
				System.out.println(" ****�Һз� ���****");
				dbListSubclassCode();
				System.out.println("�Һз��ڵ� : ");
				String goodsSubclassCode = sc.next(); 
				subclassCode= dbSelectSubclassCode(goodsSubclassCode); //SubclassCodeVO �Һз� ���� ����
			
				if(subclassCode!=null){
					//subclassCode DB�� �ִ� �޼ҵ�
					dbdeleteSubclassCode(subclassCode);
					///////////////////////////////////////////subclassCode ArrayList �� ������
				}
				else{
					System.out.println("--�ش��ϴ� �Һз� �ڵ�Code�� �����ϴ�.");
				}//end of else
			}//end of while
		}//end of deleteSubclassCodeCode()
		
		//ClassCode ���̺� ����
			public void dbdeleteSubclassCode(SubclassCodeVO subclassCode){
				con = getConnect();
				PreparedStatement pstmt = null;
				String sql = "delete from SubclassCode where goodsSubclassCode=?";
				try{
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, subclassCode.getGoodsSubclassCode());
					int count = pstmt.executeUpdate();
					if(count>0){
						System.out.println("--�Һз�code ���� �Ϸ�");
					}else{
						System.out.println("--�Һз�code ���� ����");
					}
				}catch(SQLException cne){
					cne.printStackTrace();
				}finally{
					try{
						con.close();
						pstmt.close();
					}catch(Exception e){	}
				}//end of finally
			}//end of dbdeleteSubclassCode(SubclassCodeVO subclassCode)
		
		
		// 4.�Һз����
	public void dbListSubclassCode(){
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from SubclassCode";
		try{
		pstmt = con.prepareStatement(sql);
		//goodsSubclassCode varchar2(20),
		//goodsSubclassName varchar2(20),
		//goodsClassCode varchar2(20),
		rs = pstmt.executeQuery();
		System.out.println("==============================");
		while(rs.next()){
			String goodsSubclassCode = rs.getString("goodsSubclassCode");
			String goodsSubclassName = rs.getString("goodsSubclassName");
			String goodsClassCode = rs.getString("goodsClassCode");
			
			System.out.println("��з��ڵ� : "+goodsClassCode);
			System.out.println("�Һз��ڵ� : "+goodsSubclassCode);
			System.out.println("�Һз��̸� : "+goodsSubclassName);
			
			System.out.println("==============================");
			}//end for while
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dbListSubclassCode
	
	
	
		/////////////////////////////////////////////////////////////////////
		//��з�
	//�ش� �ڵ� Ŭ���ؼ� ���� ������ �޼ҵ� db	
	public ClassCodeVO dbSelectClassCode(String matchClassCode){
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ClassCodeVO classCodeVO = null;
		String sql = "select * from ClassCode where goodsClassCode= ?";
		try{
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, matchClassCode);
		int count = pstmt.executeUpdate();
		if(count>0){
			rs = pstmt.executeQuery();
			System.out.println("==============================");
			while(rs.next()){
				String goodsClassCode = rs.getString("goodsClassCode");
				String goodsClassName = rs.getString("goodsClassName");

				//goodsClassCode varchar2(20),
				//goodsClassName varchar2(20),
				classCodeVO = new ClassCodeVO(goodsClassCode,goodsClassName);
				
				System.out.println("��з��ڵ� : "+goodsClassCode);
				System.out.println("��з��̸� : "+goodsClassName);
				System.out.println("==============================");
			}//end for while
		}//end of if
		else{
			System.out.println("��з� �ڵ� ã�� ����");
		}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
		return classCodeVO;
	}//end of dbSelectClassCode
	
	
		// 1.��з��߰�
	public void addClassCode(){
		//goodsClassCode varchar2(20),
		//goodsClassName varchar2(20),
	
		System.out.println("*****��з��߰�â*****");
		System.out.println("��з��ڵ� : ");
		String goodsClassCode = sc.next();
		System.out.println("��з��̸� : ");
		String goodsClassName = sc.next();
		ClassCodeVO classCodeVO = new ClassCodeVO(goodsClassCode,goodsClassName);
		
		//db�� �Է�
		dbAddClassCode(classCodeVO);
		//ArrayList�� �Է�
		arrayListClassCode.add(classCodeVO);
		
	}//end of addClassCode(MartMemberVO memberVO)
	
	public void dbAddClassCode(ClassCodeVO classCodeVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		String sql = "insert into ClassCode values(?,?)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, classCodeVO.getGoodsClassCode());
			pstmt.setString(2, classCodeVO.getGoodsClassName());

			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--��з� �ڵ� �߰� �Ϸ�");
			}else{
				System.out.println("--��з� �ڵ� �߰� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally	
	}//end of dbAddClassCode(ClassCodeVO classCodeVO)
	
		// 2.��з�����
	public void updateClassCode( ){
		ClassCodeVO classCodeVO = null;

		System.out.println("*****��з� �ڵ� ����â*****");
		System.out.println(" ****��з� ���****");
		dbListClassCode();
		System.out.println("��з��ڵ� : ");
		String goodsClassCode = sc.next();
		
		classCodeVO= dbSelectClassCode(goodsClassCode);
		
		if(classCodeVO!=null){
			System.out.println("��з��̸� : ");
			String goodsClassName = sc.next();
			classCodeVO.setGoodsClassName(goodsClassName);
			
			//ClassCode DB�� �ִ� �޼ҵ�
			dbupdateClassCode(classCodeVO);
			///////////////////////////////////////////ClassCode ArrayList �� ������
		}
		else{
			System.out.println("--�ش��ϴ� ��з� �ڵ�Code�� �����ϴ�.");
		}//end of else

	}//end of updateGoods()
	
	//ClassCode ���̺� ����
		public void dbupdateClassCode(ClassCodeVO classCodeVO){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "update ClassCode set goodsClassName=? where goodsClassCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, classCodeVO.getGoodsClassName());
				pstmt.setString(2, classCodeVO.getGoodsClassCode());
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--��з�code ���� �Ϸ�");
				}else{
					System.out.println("--��з�code ���� ����");
				}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally
		}//end of dbupdateClassCode(ClassCodeVO classCodeVO)
	
		// 3.��з�����
		public void deleteClassCode( ){
			ClassCodeVO classCodeVO = null;
			System.out.println("*****��з� �ڵ� ����â*****");
			System.out.println(" ****��з� ���****");
			dbListClassCode();
			System.out.println("��з��ڵ� : ");
			String goodsClassCode = sc.next();
			
			classCodeVO= dbSelectClassCode(goodsClassCode);
			
			if(classCodeVO!=null){
				System.out.println("���� ��û ���Դϴ�.");
				//ClassCode DB�� �ִ� �޼ҵ�
				dbdeleteClassCode(classCodeVO);
				///////////////////////////////////////////ClassCode ArrayList �� ������
			}
			else{
				System.out.println("--�ش��ϴ� ��з� �ڵ�Code�� �����ϴ�.");
			}//end of else
		
		}//end of deleteClassCode()
		
		//ClassCode ���̺� ����
		public void dbdeleteClassCode(ClassCodeVO classCodeVO){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "delete from ClassCode where goodsClassCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, classCodeVO.getGoodsClassCode());
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--��з�code ���� �Ϸ�");
				}else{
					System.out.println("--��з�code ���� ����");
				}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally
		}//end of dbdeleteClassCode(ClassCodeVO classCodeVO)
		
		
		// 4.��з����
	public void dbListClassCode(){
		con = getConnect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from ClassCode";
		try{
		pstmt = con.prepareStatement(sql);

		rs = pstmt.executeQuery();
		System.out.println("==============================");
		while(rs.next()){
			//goodsClassCode varchar2(20),
			//goodsClassName varchar2(20),
			String goodsClassCode = rs.getString("goodsClassCode");
			String goodsClassName = rs.getString("goodsClassName");
			
			System.out.println("��ǰ�ڵ� : "+goodsClassCode);
			System.out.println("��ǰ�̸� :"+goodsClassName);
			System.out.println("==============================");
			}//end for while
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally
	}//end of dbListClassCode
		
	
}//end of MartClassCodeSVC
