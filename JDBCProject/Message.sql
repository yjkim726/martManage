create table Message(

 messageNumber number,
 sender varchar2(15),
 receiver varchar2(15),
 messageTitle varchar2(20),
 messageContent varchar2(100)
 primary key (messageNumber),
 foreign key(receiver) references SellerConfirmation()
);