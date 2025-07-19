-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: banco_db
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `dni` bigint NOT NULL,
  `cuil` varchar(30) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `sexo` varchar(10) NOT NULL,
  `nacionalidad` varchar(80) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `localidad` varchar(50) NOT NULL,
  `provincia` varchar(80) NOT NULL,
  `correo_electronico` varchar(100) NOT NULL,
  `eliminado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `uk_cliente_dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,35546642,'20355466428','Lucio','Garcia','Masculino','Argentino','1997-05-29','aguilar 3245','Capital','Buenos Aires','lgarcia@mail.com',0),(2,41098583,'20410985836','Matias','Soubelet','Masculino','Argentino','1998-12-22','calle falsa 123','Cordoba','Cordoba','matis@mail.com',1),(3,33456789,'20334567894','María','González','Femenino','Argentina','1987-03-15','Av. Corrientes 1234','Buenos Aires','Buenos Aires','maria.gonzalez@email.com',0),(4,44567890,'20445678905','Carlos','López','Masculino','Argentina','1992-07-22','San Martín 567','Córdoba','Córdoba','carlos.lopez@email.com',0),(5,35678901,'27356789016','Ana','Martínez','Femenino','Argentina','1989-11-08','Belgrano 890','Rosario','Santa Fe','ana.martinez@email.com',0),(6,42789012,'20427890127','Roberto','Silva','Masculino','Argentina','1995-01-30','Rivadavia 2345','Mendoza','Mendoza','roberto.silva@email.com',0),(7,38890123,'27388901238','Laura','Fernández','Femenino','Argentina','1991-06-12','Mitre 678','La Plata','Buenos Aires','laura.fernandez@email.com',0),(8,29456123,'20294561234','Martín','Rodríguez','Masculino','Argentina','1985-04-18','Av. San Martín 1567','Tucumán','Tucumán','martin.rodriguez@email.com',0),(9,31567234,'27315672349','Valentina','Torres','femenino','Argentina','1988-09-25','Sarmiento 890','Salta','Salta','valu_kpa@email.com',0),(10,36678345,'20366783456','Diego','Morales','Masculino','Argentina','1990-12-03','Independencia 2234','Neuquén','Neuquén','diego.morales@email.com',0),(11,40789456,'27407894567','Sofía','Vargas','Femenino','Argentina','1993-02-14','España 445','Bahía Blanca','Buenos Aires','sofia.vargas@email.com',0),(12,33890567,'20338905678','Joaquín','Herrera','Masculino','Argentina','1986-08-07','Pellegrini 1123','Santa Fe','Santa Fe','joaquin.herrera@email.com',0),(13,27637282,'20276372828','Carlos','Rodriguez','masculino','Argentino','1991-10-10','Julian Navarro','RÃ­o Cuarto','Buenos Aires','carlosprofe@mail.com',0);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
  `id_cuenta` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `fecha_creacion` date NOT NULL,
  `id_tipo_cuenta` int NOT NULL,
  `numero_cuenta` varchar(20) NOT NULL,
  `cbu` varchar(22) NOT NULL,
  `saldo` decimal(15,2) NOT NULL DEFAULT '10000.00',
  `activa` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_cuenta`),
  UNIQUE KEY `uk_cuenta_numero` (`numero_cuenta`),
  UNIQUE KEY `uk_cuenta_cbu` (`cbu`),
  KEY `fk_cuenta_cliente` (`id_cliente`),
  KEY `fk_cuenta_tipo` (`id_tipo_cuenta`),
  CONSTRAINT `fk_cuenta_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `fk_cuenta_tipo` FOREIGN KEY (`id_tipo_cuenta`) REFERENCES `cuenta_tipo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,2,'2025-07-16',1,'10001','0010000000000000010001',4000.00,0),(2,2,'2025-07-16',2,'10002','0010000000000000010002',16000.00,0),(3,3,'2025-07-15',1,'10003','0010000000000000010003',7500.00,1),(4,3,'2025-07-13',1,'10004','0010000000000000010004',10000.00,1),(5,4,'2025-07-15',1,'10005','0010000000000000010005',10000.00,1),(6,5,'2025-07-10',1,'10006','0010000000000000010006',10000.00,1),(7,5,'2025-07-13',2,'10007','0010000000000000010007',39000.00,1),(8,6,'2025-07-10',1,'10008','0010000000000000010008',399000.00,1),(9,6,'2025-07-15',2,'10009','0010000000000000010009',12200.00,1),(10,7,'2025-07-13',1,'10010','0010000000000000010010',7800.00,1),(11,7,'2025-07-10',1,'10011','0010000000000000010011',160000.00,1),(12,8,'2025-07-13',1,'10012','0010000000000000010012',326500.00,1),(13,9,'2025-06-30',1,'10013','0010000000000000010013',10000.00,1),(14,9,'2025-07-13',2,'10014','0010000000000000010014',10000.00,1),(15,10,'2025-07-10',1,'10015','0010000000000000010015',13750.00,1),(16,10,'2025-06-29',2,'10016','0010000000000000010016',202000.00,1),(17,11,'2025-07-13',1,'10017','0010000000000000010017',8750.00,1),(18,12,'2025-06-30',2,'10018','0010000000000000010018',10000.00,1),(19,12,'2025-06-29',2,'10019','0010000000000000010019',10000.00,1),(20,13,'2025-07-17',2,'10020','0010000000000000010020',192834.00,1),(21,13,'2025-07-17',1,'10021','0010000000000000010021',10500.00,1),(22,13,'2025-07-17',1,'10022','0010000000000000010022',10000.00,0),(23,13,'2025-07-17',1,'10023','0010000000000000010023',10000.00,1);
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_tipo`
--

DROP TABLE IF EXISTS `cuenta_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta_tipo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_tipo`
--

LOCK TABLES `cuenta_tipo` WRITE;
/*!40000 ALTER TABLE `cuenta_tipo` DISABLE KEYS */;
INSERT INTO `cuenta_tipo` VALUES (1,'Cuenta Corriente'),(2,'Caja de Ahorro');
/*!40000 ALTER TABLE `cuenta_tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localidades`
--

DROP TABLE IF EXISTS `localidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `localidades` (
  `id_localidad` int NOT NULL AUTO_INCREMENT,
  `nombre_localidad` varchar(100) NOT NULL,
  `id_provincia` int NOT NULL,
  PRIMARY KEY (`id_localidad`),
  KEY `id_provincia` (`id_provincia`),
  CONSTRAINT `localidades_ibfk_1` FOREIGN KEY (`id_provincia`) REFERENCES `provincias` (`id_provincia`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localidades`
--

LOCK TABLES `localidades` WRITE;
/*!40000 ALTER TABLE `localidades` DISABLE KEYS */;
INSERT INTO `localidades` VALUES (1,'La Plata',1),(2,'Mar del Plata',1),(3,'Bahía Blanca',1),(4,'Quilmes',1),(5,'Córdoba Capital',2),(6,'Villa Carlos Paz',2),(7,'Río Cuarto',2),(8,'Rosario',3),(9,'Santa Fe Capital',3),(10,'Venado Tuerto',3),(11,'Mendoza Capital',4),(12,'San Rafael',4),(13,'Godoy Cruz',4),(14,'Salta Capital',5),(15,'Orán',5),(16,'Tartagal',5);
/*!40000 ALTER TABLE `localidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento` (
  `id_movimiento` int NOT NULL AUTO_INCREMENT,
  `id_cuenta` int NOT NULL,
  `id_tipo_movimiento` int NOT NULL,
  `fecha` date NOT NULL,
  `detalle` varchar(200) NOT NULL,
  `importe` decimal(15,2) NOT NULL,
  `id_prestamo` int DEFAULT NULL,
  PRIMARY KEY (`id_movimiento`),
  KEY `fk_movimiento_cuenta` (`id_cuenta`),
  KEY `fk_movimiento_tipo` (`id_tipo_movimiento`),
  KEY `fk_movimiento_prestamo` (`id_prestamo`),
  CONSTRAINT `fk_movimiento_cuenta` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta` (`id_cuenta`),
  CONSTRAINT `fk_movimiento_prestamo` FOREIGN KEY (`id_prestamo`) REFERENCES `prestamo` (`id_prestamo`),
  CONSTRAINT `fk_movimiento_tipo` FOREIGN KEY (`id_tipo_movimiento`) REFERENCES `movimiento_tipo` (`id_tipo_movimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento`
--

LOCK TABLES `movimiento` WRITE;
/*!40000 ALTER TABLE `movimiento` DISABLE KEYS */;
INSERT INTO `movimiento` VALUES (1,1,1,'2025-07-16','Depósito inicial por apertura de cuenta',10000.00,NULL),(2,2,1,'2025-07-16','Depósito inicial por apertura de cuenta',10000.00,NULL),(3,1,4,'2025-07-16','Transferencia enviada - CBU: 0010000000000000010002',-5000.00,NULL),(4,2,4,'2025-07-16','Transferencia recibida - CBU: 0010000000000000010002',5000.00,NULL),(5,1,4,'2025-07-16','Transferencia enviada - CBU: 0010000000000000010002',-1000.00,NULL),(6,2,4,'2025-07-16','Transferencia recibida - CBU: 0010000000000000010002',1000.00,NULL),(7,3,1,'2025-07-15','Depósito inicial por apertura de cuenta',10000.00,NULL),(8,4,1,'2025-07-17','Depósito inicial por apertura de cuenta',10000.00,NULL),(9,5,1,'2025-07-13','Depósito inicial por apertura de cuenta',10000.00,NULL),(10,6,1,'2025-07-10','Depósito inicial por apertura de cuenta',10000.00,NULL),(11,7,1,'2025-07-13','Depósito inicial por apertura de cuenta',10000.00,NULL),(12,8,1,'2025-07-10','Depósito inicial por apertura de cuenta',10000.00,NULL),(13,9,1,'2025-07-15','Depósito inicial por apertura de cuenta',10000.00,NULL),(14,10,1,'2025-07-13','Depósito inicial por apertura de cuenta',10000.00,NULL),(15,11,1,'2025-07-10','Depósito inicial por apertura de cuenta',10000.00,NULL),(16,12,1,'2025-07-13','Depósito inicial por apertura de cuenta',10000.00,NULL),(17,13,1,'2025-06-30','Depósito inicial por apertura de cuenta',10000.00,NULL),(18,14,1,'2025-07-13','Depósito inicial por apertura de cuenta',10000.00,NULL),(19,15,1,'2025-07-10','Depósito inicial por apertura de cuenta',10000.00,NULL),(20,16,1,'2025-06-29','Depósito inicial por apertura de cuenta',10000.00,NULL),(21,17,1,'2025-07-13','Depósito inicial por apertura de cuenta',10000.00,NULL),(22,18,1,'2025-06-30','Depósito inicial por apertura de cuenta',10000.00,NULL),(23,19,1,'2025-06-29','Depósito inicial por apertura de cuenta',10000.00,NULL),(24,3,4,'2025-07-17','Transferencia enviada - CBU: 0010000000000000010015',-2500.00,NULL),(25,15,4,'2025-07-17','Transferencia recibida - CBU: 0010000000000000010015',2500.00,NULL),(26,12,4,'2025-07-17','Transferencia enviada - CBU: 0010000000000000010016',-8500.00,NULL),(27,16,4,'2025-07-17','Transferencia recibida - CBU: 0010000000000000010016',8500.00,NULL),(28,17,4,'2025-07-17','Transferencia enviada - CBU: 0010000000000000010015',-1250.00,NULL),(29,15,4,'2025-07-17','Transferencia recibida - CBU: 0010000000000000010015',1250.00,NULL),(30,16,4,'2025-07-17','Transferencia enviada - CBU: 0010000000000000010007',-4000.00,NULL),(31,7,4,'2025-07-17','Transferencia recibida - CBU: 0010000000000000010007',4000.00,NULL),(32,8,2,'2025-07-17','Depósito por préstamo aprobado',389000.00,1),(33,12,2,'2025-07-17','Depósito por préstamo aprobado',500000.00,2),(34,16,2,'2025-07-17','Depósito por préstamo aprobado',225000.00,3),(35,16,3,'2025-07-17','Pago de cuota de préstamo',-37500.00,3),(36,12,4,'2025-07-17','Transferencia enviada - CBU: 0010000000000000010011',-150000.00,NULL),(37,11,4,'2025-07-17','Transferencia recibida - CBU: 0010000000000000010011',150000.00,NULL),(38,12,4,'2025-07-17','Transferencia enviada - CBU: 0010000000000000010007',-25000.00,NULL),(39,7,4,'2025-07-17','Transferencia recibida - CBU: 0010000000000000010007',25000.00,NULL),(40,10,4,'2025-07-17','Transferencia enviada - CBU: 0010000000000000010009',-2200.00,NULL),(41,9,4,'2025-07-17','Transferencia recibida - CBU: 0010000000000000010009',2200.00,NULL),(42,20,1,'2025-07-17','Depósito inicial por apertura de cuenta',10000.00,NULL),(43,21,1,'2025-07-17','Depósito inicial por apertura de cuenta',10000.00,NULL),(44,22,1,'2025-07-17','Depósito inicial por apertura de cuenta',10000.00,NULL),(45,23,1,'2025-07-17','Depósito inicial por apertura de cuenta',10000.00,NULL),(46,20,4,'2025-07-17','Transferencia enviada - CBU: 0010000000000000010021',-500.00,NULL),(47,21,4,'2025-07-17','Transferencia recibida - CBU: 0010000000000000010021',500.00,NULL),(48,20,2,'2025-07-17','Depósito por préstamo aprobado',200000.00,9),(49,20,3,'2025-07-17','Pago de cuota de préstamo',-16666.00,9);
/*!40000 ALTER TABLE `movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento_tipo`
--

DROP TABLE IF EXISTS `movimiento_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento_tipo` (
  `id_tipo_movimiento` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id_tipo_movimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento_tipo`
--

LOCK TABLES `movimiento_tipo` WRITE;
/*!40000 ALTER TABLE `movimiento_tipo` DISABLE KEYS */;
INSERT INTO `movimiento_tipo` VALUES (1,'Alta de cuenta'),(2,'Alta de prestamo'),(3,'Pago de prestamo'),(4,'Transferencia'),(5,'Depósito'),(6,'Extracción'),(7,'Débito automático'),(8,'Acreditación de sueldo');
/*!40000 ALTER TABLE `movimiento_tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamo` (
  `id_prestamo` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_cuenta` int NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `fecha_aprobacion` date DEFAULT NULL,
  `importe_solicitado` decimal(15,2) NOT NULL,
  `plazo_pago_meses` int NOT NULL,
  `importe_por_cuota` decimal(15,2) NOT NULL,
  `cantidad_cuotas` int NOT NULL,
  `estado` varchar(20) NOT NULL,
  `eliminado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_prestamo`),
  KEY `fk_prestamo_cliente` (`id_cliente`),
  KEY `fk_prestamo_cuenta` (`id_cuenta`),
  CONSTRAINT `fk_prestamo_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `fk_prestamo_cuenta` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta` (`id_cuenta`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
INSERT INTO `prestamo` VALUES (1,6,8,'2025-07-17','2025-07-17',389000.00,6,64833.33,6,'aceptado',0),(2,8,12,'2025-07-17','2025-07-17',500000.00,12,41666.67,12,'aceptado',0),(3,10,16,'2025-07-17','2025-07-17',225000.00,6,37500.00,6,'aceptado',0),(4,9,14,'2025-07-17',NULL,250000.00,12,20833.33,12,'denegado',0),(5,12,19,'2025-07-17',NULL,125000.00,6,20833.33,6,'pendiente',0),(6,5,6,'2025-07-17',NULL,250000.00,6,41666.67,6,'denegado',0),(7,2,2,'2025-07-17',NULL,250000.00,6,41666.67,6,'pendiente',0),(8,13,23,'2025-07-17',NULL,100000.00,6,16666.67,6,'denegado',0),(9,13,20,'2025-07-17','2025-07-17',200000.00,12,16666.67,12,'aceptado',0);
/*!40000 ALTER TABLE `prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo_cuota`
--

DROP TABLE IF EXISTS `prestamo_cuota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamo_cuota` (
  `id_prestamo_cuota` int NOT NULL AUTO_INCREMENT,
  `id_prestamo` int NOT NULL,
  `numero_cuota` int NOT NULL,
  `monto` decimal(15,2) NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  `fecha_pago` date DEFAULT NULL,
  `pagada` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_prestamo_cuota`),
  KEY `fk_cuota_prestamo` (`id_prestamo`),
  CONSTRAINT `fk_cuota_prestamo` FOREIGN KEY (`id_prestamo`) REFERENCES `prestamo` (`id_prestamo`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo_cuota`
--

LOCK TABLES `prestamo_cuota` WRITE;
/*!40000 ALTER TABLE `prestamo_cuota` DISABLE KEYS */;
INSERT INTO `prestamo_cuota` VALUES (1,1,1,64833.00,'2025-08-17',NULL,0),(2,1,2,64833.00,'2025-09-17',NULL,0),(3,1,3,64833.00,'2025-10-17',NULL,0),(4,1,4,64833.00,'2025-11-17',NULL,0),(5,1,5,64833.00,'2025-12-17',NULL,0),(6,1,6,64833.00,'2026-01-17',NULL,0),(7,2,1,41666.00,'2025-08-17',NULL,0),(8,2,2,41666.00,'2025-09-17',NULL,0),(9,2,3,41666.00,'2025-10-17',NULL,0),(10,2,4,41666.00,'2025-11-17',NULL,0),(11,2,5,41666.00,'2025-12-17',NULL,0),(12,2,6,41666.00,'2026-01-17',NULL,0),(13,2,7,41666.00,'2026-02-17',NULL,0),(14,2,8,41666.00,'2026-03-17',NULL,0),(15,2,9,41666.00,'2026-04-17',NULL,0),(16,2,10,41666.00,'2026-05-17',NULL,0),(17,2,11,41666.00,'2026-06-17',NULL,0),(18,2,12,41666.00,'2026-07-17',NULL,0),(19,3,1,37500.00,'2025-08-17','2025-07-17',1),(20,3,2,37500.00,'2025-09-17',NULL,0),(21,3,3,37500.00,'2025-10-17',NULL,0),(22,3,4,37500.00,'2025-11-17',NULL,0),(23,3,5,37500.00,'2025-12-17',NULL,0),(24,3,6,37500.00,'2026-01-17',NULL,0),(25,9,1,16666.00,'2025-08-17','2025-07-17',1),(26,9,2,16666.00,'2025-09-17',NULL,0),(27,9,3,16666.00,'2025-10-17',NULL,0),(28,9,4,16666.00,'2025-11-17',NULL,0),(29,9,5,16666.00,'2025-12-17',NULL,0),(30,9,6,16666.00,'2026-01-17',NULL,0),(31,9,7,16666.00,'2026-02-17',NULL,0),(32,9,8,16666.00,'2026-03-17',NULL,0),(33,9,9,16666.00,'2026-04-17',NULL,0),(34,9,10,16666.00,'2026-05-17',NULL,0),(35,9,11,16666.00,'2026-06-17',NULL,0),(36,9,12,16666.00,'2026-07-17',NULL,0);
/*!40000 ALTER TABLE `prestamo_cuota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provincias`
--

DROP TABLE IF EXISTS `provincias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provincias` (
  `id_provincia` int NOT NULL AUTO_INCREMENT,
  `nombre_provincia` varchar(100) NOT NULL,
  PRIMARY KEY (`id_provincia`),
  UNIQUE KEY `nombre_provincia` (`nombre_provincia`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provincias`
--

LOCK TABLES `provincias` WRITE;
/*!40000 ALTER TABLE `provincias` DISABLE KEYS */;
INSERT INTO `provincias` VALUES (1,'Buenos Aires'),(2,'Córdoba'),(4,'Mendoza'),(5,'Salta'),(3,'Santa Fe');
/*!40000 ALTER TABLE `provincias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefono`
--

DROP TABLE IF EXISTS `telefono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `telefono` (
  `id_telefono` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_telefono`),
  KEY `fk_telefono_cliente` (`id_cliente`),
  CONSTRAINT `fk_telefono_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefono`
--

LOCK TABLES `telefono` WRITE;
/*!40000 ALTER TABLE `telefono` DISABLE KEYS */;
INSERT INTO `telefono` VALUES (1,8,'381-555-0123',1),(2,9,'387-555-0234',1),(3,10,'299-555-0345',1),(4,11,'291-555-0456',1),(5,12,'342-555-0567',1);
/*!40000 ALTER TABLE `telefono` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transferencia`
--

DROP TABLE IF EXISTS `transferencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transferencia` (
  `id_transferencia` int NOT NULL AUTO_INCREMENT,
  `id_movimiento` int DEFAULT NULL,
  `cuenta_origen` int DEFAULT NULL,
  `cuenta_destino` int DEFAULT NULL,
  PRIMARY KEY (`id_transferencia`),
  KEY `id_movimiento` (`id_movimiento`),
  CONSTRAINT `transferencia_ibfk_1` FOREIGN KEY (`id_movimiento`) REFERENCES `movimiento` (`id_movimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transferencia`
--

LOCK TABLES `transferencia` WRITE;
/*!40000 ALTER TABLE `transferencia` DISABLE KEYS */;
INSERT INTO `transferencia` VALUES (1,3,1,2),(2,5,1,2),(3,24,3,15),(5,26,12,16),(6,28,17,15),(7,30,16,7),(8,36,12,11),(9,38,12,7),(10,40,10,9),(11,46,20,21);
/*!40000 ALTER TABLE `transferencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `usuario` varchar(50) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `tipo_usuario` varchar(20) NOT NULL,
  `eliminado` tinyint(1) NOT NULL DEFAULT '1',
  `fecha_creacion` date NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `uk_usuario_usuario` (`usuario`),
  KEY `fk_usuario_cliente` (`id_cliente`),
  CONSTRAINT `fk_usuario_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,'lgarcia','123','admin',0,'2025-07-16'),(2,2,'matis','123','cliente',1,'2025-07-16'),(3,3,'mgonzalez','pass123','cliente',0,'2025-07-17'),(4,4,'clopez','pass123','cliente',0,'2025-07-17'),(5,5,'amartinez','pass123','cliente',0,'2025-07-17'),(6,6,'rsilva','pass123','cliente',0,'2025-07-17'),(7,7,'lfernandez','pass123','cliente',0,'2025-07-17'),(8,8,'mrodriguez','pass123','cliente',0,'2025-03-15'),(9,9,'vtorres','pass123','cliente',0,'2025-04-08'),(10,10,'dmorales','pass123','cliente',0,'2025-02-22'),(11,11,'svargas','pass123','cliente',0,'2025-05-10'),(12,12,'jherrera','pass123','cliente',0,'2025-01-18'),(13,13,'crodriguez','123','cliente',0,'2025-07-17');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'banco_db'
--
/*!50003 DROP PROCEDURE IF EXISTS `aceptar_prestamo_valida_cliente_activo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `aceptar_prestamo_valida_cliente_activo`(

    IN idPrestamo INT, 

    OUT resultado INT

)
salida: BEGIN

    DECLARE eliminado_cliente TINYINT DEFAULT NULL;



    DECLARE EXIT HANDLER FOR SQLEXCEPTION

    BEGIN

        ROLLBACK;

        SET resultado = -1;

    END;



    START TRANSACTION;

   

    SELECT c.eliminado INTO eliminado_cliente FROM prestamo p

    JOIN cliente c ON p.id_cliente = c.id_cliente WHERE p.id_prestamo = idPrestamo;

    

    IF eliminado_cliente IS NULL OR eliminado_cliente = 1 THEN

        ROLLBACK;

        SET resultado = 0;

        LEAVE salida;

    END IF;

  

    UPDATE prestamo SET estado='aceptado',fecha_aprobacion=DATE(CURRENT_TIMESTAMP()) WHERE id_prestamo = idPrestamo;



    COMMIT;

    SET resultado = 1;

END salida ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminar_usuario_completo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_usuario_completo`( IN p_idUsuario INT, IN p_idCliente INT )
BEGIN

    DECLARE EXIT HANDLER FOR SQLEXCEPTION

    BEGIN

        ROLLBACK;

    END;

    START TRANSACTION;    

    UPDATE usuario SET eliminado = 1 WHERE id_usuario = p_idUsuario;

   

    UPDATE cliente  SET eliminado = 1 WHERE id_cliente = p_idCliente;

   

    UPDATE cuenta SET activa = 0 WHERE id_cliente = p_idCliente;

    

    COMMIT;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_historial_transferencias_cliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_historial_transferencias_cliente`(

    IN p_id_cliente INT

)
BEGIN

    SELECT 

        t.id_transferencia,

        t.id_movimiento,

        t.cuenta_origen,

        t.cuenta_destino,

        m.fecha,

        m.detalle,

        m.importe,

        co.numero_cuenta as num_cuenta_origen,

        cd.numero_cuenta as num_cuenta_destino,

        CONCAT(co.numero_cuenta, ' → ', cd.numero_cuenta) as transferencia_detalle,

        mt.descripcion as tipo_movimiento,

        CONCAT(cli.nombre, ' ', cli.apellido) as cliente_nombre

    FROM movimiento m

    INNER JOIN cuenta co ON m.id_cuenta = co.id_cuenta

    INNER JOIN cliente cli ON co.id_cliente = cli.id_cliente

    INNER JOIN movimiento_tipo mt ON m.id_tipo_movimiento = mt.id_tipo_movimiento

    INNER JOIN transferencia t ON m.id_movimiento = t.id_movimiento

    LEFT JOIN cuenta cd ON t.cuenta_destino = cd.id_cuenta

    WHERE co.id_cliente = p_id_cliente

    AND t.cuenta_origen = co.id_cuenta

    ORDER BY m.fecha DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_pagar_cuota` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_pagar_cuota`(

    IN p_id_cuota INT,

    IN p_id_cuenta INT,

    IN p_monto DECIMAL(10,2)

)
BEGIN

    DECLARE v_saldo_actual DECIMAL(10,2);

    DECLARE v_cuota_pendiente DECIMAL(10,2);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION

    BEGIN

        ROLLBACK;

        RESIGNAL;

    END;

    

    -- Iniciar transacción

    START TRANSACTION;

    

    -- Obtener saldo actual de la cuenta

    SELECT saldo INTO v_saldo_actual 

    FROM cuenta 

    WHERE id_cuenta = p_id_cuenta;

    

    -- Obtener monto de la cuota

    SELECT monto INTO v_cuota_pendiente 

    FROM prestamo_cuota 

    WHERE id_prestamo_cuota = p_id_cuota AND pagada = 0;

    

    -- Verificar si hay suficiente saldo

    IF v_saldo_actual >= p_monto THEN

        -- Actualizar saldo de la cuenta

        UPDATE cuenta 

        SET saldo = saldo - p_monto 

        WHERE id_cuenta = p_id_cuenta;

        

        -- Marcar cuota como pagada

        UPDATE prestamo_cuota 

        SET pagada = 1, 

            fecha_pago = curdate() 

        WHERE id_prestamo_cuota = p_id_cuota;

        

        COMMIT;

        SELECT 'Pago realizado exitosamente' AS mensaje;

    ELSE

        ROLLBACK;

        SELECT 'Saldo insuficiente' AS mensaje;

    END IF;

    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-19  1:47:01
