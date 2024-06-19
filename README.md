# ForumHub API

## Descrição
Este projeto implementa uma API RESTful para gerenciar um fórum de discussões sobre cursos, tópicos, respostas e usuários. Ele oferece operações para criar, listar, atualizar e excluir cursos, tópicos, respostas e usuários, além de funcionalidades de autenticação e autorização com tokens JWT.

## Funcionalidades Principais

### Autenticação e Segurança

- **AuthController**: Endpoint `/login` para autenticação de usuários usando email e senha, gerando um token JWT.
- **TokenService**: Serviço para geração e validação de tokens JWT usando um segredo configurável.

### Gestão de Cursos

- **CourseController**: Endpoint `/cursos` para operações CRUD em cursos.
- **CourseRepository**: Interface Spring Data JPA para acesso aos dados de cursos.

### Gestão de Tópicos

- **TopicController**: Endpoint `/topicos` para operações CRUD em tópicos de discussão.
- **TopicService**: Serviço para criar, atualizar, listar e deletar tópicos com validação de autorização e verificação de duplicidade de título e mensagem.

### Gestão de Respostas

- **ReplyController**: Endpoint `/topicos/{id}/resposta` para operações CRUD em respostas de tópicos.
- **ReplyService**: Serviço para criar, atualizar e deletar respostas com validação de autorização.

### Gestão de Usuários

- **UserController**: Endpoint `/user` para operações CRUD em usuários.
- **UserService**: Serviço para atualizar e deletar usuários com validação de autorização e critérios de segurança de senha.

## Tecnologias e Bibliotecas Utilizadas

- **Spring Boot**: Framework para criação de aplicações Java.
- **Spring Data JPA**: Facilita o acesso e manipulação de dados com o banco de dados usando Hibernate.
- **Spring Security**: Fornecimento de autenticação e autorização.
- **Spring Validation**: Suporte para validação de dados.
- **Flyway**: Controle de versão para banco de dados.
- **PostgreSQL**: Banco de dados relacional.
- **Lombok**: Biblioteca para reduzir o código boilerplate.
- **Springdoc OpenAPI**: Geração de documentação interativa da API com o Swagger.
- **Java JWT (Auth0)**: Biblioteca para manipulação de tokens JWT.

## Configuração e Execução

### Pré-requisitos

- Java 17 ou superior instalado.
- Maven instalado para construção do projeto.
- Banco de dados PostgreSQL configurado com as variáveis de ambiente `POSTGRES_DB_HOST`, `POSTGRES_DB_NAME`, `POSTGRES_DB_USER` e `POSTGRES_DB_PASSWORD`.

### Configuração do Banco de Dados

- Configure um banco de dados PostgreSQL com o nome e credenciais especificadas em `application.properties`.

### Build do Projeto com Maven

```bash
mvn clean package
```
#### Execução do Projeto
```bash
java -jar target/forumHub-0.0.1-SNAPSHOT.jar
```
#### Acessando a Documentação da API
- Após iniciar o projeto, acesse http://localhost:8080/swagger-ui.html para visualizar a documentação interativa da API gerada pelo Swagger.

#### Contribuição e Licença
- Este projeto está licenciado sob a MIT License. Sinta-se à vontade para contribuir e enviar pull requests para melhorar este projeto.