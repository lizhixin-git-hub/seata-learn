-- hmily使用数据库
create database hmily character set 'utf8mb4' collate 'utf8mb4_general_ci';

-- bank1、bank2数据库与表
create table account_info
(
     id bigint(20) not null auto_increment,
		 account_name varchar(100) default null comment '户主姓名',
		 account_no varchar(100) default null comment '银行卡号',
		 account_password varchar(100) default null comment '账号密码',
		 account_balance double default null comment '账户余额',
		 primary key (id) using btree
) engine=INNODB;

insert into account_info values(3,'李四的账户','2',null,0);

insert into account_info values(2,'张三的账户','1',null,1000);

-- 控制三阶段提交幂等日志表
create table local_try_log
(
    tx_no varchar(64) not null comment '事务id',
		create_time datetime default null,
		primary key (tx_no)
) engine=INNODB;

create table local_confirm_log
(
    tx_no varchar(64) not null comment '事务id',
		create_time datetime default null,
		primary key (tx_no)
);

create table local_cancel_log
(
    tx_no varchar(64) not null comment '事务id',
		create_time datetime default null,
		primary key (tx_no)
);