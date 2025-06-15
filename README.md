Está API foi construída para gerenciar as tarefas diárias de um usuário único para melhor organização de seu dia-a-dia.

Tecnologias utilizadas:
- Java 21;
- Ecossistema Spring(Spring Boot, Spring Data JPA, Spring Web);
- Lombok para uma sintaxe mais limpa;
- Banco de dados PostgreSQL;
- JUnit5 e Mockito para testes;
- Swagger/OpenAPI para documentação dos endpoints;
- Maven para gerenciamento de dependências.

Instruções para rodar o projeto:

- Baixar e instalar o Java 21, garantindo que ele esteja no PATH;
- Baixar e instalar o postgreSQL versão 17.5 (https://www.enterprisedb.com/downloads/postgres-postgresql-downloads);
- Criar um database no postgreSQL (recomendo o nome GestaoTarefas);
- Baixar ou clonar o repositório;
- Via terminal, dentro da pasta do projeto, executar o seguinte comando para buildar o jar do projeto: ./mvnw clean package;
- Editar os arquivos run.cmd (Windows) e run.sh (Linux) e substituir os valores das variáveis de acordo:
        POSTGRES_HOST: endereço do banco de dados;
        POSTGRES_PORT: porta do banco de dados;
        POSTGRES_DB: nome do database criado;
        POSTGRES_USER: usuário do postgreSQL;
        POSTGRES_PASSWORD: senha do postgreSQL;
- Via terminal, dentro da pasta do projeto, executar o seguinte comando:
        Windows: .\run.cmd
        Linux: ./run.sh       

Url para documentação da API: http://localhost:8080/swagger-ui/index.html (Disponível com o projeto rodando);

