package br.com.supernova.project_jpa.model;

import javax.persistence.*;

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
