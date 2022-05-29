/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.29 : Database - exam
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`exam` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `exam`;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `admin_id` int NOT NULL COMMENT '管理员id',
  `admin_username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `admin_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE KEY `admin_username` (`admin_username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `administrator` */

insert  into `administrator`(`admin_id`,`admin_username`,`admin_password`,`admin_name`,`sex`,`age`,`phone`,`email`) values 
(1001,'admin','123456','李狗蛋','男',14,'15616879633','755192560@qq.com');

/*Table structure for table `answersheet` */

DROP TABLE IF EXISTS `answersheet`;

CREATE TABLE `answersheet` (
  `as_id` int NOT NULL AUTO_INCREMENT COMMENT '答题卡id',
  `test_id` int NOT NULL COMMENT '试题id',
  `stu_answer` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '学生答案',
  `stu_score` int DEFAULT NULL COMMENT '考生得分',
  `stu_id` int DEFAULT NULL COMMENT '考生id',
  `tp_id` int DEFAULT NULL COMMENT '试卷id',
  `is_ask` int DEFAULT NULL COMMENT '是否为问答题，1是，0否',
  PRIMARY KEY (`as_id`,`test_id`) USING BTREE,
  KEY `stu_id` (`stu_id`) USING BTREE,
  KEY `tp_id` (`tp_id`) USING BTREE,
  KEY `as_id` (`as_id`) USING BTREE,
  CONSTRAINT `answersheet_ibfk_1` FOREIGN KEY (`tp_id`) REFERENCES `testpaper` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `astu_id` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `answersheet` */

insert  into `answersheet`(`as_id`,`test_id`,`stu_answer`,`stu_score`,`stu_id`,`tp_id`,`is_ask`) values 
(116,10069,'A',0,1001,20210002,0),
(117,10071,'B',0,1001,20210002,0),
(118,10061,'BC',0,1001,20210002,0),
(119,10070,'A',0,1001,20210002,0),
(120,10013,'D',0,1001,20210008,0),
(121,10076,'C',0,1001,20210010,0),
(122,10001,'A',2,1001,20210010,0),
(123,10011,'B',0,1001,20210011,0),
(124,10011,'A',3,1001,20210011,0),
(125,10076,'A',0,1001,20210013,0),
(126,10001,'B',0,1001,20210013,0),
(127,10076,'A',0,1001,20210014,0),
(128,10001,'B',0,1001,20210014,0),
(129,10076,'ABC',0,1001,20210016,0),
(130,10001,'A',2,1001,20210016,0),
(131,10076,'ABCD',0,1001,20210017,0),
(132,10076,'ABCD',0,1001,20210023,0),
(133,10001,'A',2,1001,20210025,0),
(134,10001,'A',2,1001,20210025,0),
(135,10069,'A',0,1001,20210027,0),
(136,10071,'A',0,1001,20210027,0),
(137,10074,'C',0,1001,20210027,0),
(138,10070,'A',0,1001,20210027,0),
(139,10073,'B',0,1001,20210027,0),
(140,10001,'A',2,1001,20210029,0),
(141,10001,'A',2,1001,20210029,0),
(142,10013,'A',0,1001,20210031,0),
(143,10079,'A',3,1001,20210031,0),
(144,10077,'A',2,1001,20210031,0),
(145,10013,'A',0,1001,20210031,0),
(146,10079,'A',3,1001,20210031,0),
(147,10077,'A',2,1001,20210031,0),
(148,10013,'A',0,1001,20210032,0),
(149,10079,'B',0,1001,20210032,0),
(150,10077,'A',2,1001,20210032,0),
(151,10001,'A',2,1001,20210029,0),
(152,10013,'C',0,1001,20210033,0),
(153,10079,'A',3,1001,20210033,0),
(154,10077,'A',2,1001,20210033,0),
(155,10013,'C',0,1001,20210033,0),
(156,10079,'A',3,1001,20210033,0),
(157,10077,'A',2,1001,20210033,0),
(158,10013,'B',3,1001,20210033,0),
(159,10013,'A',0,1001,20210035,0),
(160,10079,'B',0,1001,20210035,0),
(161,10077,'B',0,1001,20210035,0),
(162,10013,'A',0,1001,20210042,0),
(163,10079,'C',0,1001,20210042,0),
(164,10077,'B',0,1001,20210042,0),
(165,10013,'A',0,1001,20210043,0),
(166,10079,'B',0,1001,20210043,0),
(167,10077,'A',2,1001,20210043,0),
(168,10013,'A',0,1001,20210044,0),
(169,10079,'B',0,1001,20210044,0),
(170,10077,'B',0,1001,20210044,0),
(171,10013,'A',0,1001,20210045,0),
(172,10079,'A',3,1001,20210045,0),
(173,10077,'B',0,1001,20210045,0);

/*Table structure for table `attendance` */

DROP TABLE IF EXISTS `attendance`;

CREATE TABLE `attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subject_id` int DEFAULT NULL,
  `student_id` int DEFAULT NULL,
  `sign_in_time` datetime DEFAULT NULL,
  `state` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `subject_id` (`subject_id`) USING BTREE,
  KEY `student_id` (`student_id`) USING BTREE,
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `attendance` */

insert  into `attendance`(`id`,`subject_id`,`student_id`,`sign_in_time`,`state`) values 
(3,6001,1001,'2022-04-26 07:57:16',1);

/*Table structure for table `college` */

DROP TABLE IF EXISTS `college`;

CREATE TABLE `college` (
  `col_id` int NOT NULL COMMENT '学院id',
  `col_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`col_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `college` */

insert  into `college`(`col_id`,`col_name`) values 
(4001,'计算机科学与软件学院'),
(4002,'美术学院'),
(4003,'政法学院'),
(4004,'音乐学院');

/*Table structure for table `description` */

DROP TABLE IF EXISTS `description`;

CREATE TABLE `description` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `admin_id` int DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `description` */

insert  into `description`(`id`,`content`,`admin_id`,`update_time`) values 
(1,'dadadaa',1001,'2022-04-02 10:39:37');

/*Table structure for table `favorite` */

DROP TABLE IF EXISTS `favorite`;

CREATE TABLE `favorite` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `file_id` int DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `student_id` (`student_id`) USING BTREE,
  KEY `file_id` (`file_id`) USING BTREE,
  KEY `question_id` (`question_id`) USING BTREE,
  CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`file_id`) REFERENCES `learning_file` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favorite_ibfk_3` FOREIGN KEY (`question_id`) REFERENCES `testbank` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `favorite` */

insert  into `favorite`(`id`,`student_id`,`type`,`file_id`,`question_id`) values 
(1,3001,2,1,10071);

/*Table structure for table `learning_file` */

DROP TABLE IF EXISTS `learning_file`;

CREATE TABLE `learning_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subject_id` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `subject_id` (`subject_id`) USING BTREE,
  CONSTRAINT `learning_file_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`sub_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `learning_file` */

insert  into `learning_file`(`id`,`subject_id`,`name`) values 
(1,6001,'1.doc'),
(2,6001,'1650897714410.jpg');

/*Table structure for table `major` */

DROP TABLE IF EXISTS `major`;

CREATE TABLE `major` (
  `major_id` int NOT NULL COMMENT '专业id',
  `col_id` int NOT NULL COMMENT '所属学院id',
  `major_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专业名称',
  PRIMARY KEY (`major_id`) USING BTREE,
  KEY `col_id` (`col_id`) USING BTREE,
  CONSTRAINT `col_id` FOREIGN KEY (`col_id`) REFERENCES `college` (`col_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `major` */

insert  into `major`(`major_id`,`col_id`,`major_name`) values 
(5001,4001,'软件工程'),
(5002,4003,'法学');

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `admin_id` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `admin_id` (`admin_id`) USING BTREE,
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `administrator` (`admin_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `notice` */

insert  into `notice`(`id`,`title`,`content`,`admin_id`,`create_time`) values 
(1,'131','1233333',1001,'2022-03-17 10:23:43'),
(2,'123131qeqa','13133131faafad',1001,'2022-03-17 10:23:43'),
(3,'qweqe','123qeqeq',1001,'2022-03-17 10:23:43'),
(4,'123131dadadqeqe','131313qeqe',1001,'2022-03-17 10:23:43'),
(7,'aaa','123',1001,'2022-04-02 07:20:23'),
(8,'1','1',1001,'2022-04-02 08:19:01'),
(9,'3','4',1001,'2022-04-02 08:19:07'),
(10,'123','1231',1001,'2022-04-02 08:23:41'),
(11,'123123','12311231',1001,'2022-04-02 08:23:49'),
(12,'18',NULL,1001,'2022-04-02 15:54:43');

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stu_id` int NOT NULL,
  `sub_id` int DEFAULT NULL COMMENT '科目id',
  `sub_score` double(10,0) DEFAULT NULL COMMENT '科目成绩',
  `tp_id` int DEFAULT NULL COMMENT '答题卡id',
  `submit_time` datetime DEFAULT NULL COMMENT '交卷时间',
  `state` int DEFAULT NULL COMMENT '成绩状态，0未公开，1公开',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sub_id` (`sub_id`) USING BTREE,
  KEY `as_id` (`tp_id`) USING BTREE,
  CONSTRAINT `as_id` FOREIGN KEY (`tp_id`) REFERENCES `testpaper` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ssub_id` FOREIGN KEY (`sub_id`) REFERENCES `subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `score` */

insert  into `score`(`id`,`stu_id`,`sub_id`,`sub_score`,`tp_id`,`submit_time`,`state`) values 
(2,1001,6005,0,20210002,'2022-04-03 09:11:16',1),
(3,1001,6001,0,20210008,'2022-04-02 23:54:52',1),
(4,1001,6003,2,20210010,'2022-04-03 00:35:23',1),
(5,1001,6003,0,20210013,'2022-04-03 00:58:38',1),
(6,1001,6003,0,20210014,'2022-04-03 01:02:40',1),
(7,1001,6003,2,20210016,'2022-04-09 10:31:45',1),
(8,1001,6001,3,20210033,'2022-04-29 06:56:32',0),
(9,1001,6001,0,20210035,'2022-04-29 06:56:59',0),
(10,1001,6001,0,20210042,'2022-04-29 07:22:52',0),
(11,1001,6001,2,20210043,'2022-04-29 07:31:52',0),
(12,1001,6001,0,20210044,'2022-04-29 07:53:57',0),
(13,1001,6001,3,20210045,'2022-04-29 08:05:27',0);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `stu_id` int NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `stu_username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `stu_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `stu_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `major_id` int DEFAULT NULL COMMENT '专业id',
  `col_id` int DEFAULT NULL COMMENT '学院id',
  `stu_state` int NOT NULL COMMENT '审核状态',
  `age` int DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`stu_id`) USING BTREE,
  UNIQUE KEY `stu_username` (`stu_username`) USING BTREE,
  KEY `major_id` (`major_id`) USING BTREE,
  KEY `col_id` (`col_id`) USING BTREE,
  CONSTRAINT `major_id` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3010 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `student` */

insert  into `student`(`stu_id`,`stu_username`,`stu_password`,`stu_name`,`major_id`,`col_id`,`stu_state`,`age`,`sex`,`phone`,`email`) values 
(1001,'admin','123456','学生12',5001,4001,1,15,'男1','15616879633','755192560@qq.com'),
(3001,'201824113316','123456','李狗蛋',5001,4001,1,18,'男','15616879633','755192560@qq.com'),
(3002,'200001','123456','张三',5002,4003,1,18,'男','15616879633','755192560@qq.com'),
(3003,'3003','123456','李子',5001,4001,0,18,'男','15616879633','755192560@qq.com'),
(3005,'3005','123456','阿狗子',5001,4001,1,18,'男','15616879633','755192560@qq.com'),
(3008,'201824113350','123456','覃阿才',5001,4001,1,18,'男','15616879633','755192560@qq.com'),
(3009,'161002412','123123','甲乙丙',NULL,4001,0,NULL,NULL,NULL,NULL);

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
  `sub_id` int NOT NULL AUTO_INCREMENT COMMENT '科目id',
  `major_id` int NOT NULL COMMENT '专业id',
  `sub_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科目名称',
  `teacher_id` int DEFAULT NULL,
  PRIMARY KEY (`sub_id`,`major_id`) USING BTREE,
  KEY `major_id` (`major_id`) USING BTREE,
  KEY `sub_id` (`sub_id`) USING BTREE,
  KEY `teacher_id` (`teacher_id`) USING BTREE,
  CONSTRAINT `smajor_id` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`tea_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6006 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `subject` */

insert  into `subject`(`sub_id`,`major_id`,`sub_name`,`teacher_id`) values 
(6001,5001,'计网',NULL),
(6002,5001,'操作系统',NULL),
(6003,5001,'javaWeb开发',NULL),
(6004,5001,'软件测试',NULL),
(6005,5001,'数据结构',NULL);

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `tea_id` int NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `tea_username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `tea_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `tea_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `col_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学院名称',
  `tea_state` int NOT NULL COMMENT '审核状态',
  `age` int DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`tea_id`) USING BTREE,
  UNIQUE KEY `tea_username` (`tea_username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2015 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `teacher` */

insert  into `teacher`(`tea_id`,`tea_username`,`tea_password`,`tea_name`,`col_name`,`tea_state`,`age`,`sex`,`phone`,`email`) values 
(1001,'admin','123456','张三','音乐学院',1,14,'男','15616879633','755192560@qq.com'),
(2001,'202021','123456','李小芳','计算机科学与软件学院',1,18,'男','15616879633','755192560@qq.com'),
(2002,'202022','123456','钟惠','计算机科学与软件学院',0,18,'男','15616879633','755192560@qq.com'),
(2008,'202023','123456','李狗蛋','美术学院',0,18,'男','15616879633','755192560@qq.com'),
(2009,'202024','123456','阿狗','音乐学院',1,18,'男','15616879633','755192560@qq.com'),
(2013,'202025','123456','王大锤','政法学院',0,18,'男','15616879633','755192560@qq.com'),
(2014,'123','123','zs','美术学院',0,18,'男','15616879633','755192560@qq.com');

/*Table structure for table `testbank` */

DROP TABLE IF EXISTS `testbank`;

CREATE TABLE `testbank` (
  `test_id` int NOT NULL AUTO_INCREMENT COMMENT '试题id',
  `sub_id` int DEFAULT NULL COMMENT '学科id',
  `test_type` int DEFAULT NULL,
  `test_content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '题目内容',
  `option_a` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `option_b` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `option_c` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `option_d` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '答案',
  `score` int DEFAULT NULL COMMENT '分值',
  `test_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `tea_id` int DEFAULT NULL,
  PRIMARY KEY (`test_id`) USING BTREE,
  KEY `tea_id` (`tea_id`) USING BTREE,
  CONSTRAINT `tea_id` FOREIGN KEY (`tea_id`) REFERENCES `teacher` (`tea_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10089 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `testbank` */

insert  into `testbank`(`test_id`,`sub_id`,`test_type`,`test_content`,`option_a`,`option_b`,`option_c`,`option_d`,`answer`,`score`,`test_date`,`tea_id`) values 
(10001,6003,3,'开汉是崽崽码？','是','否','','','A',2,'2021-05-21 17:40:11',2001),
(10002,6002,2,'开汉是SB码？','是','不然呢','必须的','你以为','ABCD',5,'2021-05-21 17:40:13',2001),
(10011,6004,1,'aaaaaaa','aaaaaaa','aaaa','aaaaa','aaaaaaa','A',3,'2021-05-21 17:40:14',2001),
(10013,6001,1,'sdads','sad','asd','asd','sad','B',3,'2021-05-21 17:40:15',2001),
(10061,6005,2,'1+1=','11','10','01','2','BCD',5,'2021-05-27 11:11:38',1001),
(10068,6002,2,'111','11','11','11','11','BC',5,'2021-05-28 17:25:08',1001),
(10069,6005,1,'11','11','11','11','11','B',3,'2021-05-28 21:56:32',1001),
(10070,6005,3,'1+1=1','对','错',NULL,NULL,'B',2,'2021-05-30 11:14:19',1001),
(10071,6005,1,'2+2=？','1','2','3','4','D',3,'2021-05-30 11:15:12',1001),
(10072,6005,2,'2+2=？','22','2','4','42','AC',5,'2021-05-30 11:17:18',1001),
(10073,6005,3,'2+2=4','对','错',NULL,NULL,'A',2,'2021-05-30 11:17:53',1001),
(10074,6005,1,'3+3=？','6','33','43','3','A',3,'2021-05-30 12:39:46',1001),
(10075,6002,2,'11','11','11','11','111','ABC',5,'2021-05-31 23:19:58',1001),
(10076,6003,2,'123456','11','11','11','11','BCD',5,'2021-05-31 23:20:59',1001),
(10077,6001,3,'1','1','1','1','1','A',2,'2022-04-04 11:29:09',1001),
(10079,6001,1,'1','123','2','123','3','A',3,'2022-04-09 16:38:29',1001),
(10088,6003,4,'1',NULL,NULL,NULL,'','1',10,'2022-04-09 20:28:10',1001);

/*Table structure for table `testpaper` */

DROP TABLE IF EXISTS `testpaper`;

CREATE TABLE `testpaper` (
  `tp_id` int NOT NULL AUTO_INCREMENT COMMENT '试卷id',
  `tp_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '试卷名称',
  `tp_type` int NOT NULL COMMENT '试卷类型',
  `exam_time` datetime DEFAULT NULL COMMENT '考试开始时间',
  `exam_length` time DEFAULT NULL COMMENT '考试时长',
  `exam_password` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '入考码',
  `exam_state` int DEFAULT NULL COMMENT '考试状态',
  `sub_id` int DEFAULT NULL COMMENT '科目id',
  `tea_id` int DEFAULT NULL COMMENT '教师id',
  `exam_total` int NOT NULL DEFAULT '0' COMMENT '总分',
  `files` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`tp_id`) USING BTREE,
  KEY `sub_id` (`sub_id`) USING BTREE,
  CONSTRAINT `sub_id` FOREIGN KEY (`sub_id`) REFERENCES `subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=20210047 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `testpaper` */

insert  into `testpaper`(`tp_id`,`tp_name`,`tp_type`,`exam_time`,`exam_length`,`exam_password`,`exam_state`,`sub_id`,`tea_id`,`exam_total`,`files`) values 
(20210001,'数据结构期中测试',1,'2022-04-01 08:11:31','03:00:00','123456',2,6005,2001,23,NULL),
(20210002,'算法分析期末测试',0,'2022-04-01 08:11:22','02:00:00','',2,6005,2001,18,NULL),
(20210008,'数据结构',0,'2022-04-28 23:41:52','00:30:00','',2,6001,1001,8,''),
(20210010,'javaweb考试',1,'2022-04-03 08:43:00','00:45:00','1234',2,6003,1001,7,'1649489633359.docx'),
(20210011,'软件测试',1,'2022-04-03 08:43:46','00:45:00','1234',2,6004,1001,3,NULL),
(20210013,'java考试',0,'2022-04-28 23:42:20','00:30:00','',2,6003,1001,7,NULL),
(20210014,'javaweb考试',1,'2022-04-09 18:28:49','00:30:00','1234',2,6001,1001,7,NULL),
(20210015,'java考试',1,'2022-04-03 11:05:05','00:45:00','1234',2,6003,1001,7,NULL),
(20210016,'xxxxxx',1,'2022-04-09 18:29:32','00:30:00','1234',2,6003,1001,7,NULL),
(20210017,'java',1,'2022-04-09 20:38:20','00:30:00','1234',2,6003,1001,7,NULL),
(20210018,'java测试',1,'2022-04-28 22:42:23','00:30:00','1234',2,6001,1001,100,NULL),
(20210019,'java测试',1,'2022-04-28 22:44:12','00:45:00','1234',2,6001,1001,8,NULL),
(20210020,'java测试',0,'2022-04-28 22:46:41','00:45:00','',2,6003,1001,100,NULL),
(20210021,'java测试',1,'2022-04-28 22:49:20','00:45:00','1234',2,6001,1001,8,NULL),
(20210022,'java测试',1,'2022-04-28 22:51:53','01:15:00','1234',2,6001,1001,8,NULL),
(20210023,'javaweb车市',0,'2022-04-28 23:45:16','01:15:00','',2,6003,1001,7,NULL),
(20210024,'javaweb',0,'2022-04-27 00:00:00','00:30:00','',2,6003,1001,100,NULL),
(20210025,'javaweb测试',0,'2022-04-29 00:02:05','00:45:00','',2,6003,1001,2,NULL),
(20210026,'java车市',0,'2022-04-29 00:29:05','01:00:00','',2,6003,1001,100,NULL),
(20210027,'数据结构',0,'2022-04-29 00:29:30','01:00:00','',2,6005,1001,13,NULL),
(20210028,'操作系统',0,'2022-04-29 11:58:12','01:00:00','',2,6002,1001,15,NULL),
(20210029,'javaWeb',0,'2022-04-29 11:59:32','01:00:00','',2,6003,1001,2,NULL),
(20210030,'javaweb',0,'2022-04-29 12:36:57','01:00:00','',2,6003,1001,2,NULL),
(20210031,'计网',0,'2022-04-29 12:41:58','01:15:00','',2,6001,1001,8,NULL),
(20210032,'计网1',0,'2022-04-29 12:51:13','01:15:00','',2,6001,1001,8,NULL),
(20210033,'计算',0,'2022-04-29 13:07:19','01:15:00','',2,6001,1001,8,NULL),
(20210034,'操作1',0,'2022-04-29 14:07:48','01:00:00','',2,6002,1001,15,NULL),
(20210035,'计算2',0,'2022-04-29 14:11:29','01:15:00','',2,6001,1001,8,NULL),
(20210042,'计算3',0,'2022-04-29 15:22:12','01:15:00','',2,6001,1001,8,NULL),
(20210043,'计算4',0,'2022-04-29 15:31:01','01:15:00','',2,6001,1001,8,NULL),
(20210044,'计算4',0,'2022-04-29 15:53:04','01:15:00','',2,6001,1001,8,NULL),
(20210045,'计算机测试5',1,'2022-04-29 16:03:20','01:15:00','1234',2,6001,1001,8,NULL),
(20210046,'123',1,'2022-05-29 22:56:44','01:00:00','123123',0,6001,1001,100,NULL);

/*Table structure for table `tplist` */

DROP TABLE IF EXISTS `tplist`;

CREATE TABLE `tplist` (
  `tp_id` int NOT NULL COMMENT '试卷id',
  `test_id` int NOT NULL COMMENT '试题id',
  PRIMARY KEY (`tp_id`,`test_id`) USING BTREE,
  KEY `test_id` (`test_id`) USING BTREE,
  CONSTRAINT `test_id` FOREIGN KEY (`test_id`) REFERENCES `testbank` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tp_id` FOREIGN KEY (`tp_id`) REFERENCES `testpaper` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tplist` */

insert  into `tplist`(`tp_id`,`test_id`) values 
(20210010,10001),
(20210013,10001),
(20210014,10001),
(20210015,10001),
(20210016,10001),
(20210017,10001),
(20210023,10001),
(20210025,10001),
(20210029,10001),
(20210030,10001),
(20210028,10002),
(20210034,10002),
(20210011,10011),
(20210008,10013),
(20210019,10013),
(20210021,10013),
(20210022,10013),
(20210031,10013),
(20210032,10013),
(20210033,10013),
(20210035,10013),
(20210042,10013),
(20210043,10013),
(20210044,10013),
(20210045,10013),
(20210001,10061),
(20210002,10061),
(20210028,10068),
(20210034,10068),
(20210001,10069),
(20210002,10069),
(20210027,10069),
(20210001,10070),
(20210002,10070),
(20210027,10070),
(20210001,10071),
(20210002,10071),
(20210027,10071),
(20210001,10072),
(20210002,10072),
(20210001,10073),
(20210027,10073),
(20210001,10074),
(20210027,10074),
(20210028,10075),
(20210034,10075),
(20210010,10076),
(20210013,10076),
(20210014,10076),
(20210015,10076),
(20210016,10076),
(20210017,10076),
(20210023,10076),
(20210008,10077),
(20210019,10077),
(20210021,10077),
(20210022,10077),
(20210031,10077),
(20210032,10077),
(20210033,10077),
(20210035,10077),
(20210042,10077),
(20210043,10077),
(20210044,10077),
(20210045,10077),
(20210008,10079),
(20210019,10079),
(20210021,10079),
(20210022,10079),
(20210031,10079),
(20210032,10079),
(20210033,10079),
(20210035,10079),
(20210042,10079),
(20210043,10079),
(20210044,10079),
(20210045,10079),
(20210010,10088),
(20210017,10088),
(20210023,10088);

/*Table structure for table `wrong_question` */

DROP TABLE IF EXISTS `wrong_question`;

CREATE TABLE `wrong_question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `student_id` (`student_id`) USING BTREE,
  KEY `question_id` (`question_id`) USING BTREE,
  CONSTRAINT `wrong_question_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `wrong_question_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `testbank` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `wrong_question` */

insert  into `wrong_question`(`id`,`student_id`,`question_id`) values 
(2,1001,10001);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
