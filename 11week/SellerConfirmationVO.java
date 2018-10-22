package JDBCProject;

public class SellerConfirmationVO {
	
	 //memberId varchar2(20),
	// sellerState varchar2(9),  --허가될 경우 permission 이라고 바뀜  허가전은 nonPermission
	// sellerCompanyName varchar2(20),
	// sellerOperatorNumber number(20),
	// sellerEarningsRate varchar2(10),
	// sellerPhone varchar2(15),
	// sellerCertificationDate varchar2(15),
	
	private String memberId;
	private String sellerState;
	private String sellerCompanyName;
	private String sellerOperatorNumber;
	private String sellerEarningsRate;
	private String sellerPhone;
	private String sellerCertificationDate;
	
	SellerConfirmationVO(String memberId, String sellerState, String sellerCompanyName,String sellerOperatorNumber,
			String sellerEarningsRate,String sellerPhone ){
		this.memberId=memberId;
		this.sellerState=sellerState;
		this.sellerCompanyName =sellerCompanyName;
		this.sellerOperatorNumber= sellerOperatorNumber;
		this.sellerEarningsRate=sellerEarningsRate;
		this.sellerPhone=sellerPhone;
	}//end of  생성자
	
	SellerConfirmationVO(String memberId, String sellerState, String sellerCompanyName,String sellerOperatorNumber,
			String sellerEarningsRate,String sellerPhone,String sellerCertificationDate){
		this.memberId=memberId;
		this.sellerState=sellerState;
		this.sellerCompanyName =sellerCompanyName;
		this.sellerOperatorNumber= sellerOperatorNumber;
		this.sellerEarningsRate=sellerEarningsRate;
		this.sellerPhone=sellerPhone;
		this.sellerCertificationDate=sellerCertificationDate;
	}//end of 완벽 생성자
	
	public String getMemberId() {
		return memberId;
	}//end of getMemberId
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}//end of setMemberId
	
	public String getSellerState() {
		return sellerState;
	}//end of getSellerState
	
	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}//end of setSellerState
	
	public String getSellerCompanyName() {
		return sellerCompanyName;
	}//end of getSellerCompanyName
	
	public void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}//end of setSellerCompanyName
	
	public String getSellerOperatorNumber() {
		return sellerOperatorNumber;
	}//end of getSellerOperatorNumber
	
	public void setSellerOperatorNumber(String sellerOperatorNumber) {
		this.sellerOperatorNumber = sellerOperatorNumber;
	}//end of setSellerOperatorNumber
	
	public String getSellerEarningsRate() {
		return sellerEarningsRate;
	}//end of getSellerEarningsRate
	
	public void setSellerEarningsRate(String sellerEarningsRate) {
		this.sellerEarningsRate = sellerEarningsRate;
	}//end of setSellerEarningsRate
	
	public String getSellerPhone() {
		return sellerPhone;
	}//end of getSellerPhone
	
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}//end of setSellerPhone
	
	public String getSellerCertificationDate() {
		return sellerCertificationDate;
	}//end of getSellerCertificationDate
	
	public void setSellerCertificationDate(String sellerCertificationDate) {
		this.sellerCertificationDate = sellerCertificationDate;
	}//end of setSellerCertificationDate
}//end of SellerConfirmationVO
