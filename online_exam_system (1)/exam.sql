/*
 Navicat Premium Data Transfer

 Source Server         : project
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : exam

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 03/04/2022 09:46:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `admin_id` int(0) NOT NULL COMMENT '管理员id',
  `admin_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `admin_username`(`admin_username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1001, 'admin', '123456', '李狗蛋', '男', 18, '15616879633', '755192560@qq.com');

-- ----------------------------
-- Table structure for answersheet
-- ----------------------------
DROP TABLE IF EXISTS `answersheet`;
CREATE TABLE `answersheet`  (
  `as_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '答题卡id',
  `test_id` int(0) NOT NULL COMMENT '试题id',
  `stu_answer` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生答案',
  `stu_score` int(0) NULL DEFAULT NULL COMMENT '考生得分',
  `stu_id` int(0) NULL DEFAULT NULL COMMENT '考生id',
  `tp_id` int(0) NULL DEFAULT NULL COMMENT '试卷id',
  PRIMARY KEY (`as_id`, `test_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  INDEX `tp_id`(`tp_id`) USING BTREE,
  INDEX `as_id`(`as_id`) USING BTREE,
  CONSTRAINT `answersheet_ibfk_1` FOREIGN KEY (`tp_id`) REFERENCES `testpaper` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `astu_id` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 120 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answersheet
-- ----------------------------
INSERT INTO `answersheet` VALUES (116, 10069, 'A', 0, 1001, 20210002);
INSERT INTO `answersheet` VALUES (117, 10071, 'B', 0, 1001, 20210002);
INSERT INTO `answersheet` VALUES (118, 10061, 'BC', 0, 1001, 20210002);
INSERT INTO `answersheet` VALUES (119, 10070, 'A', 0, 1001, 20210002);
INSERT INTO `answersheet` VALUES (120, 10013, 'D', 0, 1001, 20210008);
INSERT INTO `answersheet` VALUES (121, 10076, 'C', 0, 1001, 20210010);
INSERT INTO `answersheet` VALUES (122, 10001, 'A', 2, 1001, 20210010);
INSERT INTO `answersheet` VALUES (123, 10011, 'B', 0, 1001, 20210011);
INSERT INTO `answersheet` VALUES (124, 10011, 'A', 3, 1001, 20210011);
INSERT INTO `answersheet` VALUES (125, 10076, 'A', 0, 1001, 20210013);
INSERT INTO `answersheet` VALUES (126, 10001, 'B', 0, 1001, 20210013);
INSERT INTO `answersheet` VALUES (127, 10076, 'A', 0, 1001, 20210014);
INSERT INTO `answersheet` VALUES (128, 10001, 'B', 0, 1001, 20210014);

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `col_id` int(0) NOT NULL COMMENT '学院id',
  `col_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`col_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_id` int(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of description
-- ----------------------------
INSERT INTO `description` VALUES (1, 'dadadaa', 1001, '2022-04-02 10:39:37');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `major_id` int(0) NOT NULL COMMENT '专业id',
  `col_id` int(0) NOT NULL COMMENT '所属学院id',
  `major_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业名称',
  PRIMARY KEY (`major_id`) USING BTREE,
  INDEX `col_id`(`col_id`) USING BTREE,
  CONSTRAINT `col_id` FOREIGN KEY (`col_id`) REFERENCES `college` (`col_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_id` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `admin_id`(`admin_id`) USING BTREE,
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `administrator` (`admin_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '131', '131313adad111', 1001, '2022-03-17 10:23:43');
INSERT INTO `notice` VALUES (2, '123131qeqa', '13133131faafad', 1001, '2022-03-17 10:23:43');
INSERT INTO `notice` VALUES (3, 'qweqe', '123qeqeq', 1001, '2022-03-17 10:23:43');
INSERT INTO `notice` VALUES (4, '123131dadadqeqe', '131313qeqe', 1001, '2022-03-17 10:23:43');
INSERT INTO `notice` VALUES (7, 'aaa', '123', 1001, '2022-04-02 07:20:23');
INSERT INTO `notice` VALUES (8, '1', '1', 1001, '2022-04-02 08:19:01');
INSERT INTO `notice` VALUES (9, '3', '4', 1001, '2022-04-02 08:19:07');
INSERT INTO `notice` VALUES (10, '123', '1231', 1001, '2022-04-02 08:23:41');
INSERT INTO `notice` VALUES (11, '123123', '12311231', 1001, '2022-04-02 08:23:49');
INSERT INTO `notice` VALUES (12, '18', NULL, 1001, '2022-04-02 15:54:43');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `stu_id` int(0) NOT NULL,
  `sub_id` int(0) NULL DEFAULT NULL COMMENT '科目id',
  `sub_score` double(10, 0) NULL DEFAULT NULL COMMENT '科目成绩',
  `tp_id` int(0) NULL DEFAULT NULL COMMENT '答题卡id',
  `leave_times` int(0) NULL DEFAULT NULL COMMENT '离开页面次数',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始答题时间',
  `submit_time` datetime(0) NULL DEFAULT NULL COMMENT '交卷时间',
  `state` int(0) NULL DEFAULT NULL COMMENT '成绩状态，0未公开，1公开',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sub_id`(`sub_id`) USING BTREE,
  INDEX `as_id`(`tp_id`) USING BTREE,
  CONSTRAINT `as_id` FOREIGN KEY (`tp_id`) REFERENCES `testpaper` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ssub_id` FOREIGN KEY (`sub_id`) REFERENCES `subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (2, 1001, 6005, 0, 20210002, 1, '2022-04-03 08:11:11', '2022-04-03 09:11:16', 0);
INSERT INTO `score` VALUES (3, 1001, 6001, 0, 20210008, 12, '2022-04-02 17:10:54', '2022-04-02 23:54:52', 0);
INSERT INTO `score` VALUES (4, 1001, 6003, 2, 20210010, 2, NULL, '2022-04-03 00:35:23', 0);
INSERT INTO `score` VALUES (5, 1001, 6003, 0, 20210013, 2, '2022-04-03 00:58:30', '2022-04-03 00:58:38', 0);
INSERT INTO `score` VALUES (6, 1001, 6003, 0, 20210014, 2, '2022-04-03 01:02:34', '2022-04-03 01:02:40', 0);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `stu_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stu_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `major_id` int(0) NULL DEFAULT NULL COMMENT '专业id',
  `col_id` int(0) NULL DEFAULT NULL COMMENT '学院id',
  `stu_state` int(0) NOT NULL COMMENT '审核状态',
  `age` int(0) NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stu_id`) USING BTREE,
  UNIQUE INDEX `stu_username`(`stu_username`) USING BTREE,
  INDEX `major_id`(`major_id`) USING BTREE,
  INDEX `col_id`(`col_id`) USING BTREE,
  CONSTRAINT `major_id` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3009 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1001, 'admin', '123456', '学生1', 5001, 4001, 1, 25, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3001, '201824113316', '123456', '李狗蛋', 5001, 4001, 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3002, '200001', '123456', '张三', 5002, 4003, 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3003, '3003', '123456', '李子', 5001, 4001, 0, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3005, '3005', '123456', '阿狗子', 5001, 4001, 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `student` VALUES (3008, '201824113350', '123456', '覃阿才', 5001, 4001, 1, 18, '男', '15616879633', '755192560@qq.com');

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `sub_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '科目id',
  `major_id` int(0) NOT NULL COMMENT '专业id',
  `sub_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科目名称',
  PRIMARY KEY (`sub_id`, `major_id`) USING BTREE,
  INDEX `major_id`(`major_id`) USING BTREE,
  INDEX `sub_id`(`sub_id`) USING BTREE,
  CONSTRAINT `smajor_id` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6006 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES (6001, 5001, '计网');
INSERT INTO `subject` VALUES (6002, 5001, '操作系统');
INSERT INTO `subject` VALUES (6003, 5001, 'javaWeb开发');
INSERT INTO `subject` VALUES (6004, 5001, '软件测试');
INSERT INTO `subject` VALUES (6005, 5001, '数据结构');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tea_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `tea_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tea_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tea_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `col_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院名称',
  `tea_state` int(0) NOT NULL COMMENT '审核状态',
  `age` int(0) NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tea_id`) USING BTREE,
  UNIQUE INDEX `tea_username`(`tea_username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2015 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1001, 'admin', '123456', '教师1', '计算机科学与软件学院', 1, 23, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2001, '202021', '123456', '李小芳', '计算机科学与软件学院', 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2002, '202022', '123456', '钟惠', '计算机科学与软件学院', 0, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2008, '202023', '123456', '李狗蛋', '美术学院', 0, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2009, '202024', '123456', '阿狗', '音乐学院', 1, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2013, '202025', '123456', '王大锤', '政法学院', 0, 18, '男', '15616879633', '755192560@qq.com');
INSERT INTO `teacher` VALUES (2014, '123', '123', 'zs', '美术学院', 0, 18, '男', '15616879633', '755192560@qq.com');

-- ----------------------------
-- Table structure for testbank
-- ----------------------------
DROP TABLE IF EXISTS `testbank`;
CREATE TABLE `testbank`  (
  `test_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '试题id',
  `sub_id` int(0) NULL DEFAULT NULL COMMENT '学科id',
  `test_type` int(0) NULL DEFAULT NULL,
  `test_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题目内容',
  `option_a` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `option_b` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `option_c` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `option_d` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '答案',
  `score` int(0) NULL DEFAULT NULL COMMENT '分值',
  `test_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上传时间',
  `tea_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`test_id`) USING BTREE,
  INDEX `tea_id`(`tea_id`) USING BTREE,
  CONSTRAINT `tea_id` FOREIGN KEY (`tea_id`) REFERENCES `teacher` (`tea_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10077 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testbank
-- ----------------------------
INSERT INTO `testbank` VALUES (10001, 6003, 3, '开汉是崽崽码？', '是', '否', '', '', 'A', 2, '2021-05-21 17:40:11', 2001);
INSERT INTO `testbank` VALUES (10002, 6002, 2, '开汉是SB码？', '是', '不然呢', '必须的', '你以为', 'ABCD', 5, '2021-05-21 17:40:13', 2001);
INSERT INTO `testbank` VALUES (10011, 6004, 1, 'aaaaaaa', 'aaaaaaa', 'aaaa', 'aaaaa', 'aaaaaaa', 'A', 3, '2021-05-21 17:40:14', 2001);
INSERT INTO `testbank` VALUES (10013, 6001, 1, 'sdads', 'sad', 'asd', 'asd', 'sad', 'B', 3, '2021-05-21 17:40:15', 2001);
INSERT INTO `testbank` VALUES (10061, 6005, 2, '1+1=', '11', '10', '01', '2', 'BD', 5, '2021-05-27 11:11:38', 1001);
INSERT INTO `testbank` VALUES (10068, 6002, 2, '111', '11', '11', '11', '11', 'BC', 5, '2021-05-28 17:25:08', 1001);
INSERT INTO `testbank` VALUES (10069, 6005, 1, '11', '11', '11', '11', '11', 'B', 3, '2021-05-28 21:56:32', 1001);
INSERT INTO `testbank` VALUES (10070, 6005, 3, '1+1=1', '对', '错', NULL, NULL, 'B', 2, '2021-05-30 11:14:19', 1001);
INSERT INTO `testbank` VALUES (10071, 6005, 1, '2+2=？', '1', '2', '3', '4', 'D', 3, '2021-05-30 11:15:12', 1001);
INSERT INTO `testbank` VALUES (10072, 6005, 2, '2+2=？', '22', '2', '4', '42', 'AC', 5, '2021-05-30 11:17:18', 1001);
INSERT INTO `testbank` VALUES (10073, 6005, 3, '2+2=4', '对', '错', NULL, NULL, 'A', 2, '2021-05-30 11:17:53', 1001);
INSERT INTO `testbank` VALUES (10074, 6005, 1, '3+3=？', '6', '33', '43', '3', 'A', 3, '2021-05-30 12:39:46', 1001);
INSERT INTO `testbank` VALUES (10075, 6002, 2, '11', '11', '11', '11', '111', 'ABC', 5, '2021-05-31 23:19:58', 1001);
INSERT INTO `testbank` VALUES (10076, 6003, 2, '123456', '11', '11', '11', '11', 'BCD', 5, '2021-05-31 23:20:59', 1001);

-- ----------------------------
-- Table structure for testpaper
-- ----------------------------
DROP TABLE IF EXISTS `testpaper`;
CREATE TABLE `testpaper`  (
  `tp_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '试卷id',
  `tp_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试卷名称',
  `tp_type` int(0) NOT NULL COMMENT '试卷类型',
  `exam_time` datetime(0) NULL DEFAULT NULL COMMENT '考试开始时间',
  `exam_length` time(0) NULL DEFAULT NULL COMMENT '考试时长',
  `exam_password` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '入考码',
  `exam_state` int(0) NULL DEFAULT NULL COMMENT '考试状态',
  `sub_id` int(0) NULL DEFAULT NULL COMMENT '科目id',
  `tea_id` int(0) NULL DEFAULT NULL COMMENT '教师id',
  `exam_total` int(0) NOT NULL DEFAULT 0 COMMENT '总分',
  PRIMARY KEY (`tp_id`) USING BTREE,
  INDEX `sub_id`(`sub_id`) USING BTREE,
  CONSTRAINT `sub_id` FOREIGN KEY (`sub_id`) REFERENCES `subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20210008 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testpaper
-- ----------------------------
INSERT INTO `testpaper` VALUES (20210001, '数据结构期中测试', 1, '2022-04-01 08:11:31', '03:00:00', '123456', 2, 6005, 2001, 23);
INSERT INTO `testpaper` VALUES (20210002, '算法分析期末测试', 0, '2022-04-01 08:11:22', '02:00:00', '', 2, 6005, 2001, 13);
INSERT INTO `testpaper` VALUES (20210008, '数据结构', 0, '2022-04-03 07:52:34', '04:00:00', '', 1, 6001, 1001, 3);
INSERT INTO `testpaper` VALUES (20210010, 'javaweb考试', 1, '2022-04-03 08:43:00', '00:45:00', '1234', 2, 6003, 1001, 0);
INSERT INTO `testpaper` VALUES (20210011, '软件测试', 1, '2022-04-03 08:43:46', '00:45:00', '1234', 2, 6004, 1001, 3);
INSERT INTO `testpaper` VALUES (20210013, 'java考试', 1, '2022-04-03 08:57:07', '01:00:00', '1234', 1, 6003, 1001, 7);
INSERT INTO `testpaper` VALUES (20210014, 'javaweb考试', 1, '2022-04-03 09:01:26', '00:30:00', '1234', 2, 6003, 1001, 7);

-- ----------------------------
-- Table structure for tplist
-- ----------------------------
DROP TABLE IF EXISTS `tplist`;
CREATE TABLE `tplist`  (
  `tp_id` int(0) NOT NULL COMMENT '试卷id',
  `test_id` int(0) NOT NULL COMMENT '试题id',
  PRIMARY KEY (`tp_id`, `test_id`) USING BTREE,
  INDEX `test_id`(`test_id`) USING BTREE,
  CONSTRAINT `test_id` FOREIGN KEY (`test_id`) REFERENCES `testbank` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tp_id` FOREIGN KEY (`tp_id`) REFERENCES `testpaper` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tplist
-- ----------------------------
INSERT INTO `tplist` VALUES (20210013, 10001);
INSERT INTO `tplist` VALUES (20210014, 10001);
INSERT INTO `tplist` VALUES (20210011, 10011);
INSERT INTO `tplist` VALUES (20210008, 10013);
INSERT INTO `tplist` VALUES (20210001, 10061);
INSERT INTO `tplist` VALUES (20210002, 10061);
INSERT INTO `tplist` VALUES (20210001, 10069);
INSERT INTO `tplist` VALUES (20210002, 10069);
INSERT INTO `tplist` VALUES (20210001, 10070);
INSERT INTO `tplist` VALUES (20210002, 10070);
INSERT INTO `tplist` VALUES (20210001, 10071);
INSERT INTO `tplist` VALUES (20210002, 10071);
INSERT INTO `tplist` VALUES (20210001, 10072);
INSERT INTO `tplist` VALUES (20210001, 10073);
INSERT INTO `tplist` VALUES (20210001, 10074);
INSERT INTO `tplist` VALUES (20210013, 10076);
INSERT INTO `tplist` VALUES (20210014, 10076);

SET FOREIGN_KEY_CHECKS = 1;
