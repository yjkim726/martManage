package JDBCProject;

public class SubclassCodeVO {
	//goodsSubclassCode varchar2(20),
	//goodsSubclassName varchar2(20),
	//goodsClassCode varchar2(20),
	
	private String goodsSubclassCode;
	private String goodsSubclassName;
	private String goodsClassCode;
	
	
	SubclassCodeVO(String goodsSubclassCode, String goodsSubclassName, String goodsClassCode ){
		this.goodsSubclassCode = goodsSubclassCode;
		this.goodsSubclassName = goodsSubclassName;
		this.goodsClassCode = goodsClassCode;
	}
	
	public String getGoodsSubclassCode() {
		return goodsSubclassCode;
	}
	public void setGoodsSubclassCode(String goodsSubclassCode) {
		this.goodsSubclassCode = goodsSubclassCode;
	}
	public String getGoodsSubclassName() {
		return goodsSubclassName;
	}
	public void setGoodsSubclassName(String goodsSubclassName) {
		this.goodsSubclassName = goodsSubclassName;
	}
	public String getGoodsClassCode() {
		return goodsClassCode;
	}
	public void setGoodsClassCode(String goodsClassCode) {
		this.goodsClassCode = goodsClassCode;
	}
	
	
	
	
}
