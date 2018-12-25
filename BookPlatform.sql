-- 前11行是删库和建库的命令，首次建表使用
DROP DATABASE IF EXISTS BookPlatform;
CREATE DATABASE BookPlatform;

USE BookPlatform;
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Rent;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS Collection;


-- 用户表
CREATE TABLE User (
  userName char(20) NOT NULL,
  password char(20) NOT NULL,
  tel char(30) NOT NULL,
  status tinyint  NOT NULL DEFAULT '0', Constraint ck_User_status Check(status in (0, 1, 2)), -- 0普通用户 1管理员 2图书逾期用户
  id int NOT NULL AUTO_INCREMENT, Constraint pk_User_id PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 图书表
CREATE TABLE Book (
  name char(30) NOT NULL,
  type char(30) NOT NULL,
  author char(30) NOT NULL,
  intro varchar(100) NOT NULL,
  imgUrl varchar(100) NOT NULL Default "NULL", -- "NULL" 表示无图片
  status tinyint  NOT NULL DEFAULT '0', Constraint ck_Book_status Check(status in (0, 1, 2, 3)), -- 0在架 1待审核 2已预约 3已借 4下架
  userId int NOT NULL, Constraint fk_User_id_Book_userId FOREIGN KEY (userId) REFERENCES User(id) on delete cascade on update cascade,
  id int NOT NULL AUTO_INCREMENT,   Constraint pk_Book_id  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 


-- 租书表
CREATE TABLE Rent (
	id int NOT NULL AUTO_INCREMENT, Constraint pk_Rent_id PRIMARY KEY (id),
    beginData DateTime NOT NULL Default now(), -- 固定借出时间30天
    bookId int NOT NULL, Constraint fk_Book_id_Rent_bookId FOREIGN KEY (bookId) REFERENCES Book(id) on delete cascade on update cascade,
    userId int NOT NULL, Constraint fk_User_id_Rent_userId FOREIGN KEY (userId) REFERENCES User(id) on delete cascade on update cascade,
	status tinyint not null default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Reserve (
	id int NOT NULL AUTO_INCREMENT, Constraint pk_Rerseve_id PRIMARY KEY (id),
    beginData DateTime NOT NULL Default now(), -- 固定借出时间30天
    bookId int NOT NULL, Constraint fk_Book_id_Rerseve_bookId FOREIGN KEY (bookId) REFERENCES Book(id) on delete cascade on update cascade,
    userId int NOT NULL, Constraint fk_User_id_Rerseve_userId FOREIGN KEY (userId) REFERENCES User(id) on delete cascade on update cascade,
	status tinyint not null default 0  -- 0为正常，1为失效
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- 评论表
CREATE TABLE Comment (
	content varchar(200) NOT NULL,
    createTime DateTime Not NULL Default now(),
	userId int NOT NULL, Constraint fk_User_id_Comment_userId FOREIGN KEY (userId) REFERENCES User(id) on delete cascade on update cascade,
	bookId int NOT NULL, Constraint fk_Book_id_Comment_bookId FOREIGN KEY (bookId) REFERENCES Book(id) on delete cascade on update cascade,
	id int Not NULL AUTO_INCREMENT, Constraint pk_Comment_id PRIMARY KEY (id),
	status tinyint not null default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 收藏表
CREATE TABLE Collection (
	collTime DateTime Not NULL Default now(),
	userId int NOT NULL, Constraint fk_User_id_Collection_userId FOREIGN KEY (userId) REFERENCES User(id) on delete cascade on update cascade,
	bookId int NOT NULL, Constraint fk_Book_id_Collection_bookId FOREIGN KEY (bookId) REFERENCES Book(id) on delete cascade on update cascade,
	id int Not NULL AUTO_INCREMENT, Constraint pk_Collection_id PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

