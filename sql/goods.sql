/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50706
 Source Host           : localhost:3306
 Source Schema         : logisticssystem

 Target Server Type    : MySQL
 Target Server Version : 50706
 File Encoding         : 65001

 Date: 30/04/2021 16:04:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goodsID` int(11) NOT NULL,
  `goodsName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `volume` double NULL DEFAULT NULL,
  PRIMARY KEY (`goodsID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '1', 1);
INSERT INTO `goods` VALUES (2, '2', 2);
INSERT INTO `goods` VALUES (3, '3', 3);

SET FOREIGN_KEY_CHECKS = 1;
