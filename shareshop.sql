/*
 Navicat Premium Data Transfer

 Source Server         : share_linux
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 112.74.165.55:3306
 Source Schema         : shareshop

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 25/03/2019 20:00:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coupon_info
-- ----------------------------
DROP TABLE IF EXISTS `coupon_info`;
CREATE TABLE `coupon_info`  (
  `coupon_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` tinyint(4) NOT NULL,
  `face_value` decimal(10, 2) NULL DEFAULT NULL,
  `use_condition` decimal(10, 2) NULL DEFAULT NULL,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `amount` int(11) NULL DEFAULT NULL,
  `remaining_quantity` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT 0,
  `status` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`coupon_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_info
-- ----------------------------
INSERT INTO `coupon_info` VALUES (1, 'aa', 0, 20.00, 100.00, '', '2019-03-14 17:19:48', '2019-03-19 17:19:53', 1000, 800, '2019-03-06 17:20:10', 1, 1);
INSERT INTO `coupon_info` VALUES (2, 'bb', 1, 20.00, 200.00, NULL, '2019-03-10 17:25:20', '2019-03-17 17:25:27', 500, NULL, '2019-03-13 17:28:55', 0, 1);
INSERT INTO `coupon_info` VALUES (3, 'ABC', 0, 2.00, 20.00, '', '2019-03-12 09:46:28', '2019-03-14 20:39:53', 1000, 1000, '2019-03-17 17:59:38', 3, -1);
INSERT INTO `coupon_info` VALUES (4, 'Big Sale', 0, 50.00, 199.00, '', '2019-03-14 17:19:48', '2019-03-19 17:19:53', 10, 800, '2019-03-21 14:53:54', 1, 1);

-- ----------------------------
-- Table structure for coupon_logs
-- ----------------------------
DROP TABLE IF EXISTS `coupon_logs`;
CREATE TABLE `coupon_logs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  `coupon_id` int(11) NULL DEFAULT NULL,
  `order_number` bigint(20) NULL DEFAULT NULL,
  `order_original_money` decimal(10, 2) NULL DEFAULT NULL,
  `coupon_money` decimal(10, 2) NULL DEFAULT NULL,
  `order_final_money` decimal(10, 2) NULL DEFAULT NULL,
  `full_money` decimal(10, 2) NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `order_number`(`order_number`) USING BTREE,
  INDEX `coupon_id`(`coupon_id`) USING BTREE,
  CONSTRAINT `coupon_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `coupon_logs_ibfk_3` FOREIGN KEY (`order_number`) REFERENCES `order_master` (`order_number`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `coupon_logs_ibfk_4` FOREIGN KEY (`coupon_id`) REFERENCES `coupon_info` (`coupon_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_logs
-- ----------------------------
INSERT INTO `coupon_logs` VALUES (1, 00000000001, 1, 7879988800001, 100.00, 20.00, 80.00, 100.00, '2019-03-13 17:31:30', 1);

-- ----------------------------
-- Table structure for coupon_receive
-- ----------------------------
DROP TABLE IF EXISTS `coupon_receive`;
CREATE TABLE `coupon_receive`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  `coupon_id` int(11) NULL DEFAULT NULL,
  `coupon_money` decimal(10, 2) NULL DEFAULT NULL,
  `full_money` decimal(10, 2) NULL DEFAULT NULL,
  `receive_time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `coupon_id`(`coupon_id`) USING BTREE,
  CONSTRAINT `coupon_receive_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `coupon_receive_ibfk_2` FOREIGN KEY (`coupon_id`) REFERENCES `coupon_info` (`coupon_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_receive
-- ----------------------------
INSERT INTO `coupon_receive` VALUES (1, 00000000001, 1, 20.00, 100.00, '2019-03-11 17:30:20', 1);

-- ----------------------------
-- Table structure for manager_category
-- ----------------------------
DROP TABLE IF EXISTS `manager_category`;
CREATE TABLE `manager_category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manager_category
-- ----------------------------
INSERT INTO `manager_category` VALUES (1, 11, 'admin', 'Manage commodity information');
INSERT INTO `manager_category` VALUES (4, 11, 'accaa', NULL);
INSERT INTO `manager_category` VALUES (7, 20, 'accaa', NULL);
INSERT INTO `manager_category` VALUES (8, 20, 'accaa', NULL);
INSERT INTO `manager_category` VALUES (9, 11, '第9个分类', '12121313hhhhhhhhhhhhhhhhhhhhhhh');

-- ----------------------------
-- Table structure for manager_info
-- ----------------------------
DROP TABLE IF EXISTS `manager_info`;
CREATE TABLE `manager_info`  (
  `manager_info_id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_card_type` tinyint(4) NOT NULL DEFAULT 1,
  `identity_card_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  `register_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`manager_info_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `manager_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manager_info
-- ----------------------------
INSERT INTO `manager_info` VALUES (00000000001, 5, 'Mike', 1, '3478374', '18076542387', '12@gmain.com', 'male', '1993-07-16 20:30:19', '2019-02-19 20:30:36');
INSERT INTO `manager_info` VALUES (00000000002, 6, 'Ma', 1, '3478374', '18076542387', '12@gmain.com', 'male', '1993-07-16 20:30:19', '2019-03-11 10:35:45');

-- ----------------------------
-- Table structure for order_cart
-- ----------------------------
DROP TABLE IF EXISTS `order_cart`;
CREATE TABLE `order_cart`  (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL,
  `product_specs_id` int(11) NULL DEFAULT NULL,
  `product_quantity` int(11) NULL DEFAULT NULL,
  `product_price` decimal(10, 2) NULL DEFAULT NULL,
  `add_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`cart_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `product_specs_id`(`product_specs_id`) USING BTREE,
  CONSTRAINT `order_cart_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_cart_ibfk_4` FOREIGN KEY (`product_specs_id`) REFERENCES `product_specs` (`product_specs_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_cart
-- ----------------------------
INSERT INTO `order_cart` VALUES (1, 1, 1, 3, 7.65, '2019-03-08 21:31:22');
INSERT INTO `order_cart` VALUES (2, 1, 2, 2, 7.65, '2019-03-09 21:31:36');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `order_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NULL DEFAULT NULL,
  `product_specs_id` int(11) NULL DEFAULT NULL,
  `poduct_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_quantity` int(11) NULL DEFAULT NULL,
  `product_price` decimal(10, 2) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`order_detail_id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `product_specs_id`(`product_specs_id`) USING BTREE,
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_master` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`product_specs_id`) REFERENCES `product_specs` (`product_specs_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, 1, 1, 'Fashion hat', 2, 7.65, 1);
INSERT INTO `order_detail` VALUES (2, 1, 2, 'Fashion hat', 4, 7.65, 1);
INSERT INTO `order_detail` VALUES (3, 2, 1, 'Fashion hat', 1, 7.65, 1);
INSERT INTO `order_detail` VALUES (4, 2, 2, 'Fashion hat', 3, 7.65, 1);

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` bigint(20) NOT NULL,
  `user_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `consignee_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `consignee_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `postal_code` int(11) NULL DEFAULT NULL,
  `state` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `first_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `second_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `payment_mode` tinyint(4) NULL DEFAULT NULL,
  `order_moeny` decimal(10, 2) NULL DEFAULT NULL,
  `discount_money` decimal(10, 2) NULL DEFAULT NULL,
  `shipping_money` decimal(10, 2) NULL DEFAULT NULL,
  `payment_money` decimal(10, 2) NULL DEFAULT NULL,
  `express_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `pay_time` datetime(0) NULL DEFAULT NULL,
  `shipping_time` datetime(0) NULL DEFAULT NULL,
  `receive_time` datetime(0) NULL DEFAULT NULL,
  `order_status` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `order_number`(`order_number`) USING BTREE,
  CONSTRAINT `order_master_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_master
-- ----------------------------
INSERT INTO `order_master` VALUES (1, 7879988800001, 00000000001, 'nameq', '13978652345', 111100, 'jaja', 'nei', 'fir', 'sen', NULL, 15.30, 0.00, 1.00, 16.30, NULL, NULL, '2019-03-02 17:38:00', NULL, NULL, 1);
INSERT INTO `order_master` VALUES (2, 7879988800002, 00000000003, 'bbb', '13678930872', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_master` VALUES (3, 7879988800876, 00000000003, 'ccc', '13087342876', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order_master` VALUES (4, 7879988800112, 00000000003, 'ddd', '13178651298', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for product_attribute_key
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute_key`;
CREATE TABLE `product_attribute_key`  (
  `key_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NULL DEFAULT NULL,
  `attr_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name_sort` tinyint(4) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`key_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_attribute_key
-- ----------------------------
INSERT INTO `product_attribute_key` VALUES (1, 1, 'color', 1, '2019-03-20 15:01:47', '2019-03-20 15:01:54', 1);
INSERT INTO `product_attribute_key` VALUES (2, 1, 'size', 1, '2019-03-20 15:03:28', '2019-03-20 15:03:32', 1);
INSERT INTO `product_attribute_key` VALUES (3, 2, 'weight', 1, '2019-03-21 15:19:56', NULL, 1);
INSERT INTO `product_attribute_key` VALUES (4, 2, 'source', 2, '2019-03-21 16:27:40', NULL, 1);

-- ----------------------------
-- Table structure for product_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute_value`;
CREATE TABLE `product_attribute_value`  (
  `value_id` int(11) NOT NULL AUTO_INCREMENT,
  `attr_key_id` int(11) NOT NULL,
  `attr_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value_sort` tinyint(4) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`value_id`) USING BTREE,
  INDEX `attr_key_id`(`attr_key_id`) USING BTREE,
  CONSTRAINT `product_attribute_value_ibfk_1` FOREIGN KEY (`attr_key_id`) REFERENCES `product_attribute_key` (`key_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_attribute_value
-- ----------------------------
INSERT INTO `product_attribute_value` VALUES (1, 1, 'red', 1, '2019-03-20 16:17:12', '2019-03-20 16:17:14', 1);
INSERT INTO `product_attribute_value` VALUES (2, 1, 'black', 2, '2019-03-20 16:17:30', '2019-03-20 16:17:33', 1);
INSERT INTO `product_attribute_value` VALUES (3, 1, 'green', 2, '2019-03-21 15:44:47', NULL, 1);

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `category_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT 0,
  `category_level` tinyint(4) UNSIGNED NULL DEFAULT NULL,
  `category_status` tinyint(4) NULL DEFAULT 1,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (0, 'all', NULL, 0, NULL, 1, NULL);
INSERT INTO `product_category` VALUES (1, 'brick2', '1', 3, 1, 1, NULL);
INSERT INTO `product_category` VALUES (2, 'jewelry', '2', 0, 1, 1, NULL);
INSERT INTO `product_category` VALUES (3, 'scarf', '3', 1, 2, 1, NULL);
INSERT INTO `product_category` VALUES (4, 'hat', '4', 1, 2, 1, NULL);
INSERT INTO `product_category` VALUES (5, 'diamond', '5', 3, 3, 1, NULL);
INSERT INTO `product_category` VALUES (6, 'brick', '6', 5, 3, 1, NULL);
INSERT INTO `product_category` VALUES (7, 'brick2', '7', 4, 1, 1, NULL);
INSERT INTO `product_category` VALUES (8, 'brick2', '8', 3, 1, 1, NULL);
INSERT INTO `product_category` VALUES (9, 'jewelry', '18', 3, 1, 0, NULL);
INSERT INTO `product_category` VALUES (10, 'brick2', '15', 4, 1, 1, NULL);

-- ----------------------------
-- Table structure for product_comment
-- ----------------------------
DROP TABLE IF EXISTS `product_comment`;
CREATE TABLE `product_comment`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NULL DEFAULT NULL,
  `order_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) UNSIGNED NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `second_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comment_time` datetime(0) NULL DEFAULT NULL,
  `audit_status` tinyint(4) UNSIGNED NULL DEFAULT 1,
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `product_comment_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `product_comment_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order_master` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `product_comment_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_comment
-- ----------------------------
INSERT INTO `product_comment` VALUES (1, 1, 1, 1, 'hat', 'good good', 'I like it ,perfect', '2019-03-02 17:33:52', 1);
INSERT INTO `product_comment` VALUES (2, 1, 1, 1, 'hat22', 'dd', 'fdfdff', '2019-03-02 17:34:45', 1);
INSERT INTO `product_comment` VALUES (3, 1, 1, 1, 'hfsj', 'ordinary', '', '2019-03-02 18:07:17', 1);
INSERT INTO `product_comment` VALUES (4, 1, 1, 1, 'hat', 'good good hahaha', 'very small', '2019-03-02 19:02:05', 1);
INSERT INTO `product_comment` VALUES (5, 1, 1, 1, 'hat', 'good good hahaha', '', '2019-03-02 19:02:42', 1);
INSERT INTO `product_comment` VALUES (6, 1, 1, 1, 'hat', 'good good', 'very small', '2019-03-07 17:21:55', 1);
INSERT INTO `product_comment` VALUES (7, 1, 1, 1, 'hat', 'good wonderful', NULL, '2019-03-07 17:54:31', 1);
INSERT INTO `product_comment` VALUES (8, 1, 1, 1, 'hat', 'good good', NULL, '2019-03-07 21:31:26', 1);

-- ----------------------------
-- Table structure for product_discount
-- ----------------------------
DROP TABLE IF EXISTS `product_discount`;
CREATE TABLE `product_discount`  (
  `discount_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `discount_rate` tinyint(4) NULL DEFAULT NULL,
  `seller_discount_rate` tinyint(4) NULL DEFAULT NULL,
  `yield_rate` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`discount_id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  CONSTRAINT `product_discount_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_discount
-- ----------------------------
INSERT INTO `product_discount` VALUES (1, 1, 10, 12, 5);
INSERT INTO `product_discount` VALUES (2, 3, 7, 8, 6);
INSERT INTO `product_discount` VALUES (3, 2, 8, 10, 5);
INSERT INTO `product_discount` VALUES (4, 4, 7, 11, 6);
INSERT INTO `product_discount` VALUES (5, 0, 10, 10, 10);

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `brand_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `one_category_id` int(11) NULL DEFAULT NULL,
  `two_category_id` int(11) NULL DEFAULT NULL,
  `three_category_id` int(11) NULL DEFAULT NULL,
  `main_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attribute_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publish_status` tinyint(4) NULL DEFAULT 0,
  `audit_status` tinyint(4) NULL DEFAULT 0,
  `use_coupon` tinyint(4) NULL DEFAULT NULL,
  `discount_rate` int(11) NULL DEFAULT 0,
  `production_date` datetime(0) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stock` int(11) NULL DEFAULT NULL,
  `input_time` datetime(0) NULL DEFAULT NULL,
  `modified_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `one_category_id`(`one_category_id`) USING BTREE,
  INDEX `two_category_id`(`two_category_id`) USING BTREE,
  INDEX `three_category_id`(`three_category_id`) USING BTREE,
  CONSTRAINT `product_info_ibfk_1` FOREIGN KEY (`one_category_id`) REFERENCES `product_category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `product_info_ibfk_2` FOREIGN KEY (`two_category_id`) REFERENCES `product_category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `product_info_ibfk_3` FOREIGN KEY (`three_category_id`) REFERENCES `product_category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, 'Fashion hat', 'cacuss', 1, 3, 5, NULL, NULL, 1, 1, NULL, 0, NULL, NULL, NULL, '2019-02-27 19:11:27', '2019-03-07 18:11:49');
INSERT INTO `product_info` VALUES (2, 'Fashion hat', 'cacuss', 1, 3, 5, NULL, NULL, 1, 1, NULL, 0, NULL, NULL, NULL, '2019-02-27 19:11:29', '2019-03-01 17:25:26');
INSERT INTO `product_info` VALUES (3, 'peaked hat', 'cacuss', 1, 4, 5, NULL, NULL, 1, 1, NULL, 0, NULL, NULL, NULL, '2019-02-27 19:11:29', '2019-03-01 21:55:55');
INSERT INTO `product_info` VALUES (4, 'peaked hat1', 'cacuss1', 1, 3, 4, NULL, NULL, 1, 1, NULL, 20, NULL, NULL, NULL, '2019-02-27 19:11:29', '2019-03-01 10:48:22');
INSERT INTO `product_info` VALUES (5, 'beauty hat', 'cacuss', 2, 3, 5, NULL, NULL, 1, 1, NULL, 0, NULL, NULL, NULL, '2019-03-07 18:07:59', '2019-03-07 18:09:15');

-- ----------------------------
-- Table structure for product_pic_info
-- ----------------------------
DROP TABLE IF EXISTS `product_pic_info`;
CREATE TABLE `product_pic_info`  (
  `product_pic_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `pic_desc` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_master` tinyint(4) NULL DEFAULT 0,
  `pic_status` tinyint(4) NULL DEFAULT 1,
  `pic_order` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`product_pic_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  CONSTRAINT `product_pic_info_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_specs
-- ----------------------------
DROP TABLE IF EXISTS `product_specs`;
CREATE TABLE `product_specs`  (
  `product_specs_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `specs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `specs_sort` tinyint(4) NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `average_cost` decimal(10, 2) NULL DEFAULT NULL,
  `original_price` decimal(10, 2) NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_stock` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`product_specs_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  CONSTRAINT `product_specs_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_specs
-- ----------------------------
INSERT INTO `product_specs` VALUES (1, 1, 'aa', '2345678', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `product_specs` VALUES (2, 2, 'bb', '4345666', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for return_record
-- ----------------------------
DROP TABLE IF EXISTS `return_record`;
CREATE TABLE `return_record`  (
  `return_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL,
  `order_id` int(11) NULL DEFAULT NULL,
  `order_detail_id` int(11) NOT NULL,
  `product_specs_id` int(11) NULL DEFAULT NULL,
  `return_type` tinyint(4) NULL DEFAULT NULL,
  `return_money` decimal(10, 2) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`return_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `order_detail_id`(`order_detail_id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `return_record_ibfk_3`(`product_specs_id`) USING BTREE,
  CONSTRAINT `return_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `return_record_ibfk_3` FOREIGN KEY (`product_specs_id`) REFERENCES `product_specs` (`product_specs_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `return_record_ibfk_4` FOREIGN KEY (`order_detail_id`) REFERENCES `order_detail` (`order_detail_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `return_record_ibfk_5` FOREIGN KEY (`order_id`) REFERENCES `order_master` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_addr
-- ----------------------------
DROP TABLE IF EXISTS `user_addr`;
CREATE TABLE `user_addr`  (
  `user_addr_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL,
  `postal_code` int(11) NULL DEFAULT NULL,
  `state` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `first_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `second_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_default` tinyint(4) NULL DEFAULT NULL,
  `modified_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_addr_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_addr_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_addr
-- ----------------------------
INSERT INTO `user_addr` VALUES (1, 1, 345673, 'New York', 'New York', 'aaaa', NULL, 1, '2019-03-08 21:15:46');
INSERT INTO `user_addr` VALUES (2, 2, 47384, 'California', 'San Francisco ', 'wwww', NULL, 0, '2019-03-14 21:18:48');
INSERT INTO `user_addr` VALUES (3, 3, 233323, 'New York', 'San', 'ccccc', 'cccccc', 1, '2019-03-25 00:00:00');

-- ----------------------------
-- Table structure for user_collect
-- ----------------------------
DROP TABLE IF EXISTS `user_collect`;
CREATE TABLE `user_collect`  (
  `collect_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NULL DEFAULT NULL,
  `product_id` int(11) NULL DEFAULT NULL,
  `collect_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`collect_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_collect_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_collect_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_collect
-- ----------------------------
INSERT INTO `user_collect` VALUES (1, 1, 1, '2019-03-08 18:04:55');
INSERT INTO `user_collect` VALUES (2, 1, 3, '2019-02-26 18:05:12');
INSERT INTO `user_collect` VALUES (3, 3, 1, '2019-03-07 18:05:26');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_info_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_card_type` tinyint(4) NOT NULL DEFAULT 1,
  `identity_card_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  `register_time` datetime(0) NULL DEFAULT NULL,
  `user_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `superior_id` int(11) NULL DEFAULT 0,
  `is_seller` tinyint(4) UNSIGNED NULL DEFAULT 0,
  `user_money` decimal(10, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`user_info_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE,
  UNIQUE INDEX `identity_card_no`(`identity_card_no`) USING BTREE,
  UNIQUE INDEX `phone_number`(`phone_number`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_danish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'Tom123', 1, '46829112', '13078332783', '323@gmai.com', '1', '2023-07-31 22:03:39', '2019-03-01 20:17:27', 00000000001, 3, 0, 0.00);
INSERT INTO `user_info` VALUES (2, 'Jack111', 0, '468293564378', '13078393765', '122@gmai.com', '0', '1995-10-12 20:16:59', '2019-02-26 20:29:37', 00000000002, 0, 1, 0.00);
INSERT INTO `user_info` VALUES (3, 'Bob123', 1, '468297438742', '13867236723', '4839@gmain.com', 'femal', '1995-10-12 20:16:59', '2019-03-03 20:30:52', 00000000003, 0, 0, 1000.00);
INSERT INTO `user_info` VALUES (4, 'Mike', 1, '23243434', '18735459332', '2231@qq.com', '1', '2019-02-23 00:00:00', '2019-03-20 00:00:00', 00000000004, 0, 0, 0.00);

-- ----------------------------
-- Table structure for user_login
-- ----------------------------
DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login`  (
  `user_id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_login
-- ----------------------------
INSERT INTO `user_login` VALUES (00000000001, 'admin', 'jq0ZrHhJKfhNp6cfMN4gSQ==', 1, 1);
INSERT INTO `user_login` VALUES (00000000002, 'hello2', 'hello2', 1, 2);
INSERT INTO `user_login` VALUES (00000000003, 'hello', 'hello', 1, 3);
INSERT INTO `user_login` VALUES (00000000004, 'hello1', 'hello1', 1, 1);
INSERT INTO `user_login` VALUES (00000000005, 'hello12', '12asa21', 1, 11);
INSERT INTO `user_login` VALUES (00000000006, 'hello112', '12asa21', 1, 20);

-- ----------------------------
-- Table structure for user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log`  (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_time` datetime(0) NOT NULL,
  `login_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `login_type` tinyint(4) NULL DEFAULT NULL,
  `user_id` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_login_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `withdraw_record`;
CREATE TABLE `withdraw_record`  (
  `withdraw_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `withdraw_money` decimal(10, 2) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`withdraw_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `withdraw_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of withdraw_record
-- ----------------------------
INSERT INTO `withdraw_record` VALUES (1, 00000000002, 1.00, '2019-03-05 20:38:41', 1);
INSERT INTO `withdraw_record` VALUES (2, 00000000003, 1.00, '2019-03-07 20:41:11', 1);
INSERT INTO `withdraw_record` VALUES (3, 00000000003, 5.00, '2019-03-05 16:32:12', 1);
INSERT INTO `withdraw_record` VALUES (4, 00000000002, 4.00, '2019-03-05 16:32:57', 1);
INSERT INTO `withdraw_record` VALUES (5, 00000000003, 1.00, '2019-03-07 18:19:57', 1);
INSERT INTO `withdraw_record` VALUES (6, 00000000003, 5.00, '2019-03-07 22:20:02', 1);

-- ----------------------------
-- Table structure for yield_detail
-- ----------------------------
DROP TABLE IF EXISTS `yield_detail`;
CREATE TABLE `yield_detail`  (
  `yield_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `order_id` int(11) NOT NULL,
  `yield_money` decimal(10, 2) NOT NULL,
  `purchaser_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`yield_id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `purchaser_id`(`purchaser_id`) USING BTREE,
  CONSTRAINT `yield_detail_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order_master` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `yield_detail_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `yield_detail_ibfk_4` FOREIGN KEY (`purchaser_id`) REFERENCES `user_login` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yield_detail
-- ----------------------------
INSERT INTO `yield_detail` VALUES (1, 00000000002, 2, 2.00, 00000000003, '2019-03-13 20:32:10', 1);
INSERT INTO `yield_detail` VALUES (2, 00000000002, 3, 3.00, 00000000003, '2019-03-03 20:35:36', 1);
INSERT INTO `yield_detail` VALUES (3, 00000000002, 4, 2.00, 00000000003, '2019-03-05 16:34:36', 1);

SET FOREIGN_KEY_CHECKS = 1;
