create table PROJETO(
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    dataInicio Date NOT NULL,
    dataTermino Date NOT NULL,
    patrocinador VARCHAR(500) NOT NULL,
    stakeholders VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
);

create table USUARIO(
    id INTEGER NOT NULL,
    ativo BOOLEAN NOT NULL,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
);

create table PACOTE(
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    dataCriacao Date NOT NULL,
    dataPrevistaRealizacao Date,
    abandonado BOOLEAN NOT NULL,
    documento VARCHAR(100) NOT NULL,
    nomeEstado VARCHAR(50) NOT NULL,
    idUsuario INTEGER NOT NULL,    
    idProjeto INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idUsuario) REFERENCES USUARIO(id),
    FOREIGN KEY (idProjeto) REFERENCES PROJETO(id)
);

create table ANDAMENTO(
    id INTEGER NOT NULL AUTO_INCREMENT,
    dataModificacao Date NOT NULL,
    dataPrevistaConclusao Date,
    descricao VARCHAR(100) NOT NULL,
    idPacote  INTEGER NOT NULL,
    nomeEstado  VARCHAR(30) NOT NULL,
    idUsuarioRemetente INTEGER NOT NULL,
    idUsuarioDestinatario INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (idPacote) REFERENCES PACOTE(id),
    FOREIGN KEY (idUsuarioRemetente) REFERENCES USUARIO(id),
    FOREIGN KEY (idUsuarioDestinatario) REFERENCES USUARIO(id)
);


create table MEMBRO(
    idUsuario INTEGER NOT NULL,
    idProjeto INTEGER,
    papel VARCHAR(100) NOT NULL,
    PRIMARY KEY (idUsuario, idProjeto, papel),
    FOREIGN KEY (idUsuario) REFERENCES USUARIO(id),
    FOREIGN KEY (idProjeto) REFERENCES PROJETO(id)
);

