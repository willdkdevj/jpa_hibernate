package br.com.supernova.project_jpa;

import br.com.supernova.project_jpa.factory.FactoryEntity;
import br.com.supernova.project_jpa.model.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class Teste {
    public static void main(String[] args) {
        Produto celular = new Produto("Xiaomi Redmi", "Muito Bom", new BigDecimal(1300));

        EntityManager entityManager = FactoryEntity.builderEntityManager();
        entityManager.persist(celular);

    }
}