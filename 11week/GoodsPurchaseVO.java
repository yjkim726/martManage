package JDBCProject;

import java.sql.Date;

public class GoodsPurchaseVO {
	 // goodsPurchaseNumber varchar2(20),
	 // goodsCode varchar2(20),
	 // memberId varchar2(20),
	 // goodsPurchasePrice int,
	 // goodsPurchaseWay varchar2(15),
	 // goodsReceiver varchar2(20),
	 // goodsAddress varchar2(30),
	 // goodsDeliveryFinishWhether varchar2(15),  --물품배송완료여부
	 // goodsRefundWhether varchar2(15),
	//  goodsPurchaseDate date,
	
	private String goodsCode;
	private String memberId;
	private int goodsPurchasePrice;
	private String goodsPurchaseWay;
	private String goodsReceiver;
	private String goodsAddress;
	private String goodsDeliveryFinishWhether;
	private String goodsRefundWhether;

	
	GoodsPurchaseVO(String goodsCode,String memberId,int goodsPurchasePrice,
			String goodsPurchaseWay, String goodsReceiver,String goodsAddress,
			String goodsDeliveryFinishWhether,String goodsRefundWhether){
		this.goodsCode =goodsCode;
		this.memberId = memberId;
		this.goodsPurchasePrice = goodsPurchasePrice;
		this.goodsPurchaseWay = goodsPurchaseWay;
		this.goodsReceiver = goodsReceiver;
		this.goodsAddress = goodsAddress;
		this.goodsDeliveryFinishWhether = goodsDeliveryFinishWhether;
		this.goodsRefundWhether = goodsRefundWhether;
	}
	
	

	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getGoodsPurchasePrice() {
		return goodsPurchasePrice;
	}
	public void setGoodsPurchasePrice(int goodsPurchasePrice) {
		this.goodsPurchasePrice = goodsPurchasePrice;
	}
	public String getGoodsPurchaseWay() {
		return goodsPurchaseWay;
	}
	public void setGoodsPurchaseWay(String goodsPurchaseWay) {
		this.goodsPurchaseWay = goodsPurchaseWay;
	}
	public String getGoodsReceiver() {
		return goodsReceiver;
	}
	public void setGoodsReceiver(String goodsReceiver) {
		this.goodsReceiver = goodsReceiver;
	}
	public String getGoodsAddress() {
		return goodsAddress;
	}
	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}
	public String getGoodsDeliveryFinishWhether() {
		return goodsDeliveryFinishWhether;
	}
	public void setGoodsDeliveryFinishWhether(String goodsDeliveryFinishWhether) {
		this.goodsDeliveryFinishWhether = goodsDeliveryFinishWhether;
	}
	public String getGoodsRefundWhether() {
		return goodsRefundWhether;
	}
	public void setGoodsRefundWhether(String goodsRefundWhether) {
		this.goodsRefundWhether = goodsRefundWhether;
	}

	
}//end of GoodsPurchaseVO
