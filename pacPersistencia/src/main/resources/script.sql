create table ESTADO(
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(50) NOT NULL,
    estadoFinal BOOLEAN NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

create table PROJETO(
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
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
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    dataCriacao Date NOT NULL,
    dataPrevistaRealizacao Date NOT NULL,
    abandonado BOOLEAN NOT NULL,
    documento VARCHAR(100) NOT NULL,
    idEstado INTEGER NOT NULL,    
    idUsuario INTEGER NOT NULL,    
    idProjeto INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idEstado) REFERENCES ESTADO(id),
    FOREIGN KEY (idUsuario) REFERENCES USUARIO(id),
    FOREIGN KEY (idProjeto) REFERENCES PROJETO(id)
);

create table ANDAMENTO(
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    dataModificacao Date NOT NULL,
    dataPrevistaConclusao Date NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    idPacote  INTEGER NOT NULL,
    idEstado  INTEGER NOT NULL,
    idUsuario INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idPacote) REFERENCES PACOTE(id),
    FOREIGN KEY (idEstado) REFERENCES ESTADO(id),
    FOREIGN KEY (idUsuario) REFERENCES USUARIO(id)
);

create table MEMBRO_PROJETO(
    idUsuario INTEGER NOT NULL,
    idProjeto INTEGER NOT NULL,
    papel VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_usuario, id_projeto, papel),
    FOREIGN KEY (id_usuario) REFERENCES USUARIO(id),
    FOREIGN KEY (id_projeto) REFERENCES PROJETO(id)
);

