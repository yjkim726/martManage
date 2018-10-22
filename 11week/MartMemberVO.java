package JDBCProject;


public class MartMemberVO{
	private String memberId;
	private String memberseparation;  //회원구분
	private String memberPassword;
	private String memberName;
	private String memberPhone;
	private String memberEmail;
	private String termsAgreement; //약관동의
	private String memberAddress;
	private String memberJoinDate;

	// memberJoinDate는 DB로 넣는다
	MartMemberVO(String memberId, String memberseparation, String memberPassword, String memberName, String memberPhone, 
			String memberEmail,  String memberAddress ){
			this.memberId = memberId;
			this.memberseparation = memberseparation;
			this.memberPassword = memberPassword;
			this.memberName = memberName;
			this.memberPhone = memberPhone;
			this.memberEmail = memberEmail;
			this.memberAddress = memberAddress;
		}//end of Member 생성자
	
	MartMemberVO(String memberId, String memberseparation, String memberPassword, String memberName, String memberPhone, 
		String memberEmail,  String memberAddress, String memberJoinDate ){
		this.memberId = memberId;
		this.memberseparation = memberseparation;
		this.memberPassword = memberPassword;
		this.memberName = memberName;
		this.memberPhone = memberPhone;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberJoinDate = memberJoinDate;
	}//end of Member 생성자

	public String getMemberseparation() {
		return memberseparation;
	}


	public void setMemberseparation(String memberseparation) {
		this.memberseparation = memberseparation;
	}
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getTermsAgreement() {
		return termsAgreement;
	}

	public void setTermsAgreement(String termsAgreement) {
		this.termsAgreement = termsAgreement;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberJoinDate() {
		return memberJoinDate;
	}

	public void setMemberJoinDate(String memberJoinDate) {
		this.memberJoinDate = memberJoinDate;
	}
	
	
	
}//end Member Class

