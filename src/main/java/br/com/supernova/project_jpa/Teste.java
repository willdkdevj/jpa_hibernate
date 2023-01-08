package br.com.supernova.project_jpa;

import br.com.supernova.project_jpa.dao.ProdutoDao;
import br.com.supernova.project_jpa.enums.TipoProduto;
import br.com.supernova.project_jpa.exception.ErrorJPAException;
import br.com.supernova.project_jpa.factory.FactoryEntity;
import br.com.supernova.project_jpa.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Teste {
    public static void main(String[] args) {
        Produto celular = new Produto("Xiaomi Redmi", "Muito Bom", new BigDecimal(1300), TipoProduto.ELETRONICOS);

        ProdutoDao dao = new ProdutoDao(FactoryEntity.builderEntityManager());
        dao.cadastrar(celular);

    }
}