CREATE TABLE `libraryproject`.`utilisateur` (
    `idUtlstr` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id utilisatuer' ,
    `nomUtlstr` VARCHAR(100) NOT NULL COMMENT 'Nom utilisateur' ,
    `motDePasse` VARCHAR(100) NOT NULL COMMENT 'Mot de passe' ,
    `nom` VARCHAR(50) NOT NULL COMMENT 'Nom' ,
    `prenom` VARCHAR(50) NOT NULL COMMENT 'Prenom' ,
    `addresse` VARCHAR(255) NULL COMMENT 'Adresse' ,
    `tel` INT(8) UNSIGNED NOT NULL COMMENT 'Numero du telephone' ,
    PRIMARY KEY (`idUtlstr`),
    UNIQUE `usernameUnique` (`nomUtlstr`(100)))
    ENGINE = InnoDB;
INSERT INTO `utilisateur` (`nomUtlstr`, `motDePasse`, `nom`, `prenom`, `addresse`, `tel`) VALUES ('Hlel', 'Test1234', 'Mohamed Aziz', 'Hlel', 'Raoued', '54385290');
INSERT INTO `utilisateur` (`nomUtlstr`, `motDePasse`, `nom`, `prenom`, `addresse`, `tel`) VALUES ('Kammoun', 'mdp1234', 'Hale', 'Kammoun', 'Ghazela', '52789456');