# Order Processing

Sistema desenvolvido em Java com Spring Boot para processar pedidos e buscá-los.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![REST API](https://img.shields.io/badge/REST%20API-4CAF50?style=for-the-badge&logo=cloudflare&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2%20Database-004d99?style=for-the-badge&logo=h2&logoColor=white)

## 📑 Índice

1. [Ambiente de Desenvolvimento](#-ambiente-de-desenvolvimento)
2. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
3. [Como Rodar o Projeto](#-como-rodar-o-projeto)
4. [Como Executar os Testes](#-como-executar-os-testes)

## 🚀 Ambiente de desenvolvimento

O repositório do código fonte se encontra privado no GitHub:

>https://github.com/gneves14/order-processing

## 💻 Tecnologias Utilizadas:

>Java 21 → Linguagem de programação

>Spring → Framework

>Maven → Ferramenta de build

>H2 Database → Banco de dados (memória)

## 🛠️ Como rodar o projeto

Para rodar o projeto basta executar o seguinte comando:

```properties
    mvn spring-boot:run
```

## ⚙️ Como executar os testes

Os testes são criados usando as bibliotecas JUnit e Mockito propriamente do Spring.
Para executar os testes basta rodar o seguinte comamdo:

```properties
    mvn test
```

## 📄 Documentação

> Este projeto tem como objetivo receber um arquivo via API, onde cada linha é parte de um pedido, processá-lo, e persistir, separando usuário, pedidos e produtos. 
> 
> Após o arquivo ser processado, é possível consultar os dados gerados a partir da extração e leitura do mesmo, onde o retorno é feito utilizando DTOs para não expor as entidades.
> A consulta pode ser feita via API, onde possui os filtros não obrigatórios de id do  pedido (orderId), data início (startDate) e data fim (endDate).
> 
> A documentação e testes de APIs, se encontra no Swagger: /processor/swagger-ui/index.html
> 
> Obs.: É possível que nos pedidos, possua itens com o mesmo productId. Isto se deve devido ao fato de ser permitido haver diferentes meios de pagamento no pedido, etc.