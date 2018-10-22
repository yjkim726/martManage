drop table Goods;
create table Goods(
	--//private String goodsCode;
	--//private String goodsPrice;
 	--//private String goodsDiscountWhether;
	--//private String goodsDeleteWhether;  //��ǰ��������
	--//private String goodsRestrictWhether;  //��ǰ���翩��
	--//private String goodsRegistrationDate;  //��ǰ�������
	--//private String goodSubclassCode; //��ǰ �Һз��ڵ�(fk)
	--//private String memberId; //ȸ��table ȸ��ID(fk)
	
	
	goodsCode varchar2(20),
	goodsName varchar2(50),
	goodsPrice int,
	goodsDiscountWhether varchar2(15), --��ǰ ���� ����  Goods.java���� boolean
	goodsDeleteWhether varchar2(15), --��ǰ��������
	goodsRestrictWhether varchar2(15), --��ǰ���翩��
	goodsRegistrationDate date default sysdate, --��ǰ�������
	goodsSubclassCode varchar2(20),
	memberId varchar2(20),
	primary key(goodsCode),
	foreign key(goodsSubclassCode) references SubclassCode(goodsSubclassCode),
	foreign key(memberId) references Member(memberId)
);