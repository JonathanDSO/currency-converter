# Projeto REST com Spring Boot - Currency Converter

Desenvolvimento de uma API REST com Java e Spring Boot

Para outros detalhes desta aplicação, acessar a Wiki deste projeto:
https://github.com/JonathanDSO/currency-converter/wiki


## Como executar
- Clone este repositório
- Certifique-se de usar JDK 11 e Maven 3.x
- Você pode construir o projeto e executar os testes executando 
```
mvn clean package 
```
- Depois de construído com sucesso, você pode executar o serviço por um destes métodos:

### Via docker
Ter instalado o docker.
Construir e subir a imagem docker, através do comando:
```
docker-compose up -d --build
```

### Via "mvn spring-boot:run"
- Executar o comando:
```
mvn spring-boot:run
```

### Via "java -jar" 
- Acessar a pasta target, onde foi gerado o .jar, e executar o comando:
```
java -jar customer-crud-0.0.1-SNAPSHOT.jar
```

Depois que o aplicativo for executado, você deve ver algo assim

```
2021-06-23 16:53:42.924  INFO 504 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-06-23 16:53:42.926  INFO 504 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Context refreshed
2021-06-23 16:53:42.975  INFO 504 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Found 1 custom documentation plugin(s)
2021-06-23 16:53:43.052  INFO 504 --- [           main] s.d.s.w.s.ApiListingReferenceScanner     : Scanning for api listing references
2021-06-23 16:53:43.462  INFO 504 --- [           main] b.c.j.c.CurrencyConverterApplication     : Started CurrencyConverterApplication in 9.013 seconds (JVM running for 9.881)
```

Para visualizar os documentos da API Swagger 2
Execute o servidor e navegue até http://localhost:8080/swagger-ui.html

## Sobre o serviço

O serviço é apenas um serviço REST simples que realiza conversão de moedas, cadastro das transações e consulta das trnasações. Ele usa um banco de dados na memória (H2) para armazenar os dados. Com possibilidade para alterar para um banco de dados relacional como MySQL ou PostgreSQL. Ajustando apenas o arquivo `application.properties` e inserindo a dependência do banco de dados, no arquivo  `pom.xml`.

Aqui está o que este pequeno aplicativo demonstra:

-   Integração total com o **Spring** Framework mais recente : inversão de controle, injeção de dependência, etc.
-   Empacotando como um único jar. 
-   Serviço RESTful usando anotação, com solicitações via JSON.
-   Mapeamento de exceções para a resposta HTTP correta com detalhes de exceção no corpo.
-   Integração do _Spring Data_ com JPA / Hibernate.
-   Funcionalidade CRUD automática em relação à fonte de dados usando o padrão Spring _Repository_
-   Demonstra a estrutura de teste MockMVC com bibliotecas associadas
-   Todas as APIs são "autodocumentadas" pelo Swagger2 usando anotações

### Converter moeda

```
POST /currency-converter
Accept: application/json
Content-Type: application/json

{
	"userId":1,
	"sourceCurrency":"EUR",
	"sourceValue":1,
	"targetCurrency":"BRL"
}

RESPONSE: HTTP 201 (Created)
```

### Recupere a lista paginada de transações

```
GET /currency-converter?page=0&size=10

Content-Type: application/json

[
    {
        "transactionId": 1,
        "userId": 1,
        "sourceCurrency": "EUR",
        "sourceValue": 1,
        "targetCurrency": "JPY",
        "targetValue": 132.10849,
        "conversionRateUsed": 132.10849,
        "date": "2021-06-28"
    },
    {
        "transactionId": 2,
        "userId": 1,
        "sourceCurrency": "EUR",
        "sourceValue": 1,
        "targetCurrency": "BRL",
        "targetValue": 5.904754,
        "conversionRateUsed": 5.904754,
        "date": "2021-06-28"
    }
]

Resposta: HTTP 200
Conteúdo: lista paginada de transações
```

## Bibliotecas usadas

 1. Spring Boot 2.5.1 
 2. Spring Boot Test
 3. Swagger 2 
 4. H2 database
 5. Lombok
 6. Dozer Mapper
