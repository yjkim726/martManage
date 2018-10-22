drop table KeywordRequest;

create table KeywordRequest(
serialNumber varchar2(18),
requestKeyword varchar2(18),
memberId varchar2(20),
requestDate date default sysdate,
primary key(serialNumber),
foreign key(memberId) references Member(memberId)
);