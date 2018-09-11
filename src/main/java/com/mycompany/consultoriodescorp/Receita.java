package com.mycompany.consultoriodescorp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author valeria
 */
@Entity
@Table(name = "TB_RECEITA")
public class Receita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_NOME_REMEDIO")
    private String nomeRemedio;
    @Column(name = "TXT_TRATAMENTO")
    private String Tratamento;
    @Column(name = "TXT_DURACAO")
    private String duracao;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Funcionario medico;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_RECEITAS_PACIENTES", joinColumns = {
        @JoinColumn(name = "ID_RECEITA")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_USUARIO")})
    private List<Receita> receitas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeRemedio() {
        return nomeRemedio;
    }

    public void setNomeRemedio(String nomeRemedio) {
        this.nomeRemedio = nomeRemedio;
    }

    public String getTratamento() {
        return Tratamento;
    }

    public void setTratamento(String Tratamento) {
        this.Tratamento = Tratamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Funcionario getMedico() {
        return medico;
    }

    public void setMedico(Funcionario medico) {
        this.medico = medico;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(Receita receita) {
        if (this.receitas == null) {
            this.receitas = new ArrayList<>();
        }
        this.receitas.add(receita);

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Receita other = (Receita) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
