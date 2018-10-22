package JDBCProject;

public class GoodsVO{
	private String goodsCode;
	private String goodsName;
	private int goodsPrice;
	private String goodsDiscountWhether; //��ǰ���ο���
	private String goodsDeleteWhether;  //��ǰ��������
	private String goodsRestrictWhether;  //��ǰ���翩��
	private String goodsRegistrationDate;  //��ǰ�������
	private String goodSubclassCode; //��ǰ �Һз��ڵ�(fk)
	private String memberId; //ȸ��table ȸ��ID(fk)
	
	GoodsVO(String goodsCode, String goodsName, int goodsPrice, String goodsDiscountWhether, String goodsDeleteWhether,
			String goodsRestrictWhether, String goodSubclassCode, String memberId ){
		this.goodsCode = goodsCode;
		this.goodsName = goodsName;
		this.goodsPrice = goodsPrice;
		this.goodsDiscountWhether = goodsDiscountWhether;
		this.goodsDeleteWhether = goodsDeleteWhether;
		this.goodsRestrictWhether =goodsRestrictWhether;
		this.goodSubclassCode = goodSubclassCode;
		this.memberId =memberId;
	}//������
	
	GoodsVO(String goodsCode, String goodsName, int goodsPrice, String goodsDiscountWhether, String goodsDeleteWhether,
			String goodsRestrictWhether, String goodsRegistrationDate, String goodSubclassCode, String memberId ){
		this.goodsCode = goodsCode;
		this.goodsName = goodsName;
		this.goodsPrice = goodsPrice;
		this.goodsDiscountWhether = goodsDiscountWhether;
		this.goodsDeleteWhether = goodsDeleteWhether;
		this.goodsRestrictWhether =goodsRestrictWhether;
		this.goodsRegistrationDate = goodsRegistrationDate;
		this.goodSubclassCode = goodSubclassCode;
		this.memberId =memberId;
	}//��¥���
	
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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