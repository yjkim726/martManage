drop table Keyword;

create table Keyword(
keywordCode varchar2(20),
keywordName varchar2(20),
keywordRegistrationDate date default sysdate,
goodsSubclassCode varchar2(20),
primary key(keywordCode),
foreign key(goodsSubclassCode) references SubclassCode(goodsSubclassCode)
);