# 🍔 PedeAi

Sistema backend para gerenciamento de delivery desenvolvido em Java utilizando o framework Spring Boot.

O projeto tem como objetivo fornecer uma API REST para gerenciamento de pedidos, produtos e clientes, servindo como aplicação prática para a disciplina de Programação I.

---

# 📚 Sobre o Projeto

O PedeAi foi criado com a finalidade de simular o funcionamento básico de um sistema de delivery, permitindo operações de cadastro e gerenciamento dos principais recursos da aplicação.

A API segue o padrão REST e foi desenvolvida com foco em organização de código, boas práticas e arquitetura backend utilizando o ecossistema Spring.

---

# 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Maven
- H2 Database
- Swagger/OpenAPI
- Docker
- Git e GitHub

---

# ⚙️ Funcionalidades

- 📦 Gerenciamento de produtos
- 👥 Cadastro de clientes
- 🛒 Controle de pedidos
- 🔍 Operações CRUD
- 🌐 API RESTful
- 📄 Documentação Swagger
- 🐳 Containerização com Docker

---

# ▶️ Como Executar o Projeto

## 📋 Pré-requisitos

Antes de começar, você precisa ter instalado:

- Java JDK 21
- Maven
- Docker (opcional)
- Git

---

# 📥 Clone o repositório

```bash
git clone https://github.com/vinicius-barbosa1/PedeAi.git
```

---

# 📂 Acesse a pasta do projeto

```bash
cd PedeAi
```

---

# ▶️ Executando Localmente

## Via Maven

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080/ajufood/pedeai
```

---

# 🐳 Executando com Docker

## Gerar o JAR

```bash
mvn clean package -DskipTests
```

## Build da imagem Docker

```bash
docker build -t pedeai .
```

## Executar o container

```bash
docker run -p 8080:8080 pedeai
```

A aplicação estará disponível em:

```text
http://localhost:8080/ajufood/pedeai
```

---

# 📄 Documentação da API (Swagger)

A documentação pode ser acessada em:

```text
http://localhost:8080/ajufood/pedeai/swagger-ui/index.html
```

---

# 🗄️ Banco de Dados H2

Console do banco H2:

```text
http://localhost:8080/ajufood/pedeai/h2
```

## Credenciais

### JDBC URL

```text
jdbc:h2:mem:dbPedeAi
```

### Usuário

```text
sa
```

### Senha

```text
(vazio)
```

---

# 🎯 Objetivo Acadêmico

Este projeto foi desenvolvido como atividade prática da disciplina de Programação I, com o objetivo de aplicar conceitos de:

- Programação Orientada a Objetos
- Desenvolvimento Backend com Spring 
- APIs REST
- Persistência de Dados
- Containerização com Docker
- Versionamento com Git/GitHub

---

# 👨‍💻 Autor

Desenvolvido por Vinícius Barbosa

GitHub:
https://github.com/vinicius-barbosa1/PedeAi

---

# 📄 Licença

Este projeto possui finalidade exclusivamente acadêmica.