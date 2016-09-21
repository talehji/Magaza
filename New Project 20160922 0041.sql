-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.13


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema magaza
--

CREATE DATABASE IF NOT EXISTS magaza;
USE magaza;

--
-- Definition of table `depo`
--

DROP TABLE IF EXISTS `depo`;
CREATE TABLE `depo` (
  `idDepo` int(11) NOT NULL AUTO_INCREMENT,
  `Sayi` varchar(255) DEFAULT NULL,
  `idMallar` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDepo`),
  KEY `FK_depo_idMallar` (`idMallar`),
  CONSTRAINT `FK_depo_idMallar` FOREIGN KEY (`idMallar`) REFERENCES `mallar` (`idMallar`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `depo`
--

/*!40000 ALTER TABLE `depo` DISABLE KEYS */;
INSERT INTO `depo` (`idDepo`,`Sayi`,`idMallar`) VALUES 
 (1,'150',1),
 (2,'100',2);
/*!40000 ALTER TABLE `depo` ENABLE KEYS */;


--
-- Definition of table `kassa`
--

DROP TABLE IF EXISTS `kassa`;
CREATE TABLE `kassa` (
  `idKassa` int(11) NOT NULL AUTO_INCREMENT,
  `Borc` double DEFAULT NULL,
  `Medaxil` double DEFAULT NULL,
  `Tarix` datetime DEFAULT NULL,
  `idMember` int(11) DEFAULT NULL,
  `idSatisNovu` int(11) DEFAULT NULL,
  `idMusteri` int(10) DEFAULT '1',
  PRIMARY KEY (`idKassa`),
  KEY `FK_kassa_idSatisNovu` (`idSatisNovu`),
  KEY `FK_kassa_idMenber` (`idMember`) USING BTREE,
  KEY `FK_kassa_3` (`idMusteri`),
  CONSTRAINT `FK_kassa_3` FOREIGN KEY (`idMusteri`) REFERENCES `musteri` (`idMusteri`),
  CONSTRAINT `FK_kassa_idMenber` FOREIGN KEY (`idMember`) REFERENCES `member` (`idMember`),
  CONSTRAINT `FK_kassa_idSatisNovu` FOREIGN KEY (`idSatisNovu`) REFERENCES `satisnovu` (`idSatisNovu`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `kassa`
--

/*!40000 ALTER TABLE `kassa` DISABLE KEYS */;
INSERT INTO `kassa` (`idKassa`,`Borc`,`Medaxil`,`Tarix`,`idMember`,`idSatisNovu`,`idMusteri`) VALUES 
 (3,1,2,'2016-09-21 17:13:50',2,2,2),
 (4,0,2,'2016-09-21 17:14:07',2,1,1),
 (5,2,5,'2016-09-21 18:13:46',2,2,2);
/*!40000 ALTER TABLE `kassa` ENABLE KEYS */;


--
-- Definition of table `kateqoriya`
--

DROP TABLE IF EXISTS `kateqoriya`;
CREATE TABLE `kateqoriya` (
  `idKateqoriya` int(11) NOT NULL AUTO_INCREMENT,
  `Ad` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idKateqoriya`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `kateqoriya`
--

/*!40000 ALTER TABLE `kateqoriya` DISABLE KEYS */;
INSERT INTO `kateqoriya` (`idKateqoriya`,`Ad`) VALUES 
 (1,'İçkilər'),
 (2,'Şokalad'),
 (3,'Konfet'),
 (4,'Süd məhsulları');
/*!40000 ALTER TABLE `kateqoriya` ENABLE KEYS */;


--
-- Definition of table `mallar`
--

DROP TABLE IF EXISTS `mallar`;
CREATE TABLE `mallar` (
  `idMallar` int(11) NOT NULL AUTO_INCREMENT,
  `Ad` varchar(255) NOT NULL,
  `Barkod` varchar(255) NOT NULL,
  `Cekisi` varchar(255) NOT NULL,
  `AlisQiymeti` double NOT NULL,
  `SatisQiymeti` double NOT NULL,
  `isActive` varchar(45) NOT NULL,
  `idKateqoriya` int(10) NOT NULL,
  PRIMARY KEY (`idMallar`),
  KEY `FK_mallar_1` (`idKateqoriya`),
  CONSTRAINT `FK_mallar_1` FOREIGN KEY (`idKateqoriya`) REFERENCES `kateqoriya` (`idKateqoriya`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mallar`
--

/*!40000 ALTER TABLE `mallar` DISABLE KEYS */;
INSERT INTO `mallar` (`idMallar`,`Ad`,`Barkod`,`Cekisi`,`AlisQiymeti`,`SatisQiymeti`,`isActive`,`idKateqoriya`) VALUES 
 (1,'Coca Cola','111','470',1,1,'1',1),
 (2,'Fanta','222','470',0.65,1,'1',1);
/*!40000 ALTER TABLE `mallar` ENABLE KEYS */;


--
-- Definition of table `member`
--

DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `idMember` int(11) NOT NULL AUTO_INCREMENT,
  `Ad` varchar(255) DEFAULT NULL,
  `isActive` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Soyad` varchar(255) DEFAULT NULL,
  `Telefon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idMember`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `member`
--

/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` (`idMember`,`Ad`,`isActive`,`Password`,`Soyad`,`Telefon`) VALUES 
 (1,'Admin','1','123456',' ',' '),
 (2,'Guest','1','123456',' ',' ');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;


--
-- Definition of table `musteri`
--

DROP TABLE IF EXISTS `musteri`;
CREATE TABLE `musteri` (
  `idMusteri` int(11) NOT NULL AUTO_INCREMENT,
  `Ad` varchar(255) DEFAULT NULL,
  `Qeyd` varchar(255) DEFAULT NULL,
  `Soyad` varchar(255) DEFAULT NULL,
  `Telefon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idMusteri`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `musteri`
--

/*!40000 ALTER TABLE `musteri` DISABLE KEYS */;
INSERT INTO `musteri` (`idMusteri`,`Ad`,`Qeyd`,`Soyad`,`Telefon`) VALUES 
 (1,'Guest','','',''),
 (2,'Nizami','','Gəncəvi','');
/*!40000 ALTER TABLE `musteri` ENABLE KEYS */;


--
-- Definition of table `satis`
--

DROP TABLE IF EXISTS `satis`;
CREATE TABLE `satis` (
  `idSatis` int(11) NOT NULL AUTO_INCREMENT,
  `idKassa` int(11) DEFAULT NULL,
  `idMallar` int(11) DEFAULT NULL,
  `idMusteri` int(11) DEFAULT NULL,
  PRIMARY KEY (`idSatis`),
  KEY `FK_satis_idMallar` (`idMallar`),
  KEY `FK_satis_idKassa` (`idKassa`),
  KEY `FK_satis_idMusteri` (`idMusteri`),
  CONSTRAINT `FK_satis_idKassa` FOREIGN KEY (`idKassa`) REFERENCES `kassa` (`idKassa`),
  CONSTRAINT `FK_satis_idMallar` FOREIGN KEY (`idMallar`) REFERENCES `mallar` (`idMallar`),
  CONSTRAINT `FK_satis_idMusteri` FOREIGN KEY (`idMusteri`) REFERENCES `musteri` (`idMusteri`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `satis`
--

/*!40000 ALTER TABLE `satis` DISABLE KEYS */;
INSERT INTO `satis` (`idSatis`,`idKassa`,`idMallar`,`idMusteri`) VALUES 
 (1,3,2,2),
 (2,3,2,2),
 (3,4,2,1),
 (4,4,2,1),
 (5,5,2,2),
 (6,5,1,2),
 (7,5,2,2),
 (8,5,1,2),
 (9,5,1,2);
/*!40000 ALTER TABLE `satis` ENABLE KEYS */;


--
-- Definition of table `satisnovu`
--

DROP TABLE IF EXISTS `satisnovu`;
CREATE TABLE `satisnovu` (
  `idSatisNovu` int(11) NOT NULL AUTO_INCREMENT,
  `Ad` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idSatisNovu`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `satisnovu`
--

/*!40000 ALTER TABLE `satisnovu` DISABLE KEYS */;
INSERT INTO `satisnovu` (`idSatisNovu`,`Ad`) VALUES 
 (1,'Nəğd'),
 (2,'Nisyə');
/*!40000 ALTER TABLE `satisnovu` ENABLE KEYS */;


--
-- Definition of table `setting`
--

DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `idSetting` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Data` text NOT NULL,
  PRIMARY KEY (`idSetting`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `setting`
--

/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
