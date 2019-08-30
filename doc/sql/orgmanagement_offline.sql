/*
 Navicat MySQL Data Transfer

 Source Server         : ucarinc
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 47.107.99.226:3306
 Source Schema         : orgmanagement_offline

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 13/08/2019 17:58:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_b_account
-- ----------------------------
DROP TABLE IF EXISTS `t_b_account`;
CREATE TABLE `t_b_account`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  `account_login` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `account_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `is_link_emp` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否关联员工:0.否 1.是 2.全部',
  `emp_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '关联用户主键',
  `account_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密保邮箱',
  `data_permission_id` int(10) UNSIGNED NOT NULL COMMENT '数据权限id',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除 1:是 0 :否',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `status` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '账号状态 1.正常 2.冻结 3.无效',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `AK_unique`(`account_login`, `emp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户账号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_account
-- ----------------------------
INSERT INTO `t_b_account` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, 10001001, '234@qerqwerqwerqwerq', 0, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '超级管理员');
INSERT INTO `t_b_account` VALUES (2, 'superadmin', 'e10adc3949ba59abbe56e057f20f883e', 1, 10001002, 'e3232@163.com', 2, 0, 10001001, '2019-08-05 15:36:53.000', 10001001, '2019-08-05 15:37:46.000', 1, NULL);
INSERT INTO `t_b_account` VALUES (3, 'login', 'e10adc3949ba59abbe56e057f20f883e', 1, 10001019, 'asbd.sdf@ucarinc.com', 1, 0, 0, '2019-08-06 10:24:47.000', 10001001, '2019-08-06 10:24:59.000', 1, NULL);
INSERT INTO `t_b_account` VALUES (4, '124444', '121444', 0, NULL, NULL, 1, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-10 15:41:07.000', 1, NULL);
INSERT INTO `t_b_account` VALUES (15, '124567', '654321', 1, 10001005, '135454@qq.com', 4, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (16, 'admin111', '123456', 1, 10001007, '12345@qq.com', 2, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (17, 'admin222', '12345', 1, 10001008, '1234@qq.com', 2, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (18, '12345465', '123454657', 1, 10001009, '12344@qq.com', 2, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (19, 'admin666', '123456', 1, 10001011, '12345@qq.com', 4, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (20, 'admin777', '123456', 1, 10001012, '12345@qq.com', 1, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (21, 'admin333', '123456', 1, 10001018, '12345@qq.com', 3, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (22, '1235667', '9996535e07258a7bbfd8b132435c5962', 1, 10001010, '132435@qq.com', 3, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (23, '121345678', '35bd8a3d46ef915b8bde02f419f6be9f', 1, 10001059, '1132414@qq.com', 0, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (24, '876', '', 0, 10004872, NULL, 0, 0, 0, '0000-00-00 00:00:00.000', 0, '0000-00-00 00:00:00.000', 1, NULL);
INSERT INTO `t_b_account` VALUES (25, 'admin778', 'c4ad74ebc7e9a2cfa9dbe407221b17fd', 1, 10001063, '12345@qq.com', 3, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (26, 'admin887', 'f2419dc616a273acf81596b25a5d2121', 1, 10001061, '12345@qq.com', 2, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (27, 'admin776', '9ab959bc38dfcaad0e06e1616f92f5d9', 1, 10001057, '1234@qq.com', 2, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (28, 'admin767', 'eae10f69d2cbb4d2be74fd16478ff99d', 1, 10001061, '12345@qq.com', 2, 0, 10001001, '2019-08-09 15:39:54.000', 10001001, '2019-08-09 15:39:54.000', 1, '');
INSERT INTO `t_b_account` VALUES (29, 'admin773', '7b22bee26408a454168ccaf2d2eb6294', 0, NULL, NULL, 2, 0, 10001001, '2019-08-09 15:42:13.000', 10001001, '2019-08-09 15:42:13.000', 1, '');
INSERT INTO `t_b_account` VALUES (31, 'wtfadmin', '343c36560e89716edf126208e67735cb', 1, 10001065, '751148393@qq.com', 0, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (32, 'gryadmin', '1a5d337a8fa3955a8a48c7a362cc85d3', 1, 10001054, '962246207@qq.com', 0, 0, 10001001, '2019-08-02 16:11:24.000', 10001001, '2019-08-02 16:11:24.000', 1, '');
INSERT INTO `t_b_account` VALUES (33, 'admin998', '9ab959bc38dfcaad0e06e1616f92f5d9', 1, 10001058, '13413413@qq.com', 2, 0, 10001001, '2019-08-10 16:47:03.000', 10001001, '2019-08-10 16:47:03.000', 1, '');
INSERT INTO `t_b_account` VALUES (34, 'admin737', 'e27d853190a3062bab83d97eff91bb0a', 1, 10001064, '962246207@qq.com', 3, 0, 10001001, '2019-08-10 16:51:02.000', 10001001, '2019-08-10 16:51:02.000', 1, '');
INSERT INTO `t_b_account` VALUES (35, 'gryadmin1', '1a5d337a8fa3955a8a48c7a362cc85d3', 1, 10001060, '213123123@qq.com', 1, 0, 10001001, '2019-08-10 16:55:16.000', 10001001, '2019-08-10 16:55:16.000', 1, '');
INSERT INTO `t_b_account` VALUES (36, 'gryadmin2', '1a5d337a8fa3955a8a48c7a362cc85d3', 1, 10001041, '213123123@qq.com', 1, 0, 10001001, '2019-08-10 16:56:50.000', 10001001, '2019-08-10 16:56:50.000', 1, '');
INSERT INTO `t_b_account` VALUES (37, 'admin123', '7b22bee26408a454168ccaf2d2eb6294', 1, 10001067, '962246207@qq.com', 2, 0, 10001001, '2019-08-10 17:00:59.000', 10001001, '2019-08-10 17:00:59.000', 1, '');
INSERT INTO `t_b_account` VALUES (40, 'admin446', '5e065cdcc904fa398b8b0db3aa268e9f', 0, NULL, '1435465@qq.com', 1, 0, 10001001, '2019-08-12 22:37:52.000', 10001001, '2019-08-12 22:37:52.000', 1, '');
INSERT INTO `t_b_account` VALUES (41, 'gry12345', '9ab959bc38dfcaad0e06e1616f92f5d9', 0, NULL, '12345@qq.com', 0, 0, 10001001, '2019-08-13 10:55:32.000', 10001001, '2019-08-13 10:55:32.000', 1, '');
INSERT INTO `t_b_account` VALUES (42, '131236', 'de60d7b38de42d98438fabf62962b455', 1, NULL, '123456@qq.com', 2, 0, 10001001, '2019-08-13 14:12:46.000', 10001001, '2019-08-13 14:12:46.000', 1, '');
INSERT INTO `t_b_account` VALUES (43, 'nopermission', 'e64b78fc3bc91bcbc7dc232ba8ec59e0', 1, NULL, '1@1.com', 0, 0, 10001001, '2019-08-13 15:41:11.000', 10001001, '2019-08-13 15:41:11.000', 1, '');

-- ----------------------------
-- Table structure for t_b_bussiness_line
-- ----------------------------
DROP TABLE IF EXISTS `t_b_bussiness_line`;
CREATE TABLE `t_b_bussiness_line`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bussiness_line_id` tinyint(3) UNSIGNED NOT NULL COMMENT '业务线id',
  `bussiness_line_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务线名称',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否被删除，已删除1，未删除0',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '新建人',
  `create_time` datetime(3) NOT NULL COMMENT '新建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_bussiness_line_id`(`bussiness_line_id`) USING BTREE,
  UNIQUE INDEX `uniq_bussiness_line_name`(`bussiness_line_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_bussiness_line
-- ----------------------------
INSERT INTO `t_b_bussiness_line` VALUES (1, 1, '买买车', 0, 1, '2019-08-02 17:00:50.000', 1, '2019-08-02 17:00:50.000', NULL);
INSERT INTO `t_b_bussiness_line` VALUES (2, 2, '闪贷', 0, 1, '2019-08-02 17:00:50.000', 1, '2019-08-02 17:00:50.000', NULL);
INSERT INTO `t_b_bussiness_line` VALUES (3, 3, '租车', 0, 1, '2019-08-02 17:00:50.000', 1, '2019-08-02 17:00:50.000', NULL);
INSERT INTO `t_b_bussiness_line` VALUES (4, 4, '专车', 0, 1, '2019-08-02 17:00:50.000', 1, '2019-08-02 17:00:50.000', NULL);
INSERT INTO `t_b_bussiness_line` VALUES (5, 5, '保险', 0, 1, '2019-08-02 17:00:50.000', 1, '2019-08-02 17:00:50.000', NULL);

-- ----------------------------
-- Table structure for t_b_city
-- ----------------------------
DROP TABLE IF EXISTS `t_b_city`;
CREATE TABLE `t_b_city`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `inter_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '国际代码',
  `province_inter_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份国际代码',
  `province_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '省份名称',
  `city_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市名称',
  `city_phonetic` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '城市拼音',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态，0为无效，1为有效',
  `modify_emp` bigint(20) NOT NULL DEFAULT 0 COMMENT '员工编号',
  `modify_time` datetime(3) NOT NULL COMMENT '更新时间',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除,1为删除，0为未删除',
  `create_emp` bigint(20) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `intl_id`(`inter_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_city
-- ----------------------------
INSERT INTO `t_b_city` VALUES (1, '123321', '123321', '北京市', '北京市', 'beijing', 1, 1, '2019-08-01 16:54:05.000', '2019-08-01 16:53:59.000', '测试用', 0, 1);

-- ----------------------------
-- Table structure for t_b_company
-- ----------------------------
DROP TABLE IF EXISTS `t_b_company`;
CREATE TABLE `t_b_company`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `co_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公司名称',
  `co_credit_code` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '统一社会信用代码',
  `co_type` tinyint(1) NULL DEFAULT NULL COMMENT '公司类型，1为有限责任公司，0为股份有限公司',
  `business_time` date NULL DEFAULT NULL COMMENT '营业期限',
  `co_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住所',
  `business_scope` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经营范围',
  `legal_representative` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '法人代表',
  `registered_capital` bigint(20) NULL DEFAULT NULL COMMENT '注册资本',
  `set_date` date NULL DEFAULT NULL COMMENT '成立日期',
  `registered_authority` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登记机关',
  `approval_date` date NULL DEFAULT NULL COMMENT '核准日期',
  `registered_status` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登记状态',
  `business_license` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '营业执照',
  `co_nature` tinyint(1) NULL DEFAULT NULL COMMENT '公司性质：0为一般纳税人，1为小规模纳税人',
  `registered_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册地址',
  `open_bank` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户银行',
  `co_account` bigint(20) NULL DEFAULT NULL COMMENT '公司账号',
  `registered_phone` bigint(12) NULL DEFAULT NULL COMMENT '注册电话',
  `co_mark` tinyint(1) NULL DEFAULT NULL COMMENT '总公司标志：0为总公司，1为子公司',
  `modify_emp` bigint(20) NOT NULL COMMENT '员工编号',
  `create_time` date NOT NULL COMMENT '创建时间',
  `modify_time` date NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1为删除，0为未删除',
  `create_emp` bigint(20) NOT NULL COMMENT '创建人',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '0为无效，1为有效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `co_name`(`co_name`) USING BTREE,
  UNIQUE INDEX `co_credit_code`(`co_credit_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_company
-- ----------------------------
INSERT INTO `t_b_company` VALUES (2, '租车', 'zuche', 1, '2019-08-14', '你好', '0', '罗靖', 2223, '2019-08-20', '你好', '2019-08-07', '0', NULL, 0, '合肥', '华润银行', 2231, 31231234, 0, 9099, '2019-08-07', '2019-08-28', '0', 0, 0, 1);
INSERT INTO `t_b_company` VALUES (3, '专车', 'zhuanche', 1, '2019-08-05', '疯奶', '3', '腾飞', 324, '2019-08-05', '你好', '2019-08-05', '1', NULL, 1, '厦门', NULL, NULL, NULL, 1, 0, '2019-08-05', '2019-08-22', '0', 0, 0, 1);
INSERT INTO `t_b_company` VALUES (4, '车闪贷', 'cheshandai', 1, '2019-08-05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, 0, '2019-08-28', '2019-08-22', '0', 0, 0, 1);
INSERT INTO `t_b_company` VALUES (5, '宝沃', 'baowo', 1, '2019-08-05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 1, 0, '2019-08-05', '2019-08-05', '1', 0, 0, 1);
INSERT INTO `t_b_company` VALUES (6, '买买车', 'maimaichemaimaiche', 0, '2019-08-05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'http://localhost:8081/orgmanagement/img/maimaichemaimaiche/test.png', 0, NULL, NULL, NULL, NULL, 0, 10001001, '2019-08-05', '2019-08-13', '1', 0, 0, 1);
INSERT INTO `t_b_company` VALUES (10, '腾讯', 'wuququqiwuqiwuqiwu', 0, NULL, '', '', '陈东', NULL, NULL, '', NULL, '', 'http://localhost:8081/orgmanagement/img/wuququqiwuqiwuqiwu/test.png', 1, '', '', NULL, NULL, 0, 10001001, '2019-08-09', '2019-08-13', '123', 0, 0, 1);
INSERT INTO `t_b_company` VALUES (13, '租出', 'jujujujujujujuj', NULL, NULL, '', '', '', NULL, NULL, '', NULL, '', 'http://localhost:8081/orgmanagement/img/jujujujujujujuj/QQ图片20190806181512.png', NULL, '', '', NULL, NULL, NULL, 10001001, '2019-08-13', '2019-08-13', '', 0, 10001001, 1);
INSERT INTO `t_b_company` VALUES (22, '陈东', 'kikikikikikikiki', 1, '2019-08-08', '厦门 ', '全国', '陈东', NULL, '2019-08-05', '厦门', '2019-08-13', '有效', '', 0, '厦门', '28819238192381231', 1188282828, NULL, 0, 10001001, '2019-08-13', '2019-08-13', '正好', 0, 10001001, 1);

-- ----------------------------
-- Table structure for t_b_county
-- ----------------------------
DROP TABLE IF EXISTS `t_b_county`;
CREATE TABLE `t_b_county`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `inter_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '国际代码',
  `province_inter_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份国际代码',
  `province_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '省份名称',
  `city_inter_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市国际代码',
  `city_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市名称',
  `county_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区/县名称',
  `county_phonetic` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '城市拼音',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态，0为无效，1为有效',
  `modify_emp` bigint(20) NOT NULL DEFAULT 0 COMMENT '员工编号',
  `modify_time` datetime(3) NOT NULL COMMENT '更新时间',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1为删除，0为未删除',
  `create_emp` bigint(20) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `intl_id`(`inter_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_county
-- ----------------------------
INSERT INTO `t_b_county` VALUES (4, '123322', '123321', '北京市', '123321', '北京市', '朝阳区', 'chaoyangqu', 1, 0, '2019-08-03 09:21:14.000', '2019-08-03 09:21:09.000', '测试用', 0, 0);

-- ----------------------------
-- Table structure for t_b_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_b_dept`;
CREATE TABLE `t_b_dept`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dept_id` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编号',
  `office_spot` smallint(3) UNSIGNED NOT NULL COMMENT '办公点标识',
  `dept_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `emp_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '负责人ID',
  `emp_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人姓名',
  `emp_phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `emp_email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `city_inter_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在城市id',
  `county_inter_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区/县id',
  `is_city` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '判断，1-城市 2-区县',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `lon` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  `lat` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `telephone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机号',
  `dept_level` tinyint(1) UNSIGNED NOT NULL COMMENT '部门级别，总部0，分公司1，管理部2，区域3，办公点4',
  `super_dept_id` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级部门',
  `dept_type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '部门类型，门店0，停车场1，交车中心2，维修厂3',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态，无效0，有效1',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否被删除，已删除1，未删除0',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '新建人',
  `create_time` datetime(3) NOT NULL COMMENT '新建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_dept_id`(`dept_id`) USING BTREE,
  UNIQUE INDEX `uniq_office_spot_super_dept_id`(`office_spot`, `super_dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_dept
-- ----------------------------
INSERT INTO `t_b_dept` VALUES (2, 'Z000001', 1, '总公司', 1, '张三', '15101010101', '1001@qq.com', '123321', '0', 1, '北京天安门', '120', '80', '10011234123', 0, '', 0, 1, 0, 0, '2019-08-03 14:32:56.000', 0, '2019-08-12 17:09:23.406', NULL);
INSERT INTO `t_b_dept` VALUES (4, 'F200000', 1, '北京公司', 1, '李四', '15512123223', '1002@qq.com', '123321', '', 1, '北京故宫', '120', '80', '100111111111', 1, 'Z000001', 0, 1, 0, 1, '2019-08-03 14:32:52.000', 1, '2019-08-12 16:38:10.287', 'searchAppNodeApi() 是我请求后端的api方法，拿到请求成功的回调函数的 response 参数。但是， response 里面包含很多我不需要的字段，我只需要其中的 dev_eui 字段。因此，循环 response.data 将其中每条的 dev_eui 重组成 {value:\'输入建议\'} 的格式并 push 进声明的新数组 devEuiArr。\n--------------');
INSERT INTO `t_b_dept` VALUES (10, 'F200001', 2, '天津公司', 2, '王五', '15552234211', '1003@qq.com', '123321', '0', 1, '天津高老庄', '120', '80', '10012222222', 1, 'Z000001', 0, 1, 0, 1, '2019-08-03 14:32:48.000', 1, '2019-08-10 11:08:05.978', NULL);
INSERT INTO `t_b_dept` VALUES (11, 'F000001', 3, '上海总公司', 3, '李老六', '10101010103', '1003@qq.com', '123321', '0', 1, '上海石家庄', '120', '80', '1001', 1, 'Z000001', 0, 0, 1, 1, '2019-08-03 14:32:46.000', 1, '2019-08-03 14:34:13.000', NULL);
INSERT INTO `t_b_dept` VALUES (12, 'G300000', 4, '北京门店1', 4, '林七', '15101010104', '1004@qq.com', '123321', '', 1, '北京朝阳区', '120', '80', '1000-2323232', 2, 'F200000', 0, 1, 1, 1, '2019-08-03 14:32:42.000', 1, '2019-08-13 17:32:17.863', NULL);
INSERT INTO `t_b_dept` VALUES (13, 'G300001', 5, '北京门店2', 5, '黄八', '15101010105', '1005@qq.com', '', '123322', 2, '北京思明区', '120', '80', '10014622634', 2, 'F200000', 0, 1, 0, 1, '2019-08-03 14:32:39.000', 1, '2019-08-12 16:30:53.861', NULL);
INSERT INTO `t_b_dept` VALUES (19, 'Gsalsdf', 123, '厦门翔安', NULL, '', '15532342345', '', '123321', '0', 1, '', '', '', '', 2, 'Z000001', NULL, 0, 0, 10001001, '2019-08-08 01:17:25.000', 10001001, '2019-08-10 11:07:48.431', '');
INSERT INTO `t_b_dept` VALUES (20, 'B123123', 111, '厦门特房办公点', NULL, '', '18850068143', '', '123321', '0', 1, '', '', '', '', 4, 'Z000001', NULL, 1, 0, 10001001, '2019-08-08 08:28:46.201', 10001001, '2019-08-08 08:28:46.201', '');
INSERT INTO `t_b_dept` VALUES (21, 'F123123', 111, '厦门特房分公司', NULL, '', '15512341234', '', '123321', '0', 1, '', '', '', '', 1, 'B123123', NULL, 1, 1, 10001001, '2019-08-08 08:29:31.452', 10001001, '2019-08-08 08:29:31.452', '');
INSERT INTO `t_b_dept` VALUES (22, 'G123456', 12, '上海管理部', NULL, '', '15523462346', '', '123321', '0', 1, '', '', '', '', 2, 'F000001', NULL, 0, 1, 10001001, '2019-08-09 14:36:55.000', 10001001, '2019-08-10 11:09:56.693', '');
INSERT INTO `t_b_dept` VALUES (27, 'G123678', 188, '测试tree更新', NULL, '', '15572383496', '', '123321', '', 1, '', '', '', '', 2, 'Z000001', NULL, 1, 0, 10001001, '2019-08-13 17:11:52.405', 10001001, '2019-08-13 17:11:52.405', '');

-- ----------------------------
-- Table structure for t_b_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_b_employee`;
CREATE TABLE `t_b_employee`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emp_id` bigint(20) NOT NULL,
  `emp_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `emp_sex` tinyint(1) NULL DEFAULT NULL,
  `emp_phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `emp_email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `emp_depart` char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_dimission` tinyint(1) NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  `create_emp` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(3) NULL DEFAULT NULL,
  `modify_emp` bigint(20) NULL DEFAULT NULL,
  `modify_time` datetime(3) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique`(`emp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_employee
-- ----------------------------
INSERT INTO `t_b_employee` VALUES (1, 10001001, '管理员不要删', 1, '15619382398', '23123122@qq.com', 'Z000001', 0, 0, 10001001, '2019-08-05 15:37:12.000', 10001001, '2019-08-09 08:53:38.000', '管理员');
INSERT INTO `t_b_employee` VALUES (2, 10001002, 'T员工', 1, '15623234949', '231231@qq.com', 'G300000', 0, 0, 110001001, '2019-08-02 15:37:12.000', NULL, NULL, NULL);
INSERT INTO `t_b_employee` VALUES (3, 10001003, 'T小元', 0, '15623498594', '123214@qweq', 'F200002', 0, 0, 10001001, '2019-08-02 15:44:37.000', NULL, NULL, NULL);
INSERT INTO `t_b_employee` VALUES (4, 10001004, 'Tzz', 0, '13254864785', '1132432@qq.com', 'Z000001', 0, 0, 10001001, '2019-08-03 10:40:00.000', NULL, '2019-08-09 14:33:56.000', NULL);
INSERT INTO `t_b_employee` VALUES (19, 10001019, 'T三', 1, '15623498594', NULL, 'F000001', 0, 0, 10001001, '2019-08-03 10:40:00.000', NULL, NULL, NULL);
INSERT INTO `t_b_employee` VALUES (20, 10001020, 'T小云', 1, '13854783748', '231212@qq.com', 'Z000001', 0, 0, 10001001, '2019-08-03 10:40:00.000', NULL, NULL, NULL);
INSERT INTO `t_b_employee` VALUES (28, 10001028, 'T小蛋', 0, '15638934758', '13413413@qq.com', 'G300000', 1, 0, 10001001, '2019-08-03 10:40:00.000', NULL, NULL, NULL);
INSERT INTO `t_b_employee` VALUES (41, 10001041, 'T黎明', 0, '15648579685', '7593385@qq.com', 'Z000001', 0, 0, 10001001, '2019-08-03 10:40:00.000', NULL, NULL, NULL);
INSERT INTO `t_b_employee` VALUES (42, 10001042, 'Tzz', 0, '15623498594', NULL, 'Z000001', 1, 0, 10001001, '2019-08-03 10:40:00.000', NULL, NULL, NULL);
INSERT INTO `t_b_employee` VALUES (56, 10001056, 'T阿瑟', 0, '13245873902', '213123123@qq.com', 'F200001', 0, 0, 10001001, '2019-08-05 12:44:43.000', NULL, NULL, NULL);
INSERT INTO `t_b_employee` VALUES (57, 10001057, 'Txxxx', 0, '15634234875', '123123@qq.com', 'F200002', 0, 0, 10001001, '2019-07-27 09:47:00.000', 10001001, '2019-07-27 09:47:00.000', '');
INSERT INTO `t_b_employee` VALUES (58, 10001058, 'Tasd', 1, '14539486859', '31232131@qq.com', 'F200000', 0, 0, 10001001, '2019-07-27 09:47:00.000', 10001001, '2019-07-27 09:47:00.000', '');
INSERT INTO `t_b_employee` VALUES (59, 10001059, 'T太', 0, '15632884589', '23123214@qq.com', 'Z000001', 0, 0, 10001001, '2019-07-27 09:47:00.000', 10001001, '2019-07-27 09:47:00.000', '');
INSERT INTO `t_b_employee` VALUES (60, 10001060, 'To.o', 0, '13238485748', '2312312@qq.com', 'Z000001', 0, 0, 10001001, '2019-07-27 09:47:00.000', 10001001, '2019-07-27 09:47:00.000', '');
INSERT INTO `t_b_employee` VALUES (61, 10001061, 'T李现', 0, '13668572093', '213123@qq.com', 'F200001', 0, 0, 10001001, '2019-07-27 09:47:00.000', 10001001, '2019-07-27 09:47:00.000', '');
INSERT INTO `t_b_employee` VALUES (63, 10001062, 'T小新', 0, '13254636475', '2334123@qq.com', 'F123123', 0, 0, 10001001, '2019-08-09 11:07:30.000', 10001001, '2019-08-09 11:07:30.000', '测试时间');
INSERT INTO `t_b_employee` VALUES (64, 10001063, 'T法师', 0, '13245463423', '123123@qq.com', 'F000001', 0, 0, 10001001, '2019-08-09 11:08:38.000', 10001001, '2019-08-09 11:24:20.000', '修改员工第二次');
INSERT INTO `t_b_employee` VALUES (65, 10001064, 'wtf', 1, '17720808072', '751148393@qq.com', 'F200001', 0, 0, 10001001, '2019-08-05 15:37:12.000', 10001001, '2019-08-09 08:53:38.000', '管理员');
INSERT INTO `t_b_employee` VALUES (66, 10001067, 'gry', 0, '14145515231', '962246207@qq.com', 'F200000', 0, 1, 10001001, '2019-08-09 11:08:38.000', 10001001, '2019-08-09 11:08:38.000', '管理员');
INSERT INTO `t_b_employee` VALUES (67, 10001066, 'T组织', 0, '13265747654', '', '', 0, 1, 1, '2019-08-13 09:42:23.000', 1, '2019-08-13 09:42:23.000', '');

-- ----------------------------
-- Table structure for t_b_login_history
-- ----------------------------
DROP TABLE IF EXISTS `t_b_login_history`;
CREATE TABLE `t_b_login_history`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_login` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `last_login_time` datetime(3) NOT NULL COMMENT '最后登录时间',
  `is_delete` tinyint(1) UNSIGNED NOT NULL COMMENT '是否删除 1:是 0:否',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQUE_ACCOUNT_lOGIN`(`account_login`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_login_history
-- ----------------------------
INSERT INTO `t_b_login_history` VALUES (1, 'admin', '2019-08-13 09:43:05.047', 0, 1, '2019-08-13 09:40:22.497', 2, '2019-08-13 09:43:05.047', NULL);
INSERT INTO `t_b_login_history` VALUES (5, 'superadmin', '2019-08-13 09:43:05.047', 0, 1, '2019-08-13 09:40:22.497', 2, '2019-08-13 09:43:05.047', NULL);
INSERT INTO `t_b_login_history` VALUES (6, 'nopermission', '2019-08-13 09:43:05.047', 0, 1, '2019-08-13 09:40:22.497', 2, '2019-08-13 09:43:05.047', NULL);

-- ----------------------------
-- Table structure for t_b_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `t_b_operate_history`;
CREATE TABLE `t_b_operate_history`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `emp_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '员工编号',
  `data_permission` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '数据权限类型',
  `status` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态',
  `operate_type` tinyint(1) UNSIGNED NOT NULL COMMENT '操作类型',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_operate_history
-- ----------------------------
INSERT INTO `t_b_operate_history` VALUES (6, 10001067, 2, 1, 0, 10001001, '2019-08-10 17:00:59.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (7, 10001067, 2, 1, 2, 10001001, '2019-08-12 08:22:17.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (8, 10001056, 2, 1, 3, 10001001, '2019-08-12 08:23:56.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (9, 10001056, 1, 1, 3, 10001001, '2019-08-12 08:30:48.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (10, 10001056, 1, 0, 4, 10001001, '2019-08-12 08:31:13.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (11, 10001056, 2, 0, 4, 10001001, '2019-08-12 08:31:39.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (12, 10001065, 0, 1, 1, 10001065, '2019-08-12 11:35:39.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (13, 10001001, 0, 1, 9, 10001001, '2019-08-12 17:59:18.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (14, 10001002, 2, 1, 3, 10001001, '2019-08-12 21:08:34.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (15, 10001002, 2, 0, 4, 10001001, '2019-08-12 21:08:52.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (16, 10001064, 3, 1, 10, 10001001, '2019-08-12 23:16:21.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (22, NULL, 0, 1, 0, 10001001, '2019-08-13 10:55:32.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (23, 10001062, 0, NULL, 10, 10001001, '2019-08-13 11:00:41.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (24, 10001062, 0, NULL, 10, 10001001, '2019-08-13 11:02:49.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (25, 10001028, 1, 1, 10, 10001001, '2019-08-13 14:11:41.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (26, NULL, 2, 1, 0, 10001001, '2019-08-13 14:12:46.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (28, 10001065, 0, 1, 1, 10001065, '2019-08-13 15:00:41.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (29, 10001042, NULL, NULL, 10, 10001001, '2019-08-13 15:00:43.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (30, 10001065, 0, 1, 1, 10001065, '2019-08-13 15:01:18.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (31, 10001042, NULL, NULL, 9, 10001001, '2019-08-13 15:05:31.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (32, 10001001, 0, 1, 1, 10001001, '2019-08-13 15:17:52.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (33, NULL, 0, 1, 0, 10001001, '2019-08-13 15:41:11.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (34, 10001001, 0, 1, 1, 10001001, '2019-08-13 16:23:58.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (35, 10001001, 0, 1, 1, 10001001, '2019-08-13 16:26:32.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (36, 10001001, 0, 1, 1, 10001001, '2019-08-13 16:30:04.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (37, 10001001, 0, 1, 1, 10001001, '2019-08-13 16:32:35.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (38, 10001001, 0, 1, 1, 10001001, '2019-08-13 16:37:31.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (39, 10001001, 0, 1, 1, 10001001, '2019-08-13 16:39:51.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (40, 10001001, 0, 1, 1, 10001001, '2019-08-13 16:40:00.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (41, 10001001, 0, 1, 1, 10001001, '2019-08-13 16:41:08.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (42, 10001001, 0, 1, 1, 10001001, '2019-08-13 17:12:39.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (43, 10001001, 0, 1, 1, 10001001, '2019-08-13 17:13:25.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (44, 10001001, 0, 1, 1, 10001001, '2019-08-13 17:16:50.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (45, 10001001, 0, 1, 0, 10001001, '2019-08-13 17:18:59.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (46, 10001001, 0, 1, 1, 10001001, '2019-08-13 17:21:26.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (47, 10001001, 0, 1, 0, 10001001, '2019-08-13 17:29:16.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (48, 10001001, 0, 1, 1, 10001001, '2019-08-13 17:38:05.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (49, 10001001, 0, 1, 1, 10001001, '2019-08-13 17:39:51.000', NULL);
INSERT INTO `t_b_operate_history` VALUES (50, 10001001, 0, 1, 1, 10001001, '2019-08-13 17:41:28.000', NULL);

-- ----------------------------
-- Table structure for t_b_province
-- ----------------------------
DROP TABLE IF EXISTS `t_b_province`;
CREATE TABLE `t_b_province`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `inter_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '国际代码',
  `province_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '省份名称',
  `province_phonetic` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '省份拼音',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态，0为无效，1为有效',
  `modify_emp` bigint(20) NOT NULL DEFAULT 0 COMMENT '员工编号',
  `modify_time` datetime(3) NOT NULL COMMENT '更新时间',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1为删除，0为未删除',
  `create_emp` bigint(20) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `intl_id`(`inter_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_province
-- ----------------------------
INSERT INTO `t_b_province` VALUES (6, '123321', '北京市', 'beijing', 1, 0, '2019-08-03 13:58:32.000', '2019-08-02 13:58:35.000', '测试用', 1, 0);
INSERT INTO `t_b_province` VALUES (7, '123333', '福建省', 'fujian', 0, 0, '2019-08-05 09:28:14.000', '2019-08-05 09:28:11.000', '测试用', 0, 0);

-- ----------------------------
-- Table structure for t_b_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_b_resource`;
CREATE TABLE `t_b_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `resource_id` bigint(20) UNSIGNED NOT NULL COMMENT '节点编号 1/xx/xx子节点递增2位',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '父级节点编号',
  `menu_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编号',
  `menu_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `permission_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源权限名称',
  `permission_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限编号格式 模块:操作',
  `resource_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口url',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除 1:是 0 :否',
  `resource_type` tinyint(1) UNSIGNED NOT NULL COMMENT '资源类型  1:菜单节点 2:按钮节点',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `AK_QUNIQUE`(`resource_id`, `resource_url`) USING BTREE,
  INDEX `Index_1`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_resource
-- ----------------------------
INSERT INTO `t_b_resource` VALUES (2, 1, NULL, 'comporgmanagement', '公司组织结构管理', '公司组织结构管理', '虚功能节点', '虚功能节点', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (3, 101, 1, 'department', '部门管理', '部门管理', '虚功能节点', 'department', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (4, 103, 1, 'account', '账号管理', '账号管理', '虚功能节点', 'account', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (6, 102, 1, 'employee', '员工管理', '员工管理', '虚功能节点', 'employee', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (7, 10301, 103, 'accountList', '账号管理', '新建账号', 'account:addAccount', '/account/addAccount', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (8, 104, 1, 'role', '角色管理', '角色管理', '虚功能节点', 'role', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (9, 10401, 104, 'roleList', '角色管理', '角色列表', 'role:getList', '/role/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (10, 10402, 104, 'roleApplyList', '角色申请', '角色申请', 'role:apply', '/role/apply', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (11, 10403, 104, 'roleApproveList', '角色申请审批', '角色申请审批', 'role:approve', '/role/approve', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (12, 10302, 103, 'accountList', '账号管理', '获取账号列表', 'account:getList', '/account/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (13, 10101, 101, 'departmentList', '部门管理', '获取部门列表', 'department:getList', '/department/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (14, 10201, 102, 'employeeList', '员工管理', '获取员工列表', 'employee:getList', '/employee/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (15, 105, 1, 'company', '公司管理', '公司管理', '虚功能节点', 'company', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (17, 10501, 105, 'companyList', '公司列表', '公司列表', 'company:getList', '/company/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (18, 106, 1, 'search', '查询', '查询', '虚功能节点', 'search', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (19, 10601, 106, 'searchDept', '部门查询', '部门查询', 'search:dept', '/search/department/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (20, 10602, 106, 'searchRole', '角色查询', '角色查询', 'search:role', '/search/role', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (21, 10603, 106, 'searchAccountDetail', '角色账号明细查询', '角色账号明细查询', 'search:accountDetail', '/account_detail', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (22, 10604, 106, 'searchPermissionDetail', '角色功能权限明细查询', '角色功能权限明细查询', 'search:permissionDetail', '/permission_detail', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (23, 10605, 106, 'searchAccountPermission', '账号功能权限明细查询', '账号功能权限明细查询', 'search:accountPermission', '/account_permission', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (24, 107, 1, 'area', '行政区划管理', '行政区划管理', '虚功能节点', 'area', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (25, 10701, 107, 'areaProvince', '省/市', '获取省/市列表', 'area:provinceList', '/province/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (26, 10702, 107, 'areaCity', '城市', '获取城市列表', 'area:cityList', '/city/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (27, 10703, 107, 'areaCounty', '区/县', '获取区/县列表', 'area:countyList', '/county/list', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (28, 108, 1, 'empAccount', '个人账号管理', '个人账号管理', '虚功能节点', 'emp_account', 0, 1, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (29, 10801, 108, 'empAccountPassReset', '密码重置', '密码重置', 'empAccount:passReset', '/pass_reset', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (30, 10802, 108, 'empAccountBaseInfo', '基本信息查看', '基本信息查看', 'empAccount:baseInfo', '/pass_reset', 0, 2, 1, '2019-08-02 17:06:06.000', 1, '2019-08-02 17:06:06.000', NULL);
INSERT INTO `t_b_resource` VALUES (32, 10404, 104, 'rolePermission', '角色权限管理', '角色权限管理', 'role:permission', '/role/management/permission', 0, 1, 1, '2019-08-13 14:04:57.000', 1, '2019-08-13 14:05:08.000', NULL);

-- ----------------------------
-- Table structure for t_b_role
-- ----------------------------
DROP TABLE IF EXISTS `t_b_role`;
CREATE TABLE `t_b_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `approve_account` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审批人账号',
  `approve_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '审批人员工编号',
  `approve_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批人姓名',
  `approve_department` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批人所属部门',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除 1:删除 0:未删除',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '角色状态默认为有效，不允许编辑，1有效，0无效',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_emp` bigint(20) NOT NULL COMMENT '新建人',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  `modify_emp` bigint(20) NOT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_ role_id`(`role_id`) USING BTREE,
  UNIQUE INDEX `idx_role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_b_role
-- ----------------------------
INSERT INTO `t_b_role` VALUES (1, 1, '超级管理员', '', 0, '李红', 'Z000001', '默认管理员', 0, 1, '2019-08-02 16:11:24', 1, '2019-08-02 16:11:24', 1, NULL);
INSERT INTO `t_b_role` VALUES (2, 2, '默认管理员1', 'gryadmin2', 10001041, 'T黎明', '总公司', NULL, 0, 1, '2019-08-02 16:11:24', 10001001, '2019-08-13 09:42:59', 10001001, NULL);
INSERT INTO `t_b_role` VALUES (3, 3, '默认管理员4', '111', 1, 'a', NULL, NULL, 0, 0, '2019-08-03 16:46:25', 10001001, '2019-08-03 16:46:59', 10001002, NULL);
INSERT INTO `t_b_role` VALUES (4, 4, '默认管理员12', '111', 1, 'a', NULL, NULL, 0, 0, '2019-08-03 16:46:27', 10001001, '2019-08-03 16:47:02', 10001001, NULL);
INSERT INTO `t_b_role` VALUES (5, 5, '普通管理员', '111', 1, 'a', NULL, NULL, 0, 1, '2019-08-10 10:20:48', 10001001, '2019-08-10 10:20:51', 10001002, NULL);
INSERT INTO `t_b_role` VALUES (6, 6, '财务部总监', 'admin773', 10001056, 'T阿瑟', '天津公司', '', 0, 1, '2019-08-10 00:00:00', 10001001, '2019-08-10 00:00:00', 10001001, '');
INSERT INTO `t_b_role` VALUES (7, 7, '财务部总监1', 'admin773', 10001056, 'T阿瑟', '天津公司', '', 0, 0, '2019-08-10 00:00:00', 10001002, '2019-08-10 00:00:00', 10001002, '');
INSERT INTO `t_b_role` VALUES (8, 8, '人事部总监', 'login', 10001019, 'T三', '上海总公司', '', 0, 1, '2019-08-10 00:00:00', 10001002, '2019-08-10 00:00:00', 10001002, '');
INSERT INTO `t_b_role` VALUES (9, 9, '科技部总监', 'admin767', 10001061, 'T李现', '天津公司', '', 0, 1, '2019-08-10 00:00:00', 10001001, '2019-08-10 00:00:00', 10001001, '');
INSERT INTO `t_b_role` VALUES (10, 10, 'CTOdalao', 'admin767', 10001061, 'T李现', '天津公司', '1', 0, 0, '2019-08-10 00:00:00', 10001002, '2019-08-10 00:00:00', 10001002, '');
INSERT INTO `t_b_role` VALUES (11, 11, '总裁', 'admin767', 10001061, 'T李现', '天津公司', '', 0, 1, '2019-08-10 00:00:00', 10001001, '2019-08-10 00:00:00', 10001002, '');
INSERT INTO `t_b_role` VALUES (12, 12, '总监', 'login', 10001019, 'T三', '上海总公司', '', 0, 0, '2019-08-10 12:17:37', 10001002, '2019-08-10 00:00:00', 10001001, '');
INSERT INTO `t_b_role` VALUES (13, 13, '普通员工', 'admin', 10001001, 'T管理员', '总公司', '', 0, 0, '2019-08-10 13:42:05', 10001002, '2019-08-10 00:00:00', 10001002, '');
INSERT INTO `t_b_role` VALUES (14, 14, '测试人员', 'login', 10001019, 'T三', '上海总公司', '', 0, 1, '2019-08-10 14:42:45', 10001001, '2019-08-10 00:00:00', 10001002, '');
INSERT INTO `t_b_role` VALUES (15, 15, 'few', 'login', 10001019, 'T三', '上海总公司', '', 0, 1, '2019-08-10 14:44:18', 10001002, '2019-08-10 00:00:00', 10001001, '');
INSERT INTO `t_b_role` VALUES (19, 16, 'asdad', 'admin111', 10001007, NULL, NULL, '', 0, 0, '2019-08-10 15:31:10', 10001001, '2019-08-10 00:00:00', 10001002, '');
INSERT INTO `t_b_role` VALUES (20, 17, '二哥如果', 'gryadmin2', 10001056, 'T阿瑟', '天津公司', '1', 0, 0, '2019-08-12 00:00:00', 10001002, '2019-08-12 00:00:00', 10001001, '');
INSERT INTO `t_b_role` VALUES (21, 18, '阿萨大大', 'gryadmin2', 10001041, 'T黎明', '总公司', '1', 0, 0, '2019-08-12 14:23:06', 1223, '2019-08-12 14:22:49', 113224, '');
INSERT INTO `t_b_role` VALUES (22, 19, '阿达萨顶顶', 'admin123', 10001067, 'gry', '北京公司', '1', 0, 0, '2019-08-12 14:32:28', 1223, '2019-08-12 14:32:10', 113224, '');
INSERT INTO `t_b_role` VALUES (23, 20, '啊大苏打', 'gryadmin2', 10001041, 'T黎明', '总公司', '1', 0, 0, '2019-08-12 14:50:54', 1223, '2019-08-12 14:50:41', 113224, '');
INSERT INTO `t_b_role` VALUES (24, 21, '阿三大啊', 'admin778', 10001063, 'T法师', '上海总公司', 'dad', 0, 0, '2019-08-12 15:01:19', 10001001, '2019-08-12 15:04:35', 10001001, '');
INSERT INTO `t_b_role` VALUES (25, 22, '啊大大', 'gryadmin2', 10001041, 'T黎明', '总公司', '1', 0, 0, '2019-08-12 16:06:13', 10001001, '2019-08-12 16:06:04', 10001001, '');
INSERT INTO `t_b_role` VALUES (26, 23, '普通工人', 'admin767', 10001061, 'T李现', '天津公司', '1', 0, 0, '2019-08-13 09:09:15', 10001001, '2019-08-13 09:08:41', 10001001, '');
INSERT INTO `t_b_role` VALUES (27, 24, '苏打绿', 'admin123', 10001067, 'gry', '北京公司', '苏打', 0, 1, '2019-08-13 09:39:45', 10001001, '2019-08-13 09:38:17', 10001001, '');

-- ----------------------------
-- Table structure for t_r_account_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_r_account_resource`;
CREATE TABLE `t_r_account_resource`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `account_login` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号id',
  `resource_id` bigint(20) UNSIGNED NOT NULL COMMENT '资源id',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除 1:删除 0:未删除',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQUE`(`account_login`, `resource_id`) USING BTREE COMMENT '账户+资源唯一约束'
) ENGINE = InnoDB AUTO_INCREMENT = 119 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_r_account_resource
-- ----------------------------
INSERT INTO `t_r_account_resource` VALUES (3, 'admin', 10802, 1, 1, '2019-08-08 13:40:22.000', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (4, 'admin', 10301, 1, 10001001, '2019-08-10 11:21:56.980', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (5, 'admin', 10302, 1, 10001001, '2019-08-10 11:21:56.981', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (8, 'admin', 10501, 1, 10001001, '2019-08-10 11:30:47.700', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (9, 'admin', 10101, 1, 10001001, '2019-08-10 14:20:50.503', 10001002, '2019-08-13 14:15:52.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (10, 'admin', 10201, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (11, 'admin', 10401, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (12, 'admin', 10402, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (13, 'admin', 10403, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (14, 'admin', 10601, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (15, 'admin', 10602, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (16, 'admin', 10603, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (17, 'admin', 10604, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (18, 'admin', 10605, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (19, 'admin', 10701, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (20, 'admin', 10702, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (21, 'admin', 10703, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (22, 'admin', 10801, 1, 10001001, '2019-08-10 14:20:50.503', 10001001, '2019-08-10 14:20:55.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (27, 'login', 10101, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (28, 'login', 10301, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (29, 'login', 10302, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (30, 'login', 10201, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (31, 'login', 10401, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (32, 'login', 10402, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (33, 'login', 10403, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (34, 'login', 10501, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (35, 'login', 10601, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (36, 'login', 10602, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (37, 'login', 10603, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (38, 'login', 10604, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (39, 'login', 10605, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (40, 'login', 10701, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (41, 'login', 10702, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (42, 'login', 10703, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (43, 'login', 10801, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (44, 'login', 10802, 1, 10001001, '2019-08-10 15:51:40.872', 10001001, '2019-08-10 15:51:46.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (45, 'admin123', 10101, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (46, 'admin123', 10301, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (47, 'admin123', 10302, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (48, 'admin123', 10201, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (49, 'admin123', 10401, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (50, 'admin123', 10402, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (51, 'admin123', 10403, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (52, 'admin123', 10501, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (53, 'admin123', 10601, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (54, 'admin123', 10602, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (55, 'admin123', 10603, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (56, 'admin123', 10604, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (57, 'admin123', 10605, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (58, 'admin123', 10701, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (59, 'admin123', 10702, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (60, 'admin123', 10703, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-12 12:18:09.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (61, 'admin123', 10801, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-13 08:46:22.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (62, 'admin123', 10802, 1, 10001001, '2019-08-12 12:12:02.347', 10001001, '2019-08-13 08:46:22.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (116, 'superadmin', 10101, 1, 10001001, '2019-08-13 14:17:10.708', 10001002, '2019-08-13 15:02:31.000', NULL);
INSERT INTO `t_r_account_resource` VALUES (117, 'superadmin', 10801, 0, 10001001, '2019-08-13 15:03:50.440', 10001001, '2019-08-13 15:03:50.440', NULL);
INSERT INTO `t_r_account_resource` VALUES (118, 'superadmin', 10802, 0, 10001001, '2019-08-13 15:03:50.440', 10001001, '2019-08-13 15:03:50.440', NULL);

-- ----------------------------
-- Table structure for t_r_account_role
-- ----------------------------
DROP TABLE IF EXISTS `t_r_account_role`;
CREATE TABLE `t_r_account_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `account_login` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号主键',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色主键',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除 1:是 0 :否',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQUE_account_login+Role_id`(`account_login`, `role_id`) USING BTREE,
  INDEX `Index_account_login`(`account_login`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账号角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_r_account_role
-- ----------------------------
INSERT INTO `t_r_account_role` VALUES (8, 'admin', 678, 1, 10001001, '2019-08-09 13:56:59.389', 10001001, '2019-08-09 15:20:12.000', NULL);
INSERT INTO `t_r_account_role` VALUES (9, 'admin', 3, 0, 10001001, '2019-08-09 13:57:17.868', 10001001, '2019-08-09 15:20:12.000', NULL);
INSERT INTO `t_r_account_role` VALUES (10, 'admin', 2, 0, 10001001, '2019-08-09 13:57:34.178', 10001001, '2019-08-09 15:20:12.000', NULL);
INSERT INTO `t_r_account_role` VALUES (28, 'admin', 5212, 0, 10001001, '2019-08-10 10:11:45.209', 10001001, '2019-08-10 10:13:04.000', NULL);
INSERT INTO `t_r_account_role` VALUES (29, 'admin', 5213, 1, 10001001, '2019-08-10 10:11:45.209', 10001001, '2019-08-10 10:12:23.000', NULL);
INSERT INTO `t_r_account_role` VALUES (31, 'admin', 8, 1, 10001001, '2019-08-10 10:59:10.828', 10001001, '2019-08-10 13:30:01.000', NULL);
INSERT INTO `t_r_account_role` VALUES (32, 'admin', 10, 1, 10001001, '2019-08-10 11:21:56.354', 10001001, '2019-08-10 13:30:01.000', NULL);
INSERT INTO `t_r_account_role` VALUES (33, 'admin', 5, 1, 10001001, '2019-08-10 11:30:47.657', 10001001, '2019-08-10 14:21:07.000', NULL);
INSERT INTO `t_r_account_role` VALUES (34, 'admin', 7, 0, 10001001, '2019-08-10 11:30:47.657', 10001001, '2019-08-10 11:30:47.657', NULL);
INSERT INTO `t_r_account_role` VALUES (36, 'admin123', 1, 0, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-13 08:46:22.000', NULL);
INSERT INTO `t_r_account_role` VALUES (37, 'admin123', 2, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (38, 'admin123', 5, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (39, 'admin123', 6, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (40, 'admin123', 8, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (41, 'admin123', 9, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (42, 'admin123', 11, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (43, 'admin123', 12, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (44, 'admin123', 13, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (45, 'admin123', 14, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (46, 'admin123', 15, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (47, 'admin123', 16, 1, 10001001, '2019-08-12 12:11:52.004', 10001001, '2019-08-12 12:17:43.000', NULL);
INSERT INTO `t_r_account_role` VALUES (60, 'admin737', 13, 1, 10001001, '2019-08-12 13:41:03.953', 10001001, '2019-08-12 13:41:10.000', NULL);
INSERT INTO `t_r_account_role` VALUES (61, 'admin737', 11, 0, 10001001, '2019-08-12 13:41:10.947', 10001001, '2019-08-12 13:41:10.947', NULL);
INSERT INTO `t_r_account_role` VALUES (62, 'admin767', 13, 0, 10001001, '2019-08-12 13:41:37.240', 10001001, '2019-08-12 13:41:37.240', NULL);
INSERT INTO `t_r_account_role` VALUES (63, 'admin767', 14, 0, 10001001, '2019-08-12 13:41:37.240', 10001001, '2019-08-12 13:41:37.240', NULL);
INSERT INTO `t_r_account_role` VALUES (65, 'admin446', 1, 1, 10001001, '2019-08-13 08:04:23.262', 10001001, '2019-08-13 08:05:01.000', NULL);
INSERT INTO `t_r_account_role` VALUES (66, '你好啊', 12, 0, 10001001, '2019-08-13 11:31:38.669', 10001001, '2019-08-13 11:31:38.669', NULL);
INSERT INTO `t_r_account_role` VALUES (67, '不好啊', 12, 1, 10001001, '2019-08-13 11:31:38.725', 10001001, '2019-08-13 11:31:38.725', NULL);
INSERT INTO `t_r_account_role` VALUES (68, 'admin123', 22, 0, 10001001, '2019-08-13 11:33:21.561', 10001001, '2019-08-13 11:33:21.561', NULL);
INSERT INTO `t_r_account_role` VALUES (69, 'admin998', 22, 0, 10001001, '2019-08-13 11:33:21.605', 10001001, '2019-08-13 11:33:21.605', NULL);
INSERT INTO `t_r_account_role` VALUES (70, 'admin737', 22, 0, 10001001, '2019-08-13 11:33:21.654', 10001001, '2019-08-13 11:33:21.654', NULL);
INSERT INTO `t_r_account_role` VALUES (73, 'admin', 1, 0, 10001001, '2019-08-13 13:36:51.000', 10001002, '2019-08-13 14:15:52.000', NULL);
INSERT INTO `t_r_account_role` VALUES (76, '876', 678, 1, 10001001, '2019-08-13 13:56:53.000', 10001001, '2019-08-13 13:56:57.000', NULL);
INSERT INTO `t_r_account_role` VALUES (78, 'admin123', 24, 1, 10001001, '2019-08-13 13:39:20.465', 10001001, '2019-08-13 13:39:20.465', NULL);
INSERT INTO `t_r_account_role` VALUES (81, 'superadmin', 1, 1, 10001001, '2019-08-13 13:36:51.000', 10001002, '2019-08-13 14:16:35.000', NULL);
INSERT INTO `t_r_account_role` VALUES (92, 'superadmin', 7788, 0, 121333, '2019-08-13 13:36:51.000', 213, '2019-08-13 13:36:51.000', NULL);

-- ----------------------------
-- Table structure for t_r_dept_bussiness_line
-- ----------------------------
DROP TABLE IF EXISTS `t_r_dept_bussiness_line`;
CREATE TABLE `t_r_dept_bussiness_line`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dept_id` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编号',
  `bussiness_line_id` tinyint(3) UNSIGNED NOT NULL COMMENT '业务线id',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否被删除，已删除1，未删除0',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '新建人',
  `create_time` datetime(3) NOT NULL COMMENT '新建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_dept_id_bussiness_line_id`(`dept_id`, `bussiness_line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_r_dept_bussiness_line
-- ----------------------------
INSERT INTO `t_r_dept_bussiness_line` VALUES (1, 'Z000001', 1, 0, 1, '2019-08-03 13:38:51.006', 1, '2019-08-03 13:38:51.006', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (2, 'Z000001', 2, 0, 1, '2019-08-03 13:38:51.006', 1, '2019-08-03 13:38:51.006', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (3, 'Z000001', 3, 0, 1, '2019-08-03 13:38:51.006', 1, '2019-08-03 13:38:51.006', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (4, 'Z000001', 4, 0, 1, '2019-08-03 13:38:51.006', 1, '2019-08-03 13:38:51.006', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (5, 'Z000001', 5, 0, 1, '2019-08-03 13:38:51.006', 1, '2019-08-03 13:38:51.006', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (6, 'F000001', 1, 1, 1, '2019-08-03 13:38:51.006', 1, '2019-08-03 13:38:51.006', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (7, 'F200001', 2, 0, 1, '2019-08-03 13:38:51.006', 1, '2019-08-03 13:38:51.006', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (8, 'F200001', 3, 0, 1, '2019-08-03 13:38:51.006', 1, '2019-08-03 13:38:51.006', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (16, 'F000002', 2, 0, 1, '2019-08-03 16:02:36.914', 1, '2019-08-03 16:02:36.914', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (17, 'F000002', 3, 0, 1, '2019-08-03 16:02:36.914', 1, '2019-08-03 16:02:36.914', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (21, 'F000002', 1, 0, 1, '2019-08-03 16:11:56.602', 1, '2019-08-03 16:11:56.602', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (22, 'Gsalsdf', 1, 0, 10001001, '2019-08-08 01:17:25.755', 10001001, '2019-08-08 01:17:25.755', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (23, 'Gsalsdf', 2, 0, 10001001, '2019-08-08 01:17:25.755', 10001001, '2019-08-08 01:17:25.755', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (24, 'Gsalsdf', 3, 0, 10001001, '2019-08-08 01:17:25.755', 10001001, '2019-08-08 01:17:25.755', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (25, 'Gsalsdf', 4, 0, 10001001, '2019-08-08 01:17:25.755', 10001001, '2019-08-08 01:17:25.755', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (26, 'Gsalsdf', 5, 0, 10001001, '2019-08-08 01:17:25.755', 10001001, '2019-08-08 01:17:25.755', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (27, 'B123123', 1, 0, 10001001, '2019-08-08 08:28:46.201', 10001001, '2019-08-08 08:28:46.201', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (28, 'B123123', 2, 0, 10001001, '2019-08-08 08:28:46.201', 10001001, '2019-08-08 08:28:46.201', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (29, 'B123123', 3, 0, 10001001, '2019-08-08 08:28:46.201', 10001001, '2019-08-08 08:28:46.201', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (30, 'B123123', 4, 0, 10001001, '2019-08-08 08:28:46.201', 10001001, '2019-08-08 08:28:46.201', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (31, 'B123123', 5, 0, 10001001, '2019-08-08 08:28:46.201', 10001001, '2019-08-08 08:28:46.201', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (32, 'F123123', 1, 1, 10001001, '2019-08-08 08:29:31.452', 10001001, '2019-08-08 08:29:31.452', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (33, 'F123123', 2, 1, 10001001, '2019-08-08 08:29:31.452', 10001001, '2019-08-08 08:29:31.452', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (34, 'F123123', 3, 1, 10001001, '2019-08-08 08:29:31.452', 10001001, '2019-08-08 08:29:31.452', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (35, 'F123123', 4, 1, 10001001, '2019-08-08 08:29:31.452', 10001001, '2019-08-08 08:29:31.452', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (36, 'F123123', 5, 1, 10001001, '2019-08-08 08:29:31.452', 10001001, '2019-08-08 08:29:31.452', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (37, 'G123456', 1, 0, 10001001, '2019-08-09 14:36:55.150', 10001001, '2019-08-09 14:36:55.150', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (38, 'G300000', 2, 1, 1, '2019-08-10 14:13:29.111', 1, '2019-08-10 14:13:29.111', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (39, 'F200000', 2, 0, 1, '2019-08-12 16:29:47.281', 1, '2019-08-12 16:29:47.281', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (40, 'F200000', 3, 0, 1, '2019-08-12 16:29:47.281', 1, '2019-08-12 16:29:47.281', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (41, 'G300001', 2, 0, 1, '2019-08-12 16:30:53.861', 1, '2019-08-12 16:30:53.861', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (42, 'G123678', 1, 0, 10001001, '2019-08-13 17:11:52.405', 10001001, '2019-08-13 17:11:52.405', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (43, 'G123678', 2, 0, 10001001, '2019-08-13 17:11:52.405', 10001001, '2019-08-13 17:11:52.405', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (44, 'G123678', 3, 0, 10001001, '2019-08-13 17:11:52.405', 10001001, '2019-08-13 17:11:52.405', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (45, 'G123678', 4, 0, 10001001, '2019-08-13 17:11:52.405', 10001001, '2019-08-13 17:11:52.405', NULL);
INSERT INTO `t_r_dept_bussiness_line` VALUES (46, 'G123678', 5, 0, 10001001, '2019-08-13 17:11:52.405', 10001001, '2019-08-13 17:11:52.405', NULL);

-- ----------------------------
-- Table structure for t_r_dept_company
-- ----------------------------
DROP TABLE IF EXISTS `t_r_dept_company`;
CREATE TABLE `t_r_dept_company`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dept_id` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编号',
  `co_credit_code` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '统一社会信用代码',
  `co_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公司名称',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否被删除，已删除1，未删除0',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '新建人',
  `create_time` datetime(3) NOT NULL COMMENT '新建时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_dept_id_co_credit_code`(`dept_id`, `co_credit_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_r_dept_company
-- ----------------------------
INSERT INTO `t_r_dept_company` VALUES (2, 'F200000', 'zhuanche', '专车', 0, 0, '2019-08-10 21:43:51.000', 0, '2019-08-10 21:43:51.000', NULL);
INSERT INTO `t_r_dept_company` VALUES (3, 'F200001', 'baowo', '宝沃', 0, 0, '2019-08-10 21:43:51.000', 0, '2019-08-10 21:43:51.000', NULL);
INSERT INTO `t_r_dept_company` VALUES (4, 'F200002', 'maimaiche', '买买车', 0, 0, '2019-08-10 21:43:51.000', 0, '2019-08-10 21:43:51.000', NULL);
INSERT INTO `t_r_dept_company` VALUES (5, 'G300000', 'cheshandai', '车闪贷', 1, 0, '2019-08-10 21:43:51.000', 0, '2019-08-10 21:43:51.000', NULL);
INSERT INTO `t_r_dept_company` VALUES (7, 'F200001', 'cheshandai', '车闪贷', 0, 0, '2019-08-10 21:43:51.000', 0, '2019-08-10 21:43:51.000', NULL);
INSERT INTO `t_r_dept_company` VALUES (20, 'G300001', 'zuche', '租车', 0, 10001000, '2019-08-12 16:48:54.951', 10001000, '2019-08-12 16:48:54.951', NULL);
INSERT INTO `t_r_dept_company` VALUES (21, 'G300001', 'zhuanche', '专车', 0, 10001000, '2019-08-12 16:48:54.951', 10001000, '2019-08-12 16:48:54.951', NULL);
INSERT INTO `t_r_dept_company` VALUES (22, 'G300001', 'cheshandai', '车闪贷', 0, 10001000, '2019-08-12 16:48:54.951', 10001000, '2019-08-12 16:48:54.951', NULL);
INSERT INTO `t_r_dept_company` VALUES (23, 'Z000001', 'baowo', '宝沃', 0, 10001000, '2019-08-13 15:31:11.777', 10001000, '2019-08-13 15:31:11.777', NULL);
INSERT INTO `t_r_dept_company` VALUES (24, 'Z000001', 'cheshandai', '车闪贷', 0, 10001000, '2019-08-13 15:31:11.777', 10001000, '2019-08-13 15:31:11.777', NULL);
INSERT INTO `t_r_dept_company` VALUES (25, 'Z000001', 'zhuanche', '专车', 0, 10001000, '2019-08-13 15:31:11.777', 10001000, '2019-08-13 15:31:11.777', NULL);
INSERT INTO `t_r_dept_company` VALUES (26, 'Z000001', 'zuche', '租车', 0, 10001000, '2019-08-13 15:31:11.777', 10001000, '2019-08-13 15:31:11.777', NULL);

-- ----------------------------
-- Table structure for t_r_role_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_r_role_apply`;
CREATE TABLE `t_r_role_apply`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_code` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色申请编号',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '申请角色id',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请角色名称',
  `role_approve_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色审批负责人姓名',
  `bussiness_line` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色支持业务线（多条业务线时以分号分隔）',
  `apply_account_login` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请人登录账号',
  `apply_emp_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '申请人员工编号',
  `apply_emp_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人员工姓名',
  `apply_dept` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人所属部门',
  `is_delete` tinyint(1) UNSIGNED NOT NULL COMMENT '是否删除 0：未删除；1：已删除',
  `operate_emp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `operate_time` datetime(3) NULL DEFAULT NULL COMMENT '操作时间',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态 0：已新建，1：待审批， 2：审批通过， 3：审批拒绝， 4：已删除，5：全部',
  `approve_account_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人 显示规则：登录账号（员工姓名）',
  `approve_time` datetime(3) NULL DEFAULT NULL COMMENT '审批时间',
  `modify_account_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人 显示规则：登录账号（姓名）',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间（也即申请时间）',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `modify_time` datetime(3) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_emp` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `approve_comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_apply_code`(`apply_code`) USING BTREE,
  INDEX `idx_account_login`(`apply_account_login`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_r_role_apply
-- ----------------------------
INSERT INTO `t_r_role_apply` VALUES (1, 'SQ2019080300001', 1, '超级管理员', '李红', '租车', '123412', 23424, '2234', '234', 1, '321', '2019-08-02 09:56:01.000', 0, '234234', '2019-08-06 09:56:06.000', '23423', '2019-08-06 09:56:13.000', 23423, '2019-08-03 09:56:18.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (3, 'SQ2019080300003', 2, '默认管理员', '李红', '租车', '337048', 23424, '2234', '234', 0, '李红', '2019-08-12 18:46:41.000', 3, '李红', '2019-08-12 18:46:41.000', '23423', '2019-08-06 09:56:13.000', 23423, '2019-08-03 09:56:13.000', 234234, '23423', 'fwafwafw');
INSERT INTO `t_r_role_apply` VALUES (4, '4', 3, '默认管理员', '李红', '租车', '123412', 999999, '2234', '234', 0, '324', '2019-08-03 09:56:01.000', 1, '234234', '2019-08-03 09:56:06.000', '23423', '2019-08-03 09:56:13.000', 23423, '2019-08-12 19:21:38.496', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (5, '5', 5, '默认管理员', '李红', '租车', '123412', 23424, '不告诉你', '234', 0, '李红', '2019-08-12 14:11:22.000', 3, '李红', '2019-08-12 14:11:22.000', '23423', '2019-08-03 09:56:13.000', 23423, '2019-08-03 09:56:13.000', 234234, '23423', '11111111111111');
INSERT INTO `t_r_role_apply` VALUES (6, '6', 6, '默认管理员', '李红', '租车', '123412', 23424, '2234', '买买车', 0, '324', '2019-08-06 09:56:01.000', 1, '234234', '2019-08-03 09:56:06.000', '23423', '2019-08-03 09:56:13.000', 23423, '2019-08-03 09:56:13.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (7, '7', 8, '默认管理员', '李红', '租车', '123412', 23424, '2234', 'hahah', 0, '324', '2019-08-06 09:56:01.000', 1, '234234', '2019-08-03 09:56:06.000', '23423', '2019-08-03 09:56:13.000', 23423, '2019-08-03 09:56:13.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (8, '8', 9, '默认管理员', '李红', '保险', '123412', 23424, '2234', '234', 0, '李红', '2019-08-12 13:48:32.000', 1, '李红', '2019-08-12 13:48:32.000', '23423', '2019-08-03 09:56:13.000', 23423, '2019-08-03 09:56:13.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (9, '9', 8, '默认管理员', '李红', '租车', '123412', 23424, '2234', '234', 0, '377', '2019-08-03 09:56:01.000', 2, '234234', '2019-08-03 09:56:06.000', '23423', '2019-08-03 09:56:13.000', 23423, '2019-08-03 09:56:13.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (19, '19', 6, '默认管理员', '李红', '租车', '123412', 23424, '2234', '234', 0, '777', '2019-08-03 09:56:01.000', 1, '234234', '2019-08-03 09:56:06.000', '23423', '2019-08-03 09:56:13.000', 23423, '2019-08-03 09:56:13.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (20, '20', 1, '默认管理员', '李红', '租车', '123412', 23424, '2234', '234', 0, NULL, '2019-08-12 19:25:40.000', 2, NULL, '2019-08-12 19:25:40.000', '23423', '2019-08-03 09:56:13.000', 23423, '2019-08-03 09:56:13.000', 234234, '发发我发', 'vvvvv');
INSERT INTO `t_r_role_apply` VALUES (21, '21', 9, '默认管理员', '李红', '租车', '123412', 23424, '2234', '234', 0, '866', '2019-08-03 09:56:01.000', 0, '234234', '2019-08-03 09:56:01.000', '23423', '2019-08-03 09:56:01.000', 23423, '2019-08-03 09:56:01.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (22, '3', 88, '默认管理员', '李红', '租车', '123412', 23424, '2234', '234', 0, '324', '2019-08-03 09:56:01.000', 1, '234234', '2019-08-03 09:56:01.000', '23423', '2019-08-03 09:56:01.000', 23423, '2019-08-03 09:56:01.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (23, '23', 7, '默认管理员', '李红', '租车', '123412', 23424, '2234', '234', 0, '李红', '2019-08-12 13:54:19.000', 1, '李红', '2019-08-12 13:54:19.000', '23423', '2019-08-03 09:56:01.000', 23423, '2019-08-03 09:56:01.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (24, '24', 8, '默认管理员', '李红', '租车', '123412', 23424, '2234', '234', 0, '324', '2019-08-03 09:56:01.000', 0, '234234', '2019-08-03 09:56:01.000', '23423', '2019-08-03 09:56:01.000', 23423, '2019-08-03 09:56:01.000', 234234, '23423', NULL);
INSERT INTO `t_r_role_apply` VALUES (25, '25', 12, '默认管理员', '李红', '租车', '123412', 23424, '2234', '234', 0, NULL, '2019-08-13 15:05:31.000', 2, NULL, '2019-08-13 15:05:31.000', 'admin(管理员不要删)', '2019-08-03 09:56:01.000', 23423, '2019-08-13 14:06:36.994', 10001001, '在vv地址DVD', '啊服务费');
INSERT INTO `t_r_role_apply` VALUES (67, 'SQ2019081200001', 7, '财务部总监1', 'T阿瑟', '买买车', '324252', 23424, '申请人账号（姓名）', '当前部门', 1, NULL, NULL, 0, NULL, NULL, NULL, '2019-08-12 09:50:44.064', 123123, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (68, 'SQ2019081200002', 22, '啊大大', 'T黎明', '买买车', 'admin', 10001001, 'admin(董事长不要删)', '总公司', 0, NULL, '2019-08-13 11:33:20.000', 2, NULL, '2019-08-13 11:33:20.000', '', '2019-08-12 22:38:35.776', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (69, 'SQ2019081200003', 21, '阿三大啊', 'T法师', '买买车,闪贷,专车', 'admin', 10001001, 'admin(管理员不要删)', '总公司', 0, 'T法师', '2019-08-13 11:38:10.000', 3, 'T法师', '2019-08-13 11:38:10.000', '', '2019-08-12 23:25:56.326', 10001001, NULL, NULL, NULL, '滚');
INSERT INTO `t_r_role_apply` VALUES (70, 'SQ2019081200004', 21, '阿三大啊', 'T法师', '买买车,闪贷,专车', 'admin', 10001001, 'admin(管理员不要删)', '总公司', 0, '管理员不要删', '2019-08-12 23:28:38.990', 1, NULL, NULL, '', '2019-08-12 23:29:58.741', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (72, 'SQ2019081300001', 24, '苏打绿', 'gry', '买买车,闪贷,租车', 'admin', 10001001, '管理员不要删', '总公司', 0, '管理员不要删', '2019-08-13 11:19:59.852', 1, NULL, NULL, '', '2019-08-13 11:20:19.823', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (73, 'SQ2019081300002', 24, '苏打绿', 'gry', '买买车,闪贷,租车', 'admin', 10001001, 'admin(管理员不要删)', '总公司', 0, NULL, '2019-08-13 13:39:18.000', 2, NULL, '2019-08-13 13:39:18.000', '', '2019-08-13 13:38:15.969', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (74, 'SQ2019081300003', 24, '苏打绿', 'gry', '买买车,闪贷,租车', 'admin', 10001001, 'admin(管理员不要删)', '总公司', 0, NULL, '2019-08-13 13:39:22.000', 2, NULL, '2019-08-13 13:39:22.000', '', '2019-08-13 13:38:47.848', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (75, 'SQ2019081300004', 24, '苏打绿', 'gry', '买买车,闪贷,租车', 'admin', 10001001, 'admin(管理员不要删)', '总公司', 0, NULL, '2019-08-13 13:39:28.000', 2, NULL, '2019-08-13 13:39:28.000', '', '2019-08-13 13:39:00.002', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (76, 'SQ2019081300005', 24, '苏打绿', 'gry', '买买车,闪贷,租车', 'admin', 10001001, 'admin(管理员不要删)', '总公司', 0, NULL, '2019-08-13 13:47:54.000', 2, NULL, '2019-08-13 13:47:54.000', '', '2019-08-13 13:40:05.456', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (77, 'SQ2019081300006', 24, '苏打绿', 'gry', '买买车,闪贷,租车', 'admin', 10001001, 'admin(管理员不要删)', '总公司', 0, NULL, '2019-08-13 13:43:31.000', 2, NULL, '2019-08-13 13:43:31.000', '', '2019-08-13 13:42:55.089', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (78, 'SQ2019081300007', 24, '苏打绿', 'gry', '买买车,闪贷,租车', 'admin', 10001001, 'admin(管理员不要删)', '总公司', 0, NULL, '2019-08-13 14:15:43.000', 2, NULL, '2019-08-13 14:15:43.000', '', '2019-08-13 14:15:22.451', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (79, 'SQ2019081300008', 20, '啊大苏打', 'T黎明', '买买车,闪贷', 'admin', 10001001, '管理员不要删', '总公司', 0, '管理员不要删', '2019-08-13 15:50:01.549', 0, NULL, NULL, '', '2019-08-13 15:50:34.566', 10001001, NULL, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (80, 'SQ2019081300009', 23, '普通工人', 'T李现', '买买车,闪贷', 'admin', 10001001, '管理员不要删', '总公司', 0, '管理员不要删', '2019-08-13 16:58:14.433', 0, NULL, NULL, 'admin(管理员不要删)', '2019-08-13 16:52:50.553', 10001001, '2019-08-13 16:58:16.002', 10001001, NULL, NULL);
INSERT INTO `t_r_role_apply` VALUES (81, 'SQ2019081300010', 24, '苏打绿', 'gry', '买买车,闪贷', 'admin', 10001001, '管理员不要删', '总公司', 0, '管理员不要删', '2019-08-13 17:19:08.066', 0, NULL, NULL, 'admin(管理员不要删)', '2019-08-13 17:19:47.988', 10001001, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_r_role_apply_account
-- ----------------------------
DROP TABLE IF EXISTS `t_r_role_apply_account`;
CREATE TABLE `t_r_role_apply_account`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_code` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色申请编号',
  `apply_account` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请账号',
  `emp_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联员工姓名\r\n            ',
  `emp_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '关联员工编号',
  `emp_dept` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联员工所属部门',
  `operate_type` tinyint(1) UNSIGNED NOT NULL COMMENT '操作类型 规则  0 ：移除；1：添加',
  `is_delete` tinyint(1) UNSIGNED NOT NULL COMMENT '是否删除 0：未删除；1：已删除',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `modify_time` datetime(3) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_emp` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_apply_code`(`apply_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 145 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色申请账号表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_r_role_apply_account
-- ----------------------------
INSERT INTO `t_r_role_apply_account` VALUES (1, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 16:41:47.707', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (2, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 16:41:47.807', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (3, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 16:43:09.505', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (4, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 16:43:09.592', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (5, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 16:45:17.043', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (6, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 16:45:17.107', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (7, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 16:46:59.099', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (8, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 16:46:59.207', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (9, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 16:48:52.674', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (10, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 16:48:52.764', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (11, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 16:59:08.840', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (12, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 16:59:08.945', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (13, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 17:01:54.811', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (14, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 17:01:54.901', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (15, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 17:05:56.288', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (16, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 17:05:56.360', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (17, 'SQ201908080', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-08 17:06:54.233', 123123, '2019-08-05 15:37:46.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (18, 'SQ201908080', 'admin', 'T管理员', 10001001, '总公司', 1, 0, '2019-08-08 17:06:54.330', 123123, '2019-08-02 16:11:24.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (112, 'SQ2019081200001', 'admin123', 'gry', 10001067, '总公司', 1, 0, '2019-08-12 09:40:28.589', 123123, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (113, 'SQ2019081200001', 'gryadmin1', 'To.o', 10001060, '总公司', 1, 0, '2019-08-12 09:40:28.684', 123123, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (114, 'SQ2019081200001', 'admin773', 'T阿瑟', 10001056, '天津公司', 1, 0, '2019-08-12 09:40:28.740', 123123, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (115, 'SQ2019081200002', 'admin123', 'gry', 10001067, '总公司', 1, 0, '2019-08-12 09:41:22.796', 123123, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (116, 'SQ2019081200003', 'gryadmin2', 'T黎明', 10001041, '总公司', 1, 0, '2019-08-12 09:45:40.057', 123123, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (117, 'SQ2019081200001', 'admin123', 'gry', 10001067, '总公司', 1, 0, '2019-08-12 09:50:56.814', 123123, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (118, '20', 'admin123', 'gay ', NULL, NULL, 0, 0, '2019-08-12 14:43:35.000', 0, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (119, '20', 'superadmin', 'gay ', NULL, NULL, 1, 0, '2019-08-12 14:22:37.000', 0, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (120, '20', 'admin', 'xx', NULL, NULL, 0, 0, '2019-08-12 14:22:40.000', 0, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (121, '25', '你好啊', NULL, NULL, NULL, 1, 0, '2019-08-08 19:26:56.000', 0, '2019-08-13 14:06:37.328', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (124, '25', '不好啊', NULL, NULL, NULL, 0, 0, '2019-08-12 19:38:34.000', 0, '2019-08-13 14:06:37.391', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (125, 'SQ2019081200002', 'admin998', 'Tasd', 10001058, '北京公司', 1, 0, '2019-08-12 22:38:36.301', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (126, 'SQ2019081200002', 'admin737', 'wtf', 10001064, '天津公司', 1, 0, '2019-08-12 22:38:36.380', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (127, 'SQ2019081200003', 'admin123', 'gry', 10001067, '北京公司', 1, 0, '2019-08-12 23:25:56.810', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (128, 'SQ2019081200003', 'gryadmin2', 'T黎明', 10001041, '总公司', 1, 0, '2019-08-12 23:25:56.884', 10001001, '2019-08-13 13:46:59.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (129, 'SQ2019081200003', 'gryadmin1', 'To.o', 10001060, '总公司', 1, 0, '2019-08-12 23:25:56.939', 10001001, '2019-08-10 13:47:14.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (130, 'SQ2019081200004', 'admin778', 'T法师', 10001063, '上海总公司', 1, 0, '2019-08-12 23:29:59.161', 10001001, '2019-08-12 13:47:26.000', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (131, 'SQ2019081200004', '121345678', 'T太', 10001059, '总公司', 1, 0, '2019-08-12 23:30:00.232', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (132, 'SQ2019081300001', 'admin123', 'gry', 10001067, '北京公司', 1, 0, '2019-08-13 11:20:19.898', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (133, 'SQ2019081300002', 'admin123', 'gry', 10001067, '北京公司', 1, 0, '2019-08-13 13:38:16.397', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (134, 'SQ2019081300003', 'admin123', 'gry', 10001067, '北京公司', 1, 0, '2019-08-13 13:38:47.918', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (135, 'SQ2019081300004', 'admin123', 'gry', 10001067, '北京公司', 1, 0, '2019-08-13 13:39:00.375', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (136, 'SQ2019081300005', 'gryadmin2', 'T黎明', 10001041, '总公司', 0, 0, '2019-08-13 13:40:05.834', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (137, 'SQ2019081300006', 'admin123', 'gry', 10001067, '北京公司', 0, 0, '2019-08-13 13:42:55.458', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (138, 'SQ2019081300007', 'admin123', 'gry', 10001067, '北京公司', 1, 0, '2019-08-13 14:15:22.892', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (139, 'SQ2019081300008', 'gryadmin1', 'To.o', 10001060, '总公司', 1, 0, '2019-08-13 15:50:34.706', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (140, 'SQ2019081300008', 'gryadmin2', 'T黎明', 10001041, '总公司', 1, 0, '2019-08-13 15:50:34.772', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (141, 'SQ2019081300009', 'admin998', 'Tasd', 10001058, '北京公司', 1, 0, '2019-08-13 16:52:50.667', 10001001, '2019-08-13 16:58:16.020', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (142, 'SQ2019081300009', 'admin737', 'wtf', 10001064, '天津公司', 1, 0, '2019-08-13 16:52:50.728', 10001001, '2019-08-13 16:58:16.083', 10001001, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (143, 'SQ2019081300010', '121345678', 'T太', 10001059, '总公司', 1, 0, '2019-08-13 17:19:48.353', 10001001, NULL, NULL, NULL);
INSERT INTO `t_r_role_apply_account` VALUES (144, 'SQ2019081300010', 'superadmin', 'T员工', 10001002, '北京门店1', 1, 0, '2019-08-13 17:19:48.416', 10001001, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_r_role_line
-- ----------------------------
DROP TABLE IF EXISTS `t_r_role_line`;
CREATE TABLE `t_r_role_line`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `bussiness_line_id` tinyint(3) UNSIGNED NOT NULL COMMENT '业务线id',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除 1:删除 0:未删除',
  `create_emp` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_emp` bigint(20) NOT NULL COMMENT '修改人',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_id`(`role_id`, `bussiness_line_id`) USING BTREE,
  UNIQUE INDEX `role_id_2`(`role_id`, `bussiness_line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色业务线表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_r_role_line
-- ----------------------------
INSERT INTO `t_r_role_line` VALUES (0, 9, 5, 1, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (1, 10, 1, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (2, 10, 2, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (3, 10, 3, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (4, 10, 4, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (5, 10, 5, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (16, 11, 2, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (17, 12, 1, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (18, 12, 2, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (19, 7, 1, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (20, 8, 3, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (21, 8, 2, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (22, 8, 1, 0, 0, '2019-08-10 13:18:50', 0, '2019-08-10 13:18:55', NULL);
INSERT INTO `t_r_role_line` VALUES (23, 13, 1, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (24, 13, 2, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (25, 13, 5, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (28, 14, 1, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (29, 14, 2, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (30, 14, 3, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (31, 15, 1, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (32, 15, 2, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (33, 15, 3, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (40, 16, 4, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (41, 16, 5, 0, 1223, '2019-08-10 00:00:00', 113224, '2019-08-10 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (42, 17, 1, 0, 1223, '2019-08-12 00:00:00', 10001001, '2019-08-12 00:00:00', '');
INSERT INTO `t_r_role_line` VALUES (43, 18, 1, 0, 1223, '2019-08-12 14:22:49', 113224, '2019-08-12 14:22:49', '');
INSERT INTO `t_r_role_line` VALUES (44, 18, 2, 0, 1223, '2019-08-12 14:22:49', 113224, '2019-08-12 14:22:49', '');
INSERT INTO `t_r_role_line` VALUES (45, 18, 3, 0, 1223, '2019-08-12 14:22:49', 113224, '2019-08-12 14:22:49', '');
INSERT INTO `t_r_role_line` VALUES (46, 19, 3, 0, 1223, '2019-08-12 14:32:10', 113224, '2019-08-12 14:32:10', '');
INSERT INTO `t_r_role_line` VALUES (47, 20, 1, 0, 1223, '2019-08-12 14:50:41', 113224, '2019-08-12 14:50:41', '');
INSERT INTO `t_r_role_line` VALUES (48, 20, 2, 0, 1223, '2019-08-12 14:50:41', 113224, '2019-08-12 14:50:41', '');
INSERT INTO `t_r_role_line` VALUES (49, 21, 1, 0, 10001001, '2019-08-12 15:00:45', 10001001, '2019-08-12 15:04:35', '');
INSERT INTO `t_r_role_line` VALUES (50, 21, 2, 0, 10001001, '2019-08-12 15:00:45', 10001001, '2019-08-12 15:04:35', '');
INSERT INTO `t_r_role_line` VALUES (51, 21, 4, 0, 10001001, '2019-08-12 15:00:45', 10001001, '2019-08-12 15:04:35', '');
INSERT INTO `t_r_role_line` VALUES (52, 22, 1, 0, 10001001, '2019-08-12 16:06:04', 10001001, '2019-08-12 16:06:04', '');
INSERT INTO `t_r_role_line` VALUES (53, 23, 1, 0, 10001001, '2019-08-13 09:08:41', 10001001, '2019-08-13 09:08:41', '');
INSERT INTO `t_r_role_line` VALUES (54, 23, 2, 0, 10001001, '2019-08-13 09:08:41', 10001001, '2019-08-13 09:08:41', '');
INSERT INTO `t_r_role_line` VALUES (55, 24, 1, 0, 10001001, '2019-08-13 09:38:17', 10001001, '2019-08-13 09:38:17', '');
INSERT INTO `t_r_role_line` VALUES (56, 24, 2, 0, 10001001, '2019-08-13 09:38:17', 10001001, '2019-08-13 09:38:17', '');
INSERT INTO `t_r_role_line` VALUES (57, 1, 3, 0, 10001001, '2019-08-13 09:38:17', 10001001, '2019-08-13 09:38:17', '');

-- ----------------------------
-- Table structure for t_r_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_r_role_resource`;
CREATE TABLE `t_r_role_resource`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色主键',
  `resource_id` bigint(20) UNSIGNED NOT NULL COMMENT '该角色拥有的资源id',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除 1:是 0 :否',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `create_emp` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `modify_time` datetime(3) NOT NULL COMMENT '修改时间',
  `modify_emp` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQUE_ROLE_ID_RESOURCE_ID`(`role_id`, `resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源表表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_r_role_resource
-- ----------------------------
INSERT INTO `t_r_role_resource` VALUES (1, 1, 10101, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (2, 1, 10201, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (3, 1, 10301, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (8, 1, 10302, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (9, 1, 10401, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (10, 1, 10402, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (11, 1, 10403, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (12, 1, 10501, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (13, 1, 10601, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (14, 1, 10605, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (15, 1, 10604, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (16, 1, 10603, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (17, 1, 10602, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (18, 1, 10701, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (19, 1, 10702, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (20, 1, 10703, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (21, 1, 10801, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);
INSERT INTO `t_r_role_resource` VALUES (22, 1, 10802, 0, '2019-08-02 16:11:24.000', 1, '2019-08-12 13:01:16.000', 10001001, NULL);

SET FOREIGN_KEY_CHECKS = 1;
