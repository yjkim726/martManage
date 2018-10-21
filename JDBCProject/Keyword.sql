
create table Keyword(
	keywordCode varchar2(20),
	keywordName varchar2(20),
	keywordRegistrationDate date,
	goodsSubclassCode varchar2(20),
	
	constraint Keyword_pk_keywordCode  primary key(keywordCode),
	constraint Keyword_fk_goodSubclassCode foreign key(goodSubclassCode) references SubclassCode

);