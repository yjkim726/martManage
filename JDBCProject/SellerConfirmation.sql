create table SellerConfirmation(

 memberId varchar2(20),
 sellerState varchar2(9),
 sellerOperatorNumber number(20),
 sellerEarningsRate varchar2(10),
 sellerPhone varchar2(10),
 sellerCertificationDate varchar2(15),
 
 primary key(memberId),
 foreign key(memberId) references Member(memberId)

);