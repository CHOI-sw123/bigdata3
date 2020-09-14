CREATE TABLE tblMem(
	num number primary key,
	name varchar2(20) not null,
	phone varchar2(20) not null,
	addr varchar2(50),
	lat number(16, 12),
	lng number(16, 12)
);

CREATE sequence seq_num;

INSERT INTO tblMem 
VALUES(seq_num.NEXTVAL, '나길동', '010-1111-1111', '광주광역시 남구 진월동', 35.125769984, 126.852047602507);

SELECT * FROM tblMem;

DELETE FROM tblMem WHERE num=2;