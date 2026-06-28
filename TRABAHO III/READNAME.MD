# Sistema de Controle de Permissões e Acessos

Este projeto foi desenvolvido aplicando estritamente POO, SOLID e Object Calisthenics. Não utiliza frameworks externos e roda 100% em memória.

## Como Executar no VS Code (Com Maven)
1. Certifique-se de ter as extensões de Java e Maven instaladas.
2. Abra o terminal na raiz do projeto.
3. Compile o projeto: `mvn clean install`
4. Execute: `mvn exec:java -Dexec.mainClass="br.edu.poo.permissoes.app.Main"`

## Como Executar no VS Code (Sem Maven / Java Puro)
1. Certifique-se de ter o "Extension Pack for Java" instalado.
2. Navegue até o arquivo `src/main/java/br/edu/poo/permissoes/app/Main.java`.
3. Clique em **Run** sobre o método `main`.