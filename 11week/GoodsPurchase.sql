drop table GoodsPurchase;
drop sequence goodsPurchaseN;


create table GoodsPurchase(
  goodsPurchaseNumber varchar2(20),
  goodsCode varchar2(20),
  memberId varchar2(20),
  goodsPurchasePrice int,
  goodsPurchaseWay varchar2(30),
  goodsReceiver varchar2(20),
  goodsAddress varchar2(30),
  goodsDeliveryFinishWhether varchar2(15),  --물품배송완료여부
  goodsRefundWhether varchar2(15),
  goodsPurchaseDate date default sysdate,
  
  primary key(goodsPurchaseNumber),
  foreign key(goodsCode) references Goods(goodsCode),
  foreign key(memberId) references Member(memberId)
);

create sequence goodsPurchaseN;
