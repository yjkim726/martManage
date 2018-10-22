package JDBCProject;

import static JDBCProject.JdbcUtil.getConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class KeywordSVC {
	Scanner sc = new Scanner(System.in);
	Connection con = null;
	ArrayList<KeywordVO> arrayListKeywordVO = new ArrayList<KeywordVO>();
	
	//Ű���� �߰�
	public void addkeyword(MartClassCodeSVC martClassCodeSVC){
		//keywordCode varchar2(20),
		//keywordName varchar2(20),
		//keywordRegistrationDate date,
		//goodsSubclassCode varchar2(20),
		while(true){
			System.out.println("*****Ű�����߰�â*****");
			//��з� �ڵ� ����
			martClassCodeSVC.dbListClassCode();
			String selectedClassCode = sc.next();
			//�Һз� �ڵ� ���� - ��з� ���ý� �� ���� ���̺� ��ϵ�
			martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
			String goodsSubclassCode = sc.next();
			
			System.out.println("Ű�����ڵ带 �Է����ּ��� : ");
			String keywordCode = sc.next();
			System.out.println("Ű�����̸��� �Է����ּ��� : ");
			String keywordName = sc.next();
			
			KeywordVO keywordVO = new KeywordVO(keywordCode,keywordName,goodsSubclassCode);
			//db�� �Է�
			dbAddkeyword(keywordVO);
		}//end of while
	}//end of addSubclassCode
	
	public void dbAddkeyword(KeywordVO keywordVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		String sql = "insert into Keyword values(?,?,defualt,?)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keywordVO.getKeywordCode());
			pstmt.setString(2, keywordVO.getKeywordName());
			pstmt.setString(3, keywordVO.getGoodsSubclassCode());

			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--Ű���� �߰� �Ϸ�");
			}else{
				System.out.println("--Ű���� �߰� ����");
			}
		}catch(SQLException cne){
			cne.printStackTrace();
		}finally{
			try{
				con.close();
				pstmt.close();
			}catch(Exception e){	}
		}//end of finally	
	}//end of dbAddkeyword(KeywordVO keywordVO)
	
	//Ű���� ����
		public void updateKeyword(MartClassCodeSVC martClassCodeSVC){
			while(true){
				System.out.println("*****Ű�������â*****");
				//��з� �ڵ� ����
				martClassCodeSVC.dbListClassCode();
				String selectedClassCode = sc.next();
				//�Һз� �ڵ� ���� - ��з� ���ý� �� ���� ���̺� ��ϵ�
				martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
				String goodsSubclassCode = sc.next();
				
				//�Һз� ���ý� Ű���� �˻��ϴ� �޼ҵ�
				dbMatchKeyword(goodsSubclassCode);
				System.out.println("������ Ű�����ڵ带 �Է����ּ��� : ");
				String keywordCode = sc.next();
				
				System.out.println("������ Ű�����ڵ尪�� �Է����ּ��� : ");
				String reKeywordCode = sc.next();
				System.out.println("������ Ű�����̸��� �Է����ּ��� : ");
				String keywordName = sc.next();
				
				KeywordVO keywordVO = new KeywordVO(reKeywordCode,keywordName,goodsSubclassCode);
				//db�� �Է�
				dbUpdateKeyword(keywordVO,keywordCode);
			}//end of while
		}//end of updatekeyword(MartClassCodeSVC martClassCodeSVC)
		
		public void dbUpdateKeyword(KeywordVO keywordVO,String keywordCode){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "update Keyword set keywordCode=? keywordName =? where keywordCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keywordVO.getKeywordCode());
				pstmt.setString(2, keywordVO.getKeywordName());
				pstmt.setString(3, keywordCode);

				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--Ű���� �߰� �Ϸ�");
				}else{
					System.out.println("--Ű���� �߰� ����");
				}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally	
		}//end of dbUpdatekeyword(KeywordVO keywordVO,String keywordCode)
	
	//Ű���� ����
		public void deleteKeyword(MartClassCodeSVC martClassCodeSVC){
			while(true){
				System.out.println("*****Ű���� ����â*****");
				//��з� �ڵ� ����
				martClassCodeSVC.dbListClassCode();
				String selectedClassCode = sc.next();
				//�Һз� �ڵ� ���� - ��з� ���ý� �� ���� ���̺� ��ϵ�
				martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
				String goodsSubclassCode = sc.next();
				
				//�Һз� ���ý� Ű���� �˻��ϴ� �޼ҵ�
				dbMatchKeyword(goodsSubclassCode);
				System.out.println("������ Ű�����ڵ带 �Է����ּ��� : ");
				String keywordCode = sc.next();
				
				//db�� �Է�
				dbDeletekeyword(keywordCode);
			}//end of while
		}//end of updatekeyword(MartClassCodeSVC martClassCodeSVC)
		
		public void dbDeletekeyword(String keywordCode){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "delete from Keyword where keywordCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keywordCode);

				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--Ű���� ���� �Ϸ�");
				}else{
					System.out.println("--Ű���� ���� ����");
				}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally	
		}//end of dbUpdatekeyword(KeywordVO keywordVO,String keywordCode)
		
		
		
	//Ű���� �˻�
		public void dbMatchKeyword(String matchSubclassCode){
			con = getConnect();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select * from Keyword where goodsSubclassCode= ?";
			try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, matchSubclassCode);
			int count = pstmt.executeUpdate();
			if(count>0){
				rs = pstmt.executeQuery();
				System.out.println("==============================");
				while(rs.next()){
					String keywordCode = rs.getString("keywordCode");
					String keywordName = rs.getString("keywordName");
					
					System.out.println("keywordCode : "+keywordCode);
					System.out.println("keywordName : "+keywordName);
					System.out.println("==============================");
					}//end for while
				}//end of if
			else{
				System.out.println("Ű���� ã�� ����");
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
}//end of KeywordSVC
