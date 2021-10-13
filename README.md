# Conversor de Moedas

Este projeto está disponível no [https://currency-converter-app01.herokuapp.com](https://currency-converter-app01.herokuapp.com) e a documentação via Swagger está disponível em [https://currency-converter-app01.herokuapp.com/swagger-ui/](https://currency-converter-app01.herokuapp.com/swagger-ui/).

O Deploy foi feito via Github Actions [deploy-heroku.yml](https://github.com/joserafael97/currency-converter/blob/workflow-heroku/.github/workflows/deploy-heroku.yml) com base na branch [workflow-heroku](https://github.com/joserafael97/currency-converter/tree/workflow-heroku)

## propósito
O principal objetivo da Aplicação é converter um valor monetário de um moeda para outra.

**Obs:** Por uma limitação a conversão é feita das moedas BRL, USD, JPY para EUR.

## features
* Conversão de valores das  BRL, USD, JPY para EUR.

## Motivação tecnológica
O projeto foi construído em JAVA versão 8 devido a familiaridade do desenvovedor nesta tecnologia.

O Framework Spring e todo seu ecosistema como tecnologia de desenvolvimento foi escolhido devido a facilidade na criação de APIs REST e a um conhecimento prêvio do desenvolvedor neste ambiente.

## Possíveis Melhorias

* Adição de autenticação via Spring Security via Token (JWT ou outra forma), restrigindo end-points para listagem e criação de transações (Conversões monetárias). Inserção de uma camada de usuário a qual restringe a visualização de conversões próprias.
* Troca de Api de acesso a taxas de conversão para permitir outras bases de conversões.
* Revisar nomeclaturas usadas no domínio da aplicação.
* Não retornar Id User (Key access api taxas de conversão), pois não faz muito sentido o usuário saber essa informação.
* Melhoria padrões e criação de mecanismo de armazenamento de logs

## Módulos do Projeto

## 1. Common
Módulo principal que contém códigos reaproveitáveis em demais módulos do projeto.

### 1.1 infrastructure
Módulo que contém código utilitários para Controllers, repositórios e validadores.

### 1.2 auth-utils
Módulo com utilitários para adicionar camada segurança eme aplicações com o Spring security.

**Obs:** Na versão atual do projeto não foi utilizado.

## 2. Currency-converter
Módulo principal do projeto com todas regras as negócio para o conversor de moedas.

### 2.1 Currency-converter-domain
Módulo contendo as regras de negócios. Neste módulo foi isolado a parte de libs e infra da regras para conversão de moedas.

Ele apresenta o conceito de arquitetura clean code e hexagonal, considerando a ideia de inversão de dependência, onde os demais módulos devem podem depender do domain mas não o inverso. 

### 2.2 Currency-converter-persistence
Módulo contendo infraestrutura para acesso e gerenciamento da persistência.

### 2.3 Currency-converter-Api
Módulo contendo código para execução da aplicação principal do projeto relacionados ao Currency-converter.

Nesse módulo são expostos todos recursos via API REST construída com Spring Boot. 

## Ambiente de Desenvolvimento

O projeto ccurrency-converter-api (Módulo de execução) utiliza os seguintes componentes:

- Spring boot 2.5.3

O projeto Currency-converter utiliza o [apache maven](https://maven.apache.org/) como gerenciador de pacotes. Assim, para gerar o pacote de desenvolvimento é necessário executar o seguinte comando:

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
spring.datasource.url=jdbc:h2:mem:currency-converter-db
spring.datasource.username=sa
spring.datasource.password=

```
Para alterações nessas configurações o arquivo **currency-converter/currency-converter-api/src/main/resources/application-dev.properties** deve ser alterado.

### O perfil de execução test
O perfil test por padrão acessa o banco de dados utilizando a seguinte configuração:
```
spring.datasource.url=jdbc:h2:mem:test-db
spring.datasource.username=sa
spring.datasource.password=
```
Observe que a única diferença com o perfil dev é o schema do banco de dados. Para alterações nessas configurações o arquivo **currency-converter/src/main/resources/application-test.properties** deve ser alterado.

### O perfil de execução prod
O perfil test por padrão acessa o banco de dados utilizando a seguinte configuração:
```
DEFINIDO VIA VARIÁVEIS DE AMBIENTE
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


