drop table GoodsRestrictRecord;
drop sequence goodsRestrictRecordN;

create table GoodsRestrictRecord(

 goodsRestrictRecordNumber varchar2(15),
 goodsCode varchar2(20),
 goodsRestrictCode varchar2(20),
 goodsRestrictContent varchar2(50),
 goodsRestrictDate date default sysdate,
 
 primary key(goodsRestrictRecordNumber,goodsCode),
 foreign key(goodsCode) references Goods(goodsCode)
);

create sequence goodsRestrictRecordN;
