/*
Navicat MySQL Data Transfer

Source Server         : [本地]127.0.0.1
Source Server Version : 50638
Source Host           : 127.0.0.1:3306
Source Database       : ecrmdb

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-06-07 00:36:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity_bet_record
-- ----------------------------
DROP TABLE IF EXISTS `activity_bet_record`;
CREATE TABLE `activity_bet_record` (
  `betrecordcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `ecactivitycode` int(11) DEFAULT NULL COMMENT '定制活动编码',
  `mustbet` decimal(16,2) DEFAULT NULL COMMENT '需完成打码',
  `alreadybet` decimal(16,2) DEFAULT '0.00' COMMENT '已完成打码',
  `betrecordstatus` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否完成 0未完成 1已完成',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `finishtime` datetime DEFAULT NULL COMMENT '完成时间',
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '用户账号',
  `recharge` decimal(16,2) DEFAULT '0.00' COMMENT '充值金额',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL,
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL,
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL,
  `parentemployeeaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `ordernumber` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`betrecordcode`),
  KEY `FK_activity_bet_record_enterprise_activity_customization` (`ecactivitycode`),
  KEY `FK_activity_bet_record_enterprise_employee` (`employeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=55106 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='活动打码记录';

-- ----------------------------
-- Table structure for activity_but_bonus
-- ----------------------------
DROP TABLE IF EXISTS `activity_but_bonus`;
CREATE TABLE `activity_but_bonus` (
  `lsh` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键流水号',
  `createdate` int(11) DEFAULT NULL COMMENT '创建日期 yyyyMMdd',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `pay_type` char(2) DEFAULT NULL COMMENT '业务分类 请看枚举分类',
  `enterprisebrandactivitycode` int(11) DEFAULT NULL COMMENT '企业绑定的活动编码',
  `enterprisecode` char(8) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `loginaccount` varchar(50) DEFAULT NULL COMMENT '员工账号',
  `butmoney` double DEFAULT NULL COMMENT '有效投注额',
  `bonusrand` double DEFAULT NULL COMMENT '注投返利比例',
  `bonusmoney` double DEFAULT NULL COMMENT '返利金额',
  `bonustime` datetime DEFAULT NULL COMMENT '返利时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '注备说明 请记录游戏大类或小类代码',
  `status` char(2) DEFAULT '0' COMMENT '发放状态 0=未发放 1=已发放',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=2518 DEFAULT CHARSET=utf8 COMMENT='投注返水记录';

-- ----------------------------
-- Table structure for activity_deposit_bonus
-- ----------------------------
DROP TABLE IF EXISTS `activity_deposit_bonus`;
CREATE TABLE `activity_deposit_bonus` (
  `ordernumber` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '订单ID',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `depositType` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '充值活动类型',
  `depositamount` decimal(16,2) NOT NULL COMMENT '充值金额',
  `returnratio` decimal(16,2) NOT NULL COMMENT '返奖比率',
  `returnamount` decimal(16,2) NOT NULL COMMENT '返奖金额',
  `deposittime` datetime NOT NULL COMMENT '充值时间',
  `gettime` datetime NOT NULL COMMENT '领取时间',
  `uniqueinfo` text COLLATE utf8_bin NOT NULL COMMENT '身份认证信息',
  PRIMARY KEY (`ordernumber`),
  KEY `FK_activity_reg_bonus_enterprise_employee_1` (`employeecode`),
  KEY `FK_activity_reg_bonus_enterprise_employee_2` (`parentemployeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='充值返利';

-- ----------------------------
-- Table structure for activity_lose_bonus
-- ----------------------------
DROP TABLE IF EXISTS `activity_lose_bonus`;
CREATE TABLE `activity_lose_bonus` (
  `losebonuscode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `loseamount` decimal(16,2) NOT NULL COMMENT '上月输钱金额',
  `returnratio` smallint(6) NOT NULL COMMENT '返奖比率',
  `returnamount` decimal(16,2) NOT NULL COMMENT '返奖金额',
  `gettime` datetime NOT NULL COMMENT '领取时间',
  `losebonusstatus` char(1) COLLATE utf8_bin NOT NULL COMMENT '是否派发 0未派发 1已派发',
  `uniqueinfo` text COLLATE utf8_bin NOT NULL COMMENT '身份认证信息',
  PRIMARY KEY (`losebonuscode`),
  KEY `FK_activity_reg_bonus_enterprise_employee_1` (`employeecode`),
  KEY `FK_activity_reg_bonus_enterprise_employee_2` (`parentemployeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='输钱返利';

-- ----------------------------
-- Table structure for activity_raffle_control
-- ----------------------------
DROP TABLE IF EXISTS `activity_raffle_control`;
CREATE TABLE `activity_raffle_control` (
  `rafflecontrolcode` int(11) NOT NULL AUTO_INCREMENT,
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '用户账号',
  `availabletimes` smallint(6) NOT NULL DEFAULT '0' COMMENT '当前可用抽奖次数',
  `finishtimes` smallint(6) NOT NULL DEFAULT '0' COMMENT '已完成抽奖次数',
  `donatedate` datetime NOT NULL COMMENT '赠送时间',
  `ecactivitycode` int(11) DEFAULT '0',
  `createtime` datetime DEFAULT NULL,
  `createdate` int(11) DEFAULT '0',
  `fingerprintcode` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `loginip` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`rafflecontrolcode`),
  KEY `index1` (`employeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='抽奖控制统计表';

-- ----------------------------
-- Table structure for activity_raffle_record
-- ----------------------------
DROP TABLE IF EXISTS `activity_raffle_record`;
CREATE TABLE `activity_raffle_record` (
  `rafflecontrolcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '用户账号',
  `reffletime` datetime NOT NULL COMMENT '抽奖时间',
  `reffleprizes` char(50) COLLATE utf8_bin NOT NULL COMMENT '中奖奖项',
  `fingerprintcode` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `loginip` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `ecactivitycode` int(11) DEFAULT NULL,
  PRIMARY KEY (`rafflecontrolcode`),
  KEY `FK_activity_reg_bonus_enterprise_employee_1` (`employeecode`),
  KEY `FK_activity_reg_bonus_enterprise_employee_2` (`parentemployeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='抽奖记录表';

-- ----------------------------
-- Table structure for activity_raffle_signin
-- ----------------------------
DROP TABLE IF EXISTS `activity_raffle_signin`;
CREATE TABLE `activity_raffle_signin` (
  `rafflesignincode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `signintime` datetime NOT NULL COMMENT '签到时间',
  `depositamount` decimal(16,2) NOT NULL COMMENT '充值金额',
  `betamount` decimal(16,2) NOT NULL COMMENT '打码金额',
  `isexpires` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否在有效期内(1:是，0:否)',
  PRIMARY KEY (`rafflesignincode`),
  KEY `FK_activity_reg_bonus_enterprise_employee` (`employeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='签到登记表';

-- ----------------------------
-- Table structure for activity_redbag
-- ----------------------------
DROP TABLE IF EXISTS `activity_redbag`;
CREATE TABLE `activity_redbag` (
  `lsh` varchar(36) NOT NULL DEFAULT '',
  `enterprisecode` char(8) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(20) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `logindate` varchar(8) DEFAULT NULL,
  `loginip` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `audittime` datetime DEFAULT NULL,
  `auditer` varchar(20) DEFAULT NULL,
  `paytyime` datetime DEFAULT NULL,
  `auditremark` varchar(500) DEFAULT NULL,
  `payer` varchar(20) DEFAULT NULL,
  `lsbs` varchar(2) DEFAULT NULL,
  `ectivityname` varchar(100) DEFAULT NULL,
  `enterprisebrandactivitycode` varchar(20) DEFAULT NULL,
  `redbagtype` int(11) DEFAULT '1',
  `fingerprintcode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lsh`),
  KEY `index_activity_redbag_2` (`enterprisecode`),
  KEY `activity_redbag_ix2` (`employeecode`),
  KEY `activity_redbag_ix3` (`logindate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for activity_reg_bonus
-- ----------------------------
DROP TABLE IF EXISTS `activity_reg_bonus`;
CREATE TABLE `activity_reg_bonus` (
  `regbonuscode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `regbonus` decimal(16,2) NOT NULL COMMENT '奖金金额',
  `gettime` datetime NOT NULL COMMENT '领取时间',
  `uniqueinfo` text COLLATE utf8_bin NOT NULL COMMENT '身份认证信息',
  PRIMARY KEY (`regbonuscode`),
  KEY `FK_activity_reg_bonus_enterprise_employee_1` (`employeecode`),
  KEY `FK_activity_reg_bonus_enterprise_employee_2` (`parentemployeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='注册彩金';

-- ----------------------------
-- Table structure for activity_template
-- ----------------------------
DROP TABLE IF EXISTS `activity_template`;
CREATE TABLE `activity_template` (
  `activitycode` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动编码',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '活动名称',
  `activitydesc` text COLLATE utf8_bin COMMENT '活动描述',
  `activitynature` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '活动特性，0：常规活动 1：特殊活动',
  `status` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '状态 (1启用，2禁用)',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`activitycode`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1638 COMMENT='活动模板';

-- ----------------------------
-- Table structure for activity_template_setting
-- ----------------------------
DROP TABLE IF EXISTS `activity_template_setting`;
CREATE TABLE `activity_template_setting` (
  `activitysettingcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动配置编码',
  `activitycode` int(11) NOT NULL COMMENT '活动模板编码',
  `agementname` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '参数',
  `agementdesc` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '参数描述',
  PRIMARY KEY (`activitysettingcode`),
  KEY `FK_activity_template_setting_activity_template_activitycode` (`activitycode`),
  CONSTRAINT `FK_activity_template_setting_activity_template_activitycode` FOREIGN KEY (`activitycode`) REFERENCES `activity_template` (`activitycode`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=2730 COMMENT='活动配置';

-- ----------------------------
-- Table structure for agent_banner_info
-- ----------------------------
DROP TABLE IF EXISTS `agent_banner_info`;
CREATE TABLE `agent_banner_info` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `enterprisecode` char(8) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '品牌编码',
  `bannertype` char(10) DEFAULT NULL COMMENT 'banner类型:PC/H5',
  `imgpath` varchar(500) DEFAULT NULL COMMENT 'banner图片地址',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='代理推广系统 - banner表';

-- ----------------------------
-- Table structure for agent_item_info
-- ----------------------------
DROP TABLE IF EXISTS `agent_item_info`;
CREATE TABLE `agent_item_info` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `enterprisecode` char(8) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '品牌编码',
  `iconpath` varchar(500) DEFAULT NULL COMMENT '栏目图标地址',
  `title` varchar(100) DEFAULT NULL COMMENT '栏目标题',
  `content` varchar(5000) DEFAULT NULL COMMENT '栏目内容',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='代理推广系统 - 栏目表';

-- ----------------------------
-- Table structure for agent_site_contact
-- ----------------------------
DROP TABLE IF EXISTS `agent_site_contact`;
CREATE TABLE `agent_site_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `domaincode` int(11) NOT NULL COMMENT '站点编码',
  `qq` varchar(50) DEFAULT NULL COMMENT 'QQ ',
  `skype` varchar(50) DEFAULT NULL COMMENT 'Skype',
  `vchat` varchar(50) DEFAULT NULL COMMENT '微信',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号码',
  PRIMARY KEY (`id`),
  KEY `FK_agent_site_contact_brand_domain_domaincode` (`domaincode`),
  CONSTRAINT `FK_agent_site_contact_brand_domain_domaincode` FOREIGN KEY (`domaincode`) REFERENCES `brand_domain` (`domaincode`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=5461 COMMENT='代理站点联系方式';

-- ----------------------------
-- Table structure for api_aoi_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_aoi_gameinfo`;
CREATE TABLE `api_aoi_gameinfo` (
  `aoi_ProductID` varchar(32) NOT NULL,
  `aoi_UserName` varchar(32) DEFAULT NULL,
  `aoi_GameRecordID` varchar(32) DEFAULT NULL,
  `aoi_OrderNumber` varchar(32) DEFAULT NULL,
  `aoi_TableID` varchar(32) DEFAULT NULL,
  `aoi_Stage` varchar(32) DEFAULT NULL,
  `aoi_Inning` varchar(32) DEFAULT NULL,
  `aoi_GameNameID` varchar(32) DEFAULT NULL,
  `aoi_GameBettingKind` varchar(32) DEFAULT NULL,
  `aoi_GameBettingContent` text,
  `aoi_ResultType` varchar(32) DEFAULT NULL,
  `aoi_BettingAmount` decimal(11,2) DEFAULT NULL,
  `aoi_CompensateRate` varchar(32) DEFAULT NULL,
  `aoi_WinLoseAmount` decimal(11,2) DEFAULT NULL,
  `aoi_Balance` decimal(11,2) DEFAULT NULL,
  `aoi_AddTime` datetime DEFAULT NULL,
  `aoi_VendorId` varchar(32) DEFAULT NULL,
  `aoi_ValidAmount` decimal(11,2) DEFAULT NULL,
  `aoi_createtime` datetime DEFAULT NULL,
  `aoi_PlatformID` varchar(5) DEFAULT NULL,
  `Platformflag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`aoi_ProductID`),
  KEY `ix_aoi_gameinfo_aoi_UserName` (`aoi_UserName`) USING BTREE,
  KEY `api_aoi_gameinfo_ix2` (`aoi_AddTime`),
  KEY `api_aoi_gameinfo_ix3` (`aoi_createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_av_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_av_gameinfo`;
CREATE TABLE `api_av_gameinfo` (
  `tid` varchar(20) NOT NULL DEFAULT '',
  `transType` varchar(20) DEFAULT NULL,
  `amount` double(10,2) DEFAULT NULL,
  `transType2` varchar(20) DEFAULT NULL,
  `amount2` double(10,2) DEFAULT NULL,
  `provider` varchar(20) DEFAULT NULL,
  `roundID` varchar(20) DEFAULT NULL,
  `gameInfo` varchar(20) DEFAULT NULL,
  `history` varchar(255) DEFAULT NULL,
  `isRoundFinished` varchar(10) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `userID` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tid`),
  KEY `api_av_gameinfo_ix1` (`time`),
  KEY `api_av_gameinfo_ix2` (`userID`),
  KEY `api_av_gameinfo_ix3` (`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_bbin_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_bbin_gameinfo`;
CREATE TABLE `api_bbin_gameinfo` (
  `bbin_WagersID` varchar(32) NOT NULL,
  `bbin_UserName` varchar(32) DEFAULT NULL,
  `bbin_WagersDate` datetime DEFAULT NULL,
  `bbin_GameType` varchar(32) DEFAULT NULL,
  `bbin_Result` text,
  `bbin_BetAmount` decimal(11,2) DEFAULT NULL,
  `bbin_Payoff` decimal(11,2) DEFAULT NULL,
  `bbin_Currency` varchar(32) DEFAULT NULL,
  `bbin_Commissionable` decimal(11,2) DEFAULT NULL,
  `bbin_SerialID` varchar(32) DEFAULT NULL,
  `bbin_RoundNo` varchar(32) DEFAULT NULL,
  `bbin_GameCode` varchar(32) DEFAULT NULL,
  `bbin_ResultType` varchar(32) DEFAULT NULL,
  `bbin_Card` varchar(100) DEFAULT NULL,
  `bbin_ExchangeRate` decimal(11,4) DEFAULT NULL,
  `bbin_Commission` decimal(11,4) DEFAULT NULL,
  `bbin_Origin` varchar(50) DEFAULT NULL,
  `bbin_IsPaid` varchar(32) DEFAULT NULL,
  `bbin_createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  `bbin_WagerDetail` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`bbin_WagersID`),
  KEY `ix_bbin_gameinfo_bbin_UserName` (`bbin_UserName`) USING BTREE,
  KEY `ix_bbin_gameinfo_bbin_WagersDate` (`bbin_WagersDate`) USING BTREE,
  KEY `api_bbin_gameinfo_ix3` (`bbin_createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_bet67_oblive_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_bet67_oblive_gameinfo`;
CREATE TABLE `api_bet67_oblive_gameinfo` (
  `id` varchar(255) NOT NULL COMMENT '唯一编号【抓取历史数据使用】',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `betId` varchar(128) DEFAULT NULL COMMENT '局号',
  `gameTypeName` varchar(64) DEFAULT NULL COMMENT '游戏类型',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `betAmount` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `validAmount` decimal(11,2) DEFAULT NULL COMMENT '有效下单金额',
  `winOrLoss` decimal(11,2) DEFAULT NULL COMMENT '结算结果',
  `betStatus` varchar(32) DEFAULT NULL COMMENT '下单状态',
  `betTypeName` varchar(255) DEFAULT NULL COMMENT '下单项',
  `gameResult` varchar(255) DEFAULT NULL COMMENT '结果说明',
  `tableName` varchar(255) DEFAULT NULL COMMENT '桌台名称',
  `commissionName` varchar(255) DEFAULT NULL COMMENT '桌台类型',
  `gameStartTime` datetime DEFAULT NULL COMMENT '游戏开始时间',
  `gameEndTime` datetime DEFAULT NULL COMMENT '游戏结束时间',
  PRIMARY KEY (`id`),
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betId` (`betId`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_betStatus` (`betStatus`) USING BTREE,
  KEY `IDX_gameStartTime` (`gameStartTime`) USING BTREE,
  KEY `IDX_gameEndTime` (`gameEndTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_bet67_oglive_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_bet67_oglive_gameinfo`;
CREATE TABLE `api_bet67_oglive_gameinfo` (
  `id` varchar(255) DEFAULT NULL COMMENT '唯一编号【抓取历史数据使用】',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `gameRoundId` varchar(32) DEFAULT NULL COMMENT '局号',
  `gameTypeName` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `betAmount` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `winOrLoss` decimal(11,2) DEFAULT NULL COMMENT '结算结果',
  `betTypeName` varchar(64) DEFAULT NULL COMMENT '下单',
  `isBill` varchar(32) DEFAULT NULL COMMENT '无效单或已结算',
  `betId` varchar(255) DEFAULT NULL COMMENT '注单号',
  `platformName` varchar(32) DEFAULT NULL COMMENT '厅名',
  `gameKind` varchar(64) DEFAULT NULL COMMENT '游戏名称类型',
  `balance` decimal(11,2) DEFAULT NULL,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_isBill` (`isBill`) USING BTREE,
  KEY `IDX_betId` (`betId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_bet67_oglottery_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_bet67_oglottery_gameinfo`;
CREATE TABLE `api_bet67_oglottery_gameinfo` (
  `id` varchar(255) NOT NULL COMMENT '唯一编号【抓取历史数据使用】',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `betId` varchar(255) DEFAULT NULL COMMENT '投注ID',
  `gameName` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `phaseNum` int(11) DEFAULT NULL COMMENT '期数',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `betAmount` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `winOrLoss` decimal(11,2) DEFAULT NULL COMMENT '结算结果',
  `betTypeName` varchar(255) DEFAULT NULL COMMENT '下单项',
  `betStatus` varchar(255) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `IDX_betId` (`betId`) USING BTREE,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_betStatus` (`betStatus`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_bet67_sllive_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_bet67_sllive_gameinfo`;
CREATE TABLE `api_bet67_sllive_gameinfo` (
  `id` varchar(255) NOT NULL COMMENT '唯一编号【抓取历史数据使用】',
  `betId` varchar(64) DEFAULT NULL COMMENT '注单号',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `desNo` varchar(32) DEFAULT NULL COMMENT '台号',
  `betMoney` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `round` varchar(32) DEFAULT NULL COMMENT '局数',
  `betResult` decimal(11,2) DEFAULT NULL COMMENT '结算结果',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `gameCode` varchar(32) DEFAULT NULL COMMENT '游戏类型',
  `betRgn` varchar(255) DEFAULT NULL COMMENT '投注码',
  `chipLeft` decimal(11,2) DEFAULT NULL COMMENT '本局余额',
  `openAnswer` varchar(255) DEFAULT NULL COMMENT '结果',
  `openDetail` varchar(255) DEFAULT NULL COMMENT '发牌详情',
  `bSuccess` varchar(32) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `IDX_betId` (`betId`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_round` (`round`) USING BTREE,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_bSuccess` (`bSuccess`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_bet67_tssport_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_bet67_tssport_gameinfo`;
CREATE TABLE `api_bet67_tssport_gameinfo` (
  `id` varchar(255) NOT NULL COMMENT '唯一编号【抓取历史数据使用】',
  `betId` varchar(128) DEFAULT NULL COMMENT '交易号',
  `isNormalWager` varchar(64) DEFAULT NULL COMMENT '是否普通注单',
  `playTypeName` varchar(64) DEFAULT NULL COMMENT '类别名称',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `matchName` varchar(64) DEFAULT NULL COMMENT '球赛名称',
  `eventName` varchar(64) DEFAULT NULL COMMENT '赛式名称',
  `matchDate` datetime DEFAULT NULL COMMENT '赛式时间',
  `betTypeName` varchar(128) DEFAULT NULL COMMENT '下单项目名称',
  `teamBetName` varchar(64) DEFAULT NULL COMMENT '下单方式名称',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `wagerOdds` varchar(16) DEFAULT NULL COMMENT '赔率',
  `wagerStake` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `winAmt` varchar(255) DEFAULT NULL COMMENT '结算结果',
  `score` varchar(64) DEFAULT NULL COMMENT '比分',
  `betStatus` varchar(32) DEFAULT NULL COMMENT '注单状态名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `handicap` varchar(64) DEFAULT NULL COMMENT '让球',
  `billTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `isBill` char(2) DEFAULT NULL COMMENT '是否结算',
  `betDetail` varchar(1024) DEFAULT NULL COMMENT '订单明细【html数据】',
  PRIMARY KEY (`id`),
  KEY `IDX_betId` (`betId`) USING BTREE,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_billTime` (`billTime`) USING BTREE,
  KEY `IDX_isBill` (`isBill`) USING BTREE,
  KEY `IDX_betStatus` (`betStatus`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_data_log
-- ----------------------------
DROP TABLE IF EXISTS `api_data_log`;
CREATE TABLE `api_data_log` (
  `datalog_id` varchar(32) NOT NULL,
  `datalog_count` int(11) DEFAULT NULL,
  `datalog_gametype` varchar(225) DEFAULT NULL,
  `datalog_starttime` datetime DEFAULT NULL,
  `datalog_endtime` datetime DEFAULT NULL,
  `datalog_flag` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`datalog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_dzpk_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_dzpk_gameinfo`;
CREATE TABLE `api_dzpk_gameinfo` (
  `gameID` varchar(50) NOT NULL COMMENT '游戏流水号，唯一',
  `accounts` varchar(50) DEFAULT NULL COMMENT '玩家账号',
  `serverName` varchar(50) DEFAULT NULL COMMENT '房间名',
  `tableID` varchar(50) DEFAULT NULL COMMENT '桌子号',
  `chairID` varchar(50) DEFAULT NULL COMMENT '椅子号',
  `userCount` varchar(50) DEFAULT NULL COMMENT '玩家数量',
  `handCard` varchar(50) DEFAULT NULL COMMENT '手牌',
  `cellScore` varchar(50) DEFAULT NULL COMMENT '盲注',
  `allBet` varchar(50) DEFAULT NULL COMMENT '下注',
  `profit` varchar(50) DEFAULT NULL COMMENT '盈利',
  `Revenue` varchar(50) DEFAULT NULL COMMENT '税收（抽水）',
  `GameStartTime` varchar(50) DEFAULT NULL COMMENT '一局开始时间',
  `GameEndTime` varchar(50) DEFAULT NULL COMMENT '一局结束时间',
  `channelId` varchar(50) DEFAULT NULL COMMENT '渠道ID',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `Platformflag` varchar(50) DEFAULT NULL COMMENT '代理标志',
  PRIMARY KEY (`gameID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='德州扑克游戏表';

-- ----------------------------
-- Table structure for api_ebet_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_ebet_gameinfo`;
CREATE TABLE `api_ebet_gameinfo` (
  `bethistoryid` varchar(50) NOT NULL,
  `gametype` varchar(10) DEFAULT NULL,
  `gamename` varchar(20) DEFAULT NULL,
  `betmap` varchar(2000) DEFAULT NULL,
  `judgeresult` varchar(2000) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `payouttime` varchar(50) DEFAULT NULL,
  `gametime` varchar(50) DEFAULT NULL,
  `roundno` varchar(50) DEFAULT NULL,
  `subchannelid` varchar(10) DEFAULT NULL,
  `validbet` varchar(10) DEFAULT NULL,
  `payout` varchar(10) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `bankercards` varchar(2000) DEFAULT NULL,
  `tigercard` varchar(2000) DEFAULT NULL,
  `dragoncard` varchar(2000) DEFAULT NULL,
  `numbercard` varchar(2000) DEFAULT NULL,
  `alldices` varchar(2000) DEFAULT NULL,
  `playercards` varchar(2000) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  PRIMARY KEY (`bethistoryid`),
  KEY `api_ebet_gameinfo_ix1` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_eibc_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_eibc_gameinfo`;
CREATE TABLE `api_eibc_gameinfo` (
  `transid` varchar(50) NOT NULL DEFAULT '',
  `playername` varchar(50) DEFAULT NULL,
  `transactiontime` varchar(30) DEFAULT NULL,
  `matchid` varchar(20) DEFAULT NULL,
  `leagueid` varchar(20) DEFAULT NULL,
  `leaguename` varchar(1000) DEFAULT NULL,
  `sporttype` varchar(20) DEFAULT NULL,
  `awayid` varchar(20) DEFAULT NULL,
  `awayidname` varchar(1000) DEFAULT NULL,
  `homeid` varchar(20) DEFAULT NULL,
  `homeidname` varchar(1000) DEFAULT NULL,
  `matchdatetime` varchar(50) DEFAULT NULL,
  `bettype` varchar(20) DEFAULT NULL,
  `parlayrefno` varchar(20) DEFAULT NULL,
  `betteam` varchar(100) DEFAULT NULL,
  `hdp` varchar(20) DEFAULT NULL,
  `awayhdp` varchar(20) DEFAULT NULL,
  `homehdp` varchar(20) DEFAULT NULL,
  `odds` varchar(20) DEFAULT NULL,
  `oddstype` varchar(20) DEFAULT NULL,
  `awayscore` varchar(20) DEFAULT NULL,
  `homescore` varchar(20) DEFAULT NULL,
  `islive` varchar(20) DEFAULT NULL,
  `lastballno` varchar(20) DEFAULT NULL,
  `ticketstatus` varchar(20) DEFAULT NULL,
  `stake` varchar(20) DEFAULT NULL,
  `winloseamount` varchar(20) DEFAULT NULL,
  `winlostdatetime` varchar(30) DEFAULT NULL,
  `currency` varchar(20) DEFAULT NULL,
  `versionkey` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `nettime` datetime DEFAULT NULL,
  PRIMARY KEY (`transid`),
  KEY `index1` (`playername`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_gametype
-- ----------------------------
DROP TABLE IF EXISTS `api_gametype`;
CREATE TABLE `api_gametype` (
  `gametype_id` varchar(32) NOT NULL,
  `gametype_name` varchar(20) DEFAULT NULL,
  `gametype_code` varchar(8) DEFAULT NULL,
  `gametype_state` tinyint(1) DEFAULT NULL,
  `gametype_createtime` datetime DEFAULT NULL,
  `gametype_createuser` varchar(12) DEFAULT NULL,
  `gametype_remarks` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`gametype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_gb_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_gb_gameinfo`;
CREATE TABLE `api_gb_gameinfo` (
  `settleid` varchar(20) NOT NULL,
  `betid` varchar(20) DEFAULT NULL,
  `betgrpno` varchar(30) DEFAULT NULL,
  `tpcode` varchar(20) DEFAULT NULL,
  `gbsn` varchar(20) DEFAULT NULL,
  `memberid` varchar(20) DEFAULT NULL,
  `curcode` varchar(20) DEFAULT NULL,
  `betdt` varchar(30) DEFAULT NULL,
  `bettype` varchar(20) DEFAULT NULL,
  `bettypeparam1` varchar(20) DEFAULT NULL,
  `bettypeparam2` varchar(20) DEFAULT NULL,
  `wintype` varchar(20) DEFAULT NULL,
  `hxmguid` varchar(20) DEFAULT NULL,
  `initbetamt` varchar(20) DEFAULT NULL,
  `realbetamt` varchar(20) DEFAULT NULL,
  `holdingamt` varchar(20) DEFAULT NULL,
  `initbetrate` varchar(20) DEFAULT NULL,
  `realbetrate` varchar(20) DEFAULT NULL,
  `prewinamt` varchar(20) DEFAULT NULL,
  `betresult` varchar(20) DEFAULT NULL,
  `wlamt` varchar(20) DEFAULT NULL,
  `refundbetamt` varchar(20) DEFAULT NULL,
  `ticketbetamt` varchar(20) DEFAULT NULL,
  `ticketresult` varchar(20) DEFAULT NULL,
  `ticketwlamt` varchar(20) DEFAULT NULL,
  `settledt` varchar(30) DEFAULT NULL,
  `kenolist` text,
  `lottolist` text,
  `ssclist` text,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `pkxlist` text,
  PRIMARY KEY (`settleid`),
  KEY `api_gb_gameinfo_ix1` (`memberid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_ggp_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_ggp_gameinfo`;
CREATE TABLE `api_ggp_gameinfo` (
  `lsh` varchar(36) NOT NULL,
  `gamedate` varchar(10) NOT NULL,
  `username` varchar(50) NOT NULL DEFAULT '',
  `ngr` varchar(10) NOT NULL,
  PRIMARY KEY (`lsh`),
  UNIQUE KEY `index1` (`gamedate`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_gg_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_gg_gameinfo`;
CREATE TABLE `api_gg_gameinfo` (
  `autoid` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏编码',
  `gameId` varchar(10) DEFAULT NULL COMMENT '游戏编码',
  `cuuency` varchar(10) DEFAULT NULL COMMENT '货币',
  `linkId` varchar(20) DEFAULT NULL COMMENT '局号',
  `accountno` varchar(20) DEFAULT NULL COMMENT '用户名',
  `betmoney` double DEFAULT NULL COMMENT '投注金额',
  `netmoney` double DEFAULT NULL COMMENT '输赢',
  `bettime` datetime DEFAULT NULL COMMENT '投注时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建记录时间',
  `Platformflag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`autoid`),
  KEY `api_gg_gameinfo_ix1` (`accountno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_hab_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_hab_gameinfo`;
CREATE TABLE `api_hab_gameinfo` (
  `friendlygameinstanceid` varchar(36) NOT NULL,
  `playerid` varchar(36) DEFAULT NULL,
  `brandid` varchar(36) DEFAULT NULL,
  `username` varchar(36) DEFAULT NULL,
  `brandgameid` varchar(36) DEFAULT NULL,
  `gamekeyname` varchar(36) DEFAULT NULL,
  `gametypeid` varchar(36) DEFAULT NULL,
  `dtstarted` datetime DEFAULT NULL,
  `dtcompleted` datetime DEFAULT NULL,
  `gameinstanceid` varchar(36) DEFAULT NULL,
  `stake` double DEFAULT NULL,
  `payout` double DEFAULT NULL,
  `jackpotwin` double DEFAULT NULL,
  `jackpotcontribution` double DEFAULT NULL,
  `currencycode` varchar(10) DEFAULT NULL,
  `channeltypeid` varchar(10) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  PRIMARY KEY (`friendlygameinstanceid`),
  KEY `index_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_ibc_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_ibc_gameinfo`;
CREATE TABLE `api_ibc_gameinfo` (
  `ibc_rowguid` varchar(100) NOT NULL,
  `ibc_ballid` varchar(100) DEFAULT NULL,
  `ibc_balltime` datetime DEFAULT NULL,
  `ibc_content` text,
  `ibc_curpl` varchar(20) DEFAULT NULL,
  `ibc_ds` varchar(100) DEFAULT NULL,
  `ibc_dxc` varchar(100) DEFAULT NULL,
  `ibc_isbk` varchar(100) DEFAULT NULL,
  `ibc_iscancel` varchar(20) DEFAULT NULL,
  `ibc_isdanger` varchar(20) DEFAULT NULL,
  `ibc_isjs` varchar(20) DEFAULT NULL,
  `ibc_lose` double(11,2) DEFAULT NULL,
  `ibc_matchid` varchar(100) DEFAULT NULL,
  `ibc_moneyrate` varchar(100) DEFAULT NULL,
  `ibc_orderid` varchar(100) DEFAULT NULL,
  `ibc_recard` varchar(100) DEFAULT NULL,
  `ibc_result` varchar(100) DEFAULT NULL,
  `ibc_rqc` varchar(100) DEFAULT NULL,
  `ibc_rqteam` varchar(100) DEFAULT NULL,
  `ibc_sportid` varchar(100) DEFAULT NULL,
  `ibc_tballtime` varchar(32) DEFAULT NULL,
  `ibc_thisdate` varchar(32) DEFAULT NULL,
  `ibc_truewin` varchar(100) DEFAULT NULL,
  `ibc_tzip` varchar(100) DEFAULT NULL,
  `ibc_tzmoney` double(11,2) DEFAULT NULL,
  `ibc_tzteam` varchar(100) DEFAULT NULL,
  `ibc_tztype` varchar(100) DEFAULT NULL,
  `ibc_updatetime` datetime DEFAULT NULL,
  `ibc_username` varchar(100) DEFAULT NULL,
  `ibc_win` double(11,2) DEFAULT NULL,
  `ibc_zdbf` varchar(100) DEFAULT NULL,
  `ibc_vendorid` varchar(100) DEFAULT NULL,
  `ibc_createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ibc_rowguid`),
  KEY `ix_ibc_gameinfo_ibc_username` (`ibc_username`) USING BTREE,
  KEY `api_ibc_gameinfo_ix2` (`ibc_balltime`),
  KEY `api_ibc_gameinfo_ix4` (`ibc_createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_idn2_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_idn2_gameinfo`;
CREATE TABLE `api_idn2_gameinfo` (
  `lsh` varchar(36) NOT NULL,
  `turnoverdate` datetime NOT NULL,
  `userid` varchar(50) NOT NULL,
  `totalturnover` varchar(50) DEFAULT NULL,
  `turnoverpoker` varchar(50) DEFAULT NULL,
  `turnoverdomino` varchar(50) DEFAULT NULL,
  `turnoverceme` varchar(50) DEFAULT NULL,
  `turnoverblackjack` varchar(50) DEFAULT NULL,
  `turnovercapsa` varchar(50) DEFAULT NULL,
  `turnoverlivepoker` varchar(50) DEFAULT NULL,
  `texaspoker` varchar(50) DEFAULT NULL,
  `domino` varchar(50) DEFAULT NULL,
  `ceme` varchar(50) DEFAULT NULL,
  `blackjack` varchar(50) DEFAULT NULL,
  `capsa` varchar(50) DEFAULT NULL,
  `livepoker` varchar(50) DEFAULT NULL,
  `pokertournament` varchar(50) DEFAULT NULL,
  `agentcommission` varchar(50) DEFAULT NULL,
  `agentbill` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`turnoverdate`,`userid`),
  KEY `index1` (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_idn_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_idn_gameinfo`;
CREATE TABLE `api_idn_gameinfo` (
  `transactionno` varchar(50) NOT NULL,
  `tableno` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `betdate` varchar(50) DEFAULT NULL,
  `game` varchar(50) DEFAULT NULL,
  `bettable` varchar(50) DEFAULT NULL,
  `periode` varchar(50) DEFAULT NULL,
  `room` varchar(50) DEFAULT NULL,
  `bet` varchar(20) DEFAULT NULL,
  `currbet` varchar(50) DEFAULT NULL,
  `rbet` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `card` varchar(50) DEFAULT NULL,
  `hand` varchar(50) DEFAULT NULL,
  `prize` varchar(50) DEFAULT NULL,
  `curr` varchar(50) DEFAULT NULL,
  `currplayer` varchar(50) DEFAULT NULL,
  `curramount` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `total` varchar(50) DEFAULT NULL,
  `agentcomission` varchar(50) DEFAULT NULL,
  `agentbill` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  PRIMARY KEY (`transactionno`),
  KEY `api_idn_gameinfo_ix1` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_im_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_im_gameinfo`;
CREATE TABLE `api_im_gameinfo` (
  `betid` varchar(50) NOT NULL,
  `providername` varchar(50) DEFAULT NULL,
  `gameid` varchar(50) DEFAULT NULL,
  `wagercreationdatetime` varchar(50) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `playerid` varchar(50) DEFAULT NULL,
  `stakeamount` double(50,0) DEFAULT NULL,
  `memberexposure` double(50,0) DEFAULT NULL,
  `payoutamount` double(50,0) DEFAULT NULL,
  `winloss` double(50,0) DEFAULT NULL,
  `oddstype` varchar(50) DEFAULT NULL,
  `wagertype` varchar(50) DEFAULT NULL,
  `platform` varchar(50) DEFAULT NULL,
  `issettled` varchar(50) DEFAULT NULL,
  `isconfirmed` varchar(50) DEFAULT NULL,
  `iscancelled` varchar(50) DEFAULT NULL,
  `bettradestatus` varchar(50) DEFAULT NULL,
  `bettradecommission` varchar(50) DEFAULT NULL,
  `bettradebuybackamount` varchar(50) DEFAULT NULL,
  `combotype` varchar(50) DEFAULT NULL,
  `lastupdateddate` varchar(50) DEFAULT NULL,
  `detailitems` text,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `nettime` datetime DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `platformflag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`betid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_jdb_file
-- ----------------------------
DROP TABLE IF EXISTS `api_jdb_file`;
CREATE TABLE `api_jdb_file` (
  `lsh` char(32) NOT NULL COMMENT '流水号，主键，file值的MD5',
  `parent` varchar(50) NOT NULL COMMENT '上级账号',
  `date` char(8) NOT NULL COMMENT '日期',
  `file` varchar(100) NOT NULL COMMENT '文件路径',
  `times` bigint(20) NOT NULL COMMENT '插入时间，时间戳13位',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '文件解析状态：0-未解析 1-已解析',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='JDB游戏数据FTP文件列表';

-- ----------------------------
-- Table structure for api_jdb_ftp
-- ----------------------------
DROP TABLE IF EXISTS `api_jdb_ftp`;
CREATE TABLE `api_jdb_ftp` (
  `configid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `ftpname` varchar(20) NOT NULL COMMENT 'FTP登录账号',
  `ftppswd` varchar(30) NOT NULL COMMENT 'FTP登录密码',
  `ftpip` char(15) NOT NULL COMMENT 'FTP登录地址',
  `ftpport` int(11) NOT NULL COMMENT 'FTP登录端口',
  `datefolder` char(8) DEFAULT NULL COMMENT '上一次下载的文件目录，下次执行会+1',
  `endfolder` char(8) DEFAULT NULL COMMENT '结束下载的日期',
  `localfolder` varchar(20) DEFAULT 'D:/jdb168' COMMENT '本地存储文件的主文件夹，生成文件路径${localfolder}/${ftpname}/${datefolder}/gamename/filename',
  `updatetime` datetime DEFAULT NULL COMMENT '上一次完成下载的时间',
  `lastnum` int(11) DEFAULT '0' COMMENT '上一次下载的数量',
  `totalnum` int(11) DEFAULT '0' COMMENT '下载总量',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1-正常，0-停止',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`configid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='JDB168 FTP配置信息';

-- ----------------------------
-- Table structure for api_jdb_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_jdb_gameinfo`;
CREATE TABLE `api_jdb_gameinfo` (
  `seqNo` bigint(20) NOT NULL COMMENT '游戏序号，主键',
  `playerId` varchar(19) NOT NULL COMMENT '玩家账号',
  `mtype` int(11) DEFAULT NULL COMMENT '机台类型',
  `gameDate` char(19) DEFAULT NULL COMMENT '游戏时间',
  `bet` double DEFAULT NULL COMMENT '押注金额',
  `gambleBet` double DEFAULT NULL COMMENT '博取游戏押注金额',
  `win` double DEFAULT NULL COMMENT '游戏输赢',
  `total` double DEFAULT NULL COMMENT '总输赢',
  `currency` char(2) DEFAULT NULL COMMENT '货币编码',
  `denom` double DEFAULT NULL COMMENT '投注面额',
  `lastModifyTime` char(19) DEFAULT NULL COMMENT '最后修改时间',
  `playerIp` char(15) DEFAULT NULL COMMENT '玩家IP',
  `clientType` varchar(20) DEFAULT NULL COMMENT '客户端类型',
  `gType` int(11) DEFAULT NULL COMMENT '游戏类型：0-老虎机，7-捕鱼机，9-水果机',
  `hasGamble` int(11) DEFAULT NULL COMMENT '是否博取游戏：0-否，1-是【老虎鱼&水果机】',
  `hasBonusGame` int(11) DEFAULT NULL COMMENT '是否奖金游戏：0-否，1-是【水果机专属】',
  `hasFreegame` int(11) DEFAULT NULL COMMENT '是否免费游戏：0-否，1-是【老虎机专属】',
  `roomType` int(11) DEFAULT NULL COMMENT '捕鱼机房间类型：0-欢乐区，1-富豪区【捕鱼机专属】',
  `systemTakeWin` int(11) DEFAULT NULL COMMENT '标记该笔为游戏中断线，由系统结算：0-否，1-是【老虎机专属】',
  `jackpot` double DEFAULT NULL COMMENT '赢得彩金金额【老虎机专属】',
  `jackpotContribute` double DEFAULT NULL COMMENT '彩金贡献值【老虎机专属】',
  `beforeBalance` double DEFAULT NULL COMMENT '进场金额【捕鱼机专属】',
  `afterBalance` double DEFAULT NULL COMMENT '离场金额【捕鱼机专属】',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `bettime` datetime DEFAULT NULL COMMENT '投注时间',
  `betmoney` double DEFAULT NULL COMMENT '投注金额',
  `netmoney` double DEFAULT NULL COMMENT '输赢金额',
  PRIMARY KEY (`seqNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='JDB168接口游戏数据';

-- ----------------------------
-- Table structure for api_login_log
-- ----------------------------
DROP TABLE IF EXISTS `api_login_log`;
CREATE TABLE `api_login_log` (
  `loginlog_id` varchar(32) NOT NULL DEFAULT '',
  `loginlog_name` varchar(20) DEFAULT NULL,
  `loginlog_ip` varchar(32) DEFAULT NULL,
  `loginlog_createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`loginlog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_logmsg
-- ----------------------------
DROP TABLE IF EXISTS `api_logmsg`;
CREATE TABLE `api_logmsg` (
  `log_id` varchar(32) NOT NULL,
  `platform_id` varchar(32) DEFAULT NULL,
  `log_createtime` datetime DEFAULT NULL,
  `log_type` varchar(20) DEFAULT NULL,
  `log_result` text,
  `log_state` varchar(15) DEFAULT NULL,
  `log_gametype` varchar(20) DEFAULT NULL,
  `log_username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `FK_Relationship_8` (`platform_id`),
  CONSTRAINT `FK_Relationship_8` FOREIGN KEY (`platform_id`) REFERENCES `api_platform` (`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_m88_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_m88_gameinfo`;
CREATE TABLE `api_m88_gameinfo` (
  `transid` varchar(36) NOT NULL,
  `memberid` varchar(36) DEFAULT NULL,
  `operatorid` varchar(50) DEFAULT NULL,
  `sitecode` varchar(36) DEFAULT NULL,
  `leagueid` varchar(36) DEFAULT NULL,
  `homeid` varchar(36) DEFAULT NULL,
  `awayid` varchar(36) DEFAULT NULL,
  `matchdatetime` varchar(36) DEFAULT NULL,
  `bettype` varchar(36) DEFAULT NULL,
  `parlayrefno` varchar(36) DEFAULT NULL,
  `odds` varchar(36) DEFAULT NULL,
  `currency` varchar(36) DEFAULT NULL,
  `stake` varchar(36) DEFAULT NULL,
  `winlostamount` varchar(36) DEFAULT NULL,
  `transactiontime` varchar(36) DEFAULT NULL,
  `ticketstatus` varchar(36) DEFAULT NULL,
  `versionkey` varchar(36) DEFAULT NULL,
  `winlostdatetime` varchar(36) DEFAULT NULL,
  `oddstype` varchar(36) DEFAULT NULL,
  `sportstype` varchar(36) DEFAULT NULL,
  `betteam` varchar(255) DEFAULT NULL,
  `homehdp` varchar(36) DEFAULT NULL,
  `awayhdp` varchar(36) DEFAULT NULL,
  `matchid` varchar(36) DEFAULT NULL,
  `islive` varchar(36) DEFAULT NULL,
  `homescore` varchar(36) DEFAULT NULL,
  `awayscore` varchar(36) DEFAULT NULL,
  `choicecode` varchar(255) DEFAULT NULL,
  `choicename` varchar(255) DEFAULT NULL,
  `txntype` varchar(36) DEFAULT NULL,
  `lastupdate` varchar(36) DEFAULT NULL,
  `leaguename` varchar(255) DEFAULT NULL,
  `homename` varchar(255) DEFAULT NULL,
  `awayname` varchar(255) DEFAULT NULL,
  `sportname` varchar(255) DEFAULT NULL,
  `oddsname` varchar(255) DEFAULT NULL,
  `bettypename` varchar(255) DEFAULT NULL,
  `winloststatus` varchar(36) DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `nettime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`transid`),
  KEY `api_m88_gameinfo_ix1` (`memberid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_mg_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_mg_gameinfo`;
CREATE TABLE `api_mg_gameinfo` (
  `mg_transId` varchar(50) NOT NULL COMMENT '????',
  `mg_key` varchar(50) DEFAULT NULL COMMENT '????(????)',
  `mg_colId` varchar(50) DEFAULT NULL COMMENT '???????(????)',
  `mg_agentId` varchar(50) DEFAULT NULL COMMENT '????',
  `mg_mbrId` varchar(50) DEFAULT NULL COMMENT '????',
  `mg_mbrCode` varchar(50) DEFAULT NULL COMMENT '????',
  `mg_gameId` varchar(50) DEFAULT NULL COMMENT '????',
  `mg_transType` varchar(50) DEFAULT NULL COMMENT '?????[?? -> "bet", ? -> "win", ?? -> "refund"]',
  `mg_transTime` varchar(50) DEFAULT NULL COMMENT '????????UTC+0???',
  `mg_mgsGameId` varchar(50) DEFAULT NULL COMMENT 'MG ???????',
  `mg_mgsActionId` varchar(50) DEFAULT NULL COMMENT 'MG ????????',
  `mg_amnt` varchar(50) DEFAULT NULL COMMENT '?????(????????????????)',
  `mg_clrngAmnt` varchar(50) DEFAULT NULL COMMENT '?????(????????????????)',
  `mg_balance` varchar(50) DEFAULT NULL COMMENT '??????',
  `mg_refTransId` varchar(50) DEFAULT NULL COMMENT '???????? ??????????, ???????????????transId?',
  `mg_refTransType` varchar(50) DEFAULT NULL COMMENT '?????????. [?? -> "bet", ? -> "win"]',
  `mg_win` varchar(50) DEFAULT NULL COMMENT '??????',
  `mg_createtime` datetime DEFAULT NULL COMMENT '????',
  `Platformflag` varchar(50) DEFAULT NULL COMMENT '????',
  PRIMARY KEY (`mg_transId`),
  KEY `api_mg_gameinfo_ix1` (`mg_createtime`),
  KEY `api_mg_gameinfo_ix2` (`mg_mbrId`,`mg_gameId`,`mg_mgsGameId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MG?????';

-- ----------------------------
-- Table structure for api_nhq_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_nhq_gameinfo`;
CREATE TABLE `api_nhq_gameinfo` (
  `BettingCredits` double(15,2) DEFAULT NULL,
  `PreCreditsPoint` double(20,2) DEFAULT NULL,
  `GameResult` text,
  `GameRoomName` varchar(50) DEFAULT NULL,
  `AgentAccount` varchar(20) DEFAULT NULL,
  `GamblingCode` varchar(32) DEFAULT NULL,
  `AfterPayoutCredits` double(20,2) DEFAULT NULL,
  `UserAccount` varchar(20) DEFAULT NULL,
  `GameType` varchar(5) DEFAULT NULL,
  `BettingDate` datetime DEFAULT NULL,
  `BettingNO` varchar(20) DEFAULT '',
  `DealerName` varchar(100) DEFAULT NULL,
  `GameName` varchar(20) DEFAULT NULL,
  `SetGameNo` varchar(20) DEFAULT NULL,
  `IsPayout` tinyint(4) DEFAULT NULL,
  `ParentUserID` varchar(10) DEFAULT NULL,
  `WinningCredits` double(15,2) DEFAULT NULL,
  `BettingPoint` varchar(20) DEFAULT NULL,
  `TableName` varchar(20) DEFAULT NULL,
  `TrackIP` varchar(30) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `WashCodeCredits` double(20,2) DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `BettingID` varchar(32) NOT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`BettingID`),
  KEY `api_nhq_gameinfo_ix1` (`UserAccount`),
  KEY `api_nhq_gameinfo_ix2` (`BettingDate`),
  KEY `api_nhq_gameinfo_ix3` (`CreateTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_platform
-- ----------------------------
DROP TABLE IF EXISTS `api_platform`;
CREATE TABLE `api_platform` (
  `platform_id` varchar(32) NOT NULL,
  `platform_name` varchar(20) DEFAULT NULL,
  `platform_deskey` varchar(8) DEFAULT NULL,
  `platform_md5key` varchar(8) DEFAULT NULL,
  `platform_createtime` datetime DEFAULT NULL,
  `platform_createuser` varchar(12) DEFAULT NULL,
  `platform_username` varchar(12) DEFAULT NULL,
  `platform_password` varchar(32) DEFAULT NULL,
  `platform_remarks` varchar(500) DEFAULT NULL,
  `platform_ip` varchar(500) DEFAULT NULL,
  `platform_state` tinyint(1) DEFAULT NULL,
  `platform_type` varchar(12) DEFAULT NULL,
  `platform_parentid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_platform_money
-- ----------------------------
DROP TABLE IF EXISTS `api_platform_money`;
CREATE TABLE `api_platform_money` (
  `money_id` varchar(32) NOT NULL,
  `platform_id` varchar(32) DEFAULT NULL,
  `money_money` decimal(20,2) DEFAULT NULL,
  `money_gametype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`money_id`),
  KEY `FK_Relationship_6` (`platform_id`),
  CONSTRAINT `FK_Relationship_6` FOREIGN KEY (`platform_id`) REFERENCES `api_platform` (`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_platform_moneyinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_platform_moneyinfo`;
CREATE TABLE `api_platform_moneyinfo` (
  `pmoney_id` varchar(32) NOT NULL,
  `platform_id` varchar(32) DEFAULT NULL,
  `pmoney_money` decimal(20,2) DEFAULT NULL,
  `pmoney_gametype` varchar(8) DEFAULT NULL,
  `pmoney_createtime` datetime DEFAULT NULL,
  `pmoney_createuser` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`pmoney_id`),
  KEY `FK_Relationship_7` (`platform_id`),
  CONSTRAINT `FK_Relationship_7` FOREIGN KEY (`platform_id`) REFERENCES `api_platform` (`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_platform_webservice
-- ----------------------------
DROP TABLE IF EXISTS `api_platform_webservice`;
CREATE TABLE `api_platform_webservice` (
  `webservice_id` varchar(32) NOT NULL,
  `platform_id` varchar(32) DEFAULT NULL,
  `webservice_name` varchar(20) DEFAULT NULL,
  `webservice_password` varchar(32) DEFAULT NULL,
  `webservice_key` varchar(100) DEFAULT NULL,
  `webservice_key2` varchar(100) DEFAULT NULL,
  `webservice_gametype` varchar(8) DEFAULT NULL,
  `webservice_url` varchar(200) DEFAULT NULL,
  `webservice_url2` varchar(200) DEFAULT NULL,
  `webservice_state` tinyint(1) DEFAULT NULL,
  `webservice_createuser` varchar(12) DEFAULT NULL,
  `webservice_createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`webservice_id`),
  KEY `FK_Relationship_9` (`platform_id`),
  CONSTRAINT `FK_Relationship_9` FOREIGN KEY (`platform_id`) REFERENCES `api_platform` (`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_ptgame
-- ----------------------------
DROP TABLE IF EXISTS `api_ptgame`;
CREATE TABLE `api_ptgame` (
  `code` varchar(20) NOT NULL DEFAULT '',
  `ename` varchar(200) DEFAULT NULL,
  `cname` varchar(200) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_pt_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_pt_gameinfo`;
CREATE TABLE `api_pt_gameinfo` (
  `pt_gamecode` varchar(32) NOT NULL,
  `pt_username` varchar(32) DEFAULT NULL,
  `pt_windowcode` varchar(32) DEFAULT NULL,
  `pt_gameid` varchar(32) DEFAULT NULL,
  `pt_gametype` varchar(32) DEFAULT NULL,
  `pt_gamename` varchar(200) DEFAULT NULL,
  `pt_bet` decimal(11,2) DEFAULT NULL,
  `pt_win` decimal(11,2) DEFAULT NULL,
  `pt_balance` decimal(11,2) DEFAULT NULL,
  `pt_gamedate` datetime DEFAULT NULL,
  `pt_info` varchar(1000) DEFAULT NULL,
  `pt_sessionid` varchar(32) DEFAULT NULL,
  `pt_progressivebet` varchar(32) DEFAULT NULL,
  `pt_progressivewin` varchar(32) DEFAULT NULL,
  `pt_currentbet` varchar(32) DEFAULT NULL,
  `pt_livenetwork` varchar(32) DEFAULT NULL,
  `pt_rnum` varchar(32) DEFAULT NULL,
  `pt_createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pt_gamecode`),
  KEY `ix_pt_gameinfo_pt_username` (`pt_username`) USING BTREE,
  KEY `ix_pt_gameinfo_pt_gamedate` (`pt_gamedate`) USING BTREE,
  KEY `api_pt_gameinfo_ix3` (`pt_createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_qp_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_qp_gameinfo`;
CREATE TABLE `api_qp_gameinfo` (
  `TurnNumber` varchar(50) NOT NULL COMMENT '游戏局号',
  `ServerID` varchar(50) DEFAULT NULL COMMENT '游戏类型',
  `KindID` varchar(50) DEFAULT NULL COMMENT '房间类型',
  `RoomName` varchar(50) DEFAULT NULL COMMENT '游戏房间名称',
  `StartTime` varchar(50) DEFAULT NULL COMMENT '下注时间',
  `EndTime` varchar(50) DEFAULT NULL COMMENT '派彩时间',
  `Score` varchar(50) DEFAULT NULL COMMENT '投注金额',
  `TurnScore` varchar(50) DEFAULT NULL COMMENT '输赢',
  `Account` varchar(50) DEFAULT NULL COMMENT '玩家账号',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `Platformflag` varchar(50) DEFAULT NULL COMMENT '标志',
  `Revenue` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TurnNumber`),
  KEY `api_mg_gameinfo_ix1` (`createtime`),
  KEY `api_qp_gameinfo_ix2` (`Account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='棋牌游戏表';

-- ----------------------------
-- Table structure for api_qt_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_qt_gameinfo`;
CREATE TABLE `api_qt_gameinfo` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `playergameroundid` varchar(36) DEFAULT NULL,
  `gameid` varchar(36) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `balance` varchar(10) DEFAULT NULL,
  `created` varchar(50) DEFAULT NULL,
  `gameprovider` varchar(36) DEFAULT NULL,
  `gtype` varchar(10) DEFAULT NULL,
  `gameclienttype` varchar(36) DEFAULT NULL,
  `gamecategory` varchar(36) DEFAULT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `playerdevice` varchar(36) DEFAULT NULL,
  `operatorid` varchar(36) DEFAULT NULL,
  `playerid` varchar(36) DEFAULT NULL,
  `wallettransactionid` varchar(36) DEFAULT NULL,
  `roundstatus` varchar(36) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index1` (`playergameroundid`),
  KEY `api_qt_gameinfo_ix2` (`playerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_qwp_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_qwp_gameinfo`;
CREATE TABLE `api_qwp_gameinfo` (
  `TurnNumber` bigint(20) NOT NULL COMMENT '局号',
  `ServerId` int(11) NOT NULL COMMENT '房间Id',
  `KindId` int(11) NOT NULL COMMENT '游戏Id',
  `RoomName` varchar(100) DEFAULT NULL COMMENT '房间名称',
  `StartTime` datetime DEFAULT NULL COMMENT '开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '结束时间',
  `RecordTime` datetime DEFAULT NULL COMMENT '记录时间',
  `CardData` varchar(100) DEFAULT NULL COMMENT '牌型',
  `Account` varchar(50) DEFAULT NULL COMMENT '玩家账号',
  `Score` double DEFAULT NULL COMMENT '输赢',
  `TurnScore` double DEFAULT NULL COMMENT '底分',
  `Revenue` double DEFAULT NULL COMMENT '抽水',
  PRIMARY KEY (`TurnNumber`,`ServerId`,`KindId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='去玩棋牌接口游戏数据';

-- ----------------------------
-- Table structure for api_sa_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_sa_gameinfo`;
CREATE TABLE `api_sa_gameinfo` (
  `BetTime` datetime DEFAULT NULL,
  `PayoutTime` datetime DEFAULT NULL,
  `Username` varchar(20) DEFAULT NULL,
  `HostID` varchar(10) DEFAULT NULL,
  `GameID` varchar(32) DEFAULT NULL,
  `Round` varchar(10) DEFAULT NULL,
  `Sets` varchar(10) DEFAULT NULL,
  `BetID` varchar(32) NOT NULL DEFAULT '',
  `BetAmount` double(10,2) DEFAULT NULL,
  `ResultAmount` double(10,2) DEFAULT NULL,
  `GameType` varchar(15) DEFAULT NULL,
  `BetType` varchar(10) DEFAULT NULL,
  `BetSource` varchar(10) DEFAULT NULL,
  `State` varchar(10) DEFAULT NULL,
  `Detail` varchar(32) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  `validbet` double(10,2) DEFAULT '0.00',
  PRIMARY KEY (`BetID`),
  KEY `api_sa_gameinfo_ix1` (`BetTime`),
  KEY `api_sa_gameinfo_ix2` (`PayoutTime`),
  KEY `api_sa_gameinfo_ix3` (`Username`),
  KEY `api_sa_gameinfo_ix4` (`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_sgs_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_sgs_gameinfo`;
CREATE TABLE `api_sgs_gameinfo` (
  `ugsbetid` varchar(50) NOT NULL,
  `txid` varchar(50) DEFAULT NULL,
  `betid` varchar(50) DEFAULT NULL,
  `beton` datetime DEFAULT NULL,
  `betclosedon` datetime DEFAULT NULL,
  `betupdatedon` datetime DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `roundid` varchar(50) DEFAULT NULL,
  `roundstatus` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `riskamt` double DEFAULT NULL,
  `winamt` double DEFAULT NULL,
  `winloss` double DEFAULT NULL,
  `beforebal` double DEFAULT NULL,
  `postbal` double DEFAULT NULL,
  `cur` varchar(10) DEFAULT NULL,
  `gameprovider` varchar(50) DEFAULT NULL,
  `gamename` varchar(50) DEFAULT NULL,
  `gameid` varchar(50) DEFAULT NULL,
  `platformtype` varchar(50) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  `bettype` varchar(50) DEFAULT NULL,
  `playtype` varchar(50) DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `validmoney` double DEFAULT '0',
  PRIMARY KEY (`ugsbetid`),
  KEY `api_sgs_gameinfo_ix1` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_solt_gametype
-- ----------------------------
DROP TABLE IF EXISTS `api_solt_gametype`;
CREATE TABLE `api_solt_gametype` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `biggametype` varchar(10) DEFAULT NULL,
  `enname` varchar(100) DEFAULT NULL,
  `cnname` varchar(100) DEFAULT NULL,
  `trname` varchar(100) DEFAULT NULL,
  `japname` varchar(100) DEFAULT NULL,
  `krname` varchar(100) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `category2` varchar(100) DEFAULT NULL,
  `category3` varchar(100) DEFAULT NULL,
  `imagename` varchar(100) DEFAULT NULL,
  `gamecodeweb` varchar(50) DEFAULT NULL,
  `gamecodeh5` varchar(50) DEFAULT NULL,
  `isweb` varchar(10) DEFAULT NULL,
  `ish5` varchar(10) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `stype` varchar(50) DEFAULT NULL,
  `ord` varchar(10) DEFAULT '999',
  PRIMARY KEY (`lsh`),
  KEY `api_solt_gametype_ix1` (`biggametype`)
) ENGINE=InnoDB AUTO_INCREMENT=3815 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_solt_gametype_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `api_solt_gametype_enterprise`;
CREATE TABLE `api_solt_gametype_enterprise` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `enterprisecode` char(8) DEFAULT NULL,
  `biggametype` varchar(10) DEFAULT NULL,
  `gametype_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=13083 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_tag_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_tag_gameinfo`;
CREATE TABLE `api_tag_gameinfo` (
  `billNo` varchar(32) NOT NULL,
  `playerName` varchar(32) DEFAULT NULL,
  `agentCode` varchar(32) DEFAULT NULL,
  `gameCode` varchar(32) DEFAULT NULL,
  `netAmount` decimal(15,2) DEFAULT NULL,
  `betTime` datetime DEFAULT NULL,
  `gameType` varchar(32) DEFAULT NULL,
  `betAmount` decimal(15,2) DEFAULT NULL,
  `validBetAmount` decimal(8,2) DEFAULT NULL,
  `flag` varchar(5) DEFAULT NULL,
  `playType` varchar(50) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `tableCode` varchar(32) DEFAULT NULL,
  `loginIP` varchar(32) DEFAULT NULL,
  `recalcuTime` datetime DEFAULT NULL,
  `platformId` varchar(32) DEFAULT NULL,
  `platformType` varchar(32) DEFAULT NULL,
  `stringex` varchar(32) DEFAULT NULL,
  `remark` text,
  `round` varchar(32) DEFAULT NULL,
  `result` text,
  `beforeCredit` decimal(15,2) DEFAULT NULL,
  `deviceType` varchar(32) DEFAULT NULL,
  `betAmountBase` decimal(15,2) DEFAULT NULL,
  `betAmountBonus` decimal(15,2) DEFAULT NULL,
  `netAmountBase` decimal(15,2) DEFAULT NULL,
  `netAmountBonus` decimal(15,2) DEFAULT NULL,
  `slottype` varchar(32) DEFAULT NULL,
  `mainbillno` varchar(32) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`billNo`),
  KEY `api_tag_gameinfo_ix1` (`playerName`),
  KEY `api_tag_gameinfo_ix2` (`betTime`),
  KEY `api_tag_gameinfo_ix3` (`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_tag_xml_record
-- ----------------------------
DROP TABLE IF EXISTS `api_tag_xml_record`;
CREATE TABLE `api_tag_xml_record` (
  `agentcode` varchar(10) NOT NULL,
  `platformtype` varchar(10) NOT NULL COMMENT '平台类型：AGIN/HUNTER/XIN',
  `filenumber` bigint(12) NOT NULL,
  `xmltype` varchar(20) NOT NULL DEFAULT 'normal' COMMENT 'normal/lostAndfound',
  `filepath` varchar(100) NOT NULL,
  `createtime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0' COMMENT '0=未处理 1=已解析',
  PRIMARY KEY (`agentcode`,`platformtype`,`filenumber`,`xmltype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_tag_xml_timer
-- ----------------------------
DROP TABLE IF EXISTS `api_tag_xml_timer`;
CREATE TABLE `api_tag_xml_timer` (
  `agentcode` varchar(10) NOT NULL,
  `platformtype` varchar(10) NOT NULL COMMENT '平台类型：AGIN/HUNTER/XIN',
  `xmltype` varchar(20) NOT NULL COMMENT 'normal/lostAndfound',
  `updatetime` varchar(20) DEFAULT NULL COMMENT '确精到分钟',
  PRIMARY KEY (`agentcode`,`platformtype`,`xmltype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_tb_game
-- ----------------------------
DROP TABLE IF EXISTS `api_tb_game`;
CREATE TABLE `api_tb_game` (
  `GAME_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号，自动增长',
  `GAME_NAME` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`GAME_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='游戏表';

-- ----------------------------
-- Table structure for api_tb_game_kind
-- ----------------------------
DROP TABLE IF EXISTS `api_tb_game_kind`;
CREATE TABLE `api_tb_game_kind` (
  `GAME_KIND_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号，自动增长',
  `GAME_ID` int(11) NOT NULL COMMENT '所属游戏编号',
  `GAME_KIND_NO` varchar(50) NOT NULL COMMENT '编码',
  `GAME_KIND_PARENT` int(11) NOT NULL DEFAULT '0' COMMENT '上级编号（默认为0，顶级）',
  `GAME_KIND_NAME` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`GAME_KIND_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='游戏种类开';

-- ----------------------------
-- Table structure for api_tb_game_type
-- ----------------------------
DROP TABLE IF EXISTS `api_tb_game_type`;
CREATE TABLE `api_tb_game_type` (
  `GAME_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号，自动增长',
  `GAME_ID` int(11) NOT NULL COMMENT '所属游戏编号',
  `GAME_TYPE_NAME` varchar(50) NOT NULL COMMENT '名称',
  `GAME_TYPE_DESC` varchar(50) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`GAME_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='游戏类型表';

-- ----------------------------
-- Table structure for api_tb_max_log
-- ----------------------------
DROP TABLE IF EXISTS `api_tb_max_log`;
CREATE TABLE `api_tb_max_log` (
  `MAX_LOG_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号，自动增长',
  `MAX_LOG_FLAG` varchar(50) DEFAULT NULL COMMENT '代理标志',
  `MAX_LOG_KIND` varchar(50) DEFAULT NULL COMMENT '游戏种类',
  `MAX_LOG_SUBKIND` varchar(50) DEFAULT NULL COMMENT '游戏子种类',
  `MAX_LOG_TYPE` varchar(50) DEFAULT NULL COMMENT '游戏类型',
  `MAX_LOG_VALUE` varchar(50) DEFAULT NULL COMMENT '最大值',
  PRIMARY KEY (`MAX_LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='最大数据记录表';

-- ----------------------------
-- Table structure for api_tb_proxy
-- ----------------------------
DROP TABLE IF EXISTS `api_tb_proxy`;
CREATE TABLE `api_tb_proxy` (
  `PROXY_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号，自动增长',
  `PROXY_NAME` varchar(50) NOT NULL COMMENT '名称',
  `PROXY_SITE` varchar(50) DEFAULT NULL COMMENT '网站',
  `PROXY_KEY1` varchar(50) DEFAULT NULL COMMENT '备选KEY1',
  `PROXY_KEY2` varchar(50) DEFAULT NULL COMMENT '备选KEY2',
  `PROXY_CODE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PROXY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 COMMENT='代理表';

-- ----------------------------
-- Table structure for api_tb_proxy_key
-- ----------------------------
DROP TABLE IF EXISTS `api_tb_proxy_key`;
CREATE TABLE `api_tb_proxy_key` (
  `PROXY_KEY_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号，自动增长',
  `PROXY_ID` int(11) NOT NULL COMMENT '所属代理编号',
  `GAME_ID` int(11) NOT NULL COMMENT '所属游戏游戏编号',
  `PROXY_MD5_KEY` varchar(2000) NOT NULL COMMENT 'MD5密钥',
  `PROXY_API_URL` varchar(200) NOT NULL COMMENT '接口URL',
  PRIMARY KEY (`PROXY_KEY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 COMMENT='代理密钥表';

-- ----------------------------
-- Table structure for api_tgp_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_tgp_gameinfo`;
CREATE TABLE `api_tgp_gameinfo` (
  `ugsbetid` varchar(50) NOT NULL DEFAULT '',
  `txid` varchar(50) DEFAULT NULL,
  `betid` varchar(50) DEFAULT NULL,
  `beton` datetime DEFAULT NULL,
  `betclosedon` datetime DEFAULT NULL,
  `betupdatedon` datetime DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `roundid` varchar(50) DEFAULT NULL,
  `roundstatus` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `riskamt` double DEFAULT NULL,
  `winamt` double DEFAULT NULL,
  `winloss` double DEFAULT NULL,
  `beforebal` double DEFAULT NULL,
  `postbal` double DEFAULT NULL,
  `cur` varchar(10) DEFAULT NULL,
  `gameprovider` varchar(50) DEFAULT NULL,
  `gamename` varchar(50) DEFAULT NULL,
  `gameid` varchar(50) DEFAULT NULL,
  `platformtype` varchar(50) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  `bettype` varchar(50) DEFAULT NULL,
  `playtype` varchar(50) DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `validmoney` double DEFAULT '0',
  PRIMARY KEY (`ugsbetid`),
  KEY `api_tgp_gameinfo_ix1` (`userid`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_ttg_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_ttg_gameinfo`;
CREATE TABLE `api_ttg_gameinfo` (
  `transactionId` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易ID',
  `requestId` varchar(50) DEFAULT NULL COMMENT '请求ID',
  `partnerId` varchar(50) DEFAULT NULL COMMENT '同伴ID',
  `playerId` varchar(50) DEFAULT NULL COMMENT '玩家ID',
  `amount` varchar(50) DEFAULT NULL COMMENT '总额',
  `handId` varchar(50) DEFAULT NULL COMMENT '游戏独特的手Id',
  `transactionType` varchar(50) DEFAULT NULL COMMENT '交易类型',
  `transactionSubType` varchar(50) DEFAULT NULL COMMENT '子交易类型',
  `currency` varchar(50) DEFAULT NULL COMMENT '货币类型',
  `game` varchar(50) DEFAULT NULL COMMENT '游戏名称',
  `transactionDate` varchar(50) DEFAULT NULL COMMENT '交易日期',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `Platformflag` varchar(50) DEFAULT NULL COMMENT '标志',
  PRIMARY KEY (`transactionId`),
  KEY `api_ttg_gameinfo_ix1` (`createtime`),
  KEY `api_ttg_gameinfo_ix2` (`playerId`),
  KEY `idx_api_ttg_gameinfo_handId_transactionId` (`handId`,`transactionId`),
  KEY `idx_api_ttg_gameinfo_handId_transactionSubType_amount` (`handId`,`transactionSubType`,`amount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='TTG游戏表';

-- ----------------------------
-- Table structure for api_user
-- ----------------------------
DROP TABLE IF EXISTS `api_user`;
CREATE TABLE `api_user` (
  `user_id` varchar(32) NOT NULL,
  `platform_id` varchar(32) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_createtime` datetime DEFAULT NULL,
  `user_code` varchar(32) DEFAULT NULL,
  `user_parentcode` varchar(32) DEFAULT NULL,
  `user_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_Relationship_10` (`platform_id`),
  CONSTRAINT `FK_Relationship_10` FOREIGN KEY (`platform_id`) REFERENCES `api_platform` (`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_user_game
-- ----------------------------
DROP TABLE IF EXISTS `api_user_game`;
CREATE TABLE `api_user_game` (
  `usergame_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `usergame_name` varchar(20) DEFAULT NULL,
  `usergame_password` varchar(15) DEFAULT NULL,
  `usergame_gametype` varchar(12) DEFAULT NULL,
  `usergame_time` datetime DEFAULT NULL,
  PRIMARY KEY (`usergame_id`),
  KEY `FK_Relationship_59` (`user_id`),
  KEY `ix_user_game_usergame_name` (`usergame_name`) USING BTREE,
  CONSTRAINT `FK_Relationship_59` FOREIGN KEY (`user_id`) REFERENCES `api_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_user_moneyinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_user_moneyinfo`;
CREATE TABLE `api_user_moneyinfo` (
  `moneyinfo_id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `moneyinfo_type` varchar(12) DEFAULT NULL,
  `moneyinfo_money` decimal(15,2) DEFAULT NULL,
  `moneyinfo_createtime` datetime DEFAULT NULL,
  `moneyinfo_gametype` varchar(15) DEFAULT NULL,
  `moneyinfo_ordernum` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`moneyinfo_id`),
  KEY `FK_Relationship_3` (`user_id`),
  CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`user_id`) REFERENCES `api_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_win88_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_win88_gameinfo`;
CREATE TABLE `api_win88_gameinfo` (
  `pt_gamecode` varchar(32) NOT NULL,
  `pt_username` varchar(32) DEFAULT NULL,
  `pt_windowcode` varchar(32) DEFAULT NULL,
  `pt_gameid` varchar(32) DEFAULT NULL,
  `pt_gametype` varchar(32) DEFAULT NULL,
  `pt_gamename` varchar(200) DEFAULT NULL,
  `pt_bet` decimal(11,2) DEFAULT NULL,
  `pt_win` decimal(11,2) DEFAULT NULL,
  `pt_balance` decimal(11,2) DEFAULT NULL,
  `pt_gamedate` datetime DEFAULT NULL,
  `pt_info` varchar(1000) DEFAULT NULL,
  `pt_sessionid` varchar(32) DEFAULT NULL,
  `pt_progressivebet` varchar(32) DEFAULT NULL,
  `pt_progressivewin` varchar(32) DEFAULT NULL,
  `pt_currentbet` varchar(32) DEFAULT NULL,
  `pt_livenetwork` varchar(32) DEFAULT NULL,
  `pt_rnum` varchar(32) DEFAULT NULL,
  `pt_createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pt_gamecode`),
  KEY `ix_pt_gameinfo_pt_username` (`pt_username`) USING BTREE,
  KEY `ix_pt_gameinfo_pt_gamedate` (`pt_gamedate`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_xcp_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_xcp_gameinfo`;
CREATE TABLE `api_xcp_gameinfo` (
  `xcp_projectid` varchar(32) NOT NULL,
  `xcp_lotteryid` varchar(32) DEFAULT NULL,
  `xcp_username` varchar(32) DEFAULT NULL,
  `xcp_issue` varchar(32) DEFAULT NULL,
  `xcp_isgetprize` varchar(32) DEFAULT NULL,
  `xcp_updatetime` datetime DEFAULT NULL,
  `xcp_writetime` datetime DEFAULT NULL,
  `xcp_totalprice` double(11,4) DEFAULT NULL,
  `xcp_code` text,
  `xcp_bonus` double(11,4) DEFAULT NULL,
  `xcp_codetimes` double(11,2) DEFAULT NULL,
  `xcp_prize` double(11,4) DEFAULT NULL,
  `xcp_createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`xcp_projectid`),
  KEY `ix_xcp_gameinfo_xcp_username` (`xcp_username`) USING BTREE,
  KEY `api_xcp_gameinfo_ix2` (`xcp_writetime`),
  KEY `api_xcp_gameinfo_ix3` (`xcp_createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_yag_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_yag_gameinfo`;
CREATE TABLE `api_yag_gameinfo` (
  `betId` varchar(32) NOT NULL DEFAULT '',
  `user` varchar(25) DEFAULT NULL,
  `gameId` varchar(20) DEFAULT NULL,
  `gameName` varchar(100) DEFAULT NULL,
  `money` double(10,2) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `result` double(10,2) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `validMoney` double(10,0) DEFAULT NULL,
  PRIMARY KEY (`betId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_yoplay_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_yoplay_gameinfo`;
CREATE TABLE `api_yoplay_gameinfo` (
  `billNo` varchar(50) NOT NULL,
  `playerName` varchar(50) DEFAULT NULL,
  `agentCode` varchar(50) DEFAULT NULL,
  `gameCode` varchar(200) DEFAULT NULL,
  `netAmount` varchar(50) DEFAULT NULL,
  `betTime` datetime DEFAULT NULL,
  `gameType` varchar(50) DEFAULT NULL,
  `betAmount` varchar(50) DEFAULT NULL,
  `validBetAmount` varchar(50) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  `playType` varchar(50) DEFAULT NULL,
  `currency` varchar(50) DEFAULT NULL,
  `tableCode` varchar(50) DEFAULT NULL,
  `loginIP` varchar(50) DEFAULT NULL,
  `recalcuTime` varchar(50) DEFAULT NULL,
  `platformType` varchar(50) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `round` varchar(50) DEFAULT NULL,
  `slottype` varchar(50) DEFAULT NULL,
  `result` varchar(2048) DEFAULT NULL,
  `mainbillno` varchar(50) DEFAULT NULL,
  `beforeCredit` varchar(50) DEFAULT NULL,
  `deviceType` varchar(50) DEFAULT NULL,
  `betAmountBase` varchar(50) DEFAULT NULL,
  `betAmountBonus` varchar(50) DEFAULT NULL,
  `netAmountBase` varchar(50) DEFAULT NULL,
  `netAmountBonus` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `validmoney` double DEFAULT NULL,
  PRIMARY KEY (`billNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_zj_gameinfo
-- ----------------------------
DROP TABLE IF EXISTS `api_zj_gameinfo`;
CREATE TABLE `api_zj_gameinfo` (
  `id` bigint(20) NOT NULL,
  `userName` varchar(20) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `gameType` varchar(20) DEFAULT NULL,
  `tableInfoId` varchar(20) DEFAULT NULL,
  `shoeInfoId` varchar(20) DEFAULT NULL,
  `gameInfoId` varchar(10) DEFAULT NULL,
  `tableName` varchar(10) DEFAULT NULL,
  `issueNo` varchar(10) DEFAULT NULL,
  `bankerResult` varchar(100) DEFAULT NULL,
  `resultList` varchar(100) DEFAULT NULL,
  `pokerList` varchar(100) DEFAULT NULL,
  `stakeAmount` double(8,2) DEFAULT NULL,
  `validStake` double(8,2) DEFAULT NULL,
  `winLoss` double(8,2) DEFAULT NULL,
  `comm` double(8,2) DEFAULT NULL,
  `balanceAfter` double(8,2) DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL,
  `Details` text,
  `betTime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `api_zj_gameinfo_ix1` (`userName`),
  KEY `api_zj_gameinfo_ix2` (`betTime`),
  KEY `api_zj_gameinfo_ix3` (`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for baccarath5_balance
-- ----------------------------
DROP TABLE IF EXISTS `baccarath5_balance`;
CREATE TABLE `baccarath5_balance` (
  `employeecode` char(8) NOT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `balance` double DEFAULT '0',
  `updatetime` datetime DEFAULT NULL,
  `levelcode` int(2) DEFAULT '1',
  PRIMARY KEY (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for baccarath5_exchange
-- ----------------------------
DROP TABLE IF EXISTS `baccarath5_exchange`;
CREATE TABLE `baccarath5_exchange` (
  `lsh` varchar(36) NOT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `exchange_in` double DEFAULT NULL,
  `exchange_out` double DEFAULT NULL,
  `exchange_rate` double DEFAULT NULL,
  `exchange_time` datetime DEFAULT NULL,
  `exchange_status` char(2) DEFAULT '0' COMMENT '0=处理中 1=成功 2=失败',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`lsh`),
  KEY `index1` (`employeecode`),
  KEY `index2` (`loginaccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for baccarath5_rebate
-- ----------------------------
DROP TABLE IF EXISTS `baccarath5_rebate`;
CREATE TABLE `baccarath5_rebate` (
  `lsh` varchar(36) NOT NULL,
  `patchno` varchar(20) DEFAULT NULL COMMENT '批次号',
  `ordernumber` varchar(36) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '充值代理账号',
  `money` double DEFAULT NULL COMMENT '充值元宝数',
  `orderamount` double DEFAULT NULL COMMENT '充值金额',
  `orderdate` datetime DEFAULT NULL,
  `currenlevel` varchar(10) DEFAULT NULL COMMENT '代理当前等级',
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `employeecurrenlevel` varchar(10) DEFAULT NULL COMMENT '当前返现代理的等级',
  `rebate` double(10,2) DEFAULT NULL COMMENT '返现比例',
  `rebatemoney` double(16,2) DEFAULT NULL COMMENT '返现金额',
  `rebatestatus` int(11) DEFAULT '1' COMMENT '1=待处理 2=返现成功 3=返现失败',
  `rebatetime` datetime DEFAULT NULL COMMENT '返现处理时间',
  `rebatedesc` varchar(252) DEFAULT NULL COMMENT '返现处理结果说明',
  `remark` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bank
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `bankcode` char(4) COLLATE utf8_bin NOT NULL COMMENT '银行代码',
  `bankname` varchar(16) CHARACTER SET utf8 NOT NULL COMMENT '银行名称',
  `logopath` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片网址',
  `banklogo` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '银行LOGO',
  `bankurl` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '银行URL',
  `displayorder` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`bankcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=963;

-- ----------------------------
-- Table structure for bankcards_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `bankcards_blacklist`;
CREATE TABLE `bankcards_blacklist` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `username` varchar(50) DEFAULT NULL COMMENT '人员姓名',
  `bankcard` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `phoneno` varchar(50) DEFAULT NULL COMMENT '电话号码',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='黑名单列表';

-- ----------------------------
-- Table structure for banks_card_bin
-- ----------------------------
DROP TABLE IF EXISTS `banks_card_bin`;
CREATE TABLE `banks_card_bin` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `bankprefix` varchar(19) NOT NULL,
  `bankcode` varchar(4) DEFAULT NULL,
  `bankname` varchar(50) DEFAULT NULL,
  `banklen` int(11) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=1401 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_all_agent
-- ----------------------------
DROP TABLE IF EXISTS `betting_all_agent`;
CREATE TABLE `betting_all_agent` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(20) DEFAULT NULL COMMENT '玩家用户名',
  `gametype` varchar(15) DEFAULT NULL,
  `gamebigtype` varchar(2) DEFAULT NULL,
  `betday` int(11) DEFAULT NULL COMMENT '投注日期',
  `betmoney` double(10,2) DEFAULT NULL COMMENT '投注金额',
  `netmoney` double(10,2) DEFAULT NULL COMMENT '输赢金额：赢正数，输负数。',
  `validmoney` double(10,2) DEFAULT NULL COMMENT '有效投注额',
  `createtime` datetime DEFAULT NULL COMMENT '记录添加时间',
  `patchno` varchar(20) DEFAULT NULL COMMENT '记录批次号。99开头的表示正常的汇总数据，98开头的表示补单的汇总数据',
  `rate` double(10,4) DEFAULT '0.0000' COMMENT '洗码比例',
  `amount` double(10,2) DEFAULT '0.00' COMMENT '洗码金额',
  `status` int(11) DEFAULT '0' COMMENT '0=生成 1=已发放',
  PRIMARY KEY (`lsh`),
  KEY `index_employeecode` (`employeecode`),
  KEY `index_parentemployeecode` (`parentemployeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='总投注记录天统计报表';

-- ----------------------------
-- Table structure for betting_all_day
-- ----------------------------
DROP TABLE IF EXISTS `betting_all_day`;
CREATE TABLE `betting_all_day` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `User_Name` varchar(20) DEFAULT NULL COMMENT '玩家用户名',
  `Game_Platform` varchar(10) DEFAULT NULL COMMENT '游戏平台',
  `Game_Big_Type` varchar(2) DEFAULT NULL,
  `Game_Type` varchar(45) DEFAULT NULL COMMENT '游戏类型 真人视讯 体育 小游戏 彩票',
  `Bet_Day` date DEFAULT NULL COMMENT '投注日期',
  `Bet_Money` double(10,2) DEFAULT NULL COMMENT '投注金额',
  `Net_Money` double(10,2) DEFAULT NULL COMMENT '输赢金额：赢正数，输负数。',
  `Valid_Money` double(10,2) DEFAULT NULL COMMENT '有效投注额',
  `Rebates_Cash` double(10,2) DEFAULT NULL,
  `Rebates` tinyint(1) DEFAULT '0' COMMENT '是否返水 1 2 ',
  `Add_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录添加时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '打码发放状态 1未发放 2已发放',
  `paytype` tinyint(4) DEFAULT '0',
  `is_daily_agent` tinyint(1) DEFAULT '0',
  `is_weekly_member` tinyint(1) DEFAULT '0',
  `ratio` double DEFAULT '0',
  `payno` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `index_employeecode` (`employeecode`),
  KEY `index_parentemployeecode` (`parentemployeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=34938 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=157 COMMENT='总投注记录天统计报表';

-- ----------------------------
-- Table structure for betting_all_day2
-- ----------------------------
DROP TABLE IF EXISTS `betting_all_day2`;
CREATE TABLE `betting_all_day2` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `User_Name` varchar(20) DEFAULT NULL COMMENT '玩家用户名',
  `Game_Platform` varchar(10) DEFAULT NULL COMMENT '游戏平台',
  `Game_Big_Type` varchar(2) DEFAULT NULL,
  `Game_Type` varchar(45) DEFAULT NULL COMMENT '记录批次号。99开头的表示正常的汇总数据，98开头的表示补单的汇总数据',
  `Bet_Day` int(11) DEFAULT NULL COMMENT '投注日期',
  `Bet_Money` double(10,2) DEFAULT NULL COMMENT '投注金额',
  `Net_Money` double(10,2) DEFAULT NULL COMMENT '输赢金额：赢正数，输负数。',
  `Valid_Money` double(10,2) DEFAULT NULL COMMENT '有效投注额',
  `Add_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录添加时间',
  `patchno` varchar(20) DEFAULT NULL COMMENT '记录批次号。99开头的表示正常的汇总数据，98开头的表示补单的汇总数据',
  PRIMARY KEY (`Id`),
  KEY `index_employeecode` (`employeecode`),
  KEY `index_parentemployeecode` (`parentemployeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='总投注记录天统计报表';

-- ----------------------------
-- Table structure for betting_all_game_winlose_detail
-- ----------------------------
DROP TABLE IF EXISTS `betting_all_game_winlose_detail`;
CREATE TABLE `betting_all_game_winlose_detail` (
  `platformtype` varchar(6) NOT NULL COMMENT 'platformtype: AG, BBIN,HY,OAG,OIBC,OG,PNG,PT,CP,AV,MG,QP,SA,TTG,YGAG,ZJ',
  `platformid` varchar(50) NOT NULL COMMENT '第三方平台唯一标识',
  `employeecode` char(8) NOT NULL,
  `bettime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `betmoney` double(15,2) NOT NULL DEFAULT '0.00',
  `validbet` double(15,2) NOT NULL DEFAULT '0.00',
  `netmoney` double(15,2) NOT NULL DEFAULT '0.00',
  `enterprisecode` char(8) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `gamebigtype` varchar(6) DEFAULT NULL,
  `gametype` varchar(15) DEFAULT NULL,
  `patchno` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`platformtype`,`platformid`),
  KEY `idx_betting_all_game_winlose_detail_ben` (`bettime`,`employeecode`,`netmoney`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='整合所有第三方平台每一局游戏的输赢';

-- ----------------------------
-- Table structure for betting_all_member
-- ----------------------------
DROP TABLE IF EXISTS `betting_all_member`;
CREATE TABLE `betting_all_member` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(20) DEFAULT NULL COMMENT '玩家用户名',
  `gametype` varchar(15) DEFAULT NULL,
  `gamebigtype` varchar(2) DEFAULT NULL,
  `betday` int(11) DEFAULT NULL COMMENT '投注日期',
  `betmoney` double(10,2) DEFAULT NULL COMMENT '投注金额',
  `netmoney` double(10,2) DEFAULT NULL COMMENT '输赢金额：赢正数，输负数。',
  `validmoney` double(10,2) DEFAULT NULL COMMENT '有效投注额',
  `createtime` datetime DEFAULT NULL COMMENT '记录添加时间',
  `patchno` varchar(20) DEFAULT NULL COMMENT '记录批次号。99开头的表示正常的汇总数据，98开头的表示补单的汇总数据',
  `rate` double(10,4) DEFAULT '0.0000' COMMENT '洗码比例',
  `amount` double(10,2) DEFAULT '0.00' COMMENT '洗码金额',
  `status` int(11) DEFAULT '0' COMMENT '0=生成 1=已发放',
  PRIMARY KEY (`lsh`),
  KEY `index_employeecode` (`employeecode`),
  KEY `index_parentemployeecode` (`parentemployeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='总投注记录天统计报表';

-- ----------------------------
-- Table structure for betting_all_plan
-- ----------------------------
DROP TABLE IF EXISTS `betting_all_plan`;
CREATE TABLE `betting_all_plan` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `patchno` varchar(20) NOT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `total_count` int(11) DEFAULT NULL,
  `total_betmoney` double DEFAULT NULL,
  `total_validbet` double DEFAULT NULL,
  `total_netmoney` double DEFAULT NULL,
  `betday` int(11) DEFAULT NULL,
  `operater` varchar(50) DEFAULT NULL,
  `operater_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0' COMMENT '0=已生成计划 1=已取消 2=财务已核准 3=已支付',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_bet67_oblive
-- ----------------------------
DROP TABLE IF EXISTS `betting_bet67_oblive`;
CREATE TABLE `betting_bet67_oblive` (
  `betId` varchar(128) NOT NULL DEFAULT '' COMMENT '局号',
  `gameTypeName` varchar(64) DEFAULT NULL COMMENT '游戏类型',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `betAmount` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `validAmount` decimal(11,2) DEFAULT NULL COMMENT '有效下单金额',
  `winOrLoss` decimal(11,2) DEFAULT NULL COMMENT '结算结果',
  `betStatus` varchar(32) DEFAULT NULL COMMENT '下单状态',
  `betTypeName` varchar(255) DEFAULT NULL COMMENT '下单项',
  `gameResult` varchar(255) DEFAULT NULL COMMENT '结果说明',
  `tableName` varchar(255) DEFAULT NULL COMMENT '桌台名称',
  `commissionName` varchar(255) DEFAULT NULL COMMENT '桌台类型',
  `gameStartTime` datetime DEFAULT NULL COMMENT '游戏开始时间',
  `gameEndTime` datetime DEFAULT NULL COMMENT '游戏结束时间',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '企业品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '员工上级编码',
  `gamebigtype` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`betId`),
  KEY `IDX_employeecode` (`employeecode`) USING BTREE,
  KEY `IDX_enterprisecode` (`enterprisecode`) USING BTREE,
  KEY `IDX_brandcode` (`brandcode`) USING BTREE,
  KEY `IDX_parentemployeecode` (`parentemployeecode`) USING BTREE,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betId` (`betId`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_betStatus` (`betStatus`) USING BTREE,
  KEY `IDX_gameStartTime` (`gameStartTime`) USING BTREE,
  KEY `IDX_gameEndTime` (`gameEndTime`) USING BTREE,
  KEY `IDX_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_bet67_oglive
-- ----------------------------
DROP TABLE IF EXISTS `betting_bet67_oglive`;
CREATE TABLE `betting_bet67_oglive` (
  `betId` varchar(255) NOT NULL DEFAULT '' COMMENT '注单号',
  `gameRoundId` varchar(32) DEFAULT NULL COMMENT '局号',
  `gameTypeName` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `betAmount` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `winOrLoss` decimal(11,2) DEFAULT NULL COMMENT '结算结果',
  `betTypeName` varchar(64) DEFAULT NULL COMMENT '下单',
  `isBill` varchar(32) DEFAULT NULL COMMENT '无效单或已结算',
  `platformName` varchar(32) DEFAULT NULL COMMENT '厅名',
  `gameKind` varchar(64) DEFAULT NULL COMMENT '游戏名称类型',
  `balance` decimal(11,2) DEFAULT NULL,
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '企业品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '员工上级编码',
  `gamebigtype` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`betId`),
  KEY `IDX_employeecode` (`employeecode`) USING BTREE,
  KEY `IDX_enterprisecode` (`enterprisecode`) USING BTREE,
  KEY `IDX_brandcode` (`brandcode`) USING BTREE,
  KEY `IDX_parentemployeecode` (`parentemployeecode`) USING BTREE,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_isBill` (`isBill`) USING BTREE,
  KEY `IDX_betId` (`betId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_bet67_oglottery
-- ----------------------------
DROP TABLE IF EXISTS `betting_bet67_oglottery`;
CREATE TABLE `betting_bet67_oglottery` (
  `betId` varchar(255) NOT NULL DEFAULT '' COMMENT '投注ID',
  `gameName` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `phaseNum` int(11) DEFAULT NULL COMMENT '期数',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `betAmount` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `winOrLoss` decimal(11,2) DEFAULT NULL COMMENT '结算结果',
  `betTypeName` varchar(255) DEFAULT NULL COMMENT '下单项',
  `betStatus` varchar(255) DEFAULT NULL COMMENT '状态',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '企业品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '员工上级编码',
  `gamebigtype` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`betId`),
  KEY `IDX_employeecode` (`employeecode`) USING BTREE,
  KEY `IDX_enterprisecode` (`enterprisecode`) USING BTREE,
  KEY `IDX_brandcode` (`brandcode`) USING BTREE,
  KEY `IDX_parentemployeecode` (`parentemployeecode`) USING BTREE,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betId` (`betId`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_betStatus` (`betStatus`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_bet67_sllive
-- ----------------------------
DROP TABLE IF EXISTS `betting_bet67_sllive`;
CREATE TABLE `betting_bet67_sllive` (
  `betId` varchar(64) NOT NULL DEFAULT '' COMMENT '注单号',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `desNo` varchar(32) DEFAULT NULL COMMENT '台号',
  `betMoney` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `round` varchar(32) DEFAULT NULL COMMENT '局数',
  `betResult` decimal(11,2) DEFAULT NULL COMMENT '结算结果',
  `gameCode` varchar(32) DEFAULT NULL COMMENT '游戏类型',
  `betRgn` varchar(255) DEFAULT NULL COMMENT '投注码',
  `chipLeft` decimal(11,2) DEFAULT NULL COMMENT '本局余额',
  `openAnswer` varchar(255) DEFAULT NULL COMMENT '结果',
  `openDetail` varchar(255) DEFAULT NULL COMMENT '发牌详情',
  `bSuccess` varchar(32) DEFAULT NULL COMMENT '状态',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '企业品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '员工上级编码',
  `gamebigtype` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`betId`),
  KEY `IDX_employeecode` (`employeecode`) USING BTREE,
  KEY `IDX_enterprisecode` (`enterprisecode`) USING BTREE,
  KEY `IDX_brandcode` (`brandcode`) USING BTREE,
  KEY `IDX_parentemployeecode` (`parentemployeecode`) USING BTREE,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betId` (`betId`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_round` (`round`) USING BTREE,
  KEY `IDX_bSuccess` (`bSuccess`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_bet67_tssport
-- ----------------------------
DROP TABLE IF EXISTS `betting_bet67_tssport`;
CREATE TABLE `betting_bet67_tssport` (
  `betId` varchar(128) NOT NULL DEFAULT '' COMMENT '交易号',
  `isNormalWager` varchar(64) DEFAULT NULL COMMENT '是否普通注单',
  `playTypeName` varchar(64) DEFAULT NULL COMMENT '类别名称',
  `matchName` varchar(64) DEFAULT NULL COMMENT '球赛名称',
  `eventName` varchar(64) DEFAULT NULL COMMENT '赛式名称',
  `matchDate` datetime DEFAULT NULL COMMENT '赛式时间',
  `betTypeName` varchar(128) DEFAULT NULL COMMENT '下单项目名称',
  `teamBetName` varchar(64) DEFAULT NULL COMMENT '下单方式名称',
  `betTime` datetime DEFAULT NULL COMMENT '下单时间',
  `wagerOdds` varchar(16) DEFAULT NULL COMMENT '赔率',
  `wagerStake` decimal(11,2) DEFAULT NULL COMMENT '下单金额',
  `winAmt` varchar(255) DEFAULT NULL COMMENT '结算结果',
  `score` varchar(64) DEFAULT NULL COMMENT '比分',
  `betStatus` varchar(32) DEFAULT NULL COMMENT '注单状态名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `handicap` varchar(64) DEFAULT NULL COMMENT '让球',
  `billTime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `isBill` char(2) DEFAULT NULL COMMENT '是否结算',
  `betDetail` varchar(1024) DEFAULT NULL COMMENT '订单明细【html数据】',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '企业品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '员工上级编码',
  `gamebigtype` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`betId`),
  KEY `IDX_employeecode` (`employeecode`) USING BTREE,
  KEY `IDX_enterprisecode` (`enterprisecode`) USING BTREE,
  KEY `IDX_brandcode` (`brandcode`) USING BTREE,
  KEY `IDX_parentemployeecode` (`parentemployeecode`) USING BTREE,
  KEY `IDX_betId` (`betId`) USING BTREE,
  KEY `IDX_userName` (`userName`) USING BTREE,
  KEY `IDX_betTime` (`betTime`) USING BTREE,
  KEY `IDX_billTime` (`billTime`) USING BTREE,
  KEY `IDX_isBill` (`isBill`) USING BTREE,
  KEY `IDX_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_ebet
-- ----------------------------
DROP TABLE IF EXISTS `betting_ebet`;
CREATE TABLE `betting_ebet` (
  `bethistoryid` varchar(50) NOT NULL,
  `gametype` varchar(10) DEFAULT NULL,
  `gamename` varchar(20) DEFAULT NULL,
  `betmap` varchar(2000) DEFAULT NULL,
  `judgeresult` varchar(2000) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `payouttime` varchar(50) DEFAULT NULL,
  `gametime` varchar(50) DEFAULT NULL,
  `roundno` varchar(50) DEFAULT NULL,
  `subchannelid` varchar(10) DEFAULT NULL,
  `validbet` varchar(10) DEFAULT NULL,
  `payout` varchar(10) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `bankercards` varchar(2000) DEFAULT NULL,
  `tigercard` varchar(2000) DEFAULT NULL,
  `dragoncard` varchar(2000) DEFAULT NULL,
  `numbercard` varchar(2000) DEFAULT NULL,
  `alldices` varchar(2000) DEFAULT NULL,
  `playercards` varchar(2000) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`bethistoryid`),
  KEY `betting_ebet_ix1` (`username`),
  KEY `betting_ebet_ix2` (`enterprisecode`),
  KEY `betting_ebet_ix3` (`employeecode`),
  KEY `betting_ebet_ix4` (`parentemployeecode`),
  KEY `betting_ebet_ix5` (`loginaccount`),
  KEY `betting_ebet_ix6` (`bettime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_eibc
-- ----------------------------
DROP TABLE IF EXISTS `betting_eibc`;
CREATE TABLE `betting_eibc` (
  `transid` varchar(50) NOT NULL,
  `playername` varchar(50) DEFAULT NULL,
  `transactiontime` varchar(30) DEFAULT NULL,
  `matchid` varchar(20) DEFAULT NULL,
  `leagueid` varchar(20) DEFAULT NULL,
  `leaguename` varchar(1000) DEFAULT NULL,
  `sporttype` varchar(20) DEFAULT NULL,
  `awayid` varchar(20) DEFAULT NULL,
  `awayidname` varchar(1000) DEFAULT NULL,
  `homeid` varchar(20) DEFAULT NULL,
  `homeidname` varchar(1000) DEFAULT NULL,
  `matchdatetime` varchar(50) DEFAULT NULL,
  `bettype` varchar(20) DEFAULT NULL,
  `parlayrefno` varchar(20) DEFAULT NULL,
  `betteam` varchar(100) DEFAULT NULL,
  `hdp` varchar(20) DEFAULT NULL,
  `awayhdp` varchar(20) DEFAULT NULL,
  `homehdp` varchar(20) DEFAULT NULL,
  `odds` varchar(20) DEFAULT NULL,
  `oddstype` varchar(20) DEFAULT NULL,
  `awayscore` varchar(20) DEFAULT NULL,
  `homescore` varchar(20) DEFAULT NULL,
  `islive` varchar(20) DEFAULT NULL,
  `lastballno` varchar(20) DEFAULT NULL,
  `ticketstatus` varchar(20) DEFAULT NULL,
  `stake` varchar(20) DEFAULT NULL,
  `winloseamount` varchar(20) DEFAULT NULL,
  `winlostdatetime` varchar(30) DEFAULT NULL,
  `currency` varchar(20) DEFAULT NULL,
  `versionkey` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `nettime` datetime DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`transid`),
  KEY `index1` (`playername`),
  KEY `index2` (`enterprisecode`),
  KEY `index3` (`employeecode`),
  KEY `index4` (`loginaccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_gb
-- ----------------------------
DROP TABLE IF EXISTS `betting_gb`;
CREATE TABLE `betting_gb` (
  `settleid` varchar(20) NOT NULL,
  `betid` varchar(20) DEFAULT NULL,
  `betgrpno` varchar(30) DEFAULT NULL,
  `tpcode` varchar(20) DEFAULT NULL,
  `gbsn` varchar(20) DEFAULT NULL,
  `memberid` varchar(20) DEFAULT NULL,
  `curcode` varchar(20) DEFAULT NULL,
  `betdt` varchar(30) DEFAULT NULL,
  `bettype` varchar(20) DEFAULT NULL,
  `bettypeparam1` varchar(20) DEFAULT NULL,
  `bettypeparam2` varchar(20) DEFAULT NULL,
  `wintype` varchar(20) DEFAULT NULL,
  `hxmguid` varchar(20) DEFAULT NULL,
  `initbetamt` varchar(20) DEFAULT NULL,
  `realbetamt` varchar(20) DEFAULT NULL,
  `holdingamt` varchar(20) DEFAULT NULL,
  `initbetrate` varchar(20) DEFAULT NULL,
  `realbetrate` varchar(20) DEFAULT NULL,
  `prewinamt` varchar(20) DEFAULT NULL,
  `betresult` varchar(20) DEFAULT NULL,
  `wlamt` varchar(20) DEFAULT NULL,
  `refundbetamt` varchar(20) DEFAULT NULL,
  `ticketbetamt` varchar(20) DEFAULT NULL,
  `ticketresult` varchar(20) DEFAULT NULL,
  `ticketwlamt` varchar(20) DEFAULT NULL,
  `settledt` varchar(30) DEFAULT NULL,
  `kenolist` varchar(2000) DEFAULT NULL,
  `lottolist` varchar(2000) DEFAULT NULL,
  `ssclist` varchar(2000) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `pkxlist` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`settleid`),
  KEY `betting_gb_ix1` (`memberid`),
  KEY `betting_gb_ix2` (`enterprisecode`),
  KEY `betting_gb_ix3` (`employeecode`),
  KEY `betting_gb_ix4` (`parentemployeecode`),
  KEY `betting_gb_ix5` (`loginaccount`),
  KEY `betting_gb_ix6` (`bettime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_gg
-- ----------------------------
DROP TABLE IF EXISTS `betting_gg`;
CREATE TABLE `betting_gg` (
  `autoid` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏编码',
  `gameid` varchar(10) DEFAULT NULL,
  `cuuency` varchar(10) DEFAULT NULL COMMENT '货币',
  `linkid` varchar(20) DEFAULT NULL,
  `accountno` varchar(20) DEFAULT NULL COMMENT '用户名',
  `betmoney` double DEFAULT NULL COMMENT '投注金额',
  `netmoney` double DEFAULT NULL COMMENT '输赢',
  `bettime` datetime DEFAULT NULL COMMENT '投注时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建记录时间',
  `platformflag` varchar(30) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`autoid`),
  KEY `betting_gg_ix1` (`createtime`),
  KEY `betting_gg_ix2` (`enterprisecode`),
  KEY `betting_gg_ix3` (`brandcode`),
  KEY `betting_gg_ix4` (`employeecode`),
  KEY `betting_gg_ix6` (`loginaccount`),
  KEY `betting_gg_ix7` (`bettime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_ggp
-- ----------------------------
DROP TABLE IF EXISTS `betting_ggp`;
CREATE TABLE `betting_ggp` (
  `lsh` varchar(36) NOT NULL,
  `gamedate` varchar(10) NOT NULL,
  `username` varchar(50) NOT NULL DEFAULT '',
  `ngr` varchar(10) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT 'QP',
  `status` int(11) DEFAULT '0',
  UNIQUE KEY `index1` (`gamedate`,`username`),
  KEY `index2` (`enterprisecode`,`loginaccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_hab
-- ----------------------------
DROP TABLE IF EXISTS `betting_hab`;
CREATE TABLE `betting_hab` (
  `friendlygameinstanceid` varchar(36) NOT NULL,
  `playerid` varchar(36) DEFAULT NULL,
  `brandid` varchar(36) DEFAULT NULL,
  `username` varchar(36) DEFAULT NULL,
  `brandgameid` varchar(36) DEFAULT NULL,
  `gamekeyname` varchar(36) DEFAULT NULL,
  `gametypeid` varchar(36) DEFAULT NULL,
  `dtstarted` datetime DEFAULT NULL,
  `dtcompleted` datetime DEFAULT NULL,
  `gameinstanceid` varchar(36) DEFAULT NULL,
  `stake` double DEFAULT NULL,
  `payout` double DEFAULT NULL,
  `jackpotwin` double DEFAULT NULL,
  `jackpotcontribution` double DEFAULT NULL,
  `currencycode` varchar(10) DEFAULT NULL,
  `channeltypeid` varchar(10) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`friendlygameinstanceid`),
  KEY `index_username` (`username`),
  KEY `betting_hab_ix2` (`bettime`),
  KEY `betting_hab_ix3` (`enterprisecode`),
  KEY `betting_hab_ix4` (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_hq_ag
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_ag`;
CREATE TABLE `betting_hq_ag` (
  `bill_No` varchar(32) NOT NULL COMMENT '注单流水号(满足平台的唯一约束条件)',
  `brandcode` varchar(20) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '用户登录账号',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏大类',
  `player_Name` varchar(50) DEFAULT NULL COMMENT '玩家账号',
  `agent_Code` varchar(55) DEFAULT NULL COMMENT '代理商编号',
  `game_Code` varchar(50) DEFAULT NULL COMMENT '游戏局号',
  `net_Amount` double(11,2) DEFAULT NULL COMMENT '玩家输赢额度',
  `bet_Time` timestamp NULL DEFAULT NULL COMMENT '投注时间',
  `game_Type` varchar(10) DEFAULT NULL COMMENT '游戏类型',
  `bet_Amount` double(11,2) DEFAULT NULL COMMENT '投注金额',
  `valid_Bet_Amount` double(11,2) DEFAULT NULL COMMENT '有效投注额度',
  `flag` int(1) DEFAULT NULL COMMENT '结算状态',
  `play_Type` varchar(50) DEFAULT NULL COMMENT '游戏玩法',
  `currency` varchar(5) DEFAULT NULL COMMENT '货币类型',
  `table_Code` varchar(10) DEFAULT NULL COMMENT '桌子编号',
  `login_IP` varchar(16) DEFAULT NULL COMMENT '玩家 IP',
  `recalcu_Time` timestamp NULL DEFAULT NULL COMMENT '注单重新派彩时间',
  `platform_Id` varchar(50) DEFAULT NULL COMMENT '平台编号(通常為空)',
  `platform_Type` varchar(10) DEFAULT NULL COMMENT '平台類型',
  `remark` varchar(55) DEFAULT NULL COMMENT '輪盤遊戲  -  額外資訊',
  `round` varchar(55) DEFAULT NULL,
  `stringex` varchar(55) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT NULL COMMENT '数据导出时间',
  `result` text,
  `before_Credit` varchar(50) DEFAULT NULL,
  `device_Type` varchar(20) DEFAULT NULL COMMENT '设备类型',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`bill_No`),
  KEY `IDX_betting_hq_ag_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_ag_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_ag_enterprisecode` (`enterprisecode`),
  KEY `UK_betting_hq_ag_parentemployeecode` (`parentemployeecode`),
  KEY `betting_hq_ag_ix6` (`createtime`),
  KEY `idx_betting_hq_ag_bepn` (`bet_Time`,`employeecode`,`parentemployeecode`,`net_Amount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=377;

-- ----------------------------
-- Table structure for betting_hq_bbin
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_bbin`;
CREATE TABLE `betting_hq_bbin` (
  `bbin_Wagers_Id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '注单号码',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '登陆账号',
  `bbin_User_Name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '帐号',
  `bbin_Wagers_Date` timestamp NULL DEFAULT NULL COMMENT '下注时间',
  `bbin_Serial_Id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '局号',
  `bbin_Round_No` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '场次',
  `bbin_Game_Type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏种类',
  `bbin_Game_Code` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '桌号',
  `bbin_Result` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '开牌结果',
  `bbin_Result_Type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '注单结果\n-1：注销、0：未结算',
  `bbin_Card` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结果牌',
  `bbin_Bet_Amount` double(10,2) DEFAULT NULL COMMENT '下注金额',
  `bbin_Payoff` double(10,2) DEFAULT NULL COMMENT '派彩金额',
  `bbin_Currency` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `bbin_Exchange_Rate` double(10,2) DEFAULT NULL COMMENT '与人民币的汇率',
  `bbin_Commissionable` double(10,2) DEFAULT NULL COMMENT '会员有效投注额',
  `bbin_Commission` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '退水',
  `bbin_Origin` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '1.行动装置下单：M \n1-1.ios手机：MI1 \n1-2.ios平板：MI2 \n1-3.Android手机：MA1 \n1-4.Android平板：MA2 \n2.计算机下单：P',
  `bbin_Modified_Date` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '注单变更时间',
  `bbin_Is_Paid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT 'Y：已派彩、N：未派彩',
  `bbin_Order_Date` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '帐务时间',
  `bbin_UPTIME` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `bbin_createtime` timestamp NULL DEFAULT NULL COMMENT '平台更新时间',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gamebigtype` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  `bbin_Wager_Detail` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`bbin_Wagers_Id`),
  KEY `IDX_betting_hq_bbin_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_bbin_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_bbin_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_hq_bbin_parentemployeecode` (`parentemployeecode`),
  KEY `betting_hq_bbin_ix6` (`bbin_createtime`),
  KEY `idx_betting_hq_bbin_bepP` (`bbin_Wagers_Date`,`employeecode`,`parentemployeecode`,`bbin_Payoff`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=372 COMMENT='环球BBIN';

-- ----------------------------
-- Table structure for betting_hq_nhq
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_nhq`;
CREATE TABLE `betting_hq_nhq` (
  `Betting_ID` varchar(20) NOT NULL,
  `Betting_NO` varchar(20) NOT NULL DEFAULT '' COMMENT '注单号	\n',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '用户编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '用户账号',
  `Betting_Credits` double(15,2) DEFAULT NULL COMMENT '投注积分',
  `Pre_Credits_Point` double(20,2) DEFAULT NULL COMMENT '投注前积分',
  `Game_Result` varchar(255) DEFAULT NULL COMMENT '游戏结果	\n',
  `Game_Room_Name` varchar(50) DEFAULT NULL COMMENT '游戏大厅	\n',
  `Agent_Account` varchar(20) DEFAULT NULL COMMENT '代理帐号	\n',
  `Gambling_Code` varchar(32) DEFAULT NULL COMMENT '赌局号	\n',
  `After_Payout_Credits` double(20,2) DEFAULT NULL COMMENT '派彩积分	\n',
  `User_Account` varchar(20) DEFAULT NULL COMMENT '会员帐号	\n',
  `Game_Type` varchar(5) DEFAULT NULL COMMENT '游戏类型	\n',
  `Betting_Date` timestamp NULL DEFAULT NULL COMMENT '下注时间	\n',
  `Dealer_Name` varchar(50) DEFAULT NULL COMMENT '荷官	\n\n',
  `Game_Name` varchar(20) DEFAULT NULL COMMENT '游戏名称	\n',
  `Set_Game_No` varchar(20) DEFAULT NULL COMMENT '靴号局号	\n',
  `Is_Payout` tinyint(4) DEFAULT NULL COMMENT '是否派彩	\n',
  `Parent_User_ID` varchar(10) DEFAULT NULL COMMENT '上级ID	\n',
  `Winning_Credits` double(15,2) DEFAULT NULL COMMENT '输赢积分	\n',
  `Betting_Point` varchar(20) DEFAULT NULL COMMENT '下注点	\n',
  `Table_Name` varchar(20) DEFAULT NULL COMMENT '桌名	\n',
  `Track_IP` varchar(30) DEFAULT NULL COMMENT '追踪IP	\n',
  `Create_Time` timestamp NULL DEFAULT NULL COMMENT '导入时间	\n',
  `Wash_Code_Credits` double(20,2) DEFAULT NULL COMMENT '游码积分	\n',
  `Update_Time` timestamp NULL DEFAULT NULL COMMENT '更新时间	\n',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`Betting_ID`),
  KEY `IDX_betting_hq_nhq_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_nhq_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_nhq_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_hq_nhq_parentemployeecode` (`parentemployeecode`),
  KEY `betting_hq_nhq_ix5` (`Create_Time`),
  KEY `idx_betting_hq_nhq_BepW` (`Betting_Date`,`employeecode`,`parentemployeecode`,`Winning_Credits`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=409;

-- ----------------------------
-- Table structure for betting_hq_og_ag
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_og_ag`;
CREATE TABLE `betting_hq_og_ag` (
  `aoi_Product_Id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '唯一ID',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '登陆账号',
  `aoi_User_Name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `aoi_Game_Record_Id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏结果',
  `aoi_Order_Number` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '单号',
  `aoi_Table_Id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '桌号',
  `aoi_Stage` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '局号',
  `aoi_Inning` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '靴号',
  `aoi_Game_Name_Id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏类型',
  `aoi_Game_Betting_Kind` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '投注类型',
  `aoi_Game_Betting_Content` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '番摊,轮盘,骰宝的投注区',
  `aoi_Result_Type` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏结果类型',
  `aoi_Betting_Amount` double(10,2) DEFAULT NULL COMMENT '投注金额',
  `aoi_Compensate_Rate` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '赔率',
  `aoi_Win_Lose_Amount` double(10,2) DEFAULT NULL COMMENT '输赢金额',
  `aoi_Balance` double(10,2) DEFAULT NULL COMMENT '余额',
  `aoi_Add_Time` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `aoi_Vendor_Id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商Id',
  `aoi_Valid_Amount` double(10,2) DEFAULT NULL COMMENT '有效投注额',
  `aoi_PlatformID` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `aoi_createtime` timestamp NULL DEFAULT NULL COMMENT '平台更新时间',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gamebigtype` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`aoi_Product_Id`),
  KEY `IDX_betting_hq_og_ag_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_og_ag_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_og_ag_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_hq_og_ag_parentemployeecode` (`parentemployeecode`),
  KEY `betting_hq_og_ag_ix5` (`aoi_Add_Time`),
  KEY `betting_hq_og_ag_ix6` (`aoi_createtime`),
  KEY `idx_betting_hq_og_ag_aepW` (`aoi_Add_Time`,`employeecode`,`parentemployeecode`,`aoi_Win_Lose_Amount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=448 COMMENT='环球_OG_AG';

-- ----------------------------
-- Table structure for betting_hq_og_ibc
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_og_ibc`;
CREATE TABLE `betting_hq_og_ibc` (
  `ibc_rowguid` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '唯一ID',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `ibc_ballid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '场次ID',
  `ibc_balltime` timestamp NULL DEFAULT NULL COMMENT '开赛时间',
  `ibc_content` text COLLATE utf8_bin COMMENT '下注内容',
  `ibc_curpl` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '赔率',
  `ibc_ds` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '单双',
  `ibc_dxc` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '大小个数',
  `ibc_isbk` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏类别',
  `ibc_iscancel` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '是否取消',
  `ibc_isdanger` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '是否危险球',
  `ibc_isjs` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '是否结算',
  `ibc_lose` double(10,2) DEFAULT NULL COMMENT '输掉的金额',
  `ibc_matchid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '联赛ID',
  `ibc_moneyrate` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '货币比率',
  `ibc_orderid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '投注单号',
  `ibc_recard` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '红牌',
  `ibc_result` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '赛事结果',
  `ibc_rqc` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '让球个数',
  `ibc_rqteam` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '让球队伍',
  `ibc_sportid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '下注球类ID号',
  `ibc_tballtime` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '走地时间',
  `ibc_thisdate` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '结算日期',
  `ibc_truewin` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '赢半，赢，输半，输，和',
  `ibc_tzip` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '投注IP，ibc 不提供，为空就行',
  `ibc_tzmoney` double(10,2) DEFAULT NULL COMMENT '下注金额',
  `ibc_tzteam` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '下注队伍',
  `ibc_tztype` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '下注类别',
  `ibc_updatetime` timestamp NULL DEFAULT NULL COMMENT '下注时间',
  `ibc_username` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '会员名',
  `ibc_win` double(10,2) DEFAULT NULL COMMENT '赢的钱',
  `ibc_zdbf` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '走地比分',
  `ibc_vendorid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '注单更新顺序号',
  `ibc_createtime` timestamp NULL DEFAULT NULL COMMENT '平台更新时间',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL,
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `gamebigtype` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`ibc_rowguid`),
  KEY `idx_UserName` (`ibc_username`),
  KEY `IDX_betting_hq_og_ibc_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_og_ibc_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_og_ibc_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_hq_og_ibc_parentemployeecode` (`parentemployeecode`),
  KEY `betting_hq_og_ibc_ix6` (`ibc_balltime`),
  KEY `betting_hq_og_ibc_ix7` (`ibc_createtime`),
  KEY `idx_betting_hq_og_ibc_ibc_updatetime` (`ibc_updatetime`),
  KEY `idx_betting_hq_og_ibc_uepwlose` (`ibc_updatetime`,`employeecode`,`parentemployeecode`,`ibc_win`,`ibc_lose`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1867 COMMENT='环球OG_IBC';

-- ----------------------------
-- Table structure for betting_hq_og_og
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_og_og`;
CREATE TABLE `betting_hq_og_og` (
  `aoi_Product_Id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '唯一ID',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `aoi_User_Name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `aoi_Game_Record_Id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏结果',
  `aoi_Order_Number` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '单号',
  `aoi_Table_Id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '桌号',
  `aoi_Stage` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '局号',
  `aoi_Inning` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '靴号',
  `aoi_Game_Name_Id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏类型',
  `aoi_Game_Betting_Kind` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '投注类型',
  `aoi_Game_Betting_Content` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '番摊,轮盘,骰宝的投注区',
  `aoi_Result_Type` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏结果类型',
  `aoi_Betting_Amount` double(10,2) DEFAULT NULL COMMENT '投注金额',
  `aoi_Compensate_Rate` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '赔率',
  `aoi_Win_Lose_Amount` double(10,2) DEFAULT NULL COMMENT '输赢金额',
  `aoi_Balance` double(10,2) DEFAULT NULL COMMENT '余额',
  `aoi_Add_Time` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `aoi_Vendor_Id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商Id',
  `aoi_Valid_Amount` double(10,2) DEFAULT NULL COMMENT '有效投注额',
  `aoi_Platform_ID` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `aoi_createtime` timestamp NULL DEFAULT NULL COMMENT '平台更新时间',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL,
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `gamebigtype` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`aoi_Product_Id`),
  KEY `idx_GameType` (`aoi_Game_Name_Id`),
  KEY `idx_UserName` (`aoi_User_Name`),
  KEY `UK_betting_hq_og_og_employeecode` (`employeecode`),
  KEY `UK_betting_hq_og_og_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_hq_og_og_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_og_og_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_og_og_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_hq_og_og_parentemployeecode` (`parentemployeecode`),
  KEY `betting_hq_og_og_ix9` (`aoi_Add_Time`),
  KEY `betting_hq_og_og_ix10` (`aoi_createtime`),
  KEY `idx_betting_hq_og_AepWLose` (`aoi_Add_Time`,`employeecode`,`parentemployeecode`,`aoi_Win_Lose_Amount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=305 COMMENT='环球_OG_OG';

-- ----------------------------
-- Table structure for betting_hq_png
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_png`;
CREATE TABLE `betting_hq_png` (
  `bill_No` varchar(32) NOT NULL COMMENT '注单流水号(满足平台的唯一约束条件)',
  `brandcode` varchar(20) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '用户登录账号',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏大类',
  `player_Name` varchar(50) DEFAULT NULL COMMENT '玩家账号',
  `agent_Code` varchar(55) DEFAULT NULL COMMENT '代理商编号',
  `game_Code` varchar(50) DEFAULT NULL COMMENT '游戏局号',
  `net_Amount` double(11,2) DEFAULT NULL COMMENT '玩家输赢额度',
  `bet_Time` timestamp NULL DEFAULT NULL COMMENT '投注时间',
  `game_Type` varchar(10) DEFAULT NULL COMMENT '游戏类型',
  `bet_Amount` double(11,2) DEFAULT NULL COMMENT '投注金额',
  `valid_Bet_Amount` double(11,2) DEFAULT NULL COMMENT '有效投注额度',
  `flag` int(1) DEFAULT NULL COMMENT '结算状态',
  `play_Type` varchar(50) DEFAULT NULL COMMENT '游戏玩法',
  `currency` varchar(5) DEFAULT NULL COMMENT '货币类型',
  `table_Code` varchar(10) DEFAULT NULL COMMENT '桌子编号',
  `login_IP` varchar(16) DEFAULT NULL COMMENT '玩家 IP',
  `recalcu_Time` timestamp NULL DEFAULT NULL COMMENT '注单重新派彩时间',
  `platform_Id` varchar(50) DEFAULT NULL COMMENT '平台编号(通常為空)',
  `platform_Type` varchar(10) DEFAULT NULL COMMENT '平台類型',
  `remark` varchar(55) DEFAULT NULL COMMENT '輪盤遊戲  -  額外資訊',
  `round` varchar(55) DEFAULT NULL,
  `stringex` varchar(55) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT NULL COMMENT '数据导出时间',
  `result` text,
  `before_Credit` varchar(50) DEFAULT NULL,
  `device_Type` varchar(20) DEFAULT NULL COMMENT '设备类型',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`bill_No`),
  KEY `IDX_betting_hq_ag_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_ag_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_ag_enterprisecode` (`enterprisecode`),
  KEY `UK_betting_hq_ag_parentemployeecode` (`parentemployeecode`),
  KEY `index4` (`bet_Time`),
  KEY `betting_hq_ag_ix6` (`createtime`),
  KEY `idx_betting_hq_png_bepn` (`bet_Time`,`employeecode`,`parentemployeecode`,`net_Amount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_hq_pt
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_pt`;
CREATE TABLE `betting_hq_pt` (
  `pt_gamecode` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '游戏编号',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `pt_gametype` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏类型',
  `pt_gameid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏id',
  `pt_gamename` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '游戏名称',
  `pt_username` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `pt_windowcode` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '窗口代码',
  `pt_bet` double(10,2) DEFAULT NULL COMMENT '投注额',
  `pt_win` double(10,2) DEFAULT NULL COMMENT '赢',
  `pt_balance` double(10,2) DEFAULT NULL COMMENT '余额',
  `pt_gamedate` timestamp NULL DEFAULT NULL COMMENT '游戏时间',
  `pt_info` varchar(1500) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏信息',
  `pt_sessionid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT 'sessionID',
  `pt_progressivebet` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '逐步下注',
  `pt_progressivewin` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '进步的胜利',
  `pt_currentbet` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '最近赌注',
  `pt_livenetwork` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '活网工作',
  `pt_rnum` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '序号',
  `pt_createtime` timestamp NULL DEFAULT NULL COMMENT '平台更新时间',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL,
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `gamebigtype` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`pt_gamecode`),
  KEY `idx_UserName` (`pt_username`),
  KEY `IDX_betting_hq_pt_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_pt_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_pt_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_hq_pt_parentemployeecode` (`parentemployeecode`),
  KEY `betting_hq_pt_ix6` (`pt_gamedate`),
  KEY `betting_hq_pt_ix7` (`pt_createtime`),
  KEY `idx_betting_hq_pt_gepwbet` (`pt_gamedate`,`employeecode`,`parentemployeecode`,`pt_win`,`pt_bet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=461 COMMENT='环球PT';

-- ----------------------------
-- Table structure for betting_hq_xcp
-- ----------------------------
DROP TABLE IF EXISTS `betting_hq_xcp`;
CREATE TABLE `betting_hq_xcp` (
  `xcp_projectid` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '投注id',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `xcp_bonus` double(10,2) DEFAULT NULL COMMENT '返奖',
  `xcp_prize` double(10,2) DEFAULT NULL COMMENT '奖金额',
  `xcp_writetime` timestamp NULL DEFAULT NULL COMMENT '下注时间',
  `xcp_username` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `xcp_code` text COLLATE utf8_bin COMMENT '下注内容',
  `xcp_totalprice` double(10,2) DEFAULT NULL COMMENT '下注金额',
  `xcp_isgetprize` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '是否中奖，1=未中间，2=中奖',
  `xcp_updatetime` timestamp NULL DEFAULT NULL COMMENT '派奖时间',
  `xcp_lotteryid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '彩种，彩种编号参照附录',
  `xcp_issue` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '奖期',
  `xcp_codetimes` double(10,2) DEFAULT NULL COMMENT '倍数',
  `xcp_createtime` timestamp NULL DEFAULT NULL COMMENT '平台更新时间',
  `Last_Time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL,
  `loginaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `gamebigtype` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`xcp_projectid`),
  KEY `IDX_betting_hq_xcp_brandcode` (`brandcode`),
  KEY `IDX_betting_hq_xcp_employeecode` (`employeecode`),
  KEY `IDX_betting_hq_xcp_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_hq_xcp_parentemployeecode` (`parentemployeecode`),
  KEY `betting_hq_xcp_ix5` (`xcp_createtime`),
  KEY `betting_hq_xcp_ix6` (`xcp_writetime`),
  KEY `idx_betting_hq_xcp_wepbonus` (`xcp_writetime`,`employeecode`,`parentemployeecode`,`xcp_bonus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=262 COMMENT='环球XCP';

-- ----------------------------
-- Table structure for betting_idn
-- ----------------------------
DROP TABLE IF EXISTS `betting_idn`;
CREATE TABLE `betting_idn` (
  `transactionno` varchar(50) NOT NULL,
  `tableno` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `betdate` varchar(50) DEFAULT NULL,
  `game` varchar(50) DEFAULT NULL,
  `bettable` varchar(50) DEFAULT NULL,
  `periode` varchar(50) DEFAULT NULL,
  `room` varchar(50) DEFAULT NULL,
  `bet` varchar(20) DEFAULT NULL,
  `currbet` varchar(50) DEFAULT NULL,
  `rbet` varchar(50) DEFAULT NULL,
  `game_status` varchar(50) DEFAULT NULL,
  `card` varchar(50) DEFAULT NULL,
  `hand` varchar(50) DEFAULT NULL,
  `prize` varchar(50) DEFAULT NULL,
  `curr` varchar(50) DEFAULT NULL,
  `currplayer` varchar(50) DEFAULT NULL,
  `curramount` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `total` varchar(50) DEFAULT NULL,
  `agentcomission` varchar(50) DEFAULT NULL,
  `agentbill` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`transactionno`),
  KEY `betting_idn_ix1` (`bettime`),
  KEY `betting_idn_ix2` (`employeecode`),
  KEY `betting_idn_ix3` (`enterprisecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_idn2
-- ----------------------------
DROP TABLE IF EXISTS `betting_idn2`;
CREATE TABLE `betting_idn2` (
  `lsh` varchar(36) NOT NULL,
  `turnoverdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `userid` varchar(50) NOT NULL DEFAULT '',
  `totalturnover` varchar(50) DEFAULT NULL,
  `turnoverpoker` varchar(50) DEFAULT NULL,
  `turnoverdomino` varchar(50) DEFAULT NULL,
  `turnoverceme` varchar(50) DEFAULT NULL,
  `turnoverblackjack` varchar(50) DEFAULT NULL,
  `turnovercapsa` varchar(50) DEFAULT NULL,
  `turnoverlivepoker` varchar(50) DEFAULT NULL,
  `texaspoker` varchar(50) DEFAULT NULL,
  `domino` varchar(50) DEFAULT NULL,
  `ceme` varchar(50) DEFAULT NULL,
  `blackjack` varchar(50) DEFAULT NULL,
  `capsa` varchar(50) DEFAULT NULL,
  `livepoker` varchar(50) DEFAULT NULL,
  `pokertournament` varchar(50) DEFAULT NULL,
  `agentcommission` varchar(50) DEFAULT NULL,
  `agentbill` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT 'QP',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`turnoverdate`,`userid`),
  KEY `index2` (`userid`),
  KEY `index3` (`loginaccount`),
  KEY `index4` (`employeecode`),
  KEY `index1` (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_im
-- ----------------------------
DROP TABLE IF EXISTS `betting_im`;
CREATE TABLE `betting_im` (
  `betid` varchar(50) NOT NULL,
  `providername` varchar(50) DEFAULT NULL,
  `gameid` varchar(50) DEFAULT NULL,
  `wagercreationdatetime` varchar(50) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `playerid` varchar(50) DEFAULT NULL,
  `stakeamount` double(50,0) DEFAULT NULL,
  `memberexposure` double(50,0) DEFAULT NULL,
  `payoutamount` double(50,0) DEFAULT NULL,
  `winloss` double(50,0) DEFAULT NULL,
  `oddstype` varchar(50) DEFAULT NULL,
  `wagertype` varchar(50) DEFAULT NULL,
  `platform` varchar(50) DEFAULT NULL,
  `issettled` varchar(50) DEFAULT NULL,
  `isconfirmed` varchar(50) DEFAULT NULL,
  `iscancelled` varchar(50) DEFAULT NULL,
  `bettradestatus` varchar(50) DEFAULT NULL,
  `bettradecommission` varchar(50) DEFAULT NULL,
  `bettradebuybackamount` varchar(50) DEFAULT NULL,
  `combotype` varchar(50) DEFAULT NULL,
  `lastupdateddate` varchar(50) DEFAULT NULL,
  `detailitems` text,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `nettime` datetime DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `platformflag` varchar(50) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`betid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_jdb
-- ----------------------------
DROP TABLE IF EXISTS `betting_jdb`;
CREATE TABLE `betting_jdb` (
  `seqNo` bigint(20) NOT NULL COMMENT '游戏序号，主键',
  `playerId` varchar(19) NOT NULL COMMENT '玩家账号',
  `mtype` int(11) DEFAULT NULL COMMENT '机台类型',
  `gameDate` char(19) DEFAULT NULL COMMENT '游戏时间',
  `bet` double DEFAULT NULL COMMENT '押注金额',
  `gambleBet` double DEFAULT NULL COMMENT '博取游戏押注金额',
  `win` double DEFAULT NULL COMMENT '游戏输赢',
  `total` double DEFAULT NULL COMMENT '总输赢',
  `currency` char(2) DEFAULT NULL COMMENT '货币编码',
  `denom` double DEFAULT NULL COMMENT '投注面额',
  `lastModifyTime` char(19) DEFAULT NULL COMMENT '最后修改时间',
  `playerIp` char(15) DEFAULT NULL COMMENT '玩家IP',
  `clientType` varchar(20) DEFAULT NULL COMMENT '客户端类型',
  `gType` int(11) DEFAULT NULL COMMENT '游戏类型：0-老虎机，7-捕鱼机，9-水果机',
  `hasGamble` int(11) DEFAULT NULL COMMENT '是否博取游戏：0-否，1-是【老虎鱼&水果机】',
  `hasBonusGame` int(11) DEFAULT NULL COMMENT '是否奖金游戏：0-否，1-是【水果机专属】',
  `hasFreegame` int(11) DEFAULT NULL COMMENT '是否免费游戏：0-否，1-是【老虎机专属】',
  `roomType` int(11) DEFAULT NULL COMMENT '捕鱼机房间类型：0-欢乐区，1-富豪区【捕鱼机专属】',
  `systemTakeWin` int(11) DEFAULT NULL COMMENT '标记该笔为游戏中断线，由系统结算：0-否，1-是【老虎机专属】',
  `jackpot` double DEFAULT NULL COMMENT '赢得彩金金额【老虎机专属】',
  `jackpotContribute` double DEFAULT NULL COMMENT '彩金贡献值【老虎机专属】',
  `beforeBalance` double DEFAULT NULL COMMENT '进场金额【捕鱼机专属】',
  `afterBalance` double DEFAULT NULL COMMENT '离场金额【捕鱼机专属】',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '会员编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '会员上级编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '会员账号',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏类型',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `bettime` datetime DEFAULT NULL COMMENT '投注时间',
  `betmoney` double DEFAULT NULL COMMENT '投注金额',
  `netmoney` double DEFAULT NULL COMMENT '输赢金额',
  PRIMARY KEY (`seqNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='JDB168游戏数据';

-- ----------------------------
-- Table structure for betting_kr_av
-- ----------------------------
DROP TABLE IF EXISTS `betting_kr_av`;
CREATE TABLE `betting_kr_av` (
  `tid` varchar(20) NOT NULL DEFAULT '' COMMENT '注单ID',
  `enterprisecode` varchar(8) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(8) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` varchar(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` varchar(8) DEFAULT NULL COMMENT '上级员工编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '登陆用户名',
  `transtype` varchar(20) DEFAULT NULL COMMENT '投注标识',
  `amount` double(10,2) DEFAULT NULL COMMENT '下注金额',
  `transtype2` varchar(20) DEFAULT NULL COMMENT '结果标识',
  `amount2` double(10,2) DEFAULT NULL COMMENT '输赢金额',
  `provider` varchar(20) DEFAULT NULL COMMENT '游戏大类',
  `roundid` varchar(20) DEFAULT NULL COMMENT '循环序号',
  `gameinfo` varchar(20) DEFAULT NULL COMMENT '游戏信息',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏大类',
  `history` varchar(255) DEFAULT NULL COMMENT '游戏历史名称',
  `isroundfinished` varchar(10) DEFAULT NULL COMMENT '完成信息',
  `time` datetime DEFAULT NULL COMMENT '下注时间',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户名',
  `createtime` datetime DEFAULT NULL COMMENT '数据生成时间',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`tid`),
  KEY `index_employeecode` (`employeecode`),
  KEY `betting_kr_av_ix2` (`enterprisecode`),
  KEY `betting_kr_av_ix3` (`parentemployeecode`),
  KEY `betting_kr_av_ix4` (`brandcode`),
  KEY `betting_kr_av_ix5` (`time`),
  KEY `betting_kr_av_ix6` (`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=496;

-- ----------------------------
-- Table structure for betting_m88
-- ----------------------------
DROP TABLE IF EXISTS `betting_m88`;
CREATE TABLE `betting_m88` (
  `transid` varchar(36) NOT NULL,
  `memberid` varchar(36) DEFAULT NULL,
  `operatorid` varchar(50) DEFAULT NULL,
  `sitecode` varchar(36) DEFAULT NULL,
  `leagueid` varchar(36) DEFAULT NULL,
  `homeid` varchar(36) DEFAULT NULL,
  `awayid` varchar(36) DEFAULT NULL,
  `matchdatetime` varchar(36) DEFAULT NULL,
  `bettype` varchar(36) DEFAULT NULL,
  `parlayrefno` varchar(36) DEFAULT NULL,
  `odds` varchar(36) DEFAULT NULL,
  `currency` varchar(36) DEFAULT NULL,
  `stake` varchar(36) DEFAULT NULL,
  `winlostamount` varchar(36) DEFAULT NULL,
  `transactiontime` varchar(36) DEFAULT NULL,
  `ticketstatus` varchar(36) DEFAULT NULL,
  `versionkey` varchar(36) DEFAULT NULL,
  `winlostdatetime` varchar(36) DEFAULT NULL,
  `oddstype` varchar(36) DEFAULT NULL,
  `sportstype` varchar(36) DEFAULT NULL,
  `betteam` varchar(255) DEFAULT NULL,
  `homehdp` varchar(36) DEFAULT NULL,
  `awayhdp` varchar(36) DEFAULT NULL,
  `matchid` varchar(36) DEFAULT NULL,
  `islive` varchar(36) DEFAULT NULL,
  `homescore` varchar(36) DEFAULT NULL,
  `awayscore` varchar(36) DEFAULT NULL,
  `choicecode` varchar(255) DEFAULT NULL,
  `choicename` varchar(255) DEFAULT NULL,
  `txntype` varchar(36) DEFAULT NULL,
  `lastupdate` varchar(36) DEFAULT NULL,
  `leaguename` varchar(255) DEFAULT NULL,
  `homename` varchar(255) DEFAULT NULL,
  `awayname` varchar(255) DEFAULT NULL,
  `sportname` varchar(255) DEFAULT NULL,
  `oddsname` varchar(255) DEFAULT NULL,
  `bettypename` varchar(255) DEFAULT NULL,
  `winloststatus` varchar(36) DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `nettime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`transid`),
  KEY `betting_m88_ix1` (`employeecode`),
  KEY `betting_m88_ix2` (`bettime`),
  KEY `betting_m88_ix3` (`enterprisecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_mg
-- ----------------------------
DROP TABLE IF EXISTS `betting_mg`;
CREATE TABLE `betting_mg` (
  `mg_trans_Id` varchar(50) NOT NULL COMMENT '????',
  `mg_key` varchar(50) DEFAULT NULL COMMENT '????(????)',
  `mg_col_Id` varchar(50) DEFAULT NULL COMMENT '???????(????)',
  `mg_agent_Id` varchar(50) DEFAULT NULL COMMENT '????',
  `mg_mbr_Id` varchar(50) DEFAULT NULL COMMENT '????',
  `mg_mbr_Code` varchar(50) DEFAULT NULL COMMENT '????',
  `mg_game_Id` varchar(50) DEFAULT NULL COMMENT '????',
  `mg_trans_Type` varchar(50) DEFAULT NULL COMMENT '?????[?? -> "bet", ? -> "win", ?? -> "refund"]',
  `mg_trans_Time` varchar(50) DEFAULT NULL COMMENT '????????UTC+0???',
  `mg_mgs_Game_Id` varchar(50) DEFAULT NULL COMMENT 'MG ???????',
  `mg_mgs_Action_Id` varchar(50) DEFAULT NULL COMMENT 'MG ????????',
  `mg_amnt` varchar(50) DEFAULT NULL COMMENT '?????(????????????????)',
  `mg_clrng_Amnt` varchar(50) DEFAULT NULL COMMENT '?????(????????????????)',
  `mg_balance` varchar(50) DEFAULT NULL COMMENT '??????',
  `mg_ref_Trans_Id` varchar(50) DEFAULT NULL COMMENT '???????? ??????????, ???????????????transId?',
  `mg_ref_Trans_Type` varchar(50) DEFAULT NULL COMMENT '?????????. [?? -> "bet", ? -> "win"]',
  `mg_win` varchar(50) DEFAULT NULL COMMENT '??????',
  `mg_createtime` datetime DEFAULT NULL COMMENT '????',
  `Platformflag` varchar(50) DEFAULT NULL COMMENT '????',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '????',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '????',
  `employeecode` char(8) DEFAULT NULL COMMENT '????',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '??????',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '??????',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '????',
  `status` int(11) DEFAULT '0',
  `bettime` datetime DEFAULT NULL,
  `net_money` double DEFAULT NULL,
  `mg_amount` double DEFAULT NULL,
  PRIMARY KEY (`mg_trans_Id`),
  KEY `betting_mg_ix1` (`mg_createtime`),
  KEY `betting_mg_ix2` (`enterprisecode`),
  KEY `betting_mg_ix3` (`brandcode`),
  KEY `betting_mg_ix4` (`employeecode`),
  KEY `betting_mg_ix6` (`loginaccount`),
  KEY `betting_mg_ix7` (`bettime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MG?????';

-- ----------------------------
-- Table structure for betting_qp
-- ----------------------------
DROP TABLE IF EXISTS `betting_qp`;
CREATE TABLE `betting_qp` (
  `Turn_Number` varchar(50) NOT NULL COMMENT '游戏局号',
  `Server_ID` varchar(50) DEFAULT NULL COMMENT '游戏类型',
  `Kind_ID` varchar(50) DEFAULT NULL COMMENT '房间类型',
  `Room_Name` varchar(50) DEFAULT NULL COMMENT '游戏房间名称',
  `Start_Time` varchar(50) DEFAULT NULL COMMENT '下注时间',
  `End_Time` varchar(50) DEFAULT NULL COMMENT '派彩时间',
  `Score` varchar(50) DEFAULT NULL COMMENT '投注金额',
  `Turn_Score` varchar(50) DEFAULT NULL COMMENT '输赢',
  `Account` varchar(50) DEFAULT NULL COMMENT '玩家账号',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `Platformflag` varchar(50) DEFAULT NULL COMMENT '标志',
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `bettime` datetime DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `Revenue` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Turn_Number`),
  KEY `betting_mg_ix1` (`createtime`),
  KEY `betting_mg_ix2` (`enterprisecode`),
  KEY `betting_mg_ix3` (`brandcode`),
  KEY `betting_mg_ix4` (`employeecode`),
  KEY `betting_mg_ix6` (`loginaccount`),
  KEY `betting_mg_ix7` (`bettime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='棋牌游戏表';

-- ----------------------------
-- Table structure for betting_qt
-- ----------------------------
DROP TABLE IF EXISTS `betting_qt`;
CREATE TABLE `betting_qt` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `playergameroundid` varchar(36) DEFAULT NULL,
  `gameid` varchar(36) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `balance` varchar(10) DEFAULT NULL,
  `created` varchar(50) DEFAULT NULL,
  `gameprovider` varchar(36) DEFAULT NULL,
  `gtype` varchar(10) DEFAULT NULL,
  `gameclienttype` varchar(36) DEFAULT NULL,
  `gamecategory` varchar(36) DEFAULT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `playerdevice` varchar(36) DEFAULT NULL,
  `operatorid` varchar(36) DEFAULT NULL,
  `playerid` varchar(36) DEFAULT NULL,
  `wallettransactionid` varchar(36) DEFAULT NULL,
  `roundstatus` varchar(36) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index1` (`playergameroundid`),
  KEY `betting_qt_ix2` (`playerid`),
  KEY `betting_qt_ix3` (`enterprisecode`),
  KEY `betting_qt_ix4` (`employeecode`),
  KEY `betting_qt_ix5` (`parentemployeecode`),
  KEY `betting_qt_ix6` (`loginaccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_qwp
-- ----------------------------
DROP TABLE IF EXISTS `betting_qwp`;
CREATE TABLE `betting_qwp` (
  `TurnNumber` bigint(20) NOT NULL COMMENT '局号',
  `ServerId` int(11) DEFAULT NULL COMMENT '房间Id',
  `KindId` int(11) DEFAULT NULL COMMENT '游戏Id',
  `RoomName` varchar(100) DEFAULT NULL COMMENT '房间名称',
  `StartTime` datetime DEFAULT NULL COMMENT '开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '结束时间',
  `RecordTime` datetime DEFAULT NULL COMMENT '记录时间',
  `CardData` varchar(100) DEFAULT NULL COMMENT '牌型',
  `Account` varchar(50) DEFAULT NULL COMMENT '玩家账号',
  `Score` double DEFAULT NULL COMMENT '输赢',
  `TurnScore` double DEFAULT NULL COMMENT '底分',
  `Revenue` double DEFAULT NULL COMMENT '抽水',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '会员编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '会员上级编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '会员账号',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏类型',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`TurnNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='去玩棋牌游戏数据';

-- ----------------------------
-- Table structure for betting_sa
-- ----------------------------
DROP TABLE IF EXISTS `betting_sa`;
CREATE TABLE `betting_sa` (
  `betid` varchar(32) NOT NULL DEFAULT '' COMMENT '投注ID',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '企业品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '员工上级编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '登陆账号',
  `gamebigtype` varchar(10) DEFAULT NULL COMMENT '游戏大类',
  `bettime` datetime DEFAULT NULL COMMENT '投注时间',
  `payouttime` datetime DEFAULT NULL COMMENT '结算时间',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `hostid` varchar(10) DEFAULT NULL COMMENT '桌台 ID',
  `gameid` varchar(32) DEFAULT NULL COMMENT '游戏 ID',
  `round` varchar(10) DEFAULT NULL COMMENT '局',
  `sets` varchar(10) DEFAULT NULL COMMENT '靴',
  `betamount` double(10,2) DEFAULT NULL COMMENT '下注额',
  `resultamount` double(10,2) DEFAULT NULL COMMENT '结算',
  `gametype` varchar(15) DEFAULT NULL COMMENT '游戏类型',
  `bettype` varchar(10) DEFAULT NULL COMMENT '投注类型',
  `betsource` varchar(10) DEFAULT NULL COMMENT '投注来源',
  `state` varchar(10) DEFAULT NULL COMMENT '下注是否成功',
  `detail` varchar(32) DEFAULT NULL COMMENT '其他详情',
  `createtime` datetime DEFAULT NULL COMMENT '数据生成时间',
  `status` int(11) DEFAULT '0',
  `validbet` double(10,2) DEFAULT '0.00',
  PRIMARY KEY (`betid`),
  KEY `index_employeecode` (`employeecode`),
  KEY `betting_sa_ix2` (`enterprisecode`),
  KEY `betting_sa_ix3` (`brandcode`),
  KEY `betting_sa_ix4` (`employeecode`),
  KEY `betting_sa_ix5` (`parentemployeecode`),
  KEY `betting_sa_ix6` (`bettime`),
  KEY `betting_sa_ix7` (`payouttime`),
  KEY `betting_sa_ix8` (`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_sgs
-- ----------------------------
DROP TABLE IF EXISTS `betting_sgs`;
CREATE TABLE `betting_sgs` (
  `ugsbetid` varchar(50) NOT NULL,
  `txid` varchar(50) DEFAULT NULL,
  `betid` varchar(50) DEFAULT NULL,
  `beton` datetime DEFAULT NULL,
  `betclosedon` datetime DEFAULT NULL,
  `betupdatedon` datetime DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `roundid` varchar(50) DEFAULT NULL,
  `roundstatus` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `riskamt` double DEFAULT NULL,
  `winamt` double DEFAULT NULL,
  `winloss` double DEFAULT NULL,
  `beforebal` double DEFAULT NULL,
  `postbal` double DEFAULT NULL,
  `cur` varchar(10) DEFAULT NULL,
  `gameprovider` varchar(50) DEFAULT NULL,
  `gamename` varchar(50) DEFAULT NULL,
  `gameid` varchar(50) DEFAULT NULL,
  `platformtype` varchar(50) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  `bettype` varchar(50) DEFAULT NULL,
  `playtype` varchar(50) DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `platformflag` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `validmoney` double DEFAULT '0',
  PRIMARY KEY (`ugsbetid`),
  KEY `betting_sgs_ix1` (`userid`),
  KEY `betting_sgs_ix2` (`bettime`),
  KEY `betting_sgs_ix3` (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_tgp
-- ----------------------------
DROP TABLE IF EXISTS `betting_tgp`;
CREATE TABLE `betting_tgp` (
  `ugsbetid` varchar(50) NOT NULL DEFAULT '',
  `txid` varchar(50) DEFAULT NULL,
  `betid` varchar(50) DEFAULT NULL,
  `beton` datetime DEFAULT NULL,
  `betclosedon` datetime DEFAULT NULL,
  `betupdatedon` datetime DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `roundid` varchar(50) DEFAULT NULL,
  `roundstatus` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `riskamt` double DEFAULT NULL,
  `winamt` double DEFAULT NULL,
  `winloss` double DEFAULT NULL,
  `beforebal` double DEFAULT NULL,
  `postbal` double DEFAULT NULL,
  `cur` varchar(10) DEFAULT NULL,
  `gameprovider` varchar(50) DEFAULT NULL,
  `gamename` varchar(50) DEFAULT NULL,
  `gameid` varchar(50) DEFAULT NULL,
  `platformtype` varchar(50) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  `bettype` varchar(50) DEFAULT NULL,
  `playtype` varchar(50) DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `bettime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `validmoney` double DEFAULT '0',
  PRIMARY KEY (`ugsbetid`),
  KEY `betting_tgp_ix1` (`userid`),
  KEY `betting_tgp_ix2` (`enterprisecode`),
  KEY `betting_tgp_ix3` (`employeecode`),
  KEY `betting_tgp_ix4` (`parentemployeecode`),
  KEY `betting_tgp_ix5` (`loginaccount`),
  KEY `betting_tgp_ix6` (`bettime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_ttg
-- ----------------------------
DROP TABLE IF EXISTS `betting_ttg`;
CREATE TABLE `betting_ttg` (
  `transaction_Id` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易ID',
  `request_Id` varchar(50) DEFAULT NULL COMMENT '请求ID',
  `partner_Id` varchar(50) DEFAULT NULL COMMENT '同伴ID',
  `player_Id` varchar(50) DEFAULT NULL COMMENT '玩家ID',
  `amount` varchar(50) DEFAULT NULL COMMENT '总额',
  `hand_Id` varchar(50) DEFAULT NULL COMMENT '游戏独特的手Id',
  `transaction_Type` varchar(50) DEFAULT NULL COMMENT '交易类型',
  `transaction_Sub_Type` varchar(50) DEFAULT NULL COMMENT '子交易类型',
  `currency` varchar(50) DEFAULT NULL COMMENT '货币类型',
  `game` varchar(50) DEFAULT NULL COMMENT '游戏名称',
  `transaction_Date` varchar(50) DEFAULT NULL COMMENT '交易日期',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `Platformflag` varchar(50) DEFAULT NULL COMMENT '标志',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '用户登录账号',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  `bettime` datetime DEFAULT NULL,
  `net_Money` varchar(50) DEFAULT NULL,
  `resolvetime` datetime DEFAULT NULL,
  PRIMARY KEY (`transaction_Id`),
  KEY `betting_ttg_ix1` (`createtime`),
  KEY `betting_ttg_ix2` (`enterprisecode`),
  KEY `betting_ttg_ix3` (`brandcode`),
  KEY `betting_ttg_ix4` (`employeecode`),
  KEY `betting_ttg_ix5` (`parentemployeecode`),
  KEY `betting_ttg_ix6` (`bettime`),
  KEY `idx_betting_ttg_hand_Id_transaction_Id` (`hand_Id`,`transaction_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='TTG游戏表';

-- ----------------------------
-- Table structure for betting_win88
-- ----------------------------
DROP TABLE IF EXISTS `betting_win88`;
CREATE TABLE `betting_win88` (
  `pt_gamecode` varchar(32) NOT NULL,
  `pt_username` varchar(32) DEFAULT NULL,
  `pt_windowcode` varchar(32) DEFAULT NULL,
  `pt_gameid` varchar(32) DEFAULT NULL,
  `pt_gametype` varchar(32) DEFAULT NULL,
  `pt_gamename` varchar(200) DEFAULT NULL,
  `pt_bet` decimal(11,2) DEFAULT NULL,
  `pt_win` decimal(11,2) DEFAULT NULL,
  `pt_balance` decimal(11,2) DEFAULT NULL,
  `pt_gamedate` datetime DEFAULT NULL,
  `pt_info` varchar(1000) DEFAULT NULL,
  `pt_sessionid` varchar(32) DEFAULT NULL,
  `pt_progressivebet` varchar(32) DEFAULT NULL,
  `pt_progressivewin` varchar(32) DEFAULT NULL,
  `pt_currentbet` varchar(32) DEFAULT NULL,
  `pt_livenetwork` varchar(32) DEFAULT NULL,
  `pt_rnum` varchar(32) DEFAULT NULL,
  `pt_createtime` datetime DEFAULT NULL,
  `Platformflag` varchar(50) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(12) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`pt_gamecode`),
  KEY `ix_pt_gameinfo_pt_username` (`pt_username`) USING BTREE,
  KEY `ix_pt_gameinfo_pt_gamedate` (`pt_gamedate`) USING BTREE,
  KEY `betting_win88_ix3` (`enterprisecode`),
  KEY `betting_win88_ix4` (`employeecode`),
  KEY `betting_win88_ix5` (`loginaccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_yg_ag
-- ----------------------------
DROP TABLE IF EXISTS `betting_yg_ag`;
CREATE TABLE `betting_yg_ag` (
  `bet_id` varchar(32) NOT NULL DEFAULT '' COMMENT '注单 ID',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` varchar(20) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '上级编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '登陆账号',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏大类',
  `user` varchar(25) DEFAULT NULL COMMENT '会员帐号',
  `game_id` varchar(20) DEFAULT NULL COMMENT '游戏 ID',
  `game_name` varchar(100) DEFAULT NULL COMMENT '游戏名称',
  `money` double(10,2) DEFAULT NULL COMMENT '下注金额',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注，如游戏场景 ID，捕鱼王奖励，游戏局号等',
  `time` datetime DEFAULT NULL COMMENT '下注时间',
  `result` double(10,2) DEFAULT NULL COMMENT '输赢结果，正数为会员所赢的钱数，负数为会员输的钱',
  `valid_money` double(10,0) DEFAULT NULL COMMENT '有效下注金额',
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间？',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`bet_id`),
  KEY `IDX_betting_yg_ag_brandcode` (`brandcode`),
  KEY `IDX_betting_yg_ag_employeecode` (`employeecode`),
  KEY `IDX_betting_yg_ag_enterprisecode` (`enterprisecode`),
  KEY `IDX_betting_yg_ag_parentemployeecode` (`parentemployeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_yoplay
-- ----------------------------
DROP TABLE IF EXISTS `betting_yoplay`;
CREATE TABLE `betting_yoplay` (
  `bill_No` varchar(50) NOT NULL,
  `player_Name` varchar(50) DEFAULT NULL,
  `agent_Code` varchar(50) DEFAULT NULL,
  `game_Code` varchar(200) DEFAULT NULL,
  `net_Amount` varchar(50) DEFAULT NULL,
  `bet_Time` datetime DEFAULT NULL,
  `game_Type` varchar(50) DEFAULT NULL,
  `bet_Amount` varchar(50) DEFAULT NULL,
  `valid_Bet_Amount` varchar(50) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  `play_Type` varchar(50) DEFAULT NULL,
  `currency` varchar(50) DEFAULT NULL,
  `table_Code` varchar(50) DEFAULT NULL,
  `login_IP` varchar(50) DEFAULT NULL,
  `recalcu_Time` varchar(50) DEFAULT NULL,
  `platform_Type` varchar(50) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `round` varchar(50) DEFAULT NULL,
  `slottype` varchar(50) DEFAULT NULL,
  `result` varchar(2048) DEFAULT NULL,
  `mainbillno` varchar(50) DEFAULT NULL,
  `before_Credit` varchar(50) DEFAULT NULL,
  `device_Type` varchar(50) DEFAULT NULL,
  `bet_Amount_Base` varchar(50) DEFAULT NULL,
  `bet_Amount_Bonus` varchar(50) DEFAULT NULL,
  `net_Amount_Base` varchar(50) DEFAULT NULL,
  `net_Amount_Bonus` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `betmoney` double DEFAULT NULL,
  `netmoney` double DEFAULT NULL,
  `validmoney` double DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT 'DZ',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`bill_No`),
  KEY `index1` (`bet_Time`),
  KEY `index2` (`loginaccount`),
  KEY `index3` (`bill_No`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for betting_zj
-- ----------------------------
DROP TABLE IF EXISTS `betting_zj`;
CREATE TABLE `betting_zj` (
  `id` varchar(32) NOT NULL COMMENT '游戏平台记录 id',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '企业品牌编码',
  `employeecode` char(8) DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '员工上级编码',
  `loginaccount` varchar(12) DEFAULT NULL COMMENT '员工账号',
  `username` varchar(20) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `gametype` varchar(20) DEFAULT NULL,
  `tableinfoid` varchar(20) DEFAULT NULL COMMENT '桌号 ID',
  `shoeinfoid` varchar(20) DEFAULT NULL COMMENT '靴号 ID',
  `gameinfoid` varchar(10) DEFAULT NULL,
  `tablename` varchar(10) DEFAULT NULL,
  `issueno` varchar(10) DEFAULT NULL,
  `bankerresult` varchar(100) DEFAULT NULL,
  `resultlist` varchar(100) DEFAULT NULL,
  `pokerlist` varchar(100) DEFAULT NULL,
  `stakeamount` double(8,2) DEFAULT NULL COMMENT '注投额？',
  `validstake` double(8,2) DEFAULT NULL COMMENT '效有投注额？',
  `winloss` double(8,2) DEFAULT NULL COMMENT '输赢金额？',
  `comm` double(8,2) DEFAULT NULL COMMENT '佣金',
  `balanceafter` double(8,2) DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL COMMENT '下注 IP',
  `details` text,
  `bettime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL COMMENT '数据拉取时间',
  `gamebigtype` varchar(5) DEFAULT NULL COMMENT '游戏大类',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_employeecode` (`employeecode`),
  KEY `betting_zj_ix2` (`enterprisecode`),
  KEY `betting_zj_ix3` (`employeecode`),
  KEY `betting_zj_ix4` (`brandcode`),
  KEY `betting_zj_ix5` (`parentemployeecode`),
  KEY `betting_zj_ix6` (`bettime`),
  KEY `betting_zj_ix7` (`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for brand_domain
-- ----------------------------
DROP TABLE IF EXISTS `brand_domain`;
CREATE TABLE `brand_domain` (
  `domaincode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) NOT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(10) DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '上级用户编码',
  `domainlink` varchar(64) NOT NULL COMMENT '推广域名',
  `employeetype` varchar(10) NOT NULL COMMENT '用户类型',
  `dividend` decimal(5,4) NOT NULL DEFAULT '0.0000' COMMENT '分红',
  `share` decimal(5,4) NOT NULL DEFAULT '0.0000' COMMENT '占成',
  `bonus` varchar(2000) NOT NULL COMMENT '洗码',
  `domaintype` char(1) NOT NULL DEFAULT '3' COMMENT '域名类型 1:企业绑定域名-会员|2:会员站点域名|3:代理注册链接|4:代理站点域名|5：企业绑定域名-代理',
  `isdefualt` char(1) NOT NULL DEFAULT '0' COMMENT '是否默认主域名',
  `copyright` char(1) NOT NULL DEFAULT '2' COMMENT '域名版权(1、公共 , 2、私有)',
  `linkdomain` int(11) DEFAULT NULL COMMENT '二级域名指向的主域名',
  `createdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `linkstatus` char(1) NOT NULL DEFAULT '1' COMMENT '链接状态(1、启用 2、禁用)',
  `webtemplatecode` char(6) DEFAULT NULL COMMENT '站点模板',
  `datastatus` char(2) NOT NULL DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`domaincode`),
  KEY `UK_brand_domain_domainlink` (`domainlink`)
) ENGINE=InnoDB AUTO_INCREMENT=1300 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=819 COMMENT='注册链接与站点域名';

-- ----------------------------
-- Table structure for data_handle
-- ----------------------------
DROP TABLE IF EXISTS `data_handle`;
CREATE TABLE `data_handle` (
  `handlecode` varchar(50) NOT NULL COMMENT '编码',
  `handledesc` varchar(50) NOT NULL COMMENT '说明',
  `updatetime` varchar(2000) NOT NULL COMMENT '更新时间',
  `lasttime` varchar(50) DEFAULT '0',
  `lastnum` int(11) DEFAULT '0',
  `daynum` int(11) DEFAULT '0',
  `allnum` int(11) DEFAULT '0',
  `intervalnum` int(11) DEFAULT '20',
  `remark` varchar(200) DEFAULT NULL,
  `gametype` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`handlecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据抓取';

-- ----------------------------
-- Table structure for data_handle_logs
-- ----------------------------
DROP TABLE IF EXISTS `data_handle_logs`;
CREATE TABLE `data_handle_logs` (
  `lsh` varchar(36) NOT NULL,
  `gametype` varchar(30) DEFAULT NULL,
  `lasttime` datetime DEFAULT NULL,
  `dataparams` varchar(2000) DEFAULT NULL,
  `datalog` varchar(4000) DEFAULT NULL,
  `agentaccount` varchar(50) DEFAULT NULL,
  `flag` int(11) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_handle_maintenance
-- ----------------------------
DROP TABLE IF EXISTS `data_handle_maintenance`;
CREATE TABLE `data_handle_maintenance` (
  `gametype` varchar(15) NOT NULL DEFAULT '',
  `flag` int(11) DEFAULT '1',
  `lasttime` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`gametype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_handle_repeat
-- ----------------------------
DROP TABLE IF EXISTS `data_handle_repeat`;
CREATE TABLE `data_handle_repeat` (
  `lsh` varchar(36) NOT NULL,
  `gametype` varchar(15) DEFAULT NULL,
  `starttime` varchar(20) DEFAULT NULL,
  `endtime` varchar(255) DEFAULT NULL,
  `lasttime` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for deposit_withdral_order_delegate
-- ----------------------------
DROP TABLE IF EXISTS `deposit_withdral_order_delegate`;
CREATE TABLE `deposit_withdral_order_delegate` (
  `delegatecode` int(11) NOT NULL AUTO_INCREMENT COMMENT '委派编码',
  `flowcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '流程编码',
  `ordernumber` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `assignedtocode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '委派人编码',
  `assignedtoaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '委派人名称',
  `begintime` timestamp NULL DEFAULT NULL COMMENT '开始处理时间',
  `endtime` timestamp NULL DEFAULT NULL COMMENT '处理结束时间',
  `processduration` int(11) DEFAULT NULL COMMENT '处理时长',
  `overtimereason` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '超时处理原因',
  `imgname` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '图片路径',
  `auditresult` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '审核结果',
  `auditdesc` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '审核说明',
  PRIMARY KEY (`delegatecode`),
  KEY `FK_deposit_w` (`flowcode`),
  KEY `FK_deposit_withdral_order` (`ordernumber`),
  KEY `index_assignedtocode` (`assignedtocode`)
) ENGINE=InnoDB AUTO_INCREMENT=50015 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=168 COMMENT='存取款单委派';

-- ----------------------------
-- Table structure for deposit_withdraw_order
-- ----------------------------
DROP TABLE IF EXISTS `deposit_withdraw_order`;
CREATE TABLE `deposit_withdraw_order` (
  `ordernumber` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `employeecode` char(10) COLLATE utf8_bin NOT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '员工上级编码',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `paymenttypecode` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式',
  `orderdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单日期',
  `orderamount` decimal(16,2) DEFAULT NULL COMMENT '订单金额',
  `exchangerate` decimal(16,5) unsigned DEFAULT NULL COMMENT '汇率',
  `enterprisepaymentname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '企业收付款账号姓名',
  `enterprisepaymentaccount` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '企业收付款账号',
  `enterprisepaymentbank` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '企业收付款银行',
  `employeepaymentname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '员工收付款姓名',
  `employeepaymentaccount` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '员工收付款账号',
  `employeepaymentbank` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '员工收付款银行',
  `ordertype` tinyint(4) DEFAULT NULL COMMENT '订单类型{1:存款,2:取款}',
  `orderstatus` tinyint(4) DEFAULT NULL COMMENT '订单状态{1:处理中,2:已处理,3:驳回,4:拒绝,5:待出款}',
  `ordercreater` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '订单创建人',
  `traceip` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '跟踪IP',
  `ordercomment` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '订单备注',
  `flowcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '流程编码',
  `delegatecode` int(11) NOT NULL COMMENT '委托编码',
  `handleemployee` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '当前处理用户',
  `handleovertime` timestamp NULL DEFAULT NULL COMMENT '订单处理完成时间',
  `favourableid` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ordernumber`),
  KEY `ER_employee_N_order_FK` (`employeecode`),
  KEY `index_order_number` (`ordernumber`),
  KEY `index4` (`parentemployeecode`),
  KEY `index5` (`enterprisecode`),
  KEY `index6` (`orderdate`),
  KEY `index7` (`handleemployee`),
  KEY `index8` (`delegatecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=534 COMMENT='应该有操作时间，处理人，有状态， 自动转出平台';

-- ----------------------------
-- Table structure for employee_agent_relation
-- ----------------------------
DROP TABLE IF EXISTS `employee_agent_relation`;
CREATE TABLE `employee_agent_relation` (
  `employeecode` char(8) NOT NULL COMMENT '用户编码',
  `loginaccount` varchar(12) NOT NULL COMMENT '登陆账号',
  `lowercode` text COMMENT '下级代理编码',
  `uppercode` text COMMENT '上级代理编码',
  `lowercodeenum` set('one','two') DEFAULT NULL,
  PRIMARY KEY (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=727;

-- ----------------------------
-- Table structure for employee_api_accout
-- ----------------------------
DROP TABLE IF EXISTS `employee_api_accout`;
CREATE TABLE `employee_api_accout` (
  `apiaccountcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌ID',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '登陆账号',
  `gametype` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `gameaccount` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏账号',
  `gamepassword` char(64) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏密码',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `balance` decimal(16,2) DEFAULT '0.00',
  `status` varchar(6) COLLATE utf8_bin DEFAULT '启用',
  PRIMARY KEY (`apiaccountcode`),
  UNIQUE KEY `UK_employee_api_accout` (`employeecode`,`gametype`),
  KEY `index_gameaccount` (`gameaccount`),
  KEY `employee_api_accout_ix3` (`loginaccount`,`gametype`),
  KEY `idx_employee_api_accout_gameaccount_gametype` (`gameaccount`,`gametype`)
) ENGINE=InnoDB AUTO_INCREMENT=84157 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=630 COMMENT='会员游戏平台账号';

-- ----------------------------
-- Table structure for employee_api_accout_password_job
-- ----------------------------
DROP TABLE IF EXISTS `employee_api_accout_password_job`;
CREATE TABLE `employee_api_accout_password_job` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `employeecode` char(8) DEFAULT NULL,
  `enterprisecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(100) DEFAULT NULL,
  `gametype` varchar(15) DEFAULT NULL,
  `gameaccount` varchar(30) DEFAULT NULL,
  `gamepassword` char(64) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `updatestatus` int(11) DEFAULT '0',
  `updatecomment` text,
  PRIMARY KEY (`lsh`),
  KEY `employee_api_accout_password_job_ix1` (`employeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=1155 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for employee_available
-- ----------------------------
DROP TABLE IF EXISTS `employee_available`;
CREATE TABLE `employee_available` (
  `employeecode` char(8) NOT NULL COMMENT '员工编码',
  `availabletime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '有效截至时间',
  PRIMARY KEY (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户有效时间';

-- ----------------------------
-- Table structure for employee_gamecataloy_bonus
-- ----------------------------
DROP TABLE IF EXISTS `employee_gamecataloy_bonus`;
CREATE TABLE `employee_gamecataloy_bonus` (
  `employeecode` char(10) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '员工编码',
  `gametype` varchar(15) COLLATE utf8_bin NOT NULL DEFAULT '',
  `categorytype` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '游戏种类类型标志(唯一)',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `bonus` decimal(5,4) NOT NULL DEFAULT '0.0000' COMMENT '洗码',
  PRIMARY KEY (`employeecode`,`gametype`,`categorytype`),
  KEY `FK_employee_gamecataloy_bonus_game_gametype` (`gametype`),
  KEY `UK_employee_gam` (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=98 COMMENT='接入平台游戏种类洗码设置表';

-- ----------------------------
-- Table structure for employee_level_condition
-- ----------------------------
DROP TABLE IF EXISTS `employee_level_condition`;
CREATE TABLE `employee_level_condition` (
  `levelconditioncode` char(4) COLLATE utf8_bin NOT NULL COMMENT '级别条件编码',
  `employeelevelcode` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '员工级别编码',
  `depositamount` decimal(16,2) DEFAULT NULL COMMENT '存款量',
  `depositnumber` int(11) DEFAULT NULL COMMENT '存款次数',
  PRIMARY KEY (`levelconditioncode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='企业员工级别表';

-- ----------------------------
-- Table structure for employee_mapping_menu
-- ----------------------------
DROP TABLE IF EXISTS `employee_mapping_menu`;
CREATE TABLE `employee_mapping_menu` (
  `employeecode` char(10) COLLATE utf8_bin NOT NULL COMMENT '员工编码',
  `menucode` char(8) COLLATE utf8_bin NOT NULL COMMENT '菜单编码',
  PRIMARY KEY (`employeecode`,`menucode`),
  KEY `FK_ER_permission_group_mapping_N_group` (`employeecode`),
  KEY `UK_employee_mapping_menu_menucode` (`menucode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1489;

-- ----------------------------
-- Table structure for employee_mapping_role
-- ----------------------------
DROP TABLE IF EXISTS `employee_mapping_role`;
CREATE TABLE `employee_mapping_role` (
  `employeecode` char(8) NOT NULL COMMENT '员工CODE',
  `rolecode` char(8) NOT NULL COMMENT '角色CODE',
  PRIMARY KEY (`employeecode`,`rolecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工角色对应表';

-- ----------------------------
-- Table structure for employee_message
-- ----------------------------
DROP TABLE IF EXISTS `employee_message`;
CREATE TABLE `employee_message` (
  `messagecode` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息编码',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `sendemployeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '发送人编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `sendemployeeaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '发送人账号',
  `messagetextcode` int(11) NOT NULL COMMENT '消息编号',
  `acceptemployeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '接收人编码',
  `acceptaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '接收人账号',
  `messagetype` char(1) COLLATE utf8_bin NOT NULL COMMENT '消息类型（1:系统消息 ， 2:用户消息）',
  `replaycode` int(11) DEFAULT NULL COMMENT '回复编号',
  `readstatus` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '阅读状态（1:未阅读，2:已阅读）',
  PRIMARY KEY (`messagecode`),
  KEY `FK_employee_massage_enterprise_operating_brand_brandcode` (`brandcode`),
  KEY `FK_employee_message_employee_message_text_messagetextcode` (`messagetextcode`),
  CONSTRAINT `FK_employee_message_employee_message_text_messagetextcode` FOREIGN KEY (`messagetextcode`) REFERENCES `employee_message_text` (`messagetextcode`)
) ENGINE=InnoDB AUTO_INCREMENT=31760 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=585;

-- ----------------------------
-- Table structure for employee_message_text
-- ----------------------------
DROP TABLE IF EXISTS `employee_message_text`;
CREATE TABLE `employee_message_text` (
  `messagetextcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息文本编号',
  `content` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '消息内容',
  `sendtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发送时间',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态(1:正常 ， -1:删除)',
  PRIMARY KEY (`messagetextcode`)
) ENGINE=InnoDB AUTO_INCREMENT=28833 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=819;

-- ----------------------------
-- Table structure for employee_money_change
-- ----------------------------
DROP TABLE IF EXISTS `employee_money_change`;
CREATE TABLE `employee_money_change` (
  `moneychangecode` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '帐变编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `moneychangetypecode` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '帐变类型编码',
  `moneychangedate` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '帐变时间',
  `currencycode` char(3) COLLATE utf8_bin DEFAULT NULL,
  `moneychangeamount` decimal(16,2) DEFAULT NULL COMMENT '帐变金额',
  `settlementamount` decimal(16,2) DEFAULT NULL COMMENT '帐变前余额',
  `moneyaddtype` char(3) COLLATE utf8_bin DEFAULT NULL COMMENT '冲正冲负类型',
  `moneyinoutcomment` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `ordernumber` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '帐变订单编号',
  `timesort` bigint(20) DEFAULT NULL COMMENT '排序字段',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`moneychangecode`),
  KEY `ER_money_change_type_N_money_change_FK` (`moneychangetypecode`),
  KEY `FK_employee_money_change_enterprise_employee_employeecode` (`employeecode`),
  KEY `employee_money_change_ix3` (`parentemployeecode`),
  KEY `employee_money_change_ix4` (`ordernumber`),
  CONSTRAINT `FK_ER_money_change_type_N_money_change` FOREIGN KEY (`moneychangetypecode`) REFERENCES `employee_money_change_type` (`moneychangetypecode`),
  CONSTRAINT `FK_employee_money_change_enterprise_employee_employeecode` FOREIGN KEY (`employeecode`) REFERENCES `enterprise_employee` (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=334;

-- ----------------------------
-- Table structure for employee_money_change_type
-- ----------------------------
DROP TABLE IF EXISTS `employee_money_change_type`;
CREATE TABLE `employee_money_change_type` (
  `moneychangetypecode` char(32) COLLATE utf8_bin NOT NULL COMMENT '帐变类型编码',
  `moneychangetype` varchar(8) CHARACTER SET utf8 NOT NULL COMMENT '帐变类型',
  `moneychangetypeclassify` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '帐变类型类别(1、常规 2、活动)',
  `moneyinouttype` char(1) COLLATE utf8_bin NOT NULL COMMENT '进出帐类型(1进账，2出账)',
  `datastatus` char(2) COLLATE utf8_bin DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`moneychangetypecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1260 COMMENT='用户充值 用户取款 A活动充值...';

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise` (
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `parententerprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业上级编码',
  `enterprisename` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '企业名称',
  `registrationdate` datetime NOT NULL COMMENT '登记日期',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态',
  `ipenable` char(2) COLLATE utf8_bin DEFAULT '1',
  `takerate` varchar(10) COLLATE utf8_bin DEFAULT '0',
  `depositrate` varchar(10) COLLATE utf8_bin DEFAULT '0',
  `transfertype` varchar(2) COLLATE utf8_bin DEFAULT '1',
  `rate` double DEFAULT '100',
  `rate2` double DEFAULT '1',
  `currencycode` char(8) COLLATE utf8_bin DEFAULT 'CNY' COMMENT '币种',
  PRIMARY KEY (`enterprisecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1638 COMMENT='企业';

-- ----------------------------
-- Table structure for enterprise_activity_customization
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_activity_customization`;
CREATE TABLE `enterprise_activity_customization` (
  `ecactivitycode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `activitytemplatecode` int(11) NOT NULL COMMENT '活动模板ID',
  `activityname` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '活动名称',
  `unshare` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '不共享活动',
  `activityimage` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '活动PC端图片',
  `activityimagehfive` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '活动H5端图片',
  `activitycontent` text COLLATE utf8_bin COMMENT '活动内容',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态',
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `ord` int(11) DEFAULT '99',
  `activitycontenth5` text COLLATE utf8_bin,
  PRIMARY KEY (`ecactivitycode`),
  KEY `FK_enterprise_activity_customization_enterprise_enterprisecode` (`enterprisecode`),
  KEY `enterprise_activity_customization_ix2` (`activitytemplatecode`),
  CONSTRAINT `FK_enterprise_activity_customization_enterprise_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='企业活动定制';

-- ----------------------------
-- Table structure for enterprise_activity_customization_setting
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_activity_customization_setting`;
CREATE TABLE `enterprise_activity_customization_setting` (
  `eacscode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ecactivitycode` int(11) NOT NULL COMMENT '企业定制活动编码',
  `activitysettingcode` int(11) NOT NULL COMMENT '活动参数编码',
  `agementvalue` varchar(2000) NOT NULL COMMENT '参数值',
  PRIMARY KEY (`eacscode`),
  KEY `enterprise_activity_customization_setting_ix1` (`activitysettingcode`)
) ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8 COMMENT='企业定制活动参数';

-- ----------------------------
-- Table structure for enterprise_activity_pay
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_activity_pay`;
CREATE TABLE `enterprise_activity_pay` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `employeecode` char(8) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` varchar(20) DEFAULT NULL,
  `parentemployeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `acname` varchar(500) DEFAULT NULL,
  `ecactivitycode` int(11) DEFAULT NULL,
  `paytype` int(11) DEFAULT NULL,
  `paystatus` int(11) DEFAULT NULL,
  `paymoneyaudit` double DEFAULT NULL,
  `paymoneyreal` double DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `audittime` datetime DEFAULT NULL,
  `auditer` varchar(50) DEFAULT NULL,
  `paytyime` datetime DEFAULT NULL,
  `payer` varchar(50) DEFAULT NULL,
  `auditremark` varchar(500) DEFAULT NULL,
  `descs` varchar(500) DEFAULT NULL,
  `lsbs` varchar(20) DEFAULT '1',
  `depositmoney` double DEFAULT '0',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=9455 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enterprise_agength5_level
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_agength5_level`;
CREATE TABLE `enterprise_agength5_level` (
  `employeelevelcode` char(10) COLLATE utf8_bin NOT NULL COMMENT '员工级别编码',
  `employeelevelname` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '员工级别',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `upperlevel_rate` decimal(10,2) DEFAULT NULL COMMENT '直接上级返现比例',
  `upperlevel_rate2` decimal(10,2) DEFAULT NULL COMMENT '间接上级返现比例',
  `personcount` int(3) DEFAULT '3' COMMENT '升级条件：需发展的会员人数',
  `agengtcount` int(3) DEFAULT '3' COMMENT '升级条件：需发展的代理人数',
  `orderamount` decimal(16,2) DEFAULT '1.00' COMMENT '升级条件：下线代理需完成的元宝交易量',
  `datastatus` int(4) DEFAULT '1',
  `ord` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`employeelevelcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1820 COMMENT='企业代理级别';

-- ----------------------------
-- Table structure for enterprise_banner_info
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_banner_info`;
CREATE TABLE `enterprise_banner_info` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `bannername` varchar(50) DEFAULT NULL,
  `bannertype` varchar(50) DEFAULT 'PC' COMMENT 'banner使用类型：PC，H5',
  `imgpath` varchar(500) DEFAULT NULL,
  `linkpath` varchar(500) DEFAULT NULL,
  `ord` int(11) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `enterprisecode` char(8) DEFAULT NULL,
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enterprise_brand_activity
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_brand_activity`;
CREATE TABLE `enterprise_brand_activity` (
  `enterprisebrandactivitycode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '团队编码',
  `ecactivitycode` int(11) DEFAULT NULL COMMENT '定制活动编码',
  `begintime` datetime NOT NULL COMMENT '活动开始时间',
  `endtime` datetime NOT NULL COMMENT '活动结束时间',
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '活动状态',
  PRIMARY KEY (`enterprisebrandactivitycode`),
  KEY `FK_enterprise_brand_activity_enterprise_brand_brandcode` (`brandcode`),
  KEY `FK_enterprise_brand_activity_enterprise_employee_employeecode` (`employeecode`),
  KEY `FK_enterprise_brand_activity_enterprise_enterprisecode` (`enterprisecode`),
  KEY `FK_enterprise_brand_activity_activity_template_activitycode` (`ecactivitycode`),
  CONSTRAINT `FK_enterprise_brand_activity_activity_template_activitycode` FOREIGN KEY (`ecactivitycode`) REFERENCES `enterprise_activity_customization` (`ecactivitycode`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_enterprise_brand_activity_enterprise_brand_brandcode` FOREIGN KEY (`brandcode`) REFERENCES `enterprise_operating_brand` (`brandcode`),
  CONSTRAINT `FK_enterprise_brand_activity_enterprise_employee_employeecode` FOREIGN KEY (`employeecode`) REFERENCES `enterprise_employee` (`employeecode`),
  CONSTRAINT `FK_enterprise_brand_activity_enterprise_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=3276 COMMENT='企业品牌活动';

-- ----------------------------
-- Table structure for enterprise_brand_contact
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_brand_contact`;
CREATE TABLE `enterprise_brand_contact` (
  `contactcode` char(10) COLLATE utf8_bin NOT NULL COMMENT '联系方式编码',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin NOT NULL COMMENT '品牌编码',
  `contacttitle` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '联系方式title',
  `contacttype` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '联系方式类型(QQ,Skyle,Vchat,Phone)',
  `content` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '联系方式内容',
  `contenttype` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '内容类型(value,link,img)',
  `enable` char(1) COLLATE utf8_bin NOT NULL COMMENT '是否启用(1、启用 2、禁用)',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据类型(1、正常 -1、删除)',
  PRIMARY KEY (`contactcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=4096 COMMENT='品牌客服联系方式';

-- ----------------------------
-- Table structure for enterprise_brand_notic
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_brand_notic`;
CREATE TABLE `enterprise_brand_notic` (
  `noticcode` char(12) COLLATE utf8_bin NOT NULL COMMENT '公告编码',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `notictype` varchar(15) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '公告类型',
  `title` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '公告标题',
  `content` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '公告内容',
  `begintime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '公告开始时间',
  `endtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公告结束时间',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`noticcode`),
  KEY `FK_enterprise_brand_notic_enterprise_operating_brand_brandcode` (`brandcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=4096;

-- ----------------------------
-- Table structure for enterprise_employee
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_employee`;
CREATE TABLE `enterprise_employee` (
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '上级员工编码',
  `parentemployeeaccount` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '上级员工账号',
  `employeetypecode` char(4) COLLATE utf8_bin NOT NULL COMMENT '员工类型编码',
  `employeelevelcode` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '员工层级编码',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `loginaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '登录账号',
  `loginpassword` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '登录密码',
  `fundpassword` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '资金密码',
  `displayalias` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '用户昵称',
  `onlinestatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT '在线状态(0=下线 1=在线)',
  `employeestatus` tinyint(4) NOT NULL DEFAULT '1' COMMENT '员工状态(1.启用 2.锁定游戏 3.禁用)',
  `logindatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册登录时间',
  `lastlogintime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次登录时间',
  `dividend` decimal(5,4) DEFAULT '0.0000' COMMENT '分红',
  `share` decimal(5,4) DEFAULT '0.0000' COMMENT '占成',
  `creditrating` tinyint(4) NOT NULL DEFAULT '0',
  `registercode` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '注册链接编码',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '1:有效用户，-1:无效的用户',
  `loginpassword2` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `phoneno` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `ipstatus` varchar(2) COLLATE utf8_bin DEFAULT '1' COMMENT '是否IP限制: 1 - 限制, ',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `qq` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `wechat` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `alipay` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `phonestatus` varchar(2) COLLATE utf8_bin DEFAULT '0',
  PRIMARY KEY (`employeecode`),
  KEY `ix_enterprise_employee_employeecode_parentemployeecode` (`employeecode`,`parentemployeecode`),
  KEY `IX_enterprise_employee_parentemployeecode` (`parentemployeecode`),
  KEY `FK_enterprise_employ` (`employeelevelcode`),
  KEY `FK_enterprise_employee_enterprise_employee_type_employeetypecode` (`employeetypecode`),
  KEY `IDX_enterprise_employee_dividend` (`dividend`),
  KEY `IDX_enterprise_employee_share` (`share`),
  KEY `index_loginaccount` (`loginaccount`),
  KEY `index_enterprisecode` (`enterprisecode`),
  KEY `index_brandcode` (`brandcode`),
  KEY `enterprise_employee_ix10` (`phoneno`),
  CONSTRAINT `FK_enterprise_employee_enterprise_employee_type_employeetypecode` FOREIGN KEY (`employeetypecode`) REFERENCES `enterprise_employee_type` (`employeetypecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1260 COMMENT='代理可以看成企业在外的员工\r\n\r\n是否要加推荐人 活动渠道  设备渠道（ios,android,ip';

-- ----------------------------
-- Table structure for enterprise_employee_all_uplevels
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_employee_all_uplevels`;
CREATE TABLE `enterprise_employee_all_uplevels` (
  `employeecode` char(8) NOT NULL,
  `parentemployeecode` varchar(8) NOT NULL DEFAULT '00000000',
  `employeetypecode` char(4) NOT NULL,
  `employeeallupleves` varchar(1000) NOT NULL COMMENT '最大100层，如果超过100层级， 调整该字段',
  `brandcode` char(8) DEFAULT NULL,
  `loginaccount` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`employeecode`),
  KEY `idx_enterprise_employee_all_uplevels_employeeallupleves_eep` (`employeeallupleves`(191),`employeetypecode`,`employeecode`,`parentemployeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='包函有所有用户的层级字段， 这个表由enterprise_employee表触发器来完成维护';

-- ----------------------------
-- Table structure for enterprise_employee_capital_account
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_employee_capital_account`;
CREATE TABLE `enterprise_employee_capital_account` (
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `balance` decimal(16,2) NOT NULL COMMENT '账户余额',
  `upintegralbalance` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '上分前余额',
  `accumulateddeposit` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '累计存款',
  `accumulatedwithdraw` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '累计提现',
  PRIMARY KEY (`employeecode`),
  KEY `index_employeecode` (`employeecode`),
  KEY `index_parentemployeecode` (`parentemployeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1638 COMMENT='企业人员资金账户';

-- ----------------------------
-- Table structure for enterprise_employee_information
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_employee_information`;
CREATE TABLE `enterprise_employee_information` (
  `informationcode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户银行卡编码',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `bankcode` char(4) COLLATE utf8_bin NOT NULL COMMENT '银行编码',
  `paymentaccount` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '账号',
  `accountname` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '户名',
  `openningbank` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '开户支行',
  `phonenumber` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `QQ` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT 'QQ',
  `skype` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'skype',
  `email` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'email',
  `infomationcomment` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '状态(1:锁定 2:解锁 )',
  `datastatus` char(2) COLLATE utf8_bin DEFAULT '1' COMMENT '1正常，-1删除',
  PRIMARY KEY (`informationcode`),
  KEY `FK_ER_bank_N_bank_card` (`bankcode`),
  KEY `FK_enterprise_employee_information_enterprise` (`employeecode`),
  KEY `index_enterprisecode` (`enterprisecode`),
  CONSTRAINT `FK_enterprise_employee_information_enterprise` FOREIGN KEY (`employeecode`) REFERENCES `enterprise_employee` (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=16384;

-- ----------------------------
-- Table structure for enterprise_employee_level
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_employee_level`;
CREATE TABLE `enterprise_employee_level` (
  `employeelevelcode` char(10) COLLATE utf8_bin NOT NULL COMMENT '员工级别编码',
  `employeelevel` varchar(8) CHARACTER SET utf8 NOT NULL COMMENT '员工级别',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `taketimeofday` smallint(6) DEFAULT NULL COMMENT '每日取款次数',
  `takemoneyofday` decimal(16,2) DEFAULT NULL COMMENT '每日取款金额',
  `bankcardcout` int(3) DEFAULT '5' COMMENT '可绑定银行卡数量',
  `datastatus` int(4) DEFAULT '1',
  `isdefault` varchar(2) COLLATE utf8_bin DEFAULT '0',
  `ord` int(11) DEFAULT '1',
  `conditionlevel` varchar(100) COLLATE utf8_bin DEFAULT '0-0',
  PRIMARY KEY (`employeelevelcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1820 COMMENT='企业员工级别';

-- ----------------------------
-- Table structure for enterprise_employee_level_bonus
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_employee_level_bonus`;
CREATE TABLE `enterprise_employee_level_bonus` (
  `employeelevelcode` char(10) NOT NULL,
  `gametype` varchar(8) NOT NULL,
  `categorytype` varchar(4) NOT NULL,
  `bonus` decimal(5,4) NOT NULL DEFAULT '0.0000',
  PRIMARY KEY (`employeelevelcode`,`gametype`,`categorytype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enterprise_employee_regedit_log
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_employee_regedit_log`;
CREATE TABLE `enterprise_employee_regedit_log` (
  `lsh` varchar(36) NOT NULL DEFAULT '',
  `enterprisecode` char(8) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(20) DEFAULT NULL,
  `parentemployeeaccount` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lsh`),
  KEY `index_enterprise_employee_regedit_log_1` (`enterprisecode`),
  KEY `index_enterprise_employee_regedit_log_2` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enterprise_employee_type
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_employee_type`;
CREATE TABLE `enterprise_employee_type` (
  `employeetypecode` char(4) COLLATE utf8_bin NOT NULL COMMENT '员工类别编码',
  `employeetype` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '员工类别',
  `datastatus` int(4) DEFAULT '0' COMMENT '{0=有效的,1=无效的}',
  PRIMARY KEY (`employeetypecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=3276 COMMENT='企业员工类别';

-- ----------------------------
-- Table structure for enterprise_game
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_game`;
CREATE TABLE `enterprise_game` (
  `code` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `gamecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '接口编码',
  PRIMARY KEY (`code`),
  KEY `FK_enterprise_game_enterprise_enterprisecode` (`enterprisecode`),
  CONSTRAINT `FK_enterprise_game_enterprise_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`)
) ENGINE=InnoDB AUTO_INCREMENT=1390 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=334;

-- ----------------------------
-- Table structure for enterprise_information
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_information`;
CREATE TABLE `enterprise_information` (
  `enterpriseinformationcode` char(8) COLLATE utf8_bin NOT NULL COMMENT '企业银行卡编码',
  `bankcode` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '银行代码',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `openningaccount` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '账号或地址',
  `accountname` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '开户名',
  `openningbank` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '开户支行',
  `enterprisecontact` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '企业联系人',
  `enterprisephone` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '企业联系人电话',
  `enterpriseqq` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '企业联系人QQ',
  `enterpriseemail` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '企业联系人邮箱',
  `currentbalance` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '当前余额',
  `enable` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '是否启用',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`enterpriseinformationcode`),
  KEY `FK_enterprise_information_bank_bankcode` (`bankcode`),
  KEY `FK_enterprise_information_enterprise_enterprisecode` (`enterprisecode`),
  KEY `UK_enterprise_information` (`openningbank`,`datastatus`),
  CONSTRAINT `FK_enterprise_information_enterprise_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=5461;

-- ----------------------------
-- Table structure for enterprise_information_qrcode
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_information_qrcode`;
CREATE TABLE `enterprise_information_qrcode` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `qrtype` int(2) DEFAULT '1',
  `qrurl` varchar(500) DEFAULT NULL,
  `qraccountno` varchar(50) DEFAULT NULL,
  `qraccountname` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enterprise_operating_brand
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_operating_brand`;
CREATE TABLE `enterprise_operating_brand` (
  `brandcode` char(8) COLLATE utf8_bin NOT NULL,
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL,
  `brandname` varchar(16) COLLATE utf8_bin NOT NULL,
  `brandfoundedtime` datetime NOT NULL,
  `webtemplatecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT 'web模板编码',
  `logopath` varchar(150) COLLATE utf8_bin NOT NULL,
  `defualtchip` decimal(5,2) NOT NULL DEFAULT '1.00' COMMENT '默认打码',
  `datastatus` char(2) COLLATE utf8_bin DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`brandcode`),
  KEY `FK_enterprise_operating_brand_webview_template_webtemplatecode` (`webtemplatecode`),
  KEY `index_enterprisecode` (`enterprisecode`),
  CONSTRAINT `FK_enterprise_operating_brand_webview_template_webtemplatecode` FOREIGN KEY (`webtemplatecode`) REFERENCES `webview_template` (`webtemplatecode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=2730 COMMENT='一般指前端的现金网';

-- ----------------------------
-- Table structure for enterprise_operating_brand_game
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_operating_brand_game`;
CREATE TABLE `enterprise_operating_brand_game` (
  `brandcode` char(8) COLLATE utf8_bin NOT NULL COMMENT '品牌编码',
  `gamecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '接口编码',
  `flag` int(11) DEFAULT '0',
  PRIMARY KEY (`brandcode`,`gamecode`),
  KEY `FK_enterprise_operating_brand_game_game_gamecode` (`gamecode`),
  CONSTRAINT `FK_enterprise_operati` FOREIGN KEY (`brandcode`) REFERENCES `enterprise_operating_brand` (`brandcode`),
  CONSTRAINT `FK_enterprise_operating_brand_game_game_gamecode` FOREIGN KEY (`gamecode`) REFERENCES `game` (`gamecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=431 COMMENT='企业品牌接入游戏';

-- ----------------------------
-- Table structure for enterprise_operating_brand_pay
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_operating_brand_pay`;
CREATE TABLE `enterprise_operating_brand_pay` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `enterprisecode` char(8) DEFAULT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '品牌编码',
  `paycallbackurl` varchar(255) DEFAULT NULL COMMENT '回调域名',
  `adminurl` varchar(255) DEFAULT NULL,
  `datastatus` int(11) DEFAULT '1' COMMENT '是否有效 0=否1=是',
  PRIMARY KEY (`lsh`),
  KEY `enterprise_operating_brand_pay_ix1` (`enterprisecode`),
  KEY `enterprise_operating_brand_pay_ix2` (`brandcode`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='品牌支付回调域名管理';

-- ----------------------------
-- Table structure for enterprise_report_dates
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_report_dates`;
CREATE TABLE `enterprise_report_dates` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `enterprisecode` char(6) DEFAULT NULL,
  `reportdate` int(8) DEFAULT NULL COMMENT '报表日期',
  `member_regedit_count` int(11) DEFAULT NULL COMMENT '会员注册人数',
  `agent_regedit_count` int(11) DEFAULT NULL COMMENT '理代注册人数',
  `login_count` int(11) DEFAULT NULL COMMENT '登录人数',
  `first_deposit_count` int(11) DEFAULT NULL COMMENT '首存人数',
  `first_deposit_money` double DEFAULT NULL COMMENT '首存总额',
  `second_deposit_count` int(11) DEFAULT NULL COMMENT '二存人数',
  `second_deposit_money` double DEFAULT NULL COMMENT '二存总额',
  `no_second_count` int(11) DEFAULT NULL COMMENT '首存未二存人数',
  `no_three_count` int(11) DEFAULT NULL COMMENT '二存未三存人数',
  `today_deposit_count` int(11) DEFAULT NULL COMMENT '存款人数',
  `today_deposit_count1` int(11) DEFAULT NULL COMMENT '存款总人次',
  `today_deposit_money` double DEFAULT NULL COMMENT '存款总额',
  `today_take_count` int(11) DEFAULT NULL COMMENT '取款人数',
  `today_take_count1` int(11) DEFAULT NULL COMMENT '款取总人次',
  `today_take_money` double DEFAULT NULL COMMENT '取款总额',
  `today_betmoney` double DEFAULT NULL COMMENT '投注总额',
  `today_netmoney` double DEFAULT NULL COMMENT '赢输总额',
  `today_vaildmoney` double DEFAULT NULL COMMENT '有效投注总额',
  `today_prosmoney` double DEFAULT NULL COMMENT '冲正总额',
  `today_consmoney` double DEFAULT NULL COMMENT '冲负总额',
  `today_preferential_count` int(11) DEFAULT NULL COMMENT '优惠人数',
  `today_preferential_money` double DEFAULT NULL COMMENT '优惠总额',
  `today_wash_count` int(11) DEFAULT NULL COMMENT '洗码发放人数',
  `today_wash_money` double DEFAULT NULL COMMENT '洗码发放总额',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB AUTO_INCREMENT=503 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enterprise_thirdparty_payment
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_thirdparty_payment`;
CREATE TABLE `enterprise_thirdparty_payment` (
  `enterprisethirdpartycode` char(8) COLLATE utf8_bin NOT NULL COMMENT '企业第三方支付编码',
  `dscription` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '账号描述',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `thirdpartypaymenttypecode` char(4) COLLATE utf8_bin NOT NULL COMMENT '第三方支付类型编码',
  `currentbalance` decimal(16,2) DEFAULT '0.00' COMMENT '当前余额',
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '状态（1：启用 2：禁用)',
  `isdefualttakecard` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '是否为默认系统自动出款卡(0:否，1:是)',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态',
  `h5status` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1',
  `isbanks` tinyint(4) DEFAULT '1',
  `isweixin` tinyint(4) DEFAULT '1',
  `iszhifubao` tinyint(4) DEFAULT '1',
  `displayname` varchar(30) COLLATE utf8_bin DEFAULT '未定义',
  `minmoney` decimal(16,2) DEFAULT '100.00',
  `maxmoney` decimal(16,2) DEFAULT '50000.00',
  `ord` varchar(2) COLLATE utf8_bin DEFAULT '99',
  `callbackurl` varchar(500) COLLATE utf8_bin DEFAULT 'http://api.hyzonghe.net/',
  PRIMARY KEY (`enterprisethirdpartycode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1638;

-- ----------------------------
-- Table structure for enterprise_thirdparty_payment_agument
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_thirdparty_payment_agument`;
CREATE TABLE `enterprise_thirdparty_payment_agument` (
  `paymentagumentcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisethirdpartycode` char(8) COLLATE utf8_bin NOT NULL COMMENT '企业第三方支付编码',
  `paymentsettingcode` char(5) COLLATE utf8_bin NOT NULL COMMENT '第三方支付参数编码',
  `agumentvalue` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '参数值',
  PRIMARY KEY (`paymentagumentcode`),
  KEY `index_enterprisethirdpartycode` (`enterprisethirdpartycode`)
) ENGINE=InnoDB AUTO_INCREMENT=877 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=512;

-- ----------------------------
-- Table structure for enterprise_webview
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_webview`;
CREATE TABLE `enterprise_webview` (
  `ewvcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `enterprisecode` char(6) NOT NULL COMMENT '企业编码',
  `webtemplatecode` char(6) NOT NULL COMMENT 'web模板编码',
  `sitetype` char(1) NOT NULL DEFAULT '1' COMMENT '1、会员站点  2、代理站点',
  PRIMARY KEY (`ewvcode`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 COMMENT='web模板分配';

-- ----------------------------
-- Table structure for enterprise_withdral_config
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_withdral_config`;
CREATE TABLE `enterprise_withdral_config` (
  `enterprisecode` char(6) NOT NULL COMMENT '企业编码',
  `withdralway` char(1) NOT NULL COMMENT '取款方式（1:系统自动出款，2:财务手动出款）',
  `upperlimit` decimal(10,2) DEFAULT NULL COMMENT '出款上线(金额设置)',
  PRIMARY KEY (`enterprisecode`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=30 COMMENT='企业出款方式配置';

-- ----------------------------
-- Table structure for exchange_rate
-- ----------------------------
DROP TABLE IF EXISTS `exchange_rate`;
CREATE TABLE `exchange_rate` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `currencyname` varchar(12) NOT NULL COMMENT '货币名称',
  `currencycode` char(8) NOT NULL COMMENT '货币字母代码',
  `exchangerate` decimal(8,4) DEFAULT '0.0000' COMMENT '对人民币汇率',
  `oprationtime` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `oprationperson` varchar(12) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for favourable
-- ----------------------------
DROP TABLE IF EXISTS `favourable`;
CREATE TABLE `favourable` (
  `lsh` varchar(36) NOT NULL DEFAULT '',
  `favourablename` varchar(100) DEFAULT NULL,
  `isonce` int(11) DEFAULT '0',
  `enterprisecode` char(6) DEFAULT NULL,
  `lsbs` double DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `isdeault` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for favourable_user
-- ----------------------------
DROP TABLE IF EXISTS `favourable_user`;
CREATE TABLE `favourable_user` (
  `lsh` varchar(36) NOT NULL DEFAULT '',
  `favourableid` varchar(36) DEFAULT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  PRIMARY KEY (`lsh`),
  KEY `index1` (`enterprisecode`,`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for game
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `gamecode` char(4) COLLATE utf8_bin NOT NULL COMMENT '游戏编码',
  `gametype` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏平台编码',
  `gamename` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏名称',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  `picid` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '素材ID',
  `android` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'android唤起语句',
  `h5` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT 'IOS唤起语句',
  `iso` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'H5登陆链接',
  `downloadurl` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '下载链接',
  `datastatus` char(2) COLLATE utf8_bin DEFAULT '1' COMMENT '数据是否删除(1有效，-1删除)',
  PRIMARY KEY (`gamecode`),
  UNIQUE KEY `UK_game_gametype` (`gametype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1638 COMMENT='游戏';

-- ----------------------------
-- Table structure for game_api_input
-- ----------------------------
DROP TABLE IF EXISTS `game_api_input`;
CREATE TABLE `game_api_input` (
  `apicode` char(8) COLLATE utf8_bin NOT NULL COMMENT '接口编码',
  `platformcode` char(64) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏编码',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `apiurl` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '接口地址',
  `outputapistatus` tinyint(4) NOT NULL COMMENT '接口状态',
  `apikey1` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '接口key1',
  `apikey2` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '接口key2',
  `apiuser` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '接口用户',
  `apipassword` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '接口用户密码',
  `apicomment` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '接口备注',
  `trytime` smallint(6) DEFAULT '5' COMMENT '接口尝试连接次数',
  PRIMARY KEY (`apicode`),
  UNIQUE KEY `UK_game_api_input_enterprisecode` (`enterprisecode`),
  KEY `FK_game_api_input_game_gamecode` (`platformcode`),
  CONSTRAINT `FK_game_api_input_enterprise_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=2730 COMMENT='游戏接入接口';

-- ----------------------------
-- Table structure for game_category
-- ----------------------------
DROP TABLE IF EXISTS `game_category`;
CREATE TABLE `game_category` (
  `categorycode` int(11) NOT NULL AUTO_INCREMENT COMMENT '游戏种类编码',
  `gametype` varchar(15) DEFAULT NULL,
  `categorytype` varchar(8) NOT NULL COMMENT '游戏种类类型标志(唯一)',
  `categoryname` varchar(8) NOT NULL COMMENT '游戏种类名称',
  `minbonus` decimal(5,4) NOT NULL DEFAULT '0.0000' COMMENT '最小洗码值',
  `maxbonus` decimal(5,4) NOT NULL DEFAULT '0.0000' COMMENT '最大洗码值',
  `sort` smallint(6) NOT NULL COMMENT '排序',
  PRIMARY KEY (`categorycode`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=1024;

-- ----------------------------
-- Table structure for game_class
-- ----------------------------
DROP TABLE IF EXISTS `game_class`;
CREATE TABLE `game_class` (
  `gameclasscode` int(11) NOT NULL AUTO_INCREMENT,
  `gametype` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `classchinaname` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏分类名称(中文)',
  `classenglishname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏分类名称(英文)',
  `parentclasstype` char(5) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单编码',
  `sort` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`gameclasscode`),
  KEY `FK_game_class_game_gametype` (`gametype`),
  CONSTRAINT `FK_game_class_game_gametype` FOREIGN KEY (`gametype`) REFERENCES `game` (`gametype`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=780 COMMENT='游戏分类';

-- ----------------------------
-- Table structure for game_class_details
-- ----------------------------
DROP TABLE IF EXISTS `game_class_details`;
CREATE TABLE `game_class_details` (
  `classdetailscode` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类明细编码,主键',
  `gameclasscode` char(4) NOT NULL COMMENT '分类编码',
  `classdetailsflag` varchar(10) NOT NULL COMMENT '游戏flag,对应各个平台的游戏编码',
  `detailschinaname` varchar(25) DEFAULT NULL COMMENT '游戏中文名',
  `detailsenglishname` varchar(50) DEFAULT NULL COMMENT '游戏英文名',
  `picturename` varchar(50) DEFAULT NULL COMMENT '图片名称',
  `path` varchar(255) DEFAULT NULL COMMENT '服务器存储路径',
  `sort` smallint(6) NOT NULL COMMENT '排序',
  `disable` char(1) DEFAULT '1' COMMENT '是否可用(1:可用，2:不可用)',
  PRIMARY KEY (`classdetailscode`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=253 COMMENT='游戏分类-详细游戏信息';

-- ----------------------------
-- Table structure for game_platform_prefix
-- ----------------------------
DROP TABLE IF EXISTS `game_platform_prefix`;
CREATE TABLE `game_platform_prefix` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) DEFAULT NULL COMMENT '企业编码',
  `game_platform` varchar(10) DEFAULT NULL COMMENT '游戏平台',
  `prefixcode` varchar(10) DEFAULT NULL COMMENT '前缀',
  PRIMARY KEY (`lsh`),
  KEY `index_enterprisecode_game_platform` (`enterprisecode`,`game_platform`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hqgametype
-- ----------------------------
DROP TABLE IF EXISTS `hqgametype`;
CREATE TABLE `hqgametype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hq_gm` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=4096;

-- ----------------------------
-- Table structure for integral_exchange
-- ----------------------------
DROP TABLE IF EXISTS `integral_exchange`;
CREATE TABLE `integral_exchange` (
  `lsh` varchar(36) NOT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(20) DEFAULT NULL,
  `integralcode` varchar(36) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `patchno` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `exchangedesc` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `checkaccount` varchar(20) DEFAULT NULL,
  `checktime` datetime DEFAULT NULL,
  `checkdesc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`lsh`),
  KEY `index1` (`enterprisecode`,`brandcode`),
  KEY `index2` (`employeecode`),
  KEY `index3` (`loginaccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for integral_record
-- ----------------------------
DROP TABLE IF EXISTS `integral_record`;
CREATE TABLE `integral_record` (
  `lsh` varchar(36) NOT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(20) DEFAULT NULL,
  `gametype` varchar(15) DEFAULT NULL,
  `gamebigtype` varchar(4) DEFAULT NULL,
  `platformid` varchar(50) DEFAULT NULL,
  `betmoney` decimal(10,2) DEFAULT '0.00',
  `rebatemoney` decimal(10,2) DEFAULT '0.00',
  `m2i_rate` decimal(10,2) DEFAULT '1.00',
  `amount` decimal(10,2) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `createtime` datetime DEFAULT NULL,
  `exchangetime` datetime DEFAULT NULL,
  `exchangetype` int(11) DEFAULT NULL,
  `exchangedesc` varchar(100) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`lsh`),
  KEY `index1` (`enterprisecode`,`brandcode`),
  KEY `index2` (`employeecode`),
  KEY `index3` (`loginaccount`),
  KEY `index4` (`gametype`,`gamebigtype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for integral_setting
-- ----------------------------
DROP TABLE IF EXISTS `integral_setting`;
CREATE TABLE `integral_setting` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT,
  `enterprisecode` char(6) DEFAULT NULL,
  `brandcode` char(8) DEFAULT NULL,
  `gametype` varchar(15) DEFAULT NULL,
  `gamebigtype` varchar(4) DEFAULT NULL,
  `m2i_rate` decimal(10,2) DEFAULT '1.00',
  `i2m_rate` decimal(10,2) DEFAULT '1.00',
  `timeoutday` int(11) DEFAULT '7',
  PRIMARY KEY (`lsh`),
  UNIQUE KEY `index1` (`enterprisecode`,`brandcode`,`gametype`,`gamebigtype`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for login_white_list
-- ----------------------------
DROP TABLE IF EXISTS `login_white_list`;
CREATE TABLE `login_white_list` (
  `lsh` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `enterprisecode` char(8) DEFAULT NULL COMMENT '企业编码',
  `ip` char(18) DEFAULT NULL COMMENT '白名单IP',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`lsh`),
  KEY `index_enterprisecode` (`enterprisecode`)
) ENGINE=InnoDB AUTO_INCREMENT=460 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `code` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) NOT NULL COMMENT '企业编码',
  `brandcode` char(8) DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(10) NOT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) DEFAULT NULL COMMENT '用户上级编码',
  `loginaccount` varchar(12) NOT NULL COMMENT '登陆账号',
  `logintime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登陆时间',
  `loginip` char(18) NOT NULL COMMENT '登陆IP',
  `machinecode` varchar(32) DEFAULT NULL COMMENT '机器码',
  `opratesystem` varchar(25) DEFAULT NULL COMMENT '操作系统',
  `browserversion` varchar(50) DEFAULT NULL COMMENT '浏览器版本',
  `refererurl` varchar(255) DEFAULT NULL COMMENT '来源网址',
  PRIMARY KEY (`code`),
  KEY `log_login_ix1` (`employeecode`),
  KEY `log_login_ix2` (`loginaccount`)
) ENGINE=InnoDB AUTO_INCREMENT=252785 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=168;

-- ----------------------------
-- Table structure for log_operation
-- ----------------------------
DROP TABLE IF EXISTS `log_operation`;
CREATE TABLE `log_operation` (
  `logcode` int(10) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `tablename` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '数据表名称',
  `servicename` varchar(25) COLLATE utf8_bin NOT NULL COMMENT '业务名称',
  `visiteurl` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '访问URL',
  `oprationtype` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '操作类型',
  `oprationtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '上级用户编码',
  `oprationperson` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '操作人',
  PRIMARY KEY (`logcode`),
  KEY `IDX_log_operation_employeecode` (`employeecode`),
  KEY `UK_log_operation_oprationperson` (`oprationperson`),
  KEY `UK_log_operation_servicename` (`servicename`)
) ENGINE=InnoDB AUTO_INCREMENT=82004 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=185 COMMENT='用户操作日志';

-- ----------------------------
-- Table structure for log_operation_detail
-- ----------------------------
DROP TABLE IF EXISTS `log_operation_detail`;
CREATE TABLE `log_operation_detail` (
  `logdetailcode` int(10) NOT NULL AUTO_INCREMENT COMMENT '详细日志ID',
  `logcode` int(11) NOT NULL COMMENT '日志ID',
  `fieldname` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '操作字段',
  `fieldvalue` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '操作值',
  `conditions` text COLLATE utf8_bin COMMENT '操作条件',
  PRIMARY KEY (`logdetailcode`)
) ENGINE=InnoDB AUTO_INCREMENT=799288 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=58 COMMENT='操作日志详细';

-- ----------------------------
-- Table structure for payment_type
-- ----------------------------
DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE `payment_type` (
  `paymenttypecode` char(4) COLLATE utf8_bin NOT NULL,
  `paymenttype` varchar(32) COLLATE utf8_bin NOT NULL,
  `datastatus` char(2) COLLATE utf8_bin DEFAULT '1',
  PRIMARY KEY (`paymenttypecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=8192 COMMENT='银行卡 银行卡-柜台办理 银行卡-ATM办理 易宝 支付宝 汇潮 等';

-- ----------------------------
-- Table structure for permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `permission_menu`;
CREATE TABLE `permission_menu` (
  `menucode` char(6) COLLATE utf8_bin NOT NULL,
  `parentmenucode` char(6) COLLATE utf8_bin DEFAULT NULL,
  `menuname` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `englishname` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `menuurl` varchar(256) COLLATE utf8_bin NOT NULL,
  `menusort` tinyint(4) DEFAULT NULL,
  `menulevel` tinyint(4) DEFAULT NULL,
  `menustatus` tinyint(4) NOT NULL,
  `datastatus` char(2) COLLATE utf8_bin DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`menucode`),
  KEY `index_parentmenucode` (`parentmenucode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=606 COMMENT='增，删，改，查，开放下一级，隐私信息显示\r\n页面只作逻辑删除，数据库维护做物理删除';

-- ----------------------------
-- Table structure for permission_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role` (
  `rolecode` char(8) NOT NULL COMMENT '主键',
  `rolename` varchar(20) NOT NULL COMMENT '角色名称',
  `enterprisecode` char(8) NOT NULL COMMENT '企业CODE',
  `datastatus` char(2) NOT NULL DEFAULT '1' COMMENT '1:有效数据，-1:无效的数据',
  `permissions` text COMMENT '拥有的权限',
  PRIMARY KEY (`rolecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for platform_api_output
-- ----------------------------
DROP TABLE IF EXISTS `platform_api_output`;
CREATE TABLE `platform_api_output` (
  `outputapicode` char(32) COLLATE utf8_bin NOT NULL COMMENT '输出接口编码',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `outputapiurl` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '接口地址',
  `outputapistatus` tinyint(4) NOT NULL DEFAULT '1' COMMENT '接口状态 1.可用 0.不可用',
  `apikey1` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'aesKey ',
  `apikey2` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'md5Key',
  `apiuser` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '接口用户',
  `apipassword` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '接口用户密码',
  `apicomment` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '接口备注',
  PRIMARY KEY (`outputapicode`),
  KEY `FK_platform_api_output_enterprise_enterprisecode` (`enterprisecode`),
  CONSTRAINT `FK_platform_api_output_enterprise_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1092 COMMENT='平台输出接口';

-- ----------------------------
-- Table structure for private_data_access
-- ----------------------------
DROP TABLE IF EXISTS `private_data_access`;
CREATE TABLE `private_data_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '企业编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '员工编码',
  PRIMARY KEY (`id`),
  KEY `FK_private_data_access_enterprise_employee_employeecode` (`employeecode`),
  KEY `FK_private_data_access_enterprise_enterprisecode` (`enterprisecode`),
  CONSTRAINT `FK_private_data_access_enterprise_employee_employeecode` FOREIGN KEY (`employeecode`) REFERENCES `enterprise_employee` (`employeecode`),
  CONSTRAINT `FK_private_data_access_enterprise_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1638 COMMENT='隐私数据权限控制';

-- ----------------------------
-- Table structure for report_daily_agent
-- ----------------------------
DROP TABLE IF EXISTS `report_daily_agent`;
CREATE TABLE `report_daily_agent` (
  `reportcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT ' 上级员工编码',
  `loginaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '用户账户',
  `bet` decimal(16,2) NOT NULL COMMENT '投注量',
  `amount` decimal(16,2) NOT NULL COMMENT '打码金额',
  `ratio` decimal(16,4) NOT NULL COMMENT '打码比率',
  `time` datetime NOT NULL COMMENT '打码日期',
  `reporttime` datetime NOT NULL COMMENT '报表日期',
  `gameplatform` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '游戏平台',
  `gametype` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '打码发放状态(0：未发放 1：已发放 2：周结汇总)',
  `is_weekly_agent` tinyint(1) DEFAULT '0',
  `payno` varchar(19) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`reportcode`),
  KEY `FK_daily_report_agent_brandcode` (`brandcode`),
  KEY `FK_daily_report_agent_employeecode` (`employeecode`),
  KEY `FK_daily_report_agent_enterprisecode` (`enterprisecode`),
  KEY `FK_daily_report_agent_parentemployeecode` (`parentemployeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=7800 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代理日结打码报表';

-- ----------------------------
-- Table structure for report_daily_member
-- ----------------------------
DROP TABLE IF EXISTS `report_daily_member`;
CREATE TABLE `report_daily_member` (
  `reportcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT ' 上级员工编码',
  `loginaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '用户账户',
  `bet` decimal(16,2) NOT NULL COMMENT '投注量',
  `amount` decimal(16,2) NOT NULL COMMENT '打码金额',
  `ratio` decimal(3,3) NOT NULL COMMENT '打码比率',
  `time` datetime NOT NULL COMMENT '打码日期',
  `reporttime` datetime NOT NULL COMMENT '报表日期',
  `gametype` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '打码发放状态',
  `payno` varchar(19) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`reportcode`),
  KEY `FK_daily_report_player_employeecode` (`employeecode`),
  KEY `FK_daily_report_player_enterprisecode` (`enterprisecode`),
  KEY `FK_daily_report_player_parentemployeecode` (`parentemployeecode`),
  KEY `FK_daily_report_player_brandcode` (`brandcode`),
  CONSTRAINT `FK_daily_report_player_employeecode` FOREIGN KEY (`employeecode`) REFERENCES `enterprise_employee` (`employeecode`),
  CONSTRAINT `FK_daily_report_player_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`),
  CONSTRAINT `FK_daily_report_player_parentemployeecode` FOREIGN KEY (`parentemployeecode`) REFERENCES `enterprise_employee` (`employeecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='玩家日结打码报表';

-- ----------------------------
-- Table structure for report_weekly_agent
-- ----------------------------
DROP TABLE IF EXISTS `report_weekly_agent`;
CREATE TABLE `report_weekly_agent` (
  `reportcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT ' 上级员工编码',
  `loginaccount` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '用户账户',
  `bet` decimal(16,2) NOT NULL COMMENT '投注量',
  `amount` decimal(16,2) NOT NULL COMMENT '打码金额',
  `subtract` decimal(16,2) NOT NULL COMMENT '需要减去的已发放金额',
  `ratio` decimal(16,4) NOT NULL COMMENT '打码比率',
  `starttime` datetime NOT NULL COMMENT '打码开始日期',
  `endtime` datetime NOT NULL COMMENT '打码结束日期',
  `reporttime` datetime NOT NULL COMMENT '报表日期',
  `gameplatform` varchar(10) COLLATE utf8_bin NOT NULL,
  `gametype` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '打码发放状态 1未发放 2已发放',
  `paytype` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '业务类型 1正常 2补发',
  `patchno` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '批次号，同一组数据的唯一编码，包括：员工编码、游戏平台、游戏类型、报表开始时间',
  `actual` decimal(16,2) DEFAULT '0.00',
  `payno` varchar(19) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`reportcode`),
  KEY `FK_weekly_report_T004_employeecode` (`employeecode`),
  KEY `FK_weekly_report_T004_enterprisecode` (`enterprisecode`),
  KEY `FK_weekly_report_T004_parentemployeecode` (`parentemployeecode`),
  KEY `FK_weekly_report_T004_brandcode` (`brandcode`),
  CONSTRAINT `FK_weekly_report_T004_employeecode` FOREIGN KEY (`employeecode`) REFERENCES `enterprise_employee` (`employeecode`),
  CONSTRAINT `FK_weekly_report_T004_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`),
  CONSTRAINT `FK_weekly_report_T004_parentemployeecode` FOREIGN KEY (`parentemployeecode`) REFERENCES `enterprise_employee` (`employeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=2718 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代理周结打码报表';

-- ----------------------------
-- Table structure for report_weekly_member
-- ----------------------------
DROP TABLE IF EXISTS `report_weekly_member`;
CREATE TABLE `report_weekly_member` (
  `reportcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `employeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '员工编码',
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT ' 上级员工编码',
  `loginaccount` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户账户',
  `bet` decimal(16,2) NOT NULL COMMENT '投注量',
  `amount` decimal(16,2) NOT NULL COMMENT '打码金额',
  `subtract` decimal(16,2) NOT NULL COMMENT '需要减去的已发放金额',
  `ratio` decimal(3,3) NOT NULL COMMENT '打码比率',
  `starttime` datetime NOT NULL COMMENT '打码开始日期',
  `endtime` datetime NOT NULL COMMENT '打码开始日期',
  `reporttime` datetime NOT NULL COMMENT '报表日期',
  `gameplatform` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '游戏平台',
  `gametype` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '打码发放状态',
  `paytype` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1',
  `patchno` varchar(36) COLLATE utf8_bin NOT NULL,
  `actual` decimal(16,2) DEFAULT '0.00',
  `payno` varchar(19) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`reportcode`),
  KEY `FK_report_weekly_T003_employeecode` (`employeecode`),
  KEY `FK_report_weekly_T003_enterprisecode` (`enterprisecode`),
  KEY `FK_report_weekly_T003_parentemployeecode` (`parentemployeecode`),
  KEY `FK_report_weekly_T003_brandcode` (`brandcode`),
  CONSTRAINT `FK_report_weekly_T003_employeecode` FOREIGN KEY (`employeecode`) REFERENCES `enterprise_employee` (`employeecode`),
  CONSTRAINT `FK_report_weekly_T003_enterprisecode` FOREIGN KEY (`enterprisecode`) REFERENCES `enterprise` (`enterprisecode`),
  CONSTRAINT `FK_report_weekly_T003_parentemployeecode` FOREIGN KEY (`parentemployeecode`) REFERENCES `enterprise_employee` (`employeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=20824 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='玩家周结打码报表';

-- ----------------------------
-- Table structure for table_pk_seed
-- ----------------------------
DROP TABLE IF EXISTS `table_pk_seed`;
CREATE TABLE `table_pk_seed` (
  `tablename` varchar(128) COLLATE utf8_bin NOT NULL,
  `performancefactor` tinyint(4) NOT NULL DEFAULT '0' COMMENT '在写入要求很高的表中，用于分割字符主键生成范围，这时采取随机生成',
  `seed` varchar(16) COLLATE utf8_bin NOT NULL,
  `codeprefix` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`tablename`,`performancefactor`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1638;

-- ----------------------------
-- Table structure for thirdparty_payment_bank
-- ----------------------------
DROP TABLE IF EXISTS `thirdparty_payment_bank`;
CREATE TABLE `thirdparty_payment_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID ,主键',
  `bankcode` char(4) NOT NULL COMMENT '银行编码',
  `thirdpartypaymenttypecode` char(4) DEFAULT NULL COMMENT '第三方支付编码',
  `paymenttypebankcode` varchar(20) DEFAULT NULL COMMENT '第三方支付银行编码',
  `enable` char(1) DEFAULT NULL COMMENT '启用与禁用',
  PRIMARY KEY (`id`),
  KEY `index_thirdpartypaymenttypecode` (`thirdpartypaymenttypecode`)
) ENGINE=InnoDB AUTO_INCREMENT=683 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=910 COMMENT='第三方支付银行';

-- ----------------------------
-- Table structure for thirdparty_payment_type
-- ----------------------------
DROP TABLE IF EXISTS `thirdparty_payment_type`;
CREATE TABLE `thirdparty_payment_type` (
  `thirdpartypaymenttypecode` char(4) COLLATE utf8_bin NOT NULL COMMENT '第三方支付类型编码',
  `thirdpartypaymenttypename` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '第三方支付类型名称',
  `status` char(1) COLLATE utf8_bin NOT NULL COMMENT '状态',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`thirdpartypaymenttypecode`),
  UNIQUE KEY `UK_third_party_payment_thirdpa` (`thirdpartypaymenttypecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=16384 COMMENT='第三方支付主表';

-- ----------------------------
-- Table structure for thirdparty_payment_type_setting
-- ----------------------------
DROP TABLE IF EXISTS `thirdparty_payment_type_setting`;
CREATE TABLE `thirdparty_payment_type_setting` (
  `paymentsettingcode` char(5) COLLATE utf8_bin NOT NULL COMMENT '第三方支付参数编码',
  `thirdpartypaymenttypecode` char(4) COLLATE utf8_bin NOT NULL COMMENT '第三方支付编码',
  `argumentfiled` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '参数字段',
  `argumentdesc` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '参数描述',
  `datastatus` char(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`paymentsettingcode`),
  KEY `FK_thirdparty_payment_type_setting_thirdparty_payment_type` (`thirdpartypaymenttypecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=2730 COMMENT='第三方支付参数表';

-- ----------------------------
-- Table structure for user_logs
-- ----------------------------
DROP TABLE IF EXISTS `user_logs`;
CREATE TABLE `user_logs` (
  `lsh` varchar(36) NOT NULL,
  `enterprisecode` char(6) DEFAULT NULL,
  `employeecode` char(8) DEFAULT NULL,
  `loginaccount` varchar(12) DEFAULT NULL,
  `operatype` varchar(20) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `operaer` varchar(50) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lsh`),
  KEY `user_logs_ix2` (`enterprisecode`,`loginaccount`,`operatype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_money_in_and_out
-- ----------------------------
DROP TABLE IF EXISTS `user_money_in_and_out`;
CREATE TABLE `user_money_in_and_out` (
  `moneyinoutcode` char(16) COLLATE utf8_bin NOT NULL COMMENT '上下分编码',
  `employeecode` char(8) COLLATE utf8_bin NOT NULL COMMENT '员工编码',
  `gametype` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `parentemployeecode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户上级编码',
  `moneyinoutdate` datetime DEFAULT NULL COMMENT '上下分时间',
  `moneyinoutamount` decimal(16,4) DEFAULT NULL COMMENT '上下分金额',
  `moneyinoutcomment` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `beforebalance` decimal(16,4) DEFAULT NULL COMMENT '上下分前余额',
  `afterbalance` decimal(16,4) DEFAULT NULL COMMENT '上下分后余额',
  `updatecapital` char(1) COLLATE utf8_bin NOT NULL COMMENT '是否成功 (1：是 2：否)',
  `opreatetype` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '上下分类型（1：上分，2：下分）',
  `backmoney` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '是否返还金额（1：否 ， 2：是）',
  `timesort` bigint(20) DEFAULT NULL COMMENT '排序字段',
  `orderno` varchar(19) COLLATE utf8_bin DEFAULT '0',
  `patchno` varchar(19) COLLATE utf8_bin DEFAULT '0',
  `enterprisecode` char(8) COLLATE utf8_bin DEFAULT '0',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT '0',
  `isdown` varchar(1) COLLATE utf8_bin DEFAULT '1',
  PRIMARY KEY (`moneyinoutcode`),
  KEY `index_employeecode` (`employeecode`),
  KEY `index_orderno` (`orderno`),
  KEY `index_patchno` (`patchno`),
  KEY `user_money_in_and_out_ix4` (`moneyinoutdate`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=16384 COMMENT='用户账变';

-- ----------------------------
-- Table structure for webview_template
-- ----------------------------
DROP TABLE IF EXISTS `webview_template`;
CREATE TABLE `webview_template` (
  `webtemplatecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '模板编码',
  `templatename` varchar(25) COLLATE utf8_bin NOT NULL COMMENT '模板名字',
  `sign` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '模板唯一标识',
  `templatetype` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '模板类型(CP:彩票, XJW:现金网)',
  `sitetype` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '1、会员站点  2、代理站点',
  PRIMARY KEY (`webtemplatecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=4096 COMMENT='会员游戏界面模板';

-- ----------------------------
-- Table structure for working_flow_configuration
-- ----------------------------
DROP TABLE IF EXISTS `working_flow_configuration`;
CREATE TABLE `working_flow_configuration` (
  `flowcode` char(8) COLLATE utf8_bin NOT NULL COMMENT '流程编号',
  `flowname` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '流程名称',
  `enterprisecode` char(6) COLLATE utf8_bin NOT NULL COMMENT '企业编码',
  `brandcode` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `flowtype` smallint(6) NOT NULL COMMENT '流程标志',
  `enable` smallint(6) NOT NULL COMMENT '是否启用',
  `flowsort` smallint(6) NOT NULL COMMENT '流程顺序',
  `flowthreshold` decimal(10,0) NOT NULL COMMENT '流程启动条件',
  `handletime` int(11) DEFAULT NULL COMMENT '处理时长',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `datastatus` smallint(6) NOT NULL DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`flowcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=1638 COMMENT='工作流配置表';

-- ----------------------------
-- Table structure for working_flow_object
-- ----------------------------
DROP TABLE IF EXISTS `working_flow_object`;
CREATE TABLE `working_flow_object` (
  `employeecode` char(10) COLLATE utf8_bin NOT NULL COMMENT '员工编码',
  `flowcode` char(8) COLLATE utf8_bin NOT NULL COMMENT '工作流编码',
  PRIMARY KEY (`employeecode`,`flowcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AVG_ROW_LENGTH=963 COMMENT='工作流审核对象';

-- ----------------------------
-- View structure for view_enterpriseinfomation
-- ----------------------------
DROP VIEW IF EXISTS `view_enterpriseinfomation`;
CREATE VIEW `view_enterpriseinfomation` AS select `ei`.`enterpriseinformationcode` AS `enterpriseinformationcode`,`ei`.`bankcode` AS `bankcode`,`ei`.`enterprisecode` AS `enterprisecode`,`b`.`bankname` AS `bankname`,`b`.`banklogo` AS `banklogo`,`b`.`bankurl` AS `bankurl`,`ei`.`brandcode` AS `brandcode`,`ei`.`openningaccount` AS `openningaccount`,`ei`.`accountname` AS `accountname`,`ei`.`openningbank` AS `openningbank`,`ei`.`enterprisecontact` AS `enterprisecontact`,`ei`.`enterprisephone` AS `enterprisephone`,`ei`.`enterpriseqq` AS `enterpriseqq`,`ei`.`enterpriseemail` AS `enterpriseemail`,`ei`.`currentbalance` AS `currentbalance`,`ei`.`enable` AS `enable`,`ei`.`datastatus` AS `datastatus` from (`enterprise_information` `ei` left join `bank` `b` on((`ei`.`bankcode` = `b`.`bankcode`))) ;

-- ----------------------------
-- View structure for view_enterprise_employee
-- ----------------------------
DROP VIEW IF EXISTS `view_enterprise_employee`;
CREATE VIEW `view_enterprise_employee` AS select `ee`.`registercode` AS `registercode`,`ee`.`employeecode` AS `employeecode`,`ee`.`enterprisecode` AS `enterprisecode`,`ee`.`displayalias` AS `displayalias`,`ee`.`loginaccount` AS `loginaccount`,`ee`.`loginpassword` AS `loginpassword`,`ee`.`loginpassword2` AS `loginpassword2`,`ee`.`phoneno` AS `phoneno`,`ee`.`phonestatus` AS `phonestatus`,`ee`.`ipstatus` AS `ipstatus`,`ee`.`email` AS `email`,`ee`.`wechat` AS `wechat`,`ee`.`alipay` AS `alipay`,`ee`.`qq` AS `qq`,`ee`.`fundpassword` AS `fundpassword`,`ee`.`parentemployeecode` AS `parentemployeecode`,`ee`.`parentemployeeaccount` AS `parentemployeeaccount`,`ee`.`employeetypecode` AS `employeetypecode`,`eet`.`employeetype` AS `employeetypename`,`ee`.`employeelevelcode` AS `employeelevelcode`,`eel`.`employeelevel` AS `employeelevelname`,`ee`.`brandcode` AS `brandcode`,`eob`.`brandname` AS `brandname`,`ee`.`onlinestatus` AS `onlinestatus`,`ee`.`employeestatus` AS `employeestatus`,`ee`.`logindatetime` AS `logindatetime`,`ee`.`lastlogintime` AS `lastlogintime`,`ee`.`dividend` AS `dividend`,`ee`.`share` AS `share`,`ee`.`creditrating` AS `creditrating`,`ee`.`datastatus` AS `datastatus`,`eeca`.`balance` AS `balance`,`eeca`.`accumulateddeposit` AS `accumulateddeposit`,`eeca`.`accumulatedwithdraw` AS `accumulatedwithdraw`,`e`.`enterprisename` AS `enterprisename` from (((((`enterprise_employee` `ee` left join `enterprise_operating_brand` `eob` on((`ee`.`brandcode` = `eob`.`brandcode`))) left join `enterprise_employee_type` `eet` on((`ee`.`employeetypecode` = `eet`.`employeetypecode`))) left join `enterprise_employee_level` `eel` on((`ee`.`employeelevelcode` = `eel`.`employeelevelcode`))) left join `enterprise_employee_capital_account` `eeca` on((`ee`.`employeecode` = `eeca`.`employeecode`))) left join `enterprise` `e` on((`ee`.`enterprisecode` = `e`.`enterprisecode`))) where ((`e`.`datastatus` = 1) and (`ee`.`datastatus` = 1) and (`eet`.`datastatus` = 1)) ;

-- ----------------------------
-- View structure for view_workingflowconfiguration
-- ----------------------------
DROP VIEW IF EXISTS `view_workingflowconfiguration`;
CREATE VIEW `view_workingflowconfiguration` AS select `wfc`.`flowcode` AS `flowcode`,`wfc`.`flowname` AS `flowname`,`wfc`.`enterprisecode` AS `enterprisecode`,`wfc`.`brandcode` AS `brandcode`,`eob`.`brandname` AS `brandname`,`wfc`.`flowtype` AS `flowtype`,`wfc`.`enable` AS `enable`,`wfc`.`flowsort` AS `flowsort`,`wfc`.`flowthreshold` AS `flowthreshold`,`wfc`.`handletime` AS `handletime`,`wfc`.`createtime` AS `createtime`,`wfc`.`datastatus` AS `datastatus` from (`working_flow_configuration` `wfc` left join `enterprise_operating_brand` `eob` on((`wfc`.`brandcode` = `eob`.`brandcode`))) ;

-- ----------------------------
-- View structure for v_all_game_trade
-- ----------------------------
DROP VIEW IF EXISTS `v_all_game_trade`;
CREATE VIEW `v_all_game_trade` AS select `bha`.`bet_Time` AS `bet_time`,`bha`.`net_Amount` AS `netMoney`,`bha`.`employeecode` AS `employeecode`,`bha`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_ag` `bha` USE INDEX (`idx_betting_hq_ag_bepn`) where (`bha`.`employeecode` is not null) union all select `bhb`.`bbin_Wagers_Date` AS `bet_time`,`bhb`.`bbin_Payoff` AS `netMoney`,`bhb`.`employeecode` AS `employeecode`,`bhb`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_bbin` `bhb` USE INDEX (`idx_betting_hq_bbin_bepP`) where (`bhb`.`employeecode` is not null) union all select `bhq`.`Betting_Date` AS `bet_time`,`bhq`.`Winning_Credits` AS `netMoney`,`bhq`.`employeecode` AS `employeecode`,`bhq`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_nhq` `bhq` USE INDEX (`idx_betting_hq_nhq_BepW`) where (`bhq`.`employeecode` is not null) union all select `bhoa`.`aoi_Add_Time` AS `bet_time`,`bhoa`.`aoi_Win_Lose_Amount` AS `netMoney`,`bhoa`.`employeecode` AS `employeecode`,`bhoa`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_og_ag` `bhoa` USE INDEX (`idx_betting_hq_og_ag_aepW`) where (`bhoa`.`employeecode` is not null) union all select `bhoi`.`ibc_updatetime` AS `bet_time`,(`bhoi`.`ibc_win` - `bhoi`.`ibc_lose`) AS `netMoney`,`bhoi`.`employeecode` AS `employeecode`,`bhoi`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_og_ibc` `bhoi` USE INDEX (`idx_betting_hq_og_ibc_uepwlose`) where (`bhoi`.`employeecode` is not null) union all select `bhoo`.`aoi_Add_Time` AS `bet_time`,`bhoo`.`aoi_Win_Lose_Amount` AS `netMoney`,`bhoo`.`employeecode` AS `employeecode`,`bhoo`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_og_og` `bhoo` USE INDEX (`idx_betting_hq_og_AepWLose`) where (`bhoo`.`employeecode` is not null) union all select `bhpn`.`bet_Time` AS `bet_time`,`bhpn`.`net_Amount` AS `netMoney`,`bhpn`.`employeecode` AS `employeecode`,`bhpn`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_png` `bhpn` USE INDEX (`idx_betting_hq_png_bepn`) where (`bhpn`.`employeecode` is not null) union all select `bhp`.`pt_gamedate` AS `bet_time`,(`bhp`.`pt_win` - `bhp`.`pt_bet`) AS `netMoney`,`bhp`.`employeecode` AS `employeecode`,`bhp`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_pt` `bhp` USE INDEX (`idx_betting_hq_pt_gepwbet`) where (`bhp`.`employeecode` is not null) union all select `bhx`.`xcp_writetime` AS `bet_time`,`bhx`.`xcp_bonus` AS `netMoney`,`bhx`.`employeecode` AS `employeecode`,`bhx`.`parentemployeecode` AS `parentemployeecode` from `betting_hq_xcp` `bhx` USE INDEX (`idx_betting_hq_xcp_wepbonus`) where (`bhx`.`employeecode` is not null) ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_bbin
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_bbin`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_bbin`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_hq_bbin bhn WHERE bhn.bbin_Wagers_Date BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 
    OPEN e_em;
  
      REPEAT
        FETCH e_em INTO e_employeecode;
        SELECT GROUP_CONCAT(temp.bbin_Wagers_Id) INTO u_record FROM (SELECT bhn.bbin_Wagers_Id  FROM betting_hq_bbin bhn WHERE bhn.bbin_Wagers_Date BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
        -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
        UPDATE betting_hq_bbin set employeecode = e_employeecode WHERE FIND_IN_SET(bbin_Wagers_Id,u_record)>0;
        set limit_start = limit_start+limit_pagesize;
      until done end REPEAT;       
  
    CLOSE e_em;
  
    UPDATE betting_hq_bbin bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
    SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
  END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_ibc
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_ibc`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_ibc`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_hq_og_ibc bhn WHERE bhn.ibc_updatetime BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 
    OPEN e_em;
  
      REPEAT
        FETCH e_em INTO e_employeecode;
        SELECT GROUP_CONCAT(temp.ibc_rowguid) INTO u_record FROM (SELECT bhn.ibc_rowguid  FROM betting_hq_og_ibc bhn WHERE bhn.ibc_updatetime BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
        -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
        UPDATE betting_hq_og_ibc set employeecode = e_employeecode WHERE FIND_IN_SET(ibc_rowguid,u_record)>0;
        set limit_start = limit_start+limit_pagesize;
      until done end REPEAT;       
  
    CLOSE e_em;
  
    UPDATE betting_hq_og_ibc bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
    SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
 END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_nhq
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_nhq`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_nhq`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_hq_nhq bhn WHERE bhn.Betting_Date BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 
    
      OPEN e_em;
    
        REPEAT
          FETCH e_em INTO e_employeecode;
          SELECT GROUP_CONCAT(temp.Betting_ID) INTO u_record FROM (
            SELECT bhn.Betting_ID  FROM betting_hq_nhq bhn WHERE bhn.Betting_Date BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
          -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
          UPDATE betting_hq_nhq set employeecode = e_employeecode WHERE FIND_IN_SET( Betting_ID,u_record)>0;
          set limit_start = limit_start+limit_pagesize;
        until done end REPEAT;       
    
      CLOSE e_em;

      UPDATE betting_hq_nhq bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
      SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
  END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_oag
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_oag`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_oag`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_hq_og_ag bhn WHERE bhn.aoi_Add_Time BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 
  OPEN e_em;

    REPEAT
      FETCH e_em INTO e_employeecode;
      SELECT GROUP_CONCAT(temp.aoi_Product_Id) INTO u_record FROM (SELECT bhn.aoi_Product_Id  FROM betting_hq_og_ag bhn LIMIT limit_start,limit_pagesize) AS temp;
      -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
      UPDATE betting_hq_og_ag set employeecode = e_employeecode WHERE FIND_IN_SET(aoi_Product_Id,u_record)>0;
      set limit_start = limit_start+limit_pagesize;
    until done end REPEAT;       

  CLOSE e_em;

  UPDATE betting_hq_og_ag bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
  SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
    END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_og
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_og`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_og`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_hq_og_og bhn WHERE bhn.aoi_Add_Time BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 
    OPEN e_em;
      REPEAT
        FETCH e_em INTO e_employeecode;
        SELECT GROUP_CONCAT(temp.aoi_Product_Id) INTO u_record FROM (SELECT bhn.aoi_Product_Id  FROM betting_hq_og_og bhn WHERE bhn.aoi_Add_Time BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
        -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
        UPDATE betting_hq_og_og set employeecode = e_employeecode WHERE FIND_IN_SET(aoi_Product_Id,u_record)>0;
        set limit_start = limit_start+limit_pagesize;
      until done end REPEAT;       
  
    CLOSE e_em;
  
    UPDATE betting_hq_og_og bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
    SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
  END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_pt
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_pt`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_pt`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_hq_pt as bhn WHERE bhn.pt_gamedate BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 
    OPEN e_em;
  
      REPEAT
        FETCH e_em INTO e_employeecode;
        SELECT GROUP_CONCAT(temp.pt_gamecode) INTO u_record FROM (SELECT bhn.pt_gamecode  FROM betting_hq_pt bhn WHERE bhn.pt_gamedate BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
        -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
        UPDATE betting_hq_pt set employeecode = e_employeecode WHERE FIND_IN_SET(pt_gamecode,u_record)>0;
        set limit_start = limit_start+limit_pagesize;
      until done end REPEAT;       
  
    CLOSE e_em;
  
    UPDATE betting_hq_pt bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
    SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
    END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_tag
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_tag`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_tag`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_hq_ag bhn WHERE bhn.bet_Time BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 
  OPEN e_em;

    REPEAT
      FETCH e_em INTO e_employeecode;
      SELECT GROUP_CONCAT(temp.bill_No) INTO u_record FROM (SELECT bhn.bill_No  FROM betting_hq_ag bhn WHERE bhn.bet_Time BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
      -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
      UPDATE betting_hq_ag set employeecode = e_employeecode WHERE FIND_IN_SET(bill_No,u_record)>0;
      set limit_start = limit_start+limit_pagesize;
    until done end REPEAT;       

  CLOSE e_em;

  UPDATE betting_hq_ag bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
  SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
  END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_xcp
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_xcp`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_xcp`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_hq_xcp bhn WHERE bhn.xcp_writetime BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);
  IF limit_pagesize >0 THEN 
  OPEN e_em;

    REPEAT
      FETCH e_em INTO e_employeecode;
      SELECT GROUP_CONCAT(temp.xcp_projectid) INTO u_record FROM (SELECT bhn.xcp_projectid  FROM betting_hq_xcp bhn WHERE bhn.xcp_writetime BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
      -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
      UPDATE betting_hq_xcp set employeecode = e_employeecode WHERE FIND_IN_SET(xcp_projectid,u_record)>0;
      set limit_start = limit_start+limit_pagesize;
    until done end REPEAT;       

  CLOSE e_em;

  UPDATE betting_hq_xcp bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
  SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
 END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_yag
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_yag`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_yag`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_yg_ag bhn WHERE bhn.createtime BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 

  OPEN e_em;

    REPEAT
      FETCH e_em INTO e_employeecode;
      SELECT GROUP_CONCAT(temp.bet_id) INTO u_record FROM (SELECT bhn.bet_id  FROM betting_yg_ag bhn WHERE bhn.createtime BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
      -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
      UPDATE betting_yg_ag set employeecode = e_employeecode WHERE FIND_IN_SET(bet_id,u_record)>0;
      set limit_start = limit_start+limit_pagesize;
    until done end REPEAT;       

  CLOSE e_em;

  UPDATE betting_yg_ag bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
  SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
  END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for dhp_tn_betrecord_rinse_zj
-- ----------------------------
DROP PROCEDURE IF EXISTS `dhp_tn_betrecord_rinse_zj`;
DELIMITER ;;
CREATE PROCEDURE `dhp_tn_betrecord_rinse_zj`(IN in_begintime datetime,IN in_endtime datetime)
BEGIN
  DECLARE done boolean DEFAULT FALSE;

  DECLARE limit_start int DEFAULT 0;

  DECLARE limit_pagesize int DEFAULT 0;

  DECLARE e_employeecode CHAR(8);
  
  DECLARE u_record longtext;  

  DECLARE e_em CURSOR FOR SELECT employeecode FROM enterprise_employee WHERE employeetypecode='T003';

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;

  SELECT COUNT(1) INTO @employeecount FROM enterprise_employee WHERE employeetypecode='T003';

  SELECT COUNT(1) INTO @recordcount FROM betting_zj bhn WHERE bhn.bettime BETWEEN in_begintime AND in_endtime;

  SET limit_pagesize = floor(@recordcount/@employeecount);

  IF limit_pagesize >0 THEN 
    OPEN e_em;
  
      REPEAT
        FETCH e_em INTO e_employeecode;
        SELECT GROUP_CONCAT(temp.id) INTO u_record FROM (SELECT bhn.id  FROM betting_zj bhn WHERE bhn.bettime BETWEEN in_begintime AND in_endtime LIMIT limit_start,limit_pagesize) AS temp;
        -- SELECT COUNT(1) INTO @u_count FROM betting_hq_nhq WHERE FIND_IN_SET( Betting_ID,u_record)>0;
        UPDATE betting_zj set employeecode = e_employeecode WHERE FIND_IN_SET(id,u_record)>0;
        set limit_start = limit_start+limit_pagesize;
      until done end REPEAT;       
  
    CLOSE e_em;
  
    UPDATE betting_zj bhn LEFT JOIN enterprise_employee ee ON bhn.employeecode = ee.employeecode 
    SET bhn.enterprisecode=ee.enterprisecode , bhn.brandcode=ee.brandcode ,bhn.loginaccount=ee.loginaccount , bhn.parentemployeecode=ee.parentemployeecode;
  END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for ups_daily_deposit_report
-- ----------------------------
DROP PROCEDURE IF EXISTS `ups_daily_deposit_report`;
DELIMITER ;;
CREATE PROCEDURE `ups_daily_deposit_report`(IN in_enterprisecode CHAR(6), OUT out_passed INT, OUT out_review INT, OUT out_notpass INT)
BEGIN
	SELECT COUNT(1) INTO `out_review` FROM deposit_withdraw_order WHERE enterprisecode = in_enterprisecode AND ordertype = 1 AND orderstatus = 1 AND orderdate >= CURDATE() AND orderdate <= DATE_ADD(CURDATE(), INTERVAL 1 DAY);
	SELECT COUNT(1) INTO `out_passed` FROM deposit_withdraw_order WHERE enterprisecode = in_enterprisecode AND ordertype = 1 AND orderstatus = 2 AND orderdate >= CURDATE() AND orderdate <= DATE_ADD(CURDATE(), INTERVAL 1 DAY);
	SELECT COUNT(1) INTO `out_notpass`FROM deposit_withdraw_order WHERE enterprisecode = in_enterprisecode AND ordertype = 1 AND orderstatus IN (3,4) AND orderdate >= CURDATE() AND orderdate <= DATE_ADD(CURDATE(), INTERVAL 1 DAY);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for ups_daily_withdrawal_report
-- ----------------------------
DROP PROCEDURE IF EXISTS `ups_daily_withdrawal_report`;
DELIMITER ;;
CREATE PROCEDURE `ups_daily_withdrawal_report`(IN in_enterprisecode CHAR(6), OUT out_passed INT, OUT out_review INT, OUT out_notpass INT, OUT out_topaid INT)
BEGIN
	SELECT COUNT(1) INTO `out_review` FROM deposit_withdraw_order WHERE enterprisecode = in_enterprisecode AND ordertype = 2 AND orderstatus = 1 AND orderdate >= CURDATE() AND orderdate <= DATE_ADD(CURDATE(), INTERVAL 1 DAY);
	SELECT COUNT(1) INTO `out_passed` FROM deposit_withdraw_order WHERE enterprisecode = in_enterprisecode AND ordertype = 2 AND orderstatus = 2 AND orderdate >= CURDATE() AND orderdate <= DATE_ADD(CURDATE(), INTERVAL 1 DAY);
	SELECT COUNT(1) INTO `out_notpass`FROM deposit_withdraw_order WHERE enterprisecode = in_enterprisecode AND ordertype = 2 AND orderstatus IN (3,4) AND orderdate >= CURDATE() AND orderdate <= DATE_ADD(CURDATE(), INTERVAL 1 DAY);
	SELECT COUNT(1) INTO `out_topaid`   FROM deposit_withdraw_order WHERE enterprisecode = in_enterprisecode AND ordertype = 2 AND orderstatus = 5 AND orderdate >= CURDATE() AND orderdate <= DATE_ADD(CURDATE(), INTERVAL 1 DAY);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_acting_contribution_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_acting_contribution_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_acting_contribution_ranking`(IN employeecode varchar(10), IN date_begin date,IN date_end date)
BEGIN
  /*
代理贡献排名
*/

  SELECT
    FF.employeecode,
    FF.loginaccount,
    CASE
        WHEN FF.employeetypecode = 'T004' THEN ufn_down_recursion_team_of_agent_count(FF.employeecode) ELSE 0
      END AS row1,
    CASE
        WHEN FF.employeetypecode = 'T003' THEN (SELECT
            COUNT(1)
          FROM enterprise_employee
          WHERE parentemployeecode = FF.employeecode) ELSE 0
      END AS row2,
    GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
    ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare)

  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE parentemployeecode = employeecode
    AND A.lastlogintime BETWEEN date_begin AND date_end
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
      GROUP BY C.employeecode,
               C.enterprisecode,
               C.brandcode) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_acting_contribution_ranking_bak
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_acting_contribution_ranking_bak`;
DELIMITER ;;
CREATE PROCEDURE `usp_acting_contribution_ranking_bak`(IN employeecode varchar(10),
IN date_begin date,
IN date_end date)
BEGIN
  SELECT
    FF.employeecode,
    FF.loginaccount,
    '',
    '',
    GG.net_money - (GG.Valid_Money * GG.bonus) - ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend) -
    ((GG.net_money - (GG.Valid_Money * GG.bonus) - ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare)

  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE EXISTS (SELECT
        employeecode
      FROM enterprise_employee AS B
      WHERE parentemployeecode = employeecode
      AND B.employeecode = A.parentemployeecode) AND A.lastlogintime BETWEEN date_begin AND date_end
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_Acting_contribution_user_lose_win_Ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_Acting_contribution_user_lose_win_Ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_Acting_contribution_user_lose_win_Ranking`(IN employee_id varchar(10), IN begin_date date, IN end_date date)
BEGIN
  /*
 二次查询代理贡献排名 ---返回客户输赢统计
*/
  IF ISNULL(begin_date) = 1 AND ISNULL(end_date) = 1 THEN
    SET begin_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET end_date = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    GROUP_AA.loginaccount,
    ufn_down_recursion_team_of_agent_number(GROUP_AA.employeecode) AS num,
    GROUP_AA.employeecode,
    Game_B.Bet_Money AS game_betting_amount,
    Game_B.bonus,
    CASE
        WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus) * GROUP_AA.dividend ELSE 0
      END AS dividend,
    CASE
        WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus + ((Game_B.net_money - Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share ELSE (Game_B.net_money + Game_B.bonus) * GROUP_AA.share
      END AS share,
    CASE
        WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus + ((Game_B.net_money + Game_B.bonus)) * GROUP_AA.dividend) -
        ((Game_B.net_money + Game_B.bonus + ((Game_B.net_money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share) ELSE Game_B.net_money + Game_B.bonus - ((Game_B.net_money + Game_B.bonus) * GROUP_AA.share)
      END AS user_lose_win_amount
  FROM enterprise_employee GROUP_AA
    INNER JOIN (SELECT
        A.enterprisecode,
        A.employeecode,
        A.parentemployeecode,
        A.Game_Platform,
        A.Game_Type,
        SUM(A.Bet_Money) AS Bet_Money,
        SUM(A.Net_Money) AS Net_Money,
        SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
      FROM betting_all_day AS A
        INNER JOIN employee_gamecataloy_bonus AS B
          ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
          AND A.Game_Big_Type = B.categorytype
          AND A.Bet_Day BETWEEN begin_date AND end_date
      GROUP BY A.enterprisecode,
               A.employeecode,
               A.parentemployeecode,
               A.Game_Platform,
               A.Game_Type) AS Game_B
      ON GROUP_AA.employeecode = Game_B.employeecode
      AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
      AND GROUP_AA.enterprisecode = Game_B.enterprisecode
  WHERE GROUP_AA.parentemployeecode = employee_id
  AND GROUP_AA.employeetypecode = 'T004'
  UNION ALL
  SELECT
    '直线会员' AS loginaccount,
    num,
    employee_id AS employeecode,
    Bet_Money AS game_betting_amount,
    bonus,
    CASE
        WHEN net_money > 0 THEN (net_money + bonus) * dividend ELSE 0
      END AS dividend,
    CASE
        WHEN net_money > 0 THEN (net_money + bonus + ((net_money - bonus) * dividend)) * share ELSE (net_money + bonus) * share
      END AS share,
    CASE
        WHEN net_money > 0 THEN (net_money + bonus + ((net_money + bonus)) * dividend) - ((net_money + bonus + ((net_money + bonus) * dividend)) * share) ELSE net_money + bonus - ((net_money + bonus) * share)
      END AS user_lose_win_amount

  FROM (SELECT
      LINE_AA.loginaccount,
      COUNT(1) AS num,
      LINE_AA.employeecode,
      Game_B.Bet_Money,
      Game_B.net_Money,
      Game_B.bonus,
      LINE_AA.dividend,
      LINE_AA.share
    FROM enterprise_employee AS LINE_AA
      INNER JOIN (SELECT
          A.enterprisecode,
          A.employeecode,
          A.parentemployeecode,
          A.Game_Platform,
          A.Game_Type,
          SUM(A.Bet_Money) AS Bet_Money,
          SUM(A.Net_Money) AS Net_Money,
          SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
        FROM betting_all_day AS A
          INNER JOIN employee_gamecataloy_bonus AS B
            ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
            AND A.Game_Big_Type = B.categorytype
            AND A.Bet_Day BETWEEN begin_date AND end_date
        GROUP BY A.enterprisecode,
                 A.employeecode,
                 A.parentemployeecode,
                 A.Game_Platform,
                 A.Game_Type) AS Game_B
        ON LINE_AA.employeecode = Game_B.employeecode
        AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
        AND LINE_AA.enterprisecode = Game_B.enterprisecode
    WHERE LINE_AA.parentemployeecode = employee_id
    AND LINE_AA.employeetypecode = 'T003') AS FF
  WHERE FF.num > 0;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_activity_butmoney_by_enterprisecode_bigtype
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_activity_butmoney_by_enterprisecode_bigtype`;
DELIMITER ;;
CREATE PROCEDURE `usp_activity_butmoney_by_enterprisecode_bigtype`(IN in_enterprisecode CHAR(8),IN in_brandcode CHAR(8), IN in_begindate datetime, IN in_enddate datetime, IN in_gamebigtype CHAR(8) )
    READS SQL DATA
BEGIN
        
        /*************统计企业的所有会员在指定时间内的有效投注额，按人员分组 （按大类）*************/
                
        select employeecode,loginaccount,sum(mm) game_betting_amount from (
                /***AG**/                
                select employeecode,loginaccount,sum(valid_Bet_Amount) mm from betting_hq_ag where 
                enterprisecode=in_enterprisecode 
                and (bet_Time >= in_begindate and bet_Time <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***BBIN**/
                select employeecode,loginaccount,sum(bbin_Commissionable) mm from betting_hq_bbin where 
                enterprisecode=in_enterprisecode 
                and (bbin_Wagers_Date >= in_begindate and bbin_Wagers_Date <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***NHQ**/
                select employeecode,loginaccount,sum(Wash_Code_Credits) mm from betting_hq_nhq where
                enterprisecode=in_enterprisecode 
                and (Betting_Date >= in_begindate and Betting_Date <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***OG_AG**/
                select employeecode,loginaccount,sum(aoi_Valid_Amount) mm from betting_hq_og_ag where                 
                enterprisecode=in_enterprisecode 
                and (aoi_Add_Time >= in_begindate and aoi_Add_Time <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***OG_IBC**/
                select employeecode,loginaccount,sum(ibc_tzmoney) mm from betting_hq_og_ibc where
                enterprisecode=in_enterprisecode 
                and (ibc_balltime >= in_begindate and ibc_balltime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***OG_OG**/
                select employeecode,loginaccount,sum(aoi_Valid_Amount) mm from betting_hq_og_og where 
                enterprisecode=in_enterprisecode 
                and (aoi_Add_Time >= in_begindate and aoi_Add_Time <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                                
                UNION  ALL
                /***PT**/
                select employeecode,loginaccount,sum(pt_bet) mm from betting_hq_pt where                 
                enterprisecode=in_enterprisecode 
                and (pt_gamedate >= in_begindate and pt_gamedate <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***XCP**/
                select employeecode,loginaccount,sum(xcp_totalprice) mm from betting_hq_xcp where                 
                enterprisecode=in_enterprisecode 
                and (xcp_writetime >= in_begindate and xcp_writetime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***KR_AV**/
                select employeecode,loginaccount,sum(amount) mm from betting_kr_av where                 
                enterprisecode=in_enterprisecode 
                and (time >= in_begindate and time <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***SA**/
                select employeecode,loginaccount,sum(betamount) mm from betting_sa where                 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***YG_AG**/
                select employeecode,loginaccount,sum(valid_money) mm from betting_yg_ag where                 
                enterprisecode=in_enterprisecode 
                and (time >= in_begindate and time <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***ZJ**/
                select employeecode,loginaccount,sum(validstake) mm from betting_zj where                 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***TTG**/
                select employeecode,loginaccount,sum(amount) mm from betting_ttg where                 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***MG**/
                select employeecode,loginaccount,sum(mg_amount) mm from betting_mg where                 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***PNG**/                
                select employeecode,loginaccount,sum(valid_Bet_Amount) mm from betting_hq_png where 
                enterprisecode=in_enterprisecode 
                and (bet_Time >= in_begindate and bet_Time <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                
                UNION  ALL
                /***QP**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_qp where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***GG**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_gg where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***SGS**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_sgs where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***IDN**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_idn where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***M88**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_m88 where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***haba**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_hab where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                
                UNION  ALL
                /***WIN88**/
                select employeecode,loginaccount,sum(pt_bet) mm from betting_win88 where                 
                enterprisecode=in_enterprisecode 
                and (pt_gamedate >= in_begindate and pt_gamedate <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***TGP**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_tgp where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***QT**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_qt where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***GB**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_gb where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***EBET**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_ebet where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***EIBC**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_eibc where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gamebigtype=in_gamebigtype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                                
        ) as a group by a.employeecode;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_activity_butmoney_by_enterprisecode_smalltype
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_activity_butmoney_by_enterprisecode_smalltype`;
DELIMITER ;;
CREATE PROCEDURE `usp_activity_butmoney_by_enterprisecode_smalltype`(IN in_enterprisecode CHAR(8),IN in_brandcode CHAR(8), IN in_begindate datetime, IN in_enddate datetime, IN in_gametype VARCHAR(50) )
    READS SQL DATA
BEGIN
        /*************统计企业的所有会员在指定时间内的有效投注额，按人员分组 （按小类）*************/
                
        select employeecode,loginaccount,sum(mm) game_betting_amount from (
                /***AG**/                
                select employeecode,loginaccount,sum(valid_Bet_Amount) mm from betting_hq_ag where 
                enterprisecode=in_enterprisecode 
                and (bet_Time >= in_begindate and bet_Time <= in_enddate)
                and game_type=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***BBIN**/
                select employeecode,loginaccount,sum(bbin_Commissionable) mm from betting_hq_bbin where 
                enterprisecode=in_enterprisecode 
                and (bbin_Wagers_Date >= in_begindate and bbin_Wagers_Date <= in_enddate)
                and bbin_game_type=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***NHQ**/
                select employeecode,loginaccount,sum(Wash_Code_Credits) mm from betting_hq_nhq where
                enterprisecode=in_enterprisecode 
                and (Betting_Date >= in_begindate and Betting_Date <= in_enddate)
                and game_type=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***OG_AG**/
                select employeecode,loginaccount,sum(aoi_Valid_Amount) mm from betting_hq_og_ag where                 
                enterprisecode=in_enterprisecode 
                and (aoi_Add_Time >= in_begindate and aoi_Add_Time <= in_enddate)
                and aoi_Game_Name_Id=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***OG_IBC**/
                select employeecode,loginaccount,sum(ibc_tzmoney) mm from betting_hq_og_ibc where
                enterprisecode=in_enterprisecode 
                and (ibc_balltime >= in_begindate and ibc_balltime <= in_enddate)
                and ibc_isbk=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***OG_OG**/
                select employeecode,loginaccount,sum(aoi_Valid_Amount) mm from betting_hq_og_og where 
                enterprisecode=in_enterprisecode 
                and (aoi_Add_Time >= in_begindate and aoi_Add_Time <= in_enddate)
                and aoi_Game_Name_Id=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                                
                UNION  ALL
                /***PT**/
                select employeecode,loginaccount,sum(pt_bet) mm from betting_hq_pt where                 
                enterprisecode=in_enterprisecode 
                and (pt_gamedate >= in_begindate and pt_gamedate <= in_enddate)
                and pt_gametype=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***XCP 此类无小类**/
                select employeecode,loginaccount,sum(xcp_totalprice) mm from betting_hq_xcp where                 
                enterprisecode=in_enterprisecode 
                and (xcp_writetime >= in_begindate and xcp_writetime <= in_enddate)
                
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***KR_AV**/
                select employeecode,loginaccount,sum(amount) mm from betting_kr_av where                 
                enterprisecode=in_enterprisecode 
                and (time >= in_begindate and time <= in_enddate)
                and provider=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***SA**/
                select employeecode,loginaccount,sum(betamount) mm from betting_sa where                 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gametype=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***YG_AG**/
                select employeecode,loginaccount,sum(valid_money) mm from betting_yg_ag where                 
                enterprisecode=in_enterprisecode 
                and (time >= in_begindate and time <= in_enddate)
                and game_id=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***ZJ**/
                select employeecode,loginaccount,sum(validstake) mm from betting_zj where                 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gametype=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***TTG**/
                select employeecode,loginaccount,sum(amount) mm from betting_ttg where                 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gametype=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***MG**/
                select employeecode,loginaccount,sum(mg_amount) mm from betting_mg where                 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gametype=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                
                UNION  ALL
                /***PNG**/                
                select employeecode,loginaccount,sum(valid_Bet_Amount) mm from betting_hq_png where 
                enterprisecode=in_enterprisecode 
                and (bet_Time >= in_begindate and bet_Time <= in_enddate)
                and game_type=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                
                UNION  ALL
                /***QP**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_qp where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and Kind_ID=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***GG**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_gg where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                
                UNION  ALL
                /***SGS**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_sgs where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***IDN**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_idn where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***M88**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_m88 where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***haba**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_hab where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***WIN88**/
                select employeecode,loginaccount,sum(pt_bet) mm from betting_win88 where                 
                enterprisecode=in_enterprisecode 
                and (pt_gamedate >= in_begindate and pt_gamedate <= in_enddate)
                and pt_gametype=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***TGP**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_tgp where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***QT**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_qt where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***GB**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_gb where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***EBET**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_ebet where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                UNION  ALL
                /***EIBC**/                
                select employeecode,loginaccount,sum(netmoney) mm from betting_eibc where 
                enterprisecode=in_enterprisecode 
                and (bettime >= in_begindate and bettime <= in_enddate)
                and gameid=in_gametype
                and IF (in_brandcode is NULL, 0 = 0, brandcode=in_brandcode) group by employeecode
                
                
        ) as a group by a.employeecode;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_activity_by_member_employeecode_one
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_activity_by_member_employeecode_one`;
DELIMITER ;;
CREATE PROCEDURE `usp_activity_by_member_employeecode_one`(IN `in_employeecode` char(8),IN `in_begindate` datetime,IN `in_enddate` datetime,OUT `activity_money` decimal(14,2))
BEGIN
	/***********根据会员独立计算时间段内的涉及活动的总金额**********/
	select sum(moneychangeamount) into activity_money from employee_money_change 
where employeecode = in_employeecode 
and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
and moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811';
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_agent_contribution
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_agent_contribution`;
DELIMITER ;;
CREATE PROCEDURE `usp_agent_contribution`(IN employee_id varchar(10),
IN date_begin date,
IN date_end date)
BEGIN
  /*
	代理贡献排名
*/

  IF ISNULL(date_begin) = 1 AND ISNULL(date_end) = 1 THEN
    SET date_begin = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    employeecode,
    loginaccount,
    agentCount,
    memberCount,
    employeetypename,
    SUM(Profit_amount) AS Profit_amount

  FROM (SELECT
      FF.employeecode,
      FF.loginaccount,
      CASE
          WHEN FF.employeetypecode = 'T004' THEN ufn_down_recursion_team_of_agent_number(FF.employeecode) ELSE 0
        END AS agentCount,
      CASE
          WHEN FF.employeetypecode = 'T003' THEN (SELECT
              COUNT(1)
            FROM enterprise_employee
            WHERE parentemployeecode = FF.employeecode
            AND employeetypecode = 'T003') ELSE 0
        END AS memberCount,
      employeetypename,
      GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
      ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) AS profit_amount

    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        N.employeetype AS employeetypename,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
        INNER JOIN enterprise_employee_type AS N
          ON A.employeetypecode = N.employeetypecode
      WHERE A.parentemployeecode = employee_id
      AND A.employeetypecode = 'T004'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          C.Game_Platform,
          C.Game_Big_Type,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND C.Bet_Day BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode,
                 C.Game_Platform,
                 C.Game_Big_Type) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode) AS HH
  GROUP BY employeecode,
           loginaccount,
           agentCount,
           memberCount,
           employeetypename
  ORDER BY profit_amount DESC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_agent_contribution_detail
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_agent_contribution_detail`;
DELIMITER ;;
CREATE PROCEDURE `usp_agent_contribution_detail`(IN employee_id varchar(10), IN begin_date date, IN end_date date, IN startsd int, IN endsd int, OUT param1 int)
BEGIN
  /*
 二次查询代理贡献排名 ---返回客户输赢统计
*/
  IF ISNULL(begin_date) = 1 AND ISNULL(end_date) = 1 THEN
    SET begin_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET end_date = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      GROUP_AA.loginaccount,
      ufn_down_recursion_team_of_agent_number(GROUP_AA.employeecode) AS num,
      GROUP_AA.employeecode,
      Game_B.Bet_Money AS game_betting_amount,
      Game_B.bonus,
      CASE
          WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus) * GROUP_AA.dividend ELSE 0
        END AS dividend,
      CASE
          WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus + ((Game_B.net_money - Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share ELSE (Game_B.net_money + Game_B.bonus) * GROUP_AA.share
        END AS share,
      CASE
          WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus + ((Game_B.net_money + Game_B.bonus)) * GROUP_AA.dividend) -
          ((Game_B.net_money + Game_B.bonus + ((Game_B.net_money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share) ELSE Game_B.net_money + Game_B.bonus - ((Game_B.net_money + Game_B.bonus) * GROUP_AA.share)
        END AS user_lose_win_amount
    FROM enterprise_employee GROUP_AA
      INNER JOIN (SELECT
          A.enterprisecode,
          A.employeecode,
          A.parentemployeecode,
          A.Game_Platform,
          A.Game_Type,
          SUM(A.Bet_Money) AS Bet_Money,
          SUM(A.Net_Money) AS Net_Money,
          SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
        FROM betting_all_day AS A
          INNER JOIN employee_gamecataloy_bonus AS B
            ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
            AND A.Game_Big_Type = B.categorytype
            AND A.Bet_Day BETWEEN begin_date AND end_date
        GROUP BY A.enterprisecode,
                 A.employeecode,
                 A.parentemployeecode,
                 A.Game_Platform,
                 A.Game_Type) AS Game_B
        ON GROUP_AA.employeecode = Game_B.employeecode
        AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
        AND GROUP_AA.enterprisecode = Game_B.enterprisecode
    WHERE GROUP_AA.parentemployeecode = employee_id
    AND GROUP_AA.employeetypecode = 'T004'
    UNION ALL
    SELECT
      '直线会员' AS loginaccount,
      num,
      employee_id AS employeecode,
      Bet_Money AS game_betting_amount,
      bonus,
      CASE
          WHEN net_money > 0 THEN (net_money + bonus) * dividend ELSE 0
        END AS dividend,
      CASE
          WHEN net_money > 0 THEN (net_money + bonus + ((net_money - bonus) * dividend)) * share ELSE (net_money + bonus) * share
        END AS share,
      CASE
          WHEN net_money > 0 THEN (net_money + bonus + ((net_money + bonus)) * dividend) - ((net_money + bonus + ((net_money + bonus) * dividend)) * share) ELSE net_money + bonus - ((net_money + bonus) * share)
        END AS user_lose_win_amount

    FROM (SELECT
        LINE_AA.loginaccount,
        COUNT(1) AS num,
        LINE_AA.employeecode,
        Game_B.Bet_Money,
        Game_B.net_Money,
        Game_B.bonus,
        LINE_AA.dividend,
        LINE_AA.share
      FROM enterprise_employee AS LINE_AA
        INNER JOIN (SELECT
            A.enterprisecode,
            A.employeecode,
            A.parentemployeecode,
            A.Game_Platform,
            A.Game_Type,
            SUM(A.Bet_Money) AS Bet_Money,
            SUM(A.Net_Money) AS Net_Money,
            SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
          FROM betting_all_day AS A
            INNER JOIN employee_gamecataloy_bonus AS B
              ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
              AND A.Game_Big_Type = B.categorytype
              AND A.Bet_Day BETWEEN begin_date AND end_date
          GROUP BY A.enterprisecode,
                   A.employeecode,
                   A.parentemployeecode,
                   A.Game_Platform,
                   A.Game_Type) AS Game_B
          ON LINE_AA.employeecode = Game_B.employeecode
          AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
          AND LINE_AA.enterprisecode = Game_B.enterprisecode
      WHERE LINE_AA.parentemployeecode = employee_id
      AND LINE_AA.employeetypecode = 'T003') AS FF
    WHERE FF.num > 0) AS HH;

  SELECT
    GROUP_AA.loginaccount,
    ufn_down_recursion_team_of_agent_number(GROUP_AA.employeecode) AS num,
    GROUP_AA.employeecode,
    N.employeetype AS employeetypename,
    Game_B.Bet_Money AS game_betting_amount,
    Game_B.bonus,
    CASE
        WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus) * GROUP_AA.dividend ELSE 0
      END AS dividend,
    CASE
        WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus + ((Game_B.net_money - Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share ELSE (Game_B.net_money + Game_B.bonus) * GROUP_AA.share
      END AS share,
    CASE
        WHEN Game_B.net_money > 0 THEN (Game_B.net_money + Game_B.bonus + ((Game_B.net_money + Game_B.bonus)) * GROUP_AA.dividend) -
        ((Game_B.net_money + Game_B.bonus + ((Game_B.net_money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share) ELSE Game_B.net_money + Game_B.bonus - ((Game_B.net_money + Game_B.bonus) * GROUP_AA.share)
      END AS user_lose_win_amount
  FROM enterprise_employee GROUP_AA
    INNER JOIN (SELECT
        A.enterprisecode,
        A.employeecode,
        A.parentemployeecode,
        A.Game_Platform,
        A.Game_Type,
        SUM(A.Bet_Money) AS Bet_Money,
        SUM(A.Net_Money) AS Net_Money,
        SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
      FROM betting_all_day AS A
        INNER JOIN employee_gamecataloy_bonus AS B
          ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
          AND A.Game_Big_Type = B.categorytype
          AND A.Bet_Day BETWEEN begin_date AND end_date
      GROUP BY A.enterprisecode,
               A.employeecode,
               A.parentemployeecode,
               A.Game_Platform,
               A.Game_Type) AS Game_B
      ON GROUP_AA.employeecode = Game_B.employeecode
      AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
      AND GROUP_AA.enterprisecode = Game_B.enterprisecode
    INNER JOIN enterprise_employee_type AS N
      ON GROUP_AA.employeetypecode = N.employeetypecode
  WHERE GROUP_AA.parentemployeecode = employee_id
  AND GROUP_AA.employeetypecode = 'T004'
  UNION ALL
  SELECT
    '直线会员' AS loginaccount,
    num,
    employee_id AS employeecode,
    '会员' AS employeetypename,
    Bet_Money AS game_betting_amount,
    bonus,
    CASE
        WHEN net_money > 0 THEN (net_money + bonus) * dividend ELSE 0
      END AS dividend,
    CASE
        WHEN net_money > 0 THEN (net_money + bonus + ((net_money - bonus) * dividend)) * share ELSE (net_money + bonus) * share
      END AS share,
    CASE
        WHEN net_money > 0 THEN (net_money + bonus + ((net_money + bonus)) * dividend) - ((net_money + bonus + ((net_money + bonus) * dividend)) * share) ELSE net_money + bonus - ((net_money + bonus) * share)
      END AS user_lose_win_amount

  FROM (SELECT
      LINE_AA.loginaccount,
      COUNT(1) AS num,
      LINE_AA.employeecode,
      Game_B.Bet_Money,
      Game_B.net_Money,
      Game_B.bonus,
      LINE_AA.dividend,
      LINE_AA.share
    FROM enterprise_employee AS LINE_AA
      INNER JOIN (SELECT
          A.enterprisecode,
          A.employeecode,
          A.parentemployeecode,
          A.Game_Platform,
          A.Game_Type,
          SUM(A.Bet_Money) AS Bet_Money,
          SUM(A.Net_Money) AS Net_Money,
          SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
        FROM betting_all_day AS A
          INNER JOIN employee_gamecataloy_bonus AS B
            ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
            AND A.Game_Big_Type = B.categorytype
            AND A.Bet_Day BETWEEN begin_date AND end_date
        GROUP BY A.enterprisecode,
                 A.employeecode,
                 A.parentemployeecode,
                 A.Game_Platform,
                 A.Game_Type) AS Game_B
        ON LINE_AA.employeecode = Game_B.employeecode
        AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
        AND LINE_AA.enterprisecode = Game_B.enterprisecode
    WHERE LINE_AA.parentemployeecode = employee_id
    AND LINE_AA.employeetypecode = 'T003') AS FF
  WHERE FF.num > 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_agent_lose_win__detail
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_agent_lose_win__detail`;
DELIMITER ;;
CREATE PROCEDURE `usp_agent_lose_win__detail`(IN employee_id varchar(10), IN begin_date date, IN end_date date, IN startd int, IN endsd int)
BEGIN
  /*
 二次查询客户输赢统计
*/
  IF ISNULL(begin_date) = 1 AND ISNULL(end_date) = 1 THEN
    SET begin_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET end_date = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  SELECT
    GROUP_AA.loginaccount,
    ufn_down_recursion_team_of_agent_count(GROUP_AA.employeecode) AS num,
    GROUP_AA.employeecode,
    Game_B.Bet_Money AS game_betting_amount,
    Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend) -
    ((Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share) AS lose_win_amount,
    Game_B.bonus,
    (Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend AS dividend,
    (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share AS share
  FROM enterprise_employee GROUP_AA
    INNER JOIN (SELECT
        A.enterprisecode,
        A.employeecode,
        A.parentemployeecode,
        A.Game_Platform,
        A.Game_Type,
        SUM(A.Bet_Money) AS Bet_Money,
        SUM(A.Net_Money) AS Net_Money,
        SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
      FROM betting_all_day AS A
        INNER JOIN employee_gamecataloy_bonus AS B
          ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
          AND A.Game_Big_Type = B.categorytype
          AND A.Bet_Day BETWEEN begin_date AND end_date
      GROUP BY A.enterprisecode,
               A.employeecode,
               A.parentemployeecode,
               A.Game_Platform,
               A.Game_Type) AS Game_B
      ON GROUP_AA.employeecode = Game_B.employeecode
      AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
      AND GROUP_AA.enterprisecode = Game_B.enterprisecode
  WHERE GROUP_AA.parentemployeecode = employee_id
  AND GROUP_AA.employeetypecode = 'T004'
  UNION ALL
  SELECT
    '直线会员' AS loginaccount,
    SUM(num) AS num,
    employee_id AS employeecode,
    Bet_Money AS game_betting_amount,
    net_Money + bonus + dividend - share AS lose_win_amount,
    bonus,
    dividend,
    share
  FROM (SELECT
      LINE_AA.loginaccount,
      COUNT(1) AS num,
      LINE_AA.employeecode,
      Game_B.Bet_Money,
      Game_B.net_Money,
      Game_B.bonus,
      (Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend AS dividend,
      (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend)) * LINE_AA.share AS share
    FROM enterprise_employee AS LINE_AA
      INNER JOIN (SELECT
          A.enterprisecode,
          A.employeecode,
          A.parentemployeecode,
          A.Game_Platform,
          A.Game_Type,
          SUM(A.Bet_Money) AS Bet_Money,
          SUM(A.Net_Money) AS Net_Money,
          SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
        FROM betting_all_day AS A
          INNER JOIN employee_gamecataloy_bonus AS B
            ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
            AND A.Game_Big_Type = B.categorytype
            AND A.Bet_Day BETWEEN begin_date AND end_date
        GROUP BY A.enterprisecode,
                 A.employeecode,
                 A.parentemployeecode,
                 A.Game_Platform,
                 A.Game_Type) AS Game_B
        ON LINE_AA.employeecode = Game_B.employeecode
        AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
        AND LINE_AA.enterprisecode = Game_B.enterprisecode
    WHERE LINE_AA.parentemployeecode = employee_id
    AND LINE_AA.employeetypecode = 'T003') AS FF
  WHERE FF.num > 0 LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_customers_lose_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_customers_lose_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_customers_lose_ranking`(IN employeecode varchar(10),
IN start_end date,
IN date_end date)
BEGIN
  /*
 客户输排名
*/
  SELECT
    B.User_Name,
    A.employeecode,
    SUM(B.Net_Money) AS Money,
    start_end AS date_ben,
    date_end AS date_end
  FROM (SELECT
      *
    FROM enterprise_employee
    WHERE parentemployeecode = employeecode
    AND logindatetime BETWEEN start_end AND date_end) AS A
    INNER JOIN betting_all_day AS B
      ON A.employeecode = B.employeecode
  GROUP BY B.User_Name,
           A.employeecode
  ORDER BY Money DESC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_customers_profit_count
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_customers_profit_count`;
DELIMITER ;;
CREATE PROCEDURE `usp_customers_profit_count`(IN employee_id varchar(10), IN begin_date date, IN end_date date, IN startd int, IN endsd int)
BEGIN
  /*
 二次查询利润表
*/
  SELECT
    GROUP_AA.loginaccount,
    ufn_down_recursion_team_of_agent_count(GROUP_AA.employeecode) AS num,
    GROUP_AA.employeecode,
    Game_B.Bet_Money AS game_betting_amount,
    Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend) -
    ((Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share) AS lose_win_amount,
    Game_B.bonus,
    (Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend AS dividend,
    (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share AS share
  FROM enterprise_employee GROUP_AA
    INNER JOIN (SELECT
        A.enterprisecode,
        A.employeecode,
        A.parentemployeecode,
        A.Game_Platform,
        A.Game_Type,
        SUM(A.Bet_Money) AS Bet_Money,
        SUM(A.Net_Money) AS Net_Money,
        SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
      FROM betting_all_day AS A
        INNER JOIN employee_gamecataloy_bonus AS B
          ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
          AND A.Game_Big_Type = B.categorytype) AS Game_B
      ON GROUP_AA.employeecode = Game_B.employeecode
      AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
      AND GROUP_AA.enterprisecode = Game_B.enterprisecode
  WHERE GROUP_AA.parentemployeecode = employee_id
  AND DATE_FORMAT(GROUP_AA.logindatetime, '%Y-%m-%d') BETWEEN begin_date AND end_date
  AND GROUP_AA.employeetypecode = 'T004'
  UNION ALL
  SELECT
    '直线会员' AS loginaccount,
    SUM(num) AS num,
    'E00000BN' AS employeecode,
    Bet_Money AS game_betting_amount,
    net_Money + bonus + dividend - share AS lose_win_amount,
    bonus,
    dividend,
    share
  FROM (SELECT
      LINE_AA.loginaccount,
      COUNT(1) AS num,
      LINE_AA.employeecode,
      Game_B.Bet_Money,
      Game_B.net_Money,
      Game_B.bonus,
      (Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend AS dividend,
      (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend)) * LINE_AA.share AS share
    FROM enterprise_employee AS LINE_AA
      INNER JOIN (SELECT
          A.enterprisecode,
          A.employeecode,
          A.parentemployeecode,
          A.Game_Platform,
          A.Game_Type,
          SUM(A.Bet_Money) AS Bet_Money,
          SUM(A.Net_Money) AS Net_Money,
          SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
        FROM betting_all_day AS A
          INNER JOIN employee_gamecataloy_bonus AS B
            ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
            AND A.Game_Big_Type = B.categorytype) AS Game_B
        ON LINE_AA.employeecode = Game_B.employeecode
        AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
        AND LINE_AA.enterprisecode = Game_B.enterprisecode
    WHERE LINE_AA.parentemployeecode = employee_id
    AND DATE_FORMAT(GROUP_AA.logindatetime, '%Y-%m-%d') BETWEEN begin_date AND end_date
    AND LINE_AA.employeetypecode = 'T003') AS FF
  WHERE FF.num > 0 LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_customers_winning_losing_count
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_customers_winning_losing_count`;
DELIMITER ;;
CREATE PROCEDURE `usp_customers_winning_losing_count`(IN employee_id varchar(10), IN begin_date date, IN end_date date, IN startd int, IN endsd int)
BEGIN
  /*
 二次查询客户输赢统计
*/
  SELECT
    GROUP_AA.loginaccount,
    ufn_down_recursion_team_of_agent_count(GROUP_AA.employeecode) AS num,
    GROUP_AA.employeecode,
    Game_B.Bet_Money AS game_betting_amount,
    Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend) -
    ((Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share) AS lose_win_amount,
    Game_B.bonus,
    (Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend AS dividend,
    (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share AS share
  FROM enterprise_employee GROUP_AA
    INNER JOIN (SELECT
        A.enterprisecode,
        A.employeecode,
        A.parentemployeecode,
        A.Game_Platform,
        A.Game_Type,
        SUM(A.Bet_Money) AS Bet_Money,
        SUM(A.Net_Money) AS Net_Money,
        SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
      FROM betting_all_day AS A
        INNER JOIN employee_gamecataloy_bonus AS B
          ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
          AND A.Game_Big_Type = B.categorytype) AS Game_B
      ON GROUP_AA.employeecode = Game_B.employeecode
      AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
      AND GROUP_AA.enterprisecode = Game_B.enterprisecode
  WHERE GROUP_AA.parentemployeecode = employee_id
  AND DATE_FORMAT(GROUP_AA.logindatetime, '%Y-%m-%d') BETWEEN begin_date AND end_date
  AND GROUP_AA.employeetypecode = 'T004'
  UNION ALL
  SELECT
    loginaccount,
    num,
    employeecode,
    game_betting_amount,
    lose_win_amount,
    bonus,
    dividend,
    share
  FROM (SELECT
      '直线会员' AS loginaccount,
      SUM(num) AS num,
      employee_id AS employeecode,
      Bet_Money AS game_betting_amount,
      net_Money + bonus + dividend - share AS lose_win_amount,
      bonus,
      dividend,
      share
    FROM (SELECT
        LINE_AA.loginaccount,
        COUNT(1) AS num,
        LINE_AA.employeecode,
        Game_B.Bet_Money,
        Game_B.net_Money,
        Game_B.bonus,
        (Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend AS dividend,
        (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend)) * LINE_AA.share AS share
      FROM enterprise_employee AS LINE_AA
        INNER JOIN (SELECT
            A.enterprisecode,
            A.employeecode,
            A.parentemployeecode,
            A.Game_Platform,
            A.Game_Type,
            SUM(A.Bet_Money) AS Bet_Money,
            SUM(A.Net_Money) AS Net_Money,
            SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
          FROM betting_all_day AS A
            INNER JOIN employee_gamecataloy_bonus AS B
              ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
              AND A.Game_Big_Type = B.categorytype
          GROUP BY A.enterprisecode,
                   A.employeecode,
                   A.parentemployeecode,
                   A.Game_Platform,
                   A.Game_Type) AS Game_B
          ON LINE_AA.employeecode = Game_B.employeecode
          AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
          AND LINE_AA.enterprisecode = Game_B.enterprisecode
      WHERE LINE_AA.parentemployeecode = employee_id
      AND DATE_FORMAT(LINE_AA.logindatetime, '%Y-%m-%d') BETWEEN begin_date AND end_date
      AND LINE_AA.employeetypecode = 'T003') AS FF
    WHERE FF.num > 0) AS TT
  WHERE num > 0 LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_customers_win_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_customers_win_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_customers_win_ranking`(IN employeecode varchar(10), IN start_end date, IN date_end date)
BEGIN
  /*
 客户赢排名
*/
  SELECT
    B.User_Name,
    A.employeecode,
    SUM(B.Net_Money) AS Money,
    start_end AS date_ben,
    date_end AS date_end
  FROM (SELECT
      *
    FROM enterprise_employee
    WHERE parentemployeecode = employeecode
    AND logindatetime BETWEEN start_end AND date_end) AS A
    INNER JOIN betting_all_day AS B
      ON A.employeecode = B.employeecode
  GROUP BY B.User_Name,
           A.employeecode
  ORDER BY Money ASC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_Customer_Access_Statistics
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_Customer_Access_Statistics`;
DELIMITER ;;
CREATE PROCEDURE `usp_Customer_Access_Statistics`(IN employeecode varchar(10), IN date_begin date, IN date_end date, IN var_start int, IN var_end int)
BEGIN
  /*
用户存取统计
*/
  SELECT
    AA.loginaccount,
    AA.employeecode,
    BB.deposit_mount,
    BB.deposit_num,
    CC.take_mount,
    CC.take_num
  FROM (SELECT
      loginaccount,
      employeecode,
      parentemployeecode,
      enterprisecode,
      brandcode
    FROM enterprise_employee
    WHERE parentemployeecode = employeecode
    AND logindatetime BETWEEN date_begin AND date_end) AS AA
    INNER JOIN (SELECT
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode,
        SUM(orderamount) AS deposit_mount,
        COUNT(1) AS deposit_num
      FROM deposit_withdraw_order
      WHERE ordertype = 1
      GROUP BY employeecode,
               parentemployeecode,
               enterprisecode,
               brandcode) AS BB
      ON AA.employeecode = BB.employeecode
      AND AA.parentemployeecode = BB.parentemployeecode AND AA.enterprisecode = BB.enterprisecode
      AND AA.brandcode = BB.brandcode
    INNER JOIN (SELECT
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode,
        SUM(orderamount) AS take_mount,
        COUNT(1) AS take_num
      FROM deposit_withdraw_order
      WHERE ordertype = 2
      GROUP BY employeecode,
               parentemployeecode,
               enterprisecode,
               brandcode) AS CC
      ON AA.employeecode = CC.employeecode
      AND AA.parentemployeecode = CC.parentemployeecode AND AA.enterprisecode = CC.enterprisecode
      AND AA.brandcode = CC.brandcode LIMIT var_start, var_end;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_customer_line_winning_loseing_count
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_customer_line_winning_loseing_count`;
DELIMITER ;;
CREATE PROCEDURE `usp_customer_line_winning_loseing_count`(IN employee_id varchar(10), IN start_date date, IN date_end date, IN startd int, IN endsd int)
BEGIN
  /*
 二次查询客户输赢统计----直线会员报表显示
*/
  IF startd = 0 THEN
    SET startd = 0;
  END IF;
  IF endsd = 0 THEN
    SET endsd = 10;
  END IF;

  SELECT
    AA.employeecode,
    AA.loginaccount,
    COUNT(1) AS num,
    GG.Bet_Money AS game_betting_amount,
    GG.Net_Money + GG.Valid_Money * GG.bonus + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend + ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS lose_win_amount,
    GG.Valid_Money * GG.bonus AS bonus,
    (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend AS Dividend,
    ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS share
  FROM enterprise_employee AS AA
    INNER JOIN (SELECT
        BB.employeecode,
        BB.parentemployeecode,
        BB.enterprisecode,
        BB.Game_Platform,
        BB.Game_Type,
        SUM(BB.Bet_Money) AS Bet_Money,
        SUM(BB.Net_Money) AS Net_Money,
        SUM(BB.Valid_Money) AS Valid_Money,
        SUM(DD.bonus) AS bonus
      FROM betting_all_day BB
        INNER JOIN employee_gamecataloy_bonus AS DD
          ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
      GROUP BY BB.employeecode,
               BB.parentemployeecode,
               BB.enterprisecode,
               BB.Game_Platform,
               BB.Game_Type) AS GG
      ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
  WHERE AA.parentemployeecode = employee_id
  AND AA.employeetypecode = 'T003'
  AND DATE_FORMAT(AA.logindatetime, '%Y-%m-%d') BETWEEN start_date AND date_end
  GROUP BY AA.employeecode,
           AA.loginaccount LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_domainlink_user_dom
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_domainlink_user_dom`;
DELIMITER ;;
CREATE PROCEDURE `usp_domainlink_user_dom`(IN employee_id varchar(10), IN link_code varchar(64), IN startsd int, IN endsd int, OUT param1 int)
BEGIN
  /*
品牌编码，品牌名称， 企业编码，企业名称, 域名, 注册用户数量, 用户编码, 用户登录账号
*/
  DECLARE rownums int DEFAULT 0;
  DECLARE rownumd int DEFAULT 0;

  IF ISNULL(employee_id) = 1 THEN
    SET rownums = 1;
  END IF;

  IF ISNULL(link_code) = 1 THEN
    SET rownumd = 1;
  END IF;

  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      A.brandcode,
      brand.brandname,
      A.enterprisecode,
      EN.enterprisename,
      A.domainlink,
      nn.num,
      A.employeecode,
      nn.loginaccount
    FROM brand_domain A
      INNER JOIN (SELECT
          employeecode,
          loginaccount,
          COUNT(1) num
        FROM enterprise_employee
        WHERE datastatus = 1
        GROUP BY employeecode,
                 loginaccount) AS nn
        ON A.employeecode = nn.employeecode
      LEFT JOIN enterprise AS EN
        ON A.enterprisecode = EN.enterprisecode
      LEFT JOIN enterprise_operating_brand AS brand
        ON A.brandcode = brand.brandcode
    WHERE A.domaintype = 2 AND A.datastatus = 1
    AND ((rownums = 1) OR (rownums = 0 AND A.employeecode = employee_id))
    AND ((rownumd = 1) OR (rownumd = 0 AND A.domainlink = link_code))) AS HH;


  SELECT
    A.brandcode,
    brand.brandname,
    A.enterprisecode,
    EN.enterprisename,
    A.domainlink,
    nn.num,
    A.employeecode,
    nn.loginaccount
  FROM brand_domain A
    INNER JOIN (SELECT
        employeecode,
        loginaccount,
        COUNT(1) num
      FROM enterprise_employee
      WHERE datastatus = 1
      GROUP BY employeecode,
               loginaccount) AS nn
      ON A.employeecode = nn.employeecode
    LEFT JOIN enterprise AS EN
      ON A.enterprisecode = EN.enterprisecode
    LEFT JOIN enterprise_operating_brand AS brand
      ON A.brandcode = brand.brandcode
  WHERE A.domaintype = 2 AND A.datastatus = 1
  AND ((rownums = 1) OR (rownums = 0 AND A.employeecode = employee_id))
  AND ((rownumd = 1) OR (rownumd = 0 AND A.domainlink = link_code))
  LIMIT startsd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_down_recursion_team_of_agent
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_down_recursion_team_of_agent`;
DELIMITER ;;
CREATE PROCEDURE `usp_down_recursion_team_of_agent`(IN in_parentemployeecode char(8))
    READS SQL DATA
BEGIN
  DECLARE l_parentemployeecode longtext;
  DECLARE lc_parentemployeecode longtext;

  SET l_parentemployeecode = in_parentemployeecode;
  SET lc_parentemployeecode = in_parentemployeecode;

LABLE_LP:
LOOP
  SELECT
    GROUP_CONCAT(employeecode SEPARATOR ',') INTO lc_parentemployeecode
  FROM enterprise_employee
  WHERE FIND_IN_SET(parentemployeecode, lc_parentemployeecode) > 0 AND employeetypecode != 'T003';
  IF lc_parentemployeecode IS NULL OR lc_parentemployeecode = 'NULL' THEN
    LEAVE LABLE_LP;
  END IF;
  SET l_parentemployeecode = CONCAT_WS(',', l_parentemployeecode, lc_parentemployeecode);
END LOOP LABLE_LP;

  SELECT
    ee.employeecode,
    ee.parentemployeecode
  FROM enterprise_employee ee
  WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003';
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_employeecode_income_statemet
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_employeecode_income_statemet`;
DELIMITER ;;
CREATE PROCEDURE `usp_employeecode_income_statemet`(IN employeecode varchar(10), IN date_begin date,IN date_end date,IN var_start int,IN var_end int)
BEGIN
  /*
	利润表
*/

  SELECT
    FF.loginaccount,
    FF.num,
    FF.employeecode,
    GG.Bet_Money,
    GG.net_money,
    GG.Valid_Money * GG.bonus AS bonus_money,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
      END AS dividend_money,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
      END AS hare_money,
    CASE
        WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
        ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
      END AS money

  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE EXISTS (SELECT
        employeecode
      FROM enterprise_employee AS B
      WHERE parentemployeecode = employeecode
      AND B.employeecode = A.parentemployeecode) AND A.lastlogintime BETWEEN date_begin AND date_end
    AND A.employeetypecode = 'T004'
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.Bet_Money) AS Bet_Money,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode
  UNION ALL
  SELECT
    '直线会员' AS loginaccount,
    COUNT(1) AS num,
    employeecode AS employeecode,
    GG.Bet_Money,
    GG.net_money,
    SUM(GG.Valid_Money * GG.bonus) AS bonus_money,
    SUM(CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
      END) AS dividend_money,
    SUM(CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
      END) AS hare_money,
    SUM(CASE
        WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
        ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
      END) AS money
  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE EXISTS (SELECT
        employeecode
      FROM enterprise_employee AS B
      WHERE parentemployeecode = employeecode
      AND B.employeecode = A.parentemployeecode) AND A.lastlogintime BETWEEN date_begin AND date_end
    AND A.employeetypecode = 'T003'
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.Bet_Money) AS Bet_Money,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode
  LIMIT var_start, var_end;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_employee_login_activity
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_employee_login_activity`;
DELIMITER ;;
CREATE PROCEDURE `usp_employee_login_activity`(IN login varchar(10))
BEGIN
  /*
			客户活跃统计
	*/
  DECLARE rownum int DEFAULT 0;

  SELECT
    '三天内' AS Days,
    COUNT(1) AS num
  FROM enterprise_employee
  WHERE parentemployeecode = login
  AND datastatus = 1
  AND DATE_FORMAT(lastlogintime, '%Y-%m-%d') NOT BETWEEN DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 3 DAY)
  AND DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d')
  UNION ALL
  SELECT
    '七天内' AS Days,
    COUNT(1) AS num
  FROM enterprise_employee
  WHERE parentemployeecode = login
  AND datastatus = 1
  AND DATE_FORMAT(lastlogintime, '%Y-%m-%d') NOT BETWEEN DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 7 DAY)
  AND DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d')
  UNION ALL
  SELECT
    '十五天内' AS Days,
    COUNT(1) AS num
  FROM enterprise_employee
  WHERE parentemployeecode = login
  AND datastatus = 1
  AND DATE_FORMAT(lastlogintime, '%Y-%m-%d') NOT BETWEEN DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 15 DAY)
  AND DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d')
  UNION ALL
  SELECT
    '一个月内' AS Days,
    COUNT(1) AS num
  FROM enterprise_employee
  WHERE parentemployeecode = login
  AND datastatus = 1
  AND DATE_FORMAT(lastlogintime, '%Y-%m-%d') NOT BETWEEN DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY)
  AND DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d')
  UNION ALL
  SELECT
    '三个月内' AS Days,
    COUNT(1) AS num
  FROM enterprise_employee
  WHERE parentemployeecode = login
  AND datastatus = 1
  AND DATE_FORMAT(lastlogintime, '%Y-%m-%d') NOT BETWEEN DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 90 DAY)
  AND DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d')
  UNION ALL
  SELECT
    '三个月以上' AS Days,
    COUNT(1) AS num
  FROM enterprise_employee
  WHERE parentemployeecode = login
  AND datastatus = 1
  AND DATE_FORMAT(lastlogintime, '%Y-%m-%d') < DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 90 DAY);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_enterprise_user_count
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_enterprise_user_count`;
DELIMITER ;;
CREATE PROCEDURE `usp_enterprise_user_count`( in  enterprise_id  VARCHAR(20))
BEGIN
/* 
  企业人数，近一个月的存款情况
*/
	DECLARE  start_date   date;
	DECLARE  date_end   date;

	SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
  SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');

	SELECT count(1)+300000 as registernum, IF(sum(B.orderamount)IS NULL,0,sum(B.orderamount))+50000000 as saveamount
		FROM (SELECT DISTINCT employeecode,parentemployeecode, enterprisecode
						FROM enterprise_employee
					 WHERE enterprisecode = enterprise_id
						 AND datastatus = 1) as A LEFT JOIN  
				 (SELECT employeecode,parentemployeecode, enterprisecode, sum(orderamount) as orderamount
						FROM deposit_withdraw_order 
					 WHERE DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN start_date AND date_end
						 AND ordertype = 1
						 AND orderstatus = 2
					GROUP BY employeecode,parentemployeecode, enterprisecode)as B
			ON A.employeecode = B.employeecode AND A.parentemployeecode = B.parentemployeecode
			AND A.enterprisecode = B.enterprisecode;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_game_Reports
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_game_Reports`;
DELIMITER ;;
CREATE PROCEDURE `usp_game_Reports`(IN employeecode varchar(10),IN date_begin date,IN date_end date,IN var_start int,IN var_end int,OUT param1 int)
BEGIN
  /*
游戏报表
*/
  IF ISNULL(var_start) = 1 AND ISNULL(var_end) = 1 THEN
    SET var_start = 0;
    SET var_end = 10;
  END IF;
  IF ISNULL(date_begin) = 1 AND ISNULL(date_end) = 1 THEN
    SET date_begin = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      FF.loginaccount,
      FF.num,
      FF.employeecode,
      TT.employeetype,
      GG.Bet_Money AS game_betting_amount,
      GG.net_money + (GG.Valid_Money * GG.bonus) AS lose_win_amount,
      GG.Valid_Money * GG.bonus AS bonus

    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T004'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND C.Bet_Day BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode
      INNER JOIN enterprise_employee_type AS TT
        ON FF.employeetypecode = TT.employeetypecode
    UNION ALL
    SELECT
      '直线会员' AS loginaccount,
      COUNT(1) AS num,
      employeecode AS employeecode,
      '会员' AS employeetype,
      SUM(GG.Bet_Money) AS game_betting_amount,
      SUM(GG.net_money + (GG.Valid_Money * GG.bonus)) AS lose_win_amount,
      SUM(GG.Valid_Money * GG.bonus) AS bonus

    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T003'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND C.Bet_Day BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode) AS HH;

  SELECT
    FF.loginaccount,
    FF.num,
    FF.employeecode,
    TT.employeetype employeetypename,
    GG.Bet_Money AS game_betting_amount,
    GG.net_money + (GG.Valid_Money * GG.bonus) AS lose_win_amount,
    GG.Valid_Money * GG.bonus AS bonus

  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE A.parentemployeecode = employeecode
    AND A.employeetypecode = 'T004'
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.Bet_Money) AS Bet_Money,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
          AND C.Bet_Day BETWEEN date_begin AND date_end
      GROUP BY C.employeecode,
               C.enterprisecode,
               C.brandcode) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode
    INNER JOIN enterprise_employee_type AS TT
      ON FF.employeetypecode = TT.employeetypecode
  UNION ALL
  SELECT
    loginaccount,
    num,
    employeecode,
    employeetype,
    game_betting_amount,
    lose_win_amount,
    bonus
  FROM (SELECT
      '直线会员' AS loginaccount,
      COUNT(1) AS num,
      employeecode AS employeecode,
      '会员' AS employeetype,
      SUM(GG.Bet_Money) AS game_betting_amount,
      SUM(GG.net_money + (GG.Valid_Money * GG.bonus)) AS lose_win_amount,
      SUM(GG.Valid_Money * GG.bonus) AS bonus

    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T003'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          C.Game_Platform,
          C.Game_Big_Type,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND C.Bet_Day BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode,
                 C.Game_Platform,
                 C.Game_Big_Type) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode) AS TT
  WHERE num > 0 LIMIT var_start, var_end;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_Linear_income_statement_Member
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_Linear_income_statement_Member`;
DELIMITER ;;
CREATE PROCEDURE `usp_Linear_income_statement_Member`(IN employee_id varchar(10),IN start_date date,IN date_end date,IN startd int,IN endsd int)
BEGIN
  /*
 直线会员报表显示
*/
  IF startd = 0 THEN
    SET startd = 0;
  END IF;
  IF endsd = 0 THEN
    SET endsd = 10;
  END IF;

  SELECT
    AA.employeecode,
    AA.loginaccount,
    COUNT(1) AS num,
    GG.Bet_Money,
    GG.Net_Money + GG.Valid_Money * GG.bonus + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend + ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS MONEY,
    GG.Valid_Money * GG.bonus AS wash,
    (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend AS Dividend,
    ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS Into_account
  FROM enterprise_employee AS AA
    INNER JOIN (SELECT
        BB.employeecode,
        BB.parentemployeecode,
        BB.enterprisecode,
        BB.Game_Platform,
        BB.Game_Type,
        SUM(BB.Bet_Money) AS Bet_Money,
        SUM(BB.Net_Money) AS Net_Money,
        SUM(BB.Valid_Money) AS Valid_Money,
        SUM(DD.bonus) AS bonus
      FROM betting_all_day BB
        INNER JOIN employee_gamecataloy_bonus AS DD
          ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
      GROUP BY BB.employeecode,
               BB.parentemployeecode,
               BB.enterprisecode,
               BB.Game_Platform,
               BB.Game_Type) AS GG
      ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
  WHERE AA.parentemployeecode = employee_id
  AND AA.employeetypecode = 'T003'
  AND DATE_FORMAT(AA.logindatetime, '%Y-%m-%d') BETWEEN start_date AND date_end
  GROUP BY AA.employeecode,
           AA.loginaccount LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_login_desc
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_login_desc`;
DELIMITER ;;
CREATE PROCEDURE `usp_login_desc`(IN employeecode varchar(20), IN start_end date, IN date_end date)
BEGIN
  /*
  取款排名
*/
  SELECT
    GG.employeecode,
    GG.loginaccount,
    IFNULL(SUM(DD.orderamount), 0) AS orderamount,
    start_end AS start_end,
    date_end AS date_end
  FROM (SELECT
      employeecode,
      loginaccount
    FROM enterprise_employee AA
    WHERE parentemployeecode = employeecode) AS GG
    LEFT JOIN deposit_withdraw_order AS DD
      ON GG.employeecode = DD.employeecode
      AND DD.orderdate BETWEEN start_end AND date_end
      AND DD.ordertype = 2
  GROUP BY GG.employeecode,
           GG.loginaccount
  ORDER BY orderamount DESC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_login_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_login_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_login_ranking`(IN employeecode varchar(20), IN start_end varchar(20), IN date_end varchar(20))
BEGIN
  /*
	存款排名
*/
  SELECT
    GG.employeecode,
    GG.loginaccount,
    IFNULL(SUM(DD.orderamount), 0) AS orderamount,
    start_end AS start_end,
    date_end AS date_end
  FROM (SELECT
      employeecode,
      loginaccount
    FROM enterprise_employee AA
    WHERE parentemployeecode = employeecode) AS GG
    LEFT JOIN deposit_withdraw_order AS DD
      ON GG.employeecode = DD.employeecode
      AND DD.orderdate BETWEEN start_end AND date_end
      AND DD.ordertype = 1
  GROUP BY GG.employeecode,
           GG.loginaccount
  ORDER BY orderamount DESC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_member_lose_win_detail
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_member_lose_win_detail`;
DELIMITER ;;
CREATE PROCEDURE `usp_member_lose_win_detail`(IN employee_id varchar(10),IN start_date date,IN date_end date,IN startd int,IN endsd int,OUT param1 int)
BEGIN
  /*
 二次查询客户输赢统计----直线会员报表显示
*/
  IF startd = 0 THEN
    SET startd = 0;
  END IF;
  IF endsd = 0 THEN
    SET endsd = 10;
  END IF;
  IF ISNULL(start_date) = 1 AND ISNULL(date_end) = 1 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      AA.employeecode,
      AA.loginaccount,
      COUNT(1) AS num,
      CONCAT(CC.employeetype, '_nodown') AS employeetypename,
      GG.Bet_Money AS game_betting_amount,
      GG.Net_Money AS lose_win_amount,
      GG.Net_Money + GG.Valid_Money * GG.bonus + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend + ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS user_lose_win_amount,
      GG.Valid_Money * GG.bonus AS bonus,
      (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend AS Dividend,
      ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS share
    FROM enterprise_employee AS AA
      INNER JOIN (SELECT
          BB.employeecode,
          BB.parentemployeecode,
          BB.enterprisecode,
          BB.Game_Platform,
          BB.Game_Type,
          SUM(BB.Bet_Money) AS Bet_Money,
          SUM(BB.Net_Money) AS Net_Money,
          SUM(BB.Valid_Money) AS Valid_Money,
          SUM(DD.bonus) AS bonus
        FROM betting_all_day BB
          INNER JOIN employee_gamecataloy_bonus AS DD
            ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
            AND DATE_FORMAT(BB.Bet_Day, '%Y-%m-%d') BETWEEN start_date AND date_end
        GROUP BY BB.employeecode,
                 BB.parentemployeecode,
                 BB.enterprisecode,
                 BB.Game_Platform,
                 BB.Game_Type) AS GG
        ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
      INNER JOIN enterprise_employee_type AS CC
        ON AA.employeetypecode = CC.employeetypecode
    WHERE AA.parentemployeecode = employee_id
    GROUP BY AA.employeecode,
             AA.loginaccount) AS HH;

  SELECT
    AA.employeecode,
    AA.loginaccount,
    COUNT(1) AS num,
    CONCAT(CC.employeetype, '_nodown') AS employeetypename,
    GG.Bet_Money AS game_betting_amount,
    GG.Net_Money AS lose_win_amount,
    GG.Net_Money + GG.Valid_Money * GG.bonus + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend + ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS user_lose_win_amount,
    GG.Valid_Money * GG.bonus AS bonus,
    (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend AS Dividend,
    ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS share
  FROM enterprise_employee AS AA
    INNER JOIN (SELECT
        BB.employeecode,
        BB.parentemployeecode,
        BB.enterprisecode,
        BB.Game_Platform,
        BB.Game_Type,
        SUM(BB.Bet_Money) AS Bet_Money,
        SUM(BB.Net_Money) AS Net_Money,
        SUM(BB.Valid_Money) AS Valid_Money,
        SUM(DD.bonus) AS bonus
      FROM betting_all_day BB
        INNER JOIN employee_gamecataloy_bonus AS DD
          ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
          AND DATE_FORMAT(BB.Bet_Day, '%Y-%m-%d') BETWEEN start_date AND date_end
      GROUP BY BB.employeecode,
               BB.parentemployeecode,
               BB.enterprisecode,
               BB.Game_Platform,
               BB.Game_Type) AS GG
      ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
    INNER JOIN enterprise_employee_type AS CC
      ON AA.employeetypecode = CC.employeetypecode
  WHERE AA.parentemployeecode = employee_id
  AND AA.employeetypecode = 'T003'

  GROUP BY AA.employeecode,
           AA.loginaccount LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_pay_time
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_pay_time`;
DELIMITER ;;
CREATE PROCEDURE `usp_pay_time`(IN pay_code varchar(10),IN start_end date,IN date_end date)
BEGIN
  /*
业务办理统计
*/
  DECLARE rownum int DEFAULT 0;

  IF ISNULL(start_end) = 1 AND ISNULL(date_end) = 1 THEN
    SET rownum = 1;
  END IF;
  SELECT
    '3-分钟' AS time,
    COUNT(1) AS mum,
    start_end AS start_date,
    date_end AS end_date
  FROM (SELECT
      (handleovertime - orderdate) time,
      ordernumber
    FROM deposit_withdraw_order
    WHERE enterprisecode = pay_code
    AND orderstatus = 2
    AND ((rownum = 1) OR (rownum = 0 AND orderdate BETWEEN start_end AND date_end))) AS A
  WHERE A.time BETWEEN '0' AND '300'
  UNION ALL
  SELECT
    '5-分钟' AS time,
    COUNT(1) AS mum,
    start_end AS start_date,
    date_end AS end_date
  FROM (SELECT
      (handleovertime - orderdate) time,
      ordernumber
    FROM deposit_withdraw_order
    WHERE enterprisecode = pay_code
    AND orderstatus = 2
    AND ((rownum = 1) OR (rownum = 0 AND orderdate BETWEEN start_end AND date_end))) AS A
  WHERE A.time BETWEEN '300' AND '500'
  UNION ALL
  SELECT
    '10-分钟' AS time,
    COUNT(1) AS mum,
    start_end AS start_date,
    date_end AS end_date
  FROM (SELECT
      (handleovertime - orderdate) time,
      ordernumber
    FROM deposit_withdraw_order
    WHERE enterprisecode = pay_code
    AND orderstatus = 2
    AND ((rownum = 1) OR (rownum = 0 AND orderdate BETWEEN start_end AND date_end))) AS A
  WHERE A.time BETWEEN '500' AND '1000'
  UNION ALL
  SELECT
    '30-分钟' AS time,
    COUNT(1) AS mum,
    start_end AS start_date,
    date_end AS end_date
  FROM (SELECT
      (handleovertime - orderdate) time,
      ordernumber
    FROM deposit_withdraw_order
    WHERE enterprisecode = pay_code
    AND orderstatus = 2
    AND ((rownum = 1) OR (rownum = 0 AND orderdate BETWEEN start_end AND date_end))) AS A
  WHERE A.time BETWEEN '1000' AND '3000'
  UNION ALL
  SELECT
    '60-分钟' AS time,
    COUNT(1) AS mum,
    start_end AS start_date,
    date_end AS end_date
  FROM (SELECT
      (handleovertime - orderdate) time,
      ordernumber
    FROM deposit_withdraw_order
    WHERE enterprisecode = pay_code
    AND orderstatus = 2
    AND ((rownum = 1) OR (rownum = 0 AND orderdate BETWEEN start_end AND date_end))) AS A
  WHERE A.time BETWEEN '3000' AND '10000'
  UNION ALL
  SELECT
    '60-分钟以上' AS time,
    COUNT(1) AS mum,
    start_end AS start_date,
    date_end AS end_date
  FROM (SELECT
      (handleovertime - orderdate) time,
      ordernumber
    FROM deposit_withdraw_order
    WHERE enterprisecode = pay_code
    AND orderstatus = 2
    AND ((rownum = 1) OR (rownum = 0 AND orderdate BETWEEN start_end AND date_end))) AS A
  WHERE A.time > '10000';
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_report_by_agent_employeecode
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_report_by_agent_employeecode`;
DELIMITER ;;
CREATE PROCEDURE `usp_report_by_agent_employeecode`(IN `in_parentemployeecode` char(8),IN `in_begindate` datetime,IN `in_enddate` datetime,OUT `but_money` decimal(14,2),OUT `net_money` decimal(14,2),OUT `valid_money` decimal(14,2),OUT `rebates_cash` decimal(14,2) , OUT `countMember` INTEGER,  OUT `countAgent` INTEGER, OUT `countEmployee` INTEGER, OUT `activity_money` decimal(14,2), OUT depositMoney decimal(14,2) , OUT withdrawMoney decimal(14,2) )
BEGIN
	/****************根据代理编码统计时间段内的该代理下的整个团队的汇总数据****************/
	
	DECLARE l_parentemployeecode longtext;
  	DECLARE lc_parentemployeecode longtext;
	
	DECLARE temp_but_money decimal(14,2) default 0;
 	DECLARE temp_net_money decimal(14,2) default 0;
  	DECLARE temp_valid_money decimal(14,2) default 0;
  	DECLARE temp_rebates_cash decimal(14,2) default 0;


	SET but_money = 0 ;
        SET net_money = 0 ;
        SET valid_money = 0 ;
        SET rebates_cash = 0 ;

  	SET l_parentemployeecode = in_parentemployeecode;
  	SET lc_parentemployeecode = in_parentemployeecode;

	LABLE_LP:
	LOOP
  		SELECT
    			GROUP_CONCAT(employeecode SEPARATOR ',') INTO lc_parentemployeecode
  			FROM enterprise_employee
  		WHERE FIND_IN_SET(parentemployeecode, lc_parentemployeecode) > 0 AND employeetypecode != 'T003';
  	IF lc_parentemployeecode IS NULL OR lc_parentemployeecode = 'NULL' THEN
    		LEAVE LABLE_LP;
  	END IF;
  	SET l_parentemployeecode = CONCAT_WS(',', l_parentemployeecode, lc_parentemployeecode);
	END LOOP LABLE_LP;
        
        /********AG*****/
	select ifnull(sum(hh.bet_Amount),0) , ifnull(sum(hh.net_Amount),0), ifnull(sum(hh.valid_Bet_Amount),0), ifnull(sum(hh.valid_Bet_Amount * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_ag hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='TAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********BBIN*****/
	select ifnull(sum(hh.bbin_Bet_Amount),0) , ifnull(sum(hh.bbin_Payoff),0), ifnull(sum(hh.bbin_Commissionable),0), ifnull(sum(hh.bbin_Commissionable * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_bbin hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	) and (hh.bbin_Wagers_Date >= in_begindate and hh.bbin_Wagers_Date <= in_enddate) and bb.gametype='BBINGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
	/********NHQ*****/
	select ifnull(sum(hh.Betting_Credits),0) , ifnull(sum(hh.Winning_Credits),0), ifnull(sum(hh.Wash_Code_Credits),0), ifnull(sum(hh.Wash_Code_Credits * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_nhq hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.Betting_Date >= in_begindate and hh.Betting_Date <= in_enddate) and bb.gametype='NHQGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
	
	/********OG_AG*****/
	select ifnull(sum(hh.aoi_Betting_Amount),0) , ifnull(sum(hh.aoi_Win_Lose_Amount),0), ifnull(sum(hh.aoi_Valid_Amount),0), ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_ag hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='AGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********OG_IBC*****/
	select ifnull(sum(hh.ibc_tzmoney),0) , ifnull(sum(if(hh.ibc_lose>0, 0-hh.ibc_lose, hh.ibc_win)),0), ifnull(sum(hh.ibc_tzmoney),0), ifnull(sum(hh.ibc_tzmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_ibc hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.ibc_balltime >= in_begindate and hh.ibc_balltime <= in_enddate) and bb.gametype='IBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********OG_OG*****/
	select ifnull(sum(hh.aoi_Betting_Amount),0) , ifnull(sum(hh.aoi_Win_Lose_Amount),0), ifnull(sum(hh.aoi_Valid_Amount),0), ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_og hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='OGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;

                
	/********PT*****/
	select ifnull(sum(hh.pt_bet),0) , ifnull(sum(hh.pt_win),0), ifnull(sum(hh.pt_bet),0), ifnull(sum(hh.pt_bet * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_pt hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='PTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********XCP*****/
	select ifnull(sum(hh.xcp_totalprice),0) , ifnull(sum(hh.xcp_prize-xcp_totalprice),0), ifnull(sum(hh.xcp_totalprice),0), ifnull(sum(hh.xcp_totalprice * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_xcp hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.xcp_writetime >= in_begindate and hh.xcp_writetime <= in_enddate) and bb.gametype='XCPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********KR_AV*****/
	select ifnull(sum(hh.amount),0) , ifnull(sum(hh.amount2),0), ifnull(sum(hh.amount),0), ifnull(sum(hh.amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_kr_av hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='AVGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********SA*****/
	select ifnull(sum(hh.betamount),0) , ifnull(sum(hh.resultamount),0), ifnull(sum(hh.validbet),0), ifnull(sum(hh.validbet * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_sa hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SAGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********YG_AG*****/
	select ifnull(sum(hh.money),0) , ifnull(sum(hh.result),0), ifnull(sum(hh.valid_money),0), ifnull(sum(hh.valid_money * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_yg_ag hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='YAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********ZJ*****/
	select ifnull(sum(hh.stakeamount),0) , ifnull(sum(hh.winloss),0), ifnull(sum(hh.validstake),0), ifnull(sum(hh.validstake * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_zj hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='ZJGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********TTG*****/
	select ifnull(sum(hh.amount),0) , ifnull(sum(hh.net_money),0), ifnull(sum(hh.amount),0), ifnull(sum(hh.amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_ttg hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TTGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********MG*****/
	select ifnull(sum(hh.mg_amount),0) , ifnull(sum(hh.net_money),0), ifnull(sum(hh.mg_amount),0), ifnull(sum(hh.mg_amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_mg hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='MGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********PNG*****/
	select ifnull(sum(hh.bet_Amount),0) , ifnull(sum(hh.net_Amount),0), ifnull(sum(hh.valid_Bet_Amount),0), ifnull(sum(hh.valid_Bet_Amount * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_png hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='PNGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********QP*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_qp hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********GG*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_gg hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='GGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********SGS*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_sgs hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SGSGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        
        /********M88*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_m88 hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='M88Game' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********haba*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hab hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='HABGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********WIN88*****/
	select ifnull(sum(hh.pt_bet),0) , ifnull(sum(hh.pt_win-hh.pt_bet),0), ifnull(sum(hh.pt_bet),0), ifnull(sum(hh.pt_bet * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_win88 hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='W88Game' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********TGP*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_tgp hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TGPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********QT*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_qt hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********GB*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_gb hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='GBGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********EBET*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.validbet),0), ifnull(sum(hh.validbet * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_ebet hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='EBETGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********EIBC*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus ),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_eibc hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='eIBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
       
        /********统计会员及代理整个团队数量*****/
        
        /***直线会员数***/
	SELECT count(1) into countMember FROM enterprise_employee WHERE parentemployeecode = in_parentemployeecode and datastatus=1 and employeetypecode='T003';
	/***所有代理数 注意：返回数量包含了当前代理用户，所以需要减去1***/
        SELECT if(count(1)=0,0,count(1)-1) into countAgent FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode = 'T004';
         /***下级员工数***/
	SELECT count(1) into countEmployee FROM enterprise_employee WHERE parentemployeecode = in_parentemployeecode and datastatus=1 and employeetypecode='T002';
	
	/********统计整个团队的涉及的活动金额*****/
	select sum(moneychangeamount) into activity_money from employee_money_change 
                where parentemployeecode in (SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003')
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811';
        
        /********统计总存款*****/
        select IFNULL(sum(orderamount),0) into depositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and 
                parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
  	) and (orderdate >= in_begindate and orderdate <= in_enddate) ; 
  	
  	/********统计总取款*****/
  	select IFNULL(sum(orderamount),0) into withdrawMoney from deposit_withdraw_order where orderstatus='2' and ordertype=2 and 
                parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
  	) and (orderdate >= in_begindate and orderdate <= in_enddate) ;         

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_report_by_agent_employeecode_new
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_report_by_agent_employeecode_new`;
DELIMITER ;;
CREATE PROCEDURE `usp_report_by_agent_employeecode_new`(IN `in_parentemployeecode` char(8),IN `in_begindate` datetime,IN `in_enddate` datetime,OUT `but_money` decimal(14,2),OUT `net_money` decimal(14,2),OUT `valid_money` decimal(14,2),OUT `rebates_cash` decimal(14,2) , OUT `countMember` INTEGER,  OUT `countAgent` INTEGER, OUT `countEmployee` INTEGER, OUT `activity_money` decimal(14,2), OUT depositMoney decimal(14,2) , OUT withdrawMoney decimal(14,2) )
BEGIN
  
  /****************根据代理编码统计时间段内的该代理下的整个团队的汇总数据****************/
	
	DECLARE l_parentemployeecode longtext;
  	DECLARE lc_parentemployeecode longtext;
	
	
	SET but_money = 0 ;
        SET net_money = 0 ;
        SET valid_money = 0 ;
        SET rebates_cash = 0 ;

  	SET l_parentemployeecode = in_parentemployeecode;
  	SET lc_parentemployeecode = in_parentemployeecode;

	LABLE_LP:
	LOOP
  		SELECT
    			GROUP_CONCAT(employeecode SEPARATOR ',') INTO lc_parentemployeecode
  			FROM enterprise_employee
  		WHERE FIND_IN_SET(parentemployeecode, lc_parentemployeecode) > 0 AND employeetypecode != 'T003';
  	IF lc_parentemployeecode IS NULL OR lc_parentemployeecode = 'NULL' THEN
    		LEAVE LABLE_LP;
  	END IF;
  	SET l_parentemployeecode = CONCAT_WS(',', l_parentemployeecode, lc_parentemployeecode);
	END LOOP LABLE_LP;
        
        
        
        /********统计汇总表的数据*****/
        select ifnull(sum(hh.Bet_Money),0) , ifnull(sum(hh.Net_Money),0), ifnull(sum(hh.Valid_Money),0)
	into but_money,net_money,valid_money
	from betting_all_day hh where hh.parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (date(hh.Bet_Day) >= date(in_begindate) and date(hh.Bet_Day) <= date(in_enddate)) ;
	
        
       

        
        /********统计会员及代理整个团队数量*****/
        
        /***直线会员数***/
	SELECT count(1) into countMember FROM enterprise_employee WHERE parentemployeecode = in_parentemployeecode and datastatus=1 and employeetypecode='T003';
	/***所有代理数 注意：返回数量包含了当前代理用户，所以需要减去1***/
        SELECT if(count(1)=0,0,count(1)-1) into countAgent FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode = 'T004';
         /***下级员工数***/
	SELECT count(1) into countEmployee FROM enterprise_employee WHERE parentemployeecode = in_parentemployeecode and datastatus=1 and employeetypecode='T002';
	
	/********统计整个团队的涉及的活动金额*****/
	select sum(moneychangeamount) into activity_money from employee_money_change 
                where parentemployeecode in (SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003')
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811';
        
        
		
		
        /********统计整个团队的涉及的实际洗码金额
        //洗码日结("B1FF4C4ADC9C423C8D0344DDFD7DC279","洗码日结"),
        //洗码周结("6B06E77AA855454EB5ADF60B6CC37787","洗码周结"),
        //周结校验补发("56F82CC5B5DC4FFABBFD62F82CACA891","周结校验补发"), *****/       
	select sum(moneychangeamount) into rebates_cash from employee_money_change 
                where parentemployeecode in (SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003')
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode in('B1FF4C4ADC9C423C8D0344DDFD7DC279','6B06E77AA855454EB5ADF60B6CC37787','56F82CC5B5DC4FFABBFD62F82CACA891');
                
                        
        
        /********统计总存款*****/
        select IFNULL(sum(orderamount),0) into depositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and 
                parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
  	) and (orderdate >= in_begindate and orderdate <= in_enddate) ; 
  	
  	/********统计总取款*****/
  	select IFNULL(sum(orderamount),0) into withdrawMoney from deposit_withdraw_order where orderstatus='2' and ordertype=2 and 
                parentemployeecode in (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
  	) and (orderdate >= in_begindate and orderdate <= in_enddate) ; 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_report_by_member_employeecode
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_report_by_member_employeecode`;
DELIMITER ;;
CREATE PROCEDURE `usp_report_by_member_employeecode`(IN `in_parentemployeecode` char(8),IN `in_begindate` datetime,IN `in_enddate` datetime,OUT `but_money` decimal(14,2),OUT `net_money` decimal(14,2),OUT `valid_money` decimal(14,2),OUT `rebates_cash` decimal(14,2), OUT `countMember` INTEGER, OUT `activity_money` decimal(14,2) ,   OUT depositMoney decimal(14,2) , OUT withdrawMoney decimal(14,2) )
BEGIN
	
	/****************?????????????????????????****************/

	
	DECLARE temp_but_money decimal(14,2) default 0;
 	DECLARE temp_net_money decimal(14,2) default 0;
  	DECLARE temp_valid_money decimal(14,2) default 0;
  	DECLARE temp_rebates_cash decimal(14,2) default 0;


	SET but_money = 0 ;
        SET net_money = 0 ;
        SET valid_money = 0 ;
        SET rebates_cash = 0 ;


	/********AG*****/
	select ifnull(sum(hh.bet_Amount),0) , ifnull(sum(hh.net_Amount),0), ifnull(sum(hh.valid_Bet_Amount),0), ifnull(sum(hh.valid_Bet_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_ag hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='TAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********BBIN*****/
	select ifnull(sum(hh.bbin_Bet_Amount),0) , ifnull(sum(hh.bbin_Payoff-bbin_Bet_Amount),0), ifnull(sum(hh.bbin_Commissionable),0), ifnull(sum(hh.bbin_Commissionable * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_bbin hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	) and (hh.bbin_Wagers_Date >= in_begindate and hh.bbin_Wagers_Date <= in_enddate) and bb.gametype='BBINGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
	/********NHQ*****/
	select ifnull(sum(hh.Betting_Credits),0) , ifnull(sum(hh.Winning_Credits),0), ifnull(sum(hh.Wash_Code_Credits),0), ifnull(sum(hh.Wash_Code_Credits * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_nhq hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.Betting_Date >= in_begindate and hh.Betting_Date <= in_enddate) and bb.gametype='NHQGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
	
	/********OG_AG*****/
	select ifnull(sum(hh.aoi_Betting_Amount),0) , ifnull(sum(hh.aoi_Win_Lose_Amount),0), ifnull(sum(hh.aoi_Valid_Amount),0), ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_ag hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='AGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********OG_IBC*****/
	select ifnull(sum(hh.ibc_tzmoney),0) , ifnull(sum(if(hh.ibc_lose>0, 0-hh.ibc_lose, hh.ibc_win)),0), ifnull(sum(hh.ibc_tzmoney),0), ifnull(sum(hh.ibc_tzmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_ibc hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.ibc_balltime >= in_begindate and hh.ibc_balltime <= in_enddate) and bb.gametype='IBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********OG_OG*****/
	select ifnull(sum(hh.aoi_Betting_Amount),0) , ifnull(sum(hh.aoi_Win_Lose_Amount),0), ifnull(sum(hh.aoi_Valid_Amount),0), ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_og hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='OGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;

                
	/********PT*****/
	select ifnull(sum(hh.pt_bet),0) , ifnull(sum(hh.pt_win-hh.pt_bet),0), ifnull(sum(hh.pt_bet),0), ifnull(sum(hh.pt_bet * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_pt hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='PTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********XCP*****/
	select ifnull(sum(hh.xcp_totalprice),0) , ifnull(sum(hh.xcp_prize-xcp_totalprice),0), ifnull(sum(hh.xcp_totalprice),0), ifnull(sum(hh.xcp_totalprice * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_xcp hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.xcp_writetime >= in_begindate and hh.xcp_writetime <= in_enddate) and bb.gametype='XCPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********KR_AV*****/
	select ifnull(sum(hh.amount),0) , ifnull(sum(hh.amount2),0), ifnull(sum(hh.amount),0), ifnull(sum(hh.amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_kr_av hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='AVGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********SA*****/
	select ifnull(sum(hh.betamount),0) , ifnull(sum(hh.resultamount),0), ifnull(sum(hh.betamount),0), ifnull(sum(hh.betamount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_sa hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SAGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********YG_AG*****/
	select ifnull(sum(hh.money),0) , ifnull(sum(hh.result),0), ifnull(sum(hh.valid_money),0), ifnull(sum(hh.valid_money * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_yg_ag hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='YAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********ZJ*****/
	select ifnull(sum(hh.stakeamount),0) , ifnull(sum(hh.winloss),0), ifnull(sum(hh.validstake),0), ifnull(sum(hh.validstake * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_zj hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='ZJGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;

	/********TTG*****/
        select ifnull(sum(hh.amount),0) , ifnull(sum(hh.net_money),0), ifnull(sum(hh.amount),0), ifnull(sum(hh.amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_ttg hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TTGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;

	/********MG***** 2017-05-03 测试出现bug，从生产环境拷贝过来替换
	select ifnull(sum(hh.mg_amount),0) , ifnull(sum(hh.net_money),0), ifnull(sum(hh.mg_amount),0), ifnull(sum(hh.mg_amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_mg hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		SELECT ee.employeecode FROM enterprise_employee ee WHERE FIND_IN_SET(ee.employeecode, l_parentemployeecode) > 0 AND ee.employeetypecode != 'T003'
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='MGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;*/

	select ifnull(sum(hh.mg_amount),0) , ifnull(sum(hh.net_money),0), ifnull(sum(hh.mg_amount),0), ifnull(sum(hh.mg_amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_mg hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='MGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;


	/********PNG*****/
	select ifnull(sum(hh.bet_Amount),0) , ifnull(sum(hh.net_Amount),0), ifnull(sum(hh.valid_Bet_Amount),0), ifnull(sum(hh.valid_Bet_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_png hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='PNGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;


	/********QP*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_qp hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
	
	/********??????*****/
	select count(1) into countMember from enterprise_employee where parentemployeecode = in_parentemployeecode and datastatus=1 and employeetypecode='T003';
	
	/********??????????????*****/
	select sum(moneychangeamount) into activity_money from employee_money_change 
                where employeecode in ( SELECT ee.employeecode FROM enterprise_employee ee WHERE parentemployeecode =in_parentemployeecode AND ee.employeetypecode = 'T003' )
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811';
                
        /********??????????*****/
        select IFNULL(sum(orderamount),0) into depositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and 
                parentemployeecode in (
  		in_parentemployeecode
  	) and (orderdate >= in_begindate and orderdate <= in_enddate) ; 
  	
  	/********??????????*****/
  	select IFNULL(sum(orderamount),0) into withdrawMoney from deposit_withdraw_order where orderstatus='2' and ordertype=2 and 
                parentemployeecode in (
  		in_parentemployeecode
  	) and (orderdate >= in_begindate and orderdate <= in_enddate) ;         
  

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_report_by_member_employeecode_new
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_report_by_member_employeecode_new`;
DELIMITER ;;
CREATE PROCEDURE `usp_report_by_member_employeecode_new`(IN `in_parentemployeecode` char(8),IN `in_begindate` datetime,IN `in_enddate` datetime,OUT `but_money` decimal(14,2),OUT `net_money` decimal(14,2),OUT `valid_money` decimal(14,2),OUT `rebates_cash` decimal(14,2), OUT `countMember` INTEGER, OUT `activity_money` decimal(14,2) ,   OUT depositMoney decimal(14,2) , OUT withdrawMoney decimal(14,2) )
    READS SQL DATA
BEGIN
  
  
  /****************根据代理编码统计时间段内的该代理下直线会员汇总数据****************/

	

	SET but_money = 0 ;
        SET net_money = 0 ;
        SET valid_money = 0 ;
        SET rebates_cash = 0 ;


	
	 /********统计汇总表的数据*****/
        select ifnull(sum(hh.Bet_Money),0) , ifnull(sum(hh.Net_Money),0), ifnull(sum(hh.Valid_Money),0) 
	into but_money,net_money,valid_money
	from betting_all_day hh where  hh.parentemployeecode = (
  		in_parentemployeecode
	)  and (date(hh.Bet_Day) >= date(in_begindate) and date(hh.Bet_Day) <= date(in_enddate)) 
	;
        
        
        
	/********统计会员数量*****/
	select count(1) into countMember from enterprise_employee where parentemployeecode = in_parentemployeecode and datastatus=1 and employeetypecode='T003';
	
	/********统计直线会员的涉及的活动金额*****/
	select sum(moneychangeamount) into activity_money from employee_money_change 
                where employeecode in ( SELECT ee.employeecode FROM enterprise_employee ee WHERE parentemployeecode =in_parentemployeecode AND ee.employeetypecode = 'T003' )
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811';
                
        /********统计整个团队的涉及的实际洗码金额
        //洗码日结("B1FF4C4ADC9C423C8D0344DDFD7DC279","洗码日结"),
        //洗码周结("6B06E77AA855454EB5ADF60B6CC37787","洗码周结"),
        //周结校验补发("56F82CC5B5DC4FFABBFD62F82CACA891","周结校验补发"), *****/       
	select sum(moneychangeamount) into rebates_cash from employee_money_change 
                where employeecode in ( SELECT ee.employeecode FROM enterprise_employee ee WHERE parentemployeecode =in_parentemployeecode AND ee.employeetypecode = 'T003' )
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode in('B1FF4C4ADC9C423C8D0344DDFD7DC279','6B06E77AA855454EB5ADF60B6CC37787','56F82CC5B5DC4FFABBFD62F82CACA891');        
        
        
        /********统计总存款*****/
        select IFNULL(sum(orderamount),0) into depositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and 
                parentemployeecode = (
  		in_parentemployeecode
  	) and (orderdate >= in_begindate and orderdate <= in_enddate) ; 
  	
  	/********统计总取款*****/
  	select IFNULL(sum(orderamount),0) into withdrawMoney from deposit_withdraw_order where orderstatus='2' and ordertype=2 and 
                parentemployeecode = (
  		in_parentemployeecode
  	) and (orderdate >= in_begindate and orderdate <= in_enddate) ;               
  	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_report_by_member_employeecode_one
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_report_by_member_employeecode_one`;
DELIMITER ;;
CREATE PROCEDURE `usp_report_by_member_employeecode_one`(IN `in_employeecode` char(8),IN `in_begindate` datetime,IN `in_enddate` datetime,OUT `but_money` decimal(14,2),OUT `net_money` decimal(14,2),OUT `valid_money` decimal(14,2),OUT `rebates_cash` decimal(14,2),OUT `activity_money` decimal(14,2) ,  OUT depositMoney decimal(14,2) , OUT withdrawMoney decimal(14,2) )
    READS SQL DATA
BEGIN
        
        
        /********统计会员时间段内的汇总数据*****/
  
        DECLARE temp_but_money decimal(14,2) default 0;
 	DECLARE temp_net_money decimal(14,2) default 0;
  	DECLARE temp_valid_money decimal(14,2) default 0;
  	DECLARE temp_rebates_cash decimal(14,2) default 0;


	SET but_money = 0 ;
        SET net_money = 0 ;
        SET valid_money = 0 ;
        SET rebates_cash = 0 ;
        SET activity_money = 0 ;


	/********AG*****/
	select ifnull(sum(hh.bet_Amount),0) , ifnull(sum(hh.net_Amount),0), ifnull(sum(hh.valid_Bet_Amount),0), ifnull(sum(hh.valid_Bet_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_ag hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='TAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********BBIN*****/
	select ifnull(sum(hh.bbin_Bet_Amount),0) , ifnull(sum(hh.bbin_Payoff),0), ifnull(sum(hh.bbin_Commissionable),0), ifnull(sum(hh.bbin_Commissionable * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_bbin hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	) and (hh.bbin_Wagers_Date >= in_begindate and hh.bbin_Wagers_Date <= in_enddate) and bb.gametype='BBINGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
	/********NHQ*****/
	select ifnull(sum(hh.Betting_Credits),0) , ifnull(sum(hh.Winning_Credits),0), ifnull(sum(hh.Wash_Code_Credits),0), ifnull(sum(hh.Wash_Code_Credits * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_nhq hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.Betting_Date >= in_begindate and hh.Betting_Date <= in_enddate) and bb.gametype='NHQGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
	
	/********OG_AG*****/
	select ifnull(sum(hh.aoi_Betting_Amount),0) , ifnull(sum(hh.aoi_Win_Lose_Amount),0), ifnull(sum(hh.aoi_Valid_Amount),0), ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_ag hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='AGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********OG_IBC*****/
	select ifnull(sum(hh.ibc_tzmoney),0) , ifnull(sum(if(hh.ibc_lose>0, 0-hh.ibc_lose, hh.ibc_win)),0), ifnull(sum(hh.ibc_tzmoney),0), ifnull(sum(hh.ibc_tzmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_ibc hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.ibc_balltime >= in_begindate and hh.ibc_balltime <= in_enddate) and bb.gametype='IBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********OG_OG*****/
	select ifnull(sum(hh.aoi_Betting_Amount),0) , ifnull(sum(hh.aoi_Win_Lose_Amount),0), ifnull(sum(hh.aoi_Valid_Amount),0), ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_og_og hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='OGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;

                
	/********PT*****/
	select ifnull(sum(hh.pt_bet),0) , ifnull(sum(hh.pt_win),0), ifnull(sum(hh.pt_bet),0), ifnull(sum(hh.pt_bet * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_pt hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='PTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********XCP*****/
	select ifnull(sum(hh.xcp_totalprice),0) , ifnull(sum(hh.xcp_prize-xcp_totalprice),0), ifnull(sum(hh.xcp_totalprice),0), ifnull(sum(hh.xcp_totalprice * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_xcp hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.xcp_writetime >= in_begindate and hh.xcp_writetime <= in_enddate) and bb.gametype='XCPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********KR_AV*****/
	select ifnull(sum(hh.amount),0) , ifnull(sum(hh.amount2),0), ifnull(sum(hh.amount),0), ifnull(sum(hh.amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_kr_av hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='AVGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********SA*****/
	select ifnull(sum(hh.betamount),0) , ifnull(sum(hh.resultamount),0), ifnull(sum(hh.validbet),0), ifnull(sum(hh.validbet * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_sa hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SAGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********YG_AG*****/
	select ifnull(sum(hh.money),0) , ifnull(sum(hh.result),0), ifnull(sum(hh.valid_money),0), ifnull(sum(hh.valid_money * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_yg_ag hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='YAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********ZJ*****/
	select ifnull(sum(hh.stakeamount),0) , ifnull(sum(hh.winloss),0), ifnull(sum(hh.validstake),0), ifnull(sum(hh.validstake * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_zj hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='ZJGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********TTG*****/
        select ifnull(sum(hh.amount),0) , ifnull(sum(hh.net_money),0), ifnull(sum(hh.amount),0), ifnull(sum(hh.amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_ttg hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TTGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;     
        
        /********MG*****/
	select ifnull(sum(hh.mg_amount),0) , ifnull(sum(hh.net_money),0), ifnull(sum(hh.mg_amount),0), ifnull(sum(hh.mg_amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_mg hh inner join employee_gamecataloy_bonus bb on  hh.parentemployeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='MGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash; 
        
        
        /********PNG*****/
	select ifnull(sum(hh.bet_Amount),0) , ifnull(sum(hh.net_Amount),0), ifnull(sum(hh.valid_Bet_Amount),0), ifnull(sum(hh.valid_Bet_Amount * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hq_png hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='PNGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********QP*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_qp hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********GG*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_gg hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='GGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********SGS*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_sgs hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SGSGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        
        /********M88*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_m88 hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='M88Game' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********haba*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_hab hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='HABGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        
        /********WIN88*****/
	select ifnull(sum(hh.pt_bet),0) , ifnull(sum(hh.pt_win-hh.pt_bet),0), ifnull(sum(hh.pt_bet),0), ifnull(sum(hh.pt_bet * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_win88 hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='W88Game' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;		
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********TGP*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_tgp hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TGPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********QT*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_qt hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********GB*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_gb hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='GBGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********EBET*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.validbet),0), ifnull(sum(hh.validbet * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_ebet hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='EBETGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
        
        /********EIBC*****/
	select ifnull(sum(hh.betmoney),0) , ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0), ifnull(sum(hh.betmoney * bb.bonus),0) 
	into temp_but_money,temp_net_money,temp_valid_money,temp_rebates_cash
	from betting_eibc hh inner join employee_gamecataloy_bonus bb on  hh.employeecode = (
  		in_employeecode
	)  and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='eIBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode ;	
	SET but_money = but_money + temp_but_money;    
        SET net_money = net_money + temp_net_money;
        SET valid_money = valid_money + temp_valid_money;
        SET rebates_cash = rebates_cash + temp_rebates_cash;
	
	
	/********统计直线会员的涉及的活动金额*****/
	select sum(moneychangeamount) into activity_money from employee_money_change 
                where employeecode = in_employeecode 
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811';
                
        /********统计总存款*****/
        select IFNULL(sum(orderamount),0) into depositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and 
                employeecode =in_employeecode and (orderdate >= in_begindate and orderdate <= in_enddate) ; 
  	
  	/********统计总取款*****/
  	select IFNULL(sum(orderamount),0) into withdrawMoney from deposit_withdraw_order where orderstatus='2' and ordertype=2 and 
                employeecode =in_employeecode and (orderdate >= in_begindate and orderdate <= in_enddate) ;         
  	
  	        
        select but_money,net_money,valid_money,rebates_cash,activity_money, depositMoney, withdrawMoney;
  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_report_by_member_employeecode_one_new
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_report_by_member_employeecode_one_new`;
DELIMITER ;;
CREATE PROCEDURE `usp_report_by_member_employeecode_one_new`(IN `in_employeecode` char(8),IN `in_begindate` datetime,IN `in_enddate` datetime,OUT `but_money` decimal(14,2),OUT `net_money` decimal(14,2),OUT `valid_money` decimal(14,2),OUT `rebates_cash` decimal(14,2),OUT `activity_money` decimal(14,2) ,  OUT depositMoney decimal(14,2) , OUT withdrawMoney decimal(14,2) )
BEGIN
  /********统计会员时间段内的汇总数据*****/
SET but_money = 0 ;
        SET net_money = 0 ;
        SET valid_money = 0 ;
        SET rebates_cash = 0 ;
        SET activity_money = 0 ;


	
	/********统计汇总表的数据*****/
        select ifnull(sum(hh.Bet_Money),0) , ifnull(sum(hh.Net_Money),0), ifnull(sum(hh.Valid_Money),0)
	into but_money,net_money,valid_money
	from betting_all_day hh where  hh.employeecode = (
  		in_employeecode
	)  and (date(hh.Bet_Day) >= date(in_begindate) and date(hh.Bet_Day) <= date(in_enddate)) ;
        
	
	/********统计直线会员的涉及的活动金额*****/
	select sum(moneychangeamount) into activity_money from employee_money_change 
                where employeecode = in_employeecode 
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811';
        
        /********统计整个团队的涉及的实际洗码金额
        //洗码日结("B1FF4C4ADC9C423C8D0344DDFD7DC279","洗码日结"),
        //洗码周结("6B06E77AA855454EB5ADF60B6CC37787","洗码周结"),
        //周结校验补发("56F82CC5B5DC4FFABBFD62F82CACA891","周结校验补发"), *****/       
	select sum(moneychangeamount) into rebates_cash from employee_money_change 
                where employeecode = in_employeecode 
                and (moneychangedate >= in_begindate and moneychangedate <= in_enddate) 
                and moneychangetypecode in('B1FF4C4ADC9C423C8D0344DDFD7DC279','6B06E77AA855454EB5ADF60B6CC37787','56F82CC5B5DC4FFABBFD62F82CACA891');              
                
        /********统计总存款*****/
        select IFNULL(sum(orderamount),0) into depositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and 
                employeecode =in_employeecode and (orderdate >= in_begindate and orderdate <= in_enddate) ; 
  	
  	/********统计总取款*****/
  	select IFNULL(sum(orderamount),0) into withdrawMoney from deposit_withdraw_order where orderstatus='2' and ordertype=2 and 
                employeecode =in_employeecode and (orderdate >= in_begindate and orderdate <= in_enddate) ;         
  	
  	        
        select but_money,net_money,valid_money,rebates_cash,activity_money, depositMoney, withdrawMoney;    
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_report_by_member_employeecode_page
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_report_by_member_employeecode_page`;
DELIMITER ;;
CREATE PROCEDURE `usp_report_by_member_employeecode_page`(IN in_parentemployeecode CHAR(8), IN in_begindate DATETIME, IN in_enddate DATETIME,IN `var_start` INTEGER,IN `var_limit` INTEGER,OUT `but_money` decimal(14,2),OUT `net_money` decimal(14,2),OUT `valid_money` decimal(14,2),OUT `rebates_cash` decimal(14,2), OUT `countMember` INTEGER)
    READS SQL DATA
BEGIN
        
        /****************根据代理编码查询直线会员的按平台分类汇总数据，有分页******************/

SELECT sum(s1),sum(s2),sum(s3),sum(s4),count(1) into but_money,net_money,valid_money,rebates_cash,countMember FROM(
        /***AG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'TAGGame',ifnull(sum(hh.bet_Amount),0) s1, ifnull(sum(hh.net_Amount),0) s2, ifnull(sum(hh.valid_Bet_Amount),0) s3, ifnull(sum(hh.valid_Bet_Amount * bb.bonus),0) s4
        from betting_hq_ag hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='TAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***BBIN**/
        select hh.EMPLOYEECODE,hh.loginaccount,'BBINGame',ifnull(sum(hh.bbin_Bet_Amount),0) s1, ifnull(sum(hh.bbin_Payoff),0) s2, ifnull(sum(hh.bbin_Commissionable),0) s3, ifnull(sum(hh.bbin_Commissionable * bb.bonus),0) s4 
        from betting_hq_bbin hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bbin_Wagers_Date >= in_begindate and hh.bbin_Wagers_Date <= in_enddate) and bb.gametype='BBINGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***NHQ**/
        select hh.EMPLOYEECODE,hh.loginaccount,'NHQGame',ifnull(sum(hh.Betting_Credits),0) s1, ifnull(sum(hh.Winning_Credits),0) s2, ifnull(sum(hh.Wash_Code_Credits),0) s3, ifnull(sum(hh.Wash_Code_Credits * bb.bonus),0) s4
        from betting_hq_nhq hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.Betting_Date >= in_begindate and hh.Betting_Date <= in_enddate) and bb.gametype='NHQGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***OG_AG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'AGGame',ifnull(sum(hh.aoi_Betting_Amount),0) s1, ifnull(sum(hh.aoi_Win_Lose_Amount),0) s2, ifnull(sum(hh.aoi_Valid_Amount),0) s3, ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) s4
        from betting_hq_og_ag hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='AGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***OG_IBC**/
        select hh.EMPLOYEECODE,hh.loginaccount,'IBCGame',ifnull(sum(hh.ibc_tzmoney),0) s1, ifnull(sum(if(hh.ibc_lose>0, 0-hh.ibc_lose, hh.ibc_win)),0) s2, ifnull(sum(hh.ibc_tzmoney),0) s3, ifnull(sum(hh.ibc_tzmoney * bb.bonus),0) s4
        from betting_hq_og_ibc hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.ibc_balltime >= in_begindate and hh.ibc_balltime <= in_enddate) and bb.gametype='IBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***OG_OG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'OGGame',ifnull(sum(hh.aoi_Betting_Amount),0) s1, ifnull(sum(hh.aoi_Win_Lose_Amount),0) s2, ifnull(sum(hh.aoi_Valid_Amount),0) s3, ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) s4
        from betting_hq_og_og hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='OGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***PT**/
        select hh.EMPLOYEECODE,hh.loginaccount,'PTGame',ifnull(sum(hh.pt_bet),0) s1, ifnull(sum(hh.pt_win),0) s2, ifnull(sum(hh.pt_bet),0) s3, ifnull(sum(hh.pt_bet * bb.bonus),0) s4
        from betting_hq_pt hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='PTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***XCP**/
        select hh.EMPLOYEECODE,hh.loginaccount,'XCPGame',ifnull(sum(hh.xcp_totalprice),0) s1, ifnull(sum(hh.xcp_prize-xcp_totalprice),0) s2, ifnull(sum(hh.xcp_totalprice),0) s3, ifnull(sum(hh.xcp_totalprice * bb.bonus),0) s4
        from betting_hq_xcp hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.xcp_writetime >= in_begindate and hh.xcp_writetime <= in_enddate) and bb.gametype='XCPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***KR_AV**/
        select hh.EMPLOYEECODE,hh.loginaccount,'AVGame',ifnull(sum(hh.amount),0) s1, ifnull(sum(hh.amount2),0) s2, ifnull(sum(hh.amount),0) s3, ifnull(sum(hh.amount * bb.bonus),0) s4
        from betting_kr_av hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='AVGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***SA**/
        select hh.EMPLOYEECODE,hh.loginaccount,'SAGame',ifnull(sum(hh.betamount),0) s1, ifnull(sum(hh.resultamount),0) s2, ifnull(sum(hh.validbet),0) s3, ifnull(sum(hh.validbet * bb.bonus),0) s4
        from betting_sa hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SAGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***YG_AG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'YAGGame',ifnull(sum(hh.money),0) s1, ifnull(sum(hh.result),0) s2, ifnull(sum(hh.valid_money),0) s3, ifnull(sum(hh.valid_money * bb.bonus),0) s4
        from betting_yg_ag hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='YAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***ZJ**/
        select hh.EMPLOYEECODE,hh.loginaccount,'ZJGame',ifnull(sum(hh.stakeamount),0) s1, ifnull(sum(hh.winloss),0) s2, ifnull(sum(hh.validstake),0) s3, ifnull(sum(hh.validstake * bb.bonus),0) s4
        from betting_zj hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='ZJGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
       
        UNION ALL 
        /***TTG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'TTGGame',ifnull(sum(hh.amount),0) s1, ifnull(sum(hh.net_money),0) s2, ifnull(sum(hh.amount),0) s3, ifnull(sum(hh.amount * bb.bonus),0) s4
        from betting_ttg hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TTGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE 
        
        UNION ALL 
        /***MG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'MGGame',ifnull(sum(hh.mg_amount),0) s1, ifnull(sum(hh.net_money),0) s2, ifnull(sum(hh.mg_amount),0) s3, ifnull(sum(hh.mg_amount * bb.bonus),0) s4
        from betting_mg hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='MGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE 
        
        UNION ALL 
        /***PNG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'PNGGame',ifnull(sum(hh.bet_Amount),0) s1, ifnull(sum(hh.net_Amount),0) s2, ifnull(sum(hh.valid_Bet_Amount),0) s3, ifnull(sum(hh.valid_Bet_Amount * bb.bonus),0) s4
        from betting_hq_png hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='PNGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***QP**/
        select hh.EMPLOYEECODE,hh.loginaccount,'QPGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_qp hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***GG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'GGGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_gg hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='GGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***SGS**/
        select hh.EMPLOYEECODE,hh.loginaccount,'SGSGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_sgs hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SGSGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        
        UNION ALL 
        /***M88**/
        select hh.EMPLOYEECODE,hh.loginaccount,'M88Game',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_m88 hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='M88Game' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***haba**/
        select hh.EMPLOYEECODE,hh.loginaccount,'HABGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_hab hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='HABGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        
        UNION ALL
        /***WIN88**/
        select hh.EMPLOYEECODE,hh.loginaccount,'W88Game',ifnull(sum(hh.pt_bet),0) s1, ifnull(sum(hh.pt_win-hh.pt_bet),0) s2, ifnull(sum(hh.pt_bet),0) s3, ifnull(sum(hh.pt_bet * bb.bonus),0) s4
        from betting_win88 hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='W88Game' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        UNION ALL 
        /***TGP**/
        select hh.EMPLOYEECODE,hh.loginaccount,'TGPGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_tgp hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TGPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        UNION ALL 
        /***QT**/
        select hh.EMPLOYEECODE,hh.loginaccount,'QTGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_qt hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        UNION ALL 
        /***GB**/
        select hh.EMPLOYEECODE,hh.loginaccount,'GBGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_gb hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='GBGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        UNION ALL 
        /***EBET**/
        select hh.EMPLOYEECODE,hh.loginaccount,'EBETGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.validbet),0) s3, ifnull(sum(hh.validbet * bb.bonus),0) s4
        from betting_ebet hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='EBETGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***EIBC**/
        select hh.EMPLOYEECODE,hh.loginaccount,'eIBCGame',ifnull(sum(hh.betmoney),0) s1, ifnull(sum(hh.netmoney),0) s2, ifnull(sum(hh.betmoney),0) s3, ifnull(sum(hh.betmoney * bb.bonus),0) s4
        from betting_eibc hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='eIBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
) 
as b;


SELECT a.* FROM(
        /***AG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'TAGGame' gametype,ifnull(sum(hh.bet_Amount),0) game_betting_amount, ifnull(sum(hh.net_Amount),0), ifnull(sum(hh.valid_Bet_Amount),0) lose_win_amount, ifnull(sum(hh.valid_Bet_Amount * bb.bonus),0) bonus
        from betting_hq_ag hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='TAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***BBIN**/
        select hh.EMPLOYEECODE,hh.loginaccount,'BBINGame' gametype,ifnull(sum(hh.bbin_Bet_Amount),0) game_betting_amount, ifnull(sum(hh.bbin_Payoff-bbin_Bet_Amount),0) lose_win_amount, ifnull(sum(hh.bbin_Commissionable),0), ifnull(sum(hh.bbin_Commissionable * bb.bonus),0) bonus
        from betting_hq_bbin hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bbin_Wagers_Date >= in_begindate and hh.bbin_Wagers_Date <= in_enddate) and bb.gametype='BBINGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***NHQ**/
        select hh.EMPLOYEECODE,hh.loginaccount,'NHQGame' gametype,ifnull(sum(hh.Betting_Credits),0) game_betting_amount, ifnull(sum(hh.Winning_Credits),0) lose_win_amount, ifnull(sum(hh.Wash_Code_Credits),0), ifnull(sum(hh.Wash_Code_Credits * bb.bonus),0) bonus
        from betting_hq_nhq hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.Betting_Date >= in_begindate and hh.Betting_Date <= in_enddate) and bb.gametype='NHQGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***OG_AG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'AGGame' gametype,ifnull(sum(hh.aoi_Betting_Amount),0) game_betting_amount, ifnull(sum(hh.aoi_Win_Lose_Amount),0) lose_win_amount, ifnull(sum(hh.aoi_Valid_Amount),0), ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) bonus
        from betting_hq_og_ag hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='AGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***OG_IBC**/
        select hh.EMPLOYEECODE,hh.loginaccount,'IBCGame' gametype,ifnull(sum(hh.ibc_tzmoney),0) game_betting_amount, ifnull(sum(if(hh.ibc_lose>0, 0-hh.ibc_lose, hh.ibc_win)),0) lose_win_amount, ifnull(sum(hh.ibc_tzmoney),0), ifnull(sum(hh.ibc_tzmoney * bb.bonus),0) bonus
        from betting_hq_og_ibc hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.ibc_balltime >= in_begindate and hh.ibc_balltime <= in_enddate) and bb.gametype='IBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***OG_OG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'OGGame' gametype,ifnull(sum(hh.aoi_Betting_Amount),0) game_betting_amount, ifnull(sum(hh.aoi_Win_Lose_Amount),0) lose_win_amount, ifnull(sum(hh.aoi_Valid_Amount),0), ifnull(sum(hh.aoi_Valid_Amount * bb.bonus),0) bonus
        from betting_hq_og_og hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.aoi_Add_Time >= in_begindate and hh.aoi_Add_Time <= in_enddate) and bb.gametype='OGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***PT**/
        select hh.EMPLOYEECODE,hh.loginaccount,'PTGame' gametype,ifnull(sum(hh.pt_bet),0) game_betting_amount, ifnull(sum(hh.pt_win),0) lose_win_amount, ifnull(sum(hh.pt_bet),0), ifnull(sum(hh.pt_bet * bb.bonus),0) bonus
        from betting_hq_pt hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='PTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***XCP**/
        select hh.EMPLOYEECODE,hh.loginaccount,'XCPGame' gametype,ifnull(sum(hh.xcp_totalprice),0) game_betting_amount, ifnull(sum(hh.xcp_prize-xcp_totalprice),0) lose_win_amount, ifnull(sum(hh.xcp_totalprice),0), ifnull(sum(hh.xcp_totalprice * bb.bonus),0) bonus
        from betting_hq_xcp hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.xcp_writetime >= in_begindate and hh.xcp_writetime <= in_enddate) and bb.gametype='XCPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***KR_AV**/
        select hh.EMPLOYEECODE,hh.loginaccount,'AVGame' gametype,ifnull(sum(hh.amount),0) game_betting_amount, ifnull(sum(hh.amount2),0) lose_win_amount, ifnull(sum(hh.amount),0), ifnull(sum(hh.amount * bb.bonus),0) bonus
        from betting_kr_av hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='AVGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***SA**/
        select hh.EMPLOYEECODE,hh.loginaccount,'SAGame' gametype,ifnull(sum(hh.betamount),0) game_betting_amount, ifnull(sum(hh.resultamount),0) lose_win_amount, ifnull(sum(hh.validbet),0), ifnull(sum(hh.validbet * bb.bonus),0) bonus
        from betting_sa hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SAGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***YG_AG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'YAGGame' gametype,ifnull(sum(hh.money),0) game_betting_amount, ifnull(sum(hh.result),0) lose_win_amount, ifnull(sum(hh.valid_money),0), ifnull(sum(hh.valid_money * bb.bonus),0) bonus
        from betting_yg_ag hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.time >= in_begindate and hh.time <= in_enddate) and bb.gametype='YAGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***ZJ**/
        select hh.EMPLOYEECODE,hh.loginaccount,'ZJGame' gametype,ifnull(sum(hh.stakeamount),0) game_betting_amount, ifnull(sum(hh.winloss),0) lose_win_amount, ifnull(sum(hh.validstake),0), ifnull(sum(hh.validstake * bb.bonus),0) bonus
        from betting_zj hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='ZJGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        
        /***TTG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'TTGGame' gametype,ifnull(sum(hh.amount),0)     game_betting_amount, ifnull(sum(hh.net_money),0) lose_win_amount, ifnull(sum(hh.amount),0),   ifnull(sum(hh.amount * bb.bonus),0) bonus
        from betting_ttg hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TTGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        
        UNION ALL 
        /***MG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'MGGame' gametype,ifnull(sum(hh.mg_amount),0)     game_betting_amount, ifnull(sum(hh.net_money),0) lose_win_amount, ifnull(sum(hh.mg_amount),0),   ifnull(sum(hh.mg_amount * bb.bonus),0) bonus
        from betting_mg hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='MGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***PNG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'PNGGame' gametype,ifnull(sum(hh.bet_Amount),0) game_betting_amount, ifnull(sum(hh.net_Amount),0), ifnull(sum(hh.valid_Bet_Amount),0) lose_win_amount, ifnull(sum(hh.valid_Bet_Amount * bb.bonus),0) bonus
        from betting_hq_png hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bet_Time >= in_begindate and hh.bet_Time <= in_enddate) and bb.gametype='PNGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***QP**/
        select hh.EMPLOYEECODE,hh.loginaccount,'QPGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_qp hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***GG**/
        select hh.EMPLOYEECODE,hh.loginaccount,'GGGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_gg hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='GGGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***SGS**/
        select hh.EMPLOYEECODE,hh.loginaccount,'SGSGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_sgs hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='SGSGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        
        UNION ALL 
        /***M88**/
        select hh.EMPLOYEECODE,hh.loginaccount,'M88Game' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_m88 hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='M88Game' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***haba**/
        select hh.EMPLOYEECODE,hh.loginaccount,'HABGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_hab hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='HABGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        
        UNION ALL 
        /***WIN88**/
        select hh.EMPLOYEECODE,hh.loginaccount,'W88Game' gametype,ifnull(sum(hh.pt_bet),0) game_betting_amount, ifnull(sum(hh.pt_win-hh.pt_bet),0) lose_win_amount, ifnull(sum(hh.pt_bet),0), ifnull(sum(hh.pt_bet * bb.bonus),0) bonus
        from betting_win88 hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.pt_gamedate >= in_begindate and hh.pt_gamedate <= in_enddate) and bb.gametype='W88Game' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        UNION ALL 
        /***TGP**/
        select hh.EMPLOYEECODE,hh.loginaccount,'TGPGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_tgp hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='TGPGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***QT**/
        select hh.EMPLOYEECODE,hh.loginaccount,'QTGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_qt hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='QTGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***GB**/
        select hh.EMPLOYEECODE,hh.loginaccount,'GBGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_gb hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='GBGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***EBET**/
        select hh.EMPLOYEECODE,hh.loginaccount,'EBETGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.validbet),0) lose_win_amount, ifnull(sum(hh.validbet * bb.bonus),0) bonus
        from betting_ebet hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='EBETGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
        UNION ALL 
        /***EIBC**/
        select hh.EMPLOYEECODE,hh.loginaccount,'eIBCGame' gametype,ifnull(sum(hh.betmoney),0) game_betting_amount, ifnull(sum(hh.netmoney),0), ifnull(sum(hh.betmoney),0) lose_win_amount, ifnull(sum(hh.betmoney * bb.bonus),0) bonus
        from betting_eibc hh inner join employee_gamecataloy_bonus bb on hh.EMPLOYEECODE in(
                select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
        ) and (hh.bettime >= in_begindate and hh.bettime <= in_enddate) and bb.gametype='eIBCGame' and bb.categorytype=hh.gamebigtype and hh.employeecode=bb.employeecode group by EMPLOYEECODE
        
) 
as a ORDER BY a.EMPLOYEECODE DESC LIMIT var_start,var_limit;          
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_report_by_member_employeecode_page_new
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_report_by_member_employeecode_page_new`;
DELIMITER ;;
CREATE PROCEDURE `usp_report_by_member_employeecode_page_new`(IN in_parentemployeecode CHAR(8), IN in_begindate DATETIME, IN in_enddate DATETIME,IN `var_start` INTEGER,IN `var_limit` INTEGER,OUT `but_money` decimal(14,2),OUT `net_money` decimal(14,2),OUT `valid_money` decimal(14,2),OUT `rebates_cash` decimal(14,2), OUT `countMember` INTEGER)
    READS SQL DATA
BEGIN
  
  
  /****************根据代理编码查询直线会员的按平台分类汇总数据，有分页******************/

SELECT sum(s1),sum(s2),sum(s3),sum(s4),count(1) into but_money,net_money,valid_money,rebates_cash,countMember FROM(
        
        /********统计汇总表的数据*****/
        select ifnull(sum(hh.Bet_Money),0) s1, ifnull(sum(hh.Net_Money),0) s2, ifnull(sum(hh.Valid_Money),0) s3, ifnull(sum(bb.moneychangeamount ),0)  s4
	from betting_all_day hh inner join employee_money_change bb on  hh.employeecode in (
  		select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
	)  and (date(hh.Bet_Day) >= date(in_begindate) and date(hh.Bet_Day) <= date(in_enddate)) 
	and bb.moneychangetypecode in('B1FF4C4ADC9C423C8D0344DDFD7DC279','6B06E77AA855454EB5ADF60B6CC37787','56F82CC5B5DC4FFABBFD62F82CACA891') 
	and (bb.moneychangedate >= date(in_begindate) and bb.moneychangedate <= date(in_enddate)) 
	and hh.employeecode=bb.employeecode 	
        
) 
as b;


SELECT a.* FROM(
        /********统计汇总表的数据*****/        
	select hh.EMPLOYEECODE,hh.User_Name,hh.Game_Platform,ifnull(sum(hh.Bet_Money),0) game_betting_amount, ifnull(sum(hh.Net_Money),0) lose_win_amount, ifnull(sum(hh.Valid_Money),0), ifnull(sum(bb.moneychangeamount),0) bonus
	from betting_all_day hh inner join employee_money_change bb on  hh.employeecode in (
  		select EMPLOYEECODE from enterprise_employee where PARENTEMPLOYEECODE = in_parentemployeecode and datastatus=1 and employeetypecode='T003'
	)  and (date(hh.Bet_Day) >= date(in_begindate) and date(hh.Bet_Day) <= date(in_enddate)) 
	and bb.moneychangetypecode in('B1FF4C4ADC9C423C8D0344DDFD7DC279','6B06E77AA855454EB5ADF60B6CC37787','56F82CC5B5DC4FFABBFD62F82CACA891') 
	and (bb.moneychangedate >= date(in_begindate) and bb.moneychangedate <= date(in_enddate)) 
	and hh.employeecode=bb.employeecode 
        
        
        
) 
as a ORDER BY a.EMPLOYEECODE DESC LIMIT var_start,var_limit;  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_activity_raffle_signin
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_activity_raffle_signin`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_activity_raffle_signin`(IN in_employeecode varchar(8),IN in_enterpriseactivitycode int)
this_pro_lable:BEGIN
    -- 签到与抽奖次数映射关系
    DECLARE signin_faffle_mapping varchar(200) DEFAULT ''; -- '3:1,5:2,8:3,12:4,18:5';
    -- 映射组合数量
    DECLARE mapping_count SMALLINT DEFAULT 0;
    -- 单个映射单元
    DECLARE mapping_unit varchar(50);
    -- 映射关系循环变量
    DECLARE loop_variable smallint DEFAULT 1;
    -- 返回抽奖次数
    DECLARE rt_raffle_time smallint DEFAULT 0;
    
    
    -- 如果今天已签到提示签到成功
    SELECT COUNT(1) INTO @todaysign FROM activity_raffle_signin ars 
      WHERE ars.employeecode=in_employeecode AND ars.signintime >= CURDATE() AND ars.signintime<ADDDATE(CURDATE(),INTERVAL  1 DAY);
    IF(@todaysign>=1) THEN 
      SELECT 0 AS result;
      LEAVE this_pro_lable; 
    END IF;

  
    -- 查询用户今天的充值
    SELECT sum(dwo.orderamount),ufn_tn_person_betting(in_employeecode, CURDATE(), ADDDATE(CURDATE(),INTERVAL  1 DAY)) 
      INTO @savingmoney,@betting 
      FROM deposit_withdraw_order dwo 
      WHERE dwo.orderstatus=2 AND dwo.ordertype=1 AND dwo.employeecode=in_employeecode AND dwo.orderdate >= CURDATE() AND dwo.orderdate<ADDDATE(CURDATE(),INTERVAL  1 DAY);
     
    -- 是否满足签到条件
    IF @savingmoney>=50 AND @betting>=50 THEN 
      -- 签到
      INSERT INTO activity_raffle_signin(employeecode, signintime, depositamount, betamount ) VALUE(in_employeecode,NOW(),@savingmoney,@betting);
      
      -- 判断赠送抽奖次数
      SELECT eacs.agementvalue INTO signin_faffle_mapping FROM enterprise_brand_activity eba  
        LEFT JOIN enterprise_activity_customization_setting eacs ON eba.ecactivitycode = eacs.ecactivitycode 
        LEFT JOIN activity_template_setting ats ON eacs.activitysettingcode = ats.activitysettingcode
        WHERE eba.enterprisebrandactivitycode=in_enterpriseactivitycode AND ats.agementname='QDCJYS';
      -- 映射组合数量
      SET mapping_count := tool_jsonf_totallength(signin_faffle_mapping,',');
      -- 跳出循环标记
      while_lable:BEGIN
      WHILE loop_variable <= mapping_count DO 
        -- 循环顺序获取映射关系
        SET mapping_unit:= tool_jsonf_split(signin_faffle_mapping,',',loop_variable);
        -- 签到天数
        SET @signin_day := CAST(tool_jsonf_split(mapping_unit,':',1) AS decimal);
        -- 抽奖次数
        SET @raffle_time := CAST(tool_jsonf_split(mapping_unit,':',2) AS decimal);
        -- 映射时间内签到天数
        SELECT COUNT(1) INTO @sign_count  FROM activity_raffle_signin WHERE signintime BETWEEN ADDDATE(CURDATE(),INTERVAL  -(@signin_day-1) DAY) AND NOW() AND isexpires = 1;
        -- 赠送抽奖次数
        IF @sign_count < @signin_day THEN 
          LEAVE while_lable;
        ELSEIF @sign_count >= @signin_day THEN
          REPLACE INTO activity_raffle_control(employeecode, parentemployeecode, loginaccount, availabletimes,donatedate) SELECT ee.employeecode, ee.parentemployeecode, ee.loginaccount,@raffle_time,NOW() FROM enterprise_employee ee WHERE ee.employeecode=in_employeecode;
          SET rt_raffle_time := @raffle_time;
          IF loop_variable=mapping_count THEN 
            UPDATE activity_raffle_signin ars SET isexpires=0 WHERE ars.employeecode=in_employeecode;
          END IF;
        END IF  ;
        -- 循环变量递增
        SET loop_variable := loop_variable+1;
      END WHILE; 
      end while_lable;

      -- 返回赠送的抽奖次数
      IF rt_raffle_time > 0 THEN 
        SELECT rt_raffle_time AS result;
      ELSE
        SELECT 0 AS result;
      END IF ;
      
    ELSE 
    -- 签到失败
      SELECT -1 AS result;
    END IF;
   
  END this_pro_lable
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_enterprise_report_dates
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_enterprise_report_dates`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_enterprise_report_dates`(IN in_enterprisecode CHAR(6), IN in_reportdate int,OUT memberRegeditCount int,OUT agentRegeditCount int,OUT loginCount int,OUT todayDepositCount int,OUT todayTakeCount int,OUT todayBetmoney double,OUT todayNetmoney double,OUT todayVaildmoney double,OUT firstDepositCount int,OUT firstDepositMoney double,OUT secondDepositCount int,OUT secondDepositMoney double,OUT noSecondCount int,OUT noThreeCount int)
BEGIN

  
  
  ##返回投注的数据
  select sum(betmoney) ,sum(netmoney) ,sum(validbet) INTO todayBetmoney,todayNetmoney,todayVaildmoney  from betting_all_game_winlose_detail where 
  enterprisecode = in_enterprisecode and date(bettime)=in_reportdate ;
  
  ##返回存款和取款的人数（非人次）
  select count(1) INTO todayDepositCount from (
  select employeecode from deposit_withdraw_order where 
  orderstatus=2 and ordertype=1 and enterprisecode = in_enterprisecode and date(orderdate)=in_reportdate group by employeecode
  ) a;
  select count(1) INTO todayTakeCount from (
  select employeecode from deposit_withdraw_order where 
  orderstatus=2 and ordertype=2 and enterprisecode = in_enterprisecode and date(orderdate)=in_reportdate group by employeecode
  ) b;
  
  
  ##返回新注册人数
  select count(1) INTO memberRegeditCount from enterprise_employee where 
  enterprisecode = in_enterprisecode and date(logindatetime)=in_reportdate and employeetypecode='T003';  
  select count(1) INTO agentRegeditCount from enterprise_employee where 
  enterprisecode = in_enterprisecode and date(logindatetime)=in_reportdate and employeetypecode in('T004','T005');
  
  ##返回登录人数
  select count(1) INTO loginCount from (
  select count(1) from log_login where 
  enterprisecode = in_enterprisecode and date(logintime)=in_reportdate group by employeecode
  ) a;
  
  ############返回首存二存三存问题############
  ##返回首存金额和人数##
  select count(1),sum(orderamount) INTO firstDepositCount,firstDepositMoney from deposit_withdraw_order where employeecode in(
        select employeecode from deposit_withdraw_order where orderstatus=2 and ordertype=1 and enterprisecode = in_enterprisecode group by employeecode having count(1)=1
  ) and orderstatus=2 and ordertype=1 and enterprisecode = in_enterprisecode and date(orderdate)=in_reportdate;
  
  ##返回二存金额和人数##
  select count(1),sum(orderamount) INTO secondDepositCount,secondDepositMoney from deposit_withdraw_order where employeecode in(
        select employeecode from deposit_withdraw_order where orderstatus=2 and ordertype=1 and enterprisecode = in_enterprisecode and date(orderdate)=in_reportdate group by employeecode having count(1)=2
  ) and orderstatus=2 and ordertype=1 and enterprisecode = in_enterprisecode and date(orderdate)=in_reportdate;
  
  select 0,0 INTO noSecondCount,noThreeCount;
  
  ##返回金额和人次列表清单
  select moneychangetypecode,sum(moneychangeamount) as moneychangeamount,count(1) as moneychangecount from employee_money_change where 
  enterprisecode = in_enterprisecode and date(moneychangedate)=in_reportdate  group by moneychangetypecode;
  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_list_team_performance_for_brand
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_list_team_performance_for_brand`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_list_team_performance_for_brand`(IN in_enterprisereport boolean,IN in_enterprisecode char(6),IN in_employeecode char(8),IN in_begintime datetime, IN in_endtime datetime)
BEGIN
  DECLARE l_like_employeeallupleves VARCHAR(1000);
  DECLARE l_employeeallupleves_len  INT;
  DECLARE l_teamemployeecode CHAR(8);
     
  IF in_enterprisereport THEN
     SELECT ufn_up_recursion_shareholder_of_user(in_employeecode) INTO l_teamemployeecode;
  ELSE
     SET l_teamemployeecode := in_employeecode;
  END IF;
  
  SELECT CONCAT(employeeallupleves,'%'),LENGTH(employeeallupleves)+1 INTO l_like_employeeallupleves,l_employeeallupleves_len
    FROM enterprise_employee_all_uplevels
   WHERE employeecode = l_teamemployeecode;
 
  SELECT osquery.totalnetmoney AS netmoney, eob.brandcode, eob.brandname
    FROM enterprise_operating_brand eob
         INNER JOIN ( SELECT squery.brandcode, sum(ba.netmoney) AS totalnetmoney
                        FROM betting_all_game_winlose_detail ba
                             INNER JOIN ( SELECT employeecode,
                      						     brandcode
                      					    FROM enterprise_employee_all_uplevels
                      					   WHERE employeeallupleves LIKE l_like_employeeallupleves and employeetypecode = 'T003' ) squery ON squery.employeecode = ba.employeecode
                       WHERE ba.bettime >= in_begintime
                         AND ba.bettime <= in_endtime
                      GROUP BY squery.brandcode ) osquery ON osquery.brandcode = eob.brandcode
   ORDER BY totalnetmoney;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_list_team_useractivity
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_list_team_useractivity`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_list_team_useractivity`(IN in_enterprisereport boolean,IN in_employeecode char(8))
BEGIN
  # 列表 - 用户活跃度
  
  # 根据报表类型获取团队编码
  IF in_enterprisereport  THEN 
    SELECT ufn_up_recursion_shareholder_of_user(in_employeecode) INTO in_employeecode;
  END IF;

  SET @all_subordinates := ufn_down_recursion_team_of_agent(in_employeecode);
  
SELECT atu.activearea,atu.ucount
 FROM ( SELECT COUNT(ee.loginaccount) ucount,
               CASE 
                 WHEN ee.lastlogintime >= DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 7 DAY) AND ee.lastlogintime < DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 DAY) THEN 3
                 WHEN ee.lastlogintime >= DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 15 DAY) AND ee.lastlogintime < DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 7 DAY) THEN 7
                 WHEN ee.lastlogintime >= DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 30 DAY) AND ee.lastlogintime < DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 15 DAY) THEN 15
                 WHEN ee.lastlogintime > DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 90 DAY) AND ee.lastlogintime < DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 30 DAY) THEN 30
                 WHEN ee.lastlogintime < DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 90 DAY) THEN 90
                 ELSE 0
               END AS activearea
	      FROM enterprise_employee ee
		 WHERE ee.employeecode = in_employeecode OR FIND_IN_SET (ee.parentemployeecode , @all_subordinates)
	  GROUP BY activearea) AS atu
ORDER BY activearea ASC;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_all_total_money
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_all_total_money`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_all_total_money`(IN in_employeecode CHAR(8), IN start_time DATETIME, IN end_time DATETIME)
BEGIN
  
  DECLARE total_deposit_money DECIMAL(16,2);
  DECLARE total_take_money DECIMAL(16,2);
  DECLARE total_activity_money DECIMAL(16,2);
  DECLARE total_bet_money DECIMAL(16,2);
  DECLARE total_net_money DECIMAL(16,2);
  DECLARE total_stream_money DECIMAL(16,2);
  
  /********总的存款和取款********/
  select 
        SUM(CASE  WHEN s.ordertype=1 THEN s.orderamount ELSE 0 END ) AS savemoney , 
        SUM(CASE  WHEN s.ordertype=2 THEN s.orderamount ELSE 0 END ) AS getmoney 
        INTO total_deposit_money,total_take_money
        from deposit_withdraw_order s where s.orderstatus=2 and s.employeecode = in_employeecode 
        and ( IF (start_time is NULL, 0 = 0, orderdate>start_time) and IF (end_time is NULL, 0 = 0, orderdate<end_time));
        
        
  /****************所需打码流水****************/
  select sum(mustbet) INTO total_stream_money from activity_bet_record 
  where employeecode=in_employeecode 
        and ( IF (start_time is NULL, 0 = 0, createtime>start_time) and IF (end_time is NULL, 0 = 0, createtime<end_time));
        
  
   /****************游戏投注额和输赢总额****************/
  select sum(validbet) as validbet,sum(netmoney) as netmoney
  INTO total_bet_money,total_net_money
  from betting_all_game_winlose_detail 
  where employeecode=in_employeecode 
        and ( IF (start_time is NULL, 0 = 0, bettime>start_time) and IF (end_time is NULL, 0 = 0, bettime<end_time));
        
  
  /****************优惠总额****************/
  select sum(moneychangeamount) INTO total_activity_money
  from employee_money_change 
  where moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811' and employeecode=in_employeecode 
        and ( IF (start_time is NULL, 0 = 0, moneychangedate>start_time) and IF (end_time is NULL, 0 = 0, moneychangedate<end_time));
        
  /**返回数据***/
  select
  IF(total_deposit_money IS NULL ,0,total_deposit_money) total_deposit_money,
  IF(total_take_money IS NULL ,0,total_take_money) total_take_money,
  IF(total_activity_money IS NULL ,0,total_activity_money) total_activity_money,
  IF(total_bet_money IS NULL ,0,total_bet_money) total_bet_money,
  IF(total_net_money IS NULL ,0,total_net_money) total_net_money,
  IF(total_stream_money IS NULL ,0,total_stream_money) total_stream_money
  ;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_employee_allinfo_game
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_employee_allinfo_game`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_employee_allinfo_game`(IN in_employeecode CHAR(8),OUT pmoney_acvity decimal(16,2),OUT pmoney_daily decimal(16,2),OUT pmoney_up_fail decimal(16,2),OUT pmoney_add decimal(16,2),OUT pmoney_sub decimal(16,2))
BEGIN
  /****************优惠时间，优惠名称，优惠金额****************/
  select 
  sum(moneychangeamount) INTO pmoney_acvity
  from employee_money_change 
  where moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811' and employeecode=in_employeecode  ;
  
  /****************洗码数据****************/
  select 
  sum(moneychangeamount) INTO pmoney_daily
  from employee_money_change 
  where moneychangetypecode in('B1FF4C4ADC9C423C8D0344DDFD7DC279','6B06E77AA855454EB5ADF60B6CC37787','56F82CC5B5DC4FFABBFD62F82CACA891') and employeecode=in_employeecode  ;
  
  /****************上分失败返还****************/
  select 
  sum(moneychangeamount) INTO pmoney_up_fail
  from employee_money_change 
  where moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811' and employeecode=in_employeecode  ;
  
  /****************冲正****************/
  select 
  sum(moneychangeamount) INTO pmoney_add
  from employee_money_change 
  where moneychangetypecode='1A53AEC4179E4E46AEE7EE752C16E3B1' and employeecode=in_employeecode  ;
  
  /****************冲负****************/
  select 
  sum(moneychangeamount) INTO pmoney_sub
  from employee_money_change 
  where moneychangetypecode='831F252CEAE94DD0A740260EE151A437' and employeecode=in_employeecode  ;
  
  
    
    /****************游戏时间、游戏平台、累计金额****************/
  select max(bettime) as ptime,
  concat(platformtype,(CASE  WHEN gamebigtype='DZ' THEN '电子' WHEN gamebigtype='SX' THEN '真人' WHEN gamebigtype='TY' THEN '体育' WHEN gamebigtype='CP' THEN '彩票' WHEN gamebigtype='QP' THEN '棋牌' ELSE '其他' END)) as pcontent,
  sum(validbet) as pmoney,
  'GAME' as ptype 
  from betting_all_game_winlose_detail 
  where employeecode=in_employeecode group by platformtype,gamebigtype;  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_employee_allinfo_order
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_employee_allinfo_order`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_employee_allinfo_order`(IN in_employeecode CHAR(8),OUT last_deposit_time timestamp,OUT last_deposit_money decimal(16,2),
OUT last_deposit_ordernumber varchar(50),OUT first_deposit_time timestamp,OUT first_deposit_money decimal(16,2),OUT first_deposit_ordernumber varchar(50),
OUT all_deposit_money decimal(16,2),OUT all_deposit_count int,OUT last_take_time timestamp,OUT last_take_money decimal(16,2),OUT last_take_ordernumber varchar(50),
OUT first_take_time timestamp,OUT first_take_money decimal(16,2),OUT first_take_ordernumber varchar(50),OUT all_take_money decimal(16,2),OUT all_take_count int)
BEGIN
  /****************最近存款时间、操作类型，存款金额****************/
  select orderdate,orderamount,ordernumber INTO last_deposit_time,last_deposit_money,last_deposit_ordernumber from deposit_withdraw_order where ordertype=1 and orderstatus=2 and employeecode=in_employeecode order by orderdate desc limit 0,1;
    
  /****************查首存时间****************/
  select orderdate,orderamount,ordernumber INTO first_deposit_time,first_deposit_money,first_deposit_ordernumber from deposit_withdraw_order where ordertype=1 and orderstatus=2 and employeecode=in_employeecode order by orderdate asc limit 0,1;

  /****************存款金额，存款笔数****************/
  select sum(orderamount),sum(1) sm INTO all_deposit_money,all_deposit_count from deposit_withdraw_order where ordertype=1 and orderstatus=2 and employeecode=in_employeecode ;
  
  
  /****************最近取款时间、操作类型，取款金额****************/
  select orderdate,orderamount,ordernumber INTO last_take_time,last_take_money,last_take_ordernumber from deposit_withdraw_order where ordertype=2 and orderstatus=2 and employeecode=in_employeecode order by orderdate desc limit 0,1;
  
  /****************首次取款时间、操作类型，取款金额****************/
  select orderdate,orderamount,ordernumber INTO first_take_time,first_take_money,first_take_ordernumber from deposit_withdraw_order where ordertype=2 and orderstatus=2 and employeecode=in_employeecode order by orderdate asc limit 0,1;
  
  /****************存款金额，存款笔数****************/
  select sum(orderamount),sum(1) sm INTO all_take_money,all_take_count from deposit_withdraw_order where ordertype=2 and orderstatus=2 and employeecode=in_employeecode ;
 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_takemoney_inspect
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_takemoney_inspect`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_takemoney_inspect`(IN in_ordernumber varchar(32))
BEGIN
  # -- 取款稽查  jason20170420:此过程不再使用，不再维护

  -- 用户编码
  DECLARE a_employeecode char(8);
  -- 用户名
  DECLARE a_loginaccout varchar(20);
  -- 上一次存款时间
  DECLARE a_lastsavedate datetime;
  -- 上一次存款金额
  DECLARE a_lastsaveamount decimal(16,2);
  -- 存款后账户余额
  DECLARE a_aftersavebalance decimal(16,2);
  -- 本次取款时间
  DECLARE a_takemoneydate datetime;
  -- 本次取款金额
  DECLARE a_takeamount decimal(16,2);
  -- 本次存款后余额
  DECLARE a_aftertakebalance decimal(16,2);
  -- 稽查周期内帐变金额
  DECLARE a_moneychangeamout decimal(16,2);
  -- TAG游戏输赢 
  DECLARE a_taglosewin decimal(16,2);
  -- BBIN游戏输赢 
  DECLARE a_bbinlosewin decimal(16,2);
  -- NHQ游戏输赢 
  DECLARE a_nhqlosewin decimal(16,2);
  -- OAG游戏输赢 
  DECLARE a_oaglosewin decimal(16,2);
  -- IBC游戏输赢 
  DECLARE a_ibclosewin decimal(16,2);
  -- OG游戏输赢 
  DECLARE a_oglosewin decimal(16,2);
  -- PT游戏输赢 
  DECLARE a_ptlosewin decimal(16,2);
  -- XCP游戏输赢 
  DECLARE a_xcplosewin decimal(16,2);


      
  -- 游戏输赢
  DECLARE a_gamelosewin decimal(16,2);
  -- 存款后上分时间
  DECLARE a_aftersaveupidate datetime;
  -- 存款后上分金额
  DECLARE a_afterupiamount decimal(16,2);
  -- 存款上分后账户余额
  DECLARE a_afterupibalance decimal(16,2);
  
  /*********zj sa av ttg*********/   
  DECLARE a_zjlosewin decimal(16,2);
  DECLARE a_salosewin decimal(16,2);
  DECLARE a_avlosewin decimal(16,2);
  DECLARE a_ttglosewin decimal(16,2);
  DECLARE a_mglosewin decimal(16,2);
  DECLARE a_pnglosewin decimal(16,2);
  DECLARE a_qplosewin decimal(16,2);
  DECLARE a_gglosewin decimal(16,2);
  DECLARE a_sgslosewin decimal(16,2);
  DECLARE a_idnlosewin decimal(16,2);
  DECLARE a_m88losewin decimal(16,2);
  DECLARE a_hablosewin decimal(16,2);  

  #当前订单的取款时间，取款金额，取款后账户余额
  SELECT emc.employeecode,emc.moneychangedate,emc.moneychangeamount,(emc.settlementamount+emc.moneychangeamount) 
    INTO a_employeecode,a_takemoneydate,a_takeamount,a_aftertakebalance 
    FROM employee_money_change emc 
    WHERE emc.ordernumber=in_ordernumber and emc.moneychangetypecode='2BC2CB7FDD7B4720906B56812E075F94';

  SELECT ee.loginaccount INTO a_loginaccout FROM enterprise_employee ee WHERE ee.employeecode = a_employeecode;

  #当前订单的上一次存款时间，存款金额，存款后账户余额
  SELECT emc.moneychangedate,emc.moneychangeamount,(emc.settlementamount+emc.moneychangeamount) 
    INTO a_lastsavedate,a_lastsaveamount,a_aftersavebalance 
    FROM employee_money_change emc 
    WHERE emc.moneychangetypecode='8D37FD20D92043FA8D856590F0DFED0F' AND emc.employeecode=a_employeecode AND emc.moneychangedate<=a_takemoneydate 
    ORDER BY emc.moneychangedate DESC LIMIT 0,1;

  #充值上分后的上分金额，账户余额
  SELECT emc.moneychangedate,emc.moneychangeamount,(emc.settlementamount+emc.moneychangeamount) 
    INTO a_aftersaveupidate,a_afterupiamount,a_afterupibalance 
    FROM employee_money_change emc 
    WHERE emc.moneychangetypecode='AF0B2F04CCA64E3197F047402FEE5832' AND emc.employeecode=a_employeecode AND emc.moneychangedate>=a_lastsavedate AND emc.moneychangedate<=a_takemoneydate 
    ORDER BY emc.moneychangedate ASC LIMIT 0,1;
  
  

  #上一次存款到这一次取款之间的帐变金额
  SELECT SUM(emc.moneychangeamount) INTO a_moneychangeamout 
    FROM employee_money_change emc 
    WHERE emc.employeecode=a_employeecode AND emc.moneychangedate>=a_lastsavedate AND emc.moneychangedate<=a_takemoneydate;

  IF a_aftersaveupidate IS NOT NULL THEN
    
  #TAG游戏输赢 
  SELECT SUM(bha.net_Amount) INTO a_taglosewin  
    FROM betting_hq_ag bha 
    WHERE bha.employeecode=a_employeecode AND bha.bet_Time >= a_aftersaveupidate AND bha.bet_Time <=a_takemoneydate;
  
  #BBIN游戏输赢
  SELECT SUM(bhb.bbin_Payoff) INTO a_bbinlosewin 
    FROM betting_hq_bbin bhb 
    WHERE bhb.employeecode=a_employeecode AND bhb.bbin_Wagers_Date >= a_aftersaveupidate AND bhb.bbin_Wagers_Date <=a_takemoneydate;
  
  #新环球游戏输赢
  SELECT SUM(bhq.Winning_Credits) INTO a_nhqlosewin 
    FROM betting_hq_nhq bhq 
    WHERE bhq.employeecode=a_employeecode AND bhq.Betting_Date  >= a_aftersaveupidate AND bhq.Betting_Date<=a_takemoneydate;

  #OAG游戏输赢
  SELECT SUM(bhoa.aoi_Win_Lose_Amount) INTO a_oaglosewin 
    FROM  betting_hq_og_ag bhoa
    WHERE bhoa.employeecode=a_employeecode AND bhoa.aoi_Add_Time  >= a_aftersaveupidate AND bhoa.aoi_Add_Time <=a_takemoneydate;

  #IBC游戏输赢
  SELECT SUM((bhoi.ibc_win - bhoi.ibc_lose)) INTO a_ibclosewin  
    FROM  betting_hq_og_ibc bhoi
    WHERE bhoi.employeecode=a_employeecode AND bhoi.ibc_updatetime  >= a_aftersaveupidate AND bhoi.ibc_updatetime <=a_takemoneydate;

  #OG游戏输赢
  SELECT SUM(bhoo.aoi_Win_Lose_Amount) INTO a_oglosewin 
    FROM  betting_hq_og_og bhoo
    WHERE bhoo.employeecode=a_employeecode AND bhoo.aoi_Add_Time >= a_aftersaveupidate AND bhoo.aoi_Add_Time <=a_takemoneydate;

  #PT游戏输赢
  SELECT SUM((bhp.pt_win-bhp.pt_bet)) INTO a_ptlosewin 
    FROM  betting_hq_pt bhp
    WHERE bhp.employeecode=a_employeecode AND bhp.pt_gamedate >= a_aftersaveupidate  AND bhp.pt_gamedate <=a_takemoneydate; 

  #祥瑞彩游戏输赢
  SELECT SUM(bhx.xcp_bonus) INTO a_xcplosewin 
    FROM  betting_hq_xcp bhx
    WHERE bhx.employeecode=a_employeecode AND bhx.xcp_writetime >= a_aftersaveupidate  AND bhx.xcp_writetime <=a_takemoneydate; 
  
  /*********zj sa av ttg*********/   
  SELECT SUM(zj.winloss) INTO a_zjlosewin 
    FROM  betting_zj zj
    WHERE zj.employeecode=a_employeecode AND zj.bettime >= a_aftersaveupidate  AND zj.bettime <=a_takemoneydate; 
    
  SELECT SUM(sa.resultamount) INTO a_salosewin 
    FROM  betting_sa sa
    WHERE sa.employeecode=a_employeecode AND sa.bettime >= a_aftersaveupidate  AND sa.bettime <=a_takemoneydate;   
  
  SELECT SUM(av.amount2) INTO a_avlosewin 
    FROM  betting_kr_av av
    WHERE av.employeecode=a_employeecode AND av.time >= a_aftersaveupidate  AND av.time <=a_takemoneydate;     
  
  SELECT SUM(ttg.net_money) INTO a_ttglosewin 
    FROM  betting_ttg ttg
    WHERE ttg.employeecode=a_employeecode AND ttg.bettime >= a_aftersaveupidate  AND ttg.bettime <=a_takemoneydate;   
  
  SELECT SUM(mg.net_money) INTO a_mglosewin 
    FROM  betting_mg mg
    WHERE mg.employeecode=a_employeecode AND mg.bettime >= a_aftersaveupidate  AND mg.bettime <=a_takemoneydate;  
  
  SELECT SUM(bha.net_Amount) INTO a_pnglosewin  
    FROM betting_hq_png bha 
    WHERE bha.employeecode=a_employeecode AND bha.bet_Time >= a_aftersaveupidate AND bha.bet_Time <=a_takemoneydate;
  
  SELECT SUM(bha.netmoney) INTO a_qplosewin  
    FROM betting_qp bha 
    WHERE bha.employeecode=a_employeecode AND bha.bettime >= a_aftersaveupidate AND bha.bettime <=a_takemoneydate;
  
  SELECT SUM(bha.netmoney) INTO a_gglosewin  
    FROM betting_gg bha 
    WHERE bha.employeecode=a_employeecode AND bha.bettime >= a_aftersaveupidate AND bha.bettime <=a_takemoneydate;  
    
  SELECT SUM(bha.netmoney) INTO a_sgslosewin  
    FROM betting_sgs bha 
    WHERE bha.employeecode=a_employeecode AND bha.bettime >= a_aftersaveupidate AND bha.bettime <=a_takemoneydate;  
    
  SELECT SUM(bha.netmoney) INTO a_idnlosewin  
    FROM betting_idn bha 
    WHERE bha.employeecode=a_employeecode AND bha.bettime >= a_aftersaveupidate AND bha.bettime <=a_takemoneydate;      
    
  SELECT SUM(bha.netmoney) INTO a_m88losewin  
    FROM betting_m88 bha 
    WHERE bha.employeecode=a_employeecode AND bha.bettime >= a_aftersaveupidate AND bha.bettime <=a_takemoneydate;   
    
    SELECT SUM(bha.netmoney) INTO a_hablosewin  
    FROM betting_hab bha 
    WHERE bha.employeecode=a_employeecode AND bha.bettime >= a_aftersaveupidate AND bha.bettime <=a_takemoneydate;      
    
      
  #游戏总输赢
  set a_gamelosewin = IF(a_taglosewin IS NULL ,0,a_taglosewin) + IF(a_bbinlosewin IS NULL ,0,a_bbinlosewin) + IF(a_nhqlosewin IS NULL ,0,a_nhqlosewin) + 
                      IF(a_oaglosewin IS NULL ,0,a_oaglosewin) + IF(a_ibclosewin IS NULL ,0,a_ibclosewin) + IF(a_oglosewin IS NULL ,0,a_oglosewin) + 
                        IF(a_ptlosewin IS NULL ,0,a_ptlosewin) + IF(a_xcplosewin IS NULL ,0,a_xcplosewin) + 
                        IF(a_zjlosewin IS NULL ,0,a_zjlosewin)+
                        IF(a_salosewin IS NULL ,0,a_salosewin)+
                        IF(a_avlosewin IS NULL ,0,a_avlosewin)+
                        IF(a_ttglosewin IS NULL ,0,a_ttglosewin)+
                        IF(a_mglosewin IS NULL ,0,a_mglosewin)+
                        IF(a_pnglosewin IS NULL ,0,a_pnglosewin)+
                        IF(a_qplosewin IS NULL ,0,a_qplosewin)+
                        IF(a_gglosewin IS NULL ,0,a_gglosewin)+
                        IF(a_sgslosewin IS NULL ,0,a_sgslosewin)+
                        IF(a_idnlosewin IS NULL ,0,a_idnlosewin)+
                        IF(a_m88losewin IS NULL ,0,a_m88losewin)+
                        IF(a_hablosewin IS NULL ,0,a_hablosewin)
                        ;
  end IF;

  SELECT 
    a_employeecode AS employeecode,
    a_loginaccout AS loginaccount,
    IF(a_lastsavedate IS NULL ,'',a_lastsavedate) AS lastsavedate,
    IF(a_lastsaveamount IS NULL,0,a_lastsaveamount) AS lastsaveamount,
    IF(a_aftersavebalance IS NULL ,0,a_aftersavebalance) AS aftersavebalance,
    IF(a_aftersaveupidate IS NULL ,'',a_aftersaveupidate) AS aftersaveupidate,
    IF(a_afterupiamount IS NULL,0,a_afterupiamount) AS afterupiamount,
    IF(a_afterupibalance IS NULL ,0,a_afterupibalance) AS afterupibalance,
    IF(a_takemoneydate IS NULL ,'',a_takemoneydate) AS takemoneydate,
    IF(a_takeamount IS NULL ,0,a_takeamount) AS takeamount,
    IF(a_aftertakebalance IS NULL ,0,a_aftertakebalance) AS aftertakebalance,
    IF(a_moneychangeamout IS NULL ,0,a_moneychangeamout) AS moneychangeamout,
    IF(a_taglosewin IS NULL ,0,a_taglosewin) AS taglosewin,
    IF(a_bbinlosewin IS NULL ,0,a_bbinlosewin) AS bbinlosewin,
    IF(a_nhqlosewin IS NULL ,0,a_nhqlosewin) AS nhqlosewin,
    IF(a_oaglosewin IS NULL ,0,a_oaglosewin) AS oaglosewin,
    IF(a_ibclosewin IS NULL ,0,a_ibclosewin) AS ibclosewin,
    IF(a_oglosewin IS NULL ,0,a_oglosewin) AS oglosewin,
    IF(a_ptlosewin IS NULL ,0,a_ptlosewin) AS ptlosewin,
    IF(a_xcplosewin IS NULL ,0,a_xcplosewin) AS xcplosewin,    
    IF(a_zjlosewin IS NULL ,0,a_zjlosewin) AS zjlosewin,
    IF(a_salosewin IS NULL ,0,a_salosewin) AS salosewin,
    IF(a_avlosewin IS NULL ,0,a_avlosewin) AS avlosewin,
    IF(a_ttglosewin IS NULL ,0,a_ttglosewin) AS ttglosewin,
    IF(a_mglosewin IS NULL ,0,a_mglosewin) AS mglosewin,
    IF(a_pnglosewin IS NULL ,0,a_pnglosewin) AS pnglosewin,
    IF(a_qplosewin IS NULL ,0,a_qplosewin) AS qplosewin,    
    IF(a_gglosewin IS NULL ,0,a_gglosewin) AS gglosewin,
    IF(a_sgslosewin IS NULL ,0,a_sgslosewin) AS sgslosewin,
    IF(a_idnlosewin IS NULL ,0,a_idnlosewin) AS idnlosewin,
    
    IF(a_m88losewin IS NULL ,0,a_m88losewin) AS m88losewin,
    IF(a_hablosewin IS NULL ,0,a_hablosewin) AS hablosewin,
    
    IF(a_gamelosewin IS NULL ,0,a_gamelosewin) AS gamelosewin
    ;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_takemoney_inspect_new
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_takemoney_inspect_new`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_takemoney_inspect_new`(IN in_ordernumber VARCHAR(50),OUT curren_money decimal(16,2),OUT last_deposit_time timestamp,
OUT last_deposit_money decimal(16,2),OUT last_deposit_balance decimal(16,2),OUT last_take_time timestamp,OUT last_take_money decimal(16,2),OUT local_take_time timestamp,
OUT local_take_money decimal(16,2),OUT need_stream_money decimal(16,2))
BEGIN
  
  DECLARE isonce int default 0;
  DECLARE in_employeecode CHAR(8);
  DECLARE last_deposit_ordernumber VARCHAR(36);
  
  DECLARE first_deposit_time timestamp;
    
  
  /****************本次取款时间、取款金额****************/      
  select orderdate,orderamount,employeecode INTO local_take_time,local_take_money,in_employeecode from deposit_withdraw_order where ordernumber=in_ordernumber ;
  
  /****************当前余额****************/     
  select balance INTO curren_money from enterprise_employee_capital_account where employeecode=in_employeecode;
  
  /****************最近存款时间、操作类型，存款金额****************/
  select orderdate,orderamount,ordernumber INTO last_deposit_time,last_deposit_money,last_deposit_ordernumber from deposit_withdraw_order where ordertype=1 and orderstatus=2 and employeecode=in_employeecode order by orderdate desc limit 0,1;
  /*******存款后余额*****/
  select (moneychangeamount + settlementamount) INTO last_deposit_balance from employee_money_change 
  where moneychangetypecode='8D37FD20D92043FA8D856590F0DFED0F' and ordernumber=last_deposit_ordernumber and employeecode=in_employeecode;
  
  
  /****************查首存时间****************/
  select orderdate INTO first_deposit_time from deposit_withdraw_order where ordertype=1 and orderstatus=2 and employeecode=in_employeecode order by orderdate asc limit 0,1;

  /****************最近取款时间、操作类型，取款金额****************/
  select orderdate,orderamount INTO last_take_time,last_take_money from deposit_withdraw_order where ordertype=2 and orderstatus=2 and employeecode=in_employeecode order by orderdate desc limit 0,1;
  
  /****************如果只有当前这一次取款，则最后一次取款时间要改为存款时间****************/
  IF local_take_time = last_take_time THEN 
  SET  last_take_time = first_deposit_time;
  SET  isonce = 1;
  END IF;
  
  IF last_take_time = null or last_take_time is null THEN 
  SET  last_take_time = first_deposit_time;
  SET  isonce = 1;
  END IF;
  
  
  /****************所需打码流水（上一次成功取款时间到本次取款时间）****************/
  select sum(mustbet) INTO need_stream_money from activity_bet_record where employeecode=in_employeecode and (createtime>=last_take_time and createtime<=local_take_time);
  
  /****************最近游戏时间、游戏平台、累计金额；（上一次成功取款时间到本次取款时间）****************/
  select max(bettime) as ptime,
  concat(platformtype,(CASE  WHEN gamebigtype='DZ' THEN '电子' WHEN gamebigtype='SX' THEN '真人' WHEN gamebigtype='TY' THEN '体育' WHEN gamebigtype='CP' THEN '彩票' WHEN gamebigtype='QP' THEN '棋牌' ELSE '其他' END)) as pcontent,
  sum(validbet) as pmoney,
  'GAME' as ptype 
  from betting_all_game_winlose_detail 
  where employeecode=in_employeecode and (bettime>last_take_time and bettime<local_take_time) group by platformtype,gamebigtype
  
  UNION ALL
  
  /****************最近优惠时间，优惠名称，优惠金额；（上一次成功取款时间到本次取款时间）****************/
  select moneychangedate as ptime,
  moneyinoutcomment as pcontent,
  moneychangeamount as pmoney,
  'ACTIVITY' as ptype
  from employee_money_change 
  where moneychangetypecode='D6B0C11A0AC44EBBB1538BE69B004811' and employeecode=in_employeecode and (moneychangedate>last_take_time and moneychangedate<local_take_time) 
  
  UNION ALL
  
  /****************最近上分时间，上分名称，上分金额；（上一次成功取款时间到本次取款时间）****************/
  select moneyinoutdate as ptime,
  concat(gametype, (CASE  WHEN opreatetype=1 THEN '上分' WHEN opreatetype=2 THEN '下分' ELSE '其他' END)  ) as pcontent,
  abs(moneyinoutamount) as pmoney, 
  'MONEY_INOUT' as ptype
  from user_money_in_and_out 
  where updatecapital=1 and employeecode=in_employeecode and (moneyinoutdate>last_take_time and moneyinoutdate<local_take_time)   ;
  
  
  /****************如果只有当前这一次取款，则最后一次取款时间要改为空值****************/
  IF isonce = 1 THEN 
  SET  last_take_time = null ;
  SET  last_take_money = null ;
  END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_team_balance
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_team_balance`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_team_balance`(IN in_enterprisereport boolean,IN in_employeecode char(8))
    READS SQL DATA
BEGIN
  # 总计 - 团队余额
  DECLARE r_balance decimal(16,2) DEFAULT 0;

  # 根据报表类型获取团队编码
  IF in_enterprisereport  THEN 
    SELECT ufn_up_recursion_shareholder_of_user(in_employeecode) INTO in_employeecode;
  END IF;
  
  SET @all_subordinates := ufn_down_recursion_team_of_agent(in_employeecode);
  
  SELECT SUM(eeca.balance) INTO r_balance 
    FROM enterprise_employee_capital_account eeca 
   WHERE (eeca.employeecode = in_employeecode 
      OR FIND_IN_SET (eeca.parentemployeecode , @all_subordinates));
  SELECT IF(r_balance IS NULL ,0,r_balance)AS balance;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_team_losewin
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_team_losewin`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_team_losewin`(IN in_enterprisereport boolean,IN in_employeecode char(8), IN in_begintime datetime, IN in_endtime datetime)
    READS SQL DATA
BEGIN
  DECLARE r_netmoney decimal(16,2) DEFAULT 0;
  DECLARE l_like_employeeallupleves VARCHAR(1000);
  DECLARE l_employeeallupleves_len  INT;
  DECLARE l_teamemployeecode CHAR(8);
     
  IF in_enterprisereport THEN
     SELECT ufn_up_recursion_shareholder_of_user(in_employeecode) INTO l_teamemployeecode;
  ELSE
     SET l_teamemployeecode := in_employeecode;
  END IF;
  
  SELECT CONCAT(employeeallupleves,'%'),LENGTH(employeeallupleves)+1 INTO l_like_employeeallupleves,l_employeeallupleves_len
    FROM enterprise_employee_all_uplevels
   WHERE employeecode = l_teamemployeecode;

   SELECT sum(IFNULL(ba.netmoney,0)) INTO r_netmoney
     FROM betting_all_game_winlose_detail ba
          INNER JOIN ( SELECT employeecode,
                              parentemployeecode,
                              SUBSTRING(employeeallupleves, l_employeeallupleves_len, 8) AS topparentemployeecode 
                         FROM enterprise_employee_all_uplevels
                        WHERE employeeallupleves LIKE l_like_employeeallupleves and employeetypecode = 'T003' ) squery ON squery.employeecode = ba.employeecode
    WHERE ba.bettime >= in_begintime
      AND ba.bettime <= in_endtime;
	
    SELECT r_netmoney;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_team_savemoney
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_team_savemoney`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_team_savemoney`(IN in_enterprisereport boolean,IN in_employeecode char(8), IN in_begintime datetime, IN in_endtime datetime)
    READS SQL DATA
BEGIN
  # 总计 - 团队存款
  DECLARE r_savemoney decimal(16,2) DEFAULT 0;

# 根据报表类型获取团队编码
IF in_enterprisereport  THEN 
  SELECT ufn_up_recursion_shareholder_of_user(in_employeecode) INTO in_employeecode;
END IF;

SET @all_subordinates := ufn_down_recursion_team_of_agent(in_employeecode);

SELECT SUM(dwo.orderamount) INTO r_savemoney
  FROM deposit_withdraw_order dwo
 WHERE dwo.orderdate>=in_begintime
   AND dwo.orderdate<in_endtime
   AND dwo.ordertype=1
   AND dwo.orderstatus =2
   AND ( dwo.employeecode = in_employeecode
      OR FIND_IN_SET (dwo.parentemployeecode , @all_subordinates));
	  
 SELECT IF(r_savemoney IS NULL ,0,r_savemoney)AS savemoney;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_stat_team_takemoney
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_stat_team_takemoney`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_stat_team_takemoney`(IN in_enterprisereport boolean,IN in_employeecode char(8), IN in_begintime datetime, IN in_endtime datetime)
    READS SQL DATA
BEGIN
  # 总计 - 团队取款
  DECLARE r_takemoney decimal(16,2) DEFAULT 0;

# 根据报表类型获取团队编码
IF in_enterprisereport  THEN 
  SELECT ufn_up_recursion_shareholder_of_user(in_employeecode) INTO in_employeecode;
END IF;

SET @all_subordinates := ufn_down_recursion_team_of_agent(in_employeecode);

SELECT SUM(dwo.orderamount)  INTO r_takemoney
  FROM deposit_withdraw_order dwo
 WHERE dwo.orderdate>=in_begintime
   AND dwo.orderdate<in_endtime
   AND dwo.ordertype=2
   AND dwo.orderstatus =2
   AND ( dwo.employeecode = in_employeecode
      OR FIND_IN_SET (dwo.parentemployeecode , @all_subordinates));
  
SELECT IF(r_takemoney IS NULL ,0,r_takemoney)AS takemoney;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_tn_toplist_team_performance
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_tn_toplist_team_performance`;
DELIMITER ;;
CREATE PROCEDURE `usp_tn_toplist_team_performance`(IN in_enterprisereport boolean,IN in_employeecode char(8),IN in_begintime datetime, IN in_endtime datetime)
BEGIN
  DECLARE l_like_employeeallupleves VARCHAR(1000);
  DECLARE l_employeeallupleves_len  INT;
  DECLARE l_teamemployeecode CHAR(8);
     
  IF in_enterprisereport THEN
     SELECT ufn_up_recursion_shareholder_of_user(in_employeecode) INTO l_teamemployeecode;
  ELSE
     SET l_teamemployeecode := in_employeecode;
  END IF;
  
  SELECT CONCAT(employeeallupleves,'%'),LENGTH(employeeallupleves)+1 INTO l_like_employeeallupleves,l_employeeallupleves_len
    FROM enterprise_employee_all_uplevels
   WHERE employeecode = l_teamemployeecode;
 
  SELECT ee.loginaccount, osquery.totalnetmoney AS netmoney
    FROM enterprise_employee_all_uplevels ee
         INNER JOIN ( SELECT squery.topparentemployeecode, sum(ba.netmoney) AS totalnetmoney
                        FROM betting_all_game_winlose_detail ba
                             INNER JOIN ( SELECT employeecode,
                      						     parentemployeecode,
                                                 SUBSTRING(employeeallupleves, l_employeeallupleves_len, 8) AS topparentemployeecode 
                      					    FROM enterprise_employee_all_uplevels
                      					   WHERE employeeallupleves LIKE l_like_employeeallupleves and employeetypecode = 'T003' ) squery ON squery.employeecode = ba.employeecode
                       WHERE ba.bettime >= in_begintime
                         AND ba.bettime <= in_endtime
                      GROUP BY squery.topparentemployeecode ) osquery ON osquery.topparentemployeecode = ee.employeecode
   LIMIT 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_day_report
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_day_report`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_day_report`(in in_gametype VARCHAR(10),in in_loginaccount VARCHAR(10),in in_enterprisecode VARCHAR(10),in in_start int,in in_limit int,in in_startdate int,in in_enddate int,out out_count int)
BEGIN
  
  
  select COUNT(1) INTO out_count from (
          select  Game_Platform
          from betting_all_day where 1=1
          and 
          (IF (in_startdate = 0, 0 = 0, date(Bet_Day) >= in_startdate) )   
          and 
          (IF (in_enddate  = 0, 0 = 0, date(Bet_Day) <= in_enddate) )  
          and 
          (IF (in_gametype IS NULL, 0 = 0, Game_Platform = in_gametype) )
           and 
          (IF (in_loginaccount IS NULL, 0 = 0, User_Name = in_loginaccount) )   
          and 
          (IF (in_enterprisecode IS NULL, 0 = 0, enterprisecode = in_enterprisecode) ) 
          group by Game_Platform,employeecode
  ) AS a;
  
  select employeecode,betmoney,validbet,netmoney,Game_Platform AS platformtype,cnt from (
          select employeecode,SUM(Bet_Money) AS betmoney,SUM(Valid_Money) AS validbet,SUM(Net_Money) AS netmoney,Game_Platform,COUNT(1) cnt 
          from betting_all_day where 1=1
          and 
          (IF (in_startdate = 0, 0 = 0, date(Bet_Day) >= in_startdate) )   
          and 
          (IF (in_enddate  = 0, 0 = 0, date(Bet_Day) <= in_enddate) )  
          and 
          (IF (in_gametype IS NULL, 0 = 0, Game_Platform = in_gametype) )
           and 
          (IF (in_loginaccount IS NULL, 0 = 0, User_Name = in_loginaccount) )   
          and 
          (IF (in_enterprisecode IS NULL, 0 = 0, enterprisecode = in_enterprisecode) )   
            
          group by Game_Platform,employeecode
  ) AS a    order by betmoney desc limit in_start , in_limit;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_deposit_money
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_deposit_money`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_deposit_money`(IN in_employeecode CHAR(8),IN in_flag int ,out allDepositMoney DECIMAL(14,2))
    READS SQL DATA
BEGIN
	/****************
         0=昨天 1=当天 2=历史累计
        ***************/
        
        if in_flag=0 then  
            select IFNULL(sum(orderamount),0) into allDepositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and employeecode=in_employeecode and date(orderdate)=date(date_add(now(), interval -1 day));
        
        elseif in_flag=1 then 
            select IFNULL(sum(orderamount),0) into allDepositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and employeecode=in_employeecode and date(orderdate)=date(now());
            
        else  
            select IFNULL(sum(orderamount),0) into allDepositMoney from deposit_withdraw_order where orderstatus='2' and ordertype=1 and employeecode=in_employeecode ;
        end if; 
  select allDepositMoney;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_deposit_withdrawals
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_deposit_withdrawals`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_deposit_withdrawals`(IN employee_id varchar(10),IN date_begin date,IN date_end date,IN var_start int,IN var_end int,OUT param1 int)
BEGIN
  /*
  用户存取统计 ---> 直线会员及代理团队
*/
  IF ISNULL(date_begin) = 1 AND ISNULL(date_end) = 1 THEN
    SET date_begin = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  /*分页*/
  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      AA.loginaccount,
      AA.employeecode,
      BB.deposit_mount AS allDepositMoney,
      BB.deposit_num AS depositNumber,
      CC.take_mount AS allTakeMoney,
      CC.take_num AS quantity
    FROM (SELECT
        loginaccount,
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode
      FROM enterprise_employee
      WHERE parentemployeecode IN (SELECT
          A.employeecode
        FROM enterprise_employee A
        WHERE FIND_IN_SET(A.parentemployeecode, (SELECT
            ufn_down_recursion_team_of_agent(ear.employeecode) AS code_id
          FROM enterprise_employee ear
          WHERE ear.employeecode = employee_id)) > 0 AND A.datastatus = 1)) AS AA
      LEFT JOIN (SELECT
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode,
          SUM(orderamount) AS deposit_mount,
          COUNT(1) AS deposit_num
        FROM deposit_withdraw_order
        WHERE ordertype = 1
        AND orderstatus = 2
        AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY employeecode,
                 parentemployeecode,
                 enterprisecode,
                 brandcode) AS BB
        ON AA.employeecode = BB.employeecode AND AA.parentemployeecode = BB.parentemployeecode
        AND AA.enterprisecode = BB.enterprisecode AND AA.brandcode = BB.brandcode
      LEFT JOIN (SELECT
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode,
          SUM(orderamount) AS take_mount,
          COUNT(1) AS take_num
        FROM deposit_withdraw_order
        WHERE ordertype = 2
        AND orderstatus = 2
        AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY employeecode,
                 parentemployeecode,
                 enterprisecode,
                 brandcode) AS CC
        ON AA.employeecode = CC.employeecode AND AA.parentemployeecode = CC.parentemployeecode
        AND AA.enterprisecode = CC.enterprisecode AND AA.brandcode = CC.brandcode
    WHERE (BB.deposit_num > 0 OR CC.take_num > 0)
    UNION ALL
    SELECT
      loginaccount,
      employeecode,
      allDepositMoney,
      depositNumber,
      allTakeMoney,
      quantity
    FROM (SELECT
        '直线会员' AS loginaccount,
        employee_id AS employeecode,
        SUM(BB.deposit_mount) AS allDepositMoney,
        SUM(BB.deposit_num) AS depositNumber,
        SUM(CC.take_mount) AS allTakeMoney,
        SUM(CC.take_num) AS quantity
      FROM (SELECT
          loginaccount,
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode
        FROM enterprise_employee
        WHERE parentemployeecode = employee_id
        AND employeetypecode = 'T003'
        AND datastatus = 1) AS AA
        LEFT JOIN (SELECT
            employeecode,
            parentemployeecode,
            enterprisecode,
            brandcode,
            SUM(orderamount) AS deposit_mount,
            COUNT(1) AS deposit_num
          FROM deposit_withdraw_order
          WHERE ordertype = 1
          AND orderstatus = 2
          AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
          GROUP BY employeecode,
                   parentemployeecode,
                   enterprisecode,
                   brandcode) AS BB
          ON AA.employeecode = BB.employeecode AND AA.parentemployeecode = BB.parentemployeecode
          AND AA.enterprisecode = BB.enterprisecode AND AA.brandcode = BB.brandcode
        LEFT JOIN (SELECT
            employeecode,
            parentemployeecode,
            enterprisecode,
            brandcode,
            SUM(orderamount) AS take_mount,
            COUNT(1) AS take_num
          FROM deposit_withdraw_order
          WHERE ordertype = 2
          AND orderstatus = 2
          AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
          GROUP BY employeecode,
                   parentemployeecode,
                   enterprisecode,
                   brandcode) AS CC
          ON AA.employeecode = CC.employeecode AND AA.parentemployeecode = CC.parentemployeecode
          AND AA.enterprisecode = CC.enterprisecode AND AA.brandcode = CC.brandcode) AS UU
    WHERE (allDepositMoney > 0 OR allTakeMoney > 0)) AS HH;


  /*主体数据*/
  SELECT
    AA.loginaccount,
    AA.employeecode,
    BB.deposit_mount AS allDepositMoney,
    BB.deposit_num AS depositNumber,
    CC.take_mount AS allTakeMoney,
    CC.take_num AS quantity
  FROM (SELECT
      loginaccount,
      employeecode,
      parentemployeecode,
      enterprisecode,
      brandcode
    FROM enterprise_employee
    WHERE parentemployeecode IN (SELECT
        A.employeecode
      FROM enterprise_employee A
      WHERE FIND_IN_SET(A.parentemployeecode, (SELECT
          ufn_down_recursion_team_of_agent(ear.employeecode) AS code_id
        FROM enterprise_employee ear
        WHERE ear.employeecode = employee_id)) > 0 AND A.datastatus = 1)) AS AA
    LEFT JOIN (SELECT
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode,
        SUM(orderamount) AS deposit_mount,
        COUNT(1) AS deposit_num
      FROM deposit_withdraw_order
      WHERE ordertype = 1
      AND orderstatus = 2
      AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
      GROUP BY employeecode,
               parentemployeecode,
               enterprisecode,
               brandcode) AS BB
      ON AA.employeecode = BB.employeecode AND AA.parentemployeecode = BB.parentemployeecode
      AND AA.enterprisecode = BB.enterprisecode AND AA.brandcode = BB.brandcode
    LEFT JOIN (SELECT
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode,
        SUM(orderamount) AS take_mount,
        COUNT(1) AS take_num
      FROM deposit_withdraw_order
      WHERE ordertype = 2
      AND orderstatus = 2
      AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
      GROUP BY employeecode,
               parentemployeecode,
               enterprisecode,
               brandcode) AS CC
      ON AA.employeecode = CC.employeecode AND AA.parentemployeecode = CC.parentemployeecode
      AND AA.enterprisecode = CC.enterprisecode AND AA.brandcode = CC.brandcode
  WHERE (BB.deposit_num > 0 OR CC.take_num > 0)
  UNION ALL
  SELECT
    loginaccount,
    employeecode,
    allDepositMoney,
    depositNumber,
    allTakeMoney,
    quantity
  FROM (SELECT
      '直线会员' AS loginaccount,
      employee_id AS employeecode,
      SUM(BB.deposit_mount) AS allDepositMoney,
      SUM(BB.deposit_num) AS depositNumber,
      SUM(CC.take_mount) AS allTakeMoney,
      SUM(CC.take_num) AS quantity
    FROM (SELECT
        loginaccount,
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode
      FROM enterprise_employee
      WHERE parentemployeecode = employee_id
      AND employeetypecode = 'T003'
      AND datastatus = 1) AS AA
      LEFT JOIN (SELECT
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode,
          SUM(orderamount) AS deposit_mount,
          COUNT(1) AS deposit_num
        FROM deposit_withdraw_order
        WHERE ordertype = 1
        AND orderstatus = 2
        AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY employeecode,
                 parentemployeecode,
                 enterprisecode,
                 brandcode) AS BB
        ON AA.employeecode = BB.employeecode AND AA.parentemployeecode = BB.parentemployeecode
        AND AA.enterprisecode = BB.enterprisecode AND AA.brandcode = BB.brandcode
      LEFT JOIN (SELECT
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode,
          SUM(orderamount) AS take_mount,
          COUNT(1) AS take_num
        FROM deposit_withdraw_order
        WHERE ordertype = 2
        AND orderstatus = 2
        AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY employeecode,
                 parentemployeecode,
                 enterprisecode,
                 brandcode) AS CC
        ON AA.employeecode = CC.employeecode AND AA.parentemployeecode = CC.parentemployeecode
        AND AA.enterprisecode = CC.enterprisecode AND AA.brandcode = CC.brandcode) AS UU
  WHERE (allDepositMoney > 0 OR allTakeMoney > 0) LIMIT var_start, var_end;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_deposit_withdrawals_count
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_deposit_withdrawals_count`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_deposit_withdrawals_count`(IN employee_id varchar(10),IN date_begin date,IN date_end date,IN var_start int,IN var_end int,OUT param1 int)
BEGIN
  /*
用户存取统计
*/
  IF ISNULL(date_begin) = 1 AND ISNULL(date_end) = 1 THEN
    SET date_begin = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      AA.loginaccount,
      AA.employeecode,
      BB.deposit_mount AS allDepositMoney,
      BB.deposit_num AS depositNumber,
      CC.take_mount AS allTakeMoney,
      CC.take_num AS quantity
    FROM (SELECT
        loginaccount,
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode
      FROM enterprise_employee
      WHERE parentemployeecode = employee_id
      AND employeetypecode = 'T003'
      AND datastatus = 1) AS AA
      LEFT JOIN (SELECT
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode,
          SUM(orderamount) AS deposit_mount,
          COUNT(1) AS deposit_num
        FROM deposit_withdraw_order
        WHERE ordertype = 1
        AND orderstatus = 2
        AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY employeecode,
                 parentemployeecode,
                 enterprisecode,
                 brandcode) AS BB
        ON AA.employeecode = BB.employeecode
        AND AA.parentemployeecode = BB.parentemployeecode AND AA.enterprisecode = BB.enterprisecode
        AND AA.brandcode = BB.brandcode
      LEFT JOIN (SELECT
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode,
          SUM(orderamount) AS take_mount,
          COUNT(1) AS take_num
        FROM deposit_withdraw_order
        WHERE ordertype = 2
        AND orderstatus = 2
        AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY employeecode,
                 parentemployeecode,
                 enterprisecode,
                 brandcode) AS CC
        ON AA.employeecode = CC.employeecode
        AND AA.parentemployeecode = CC.parentemployeecode AND AA.enterprisecode = CC.enterprisecode
        AND AA.brandcode = CC.brandcode) AS HH;

  SELECT
    AA.loginaccount,
    AA.employeecode,
    BB.deposit_mount AS allDepositMoney,
    BB.deposit_num AS depositNumber,
    CC.take_mount AS allTakeMoney,
    CC.take_num AS quantity
  FROM (SELECT
      loginaccount,
      employeecode,
      parentemployeecode,
      enterprisecode,
      brandcode
    FROM enterprise_employee
    WHERE parentemployeecode = employee_id
    AND employeetypecode = 'T003'
    AND datastatus = 1) AS AA
    LEFT JOIN (SELECT
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode,
        SUM(orderamount) AS deposit_mount,
        COUNT(1) AS deposit_num
      FROM deposit_withdraw_order
      WHERE ordertype = 1
      AND orderstatus = 2
      AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
      GROUP BY employeecode,
               parentemployeecode,
               enterprisecode,
               brandcode) AS BB
      ON AA.employeecode = BB.employeecode
      AND AA.parentemployeecode = BB.parentemployeecode AND AA.enterprisecode = BB.enterprisecode
      AND AA.brandcode = BB.brandcode
    LEFT JOIN (SELECT
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode,
        SUM(orderamount) AS take_mount,
        COUNT(1) AS take_num
      FROM deposit_withdraw_order
      WHERE ordertype = 2
      AND orderstatus = 2
      AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
      GROUP BY employeecode,
               parentemployeecode,
               enterprisecode,
               brandcode) AS CC
      ON AA.employeecode = CC.employeecode
      AND AA.parentemployeecode = CC.parentemployeecode AND AA.enterprisecode = CC.enterprisecode
      AND AA.brandcode = CC.brandcode LIMIT var_start, var_end;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_deposit_withdrawals_count_bak
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_deposit_withdrawals_count_bak`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_deposit_withdrawals_count_bak`(IN employee_id varchar(10),IN date_begin date,IN date_end date,IN var_start int,IN var_end int,OUT param1 int)
BEGIN
  /*
   备份 --用户存取统计
*/
  IF ISNULL(date_begin) = 1 AND ISNULL(date_end) = 1 THEN
    SET date_begin = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      AA.loginaccount,
      AA.employeecode,
      BB.deposit_mount AS allDepositMoney,
      BB.deposit_num AS depositNumber,
      CC.take_mount AS allTakeMoney,
      CC.take_num AS quantity
    FROM (SELECT
        loginaccount,
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode
      FROM enterprise_employee
      WHERE parentemployeecode = employee_id
      AND employeetypecode = 'T003'
      AND datastatus = 1) AS AA
      LEFT JOIN (SELECT
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode,
          SUM(orderamount) AS deposit_mount,
          COUNT(1) AS deposit_num
        FROM deposit_withdraw_order
        WHERE ordertype = 1
        AND orderstatus = 2
        AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY employeecode,
                 parentemployeecode,
                 enterprisecode,
                 brandcode) AS BB
        ON AA.employeecode = BB.employeecode
        AND AA.parentemployeecode = BB.parentemployeecode AND AA.enterprisecode = BB.enterprisecode
        AND AA.brandcode = BB.brandcode
      LEFT JOIN (SELECT
          employeecode,
          parentemployeecode,
          enterprisecode,
          brandcode,
          SUM(orderamount) AS take_mount,
          COUNT(1) AS take_num
        FROM deposit_withdraw_order
        WHERE ordertype = 2
        AND orderstatus = 2
        AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY employeecode,
                 parentemployeecode,
                 enterprisecode,
                 brandcode) AS CC
        ON AA.employeecode = CC.employeecode
        AND AA.parentemployeecode = CC.parentemployeecode AND AA.enterprisecode = CC.enterprisecode
        AND AA.brandcode = CC.brandcode) AS HH;

  /*会员存取统计*/
  SELECT
    AA.loginaccount,
    AA.employeecode,
    BB.deposit_mount AS allDepositMoney,
    BB.deposit_num AS depositNumber,
    CC.take_mount AS allTakeMoney,
    CC.take_num AS quantity
  FROM (SELECT
      loginaccount,
      employeecode,
      parentemployeecode,
      enterprisecode,
      brandcode
    FROM enterprise_employee
    WHERE parentemployeecode = employee_id
    AND employeetypecode = 'T003'
    AND datastatus = 1) AS AA
    LEFT JOIN (SELECT
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode,
        SUM(orderamount) AS deposit_mount,
        COUNT(1) AS deposit_num
      FROM deposit_withdraw_order
      WHERE ordertype = 1
      AND orderstatus = 2
      AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
      GROUP BY employeecode,
               parentemployeecode,
               enterprisecode,
               brandcode) AS BB
      ON AA.employeecode = BB.employeecode
      AND AA.parentemployeecode = BB.parentemployeecode AND AA.enterprisecode = BB.enterprisecode
      AND AA.brandcode = BB.brandcode
    LEFT JOIN (SELECT
        employeecode,
        parentemployeecode,
        enterprisecode,
        brandcode,
        SUM(orderamount) AS take_mount,
        COUNT(1) AS take_num
      FROM deposit_withdraw_order
      WHERE ordertype = 2
      AND orderstatus = 2
      AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN date_begin AND date_end
      GROUP BY employeecode,
               parentemployeecode,
               enterprisecode,
               brandcode) AS CC
      ON AA.employeecode = CC.employeecode
      AND AA.parentemployeecode = CC.parentemployeecode AND AA.enterprisecode = CC.enterprisecode
      AND AA.brandcode = CC.brandcode LIMIT var_start, var_end;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_deposit_withdrawals_rate
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_deposit_withdrawals_rate`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_deposit_withdrawals_rate`(IN in_enterprisecode char(8), IN in_loginaccount char(8), IN startDate datetime, IN endDate datetime, IN start_index INTEGER, IN limit_count INTEGER, OUT countNumber INTEGER)
    READS SQL DATA
BEGIN
        
        SELECT count(1) INTO countNumber FROM (
                SELECT s.employeecode  FROM deposit_withdraw_order s LEFT JOIN enterprise_employee ee ON ee.employeecode=s.employeecode 
                WHERE s.orderstatus='2' AND s.enterprisecode=in_enterprisecode AND (s.orderdate >= startDate AND s.orderdate <= endDate) and IF (in_loginaccount is NULL, 0 = 0, ee.loginaccount = in_loginaccount)
                GROUP BY employeecode
        ) as c;
        

        
        SELECT employeecode,loginaccount,savemoney,takemoney,savecount,takecount FROM (
        
                SELECT s.employeecode ,ee.loginaccount,
                        SUM(CASE  WHEN s.ordertype=1 THEN s.orderamount ELSE 0 END ) AS savemoney , 
                        SUM(CASE  WHEN s.ordertype=2 THEN s.orderamount ELSE 0 END ) AS takemoney ,
                        SUM(CASE  WHEN s.ordertype=1 THEN 1 ELSE 0 END ) AS savecount , 
                        SUM(CASE  WHEN s.ordertype=2 THEN 1 ELSE 0 END ) AS takecount 
                
                FROM deposit_withdraw_order s LEFT JOIN enterprise_employee ee ON ee.employeecode=s.employeecode 
                WHERE s.orderstatus='2' AND s.enterprisecode=in_enterprisecode AND (s.orderdate >= startDate AND s.orderdate <= endDate) and IF (in_loginaccount is NULL, 0 = 0, ee.loginaccount = in_loginaccount)
                GROUP BY s.employeecode 
                
        ) AS t ORDER BY takemoney,takecount DESC LIMIT start_index, limit_count;
          
          
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_game_down_member
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_game_down_member`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_game_down_member`(IN employee_id varchar(10),IN start_date date,IN date_end date,IN startd int,IN endsd int,
OUT param1 int,OUT param2 decimal,OUT param3 decimal,OUT param4 decimal)
BEGIN
  /*
 二次查询游戏报表----直线会员游戏报表显示
*/
  IF startd = 0 THEN
    SET startd = 0;
  END IF;
  IF endsd = 0 THEN
    SET endsd = 10;
  END IF;
  IF ISNULL(start_date) = 1 AND ISNULL(date_end) = 1 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    COUNT(1) ,
   sum(HH.game_betting_amount) , 
   sum(HH.bonus) , 
   sum(HH.lose_win_amount) INTO param1,param2,param3, param4
   

  FROM (SELECT
      AA.employeecode,
      AA.loginaccount,
      COUNT(1) AS num,
      CONCAT(CC.employeetype, '_nodown') AS employeetypename,
      GG.Bet_Money AS game_betting_amount,
      GG.Net_Money AS lose_win_amount,
      GG.Net_Money + GG.Valid_Money * GG.bonus AS user_lose_win_amount,
      GG.Valid_Money * GG.bonus AS bonus
    FROM enterprise_employee AS AA
      INNER JOIN (SELECT
          BB.employeecode,
          BB.parentemployeecode,
          BB.enterprisecode,
          BB.Game_Platform,
          BB.Game_Type,
          game.gamename,
          SUM(BB.Bet_Money) AS Bet_Money,
          SUM(BB.Net_Money) AS Net_Money,
          SUM(BB.Valid_Money) AS Valid_Money,
          SUM(DD.bonus) AS bonus
        FROM betting_all_day BB
          INNER JOIN employee_gamecataloy_bonus AS DD
            ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
          INNER JOIN game
            ON BB.Game_Platform = game.gametype
            AND DATE_FORMAT(BB.Bet_Day, '%Y-%m-%d') BETWEEN start_date AND date_end
        GROUP BY BB.employeecode,
                 BB.parentemployeecode,
                 BB.enterprisecode,
                 BB.Game_Platform,
                 game.gamename,
                 BB.Game_Type) AS GG
        ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
      INNER JOIN enterprise_employee_type AS CC
        ON AA.employeetypecode = CC.employeetypecode
    WHERE AA.parentemployeecode = employee_id
    AND AA.employeetypecode = 'T003'

    GROUP BY AA.employeecode,
             AA.loginaccount) AS HH;

  SELECT
    AA.employeecode,
    AA.loginaccount,
    COUNT(1) AS num,
    CONCAT(CC.employeetype, '_nodown') AS employeetypename,
    GG.Game_Platform AS gametype,
    GG.gamename,
    GG.Bet_Money AS game_betting_amount,
    GG.Net_Money AS lose_win_amount,
    GG.Net_Money + GG.Valid_Money * GG.bonus AS user_lose_win_amount,
    GG.Valid_Money * GG.bonus AS bonus
  FROM enterprise_employee AS AA
    INNER JOIN (SELECT
        BB.employeecode,
        BB.parentemployeecode,
        BB.enterprisecode,
        BB.Game_Platform,
        BB.Game_Type,
        game.gamename,
        SUM(BB.Bet_Money) AS Bet_Money,
        SUM(BB.Net_Money) AS Net_Money,
        SUM(BB.Valid_Money) AS Valid_Money,
        SUM(DD.bonus) AS bonus
      FROM betting_all_day BB
        INNER JOIN employee_gamecataloy_bonus AS DD
          ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
        INNER JOIN game
          ON BB.Game_Platform = game.gametype
          AND DATE_FORMAT(BB.Bet_Day, '%Y-%m-%d') BETWEEN start_date AND date_end
      GROUP BY BB.employeecode,
               BB.parentemployeecode,
               BB.enterprisecode,
               BB.Game_Platform,
               game.gamename,
               BB.Game_Type) AS GG
      ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
    INNER JOIN enterprise_employee_type AS CC
      ON AA.employeetypecode = CC.employeetypecode
  WHERE AA.parentemployeecode = employee_id
  AND AA.employeetypecode = 'T003'

  GROUP BY AA.employeecode,
           AA.loginaccount,
           GG.Game_Platform,
           GG.gamename,
           GG.Bet_Money,
           GG.Net_Money,
           GG.Net_Money + GG.Valid_Money * GG.bonus,
           GG.Valid_Money * GG.bonus LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_game_win_lose_rate
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_game_win_lose_rate`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_game_win_lose_rate`(IN in_platformtype VARCHAR(10), IN in_loginaccount VARCHAR(20), IN in_enterprisecode CHAR(8), IN in_bettime_begin DATETIME, 
IN in_bettime_end DATETIME, IN in_start INTEGER,IN in_limit INTEGER,OUT countNumber INTEGER)
    READS SQL DATA
BEGIN
       select count(1) INTO  countNumber FROM (
       select loginaccount  from betting_all_game_winlose_detail 
                        WHERE (in_platformtype is null or platformtype=in_platformtype)
                        AND (in_loginaccount is null or loginaccount=in_loginaccount)
                        AND (enterprisecode=in_enterprisecode)                        
                        AND (bettime >= in_bettime_begin and bettime <= in_bettime_end)
                       group by platformtype,loginaccount
       ) as t1;
        
       select loginaccount,platformtype,sum1 as betmoney,sum2 as netmoney, FORMAT(rate,2) ratex from (      
                select loginaccount,platformtype,sum1,sum2,abs(sum2)/1.00/sum1*100 as rate from (
                        select loginaccount,platformtype,sum(betmoney) sum1,sum(netmoney) sum2  from betting_all_game_winlose_detail 
                        WHERE (in_platformtype is null or platformtype=in_platformtype)
                        AND (in_loginaccount is null or loginaccount=in_loginaccount)
                        AND (enterprisecode=in_enterprisecode)                        
                        AND (bettime >= in_bettime_begin and bettime <= in_bettime_end)
                        group by platformtype,loginaccount
                ) as t 
        ) as t2 order by rate desc limit in_start,in_limit ;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_game_win_lose_work
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_game_win_lose_work`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_game_win_lose_work`(IN employee_id varchar(10),IN begin_date date,IN end_date date,IN startsd int,IN endsd int)
BEGIN
  /*
 二次查询游戏报表 ---代理团队下游戏报表
*/
  IF ISNULL(startsd) = 1 AND ISNULL(endsd) = 1 THEN
    SET startsd = 0;
    SET endsd = 10;
  END IF;
  IF ISNULL(begin_date) = 1 AND ISNULL(end_date) = 1 THEN
    SET begin_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET end_date = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    GROUP_AA.loginaccount,
    ufn_down_recursion_team_of_agent_count(GROUP_AA.employeecode) AS num,
    GROUP_AA.employeecode,
    Game_B.Bet_Money AS game_betting_amount,
    Game_B.bonus,
    Game_B.Net_Money + Game_B.bonus AS lose_win_amount
  FROM enterprise_employee GROUP_AA
    INNER JOIN (SELECT
        A.enterprisecode,
        A.employeecode,
        A.parentemployeecode,
        A.Game_Platform,
        A.Game_Type,
        SUM(A.Bet_Money) AS Bet_Money,
        SUM(A.Net_Money) AS Net_Money,
        SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
      FROM betting_all_day AS A
        INNER JOIN employee_gamecataloy_bonus AS B
          ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
          AND A.Game_Big_Type = B.categorytype
          AND A.Bet_Day BETWEEN begin_date AND end_date
      GROUP BY A.enterprisecode,
               A.employeecode,
               A.parentemployeecode,
               A.Game_Platform,
               A.Game_Type) AS Game_B
      ON GROUP_AA.employeecode = Game_B.employeecode
      AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
      AND GROUP_AA.enterprisecode = Game_B.enterprisecode
  WHERE GROUP_AA.parentemployeecode = employee_id
  AND GROUP_AA.employeetypecode = 'T004'
  UNION ALL
  SELECT
    '直线会员' AS loginaccount,
    num,
    employee_id AS employeecode,
    Bet_Money AS game_betting_amount,
    bonus,
    net_Money + bonus AS lose_win_amount
  FROM (SELECT
      LINE_AA.loginaccount,
      COUNT(1) AS num,
      LINE_AA.employeecode,
      Game_B.Bet_Money,
      Game_B.net_Money,
      Game_B.bonus,
      Game_B.Net_Money + Game_B.bonus
    FROM enterprise_employee AS LINE_AA
      INNER JOIN (SELECT
          A.enterprisecode,
          A.employeecode,
          A.parentemployeecode,
          A.Game_Platform,
          A.Game_Type,
          SUM(A.Bet_Money) AS Bet_Money,
          SUM(A.Net_Money) AS Net_Money,
          SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
        FROM betting_all_day AS A
          INNER JOIN employee_gamecataloy_bonus AS B
            ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
            AND A.Game_Big_Type = B.categorytype
            AND A.Bet_Day BETWEEN begin_date AND end_date
        GROUP BY A.enterprisecode,
                 A.employeecode,
                 A.parentemployeecode,
                 A.Game_Platform,
                 A.Game_Type) AS Game_B
        ON LINE_AA.employeecode = Game_B.employeecode
        AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
        AND LINE_AA.enterprisecode = Game_B.enterprisecode
    WHERE LINE_AA.parentemployeecode = employee_id
    AND LINE_AA.employeetypecode = 'T003') AS FF
  WHERE FF.num > 0 LIMIT startsd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_line_game_recording
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_line_game_recording`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_line_game_recording`(IN employee_id varchar(10), IN start_date date,IN date_end date,IN startd int,IN endsd int)
BEGIN
  /*
 二次查询游戏报表----直线会员游戏报表显示
*/
  IF startd = 0 THEN
    SET startd = 0;
  END IF;
  IF endsd = 0 THEN
    SET endsd = 10;
  END IF;
  IF ISNULL(start_date) = 1 AND ISNULL(date_end) = 1 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    AA.employeecode,
    AA.loginaccount,
    COUNT(1) AS num,
    CONCAT(CC.employeetype, '_nodown') AS employeetypename,
    GG.Game_Platform,
    GG.gamename,
    GG.Bet_Money AS game_betting_amount,
    GG.Net_Money AS lose_win_amount,
    GG.Net_Money + GG.Valid_Money * GG.bonus AS user_lose_win_amount,
    GG.Valid_Money * GG.bonus AS bonus
  FROM enterprise_employee AS AA
    INNER JOIN (SELECT
        BB.employeecode,
        BB.parentemployeecode,
        BB.enterprisecode,
        BB.Game_Platform,
        game.gamename,
        BB.Game_Type,
        SUM(BB.Bet_Money) AS Bet_Money,
        SUM(BB.Net_Money) AS Net_Money,
        SUM(BB.Valid_Money) AS Valid_Money,
        SUM(DD.bonus) AS bonus
      FROM betting_all_day BB
        INNER JOIN employee_gamecataloy_bonus AS DD
          ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
        INNER JOIN game
          ON BB.Game_Platform = game.gametype
          AND DATE_FORMAT(BB.Bet_Day, '%Y-%m-%d') BETWEEN start_date AND date_end
      GROUP BY BB.employeecode,
               BB.parentemployeecode,
               BB.enterprisecode,
               BB.Game_Platform,
               game.gamename,
               BB.Game_Type) AS GG
      ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
    INNER JOIN enterprise_employee_type AS CC
      ON AA.employeetypecode = CC.employeetypecode
  WHERE AA.parentemployeecode = employee_id
  AND AA.employeetypecode = 'T003'

  GROUP BY AA.employeecode,
           AA.loginaccount LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_login_info
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_login_info`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_login_info`(IN login varchar(10),IN ip varchar(20),IN startd int,IN send int,OUT param1 int)
BEGIN
  /*
		客户登陆分析
  */
  DECLARE var_login varchar(12);
  DECLARE var_ip varchar(20);
  DECLARE var_start int;
  DECLARE var_end int;

  IF LENGTH(TRIM(login)) > 0 THEN
    SET var_login = login;
  ELSEIF ISNULL(login) = 1 THEN
    SET var_login = '';
  END IF;


  IF LENGTH(TRIM(ip)) > 0 THEN
    SET var_ip = ip;
  ELSEIF ISNULL(ip) = 1 THEN
    SET var_ip = '';
  END IF;

  IF startd = 0 THEN
    SET var_start = 0;
  ELSE
    SET var_start = startd;
  END IF;

  IF send = 0 THEN
    SET var_end = 10;
  ELSE
    SET var_end = send;
  END IF;

  IF ((LENGTH(TRIM(login)) <> 0 AND LENGTH(TRIM(ip)) <> 0) AND (ISNULL(ip) <> 1 AND ISNULL(login) <> 1)) THEN ##当用户和IP都为不为空时

    SELECT
      COUNT(1) INTO param1
    FROM (SELECT
        *
      FROM (SELECT
          loginip,
          CONCAT(var_login, ',', GROUP_CONCAT(DISTINCT loginaccount)) AS loginaccount
        FROM log_login
        WHERE loginip IN (SELECT DISTINCT
            loginip
          FROM log_login
          WHERE loginaccount = var_login) AND loginaccount <> var_login
        GROUP BY loginip
        UNION ALL
        SELECT
          loginip,
          GROUP_CONCAT(DISTINCT loginaccount) AS loginaccount
        FROM log_login
        WHERE loginip NOT IN (SELECT
            loginip
          FROM log_login
          WHERE loginip IN (SELECT DISTINCT
              loginip
            FROM log_login
            WHERE loginaccount = var_login) AND loginaccount <> var_login) AND loginaccount = var_login
        GROUP BY loginip) a
      WHERE loginip = var_ip) a;

    SELECT
      *
    FROM (SELECT
        loginip,
        CONCAT(var_login, ',', GROUP_CONCAT(DISTINCT loginaccount)) AS loginaccount
      FROM log_login
      WHERE loginip IN (SELECT DISTINCT
          loginip
        FROM log_login
        WHERE loginaccount = var_login) AND loginaccount <> var_login
      GROUP BY loginip
      UNION ALL
      SELECT
        loginip,
        GROUP_CONCAT(DISTINCT loginaccount) AS loginaccount
      FROM log_login
      WHERE loginip NOT IN (SELECT
          loginip
        FROM log_login
        WHERE loginip IN (SELECT DISTINCT
            loginip
          FROM log_login
          WHERE loginaccount = var_login) AND loginaccount <> var_login) AND loginaccount = var_login
      GROUP BY loginip) a
    WHERE loginip = var_ip;

  ELSEIF (LENGTH(TRIM(login)) > 0 AND ISNULL(login) <> 1) THEN ##用户不为空时
    SELECT
      COUNT(1) INTO param1
    FROM (SELECT
        loginip,
        CONCAT(var_login, ',', GROUP_CONCAT(DISTINCT loginaccount)) AS loginaccount
      FROM log_login
      WHERE loginip IN (SELECT DISTINCT
          loginip
        FROM log_login
        WHERE loginaccount = var_login) AND loginaccount <> var_login
      GROUP BY loginip
      UNION ALL
      SELECT
        loginip,
        GROUP_CONCAT(DISTINCT loginaccount) AS loginaccount
      FROM log_login
      WHERE loginip NOT IN (SELECT
          loginip
        FROM log_login
        WHERE loginip IN (SELECT DISTINCT
            loginip
          FROM log_login
          WHERE loginaccount = var_login) AND loginaccount <> var_login) AND loginaccount = var_login
      GROUP BY loginip) AS DD;

    SELECT
      loginip,
      CONCAT(var_login, ',', GROUP_CONCAT(DISTINCT loginaccount)) AS loginaccount
    FROM log_login
    WHERE loginip IN (SELECT DISTINCT
        loginip
      FROM log_login
      WHERE loginaccount = var_login) AND loginaccount <> var_login
    GROUP BY loginip
    UNION ALL
    SELECT
      loginip,
      GROUP_CONCAT(DISTINCT loginaccount) AS loginaccount
    FROM log_login
    WHERE loginip NOT IN (SELECT
        loginip
      FROM log_login
      WHERE loginip IN (SELECT DISTINCT
          loginip
        FROM log_login
        WHERE loginaccount = var_login) AND loginaccount <> var_login) AND loginaccount = var_login
    GROUP BY loginip;

  ELSEIF (LENGTH(TRIM(ip)) > 0 AND ISNULL(ip) <> 1) THEN ##当IP不为空时

    SELECT
      COUNT(1) INTO param1
    FROM (SELECT
        a.loginip AS loginip,
        GROUP_CONCAT(a.loginaccount) AS loginaccount
      FROM (SELECT DISTINCT
          loginip,
          loginaccount
        FROM log_login
        WHERE loginip = var_ip) a
      GROUP BY a.loginip LIMIT var_start, var_end) a;

    SELECT
      a.loginip AS loginip,
      GROUP_CONCAT(a.loginaccount) AS loginaccount
    FROM (SELECT DISTINCT
        loginip,
        loginaccount
      FROM log_login
      WHERE loginip = var_ip) a
    GROUP BY a.loginip LIMIT var_start, var_end;
  ELSEIF ((LENGTH(TRIM(login)) = 0 AND LENGTH(TRIM(ip)) = 0) OR (ISNULL(ip) = 1 AND ISNULL(login) = 1)) THEN ##当用户和IP都为空时
    SELECT
      COUNT(1) INTO param1
    FROM (SELECT
        a.loginip AS loginip,
        GROUP_CONCAT(a.loginaccount) AS loginaccount
      FROM (SELECT DISTINCT
          loginip,
          loginaccount
        FROM log_login) a
      GROUP BY a.loginip LIMIT var_start, var_end) a;

    SELECT
      a.loginip AS loginip,
      GROUP_CONCAT(a.loginaccount) AS loginaccount
    FROM (SELECT DISTINCT
        loginip,
        loginaccount
      FROM log_login) a
    GROUP BY a.loginip LIMIT var_start, var_end;
  END IF;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_lose_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_lose_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_lose_ranking`(IN employee_id varchar(10),IN start_end date,IN date_end date)
BEGIN
  /*
 客户输排名
*/
  IF ISNULL(start_end) = 1 AND ISNULL(date_end) = 1 THEN
    SET start_end = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  SELECT
    User_Name,
    employeecode,
    NetMoney,
    start_date,
    end_date
  FROM (SELECT
      B.User_Name,
      A.employeecode,
      SUM(B.Net_Money) AS NetMoney,
      start_end AS start_date,
      date_end AS end_date
    FROM (SELECT
        *
      FROM enterprise_employee
      WHERE parentemployeecode = employee_id) AS A
      INNER JOIN betting_all_day AS B
        ON A.employeecode = B.employeecode
    WHERE B.Bet_Day BETWEEN start_end AND date_end
    GROUP BY B.User_Name,
             A.employeecode) AS HH
  WHERE NetMoney < 0
  ORDER BY NetMoney ASC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_lose_wins_count
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_lose_wins_count`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_lose_wins_count`(IN employeecode varchar(10),IN date_begin date,IN date_end date,IN var_start int,IN var_end int,OUT param1 int)
BEGIN
  /*
	客户输赢统计
*/
  IF ISNULL(date_begin) = 1 AND ISNULL(date_end) = 1 THEN
    SET date_begin = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      FF.loginaccount,
      FF.num,
      FF.employeecode,
      FF.employeetypename,
      GG.Bet_Money AS game_betting_amount,
      GG.net_money AS lose_win_amount,
      GG.Valid_Money * GG.bonus AS bonus,
      CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
        END AS dividend,
      CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
        END AS share,
      CASE
          WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
          ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
        END AS user_lose_win_amount

    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        CC.employeetype AS employeetypename,
        ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
        INNER JOIN enterprise_employee_type AS CC
          ON A.employeetypecode = CC.employeetypecode
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T004'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND DATE_FORMAT(C.Bet_Day, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode
    UNION ALL
    SELECT
      '直线会员' AS loginaccount,
      COUNT(1) AS num,
      employeecode AS employeecode,
      (SELECT
          employeetype
        FROM enterprise_employee_type
        WHERE employeetypecode = 'T003') AS employeetypename,
      GG.Bet_Money AS game_betting_amount,
      GG.net_money AS lose_win_amount,
      SUM(GG.Valid_Money * GG.bonus) AS bonus,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
        END) AS dividend,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
        END) AS share,
      SUM(CASE
          WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
          ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
        END) AS user_lose_win_amount
    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T003'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          C.Game_Platform,
          C.Game_Big_Type,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND DATE_FORMAT(C.Bet_Day, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode,
                 C.Game_Platform,
                 C.Game_Big_Type) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode) AS HH;


  SELECT
    FF.loginaccount,
    FF.num,
    FF.employeecode,
    FF.employeetypename,
    GG.Bet_Money AS game_betting_amount,
    GG.net_money AS lose_win_amount,
    GG.Valid_Money * GG.bonus AS bonus,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
      END AS dividend,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
      END AS share,
    CASE
        WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
        ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
      END AS user_lose_win_amount

  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      CC.employeetype AS employeetypename,
      ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
      INNER JOIN enterprise_employee_type AS CC
        ON A.employeetypecode = CC.employeetypecode
    WHERE A.parentemployeecode = employeecode
    AND A.employeetypecode = 'T004'
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.Bet_Money) AS Bet_Money,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
          AND DATE_FORMAT(C.Bet_Day, '%Y-%m-%d') BETWEEN date_begin AND date_end
      GROUP BY C.employeecode,
               C.enterprisecode,
               C.brandcode) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode
  UNION ALL
  SELECT
    loginaccount,
    num,
    employeecode,
    employeetypename,
    game_betting_amount,
    lose_win_amount,
    bonus,
    dividend,
    share,
    user_lose_win_amount
  FROM (SELECT
      '直线会员' AS loginaccount,
      COUNT(1) AS num,
      employeecode AS employeecode,
      '会员' AS employeetypename,
      GG.Bet_Money AS game_betting_amount,
      GG.net_money AS lose_win_amount,
      SUM(GG.Valid_Money * GG.bonus) AS bonus,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
        END) AS dividend,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
        END) AS share,
      SUM(CASE
          WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
          ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
        END) AS user_lose_win_amount
    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T003'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          C.Game_Platform,
          C.Game_Big_Type,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND DATE_FORMAT(C.Bet_Day, '%Y-%m-%d') BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode,
                 C.Game_Platform,
                 C.Game_Big_Type) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode) AS TT
  WHERE num > 0
  LIMIT var_start, var_end;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_negative_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_negative_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_negative_ranking`(IN employee_id varchar(10),IN start_end date,IN date_end date)
BEGIN
  /*
 客户输排名
*/
  SELECT
    B.User_Name,
    A.employeecode,
    SUM(B.Net_Money) AS Money,
    start_end AS date_ben,
    date_end AS date_end
  FROM (SELECT
      *
    FROM enterprise_employee
    WHERE parentemployeecode = employee_id
    AND logindatetime BETWEEN start_end AND date_end) AS A
    INNER JOIN betting_all_day AS B
      ON A.employeecode = B.employeecode
  GROUP BY B.User_Name,
           A.employeecode
  ORDER BY Money DESC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_profit
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_profit`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_profit`(IN employeecode varchar(10),IN date_begin date,IN date_end date,IN var_start int,IN var_end int)
BEGIN
  /*
	利润表
*/
  IF ISNULL(var_start) = 1 AND ISNULL(var_end) = 1 THEN
    SET var_start = 0;
    SET var_end = 10;
  END IF;

  IF ISNULL(date_begin) = 1 AND ISNULL(date_end) = 1 THEN
    SET date_begin = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    FF.loginaccount,
    FF.num,
    FF.employeecode,
    GG.Bet_Money,
    GG.net_money,
    GG.Valid_Money * GG.bonus AS bonus,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
      END AS dividend,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
      END AS share,
    CASE
        WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
        ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
      END AS user_lose_win_amount

  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE A.parentemployeecode = employeecode
    AND A.employeetypecode = 'T004'
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.Bet_Money) AS Bet_Money,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
          AND C.Bet_Day BETWEEN date_begin AND date_end
      GROUP BY C.employeecode,
               C.enterprisecode,
               C.brandcode) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode
  UNION ALL
  SELECT
    loginaccount,
    num,
    employeecode,
    bet_money,
    net_money,
    bonus,
    dividend,
    share,
    user_lose_win_amount
  FROM (SELECT
      '直线会员' AS loginaccount,
      COUNT(1) AS num,
      employeecode AS employeecode,
      GG.Bet_Money,
      GG.net_money,
      SUM(GG.Valid_Money * GG.bonus) AS bonus,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
        END) AS dividend,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
        END) AS share,
      SUM(CASE
          WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
          ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
        END) AS user_lose_win_amount
    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T003'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND C.Bet_Day BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode,
                 C.Game_Platform,
                 C.Game_Big_Type) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode) AS TT
  WHERE num > 0 LIMIT var_start, var_end;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_profit_agent
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_profit_agent`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_profit_agent`(IN employeecode varchar(10),IN date_begin date,IN date_end date,IN var_start int,IN var_end int,OUT param1 int)
BEGIN
  /*
	利润表
*/
  IF ISNULL(var_start) = 1 AND ISNULL(var_end) = 1 THEN
    SET var_start = 0;
    SET var_end = 10;
  END IF;

  IF ISNULL(date_begin) = 1 AND ISNULL(date_end) = 1 THEN
    SET date_begin = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  SELECT
    COUNT(1) INTO param1
  FROM (SELECT
      FF.loginaccount,
      FF.num,
      FF.employeecode,
      GG.Bet_Money,
      GG.net_money,
      GG.Valid_Money * GG.bonus AS bonus,
      CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
        END AS dividend,
      CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
        END AS share,
      CASE
          WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
          ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
        END AS user_lose_win_amount

    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T004'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND C.Bet_Day BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode
    UNION ALL
    SELECT
      '直线会员' AS loginaccount,
      COUNT(1) AS num,
      employeecode AS employeecode,
      GG.Bet_Money,
      GG.net_money,
      SUM(GG.Valid_Money * GG.bonus) AS bonus,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
        END) AS dividend,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
        END) AS share,
      SUM(CASE
          WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
          ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
        END) AS user_lose_win_amount
    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T003'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND C.Bet_Day BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode) AS HH;

  SELECT
    FF.loginaccount,
    FF.num,
    FF.employeecode,
    GG.Bet_Money,
    GG.net_money,
    GG.Valid_Money * GG.bonus AS bonus,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
      END AS dividend,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
      END AS share,
    CASE
        WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
        ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
      END AS user_lose_win_amount

  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE A.parentemployeecode = employeecode
    AND A.employeetypecode = 'T004'
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.Bet_Money) AS Bet_Money,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
          AND C.Bet_Day BETWEEN date_begin AND date_end
      GROUP BY C.employeecode,
               C.enterprisecode,
               C.brandcode) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode
  UNION ALL
  SELECT
    loginaccount,
    num,
    employeecode,
    bet_money,
    net_money,
    bonus,
    dividend,
    SHARE,
    user_lose_win_amount
  FROM (SELECT
      '直线会员' AS loginaccount,
      COUNT(1) AS num,
      employeecode AS employeecode,
      GG.Bet_Money,
      GG.net_money,
      SUM(GG.Valid_Money * GG.bonus) AS bonus,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
        END) AS dividend,
      SUM(CASE
          WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
        END) AS share,
      SUM(CASE
          WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
          ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
        END) AS user_lose_win_amount
    FROM (SELECT
        A.employeecode,
        A.enterprisecode,
        A.brandcode,
        A.loginaccount,
        A.employeetypecode,
        SUM(dividend) AS dividend,
        SUM(share) AS hare
      FROM enterprise_employee A
      WHERE A.parentemployeecode = employeecode
      AND A.employeetypecode = 'T003'
      GROUP BY A.loginaccount,
               A.employeecode,
               A.employeetypecode) FF
      INNER JOIN (SELECT
          C.employeecode,
          C.enterprisecode,
          C.brandcode,
          C.Game_Platform,
          C.Game_Big_Type,
          SUM(C.Bet_Money) AS Bet_Money,
          SUM(C.net_money) AS net_money,
          SUM(C.Valid_Money) AS Valid_Money,
          SUM(D.bonus) AS bonus
        FROM betting_all_day AS C
          INNER JOIN employee_gamecataloy_bonus AS D
            ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
            AND C.Bet_Day BETWEEN date_begin AND date_end
        GROUP BY C.employeecode,
                 C.enterprisecode,
                 C.brandcode,
                 C.Game_Platform,
                 C.Game_Big_Type) AS GG
        ON FF.employeecode = GG.employeecode
        AND FF.enterprisecode = GG.enterprisecode AND FF.brandcode = GG.brandcode) AS TT
  WHERE num > 0
  LIMIT var_start, var_end;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_profit_count
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_profit_count`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_profit_count`(IN employee_id varchar(10),IN begin_date date,IN end_date date,IN startd int,IN endsd int,
OUT param1 int, OUT param2 decimal, OUT param3 decimal, OUT param4 decimal, OUT param5 decimal, OUT param6 decimal,OUT param7 decimal)
BEGIN
  /*
 二次查询利润表
*/
  IF ISNULL(startd) = 1 AND ISNULL(endsd) = 1 THEN
    SET startd = 0;
    SET endsd = 10;
  END IF;
  IF ISNULL(begin_date) = 1 AND ISNULL(end_date) = 1 THEN
    SET begin_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET end_date = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    COUNT(1) ,HH.game_betting_amount,HH.lose_win_amount,HH.bonus,HH.dividend,HH.share,HH.profit_amount
   INTO param1,param2,param3,param4,param5,param6,param7

  FROM (SELECT
      GROUP_AA.loginaccount,
      ufn_down_recursion_team_of_agent_count(GROUP_AA.employeecode) AS num,
      GROUP_AA.employeecode,
      TT.employeetype AS employeetypename,
      Game_B.Bet_Money AS game_betting_amount,
      Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend) -
      ((Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share) AS lose_win_amount,
      Game_B.bonus,
      (Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend AS dividend,
      (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share AS share,
      ((Game_B.Net_Money + Game_B.bonus) + (Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend) * GROUP_AA.share AS profit_amount
    FROM enterprise_employee GROUP_AA
      INNER JOIN (SELECT
          A.enterprisecode,
          A.employeecode,
          A.parentemployeecode,
          A.Game_Platform,
          A.Game_Type,
          SUM(A.Bet_Money) AS Bet_Money,
          SUM(A.Net_Money) AS Net_Money,
          SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
        FROM betting_all_day AS A
          INNER JOIN employee_gamecataloy_bonus AS B
            ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
            AND A.Game_Big_Type = B.categorytype
            AND A.Bet_Day BETWEEN begin_date AND end_date
        GROUP BY A.enterprisecode,
                 A.employeecode,
                 A.parentemployeecode,
                 A.Game_Platform,
                 A.Game_Type) AS Game_B
        ON GROUP_AA.employeecode = Game_B.employeecode
        AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
        AND GROUP_AA.enterprisecode = Game_B.enterprisecode
      LEFT JOIN enterprise_employee_type AS TT
        ON GROUP_AA.employeetypecode = TT.employeetypecode
    WHERE GROUP_AA.parentemployeecode = employee_id
    AND GROUP_AA.employeetypecode = 'T004'
    UNION ALL
    SELECT
      '直线会员' AS loginaccount,
      SUM(num) AS num,
      employee_id AS employeecode,
      '会员' AS employeetypename,
      Bet_Money AS game_betting_amount,
      net_Money + bonus + dividend - share AS lose_win_amount,
      bonus,
      dividend,
      share,
      ((Net_Money + bonus) + (Net_Money + bonus) * dividend) * share AS profit_amount
    FROM (SELECT
        LINE_AA.loginaccount,
        COUNT(1) AS num,
        LINE_AA.employeecode,
        Game_B.Bet_Money,
        Game_B.net_Money,
        Game_B.bonus,
        (Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend AS dividend,
        (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend)) * LINE_AA.share AS share
      FROM enterprise_employee AS LINE_AA
        INNER JOIN (SELECT
            A.enterprisecode,
            A.employeecode,
            A.parentemployeecode,
            A.Game_Platform,
            A.Game_Type,
            SUM(A.Bet_Money) AS Bet_Money,
            SUM(A.Net_Money) AS Net_Money,
            SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
          FROM betting_all_day AS A
            INNER JOIN employee_gamecataloy_bonus AS B
              ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
              AND A.Game_Big_Type = B.categorytype
              AND A.Bet_Day BETWEEN begin_date AND end_date
          GROUP BY A.enterprisecode,
                   A.employeecode,
                   A.parentemployeecode,
                   A.Game_Platform,
                   A.Game_Type) AS Game_B
          ON LINE_AA.employeecode = Game_B.employeecode
          AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
          AND LINE_AA.enterprisecode = Game_B.enterprisecode
      WHERE LINE_AA.parentemployeecode = employee_id
      AND LINE_AA.employeetypecode = 'T003') AS FF
    WHERE FF.num > 0) AS HH;

  SELECT
    GROUP_AA.loginaccount,
    ufn_down_recursion_team_of_agent_count(GROUP_AA.employeecode) AS num,
    GROUP_AA.employeecode,
    TT.employeetype AS employeetypename,
    Game_B.Bet_Money AS game_betting_amount,
    Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend) -
    ((Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share) AS lose_win_amount,
    Game_B.bonus,
    (Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend AS dividend,
    (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend)) * GROUP_AA.share AS share,
    ((Game_B.Net_Money + Game_B.bonus) + (Game_B.Net_Money + Game_B.bonus) * GROUP_AA.dividend) * GROUP_AA.share AS profit_amount
  FROM enterprise_employee GROUP_AA
    INNER JOIN (SELECT
        A.enterprisecode,
        A.employeecode,
        A.parentemployeecode,
        A.Game_Platform,
        A.Game_Type,
        SUM(A.Bet_Money) AS Bet_Money,
        SUM(A.Net_Money) AS Net_Money,
        SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
      FROM betting_all_day AS A
        INNER JOIN employee_gamecataloy_bonus AS B
          ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
          AND A.Game_Big_Type = B.categorytype
          AND A.Bet_Day BETWEEN begin_date AND end_date
      GROUP BY A.enterprisecode,
               A.employeecode,
               A.parentemployeecode,
               A.Game_Platform,
               A.Game_Type) AS Game_B
      ON GROUP_AA.employeecode = Game_B.employeecode
      AND GROUP_AA.parentemployeecode = Game_B.parentemployeecode
      AND GROUP_AA.enterprisecode = Game_B.enterprisecode
    LEFT JOIN enterprise_employee_type AS TT
      ON GROUP_AA.employeetypecode = TT.employeetypecode
  WHERE GROUP_AA.parentemployeecode = employee_id
  AND GROUP_AA.employeetypecode = 'T004'
  UNION ALL
  SELECT
    loginaccount,
    num,
    employeecode,
    employeetypename,
    game_betting_amount,
    lose_win_amount,
    bonus,
    dividend,
    share,
    profit_amount
  FROM (SELECT
      '直线会员' AS loginaccount,
      SUM(num) AS num,
      employee_id AS employeecode,
      '会员' AS employeetypename,
      Bet_Money AS game_betting_amount,
      net_Money + bonus + dividend - share AS lose_win_amount,
      bonus,
      dividend,
      share,
      ((Net_Money + bonus) + (Net_Money + bonus) * dividend) * share AS profit_amount
    FROM (SELECT
        LINE_AA.loginaccount,
        COUNT(1) AS num,
        LINE_AA.employeecode,
        Game_B.Bet_Money,
        Game_B.net_Money,
        Game_B.bonus,
        (Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend AS dividend,
        (Game_B.Net_Money + Game_B.bonus + ((Game_B.Net_Money + Game_B.bonus) * LINE_AA.dividend)) * LINE_AA.share AS share
      FROM enterprise_employee AS LINE_AA
        INNER JOIN (SELECT
            A.enterprisecode,
            A.employeecode,
            A.parentemployeecode,
            A.Game_Platform,
            A.Game_Type,
            SUM(A.Bet_Money) AS Bet_Money,
            SUM(A.Net_Money) AS Net_Money,
            SUM(A.Valid_Money) * SUM(B.bonus) AS bonus
          FROM betting_all_day AS A
            INNER JOIN employee_gamecataloy_bonus AS B
              ON A.employeecode = B.employeecode AND A.Game_Platform = B.gametype
              AND A.Game_Big_Type = B.categorytype
              AND A.Bet_Day BETWEEN begin_date AND end_date
          GROUP BY A.enterprisecode,
                   A.employeecode,
                   A.parentemployeecode,
                   A.Game_Platform,
                   A.Game_Type) AS Game_B
          ON LINE_AA.employeecode = Game_B.employeecode
          AND LINE_AA.parentemployeecode = Game_B.parentemployeecode
          AND LINE_AA.enterprisecode = Game_B.enterprisecode
      WHERE LINE_AA.parentemployeecode = employee_id
      AND LINE_AA.employeetypecode = 'T003') AS FF
    WHERE FF.num > 0) AS TT
  WHERE num > 0 LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_profit_member
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_profit_member`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_profit_member`(IN employee_id varchar(10), IN start_date date,IN date_end date,IN startd int,IN endsd int,
OUT param1 int, OUT param2 decimal, OUT param3 decimal, OUT param4 decimal, OUT param5 decimal, OUT param6 decimal,OUT param7 decimal)
BEGIN
  /*
 直线会员报表显示
*/
  IF ISNULL(startd) = 1 AND ISNULL(endsd) = 1 THEN
    SET startd = 0;
    SET endsd = 10;
  END IF;
  IF ISNULL(start_date) = 1 AND ISNULL(date_end) = 1 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    COUNT(1) ,HH.game_betting_amount,HH.lose_win_amount,HH.bonus,HH.dividend,HH.share,HH.profit_amount
   INTO param1,param2,param3,param4,param5,param6,param7

  FROM (SELECT
      AA.employeecode,
      AA.loginaccount,
      COUNT(1) AS num,
      GG.Bet_Money AS game_betting_amount,
      GG.Net_Money + GG.Valid_Money * GG.bonus + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend + ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS lose_win_amount,
      GG.Valid_Money * GG.bonus AS bonus,
      (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend AS Dividend,
(GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend AS share,

      ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS profit_amount
    FROM enterprise_employee AS AA
      INNER JOIN (SELECT
          BB.employeecode,
          BB.parentemployeecode,
          BB.enterprisecode,
          BB.Game_Platform,
          BB.Game_Type,
          SUM(BB.Bet_Money) AS Bet_Money,
          SUM(BB.Net_Money) AS Net_Money,
          SUM(BB.Valid_Money) AS Valid_Money,
          SUM(DD.bonus) AS bonus
        FROM betting_all_day BB
          INNER JOIN employee_gamecataloy_bonus AS DD
            ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
            AND BB.Bet_Day BETWEEN start_date AND date_end
        GROUP BY BB.employeecode,
                 BB.parentemployeecode,
                 BB.enterprisecode,
                 BB.Game_Platform,
                 BB.Game_Type) AS GG
        ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
    WHERE AA.parentemployeecode = employee_id
    AND AA.employeetypecode = 'T003'
    GROUP BY AA.employeecode,
             AA.loginaccount) AS HH;

  SELECT
    AA.employeecode,
    AA.loginaccount,
    COUNT(1) AS num,
    CONCAT(TT.employeetype, '_nodown') AS employeetypename,
    GG.Bet_Money AS game_betting_amount,
    GG.Net_Money + GG.Valid_Money * GG.bonus + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend + ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS lose_win_amount,
    GG.Valid_Money * GG.bonus AS bonus,
    (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend AS dividend,
    ((GG.Net_Money + GG.Valid_Money * GG.bonus) + (GG.Net_Money + GG.Valid_Money * GG.bonus) * AA.dividend) * AA.share AS profit_amount
  FROM enterprise_employee AS AA
    INNER JOIN (SELECT
        BB.employeecode,
        BB.parentemployeecode,
        BB.enterprisecode,
        BB.Game_Platform,
        BB.Game_Type,
        SUM(BB.Bet_Money) AS Bet_Money,
        SUM(BB.Net_Money) AS Net_Money,
        SUM(BB.Valid_Money) AS Valid_Money,
        SUM(DD.bonus) AS bonus
      FROM betting_all_day BB
        INNER JOIN employee_gamecataloy_bonus AS DD
          ON BB.employeecode = DD.employeecode AND BB.Game_Platform = DD.gametype AND BB.Game_Big_Type = DD.categorytype
          AND BB.Bet_Day BETWEEN start_date AND date_end
      GROUP BY BB.employeecode,
               BB.parentemployeecode,
               BB.enterprisecode,
               BB.Game_Platform,
               BB.Game_Type) AS GG
      ON AA.employeecode = GG.employeecode AND AA.parentemployeecode = GG.parentemployeecode AND AA.enterprisecode = GG.enterprisecode
    LEFT JOIN enterprise_employee_type AS TT
      ON AA.employeetypecode = TT.employeetypecode
  WHERE AA.parentemployeecode = employee_id
  AND AA.employeetypecode = 'T003'
  GROUP BY AA.employeecode,
           AA.loginaccount LIMIT startd, endsd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_registered
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_registered`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_registered`(IN employee_id varchar(10),IN num_id int)
BEGIN
  /*
	客户注册统计
*/
  DECLARE employeeid varchar(8);
  DECLARE rangetime date;
  DECLARE q_date_begin date;
  DECLARE q_date_end date;
  DECLARE start_date date;
  DECLARE date_end date;

  IF ISNULL(num_id) = 1 OR num_id = 1 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 7 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  IF num_id = 2 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 15 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;
  IF num_id = 3 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;


  SET rangetime = start_date;
  DROP TABLE IF EXISTS temp_employeecode;
  CREATE TEMPORARY TABLE temp_employeecode (
    startDate date NOT NULL,
    endDate date NOT NULL,
    num int,
    employeetypename varchar(6)
  )
  DEFAULT charset = utf8;
  WHILE rangetime < date_end DO
    SET q_date_begin = ADDDATE(rangetime, INTERVAL 1 DAY);
    SET q_date_end = ADDDATE(rangetime, INTERVAL 1 DAY);
    SET rangetime = q_date_end;
    -- 直线
    INSERT INTO temp_employeecode (startDate, endDate, num, employeetypename)
      VALUES (q_date_begin, q_date_end, (SELECT COUNT(1) FROM enterprise_employee A WHERE A.parentemployeecode = employee_id AND A.datastatus = 1 AND DATE_FORMAT(A.logindatetime, '%Y-%m-%d') BETWEEN q_date_begin AND q_date_end), 'member');


    -- 团队
    INSERT INTO temp_employeecode (startDate, endDate, num, employeetypename)
      VALUES (q_date_begin, q_date_end, (SELECT COUNT(1) FROM enterprise_employee A WHERE FIND_IN_SET(A.parentemployeecode, (SELECT ufn_down_recursion_team_of_agent(ear.employeecode) AS code_id FROM enterprise_employee ear WHERE ear.employeecode = employee_id)) > 0 AND A.datastatus = 1 AND DATE_FORMAT(A.logindatetime, '%Y-%m-%d') BETWEEN q_date_begin AND q_date_end), 'user');

  END WHILE;

  SELECT
    startDate,
    endDate,
    num,
    employeetypename
  FROM temp_employeecode
  ORDER BY employeetypename, startDate;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_registered_bak
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_registered_bak`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_registered_bak`(IN employee_id varchar(10),IN timeStap int,IN start_date date,IN date_end date)
BEGIN
  /*
	客户注册统计
*/
  DECLARE employeeid varchar(8);
  DECLARE row_num int;
  DECLARE rangetime date;
  DECLARE q_date_begin date;
  DECLARE q_date_end date;

  SET rangetime = start_date;
  DROP TABLE IF EXISTS temp_employeecode;
  CREATE TEMPORARY TABLE temp_employeecode (
    begin_date varchar(20) NOT NULL,
    end_date varchar(20) NOT NULL,
    rownum int,
    falg varchar(6)
  )
  DEFAULT charset = utf8;
  WHILE rangetime < date_end DO
    SET q_date_begin = ADDDATE(rangetime, INTERVAL 1 DAY);
    SET q_date_end = ADDDATE(rangetime, INTERVAL timeStap DAY);
    SET rangetime = q_date_end;
    -- 直线
    INSERT INTO temp_employeecode (begin_date, end_date, rownum, falg)
      VALUES (q_date_begin, q_date_end, (SELECT COUNT(1) FROM enterprise_employee A WHERE A.parentemployeecode = employee_id AND DATE(A.logindatetime) BETWEEN q_date_begin AND q_date_end), '会员');


    -- 团队
    INSERT INTO temp_employeecode (begin_date, end_date, rownum, falg)
      VALUES (q_date_begin, q_date_end, (SELECT COUNT(1) FROM enterprise_employee A WHERE FIND_IN_SET(A.parentemployeecode, (SELECT ufn_down_recursion_team_of_agent(ear.employeecode) AS code_id FROM enterprise_employee ear WHERE ear.employeecode = employee_id)) AND DATE(A.logindatetime) BETWEEN q_date_begin AND q_date_end), '代理');

  END WHILE;

  SELECT
    begin_date,
    end_date,
    rownum,
    falg
  FROM temp_employeecode
  ORDER BY falg, begin_date;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_registered_copy
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_registered_copy`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_registered_copy`(IN employee_id varchar(10),
IN start_date date,
IN date_end date)
BEGIN
  /*
	客户注册统计
*/
  DECLARE employeeid varchar(8);
  DECLARE rangetime date;
  DECLARE q_date_begin date;
  DECLARE q_date_end date;

  IF ISNULL(start_date) = 1 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 7 DAY);
  END IF;
  IF ISNULL(date_end) = 1 THEN
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;


  SET rangetime = start_date;
  DROP TABLE IF EXISTS temp_employeecode;
  CREATE TEMPORARY TABLE temp_employeecode (
    startDate date NOT NULL,
    endDate date NOT NULL,
    num int,
    employeetypename varchar(6)
  )
  DEFAULT charset = utf8;
  WHILE rangetime < date_end DO
    SET q_date_begin = ADDDATE(rangetime, INTERVAL 1 DAY);
    SET q_date_end = ADDDATE(rangetime, INTERVAL 1 DAY);
    SET rangetime = q_date_end;
    -- 直线
    INSERT INTO temp_employeecode (startDate, endDate, num, employeetypename)
      VALUES (q_date_begin, q_date_end, (SELECT COUNT(1) FROM enterprise_employee A WHERE A.parentemployeecode = employee_id AND A.employeetypecode = 'T003' AND DATE(A.logindatetime) BETWEEN q_date_begin AND q_date_end), '会员');


    -- 团队
    INSERT INTO temp_employeecode (startDate, endDate, num, employeetypename)
      VALUES (q_date_begin, q_date_end, (SELECT COUNT(1) FROM enterprise_employee A WHERE FIND_IN_SET(A.parentemployeecode, (SELECT ufn_down_recursion_team_of_agent(ear.employeecode) AS code_id FROM enterprise_employee ear WHERE ear.employeecode = employee_id)) AND DATE(A.logindatetime) BETWEEN q_date_begin AND q_date_end), '代理');

  END WHILE;

  SELECT
    startDate,
    endDate,
    num,
    employeetypename
  FROM temp_employeecode
  ORDER BY employeetypename, startDate;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_wins_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_wins_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_wins_ranking`(IN employee_id varchar(10),IN start_end date,IN date_end date)
BEGIN
  /*
 客户赢排名
*/
  SELECT
    User_Name,
    employeecode,
    NetMoney,
    start_date,
    end_date
  FROM (SELECT
      B.User_Name,
      A.employeecode,
      SUM(B.Net_Money) AS NetMoney,
      start_end AS start_date,
      date_end AS end_date
    FROM (SELECT
        *
      FROM enterprise_employee
      WHERE parentemployeecode = employee_id
      AND logindatetime BETWEEN start_end AND date_end) AS A
      INNER JOIN betting_all_day AS B
        ON A.employeecode = B.employeecode
    GROUP BY B.User_Name,
             A.employeecode) AS HH
  WHERE NetMoney > 0
  ORDER BY NetMoney ASC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_user_win_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_user_win_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_user_win_ranking`(IN employee_id varchar(10),IN start_end date,IN date_end date)
BEGIN
  /*
 客户赢排名
*/
  IF ISNULL(start_end) = 1 AND ISNULL(date_end) = 1 THEN
    SET start_end = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET date_end = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;

  SELECT
    User_Name,
    employeecode,
    NetMoney,
    start_date,
    end_date
  FROM (SELECT
      B.User_Name,
      A.employeecode,
      SUM(B.Net_Money) AS NetMoney,
      start_end AS start_date,
      date_end AS end_date
    FROM (SELECT
        *
      FROM enterprise_employee
      WHERE parentemployeecode = employee_id) AS A
      INNER JOIN betting_all_day AS B
        ON A.employeecode = B.employeecode
    WHERE B.Bet_Day BETWEEN start_end AND date_end
    GROUP BY B.User_Name,
             A.employeecode) AS HH
  WHERE NetMoney > 0
  ORDER BY NetMoney DESC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_win_and_lose_customer_statistics
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_win_and_lose_customer_statistics`;
DELIMITER ;;
CREATE PROCEDURE `usp_win_and_lose_customer_statistics`(IN employeecode varchar(10),IN date_begin date,IN date_end date,IN var_start int,IN var_end int)
BEGIN
  /*
	客户输赢统计
*/

  SELECT
    FF.loginaccount,
    FF.num,
    FF.employeecode,
    GG.Bet_Money,
    GG.net_money,
    GG.Valid_Money * GG.bonus AS bonus_money,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
      END AS dividend_money,
    CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
      END AS hare_money,
    CASE
        WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
        ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
      END AS money

  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      ufn_down_recursion_team_of_agent_count(A.employeecode) AS num,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE A.parentemployeecode = employeecode
    AND A.employeetypecode = 'T004'
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.Bet_Money) AS Bet_Money,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Type = D.categorytype
          AND C.Bet_Day BETWEEN date_begin AND date_end
      GROUP BY C.employeecode,
               C.enterprisecode,
               C.brandcode) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode
  UNION ALL
  SELECT
    '直线会员' AS loginaccount,
    COUNT(1) AS num,
    employeecode AS employeecode,
    GG.Bet_Money,
    GG.net_money,
    SUM(GG.Valid_Money * GG.bonus) AS bonus_money,
    SUM(CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend ELSE 0
      END) AS dividend_money,
    SUM(CASE
        WHEN GG.net_money > 0 THEN (GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money - (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare ELSE (GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare
      END) AS hare_money,
    SUM(CASE
        WHEN GG.net_money > 0 THEN GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend) -
        ((GG.net_money + (GG.Valid_Money * GG.bonus) + ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.dividend)) * FF.hare) ELSE GG.net_money + (GG.Valid_Money * GG.bonus) - ((GG.net_money + (GG.Valid_Money * GG.bonus)) * FF.hare)
      END) AS money
  FROM (SELECT
      A.employeecode,
      A.enterprisecode,
      A.brandcode,
      A.loginaccount,
      A.employeetypecode,
      SUM(dividend) AS dividend,
      SUM(share) AS hare
    FROM enterprise_employee A
    WHERE A.parentemployeecode = employeecode
    AND A.employeetypecode = 'T003'
    GROUP BY A.loginaccount,
             A.employeecode,
             A.employeetypecode) FF
    INNER JOIN (SELECT
        C.employeecode,
        C.enterprisecode,
        C.brandcode,
        SUM(C.Bet_Money) AS Bet_Money,
        SUM(C.net_money) AS net_money,
        SUM(C.Valid_Money) AS Valid_Money,
        SUM(D.bonus) AS bonus
      FROM betting_all_day AS C
        INNER JOIN employee_gamecataloy_bonus AS D
          ON C.employeecode = D.employeecode AND C.Game_Platform = D.gametype AND C.Game_Big_Type = D.categorytype
          AND C.Bet_Day BETWEEN date_begin AND date_end
      GROUP BY C.employeecode,
               C.enterprisecode,
               C.brandcode) AS GG
      ON FF.employeecode = GG.employeecode
      AND FF.enterprisecode = GG.enterprisecode
  LIMIT var_start, var_end;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_withdrawals_number_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_withdrawals_number_ranking`;
DELIMITER ;;
CREATE PROCEDURE `usp_withdrawals_number_ranking`(IN login varchar(10),IN start_date date,IN end_date date)
BEGIN
  /*
  取款次数排名
*/
  DECLARE rownum int DEFAULT 0;

  IF ISNULL(start_date) = 1 AND ISNULL(end_date) = 1 THEN
    SET start_date = DATE_SUB(DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d'), INTERVAL 30 DAY);
    SET end_date = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d');
  END IF;


  SELECT
    GG.employeecode,
    GG.loginaccount,
    DD.num,
    start_date AS start_date,
    end_date AS end_date
  FROM (SELECT
      employeecode,
      loginaccount
    FROM enterprise_employee AA
    WHERE parentemployeecode = login) AS GG
    LEFT JOIN (SELECT
        employeecode,
        COUNT(1) AS num
      FROM deposit_withdraw_order
      WHERE ordertype = 2
      AND orderstatus = '2'
      AND DATE_FORMAT(orderdate, '%Y-%m-%d') BETWEEN start_date AND end_date
      GROUP BY employeecode) AS DD
      ON GG.employeecode = DD.employeecode
  ORDER BY num DESC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for usp_withdrawals_pay
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_withdrawals_pay`;
DELIMITER ;;
CREATE PROCEDURE `usp_withdrawals_pay`(IN login varchar(20),IN start_end date,IN date_end date)
BEGIN
  /*
  取款次数排名
*/
  SELECT
    GG.employeecode,
    GG.loginaccount,
    COUNT(1) AS num,
    start_date AS start_date,
    end_date AS end_date
  FROM (SELECT
      employeecode,
      loginaccount
    FROM enterprise_employee AA
    WHERE parentemployeecode = login) AS GG
    LEFT JOIN deposit_withdraw_order AS DD
      ON GG.employeecode = DD.employeecode
      AND DD.orderdate BETWEEN start_date AND end_date
      AND DD.ordertype = 2
  GROUP BY GG.employeecode,
           GG.loginaccount
  ORDER BY num DESC LIMIT 0, 10;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for func_enterprisecode_exist
-- ----------------------------
DROP FUNCTION IF EXISTS `func_enterprisecode_exist`;
DELIMITER ;;
CREATE FUNCTION `func_enterprisecode_exist`(enterprise_id  VARCHAR(10)) RETURNS tinyint(1)
    READS SQL DATA
BEGIN
	DECLARE   log_row  boolean DEFAULT FALSE;
	-- 查询品牌是否存在企业，否则不能删除
		SELECT IF(count(1) > 0,TRUE,FALSE) INTO log_row
			FROM enterprise_operating_brand
		 WHERE enterprisecode = enterprise_id
			 AND datastatus = 1 LIMIT 1;

	-- 查询用户是否存在企业，否则不能删除
		SELECT IF(count(1) > 0,TRUE,FALSE) INTO log_row
			FROM enterprise_employee
		 WHERE enterprisecode = enterprise_id
			 AND datastatus = 1 LIMIT 1;

	-- 查询银行是否存在企业，否则不能删除
		SELECT IF(count(1) > 0,TRUE,FALSE) INTO log_row
			FROM enterprise_information
		 WHERE enterprisecode = enterprise_id
			 AND datastatus = 1 LIMIT 1;

	RETURN log_row;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for tool_jsonf_split
-- ----------------------------
DROP FUNCTION IF EXISTS `tool_jsonf_split`;
DELIMITER ;;
CREATE FUNCTION `tool_jsonf_split`(f_string longtext,f_delimiter varchar(5),f_order int) RETURNS varchar(255) CHARSET utf8
    NO SQL
BEGIN 
    # 拆分传入的字符串，返回拆分后的新字符串 
        declare result varchar(255) default ''; 
        set result = reverse(substring_index(reverse(substring_index(f_string,f_delimiter,f_order)),f_delimiter,1)); 
        return result; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for tool_jsonf_totallength
-- ----------------------------
DROP FUNCTION IF EXISTS `tool_jsonf_totallength`;
DELIMITER ;;
CREATE FUNCTION `tool_jsonf_totallength`(f_string longtext,f_delimiter varchar(5)) RETURNS int(11)
    NO SQL
BEGIN 
    # 计算传入字符串的总length 
    return 1+(length(f_string) - length(replace(f_string,f_delimiter,''))); 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for tool_map_get
-- ----------------------------
DROP FUNCTION IF EXISTS `tool_map_get`;
DELIMITER ;;
CREATE FUNCTION `tool_map_get`( map varchar(500), inputKey varchar(50) ) RETURNS varchar(255) CHARSET utf8
    NO SQL
BEGIN  
    DECLARE rowSeperator char(1) default ',';  -- 行分隔符  
    DECLARE fieldSeperator char(1) default ':';  --  键值对分隔符  
  
    DECLARE tempMap varchar(255) default map;  
    DECLARE isEnd boolean default false;  
  
    DECLARE rowIndex int default 0;  
    DECLARE pair varchar(255);  
    DECLARE pairIndex varchar(255);  
    DECLARE strKey varchar(255);  
    DECLARE strValue varchar(255);  
  
    WHILE isEnd = false do  
          
        set rowIndex = locate(rowSeperator,tempMap);  
        if rowIndex > 0 then  
            set pair = substring(tempMap,1,rowIndex-1);  
            set tempMap = substring(tempMap,rowIndex+1,9999999);  
        else  
            set pair = tempMap;  
            set isEnd = true;  
        end if;  
  
        set pairIndex = locate(fieldSeperator,pair);  
        if pairIndex > 0 then  
            set strKey = substring(pair,1,pairIndex-1);  
            set strValue = substring(pair,pairIndex+1,9999999);  
            if inputKey = strKey then  
                return strValue;  
            end if;  
        end if;  
          
    END WHILE;  
    return null;                                
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for tool_map_get_number
-- ----------------------------
DROP FUNCTION IF EXISTS `tool_map_get_number`;
DELIMITER ;;
CREATE FUNCTION `tool_map_get_number`( map varchar(500), inputKey varchar(50) ) RETURNS decimal(10,0)
    NO SQL
BEGIN  
return cast(tool_map_get(map,inputKey) AS DECIMAL );  
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for tool_map_key
-- ----------------------------
DROP FUNCTION IF EXISTS `tool_map_key`;
DELIMITER ;;
CREATE FUNCTION `tool_map_key`(jsonfstr varchar(500),keyindex int) RETURNS varchar(50) CHARSET utf8
    NO SQL
BEGIN
  -- 处理字符中转变量
  DECLARE handle_jsonstr varchar(500);
RETURN 1;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_count_employee_alluplevels_length
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_count_employee_alluplevels_length`;
DELIMITER ;;
CREATE FUNCTION `ufn_count_employee_alluplevels_length`(p_employeecode char(8)) RETURNS int(11)
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE r_length INT;
        SET r_length := 0;
        
    SELECT LENGTH(employeeallupleves) INTO r_length
	  FROM enterprise_employee_all_uplevels
	 WHERE employeecode = p_employeecode;
    
RETURN r_length;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_down_recursion_team_of_agent
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_down_recursion_team_of_agent`;
DELIMITER ;;
CREATE FUNCTION `ufn_down_recursion_team_of_agent`(in_parentemployeecode CHAR(8)) RETURNS longtext CHARSET utf8
    READS SQL DATA
BEGIN
   DECLARE l_parentemployeecode LONGTEXT;
   DECLARE lc_parentemployeecode LONGTEXT;
    
   SET l_parentemployeecode = in_parentemployeecode;
   SET lc_parentemployeecode = in_parentemployeecode;


   LABLE_LP: LOOP
   SELECT group_concat(employeecode separator ',') into lc_parentemployeecode from enterprise_employee WHERE FIND_IN_SET(parentemployeecode,lc_parentemployeecode) >0 AND employeetypecode !='T003' and datastatus=1;
      IF lc_parentemployeecode IS NULL OR lc_parentemployeecode = 'NULL' THEN
         LEAVE LABLE_LP;
      END IF;
      SET l_parentemployeecode = CONCAT(l_parentemployeecode,',',lc_parentemployeecode);
   END LOOP LABLE_LP;
 
  RETURN l_parentemployeecode;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_down_recursion_team_of_agent_number
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_down_recursion_team_of_agent_number`;
DELIMITER ;;
CREATE FUNCTION `ufn_down_recursion_team_of_agent_number`(in_parentemployeecode CHAR(8)) RETURNS int(11)
    READS SQL DATA
BEGIN
   DECLARE l_parentemployeecode 	LONGTEXT;
   DECLARE lc_parentemployeecode 	LONGTEXT;
	 DECLARE group_row							INT;
    
   SET l_parentemployeecode = in_parentemployeecode;
   SET lc_parentemployeecode = in_parentemployeecode;


   LABLE_LP: LOOP
   SELECT group_concat(employeecode separator ',') into lc_parentemployeecode from enterprise_employee 
         WHERE FIND_IN_SET(parentemployeecode,lc_parentemployeecode) >0 AND employeetypecode !='T003';
      IF lc_parentemployeecode IS NULL OR lc_parentemployeecode = 'NULL' THEN
         LEAVE LABLE_LP;
      END IF;
      SET l_parentemployeecode = CONCAT(l_parentemployeecode,',',lc_parentemployeecode);
   END LOOP LABLE_LP;
	 
	 SELECT count(1) INTO group_row from enterprise_employee where parentemployeecode in 
		(SELECT ee.employeecode
							FROM enterprise_employee ee
						 WHERE FIND_IN_SET(ee.employeecode,l_parentemployeecode) > 0 
		AND ee.employeetypecode !='T003');
  RETURN group_row;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_etl_amount_from_api_ttg_gameinfo
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_etl_amount_from_api_ttg_gameinfo`;
DELIMITER ;;
CREATE FUNCTION `ufn_etl_amount_from_api_ttg_gameinfo`(p_handId VARCHAR(50), p_transactionSubType VARCHAR(50)) RETURNS varchar(50) CHARSET utf8
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE l_amount VARCHAR(50);
    SELECT amount INTO l_amount
      FROM api_ttg_gameinfo
	 WHERE handId = p_handId
	   AND transactionSubType = p_transactionSubType;
RETURN l_amount;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_get_employee_all_uplevels
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_get_employee_all_uplevels`;
DELIMITER ;;
CREATE FUNCTION `ufn_get_employee_all_uplevels`(in_employeecode CHAR(8)) RETURNS varchar(1000) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
   DECLARE l_employeecode CHAR(8);
   DECLARE l_all_uplevels VARCHAR(1000);
   SET l_employeecode = in_employeecode;
   SET l_all_uplevels = CONCAT(',');
   
   LP: LOOP
      SELECT parentemployeecode INTO l_employeecode
        FROM enterprise_employee
	   WHERE employeecode=l_employeecode;
       
       IF l_employeecode <> in_employeecode THEN
          SET l_all_uplevels := CONCAT(',', l_employeecode, l_all_uplevels);
       END IF;
       
       IF l_employeecode = in_employeecode OR l_employeecode = '00000000' THEN
          LEAVE LP;
	   END IF;
   END LOOP;
   
   SET l_all_uplevels := CONCAT(l_all_uplevels,in_employeecode,',');
   
   RETURN l_all_uplevels;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_get_gamebigtype_from_tag
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_get_gamebigtype_from_tag`;
DELIMITER ;;
CREATE FUNCTION `ufn_get_gamebigtype_from_tag`(platformType VARCHAR(32), gameType VARCHAR(32)) RETURNS varchar(5) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
   DECLARE l_find_gameType VARCHAR(33);
   DECLARE l_gamebigtype VARCHAR(5);
   DECLARE l_SX VARCHAR(1000);
   DECLARE l_DZ VARCHAR(1000);
   DECLARE l_TY VARCHAR(1000);
   
   SET l_find_gameType = CONCAT(',',gameType,',');
   SET l_SX = ',BAC,CBAC,LINK,DT,SHB,ROU,FT,';
   SET l_TY = ',FIFA,';
   
   IF platformType = 'PNG' THEN
      SET l_gamebigtype = 'DZ';
   ELSE
      IF LOCATE(l_find_gameType, l_SX )>0 THEN
	     SET l_gamebigtype = 'SX';
	  ELSEIF LOCATE(l_find_gameType, l_TY )>0 THEN
	     SET l_gamebigtype = 'TY';
	  ELSE
	     SET l_gamebigtype = 'DZ';
	  END IF;
   END IF;
   
   RETURN l_gamebigtype;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_get_next_string
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_get_next_string`;
DELIMITER ;;
CREATE FUNCTION `ufn_get_next_string`(param_src_string varchar(128), param_dest_length tinyint) RETURNS varchar(128) CHARSET utf8 COLLATE utf8_bin
    DETERMINISTIC
BEGIN
  ## 定义常量字符串
  DECLARE vch_constant varchar(36) DEFAULT '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';

  ## 定义本地字符串
  DECLARE vch_source,
          vch_dest,
          vch_end varchar(128) DEFAULT '';

  ## 起始字符,结束字符
  DECLARE ch_start char(1) DEFAULT '0';
  DECLARE ch_end char(1) DEFAULT '';

  ## 定义参与计算的变量
  DECLARE int_string_len int DEFAULT 1;
  DECLARE int_search_char_position int DEFAULT 1;
  DECLARE int_loop int DEFAULT 1;

  SET vch_source = UPPER(param_src_string);
  SET int_string_len = LENGTH(vch_source);

#SET vch_dest = vch_source;

END_LOOP:
WHILE int_loop <= int_string_len + 1
  DO
BEGIN
  IF int_loop > int_string_len THEN
  BEGIN
    SET vch_source = CONCAT('0', vch_source);
    SET int_string_len = int_string_len + 1;
  END;
  END IF;

  SET ch_end = RIGHT(vch_source, 1);
  IF ch_end = 'Z' THEN
  BEGIN
    SET vch_source = SUBSTRING(vch_source, 1, int_string_len - int_loop);
    SET vch_end = CONCAT(vch_end, '0');
  END;
  ELSE
  BEGIN
    SET int_search_char_position = INSTR(vch_constant, ch_end);
    SET vch_dest = CONCAT(SUBSTRING(vch_source, 1, int_string_len - int_loop), SUBSTRING(vch_constant, int_search_char_position + 1, 1), IFNULL(vch_end, ''));
    LEAVE END_LOOP;
  END;
  END IF;

  SET int_loop = int_loop + 1;
END;
END WHILE;

  -- 判断目标字符串是否为NULL或空串
  IF IFNULL(vch_dest, '') = '' THEN
  BEGIN
    SET int_string_len = 1;
    SET vch_dest = '0';
  END;
  END IF;

  IF param_dest_length > int_string_len THEN
  BEGIN
    SET vch_dest = LPAD(vch_dest, param_dest_length, '0');
  END;
  END IF;

  RETURN vch_dest;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_get_next_string_by_tablename
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_get_next_string_by_tablename`;
DELIMITER ;;
CREATE FUNCTION `ufn_get_next_string_by_tablename`(param_tablename varchar(128), param_performancefactor tinyint) RETURNS varchar(16) CHARSET utf8 COLLATE utf8_bin
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
  ## 为了并发线程安全，锁定修改的记录
  DECLARE vch_dest varchar(16);

  UPDATE table_pk_seed
  SET seed = ufn_get_next_string(seed, LENGTH(seed))
  WHERE tablename = param_tablename
  AND performancefactor = param_performancefactor;

  SELECT
    CONCAT(codeprefix, seed) INTO vch_dest
  FROM table_pk_seed
  WHERE tablename = param_tablename
  AND performancefactor = param_performancefactor
  FOR UPDATE;

  RETURN vch_dest;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_min_transactionId_from_api_ttg_gameinfo
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_min_transactionId_from_api_ttg_gameinfo`;
DELIMITER ;;
CREATE FUNCTION `ufn_min_transactionId_from_api_ttg_gameinfo`(p_handId VARCHAR(50)) RETURNS bigint(20)
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE l_transactionId BIGINT;
    SELECT MIN(transaction_Id) INTO l_transactionId
      FROM betting_ttg
	 WHERE hand_Id = p_handId;
RETURN l_transactionId;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_statistic_count_of_next_level
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_statistic_count_of_next_level`;
DELIMITER ;;
CREATE FUNCTION `ufn_statistic_count_of_next_level`(in_employeetypecode char(4), ## T003, 统计会员  T003, 统计代理
in_parentemployeecode char(8)) RETURNS int(11)
    READS SQL DATA
BEGIN
  DECLARE l_count int;

  SELECT
    COUNT(1) INTO l_count
  FROM enterprise_employee
  WHERE parentemployeecode = in_parentemployeecode AND employeetypecode = in_employeetypecode AND datastatus = 1;

  RETURN l_count;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_tn_person_balances_jifen_parentcode
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_tn_person_balances_jifen_parentcode`;
DELIMITER ;;
CREATE FUNCTION `ufn_tn_person_balances_jifen_parentcode`(parentemployeecode_IN CHAR(8)) RETURNS double
    READS SQL DATA
RETURN (select IFNULL(sum(balance),0) from enterprise_employee_capital_account where parentemployeecode = parentemployeecode_IN)
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_tn_person_balances_yuanbao_parentcode
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_tn_person_balances_yuanbao_parentcode`;
DELIMITER ;;
CREATE FUNCTION `ufn_tn_person_balances_yuanbao_parentcode`(parentemployeecode_IN CHAR(8)) RETURNS double
    READS SQL DATA
RETURN (select IFNULL(sum(balance),0) from baccarath5_balance where parentemployeecode = parentemployeecode_IN)
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_tn_person_betting
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_tn_person_betting`;
DELIMITER ;;
CREATE FUNCTION `ufn_tn_person_betting`(in_employeecode char(8), in_begintime datetime, in_endtime datetime) RETURNS decimal(16,2)
    READS SQL DATA
BEGIN
  DECLARE rt_netmoney decimal(16, 2) DEFAULT 0;
  -- 查询用户某段时间的投注量
  SELECT
    SUM(stat.netMoney) INTO rt_netmoney
  FROM (SELECT
      bha.bet_Amount AS netMoney
    FROM betting_hq_ag bha
    WHERE bha.bet_Time >= in_begintime AND bha.bet_Time < in_endtime AND bha.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhb.bbin_Bet_Amount AS netMoney
    FROM betting_hq_bbin bhb
    WHERE bhb.bbin_Wagers_Date >= in_begintime AND bhb.bbin_Wagers_Date < in_endtime AND bhb.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhq.Betting_Credits AS netMoney
    FROM betting_hq_nhq bhq
    WHERE bhq.Betting_Date >= in_begintime AND bhq.Betting_Date< in_endtime AND bhq.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhoa.aoi_Betting_Amount AS netMoney
    FROM betting_hq_og_ag bhoa
    WHERE bhoa.aoi_Add_Time >= in_begintime AND bhoa.aoi_Add_Time < in_endtime AND bhoa.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhoi.ibc_tzmoney AS netMoney
    FROM betting_hq_og_ibc bhoi
    WHERE bhoi.ibc_updatetime >= in_begintime AND bhoi.ibc_updatetime < in_endtime AND bhoi.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhoo.aoi_Betting_Amount AS netMoney
    FROM betting_hq_og_og bhoo
    WHERE bhoo.aoi_Add_Time >= in_begintime AND bhoo.aoi_Add_Time < in_endtime AND bhoo.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhp.pt_bet AS netMoney
    FROM betting_hq_pt bhp
    WHERE bhp.pt_gamedate >= in_begintime AND bhp.pt_gamedate < in_endtime AND bhp.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhx.xcp_totalprice AS netMoney
    FROM betting_hq_xcp bhx
    WHERE bhx.xcp_writetime >= in_begintime AND bhx.xcp_writetime < in_endtime AND bhx.employeecode = in_employeecode
    UNION ALL
    SELECT 
      bya.money AS netMoney
    FROM betting_yg_ag bya 
    WHERE bya.time >= in_begintime AND bya.time<in_endtime AND bya.employeecode = in_employeecode
    
    /*********zj sa av ttg*********/   
    UNION ALL
    SELECT zj.stakeamount AS netMoney FROM betting_zj zj WHERE zj.bettime >= in_begintime AND zj.bettime<in_endtime AND zj.employeecode = in_employeecode
      
    UNION ALL
    SELECT sa.betamount AS netMoney FROM betting_sa sa WHERE sa.bettime >= in_begintime AND sa.bettime<in_endtime AND sa.employeecode = in_employeecode
    
    UNION ALL
    SELECT av.amount AS netMoney FROM betting_kr_av av WHERE av.time >= in_begintime AND av.time<in_endtime AND av.employeecode = in_employeecode
    
    UNION ALL
    SELECT ttg.amount AS netMoney FROM betting_ttg ttg WHERE ttg.bettime >= in_begintime AND ttg.bettime<in_endtime AND ttg.employeecode = in_employeecode
    
    UNION ALL
    SELECT mg.mg_amount AS netMoney FROM betting_mg mg WHERE mg.bettime >= in_begintime AND mg.bettime<in_endtime AND mg.employeecode = in_employeecode
    
    UNION ALL
    SELECT bha.bet_Amount AS netMoney FROM betting_hq_png bha WHERE bha.bet_Time >= in_begintime AND bha.bet_Time < in_endtime AND bha.employeecode = in_employeecode
     
   UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_qp bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_gg bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_sgs bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_idn bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_m88 bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_hab bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    
    UNION ALL
    SELECT bhp.pt_bet AS netMoney FROM betting_win88 bhp WHERE bhp.pt_gamedate >= in_begintime AND bhp.pt_gamedate < in_endtime AND bhp.employeecode = in_employeecode
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_tgp bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_qt bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_gb bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_ebet bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
    UNION ALL
    SELECT bha.betmoney AS netMoney FROM betting_eibc bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode
) AS stat;
  RETURN IF(rt_netmoney IS NULL ,0 ,rt_netmoney);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_tn_person_losewin
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_tn_person_losewin`;
DELIMITER ;;
CREATE FUNCTION `ufn_tn_person_losewin`(in_employeecode char(8), in_begintime datetime, in_endtime datetime) RETURNS decimal(16,2)
    READS SQL DATA
BEGIN
  DECLARE rt_netmoney decimal(16, 2) DEFAULT 0;
  -- 查询用户某段时间的投注量
  SELECT
    SUM(stat.netMoney) INTO rt_netmoney
  FROM (SELECT
      bha.net_Amount AS netMoney
    FROM betting_hq_ag bha
    WHERE bha.bet_Time >= in_begintime AND bha.bet_Time < in_endtime AND bha.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhb.bbin_Payoff AS netMoney
    FROM betting_hq_bbin bhb
    WHERE bhb.bbin_Wagers_Date >= in_begintime AND bhb.bbin_Wagers_Date < in_endtime AND bhb.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhq.Winning_Credits AS netMoney
    FROM betting_hq_nhq bhq
    WHERE bhq.Betting_Date >= in_begintime AND bhq.Betting_Date < in_endtime AND bhq.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhoa.aoi_Win_Lose_Amount AS netMoney
    FROM betting_hq_og_ag bhoa
    WHERE bhoa.aoi_Add_Time >= in_begintime AND bhoa.aoi_Add_Time < in_endtime AND bhoa.employeecode = in_employeecode
    UNION ALL
    SELECT
      (bhoi.ibc_win - bhoi.ibc_lose) AS netMoney
    FROM betting_hq_og_ibc bhoi
    WHERE bhoi.ibc_updatetime >= in_begintime AND bhoi.ibc_updatetime < in_endtime AND bhoi.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhoo.aoi_Win_Lose_Amount AS netMoney
    FROM betting_hq_og_og bhoo
    WHERE bhoo.aoi_Add_Time >= in_begintime AND bhoo.aoi_Add_Time < in_endtime AND bhoo.employeecode = in_employeecode
    UNION ALL
    SELECT
      (bhp.pt_win - bhp.pt_bet) AS netMoney
    FROM betting_hq_pt bhp
    WHERE bhp.pt_gamedate >= in_begintime AND bhp.pt_gamedate < in_endtime AND bhp.employeecode = in_employeecode
    UNION ALL
    SELECT
      bhx.xcp_bonus AS netMoney
    FROM betting_hq_xcp bhx
    WHERE bhx.xcp_writetime >= in_begintime AND bhx.xcp_writetime < in_endtime AND bhx.employeecode = in_employeecode
    
    /*********zj sa av ttg*********/   
    UNION ALL
    SELECT zj.winloss AS netMoney FROM betting_zj zj WHERE zj.bettime >= in_begintime AND zj.bettime<in_endtime AND zj.employeecode = in_employeecode
      
    UNION ALL
    SELECT sa.resultamount AS netMoney FROM betting_sa sa WHERE sa.bettime >= in_begintime AND sa.bettime<in_endtime AND sa.employeecode = in_employeecode
    
    UNION ALL
    SELECT av.amount2 AS netMoney FROM betting_kr_av av WHERE av.time >= in_begintime AND av.time<in_endtime AND av.employeecode = in_employeecode
    
    UNION ALL
    SELECT ttg.net_money AS netMoney FROM betting_ttg ttg WHERE ttg.bettime >= in_begintime AND ttg.bettime<in_endtime AND ttg.employeecode = in_employeecode
    
    UNION ALL
    SELECT mg.net_money AS netMoney FROM betting_mg mg WHERE mg.bettime >= in_begintime AND mg.bettime<in_endtime AND mg.employeecode = in_employeecode
    
    UNION ALL
    SELECT bha.net_Amount AS netMoney FROM betting_hq_png bha WHERE bha.bet_Time >= in_begintime AND bha.bet_Time < in_endtime AND bha.employeecode = in_employeecode

    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_qp bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode      
    
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_gg bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode      
    
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_sgs bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode      
    
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_idn bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode   
    
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_m88 bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode     
    
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_hab bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode  
    
    UNION ALL
    SELECT  (bhp.pt_win - bhp.pt_bet) AS netMoney FROM betting_win88 bhp WHERE bhp.pt_gamedate >= in_begintime AND bhp.pt_gamedate < in_endtime AND bhp.employeecode = in_employeecode
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_tgp bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode   
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_qt bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode 
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_gb bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode 
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_ebet bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode 
    UNION ALL
    SELECT bha.netmoney AS netMoney FROM betting_eibc bha WHERE bha.bettime >= in_begintime AND bha.bettime < in_endtime AND bha.employeecode = in_employeecode 
    ) AS stat;
  RETURN IF(rt_netmoney IS NULL ,0 ,rt_netmoney);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_tn_team_losewin
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_tn_team_losewin`;
DELIMITER ;;
CREATE FUNCTION `ufn_tn_team_losewin`(in_employeecode char(8), in_begintime datetime, in_endtime datetime) RETURNS decimal(16,2)
    READS SQL DATA
BEGIN
SELECT SUM(stat.netMoney) INTO @netmoney FROM (
    SELECT bha.net_Amount AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_hq_ag bha 
      WHERE bha.bet_Time >= in_begintime AND bha.bet_Time <in_endtime 
      UNION ALL 
    SELECT bhb.bbin_Payoff AS netMoney , bhb.employeecode AS employeecode,bhb.parentemployeecode AS parentemployeecode FROM betting_hq_bbin bhb 
      WHERE bhb.bbin_Wagers_Date >= in_begintime AND bhb.bbin_Wagers_Date <in_endtime 
      UNION ALL 
    SELECT bhq.Winning_Credits AS netMoney , bhq.employeecode AS employeecode,bhq.parentemployeecode AS parentemployeecode FROM betting_hq_nhq bhq 
      WHERE bhq.Betting_Date  >= in_begintime AND bhq.Betting_Date<in_endtime 
      UNION ALL 
    SELECT bhoa.aoi_Win_Lose_Amount AS netMoney, bhoa.employeecode AS employeecode, bhoa.parentemployeecode AS parentemployeecode FROM  betting_hq_og_ag bhoa
      WHERE bhoa.aoi_Add_Time  >= in_begintime AND bhoa.aoi_Add_Time <in_endtime 
      UNION ALL
    SELECT (bhoi.ibc_win - bhoi.ibc_lose) AS netMoney, bhoi.employeecode AS employeecode, bhoi.parentemployeecode AS parentemployeecode FROM  betting_hq_og_ibc bhoi
      WHERE bhoi.ibc_updatetime  >= in_begintime AND bhoi.ibc_updatetime  <in_endtime 
      UNION ALL 
    SELECT bhoo.aoi_Win_Lose_Amount AS netMoney, bhoo.employeecode AS employeecode, bhoo.parentemployeecode AS parentemployeecode FROM  betting_hq_og_og bhoo
      WHERE bhoo.aoi_Add_Time >= in_begintime AND bhoo.aoi_Add_Time <in_endtime 
      UNION ALL 
    SELECT (bhp.pt_win-bhp.pt_bet) AS netMoney, bhp.employeecode AS employeecode, bhp.parentemployeecode AS parentemployeecode FROM  betting_hq_pt bhp
      WHERE bhp.pt_gamedate >= in_begintime AND bhp.pt_gamedate <in_endtime 
      UNION ALL 
    SELECT bhx.xcp_bonus AS netMoney, bhx.employeecode AS employeecode, bhx.parentemployeecode AS parentemployeecode FROM  betting_hq_xcp bhx
      WHERE bhx.xcp_writetime >= in_begintime AND bhx.xcp_writetime <in_endtime 
            
    /*********zj sa av ttg*********/   
    UNION ALL
    SELECT zj.winloss AS netMoney ,zj.employeecode AS employeecode, zj.parentemployeecode AS parentemployeecode
     FROM betting_zj zj WHERE zj.bettime >= in_begintime AND zj.bettime<in_endtime AND zj.employeecode = in_employeecode
      
    UNION ALL
    SELECT sa.resultamount AS netMoney ,sa.employeecode AS employeecode, sa.parentemployeecode AS parentemployeecode
    FROM betting_sa sa WHERE sa.bettime >= in_begintime AND sa.bettime<in_endtime AND sa.employeecode = in_employeecode
    
    UNION ALL
    SELECT av.amount2 AS netMoney ,av.employeecode AS employeecode, av.parentemployeecode AS parentemployeecode
    FROM betting_kr_av av WHERE av.time >= in_begintime AND av.time<in_endtime AND av.employeecode = in_employeecode
    
    UNION ALL
    SELECT ttg.net_money AS netMoney ,ttg.employeecode AS employeecode, ttg.parentemployeecode AS parentemployeecode
    FROM betting_ttg ttg WHERE ttg.bettime >= in_begintime AND ttg.bettime<in_endtime AND ttg.employeecode = in_employeecode
    
    UNION ALL
    SELECT mg.net_money AS netMoney ,mg.employeecode AS employeecode, mg.parentemployeecode AS parentemployeecode
    FROM betting_mg mg WHERE mg.bettime >= in_begintime AND mg.bettime<in_endtime AND mg.employeecode = in_employeecode
    
    UNION ALL
    SELECT bha.net_Amount AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_hq_png bha 
      WHERE bha.bet_Time >= in_begintime AND bha.bet_Time <in_endtime 
      
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_qp bha 
      WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime       
    
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_gg bha 
      WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime       
      
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_sgs bha 
      WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime           
    
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_idn bha 
      WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime
      
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_m88 bha 
      WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime  
      
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_hab bha 
      WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime     
      
    UNION ALL 
    SELECT (bhp.pt_win-bhp.pt_bet) AS netMoney, bhp.employeecode AS employeecode, bhp.parentemployeecode AS parentemployeecode FROM  betting_win88 bhp WHERE bhp.pt_gamedate >= in_begintime AND bhp.pt_gamedate <in_endtime   
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_tgp bha  WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime           
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_qt bha  WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime           
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_gb bha  WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime           
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_ebet bha  WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime            
    UNION ALL
    SELECT bha.netmoney AS netMoney , bha.employeecode AS employeecode,bha.parentemployeecode AS parentemployeecode FROM betting_eibc bha  WHERE bha.bettime >= in_begintime AND bha.bettime <in_endtime            
) AS stat WHERE (stat.employeecode = in_employeecode OR stat.parentemployeecode 
  IN (SELECT ee.employeecode FROM enterprise_employee ee 
        WHERE FIND_IN_SET(ee.employeecode,ufn_down_recursion_team_of_agent(in_employeecode))));
RETURN IF(@netmoney IS NULL ,0,@netmoney);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_up_recursion_shareholder_of_user
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_up_recursion_shareholder_of_user`;
DELIMITER ;;
CREATE FUNCTION `ufn_up_recursion_shareholder_of_user`(in_employeecode char(8)) RETURNS char(8) CHARSET utf8
    READS SQL DATA
BEGIN
  DECLARE l_parentemployeecode char(8);
  DECLARE l_employeecode char(8);
  DECLARE l_employeetype char(4);
  DECLARE lc_employeetype char(4);
  SET l_parentemployeecode = in_employeecode;

LABLE_LP:
LOOP
  SELECT
    employeecode,
    parentemployeecode,
    employeetypecode INTO l_employeecode, l_parentemployeecode, l_employeetype
  FROM enterprise_employee
  WHERE employeecode = l_parentemployeecode;
  IF l_employeetype = 'T001' OR lc_employeetype = l_employeetype THEN
    LEAVE LABLE_LP;
  END IF;
  SET lc_employeetype = l_employeetype;
END LOOP LABLE_LP;

  RETURN l_employeecode;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ufn_up_recursion_upper_level_of_user
-- ----------------------------
DROP FUNCTION IF EXISTS `ufn_up_recursion_upper_level_of_user`;
DELIMITER ;;
CREATE FUNCTION `ufn_up_recursion_upper_level_of_user`(in_employeecode char(8)) RETURNS longtext CHARSET utf8
    READS SQL DATA
BEGIN
  DECLARE l_parentemployeecode longtext;
  DECLARE lc_parentemployeecode longtext;

  SET l_parentemployeecode = in_employeecode;
  SET lc_parentemployeecode = in_employeecode;

LABLE_LP:
LOOP
  SELECT
    parentemployeecode INTO lc_parentemployeecode
  FROM enterprise_employee
  WHERE employeecode = lc_parentemployeecode;
  IF lc_parentemployeecode IS NULL OR lc_parentemployeecode = '00000000' THEN
    LEAVE LABLE_LP;
  END IF;
  SET l_parentemployeecode = CONCAT(l_parentemployeecode, ',', lc_parentemployeecode);
END LOOP LABLE_LP;

  RETURN l_parentemployeecode;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for usp_orderamount_by_employeecode
-- ----------------------------
DROP FUNCTION IF EXISTS `usp_orderamount_by_employeecode`;
DELIMITER ;;
CREATE FUNCTION `usp_orderamount_by_employeecode`(in_employeecode CHAR(8),in_order_type CHAR(1)) RETURNS decimal(14,2)
    READS SQL DATA
RETURN (select IFNULL(sum(orderamount),0) sumorderamount from deposit_withdraw_order where orderstatus='2' and ordertype=in_order_type and employeecode=in_employeecode)
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_aoi_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_aoi_gameinfo_after_insert` AFTER INSERT ON `api_aoi_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'OG' AS platformtype, NEW.aoi_ProductId AS platformid,employeecode, NEW.aoi_AddTime AS bettime, NEW.aoi_BettingAmount AS betmoney, NEW.aoi_ValidAmount AS validbet, NEW.aoi_WinLoseAmount AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'SX' AS gamebigtype ,'OGGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.aoi_UserName
   	       AND gametype = 'OGGame';
		
   REPLACE INTO betting_hq_og_og
    SELECT NEW.aoi_ProductId AS aoi_Product_Id,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           NEW.aoi_UserName AS aoi_User_Name,
           NEW.aoi_GameRecordId AS aoi_Game_Record_Id,
           NEW.aoi_OrderNumber AS aoi_Order_Number,
           NEW.aoi_TableId AS aoi_Table_Id,
           NEW.aoi_Stage AS aoi_Stage,
           NEW.aoi_Inning AS aoi_Inning,
           NEW.aoi_GameNameId AS aoi_Game_Name_Id,
           NEW.aoi_GameBettingKind AS aoi_Game_Betting_Kind,
           NEW.aoi_GameBettingContent AS aoi_Game_Betting_Content,
           NEW.aoi_ResultType AS aoi_Result_Type,
           NEW.aoi_BettingAmount AS aoi_Betting_Amount,
           NEW.aoi_CompensateRate AS aoi_Compensate_Rate,
           NEW.aoi_WinLoseAmount AS aoi_Win_Lose_Amount,
           NEW.aoi_Balance AS aoi_Balance,
           NEW.aoi_AddTime AS aoi_Add_Time,
           NEW.aoi_VendorId AS aoi_Vendor_Id,
           NEW.aoi_ValidAmount AS aoi_Valid_Amount,
           NEW.aoi_PlatformID AS aoi_Platform_ID,
           NEW.aoi_createtime AS aoi_createtime,
           CURRENT_TIMESTAMP AS Last_Time,
           employeecode AS employeecode,
           loginaccount AS loginaccount,
           parentemployeecode AS parentemployeecode,
           'SX' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.aoi_UserName
   AND gametype = 'OGGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_aoi_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_aoi_gameinfo_after_delete` AFTER DELETE ON `api_aoi_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_hq_og_og WHERE aoi_Product_Id = OLD.aoi_ProductId;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'OG' AND platformid = OLD.aoi_ProductId;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_av_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_av_gameinfo_after_insert` AFTER INSERT ON `api_av_gameinfo` FOR EACH ROW BEGIN

        ## ????
   ## ????? betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'AV' platformtype, NEW.tid AS platformid, employeecode, NEW.time AS bettime,NEW.amount AS betmoney, NEW.amount AS validbet, NEW.amount2 AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype,'AVGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.userID
   	       AND gametype = 'AVGame';
		
   REPLACE INTO betting_kr_av
    SELECT NEW.tid AS tid,           
           NEW.transType AS transtype,
           NEW.amount AS amount,
           NEW.transType2 AS transtype2,           
           NEW.amount2 AS amount2,
           NEW.provider AS provider,
           NEW.roundID AS roundid,           
           NEW.gameInfo AS gameinfo,           
           NEW.history AS history,
           NEW.createtime AS createtime,     
           NEW.isRoundFinished as isroundfinished,
           NEW.time as time,
           NEW.userID as userid,                 
           employeecode AS employeecode,
           loginaccount AS loginaccount,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           parentemployeecode AS parentemployeecode,
           'DZ' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.userID
   AND gametype = 'AVGame';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_av_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_av_gameinfo_after_delete` AFTER DELETE ON `api_av_gameinfo` FOR EACH ROW BEGIN
DELETE FROM betting_kr_av WHERE tid = OLD.tid;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'AV' AND platformid = OLD.tid;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bbin_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bbin_gameinfo_after_insert` AFTER INSERT ON `api_bbin_gameinfo` FOR EACH ROW BEGIN



   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'BBIN' platformtype, NEW.bbin_WagersID AS platformid, employeecode, NEW.bbin_WagersDate AS bettime,NEW.bbin_BetAmount AS betmoney, NEW.bbin_Commissionable AS validbet, (NEW.bbin_Payoff) AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,NEW.Platformflag AS gamebigtype ,'BBINGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.bbin_UserName
   	       AND gametype = 'BBINGame';
		
   REPLACE INTO betting_hq_bbin
    SELECT NEW.bbin_WagersID AS bbin_Wagers_Id,      
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,           
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,
                                 
           NEW.bbin_UserName AS bbin_User_Name,
           NEW.bbin_WagersDate AS bbin_Wagers_Date,           
           NEW.bbin_SerialID AS bbin_Serial_Id,
           NEW.bbin_RoundNo AS bbin_Round_No,
           NEW.bbin_GameType AS bbin_Game_Type,
           NEW.bbin_GameCode AS bbin_Game_Code,
           NEW.bbin_Result AS bbin_Result,
           NEW.bbin_ResultType AS bbin_Result_Type,
           NEW.bbin_Card AS bbin_Card,       
           NEW.bbin_BetAmount AS bbin_Bet_Amount,
           
           NEW.bbin_Payoff AS bbin_Payoff,
           NEW.bbin_Currency AS bbin_Currency,
           NEW.bbin_ExchangeRate AS bbin_Exchange_Rate,
           NEW.bbin_Commissionable AS bbin_Commissionable,
           NEW.bbin_Commission AS bbin_Commission,
           NEW.bbin_Origin AS bbin_Origin,  
           NULL AS  bbin_Modified_Date,        
           NEW.bbin_IsPaid AS bbin_Is_Paid,
           NULL AS bbin_Order_Date,
           NULL AS bbin_UPTIME,
           NEW.bbin_createtime AS bbin_createtime,  
           NULL AS Last_Time, 
           NEW.Platformflag AS gamebigtype,
           0 AS status,
           NEW.bbin_WagerDetail AS bbin_Wager_Detail
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.bbin_UserName
   AND gametype = 'BBINGame';




END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bbin_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bbin_gameinfo_after_delete` AFTER DELETE ON `api_bbin_gameinfo` FOR EACH ROW BEGIN

DELETE FROM betting_hq_bbin WHERE bbin_Wagers_Id = OLD.bbin_WagersID;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'BBIN' AND platformid = OLD.bbin_WagersID;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_oblive_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_oblive_gameinfo_after_insert` AFTER INSERT ON `api_bet67_oblive_gameinfo` FOR EACH ROW BEGIN

   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'OB' platformtype, NEW.betId AS platformid, employeecode, NEW.betTime AS bettime,NEW.betAmount AS betmoney, NEW.validAmount AS validbet, NEW.winOrLoss AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'SX' AS gamebigtype  ,'BET67ABSX' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.userName
   	       AND gametype = 'BET67ABSX';
		
   REPLACE INTO betting_bet67_oblive
    	SELECT NEW.betId,  
           NEW.gameTypeName,
           NEW.betTime,
           NEW.betAmount,
           NEW.validAmount,
           NEW.winOrLoss,
           NEW.betStatus,
           NEW.betTypeName,
           NEW.gameResult,
		   NEW.tableName,
		   NEW.commissionName,
		   NEW.gameStartTime,
		   NEW.gameEndTime,

		   loginaccount AS userName,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,           
           parentemployeecode AS parentemployeecode,
           'SX' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.userName
   AND gametype = 'BET67ABSX';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_oblive_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_oblive_gameinfo_after_delete` AFTER DELETE ON `api_bet67_oblive_gameinfo` FOR EACH ROW BEGIN


        DELETE FROM betting_bet67_oblive WHERE id = OLD.id;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'OB' AND gametype='BET67ABSX' AND platformid = OLD.id;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_oglive_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_oglive_gameinfo_after_insert` AFTER INSERT ON `api_bet67_oglive_gameinfo` FOR EACH ROW BEGIN

   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'OGSX' platformtype, NEW.betId AS platformid, employeecode, NEW.betTime AS bettime,NEW.betAmount AS betmoney, NEW.betAmount AS validbet, NEW.winOrLoss AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'SX' AS gamebigtype  ,'BET67OGSX' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.userName
   	       AND gametype = 'BET67OGSX';
		
   REPLACE INTO betting_bet67_oglive
    	SELECT NEW.betId,  
           NEW.gameRoundId,
           NEW.gameTypeName,
           NEW.betTime,
           NEW.betAmount,
           NEW.winOrLoss,
           NEW.betTypeName,
           NEW.isBill,
           NEW.platformName,
           NEW.gameKind,
           NEW.balance,

		   loginaccount AS userName,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,           
           parentemployeecode AS parentemployeecode,
           'SX' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.userName
   AND gametype = 'BET67OGSX';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_oglive_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_oglive_gameinfo_after_delete` AFTER DELETE ON `api_bet67_oglive_gameinfo` FOR EACH ROW BEGIN


        DELETE FROM betting_bet67_oglottery WHERE betId = OLD.betId;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'OGSX' AND gametype='BET67OGSX' AND platformid = OLD.betId;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_oglottery_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_oglottery_gameinfo_after_insert` AFTER INSERT ON `api_bet67_oglottery_gameinfo` FOR EACH ROW BEGIN

   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'OGCP' platformtype, NEW.betId AS platformid, employeecode, NEW.betTime AS bettime,NEW.betAmount AS betmoney, NEW.betAmount AS validbet, NEW.winOrLoss AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'CP' AS gamebigtype  ,'BET67OGCP' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.userName
   	       AND gametype = 'BET67OGCP';
		
   REPLACE INTO betting_bet67_oglottery
    	SELECT NEW.betId,  
           NEW.gameName,
           NEW.phaseNum,
           NEW.betTime,
           NEW.betAmount,
           NEW.winOrLoss,
           NEW.betTypeName,
           NEW.betStatus,

		       loginaccount AS userName,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,           
           parentemployeecode AS parentemployeecode,
           'CP' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.userName
   AND gametype = 'BET67OGCP';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_oglottery_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_oglottery_gameinfo_after_delete` AFTER DELETE ON `api_bet67_oglottery_gameinfo` FOR EACH ROW BEGIN


        DELETE FROM betting_bet67_oglottery WHERE betId = OLD.betId;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'OGCP' AND gametype='BET67OGCP' AND platformid = OLD.betId;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_sllive_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_sllive_gameinfo_after_insert` AFTER INSERT ON `api_bet67_sllive_gameinfo` FOR EACH ROW BEGIN

   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'SA' platformtype, NEW.betId AS platformid, employeecode, NEW.betTime AS bettime,NEW.betMoney AS betmoney, NEW.betMoney AS validbet, NEW.betResult AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'SX' AS gamebigtype  ,'BET67SASX' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.userName
   	       AND gametype = 'BET67SASX';
		
   REPLACE INTO betting_bet67_sllive
    	SELECT NEW.betId,  
           NEW.betTime,
           NEW.desNo,
           NEW.betMoney,
           NEW.round,
           NEW.betResult,
           NEW.gameCode,
           NEW.betRgn,
					 NEW.chipLeft,
					 NEW.openAnswer,
           NEW.openDetail,
					 NEW.bSuccess,

		       loginaccount AS userName,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,           
           parentemployeecode AS parentemployeecode,
           'SX' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.userName
   AND gametype = 'BET67SASX';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_sllive_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_sllive_gameinfo_after_delete` AFTER DELETE ON `api_bet67_sllive_gameinfo` FOR EACH ROW BEGIN


        DELETE FROM betting_bet67_sllive WHERE betId = OLD.betId;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'SA' AND gametype='BET67SASX' AND platformid = OLD.betId;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_tssport_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_tssport_gameinfo_after_insert` AFTER INSERT ON `api_bet67_tssport_gameinfo` FOR EACH ROW BEGIN

   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'TS' platformtype, NEW.betId AS platformid, employeecode, NEW.betTime AS bettime,NEW.wagerStake AS betmoney, NEW.wagerStake AS validbet, NEW.winAmt AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'TY' AS gamebigtype  ,'BET67TSTY' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.userName
   	       AND gametype = 'BET67TSTY';
		
   REPLACE INTO betting_bet67_tssport
    	SELECT NEW.betId,  
           NEW.isNormalWager,
           NEW.playTypeName,
           NEW.matchName,
           NEW.eventName,
           NEW.matchDate,
           NEW.betTypeName,
           NEW.teamBetName,
           NEW.betTime,
           NEW.wagerOdds,
           NEW.wagerStake,           
           NEW.winAmt,
           NEW.score,
           NEW.betStatus,
		  NEW.remark,
		  NEW.handicap,
		  NEW.billTime,
		  NEW.isBill,
		  NEW.betDetail,

		   loginaccount AS userName,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,           
           parentemployeecode AS parentemployeecode,
           'TY' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.userName
   AND gametype = 'BET67TSTY';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_bet67_tssport_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_bet67_tssport_gameinfo_after_delete` AFTER DELETE ON `api_bet67_tssport_gameinfo` FOR EACH ROW BEGIN


        DELETE FROM betting_bet67_tssport WHERE betid = OLD.betId;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'TS' AND gametype='BET67TSTY' AND platformid = OLD.betid;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_ebet_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_ebet_gameinfo_after_insert` AFTER INSERT ON `api_ebet_gameinfo` FOR EACH ROW BEGIN


   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'EBET' platformtype, NEW.bethistoryid AS platformid, employeecode, NEW.bettime AS bettime,NEW.betmoney AS betmoney, NEW.validbet AS validbet, NEW.netmoney AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'SX' AS gamebigtype  ,'EBETGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.username
   	       AND gametype = 'EBETGame';
		
   REPLACE INTO betting_ebet
    SELECT NEW.bethistoryid AS bethistoryid,      
           
                      
           NEW.gametype AS gametype,
           NEW.gamename AS gamename,
           NEW.betmap AS betmap,
           NEW.judgeresult AS judgeresult,
           NEW.userid AS userid,
           NEW.payouttime AS payouttime,
           NEW.gametime AS gametime,
           NEW.roundno AS roundno,
           NEW.subchannelid AS subchannelid,
           NEW.validbet AS validbet,
           NEW.payout AS payout,           
           NEW.username AS username,
           NEW.bankercards AS bankercards,
           NEW.tigercard AS tigercard,
           NEW.dragoncard AS dragoncard,
           NEW.numbercard AS numbercard,
           NEW.alldices AS alldices,
           NEW.playercards AS playercards,       
           NEW.createtime AS createtime,
               
           NEW.bettime AS bettime,
           NEW.betmoney AS betmoney,   
           NEW.netmoney AS netmoney,        
           
           
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,           
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,
           'SX' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.username
   AND gametype = 'EBETGame';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_ebet_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_ebet_gameinfo_after_delete` AFTER DELETE ON `api_ebet_gameinfo` FOR EACH ROW BEGIN


        DELETE FROM betting_ebet WHERE bethistoryid = OLD.bethistoryid;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'EBET' AND platformid = OLD.bethistoryid;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_eibc_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_eibc_gameinfo_after_insert` AFTER INSERT ON `api_eibc_gameinfo` FOR EACH ROW BEGIN


   	###注意：沙巴体育和IM体育因为投注时间不重要，关键在结算时间所以这里用结算时间来存储####       
        REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'EIBC' platformtype, NEW.transid AS platformid, employeecode, NEW.nettime AS bettime,NEW.betmoney AS betmoney, NEW.betmoney AS validbet, NEW.netmoney AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'TY' AS gamebigtype ,'eIBCGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.playername
   	       AND gametype = 'eIBCGame';
		
   REPLACE INTO betting_eibc
    SELECT NEW.transid AS transid,      
         
           NEW.playername AS playername,
           NEW.transactiontime AS transactiontime,
           NEW.matchid AS matchid,
           NEW.leagueid AS leagueid,
           NEW.leaguename AS leaguename,
           NEW.sporttype AS sporttype,
           NEW.awayid AS awayid,
           NEW.awayidname AS awayidname,
           NEW.homeid AS homeid,
           NEW.homeidname AS homeidname,
           NEW.matchdatetime AS matchdatetime,           
           NEW.bettype AS bettype,
           NEW.parlayrefno AS parlayrefno,
           NEW.betteam AS betteam,
           NEW.hdp AS hdp,
           NEW.awayhdp AS awayhdp,
           NEW.homehdp AS homehdp,
           NEW.odds AS odds,           
           NEW.oddstype AS oddstype,
           NEW.awayscore AS awayscore,
           NEW.homescore AS homescore,
           NEW.islive AS islive,
           NEW.lastballno AS lastballno,
           NEW.ticketstatus AS ticketstatus,
           NEW.stake AS stake,           
           NEW.winloseamount AS winloseamount,
           NEW.winlostdatetime AS winlostdatetime,
           NEW.currency AS currency,
           NEW.versionkey AS versionkey,
           
           NEW.createtime AS createtime,
           NEW.bettime AS bettime,
           NEW.betmoney AS betmoney,
           NEW.netmoney AS netmoney,                 
           NEW.nettime AS nettime,   
           
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           'TY' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.playername
   AND gametype = 'eIBCGame';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_eibc_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_eibc_gameinfo_after_delete` AFTER DELETE ON `api_eibc_gameinfo` FOR EACH ROW BEGIN

DELETE FROM betting_eibc WHERE transid = OLD.transid;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'EIBC' AND platformid = OLD.transid;
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_gb_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_gb_gameinfo_after_insert` AFTER INSERT ON `api_gb_gameinfo` FOR EACH ROW BEGIN

   	       
        REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'GB' platformtype, NEW.settleid AS platformid, employeecode, NEW.bettime AS bettime,NEW.betmoney AS betmoney, NEW.betmoney AS validbet, NEW.netmoney AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'CP' AS gamebigtype,'GBGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.memberid
   	       AND gametype = 'GBGame';
		
   REPLACE INTO betting_gb
    SELECT NEW.settleid AS settleid,      
         
           NEW.betid AS betid,
           NEW.betgrpno AS betgrpno,
           NEW.tpcode AS tpcode,
           NEW.gbsn AS gbsn,
           NEW.memberid AS memberid,
           NEW.curcode AS curcode,
           NEW.betdt AS betdt,
           NEW.bettype AS bettype,
           NEW.bettypeparam1 AS bettypeparam1,
           NEW.bettypeparam2 AS bettypeparam2,
           NEW.wintype AS wintype,           
           NEW.hxmguid AS hxmguid,
           NEW.initbetamt AS initbetamt,
           NEW.realbetamt AS realbetamt,
           NEW.holdingamt AS holdingamt,
           NEW.initbetrate AS initbetrate,
           NEW.realbetrate AS realbetrate,
           NEW.prewinamt AS prewinamt,           
           NEW.betresult AS betresult,
           NEW.wlamt AS wlamt,
           NEW.refundbetamt AS refundbetamt,
           NEW.ticketbetamt AS ticketbetamt,
           NEW.ticketresult AS ticketresult,
           NEW.ticketwlamt AS ticketwlamt,
           NEW.settledt AS settledt,           
           NEW.kenolist AS kenolist,
           NEW.lottolist AS lottolist,
           NEW.ssclist AS ssclist,
           
           NEW.createtime AS createtime,
           NEW.bettime AS bettime,
           NEW.betmoney AS betmoney,
           NEW.netmoney AS netmoney,                 
           
           
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           'CP' AS gamebigtype,
           0 AS status,
           NEW.pkxlist AS pkxlist
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.memberid
   AND gametype = 'GBGame';
        
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_gb_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_gb_gameinfo_after_delete` AFTER DELETE ON `api_gb_gameinfo` FOR EACH ROW BEGIN

        DELETE FROM betting_gb WHERE settleid = OLD.settleid;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'GB' AND platformid = OLD.settleid;
        
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_ggp_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_ggp_gameinfo_after_insert` AFTER INSERT ON `api_ggp_gameinfo` FOR EACH ROW BEGIN


REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'GGP' platformtype, NEW.lsh AS platformid, employeecode, now() AS bettime,NEW.ngr AS betmoney, NEW.ngr AS validbet, NEW.ngr AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'QP' AS gamebigtype,'GGPGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.username AND gametype = 'GGPGame';


                 
  REPLACE INTO betting_ggp
    SELECT NEW.lsh AS lsh,           
           NEW.gamedate AS gamedate,
           NEW.username AS username,
           NEW.ngr AS ngr,           
           now() AS createtime,          
           NEW.ngr AS betmoney,                    
             
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,     
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           'QP' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.username
   AND gametype = 'GGPGame';
   
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_ggp_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_ggp_gameinfo_after_delete` AFTER DELETE ON `api_ggp_gameinfo` FOR EACH ROW BEGIN

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_gg_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_gg_gameinfo_after_insert` AFTER INSERT ON `api_gg_gameinfo` FOR EACH ROW BEGIN

   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype, gametype)
         SELECT 'GG' platformtype, NEW.autoid AS platformid, employeecode, NEW.bettime AS bettime,NEW.betmoney AS betmoney, NEW.betmoney AS validbet, NEW.netmoney AS netmoney, 
         enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype ,'GGGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.accountno AND gametype = 'GGGame';


                 
  REPLACE INTO betting_gg
    SELECT NEW.autoid AS autoid,           
           NEW.gameId AS gameid,
           NEW.cuuency AS cuuency,
           NEW.linkId AS linkid,           
           NEW.accountno AS accountno,
           NEW.betmoney AS betmoney,
           NEW.netmoney AS netmoney,           
           NEW.bettime AS bettime,           
           NEW.createtime AS createtime,      
           NEW.Platformflag AS platformflag,           
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,     
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           'DZ' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.accountno
   AND gametype = 'GGGame';

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_gg_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_gg_gameinfo_after_delete` AFTER DELETE ON `api_gg_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_gg WHERE autoid = OLD.autoid;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'GG' AND platformid = OLD.autoid;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_hab_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_hab_gameinfo_after_insert` AFTER INSERT ON `api_hab_gameinfo` FOR EACH ROW BEGIN


        REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'HAB' platformtype, NEW.friendlygameinstanceid AS platformid, employeecode, NEW.bettime AS bettime,NEW.betmoney AS betmoney, NEW.betmoney AS validbet, NEW.netmoney AS netmoney, 
         enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype  ,'HABGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.username AND gametype = 'HABGame';


                 
  REPLACE INTO betting_hab
    SELECT NEW.friendlygameinstanceid AS friendlygameinstanceid,           
           NEW.playerid AS playerid,
           NEW.brandid AS brandid,           
           NEW.username AS username,
           NEW.brandgameid AS brandgameid,
           NEW.gamekeyname AS gamekeyname,           
           NEW.gametypeid AS gametypeid,           
           NEW.dtstarted AS dtstarted,      
           NEW.dtcompleted AS dtcompleted,      
           NEW.gameinstanceid AS gameinstanceid,           
           NEW.stake AS stake,           
           NEW.payout AS payout,      
           NEW.jackpotwin AS jackpotwin,  
           NEW.jackpotcontribution AS jackpotcontribution,           
           NEW.currencycode AS currencycode,           
           NEW.channeltypeid AS channeltypeid,      
           NEW.createtime AS createtime,            
           NEW.bettime AS bettime,           
           NEW.betmoney AS betmoney,           
           NEW.netmoney AS netmoney,                 
                       
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,     
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           'DZ' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.username
   AND gametype = 'HABGame';
   
   
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_hab_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_hab_gameinfo_after_delete` AFTER DELETE ON `api_hab_gameinfo` FOR EACH ROW BEGIN

   DELETE FROM betting_hab WHERE friendlygameinstanceid = OLD.friendlygameinstanceid;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'HAB' AND platformid = OLD.friendlygameinstanceid;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_ibc_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_ibc_gameinfo_after_insert` AFTER INSERT ON `api_ibc_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'IBC' AS platformtype, NEW.ibc_rowguid AS platformid, employeecode, NEW.ibc_balltime AS bettime, NEW.ibc_tzmoney AS betmoney, NEW.ibc_tzmoney AS validbet, NEW.ibc_win-NEW.ibc_lose AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'TY' AS gamebigtype ,'IBCGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.ibc_username
   	       AND gametype = 'IBCGame';
		
   REPLACE INTO betting_hq_og_ibc
    SELECT NEW.ibc_rowguid AS ibc_rowguid,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           NEW.ibc_ballid AS ibc_ballid,
           NEW.ibc_balltime AS ibc_balltime,
           NEW.ibc_content AS ibc_content,
           NEW.ibc_curpl AS ibc_curpl,
           NEW.ibc_ds AS ibc_ds,
           NEW.ibc_dxc AS ibc_dxc,
           NEW.ibc_isbk AS ibc_isbk,
           NEW.ibc_iscancel AS ibc_iscancel,
           NEW.ibc_isdanger AS ibc_isdanger,
           NEW.ibc_isjs AS ibc_isjs,
           NEW.ibc_lose AS ibc_lose,
           NEW.ibc_matchid AS ibc_matchid,
           NEW.ibc_moneyrate AS ibc_moneyrate,
           NEW.ibc_orderid AS ibc_orderid,
           NEW.ibc_recard AS ibc_recard,
           NEW.ibc_result AS ibc_result,
           NEW.ibc_rqc AS ibc_rqc,
           NEW.ibc_rqteam AS ibc_rqteam,
           NEW.ibc_sportid AS ibc_sportid,
           NEW.ibc_tballtime AS ibc_tballtime,
           NEW.ibc_thisdate AS ibc_thisdate,
           NEW.ibc_truewin AS ibc_truewin,
           NEW.ibc_tzip AS ibc_tzip,
           NEW.ibc_tzmoney AS ibc_tzmoney,
           NEW.ibc_tzteam AS ibc_tzteam,
           NEW.ibc_tztype AS ibc_tztype,
           NEW.ibc_updatetime AS ibc_updatetime,
           NEW.ibc_username AS ibc_username,
           NEW.ibc_win AS ibc_win,
           NEW.ibc_zdbf AS ibc_zdbf,
           NEW.ibc_vendorid AS ibc_vendorid,
           NEW.ibc_createtime AS ibc_createtime,
           CURRENT_TIMESTAMP AS Last_Time,
           employeecode AS employeecode,
           loginaccount AS loginaccount,
           parentemployeecode AS parentemployeecode,
           'TY' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.ibc_username
   AND gametype = 'IBCGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_ibc_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_ibc_gameinfo_after_delete` AFTER DELETE ON `api_ibc_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_hq_og_ibc WHERE ibc_rowguid = OLD.ibc_rowguid;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'IBC' AND platformid = OLD.ibc_rowguid;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_idn2_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_idn2_gameinfo_after_insert` AFTER INSERT ON `api_idn2_gameinfo` FOR EACH ROW BEGIN

REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'IDN' platformtype, NEW.lsh AS platformid, employeecode, NEW.turnoverdate AS bettime,NEW.totalturnover AS betmoney, NEW.totalturnover AS validbet, NEW.agentcommission AS netmoney, 
         enterprisecode,brandcode,loginaccount,parentemployeecode,'QP' AS gamebigtype ,'IDNGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = lower(NEW.userid) AND gametype = 'IDNGame';


            REPLACE INTO betting_idn2
    SELECT NEW.lsh AS lsh,           
           NEW.turnoverdate AS turnoverdate,
           lower(NEW.userid) AS userid,
           NEW.totalturnover AS totalturnover,           
           NEW.turnoverpoker AS turnoverpoker,
           NEW.turnoverdomino AS turnoverdomino,
           NEW.turnoverceme AS turnoverceme,           
           NEW.turnoverblackjack AS turnoverblackjack,           
           NEW.turnovercapsa AS turnovercapsa,      
           NEW.turnoverlivepoker AS turnoverlivepoker,      
           NEW.texaspoker AS texaspoker,           
           NEW.domino AS domino,           
           NEW.ceme AS ceme,      
           NEW.blackjack AS blackjack,  
           NEW.capsa AS capsa,           
           NEW.livepoker AS livepoker,           
           NEW.pokertournament AS pokertournament,      
           NEW.agentcommission AS agentcommission, 
           NEW.agentbill AS agentbill,           
                     
           NEW.createtime AS createtime,           
           
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,     
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           'QP' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = lower(NEW.userid)
   AND gametype = 'IDNGame';     
  


END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_idn2_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_idn2_gameinfo_after_delete` AFTER DELETE ON `api_idn2_gameinfo` FOR EACH ROW BEGIN

DELETE FROM betting_idn2 WHERE lsh = OLD.lsh;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'IDN' AND platformid = OLD.lsh;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_idn_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_idn_gameinfo_after_insert` AFTER INSERT ON `api_idn_gameinfo` FOR EACH ROW BEGIN

        REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'IDN' platformtype, NEW.transactionno AS platformid, employeecode, NEW.bettime AS bettime,NEW.betmoney AS betmoney, NEW.betmoney AS validbet, NEW.netmoney AS netmoney, 
         enterprisecode,brandcode,loginaccount,parentemployeecode,'QP' AS gamebigtype,'IDNGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = lower(NEW.userid) AND gametype = 'IDNGame';


                 
  REPLACE INTO betting_idn
    SELECT NEW.transactionno AS transactionno,           
           NEW.tableno AS tableno,
           lower(NEW.userid) AS userid,
           NEW.betdate AS betdate,           
           NEW.game AS game,
           NEW.bettable AS bettable,
           NEW.periode AS periode,           
           NEW.room AS room,           
           NEW.bet AS bet,      
           NEW.currbet AS currbet,      
           NEW.rbet AS rbet,           
           NEW.status AS game_status,           
           NEW.card AS card,      
           NEW.hand AS hand,  
           NEW.prize AS prize,           
           NEW.curr AS curr,           
           NEW.currplayer AS currplayer,      
           NEW.curramount AS curramount, 
           NEW.amount AS amount,           
           NEW.total AS total,           
           NEW.agentcomission AS agentcomission,      
           NEW.agentbill AS agentbill,             
           NEW.createtime AS createtime,           
           NEW.bettime AS bettime,           
           NEW.betmoney AS betmoney,      
           NEW.netmoney AS netmoney,                  
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,     
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           'QP' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = lower(NEW.userid)
   AND gametype = 'IDNGame';


END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_idn_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_idn_gameinfo_after_delete` AFTER DELETE ON `api_idn_gameinfo` FOR EACH ROW BEGIN

        
        DELETE FROM betting_idn WHERE transactionno = OLD.transactionno;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'IDN' AND platformid = OLD.transactionno;
        
        
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_im_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_im_gameinfo_after_insert` AFTER INSERT ON `api_im_gameinfo` FOR EACH ROW BEGIN


###注意：沙巴体育和IM体育因为投注时间不重要，关键在结算时间所以这里用结算时间来存储####   

   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'IM' platformtype, NEW.betid AS platformid, employeecode, NEW.nettime AS bettime,NEW.betmoney AS betmoney, NEW.betmoney AS validbet, NEW.netmoney AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'TY' AS gamebigtype,'IMGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.playerid
   	       AND gametype = 'IMGame';
  
  
  REPLACE INTO betting_im
    SELECT NEW.betid AS betid,           
           NEW.providername AS providername,
           NEW.gameid AS gameid,
           NEW.wagercreationdatetime AS wagercreationdatetime,
           NEW.currency AS currency,
           NEW.playerid AS playerid,
           NEW.stakeamount AS stakeamount,
           NEW.memberexposure AS memberexposure,
           NEW.payoutamount AS payoutamount,
           NEW.winloss AS winloss,
           NEW.oddstype AS oddstype,
           NEW.wagertype AS wagertype,
           NEW.platform AS platform,
           NEW.issettled AS issettled,
           NEW.isconfirmed AS isconfirmed,
           NEW.iscancelled AS iscancelled,
           NEW.bettradestatus AS bettradestatus,
           NEW.bettradecommission AS bettradecommission,
           NEW.bettradebuybackamount AS bettradebuybackamount,
           NEW.combotype AS combotype,
           NEW.lastupdateddate AS lastupdateddate,
           NEW.detailitems AS detailitems,
           
           NEW.bettime AS bettime,
           NEW.betmoney AS betmoney,
           NEW.nettime AS nettime,
           NEW.netmoney AS netmoney,           
           NEW.createtime AS createtime,
           NEW.platformflag AS platformflag,
           
    
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,   
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,
           'TY' AS gamebigtype,
           0 AS status
           
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.playerid
   AND gametype = 'IMGame';		


END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_im_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_im_gameinfo_after_delete` AFTER DELETE ON `api_im_gameinfo` FOR EACH ROW BEGIN

DELETE FROM betting_im WHERE betid = OLD.betid;
DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'IM' AND platformid = OLD.betid;
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_jdb_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_jdb_gameinfo_after_insert` AFTER INSERT ON `api_jdb_gameinfo` FOR EACH ROW BEGIN

	## 同步数据到 betting_all_game_winlose_detail
	REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
    SELECT 'JDB' AS platformtype,NEW.seqNo AS platformid,employeecode,NEW.bettime AS bettime,ABS(NEW.betmoney) AS betmoney,ABS(NEW.betmoney) AS validbet,NEW.netmoney AS netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype, 'JDBGame' AS gametype
	FROM employee_api_accout
	WHERE gameaccount = NEW.playerId
	AND gametype = 'JDBGame';
	
	REPLACE INTO betting_jdb
	SELECT NEW.seqNo AS seqNo,
		NEW.playerId AS playerId,
		NEW.mtype AS mtype,
		NEW.gameDate AS gameDate,
		NEW.bet AS bet,
		NEW.gambleBet AS gambleBet,
		NEW.win AS win,
		NEW.total AS total,
		NEW.currency AS currency,
		NEW.denom AS denom,
		NEW.lastModifyTime AS lastModifyTime,
		NEW.playerIp AS playerIp,
		NEW.clientType AS clientType,
		NEW.gType AS gType,
		NEW.hasGamble AS hasGamble,
		NEW.hasBonusGame AS hasBonusGame,
		NEW.hasFreegame AS hasFreegame,
		NEW.roomType AS roomType,
		NEW.systemTakeWin AS systemTakeWin,
		NEW.jackpot AS jackpot,
		NEW.jackpotContribute AS jackpotContribute,
		NEW.beforeBalance AS beforeBalance,
		NEW.afterBalance AS afterBalance,
	
		enterprisecode AS enterprisecode,
		brandcode AS brandcode,   
		employeecode AS employeecode,
		parentemployeecode AS parentemployeecode,
		loginaccount AS loginaccount,
		'DZ' AS gamebigtype,
		0 AS status,
		
		NEW.createtime AS createtime,
		NEW.bettime AS bettime,
		ABS(NEW.betmoney) AS betmoney,
		NEW.netmoney AS netmoney
		
	FROM employee_api_accout
	WHERE gameaccount = NEW.playerId
	AND gametype = 'JDBGame';		

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_jdb_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_jdb_gameinfo_after_delete` AFTER DELETE ON `api_jdb_gameinfo` FOR EACH ROW BEGIN

DELETE FROM betting_jdb WHERE seqNo = OLD.seqNo;
DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'JDB' AND platformid = OLD.seqNo;
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_m88_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_m88_gameinfo_after_insert` AFTER INSERT ON `api_m88_gameinfo` FOR EACH ROW BEGIN
                
        ####最后更新时间放到投注时间里面，便于统计结算，同IM体育和沙巴体育#####        
        REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'M88' platformtype, NEW.transid AS platformid, employeecode, NEW.nettime AS bettime,NEW.betmoney AS betmoney, NEW.betmoney AS validbet, NEW.netmoney AS netmoney, 
         enterprisecode,brandcode,loginaccount,parentemployeecode,'TY' AS gamebigtype,'M88Game' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.memberid AND gametype = 'M88Game';


                 
  REPLACE INTO betting_m88
    SELECT NEW.transid AS transid,           
           NEW.memberid AS memberid,
           NEW.operatorid AS operatorid,           
           NEW.sitecode AS sitecode,
           NEW.leagueid AS leagueid,
           NEW.homeid AS homeid,           
           NEW.awayid AS awayid,           
           NEW.matchdatetime AS matchdatetime,      
           NEW.bettype AS bettype,      
           NEW.parlayrefno AS parlayrefno,           
           NEW.odds AS odds,           
           NEW.currency AS currency,  
           NEW.stake AS stake,  
           NEW.winlostamount AS winlostamount,           
           NEW.transactiontime AS transactiontime,           
           NEW.ticketstatus AS ticketstatus,      
           NEW.versionkey AS versionkey,            
           NEW.winlostdatetime AS winlostdatetime,           
           NEW.oddstype AS oddstype,           
           NEW.sportstype AS sportstype,      
           NEW.betteam AS betteam,  
           NEW.homehdp AS homehdp,           
           NEW.awayhdp AS awayhdp,           
           NEW.matchid AS matchid,      
           NEW.islive AS islive,            
           NEW.homescore AS homescore,           
           NEW.awayscore AS awayscore,           
           NEW.choicecode AS choicecode,   
           NEW.choicename AS choicename,  
           NEW.txntype AS txntype,           
           NEW.lastupdate AS lastupdate,           
           NEW.leaguename AS leaguename,      
           NEW.homename AS homename,            
           NEW.awayname AS awayname,           
           NEW.sportname AS sportname,           
           NEW.oddsname AS oddsname,           
           NEW.bettypename AS bettypename,            
           NEW.winloststatus AS winloststatus,           
           NEW.betmoney AS betmoney,           
           NEW.netmoney AS netmoney,   
           NEW.bettime AS bettime,            
           NEW.nettime AS nettime,           
           NEW.createtime AS createtime,       
                       
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,     
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           'TY' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.memberid
   AND gametype = 'M88Game';
   
           
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_m88_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_m88_gameinfo_after_delete` AFTER DELETE ON `api_m88_gameinfo` FOR EACH ROW BEGIN

   DELETE FROM betting_m88 WHERE transid = OLD.transid;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'M88' AND platformid = OLD.transid;
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_mg_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_mg_gameinfo_after_insert` AFTER INSERT ON `api_mg_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'MG' AS platformtype, NEW.mg_transId AS platformid ,employeecode, NEW.mg_transTime AS bettime, NEW.mg_amnt AS betmoney, NEW.mg_amnt AS validbet, NEW.mg_win AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype,'MGGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.mg_mbrCode
   	       AND gametype = 'MGGame';
		
   REPLACE INTO betting_mg
    SELECT NEW.mg_transId AS mg_trans_Id,
           NEW.mg_key AS mg_key,
           NEW.mg_colId AS mg_col_Id,
           NEW.mg_agentId AS mg_agent_Id,
           NEW.mg_mbrId AS mg_mbr_Id,
           NEW.mg_mbrCode AS mg_mbr_Code,
           NEW.mg_gameId AS mg_game_Id,
           NEW.mg_transType AS mg_trans_Type,
           NEW.mg_transTime AS mg_trans_Time,
           NEW.mg_mgsGameId AS mg_mgs_Game_Id,
           NEW.mg_mgsActionId AS mg_mgs_Action_Id,
           NEW.mg_amnt AS mg_amnt,
           NEW.mg_clrngAmnt AS mg_clrng_Amnt,
           NEW.mg_balance AS mg_balance,
           NEW.mg_refTransId AS mg_ref_Trans_Id,
           NEW.mg_refTransType AS mg_ref_Trans_Type,
           NEW.mg_win AS mg_win,
           NEW.mg_createtime AS mg_createtime,
           NEW.Platformflag AS Platformflag,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,
           'DZ' AS gamebigtype,
           0 AS status,
           NEW.mg_transTime AS bettime,
           NEW.mg_win AS net_money,
           NEW.mg_amnt AS mg_amount
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.mg_mbrCode
   AND gametype = 'MGGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_mg_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_mg_gameinfo_after_delete` AFTER DELETE ON `api_mg_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_mg WHERE mg_trans_Id = OLD.mg_transId;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'MG' AND platformid = OLD.mg_transId;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_nhq_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_nhq_gameinfo_after_insert` AFTER INSERT ON `api_nhq_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'HY' platformtype, NEW.BettingID AS platformid,employeecode, NEW.BettingDate AS bettime, NEW.BettingCredits AS betmoney, NEW.WashCodeCredits AS validbet, NEW.WinningCredits AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'SX' AS gamebigtype,'NHQGame' AS gametype
   	       FROM employee_api_accout
   	      WHERE gameaccount = NEW.UserAccount
		    AND gametype = 'NHQGame';

   ## 同步数据到 betting_hq_nhq
   REPLACE INTO betting_hq_nhq
   SELECT NEW.BettingID AS Betting_ID,
          NEW.BettingNO AS Betting_NO,
          enterprisecode AS enterprisecode,
          brandcode AS brandcode,
          employeecode AS employeecode,
          loginaccount AS loginaccount,
          NEW.BettingCredits AS Betting_Credits,
          NEW.PreCreditsPoint AS Pre_Credits_Point,
          NEW.GameResult AS Game_Result,
          NEW.GameRoomName AS Game_Room_Name,
          NEW.AgentAccount AS Agent_Account,
          NEW.GamblingCode AS Gambling_Code,
          NEW.AfterPayoutCredits AS After_Payout_Credits,
          NEW.UserAccount AS User_Account,
          NEW.GameType AS Game_Type,
          NEW.BettingDate AS Betting_Date,
          NEW.DealerName AS Dealer_Name,
          NEW.GameName AS Game_Name,
          NEW.SetGameNo AS Set_Game_No,
          NEW.IsPayout AS Is_Payout,
          NEW.ParentUserID AS Parent_User_ID,
          NEW.WinningCredits AS Winning_Credits,
          NEW.BettingPoint AS Betting_Point,
          NEW.TableName AS Table_Name,
          NEW.TrackIP AS Track_IP,
          NEW.CreateTime AS Create_Time,
          NEW.WashCodeCredits AS Wash_Code_Credits,
          NEW.UpdateTime AS Update_Time,
          CURRENT_TIMESTAMP AS Last_Time,
          parentemployeecode AS parentemployeecode,
          'SX' AS gamebigtype,
          0 AS status ##数据汇总状态
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.UserAccount
      AND gametype = 'NHQGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_nhq_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_nhq_gameinfo_after_delete` AFTER DELETE ON `api_nhq_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_hq_nhq WHERE Betting_ID = OLD.BettingID;
   DELETE FROM betting_all_game_winlose_detail WHERE  platformtype = 'HY' AND platformid = OLD.BettingID;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_pt_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_pt_gameinfo_after_insert` AFTER INSERT ON `api_pt_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'PT' platformtype, NEW.pt_gamecode AS platformid, employeecode, NEW.pt_gamedate AS bettime,NEW.pt_bet AS betmoney, NEW.pt_bet AS validbet, NEW.pt_win AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,CASE WHEN NEW.pt_gametype = 'Live Games' THEN 'SX' 
                ELSE 'DZ'
	       END AS gamebigtype ,'PTGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = LOWER(NEW.pt_username)
   	       AND gametype = 'PTGame';
		
   REPLACE INTO betting_hq_pt
    SELECT NEW.pt_gamecode AS pt_gamecode,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           NEW.pt_gametype AS pt_gametype,
           NEW.pt_gameid AS pt_gameid,
           NEW.pt_gamename AS pt_gamename,
           NEW.pt_username AS pt_username,
           NEW.pt_windowcode AS pt_windowcode,
           NEW.pt_bet AS pt_bet,
           NEW.pt_win AS pt_win,
           NEW.pt_balance AS pt_balance,
           NEW.pt_gamedate AS pt_gamedate,
           NEW.pt_info AS pt_info,
           NEW.pt_sessionid AS pt_sessionid,
           NEW.pt_progressivebet AS pt_progressivebet,
           NEW.pt_progressivewin AS pt_progressivewin,
           NEW.pt_currentbet AS pt_currentbet,
           NEW.pt_livenetwork AS pt_livenetwork,
           NEW.pt_rnum AS pt_rnum,
           NEW.pt_createtime AS pt_createtime,
           CURRENT_TIMESTAMP AS Last_Time,
           employeecode AS employeecode,
           loginaccount AS loginaccount,
           parentemployeecode AS parentemployeecode,
           CASE WHEN NEW.pt_gametype = 'Live Games' THEN 'SX'
                ELSE 'DZ'
	       END AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = LOWER(NEW.pt_username)
   AND gametype = 'PTGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_pt_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_pt_gameinfo_after_delete` AFTER DELETE ON `api_pt_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_hq_pt WHERE pt_gamecode = OLD.pt_gamecode;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'PT' AND platformid = OLD.pt_gamecode;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_qp_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_qp_gameinfo_after_insert` AFTER INSERT ON `api_qp_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'QP' platformtype, NEW.TurnNumber AS platformid, employeecode, str_to_date(NEW.StartTime,'%Y-%m-%d %k:%i:%s') AS bettime, NEW.TurnScore AS betmoney, NEW.TurnScore AS validbet, NEW.Revenue AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'QP' AS gamebigtype ,'QPGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.Account
   	       AND gametype = 'QPGame';
		
   REPLACE INTO betting_qp
    SELECT NEW.TurnNumber AS Turn_Number,
           NEW.ServerID AS Server_ID,
           NEW.KindID AS Kind_ID,
           NEW.RoomName AS Room_Name,
           NEW.StartTime AS Start_Time,
           NEW.EndTime AS End_Time,
           NEW.Score AS Score,
           NEW.TurnScore AS Turn_Score,
           NEW.Account AS Account,
           NEW.createtime AS createtime,
           NEW.Platformflag AS Platformflag,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,
           'QP' AS gamebigtype,
           0 AS status,
           str_to_date(NEW.StartTime,'%Y-%m-%d %k:%i:%s') AS bettime,
           NEW.Revenue AS netmoney,
           NEW.TurnScore AS betmoney,
           NEW.Revenue AS Revenue
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.Account
      AND gametype = 'QPGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_qp_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_qp_gameinfo_after_delete` AFTER DELETE ON `api_qp_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_qp WHERE Turn_Number = OLD.TurnNumber;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'QP' AND platformid = OLD.TurnNumber;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_qt_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_qt_gameinfo_after_insert` AFTER INSERT ON `api_qt_gameinfo` FOR EACH ROW BEGIN


   	       
        REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'QT' platformtype, NEW.id AS platformid, employeecode, NEW.bettime AS bettime,NEW.betmoney AS betmoney, NEW.betmoney AS validbet, NEW.netmoney AS netmoney,
         enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype ,'QTGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.playerid
   	       AND gametype = 'QTGame';
		
   REPLACE INTO betting_qt
    SELECT NEW.id AS id,      
         
           NEW.playergameroundid AS playergameroundid,
           NEW.gameid AS gameid,
           NEW.amount AS amount,
           NEW.balance AS balance,
           NEW.created AS created,
           NEW.gameprovider AS gameprovider,
           NEW.gtype AS gtype,
           NEW.gameclienttype AS gameclienttype,
           NEW.gamecategory AS gamecategory,
           NEW.currency AS currency,
           NEW.playerdevice AS playerdevice,           
           NEW.operatorid AS operatorid,
           NEW.playerid AS playerid,           
           NEW.wallettransactionid AS wallettransactionid,
           NEW.roundstatus AS roundstatus,
                    
           
           NEW.createtime AS createtime,
           NEW.bettime AS bettime,
           NEW.betmoney AS betmoney,
           NEW.netmoney AS netmoney,
           
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,
           
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,
           'DZ' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.playerid
   AND gametype = 'QTGame';
   
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_qt_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_qt_gameinfo_after_delete` AFTER DELETE ON `api_qt_gameinfo` FOR EACH ROW BEGIN

        DELETE FROM betting_qt WHERE id = OLD.id;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'QT' AND platformid = OLD.id;
        
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_qwp_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_qwp_gameinfo_after_insert` AFTER INSERT ON `api_qwp_gameinfo` FOR EACH ROW BEGIN

	## 同步数据到 betting_all_game_winlose_detail
	REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
    SELECT 'QWP' AS platformtype,NEW.TurnNumber AS platformid,employeecode,NEW.StartTime AS bettime,NEW.TurnScore AS betmoney,NEW.TurnScore AS validbet,NEW.Score AS netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,'QP' AS gamebigtype,'QWPGame' AS gametype
	FROM employee_api_accout
  WHERE gameaccount = NEW.Account
	AND gametype = 'QWPGame';
	
	REPLACE INTO betting_qwp
	SELECT NEW.TurnNumber AS TurnNumber,
				 NEW.ServerId AS ServerId,
				 NEW.KindId AS KindId,
				 NEW.RoomName AS RoomName,
				 NEW.StartTime AS StartTime,
				 NEW.EndTime AS EndTime,
				 NEW.RecordTime AS RecordTime,
				 NEW.CardData AS CardData,
				 NEW.Account AS Account,
				 NEW.Score AS Score,
				 NEW.TurnScore AS TurnScore,
				 NEW.Revenue AS Revenue,
	
				 enterprisecode AS enterprisecode,
				 brandcode AS brandcode,   
				 employeecode AS employeecode,
				 parentemployeecode AS parentemployeecode,
				 loginaccount AS loginaccount,
				 'QP' AS gamebigtype,
				 0 AS status
	FROM employee_api_accout
	WHERE gameaccount = NEW.Account
	AND gametype = 'QWPGame';		

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_qwp_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_qwp_gameinfo_after_delete` AFTER DELETE ON `api_qwp_gameinfo` FOR EACH ROW BEGIN

DELETE FROM betting_qwp WHERE TurnNumber = OLD.TurnNumber;
DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'QWP' AND platformid = OLD.TurnNumber;
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_sa_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_sa_gameinfo_after_insert` AFTER INSERT ON `api_sa_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'SA' platformtype, NEW.BetID AS platformid, employeecode, NEW.BetTime AS bettime, NEW.BetAmount AS betmoney, NEW.validbet AS validbet, NEW.ResultAmount AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,CASE NEW.GameType WHEN 'bac' THEN 'SX'
                             WHEN 'dtx' THEN 'SX'
                             WHEN 'sicbo' THEN 'SX'
                             WHEN 'ftan' THEN 'SX'
                             WHEN 'rot' THEN 'SX'
                             WHEN 'lottery' THEN 'CP'
                           ELSE  'DZ'
           END AS gamebigtype ,'SAGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.Username
   	       AND gametype = 'SAGame';
		
   REPLACE INTO betting_sa
    SELECT NEW.BetID AS betid,
           enterprisecode,
           brandcode,
           employeecode,
           parentemployeecode,
           loginaccount,
           CASE NEW.GameType WHEN 'bac' THEN 'SX'
                             WHEN 'dtx' THEN 'SX'
                             WHEN 'sicbo' THEN 'SX'
                             WHEN 'ftan' THEN 'SX'
                             WHEN 'rot' THEN 'SX'
                             WHEN 'lottery' THEN 'CP'
                           ELSE  'DZ'
           END AS gamebigtype,
           NEW.BetTime AS bettime,
           NEW.PayoutTime AS payouttime,
           NEW.Username AS username,
           NEW.HostID AS hostid,
           NEW.GameID AS gameid,
           NEW.Round AS round,
           NEW.Sets AS sets,
           NEW.BetAmount AS betamount,
           NEW.ResultAmount AS resultamount,
           NEW.GameType AS gametype,
           NEW.BetType AS bettype,
           NEW.BetSource AS betsource,
           NEW.State AS state,
           NEW.Detail AS detail,
           NEW.createtime AS createtime,
           0 AS status,
           NEW.validbet AS validbet
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.Username
   AND gametype = 'SAGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_sa_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_sa_gameinfo_after_delete` AFTER DELETE ON `api_sa_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_sa WHERE betid = OLD.BetID;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'SA' AND platformid = OLD.BetID;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_sgs_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_sgs_gameinfo_after_insert` AFTER INSERT ON `api_sgs_gameinfo` FOR EACH ROW BEGIN
## 同步数据
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'SGS' platformtype, NEW.ugsbetid AS platformid, employeecode, NEW.bettime AS bettime,NEW.betmoney AS betmoney, NEW.validmoney AS validbet, NEW.netmoney AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,CASE WHEN NEW.gameprovider = 'Sunbet' THEN 'SX'
                ELSE 'DZ'
	       END AS gamebigtype ,'SGSGame' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.userid
   	       AND gametype = 'SGSGame';
		
   REPLACE INTO betting_sgs
    SELECT NEW.ugsbetid AS ugsbetid,           
           NEW.txid AS txid,
           NEW.betid AS betid,
           NEW.beton AS beton,
           NEW.betclosedon AS betclosedon,
           NEW.betupdatedon AS betupdatedon,
           NEW.timestamp AS timestamp,
           NEW.roundid AS roundid,
           NEW.roundstatus AS roundstatus,
           NEW.userid AS userid,
           NEW.username AS username,
           NEW.riskamt AS riskamt,
           NEW.winamt AS winamt,
           NEW.winloss AS winloss,
           NEW.beforebal AS beforebal,
           NEW.postbal AS postbal,
           NEW.cur AS cur,
           NEW.gameprovider AS gameprovider,
           NEW.gamename AS gamename,
           NEW.gameid AS gameid,
           NEW.platformtype AS platformtype,
           NEW.ipaddress AS ipaddress,
           NEW.bettype AS bettype,
           NEW.playtype AS playtype,
           NEW.betmoney AS betmoney,
           NEW.netmoney AS netmoney,
           NEW.bettime AS bettime,
           NEW.Platformflag AS platformflag,
           NEW.createtime AS createtime,        
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,   
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,
           
           CASE WHEN NEW.gameprovider = 'Sunbet' THEN 'SX'
                ELSE 'DZ'
	       END AS gamebigtype,
           0 AS status,
           NEW.validmoney AS validmoney
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.userid
   AND gametype = 'SGSGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_sgs_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_sgs_gameinfo_after_delete` AFTER DELETE ON `api_sgs_gameinfo` FOR EACH ROW BEGIN

DELETE FROM betting_sgs WHERE ugsbetid = OLD.ugsbetid;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'SGS' AND platformid = OLD.ugsbetid;


END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_tag_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_tag_gameinfo_after_insert` AFTER INSERT ON `api_tag_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据
   IF NEW.platformType = 'PNG' THEN
   ## 同步数据到 betting_all_game_winlose_detail
      REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
            SELECT 'PNG' AS platformtype, NEW.billNo AS platformid,employeecode, NEW.betTime AS bettime, NEW.betAmount AS betmoney, NEW.validBetAmount AS validbet, NEW.netAmount AS netmoney
            ,enterprisecode,brandcode,loginaccount,parentemployeecode,ufn_get_gamebigtype_from_tag  (NEW.platformType, NEW.gameType) AS gamebigtype ,'PNGGame' AS gametype
      	      FROM employee_api_accout
      	     WHERE gameaccount = NEW.playerName
      	       AND gametype = 'PNGGame';
			
      REPLACE INTO betting_hq_png
       SELECT NEW.billNo AS bill_No,
              brandcode AS brandcode,
              enterprisecode AS enterprisecode,
              employeecode AS employeecode,
              parentemployeecode AS parentemployeecode,
              loginaccount AS loginaccount,
              ufn_get_gamebigtype_from_tag  (NEW.platformType, NEW.gameType) AS gamebigtype,
              NEW.playerName AS player_Name,
              NEW.agentCode AS agent_Code,
              NEW.gameCode AS game_Code,
              NEW.netAmount AS net_Amount,
              NEW.betTime AS bet_Time,
              NEW.gameType AS game_Type,
              NEW.betAmount AS bet_Amount,
              NEW.validBetAmount AS valid_Bet_Amount,
              NEW.flag AS flag,
              NEW.playType AS play_Type,
              NEW.currency AS currency,
              NEW.tableCode AS table_Code,
              NEW.loginIP AS login_IP,
              NEW.recalcuTime AS recalcu_Time,
              NEW.platformId AS platform_Id,
              NEW.platformType AS platform_Type,
              NEW.remark AS remark,
              NEW.round AS round,
              NEW.stringex AS stringex,
              NEW.createtime AS createtime,
              NEW.result AS result,
              NEW.beforeCredit AS before_Credit,
              NEW.deviceType AS device_Type,
              CURRENT_TIMESTAMP AS Last_Time,
              0 AS status
      	 FROM employee_api_accout
       WHERE gameaccount = NEW.playerName
      AND gametype = 'PNGGame';
   ELSE
   ## 同步数据到 betting_all_game_winlose_detail
      REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype, gametype)
            SELECT 'TAG' AS platformtype, NEW.billNo AS platformid,employeecode ,NEW.betTime AS bettime, NEW.betAmount AS betmoney, NEW.validBetAmount AS validbet, NEW.netAmount AS netmoney
            ,enterprisecode,brandcode,loginaccount,parentemployeecode,ufn_get_gamebigtype_from_tag  (NEW.platformType, NEW.gameType) AS gamebigtype ,'TAGGame' AS gametype
      	      FROM employee_api_accout
      	     WHERE gameaccount = NEW.playerName
      	       AND gametype = 'TAGGame';
			   
      REPLACE INTO betting_hq_ag
       SELECT NEW.billNo AS bill_No,
              brandcode AS brandcode,
              enterprisecode AS enterprisecode,
              employeecode AS employeecode,
              parentemployeecode AS parentemployeecode,
              loginaccount AS loginaccount,
              ufn_get_gamebigtype_from_tag  (NEW.platformType, NEW.gameType) AS gamebigtype,
              NEW.playerName AS player_Name,
              NEW.agentCode AS agent_Code,
              NEW.gameCode AS game_Code,
              NEW.netAmount AS net_Amount,
              NEW.betTime AS bet_Time,
              NEW.gameType AS game_Type,
              NEW.betAmount AS bet_Amount,
              NEW.validBetAmount AS valid_Bet_Amount,
              NEW.flag AS flag,
              NEW.playType AS play_Type,
              NEW.currency AS currency,
              NEW.tableCode AS table_Code,
              NEW.loginIP AS login_IP,
              NEW.recalcuTime AS recalcu_Time,
              NEW.platformId AS platform_Id,
              NEW.platformType AS platform_Type,
              NEW.remark AS remark,
              NEW.round AS round,
              NEW.stringex AS stringex,
              NEW.createtime AS createtime,
              NEW.result AS result,
              NEW.beforeCredit AS before_Credit,
              NEW.deviceType AS device_Type,
              CURRENT_TIMESTAMP AS Last_Time,
              0 AS status
      	 FROM employee_api_accout
       WHERE gameaccount = NEW.playerName
      AND gametype = 'TAGGame';
   END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_tag_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_tag_gameinfo_after_delete` AFTER DELETE ON `api_tag_gameinfo` FOR EACH ROW BEGIN
   IF OLD.platformType = 'PNG' THEN
      DELETE FROM betting_hq_png WHERE bill_No = OLD.billNo;
      DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'PNG' AND platformid = OLD.billNo;
   ELSE
      DELETE FROM betting_hq_ag WHERE bill_No = OLD.billNo;
      DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'TAG' AND platformid = OLD.billNo;
   END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_tgp_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_tgp_gameinfo_after_insert` AFTER INSERT ON `api_tgp_gameinfo` FOR EACH ROW BEGIN
	REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
		SELECT 'TGP' platformtype,NEW.ugsbetid AS platformid,employeecode,NEW.bettime AS bettime,NEW.betmoney AS betmoney,NEW.validmoney AS validbet,NEW.netmoney AS netmoney,enterprisecode,brandcode,loginaccount,parentemployeecode,
			CASE
				WHEN NEW.gameprovider = 'SB' OR NEW.gameprovider = 'GD' OR NEW.gameprovider = 'EA' THEN 'SX'
				WHEN NEW.gameprovider = 'ESB' THEN 'TY'
				ELSE 'DZ'
			END AS gamebigtype,
		'TGPI' AS gametype
		FROM employee_api_accout WHERE gameaccount = LOWER(NEW.userid) AND gametype = 'TGPI';
		
	REPLACE INTO betting_tgp
	SELECT NEW.ugsbetid AS ugsbetid,
		NEW.txid AS txid,
		NEW.betid AS betid,
		NEW.beton AS beton,
		NEW.betclosedon AS betclosedon,
		NEW.betupdatedon AS betupdatedon,
		NEW.timestamp AS timestamp,
		NEW.roundid AS roundid,
		NEW.roundstatus AS roundstatus,
		NEW.userid AS userid,
		NEW.username AS username,
		NEW.riskamt AS riskamt,
		NEW.winamt AS winamt,
		NEW.winloss AS winloss,
		NEW.beforebal AS beforebal,
		NEW.postbal AS postbal,
		NEW.cur AS cur,
		NEW.gameprovider AS gameprovider,
		NEW.gamename AS gamename,
		NEW.gameid AS gameid,
		NEW.platformtype AS platformtype,
		NEW.ipaddress AS ipaddress,
		NEW.bettype AS bettype,
		NEW.playtype AS playtype,
		NEW.betmoney AS betmoney,
		NEW.netmoney AS netmoney,
		NEW.bettime AS bettime,
		NEW.Platformflag AS platformflag,
		NEW.createtime AS createtime,       
		enterprisecode AS enterprisecode,
		brandcode AS brandcode,  
		employeecode AS employeecode,
		parentemployeecode AS parentemployeecode,
		loginaccount AS loginaccount,
		CASE
			WHEN NEW.gameprovider = 'SB' OR NEW.gameprovider = 'GD' OR NEW.gameprovider = 'EA' THEN 'SX'
			WHEN NEW.gameprovider = 'ESB' THEN 'TY'
			ELSE 'DZ'
		END AS gamebigtype,
		0 AS status,
		NEW.validmoney AS validmoney
	FROM employee_api_accout
	WHERE gameaccount = LOWER(NEW.userid) AND gametype = 'TGPI';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_tgp_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_tgp_gameinfo_after_delete` AFTER DELETE ON `api_tgp_gameinfo` FOR EACH ROW BEGIN
	DELETE FROM betting_tgp WHERE ugsbetid = OLD.ugsbetid;
	DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'TGPI' AND platformid = OLD.ugsbetid;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_ttg_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_ttg_gameinfo_after_insert` AFTER INSERT ON `api_ttg_gameinfo` FOR EACH ROW BEGIN
   DECLARE l_transactionId BIGINT;
       SET l_transactionId = IFNULL(ufn_min_transactionId_from_api_ttg_gameinfo(NEW.handId),NEW.transactionId);
		  
   ## 同步数据 betting_ttg
   IF NEW.transactionSubType = 'Wager' THEN
   ## 同步数据到 betting_all_game_winlose_detail
      IF EXISTS (SELECT 1 FROM betting_all_game_winlose_detail WHERE platformtype = 'TTG' AND platformid = l_transactionId) THEN
         UPDATE betting_all_game_winlose_detail
            SET betmoney = -NEW.amount, 
		   	    validbet = -NEW.amount, 
		   	    netmoney = IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Resolve'),0)+NEW.amount
          WHERE platformtype = 'TTG' AND platformid = l_transactionId;
	  ELSE
         INSERT INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
              SELECT 'TTG' AS platformtype, 
		   	         IFNULL(ufn_min_transactionId_from_api_ttg_gameinfo(NEW.handId),NEW.transactionId) AS platformid,
			         employeecode,
		   	         INSERT(INSERT(NEW.transactionDate,7,0,'-'),5,0,'-') AS bettime, 
		   	         -NEW.amount AS betmoney, 
		   	         -NEW.amount AS validbet, 
		   	         IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Resolve'),0)+NEW.amount AS netmoney
		   	         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype ,'TTGGame' AS gametype
         	     FROM employee_api_accout
         	    WHERE gameaccount = NEW.playerId
         	      AND gametype = 'TTGGame';
	  END IF;
	  
      REPLACE INTO betting_ttg
      SELECT IFNULL(ufn_min_transactionId_from_api_ttg_gameinfo(NEW.handId),NEW.transactionId) AS transaction_Id,
             NEW.requestId AS request_Id,
             NEW.partnerId AS partner_Id,
             NEW.playerId AS player_Id,
             -NEW.amount AS amount,
             NEW.handId AS hand_Id,
             NEW.transactionType AS transaction_Type,
             NEW.transactionSubType AS transaction_Sub_Type,
             NEW.currency AS currency,
             NEW.game AS game,
             NEW.transactionDate AS transaction_Date,
             NEW.createtime AS createtime,
             NEW.Platformflag AS Platformflag,
             enterprisecode AS enterprisecode,
             brandcode AS brandcode,
             employeecode AS employeecode,
             parentemployeecode AS parentemployeecode,
             loginaccount AS loginaccount,
             'DZ' AS gamebigtype,
             0 AS status,
             INSERT(INSERT(NEW.transactionDate,7,0,'-'),5,0,'-') AS bettime,
             IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Resolve'),0)+NEW.amount AS net_Money, #Wager = 下注 Resolve = 奖金
             INSERT(INSERT(NEW.transactionDate,7,0,'-'),5,0,'-') AS resolvetime
        FROM employee_api_accout 
	   WHERE gameaccount = NEW.playerId
		 AND gametype = 'TTGGame';
   ELSE
   ## 同步数据到 betting_all_game_winlose_detail
      IF EXISTS (SELECT 1 FROM betting_all_game_winlose_detail WHERE platformtype = 'TTG' AND platformid = l_transactionId) THEN
         UPDATE betting_all_game_winlose_detail
            SET betmoney = -IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Wager'),0), 
		   	    validbet = -IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Wager'),0), 
		   	    netmoney = IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Wager'),0)+NEW.amount
          WHERE platformtype = 'TTG' AND platformid = l_transactionId;
	  ELSE
         INSERT INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
              SELECT 'TTG' AS platformtype, 
		   	         IFNULL(ufn_min_transactionId_from_api_ttg_gameinfo(NEW.handId),NEW.transactionId) AS platformid,
			         employeecode,
		   	         INSERT(INSERT(NEW.transactionDate,7,0,'-'),5,0,'-') AS bettime, 
		   	         -IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Wager'),0) AS betmoney, 
		   	         -IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Wager'),0) AS validbet, 
		   	         IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Wager'),0)+NEW.amount AS netmoney
		   	         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype ,'TTGGame' AS gametype
         	     FROM employee_api_accout
         	    WHERE gameaccount = NEW.playerId
         	      AND gametype = 'TTGGame';
	  END IF;
	  
      REPLACE INTO betting_ttg
      SELECT IFNULL(ufn_min_transactionId_from_api_ttg_gameinfo(NEW.handId),NEW.transactionId) AS transaction_Id,
             NEW.requestId AS request_Id,
             NEW.partnerId AS partner_Id,
             NEW.playerId AS player_Id,
             -IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Wager'),0) AS amount,
             NEW.handId AS hand_Id,
             NEW.transactionType AS transaction_Type,
             NEW.transactionSubType AS transaction_Sub_Type,
             NEW.currency AS currency,
             NEW.game AS game,
             NEW.transactionDate AS transaction_Date,
             NEW.createtime AS createtime,
             NEW.Platformflag AS Platformflag,
             enterprisecode AS enterprisecode,
             brandcode AS brandcode,
             employeecode AS employeecode,
             parentemployeecode AS parentemployeecode,
             loginaccount AS loginaccount,
             'DZ' AS gamebigtype,
             0 AS status,
             INSERT(INSERT(NEW.transactionDate,7,0,'-'),5,0,'-') AS bettime,
             IFNULL(ufn_etl_amount_from_api_ttg_gameinfo(NEW.handId, 'Wager'),0) + NEW.amount AS net_Money, #Wager = 下注 Resolve = 奖金
             INSERT(INSERT(NEW.transactionDate,7,0,'-'),5,0,'-') AS resolvetime
        FROM employee_api_accout 
	   WHERE gameaccount = NEW.playerId
		 AND gametype = 'TTGGame';
	END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_ttg_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_ttg_gameinfo_after_delete` AFTER DELETE ON `api_ttg_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_ttg WHERE transaction_Id = OLD.transactionId;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'TTG' AND platformid = OLD.transactionId;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_win88_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_win88_gameinfo_after_insert` AFTER INSERT ON `api_win88_gameinfo` FOR EACH ROW BEGIN

   	       
        REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
         SELECT 'W88' platformtype, NEW.pt_gamecode AS platformid, employeecode, NEW.pt_gamedate AS bettime,NEW.pt_bet AS betmoney, NEW.pt_bet AS validbet, NEW.pt_win AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,CASE WHEN NEW.pt_gametype = 'Live Games' THEN 'SX'
                ELSE 'DZ'
	       END AS gamebigtype ,'W88Game' AS gametype
   	      FROM employee_api_accout
   	     WHERE gameaccount = LOWER(NEW.pt_username)
   	       AND gametype = 'W88Game';
		
   REPLACE INTO betting_win88
    SELECT NEW.pt_gamecode AS pt_gamecode,           
           NEW.pt_username AS pt_username,
           NEW.pt_windowcode AS pt_windowcode,           
           NEW.pt_gameid AS pt_gameid,
           NEW.pt_gametype AS pt_gametype,
           NEW.pt_gamename AS pt_gamename,         
           NEW.pt_bet AS pt_bet,
           NEW.pt_win AS pt_win,
           NEW.pt_balance AS pt_balance,
           NEW.pt_gamedate AS pt_gamedate,
           NEW.pt_info AS pt_info,
           NEW.pt_sessionid AS pt_sessionid,
           NEW.pt_progressivebet AS pt_progressivebet,
           NEW.pt_progressivewin AS pt_progressivewin,
           NEW.pt_currentbet AS pt_currentbet,
           NEW.pt_livenetwork AS pt_livenetwork,
           NEW.pt_rnum AS pt_rnum,
           NEW.pt_createtime AS pt_createtime,
           NEW.Platformflag AS Platformflag,
           
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           employeecode AS employeecode,
           parentemployeecode AS parentemployeecode,
           loginaccount AS loginaccount,           
           CASE WHEN NEW.pt_gametype = 'Live Games' THEN 'SX'
                ELSE 'DZ'
	       END AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = LOWER(NEW.pt_username)
   AND gametype = 'W88Game';
   
   
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_win88_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_win88_gameinfo_after_delete` AFTER DELETE ON `api_win88_gameinfo` FOR EACH ROW BEGIN

        DELETE FROM betting_win88 WHERE pt_gamecode = OLD.pt_gamecode;
        DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'W88' AND platformid = OLD.pt_gamecode;
        
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_xcp_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_xcp_gameinfo_after_insert` AFTER INSERT ON `api_xcp_gameinfo` FOR EACH ROW BEGIN
   ## 同步数据
   ## 同步数据到 betting_all_game_winlose_detail
   REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype)
         SELECT 'CP' platformtype, NEW.xcp_projectid AS platformid, employeecode, NEW.xcp_writetime AS bettime, NEW.xcp_totalprice AS betmoney, NEW.xcp_totalprice AS validbet, NEW.xcp_prize-NEW.xcp_totalprice AS netmoney
         ,enterprisecode,brandcode,loginaccount,parentemployeecode,'CP' AS gamebigtype
   	      FROM employee_api_accout
   	     WHERE gameaccount = NEW.xcp_username
   	       AND gametype = 'XCPGame';
		
   REPLACE INTO betting_hq_xcp
    SELECT NEW.xcp_projectid AS xcp_projectid,
           enterprisecode AS enterprisecode,
           brandcode AS brandcode,
           NEW.xcp_bonus AS xcp_bonus,
           NEW.xcp_prize AS xcp_prize,
           NEW.xcp_writetime AS xcp_writetime,
           NEW.xcp_username AS xcp_username,
           NEW.xcp_code AS xcp_code,
           NEW.xcp_totalprice AS xcp_totalprice,
           NEW.xcp_isgetprize AS xcp_isgetprize,
           NEW.xcp_updatetime AS xcp_updatetime,
           NEW.xcp_lotteryid AS xcp_lotteryid,
           NEW.xcp_issue AS xcp_issue,
           NEW.xcp_codetimes AS xcp_codetimes,
           NEW.xcp_createtime AS xcp_createtime,
           CURRENT_TIMESTAMP AS Last_Time,
           employeecode AS employeecode,
           loginaccount AS loginaccount,
           parentemployeecode AS parentemployeecode,
           'CP' AS gamebigtype,
           0 AS status
   	 FROM employee_api_accout
    WHERE gameaccount = NEW.xcp_username
      AND gametype = 'XCPGame';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_xcp_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_xcp_gameinfo_after_delete` AFTER DELETE ON `api_xcp_gameinfo` FOR EACH ROW BEGIN
   DELETE FROM betting_hq_xcp WHERE xcp_projectid = OLD.xcp_projectid;
   DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'CP' AND platformid = OLD.xcp_projectid;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_yoplay_gameinfo_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_api_yoplay_gameinfo_after_insert` AFTER INSERT ON `api_yoplay_gameinfo` FOR EACH ROW BEGIN

     ## 同步数据到 betting_all_game_winlose_detail
      REPLACE INTO betting_all_game_winlose_detail (platformtype,platformid,employeecode,bettime,betmoney,validbet,netmoney, enterprisecode,brandcode,loginaccount,parentemployeecode,gamebigtype,gametype)
            SELECT 'YoPLAY' AS platformtype, NEW.billNo AS platformid,employeecode ,NEW.betTime AS bettime, NEW.betmoney AS betmoney, NEW.validmoney AS validbet, NEW.netmoney AS netmoney
            ,enterprisecode,brandcode,loginaccount,parentemployeecode,'DZ' AS gamebigtype,'YoPLAYGame' AS gametype
      	      FROM employee_api_accout
      	     WHERE gameaccount = NEW.playerName
      	       AND gametype = 'YoPLAYGame';   
      /****/
      REPLACE INTO betting_yoplay
       SELECT NEW.billNo AS bill_No,              
              NEW.playerName AS player_Name,
              NEW.agentCode AS agent_Code,
              NEW.gameCode AS game_Code,
              NEW.netAmount AS net_Amount,
              NEW.betTime AS bet_Time,
              NEW.gameType AS game_Type,
              NEW.betAmount AS bet_Amount,
              NEW.validBetAmount AS valid_Bet_Amount,
              NEW.flag AS flag,
              NEW.playType AS play_Type,
              NEW.currency AS currency,
              NEW.tableCode AS table_Code,
              NEW.loginIP AS login_IP,
              NEW.recalcuTime AS recalcu_Time,
              NEW.platformType AS platform_Type,
              NEW.remark AS remark,
              NEW.round AS round,
              NEW.slottype AS slottype,
              NEW.result AS result,
              NEW.mainbillno AS mainbillno,
              NEW.beforeCredit AS before_Credit,
              NEW.deviceType AS device_Type,              
              NEW.betAmountBase AS bet_Amount_Base,
              NEW.betAmountBonus AS bet_Amount_Bonus,
              NEW.netAmountBase AS net_Amount_Base,
              NEW.netAmountBonus AS net_Amount_Bonus,
              
              NEW.createtime AS createtime,
              NEW.betmoney AS betmoney,
              NEW.netmoney AS netmoney,
              NEW.validmoney AS validmoney,
              
              enterprisecode AS enterprisecode,
              brandcode AS brandcode,
              employeecode AS employeecode,
              parentemployeecode AS parentemployeecode,
              loginaccount AS loginaccount,
              'DZ' AS gamebigtype,
              0 AS status
      	 FROM employee_api_accout
       WHERE gameaccount = NEW.playerName
      AND gametype = 'YoPLAYGame';  
        
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_api_yoplay_gameinfo_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_api_yoplay_gameinfo_after_delete` AFTER DELETE ON `api_yoplay_gameinfo` FOR EACH ROW BEGIN

DELETE FROM betting_all_game_winlose_detail WHERE platformtype = 'YoPLAY' AND platformid = OLD.billNo;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_employee_all_uplevels_after_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_employee_all_uplevels_after_insert` AFTER INSERT ON `enterprise_employee` FOR EACH ROW BEGIN
   REPLACE INTO enterprise_employee_all_uplevels VALUES (NEW.employeecode, NEW.parentemployeecode, NEW.employeetypecode, ufn_get_employee_all_uplevels(NEW.employeecode), NEW.brandcode, NEW.loginaccount);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_employee_all_uplevels_after_delete`;
DELIMITER ;;
CREATE TRIGGER `trg_employee_all_uplevels_after_delete` AFTER DELETE ON `enterprise_employee` FOR EACH ROW BEGIN
   DELETE FROM enterprise_employee_all_uplevels WHERE employeecode = OLD.employeecode;
END
;;
DELIMITER ;
SET FOREIGN_KEY_CHECKS=1;
