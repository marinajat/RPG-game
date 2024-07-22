<h1>The Royal Quest</h1>

<h2>O que é o projeto</h2>

<b> Este é um jogo desenvolvido para a disciplina de Linguagem de Programação Orientada a Objetos do curso de Engenharia da Computação da POLI/UPE</b>
- <b> No reino de Labyrinthos, a princesa foi raptada por um minotauro feroz que a mantém prisioneira em uma torre sombria. O jogador, um misterioso homem de terno e óculos, é convocado pelo rei e pela rainha para salvar a princesa. Ele deve atravessar reino, explorando o mapa, passando pelo vilarejo, floresta, comércio, lago, tempo e ponte, conversando com pessoas para obter pistas e enfrentar monstros para coletar as chaves necessárias para entrar na torre onde a princesa está em apuros.</b>


<h2>Quais tecnologias foram utilizadas no projeto</h2>

- <b>Java:</b> Linguagem de programação principal utilizada para desenvolver a lógica do jogo.
- <b>Java Swing:</b> Biblioteca utilizada para criar a interface gráfica do jogo.
- <b>Eclipse IDE:</b> Ambiente de desenvolvimento integrado utilizado para escrever, compilar e depurar o código.

<h2>Como rodar o projeto</h2>

<h3>Pré-requisitos</h3>

- <b>Java Development Kit (JDK):</b> Certifique-se de ter o JDK instalado. Você pode baixá-lo [aqui](https://www.oracle.com/java/technologies/downloads/?er=221886#javasejdk).
- <b>Eclipse IDE:</b> É recomendado usar o Eclipse IDE para facilitar o desenvolvimento e execução do projeto. Baixe o Eclipse [aqui](https://www.eclipse.org/downloads/).

<h3>Passos para rodar o projeto</h3>

1. <b>Clone o repositório:</b>

- `git clone https://github.com/marinajat/RPG-game.git`

2. <b>Importe o projeto no Eclipse:</b>

-   Abra o Eclipse.
-   Selecione `File > Import`.
-   Escolha `Existing Projects into Workspace` e clique em `Next`.
-   Clique em `Browse` e selecione a pasta onde o projeto foi clonado.
-   Clique em `Finish`.

3. <b>Compile e execute o projeto:</b>

-   No pacote `main`, encontre a classe `Main.java`.
-   Clique com o botão direito em `Main.java` e selecione `Run As > Java Application`.

<h3>Estrutura de pastas do projeto</h3>

- RPG-game/
- └── src/
- ├── main/
- │ └── Main.java
- ├── personagens/
- │ ├── Personagem.java
- │ ├── Guerreiro.java
- │ ├── Mago.java
- │ ├── Arqueiro.java
- │ └── Curandeiro.java
- ├── inimigos/
- │ ├── Inimigo.java
- │ ├── Dragao.java
- │ ├── Esqueleto.java
- │ └── Orc.java
- ├── itens/
- │ ├── Item.java
- │ ├── Espada.java
- │ ├── Pocao.java
- │ └── Armadura.java
- ├── mapa/
- │ ├── Mapa.java
- │ └── Regiao.java
- └── ui/
- └── InterfaceJogo.java

<h2>Sobre o Jogo</h2>

<b>Objetivo Final</b>

O objetivo do jogo é resgatar a princesa, derrotando os monstros que guardam as chaves pelo reino (mundo 1), atravessando a ponte e enfrentando o minotauro na torre (mundo 2).

-   <b>Personagens:</b> Escolha entre Guerreiro, Mago, Arqueiro e Curandeiro, cada um com habilidades únicas e uma progressão de níveis.
-   <b>Combate:</b> Enfrente inimigos em batalhas por turnos usando ataques, habilidades e itens.
-   <b>Exploração:</b> Mova-se pelo mapa, encontre inimigos, descubra tesouros e interaja com o ambiente.
    <b>História:</b> Desfrute de uma narrativa envolvente contada através de diálogos e eventos.

<b>Conclusão</b>

Após coletar todas as chaves e atravessar a ponte derrotando o Vampiro, o jogador entra na torre. No mundo 2, ele é recebido pelo Templário que o orienta a percorrer o labirinto e derrotar o minotauro. Ao derrotar o minotauro, o jogador resgata a princesa e salva o reino de Labyrinthos.