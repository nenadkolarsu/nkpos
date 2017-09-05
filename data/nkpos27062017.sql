-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 27, 2017 at 04:17 PM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `nkpos`
--
CREATE DATABASE IF NOT EXISTS `nkpos` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `nkpos`;

-- --------------------------------------------------------

--
-- Table structure for table `artikal_bar_kod`
--

DROP TABLE IF EXISTS `artikal_bar_kod`;
CREATE TABLE IF NOT EXISTS `artikal_bar_kod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artikal_id` int(11) DEFAULT NULL,
  `varijacija_id` int(11) DEFAULT NULL,
  `pakovanje_id` int(11) DEFAULT NULL,
  `barkod` varchar(254) COLLATE utf8_bin DEFAULT NULL,
  `barkod_type_id` int(11) DEFAULT NULL,
  `datum_vreme` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `artikli`
--

DROP TABLE IF EXISTS `artikli`;
CREATE TABLE IF NOT EXISTS `artikli` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jedinica_mere_id` int(11) DEFAULT NULL,
  `poruka_id` int(11) DEFAULT NULL,
  `poreska_grupa_id` int(11) DEFAULT NULL,
  `odeljenje_id` int(11) DEFAULT NULL,
  `artikal_kategorija_id` int(11) DEFAULT NULL,
  `artikal_tip_id` int(11) DEFAULT NULL,
  `sifra` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `naziv` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `aktivan` tinyint(1) DEFAULT NULL,
  `datum_vreme` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `napomena` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `user_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `allow_placanje_vaucher` tinyint(1) DEFAULT NULL,
  `vaucher_procenat` decimal(10,0) DEFAULT NULL,
  `vaucher_iznos` decimal(10,0) DEFAULT NULL,
  `vaucher_iznos_procenat_flag` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `rezervav1` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `rezervav2` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `rezervav3` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `rezervai1` int(11) DEFAULT NULL,
  `rezervai2` int(11) DEFAULT NULL,
  `rezervai3` int(11) DEFAULT NULL,
  `rezervad1` decimal(10,0) DEFAULT NULL,
  `rezervad2` decimal(10,0) DEFAULT NULL,
  `rezervad3` decimal(10,0) DEFAULT NULL,
  `artiklicol` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `banke`
--

DROP TABLE IF EXISTS `banke`;
CREATE TABLE IF NOT EXISTS `banke` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `napomena` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `napomena1` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `napomena2` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sifra` (`sifra`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=4 ;

--
-- Dumping data for table `banke`
--

INSERT INTO `banke` (`id`, `sifra`, `naziv`, `napomena`, `datum_vreme`, `napomena1`, `napomena2`, `aktivan`) VALUES
(1, '1', 'Sociate Generale', '275-000123456-97', '2016-05-28 03:25:07', NULL, NULL, 1),
(2, '2', 'Banca Intesa', '854-41124211-56', '2016-05-28 03:25:28', NULL, NULL, 1),
(3, '0', '', '', '2016-05-28 03:53:51', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `drzave`
--

DROP TABLE IF EXISTS `drzave`;
CREATE TABLE IF NOT EXISTS `drzave` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sifra` (`sifra`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=9 ;

--
-- Dumping data for table `drzave`
--

INSERT INTO `drzave` (`id`, `sifra`, `naziv`, `datum_vreme`, `aktivan`) VALUES
(1, '0', '', '2016-03-27 13:14:45', 1),
(2, '1', 'Srbija', '2016-03-27 13:14:59', 1),
(3, '2', 'Hrvatska', '2016-03-27 13:15:58', 1),
(5, '3', 'Madjarska', '2016-03-27 13:16:25', 1),
(6, '4', 'BIH', '2016-05-24 17:17:59', 1),
(7, '5', 'Italija', '2016-05-24 17:18:52', 1),
(8, '6', 'Slovenija', '2017-03-11 15:10:10', 1);

-- --------------------------------------------------------

--
-- Table structure for table `jedinica_mere`
--

DROP TABLE IF EXISTS `jedinica_mere`;
CREATE TABLE IF NOT EXISTS `jedinica_mere` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `napomena` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  `deljiva` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sifra` (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `nacin_placanja`
--

DROP TABLE IF EXISTS `nacin_placanja`;
CREATE TABLE IF NOT EXISTS `nacin_placanja` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  `napomena` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sifra` (`sifra`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=13 ;

--
-- Dumping data for table `nacin_placanja`
--

INSERT INTO `nacin_placanja` (`id`, `sifra`, `naziv`, `datum_vreme`, `aktivan`, `napomena`) VALUES
(9, '0', 'Gotovina', '2017-06-26 09:06:43', 1, NULL),
(10, '1', 'Čekovi', '2017-06-26 09:07:23', 1, NULL),
(11, '2', 'Platna kartica', '2017-06-26 09:07:46', 1, NULL),
(12, '3', 'Na račun', '2017-06-26 09:08:30', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `nivelacija`
--

DROP TABLE IF EXISTS `nivelacija`;
CREATE TABLE IF NOT EXISTS `nivelacija` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `broj` varchar(254) COLLATE utf8_bin NOT NULL,
  `nivelacija_datum` date DEFAULT NULL,
  `stanje` decimal(15,2) DEFAULT '0.00',
  `proglasenje_datum` date DEFAULT NULL,
  `vrednost` decimal(15,2) DEFAULT '0.00',
  `napomena` varchar(254) COLLATE utf8_bin DEFAULT NULL,
  `datum_vreme` datetime DEFAULT CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) DEFAULT '1',
  `rezerva` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `nivelacija_stavka`
--

DROP TABLE IF EXISTS `nivelacija_stavka`;
CREATE TABLE IF NOT EXISTS `nivelacija_stavka` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_artikla` int(11) DEFAULT NULL,
  `sifra_artikla` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `kolicina` decimal(15,3) DEFAULT NULL,
  `nabavna_cena` decimal(15,2) DEFAULT NULL,
  `prodajna_cena_stara` decimal(15,2) DEFAULT NULL,
  `prodajna_cena_nova` decimal(15,2) DEFAULT NULL,
  `pdv` decimal(15,2) DEFAULT NULL,
  `porez_procenat` decimal(15,2) DEFAULT NULL,
  `nivelacija_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `odeljenje`
--

DROP TABLE IF EXISTS `odeljenje`;
CREATE TABLE IF NOT EXISTS `odeljenje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `opstina`
--

DROP TABLE IF EXISTS `opstina`;
CREATE TABLE IF NOT EXISTS `opstina` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=29 ;

--
-- Dumping data for table `opstina`
--

INSERT INTO `opstina` (`id`, `sifra`, `naziv`, `datum_vreme`, `aktivan`) VALUES
(1, '0', '', '2016-03-27 05:04:59', 1),
(4, '201', 'Ada', '2017-06-27 14:47:15', 1),
(5, '203', 'Apatin', '2017-06-27 14:50:39', 1),
(6, '204', 'Bač', '2017-03-20 18:03:08', 1),
(7, '205', 'Bačka Palanka', '2017-03-20 18:03:08', 1),
(8, '206', 'Bačka Topola', '2017-03-20 18:03:08', 1),
(9, '207', 'Bački Petrovac', '2017-03-20 18:03:08', 1),
(10, '208', 'Bečej', '2017-03-20 18:03:08', 1),
(11, '209', 'Bela Crkva', '2017-03-20 18:03:08', 1),
(12, '242', 'Zrenjanin', '2017-03-20 18:03:08', 1),
(13, '213', 'Irig', '2017-03-20 18:03:08', 1),
(14, '214', 'Kanjiža', '2017-03-20 18:03:08', 1),
(15, '215', 'Kikinda', '2017-03-20 18:03:08', 1),
(16, '221', 'Novi Bečej', '2017-03-20 18:03:08', 1),
(17, '223', 'Novi Sad', '2017-03-20 18:03:08', 1),
(18, '231', 'Senta', '2017-03-20 18:03:08', 1),
(19, '232', 'Sombor', '2017-03-20 18:03:08', 1),
(20, '233', 'Srbobran', '2017-03-20 18:03:08', 1),
(21, '234', 'Sremska Mitrovica', '2017-03-20 18:03:08', 1),
(22, '236', 'Subotica', '2017-06-27 14:38:25', 1),
(23, '222', 'Novi Kneževac', '2017-03-20 18:03:09', 1),
(24, '211', 'Čoka', '2017-03-20 18:03:09', 1),
(25, '237', 'Šid', '2017-03-20 18:03:09', 1),
(26, '099', 'Šabac', '2017-03-20 18:03:09', 1),
(27, '216', 'Kovačica', '2017-03-20 18:03:09', 1),
(28, '226', 'Pančevo', '2017-03-20 18:03:09', 1);

-- --------------------------------------------------------

--
-- Table structure for table `parametri`
--

DROP TABLE IF EXISTS `parametri`;
CREATE TABLE IF NOT EXISTS `parametri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `putanja_izvestaja` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `person_icon` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `konekcija` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `user` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sifra` (`sifra`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- Dumping data for table `parametri`
--

INSERT INTO `parametri` (`id`, `sifra`, `naziv`, `putanja_izvestaja`, `person_icon`, `konekcija`, `user`, `password`, `datum_vreme`, `aktivan`) VALUES
(1, '1', '', 'C:\\\\Users\\\\ms\\\\Documents\\\\NetBeansProjects\\\\gui\\\\src\\\\report\\\\', 'D:\\\\gui\\\\slike\\\\Person-icon.jpg', 'jdbc:mysql://localhost:3306/personal?useUnicode=true&characterEncoding=utf-8', 'root', '""', '2016-05-28 10:54:52', 0),
(2, '2', '', 'C:\\\\gui\\\\reports\\\\pos\\\\', 'C:\\\\gui\\\\slike\\\\Person-icon.jpg', 'jdbc:mysql://localhost:3306/personal?useUnicode=true&characterEncoding=utf-8', 'root', '""', '2016-05-28 10:54:52', 1);

-- --------------------------------------------------------

--
-- Table structure for table `poreska_tarifa`
--

DROP TABLE IF EXISTS `poreska_tarifa`;
CREATE TABLE IF NOT EXISTS `poreska_tarifa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `broj` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `naziv` varchar(254) COLLATE utf8_bin DEFAULT NULL,
  `procenat` decimal(15,2) DEFAULT NULL,
  `fiksno` decimal(15,2) DEFAULT NULL,
  `aktivan` tinyint(1) DEFAULT NULL,
  `napomena` varchar(254) COLLATE utf8_bin DEFAULT NULL,
  `pdv` tinyint(1) DEFAULT NULL,
  `porez_indikator` int(11) DEFAULT NULL,
  `datum_od` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ptt_brojevi`
--

DROP TABLE IF EXISTS `ptt_brojevi`;
CREATE TABLE IF NOT EXISTS `ptt_brojevi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `drzava_id` int(11) NOT NULL DEFAULT '0',
  `drzava_sifra` int(11) DEFAULT '0',
  `drzava` varchar(50) COLLATE utf8_bin NOT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=185 ;

--
-- Dumping data for table `ptt_brojevi`
--

INSERT INTO `ptt_brojevi` (`id`, `sifra`, `naziv`, `drzava_id`, `drzava_sifra`, `drzava`, `datum_vreme`, `aktivan`) VALUES
(5, '0', '', 0, 0, '', '2016-03-05 18:09:52', 1),
(6, '1000', 'Zagreb', 0, 0, 'Hrvatska', '2016-03-07 19:13:19', 1),
(7, '23000', 'Zrenjanin', 0, 0, '', '2016-03-07 19:17:08', 1),
(14, '100', 'Szeged', 0, 0, 'Madjarska', '2016-05-24 15:12:36', 1),
(15, '101', 'Budapest', 0, 0, 'Madjarska', '2016-05-24 15:14:48', 1),
(16, '102', 'Esztergom', 0, 0, 'Madjarska', '2017-05-31 12:09:48', 1),
(17, '103', 'Eger', 0, 0, 'Madjarska', '2016-05-24 15:15:33', 1),
(18, '104', 'Pécs', 0, 0, 'Madjarska', '2016-05-24 15:15:53', 1),
(19, '105', 'Vác', 0, 0, 'Madjarska', '2016-05-24 15:16:15', 1),
(20, '106', 'Đakovo', 0, 0, 'Hrvatska', '2016-05-24 15:16:37', 1),
(21, '107', 'Zagreb – Kaptol', 0, 0, 'Hrvatska', '2016-05-24 15:17:00', 1),
(23, '109', 'Sarajevo', 0, 0, 'BIH', '2016-05-24 15:18:29', 1),
(24, '110', 'Roma', 0, 0, 'Italija', '2016-05-24 15:19:13', 1),
(25, '24430', 'Ada', 0, 0, 'Srbija', '2016-05-24 16:00:14', 1),
(26, '24425', 'Adorjan', 0, 0, 'Srbija', '2016-05-24 16:02:03', 1),
(27, '25260', 'Apatin', 0, 0, 'Srbija', '2016-05-24 16:02:18', 1),
(28, '21420', 'Bač', 0, 0, 'Srbija', '2016-05-24 16:07:05', 1),
(29, '21401', 'Bačka Palanka', 0, 0, 'Srbija', '2016-05-24 16:07:23', 1),
(30, '24300', 'Bačka Topola', 0, 0, 'Srbija', '2016-05-24 16:07:39', 1),
(31, '25275', 'Bački Breg', 0, 0, 'Srbija', '2016-05-24 16:07:53', 1),
(33, '25252', 'Bački Gračac', 0, 0, 'Srbija', '2016-05-24 16:08:25', 1),
(34, '25272', 'Bački Monoštor', 0, 0, 'Srbija', '2016-05-24 16:08:38', 1),
(35, '21470', 'Bački Petrovac', 0, 0, 'Srbija', '2016-05-24 16:09:22', 1),
(36, '24415', 'Bački Vinogradi', 0, 0, 'Srbija', '2016-05-24 16:09:37', 1),
(37, '21217', 'Bačko Gradište', 0, 0, 'Srbija', '2016-05-24 16:19:28', 1),
(38, '21429', 'Bačko Novo Selo', 0, 0, 'Srbija', '2016-05-24 16:19:58', 1),
(39, '21226', 'Bačko Petrovo Selo', 0, 0, 'Srbija', '2016-05-24 16:29:44', 1),
(40, '24210', 'Bajmok', 0, 0, 'Srbija', '2016-05-24 16:33:46', 1),
(41, '24331', 'Bajša', 0, 0, 'Srbija', '2016-05-24 16:33:46', 1),
(42, '21220', 'Bečej', 0, 0, 'Srbija', '2016-05-24 16:33:46', 1),
(43, '25270', 'Bezdan', 0, 0, 'Srbija', '2016-05-24 16:33:46', 1),
(44, '24206', 'Bikovo', 0, 0, 'Srbija', '2016-05-24 16:33:46', 1),
(45, '21427', 'Bođani', 0, 0, 'Srbija', '2016-05-24 16:33:47', 1),
(46, '24408', 'Bogaraš', 0, 0, 'Srbija', '2016-05-24 16:33:47', 1),
(47, '25245', 'Bogojevo', 0, 0, 'Srbija', '2016-05-24 16:42:34', 1),
(48, '21242', 'Budisava', 0, 0, 'Srbija', '2016-05-24 16:42:34', 1),
(49, '25220', 'Crvenka', 0, 0, 'Srbija', '2016-05-24 16:42:34', 1),
(50, '24220', 'Čantavir', 0, 0, 'Srbija', '2016-05-24 16:42:34', 1),
(51, '21413', 'Čelarevo', 0, 0, 'Srbija', '2016-05-24 16:42:34', 1),
(52, '25210', 'Čonoplja', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(53, '25254', 'Deronje', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(54, '25243', 'Doroslovo', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(55, '24323', 'Feketić', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(56, '21410', 'Futog', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(57, '21432', 'Gajdobra', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(58, '25282', 'Gakovo', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(59, '24406', 'Gornji Breg', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(60, '24214', 'Gornji Tavankut', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(61, '24312', 'Gunaroš', 0, 0, 'Srbija', '2016-05-24 16:42:35', 1),
(62, '24414', 'Hajdukovo', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(63, '24410', 'Horgoš', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(64, '21241', 'Kać', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(65, '24420', 'Kanjiža', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(66, '25255', 'Karavukovo', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(67, '24104', 'Kelebija', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(68, '24407', 'Kevi', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(69, '25221', 'Kljajićevo', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(70, '25274', 'Kolut', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(71, '21243', 'Kovilj', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(72, '25225', 'Kruščić', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(73, '21466', 'Kucura', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(74, '25320', 'Kula', 0, 0, 'Srbija', '2016-05-24 16:42:36', 1),
(104, '21472', 'Kulpin', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(105, '25626', 'Kupusina', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(106, '21248', 'Lok', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(107, '24215', 'Ljutovo', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(108, '24217', 'Mala Bosna', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(109, '24216', 'Male Pijace', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(110, '24321', 'Mali Iđoš', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(111, '24417', 'Martonoš', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(112, '21227', 'Mileševo', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(113, '21422', 'Mladenovo', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(114, '24435', 'Mol', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(115, '21216', 'Nadalj', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(116, '21000', 'Novi Sad', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(117, '24351', 'Novo Orahovo', 0, 0, 'Srbija', '2016-05-24 16:54:25', 1),
(118, '21423', 'Obrovac', 0, 0, 'Srbija', '2016-05-24 16:54:26', 1),
(132, '22250', 'Odžaci', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(133, '24207', 'Orom', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(134, '24342', 'Pačir', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(135, '24413', 'Palić', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(136, '21428', 'Plavna', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(137, '24313', 'Pobeda', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(138, '25263', 'Prigrevica', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(139, '25253', 'Ratkovo', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(140, '21471', 'Ravno Selo', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(141, '25280', 'Riđica', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(142, '21201', 'Rumenka', 0, 0, 'Srbija', '2016-05-24 16:57:29', 1),
(143, '21467', 'Savino Selo', 0, 0, 'Srbija', '2016-05-24 16:57:30', 1),
(144, '21425', 'Selenča', 0, 0, 'Srbija', '2016-05-24 16:57:30', 1),
(159, '24400', 'Senta', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(160, '25223', 'Sivac', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(161, '25000', 'Sombor', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(162, '25264', 'Sonta', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(163, '21480', 'Srbobran', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(164, '25244', 'Srpski Miletić', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(165, '25284', 'Stanišić', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(166, '24340', 'Stara Moravica', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(167, '24224', 'Stari Žednik', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(168, '24000', 'Subotica', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(169, '25211', 'Svetozar Miletić', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(170, '25265', 'Svilojevo', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(171, '24418', 'Šupljak', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(172, '24214', 'Tavankut', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(173, '25222', 'Telečka', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(174, '21235', 'Temerin', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(175, '21240', 'Titel', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(176, '24352', 'Tornjoš', 0, 0, 'Srbija', '2016-05-24 17:06:45', 1),
(177, '24427', 'Totovo Selo', 0, 0, 'Srbija', '2016-05-24 17:06:46', 1),
(178, '21424', 'Tovariševo', 0, 0, 'Srbija', '2016-05-24 17:06:46', 1),
(179, '24426', 'Trešnjevac', 0, 0, 'Srbija', '2016-05-24 17:06:46', 1),
(180, '24437', 'Utrine', 0, 0, 'Srbija', '2016-05-24 17:06:46', 1),
(181, '300', 'Utrine-Obornjača', 0, 0, 'Srbija', '2016-05-24 17:06:46', 1),
(182, '21426', 'Vajska', 0, 0, 'Srbija', '2016-05-24 17:06:46', 1),
(183, '21463', 'Vrbas', 0, 0, 'Srbija', '2016-05-24 17:06:46', 1),
(184, '21213', 'Zmajevo', 0, 0, 'Srbija', '2016-05-24 17:06:46', 1);

-- --------------------------------------------------------

--
-- Table structure for table `reg`
--

DROP TABLE IF EXISTS `reg`;
CREATE TABLE IF NOT EXISTS `reg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sifra` (`sifra`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=4 ;

--
-- Dumping data for table `reg`
--

INSERT INTO `reg` (`id`, `sifra`, `naziv`, `datum_vreme`, `aktivan`) VALUES
(1, 'marija1', 'biskupija1', '2016-04-19 18:31:19', 1),
(2, 'marija', 'biskupija', '2016-04-19 18:31:19', 1),
(3, 'a', 'a', '2017-05-31 07:15:13', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tip_artikla`
--

DROP TABLE IF EXISTS `tip_artikla`;
CREATE TABLE IF NOT EXISTS `tip_artikla` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sifra` varchar(45) COLLATE utf8_bin NOT NULL,
  `naziv` varchar(100) COLLATE utf8_bin NOT NULL,
  `napomena` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `datum_vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `aktivan` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sifra` (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
