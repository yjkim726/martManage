drop table GoodsModificationHistory;
drop sequence goodsModification;

create table GoodsModificationHistory(
goodsModificationHistoryNumber varchar2(18),
goodsCode varchar2(20),

goodsModificationCode varchar2(18),
goodsModificationContent varchar2(50),
goodsModificationDate date default sysdate,

primary key(goodsModificationHistoryNumber),
foreign key(goodsCode) references Goods(goodsCode)

);

create sequence goodsModification;



select * from GoodsModificationHistory;