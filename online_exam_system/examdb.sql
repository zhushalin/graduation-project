/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 127.0.0.1:3306
 Source Schema         : exam

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 02/06/2022 11:50:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `admin_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `admin_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `admin_username`(`admin_username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1001, 'admin', '123456', '李狗蛋', '男', 14, '15616879633', '755192560@qq.com');

-- ----------------------------
-- Table structure for answersheet
-- ----------------------------
DROP TABLE IF EXISTS `answersheet`;
CREATE TABLE `answersheet`  (
  `as_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '答题卡id',
  `test_id` int(11) NOT NULL COMMENT '试题id',
  `stu_answer` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生答案',
  `stu_score` int(11) DEFAULT NULL COMMENT '考生得分',
  `stu_id` int(11) DEFAULT NULL COMMENT '考生id',
  `tp_id` int(11) DEFAULT NULL COMMENT '试卷id',
  `is_ask` int(11) DEFAULT NULL COMMENT '是否为问答题，1是，0否',
  `atype` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`as_id`, `test_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  INDEX `tp_id`(`tp_id`) USING BTREE,
  INDEX `as_id`(`as_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 187 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '答题卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answersheet
-- ----------------------------
INSERT INTO `answersheet` VALUES (186, 10011, 'B', 0, 1001, 20210011, 0, NULL);

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `sign_in_time` datetime(0) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `subject_id`(`subject_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '签到信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES (3, 6001, 1001, '2022-04-26 07:57:16', 1);

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `col_id` int(11) NOT NULL COMMENT '学院id',
  `col_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`col_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学院' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (4001, '计算机科学与软件学院');
INSERT INTO `college` VALUES (4002, '美术学院');
INSERT INTO `college` VALUES (4003, '政法学院');
INSERT INTO `college` VALUES (4004, '音乐学院');

-- ----------------------------
-- Table structure for description
-- ----------------------------
DROP TABLE IF EXISTS `description`;
CREATE TABLE `description`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '平台简介' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of description
-- ----------------------------
INSERT INTO `description` VALUES (1, '平台简介', 1001, '2022-04-02 10:39:37');

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `file_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `file_id`(`file_id`) USING BTREE,
  INDEX `question_id`(`question_id`) USING BTREE,
  CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`file_id`) REFERENCES `learning_file` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favorite_ibfk_3` FOREIGN KEY (`question_id`) REFERENCES `testbank` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收藏' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorite
-- ----------------------------
INSERT INTO `favorite` VALUES (2, 1001, 2, NULL, 10071);

-- ----------------------------
-- Table structure for learning_file
-- ----------------------------
DROP TABLE IF EXISTS `learning_file`;
CREATE TABLE `learning_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型',
  `titile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
  `fileurl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `subject_id`(`subject_id`) USING BTREE,
  CONSTRAINT `learning_file_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`sub_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of learning_file
-- ----------------------------
INSERT INTO `learning_file` VALUES (5, NULL, NULL, '1', '你是', '阿斯顿撒多', 'http://localhost:8085/upload/1653995614427.jpg');
INSERT INTO `learning_file` VALUES (6, NULL, NULL, '1', '课件2', '课件2课件2课件2', 'http://localhost:8085/upload/1653998939933.pdf');
INSERT INTO `learning_file` VALUES (7, NULL, NULL, '2', '资料1', '资料1资料1', 'http://localhost:8085/upload/1653999028030.xlsx');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `major_id` int(11) NOT NULL COMMENT '专业id',
  `col_id` int(11) NOT NULL COMMENT '所属学院id',
  `major_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专业名称',
  PRIMARY KEY (`major_id`) USING BTREE,
  INDEX `col_id`(`col_id`) USING BTREE,
  CONSTRAINT `col_id` FOREIGN KEY (`col_id`) REFERENCES `college` (`col_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '专业' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (5001, 4001, '软件工程');
INSERT INTO `major` VALUES (5002, 4003, '法学');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `admin_id`(`admin_id`) USING BTREE,
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `administrator` (`admin_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '131', '12333331', 1001, '2022-03-17 10:23:43');
INSERT INTO `notice` VALUES (2, '123131qeqa', '13133131faafad', 1001, '2022-03-17 10:23:43');
INSERT INTO `notice` VALUES (3, 'qweqe', '123qeqeq', 1001, '2022-03-17 10:23:43');
INSERT INTO `notice` VALUES (7, 'aaa', '123', 1001, '2022-04-02 07:20:23');
INSERT INTO `notice` VALUES (9, '3', '4', 1001, '2022-04-02 08:19:07');
INSERT INTO `notice` VALUES (10, '123', '1231', 1001, '2022-04-02 08:23:41');
INSERT INTO `notice` VALUES (11, '123123', '12311231', 1001, '2022-04-02 08:23:49');
INSERT INTO `notice` VALUES (12, '18', NULL, 1001, '2022-04-02 15:54:43');
INSERT INTO `notice` VALUES (13, '1111', '1111', NULL, '2022-05-30 06:21:52');
INSERT INTO `notice` VALUES (14, '阿士大夫撒发', ' 阿斯顿发送到', NULL, '2022-05-30 06:22:50');
INSERT INTO `notice` VALUES (18, '第三方三', '发斯蒂芬', 1001, '2022-05-30 06:25:43');
INSERT INTO `notice` VALUES (19, '快了快了', '马上就做完了', 1001, '2022-05-30 06:31:47');
INSERT INTO `notice` VALUES (20, '123', '123', 1001, '2022-05-30 07:43:51');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) NOT NULL,
  `sub_id` int(11) DEFAULT NULL COMMENT '科目id',
  `sub_score` double(10, 0) DEFAULT NULL COMMENT '科目成绩',
  `tp_id` int(11) DEFAULT NULL COMMENT '答题卡id',
  `submit_time` datetime(0) DEFAULT NULL COMMENT '交卷时间',
  `state` int(11) DEFAULT NULL COMMENT '成绩状态，0未公开，1公开',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sub_id`(`sub_id`) USING BTREE,
  INDEX `as_id`(`tp_id`) USING BTREE,
  CONSTRAINT `as_id` FOREIGN KEY (`tp_id`) REFERENCES `testpaper` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ssub_id` FOREIGN KEY (`sub_id`) REFERENCES `subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '得分' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (2, 1001, 6005, 0, 20210002, '2022-04-03 09:11:16', 1);
INSERT INTO `score` VALUES (4, 1001, 6003, 2, 20210010, '2022-04-03 00:35:23', 1);
INSERT INTO `score` VALUES (5, 1001, 6003, 0, 20210013, '2022-04-03 00:58:38', 1);
INSERT INTO `score` VALUES (6, 1001, 6003, 0, 20210014, '2022-04-03 01:02:40', 1);
INSERT INTO `score` VALUES (7, 1001, 6003, 2, 20210016, '2022-04-09 10:31:45', 1);
INSERT INTO `score` VALUES (8, 1001, 6001, 3, 20210033, '2022-04-29 06:56:32', 0);
INSERT INTO `score` VALUES (9, 1001, 6001, 0, 20210035, '2022-04-29 06:56:59', 0);
INSERT INTO `score` VALUES (10, 1001, 6001, 0, 20210042, '2022-04-29 07:22:52', 1);
INSERT INTO `score` VALUES (11, 1001, 6001, 2, 20210043, '2022-04-29 07:31:52', 0);
INSERT INTO `score` VALUES (12, 1001, 6001, 0, 20210044, '2022-04-29 07:53:57', 0);
INSERT INTO `score` VALUES (13, 1001, 6001, 3, 20210045, '2022-04-29 08:05:27', 0);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `stu_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stu_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `major_id` int(11) DEFAULT NULL COMMENT '专业id',
  `col_id` int(11) DEFAULT NULL COMMENT '学院id',
  `stu_state` int(11) NOT NULL COMMENT '审核状态',
  `age` int(11) DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`stu_id`) USING BTREE,
  UNIQUE INDEX `stu_username`(`stu_username`) USING BTREE,
  INDEX `major_id`(`major_id`) USING BTREE,
  INDEX `col_id`(`col_id`) USING BTREE,
  CONSTRAINT `major_id` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3033 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1001, 'admin', '123456', '学生', 5001, 4003, 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3001, '201824113316', '123456', '李狗蛋', 5001, 4001, 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3002, '200001', '123456', '张三', 5002, 4003, 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3003, '3003', '123456', '李子', 5001, 4001, 0, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3005, '3005', '123456', '阿狗子', 5001, 4001, 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3008, '201824113350', '123456', '覃阿才', 5001, 4001, 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3009, '161002412', '123321', '贾小熠', 5001, 4001, 0, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES (3031, '123321', '123456', '123321', NULL, 4001, 1, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES (3032, '1122', '123456', '123', 5002, 4003, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `sub_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '科目id',
  `major_id` int(11) NOT NULL COMMENT '专业id',
  `sub_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科目名称',
  `teacher_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`sub_id`, `major_id`) USING BTREE,
  INDEX `major_id`(`major_id`) USING BTREE,
  INDEX `sub_id`(`sub_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `smajor_id` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`tea_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6007 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '科目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES (6001, 5001, '计网', NULL);
INSERT INTO `subject` VALUES (6002, 5001, '操作系统', NULL);
INSERT INTO `subject` VALUES (6003, 5001, 'javaWeb开发', NULL);
INSERT INTO `subject` VALUES (6004, 5001, '软件测试', NULL);
INSERT INTO `subject` VALUES (6005, 5001, '数据结构', NULL);
INSERT INTO `subject` VALUES (6006, 5002, '刑法', NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tea_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `tea_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `tea_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `tea_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `col_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学院名称',
  `tea_state` int(11) NOT NULL COMMENT '审核状态',
  `age` int(11) DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`tea_id`) USING BTREE,
  UNIQUE INDEX `tea_username`(`tea_username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2018 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1001, 'admin', '123456', '张三', '音乐学院', 1, 14, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2001, '202021', '123123', '李小芳', '计算机科学与软件学院', 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2009, '202024', '123456', '阿狗', '音乐学院', 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2013, '202025', '123456', '王大锤', '政法学院', 0, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2014, '123', '123', 'zs', '美术学院', 0, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2015, '202026', '123456', '测试', '计算机科学与软件学院', 0, NULL, NULL, NULL, NULL);
INSERT INTO `teacher` VALUES (2016, '1004', '123123', '萧瑟', '计算机科学与软件学院', 1, NULL, NULL, NULL, NULL);
INSERT INTO `teacher` VALUES (2017, '80', '123123', '法爷', '政法学院', 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for testbank
-- ----------------------------
DROP TABLE IF EXISTS `testbank`;
CREATE TABLE `testbank`  (
  `test_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试题id',
  `sub_id` int(11) DEFAULT NULL COMMENT '学科id',
  `test_type` int(11) DEFAULT NULL,
  `test_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '题目内容',
  `option_a` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `option_b` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `option_c` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `option_d` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '答案',
  `score` int(11) DEFAULT NULL COMMENT '分值',
  `test_date` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `tea_id` int(11) DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`test_id`) USING BTREE,
  INDEX `tea_id`(`tea_id`) USING BTREE,
  CONSTRAINT `tea_id` FOREIGN KEY (`tea_id`) REFERENCES `teacher` (`tea_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10090 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testbank
-- ----------------------------
INSERT INTO `testbank` VALUES (10001, 6003, 3, '开汉是崽崽码？', '是', '否', '', '', 'A', 2, '2021-05-21 17:40:11', 2001, NULL, NULL);
INSERT INTO `testbank` VALUES (10002, 6002, 2, '开汉是SB码？', '是', '不然呢', '必须的', '你以为', 'ABCD', 5, '2021-05-21 17:40:13', 2001, '审核通过', '简单');
INSERT INTO `testbank` VALUES (10011, 6004, 1, 'aaaaaaa', 'aaaaaaa', 'aaaa', 'aaaaa', 'aaaaaaa', 'A', 3, '2021-05-21 17:40:14', 2001, NULL, NULL);
INSERT INTO `testbank` VALUES (10013, 6001, 1, 'sdads', 'sad', 'asd', 'asd', 'sad', 'B', 3, '2021-05-21 17:40:15', 2001, NULL, NULL);
INSERT INTO `testbank` VALUES (10061, 6005, 2, '1+1=', '11', '10', '01', '2', 'BCD', 5, '2021-05-27 11:11:38', 1001, NULL, '简单');
INSERT INTO `testbank` VALUES (10068, 6002, 2, '111', '11', '11', '11', '11', 'BC', 5, '2021-05-28 17:25:08', 1001, NULL, '简单');
INSERT INTO `testbank` VALUES (10069, 6005, 1, '11', '11', '11', '11', '11', 'B', 3, '2021-05-28 21:56:32', 1001, NULL, '简单');
INSERT INTO `testbank` VALUES (10070, 6005, 3, '1+1=1', '对', '错', NULL, NULL, 'B', 2, '2021-05-30 11:14:19', 1001, NULL, '简单');
INSERT INTO `testbank` VALUES (10071, 6005, 1, '2+2=？', '1', '2', '3', '4', 'D', 3, '2021-05-30 11:15:12', 1001, NULL, '简单');
INSERT INTO `testbank` VALUES (10072, 6005, 2, '2+2=？', '22', '2', '4', '42', 'AC', 5, '2021-05-30 11:17:18', 1001, NULL, '简单');
INSERT INTO `testbank` VALUES (10073, 6005, 3, '2+2=4', '对', '错', NULL, NULL, 'A', 2, '2021-05-30 11:17:53', 1001, NULL, '简单');
INSERT INTO `testbank` VALUES (10074, 6005, 1, '3+3=？', '6', '33', '43', '3', 'A', 3, '2021-05-30 12:39:46', 1001, NULL, '重难');
INSERT INTO `testbank` VALUES (10075, 6002, 2, '11', '11', '11', '11', '111', 'ABC', 5, '2021-05-31 23:19:58', 1001, NULL, '重难');
INSERT INTO `testbank` VALUES (10076, 6003, 2, '123456', '11', '11', '11', '11', 'BCD', 5, '2021-05-31 23:20:59', 1001, NULL, NULL);
INSERT INTO `testbank` VALUES (10077, 6001, 3, '1', '1', '1', '1', '1', 'A', 2, '2022-04-04 11:29:09', 1001, NULL, NULL);
INSERT INTO `testbank` VALUES (10079, 6001, 1, '1', '123', '2', '123', '3', 'A', 3, '2022-04-09 16:38:29', 1001, NULL, NULL);
INSERT INTO `testbank` VALUES (10088, 6003, 4, '1', NULL, NULL, NULL, '', '1', 10, '2022-04-09 20:28:10', 1001, NULL, NULL);
INSERT INTO `testbank` VALUES (10089, 6001, 3, '能不能加油做完', '能', '不能', NULL, NULL, 'A', 2, '2022-05-30 04:13:12', 2001, NULL, NULL);

-- ----------------------------
-- Table structure for testpaper
-- ----------------------------
DROP TABLE IF EXISTS `testpaper`;
CREATE TABLE `testpaper`  (
  `tp_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷id',
  `tp_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '试卷名称',
  `tp_type` int(11) NOT NULL COMMENT '试卷类型',
  `exam_time` datetime(0) DEFAULT NULL COMMENT '考试开始时间',
  `exam_length` time(0) DEFAULT NULL COMMENT '考试时长',
  `exam_password` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '入考码',
  `exam_state` int(11) DEFAULT NULL COMMENT '考试状态',
  `sub_id` int(11) DEFAULT NULL COMMENT '科目id',
  `tea_id` int(11) DEFAULT NULL COMMENT '教师id',
  `exam_total` int(11) NOT NULL DEFAULT 0 COMMENT '总分',
  `files` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '考试' COMMENT '类型',
  PRIMARY KEY (`tp_id`) USING BTREE,
  INDEX `sub_id`(`sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20210051 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testpaper
-- ----------------------------
INSERT INTO `testpaper` VALUES (20210001, '数据结构期中测试', 1, '2022-04-01 08:11:31', '03:00:00', '123456', 2, 6005, 2001, 23, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210002, '算法分析期末测试', 0, '2022-04-01 08:11:22', '02:00:00', '', 2, 6005, 2001, 13, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210010, 'javaweb考试', 0, '2022-05-31 15:00:00', '00:30:00', '', 2, 6003, 1001, 7, '1649489633359.docx', '考试');
INSERT INTO `testpaper` VALUES (20210011, '软件测试', 0, '2022-06-01 22:55:00', '01:15:00', '', 1, 6002, 1001, 3, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210013, 'java考试', 0, '2022-06-01 21:44:02', '00:45:00', '', 2, 6003, 1001, 7, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210014, 'javaweb考试', 0, '2022-05-31 15:00:00', '00:30:00', '', 2, 6003, 1001, 3, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210015, 'java考试', 1, '2022-04-03 11:05:05', '00:45:00', '1234', 2, 6003, 1001, 7, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210016, 'xxxxxx', 1, '2022-04-09 18:29:32', '00:30:00', '1234', 2, 6003, 1001, 7, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210017, 'java', 1, '2022-04-09 20:38:20', '00:30:00', '1234', 2, 6003, 1001, 7, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210019, 'java测试', 1, '2022-04-28 22:44:12', '00:45:00', '1234', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210020, 'java测试', 0, '2022-04-28 22:46:41', '00:45:00', '', 2, 6003, 1001, 100, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210021, 'java测试', 1, '2022-04-28 22:49:20', '00:45:00', '1234', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210022, 'java测试', 1, '2022-04-28 22:51:53', '01:15:00', '1234', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210023, 'javaweb车市', 0, '2022-04-28 23:45:16', '01:15:00', '', 2, 6003, 1001, 7, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210024, 'javaweb', 0, '2022-04-27 00:00:00', '00:30:00', '', 2, 6003, 1001, 100, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210025, 'javaweb测试', 0, '2022-04-29 00:02:05', '00:45:00', '', 2, 6003, 1001, 2, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210026, 'java车市', 0, '2022-04-29 00:29:05', '01:00:00', '', 2, 6003, 1001, 100, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210027, '数据结构', 0, '2022-04-29 00:29:30', '01:00:00', '', 2, 6005, 1001, 13, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210028, '操作系统', 0, '2022-04-29 11:58:12', '01:00:00', '', 2, 6002, 1001, 15, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210029, 'javaWeb', 0, '2022-04-29 11:59:32', '01:00:00', '', 2, 6003, 1001, 2, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210030, 'javaweb', 0, '2022-04-29 12:36:57', '01:00:00', '', 2, 6003, 1001, 2, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210031, '计网', 0, '2022-04-29 12:41:58', '01:15:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210032, '计网1', 0, '2022-04-29 12:51:13', '01:15:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210033, '计算', 0, '2022-04-29 13:07:19', '01:15:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210034, '操作1', 0, '2022-04-29 14:07:48', '01:00:00', '', 2, 6002, 1001, 15, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210035, '计算2', 0, '2022-04-29 14:11:29', '01:15:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210042, '计算3', 0, '2022-04-29 15:22:12', '01:15:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210043, '计算4', 0, '2022-04-29 15:31:01', '01:15:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210044, '计算4', 0, '2022-04-29 15:53:04', '01:15:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210045, '计算机测试5', 1, '2022-04-29 16:03:20', '01:15:00', '1234', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210046, '123321', 0, '2022-05-30 00:00:00', '01:00:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210047, '1', 0, '2022-05-29 20:46:00', '00:30:00', '', 2, 6001, 1001, 8, NULL, '考试');
INSERT INTO `testpaper` VALUES (20210048, '计算机网络', 1, '2022-05-30 12:34:54', '00:30:00', '123123', 2, 6001, 2001, 10, '', '考试');
INSERT INTO `testpaper` VALUES (20210050, '5.30', 1, '2022-05-20 00:00:00', '00:30:00', '123123', 2, 6001, 1001, 100, NULL, '考试');

-- ----------------------------
-- Table structure for tplist
-- ----------------------------
DROP TABLE IF EXISTS `tplist`;
CREATE TABLE `tplist`  (
  `tp_id` int(11) NOT NULL COMMENT '试卷id',
  `test_id` int(11) NOT NULL COMMENT '试题id',
  PRIMARY KEY (`tp_id`, `test_id`) USING BTREE,
  INDEX `test_id`(`test_id`) USING BTREE,
  CONSTRAINT `test_id` FOREIGN KEY (`test_id`) REFERENCES `testbank` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tp_id` FOREIGN KEY (`tp_id`) REFERENCES `testpaper` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷和试题关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tplist
-- ----------------------------
INSERT INTO `tplist` VALUES (20210010, 10001);
INSERT INTO `tplist` VALUES (20210013, 10001);
INSERT INTO `tplist` VALUES (20210014, 10001);
INSERT INTO `tplist` VALUES (20210015, 10001);
INSERT INTO `tplist` VALUES (20210016, 10001);
INSERT INTO `tplist` VALUES (20210017, 10001);
INSERT INTO `tplist` VALUES (20210023, 10001);
INSERT INTO `tplist` VALUES (20210025, 10001);
INSERT INTO `tplist` VALUES (20210029, 10001);
INSERT INTO `tplist` VALUES (20210030, 10001);
INSERT INTO `tplist` VALUES (20210028, 10002);
INSERT INTO `tplist` VALUES (20210034, 10002);
INSERT INTO `tplist` VALUES (20210011, 10011);
INSERT INTO `tplist` VALUES (20210014, 10013);
INSERT INTO `tplist` VALUES (20210019, 10013);
INSERT INTO `tplist` VALUES (20210021, 10013);
INSERT INTO `tplist` VALUES (20210022, 10013);
INSERT INTO `tplist` VALUES (20210031, 10013);
INSERT INTO `tplist` VALUES (20210032, 10013);
INSERT INTO `tplist` VALUES (20210033, 10013);
INSERT INTO `tplist` VALUES (20210035, 10013);
INSERT INTO `tplist` VALUES (20210042, 10013);
INSERT INTO `tplist` VALUES (20210043, 10013);
INSERT INTO `tplist` VALUES (20210044, 10013);
INSERT INTO `tplist` VALUES (20210045, 10013);
INSERT INTO `tplist` VALUES (20210046, 10013);
INSERT INTO `tplist` VALUES (20210047, 10013);
INSERT INTO `tplist` VALUES (20210048, 10013);
INSERT INTO `tplist` VALUES (20210001, 10061);
INSERT INTO `tplist` VALUES (20210002, 10061);
INSERT INTO `tplist` VALUES (20210028, 10068);
INSERT INTO `tplist` VALUES (20210034, 10068);
INSERT INTO `tplist` VALUES (20210001, 10069);
INSERT INTO `tplist` VALUES (20210002, 10069);
INSERT INTO `tplist` VALUES (20210027, 10069);
INSERT INTO `tplist` VALUES (20210001, 10070);
INSERT INTO `tplist` VALUES (20210002, 10070);
INSERT INTO `tplist` VALUES (20210027, 10070);
INSERT INTO `tplist` VALUES (20210001, 10071);
INSERT INTO `tplist` VALUES (20210002, 10071);
INSERT INTO `tplist` VALUES (20210027, 10071);
INSERT INTO `tplist` VALUES (20210001, 10072);
INSERT INTO `tplist` VALUES (20210001, 10073);
INSERT INTO `tplist` VALUES (20210027, 10073);
INSERT INTO `tplist` VALUES (20210001, 10074);
INSERT INTO `tplist` VALUES (20210027, 10074);
INSERT INTO `tplist` VALUES (20210028, 10075);
INSERT INTO `tplist` VALUES (20210034, 10075);
INSERT INTO `tplist` VALUES (20210010, 10076);
INSERT INTO `tplist` VALUES (20210013, 10076);
INSERT INTO `tplist` VALUES (20210014, 10076);
INSERT INTO `tplist` VALUES (20210015, 10076);
INSERT INTO `tplist` VALUES (20210016, 10076);
INSERT INTO `tplist` VALUES (20210017, 10076);
INSERT INTO `tplist` VALUES (20210023, 10076);
INSERT INTO `tplist` VALUES (20210019, 10077);
INSERT INTO `tplist` VALUES (20210021, 10077);
INSERT INTO `tplist` VALUES (20210022, 10077);
INSERT INTO `tplist` VALUES (20210031, 10077);
INSERT INTO `tplist` VALUES (20210032, 10077);
INSERT INTO `tplist` VALUES (20210033, 10077);
INSERT INTO `tplist` VALUES (20210035, 10077);
INSERT INTO `tplist` VALUES (20210042, 10077);
INSERT INTO `tplist` VALUES (20210043, 10077);
INSERT INTO `tplist` VALUES (20210044, 10077);
INSERT INTO `tplist` VALUES (20210045, 10077);
INSERT INTO `tplist` VALUES (20210046, 10077);
INSERT INTO `tplist` VALUES (20210047, 10077);
INSERT INTO `tplist` VALUES (20210048, 10077);
INSERT INTO `tplist` VALUES (20210019, 10079);
INSERT INTO `tplist` VALUES (20210021, 10079);
INSERT INTO `tplist` VALUES (20210022, 10079);
INSERT INTO `tplist` VALUES (20210031, 10079);
INSERT INTO `tplist` VALUES (20210032, 10079);
INSERT INTO `tplist` VALUES (20210033, 10079);
INSERT INTO `tplist` VALUES (20210035, 10079);
INSERT INTO `tplist` VALUES (20210042, 10079);
INSERT INTO `tplist` VALUES (20210043, 10079);
INSERT INTO `tplist` VALUES (20210044, 10079);
INSERT INTO `tplist` VALUES (20210045, 10079);
INSERT INTO `tplist` VALUES (20210046, 10079);
INSERT INTO `tplist` VALUES (20210047, 10079);
INSERT INTO `tplist` VALUES (20210048, 10079);
INSERT INTO `tplist` VALUES (20210010, 10088);
INSERT INTO `tplist` VALUES (20210017, 10088);
INSERT INTO `tplist` VALUES (20210023, 10088);
INSERT INTO `tplist` VALUES (20210048, 10089);

-- ----------------------------
-- Table structure for wrong_question
-- ----------------------------
DROP TABLE IF EXISTS `wrong_question`;
CREATE TABLE `wrong_question`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `question_id`(`question_id`) USING BTREE,
  CONSTRAINT `wrong_question_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `wrong_question_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `testbank` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '错题集' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
