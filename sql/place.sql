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

 Date: 30/04/2021 16:04:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for place
-- ----------------------------
DROP TABLE IF EXISTS `place`;
CREATE TABLE `place`  (
  `uid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `lng` double NULL DEFAULT NULL,
  `lat` double NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of place
-- ----------------------------
INSERT INTO `place` VALUES ('B023B02350', '杭州汽车北站', 120.113291, 30.317022);
INSERT INTO `place` VALUES ('B023B02GYJ', '浙江大学玉泉校区', 120.122946, 30.263776);
INSERT INTO `place` VALUES ('B023B06GCX', '杭州汽车西站', 120.090936, 30.261012);
INSERT INTO `place` VALUES ('B023B06P06', '西溪国家湿地公园', 120.062723, 30.275824);
INSERT INTO `place` VALUES ('B023B08WDR', '杭州东站', 120.2126, 30.290851);
INSERT INTO `place` VALUES ('B023B09E1R', '浙江工业大学朝晖校区', 120.165978, 30.292937);
INSERT INTO `place` VALUES ('B023B09GL5', '杭州电子科技大学下沙校区', 120.343222, 30.314192);
INSERT INTO `place` VALUES ('B023B09GXZ', '杭州萧山国际机场', 120.432414, 30.234708);
INSERT INTO `place` VALUES ('B023B13L9M', '杭州西湖风景名胜区', 120.121281, 30.222718);
INSERT INTO `place` VALUES ('B023B149VY', '浙江工业大学屏峰校区', 120.041114, 30.228378);
INSERT INTO `place` VALUES ('B0FFF5UEF2', '浙江大学紫金港校区', 120.08083, 30.302959);
INSERT INTO `place` VALUES ('B0FFFYUWRA', '杭州南站', 120.295589, 30.17264);
INSERT INTO `place` VALUES ('B0FFJ86KIQ', '梦想小镇', 119.990063, 30.289961);

SET FOREIGN_KEY_CHECKS = 1;
