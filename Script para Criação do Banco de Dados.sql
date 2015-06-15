CREATE SCHEMA IF NOT EXISTS FITNESSXTREME;

CREATE TABLE Exercicio (
                idExercicio IDENTITY NOT NULL,
                nomeExercicio VARCHAR(100) NOT NULL,
                CONSTRAINT idExercicio PRIMARY KEY (idExercicio)
);


CREATE TABLE Usuario (
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
 ON Usuario
 ( matricula ASC );

CREATE TABLE Serie (
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


CREATE TABLE Aula (
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


CREATE TABLE AulaExercicio (
                idAula INTEGER NOT NULL,
                idExercicio INTEGER NOT NULL,
				serie INTEGER NOT NULL,
				quantidade INTEGER NOT NULL,
				peso INTEGER NOT NULL,
                CONSTRAINT AulaExercicio_pk PRIMARY KEY (idAula, idExercicio)
);


ALTER TABLE AulaExercicio ADD CONSTRAINT Exercicio_ExercicioAula_fk
FOREIGN KEY (idExercicio)
REFERENCES Exercicio (idExercicio)
ON DELETE NO ACTION
ON UPDATE CASCADE;

ALTER TABLE Serie ADD CONSTRAINT Usuario_Serie_fk
FOREIGN KEY (idUsuario)
REFERENCES Usuario (idUsuario)
ON DELETE NO ACTION
ON UPDATE CASCADE;

ALTER TABLE Aula ADD CONSTRAINT Serie_Aula_fk
FOREIGN KEY (idSerie)
REFERENCES Serie (idSerie)
ON DELETE NO ACTION
ON UPDATE CASCADE;

ALTER TABLE AulaExercicio ADD CONSTRAINT Aula_ExercicioAula_fk
FOREIGN KEY (idAula)
REFERENCES Aula (idAula)
ON DELETE NO ACTION
ON UPDATE CASCADE;