drop table GoodsDiscountRecord;

create table GoodsDiscountRecord(

 goodsDiscountRecordNumber varchar2(15),
 goodsCode varchar2(20),
 goodsDiscountCode varchar2(20),
 goodsDiscountContent varchar2(50),
 goodsDiscountPrice int,
 goodsDiscountStartDate varchar2(20),
 goodsDiscountEndDate varchar2(20),
 goodsDiscountRegisterDate date default sysdate,
 
 primary key(goodsDiscountRecordNumber,goodsCode),
 foreign key(goodsCode) references Goods(goodsCode)
);

create sequence goodsDiscountN;
drop sequence goodsDiscountN;