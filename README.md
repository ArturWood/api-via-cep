<img src="https://github.com/ArturWood/games-list/assets/111249818/434c56b3-9dc9-412a-91f7-2edc3f389c14" width=300px alt="Java Logo" />
<img src="https://github.com/ArturWood/games-list/assets/111249818/d8539fd2-938e-4126-b3d4-7236a1ffdbef" width=500px alt="SpringFramework Logo" />

# Via CEP - Aplicação back-end Java 17 com Spring Framework para Consulta de CEP

Esta é uma aplicação Java que permite realizar consultas de CEP utilizando a API Via CEP. A aplicação consome a API para obter dados como logradouro, bairro, cidade e estado relacionados a um determinado CEP informado pelo usuário.

## Pré-requisitos

- Java Development Kit (JDK) versão 8 ou superior instalado
- IDE Java (como Eclipse ou IntelliJ) ou um editor de texto para escrever o código
- Conexão à internet para consumir a API Via CEP

## Configuração

1. Clone este repositório em sua máquina local:

```bash
# clonar repositório
git clone https://github.com/ArturWood/api-via-cep.git

# entrar na pasta do projeto
cd api-via-cep

# executar o projeto
./mvnw spring-boot:run
```

## Endpoints

A aplicação expõe o seguinte endpoint:

- `GET /consulta-cep/{cep}`: Retorna um DTO com informações especificas coletadas da API Via CEP.

## Estrutura do Projeto

O projeto possui a seguinte estrutura de arquivos:

```bash
└───src
    └───main/java
    └───br
        └───com
            └───viacep
                ├───infra
                ├───domain
                ├───controller
                └───service
└── .gitignore
└── api-via-cep.postman_collection.json
└── pom.xml
```

- O pacote `service` contém a classe `ConsultaCepService` que realiza a chamada à API Via CEP para obter os dados do CEP.
- O pacote `domain` contém os records que representam os dados que trafegam pela API, como a resposta a chamada externa `AddressResultDto`, e a resposta do controller `AddressResponseDto`.
- O pacote `infra` contém a classe `ExceptionEntityHandler` responsavel por lidar com as exceptions lançadas pelo controller ou service.
- O arquivo `.gitignore` especifica os arquivos e pastas que devem ser ignorados pelo controle de versão do Git.
- O arquivo `api-via-cep.postman_collection.json` é uma coleção do postman para consultas e exemlos dos endpoints da API.
- O arquivo `pom.xml` para download das dependencias necessarias para o projeto usando maven.

## Documentação

No projeto foi adicionado a dependência `springdoc` para facilitar a documentação e visualização dos endpoints;<br>
Links para uso e documentação:

https://viacep.com.br/<br>
http://localhost:8080/swagger-ui/index.html

![image](https://github.com/ArturWood/api-via-cep/assets/111249818/10af9bff-aef3-479e-9188-2704fc220670)
![image](https://github.com/ArturWood/api-via-cep/assets/111249818/4ded373b-0722-43ba-a6ad-b62efb216afd)
