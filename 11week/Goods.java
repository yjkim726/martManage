drop table Goods;
create table Goods(
	--//private String goodsCode;
	--//private String goodsPrice;
 	--//private String goodsDiscountWhether;
	--//private String goodsDeleteWhether;  //물품삭제여부
	--//private String goodsRestrictWhether;  //물품제재여부
	--//private String goodsRegistrationDate;  //물품등록일자
	--//private String goodSubclassCode; //물품 소분류코드(fk)
	--//private String memberId; //회원table 회원ID(fk)
	
	
	goodsCode varchar2(20),
	goodsName varchar2(50),
	goodsPrice int,
	goodsDiscountWhether varchar2(15), --물품 할인 여부  Goods.java에선 boolean
	goodsDeleteWhether varchar2(15), --물품삭제여부
	goodsRestrictWhether varchar2(15), --물품제재여부
	goodsRegistrationDate date default sysdate, --물품등록일자
	goodsSubclassCode varchar2(20),
	memberId varchar2(20),
	primary key(goodsCode),
	foreign key(goodsSubclassCode) references SubclassCode(goodsSubclassCode),
	foreign key(memberId) references Member(memberId)
);