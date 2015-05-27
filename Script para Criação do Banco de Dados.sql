-- -----------------------------------------------------
-- Schema FitnessXtreme
-- -----------------------------------------------------
CREATE SCHEMA FitnessXtreme;

-- -----------------------------------------------------
-- Table Usuario
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.Usuario (
  idUsuario INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  matricula INT NOT NULL,
  dataNascimento DATE NOT NULL,
  cpf VARCHAR(45),
  indicadorDireito VARCHAR(1000) NOT NULL,
  indicadorEsquerdo VARCHAR(1000) NOT NULL,
  senha VARCHAR(50),
  dataCadastramento DATE NOT NULL,
  eAdministrador SMALLINT(1) NOT NULL,
  PRIMARY KEY (idUsuario));

-- -----------------------------------------------------
-- Tabela Aula
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.Aula (
  idAula INT NOT NULL AUTO_INCREMENT,
  Objetivo VARCHAR(200) NOT NULL,
  descAula VARCHAR(100),
  Usuario_idUsuario INT NOT NULL,
  dataInicioSerie DATE NOT NULL,
  dataFimSerie DATE NOT NULL,
  observacao VARCHAR(200) NOT NULL,
  dataAula DATE NOT NULL,
  repetirToda VARCHAR(50) NOT NULL,
  tempoEsteira VARCHAR(10),
  tempoBicicleta VARCHAR(10),
  tempoElipticon VARCHAR(10),
  PRIMARY KEY (idAula),  
  CONSTRAINT fk_AulaUsuario
    FOREIGN KEY (Usuario_idUsuario)
    REFERENCES FitnessXtreme.Usuario (idUsuario)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Tabela Serie
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.Serie (
  idSerie INT NOT NULL AUTO_INCREMENT,
  serie INT NOT NULL,
  quantidade INT NOT NULL,
  PRIMARY KEY (idSerie));

-- -----------------------------------------------------
-- Tabela Peso
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.Peso (
  idPeso INT NOT NULL AUTO_INCREMENT,
  peso INT NOT NULL,
  PRIMARY KEY (idPeso));


-- -----------------------------------------------------
-- Tabela GurpoExercicio
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.GurpoExercicio (
  idGurpoExercicio INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  PRIMARY KEY (idGurpoExercicio));


-- -----------------------------------------------------
-- Tabela Exercicio
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.Exercicio (
  idExercicio INT NOT NULL AUTO_INCREMENT,
  nomeExercicio VARCHAR(100) NOT NULL,
  GurpoExercicio_idGurpoExercicio INT NOT NULL,
  PRIMARY KEY (idExercicio),
  CONSTRAINT fk_Exercicio_GurpoExercicio
    FOREIGN KEY (GurpoExercicio_idGurpoExercicio)
    REFERENCES FitnessXtreme.GurpoExercicio (idGurpoExercicio)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Tabela ExercicioPeso
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.ExercicioPeso (
  idExercicio INT NOT NULL,
  idPeso INT NOT NULL,
  PRIMARY KEY (idExercicio, idPeso),
  CONSTRAINT fk_ExercicioPeso_Exercicio
    FOREIGN KEY (idExercicio)
    REFERENCES FitnessXtreme.Exercicio (idExercicio)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ExercicioPeso_Peso
    FOREIGN KEY (idPeso)
    REFERENCES FitnessXtreme.Peso (idPeso)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Tabela ExercicioSerie
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.ExercicioSerie (
  idExercicio INT NOT NULL,
  idSerie INT NOT NULL,
  PRIMARY KEY (idExercicio, idSerie),
  CONSTRAINT fk_ExercicioSerie_Exercicio
    FOREIGN KEY (idExercicio)
    REFERENCES FitnessXtreme.Exercicio (idExercicio)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ExercicioSerie_Serie
    FOREIGN KEY (idSerie)
    REFERENCES FitnessXtreme.Serie (idSerie)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Tabela AulaExercicio
-- -----------------------------------------------------
CREATE TABLE  FitnessXtreme.AulaExercicio (
  idAula INT NOT NULL,
  idExercicio INT NOT NULL,
  PRIMARY KEY (idAula, idExercicio),
  CONSTRAINT fk_AulaExercicio_Aula
    FOREIGN KEY (idAula)
    REFERENCES FitnessXtreme.Aula (idAula)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_AulaExercicio_Exercicio
    FOREIGN KEY (idExercicio)
    REFERENCES FitnessXtreme.Exercicio (idExercicio)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);