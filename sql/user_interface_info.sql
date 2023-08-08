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

 Date: 08/08/2023 22:19:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_interface_info
-- ----------------------------
DROP TABLE IF EXISTS `user_interface_info`;
CREATE TABLE `user_interface_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` bigint NOT NULL COMMENT '调用用户id',
  `interfaceInfoId` bigint NOT NULL COMMENT '接口id',
  `totalNum` int NOT NULL DEFAULT 0 COMMENT '总调用次数',
  `leftNum` int NOT NULL DEFAULT 0 COMMENT '剩余调用次数',
  `status` int NOT NULL DEFAULT 0 COMMENT '0-正常,1-禁用',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDeleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户调用接口关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_interface_info
-- ----------------------------
INSERT INTO `user_interface_info` VALUES (33, 1, 27, 2, 99997, 0, '2023-08-08 14:09:25', '2023-08-08 14:10:07', 0);
INSERT INTO `user_interface_info` VALUES (34, 1, 24, 3, 99996, 0, '2023-08-08 14:09:27', '2023-08-08 14:10:00', 0);
INSERT INTO `user_interface_info` VALUES (35, 1, 1, 4, 99995, 0, '2023-08-08 14:09:28', '2023-08-08 14:12:39', 0);
INSERT INTO `user_interface_info` VALUES (36, 1688794891779342338, 24, 1, 99998, 0, '2023-08-08 14:11:02', '2023-08-08 14:11:10', 0);
INSERT INTO `user_interface_info` VALUES (37, 1688794891779342338, 1, 1, 99998, 0, '2023-08-08 14:11:05', '2023-08-08 14:11:20', 0);

SET FOREIGN_KEY_CHECKS = 1;
