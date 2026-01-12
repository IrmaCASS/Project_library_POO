CREATE TABLE If not exists usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT NOT NULL,
    peso DOUBLE NOT NULL,
    altura DOUBLE NOT NULL,
    sexo VARCHAR(20),
    porcentagem_gordura DOUBLE,
    massa_corporal DOUBLE,
    meta VARCHAR(100),
    data_cadastro DATE NOT NULL DEFAULT CURRENT_DATE,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);



CREATE TABLE IF not exists alimento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    calorias DOUBLE NOT NULL,
    proteinas DOUBLE NOT NULL,
    carboidratos DOUBLE NOT NULL,
    gorduras DOUBLE NOT NULL,
    categoria VARCHAR(100),
    porcao_padrao DOUBLE NOT NULL
);



CREATE TABLE IF not exists meta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    valor DOUBLE NOT NULL,
    prazo DATE NOT NULL,
    usuario_id BIGINT NOT NULL,
    
    -- Estabelecendo o relacionamento com a tabela usuario
    CONSTRAINT fk_usuario_meta FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE IF not exists refeicao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_registro DATE NOT NULL,
    hora_registro TIME NOT NULL,
    usuario_id INT NOT NULL,
    total_calorias DOUBLE NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    -- Referência para a tabela usuario
    CONSTRAINT fk_usuario_refeicao FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE IF not exists refeicao_alimentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    refeicao_id BIGINT NOT NULL,
    alimento_id BIGINT NOT NULL,
    -- Chaves estrangeiras para manter a integridade
    CONSTRAINT fk_refeicao_link FOREIGN KEY (refeicao_id) REFERENCES refeicao(id),
    CONSTRAINT fk_alimento_link FOREIGN KEY (alimento_id) REFERENCES alimento(id)
);

CREATE TABLE IF not exists relatorio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_calorias DOUBLE NOT NULL,
    evolucao_peso DOUBLE NOT NULL,
    status_meta VARCHAR(100),
    usuario_id BIGINT NOT NULL,
    
    -- Relacionamento com a tabela usuario
    CONSTRAINT fk_usuario_relatorio FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

