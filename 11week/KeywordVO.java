package JDBCProject;

public class KeywordVO {
	//keywordCode varchar2(20),
	//keywordName varchar2(20),
	//keywordRegistrationDate date,
	//goodsSubclassCode varchar2(20),
	
	private String keywordCode;
	private String keywordName;
	private String keywordRegistrationDate;
	private String goodsSubclassCode;
	
	public KeywordVO(String keywordCode, String keywordName,/*String keywordRegistrationDate */ String goodsSubclassCode ){
		this.keywordCode = keywordCode;
		this.keywordName = keywordName;
		this.goodsSubclassCode = goodsSubclassCode;
	}//end of »ý¼ºÀÚ
	
	public String getKeywordCode() {
		return keywordCode;
	}
	public void setKeywordCode(String keywordCode) {
		this.keywordCode = keywordCode;
	}
	public String getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	public String getKeywordRegistrationDate() {
		return keywordRegistrationDate;
	}
	public void setKeywordRegistrationDate(String keywordRegistrationDate) {
		this.keywordRegistrationDate = keywordRegistrationDate;
	}
	public String getGoodsSubclassCode() {
		return goodsSubclassCode;
	}
	public void setGoodsSubclassCode(String goodsSubclassCode) {
		this.goodsSubclassCode = goodsSubclassCode;
	}
	

}//end of KeywordVO
