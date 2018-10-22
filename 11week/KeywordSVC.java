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
	
	//키워드 추가
	public void addkeyword(MartClassCodeSVC martClassCodeSVC){
		//keywordCode varchar2(20),
		//keywordName varchar2(20),
		//keywordRegistrationDate date,
		//goodsSubclassCode varchar2(20),
		while(true){
			System.out.println("*****키워드추가창*****");
			//대분류 코드 선택
			martClassCodeSVC.dbListClassCode();
			String selectedClassCode = sc.next();
			//소분류 코드 선택 - 대분류 선택시 그 하위 테이블 목록들
			martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
			String goodsSubclassCode = sc.next();
			
			System.out.println("키워드코드를 입력해주세요 : ");
			String keywordCode = sc.next();
			System.out.println("키워드이름을 입력해주세요 : ");
			String keywordName = sc.next();
			
			KeywordVO keywordVO = new KeywordVO(keywordCode,keywordName,goodsSubclassCode);
			//db에 입력
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
				System.out.println("--키워드 추가 완료");
			}else{
				System.out.println("--키워드 추가 실패");
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
	
	//키워드 수정
		public void updateKeyword(MartClassCodeSVC martClassCodeSVC){
			while(true){
				System.out.println("*****키워드수정창*****");
				//대분류 코드 선택
				martClassCodeSVC.dbListClassCode();
				String selectedClassCode = sc.next();
				//소분류 코드 선택 - 대분류 선택시 그 하위 테이블 목록들
				martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
				String goodsSubclassCode = sc.next();
				
				//소분류 선택시 키워드 검색하는 메소드
				dbMatchKeyword(goodsSubclassCode);
				System.out.println("수정할 키워드코드를 입력해주세요 : ");
				String keywordCode = sc.next();
				
				System.out.println("수정될 키워드코드값을 입력해주세요 : ");
				String reKeywordCode = sc.next();
				System.out.println("수정할 키워드이름을 입력해주세요 : ");
				String keywordName = sc.next();
				
				KeywordVO keywordVO = new KeywordVO(reKeywordCode,keywordName,goodsSubclassCode);
				//db에 입력
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
					System.out.println("--키워드 추가 완료");
				}else{
					System.out.println("--키워드 추가 실패");
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
	
	//키워드 삭제
		public void deleteKeyword(MartClassCodeSVC martClassCodeSVC){
			while(true){
				System.out.println("*****키워드 삭제창*****");
				//대분류 코드 선택
				martClassCodeSVC.dbListClassCode();
				String selectedClassCode = sc.next();
				//소분류 코드 선택 - 대분류 선택시 그 하위 테이블 목록들
				martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
				String goodsSubclassCode = sc.next();
				
				//소분류 선택시 키워드 검색하는 메소드
				dbMatchKeyword(goodsSubclassCode);
				System.out.println("삭제할 키워드코드를 입력해주세요 : ");
				String keywordCode = sc.next();
				
				//db에 입력
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
					System.out.println("--키워드 삭제 완료");
				}else{
					System.out.println("--키워드 삭제 실패");
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
		
		
		
	//키워드 검색
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
				System.out.println("키워드 찾기 실패");
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
