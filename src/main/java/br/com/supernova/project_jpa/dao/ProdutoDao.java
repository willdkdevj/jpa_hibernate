package br.com.supernova.project_jpa.dao;

import javax.persistence.EntityManager;

import br.com.supernova.project_jpa.exception.ErrorJPAException;
import br.com.supernova.project_jpa.model.Produto;

import java.math.BigDecimal;
import java.util.List;

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

    public void remover(Produto produto){
        this.entity = entity.merge((EntityManager) produto);
        this.entity.remove(produto);
    }

    public void atualizar(Produto produto){
        this.entity.merge((EntityManager) produto);

    }

    public Produto buscarPorId(Long id){
        return this.entity.find(Produto.class, id);
    }

    public List<Produto> buscarTodos(){
        /*
         * Ao invés do nome da tabela do banco de dados é informado o nome da entidade (Neste caso é Produto)
         * Tambén não é informado o * para retornar todos é informado um alias (Neste caso é a letra p)
         * */
        String jpql = "SELECT p FROM Produto p";
        return this.entity.createQuery(jpql).getResultList();
    }

    public List<Produto> buscarPorCateogoria(String nome){
        /*
         * Ao invés do nome da tabela do banco de dados é informado o nome da entidade (Neste caso é Produto)
         * Tambén não é informado o * para retornar todos é informado um alias (Neste caso é a letra p)
         * */
        String jpql = "SELECT p FROM Produto p WHERE p.cateogoria.nome = ?1";
        return this.entity.createQuery(jpql)
                .setParameter(1, nome)
                .getResultList();
    }

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
}
