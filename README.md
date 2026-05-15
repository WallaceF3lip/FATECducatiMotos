# 🏍️ Ducati Motos — Cadastro de Motos

Projeto desenvolvido como exercício complementar para a disciplina de **Desenvolvimento para Servidores** do 4º Semestre do curso de **Sistemas para Internet** na [FATEC](https://www.fatec.sp.gov.br/).

---

## 📋 Descrição

Aplicação desktop desenvolvida em **Java** com interface gráfica **Swing**, implementando um **CRUD completo** (Create, Read, Update, Delete) para cadastro de motos da marca Ducati.

A aplicação se conecta a um banco de dados relacional via **JDBC** e permite ao usuário visualizar, cadastrar, editar e excluir motos de forma simples e intuitiva.

---

## ✅ Funcionalidades

| Operação | Tela | Descrição |
|----------|------|-----------|
| **Read** | `ScreenHome` | Lista todas as motos cadastradas em uma tabela com busca em tempo real |
| **Create** | `ScreenCreate` | Formulário para cadastrar uma nova moto |
| **Update** | `ScreenEdit` | Formulário preenchido com os dados atuais da moto para edição |
| **Delete** | `ScreenEdit` | Exclusão da moto com confirmação antes de remover |

---

## 🗂️ Estrutura do Projeto

```
DucatiMotos/
├── src/
│   └── ducatimotos/
│       ├── AppDucatiMotos.java   # Ponto de entrada da aplicação (main)
│       ├── DucatiMotos.java      # Model: atributos e operações CRUD no banco
│       ├── Conecxao.java         # Gerenciamento da conexão JDBC
│       ├── ScreenHome.java       # Tela principal — listagem de motos
│       ├── ScreenCreate.java     # Tela de cadastro de nova moto
│       └── ScreenEdit.java       # Tela de edição e exclusão de moto
└── README.md
```

---

## 🧱 Campos da Entidade Moto

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `ID` | `int` | Identificador único (gerado pelo banco) |
| `Modelo` | `String` | Nome do modelo da moto |
| `Cor` | `String` | Cor da moto |
| `Ano` | `int` | Ano de fabricação |
| `Cilindrada` | `int` | Cilindrada em cc |
| `Preco` | `double` | Preço de venda em R$ |

---

## 🗄️ Banco de Dados

A tabela esperada no banco de dados é:

```sql
CREATE TABLE Motos (
    ID          INT PRIMARY KEY AUTO_INCREMENT,
    Modelo      VARCHAR(100) NOT NULL,
    Cor         VARCHAR(50)  NOT NULL,
    Ano         INT          NOT NULL,
    Cilindrada  INT          NOT NULL,
    Preco       DECIMAL(10, 2) NOT NULL
);
```

Configure os dados de conexão (host, porta, usuário, senha e banco) na classe `Conecxao.java`.

---

## 🛠️ Tecnologias Utilizadas

- **Java** — Linguagem principal
- **Java Swing** — Interface gráfica desktop
- **JDBC** — Conexão com banco de dados relacional
- **MySQL** — Banco de dados (ou compatível)
- **NetBeans IDE** — Ambiente de desenvolvimento

---

## 🚀 Como Executar

1. Clone ou baixe o repositório
2. Importe o projeto no **NetBeans** (ou outra IDE Java)
3. Configure a conexão com o banco em `Conecxao.java`
4. Execute o script SQL acima para criar a tabela
5. Adicione o driver JDBC (MySQL Connector/J) ao classpath do projeto
6. Execute a classe `AppDucatiMotos.java`

---

## 👨‍🎓 Informações Acadêmicas

| | |
|---|---|
| **Instituição** | FATEC |
| **Curso** | Desenvolvimento de Software |
| **Semestre** | 4º |
| **Disciplina** | Desenvolvimento para Servidores |
| **Tipo** | Exercício Complementar |
