package JDBCProject;

public class GoodsVO{
	private String goodsNumber;
	private int goodsPrice;
	private String goodsDiscountWhether;
	private String goodsDeleteWhether;  //물품삭제여부
	private String goodsRestrictWhether;  //물품제재여부
	private String goodsRegistrationDate;  //물품등록일자
	private String goodSubclassCode; //물품 소분류코드(fk)
	private String memberId; //회원table 회원ID(fk)
	
	
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsDiscountWhether() {
		return goodsDiscountWhether;
	}
	public void setGoodsDiscountWhether(String goodsDiscountWhether) {
		this.goodsDiscountWhether = goodsDiscountWhether;
	}
	
	public String getGoodsDeleteWhether() {
		return goodsDeleteWhether;
	}
	public void setGoodsDeleteWhether(String goodsDeleteWhether) {
		this.goodsDeleteWhether = goodsDeleteWhether;
	}
	public String getGoodsRestrictWhether() {
		return goodsRestrictWhether;
	}
	public void setGoodsRestrictWhether(String goodsRestrictWhether) {
		this.goodsRestrictWhether = goodsRestrictWhether;
	}
	public String getGoodsRegistrationDate() {
		return goodsRegistrationDate;
	}
	public void setGoodsRegistrationDate(String goodsRegistrationDate) {
		this.goodsRegistrationDate = goodsRegistrationDate;
	}
	public String getGoodSubclassCode() {
		return goodSubclassCode;
	}
	public void setGoodSubclassCode(String goodSubclassCode) {
		this.goodSubclassCode = goodSubclassCode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}//end Member Class