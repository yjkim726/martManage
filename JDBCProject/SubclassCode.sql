

create table SubclassCode(
	goodsSubclassCode varchar2(20),
	goodsSubclassName varchar2(20),
	goodsClassCode varchar2(20),
	
	constraint SubclassCode_pk_goodsSubclassCode primary key(goodsSubclassCode),
	constraint SubclassCode_fk_goodsClassCode foreign key(goodsClassCode) references ClassCode

);