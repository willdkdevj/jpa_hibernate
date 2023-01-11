package br.com.supernova.project_jpa;

import br.com.supernova.project_jpa.dao.CategoriaDao;
import br.com.supernova.project_jpa.dao.ProdutoDao;
import br.com.supernova.project_jpa.enums.TipoProduto;
import br.com.supernova.project_jpa.factory.FactoryEntity;
import br.com.supernova.project_jpa.model.Cateogoria;
import br.com.supernova.project_jpa.model.Produto;

import java.math.BigDecimal;
import java.util.List;

public class Teste {
    public static void main(String[] args) {
        cadastrarProduto();

    }

    private static void cadastrarProduto() {
        Cateogoria celulares = new Cateogoria("Celulares");
        Produto produto = new Produto("Xiaomi Redmi", "Muito Bom", new BigDecimal(1300), TipoProduto.ELETRONICOS, celulares);

        CategoriaDao cateogoriaDao = new CategoriaDao(FactoryEntity.builderEntityManager());
        cateogoriaDao.cadastrar(celulares);
        ProdutoDao produtoDao = new ProdutoDao(FactoryEntity.builderEntityManager());
        produtoDao.cadastrar(produto);

//        List<Produto> produtoList = buscarPorCategoriaDeProduto("Celulares");
//        produtoList.forEach(p -> System.out.println(p));
//        System.out.println(buscarProdutoPorNome("Xiaomi Redmi"));
        buscarPrecoPorProduto("Xiaomi Redmi");
    }

    private static List<Produto> buscarListaProdutos() {
        ProdutoDao produtoDao = new ProdutoDao(FactoryEntity.builderEntityManager());
        return produtoDao.buscarTodos();
    }

    private static Produto buscarProdutoPorNome(String nome){
        ProdutoDao produtoDao = new ProdutoDao(FactoryEntity.builderEntityManager());
        return produtoDao.buscarPorNome(nome);
    }

    private static List<Produto> buscarPorCategoriaDeProduto(String nome){
        ProdutoDao produtoDao = new ProdutoDao(FactoryEntity.builderEntityManager());
        return produtoDao.buscarPorCateogoria(nome);
    }

    private static BigDecimal buscarPrecoPorProduto(String nome){
        ProdutoDao produtoDao = new ProdutoDao(FactoryEntity.builderEntityManager());
        return produtoDao.buscarPrecoPorNome(nome);
    }
}