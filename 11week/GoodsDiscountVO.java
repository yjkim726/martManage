package JDBCProject;


public class GoodsDiscountVO {

	private String goodsDiscountCode;
	private String goodsDiscountContent;
	private int goodsDiscountPrice;
	private String goodsDiscountStartDate;
	private String goodsDiscountEndDate;
	
	GoodsDiscountVO(String goodsDiscountCode,String goodsDiscountContent, int goodsDiscountPrice,
			String goodsDiscountStartDate,String goodsDiscountEndDate){
		this.goodsDiscountCode = goodsDiscountCode;
		this.goodsDiscountContent = goodsDiscountContent;
		this.goodsDiscountPrice= goodsDiscountPrice;
		this.goodsDiscountStartDate=goodsDiscountStartDate;
		this.goodsDiscountEndDate= goodsDiscountEndDate;
	}//end of GoodsDiscountVO

	public String getGoodsDiscountCode() {
		return goodsDiscountCode;
	}

	public void setGoodsDiscountCode(String goodsDiscountCode) {
		this.goodsDiscountCode = goodsDiscountCode;
	}

	public String getGoodsDiscountContent() {
		return goodsDiscountContent;
	}

	public void setGoodsDiscountContent(String goodsDiscountContent) {
		this.goodsDiscountContent = goodsDiscountContent;
	}

	public String getGoodsDiscountStartDate() {
		return goodsDiscountStartDate;
	}

	
	public int getGoodsDiscountPrice() {
		return goodsDiscountPrice;
	}

	public void setGoodsDiscountPrice(int goodsDiscountPrice) {
		this.goodsDiscountPrice = goodsDiscountPrice;
	}

	public void setGoodsDiscountStartDate(String goodsDiscountStartDate) {
		this.goodsDiscountStartDate = goodsDiscountStartDate;
	}

	public String getGoodsDiscountEndDate() {
		return goodsDiscountEndDate;
	}

	public void setGoodsDiscountEndDate(String goodsDiscountEndDate) {
		this.goodsDiscountEndDate = goodsDiscountEndDate;
	}
	
}//end of GoodsDiscountVO
