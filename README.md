# to-do-list

Este é um projeto de uma aplicação de lista de tarefas (ToDoList) desenvolvida em Java utilizando o framework Spring Boot. A aplicação permite gerenciar tarefas, oferecendo funcionalidades para criação, atualização, listagem e exclusão, além de atualizar o status das tarefas.

## Funcionalidades

- Criar tarefas com título, data de previsão ou prazo, prioridade e status inicial. 
- Atualizar tarefas existentes, alterando título, data de previsão, prazo ou prioridade. 
- Listar tarefas com possibilidade de filtragem por título. 
- Excluir tarefas pelo ID. 
- Atualizar o status de uma tarefa (por exemplo, marcar como finalizada).

## Tecnologias usadas

- Java 17: Linguagem de programação utilizada no desenvolvimento. 
- Spring Boot: Framework para criação de aplicações Java robustas e escaláveis. 
- Spring Data JPA: Abstração para persistência de dados com JPA/Hibernate. 
- JUnit 5: Framework para testes unitários. 
- Mockito: Biblioteca para criação de mocks em testes. 
- Spring Boot Test: Conjunto de utilitários para facilitar os testes de integração. 
- RestAssured: Biblioteca para teste de APIs RESTful. 
- Swagger OpenAPI: Ferramenta para documentação da API. 
- Lombok: Biblioteca para reduzir código boilerplate.

## Estruturas do projeto

- `src.main.java.com.todolist`: Contém a classe principal da aplicação.
- `src.main.java.com.todolist.entity`: Entidades JPA que representam as tabelas do banco de dados.
- `src.main.java.com.todolist.repository`: Interfaces de repositórios JPA.
- `src.main.java.com.todolist.service`: Contém a lógica de negócio da aplicação.
- `src.main.java.com.todolist.controller`: Controladores REST que expõem a API.
- `src.main.java.com.todolist.request`: Classes que representam os payloads das requisições.
- `src.main.java.com.todolist.enums`: Enumerações utilizadas na aplicação.
- `src.main.java.com.todolist.exceptions`: Classes de exceções personalizadas.
- Testes:
  - `src.test.java.com.todolist.integration`: Testes de integração que verificam o fluxo completo da aplicação.
  - `src.test.java.com.todolist.mock`: Geração de dados mock
  - `src.test.java.com.todolist.controller`: Testes unitários da camada controller.
  - `src.test.java.com.todolist.repository` Testes unitários da camada repository.
  - `src.test.java.com.todolist.service`: Testes unitários da camada service.

## Como executar o projeto

### Pré-requisitos

- Java 17 ou superior instalado
- Maven instalado

### Passos

1. Clonar o repositório

```bash
git clone https://github.com/lucas-csoares/to-do-list.git
```

2. Navegar até o diretório do projeto

```bash
cd to-do-list
```

3. Construir o projeto

```bash
mvn clean install
```

4. Executar a aplicação

```bash
mvn spring-boot:run
```

# Execução de testes

Para executar todos os testes unitários e de integração:

```bash
mvn test
```

# Autor

Lucas Cabral Soares



