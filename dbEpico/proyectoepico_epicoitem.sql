-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: proyectoepico
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `epicoitem`
--

DROP TABLE IF EXISTS `epicoitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `epicoitem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `cost_price` float DEFAULT NULL,
  `unit_price` float DEFAULT NULL,
  `pic_filename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `epicoitem`
--

LOCK TABLES `epicoitem` WRITE;
/*!40000 ALTER TABLE `epicoitem` DISABLE KEYS */;
INSERT INTO `epicoitem` VALUES (2,'Baleares Algodón Ergonómico','Joyería Barbados Moda',4297,4914,'Diverso Extremadura Metal'),(3,'Ladrillo intranet amplio','recíproca',594,10547,'Arquitecto Grupo Soluciones'),(4,'Violeta local','Pescado',9193,4471,'Soluciones Pollo'),(5,'Rústico Papelería Ejecutivo','Morado Virtual Teclado',4981,7036,'Papelería'),(6,'Amarillo implementación sinergia','Coordinador Morado Ingeniero',1257,14040,'website Aragón'),(7,'Camiseta','Gorro',5858,8564,'Malasia'),(8,'Negro','Bebes Ladrillo',4845,6549,'compuesto Sabroso Guapo'),(10,'La Caridad Productor','Fundamental Compatible',5832,939,'Hecho Madera Azul'),(11,'arroz','alimento',1250,850,'arroz foto'),(12,'teja','contruccion',5950.5,3450.25,'multitejas');
/*!40000 ALTER TABLE `epicoitem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-03 21:26:53
