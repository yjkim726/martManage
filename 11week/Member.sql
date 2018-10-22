
drop table Member;

create table Member(
	--private String memberId;
	--private String memberseparation;
	--private String memberPassword;
	--private String memberName;
	--private String memberPhone;
	--private String memberEmail;
	--private String termsAgreement; //약관동의
	--private String memberAddress;
	--private String memberJoinDate;
memberId varchar2(20),
memberseparation varchar2(15),
memberPassword varchar2(20),                                                               
memberName varchar2(15),
memberPhone varchar2(15),
memberEmail varchar2(15),
termsAgreement varchar2(10),	
memberAddress varchar2(30),
memberJoinDate date default sysdate,
primary key(memberId)
);