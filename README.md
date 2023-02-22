# RETROCARDS
Desafio desenvolvido para criar um sistema para gerenciar reuniões de [retrospectiva](https://www.scrum.org/resources/what-is-a-sprint-retrospective) e também de [Kudo Box/Cards](https://management30.com/practice/kudo-cards/) Descrição do desafio e requisitos no arquivo **[DESAFIO.md](https://github.com/dayvidsonveiga/retrocards-back/blob/main/CHALLENGE.md)**

- **[Saiba tudo sobre o projeto com pdf de apresentação do projeto. Clique aqui.](https://github.com/dayvidsonveiga/retrocards-back/blob/main/presentation/RetroCards%20-%20Apresenta%C3%A7%C3%A3o.pdf)**


# Tecnologias utilizadas

- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa#overview)** 
- **[Hibernate](https://hibernate.org/orm/)**
- **[Lombok](https://projectlombok.org/)**
- **[Docker](https://www.docker.com/)**
- **[POSTGRES]

# Requisitos para executar o projeto
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [JDK 17+](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)

# Como executar o projeto
- Clone o projeto.
```bash
  git clone https://github.com/dayvidsonveiga/retrocards-back
```
- Abra um terminal na raiz do projeto e execute o comando abaixo para iniciar o banco de dados Mysql no docker.
```bash
  cd docker && docker-compose up -d
```

- Abra a IDE de sua preferência e importe o projeto clonado e aguarde o download de todas dependências do projeto

- Execute o arquivo com a classe main RetrocardsApplication.java

- Acesse a interface dos recursos do backend através do swagger usando o endereço local http://localhost:8080


