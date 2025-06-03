DROP DATABASE IF EXISTS test;
create schema test;
	use test;
	create table users(
		id int primary key auto_increment ,
		tk char(40),
		mk char(40),
		email char(50)
	);
    insert into users(tk,mk,email) values 
	('thanhdz','1','thanhit482004@gmail.com')