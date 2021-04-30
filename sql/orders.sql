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

 Date: 30/04/2021 16:04:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderID` int(11) NOT NULL,
  `startPlaceID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `endPlaceID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goodsID` int(11) NULL DEFAULT NULL,
  `goodsNumber` int(11) NULL DEFAULT NULL,
  `time` date NULL DEFAULT NULL,
  PRIMARY KEY (`orderID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 'B023B149VY', 'B023B02350', 1, 10, '2021-04-03');
INSERT INTO `orders` VALUES (2, 'B023B149VY', 'B023B02GYJ', 2, 20, '2021-04-03');
INSERT INTO `orders` VALUES (3, 'B023B149VY', 'B023B02350', 2, 22, '2021-04-03');
INSERT INTO `orders` VALUES (4, 'B023B149VY', 'B023B02GYJ', 1, 12, '2021-04-03');
INSERT INTO `orders` VALUES (5, 'B023B149VY', 'B023B06GCX', 1, 41, '2021-04-03');
INSERT INTO `orders` VALUES (6, 'B023B149VY', 'B023B06GCX', 3, 11, '2021-04-03');
INSERT INTO `orders` VALUES (7, 'B023B149VY', 'B023B06P06', 3, 20, '2021-04-03');
INSERT INTO `orders` VALUES (8, 'B023B149VY', 'B023B06P06', 2, 20, '2021-04-03');
INSERT INTO `orders` VALUES (9, 'B023B149VY', 'B023B08WDR', 1, 20, '2021-04-03');
INSERT INTO `orders` VALUES (10, 'B023B149VY', 'B023B08WDR', 3, 20, '2021-04-03');
INSERT INTO `orders` VALUES (11, 'B023B149VY', 'B023B09GL5', 3, 10, '2021-04-03');
INSERT INTO `orders` VALUES (12, 'B023B149VY', 'B023B09GL5', 2, 30, '2021-04-03');
INSERT INTO `orders` VALUES (13, 'B023B149VY', 'B023B09GXZ', 1, 50, '2021-04-03');
INSERT INTO `orders` VALUES (14, 'B023B149VY', 'B023B09GXZ', 2, 10, '2021-04-03');
INSERT INTO `orders` VALUES (15, 'B023B149VY', 'B023B13L9M', 1, 80, '2021-04-03');
INSERT INTO `orders` VALUES (16, 'B023B149VY', 'B023B13L9M', 2, 40, '2021-04-03');
INSERT INTO `orders` VALUES (17, 'B023B149VY', 'B0FFF5UEF2', 2, 20, '2021-04-03');
INSERT INTO `orders` VALUES (18, 'B023B149VY', 'B0FFF5UEF2', 3, 30, '2021-04-03');
INSERT INTO `orders` VALUES (19, 'B023B149VY', 'B0FFFYUWRA', 3, 10, '2021-04-03');
INSERT INTO `orders` VALUES (20, 'B023B149VY', 'B0FFFYUWRA', 1, 40, '2021-04-03');

SET FOREIGN_KEY_CHECKS = 1;
