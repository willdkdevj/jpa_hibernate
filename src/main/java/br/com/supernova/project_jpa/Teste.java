package br.com.supernova.project_jpa;

import br.com.supernova.project_jpa.dao.CategoriaDao;
import br.com.supernova.project_jpa.dao.ProdutoDao;
import br.com.supernova.project_jpa.enums.TipoProduto;
import br.com.supernova.project_jpa.exception.ErrorJPAException;
import br.com.supernova.project_jpa.factory.FactoryEntity;
import br.com.supernova.project_jpa.model.Cateogoria;
import br.com.supernova.project_jpa.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Teste {
    public static void main(String[] args) {
        Cateogoria celulares = new Cateogoria("Celulares");
        Produto produto = new Produto("Xiaomi Redmi", "Muito Bom", new BigDecimal(1300), TipoProduto.ELETRONICOS, celulares);

        CategoriaDao cateogoriaDao = new CategoriaDao(FactoryEntity.builderEntityManager());
        cateogoriaDao.cadastrar(celulares);
        ProdutoDao produtoDao = new ProdutoDao(FactoryEntity.builderEntityManager());
        produtoDao.cadastrar(produto);

    }
}