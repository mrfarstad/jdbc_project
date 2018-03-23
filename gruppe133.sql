DROP DATABASE IF EXISTS gruppe133;
CREATE SCHEMA gruppe133;
USE gruppe133;

CREATE TABLE Ovelse (
	ovelseId int,
    navn varchar(255),
    PRIMARY KEY(ovelseId)
);

CREATE TABLE OvelsePaApparat (
	ovelseId int,
    antallKilo int,
    antallSett int,
    apparatId int,
	PRIMARY KEY(ovelseId)
);

CREATE TABLE OvelseUtenApparat (
	ovelseId int,
	beskrivelse varchar(255),
    PRIMARY KEY(ovelseId)
);

CREATE TABLE  OvelsesGruppe (
	gruppeId int,
    muskelGruppe varchar(20),
    PRIMARY KEY(gruppeId)
);

CREATE TABLE  Treningsokt (
	oktId int,
    dato date,
    tidspunkt time,
    varighet time,
    form int,
    prestasjon int,
    PRIMARY KEY(oktId)
);

CREATE TABLE Notat (
	notatId int,
    beskrivelse varchar(255),
    oktId int,
	PRIMARY KEY(notatId)
);

CREATE TABLE TrenerMuskelGruppe (
	ovelseId int,
    gruppeId int,
	PRIMARY KEY(ovelseId, gruppeId)
);

CREATE TABLE OvelserUtfortPaTreningsokt (
	oktId int,
    ovelseId int,
	PRIMARY KEY(oktId, ovelseId)
);

CREATE TABLE Apparat (
	apparatId int,
    navn varchar(255),
    beskrivelse varchar(255),
	PRIMARY KEY(apparatId)
);

ALTER TABLE OvelsePaApparat
    ADD FOREIGN KEY(ovelseId) REFERENCES Ovelse(ovelseId) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD FOREIGN KEY(apparatId) REFERENCES Apparat(apparatId) ON UPDATE CASCADE ON DELETE SET NULL;
    
ALTER TABLE OvelseUtenApparat
    ADD FOREIGN KEY(ovelseId) REFERENCES Ovelse(ovelseId) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE TrenerMuskelGruppe
	ADD FOREIGN KEY(ovelseId) REFERENCES Ovelse(ovelseId) ON UPDATE CASCADE ON DELETE CASCADE,
	ADD FOREIGN KEY(gruppeId) REFERENCES OvelsesGruppe(gruppeId) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE OvelserUtfortPaTreningsokt 
	ADD FOREIGN KEY(oktId) REFERENCES Treningsokt(oktId) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD FOREIGN KEY(ovelseId) REFERENCES Ovelse(ovelseId) ON UPDATE CASCADE ON DELETE CASCADE;
    
ALTER TABLE Notat
	ADD FOREIGN KEY(oktId) REFERENCES Treningsokt(oktId) ON UPDATE CASCADE ON DELETE CASCADE;