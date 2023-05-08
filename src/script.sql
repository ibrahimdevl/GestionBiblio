CREATE TABLE `libraryproject`.`utilisateur`
(
    `idUtlstr`   INT(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id utilisatuer',
    `nomUtlstr`  VARCHAR(100)    NOT NULL COMMENT 'Nom utilisateur',
    `motDePasse` VARCHAR(100)    NOT NULL COMMENT 'Mot de passe',
    `nom`        VARCHAR(50)     NOT NULL COMMENT 'Nom',
    `prenom`     VARCHAR(50)     NOT NULL COMMENT 'Prenom',
    `addresse`   VARCHAR(255)    NULL COMMENT 'Adresse',
    `tel`        INT(8) UNSIGNED NOT NULL COMMENT 'Numero du telephone',
    PRIMARY KEY (`idUtlstr`),
    UNIQUE `usernameUnique` (`nomUtlstr`(100))
)
    ENGINE = InnoDB;

INSERT INTO `utilisateur` (`nomUtlstr`, `motDePasse`, `nom`, `prenom`, `addresse`, `tel`)
VALUES ('Hlel', 'Test1234', 'Mohamed Aziz', 'Hlel', 'Raoued', '54385290'),
       ('Kammoun', 'mdp1234', 'Hale', 'Kammoun', 'Ghazela', '52789456'),
       ('Nour', 'mdp0000', 'Nour', 'Barrani', 'Tunis', '24567891'),
       ('Mootez', 'Test0000', 'Mootez', 'Bani', 'Nabeul', '98456123');

CREATE TABLE `libraryproject`.`admin`
(
    `idUtlstr`    INT(4) UNSIGNED NOT NULL COMMENT 'Id utilisatuer',
    `departement` VARCHAR(50)     NULL COMMENT 'Departement',
    `email`       VARCHAR(255)    NULL COMMENT 'Email',
    PRIMARY KEY (`idUtlstr`),
    FOREIGN KEY (`idUtlstr`) REFERENCES utilisateur (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE
)
    ENGINE = InnoDB;

INSERT INTO `admin` (`idUtlstr`, `departement`, `email`)
VALUES (1, 'IT', 'med.aziz.hlel@gmail.com');

CREATE TABLE `libraryproject`.`bibliothecaire`
(
    `idUtlstr`     INT(4) UNSIGNED NOT NULL COMMENT 'Id utilisatuer',
    `dateEmbauche` DATE            NULL COMMENT 'Date de l embauche',
    `salaire`      FLOAT           NULL COMMENT 'Salaire',
    PRIMARY KEY (`idUtlstr`),
    FOREIGN KEY (`idUtlstr`) REFERENCES utilisateur (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE
)
    ENGINE = InnoDB;

INSERT INTO `bibliothecaire` (`idUtlstr`, `dateEmbauche`, `salaire`)
VALUES ('2', '2023-04-10', '2150.750');

CREATE TABLE `libraryproject`.`adherent`
(
    `idUtlstr`        INT(4) UNSIGNED NOT NULL COMMENT 'Id utilisatuer',
    `cin`             INT(8)          NOT NULL COMMENT 'Numero de carte d identite',
    `dateInscription` DATE            NULL COMMENT 'Date de inscription',
    PRIMARY KEY (`idUtlstr`),
    FOREIGN KEY (`idUtlstr`) REFERENCES utilisateur (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE
)
    ENGINE = InnoDB;

INSERT INTO `adherent` (`idUtlstr`, `cin`, `dateInscription`)
VALUES ('3', '12345678', '2023-04-05');

CREATE TABLE `libraryproject`.`document`
(
    `idDoc`     INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id de document',
    `reference` VARCHAR(5)       NOT NULL COMMENT 'Reference de document',
    `titre`     VARCHAR(255)     NOT NULL COMMENT 'Titre de document',
    `auteur`    VARCHAR(255)     NULL COMMENT 'Autoeur de document',
    PRIMARY KEY (`idDoc`)
)
    ENGINE = InnoDB;

INSERT INTO `document` (`reference`, `titre`, `auteur`)
VALUES ('ABC45', 'Les miserables', 'Vector Hugo');

CREATE TABLE `libraryproject`.`pret`
(
    `idPret`   INT(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id de pret',
    `idDoc`    INT(4) UNSIGNED NOT NULL COMMENT 'Id de document',
    `idAdh`    INT(4) UNSIGNED NOT NULL COMMENT 'Id d adherent',
    `datePret` DATE            NOT NULL COMMENT 'Date de pret',
    `validee`  BOOLEAN         NOT NULL COMMENT 'Est ce que validee par bibliothecaire',
    PRIMARY KEY (`idPret`),
    FOREIGN KEY (`idAdh`) REFERENCES adherent (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`idDoc`) REFERENCES document (`idDoc`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

INSERT INTO `pret` (`idDoc`, `idAdh`, `datePret`, `validee`)
VALUES ('1', '3', '2023-04-02', '0');

CREATE TABLE `libraryproject`.`emprunt`
(
    `idEmprunt`   INT(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id d emprunt',
    `dateEmprunt` DATE            NOT NULL COMMENT 'Date d emprunt',
    `dateRetour`  DATE            NOT NULL COMMENT 'Date limite de retour',
    `idAdh`       INT(4) UNSIGNED NOT NULL COMMENT 'id d adherent',
    `idBiblio`    INT(4) UNSIGNED NOT NULL COMMENT 'id de bibliothecaire',
    `idDoc`       INT(4) UNSIGNED NOT NULL COMMENT 'id de document',
    `retourne`    BOOLEAN         NOT NULL COMMENT 'Est ce le document est retourne',
    PRIMARY KEY (`idEmprunt`),
    FOREIGN KEY (`idAdh`) REFERENCES adherent (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`idBiblio`) REFERENCES bibliothecaire (`idUtlstr`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`idDoc`) REFERENCES document (`idDoc`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

INSERT INTO `emprunt` (`dateEmprunt`, `dateRetour`, `idAdh`, `idBiblio`, `idDoc`, `retourne`)
VALUES ('2023-04-02', '2023-04-23', '3', '2', '1', '0');
