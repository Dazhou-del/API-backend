/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : dazhou

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 08/08/2023 22:19:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `accessKey` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'accessKey',
  `secretKey` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'secretKey',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1688794891779342339 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'dazhou', 'e5a12260d7e82158b44c621a6ce3dac5', 'dazhou', 'https://dazhou123.oss-cn-guangzhou.aliyuncs.com//2023/08/07/e8eebed6-0600-47bf-87e9-864748a6ff1f.jpg', 'ssasd', 'admin', '1320069b216976119ed7d085afe292f8', 'a46b4788628a39560d631561eef108e2', '2023-08-05 13:51:12', '2023-08-07 15:56:17', 0);
INSERT INTO `user` VALUES (1687715471329079297, 'xiaoran', 'e5a12260d7e82158b44c621a6ce3dac5', 'xiaoran', 'https://dazhou123.oss-cn-guangzhou.aliyuncs.com//2023/08/07/5e59fad8-41da-498f-a9c1-95f2509b3101.jpg', NULL, 'user', 'd9c0a1c7c1c3021c69ad39606dde3222', '3f3d2805dd4f8969d39db3081896f9ec', '2023-08-05 14:41:39', '2023-08-07 12:36:41', 0);
INSERT INTO `user` VALUES (1688777121687740418, 'xiaoming', 'e5a12260d7e82158b44c621a6ce3dac5', 'xiaoming', 'https://dazhou123.oss-cn-guangzhou.aliyuncs.com//2023/08/08/dce9cca7-5b84-4c1b-bf18-75c3f27dc2cc.jpg', NULL, 'user', 'c5a5a7fc0e74d91e20cd9a1ae1dd3aeb', 'ecc16b33359c5a9e1ae8d2b95a0f04a8', '2023-08-08 13:00:16', '2023-08-08 13:04:34', 0);
INSERT INTO `user` VALUES (1688780520743272449, 'lisi', 'e5a12260d7e82158b44c621a6ce3dac5', 'lisi', 'https://dazhou123.oss-cn-guangzhou.aliyuncs.com//2023/08/08/2f1510ae-de38-471b-b61b-a0ada2c888d4.jpg', NULL, 'user', '26021ac6825b734029adb671c5da92b2', 'b5d3af501f93d3ebe6c745b60f60206b', '2023-08-08 13:13:46', '2023-08-08 13:14:01', 0);
INSERT INTO `user` VALUES (1688794891779342338, 'zzzz', 'e5a12260d7e82158b44c621a6ce3dac5', 'zzzz', 'https://dazhou123.oss-cn-guangzhou.aliyuncs.com/mor.webp', NULL, 'user', 'c244ab8edab20dedc8282dc2248ae2b3', '6d0914b3394536901879ff071d703445', '2023-08-08 14:10:53', '2023-08-08 14:10:53', 0);

SET FOREIGN_KEY_CHECKS = 1;
