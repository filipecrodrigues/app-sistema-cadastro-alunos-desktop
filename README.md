# Sistema de Cadastro de Equipamentos de TI

Sistema Desktop em Java para gerenciamento de equipamentos de TI com operações CRUD completas e exportação para Excel.

## 📋 Funcionalidades

- ✅ Cadastrar equipamentos de TI
- ✅ Listar todos os equipamentos
- ✅ Atualizar dados de equipamentos
- ✅ Deletar equipamentos
- ✅ Validação de número de série único
- ✅ Validação de data de compra
- ✅ Exportação para Excel (.xlsx)
- ✅ Interface gráfica intuitiva com Swing
- ✅ Banco de dados SQLite

## 🎯 Campos do Equipamento

- **Número de Série**: Identificador único do equipamento
- **Marca**: Fabricante do equipamento (ex: Dell, HP, Lenovo)
- **Modelo**: Modelo específico do equipamento
- **Data da Compra**: Data de aquisição (formato: dd/mm/aaaa)

## 📂 Estrutura Simplificada MVC

```
src/main/java/com/equipamentosti/

├── controller/
│   └── EquipamentoController.java  # Lógica de negócio e validações
├── dao/
│   └── EquipamentoDAO.java      # Acesso aos dados (CRUD)
├── model/
│   └── Equipamento.java         # Entidade Equipamento
├── util/
|    ├── DatabaseUtil.java        # Conexão e inicialização do banco
|    └── ExcelUtil.java           # Exportação para Excel
├── view/
│   └── EquipamentoFrame.java    # Interface gráfica (Swing)
└── Main.java                    # Ponto de entrada da aplicação
```

## 🔧 Tecnologias Utilizadas

- **Java 11** - Linguagem de programação
- **Swing** - Interface gráfica
- **SQLite** - Banco de dados embutido
- **Apache POI** - Biblioteca para geração de Excel
- **Maven** - Gerenciamento de dependências
- **Padrão MVC Simplificado** - Arquitetura do sistema

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 21 ou superior
- Maven 3.11.0 ou superior

### Compilar e Executar

1. Clone ou baixe o projeto

2. Compile o projeto:
```bash
mvn clean package
```

3. Execute o JAR gerado:
```bash
java -jar target/equipamentos-ti.jar
```

Ou execute diretamente via Maven:
```bash
mvn exec:java -Dexec.mainClass="com.equipamentosti.Main"
```

## 📖 Como Usar

### Cadastrar Equipamento
1. Preencha todos os campos do formulário
2. Clique no botão **Salvar**
3. O equipamento será adicionado à tabela

### Atualizar Equipamento
1. Clique em um equipamento na tabela para selecioná-lo
2. Os dados serão carregados nos campos
3. Edite as informações desejadas
4. Clique no botão **Atualizar**

### Deletar Equipamento
1. Clique em um equipamento na tabela
2. Clique no botão **Deletar**
3. Confirme a exclusão

### Exportar para Excel
1. Clique no botão **Exportar Excel**
2. Escolha o local e nome do arquivo
3. O arquivo .xlsx será gerado com todos os equipamentos

### Limpar Campos
- Clique no botão **Limpar** para resetar o formulário

## ✅ Validações Implementadas

- ✓ Número de série obrigatório e único
- ✓ Marca obrigatória
- ✓ Modelo obrigatório
- ✓ Data de compra obrigatória
- ✓ Data não pode ser futura
- ✓ Formato de data: dd/mm/aaaa

## 📊 Banco de Dados

O sistema utiliza SQLite com a seguinte estrutura:

```sql
CREATE TABLE equipamentos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    numero_serie TEXT NOT NULL UNIQUE,
    marca TEXT NOT NULL,
    modelo TEXT NOT NULL,
    data_compra TEXT NOT NULL
);
```

O arquivo do banco (`equipamentos_ti.db`) é criado automaticamente na primeira execução.



## 🎨 Padrão MVC Simplificado

### Model (Modelo)
- **Equipamento.java**: Representa a entidade com atributos e métodos get/set

### View (Visão)
- **EquipamentoFrame.java**: Interface gráfica que exibe dados e captura ações do usuário

### Controller (Controlador)
- **EquipamentoController.java**: Intermediário entre View e DAO, contém validações e lógica de negócio

### DAO (Data Access Object)
- **EquipamentoDAO.java**: Acesso direto ao banco de dados, executa operações CRUD

### Util (Utilitários)
- **DatabaseUtil.java**: Gerencia conexão com banco
- **ExcelUtil.java**: Exporta dados para Excel

## 🔄 Fluxo de Dados

```
View (EquipamentoFrame)
    ↓
    └─→ Captura dados do formulário
        ↓
Controller (EquipamentoController)
    ↓
    ├─→ Valida os dados
    └─→ Chama o DAO
        ↓
DAO (EquipamentoDAO)
    ↓
    └─→ Executa SQL no banco
        ↓
Database (SQLite)
```

### MVC Simplificado
```
Model → DAO → Controller → View
```

**Vantagens da versão**
- ✓ Menos arquivos e interfaces
- ✓ Mais fácil de entender para iniciantes
- ✓ Ideal para projetos menores
- ✓ Manutenção mais rápida
- ✓ Código mais direto

**Quando usar**
: Projetos pequenos/médios, equipes pequenas, aprendizado

##  Solução de Problemas

### Erro: "Data inválida"
- Verifique se está usando o formato dd/mm/aaaa (ex: 15/10/2024)

### Erro: "Número de série já cadastrado"
- O número de série deve ser único, escolha outro

### Banco não cria
- Verifique permissões de escrita na pasta do projeto

### Excel não exporta
- Verifique se tem permissão de escrita no local escolhido

## 📝 Licença

Este projeto é de código aberto para fins educacionais.

## ✒️ Autor

Desenvolvido como exemplo de Sistema CRUD Desktop em Java com MVC Simplificado
