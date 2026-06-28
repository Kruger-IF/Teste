
1. **Descrição geral da solução**

   * Como o sistema foi organizado;
   O sistema foi estruturado seguindo os preceitos da Clean Architecture e Domain-Driven Design (DDD), dividindo a responsabilidade em camadas concêntricas onde a regra de negócio central não depende de detalhes de infraestrutura.

   * Quais são os principais módulos, pacotes ou camadas;
   domain: Coração do sistema. Contém as entidades (Usuario), objetos de valor (Records), e coleções de primeira classe. Foi subdividido em subdomínios: access, audit, permission, role e user.
   application: Orquestra os casos de uso. Contém os pacotes service (ex: AutorizacaoServicePadrao) e policy (ex: PoliticaPorPermissaoDireta).
   infrastructure: Contém os detalhes técnicos, especificamente o pacote repository com as implementações em memória.
   app: Camada de entrada que contém a classe Main, atuando como contêiner de Injeção de Dependências.
   shared: Pacote compartilhado contendo exceções transversais (exception).

   * Qual foi a lógica geral adotada para representar usuários, papéis, permissões e políticas de acesso.
   Um Usuario possui um ConjuntoDePapeis. Cada Papel está vinculado a múltiplas Permissao. Durante a autorização, um ExtratorDePermissoes consolida todas as permissões do usuário, e a PoliticaAcesso cruza essas permissões com a Acao solicitada, negando imediatamente se o usuário estiver bloqueado.

2. **Justificativa do uso de herança**

   * Onde a herança foi utilizada;
   Onde a herança foi utilizada: Na modelagem da entidade principal de usuário. Criamos a classe base abstrata Usuario, que é estendida pelas classes finais Administrador, UsuarioComum e UsuarioSistema.

   * Por que a herança foi escolhida nesses pontos;
   Por que a herança foi escolhida nesses pontos: Para representar uma verdadeira relação de especialização ("É-UM" / IS-A). Um Administrador é um Usuário do sistema. A herança aqui define a natureza imutável e a origem do ator.

   * Que relação de especialização existe entre as classes;
   Diferencia entidades humanas com privilégios inerentes (Administrador, UsuarioComum) de entidades não-humanas (UsuarioSistema, que age como uma máquina/API).
   * Quais alternativas foram consideradas, como composição ou interfaces;
   Foi considerado o uso de flags booleanas (isAdministrador) ou um Enum de tipo. No entanto, isso forçaria o uso excessivo de if/else no domínio, ferindo o princípio OCP (Aberto/Fechado).

   * Quais riscos ou limitações essa escolha pode gerar.
   A herança pode causar o problema da "classe base frágil" e forte acoplamento. Para mitigar isso, mantivemos a árvore de herança com profundidade máxima de 1 nível e evitamos herança para comportamentos dinâmicos (não criamos UsuarioBloqueado, que é tratado via estado/composição).

3. **Justificativa do uso de interfaces**

   * Quais interfaces foram criadas;
   Foram criadas interfaces para todos os repositórios (UsuarioRepository, PapelRepository, etc.), para os serviços (AutorizacaoService, AuditoriaService) e para as regras lógicas (PoliticaAcesso).

   * Que contratos elas representam;
   Representam o que deve ser executado, escondendo completamente o como. O contrato define entradas e saídas esperadas de negócio.

   * Como elas contribuem para baixo acoplamento;
   A camada de application não sabe se os dados estão salvos num HashMap, num arquivo ou num SGBD. Ela apenas confia na interface.

   * Como elas permitem trocar implementações sem alterar o domínio;
   Basta criar uma nova classe que implemente a interface (ex: UsuarioRepositoryPostgres) e injetá-la no Main. Nenhuma regra de negócio será afetada.

   * Exemplos de classes que dependem de abstrações em vez de implementações concretas.
   A classe AutorizacaoServicePadrao recebe em seu construtor UsuarioRepository e PoliticaAcesso. Ela depende inteiramente de abstrações.

4. **Uso de composição**

   * Onde a composição foi utilizada;
   Na relação do Usuario com seus papéis, gerenciada através do agrupador ControleDeAcesso e da classe ConjuntoDePapeis.

   * Por que composição foi preferida em vez de herança;
   Se usássemos herança para papéis, sofreríamos uma explosão combinatória (ex: precisaríamos criar a classe UsuarioGerenteAuditor). Composição permite atribuir e revogar múltiplos papéis em tempo de execução de forma dinâmica.

   * Como os objetos colaboram entre si;
   O Usuario delega a lógica de bloqueio e papéis para o objeto ControleDeAcesso, que por sua vez utiliza o ConjuntoDePapeis para manter a integridade da lista.

   * Como a composição ajudou a reduzir acoplamento e duplicação.
   Centralizou a lógica de "adicionar/remover/listar papel" em uma única classe (ConjuntoDePapeis), evitando que essa lógica se espalhasse por serviços ou pelas subclasses de usuários.

5. **Aplicação dos princípios SOLID**

   * Exemplos concretos de aplicação do Single Responsibility Principle;
   A classe ExtratorDePermissoes tem apenas um motivo para mudar: a forma de buscar permissões. Ela não avalia acessos, apenas busca. O log foi delegado inteiramente para AuditoriaServicePadrao.

   * Exemplos concretos de aplicação do Open/Closed Principle;
   A interface PoliticaAcesso permite criar novas regras (ex: PoliticaHorarioComercial) estendendo o sistema, sem modificar a lógica original de AutorizacaoServicePadrao.

   * Exemplos concretos de aplicação do Liskov Substitution Principle;
   Nos cenários do Main, objetos Administrador e UsuarioComum são passados de forma transparente para métodos que exigem o tipo base Usuario, sem quebrar a aplicação.

   * Exemplos concretos de aplicação do Interface Segregation Principle;
   As interfaces de repositório foram segregadas. O PermissaoRepository tem apenas métodos focados em permissão. Não criamos um "RepositorioGlobal" inchado.

   * Exemplos concretos de aplicação do Dependency Inversion Principle;
   Os serviços de alto nível (UsuarioServicePadrao) exigem abstrações (UsuarioRepository) em seus construtores, cujas implementações concretas (UsuarioRepositoryEmMemoria) são fornecidas externamente.

   * Pontos em que algum princípio não foi plenamente aplicado e por quê.
   A classe Main acumula responsabilidades (cria os dados, orquestra os cenários, injeta dependências). Isso ocorreu de forma intencional porque atuou como o Composition Root (raiz de composição) em um sistema sem frameworks como o Spring.

6. **Aplicação de Object Calisthenics**

   * Quais regras foram aplicadas com mais rigor;
   - Encapsular tipos primitivos: Nenhuma String voa solta. Criamos Records para IdUsuario, Email, Acao e Motivo.
   Não usar else: A classe PoliticaPorPermissaoDireta utiliza estritamente Guard Clauses (retornos antecipados).
   Coleções de primeira classe: O Set<IdPapel> nunca é exposto nativamente; é inteiramente gerenciado por ConjuntoDePapeis.

   * Quais regras foram difíceis de aplicar;
   A regra "Máximo de 2 atributos por classe" foi a mais desafiadora. A classe Usuario precisaria ter ID, Nome, Email, Bloqueado e Papéis.

   * Exemplos de refatorações feitas para reduzir `if/else`, métodos longos ou classes grandes;
   Para resolver o problema dos 2 atributos, refatoramos a classe Usuario, agrupando Nome/Email/ID no objeto IdentificacaoUsuario e o restante em ControleDeAcesso. Para evitar ferir a regra "Um ponto por linha", criamos a classe ExtratorDePermissoes em vez de fazer encadeamentos longos de Stream API.

   * Exemplos de objetos de valor, coleções de primeira classe ou encapsulamento de primitivas;
   A classe Main viola intencionalmente o limite de atributos, pois, conforme explicado, necessita instanciar todos os repositórios e serviços do sistema para a demonstração via console.

   * Pontos em que os alunos decidiram não aplicar determinada regra e a justificativa técnica.

7. **Tratamento de erros e validações**

   * Quais validações foram implementadas;
   Quais validações foram implementadas: A PoliticaAcesso implementa a validação estrutural fail-fast, verificando imediatamente se o usuário está bloqueado antes de alocar memória para extrair permissões.

   * Que exceções ou estratégias de erro foram usadas;
   Lançamos a exceção customizada DominioException em casos de violação de estado (ex: buscar um usuário para bloquear que não existe). Em vez de retornar boolean na autorização, adotamos a estratégia de Retorno Rico: o Record chamado DecisaoAcesso, que obriga o retorno de um status atrelado a um Motivo em texto.

   * Como o sistema evita permissões duplicadas, usuários inválidos ou papéis inexistentes;
   A unicidade foi delegada matematicamente às estruturas de dados nativas do Java. O ConjuntoDePapeis e os repositórios utilizam Map e Set, que sobrescrevem chaves iguais e ignoram valores duplicados automaticamente.

8. **Discussão crítica**

   * Quais foram as decisões mais difíceis;
   Equilibrar a complexidade arquitetural com o escopo de um projeto puramente no console. O rigor do Object Calisthenics aumentou o número de arquivos (especialmente Records agrupadores) e tornou a injeção manual no Main bastante verbosa.

   * Quais partes do código ficaram melhores do ponto de vista orientado a objetos;
   A camada de Domínio (domain) ficou incrivelmente expressiva. O encapsulamento de primitivos trouxe enorme segurança em tempo de compilação (é impossível passar um IdPapel num método que espera um IdPermissao, algo que seria comum se ambos fossem Strings).

   * Quais partes ainda poderiam ser melhoradas;
   A geração de Identificadores. Atualmente, os IDs são injetados manualmente nas classes. Isso poderia ser melhorado adotando o padrão Factory ou gerando UUIDs automaticamente dentro das entidades.
   
   * O que seria alterado se o sistema precisasse evoluir para um banco de dados real;
   Devido à arquitetura implementada, nenhuma regra de negócio ou caso de uso precisaria ser alterado. Bastaria criar novas classes (ex: UsuarioRepositoryJDBC ou JPA) dentro do pacote infrastructure/repository, que implementam as interfaces já existentes. Feito isso, atualizaríamos a injeção na classe Main. O isolamento do domínio foi um sucesso absoluto.
 
