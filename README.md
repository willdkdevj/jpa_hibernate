# Aplicação do JPA em um Projeto Maven
Utilização da especificação JPA com o framework Hibernate para utilização de persistência em um projeto Maven Java

[![Maven Badge](https://img.shields.io/badge/-Maven-black?style=flat-square&logo=Apache-Maven&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)
[![JPA Badge](https://img.shields.io/badge/-JPA-blue?style=flat-square&logo=GitHub&logoColor=white&link=https://docs.jboss.org/author/display/AS71/JPA%20Reference%20Guide.html)](https://docs.jboss.org/author/display/AS71/JPA%20Reference%20Guide.html)
[![Hibernate Badge](https://img.shields.io/badge/-Hibernate-green?style=flat-square&logo=Hibernate&logoColor=white&link=https://docs.jboss.org/hibernate/orm/current/quickstart/html_single/)](https://docs.jboss.org/hibernate/orm/current/quickstart/html_single/)

## Por que utilizar a especificação JPA
O JDBC surgiu para fazer o papel de interpretador entre os bancos de dados e as aplicações desenvolvidas em Java. Mas existe alguns fatores em sua estrutura que complica sua manutenção e operabilidade nos projetos que são o **alto acoplamento** com banco de dados, onde qualquer mudança no banco de dados gera um impacto enorme na aplicação, além disso, o segundo fator problemático desta implmentação é que sua estrutura é **muito verbosa**, o que ocasiona a escrita de muito código para torná-la efetiva ao projeto.

<img align="right" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/JPAHibernate.jpg">

Este cenário que motivou a comunidade Java a elaborar uma solução que permitisse uma forma de comunicação das aplicações com os bancos de dados com as aplicações que eram desenvolvidas, mas que não fossem trabalhosa a mudanças de base de dados refletidas na aplicação, assim como, até mesmo a mudança da estrutura de um banco de dados, como a substituição de um *MySQL* para um *Oracle*, por exemplo.
Além disso, a ideia principal era não ter tanto esforço para implementar este tipo de camada de comunicação interpretativa entre eles, desta forma, surgiu a especificação JPA, mas antes surgiu o framework Hibernate.

## O framework Hibernate
O *Hibernate* foi lançado em 2001 (criado por Gavin King) que foi desenvolvido justamente para simplificar o uso do **JDBC**, ela é uma biblioteca que surgiu no mercado como *Open Source* que agora recebe a tutela da **JBoss - Red Hat** que ganhou vários adeptos por sua facilidade de implementação.

## A Especificação JPA
Como o *Hibernate* ficou muito popular nos projetoso Java, também surgiram concorrentes prometendo a mesma facilidade ou até melhor no mercado, gerando um problema de compatibilidade entre a implementação de uma biblioteca e a decisão de substituí-la por outra de mercado, impactando consideravelmente o código.
Posteriormente foi padronizada uma estrutura de bibliotea com o objetivo de criar um modelo de persistência, conhecida como *Object Ralational Mapping - ORM*, para realizar o mapeamento entre a *orientação a objetos* e os *relacionamentos* dos bancos de dados, sendo gerado a base da *Java Persistence API*, que é uma especificação para padronizar este mapeamento a objetos relacionais.

## Hibernate + JPA
O **Hibernate**,  assim como os demais frameworks de mercado, se tornaram uma implementação, na qual as mesmas para permanecerem atuantes no mercado devem seguir a especificação JPA, para permitir que não sejam fortemente acopladas a projetos Java. Em 2010 foi lançada a versão 3.5.0 do Hibernate que era compatível com a versão 2.0 da JPA, isto significa que basta trocar seu jar e seus importes para permitir a substituição dos mesmos sem ocasionar impactos relevantes aos projetos.

<img align="middle" width="250" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/CqODs.png">

Para trabalharmos com a JPA, tecomo que escolher um framework para implementa-la, não dá para utilizar a JPA "pura" porque ela é só a "casca", ou seja, uma abstração.

## Projeto Maven e suas dependências
O **Maven** é uma ferramenta desenvolvida pela *Apache*, ela serve para gerenciar as dependências e automatizar seus *builds*. sendo o arquivo **pom.xml** o responsável por aplicar suas configurações ao projeto.
Desta forma, para implementarmos o Hibernate ao projeto devemos explicitar sua dependência utilizando as tags abaixo.
```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>
    </dependency>
</dependencies>
```
> OBS: Além disso, devemos informar a versão que desejamos implementar, para este projeto foi utilizada a **versão 5.6.14.Final** mas nada o impede de implementar uma versão mais recente.

Ao analisar o **"External Libraries"**, você perceberá que ele baixou uma série de dependências e adicionou ao projeto. Dentre elas, temos o **"hibernate-entitymanager"**, e que, por sua vez, depende de todas as outras dependências, como o *"hibernate-core"*, *"hibernate-commons-anotations"* e o *"javax.persistence-api-2.2.jar"*, que é a especificação **JPA** em si.

> OBS: Para realização do projeto foi também implementado utilizado a dependencia do **H2 Database**, mas nada impede de implementar outro framework de base de dados para o projeto

### O Arquivo Persistence (XML)
As configurações da JPA estão presentes no arquivo **persistence.xml**, na qual é possível configurar através do Java, mas fica explicitado no modo de arquivo.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

</persistence>

```
A **tag** de principal importancia para a configuração da JPA é a tag **persistence-unit**, na qual é uma tag *obrigatória*, que é onde definimos os *namespaces*. Nela temos dois parâmetros, que são o *name*, na qual podemos nomear como desejado para depois apontar qual configuração desejamos utilizar, e o *transaction-type* que define a estratégia a ser utilizada para implementar a configuração, na qual possuí dois valores, o **JTA** que é adequada quando estamos utilizando um servidor de aplicações (EJB, JMS, JEE) onde o servidor se encarrega de cuidar das transações, e o **RESOURCE-LOCAL** para aplicações locais (*stand-alone*) que não possuem um servidor de aplicações, sendo seu processamento realizados na mesma instância.
```xml
<persistence-unit name="jpa_implement" transaction-type="RESOURCE_LOCAL">

</persistence-unit>

```

A persistence-unit representa uma unidade de persistência, isso significa que, que ela será a representação do banco de dados que desejamos conectar. Desta forma, poderiamos ter mais tag's persistence-unit com outras configurações representando outras configurações de acesso a bancos, mas no momento de iniciar a implementação do framework devemos apontar somente uma configuração ativa que definirá o banco a ser acessado.
> OBS: No nosso caso, foi apelidado o nome de **jpa_implement** mas nada impediria de colocar um nome que definisse com mais exatidão o banco de dados ou base de dados que se deseja relacionar.

Na estrutura da tag **persistene-unit** adicionamos quais são as propriedades a serem aplicadas no contexto da JPA. Desta maneira, implementamos em sua estrutura a tag ***properties***, e em seu contexto, aplicamos cada propriedade que definirá a atuação no cenário JPA, aplicadas com a tag ***property*** na qual tem como parãmetros um nome (*name*) e o valor (*value*).
```xml
    <persistence-unit name="jpa_implement" transaction-type="RESOURCE_LOCAL">
        <properties>
                <property name="" value=""/>
            </properties>
    </persistence-unit>
 </persistence>

```

As propriedades da JPA também tem caracterisiticas obrigatórias de exposição. Isto significa que, é necessário informar as caracteristicas do banco de dados a ser utilizado pela implementação. A propriedade *name* possui um nome especifico atribuido ao banco de dados escolhido que precisa ser informado, assim como, o parâmetro *value* relacionado ao valor, que definem a definição do driver de conexão com o banco de dados. Aqui no projeto, como selecionamos o banco de dados *H2* as definições ficaram da seguinte forma.
```xml
    <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
```
> OBS:  O driver mudará de acordo com o banco de dados escolhido, desta forma, caso fosse o **MySQL**, o driver seria ***com.mysql.driver***, se fosse **PostgreSQL**, seria ***org.postgresql.driver*** e assim por diante. 

A JPA é uma abstração do framework do JDBC que simplifica o seu uso, mas que aplica todas as suas interações para realizar esta interpretação da camada orientada a objetos com o modelo relacional.

Além do driver, é necessário configurar a JPA indicando qual é o caminho (URL) do banco de dados, isto é, onde está o endereço de conexão com o banco de dados. Esse endereço também varia de acordo com o banco de dados. 
```xml
    <property name="javax.persistence.jdbc.url" value="jdbc:h2:mem:project_jpa"/>

```
> OBS: No caso do H2, será "jdbc:h2:mem:project_jpa". Isto é, queremos que o database no H2 se chame *Project JPA*. 

Todo banco de dados possui suas definições de usuário e senha. Portanto, teremos mais duas propriedades que precisamos definir para configuração da JPA, que são , a *"jdbc.user"*, e a *"jdbc.password"*. 
```xml
    <property name="javax.persistence.jdbc.user" value="sa"/>
    <property name="javax.persistence.jdbc.password" value=""/>
```
> OBS: No caso do H2, será "sa" (geralmente usamos esse usuário no H2), e a senha ficará em branco value"".


Outra propriedade importante para esclarecer para JPA o dialeto, quer dizer, como a aplicação conversará com o banco de dados, é a propriedade ***"hibernate.dialect"***. O Hibernate precisa saber qual é a classe que tem o dialeto do banco de dados, na qual esclarece as particularidades do banco de dados. Como cada banco de dados pode ter as suas particularidades, o dialeto é o que fará a comunicação correta com o banco de dados. 
```xml
    <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
```
> OBS: No caso do H2, será "org.hibernate.dialect.H2Dialect". 

### Entidades (Entity)
Temos a configuração da persistência realizada, agora precisamos criar os objetos estarão imcubidos de manusear as informações a serem passadas e recebidas pelo banco de dados. Para isso, criamos um objeto que representará uma tabela no banco de dados e atribuimos a ela algumas anotações da JPA a fim de mostra-la o que cada atributo representa. Na JPA, isso será feito por uma classe Java, que na JPA é chamda de ***Entidade***. Como exemplo, temos a classe *Produto* que represtará a tabela *produtos* na database.
```java
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String nome;
    @Column(name = "description")
    private String descricao;
    @Column(name = "price")
    private BigDecimal preco;
    @Column(name = "createdAt")
    private LocalDate dataCriacao;
    @Enumerated(EnumType.STRING)
    private TipoProduto tipo;

    /* O construtor padrão deve sempre estar presente em transações JPA/Hibernate */
    public Produto() {
    }

    public Produto(String nome, String descricao, BigDecimal preco, TipoProduto tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.dataCriacao = LocalDate.now();
        this.tipo = tipo;
    }

    /* Getter e Setter - caso necessários *?
```
> OBS: Toda classe que representará uma tabela **é obrigatória a presença do construtor padrão.**

Se faz necessário mapear todas as tabelas no banco de dados por uma entidade, que nada mais é do que uma classe Java. Desta forma, o que precisamos entender é que a JPA não é uma especificação para um ORM, pois com a ORM somente é feito o mapeamento objeto-relacional. A JPA é muito mais do que somente o relacionamento, sendo que a partir da JPA 2.0 utilizamos **anotações** para definir regras para este mapeamento.

Sobre as anotações presentes na classe:
*   **Entity** - Ela informa a JPA que esta classe representa uma Entidade de Banco de Dados;
*   **Table** - Permite definir a qual tabela este objeto está atrelado ao informar o nome da tabela no banco de dados, através do parâmetro *name*;
*   **Id** - Permite definir qual é o atributo que representa a *Primary Key* da tabela no objeto;
*   **GeneratedValue** - informa a JPA quem será responsável por administrar a geração de chaves;
*   **Column** - Define o nome da coluna de uma tabela para o atributo da classe;
*   **Enumerated** - Define a estratégia a ser adotada para o tipo **ENUM**, na qual por padrão é utilizada pelo Hibernate a estratégia ***ORDINAL***, que define um numeral para o atributo estático enum conforme a ordem de seu cadastro, o segundo é o ***STRING***, que assume que o nome de foi dado ao atributo estático é o que deve ser inserido como valor ao banco de dados.

Pela JPA, deveríamos passar todas as classes/entidades do nosso projeto, ou seja, passaríamos o caminho completo da classe no arquivo persistence.xml através da **tag class**, porém, ao utilizar o Hibernate, não é necessário adicionar a tag, isso porque o framework consegue encontrar automaticamente as classes/entidades do projeto. Essa é uma particularidade do Hibernate, pode ser que as outras implementações não façam isso e, portanto, se faz necessário esta inclusão. 

### Entity Manager
No **JDBC**, toda a integração com o banco de dados era feita com a classe ***Connection***, na qual faz a abertura de comunicação com o banco de dados. 
Na **JPA**, existe algo similar, mas diferente de uma abertura de conexão utilizando uma instância é utilizado uma interface que realiza a conexão do Java ao banco de dados, esta interface é nomeada ***EntityManager***. Ela funciona como um administrador, o *"manager"* das entidades, ou ainda, o gestor das entidades. Desta forma, qualquer operação CRUD (Create, Read, Update e Delete) que são as operações que são realizadas para manusear a database é gerenciada por meio da JPA.

Para criar o *EntityManager*, é necessário uma classe auxiliar que fabrica esta *manager*, o ***EntityManagerFactory***. Para obter uma instância desta fabrica é necessário invocar a  do **Persistence** (JPA), na qual possuí o método estático chamado ***CreateEntityManagerFactory***.
```java
    public class FactoryEntity {
        /* Nome de Unidade de Persistência (persistence-unit) definido no persistence.xml */
        private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("jpa_implement");

        public static EntityManager builderEntityManager(){
            return FACTORY.createEntityManager();

        }
    }
```
Note que o método recebeu um parâmetro do tipo *String*, na qual é o nome fornecido para a configuração do JPA, neste caso, a unidade de persistência (***persistence-unit***) na qual é fornecido um nome, que é o parâmetro *name* do arquivo persistence.xml.

Também foi criada uma classe auxiliar nomeada como *FactoryEntity* para obter a figura de fabrica, pois já que não podemos criar várias instâncias da *EntityManager*, na qual lançará mais uma exceção caso tenha mais de uma instância.

Com o EntityManager criado, criaremos uma instância do objeto Produto, e passa-lo para o banco, para inserirmos um registro a uma tabela no banco de dados utilizamos o comando INSERT. Para isso, no EntityManager temos o métod persist() que implementa este comando seguindo a estrutura abaixo.
```java
    EntityManager em = factory.createEntityManager();

    em.getTransaction().begin();
    em.persist(produto);
    em.getTransaction().commit();
    em.close();

```

É o Hibernate que realiza o comando *insert* automaticamente baseado nas configurações da entidade através das anotações. Note que não montamos nenhuma linha de **comando em SQL**.

No persistence.xml, na tag persistence-unit, além do parâmetro *name*, há o parâmetro *transaction-type*, que no caso, aplicamos o valor **"RESOURCE_LOCAL"**, ou seja, não temos o controle de transação automático. Desta forma, foi necessário delimitar onde começa e encerra uma transação para que seja entendido o escopo. Para isso, utilizamos o método getTransaction() para obter uma instância de transação e os métodos begin() para iniciar e o método close() para encerrar o contexto transacional.

No nosso caso, o persist() é o método que realiza a interpretação do comando *insert* para ser encaminhado ao banco de dados. Já para garantir que este processo sejá efetivado, é necessário confirma-lo utilizando o método **commit()**.

Outro fator também para conseguir realizar esta transação é já existir, do outro lado, no caso, no lado do banco de dados, já criada a Database e as tabelas para realizar esta transação. Desta forma, foi adicionada a propriedade (*property*) para que o Hibernate gere os comandos SQL para criar a database automaticamente e suas respectivas tabelas conforme as configurações aplicadas as classes mapeadas como entidades.
```xml
    <property name="hibernate.hbm2ddl.auto" value="update"/>
```

Os valores que podemos informar a este parâmetro são:
*   ***create*** - Tem a função de criar o banco de dados, na qual será apagado tudo e criar todas as tabelas. Após sua criação e iniciada pela a aplicação não será possível mais apagar as tabelas;
*   ***create-drop*** - Tem a função de criar as tabelas quando é iniciada a aplicação, onde ao finaliza-la será excluída a base de dados criada;
*   ***update*** - Tem a função de criar a database e suas respectivas tabelas, se elas não existirem, caso já existirem e tiverem alterações, atualize o que mudou;
*   ***validate*** - Tem a função de não modificar a database já criada, apenas validar se está tudo funcional e cria uma estrutura de *log's*.

Para visulizar os comandos SQL gerados pelo **Hibernate** de modo formatado para facilitar sua análise, inserimos estas propriedades as configurações da persistência (persistence-unit) habilitado.
```xml
    <property name="hibernate.show_sql" value="true"/>
    <property name="hibernate.format_sql" value="true"/>
```

<img align="middle" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/saida_hibernate.png">

### Transaction (Camada DAO)
Vimos que para o EntityManager funcionar ele deve estar com um **Contexto Transacional** iniciado, este contexto mostra ao Hibernate o que deve ser analisado a fim de ser aplicado para transação relacional entre a aplicação e o banco de dados, criando um escopo de comandos a serem invocados para permitir a transação que desejamos. Seja ela para inserir, consultar, atualizar ou excluir um dado na base de dados.

Desta forma, existe um padrão de projeto que nos auxilia na organização de classes que possuí este fim de agrupar estas classes de interface chamada de **Data Access Object (DAO)**, então, foi criado o pacote *dao* com a classe *ProdutoDao* a fim de retirarmos estes comandos de nossa aplicação os abstraindo em um classe que possui a única responsabilidade de realiza-los conforme o contexto para a qual foi invocada.
```java
public class ProdutoDao {

    private EntityManager entity;

    public ProdutoDao(EntityManager entity) {
        this.entity = entity;
    }

    public void cadastrar(Produto produto) {
        try{
            /* Inicia escopo transacional */
            entity.getTransaction().begin();

            /* Invoca instância de persistência (INSERT) */
            this.entity.persist(produto);
            
            /* Garante que o esopo de persistência será executado */
            entity.getTransaction().commit();
       } catch (Exception ex) {
            /*
             *  Caso ocorra algum probleam na esecução é revertido o escopo para antes da abertura de escopo
             *  Retornando para seu estado de origem.
             */
            entity.getTransaction().rollback();
            throw new ErrorJPAException("Não foi possível concluir a transação" + ex.getMessage());
        } finally {
            /* Encerra escopo transacional */
            entity.close();
        }
    }
}
```

No código, quando precisar inserir um produto na tabela que a representa em nossa aplicação, será necessário passar o EntityManager para iniciar o contexto transacional ao invocar o método **cadastrar**. Note que quem invoca o método não sabe como é realizada a persistência, pois toda esta responsabilidade está agora com a classe ProdutoDao, mas ela só deve fornecer o EntityManager que também é fornecido pelo método builerEntityManager, construído para este fim.
```java
    ProdutoDao dao = new ProdutoDao(FactoryEntity.builderEntityManager());
    dao.cadastrar(celular);
```

## Relacionamentos Entre Tabelas (Mapeamento)
Agora foi solicitado para gente mapear uma nova entidade que foi inserida ao banco de dados, que além de sua criação, ela tem uma relação de cardinalidade com a entidade Produto, conforme ilustra a imagem abaixo.

<img align="middle" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/cardinalidade.png">

É necessário criar uma classe Categoria implementando os atributos correspondente a solicitação e também implementar as anotações JPA para informarmos ao Hibernate o mapeamento para sua criação na database definida no persistence.xml
```java
@Entity
@Table(name = "categorias")
public class Cateogoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String nome;

    public Cateogoria() {
    }

    public Cateogoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
```

Depois é necessário alterar a classe **Produto**, para implementar um novo atributo do tipo *Categoria*, mas nela será necessário algumas implementações especiais para criar esta relacionamento entre elas. Vejamos as anotações aplicadas.
```java
    @ManyToOne
    private Cateogoria cateogoria;
```
 Na JPA, para informarmos que a cardinalidade desse relacionamento é **"muitos para um (*-1)"**, é aplicado a anotação **@ManyToOne**. Ou seja, muitos produtos estão vinculados com uma Categoria. Uma categoria pode ter vários produtos, mas o produto tem uma única categoria, no caso, o relacionamento de **"um para um"**. A escolha dependerá da cardinalidade, do tipo de relacionamento entre as tabelas.

Existem algumas anotações da JPA para definições de relacionamento, são elas:
*   **@OneToMany** - Um para Muitos (1-*) na qual define que um elemento pode ter vários elementos associados;
*   **@OneToOne** - Um para Um (1-1) na qual define que um elemento só pode ser associado a um elemento;
*   **@MayToMany** - Muitos para Muitos (*-*) na qual define que muitos elementos estão associados a muitos outros elementos de outra tabela. 

<img align="middle" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/relacionamento_hibernate.png">

Note que o Hibernate realizou as tratativas necessárias para adequar as estrutura de classe que foram mapeadas como entidades no projeto. Onde podemos ver a inclusão da nova entidade e a alteração da entidade existente.

Outro **ponto de extrema importância** é a ordem que as entidades são persistidas no banco de dados, pois caso tente informar uma instância na qual ainda não tenha sido persistida será lançada a exceção ***Transient Property Value Exception***, que significa que um dos valores apresentados para ser persistido não está no estado *transient*.

Desta forma, é necessário se atendar para ordenar a persistência na ordem que as entidades que necessitam ser persistidas antes de serem referenciadas por outras entidades que fazem relacionamento. Como no nosso exemplo, que a *Categoria* faz parte da estrutura de *Produto*, na qual é um atributo da mesma, assim, é persistida a entidade *categoria* primeiro para depois menciona-la na entidade *Produto*.
```java
    public static void main(String[] args) {
        Cateogoria celulares = new Cateogoria("Celulares");
        Produto produto = new Produto("Xiaomi Redmi", "Muito Bom", new BigDecimal(1300), TipoProduto.ELETRONICOS, celulares);

        CategoriaDao cateogoriaDao = new CategoriaDao(FactoryEntity.builderEntityManager());
        cateogoriaDao.cadastrar(celulares);
        ProdutoDao produtoDao = new ProdutoDao(FactoryEntity.builderEntityManager());
        produtoDao.cadastrar(produto);

    }
```

## Ciclo de Vida das Entidades
Sempre que trabalhamos com as entidades (*entity*), quando instanciamos um objeto e invocamos os métodos do *EntityManager*, ao iniciarem no contexto da transação, elas ficam trafegando entre alguns estados no ciclo de vida, além de entender as possíveis exceções que podem ser lançadas.

<img align="middle" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/ciclodevida.png">

Sobre o ciclo de vida de uma entidade, conforme a especificação da JPA, quando instanciamos uma entidade, ela é classificada em um estado chamado de ***TRANSIENT***. Este estado é aplicado para uma entidade que nunca foi persistida, ou seja, não foi gravada no banco de dados até o momento, não possuindo um id, consequentemente, não gerenciada pela JPA. Esse é o primeiro estado de uma entidade.

A entidade só passa a ser observada pelo contexto da transação quando ela é atribuída ao método *persist()* do EntityManager, esta forma ela sai do estado *Transient* para o estado ***MANAGED***, ou seja, gerenciada. O *Managed* é o principal estado que uma entidade pode estar, pois tudo que ocorrer com ela nesse estado, a JPA observará e poderá sincronizar com o banco de dados.

> OBS: Quando temos entidades no estado *MANAGED* é possível sincroniza-las ao banco de dados a fim de obter seu *ID* ou vinculá-las através de relacionamento, este processo se dá ao commitarmos a transação, ao chamar o método *commit()*, ou, se não finalizarmos a transação, mas se tentarmos sincronizar o estado dessa entidade com o banco de dados, através do método *flush()* no EntityManager, onde não se deseja commitar a transação, pois ainda serão realizadas algumas operações.

A partir do momento que o EntityManager é encerrado através do método *close()*, ou através da retirada das entidades do contexto de persistência através do método *clear()*, as entidades mudam de estado. Este estado é chamado de ***DETACHED***, que na tradução livre seria como destacado.Quer dizer que o EntityManager não está mais as observando para replicar suas mudanças ao banco de dados, desta forma, tudo que for modificado não será alterado.

> OBS: Há outro método chamado *merge()* que tem como objetivo receber uma entidade que está no estado **detached** e devolve-la ao estado **managed**. Este método não muda o estado da entidade passada como parâmetro para managed, no caso, ele devolve uma nova referência de entidade no estado managed. A que foi passada como parâmetro, contintuará como detached. 

Para excluir uma entidade gerenciada (*managed*) existe o método *remove()* do EntityManager, passando esta entidade para o  estado ***REMOVED***. Quando o commit() ou o flush() for chamado, será sincronizado o estado *remove* com o banco de dados, na qual executará o comando **delete**.

## Consultas (JPQL)
O JPQL significa *Java Persistence Query Language* sua função é criar consultas em entidades persistidas em um banco de dados relacional. Sua sintaxe foi desenvolvida com base na linguagem *SQL*. Ele retorna um objeto entidade, ao invés de um campo resultado, a partir de um banco de dados, como é no SQL convencional. 

### Estrutura de Consulta
A estrutura é muito similar a linguagem SQL, mas ao invés de utilizarmos o asterísco (*) para informar que queremos o retorno de todas as colunas, utilizamos um *alias* que é o mesmo aplicado ao nomear a entidade, sendo este outra distinção do JPQL do SQL. Aqui informamos o nome da entidade na nossa aplicação, e não a tabela de banco de dados, neste nosso exemplo, estamos invocando a entidade Produto e não a tabela produtos.
```java
    public List<Produto> buscarTodos(){
        /*
         * Ao invés do nome da tabela do banco de dados é informado o nome da entidade (Neste caso é Produto)
         * Tambén não é informado o * para retornar todos é informado um alias (Neste caso é a letra p)
         * */
        String jpql = "SELECT p FROM Produto p";
        return this.entity.createQuery(jpql).getResultList();
    }

```
<img align="middle" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/buscarTodos.png">

Podemos realizar a consulta de uma entidade a partir de um atributo especifico da mesma. Para isso, precisamos utilizar utilizar a cláusula **WHERE** junto do alias fornecido para acessar seu atributo e é possível utilizar duas abordagens para realizar a passagem de parâmetro:
*   **Parâmetro Nomeado** - é digitado o caracter de dois pontos (:) e depois um alias onde este também é fornecido para o método setParameter;
*   **Parâmetro Posicional** - é digitado o caracter de interrogação (?) e o numeral correspondente a sua posição, na qual é também fornecido para o método setParameter.
```java
    public Produto buscarPorNome(String nome){
        /*
         * É possível utilizar como parâmetro o Parâmetro Nomeado, onde informamos dois pontos e depois um alias a ser passado no setParameter
         * Também é possivel utilizar o Parâmetro Posicional (que é o que está aplicado abaixo) que informamos uma interrogação e a posição
         * dela a ser aplicada no setParametro
         * */
        String jpql = "SELECT p FROM Produto p WHERE p.nome = ?1";
        return this.entity.createQuery(jpql, Produto.class)
                .setParameter(1, nome)
                .getSingleResult();
    }
```
<img align="middle" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/buscarPorNome.png">

Em entidades que se relacionam com outras entidades, também é possível obter retorno passando como parâmetro do filtro a entidade relacionada por intermédio de seus atributos. Na qual o próprio JPA identifica este relacionamento e implementa a consulta inserindo os *Joins* necessários.
```java
    public List buscarPorCategoria(String nome){
        /*
         * Ao invés do nome da tabela do banco de dados é informado o nome da entidade (Neste caso é Produto)
         * Tambén não é informado o * para retornar todos é informado um alias (Neste caso é a letra p)
         * */
        String jpql = "SELECT p FROM Produto p WHERE p.cateogoria.nome = :nomeCategoria";
        return this.entity.createQuery(jpql)
                .setParameter("nomeCategoria", nome)
                .getResultList();
    }
```
<img align="middle" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/buscarPorCategoria.png">

Note que utilizamos para obter uma lista de produtos a partir do nome da categoria que a entidade Produto possui. Desta forma o JPA, implementa a consulta com todos os parâmetros de Join e mapeamento com chaves para que seja montada e retornada pelo Hibernate.

Também é possível retornar somente um atributo de uma entidade a ser apresentado pelo retorno da consulta JPQL, para isso passamos o seu nome ao acessa-la via o alias fornecido para entidade e seu retorno será através do método *getSingleResult()*.
```java
    public BigDecimal buscarPrecoPorNome(String nome){
        /*
         * É possível retornar apenas um atributo de uma entidade ao fornecer o seu nome a consulta JPQL
         * Para isso, informamos seu nome ao acessa-lo pelo alias e fornecendo o nome da entidade
         * */
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = ?1";
        return this.entity.createQuery(jpql, BigDecimal.class)
                .setParameter(1, nome)
                .getSingleResult();
    }
```
<img align="middle" width="400" height="250" src="https://github.com/willdkdevj/jpa_hibernate/blob/master/assets/buscarPrecoPorNome.png">

### Consultas Nomeadas
A @NamedQuery anotação é definida como uma pré consulta com uma string de consulta que é imutável. Ao contrário de consultas dinâmicas consultas nomeadas pode melhorar o código organização, separando a JPQL seqüências de caracteres de consulta de EM POJO. Ela também passa a parâmetros de consulta em vez de incorporar o literais dinamicamente na string de consulta e, portanto, produz mais eficientes as consultas.
```java
@Entity
@Table
@NamedQuery(query = "Select e from Employee e where e.eid = :id", 
name = "find employee by id")
public class Employee 
{
   @Id
   @GeneratedValue(strategy= GenerationType.AUTO) 
   private int eid;
   private String ename;
   private double salary;
   private String deg;
   public Employee(int eid, String ename, double salary, String deg) 
   {
      super( );
      this.eid = eid;
      this.ename = ename;
      this.salary = salary;
      this.deg = deg;
   }
   public Employee( ) 
   {
      super();
   }

   \\ getter e setter
}
```

### Ansioso (EAGER) e Preguiçoso (LAZY)
É muito comum encontrar aplicações que têm problemas de performance por conta da camada de persistência, por conta de queries mal planejadas, que geram esses gargalos. Pois esquecemos de dar uma analisada nos comandos SQL que foram gerados, se não tem nenhum tipo de problema, nenhum tipo de consulta excessiva, algo que pode gerar algum impacto em performance nas aplicações.

isto ocorre devido ao **mapeamento do relacionamento**, na qual existe uma característica na JPA que explicita como deve ser realizado o tratamento para estas consultas relacionais. Por padrão, todo relacionamento que é *To One*, ou seja, *@Manytoone* ou *@OnetoOne*, será executado um comando *Select* que incluirá um **Join** para carregar outras entidades relacionadas a entidade principal.

Desta forma, este é o problema da lentidão nas consultas, devido a carregar todas as outras entidades para retornar a consulta. Mas muitas vezes só queremos consultar somente aquela entidade, mas por padrão, sempre é carregadas todas as entidades no contexto da consulta, ocasionando o gargalo.

Já os relacionamentos *To Many*, *@OnetoMany* ou *@ManytoMany*, não possuem essa característica de carregar todas as entidades atreladas a entidade principal. A JPA, ao verificar que se trata de uma lista, compreende que existe diversos registros, e que pode ser pesado. na qual poderia sobrecarregar o sistema com inúmeros registros para a memória do computador. Desta forma, tudo o que *To One* **é carregado automaticamente**, e tudo o que é *To Many* **não é carregado automaticamente**.

Este é o *comportamento padrão* da JPA, na qual é analisada a estratégia de carregamento dos relacionamentos a ser abordada conforme a anotação. Essa estratégia, ela tem dois possíveis comportamentos:
*   ***Eager*** (Ansioso) - Todo relacionamento ***To One***, o padrão é ele ser *eager*, ele faz o carregamento antecipado.
*   ***Lazy*** (Preguiçoso) - Já os relacionamentos ***To Many***, por padrão o comportamento é chamado de *lazy*, que é o carregamento preguiçoso, ou seja, o carregamento é tardio.

Então existem essas duas estratégias de carregamento, o carregamento *eager*, que carrega junto com a entidade, por mais que você não utilize aquele relacionamento, e o carregamento *lazy*, que só carrega se for feito o acesso. Desta maneira, a boa prática é todo relacionamento ***To One***, inserir um parêntese no *@ManyToOne* aplicando o parâmetro denominado ***fetch***, na qual permite controlar o carregamento através de dois valores, "EAGER" e "LAZY", sendo que, para *To One* o é aplicado o valor ***LAZY***.

```java
@ManyToOne(fetch = FetchType.LAZY)
private Cliente cliente;

```
Esta é uma boa prática, porém pode gerar um efeito colateral. A partir do momento que nós trocamos o relacionamento para ser lazy, podemos ter algum impacto na nossa aplicação. Porque pode acontecer de, queremos acessar um atributo de uma entidade atrelada a entidade principal que consultamos, mas o *Entity Manager* já estar fechado. desta forma, a JPA não vai conseguir disparar esse select para carregar essa informação, ocasionando uma *exception* **bem famosa do hibernate**, que é a tal da ***LazyInitializationException***.

Em uma aplicação real, uma aplicação web em uma API, por exemplo, pode acontecer de, no momento de você acessar uma determinada informação, o *Entity Manager* estar fechado, porque é muito comum nas aplicações, o entity manager ter seu escopo limitado apenas a chamada de um método, onde a solução seria utilizar uma ***query planejada***.

É uma query onde é planejado uma subconsulta a invocar um método auxiliar, na qual retorno as informações necessárias, para evitar fazer outros selects, para evitar tomar um lazy initialization exception se o entity manager estiver fechado.

Desta maneira, estas *Query Planejadas* são métodos criados nas classes DAO que realizam a consulta a entidade principal (nesta caso Pedido), e inserimos o **JOIN FETCH** apontando os parâmetros que relacionam estas entidades para obter o retorno que desejamos.
```java
public Pedido buscarPedidoComCliente(Long id){
    return entityManager.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
                        .setParameter("id", id)
                        .getSingleResult();
}
```

Agora temos uma consulta preparada para tratar um relacionamento especifico para obter uma informação.

## Agradecimentos
Obrigado por ter acompanhado aos meus esforços ao aplicar o conceito da especificação JPA utilizando o framework Hibernate. :octocat:

Como diria um velho mestre:
> *"Cedo ou tarde, você vai aprender, assim como eu aprendi, que existe uma diferença entre CONHECER o caminho e TRILHAR o caminho."*
>
> *Morpheus - The Matrix*
> 