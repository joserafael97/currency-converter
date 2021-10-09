# Conversor de Moedas

Este projeto está disponível no [Todo name](linkurl) e a documentação via Swagger está disponível em [swagger](urllink).

O Deploy foi feito via Github Actions [heroku-deploy](workflowsLink) com base na branch [heroku-deploy](linkbranch)

## Módulos do Projeto

## 1. Common
Módulo principal que contém códigos reaproveitáveis em demais módulos do projeto.

### 1.1 infrastructure
Módulo que contém código utilitários para Controllers, repositórios e validadores.

### 1.2 auth-utils
Módulo com utilitários para adicionar camada segurança eme aplicações com o Spring security.

## 2. Currency-converter
Módulo principal do projeto com todas regras as negócio para catálogos de produtos.

### 2.1 Currency-converter-Model
Módulo contendo regra de negócio.

### 2.2 Currency-converter-Repository
Módulo contendo infraestrutura para acesso e gerenciamento da persistência.

### 2.3 Currency-converter-Api
Módulo contendo código para execução da aplicação principal do projeto relacionados ao catalog.

## Ambiente de Desenvolvimento

O projeto Currency-converter server utiliza os seguintes componentes:

- Spring boot 2.5.3

O projeto Catalog utiliza o [apache maven](https://maven.apache.org/) como gerenciador de pacotes. Assim, para gerar o pacote de desenvolvimento é necessário executar o seguinte comando:

```
mvn clean package
```
O comando acima gera o executável do projeto no caminho **currency-converter/currency-converter-api/target/currency-converter-api.jar**.

## Execução do componente
O módulo currency-converter server possui três perfis de execução:

- dev: Para execução em ambiente de desenvolvimento.
- test: Para execução em ambiente de testes.
- prod: Para execução em ambiente de produção.

Basicamente o que difere de um perfil de execução para outro é a conexão com banco de dados. Para selecionar o perfil durante a execução do sistema, o comando abaixo deve ser executado:
```
java -jar <<arquivojar> -Dspring.profiles.active=<<perfil>>
```

ou via Maven:

```
mvn spring-boot:run -Drun.profiles=<<perfil>>
```

Onde o perfil deve ser um dos três mencionados no começo dessa seção. É importante frisar, que caso o parâmetro -Dspring.profiles.active seja informado, o perfil padrão executado será o dev.

### O perfil de execução dev
O perfil dev por padrão acessa o banco de dados utilizando a seguinte configuração:
```
host: localhost
porta: 5432
banco de dados: currency-converter
usuário: postgres
senha: 123
schema: public
```
Para alterações nessas configurações o arquivo **currency-converter/currency-converter-api/src/main/resources/application-dev.properties** deve ser alterado.

### O perfil de execução test
O perfil test por padrão acessa o banco de dados utilizando a seguinte configuração:
```
host: localhost
porta: 5432
banco de dados: currency-converter
usuário: pguser
senha: secret
schema: test
```
Observe que a única diferença com o perfil dev é o schema do banco de dados. Para alterações nessas configurações o arquivo **testrec-server/src/main/resources/application-test.properties** deve ser alterado.

### O perfil de execução prod
O perfil test por padrão acessa o banco de dados utilizando a seguinte configuração:
```
url=jdbc:h2:mem:testdb
driverClassName=org.h2.Driver
username=sa
password=
schema: public
```

Observe que para o perfil de produção, são utilizadas variáveis de ambiente para configuração da conexão com banco de dados. Caso as variáveis não sejam informadas, os valores padrão serão os que estão entre parênteses. Para alterações nessas configurações o arquivo **currency-converter/src/main/resources/application-prod.properties** deve ser alterado.

## Execução via Docker

Com docker em execução dentro do diretório no módulo principal contendo **docker-compose.yaml** basta executar o seguinte comando:

```
docker-compose up --build
```

O enderenço local da aplicação disponível será: 

```
http://localhost:9999
```

Para acessar a documentação via Swagger:

```
http://localhost:9999/swagger-ui/#/

```


Para para aplicação:

```
docker-compose down

```


