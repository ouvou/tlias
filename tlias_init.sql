-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tlias
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clazz`
--

DROP TABLE IF EXISTS `clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clazz` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `name` varchar(30) NOT NULL COMMENT '班级名称',
  `room` varchar(20) DEFAULT NULL COMMENT '班级教室',
  `begin_date` date NOT NULL COMMENT '开课时间',
  `end_date` date NOT NULL COMMENT '结课时间',
  `master_id` int unsigned DEFAULT NULL COMMENT '班主任ID, 关联员工表ID',
  `subject` tinyint unsigned NOT NULL COMMENT '学科, 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6: 嵌入式',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz`
--

LOCK TABLES `clazz` WRITE;
/*!40000 ALTER TABLE `clazz` DISABLE KEYS */;
INSERT INTO `clazz` VALUES (1,'JavaEE就业163期','212','2024-04-30','2024-06-29',10,1,'2024-06-01 17:08:23','2024-06-01 17:39:58'),(2,'前端就业90期','210','2024-07-10','2024-01-20',3,2,'2024-06-01 17:45:12','2024-06-01 17:45:12'),(3,'JavaEE就业165期','108','2024-06-15','2024-12-25',6,1,'2024-06-01 17:45:40','2024-06-01 17:45:40'),(4,'JavaEE就业166期','105','2024-07-20','2024-02-20',20,1,'2024-06-01 17:46:10','2024-06-01 17:46:10'),(5,'大数据就业58期','209','2024-08-01','2024-02-15',7,3,'2024-06-01 17:51:21','2024-06-01 17:51:21'),(6,'JavaEE就业167期','325','2024-11-20','2024-05-10',36,1,'2024-11-15 11:35:46','2024-12-13 14:31:24'),(7,'转生成为GO学长','401','2025-09-30','2025-12-30',9,5,'2025-09-24 11:10:01','2025-09-24 11:10:01'),(8,'转行学前端','201','2025-09-24','2025-11-24',69,2,'2025-09-24 11:10:43','2025-09-24 11:10:43'),(9,'阿达专属班','103','2025-09-26','2025-09-30',7,1,'2025-09-26 13:22:21','2025-09-26 13:22:21');
/*!40000 ALTER TABLE `clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dept` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `name` varchar(10) NOT NULL COMMENT '部门名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES (1,'学工部','2024-09-25 09:47:40','2025-10-27 09:13:33'),(2,'教研部','2025-09-11 19:27:29','2025-10-27 09:13:35'),(3,'咨询部','2024-09-25 09:47:40','2025-10-27 09:13:36'),(5,'人事部','2024-09-25 09:47:40','2025-10-27 09:13:37'),(64,'财务部','2025-10-16 19:10:51','2025-10-27 09:13:31');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp`
--

DROP TABLE IF EXISTS `emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT '123456' COMMENT '密码',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `gender` tinyint unsigned NOT NULL COMMENT '性别, 1:男, 2:女',
  `phone` char(11) NOT NULL COMMENT '手机号',
  `job` tinyint unsigned DEFAULT NULL COMMENT '职位, 1 班主任, 2 讲师 , 3 学工主管, 4 教研主管, 5 咨询师',
  `salary` int unsigned DEFAULT NULL COMMENT '薪资',
  `image` varchar(255) DEFAULT NULL COMMENT '头像',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `dept_id` int unsigned DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp`
--

LOCK TABLES `emp` WRITE;
/*!40000 ALTER TABLE `emp` DISABLE KEYS */;
INSERT INTO `emp` VALUES (1,'shinaian','123456','施耐庵',1,'13309090001',4,15000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2000-01-01',2,'2023-10-20 16:35:33','2023-11-16 16:11:26'),(2,'songjiang','123456','宋江',1,'13309090002',2,8600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:37'),(3,'lujunyi','123456','卢俊义',1,'13309090003',2,8900,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-05-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:39'),(4,'wuyong','123456','吴用',1,'13309090004',2,9200,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:41'),(5,'gongsunsheng','123456','公孙胜',1,'13309090005',2,9500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-12-05',2,'2023-10-20 16:35:33','2023-10-20 16:35:43'),(6,'huosanniang','123456','扈三娘',2,'13309090006',3,6500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2013-09-05',1,'2023-10-20 16:35:33','2023-10-20 16:35:45'),(7,'chaijin','123456','柴进',1,'13309090007',1,4700,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2005-08-01',1,'2023-10-20 16:35:33','2023-10-20 16:35:47'),(8,'likui','123456','李逵',1,'13309090008',1,4800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2014-11-09',1,'2023-10-20 16:35:33','2023-10-20 16:35:49'),(9,'wusong','123456','武松',1,'13309090009',1,4900,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-03-11',1,'2023-10-20 16:35:33','2023-10-20 16:35:51'),(10,'linchong','123456','林冲',1,'13309090010',1,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2013-09-05',1,'2023-10-20 16:35:33','2023-10-20 16:35:53'),(11,'huyanzhuo','123456','呼延灼',1,'13309090011',2,9700,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-02-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:55'),(12,'xiaoliguang','123456','小李广',1,'13309090012',2,10000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-08-18',2,'2023-10-20 16:35:33','2023-10-20 16:35:57'),(13,'yangzhi','123456','杨志',1,'13309090013',1,5300,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-11-01',1,'2023-10-20 16:35:33','2023-10-20 16:35:59'),(14,'shijin','123456','史进',1,'13309090014',2,10600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2002-08-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:01'),(15,'sunerniang','123456','孙二娘',2,'13309090015',2,10900,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-05-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:03'),(16,'luzhishen','123456','鲁智深',1,'13309090016',2,9600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2010-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:05'),(17,'liying','12345678','李应',1,'13309090017',1,5800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-03-21',1,'2023-10-20 16:35:33','2023-10-20 16:36:07'),(18,'shiqian','123456','时迁',1,'13309090018',2,10200,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:09'),(19,'gudasao','123456','顾大嫂',2,'13309090019',2,10500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:11'),(20,'ruanxiaoer','123456','阮小二',1,'13309090020',2,10800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2018-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:13'),(21,'ruanxiaowu','123456','阮小五',1,'13309090021',5,5200,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:15'),(22,'ruanxiaoqi','123456','阮小七',1,'13309090022',5,5500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2016-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:17'),(23,'ruanji','123456','阮籍',1,'13309090023',5,5800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:19'),(24,'tongwei','123456','童威',1,'13309090024',5,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2006-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:21'),(25,'tongmeng','123456','童猛',1,'13309090025',5,4800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2002-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:23'),(26,'yanshun','123456','燕顺',1,'13309090026',5,5400,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-01-01',3,'2023-10-20 16:35:33','2023-11-08 22:12:46'),(28,'lizhong','123456','李忠',1,'13309090028',5,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-01-01',3,'2023-10-20 16:35:33','2023-11-17 16:34:22'),(30,'liyun','123456','李云',1,'13309090030',NULL,NULL,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2020-03-01',NULL,'2023-10-20 16:35:33','2023-10-20 16:36:31'),(36,'linghuchong','123456','令狐冲',1,'18809091212',2,6800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2023-10-19',2,'2023-10-20 20:44:54','2023-11-09 09:41:04'),(44,'yuebuqun','12345678','岳不群',1,'18809091236',1,8000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2022-09-03-07-37-38222.jpg','2022-09-18',1,'2025-09-23 21:04:26','2025-09-25 18:04:02'),(67,'Logic','123456','罗辑',1,'12085209630',5,18000,'https://java-sue.oss-cn-beijing.aliyuncs.com/2025/09/fd00cd39-d354-4932-8f6f-b9a8311fded8.jpg','2025-09-25',3,'2025-09-25 18:05:55','2025-09-26 21:06:24'),(68,'Mark','123456789','马克',1,'13579246810',6,18000,'https://java-sue.oss-cn-beijing.aliyuncs.com/2025/09/9b107f6f-149c-4067-a963-e0b940eb4b9c.png','2025-09-25',5,'2025-09-25 18:13:55','2025-10-20 22:07:14'),(69,'qilin','123456','琪琳',2,'15696968520',1,12500,'https://java-sue.oss-cn-beijing.aliyuncs.com/2025/10/7dc1a66c-a9a1-4568-a039-23ae924f4528.jpg','2025-09-30',2,'2025-10-13 16:17:40','2025-10-13 16:17:40'),(70,'zhangshan','123456','张山',1,'15396967410',2,6800,'https://java-sue.oss-cn-beijing.aliyuncs.com/2025/10/b4c38b58-0e6e-4d56-ad3e-4e1f41456e28.png','2025-10-20',1,'2025-10-20 14:09:05','2025-10-27 09:13:53'),(71,'lisi','123456','李思',2,'15202208520',5,8500,'https://java-sue.oss-cn-beijing.aliyuncs.com/2025/10/f9f2c9e9-89c5-42fc-8bcf-c1216f7c9e29.jpg','2025-10-20',3,'2025-10-20 14:18:53','2025-10-27 09:13:51');
/*!40000 ALTER TABLE `emp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_expr`
--

DROP TABLE IF EXISTS `emp_expr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp_expr` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `emp_id` int unsigned DEFAULT NULL COMMENT '员工ID',
  `begin` date DEFAULT NULL COMMENT '开始时间',
  `end` date DEFAULT NULL COMMENT '结束时间',
  `company` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `job` varchar(50) DEFAULT NULL COMMENT '职位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工作经历';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_expr`
--

LOCK TABLES `emp_expr` WRITE;
/*!40000 ALTER TABLE `emp_expr` DISABLE KEYS */;
INSERT INTO `emp_expr` VALUES (17,44,'2012-07-01','2019-03-03','百度科技股份有限公司','java开发'),(18,44,'2019-03-15','2023-03-01','阿里巴巴科技股份有限公司','架构师'),(24,NULL,'2025-09-01','2025-09-25','龙骨村','玛娜生态嵌合体'),(25,NULL,'2025-09-25','2025-11-06','玛娜生态','玛娜生态体'),(28,67,'2025-09-11','2025-09-26','联合国','面壁者'),(29,69,'2024-10-01','2025-09-01','传智教育','教研主任'),(35,68,'2022-09-13','2022-09-30','灯塔','猎荒者队长'),(36,68,'2025-05-01','2025-08-30','龙谷村','正义队长'),(37,68,'2025-10-20','2026-10-30','玛娜生态','生态嵌合体'),(39,71,'2025-10-01','2025-10-20','百度','讲师'),(40,70,'2025-10-01','2025-10-20','ITcast','教师');
/*!40000 ALTER TABLE `emp_expr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_log`
--

DROP TABLE IF EXISTS `emp_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID, 主键',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `info` varchar(2000) DEFAULT NULL COMMENT '日志信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_log`
--

LOCK TABLES `emp_log` WRITE;
/*!40000 ALTER TABLE `emp_log` DISABLE KEYS */;
INSERT INTO `emp_log` VALUES (1,'2025-09-23 23:32:44','新增员工：Emp(id=57, username=yilin, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=15200, image=, entryDate=2025-09-23, deptId=2, createTime=2025-09-23T23:32:44.160480800, updateTime=2025-09-23T23:32:44.160480800, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-17, end=2025-09-23, company=黑马智学, job=教师)])'),(2,'2025-09-24 09:23:00','新增员工：Emp(id=58, username=yilin, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=12000, image=, entryDate=2025-09-24, deptId=2, createTime=2025-09-24T09:22:59.575927100, updateTime=2025-09-24T09:22:59.575927100, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-08, end=2025-09-24, company=黑马智学, job=教师)])'),(3,'2025-09-24 09:23:31','新增员工：Emp(id=59, username=zhangsan, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=12000, image=, entryDate=2025-09-24, deptId=2, createTime=2025-09-24T09:23:31.445619400, updateTime=2025-09-24T09:23:31.445619400, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-08, end=2025-09-24, company=黑马智学, job=教师)])'),(4,'2025-09-24 09:30:55','新增员工:Emp(id=60, username=zhangsan, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=12315, image=, entryDate=2025-09-24, deptId=2, createTime=2025-09-24T09:30:28.335111600, updateTime=2025-09-24T09:30:28.335111600, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-10, end=2025-10-23, company=黑马智学, job=讲师)])'),(5,'2025-09-24 09:31:01','新增员工:Emp(id=61, username=zhangsan, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=12315, image=, entryDate=2025-09-24, deptId=2, createTime=2025-09-24T09:30:59.564718500, updateTime=2025-09-24T09:30:59.564718500, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-10, end=2025-10-23, company=黑马智学, job=讲师), EmpExpr(id=null, empId=null, begin=null, end=null, company=, job=)])'),(6,'2025-09-24 09:31:01','新增员工:Emp(id=null, username=zhangsan, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=12315, image=, entryDate=2025-09-24, deptId=2, createTime=2025-09-24T09:30:59.564718500, updateTime=2025-09-24T09:30:59.564718500, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-10, end=2025-10-23, company=黑马智学, job=讲师), EmpExpr(id=null, empId=null, begin=null, end=null, company=, job=)])'),(7,'2025-09-24 09:31:01','新增员工:Emp(id=null, username=zhangsan, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=12315, image=, entryDate=2025-09-24, deptId=2, createTime=2025-09-24T09:30:59.565733, updateTime=2025-09-24T09:30:59.565733, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-10, end=2025-10-23, company=黑马智学, job=讲师), EmpExpr(id=null, empId=null, begin=null, end=null, company=, job=)])'),(8,'2025-09-24 09:31:09','新增员工:Emp(id=64, username=zhangsan, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=12315, image=, entryDate=2025-09-24, deptId=2, createTime=2025-09-24T09:31:08.883802200, updateTime=2025-09-24T09:31:08.883802200, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-10, end=2025-10-23, company=黑马智学, job=讲师), EmpExpr(id=null, empId=null, begin=null, end=null, company=, job=)])'),(9,'2025-09-24 09:31:19','新增员工:Emp(id=65, username=阿伟达瓦, password=null, name=依琳, gender=2, phone=12045201520, job=2, salary=12315, image=, entryDate=2025-09-24, deptId=2, createTime=2025-09-24T09:31:18.582496900, updateTime=2025-09-24T09:31:18.582496900, deptName=null, exprList=[EmpExpr(id=null, empId=null, begin=2025-09-10, end=2025-10-23, company=黑马智学, job=讲师), EmpExpr(id=null, empId=null, begin=null, end=null, company=, job=)])'),(10,'2025-09-25 09:45:44','新增员工:Emp(id=66, username=yilin, password=null, name=依琳, gender=2, phone=15250131420, job=2, salary=12000, image=https://java-sue.oss-cn-beijing.aliyuncs.com/2025/09/e0debbd3-281e-4ddc-af0d-2fe69763e7b4.jpg, entryDate=2025-09-25, deptId=2, createTime=2025-09-25T09:45:44.103688500, updateTime=2025-09-25T09:45:44.103688500, deptName=null, exprList=[EmpExpr(id=null, empId=66, begin=2025-09-01, end=2025-09-24, company=黑马智学, job=教师)])'),(11,'2025-09-25 18:05:55','新增员工:Emp(id=67, username=chenjingchou, password=null, name=陈靖仇, gender=1, phone=12085209630, job=6, salary=18000, image=https://java-sue.oss-cn-beijing.aliyuncs.com/2025/09/fd00cd39-d354-4932-8f6f-b9a8311fded8.jpg, entryDate=2025-09-25, deptId=3, createTime=2025-09-25T18:05:54.516350600, updateTime=2025-09-25T18:05:54.516350600, deptName=null, exprList=[])'),(12,'2025-09-25 18:13:55','新增员工:Emp(id=68, username=Mark, password=null, name=马克, gender=1, phone=13579246810, job=6, salary=18000, image=https://java-sue.oss-cn-beijing.aliyuncs.com/2025/09/9b107f6f-149c-4067-a963-e0b940eb4b9c.png, entryDate=2025-09-25, deptId=5, createTime=2025-09-25T18:13:54.682594600, updateTime=2025-09-25T18:13:54.682594600, deptName=null, exprList=[EmpExpr(id=null, empId=68, begin=null, end=null, company=, job=)])'),(13,'2025-10-13 16:17:40','新增员工:Emp(id=69, username=qilin, password=null, name=琪琳, gender=2, phone=15696968520, job=1, salary=12500, image=https://java-sue.oss-cn-beijing.aliyuncs.com/2025/10/7dc1a66c-a9a1-4568-a039-23ae924f4528.jpg, entryDate=2025-09-30, deptId=2, createTime=2025-10-13T16:17:40.287586800, updateTime=2025-10-13T16:17:40.287586800, deptName=null, exprList=[EmpExpr(id=null, empId=69, begin=2024-10-01, end=2025-09-01, company=传智教育, job=教研主任)])'),(14,'2025-10-20 14:09:05','新增员工:Emp(id=70, username=zhangshan, password=null, name=张山, gender=1, phone=15396967410, job=2, salary=6800, image=https://java-sue.oss-cn-beijing.aliyuncs.com/2025/10/b4c38b58-0e6e-4d56-ad3e-4e1f41456e28.png, entryDate=2025-10-20, deptId=1, createTime=2025-10-20T14:09:05.291637500, updateTime=2025-10-20T14:09:05.291637500, deptName=null, exprList=[EmpExpr(id=null, empId=70, begin=2025-10-01, end=2025-10-20, company=ITcast, job=教师)])'),(15,'2025-10-20 14:18:53','新增员工:Emp(id=71, username=lisi, password=null, name=李思, gender=2, phone=15202208520, job=5, salary=8500, image=https://java-sue.oss-cn-beijing.aliyuncs.com/2025/10/f9f2c9e9-89c5-42fc-8bcf-c1216f7c9e29.jpg, entryDate=2025-10-20, deptId=3, createTime=2025-10-20T14:18:52.739326600, updateTime=2025-10-20T14:18:52.739326600, deptName=null, exprList=[EmpExpr(id=null, empId=71, begin=2025-10-01, end=2025-10-20, company=百度, job=讲师)])'),(16,'2025-10-20 22:19:15','新增员工:Emp(id=72, username=123, password=null, name=123, gender=1, phone=15258585885, job=2, salary=9999, image=, entryDate=2025-09-30, deptId=64, createTime=2025-10-20T22:19:15.387266700, updateTime=2025-10-20T22:19:15.387266700, deptName=null, exprList=[EmpExpr(id=null, empId=72, begin=2025-10-09, end=2025-11-10, company=itcast, job=讲师)])'),(17,'2025-10-20 22:41:13','新增员工:Emp(id=73, username=wqdqw, password=null, name=45757, gender=1, phone=15322332134, job=null, salary=null, image=, entryDate=null, deptId=null, createTime=2025-10-20T22:41:12.756917600, updateTime=2025-10-20T22:41:12.756917600, deptName=null, exprList=[])'),(18,'2025-10-20 22:41:42','新增员工:Emp(id=74, username=455445, password=null, name=4545454, gender=2, phone=15538685527, job=null, salary=null, image=, entryDate=null, deptId=null, createTime=2025-10-20T22:41:41.610804700, updateTime=2025-10-20T22:41:41.610804700, deptName=null, exprList=[])');
/*!40000 ALTER TABLE `emp_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_login_log`
--

DROP TABLE IF EXISTS `emp_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp_login_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `is_success` tinyint unsigned DEFAULT NULL COMMENT '是否成功, 1:成功, 0:失败',
  `jwt` varchar(1000) DEFAULT NULL COMMENT 'JWT令牌',
  `cost_time` bigint unsigned DEFAULT NULL COMMENT '耗时, 单位:ms',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='登录日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_login_log`
--

LOCK TABLES `emp_login_log` WRITE;
/*!40000 ALTER TABLE `emp_login_log` DISABLE KEYS */;
INSERT INTO `emp_login_log` VALUES (1,'Mark','123456','2025-09-30 21:05:18',0,NULL,80),(2,'Mark','123456','2025-09-30 21:14:53',0,NULL,77),(3,'Mark','123456','2025-09-30 21:18:44',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc1OTI4MTUyM30.CtwKr8nbqwDGrMxTivdn9OZGE651_0zS_4gMilMZyyI',107),(4,'songjiang','123456','2025-09-30 21:24:09',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MiwidXNlcm5hbWUiOiJzb25namlhbmciLCJleHAiOjE3NTkyODE4NDh9.OpNmS5B0_82112y5eFg4VZA1QlCApsvLIYmvNvE3tZM',4),(5,'zhangsan','123456+58','2025-09-30 21:26:05',0,NULL,10),(6,'linchong','123456','2025-09-30 21:26:26',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoibGluY2hvbmciLCJleHAiOjE3NTkyODE5ODV9.VEDIEX8EDezORcG2yuEZEVNYxGd_ogoN1fSc_5QCKNk',6),(7,'阿达','123456','2025-10-09 16:15:23',0,NULL,443),(8,'Mark','123456','2025-10-09 16:15:55',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2MDA0MDk1NX0.YGdcSo0jrPEQ9rP9dHi1g6yfRAH6XPBY07Xsv-XDX44',17),(9,'Mark','123456','2025-10-10 10:36:47',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2MDEwNzAwN30.jHfdLcqeaFGRLEPJYpqV8r_UezgjP3d_P_kSPOixEVg',72),(10,'Mark','123456','2025-10-12 16:24:01',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2MDMwMDY0MH0.O94hS3bD-WlL4BKT94sAbpFP29y1SD7g3cQVGxTUz2w',69),(11,'Mark','123456','2025-10-13 15:07:06',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2MDM4MjQyNn0.ph8i1U9kmOn1_46tnS0OiybGtIwPQFPo4S-Sepkpm-A',75),(12,'mark','123456','2025-10-14 09:57:21',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2MDQ1MDI0MX0.8mXB-UvMfDOcATlBwDuIjnIeSQwb7g4jEjKAJB6U0sc',72),(13,'mark','1234','2025-10-15 09:41:49',0,NULL,92),(14,'mark','123456','2025-10-15 09:41:58',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2MDUzNTcxN30.kQkDv0a2F7N-ULR8HU452HfdvfQTuSkZ7hfrP8aQ0ks',9),(15,'songjiang','123456','2025-10-22 00:42:45',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MiwidXNlcm5hbWUiOiJzb25namlhbmciLCJleHAiOjE3NjExMDgxNjR9.IoMguElQhaf4djQ4R-Glh9-XQnTmMJC4DJ83HiEIP8g',65),(16,'mark','123456','2025-10-23 00:26:53',0,NULL,8),(17,'mark','123456789','2025-10-23 00:26:55',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2MTE5MzYxNX0.14L6YkxizFLSrxNTVB87Odwf6YpW6BV5HAihPQhSD1M',12),(18,'mark','123456','2025-11-28 08:46:29',0,NULL,68),(19,'mark','123456','2025-11-28 08:46:32',0,NULL,2),(20,'mark','123456','2025-11-28 08:46:36',0,NULL,3),(21,'linchong','123456','2025-11-28 08:46:43',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoibGluY2hvbmciLCJleHAiOjE3NjQzMzQwMDJ9.9Z9w51JTZOH3pXQL80f_a8YHe6B5LOcdzaydDdpGcGc',8),(22,'Mark','123456','2025-12-03 09:56:24',0,NULL,67),(23,'Mark','123456','2025-12-03 09:56:42',0,NULL,2),(24,'Mark','123456789','2025-12-03 09:57:02',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2NDc3MDIyMX0.Y2Or9pFf7tx2fi3jmBcsy-AC_ea9WyJrn0M3uoHeWLg',19),(25,'Mark','123456','2025-12-03 10:08:03',0,NULL,2),(26,'Mark','123456789','2025-12-03 10:08:07',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2NDc3MDg4Nn0.kImBMomoE4HGLYW-04MY9_pKbNZTRP3wtjZDHUGqMYM',2),(27,'mark','123456789','2025-12-06 13:46:08',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2NTA0MzE2OH0.Q6sIciV_SrMyf5QvhMOqn3LjBhlmnNkrus6OE0q3S98',421),(28,'mark','123456789','2025-12-10 09:01:53',1,'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NjgsInVzZXJuYW1lIjoiTWFyayIsImV4cCI6MTc2NTM3MTcxM30.MS97nRNO_l5paL1UK8ziLPAFNpP6drL40hEc9or2Aac',432);
/*!40000 ALTER TABLE `emp_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operate_log`
--

DROP TABLE IF EXISTS `operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operate_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_emp_id` int unsigned DEFAULT NULL COMMENT '操作人ID',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `class_name` varchar(100) DEFAULT NULL COMMENT '操作的类名',
  `method_name` varchar(100) DEFAULT NULL COMMENT '操作的方法名',
  `method_params` varchar(2000) DEFAULT NULL COMMENT '方法参数',
  `return_value` varchar(2000) DEFAULT NULL COMMENT '返回值',
  `cost_time` bigint unsigned DEFAULT NULL COMMENT '方法执行耗时, 单位:ms',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operate_log`
--

LOCK TABLES `operate_log` WRITE;
/*!40000 ALTER TABLE `operate_log` DISABLE KEYS */;
INSERT INTO `operate_log` VALUES (1,1,'2025-09-30 00:25:35','com.itheima.controller.DeptController','add','[Dept(id=null, name=668, createTime=2025-09-30T00:25:34.602341900, updateTime=2025-09-30T00:25:34.602341900)]','Result(code=1, msg=success, data=null)',8),(2,1,'2025-09-30 00:25:38','com.itheima.controller.DeptController','update','[Dept(id=14, name=66866, createTime=2025-09-30T00:25:35, updateTime=2025-09-30T00:25:38.343195800)]','Result(code=1, msg=success, data=null)',3),(3,1,'2025-09-30 00:25:40','com.itheima.controller.DeptController','delete','[14]','Result(code=1, msg=success, data=null)',20),(4,2,'2025-09-30 01:05:17','com.itheima.controller.DeptController','add','[Dept(id=null, name=233, createTime=2025-09-30T01:05:16.889614500, updateTime=2025-09-30T01:05:16.889614500)]','Result(code=1, msg=success, data=null)',33),(5,2,'2025-09-30 01:05:22','com.itheima.controller.DeptController','update','[Dept(id=15, name=332, createTime=2025-09-30T01:05:17, updateTime=2025-09-30T01:05:21.666295300)]','Result(code=1, msg=success, data=null)',5),(6,2,'2025-09-30 01:05:23','com.itheima.controller.DeptController','delete','[15]','Result(code=1, msg=success, data=null)',14),(7,68,'2025-09-30 01:06:31','com.itheima.controller.DeptController','add','[Dept(id=null, name=资源部, createTime=2025-09-30T01:06:31.224558900, updateTime=2025-09-30T01:06:31.224558900)]','Result(code=1, msg=success, data=null)',4),(8,68,'2025-09-30 01:06:38','com.itheima.controller.DeptController','delete','[16]','Result(code=1, msg=success, data=null)',6),(9,68,'2025-10-13 16:17:40','com.itheima.controller.EmpController','save','[Emp(id=69, username=qilin, password=null, name=琪琳, gender=2, phone=15696968520, job=1, salary=12500, image=https://java-sue.oss-cn-beijing.aliyuncs.com/2025/10/7dc1a66c-a9a1-4568-a039-23ae924f4528.jpg, entryDate=2025-09-30, deptId=2, createTime=2025-10-13T16:17:40.287586800, updateTime=2025-10-13T16:17:40.287586800, deptName=null, exprList=[EmpExpr(id=null, empId=69, begin=2024-10-01, end=2025-09-01, company=传智教育, job=教研主任)])]','Result(code=1, msg=success, data=null)',48),(10,68,'2025-10-14 09:59:11','com.itheima.controller.DeptController','update','[Dept(id=1, name=工学部, createTime=2024-09-25T09:47:40, updateTime=2025-10-14T09:59:11.115229200)]','Result(code=1, msg=success, data=null)',5),(11,68,'2025-10-14 09:59:25','com.itheima.controller.DeptController','update','[Dept(id=1, name=学工部, createTime=2024-09-25T09:47:40, updateTime=2025-10-14T09:59:24.774654800)]','Result(code=1, msg=success, data=null)',9),(12,68,'2025-10-23 00:00:45','com.itheima.controller.EmpController','updatePassword','[UserPassword(id=68, oldPassword=1235, newPassword=123456789)]','Result(code=0, msg=旧密码错误, data=null)',52),(13,68,'2025-10-23 00:23:51','com.itheima.controller.EmpController','updatePassword','[UserPassword(id=null, oldPassword=null, newPassword=null)]','Result(code=0, msg=用户不存在, data=null)',4),(14,68,'2025-10-23 00:23:55','com.itheima.controller.EmpController','updatePassword','[UserPassword(id=null, oldPassword=null, newPassword=null)]','Result(code=0, msg=用户不存在, data=null)',4),(15,68,'2025-10-23 00:26:43','com.itheima.controller.EmpController','updatePassword','[UserPassword(id=68, oldPassword=123456, newPassword=123456789)]','Result(code=1, msg=success, data=null)',11),(16,68,'2025-12-03 09:58:15','com.itheima.controller.StudentController','updateViolation','[7, 666]','Result(code=0, msg=违纪分数必须在1-255之间, data=null)',1),(17,68,'2025-12-03 09:58:20','com.itheima.controller.StudentController','updateViolation','[7, 10]','Result(code=1, msg=success, data=null)',11),(18,68,'2025-12-03 09:58:27','com.itheima.controller.StudentController','update','[Student(id=7, name=Locos, no=2023001010, gender=1, phone=13712345678, degree=5, idCard=110090110090110090, isCollege=1, address=回龙观东大街110号, graduationDate=2020-07-01, violationCount=3, violationScore=16, clazzId=2, createTime=2024-11-14T21:22:19, updateTime=2025-12-03T09:58:26.607236200, clazzName=前端就业90期)]','Result(code=1, msg=success, data=null)',10),(19,68,'2025-12-03 10:10:58','com.itheima.controller.EmpController','delete','[[27]]','Result(code=1, msg=success, data=null)',12);
/*!40000 ALTER TABLE `operate_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `no` char(10) NOT NULL COMMENT '学号',
  `gender` tinyint unsigned NOT NULL COMMENT '性别, 1: 男, 2: 女',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `id_card` char(18) NOT NULL COMMENT '身份证号',
  `is_college` tinyint unsigned NOT NULL COMMENT '是否来自于院校, 1:是, 0:否',
  `address` varchar(100) DEFAULT NULL COMMENT '联系地址',
  `degree` tinyint unsigned DEFAULT NULL COMMENT '最高学历, 1:初中, 2:高中, 3:大专, 4:本科, 5:硕士, 6:博士',
  `graduation_date` date DEFAULT NULL COMMENT '毕业时间',
  `clazz_id` int unsigned NOT NULL COMMENT '班级ID, 关联班级表ID',
  `violation_count` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '违纪次数',
  `violation_score` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '违纪扣分',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `no` (`no`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `id_card` (`id_card`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'段誉','2022000001',1,'18800000001','110120000300200001',1,'北京市昌平区建材城西路1号',1,'2021-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-15 16:20:59'),(2,'萧峰','2022000002',1,'18800210003','110120000300200002',1,'北京市昌平区建材城西路2号',2,'2022-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(3,'虚竹','2022000003',1,'18800013001','110120000300200003',1,'北京市昌平区建材城西路3号',2,'2024-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(4,'萧远山','2022000004',1,'18800003211','110120000300200004',1,'北京市昌平区建材城西路4号',3,'2024-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(5,'阿朱','2022000005',2,'18800160002','110120000300200005',1,'北京市昌平区建材城西路5号',4,'2020-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(6,'阿紫','2022000006',2,'18800000034','110120000300200006',1,'北京市昌平区建材城西路6号',4,'2021-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(7,'Locos','2023001010',1,'13712345678','110090110090110090',1,'回龙观东大街110号',5,'2020-07-01',2,3,16,'2024-11-14 21:22:19','2025-12-03 09:58:27'),(8,'康敏','2022000008',2,'18800000077','110120000300200008',1,'北京市昌平区建材城西路8号',5,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(9,'徐长老','2022000009',1,'18800000341','110120000300200009',1,'北京市昌平区建材城西路9号',3,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(10,'云中鹤','2022000010',1,'18800006571','110120000300200010',1,'北京市昌平区建材城西路10号',2,'2020-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(11,'钟万仇','2022000011',1,'18800000391','110120000300200011',1,'北京市昌平区建材城西路11号',4,'2021-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:24'),(12,'崔百泉','2022000012',1,'18800000781','110120000300200018',1,'北京市昌平区建材城西路12号',4,'2022-07-05',3,6,17,'2024-11-14 21:22:19','2024-12-13 14:33:58'),(13,'耶律洪基','2022000013',1,'18800008901','110120000300200013',1,'北京市昌平区建材城西路13号',4,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:21'),(14,'天山童姥','2022000014',2,'18800009201','110120000300200014',1,'北京市昌平区建材城西路14号',4,'2024-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:17'),(15,'刘竹庄','2022000015',1,'18800009401','110120000300200015',1,'北京市昌平区建材城西路15号',3,'2020-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(16,'李春来','2022000016',1,'18800008501','110120000300200016',1,'北京市昌平区建材城西路16号',4,'2021-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(17,'王语嫣','2022000017',2,'18800007601','110120000300200017',1,'北京市昌平区建材城西路17号',2,'2022-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(18,'郑成功','2024001101',1,'13309092345','110110110110110110',0,'北京市昌平区回龙观街道88号',5,'2021-07-01',3,2,7,'2024-11-15 16:26:18','2024-11-15 16:40:10'),(19,'阿达','2024010801',1,'15909091235','159090912351590909',1,'昌平回龙观',4,'2024-01-01',9,9,255,'2025-09-26 13:20:21','2025-09-26 18:55:45'),(20,'陈靖仇','2025092601',1,'13074108520','110100200407278151',1,'北京市南锣鼓巷1号',4,'2024-06-30',1,0,0,NULL,'2025-09-26 13:19:21'),(22,'依琳','1520131420',2,'15201314520','421202200202028520',1,'湖北省武汉市',5,'2025-10-23',8,0,0,'2025-10-23 11:46:10','2025-10-23 11:46:22');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-24 18:53:11
