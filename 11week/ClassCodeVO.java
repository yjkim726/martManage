package JDBCProject;

public class ClassCodeVO {

	//goodsClassCode varchar2(20),
	//goodsClassName varchar2(20),
	
	private String goodsClassCode;
	private String goodsClassName;
	
	ClassCodeVO(String goodsClassCode,String goodsClassName){
		this.goodsClassCode =goodsClassCode;
		this.goodsClassName = goodsClassName;
	}
	
	public String getGoodsClassCode() {
		return goodsClassCode;
	}
	public void setGoodsClassCode(String goodsClassCode) {
		this.goodsClassCode = goodsClassCode;
	}
	public String getGoodsClassName() {
		return goodsClassName;
	}
	public void setGoodsClassName(String goodsClassName) {
		this.goodsClassName = goodsClassName;
	}
	
	
}
