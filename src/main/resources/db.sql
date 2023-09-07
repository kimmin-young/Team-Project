create table users (
	u_id varchar2(50 char) primary key,
	u_pw varchar2(20 char),
	u_email varchar2(40 char) not null,
	u_nickname varchar2(30 char) not null,
	u_name varchar2(20 char) not null,
	u_phone varchar2(20 char) not null,
	u_photo varchar2(200 char),
	u_addr varchar2(100 char),
	u_role char(1 char) not null
);

select * from USERS;
--DELETE FROM users WHERE u_id = 'qwe';
--UPDATE users SET u_email='dydtj1903@gmail.com' WHERE u_id = 'asd';
--UPDATE users SET u_id='example' WHERE u_nickname ='asdf';
--UPDATE users SET u_nickname ='asdf' WHERE u_id='example';

--drop table users cascade constraints purge;

-- ** 계정에 관리자 권한을 부여하는 sql문 ** --
update users set u_role = 'm' where u_id = 'example';

create table i_recipe(
    r_no number(6) primary key,
    
    r_title varchar2(30 char) not null,
    r_category varchar2(10 char) not null,
    r_date date not null,
    r_text varchar2(1000 char) not null,
    r_photo varchar2(200) null,
    
    r_seen number(7) default 0 not null,
    r_ended varchar2(1 char) default 'X' not null,
    
    r_writer varchar2(20) not null,
    constraint fk_r_writer foreign key(r_writer)
    references users (u_id) on delete set null,
    
    r_star_avg number(2,1) default 0.0 not null
);

create sequence i_recipe_seq;
--select * from i_recipe;

--drop sequence i_recipe_seq;
--drop table i_recipe cascade constraints purge;

--** 테이블을 삭제하지 않고 싶으신 경우 **
--** 하단 sql을 이용해 컬럼을 추가해 주세요 **
--alter table i_recipe add (r_star_avg number(2,1) default 0.0 not null);
--update i_recipe set r_star_avg = 0.0;


-- ** 작성자가 탈퇴해도 레시피는 삭제되지 않게 수정 ** --
alter table i_recipe drop constraint fk_r_writer;
alter table i_recipe add constraint fk_r_writer
foreign key(r_writer) references users (u_id)
on delete set null;
--*


create table review (
    rv_no number(7) primary key,
    rv_star number(2, 1) not null,
    rv_text varchar2(200 char) not null,
    
    rv_rcp number(6) not null,
    constraint fk_rv_rcp foreign key(rv_rcp)
    references i_recipe (r_no) on delete cascade,
    
    rv_writer varchar2(20) not null,
    constraint fk_rv_writer foreign key(rv_writer)
    references users (u_id) on delete set null
);

create sequence review_seq;
--select * from review;

-- ** 레시피를 삭제하면 리뷰도 모두 삭제되게 수정 ** --
--alter table review drop constraint fk_rv_rcp;
--alter table review add constraint fk_rv_rcp
--foreign key(rv_rcp) references i_recipe (r_no)
--on delete cascade;
--*

-- ** 작성자가 탈퇴해도 리뷰는 삭제되지 않게 수정 ** --
alter table review drop constraint fk_rv_writer;
alter table review add constraint fk_rv_writer
foreign key(rv_writer) references users (u_id)
on delete set null;
--*



--drop sequence review_seq;
--drop table review cascade constraints purge;


create table tail(
    t_no number(7) primary key,
    t_date date not null,
    t_text varchar2(1000 char) not null,
    t_photo varchar2(200) null,
    
    t_head number(6) not null,
    constraint fk_t_head foreign key(t_head)
    references i_recipe(r_no) on delete cascade
);

create sequence tail_seq;
--select * from tail;

-- ** 머리를 삭제하면 꼬리도 모두 삭제되게 수정 ** --
--alter table tail drop constraint fk_t_head;
--alter table tail add constraint fk_t_head
--foreign key(t_head) references i_recipe(r_no)
--on delete cascade;
--*



--drop table tail cascade constraints purge;
--drop sequence tail_seq;


create table bookmark(
    b_no number(8) primary key, 
    
    b_user varchar2(20 char) not null,
    constraint fk_b_user foreign key(b_user) 
    references users(u_id) on delete cascade,
    
    b_rcp number(6) not null,
    constraint fk_b_rcp foreign key(b_rcp) 
    references i_recipe(r_no) on delete cascade
);

create sequence bookmark_seq;

alter table bookmark
modify b_user varchar2(50);

-- ** 유저 탈퇴하면 북마크도 삭제되게 변경 ** --
--alter table bookmark add constraint fk_b_user
--foreign key(b_user) references users(u_id)
--on delete cascade;

-- ** 글 삭제하면 북마크도 삭제되게 변경 ** --
--alter table bookmark add constraint fk_b_rcp
--foreign key(b_rcp) references i_recipe(r_no)
--on delete cascade;

--------------------------------------------
-- cs
create table cs (
	cs_num number(10) primary key,
	cs_category varchar2(10 char) not null,
	cs_title varchar2(40 char) not null,
	cs_text varchar2(3000 char) not null,
	cs_writer varchar2(50 char) not null,
	cs_views number(10) not null,
	cs_when date not null,
	constraint cs_writer_fk foreign key (cs_writer) references users(u_id) on delete cascade 
)
create sequence cs_seq

select * from cs
--drop table cs cascade constraint purge
--drop sequence cs_seq


create table csr (
	csr_num number(10) primary key,
	csr_text varchar2(300 char) not null,
	csr_writer varchar2(50 char) not null,
	csr_when date not null,
	csr_cs_num number(10) not null,
	constraint csr_cs_num_fk foreign key (csr_cs_num) references cs(cs_num) on delete cascade
)
create sequence csr_seq

select * from csr
--drop table csr cascade constraint purge
--drop sequence csr_seq

alter table i_recipe modify(r_writer varchar2(50 char));
alter table review modify(rv_writer varchar2(50 char));
alter table bookmark modify(b_user varchar2(50 char));

------------------------------------------------------

--select count(*) from cs
--where cs_title like '%%' and cs_category like '%%' 
--order by cs_num desc

--select count(*) from cs where cs_writer = 'sH6cYxuDXTdWYx9GhkJs4yYArakrvdKYqeP1LKPGS_c'
