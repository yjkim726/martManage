drop table SubclassCode;

create table SubclassCode(
	goodsSubclassCode varchar2(20),
	goodsSubclassName varchar2(20),
	goodsClassCode varchar2(20),
	primary key(goodsSubclassCode),
	foreign key(goodsClassCode) references ClassCode(goodsClassCode)
);