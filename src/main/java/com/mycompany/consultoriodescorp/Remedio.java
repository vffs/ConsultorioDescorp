
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author valeria
 */
@Entity
@Table(name="TB_REMEDIO")
public class Remedio implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID_REMEDIO")
    private Long id;
    @Column(name="CL_NOME_REMEDIO")
    private String nomeRemedio;
    @Column(name = "CL_TRATAMENTO")
    private String tratamento;
    @Column(name = "CL_DURACAO")
    private String duracao;
    
    @OneToMany(mappedBy = "remedio",fetch=FetchType.LAZY)
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
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }
    
    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void adicionarReceitas(Receita receita) {
        if(this.receitas == null){
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
        final Remedio other = (Remedio) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
    
}
