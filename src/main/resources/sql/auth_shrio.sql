/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.1.73
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : 192.168.1.73:3306
 Source Schema         : auth_shrio

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 02/12/2020 18:45:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cost
-- ----------------------------
DROP TABLE IF EXISTS `cost`;
CREATE TABLE `cost`  (
  `cost_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `house_id` int(11) NOT NULL COMMENT '房屋ID',
  `create_date` date NOT NULL COMMENT '创建日期',
  `money` int(11) NOT NULL DEFAULT 0 COMMENT '金额',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态:1 未支付，2:已支付',
  PRIMARY KEY (`cost_id`) USING BTREE,
  INDEX `FK_cost_house`(`house_id`) USING BTREE,
  CONSTRAINT `FK_cost_house` FOREIGN KEY (`house_id`) REFERENCES `house` (`house_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '费用信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cost
-- ----------------------------
INSERT INTO `cost` VALUES (1, 2, '2020-12-02', 3360, 1);

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
  `house_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `house_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '房屋编号',
  `area` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区域',
  `house_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋地址',
  `house_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋类型',
  `acreage` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋面积',
  `peroples` int(10) NULL DEFAULT NULL COMMENT '可容纳人数',
  `rent` int(10) NULL DEFAULT NULL COMMENT '房屋租金',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '房屋状态:1:已租、2:待租',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`house_id`) USING BTREE,
  INDEX `FK_house_user`(`user_id`) USING BTREE,
  CONSTRAINT `FK_house_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '房屋信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house
-- ----------------------------
INSERT INTO `house` VALUES (1, '0001', '广东省宝安区麻布新村', '105号13-1101', '1', '28.35平方', 3, 1800, 1, 3);
INSERT INTO `house` VALUES (2, '0002', '广东省宝安区麻布新村', '105号13-1102', '1', '44.2平方', 5, 3400, 1, 1);
INSERT INTO `house` VALUES (4, '0003', '广东省宝安区麻布新村', '105号13-1103', '1', '38.35平方', 3, 2800, 1, 1);

-- ----------------------------
-- Table structure for house_lease
-- ----------------------------
DROP TABLE IF EXISTS `house_lease`;
CREATE TABLE `house_lease`  (
  `house_id` int(11) NOT NULL COMMENT '房屋ID',
  `lease_id` int(11) NOT NULL COMMENT '租赁者ID',
  PRIMARY KEY (`house_id`, `lease_id`) USING BTREE,
  INDEX `FK_house_lease_lease`(`lease_id`) USING BTREE,
  CONSTRAINT `FK_house_lease_house` FOREIGN KEY (`house_id`) REFERENCES `house` (`house_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_house_lease_lease` FOREIGN KEY (`lease_id`) REFERENCES `lease` (`lease_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '房屋租赁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house_lease
-- ----------------------------
INSERT INTO `house_lease` VALUES (1, 1);
INSERT INTO `house_lease` VALUES (2, 1);

-- ----------------------------
-- Table structure for lease
-- ----------------------------
DROP TABLE IF EXISTS `lease`;
CREATE TABLE `lease`  (
  `lease_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租赁者姓名',
  `id_card` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租赁者身份证号码',
  `sex` tinyint(4) NOT NULL COMMENT '性别：1 男 2 女',
  `telphone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  `hometown` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租赁者籍贯',
  `status` tinyint(4) NOT NULL COMMENT '状态:1:租房中  2:未租房',
  `start_date` date NOT NULL COMMENT '租赁开始时间',
  `end_date` date NOT NULL COMMENT '租赁结束时间',
  `sign_date` date NOT NULL COMMENT '租赁签订时间',
  PRIMARY KEY (`lease_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '租赁者信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lease
-- ----------------------------
INSERT INTO `lease` VALUES (1, '周志刚', '430921199112205454', 1, '13265740591', '湖南', 1, '2015-10-01', '2025-01-01', '2020-12-02');

-- ----------------------------
-- Table structure for permission
-- ----------------------------

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int(11) NOT NULL,
  `permision_component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `permision_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `permision_parent_id` int(11) NULL DEFAULT NULL,
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `permission_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `permission_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
 
-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission`(permission_id,permission_zh,permission_name,permission_path,permision_icon,permision_component,permision_parent_id) VALUES (1, '首页', 'Admin', '/admin', 'el-icon-s-home', 'AdminIndex', 0);
INSERT INTO `permission`(permission_id,permission_zh,permission_name,permission_path,permision_icon,permision_component,permision_parent_id) VALUES (2, '运行情况', 'DashBoard', '/admin/dashboard', NULL, 'dashboard/Index', 1);
INSERT INTO `permission`(permission_id,permission_zh,permission_name,permission_path,permision_icon,permision_component,permision_parent_id) VALUES (3, '用户管理', 'User', '/admin', 'el-icon-user', 'AdminIndex', 0);
INSERT INTO `permission`(permission_id,permission_zh,permission_name,permission_path,permision_icon,permision_component,permision_parent_id) VALUES (4, '用户信息', 'Profile', '/admin/user/Profile', NULL, 'user/Profile', 3);
INSERT INTO `permission`(permission_id,permission_zh,permission_name,permission_path,permision_icon,permision_component,permision_parent_id) VALUES (5, '角色配置', 'Role', '/admin/user/Role', NULL, 'user/Role', 3);
INSERT INTO `permission`(permission_id,permission_zh,permission_name,permission_path,permision_icon,permision_component,permision_parent_id) VALUES (6, '测试菜单', 'ArticleManage', '/admin', 'el-icon-edit', 'AdminIndex', 0);
INSERT INTO `permission`(permission_id,permission_zh,permission_name,permission_path,permision_icon,permision_component,permision_parent_id) VALUES (7, '菜单列表1', 'ArticleList', '/admin/test/Test1', NULL, 'test/Test1', 6);
INSERT INTO `permission`(permission_id,permission_zh,permission_name,permission_path,permision_icon,permision_component,permision_parent_id) VALUES (8, '菜单列表2', 'ArticleEdit', '/admin/test/Test2', NULL, 'test/Test2', 6);


-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'svip');
INSERT INTO `role` VALUES (2, 'vip');
INSERT INTO `role` VALUES (3, 'p');
INSERT INTO `role` VALUES (4, 'admin');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `FKf8yllw1ecvwqy3ehyxawqa1qp`(`permission_id`) USING BTREE,
  CONSTRAINT `FKa6jx8n8xkesmjmv6jqug6bg68` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKf8yllw1ecvwqy3ehyxawqa1qp` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (1, 4);
INSERT INTO `role_permission` VALUES (1, 5);
INSERT INTO `role_permission` VALUES (1, 6);
INSERT INTO `role_permission` VALUES (1, 7);
INSERT INTO `role_permission` VALUES (1, 8);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (2, 2);

-- ----------------------------
-- Table structure for token_relation
-- ----------------------------
DROP TABLE IF EXISTS `token_relation`;
CREATE TABLE `token_relation`  (
  `relation_sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'token凭证',
  PRIMARY KEY (`relation_sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of token_relation
-- ----------------------------
INSERT INTO `token_relation` VALUES (3, 'Jack', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDY5NTkzNzAsInVzZXJuYW1lIjoiSmFjayJ9.m3OZ1w2YQLmI0cE9oK79_pZbXnON_-e5YMbZLPtdyrU');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL COMMENT '主键',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户',
  `telephone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `guid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '唯一码',
  `real_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123', 'Jack', '123', '123@163.com', NULL, NULL, NULL);
INSERT INTO `user` VALUES (2, '123', 'Rose', '1234', '1234@163.com', NULL, NULL, NULL);
INSERT INTO `user` VALUES (3, '123', 'Paul', '12345', '12345@163.com', NULL, NULL, NULL);
INSERT INTO `user` VALUES (4, '123', 'Jane', '123456', '123456@163.com', NULL, '简', '中国广东省深圳市盐田区');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKa68196081fvovjhkek5m97n3y`(`role_id`) USING BTREE,
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);

-- ----------------------------
-- Table structure for verification
-- ----------------------------
DROP TABLE IF EXISTS `verification`;
CREATE TABLE `verification`  (
  `verification_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `verification_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '验证码',
  `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态：1:未使用，2：已使用',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `fail_date` datetime(0) NULL DEFAULT NULL COMMENT '失效日期',
  PRIMARY KEY (`verification_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
