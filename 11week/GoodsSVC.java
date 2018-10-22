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
	//물품추가
	public void addGoods(MartMemberVO memberVO, MartClassCodeSVC martClassCodeSVC){
	
		System.out.println("*****물품추가창*****");
		//대분류 코드 선택
		martClassCodeSVC.dbListClassCode();
		String selectedClassCode = sc.next();
		//소분류 코드 선택 - 대분류 선택시 그 하위 테이블 목록들
		martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
		String selectedSubclassCode = sc.next();
		
		//물품 Code 지정
		System.out.print("GoodsCode : ");
		String goodsCode = sc.next();
		
		//해당 Code가 있나 중복검사
		GoodsVO matchgoodsVO = matchGoodsVOCode(goodsCode,selectedSubclassCode);
		if(matchgoodsVO!=null){
			//해당 Code가 있다!
			System.out.println("--해당하는 물품Code가 이미존재합니다.");
		}
		else{
			System.out.println("--중복검사 완료");
			System.out.println("--물품세부 설정");
			System.out.print("물품 이름 : ");
			String goodsName = sc.next();
			System.out.print("물품 가격 : ");
			int goodsPrice = sc.nextInt();
			
			String goodsDiscountWhether = "N"; //물품할인여부
			String goodsDeleteWhether = "Y"; //물품삭제여부
			String goodsRestrictWhether = "N";  //물품제재여부
			
			
			//GoodsVO(String goodsCode, String goodsName, int goodsPrice, 
			//String goodsDiscountWhether (물품할인여부), String goodsDeleteWhether(물품삭제여부), 
			//String goodsRestrictWhether(물품제재여부),String goodSubclassCode(물품소분류코드), String memberId )
			GoodsVO goodsVO = new GoodsVO(goodsCode,goodsName,goodsPrice,goodsDiscountWhether,goodsDeleteWhether
					,goodsRestrictWhether,selectedSubclassCode,memberVO.getMemberId());
			//Goods DB에 넣는 메소드
			dbAddGoods(goodsVO);
			///////////////////////////////////////////Goods ArrayList 도 만들어요
		}//end of else
	
	}//end of addGoods()
		
	//물품 Code가 잇는지 중복검사
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
				//goodsDiscountWhether varchar2(15), --물품 할인 여부  Goods.java에선 boolean
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
		
	//Goods 테이블에 추가
	public void dbAddGoods(GoodsVO goodsVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		
		String sql = "insert into Goods values(?,?,?,?,?,?,default,?,?)";
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, goodsVO.getGoodsCode());
			pstmt.setString(2, goodsVO.getGoodsName());
			pstmt.setInt(3, goodsVO.getGoodsPrice());
			pstmt.setString(4, goodsVO.getGoodsDiscountWhether()); //물품할인여부
			pstmt.setString(5, goodsVO.getGoodsDeleteWhether()); //물품삭제여부
			pstmt.setString(6, goodsVO.getGoodsRestrictWhether()); //물품제재여부
			//////////////날짜넣기
			pstmt.setString(7, goodsVO.getGoodSubclassCode()); //물품소분류코드
			pstmt.setString(8, goodsVO.getMemberId());
			
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--물품 입력 완료");
			}else{
				System.out.println("--물품 입력 실패");
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
	//물품수정
	public void updateGoods(MartClassCodeSVC martClassCodeSVC){

		System.out.println("*****물품수정창*****");
		GoodsVO matchgoodsVO = selectedCodeSearchGoods(martClassCodeSVC);
		//해당 Code가 있나 중복검사
		String reGoodsName = matchgoodsVO.getGoodsName();
		
		if(matchgoodsVO!=null){
			//해당 Code가 있다!
			if(matchgoodsVO.getGoodsRestrictWhether().equals("Y")||matchgoodsVO.getGoodsRestrictWhether().equals("y")){
				System.out.println("물품판매 금지당한 상품입니다.");
			}else{
				System.out.println("--물품세부 설정");			
				System.out.print("물품 이름 : ");
				String goodsName = sc.next();
				System.out.print("물품 가격 : ");
				int goodsPrice = sc.nextInt();
				System.out.print("물품할인여부 [y/n]: ");
				String goodsDiscountWhether = sc.next();
	
				//GoodsVO(String goodsCode, String goodsName, int goodsPrice, 
				//String goodsDiscountWhether (물품할인여부), String goodsDeleteWhether(물품삭제여부), 
				//String goodsRestrictWhether(물품제재여부),String goodSubclassCode(물품소분류코드), String memberId )
				matchgoodsVO.setGoodsName(goodsName);
				matchgoodsVO.setGoodsPrice(goodsPrice);
				matchgoodsVO.setGoodsDiscountWhether(goodsDiscountWhether);
				//Goods DB에 넣는 메소드
				dbUpdateGoods(matchgoodsVO);
				
				if((goodsDiscountWhether.equals("y")||goodsDiscountWhether.equals("Y"))){
					//물품할인기록테이블에 기록
					goodsDiscountRecord(matchgoodsVO);
				}
				
				//물품정보수정이력 테이블 생성
				dbGoodsModificationHistory(matchgoodsVO,reGoodsName);
				
				///////////////////////////////////////////Goods ArrayList 도 만들어요
			}
		}
		else{
			System.out.println("--해당하는 물품Code가 없습니다.");	}//end of else
	
	}//end of updateGoods()
	
	//Goods 테이블에 추가
		public void dbUpdateGoods(GoodsVO goodsVO){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "update Goods set goodsName=? , goodsPrice=?, goodsDiscountWhether=?  where goodsCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, goodsVO.getGoodsName());
				pstmt.setInt(2, goodsVO.getGoodsPrice());
				pstmt.setString(3, goodsVO.getGoodsDiscountWhether()); //물품할인여부
				pstmt.setString(4, goodsVO.getGoodsCode());
			
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--물품 수정 완료");
				}else{
					System.out.println("--물품 수정 실패");
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
	//물품삭제
	public void deleteGoods(MartClassCodeSVC martClassCodeSVC){
			System.out.println("*****물품삭제창*****");
			//대분류 코드 선택
			martClassCodeSVC.dbListClassCode();
			String selectedClassCode = sc.next();
			//소분류 코드 선택 - 대분류 선택시 그 하위 테이블 목록들
			martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
			String selectedSubclassCode = sc.next();
			
			//물품 Code 지정
			System.out.print("GoodsCode : ");
			String goodsCode = sc.next();
			
			//해당 물품Code 찾기
			GoodsVO matchgoodsVO = matchGoodsVOCode(goodsCode,selectedSubclassCode);
			if(matchgoodsVO!=null){
				System.out.println("--삭제 진행중입니다.");
				dbUpdateGoods(matchgoodsVO);
				///////////////////////////////////////////Goods ArrayList 도 만들어요
			}
			else{
				System.out.println("--해당하는 물품Code가 없습니다.");
			}//end of else
	
	}//end of deleteGoods()
		
	//Goods 테이블에 추가
		public void dbDeleteGoods(GoodsVO goodsVO){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "delete from Goods where goodsCode=?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, goodsVO.getGoodsCode());
			
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--물품 삭제 완료");
				}else{
					System.out.println("--물품 삭제 실패");
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
	
	//물품 목록
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
	
			
	//장바구니 
	public GoodsPurchaseVO shoppingBasket(MartClassCodeSVC martClassCodeSVC,MartMemberVO LoginMember){
		String goodsPurchaseWay = null;
		GoodsPurchaseVO goodsPurchaseVO = null;
		System.out.println("*****장바구니*****");
		GoodsVO matchgoodsVO =selectedCodeSearchGoods(martClassCodeSVC);
		 // []goodsPurchaseNumber varchar2(20), --물품구매번호
		 // goodsCode varchar2(20),  --물품번호
		 // memberId varchar2(20),  --회원 ID
		 // goodsPurchasePrice int, --물품구매가격
		 // goodsPurchaseWay varchar2(15), --물품구매방법
		 // goodsReceiver varchar2(20), --물품수신자
		 // goodsAddress varchar2(30), --물품배송지
		 // goodsDeliveryFinishWhether varchar2(15),  --물품배송완료여부
		 // goodsRefundWhether varchar2(15),  --물품환불여부
		//  []goodsPurchaseDate date, --물품구매일자
		System.out.println("장바구니에 넣으시겠습니까? [y/n]");
		String goodsPurchase = sc.next();
		
		if(goodsPurchase.equals("Y")||goodsPurchase.equals("y")){
			System.out.println("구매방법 [1.무통장입금/2.신용카드/3.핸드폰]");
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
				System.out.println("다시 선택해주세요.");
			}
			System.out.println("물품 배송지를 입력해주세요 :");
			String goodsAddress = sc.next();
			String goodsDeliveryFinishWhether = "N";
			String goodsRefundWhether = "N";
			goodsPurchaseVO = new GoodsPurchaseVO(
					matchgoodsVO.getGoodsCode(), LoginMember.getMemberId(), matchgoodsVO.getGoodsPrice(),
					goodsPurchaseWay, LoginMember.getMemberName(),goodsAddress,
					goodsDeliveryFinishWhether,goodsRefundWhether);
			dbShoppingBasket(goodsPurchaseVO);
			System.out.println("물품 선택이 완료되었습니다.");
		}else{
			System.out.println("물품선택이 취소되었습니다.");   }
	
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
				System.out.println("--장바구니 입력 완료");
			}else{
				System.out.println("--장바구니 입력 실패");
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
	
	
	//장바구니 
	public void dbListShoppingBasket(GoodsPurchaseVO goodsPurchaseVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		String sql = "select * from GoodsPurchase where memberId =?";
		try{
			 // []goodsPurchaseNumber varchar2(20), --물품구매번호
			 // goodsCode varchar2(20),  --물품번호
			 // memberId varchar2(20),  --회원 ID
			 // goodsPurchasePrice int, --물품구매가격
			 // goodsPurchaseWay varchar2(15), --물품구매방법
			 // goodsReceiver varchar2(20), --물품수신자
			 // goodsAddress varchar2(30), --물품배송지
			 // goodsDeliveryFinishWhether varchar2(15),  --물품배송완료여부
			 // goodsRefundWhether varchar2(15),  --물품환불여부
			//  []goodsPurchaseDate date, --물품구매일자
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
					  goodsDeliveryFinishWhether = "배송완료";
					  goodsRefundWhether = "환불완료";
				  }
				  
				  if((goodsDeliveryFinishWhether.equals("n"))||(goodsDeliveryFinishWhether.equals("N")||
						  goodsRefundWhether.equals("n"))||goodsRefundWhether.equals("N")){
					  goodsDeliveryFinishWhether = "배송준비중";
					  goodsRefundWhether = null;
				  }
				  
				  System.out.println("[ "+goodsDiscountRecordNumber+"]"+ "물품코드 : "+goodsCode
						  + "회원 ID : "+memberId); 
				  System.out.println("물품구매가격 : "+goodsPurchasePrice);
				  System.out.println("물품구매방법 : "+goodsPurchaseWay);
				  System.out.println("물품수신자 : "+goodsReceiver);
				  System.out.println("물품주소 : "+goodsAddress);
				  System.out.println("물품 배송 : "+goodsDeliveryFinishWhether);
				  if(goodsRefundWhether!=null){
				  System.out.println("물품 환불 : "+goodsRefundWhether);}
				  System.out.println("물품구매일자 : "+goodsPurchaseDate);
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
	
	
	//물품정보수정이력
	//물품정보수정이력 추가
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
			pstmt.setString(3, "가격 : "+matchgoodsVO.getGoodsPrice()+"할인여부 :"+matchgoodsVO.getGoodsDiscountWhether());
			
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("--물품정보수정이력 입력 완료");
			}else{
				System.out.println("--물품정보수정이력 입력 실패");
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
	
	//물품정보수정이력 목록출력
	public void listGoodsModificationHistory(MartClassCodeSVC martClassCodeSVC){
		System.out.println("*****물품정보수정이력 목록창*****");
		GoodsVO matchgoodsVO = selectedCodeSearchGoods(martClassCodeSVC);
		if(matchgoodsVO!=null){
			dbListGoodsModificationHistory(matchgoodsVO);
		}//end of if
		else{
			System.out.println("제대로 된 물품코드를 입력해주세요.");
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
				  
				  System.out.println("[ "+goodsModificationHistoryNumber+"]"+ "물품코드 : "+goodsCode
						  + "수정이름 : "+goodsModificationCode); 
				  System.out.println("수정내용 : "+goodsModificationContent);
				  System.out.println("수정날짜 : "+goodsModificationDate);
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
	
		///////////////////////////////////////////제재
	//물품제재이력
	
	public void goodsRestrictRecord(MartClassCodeSVC martClassCodeSVC){
		System.out.println("*****물품제재이력 목록창*****");
		GoodsVO matchgoodsVO = selectedCodeSearchGoods(martClassCodeSVC);
		if(matchgoodsVO!=null){
			System.out.println("제재를 하겠습니까?[Y/N] : ");
			String goodsRestrictWhether = sc.next();
			if(goodsRestrictWhether.equals("Y")){
				matchgoodsVO.setGoodsRestrictWhether("Y");
				System.out.println("제재코드이름 : ");
				String goodsRestrictCode = sc.next();
				System.out.println("제재사유 : ");
				String goodsRestrictContent = sc.next();
				dbGoodsRestrictRecord(matchgoodsVO,goodsRestrictCode,goodsRestrictContent);}
			else if(goodsRestrictWhether.equals("N")){
				matchgoodsVO.setGoodsRestrictWhether("N");
			}
			dbGoodsRestrictWhether(matchgoodsVO);
		}//end of if
		else{
			System.out.println("제대로 된 물품코드를 입력해주세요.");
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
				System.out.println("--물품 제재 수정 완료");
			}else{
				System.out.println("--물품 제재 수정 실패");
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
	
	
	//물품제재이력추가
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
				System.out.println("--물품제재이력 입력 완료");
			}else{
				System.out.println("--물품제재이력입력 실패");
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
	

	
	
///////////////////////////////////////////할인
	//물품할인이력
	//물품할인이력추가
	public void goodsDiscountRecord(GoodsVO matchgoodsVO){
		System.out.println("*****물품할인창*****");
		System.out.println("물품할인이름 : ");
		String goodsDiscountName = sc.next();	
		System.out.println("물품할인내용 : ");
		String goodsDiscountContent = sc.next();
		System.out.println("물품할인가격 : ");
		int goodsDiscountPrice = sc.nextInt();
		System.out.println("물품할인시작일자 : ");
		String goodsDiscountStartDate = sc.next();
		System.out.println("물품할인종료일자 : ");
		String goodsDiscountEndDate = sc.next();
		
		
		GoodsDiscountVO goodsDiscountVO = new GoodsDiscountVO(
				goodsDiscountName,	goodsDiscountContent,goodsDiscountPrice,
				 goodsDiscountStartDate,goodsDiscountEndDate);
		
		//db에 입력
		dbGoodsDiscountRecord(matchgoodsVO, goodsDiscountVO);
	}//end of goodsDiscountRecord
	
	public void dbGoodsDiscountRecord(GoodsVO matchgoodsVO,GoodsDiscountVO goodsDiscountVO){
		con = getConnect();
		PreparedStatement pstmt = null;
		
		// goodsDiscountRecordNumber varchar2(15),  물품할인이력번호
		// goodsCode varchar2(20),    물품번호
		// goodsDiscountCode varchar2(20),   물품할인코드(이름)
		// goodsDiscountContent varchar2(50),   물품할인내용
		// goodsDiscountPrice int,    물품할인가격
		// goodsDiscountStartDate date,  물품할인시작일자
		// goodsDiscountEndDate date,   물품할인종료일자 
		// goodsDiscountRegisterDate date default sysdate,  물품할인등록일자
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
				System.out.println("--물품할인이력 입력 완료");
			}else{
				System.out.println("--물품할인이력 입력 실패");
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
		
		//물품할인이력목록출력
	public void listGoodsDiscountRecord(MartClassCodeSVC martClassCodeSVC){
	
			System.out.println("*****물품할인창*****");
			GoodsVO matchgoodsVO = selectedCodeSearchGoods(martClassCodeSVC);
			if(matchgoodsVO!=null){
				dbListGoodsDiscountRecord(matchgoodsVO);
			}//end of if
			else{
				System.out.println("제대로 된 물품코드를 입력해주세요.");
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
				  
				  System.out.println("[ "+goodsDiscountRecordNumber+"]"+ "물품코드 : "+goodsCode
						  + "할인이름 : "+goodsDiscountCode); 
				  System.out.println("할인가격 : "+goodsDiscountPrice);
				  System.out.println("할인시작날짜 : "+goodsDiscountStartDate);
				  System.out.println("할인종료날짜 : "+goodsDiscountEndDate);
				  System.out.println("비고 : "+goodsDiscountContent);
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
		
		
		
	/////////////////대분류코드 -> 소분류코드 -> 물품찾기
	public GoodsVO selectedCodeSearchGoods(MartClassCodeSVC martClassCodeSVC){
		GoodsVO matchgoodsVO = null;
		//대분류 코드 선택
		martClassCodeSVC.dbListClassCode();
		String selectedClassCode = sc.next();
		//소분류 코드 선택 - 대분류 선택시 그 하위 테이블 목록들
		martClassCodeSVC.dbMatchSubclassCode(selectedClassCode);
		String selectedSubclassCode = sc.next();
		
		//물품 Code 지정
		int i =listGoodsVOCode(selectedSubclassCode);
		if(i==1){
			System.out.print("GoodsCode : ");
			String goodsCode = sc.next();
			
			//해당 Code가 있나 중복검사
			matchgoodsVO = matchGoodsVOCode(goodsCode,selectedSubclassCode);
		}
		else {
			System.out.println("물품이 없습니다.");
		}
		return matchgoodsVO;
	}//end of selectedCodeSearchGoods(MartClassCodeSVC martClassCodeSVC)
}
