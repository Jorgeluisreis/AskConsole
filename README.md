<p align="center">
  <img src="https://i.imgur.com/8YBKZWj.png" alt="AskConsole Logo">
</p>
<p align="center"> <a href="https://github.com/Jorgeluisreis/AskConsole"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Jorgeluisreis/AskConsole"> </a> <a href="https://github.com/Jorgeluisreis/AskConsole/issues"> <img alt="GitHub issues" src="https://img.shields.io/github/issues/Jorgeluisreis/AskConsole"> </a> <a href="https://github.com/Jorgeluisreis/AskConsole/pulls"> <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/Jorgeluisreis/AskConsole"> </a> <a href="https://github.com/Jorgeluisreis/AskConsole"> <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/Jorgeluisreis/AskConsole"> </a> <a href="https://github.com/Jorgeluisreis/AskConsole"> <img alt="License" src="https://img.shields.io/github/license/Jorgeluisreis/AskConsole"> </a> </p>
<p align="center"> <a href="https://github.com/Jorgeluisreis/AskConsole"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Jorgeluisreis/AskConsole"> </a> <a href="https://github.com/Jorgeluisreis/AskConsole/issues"> <img alt="GitHub issues" src="https://img.shields.io/github/issues/Jorgeluisreis/AskConsole"> </a> <a href="https://github.com/Jorgeluisreis/AskConsole/pulls"> <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/Jorgeluisreis/AskConsole"> </a> <a href="https://github.com/Jorgeluisreis/AskConsole"> <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/Jorgeluisreis/AskConsole"> </a> <a href="https://github.com/Jorgeluisreis/AskConsole"> <img alt="License" src="https://img.shields.io/github/license/Jorgeluisreis/AskConsole"> </a> </p>

# AskConsole

🚀 **AskConsole** é uma aplicação Java interativa que permite criar e gerenciar conversas com uma interface de linha de comando (CLI). Desenvolvido com o intuito de oferecer uma experiência robusta para interação e salvamento de conversas, o AskConsole é ideal para quem busca uma ferramenta prática e eficiente para comunicação textual em ambiente de console.

## 📚 Conceito

O AskConsole foi criado com o intuito de utilizar a estrutura simples do CLI com uma aplicação escalável, no caso, um ecossistema de consumo de uma LLM, sendo o Gemini do Google. Há diversos conceitos implícitos nesta aplicação, desde uma simples interação no chat até a possibilidade de continuar uma conversa de onde parou.

## 🛠️ Tecnologias Utilizadas

- ![Java](https://img.shields.io/badge/Java-21-blue) - Linguagem de programação utilizada
- ![Jansi](https://img.shields.io/badge/Jansi-2.4.1-blue) - Biblioteca para manipulação de cores no console
- ![Google Cloud AI Platform](https://img.shields.io/badge/Google_Cloud_AI_Platform-3.49.0-blue) - API utilizada para interação com modelos de IA
- ![ConsoleClear](https://img.shields.io/badge/ConsoleClear-1.0.0-blue) - Biblioteca personalizada para limpar o console criada por mim
- ![Jackson](https://img.shields.io/badge/Jackson-2.17.2-blue) - Biblioteca para manipulação de JSON
- ![JSON](https://img.shields.io/badge/JSON-20240303-blue) - Biblioteca para manipulação de JSON
- ![Google Auth Library](https://img.shields.io/badge/Google_Auth_Library-1.24.1-blue) - Biblioteca para autenticação OAuth2

## 🏛️ Arquitetura da Aplicação

<p align="center">

<img src="https://i.imgur.com/kKoqGPj.png" alt="Arquitetura">

</p>

## 📐 Padrões e Metodologias Utilizadas

- **SOLID Principles**:

  - **Single Responsibility Principle (SRP)**
  - **Open/Closed Principle (OCP)**: O sistema está projetado para permitir a adição de novas funcionalidades sem modificar o código existente, utilizando extensões de classes e interfaces.
  - **Liskov Substitution Principle (LSP)**: As subclasses podem substituir as classes base sem alterar o comportamento esperado do sistema, garantindo consistência nas implementações.
  - **Interface Segregation Principle (ISP)**: Interfaces são projetadas para serem específicas e focadas, evitando interfaces grandes e abrangentes e permitindo que as classes implementem apenas os métodos necessários.
  - **Dependency Inversion Principle (DIP)**: Dependências são abstraídas por meio de interfaces, em vez de depender diretamente de implementações concretas, facilitando a substituição e o teste das dependências.

- **Dependency Injection** - Para promover a inversão de controle e facilitar a gestão de dependências, permitindo uma arquitetura mais modular e testável.
- **Asynchronous Programming** - Utilizado para melhorar a performance e a escalabilidade da aplicação, especialmente em operações de rede e I/O.
- **Command Pattern** - Utilizado para encapsular todas as informações necessárias para executar uma ação, permitindo o uso de comandos de forma desacoplada e flexível.

## 🌐 Funcionalidades

- **Criar Conversas Interativas** - Permite iniciar e gerenciar conversas com uma interface de linha de comando.
<p align="center"> <img src="https://i.imgur.com/kqlRefx.png" alt="Conversa interativa"> </p>
<p align="center"> <img src="https://i.imgur.com/kqlRefx.png" alt="Conversa interativa"> </p>

- **Salvar Conversas** - Guarda o histórico das conversas para consultas futuras.

<p align="center"> <img src="https://i.imgur.com/X5BSo7p.png" alt="Conversas salvas"> </p>

- **Continuar Conversas** - Retoma conversas de onde pararam.

<p align="center"> <img src="https://i.imgur.com/leqmyAA.png" alt="Continuar Conversas"> </p>
<p align="center"> <img src="https://i.imgur.com/leqmyAA.png" alt="Continuar Conversas"> </p>

- **Importar Chave API** - Necessário para autenticação e uso da aplicação.

<p align="center"> <img src="https://i.imgur.com/b1V91rE.png" alt="Importar Chave API"> </p>

## 📥 Requisitos Mínimos

- **JRE 1.4.0** - Necessário para rodar a aplicação.
- **Internet** - Necessário conexão com a internet.
- **Chave API** - Obrigatória para autenticação e funcionamento da aplicação.

## 🛠️ Instalação

**OBS:** Clique [aqui](https://makersuite.google.com/app/apikey?hl=pt-br) para adquirir a sua chave API de forma gratuita para usar a aplicação.

- É altamente recomendado habilitar o encoding UTF-8 do seu computador. Caso não saiba, clique [aqui](https://cursos.alura.com.br/forum/topico-problemas-de-codificacao-de-caracteres-na-saida-282140).

### Windows

1.  Faça o download da aplicação clicando [aqui](https://github.com/Jorgeluisreis/AskConsole/releases)

2.  Descompacte o arquivo baixado usando o WinRAR ou 7-Zip.

3.  Mova a pasta descompactada para um local do seu computador.

**Observação:** Caso mova para a pasta "Arquivos de Programas", certifique-se de que as permissões de modificação da pasta estão adequadas.

4. Ao abrir a aplicação pela primeira vez, será solicitado um arquivo `.ini` com a chave API. A estrutura do arquivo deve ser:

```text
API=(Sua API Aqui)
```

Por exemplo:

```text
API=AzKj231Azi8AqweKa
```

### Linux

1.  Faça o download da aplicação clicando [aqui](https://github.com/Jorgeluisreis/AskConsole/releases)

2.  Descompacte o arquivo baixado usando o `unzip` ou qualquer outra ferramenta de descompactação de sua preferência.

3.  Mova a pasta descompactada para um local de sua escolha no sistema.

4.  Torne o arquivo executável e execute a aplicação com os seguinte comando

```text
chmod +x AskConsole.jar
```

5. Execute a aplicação

```text
java -jar AskConsole.jar
```

7. Ao abrir a aplicação pela primeira vez, será solicitado um arquivo `.ini` com a chave API. A estrutura do arquivo deve ser:

```text
API=(Sua API Aqui)
```

Por exemplo:

```text
API=AzKj231Azi8AqweKa
```
