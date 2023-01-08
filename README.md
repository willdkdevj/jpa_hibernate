# Aplicação do JPA em um Projeto Maven
Utilização da especificação JPA com o framework Hibernate para utilização de persistência em um projeto Maven Java

[![Maven Badge](https://img.shields.io/badge/-Maven-black?style=flat-square&logo=Maven&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)
[![JPA Badge](https://img.shields.io/badge/-JPA-blue?style=flat-square&logo=JPA&logoColor=white&link=https://docs.jboss.org/author/display/AS71/JPA%20Reference%20Guide.html)](https://docs.jboss.org/author/display/AS71/JPA%20Reference%20Guide.html)
[![Hibernate Badge](https://img.shields.io/badge/-Hibernate-green?style=flat-square&logo=Hibernate&logoColor=white&link=https://docs.jboss.org/hibernate/orm/current/quickstart/html_single/)](https://docs.jboss.org/hibernate/orm/current/quickstart/html_single/)

## Por que utilizar a especificação JPA
O JDBC surgiu para fazer o papel de interpretador entre os bancos de dados e as aplicações desenvolvidas em Java. Mas existe alguns fatores em sua estrutura que complica sua manutenção e operabilidade nos projetos que são o **alto acoplamento** com banco de dados, onde qualquer mudança no banco de dados gera um impacto enorme na aplicação, além disso, o segundo fator problemático desta implmentação é que sua estrutura é **muito verbosa**, o que ocasiona a escrita de muito código para torná-la efetiva ao projeto.

<img align="right" width="400" height="250" src="https://github.com/willdkdevj/real_threads/blob/master/assets/JPAHibernate.jpg">

Este cenário que motivou a comunidade Java a elaborar uma solução que permitisse uma forma de comunicação das aplicações com os bancos de dados com as aplicações que eram desenvolvidas, mas que não fossem trabalhosa a mudanças de base de dados refletidas na aplicação, assim como, até mesmo a mudança da estrutura de um banco de dados, como a substituição de um *MySQL* para um *Oracle*, por exemplo.
Além disso, a ideia principal era não ter tanto esforço para implementar este tipo de camada de comunicação interpretativa entre eles, desta forma, surgiu a especificação JPA, mas antes surgiu o framework Hibernate.

## O framework Hibernate
O *Hibernate* foi lançado em 2001 (criado por Gavin King) que foi desenvolvido justamente para simplificar o uso do **JDBC**, ela é uma biblioteca que surgiu no mercado como *Open Source* que agora recebe a tutela da **JBoss - Red Hat** que ganhou vários adeptos por sua facilidade de implementação.

## A Especificação JPA
Como o *Hibernate* ficou muito popular nos projetoso Java, também surgiram concorrentes prometendo a mesma facilidade ou até melhor no mercado, gerando um problema de compatibilidade entre a implementação de uma biblioteca e a decisão de substituí-la por outra de mercado, impactando consideravelmente o código.
Posteriormente foi padronizada uma estrutura de bibliotea com o objetivo de criar um modelo de persistência, conhecida como *Object Ralational Mapping - ORM*, para realizar o mapeamento entre a *orientação a objetos* e os *relacionamentos* dos bancos de dados, sendo gerado a base da *Java Persistence API*, que é uma especificação para padronizar este mapeamento a objetos relacionais.

## Hibernate + JPA
O **Hibernate**,  assim como os demais frameworks de mercado, se tornaram uma implementação, na qual as mesmas para permanecerem atuantes no mercado devem seguir a especificação JPA, para permitir que não sejam fortemente acopladas a projetos Java. Em 2010 foi lançada a versão 3.5.0 do Hibernate que era compatível com a versão 2.0 da JPA, isto significa que basta trocar seu jar e seus importes para permitir a substituição dos mesmos sem ocasionar impactos relevantes aos projetos.CqODs

<img align="middle" width="250" height="250" src="https://github.com/willdkdevj/real_threads/blob/master/assets/CqODs.png">

Para trabalharmos com a JPA, tecomo que escolher um framework para implementa-la, não dá para utilizar a JPA "pura" porque ela é só a "casca", ou seja, uma abstração.

## Projeto Maven e suas dependências
O **Maven** é uma ferramenta desenvolvida pela *Apache*, ela serve para gerenciar as dependências e automatizar seus *builds*. sendo o arquivo **pom.xml** o responsável por aplicar suas configurações ao projeto.
Desta forma, para implementarmos o Hibernate ao projeto devemos explicitar sua dependência utilizando as tags abaixo.
```java
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
```java
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

</persistence>

```
A **tag** de principal importancia para a configuração da JPA é a tag **persistence-unit**, na qual é uma tag *obrigatória*, que é onde definimos os *namespaces*. Nela temos dois parâmetros, que são o *name*, na qual podemos nomear como desejado para depois apontar qual configuração desejamos utilizar, e o *transaction-type* que define a estratégia a ser utilizada para implementar a configuração, na qual possuí dois valores, o **JTA** que é adequada quando estamos utilizando um servidor de aplicações (EJB, JMS, JEE) onde o servidor se encarrega de cuidar das transações, e o **RESOURCE-LOCAL** para aplicações locais (*stand-alone*) que não possuem um servidor de aplicações, sendo seu processamento realizados na mesma instância.
```java
<persistence-unit name="jpa_implement" transaction-type="RESOURCE_LOCAL">

</persistence-unit>

```

A persistence-unit representa uma unidade de persistência, isso significa que, que ela será a representação do banco de dados que desejamos conectar. Desta forma, poderiamos ter mais tag's persistence-unit com outras configurações representando outras configurações de acesso a bancos, mas no momento de iniciar a implementação do framework devemos apontar somente uma configuração ativa que definirá o banco a ser acessado.
> OBS: No nosso caso, foi apelidado o nome de **jpa_implement** mas nada impediria de colocar um nome que definisse com mais exatidão o banco de dados ou base de dados que se deseja relacionar.

Na estrutura da tag **persistene-unit** adicionamos quais são as propriedades a serem aplicadas no contexto da JPA. Desta maneira, implementamos em sua estrutura a tag ***properties***, e em seu contexto, aplicamos cada propriedade que definirá a atuação no cenário JPA, aplicadas com a tag ***property*** na qual tem como parãmetros um nome (*name*) e o valor (*value*).
```java
    <persistence-unit name="jpa_implement" transaction-type="RESOURCE_LOCAL">
        <properties>
                <property name="" value=""/>
            </properties>
    </persistence-unit>
 </persistence>

```

As propriedades da JPA também tem caracterisiticas obrigatórias de exposição. Isto significa que, é necessário informar as caracteristicas do banco de dados a ser utilizado pela implementação. A propriedade *name* possui um nome especifico atribuido ao banco de dados escolhido que precisa ser informado, assim como, o parâmetro *value* relacionado ao valor, que definem a definição do driver de conexão com o banco de dados. Aqui no projeto, como selecionamos o banco de dados *H2* as definições ficaram da seguinte forma.
```java
    <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
```
> OBS:  O driver mudará de acordo com o banco de dados escolhido, desta forma, caso fosse o **MySQL**, o driver seria ***com.mysql.driver***, se fosse **PostgreSQL**, seria ***org.postgresql.driver*** e assim por diante. 

A JPA é uma abstração do framework do JDBC que simplifica o seu uso, mas que aplica todas as suas interações para realizar esta interpretação da camada orientada a objetos com o modelo relacional.

Além do driver, é necessário configurar a JPA indicando qual é o caminho (URL) do banco de dados, isto é, onde está o endereço de conexão com o banco de dados. Esse endereço também varia de acordo com o banco de dados. 
```java
    <property name="javax.persistence.jdbc.url" value="jdbc:h2:mem:project_jpa"/>

```
> OBS: No caso do H2, será "jdbc:h2:mem:project_jpa". Isto é, queremos que o database no H2 se chame *Project JPA*. 

Todo banco de dados possui suas definições de usuário e senha. Portanto, teremos mais duas propriedades que precisamos definir para configuração da JPA, que são , a *"jdbc.user"*, e a *"jdbc.password"*. 
```java
    <property name="javax.persistence.jdbc.user" value="sa"/>
    <property name="javax.persistence.jdbc.password" value=""/>
```
> OBS: No caso do H2, será "sa" (geralmente usamos esse usuário no H2), e a senha ficará em branco value"".


Outra propriedade importante para esclarecer para JPA o dialeto, quer dizer, como a aplicação conversará com o banco de dados, é a propriedade ***"hibernate.dialect"***. O Hibernate precisa saber qual é a classe que tem o dialeto do banco de dados, na qual esclarece as particularidades do banco de dados. Como cada banco de dados pode ter as suas particularidades, o dialeto é o que fará a comunicação correta com o banco de dados. 
```java
    <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
```
> OBS: No caso do H2, será "org.hibernate.dialect.H2Dialect". 

### Entidades (Entity)


### Entity Manager


