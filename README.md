# Order Processing

Sistema desenvolvido em Java com Spring Boot para processar pedidos e buscá-los.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![REST API](https://img.shields.io/badge/REST%20API-4CAF50?style=for-the-badge&logo=cloudflare&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00758F?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![H2 Database](https://img.shields.io/badge/h2_database-09476b?style=for-the-badge&logo=h2database&logoColor=white)

## 📁 Índice

1. [Ambiente de Desenvolvimento](#-ambiente-de-desenvolvimento)
2. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
3. [Como Rodar o Projeto](#-como-rodar-o-projeto)
4. [Como Executar os Testes](#-como-executar-os-testes)
5. [Containerização com Docker](#-containerizacao-com-docker)
6. [Documentação](#-documentacao)
7. [Observações](#-observacoes)

<a name="-ambiente-de-desenvolvimento"></a>
## 🚀 Ambiente de desenvolvimento

O repositório do código fonte se encontra privado no GitHub:

> https://github.com/gneves14/order-processing

<a name="-tecnologias-utilizadas"></a>
## 💻 Tecnologias Utilizadas:

> Java 21 → Linguagem de programação 

> Spring → Framework
 
> Maven → Ferramenta de build  

> MySQL → Banco de dados  

> H2 Database → Banco de dados em memória (testes)  

> Docker → Containerização de serviços

<a name="-como-rodar-o-projeto"></a>
## 🛠️ Como rodar o projeto

Para rodar o projeto com Docker:

```bash
docker-compose up --build
```

<a name="-como-executar-os-testes"></a>
## ⚙️ Como executar os testes

Os testes são criados usando as bibliotecas JUnit e Mockito.  
Para executar os testes unitários (usando H2 como banco de testes):

```bash
mvn test
```

<a name="-containerizacao-com-docker"></a>
## 🐳 Containerização com Docker

A aplicação é containerizada com Docker. Dois contêineres são utilizados:

- mysql_container: Banco de dados MySQL 8.0
- order-processing-app: Aplicação Spring Boot buildada com Maven

O Dockerfile utiliza duas fases:

1. **build**: compila o projeto usando Maven
2. **run**: roda o .jar gerado

Para subir os serviços:

```bash
docker-compose up --build
```

Para parar os serviços:

```bash
docker-compose down
```

<a name="-documentacao"></a>
## 📄 Documentação

> Este projeto tem como objetivo:

> - Receber um arquivo via API, onde cada linha representa parte de um pedido
> - Processar e persistir os dados, separando usuário, pedidos e produtos
> - Disponibilizar consulta dos dados via API utilizando filtros opcionais:
>    - orderId (ID do pedido)
>    - startDate (data de início)
>    - endDate (data de fim)

> A documentação das APIs está disponível via Swagger:

>/processor/swagger-ui/index.html

<a name="-observacoes"></a>
## 📄 Observações
> - Um mesmo pedido pode conter produtos com o mesmo productId, pois é permitido haver diferentes meios de pagamento, entre outras variações.
>
> 
> - A configuração do projeto garante que a base de dados seja reiniciada e limpa a cada inicialização.