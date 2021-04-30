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

 Date: 30/04/2021 16:04:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `carID` int(11) NOT NULL AUTO_INCREMENT,
  `carType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `volume` double NULL DEFAULT NULL,
  `minVolume` double NULL DEFAULT NULL,
  `maxVolume` double NULL DEFAULT NULL,
  `height` double NULL DEFAULT NULL,
  `width` double NULL DEFAULT NULL,
  PRIMARY KEY (`carID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (1, '1', 20, 10, 40, 1, 1);
INSERT INTO `car` VALUES (2, '2', 50, 30, 80, 2, 2);
INSERT INTO `car` VALUES (3, '3', 100, 60, 140, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
