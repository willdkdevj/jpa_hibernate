package br.com.supernova.project_jpa.dao;

import br.com.supernova.project_jpa.exception.ErrorJPAException;
import br.com.supernova.project_jpa.model.Cateogoria;

import javax.persistence.EntityManager;

public class CategoriaDao {

    private EntityManager entity;

    public CategoriaDao(EntityManager entity) {
        this.entity = entity;
    }

    public void cadastrar(Cateogoria categoria) {
        try{
            /* Inicia escopo transacional */
            entity.getTransaction().begin();

            /* Invoca instância de persistência (INSERT) */
            this.entity.persist(categoria);

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
