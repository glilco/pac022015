DROP TABLE IF EXISTS TRANSICAO;
DROP TABLE IF EXISTS MEMBRO;
DROP TABLE IF EXISTS ANDAMENTO;
DROP TABLE IF EXISTS PACOTE;
DROP TABLE IF EXISTS USUARIO;
DROP TABLE IF EXISTS PROJETO;
DROP TABLE IF EXISTS ESTADO;
DROP TABLE IF EXISTS PAPEL;

CREATE TABLE PROJETO(
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    dataInicio Date NOT NULL,
    dataTermino Date NOT NULL,
    patrocinador VARCHAR(500) NOT NULL,
    stakeholders VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE USUARIO(
    id INTEGER NOT NULL,
    ativo BOOLEAN NOT NULL,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ESTADO(
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    estadoFinal BOOLEAN NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    permiteDelegacao BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE PAPEL(
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE PACOTE(
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    dataCriacao Date NOT NULL,
    dataPrevistaRealizacao Date,
    abandonado BOOLEAN NOT NULL,
    documento VARCHAR(100) NOT NULL,
    idEstado  INTEGER NOT NULL,
    idUsuario INTEGER NOT NULL,
    idProjeto INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idEstado)  REFERENCES ESTADO(id),
    FOREIGN KEY (idUsuario) REFERENCES USUARIO(id),
    FOREIGN KEY (idProjeto) REFERENCES PROJETO(id)
);

CREATE TABLE ANDAMENTO(
    id INTEGER NOT NULL AUTO_INCREMENT,
    dataModificacao Date NOT NULL,
    dataPrevistaConclusao Date,
    descricao VARCHAR(100) NOT NULL,
    idPacote  INTEGER NOT NULL,
    idEstado  INTEGER NOT NULL,
    idUsuarioRemetente INTEGER NOT NULL,
    idUsuarioDestinatario INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (idEstado)  REFERENCES ESTADO(id),
    FOREIGN KEY (idPacote) REFERENCES PACOTE(id),
    FOREIGN KEY (idUsuarioRemetente) REFERENCES USUARIO(id),
    FOREIGN KEY (idUsuarioDestinatario) REFERENCES USUARIO(id)
);

CREATE TABLE MEMBRO(
    idUsuario INTEGER NOT NULL,
    idProjeto INTEGER,
    idPapel   INTEGER NOT NULL,
    PRIMARY KEY (idUsuario, idProjeto, idPapel),
    FOREIGN KEY (idUsuario) REFERENCES USUARIO(id),
    FOREIGN KEY (idProjeto) REFERENCES PROJETO(id),
    FOREIGN KEY (idPapel)   REFERENCES PAPEL(id)
);

CREATE TABLE TRANSICAO(
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    regra VARCHAR(30) NOT NULL,
    idEstadoOrigem INTEGER,
    idEstadoDestino INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (idEstadoOrigem)   REFERENCES ESTADO(id),
    FOREIGN KEY (idEstadoDestino)   REFERENCES ESTADO(id)
);