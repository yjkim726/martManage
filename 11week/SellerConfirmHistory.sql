drop table SellerConfirmHistory;
create table SellerConfirmHistory(

SellerHistoryNumber varchar2(18),
memberId varchar2(20),
SellerConfirmHistoryContent varchar2(25),
SellerConfirmHistoryDate date default sysdate,

primary key(SellerHistoryNumber),
foreign key(memberId) references SellerConfirmation(memberId)

);