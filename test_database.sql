/*
 Navicat Premium Data Transfer

 Source Server         : navicat_test
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : test_database

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 13/06/2023 01:34:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for balance
-- ----------------------------
DROP TABLE IF EXISTS `balance`;
CREATE TABLE `balance`  (
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `fromway` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `value` decimal(32, 2) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of balance
-- ----------------------------
INSERT INTO `balance` VALUES ('白盏', '支付宝', 11049.50);
INSERT INTO `balance` VALUES ('白盏', '微信', 3.00);
INSERT INTO `balance` VALUES ('白盏', '卡1', 1113.00);
INSERT INTO `balance` VALUES ('白盏', 'test', -233.47);
INSERT INTO `balance` VALUES ('白盏', 'test2', 12.00);

-- ----------------------------
-- Table structure for income
-- ----------------------------
DROP TABLE IF EXISTS `income`;
CREATE TABLE `income`  (
  `id` int(32) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `value` decimal(32, 2) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  `fromway` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  `time` datetime(6) NULL DEFAULT NULL,
  `kind` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of income
-- ----------------------------
INSERT INTO `income` VALUES (00000000000000000000000000000001, 101.00, 'test', '支付宝', '2023-06-07 18:10:15.000000', '工资', '白盏');
INSERT INTO `income` VALUES (00000000000000000000000000000003, 101.50, 'test', '支付宝', '2023-06-10 00:42:17.000000', '投资', '白盏');
INSERT INTO `income` VALUES (00000000000000000000000000000004, 11000.00, 'test', '支付宝', '2023-06-14 00:00:00.000000', '工资', '白盏');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (00000000001, '管理员', '');
INSERT INTO `role` VALUES (00000000002, '普通成员', '用户默认的角色');
INSERT INTO `role` VALUES (00000000005, '测试角色', '1');

-- ----------------------------
-- Table structure for rolemenu
-- ----------------------------
DROP TABLE IF EXISTS `rolemenu`;
CREATE TABLE `rolemenu`  (
  `roleid` int NOT NULL,
  `menuid` int NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rolemenu
-- ----------------------------
INSERT INTO `rolemenu` VALUES (5, 1);
INSERT INTO `rolemenu` VALUES (5, 2);
INSERT INTO `rolemenu` VALUES (5, 3);
INSERT INTO `rolemenu` VALUES (5, 6);
INSERT INTO `rolemenu` VALUES (5, 7);
INSERT INTO `rolemenu` VALUES (5, 4);
INSERT INTO `rolemenu` VALUES (5, 9);
INSERT INTO `rolemenu` VALUES (5, 10);
INSERT INTO `rolemenu` VALUES (2, 1);
INSERT INTO `rolemenu` VALUES (2, 2);
INSERT INTO `rolemenu` VALUES (2, 3);
INSERT INTO `rolemenu` VALUES (2, 6);
INSERT INTO `rolemenu` VALUES (2, 7);
INSERT INTO `rolemenu` VALUES (2, 4);
INSERT INTO `rolemenu` VALUES (2, 8);
INSERT INTO `rolemenu` VALUES (2, 5);
INSERT INTO `rolemenu` VALUES (2, 11);
INSERT INTO `rolemenu` VALUES (1, 1);
INSERT INTO `rolemenu` VALUES (1, 2);
INSERT INTO `rolemenu` VALUES (1, 3);
INSERT INTO `rolemenu` VALUES (1, 6);
INSERT INTO `rolemenu` VALUES (1, 7);
INSERT INTO `rolemenu` VALUES (1, 4);
INSERT INTO `rolemenu` VALUES (1, 8);
INSERT INTO `rolemenu` VALUES (1, 9);
INSERT INTO `rolemenu` VALUES (1, 10);
INSERT INTO `rolemenu` VALUES (1, 5);
INSERT INTO `rolemenu` VALUES (1, 11);
INSERT INTO `rolemenu` VALUES (1, 12);
INSERT INTO `rolemenu` VALUES (1, 13);

-- ----------------------------
-- Table structure for spend
-- ----------------------------
DROP TABLE IF EXISTS `spend`;
CREATE TABLE `spend`  (
  `id` int(32) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `value` decimal(32, 2) UNSIGNED NOT NULL,
  `time` datetime(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  `kind` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  `fromway` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spend
-- ----------------------------
INSERT INTO `spend` VALUES (00000000000000000000000000000001, 100.00, '2023-06-06 19:49:01.061000', '买菜', '食物', '无敌暴龙战神', '支付宝');
INSERT INTO `spend` VALUES (00000000000000000000000000000016, 50.00, '2023-06-05 00:02:00.000000', '买菜', '生活', '白盏', '支付宝');
INSERT INTO `spend` VALUES (00000000000000000000000000000017, 120.00, '2023-06-06 00:02:00.000000', 'test', '食品', '白盏', '微信');
INSERT INTO `spend` VALUES (00000000000000000000000000000018, 213.12, '2023-06-06 00:00:00.000000', 'test', '教育', '白盏', 'test');
INSERT INTO `spend` VALUES (00000000000000000000000000000019, 20.35, '2023-06-08 00:00:00.000000', 'test', '娱乐', '白盏', 'test');
INSERT INTO `spend` VALUES (00000000000000000000000000000020, 214.00, '2023-06-07 00:00:00.000000', 'test', '娱乐', '白盏', '支付宝');
INSERT INTO `spend` VALUES (00000000000000000000000000000021, 1.00, '2023-06-10 14:52:05.000000', 'test', '医疗', '白盏', '支付宝');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(32) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '作为用户的唯一标识',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT '普通成员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5372675 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (00000000000000000000000005372659, '白盏', '123456', '管理员');
INSERT INTO `users` VALUES (00000000000000000000000005372666, '无敌暴龙战神', '123456', '普通成员');
INSERT INTO `users` VALUES (00000000000000000000000005372668, '恐怖暴龙战士', '123456', '普通成员');
INSERT INTO `users` VALUES (00000000000000000000000005372673, '小白盏', '123456', '普通成员');

SET FOREIGN_KEY_CHECKS = 1;
