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
	//1. 분류코드
	
		/////////////////////////////////////////////////////////////////////
		//소분류
	//대분류 클릭했을때 관련정보
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
				
				System.out.println("소분류코드 : "+goodsSubclassCode);
				System.out.println("소분류이름 : "+goodsSubclassName);
				System.out.println("==============================");
				}//end for while
			}//end of if
		else{
			System.out.println("소분류 코드 찾기 실패");
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
	
	
	//해당 코드 클릭해서 정보 얻어오는 메소드 db	
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
				
				System.out.println("연계 대분류 : "+goodsClassCode);
				System.out.println("소분류코드 : "+goodsSubclassCode);
				System.out.println("현 소분류 카테고리 이름 : "+goodsSubclassName);
				System.out.println("==============================");
				}//end for while
		}//end of if
		else{
			System.out.println("소분류 코드 찾기 실패");
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
	
		// 1.소분류추가
	public void addSubclassCode(){
		//goodsSubclassCode varchar2(20),
		//goodsSubclassName varchar2(20),
		//goodsClassCode varchar2(20),
		
		System.out.println("*****소분류추가창*****");
		System.out.println("--연계될 대분류 코드를 선택해주세요");
		//대분류 목록 표시
		dbListClassCode();
		String goodsClassCode = sc.next();
		System.out.println("소분류코드 : ");
		String goodsSubclassCode = sc.next();
		System.out.println("소분류이름 : ");
		String goodsSubclassName = sc.next();
		SubclassCodeVO subclassCodeVO = new SubclassCodeVO(goodsSubclassCode,goodsSubclassName,goodsClassCode);
		
		//db에 입력
		dbAddSubclassCode(subclassCodeVO);
		//ArrayList에 입력
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
				System.out.println("--소분류 코드 추가 완료");
			}else{
				System.out.println("--소분류 코드 추가 실패");
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
	
		// 2.소분류수정
	public void updateSubclassCodeCode( ){
		SubclassCodeVO subclassCode = null;

		System.out.println("*****소분류 코드 수정창*****");
		System.out.println(" ****소분류 목록****");
		dbListSubclassCode();
		System.out.println("소분류코드 : ");
		String goodsSubclassCode = sc.next(); 
		subclassCode= dbSelectSubclassCode(goodsSubclassCode); //SubclassCodeVO 소분류 선택 정보
		

		if(subclassCode!=null){
			//goodsSubclassCode varchar2(20),
			//goodsSubclassName varchar2(20),
			//goodsClassCode varchar2(20),
			System.out.println("수정 될 소분류이름 : ");
			String goodsSubclassName = sc.next();
			subclassCode.setGoodsSubclassName(goodsSubclassName);
			
			System.out.println(" ****대분류 목록****");
			dbListClassCode();
			System.out.println("수정할 대분류코드 : ");
			String goodsClassCode = sc.next();
			subclassCode.setGoodsClassCode(goodsClassCode);
			
			//subclassCode DB에 넣는 메소드
			dbupdateSubclassCode(subclassCode);
			///////////////////////////////////////////subclassCode ArrayList 도 만들어요
		}
		else{
			System.out.println("--해당하는 소분류 코드Code가 없습니다.");
		}//end of else

	}//end of updateSubclassCodeCode()
	
	//ClassCode 테이블에 수정
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
					System.out.println("--소분류code 수정 완료");
				}else{
					System.out.println("--대분류 코드를 잘못 입력하셨습니다.");
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
	
		// 3.소분류삭제
		public void deleteSubclassCodeCode( ){
			SubclassCodeVO subclassCode = null;
			while(true){
				System.out.println("*****소분류 코드 삭제창*****");
				
				System.out.println(" ****소분류 목록****");
				dbListSubclassCode();
				System.out.println("소분류코드 : ");
				String goodsSubclassCode = sc.next(); 
				subclassCode= dbSelectSubclassCode(goodsSubclassCode); //SubclassCodeVO 소분류 선택 정보
			
				if(subclassCode!=null){
					//subclassCode DB에 넣는 메소드
					dbdeleteSubclassCode(subclassCode);
					///////////////////////////////////////////subclassCode ArrayList 도 만들어요
				}
				else{
					System.out.println("--해당하는 소분류 코드Code가 없습니다.");
				}//end of else
			}//end of while
		}//end of deleteSubclassCodeCode()
		
		//ClassCode 테이블에 수정
			public void dbdeleteSubclassCode(SubclassCodeVO subclassCode){
				con = getConnect();
				PreparedStatement pstmt = null;
				String sql = "delete from SubclassCode where goodsSubclassCode=?";
				try{
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, subclassCode.getGoodsSubclassCode());
					int count = pstmt.executeUpdate();
					if(count>0){
						System.out.println("--소분류code 삭제 완료");
					}else{
						System.out.println("--소분류code 삭제 실패");
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
		
		
		// 4.소분류목록
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
			
			System.out.println("대분류코드 : "+goodsClassCode);
			System.out.println("소분류코드 : "+goodsSubclassCode);
			System.out.println("소분류이름 : "+goodsSubclassName);
			
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
		//대분류
	//해당 코드 클릭해서 정보 얻어오는 메소드 db	
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
				
				System.out.println("대분류코드 : "+goodsClassCode);
				System.out.println("대분류이름 : "+goodsClassName);
				System.out.println("==============================");
			}//end for while
		}//end of if
		else{
			System.out.println("대분류 코드 찾기 실패");
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
	
	
		// 1.대분류추가
	public void addClassCode(){
		//goodsClassCode varchar2(20),
		//goodsClassName varchar2(20),
	
		System.out.println("*****대분류추가창*****");
		System.out.println("대분류코드 : ");
		String goodsClassCode = sc.next();
		System.out.println("대분류이름 : ");
		String goodsClassName = sc.next();
		ClassCodeVO classCodeVO = new ClassCodeVO(goodsClassCode,goodsClassName);
		
		//db에 입력
		dbAddClassCode(classCodeVO);
		//ArrayList에 입력
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
				System.out.println("--대분류 코드 추가 완료");
			}else{
				System.out.println("--대분류 코드 추가 실패");
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
	
		// 2.대분류수정
	public void updateClassCode( ){
		ClassCodeVO classCodeVO = null;

		System.out.println("*****대분류 코드 수정창*****");
		System.out.println(" ****대분류 목록****");
		dbListClassCode();
		System.out.println("대분류코드 : ");
		String goodsClassCode = sc.next();
		
		classCodeVO= dbSelectClassCode(goodsClassCode);
		
		if(classCodeVO!=null){
			System.out.println("대분류이름 : ");
			String goodsClassName = sc.next();
			classCodeVO.setGoodsClassName(goodsClassName);
			
			//ClassCode DB에 넣는 메소드
			dbupdateClassCode(classCodeVO);
			///////////////////////////////////////////ClassCode ArrayList 도 만들어요
		}
		else{
			System.out.println("--해당하는 대분류 코드Code가 없습니다.");
		}//end of else

	}//end of updateGoods()
	
	//ClassCode 테이블에 수정
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
					System.out.println("--대분류code 수정 완료");
				}else{
					System.out.println("--대분류code 수정 실패");
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
	
		// 3.대분류삭제
		public void deleteClassCode( ){
			ClassCodeVO classCodeVO = null;
			System.out.println("*****대분류 코드 삭제창*****");
			System.out.println(" ****대분류 목록****");
			dbListClassCode();
			System.out.println("대분류코드 : ");
			String goodsClassCode = sc.next();
			
			classCodeVO= dbSelectClassCode(goodsClassCode);
			
			if(classCodeVO!=null){
				System.out.println("삭제 요청 중입니다.");
				//ClassCode DB에 넣는 메소드
				dbdeleteClassCode(classCodeVO);
				///////////////////////////////////////////ClassCode ArrayList 도 만들어요
			}
			else{
				System.out.println("--해당하는 대분류 코드Code가 없습니다.");
			}//end of else
		
		}//end of deleteClassCode()
		
		//ClassCode 테이블에 수정
		public void dbdeleteClassCode(ClassCodeVO classCodeVO){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "delete from ClassCode where goodsClassCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, classCodeVO.getGoodsClassCode());
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--대분류code 삭제 완료");
				}else{
					System.out.println("--대분류code 삭제 실패");
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
		
		
		// 4.대분류목록
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
			
			System.out.println("물품코드 : "+goodsClassCode);
			System.out.println("물품이름 :"+goodsClassName);
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
