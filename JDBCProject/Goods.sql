
create table Goods(
	--//private String goodsNumber;
	--//private String goodsPrice;
 	--//private String goodsDiscountWhether;
	--//private String goodsDeleteWhether;  //물품삭제여부
	--//private String goodsRestrictWhether;  //물품제재여부
	--//private String goodsRegistrationDate;  //물품등록일자
	--//private String goodSubclassCode; //물품 소분류코드(fk)
	--//private String memberId; //회원table 회원ID(fk)
	
	
	goodsNumber varchar2(20),
	goodsPrice int,
	goodsDiscountWhether varchar2(15), --물품 할인 여부  Goods.java에선 boolean
	goodsDeleteWhether varchar2(15),
	goodsRestrictWhether varchar2(15),
	goodsRegistrationDate date,
	goodSubclassCode varchar2(15),
	memberId varchar2(20),
	constraint Goods_pk_goodsNumber primary key(goodsNumber),
	constraint Goods_fk_goodSubclassCode foreign key(goodSubclassCode) references SubclassCode,
	constraint Goods_fk_memberId foreign key(memberId) references Member
	
);