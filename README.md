🚀 Hangar - Stock Manager

O Hangar é uma API robusta de gestão de estoque desenvolvida com Spring Boot 4, projetada para oferecer controle eficiente de entradas, saídas e armazenamento de produtos. O projeto foi construído focando em escalabilidade e facilidade de configuração, utilizando Docker para garantir que o ambiente de desenvolvimento seja idêntico ao de produção.
🛠️ Tecnologias Utilizadas

O projeto utiliza o que há de mais moderno no ecossistema Java (2026):

    Linguagem: Java 17

    Framework: Spring Boot 4.0.3

    Gerenciador de Dependências: Gradle (Groovy)

    Banco de Dados: PostgreSQL 15

    Migrações de Banco: Flyway

    Documentação: Springdoc OpenAPI (Swagger UI) 3.0.2

    Containerização: Docker & Docker Compose

📋 Pré-requisitos

Para rodar este projeto localmente, você não precisa de Java ou PostgreSQL instalados diretamente na sua máquina. Tudo o que você precisa são as seguintes ferramentas de containerização:

    Docker Desktop (Engine + CLI): Guia de Instalação

    Docker Compose: Guia de Instalação

⚙️ Configuração Local

Siga os passos abaixo para colocar o Hangar para rodar:
1. Clonar o Repositório

```

git clone https://github.com/SEU_USUARIO/hangar.git

cd hangar
```

2. Rodar com Docker Compose

Na raiz do projeto, execute o comando abaixo. O Docker irá baixar as imagens do JDK 17 e do PostgreSQL, compilar o código via Gradle e subir os serviços:

```
docker-compose up --build
```


🔍 Acessando a API

Após o log do terminal exibir Started HangarApplication, você poderá acessar:

    API Base: http://localhost:8080/api

    Documentação Swagger (UI): http://localhost:8080/swagger-ui/index.html

    Banco de Dados (Postgres): Localizado na porta 5432

📂 Estrutura de Migrações (Flyway)

Como o projeto utiliza o Flyway, o esquema do banco de dados é versionado. Para adicionar novas tabelas, crie scripts SQL em:
src/main/resources/db/migration/V1__descrição.sql

🤝 Contribuindo

    Faça um Fork do projeto.

    Crie uma Branch para sua feature (git checkout -b feature/nova-feature).

    Dê um Commit nas suas alterações (git commit -m 'Add nova feature').

    Dê um Push para a Branch (git push origin feature/nova-feature).

    Abra um Pull Request.
