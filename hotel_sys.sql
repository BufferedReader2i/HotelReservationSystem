-- MySQL dump 10.13  Distrib 8.3.0, for Win64 (x86_64)
--
-- Host: localhost    Database: hotelreservationsystem
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `guest`
--

DROP TABLE IF EXISTS `guest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guest` (
  `GuestID` int NOT NULL AUTO_INCREMENT,
  `OrderID` int NOT NULL,
  `Name` varchar(100) NOT NULL,
  `IDNumber` varchar(18) NOT NULL,
  `Age` int DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `Occupation` varchar(100) DEFAULT NULL,
  `EducationLevel` varchar(100) DEFAULT NULL,
  `IncomeStatus` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`GuestID`),
  KEY `OrderID` (`OrderID`),
  CONSTRAINT `guest_ibfk_1` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest`
--

LOCK TABLES `guest` WRITE;
/*!40000 ALTER TABLE `guest` DISABLE KEYS */;
INSERT INTO `guest` VALUES (9,8,'neo','123456789123456789',34,'男','工程师','研究生','中等'),(10,9,'James','123456789123456789',25,'男','工程师','高中','中等'),(11,10,'Lily','123456781234567899',12,'女','学生','其他','较低'),(13,11,'王无','123456789123456789',34,'男','公司经理','研究生','高'),(14,13,'周生','123456780123456780',35,'男','医生','其他','中等'),(15,15,'James','123456789123456789',18,'男','学生','高中','较低'),(16,16,'qwe','123456789123456789',23,'男','销售员','本科','中等'),(17,16,'ert','123456789123456780',24,'男','销售员','本科','中等'),(18,17,'Neo','123456789123456789',20,'男','学生','本科','较低'),(19,18,'wang','452201200405251633',27,'男','学生','本科','中等');
/*!40000 ALTER TABLE `guest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel` (
  `HotelID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `City` varchar(100) NOT NULL,
  `District` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`HotelID`),
  KEY `name_select` (`Name`),
  KEY `city_select` (`City`),
  KEY `union_select` (`City`,`Name`),
  KEY `union_index` (`Address`,`District`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (1,'苏州七天连锁','吴中区龙西路10号','苏州','吴中区'),(2,'26号花园民宿','西湖区龙跑路26号','杭州','西湖区'),(3,'如家精选酒店','浦东新区南芦公路1758弄2号','上海','浦东新区'),(4,'遇柒酒店','上城区青年路82号','杭州','上城区'),(5,'卡尔文酒店','花都区迎宾大道26号','广州','花都区'),(6,'桔子酒店','天河区华观路万科广场A4栋5楼','广州','天河区'),(7,'沃德酒店','崂山区辽阳东路12号','青岛','崂山区');
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoteladmin`
--

DROP TABLE IF EXISTS `hoteladmin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoteladmin` (
  `AdminID` int NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `HotelID` int NOT NULL,
  PRIMARY KEY (`AdminID`),
  KEY `HotelID` (`HotelID`),
  CONSTRAINT `hoteladmin_ibfk_1` FOREIGN KEY (`HotelID`) REFERENCES `hotel` (`HotelID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoteladmin`
--

LOCK TABLES `hoteladmin` WRITE;
/*!40000 ALTER TABLE `hoteladmin` DISABLE KEYS */;
INSERT INTO `hoteladmin` VALUES (1,'20221532','$2a$10$eEE4ocOlIt.iuGO3oDWwEex5XnwZGdAlk83YjCws.TKA5ftcm4h/S',1),(2,'Li','$2a$10$eEE4ocOlIt.iuGO3oDWwEex5XnwZGdAlk83YjCws.TKA5ftcm4h/S',2);
/*!40000 ALTER TABLE `hoteladmin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `hotelroomdetails`
--

DROP TABLE IF EXISTS `hotelroomdetails`;
/*!50001 DROP VIEW IF EXISTS `hotelroomdetails`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `hotelroomdetails` AS SELECT 
 1 AS `HotelName`,
 1 AS `Address`,
 1 AS `City`,
 1 AS `District`,
 1 AS `RoomTypeName`,
 1 AS `Description`,
 1 AS `Price`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `orderguest`
--

DROP TABLE IF EXISTS `orderguest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderguest` (
  `OrderID` int NOT NULL,
  `GuestID` int NOT NULL,
  PRIMARY KEY (`OrderID`,`GuestID`),
  KEY `GuestID` (`GuestID`),
  CONSTRAINT `orderguest_ibfk_1` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`),
  CONSTRAINT `orderguest_ibfk_2` FOREIGN KEY (`GuestID`) REFERENCES `guest` (`GuestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderguest`
--

LOCK TABLES `orderguest` WRITE;
/*!40000 ALTER TABLE `orderguest` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderguest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `RoomID` int NOT NULL,
  `HotelID` int NOT NULL DEFAULT '1',
  `UserName` varchar(50) NOT NULL,
  `CheckInDate` date NOT NULL,
  `CheckOutDate` date NOT NULL,
  `TotalPrice` decimal(10,2) NOT NULL,
  `Status` varchar(50) DEFAULT NULL,
  `Comment` text NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`OrderID`),
  KEY `UserID` (`UserID`),
  KEY `orders_ibfk_2_idx` (`RoomID`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (8,1,2,2,'neo','2024-06-29','2024-06-30',350.00,'Completed','房间很干净','2024-06-29 00:50:50'),(9,1,5,2,'neo','2024-07-01','2024-07-02',200.00,'Completed','床很舒适','2024-06-29 16:04:07'),(10,1,7,4,'neo','2024-06-01','2024-06-02',418.00,'Completed','房间很干净','2024-05-30 16:42:12'),(11,1,7,4,'neo','2024-06-03','2024-06-04',418.00,'Completed','No Comment','2024-05-30 16:45:39'),(12,1,6,4,'neo','2024-07-01','2024-07-02',418.00,'Completed','No Comment','2024-06-30 16:53:11'),(13,1,7,4,'neo','2024-07-01','2024-07-02',418.00,'Completed','No Comment','2024-06-30 16:53:11'),(14,1,8,5,'neo','2024-07-16','2024-07-17',280.00,'Cancelled','No Comment','2024-06-30 17:44:52'),(15,1,9,5,'neo','2024-07-16','2024-07-17',280.00,'Completed','No Comment','2024-06-30 17:44:53'),(16,1,8,5,'neo','2024-07-02','2024-07-03',280.00,'Cancelled','No Comment','2024-06-30 23:17:53'),(17,1,9,5,'neo','2024-07-01','2024-07-03',280.00,'Cancelled','No Comment','2024-07-01 14:51:50'),(18,1,2,2,'neo','2024-07-03','2024-07-04',332.00,'Completed','No Comment','2024-07-02 14:12:08');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `AfterOrderInsert` AFTER INSERT ON `orders` FOR EACH ROW BEGIN
    -- 声明变量用于存储当前用户的原始积分
    DECLARE originalPoints INT;

    -- 从User表获取当前用户的原始积分
    SELECT Points INTO originalPoints FROM User WHERE UserID = NEW.UserID;

    -- 更新积分，将订单总价添加到原始积分上
    UPDATE User SET Points = Points + NEW.TotalPrice WHERE UserID = NEW.UserID;

    -- 计算新的积分总数
    SET originalPoints = originalPoints + NEW.TotalPrice;

    -- 根据新的积分总数更新会员等级
    IF originalPoints < 1000 THEN
        UPDATE User SET UserLevel = '普通会员' WHERE UserID = NEW.UserID AND UserLevel != '普通会员';
    ELSEIF originalPoints >= 1000 AND originalPoints < 5000 THEN
        UPDATE User SET UserLevel = '白银贵宾' WHERE UserID = NEW.UserID AND UserLevel != '白银贵宾';
    ELSEIF originalPoints >= 5000 AND originalPoints < 10000 THEN
        UPDATE User SET UserLevel = '黄金贵宾' WHERE UserID = NEW.UserID AND UserLevel != '黄金贵宾';
    
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `AfterOrderStatusUpdate` AFTER UPDATE ON `orders` FOR EACH ROW BEGIN
    -- 检查状态是否变为 Cancelled
    DECLARE orderTotalPrice DECIMAL(10, 2);
    DECLARE originalPoints INT;
    IF OLD.Status != NEW.Status AND NEW.Status = 'Cancelled' THEN

        
        SELECT TotalPrice INTO orderTotalPrice FROM Orders WHERE OrderID = NEW.OrderID;

        -- 从用户表中获取当前用户的原始积分
        
        SELECT Points INTO originalPoints FROM User WHERE UserID = NEW.UserID;

        -- 更新用户的积分
        UPDATE User SET Points = Points - orderTotalPrice WHERE UserID = NEW.UserID;

        -- 计算新的积分总数
        SET originalPoints = originalPoints - orderTotalPrice;

        -- 根据新的积分总数更新会员等级
        IF originalPoints < 1000 THEN
            UPDATE User SET UserLevel = '普通会员' WHERE UserID = NEW.UserID AND UserLevel != '普通会员';
        ELSEIF originalPoints >= 1000 AND originalPoints < 5000 THEN
            UPDATE User SET UserLevel = '白银贵宾' WHERE UserID = NEW.UserID AND UserLevel != '白银贵宾';
        ELSEIF originalPoints >= 5000 AND originalPoints < 10000 THEN
            UPDATE User SET UserLevel = '黄金贵宾' WHERE UserID = NEW.UserID AND UserLevel != '黄金贵宾';
        END IF;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `RoomID` int NOT NULL AUTO_INCREMENT,
  `RoomTypeID` int NOT NULL,
  `HotelID` int NOT NULL,
  `Price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`RoomID`),
  KEY `RoomTypeID` (`RoomTypeID`),
  KEY `HotelID` (`HotelID`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`RoomTypeID`) REFERENCES `roomtype` (`RoomTypeID`),
  CONSTRAINT `room_ibfk_2` FOREIGN KEY (`HotelID`) REFERENCES `hotel` (`HotelID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,2,1,200.00),(2,1,2,350.00),(3,3,3,600.00),(4,4,3,1888.00),(5,2,2,200.00),(6,5,4,220.00),(7,5,4,220.00),(8,6,5,295.00),(9,6,5,295.00),(10,7,5,385.00),(11,7,5,385.00),(12,8,6,250.00),(13,9,6,450.00),(24,18,1,400.00),(25,18,1,400.00),(26,18,1,400.00),(27,18,1,400.00),(28,18,1,400.00),(29,19,1,380.00),(30,19,1,380.00),(31,19,1,380.00),(32,19,1,380.00),(33,19,1,380.00);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roomtype`
--

DROP TABLE IF EXISTS `roomtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roomtype` (
  `RoomTypeID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Description` text,
  `HotelID` int NOT NULL,
  `Occupancy` int NOT NULL,
  PRIMARY KEY (`RoomTypeID`),
  KEY `HotelID` (`HotelID`),
  CONSTRAINT `roomtype_ibfk_1` FOREIGN KEY (`HotelID`) REFERENCES `hotel` (`HotelID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roomtype`
--

LOCK TABLES `roomtype` WRITE;
/*!40000 ALTER TABLE `roomtype` DISABLE KEYS */;
INSERT INTO `roomtype` VALUES (1,'标间','两张床 2人入住 ',1,2),(2,'单人间','一张床 无窗 1人入住',2,1),(3,'套房','两张双人床，一张大床 4人入住',3,4),(4,'湖边别墅','2 张特大床 3张榻榻米 2张大床 8人入住',3,8),(5,'高级双床房','两张床，含早餐 2人入住',4,2),(6,'大床房','1张特大床 2人入住 禁烟',5,2),(7,'舒适家庭房','舒适家庭房 1张大床 1张双人床 可4人入住',5,4),(8,'双床房','两张床 2人入住 ',6,2),(9,'家庭套房','家庭房 1张单人床 1张双人床 3人入住',6,4),(18,'影院大床房','1张大床 100寸高清巨幕 影视VIP 2人入住',1,2),(19,'丽致双床房','2张床 2人入住 烘干洗衣机',1,2);
/*!40000 ALTER TABLE `roomtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Points` int DEFAULT '0',
  `UserLevel` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'neo','$2a$10$eEE4ocOlIt.iuGO3oDWwEex5XnwZGdAlk83YjCws.TKA5ftcm4h/S',2037,'白银贵宾'),(2,'li','$2a$10$TriG/uIJKUhwnexBKtUnduuw4fqaVJP8kk.SRR7vPh3taU7sWR1Ra',0,'普通会员'),(3,'wqe','$2a$10$rZG4Km5/otcHO1EWJYp9t.opAOAQT0LcEiNQuKc6sKEE3oSMAp1Am',0,'普通会员'),(4,'wqew','$2a$10$gXaCvJGIl7/OXxheNmi/KukDSa2t2l.n9uLNIn4YSBFh3Tj1xufc6',0,'普通会员'),(5,'wqf','$2a$10$pUOihSGDOtakClsnPsf1S.2XRTKhqMZIyXMZGzOE6ivQXezq8w47a',0,'普通会员'),(6,'James','$2a$10$eEE4ocOlIt.iuGO3oDWwEex5XnwZGdAlk83YjCws.TKA5ftcm4h/S',0,'普通用户');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `hotelroomdetails`
--

/*!50001 DROP VIEW IF EXISTS `hotelroomdetails`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `hotelroomdetails` AS select `h`.`Name` AS `HotelName`,`h`.`Address` AS `Address`,`h`.`City` AS `City`,`h`.`District` AS `District`,`rt`.`Name` AS `RoomTypeName`,`rt`.`Description` AS `Description`,`r`.`Price` AS `Price` from ((`hotel` `h` join `roomtype` `rt` on((`h`.`HotelID` = `rt`.`HotelID`))) join `room` `r` on(((`rt`.`RoomTypeID` = `r`.`RoomTypeID`) and (`h`.`HotelID` = `r`.`HotelID`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-05 15:14:01
