drop table SellerConfirmation;
create table SellerConfirmation(

 memberId varchar2(20),
 sellerState varchar2(20),  --허가될 경우 permission 이라고 바뀜  허가전은 nonPermission
 sellerCompanyName varchar2(20),
 sellerOperatorNumber number(20),
 sellerEarningsRate varchar2(10),
 sellerPhone varchar2(15),
 sellerCertificationDate date default sysdate,
 
 primary key(memberId),
 foreign key(memberId) references Member(memberId)

);