package br.com.supernova.project_jpa.model;

import br.com.supernova.project_jpa.enums.TipoProduto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    @ManyToOne
    private Cateogoria cateogoria;

    public Produto() {
    }

    public Produto(String nome, String descricao, BigDecimal preco, TipoProduto tipo, Cateogoria cateogoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.dataCriacao = LocalDate.now();
        this.tipo = tipo;
        this.cateogoria = cateogoria;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public TipoProduto getTipo() {
        return tipo;
    }

    public Cateogoria getCateogoria() {
        return cateogoria;
    }
}
