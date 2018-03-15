DROP DATABASE IF EXISTS treningssenter;
CREATE SCHEMA treningssenter;
USE treningssenter;

CREATE TABLE Øvelse (
	øvelseId int,
    navn varchar(255),
    PRIMARY KEY(øvelseId)
);

CREATE TABLE ØvelsePåApparat (
	øvelseId int,
    antallKilo int,
    antallSett int,
    apparatId int,
	PRIMARY KEY(øvelseId)
);

CREATE TABLE ØvelseUtenApparat (
	øvelseId int,
	beskrivelse varchar(255),
    PRIMARY KEY(øvelseId)
);

CREATE TABLE  ØvelsesGruppe (
	gruppeId int,
    muskelGruppe varchar(20),
    PRIMARY KEY(gruppeId)
);

CREATE TABLE  Treningsøkt (
	øktId int,
    dato date,
    tidspunkt datetime,
    varighet datetime,
    form int,
    prestasjon int,
    PRIMARY KEY(øktId)
);

CREATE TABLE Notat (
	notatId int,
    beskrivelse varchar(255),
    øktId int,
	PRIMARY KEY(notatId)
);

CREATE TABLE TrenerMuskelGruppe (
	øvelseId int,
    gruppeId int,
	PRIMARY KEY(øvelseId, gruppeId)
);

CREATE TABLE ØvelserUtførtPåTreningsøkt (
	øktId int,
    øvelseId int,
	PRIMARY KEY(øktId, øvelseId)
);

CREATE TABLE Apparat (
	apparatId int,
    navn varchar(255),
    beskrivelse varchar(255),
	PRIMARY KEY(apparatId)
);

ALTER TABLE ØvelsePåApparat
    ADD FOREIGN KEY(øvelseId) REFERENCES Øvelse(øvelseId) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD FOREIGN KEY(apparatId) REFERENCES Apparat(apparatId) ON UPDATE CASCADE ON DELETE CASCADE;
    
ALTER TABLE ØvelseUtenApparat
    ADD FOREIGN KEY(øvelseId) REFERENCES Øvelse(øvelseId) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE TrenerMuskelGruppe
	ADD FOREIGN KEY(øvelseId) REFERENCES Øvelse(øvelseId) ON UPDATE CASCADE ON DELETE CASCADE,
	ADD FOREIGN KEY(gruppeId) REFERENCES ØvelsesGruppe(gruppeId) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ØvelserUtførtPåTreningsøkt 
	ADD FOREIGN KEY(øktId) REFERENCES Treningsøkt(øktId) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD FOREIGN KEY(øvelseId) REFERENCES Øvelse(øvelseId) ON UPDATE CASCADE ON DELETE CASCADE;
    
ALTER TABLE Notat
	ADD FOREIGN KEY(øktId) REFERENCES Treningsøkt(øktId) ON UPDATE CASCADE ON DELETE CASCADE;