CREATE SCHEMA IF NOT EXISTS FITNESSXTREME;

CREATE TABLE FITNESSXTREME.Grupo (
                idGrupo IDENTITY NOT NULL,
                nomeGrupo VARCHAR(100) NOT NULL,                
				ordem INTEGER NOT NULL AUTO_INCREMENT,
                CONSTRAINT idGrupo PRIMARY KEY (idGrupo)
);


CREATE TABLE FITNESSXTREME.Exercicio (
                idExercicio IDENTITY NOT NULL,
                nomeExercicio VARCHAR(100) NOT NULL,
		idGrupo INTEGER NOT NULL,
                CONSTRAINT idExercicio PRIMARY KEY (idExercicio)
);


CREATE TABLE FITNESSXTREME.Usuario (
                idUsuario IDENTITY NOT NULL,
                matricula INTEGER NOT NULL,
				dataNascimento DATE NOT NULL,
                indicadorDireito VARCHAR(1000) NOT NULL,
                indicadorEsquerdo VARCHAR(1000) NOT NULL,
                senha VARCHAR(50),
                objetivo VARCHAR(200) NOT NULL,
                nome VARCHAR(100) NOT NULL,
                observacao VARCHAR(200),
                dataCadastramento DATE NOT NULL,				
                eAdministrador BOOLEAN NOT NULL,
                CONSTRAINT idUsuario PRIMARY KEY (idUsuario)
);


CREATE UNIQUE INDEX matricula
 ON FITNESSXTREME.Usuario
 ( matricula ASC );

CREATE TABLE FITNESSXTREME.Serie (
                idSerie IDENTITY NOT NULL,
                dataInicio DATE NOT NULL,
                dataFim DATE NOT NULL,
				nomeSerie VARCHAR(100) NOT NULL,
				descSerie VARCHAR(200),
                peso1 DOUBLE,
                peso2 DOUBLE,
                peso3 DOUBLE,
                idUsuario INTEGER NOT NULL,
                CONSTRAINT idSerie PRIMARY KEY (idSerie)
);


CREATE TABLE FITNESSXTREME.Aula (
                idAula IDENTITY NOT NULL,
                descAula VARCHAR(100) NOT NULL,                
                repetir VARCHAR(20) NOT NULL,
                tempoEsteira INTEGER,
                tempoBicicleta INTEGER,
                tempoElipticon INTEGER,
                impresso INTEGER DEFAULT 0 NOT NULL,
                idSerie INTEGER NOT NULL,
                CONSTRAINT idAula PRIMARY KEY (idAula)
);


CREATE TABLE FITNESSXTREME.AulaExercicio (
                idAula INTEGER NOT NULL,
                idExercicio INTEGER NOT NULL,
				serie INTEGER NOT NULL,
				quantidade INTEGER NOT NULL,
				peso INTEGER NOT NULL,
                CONSTRAINT AulaExercicio_pk PRIMARY KEY (idAula, idExercicio)
);


ALTER TABLE FITNESSXTREME.AulaExercicio ADD CONSTRAINT Exercicio_ExercicioAula_fk
FOREIGN KEY (idExercicio)
REFERENCES FITNESSXTREME.Exercicio (idExercicio)
ON DELETE NO ACTION
ON UPDATE CASCADE;

ALTER TABLE FITNESSXTREME.Serie ADD CONSTRAINT Usuario_Serie_fk
FOREIGN KEY (idUsuario)
REFERENCES FITNESSXTREME.Usuario (idUsuario)
ON DELETE NO ACTION
ON UPDATE CASCADE;

ALTER TABLE FITNESSXTREME.Aula ADD CONSTRAINT Serie_Aula_fk
FOREIGN KEY (idSerie)
REFERENCES FITNESSXTREME.Serie (idSerie)
ON DELETE NO ACTION
ON UPDATE CASCADE;

ALTER TABLE FITNESSXTREME.AulaExercicio ADD CONSTRAINT Aula_ExercicioAula_fk
FOREIGN KEY (idAula)
REFERENCES FITNESSXTREME.Aula (idAula)
ON DELETE NO ACTION
ON UPDATE CASCADE;

ALTER TABLE FITNESSXTREME.Exercicio ADD CONSTRAINT Grupo_Exercicio_fk
FOREIGN KEY (idGrupo)
REFERENCES FITNESSXTREME.Grupo (idGrupo)
ON DELETE NO ACTION
ON UPDATE CASCADE;