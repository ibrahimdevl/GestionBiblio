-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2023 at 05:20 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
-- START TRANSACTION;
-- SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `libraryproject`
--

-- --------------------------------------------------------

--
-- Table structure for table `adherent`
--

CREATE TABLE `adherent` (
  `idUtlstr` int(4) UNSIGNED NOT NULL COMMENT 'Id utilisatuer',
  `cin` int(8) NOT NULL COMMENT 'Numero de carte d identite',
  `dateInscription` date DEFAULT NULL COMMENT 'Date de inscription'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `adherent`
--

INSERT INTO `adherent` (`idUtlstr`, `cin`, `dateInscription`) VALUES
(3, 12345678, '2023-04-05');

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `idUtlstr` int(4) UNSIGNED NOT NULL COMMENT 'Id utilisatuer',
  `departement` varchar(50) DEFAULT NULL COMMENT 'Departement',
  `email` varchar(255) DEFAULT NULL COMMENT 'Email'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`idUtlstr`, `departement`, `email`) VALUES
(1, 'IT', 'med.aziz.hlel@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `bibliothecaire`
--

CREATE TABLE `bibliothecaire` (
  `idUtlstr` int(4) UNSIGNED NOT NULL COMMENT 'Id utilisatuer',
  `dateEmbauche` date DEFAULT NULL COMMENT 'Date de l embauche',
  `salaire` float DEFAULT NULL COMMENT 'Salaire'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bibliothecaire`
--

INSERT INTO `bibliothecaire` (`idUtlstr`, `dateEmbauche`, `salaire`) VALUES
(2, '2023-04-10', 2150.75);

-- --------------------------------------------------------

--
-- Table structure for table `document`
--

CREATE TABLE `document` (
  `idDoc` int(10) UNSIGNED NOT NULL COMMENT 'Id de document',
  `reference` varchar(5) NOT NULL COMMENT 'Reference de document',
  `titre` varchar(255) NOT NULL COMMENT 'Titre de document',
  `auteur` varchar(255) DEFAULT NULL COMMENT 'Autoeur de document'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `document`
--

INSERT INTO `document` (`idDoc`, `reference`, `titre`, `auteur`) VALUES
(1, 'ABC45', 'Les miserables', 'Vector Hugo'),
(2, 'JKL78', 'Test', 'Vector Hugo'),
(3, 'gfg44', 'dgfgdfgdfg', 'jfjfjfjfj');

-- --------------------------------------------------------

--
-- Table structure for table `emprunt`
--

CREATE TABLE `emprunt` (
  `idEmprunt` int(4) UNSIGNED NOT NULL COMMENT 'Id d emprunt',
  `dateEmprunt` date NOT NULL COMMENT 'Date d emprunt',
  `dateRetour` date NOT NULL COMMENT 'Date limite de retour',
  `idAdh` int(4) UNSIGNED NOT NULL COMMENT 'id d adherent',
  `idBiblio` int(4) UNSIGNED NOT NULL COMMENT 'id de bibliothecaire',
  `idDoc` int(4) UNSIGNED NOT NULL COMMENT 'id de document',
  `retourne` tinyint(1) NOT NULL COMMENT 'Est ce le document est retourne'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `emprunt`
--

INSERT INTO `emprunt` (`idEmprunt`, `dateEmprunt`, `dateRetour`, `idAdh`, `idBiblio`, `idDoc`, `retourne`) VALUES
(1, '2023-04-02', '2023-04-23', 3, 2, 1, 0),
(3, '2023-04-20', '2023-05-04', 3, 2, 1, 1),
(7, '2023-04-30', '2023-05-14', 3, 2, 2, 1),
(8, '2023-05-06', '2023-05-20', 3, 2, 3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `pret`
--

CREATE TABLE `pret` (
  `idPret` int(4) UNSIGNED NOT NULL COMMENT 'Id de pret',
  `idDoc` int(4) UNSIGNED NOT NULL COMMENT 'Id de document',
  `idAdh` int(4) UNSIGNED NOT NULL COMMENT 'Id d adherent',
  `datePret` date NOT NULL COMMENT 'Date de pret',
  `validee` tinyint(1) NOT NULL COMMENT 'Est ce que validee par bibliothecaire'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pret`
--

INSERT INTO `pret` (`idPret`, `idDoc`, `idAdh`, `datePret`, `validee`) VALUES
(1, 1, 3, '2023-04-02', 0),
(3, 2, 3, '2023-04-20', 0),
(4, 2, 3, '2023-04-20', 0),
(5, 2, 3, '2023-04-20', 0),
(7, 2, 3, '2023-04-20', 0),
(8, 2, 3, '2023-04-20', 0),
(9, 3, 3, '2023-04-30', 0);

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `idUtlstr` int(4) UNSIGNED NOT NULL COMMENT 'Id utilisatuer',
  `nomUtlstr` varchar(100) NOT NULL COMMENT 'Nom utilisateur',
  `motDePasse` varchar(100) NOT NULL COMMENT 'Mot de passe',
  `nom` varchar(50) NOT NULL COMMENT 'Nom',
  `prenom` varchar(50) NOT NULL COMMENT 'Prenom',
  `addresse` varchar(255) DEFAULT NULL COMMENT 'Adresse',
  `tel` int(8) UNSIGNED NOT NULL COMMENT 'Numero du telephone'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`idUtlstr`, `nomUtlstr`, `motDePasse`, `nom`, `prenom`, `addresse`, `tel`) VALUES
(1, 'Hlel', 'Test1234', 'Mohamed Aziz', 'Hlel', 'Raoued', 54385290),
(2, 'Kammoun', 'mdp1234', 'John', 'Doe', 'Ghazela', 52789456),
(3, 'Nour', 'mdp0000', 'Test', 'Testou', 'Tunis', 24567891),
(4, 'Mootez', 'Test0000', 'John', 'Smith', 'Nabeul', 98456123),
(6, 'test', 'Test1234', 'Mohame', 'Hlfvfel', 'Raoued', 54385290),
(8, 'ghfgh', 'jgkgkkhk', 'rtghjk', 'gbn', 'ghjgjf f', 36985741);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adherent`
--
ALTER TABLE `adherent`
  ADD PRIMARY KEY (`idUtlstr`),
  ADD UNIQUE KEY `cinUnique` (`cin`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idUtlstr`);

--
-- Indexes for table `bibliothecaire`
--
ALTER TABLE `bibliothecaire`
  ADD PRIMARY KEY (`idUtlstr`);

--
-- Indexes for table `document`
--
ALTER TABLE `document`
  ADD PRIMARY KEY (`idDoc`);

--
-- Indexes for table `emprunt`
--
ALTER TABLE `emprunt`
  ADD PRIMARY KEY (`idEmprunt`),
  ADD KEY `emprunt_ibfk_2` (`idBiblio`),
  ADD KEY `emprunt_ibfk_1` (`idAdh`),
  ADD KEY `emprunt_ibfk_3` (`idDoc`);

--
-- Indexes for table `pret`
--
ALTER TABLE `pret`
  ADD PRIMARY KEY (`idPret`),
  ADD KEY `idAdh` (`idAdh`),
  ADD KEY `idDoc` (`idDoc`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`idUtlstr`),
  ADD UNIQUE KEY `usernameUnique` (`nomUtlstr`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `document`
--
ALTER TABLE `document`
  MODIFY `idDoc` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id de document', AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `emprunt`
--
ALTER TABLE `emprunt`
  MODIFY `idEmprunt` int(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id d emprunt', AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `pret`
--
ALTER TABLE `pret`
  MODIFY `idPret` int(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id de pret', AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `idUtlstr` int(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id utilisatuer', AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `adherent`
--
ALTER TABLE `adherent`
  ADD CONSTRAINT `adherent_ibfk_1` FOREIGN KEY (`idUtlstr`) REFERENCES `utilisateur` (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`idUtlstr`) REFERENCES `utilisateur` (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `bibliothecaire`
--
ALTER TABLE `bibliothecaire`
  ADD CONSTRAINT `bibliothecaire_ibfk_1` FOREIGN KEY (`idUtlstr`) REFERENCES `utilisateur` (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `emprunt`
--
ALTER TABLE `emprunt`
  ADD CONSTRAINT `emprunt_ibfk_1` FOREIGN KEY (`idAdh`) REFERENCES `adherent` (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `emprunt_ibfk_2` FOREIGN KEY (`idBiblio`) REFERENCES `bibliothecaire` (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `emprunt_ibfk_3` FOREIGN KEY (`idDoc`) REFERENCES `document` (`idDoc`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pret`
--
ALTER TABLE `pret`
  ADD CONSTRAINT `pret_ibfk_1` FOREIGN KEY (`idAdh`) REFERENCES `adherent` (`idUtlstr`),
  ADD CONSTRAINT `pret_ibfk_2` FOREIGN KEY (`idDoc`) REFERENCES `document` (`idDoc`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
