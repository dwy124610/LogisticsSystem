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

 Date: 30/04/2021 16:04:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for transport
-- ----------------------------
DROP TABLE IF EXISTS `transport`;
CREATE TABLE `transport`  (
  `transportID` int(11) NOT NULL,
  `startPlaceID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `endPlaceID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goodsID` int(11) NULL DEFAULT NULL,
  `goodsNumber` int(11) NULL DEFAULT NULL,
  `carNumber` int(11) NULL DEFAULT NULL,
  `carVolume` double NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`transportID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of transport
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
