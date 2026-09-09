# Register Acccount

Projeto desenvolvido para pratica de conceitos básicos de JWT e de Consumo de API, utilizando de Registro e login de Conta do usuário.

## Stacks
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Web
- Spring Security + JWT (auth0)
- Flyway
- MySqL
- Lombok

### Modelagem de Dados

Fiz uma pequena modelagem para esse projeto, e pretendo ao final incrementar, então a V1 conta com essas entidades:

<img width="3600" height="2121" alt="Diagrama em branco" src="https://github.com/user-attachments/assets/0f893d5a-7bb2-40e6-8d0f-2f68e517db6e" />



## Testando Localmente

Pré-requisitos:
- Java instalado.
- Maven Instalado.
- SGBD MYSQL - Porém o banco de sua preferência, porém terá que configurar no application.properties. 

```
1. Clone o repositório para sua máquina

2. Configure as váriavéis de ambiente
spring.database.url=suaUrl
app.jwt.secret.key=suaChaveSecreta

3. Rode via comando ou via IDE
mvn clean install
e
mvn spring-boot:run
```
 


