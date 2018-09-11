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
@Table(name = "TB_EXAME")
public class Exame implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_NOME")
    private String nome;
    @ManyToOne()
    @JoinColumn(name = "ID_USUARIO")
    private Funcionario medico;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_EXAMES_PACIENTES", joinColumns = {
        @JoinColumn(name = "ID_EXAME")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_USUARIO")})
    private List<Exame> exames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcionario getMedico() {
        return medico;
    }

    public void setMedico(Funcionario medico) {
        this.medico = medico;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void setExames(Exame exame) {
        if (this.exames == null) {
            this.exames = new ArrayList<>();
        }
        this.exames.add(exame);
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
        final Exame other = (Exame) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
