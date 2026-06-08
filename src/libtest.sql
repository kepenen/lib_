CREATE TABLE `books` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `title` varchar(30) NOT NULL COMMENT '书名',
  `isbn` varchar(30) DEFAULT NULL COMMENT 'ISBN',
  `author` varchar(30) DEFAULT NULL COMMENT '作者',
  `num` int unsigned NOT NULL COMMENT '数量',
  `genre_id` int unsigned DEFAULT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='书表';

INSERT INTO `books` VALUES (5,'深入理解计算机系统','3414','大卫',5,3),(7,'三体I','123-34','刘慈欣',999,2),(8,'计算机系统','无','87-09',9,1),(9,'么么哒','877-345','无',8,2),(10,'bojack horsman','2342-344','bojack',9,1),(11,'coffee','23124','hu',555,2);


CREATE TABLE `borrowers` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `reader_id` int unsigned DEFAULT NULL COMMENT '读者ID',
  `book_id` int unsigned DEFAULT NULL COMMENT '书籍ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='借书信息';


CREATE TABLE `genres` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `genre` varchar(25) NOT NULL COMMENT '类别',
  PRIMARY KEY (`id`),
  UNIQUE KEY `genre` (`genre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='类别表';

INSERT INTO `genres` VALUES (2,'computer'),(1,'art'),(3,'math'), (4, 'science fiction');


CREATE TABLE `readers` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `username` varchar(25) NOT NULL COMMENT '用户名',
  `password` varchar(25) NOT NULL COMMENT '密码',
  `name` varchar(25) NOT NULL COMMENT '名字',
  `stu_id` varchar(25) DEFAULT NULL COMMENT '学号',
  `stu_class` varchar(25) DEFAULT NULL COMMENT '班级',
  `phone` varchar(25) DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='读者信息';
INSERT INTO `readers` VALUES (1,'abc','123','druhu','213234','cs','110');


CREATE TABLE `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `name` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(30) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `password` (`password`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
INSERT INTO `users` VALUES (1,'kepen','abcd'),(2,'liu','1234');
